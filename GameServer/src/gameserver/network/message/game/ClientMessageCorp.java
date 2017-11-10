package gameserver.network.message.game;

import gameserver.connection.attribute.ConPlayerAttr;
import gameserver.connection.attribute.info.BuildInfo;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.IOServer;
import gameserver.network.protos.game.CommonProto.Proto_BuildState;
import gameserver.network.protos.game.ProBuild;
import gameserver.network.server.connection.Connection;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;
import gameserver.utils.TransFormArgs;
import table.Int2;
import table.MT_Data_Corps;
import table.MT_Data_TechInfo;

import commonality.Common;
import commonality.Common.LIMIT_TYPE;
import commonality.Common.SETNUMBER_TYPE;
import commonality.Common.TASK_TYPE;
import commonality.ITEM_ID;
import commonality.ProductChannel;
import commonality.PromptType;
import commonality.VmChannel;

public class ClientMessageCorp {
	private final static ClientMessageCorp instance = new ClientMessageCorp();

	public static ClientMessageCorp getInstance() {
		return instance;
	}

	public void initialize() {
		IOServer.getInstance().regMsgProcess(ProBuild.Msg_C2G_MakeCorps.class,this, "OnMakeCorps");
		IOServer.getInstance().regMsgProcess(ProBuild.Msg_C2G_MakeCorpsOver.class,this, "OnMakeCorpsOver");
		
		IOServer.getInstance().regMsgProcess(ProBuild.Msg_C2G_CorpsUpgrade.class,this, "OnCorpsUpgrade");
		IOServer.getInstance().regMsgProcess(ProBuild.Msg_C2G_CorpsUpgradeOver.class,this, "OverCorpsUpgrade");
		
		IOServer.getInstance().regMsgProcess(ProBuild.Msg_C2G_ScienceUpgrade.class,this, "OnScienceUpgrade");
		IOServer.getInstance().regMsgProcess(ProBuild.Msg_C2G_ScienceUpgradeOver.class,this, "OverScienceUpgrade");
		
		IOServer.getInstance().regMsgProcess(ProBuild.Msg_C2G_DeleteCorps.class,this, "OnDeleteCorps");
		IOServer.getInstance().regMsgProcess(ProBuild.Msg_C2G_FastFinished.class,this, "OnFastFinished");
	}
	

	//产兵
	public void OnMakeCorps(Connection connection, ProBuild.Msg_C2G_MakeCorps msg) throws Exception {
		BuildInfo build = connection.getBuild().getBuild(msg.getBuildId());
		if (build == null)
			throw new GameException("玩家没有这个id的建筑");

		build.checkBuild();
		if (build.getState() == Proto_BuildState.OPERATE_UPGRADE || 
				build.getState() == Proto_BuildState.UPGRADE)
			throw new GameException(PromptType.BUILD_STATE_ERR);
		
		// 检测是否超过当前建筑的最大队列
		if (build.getCurMakeCorpNum() >= build.getCurBuildMaxCorp())
			throw new GameException(PromptType.CORP_QUEUE_NOT_ENOUGH);

		// 检测兵种是否合法，计算占用人口数
		int corpTableId = msg.getTableId();
		int makeCount = msg.getCount();
		if (makeCount <= 0)
			return ;
		
		if (!build.IsCorpsLegal(corpTableId))
			throw new GameException("兵种非法");

		MT_Data_Corps corpsData = connection.getCorps().getCorpsDataByTableId(corpTableId);
		if (corpsData == null)
			return ;
		
		// 检测是否达到人口上限
		// 当前总人口计算 = 全部所有现有人口 + 各个建筑正在建造的人口 + 本次想要建筑的人口(目前是一次一个单位) + 城防中的人口
		int willMakeCount = corpsData.Population() * makeCount;
		int makingCorpsNumber = connection.getBuild().GetMakePopulation();
		int defensCorpNumber = connection.getPlayer().getDefensePopulation();
		if (connection.getCommon().GetValue(LIMIT_TYPE.PEOPLE) < 
				willMakeCount + connection.getCorps().getAllPopulation() + makingCorpsNumber + defensCorpNumber)
			throw new GameException(PromptType.POPULATION_FULL);

		// 判断资源是否达标
		Int2 needRes = corpsData.NeedResource();
		if (needRes.field1().equals(ITEM_ID.MONEY)){
			int needMoney = calcCorpMakeNeedMoney(connection, needRes);
			if (!connection.getItem().checkItemEnough(needRes.field1(), needMoney))
				return ;
		}else {
			if (!connection.getItem().checkItemEnoughByInt2(needRes))
				return ;
		}

		// 条件达成
		long s_num = IPOManagerDb.getInstance().getNextId();
		if (needRes.field1().equals(ITEM_ID.MONEY)) {
			int needMoney = calcCorpMakeNeedMoney(connection, needRes);
			connection.getItem().setItemNumber(needRes.field1(),needMoney, SETNUMBER_TYPE.MINUS,
					VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"",Item_Channel.MAKE_CORP);
		}else {
			connection.getItem().setItemNumber(needRes.field1(),needRes.field2(), SETNUMBER_TYPE.MINUS,
					VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"",Item_Channel.MAKE_CORP);
		}

		connection.getTasks().AddTaskNumber(TASK_TYPE.CORPS, corpTableId, makeCount, 0);
		build.addMakeCorps(corpTableId, makeCount);

		//产兵开始记录日志
		int lev = connection.getCorps().getCorpsLevel(corpTableId);
		LogService.logCropUp(corpTableId,lev,lev,1, s_num, connection.getPlayerId(), 0, connection.getPlayer().getLevel());
	}

	private int calcCorpMakeNeedMoney(Connection connection, Int2 needRes) {
		int needMoney = connection.getBuffs().getValueByDecPassiveBuff(Common.PASSIVEBUFF_TYPE.CORPS_OUT_MONEY, needRes.field2());
		return needMoney;
	}
	
	// 客户端造兵倒计时结束，服务器判断造兵结束
	public void OnMakeCorpsOver(Connection connect,ProBuild.Msg_C2G_MakeCorpsOver msg) throws Exception {
		BuildInfo build = connect.getBuild().getBuild(msg.getBuildId());
		if (build == null)
			throw new GameException("玩家没有这个id的建筑");
		//返回生产成功的兵种列表
		build.checkBuild();
	}

	public void OnDeleteCorps(Connection connection, ProBuild.Msg_C2G_DeleteCorps msg) throws Exception {
		BuildInfo build = connection.getBuild().getBuild(msg.getBuildId());
		if (build == null)
			throw new GameException("玩家没有这个id的建筑");

		build.checkBuild();

		if (build.isNormalState() || build.isUpgradState())
			throw new GameException(PromptType.BUILD_STATE_ERR);
		
		build.delMakeCorps(msg.getTableId(), msg.getCount());
	}

	public void OnFastFinished(Connection connection,ProBuild.Msg_C2G_FastFinished message) throws Exception {
		BuildInfo build = connection.getBuild().getBuild(message.getBuildId());
		if (build == null)
			throw new GameException("玩家没有这个id的建筑");
		
		build.checkBuild();
		build.operateSpeedUp();
		connection.setNeed_recalc_fight_val(true);
	}
	
	//科技升级完成
	public void OverScienceUpgrade(Connection connect, ProBuild.Msg_C2G_ScienceUpgradeOver msg) throws Exception {
		BuildInfo build = connect.getBuild().getBuild(msg.getBuildId());
		if (build == null)
			throw new GameException("玩家没有这个id的建筑");
		
		int tableId = build.getCorpsArray().get(0).id;
		int oldLev = connect.getTech().getTechLevel(tableId);
		int oldbuff = connect.getTech().getTechBuffid(tableId);
		build.checkBuild();
		sendMsg(connect, msg, tableId);
		connect.setNeed_recalc_fight_val(true);
		
		ClientMessagePassiveBuff.getInstance()
			.UpdateTechPassiveBuff(connect, oldbuff, connect.getTech().getTechBuffid(tableId));
		
		//科技升级完成记录日志
		int newLev = connect.getTech().getTechLevel(tableId);
		LogService.logTechUp(tableId, oldLev,newLev, 0,connect.getPlayerId(),1, connect.getPlayer().getLevel());
	}

	private void sendMsg(Connection connect,
			ProBuild.Msg_C2G_ScienceUpgradeOver msg, int tableId) {
		ProBuild.Msg_C2G_ScienceUpgradeOver.Builder builder = ProBuild.Msg_C2G_ScienceUpgradeOver.newBuilder();
		builder.setBuildId(msg.getBuildId());
		builder.setTableId(tableId);
		connect.sendPushMessage(builder.build());
	}

	//科技升级
	public void OnScienceUpgrade(Connection connection, ProBuild.Msg_C2G_ScienceUpgrade msg) throws Exception {
		BuildInfo build = connection.getBuild().getBuild(msg.getBuildId());
		if (build == null)
			throw new GameException("玩家没有这个id的建筑");

		build.checkBuild();

		if (!build.isTech())
			throw new GameException("建筑类型不是科技");
		if (connection.getTech().techIsMaxLevel(msg.getTableId()))
			throw new GameException(PromptType.TECH_IS_FULL);

		ConPlayerAttr player = connection.getPlayer();
		int CorpsLv = connection.getTech().getTechLevel(msg.getTableId());
		MT_Data_TechInfo mtTableScience = connection.getTech().getTechData(msg.getTableId()+CorpsLv);//取得玩家科技模型数据
		int needMoney = calcTechMoney(connection, mtTableScience);
		if (!connection.getItem().checkItemEnough(ITEM_ID.MONEY, needMoney))
			return ;
		if (!connection.getItem().checkItemEnough(ITEM_ID.RARE, mtTableScience.UpgradeNeedRare()))
			return ;
		if (!player.CheckPlayerLevel(mtTableScience.UpgradePlayerLv(), true))
			return ;
		if (build.getState() == Proto_BuildState.UPGRADE || build.getState() == Proto_BuildState.OPERATE_UPGRADE)
			throw new GameException(PromptType.BUILD_STATE_ERR);
		if (build.isContainsCorp(msg.getTableId())) 
			throw new GameException(PromptType.BUILD_STATE_ERR);

		build.techUpgrade(msg.getTableId());//修改建筑信息

		//更新金币,稀有资源
		long s_num = IPOManagerDb.getInstance().getNextId();
		connection.getItem().setItemNumber(ITEM_ID.MONEY, needMoney, SETNUMBER_TYPE.MINUS,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"",Item_Channel.TECH_UP);
		connection.getItem().setItemNumber(ITEM_ID.RARE,mtTableScience.UpgradeNeedRare(), SETNUMBER_TYPE.MINUS,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"",Item_Channel.TECH_UP);
		
		//科技升级日志记录
		int lev= connection.getTech().getTechLevel(msg.getTableId());
		LogService.logTechUp(msg.getTableId(),lev,lev,s_num,connection.getPlayerId(),0, connection.getPlayer().getLevel());
		
		//自动发起帮助
		ClientMessageBuild.getInstance().auToSeekHelp(connection, build);
	}
	
	// 计算指定兵种升级所需的金钱
	private int calcTechMoney(Connection connection, MT_Data_TechInfo mtDataCorps) {
		int needMoney = connection.getBuffs().getValueByDecPassiveBuff(Common.PASSIVEBUFF_TYPE.DEC_TECH_MONEY_COST, mtDataCorps.UpgradeNeedMoney());
		return needMoney;
	}
		
	// 兵种升级
	public void OnCorpsUpgrade(Connection connection, ProBuild.Msg_C2G_CorpsUpgrade msg) throws Exception {
		BuildInfo build = connection.getBuild().getBuild(msg.getBuildId());
		if (build == null)
			throw new GameException("玩家没有这个id的建筑");
		
		build.checkBuild();
		
		// 兵种当前建筑判断
		if (!build.isInstitute())
			throw new GameException("建筑不是训练场类型");
		if (build.isMaxOut())
			throw new GameException(PromptType.BUILD_STATE_ERR);

		int CorpsLv = connection.getCorps().getCorpsLevel(msg.getTableId());
		MT_Data_Corps mtDataCorps = connection.getCorps().getCorpsDataByTableId(msg.getTableId() + CorpsLv);// 取得玩家模型数据
		int needMoney = calcUpgradeCorpMoney(connection, mtDataCorps);
		
		ConPlayerAttr player = connection.getPlayer();
		if (!connection.getItem().checkItemEnough(ITEM_ID.MONEY, needMoney))
			return ;
		if (!connection.getItem().checkItemEnough(ITEM_ID.RARE, mtDataCorps.UpgradeNeedRare()))
			return ;
		if (!player.CheckPlayerLevel(mtDataCorps.UpgradePlayerLv(), true))
			return ;
		if (CorpsLv == 9)
			throw new GameException(PromptType.CORP_LEVEL_MAX);
		if (build.getState() == Proto_BuildState.UPGRADE || build.getState() == Proto_BuildState.OPERATE_UPGRADE)
			throw new GameException(PromptType.BUILD_STATE_ERR);
		if (build.isContainsCorp(msg.getTableId()))
			throw new GameException(PromptType.BUILD_STATE_ERR);
		if (build.getTableId() != mtDataCorps.UpgradeBuildLv().field1() ||
				build.getLevel() < mtDataCorps.UpgradeBuildLv().field2())
		{
			connection.ShowPrompt(PromptType.CORP_UP_NEED_BUILD, TransFormArgs.CreateCorpArgs(mtDataCorps.ID()), 
					TransFormArgs.CreateBuildArgs(build.getDataBuilding().SpriteName(), 1), 
					mtDataCorps.UpgradeBuildLv().field2());
			return;
		}

		build.corpsUpgrade(msg.getTableId());
		//更新金币,稀有资源
		long s_num = IPOManagerDb.getInstance().getNextId();
		connection.getItem().setItemNumber(ITEM_ID.MONEY, needMoney, SETNUMBER_TYPE.MINUS,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"",Item_Channel.CORP_UP);
		connection.getItem().setItemNumber(ITEM_ID.RARE, mtDataCorps.UpgradeNeedRare(), SETNUMBER_TYPE.MINUS,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"",Item_Channel.CORP_UP);
		
		//记录兵种升级开始的日志
		int lev = connection.getCorps().getCorpsLevel(msg.getTableId());
		LogService.logCropUp(msg.getTableId(),lev,lev,0,s_num,connection.getPlayerId(),0,connection.getPlayer().getLevel());
		
		//自动发起帮助
		ClientMessageBuild.getInstance().auToSeekHelp(connection, build);
	}
	
	// 兵种升级完成
	public void OverCorpsUpgrade(Connection connect, ProBuild.Msg_C2G_CorpsUpgradeOver msg) throws Exception {
		BuildInfo build = connect.getBuild().getBuild(msg.getBuildId());
		if (build == null)
			throw new GameException("玩家没有这个id的建筑");
		if (build.getCorpsArray().isEmpty())
			throw new GameException("升级兵种出错");
		
		int cropTableId = build.getCorpsArray().get(0).id;
		int oldlevel = connect.getCorps().getCorpsLevel(cropTableId);
		build.checkBuild();
		
		//记录兵种升级完成的日志
		int newlevel = connect.getCorps().getCorpsLevel(cropTableId);
		LogService.logCropUp(cropTableId, oldlevel, newlevel, 0, 0, connect.getPlayerId(), 1,connect.getPlayer().getLevel());
		connect.setNeed_recalc_fight_val(true);
	}
	
	// 计算指定兵种升级所需的金钱
	private int calcUpgradeCorpMoney(Connection connection, MT_Data_Corps mtDataCorps) {
		int needMoney = connection.getBuffs().getValueByDecPassiveBuff(Common.PASSIVEBUFF_TYPE.CORPS_UP_MONEY, mtDataCorps.UpgradeNeedMoney());
		return needMoney;
	}

}