package gameserver.network.message.game;

import gameserver.active.ActiveService;
import gameserver.connection.attribute.ConBuildAttr;
import gameserver.connection.attribute.ConItemAttr;
import gameserver.connection.attribute.ConPlayerAttr;
import gameserver.connection.attribute.LegionManager;
import gameserver.connection.attribute.info.BuildInfo;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.IOServer;
import gameserver.network.protos.common.ProLegion;
import gameserver.network.protos.common.ProLegion.LegionInfo_enum;
import gameserver.network.protos.common.ProLegion.LegionMemberType;
import gameserver.network.protos.common.ProLegion.Proto_HelpType;
import gameserver.network.protos.common.ProLegionHelp.Msg_G2S_RequestList;
import gameserver.network.protos.game.CommonProto.Proto_BuildState;
import gameserver.network.protos.game.CommonProto.Proto_Off_Line;
import gameserver.network.protos.game.ProBuild;
import gameserver.network.protos.game.ProFight;
import gameserver.network.protos.game.ProGameInfo;
import gameserver.network.protos.game.ProGameInfo.Msg_G2C_InfoFinished;
import gameserver.network.protos.game.ProGameInfo.Msg_G2C_InfoReady;
import gameserver.network.protos.game.ProHero;
import gameserver.network.protos.game.ProHint.Msg_G2C_Prompt.PROMPT_SCENE;
import gameserver.network.protos.game.ProPlayerChange.Msg_G2C_Player_Val_Change;
import gameserver.network.protos.game.ProPve;
import gameserver.network.protos.game.ProPve.Msg_G2C_FormationSync;
import gameserver.network.protos.game.ProPve.Proto_CorpsType;
import gameserver.network.protos.game.ProPvp;
import gameserver.network.protos.game.ProPvp.Proto_ActionType;
import gameserver.network.protos.game.ProReward;
import gameserver.network.protos.game.ProReward.Proto_RewardType;
import gameserver.network.server.connection.Connection;
import gameserver.thread.ThreadPoolManager;
import gameserver.utils.DbMgr;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;
import gameserver.utils.TransFormArgs;
import gameserver.utils.Util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.Int3;
import table.MT_DataBase;
import table.MT_Data_BuildBuyMoney;
import table.MT_Data_BuildNumLimit;
import table.MT_Data_Building;
import table.MT_Data_Land;
import table.MT_Data_Luckdraw;
import table.MT_TableEnum;
import table.base.TABLE;
import table.base.TableManager;

import com.commons.util.TimeUtil;
import commonality.Common;
import commonality.Common.SETNUMBER_TYPE;
import commonality.Common.SPEEDUP_TYPE;
import commonality.Common.TASK_TYPE;
import commonality.ITEM_ID;
import commonality.ProductChannel;
import commonality.PromptType;
import commonality.VmChannel;

import database.CustomFormation;
import database.DatabasePlayer_offline_val;
import databaseshare.CustomLegionMeber;
import databaseshare.DatabaseAttack_report;
import databaseshare.DatabaseDefend_report;
import databaseshare.DatabaseLegion;
import databaseshare.DatabaseRecharge_gold_back;
import databaseshare.DatabaseRequestHelp;

public class ClientMessageBuild {
	private final static ClientMessageBuild instance = new ClientMessageBuild();
	public static ClientMessageBuild getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(ClientMessageBuild.class);
	public void initialize() {
		IOServer.getInstance().regMsgProcess(ProBuild.Msg_C2G_AskBuildingInfo.class, this, "OnAskBuild");
    	IOServer.getInstance().regMsgProcess(ProBuild.Msg_C2G_CreateBuild.class, this, "OnCreateBuild");
    	IOServer.getInstance().regMsgProcess(ProBuild.Msg_C2G_BuildMove.class, this, "OnBuildMove");
    	IOServer.getInstance().regMsgProcess(ProBuild.Msg_C2G_BuildUpgrade.class, this, "OnBuildUpgrade");
    	IOServer.getInstance().regMsgProcess(ProBuild.Msg_C2G_BuildUpgradeOver.class, this, "OnBuildUpgradeOver");
    	IOServer.getInstance().regMsgProcess(ProBuild.Msg_C2G_ExpandLand.class, this, "OnExpandLand");
    	IOServer.getInstance().regMsgProcess(ProBuild.Msg_C2G_BuildSpeedUp.class, this,"OnBuildSpeedUp");
    	IOServer.getInstance().regMsgProcess(ProBuild.Msg_C2G_GatherResource.class, this,"OnGatherResource");
    }
	
	// 扩地
	public void OnExpandLand(Connection connection, ProBuild.Msg_C2G_ExpandLand message) throws GameException  {
		// 判断是否达到扩地上限
		ConPlayerAttr player = connection.getPlayer();
		int number = player.getExtend();
		if (number >= TableManager.GetInstance().getMaxLand())
			throw new GameException("已达到扩地最大次数");
		
		// 取得扩地模板
		MT_Data_Land dataLand = TableManager.GetInstance().TableLand().GetElement(number);
		if (dataLand == null)
			throw new GameException("dataLand is null " + number);
		
		// 判断主城是否满足条件
		if (!connection.getBuild().checkmainCityLevel(dataLand.UpgradeMainLv(), true))
			return ;
		
		// 判断玩家等级是否达到
		if (!connection.getPlayer().CheckPlayerLevel(dataLand.UpgradePlayerLv(), true))
			return ;
		
		// 判断金币是否足够
		if(!connection.getItem().checkItemEnough(ITEM_ID.MONEY, dataLand.UpgradeNeedMoney()))
			return ;
		
		long s_num = IPOManagerDb.getInstance().getNextId();
		connection.getItem().setItemNumber(ITEM_ID.MONEY, dataLand.UpgradeNeedMoney()
				, SETNUMBER_TYPE.MINUS, VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, s_num, "", Item_Channel.EXTREN_LAND);
		IPOManagerDb.getInstance().LogBuyService(connection, "expandland", s_num);
		
		connection.getPlayer().extendLand(1);
		
		//扩地记录日志
		LogService.logEvent(connection.getPlayer().getPlayerId(), s_num, 17,connection.getPlayer().getExtend(),0);
	}
	
	public void OnAskBuild(Connection connection, ProBuild.Msg_C2G_AskBuildingInfo msg) {
		try{
			connection.setNot_send_flag(false);
			connection.getPlayer().updateLanguage(TableManager.GetInstance().getLanguageId(msg.getLanguage()));
			//准备发送玩家所有信息
			connection.sendReceiptMessage(Msg_G2C_InfoReady.newBuilder().setCountry(connection.getPlayer().getNation())
					.setPvpFlushTime(Util.getLeftTime()).setIsPasser(connection.getAccount().getPasser()).build());
			//上线发送军团信息
			sendLegionMsg(connection);
			//上线发送奖励信息
			sendRewardMsg(connection);
			//更新玩家属性
			connection.getPlayer().sendPlayerInfo();
			//更新被动Buff
			connection.getBuffs().SendPlayerPassiveBuffs();
			//更新兵种
			connection.getCorps().SendDataArray();
			connection.getBuild().SendDataArray();
			//更新英雄列表
			ClientMessageHero.getInstance().OnHeroDatas(connection, connection.getHero().getHeros().values(), ProHero.HeroType.HERO_LIST);
			//更新飞机数据
			connection.getAir().SendDataArray();
			//更新物品数据
			connection.getItem().SendDataArray();
			//更新数量信息
			connection.getCommon().onlineSync();
			//所有任务信息
			connection.getTasks().SendDataArray();
			//所有已完成任务列表
			connection.getTasks().SendFinishedArray();
			//上线发送科技信息
			connection.getTech().SendDataArray();
			//发送新手指导信息
			connection.getPlayer().SendGuideArray();
			//发送勋章抽奖是否免费的次数
			sendMedalMsg(connection);
			//上线发送可以帮助列表
			sendHelpMsg(connection);
			//检测是否有未读邮件或战报
			CheckAnchor(connection);
			//发聊天缓存
			ClientMessageChat.getInstance().onPlayerLogin(connection);
			//发送未领取邮件物品的列表
			connection.getMails().SendMailListMsg();
			//发送阵型
			sendFormation(connection);
			//发送活动相关
			ActiveService.getInstance().sendAllActive(connection);
			connection.getReward().CheckData();
			//发送是否被攻击
			if (connection.getPlayer().isPvpBeattacked())
				connection.sendPushMessage(ProGameInfo.Msg_G2C_PlayerBeAttack.newBuilder().build());
			//发送军团捐献金币的次数
			ClientMessageCommon.getInstance().UpdateCountInfo(connection, Proto_ActionType.DONATE_MONEY,connection.getPlayer().getContributionMoneyNum(),1);
			//更新玩家属性
			connection.getPlayer().sendCpTime();
			// 发送公告
			sendNotice(connection);
			// 发送上线邮件
			sendLoginMail(connection);
			sendLuckDrawFreeMsg(connection);
			// 发送战报
			ClientMessageFight.getInstance().OnAskAnchorData(connection, null);
			
			//发送heropath刷新次数
			connection.getPlayer().sendHeroPathNum();
			//刷新战斗力
			connection.getPlayer().sendTotalFightVal();
			// 发生基金返利
			connection.getPlayer().syncFund();
			
			//发送悬赏信息
			connection.getPlayer().sendWantedData(false);
			
			//发送首冲信息
			ClientMessageCommon.getInstance().UpdateCountInfo(connection,
					Proto_ActionType.FIRST_PAY,
					connection.getPlayer().isFirstPay()?0:1,1);
			
			repairVipExp(connection);
			
			connection.getPlayer().getRechargeCache();
			//发送变化值
			sendChangeVal(connection);
			
			connection.getAccount().updateAccount(msg.getModel(), 
					msg.getLanguage(), 
					msg.getVersion(), 
					msg.getOperatingSystem(),
					msg.getSystemLanguage());
			
			//初始数据发送完成(一定要最后发送)
			connection.sendReceiptMessage(Msg_G2C_InfoFinished.newBuilder().build());
		}catch (Exception e){
			logger.error("OnAskBuild is error : ",e);
		}
	}
	
	private void repairVipExp(Connection con) {
		long now = TimeUtil.GetDateTime();
		if (con.getPlayer().getVipOverdueTime() > now) {
			int goldnum = (int)((con.getPlayer().getVipOverdueTime() - now) / Common.DAY_MILLISECOND + 1) * 100;
			con.getMails().addGoldBackMail(goldnum);
			con.getPlayer().setVipOverdueTime(now);
		}
		
		DatabaseRecharge_gold_back back = DbMgr.getInstance().getShareDB()
				.SelectOne(DatabaseRecharge_gold_back.class, "player_id=?", con.getPlayerId());
		if (back == null)
			return ;
		
		if (back.mask != 0)
			return ;
		
		back.mask = 1;
		back.save();
		
		con.getItem().setItemNumber(ITEM_ID.VIP_EXP, back.recharge_gold_sum / 10, 
				SETNUMBER_TYPE.ADDITION, VmChannel.PurchaseGoods,
				ProductChannel.PurchaseGoods, 0, "", Item_Channel.VIP_REPAIRE);
	}

	private void sendChangeVal(Connection connection) {
		List<DatabasePlayer_offline_val> offline_vals = connection.getDb()
				.Select(DatabasePlayer_offline_val.class, "player_id = ?", connection.getPlayerId());
		if (offline_vals.isEmpty()) 
			return;
		
		for (DatabasePlayer_offline_val val : offline_vals) {
			if (val.type == Proto_Off_Line.FEAT_VAL)
				sendPlayerChange(connection, val.value, val.type, connection.getPlayer().getFeat());
			else if (val.type == Proto_Off_Line.LEGION_LV)
				sendPlayerChange(connection, val.value, val.type, connection.getPlayer().getLegionLv());
		}
		
		connection.getDb().Delete(DatabasePlayer_offline_val.class, "player_id=?", connection.getPlayerId());
	}

	private void sendPlayerChange(Connection connection, int oldval, Proto_Off_Line vipLv,Integer newVal) {
		if (oldval != newVal) {
			Msg_G2C_Player_Val_Change.Builder mBuilder2 = Msg_G2C_Player_Val_Change.newBuilder();
			mBuilder2.setType(vipLv);
			mBuilder2.setOldVal(oldval);
			mBuilder2.setNewVal(newVal);
			connection.sendPushMessage(mBuilder2.build());
		}
	}

	private void sendLegionHelpList(Connection connection) throws Exception {
		Msg_G2S_RequestList.Builder  msglist = Msg_G2S_RequestList.newBuilder();
		msglist.setLegionId(connection.getPlayer().getBelegionId());
		msglist.setPlayerId(connection.getPlayerId());
		ClientMessageLegion.getInstance().OnHelpList(connection, msglist.build());
	}
	
	private void sendLuckDrawFreeMsg(Connection connection) {
		ClientMessageItem.getInstance().sendGoldFreeLuckInfo(connection);
		ClientMessageItem.getInstance().sendMoneyFreeLuckInfo(connection);
	}

	public void sendNotice(final Connection connection) {
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				connection.getPlayer().sendNoticeList();
			}
		}, 0);
	}
	
	public void sendLoginMail(final Connection connection) {
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				connection.getMails().addLoginMail();
			}
		}, 0);
	}
	
	private void sendFormation(Connection connect) {
		//设置阵型1
		ConPlayerAttr player = connect.getPlayer();
		Msg_G2C_FormationSync.Builder msg = Msg_G2C_FormationSync.newBuilder();
		
		for (CustomFormation pair : player.getformation1()) {
			ProPve.Proto_Formation.Builder formation = ProPve.Proto_Formation.newBuilder();
			formation.setId(pair.id);
			formation.setPosX(pair.x);
			formation.setPosY(pair.y);
			formation.setType(Proto_CorpsType.valueOf(pair.type));
			msg.addFormation1(formation);
		}

		//设置阵型2
		for (CustomFormation pair : player.getformation2()) {
			ProPve.Proto_Formation.Builder formation = ProPve.Proto_Formation.newBuilder();
			formation.setId(pair.id);
			formation.setPosX(pair.x);
			formation.setPosY(pair.y);
			formation.setType(Proto_CorpsType.valueOf(pair.type));
			msg.addFormation2(formation);
		}
		
		connect.sendReceiptMessage(msg.build());
	}

	private void CheckAnchor(final Connection connection){
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				asyncSendAnchor(connection);
			}
		}, 0);
	}

	private void asyncSendAnchor(Connection connection) {
		int atkCount = DbMgr.getInstance().getShareDB()
				.Count(DatabaseAttack_report.class, "islook = ? and owen_id = ?", 0, connection.getPlayerId());
		int defCount = DbMgr.getInstance().getShareDB()
				.Count(DatabaseDefend_report.class, "islook = ? and owen_id = ?", 0, connection.getPlayerId());
		
		if (atkCount > 0 || defCount > 0){
			ProFight.Msg_G2C_LookAnchor.Builder msg = ProFight.Msg_G2C_LookAnchor.newBuilder();
			msg.setAtkCount(atkCount);
			msg.setDefCount(defCount);
			connection.sendReceiptMessage(msg.build());
		}
	}
	
	private void sendHelpMsg(final Connection connection) {
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					asynSendHelp(connection);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0);
	}

	private void asynSendHelp(Connection connection) throws Exception {
		ConPlayerAttr player = connection.getPlayer();
		if (player.getBelegionId()!=0) {
			//所有的申请列表
			List<DatabaseRequestHelp> requestHelps = DbMgr.getInstance().getShareDB().Select(DatabaseRequestHelp.class, "legion_id=? and player_id!=?",player.getBelegionId(), player.getPlayerId());
			ProLegion.Msg_G2C_AskSeekHelpList.Builder message = ProLegion.Msg_G2C_AskSeekHelpList.newBuilder();
			ProLegion.Msg_G2C_AskSeekHelpList.SeekHelpInfo.Builder info = ProLegion.Msg_G2C_AskSeekHelpList.SeekHelpInfo.newBuilder();
			
			message.setType(Proto_HelpType.HELP_LIST);
			for (DatabaseRequestHelp requestHelp:requestHelps){
				//帮助次数已满
				int curCount = requestHelp.help_list.size();
				if (curCount >= requestHelp.max_help)
					continue;
				
				//自己已经帮助过
				if (requestHelp.help_list.contains(player.getPlayerId()))
					continue;
				
				info.setCount(curCount);
				info.setId(requestHelp.help_id);
				info.setPlayerId(requestHelp.player_id);
				info.setPlayerName(requestHelp.name);
				info.setMaxCount(requestHelp.max_help);
				info.setBuildId(requestHelp.build_id);
				info.setTableId(requestHelp.table_id);
				info.setType(requestHelp.state);
				
				message.addInfo(info.build());
			}
		   connection.sendReceiptMessage(message.build());
		   
		   sendLegionHelpList(connection);
		}
	}
	
	private void sendMedalMsg(Connection connection) {
		ConPlayerAttr player = connection.getPlayer();
		ProPvp.Msg_G2C_doSthNum.Builder message = ProPvp.Msg_G2C_doSthNum.newBuilder();
		ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.Builder info = ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.newBuilder();
		info.setType(ProPvp.Proto_ActionType.MEDAL);
		info.setMaxNum(1);
		info.setDoNum(player.getluck_draw_free_num());
		message.addInfo(info);
		connection.sendReceiptMessage(message.build());
	}
	
	private void sendRewardMsg(Connection connection) {
		//发送在线时长奖励
		ConPlayerAttr player = connection.getPlayer();
		if (player.getLevel()>=Common.LUCKY_LOTTERY_LEVEL) {
			long now = TimeUtil.GetDateTime();
			//上次领奖时间
			long previousTime = player.getprevious_time();
			//经过的时间
			long passTime = (now - previousTime)/1000;
			MT_Data_Luckdraw mt_Data = TableManager.GetInstance().
			TableLuckdraw().GetElement(player.getluck_draw_num() + 1);
			ProReward.Msg_G2C_AskRewardInfo.Builder rs = ProReward.Msg_G2C_AskRewardInfo.newBuilder();
			rs.setType(Proto_RewardType.TIMER_REWARD);
			if (mt_Data == null)
				rs.setTime(-1);//本日抽奖次数已满
			else 
				rs.setTime((int)((mt_Data.time()*60-passTime)<=0?0:(mt_Data.time()*60-passTime)));
			
			connection.sendReceiptMessage(rs.build());
		}
		
		sendLoginRewardInfo(connection);
		sendLevelRewardInfo(connection);
	}

	private void sendLevelRewardInfo(Connection connection) {
		//发送等级奖励
		ProReward.Msg_G2C_AskRewardInfo.Builder rsLv = ProReward.Msg_G2C_AskRewardInfo.newBuilder();
		rsLv.setType(Proto_RewardType.LEVEL_REWARD);
		rsLv.addAllList(connection.getPlayer().getUnReciveLevRewardTableIds());
		connection.sendReceiptMessage(rsLv.build());
	}

	public void sendLoginRewardInfo(Connection connection) {
		//发送登陆奖励
		ProReward.Msg_G2C_AskRewardInfo.Builder rsLogin = ProReward.Msg_G2C_AskRewardInfo.newBuilder();
		rsLogin.setType(Proto_RewardType.LOGIN_REWARD);
		rsLogin.setNum(connection.getPlayer().getContinueLoginDay());
		rsLogin.setNumReward(connection.getPlayer().getContinueLoginReward());
		connection.sendReceiptMessage(rsLogin.build());
	}
	public void sendLegionMsg(Connection connect) {
		int legionId = connect.getPlayer().getBelegionId();
		ProLegion.Msg_G2C_LegionInfo.Builder result = ProLegion.Msg_G2C_LegionInfo.newBuilder();
		if (legionId == 0) {
			result.setType(LegionInfo_enum.Login);
			result.setId(0);
		} else {
			DatabaseLegion legion = DbMgr.getInstance().getShareDB().SelectOne(DatabaseLegion.class, "legion_id = ? ", legionId);
			if (legion == null) {
				result.setType(LegionInfo_enum.Login);
				result.setId(0);
			} else {
				result.setType(LegionInfo_enum.Login);
				result.setBossId(legion.boss_id);
				result.setId(legion.legion_id);
				result.setName(legion.name);
				result.setTitle(legion.notice);
				result.setLevel(legion.level);
				result.setIsValidate(legion.is_need_validate);
				result.setImgIndex(legion.imageIndex);
//				result.setCurNum(legion.cur_num);
				//result.setCurNum(legion.meber_list.size());
				//if (legion.request_list.size() > 0)
				//	result.setAsk(true);
				//else
				//	result.setAsk(false);
				result.setMoney(legion.total_contribution);
				String playerName = ConPlayerAttr.getPlayerNameById(legion.boss_id);
				result.setBossName(playerName);

				
				List<CustomLegionMeber> officials=LegionManager.getInstance().getMembers(legion,LegionMemberType.Officical_VALUE);
				if (officials!=null) {
					for(CustomLegionMeber member:officials)
						result.addOfficerId(member.id);
				}
				List<CustomLegionMeber> elites=LegionManager.getInstance().getMembers(legion,LegionMemberType.Elite_VALUE);
				if (elites!=null) {
					for(CustomLegionMeber member:elites)
						result.addJyId(member.id);
				}
			}
		}
		connect.sendReceiptMessage(result.build());
	}
	
	public void OnCreateBuild(Connection connect, ProBuild.Msg_C2G_CreateBuild msg) throws Exception  {
		MT_Data_Building building = TableManager.GetInstance().TableBuilding().GetElement(msg.getTableId());
		if (building == null) 
			throw new GameException("找不到对应的建筑表 {}",msg.getTableId());

		ConPlayerAttr playerAttribute = connect.getPlayer();
		// 判断是否超过了正在建设个数的上限
		if (!connect.getBuild().checkBuildQueue(true))
			return ;

		MT_DataBase buildData = Util.GetDataBaseByData(building, 0);
		if (buildData == null)
			return ;

		// 玩家等级限制检测
		int needLevel = (int)buildData.GetDataByString(MT_TableEnum.NeedPlayerLv);
		if (!playerAttribute.CheckPlayerLevel(needLevel, true))
			return ;

		// 检测可建造的最大数量
		MT_Data_BuildNumLimit limit = TableManager.GetInstance().TableBuildNumLimit().GetElement(msg.getTableId());
		if (limit == null) 
			return ;
		ConBuildAttr buildAttribute = connect.getBuild();
		int haveBuild = buildAttribute.getBuildNumber(msg.getTableId());
		if (haveBuild >= limit.Arrays().size()) 
			throw new GameException(PromptType.BUILD_IS_FULL,"建筑数量已达到最大数量 最大数量", TransFormArgs.CreateBuildArgs(building.SpriteName(), 1));

		// 建筑的位置摆放是否合法
		Int2 volume = (Int2)buildData.GetDataByString(MT_TableEnum.Volume);
		if (!buildAttribute.canPlace(playerAttribute.getExtend(), -1, msg.getPointX(), msg.getPointY(), volume.field1(), volume.field2()))
			throw new GameException("该位置不能摆放建筑");

		// 检查物品需求是否满足
		ConItemAttr itemAttribute = connect.getItem();
		
		MT_Data_BuildBuyMoney buyData = TableManager.GetInstance().TableBuildBuyMoney().GetElement(msg.getTableId());
		if (buyData == null) {
			logger.error("tableId:" + msg.getTableId() + " 建筑的价格字段不存在!!");
			return;
		}
		int buildCount = connect.getBuild().getBuildNumber(msg.getTableId());
		if (buyData.Arrays().size() <= buildCount) {
			logger.error("TableId:" + msg.getTableId() + " buyData.Arrays().size() <= buildCount ");
			return;
		}
		if (TABLE.IsInvalid(buyData.Arrays().get(buildCount))) {
			logger.error("价格非法值, 无法购买  TableId:" + msg.getTableId());
			return;
		}
		Int2 value = buyData.Arrays().get(buildCount);
		int needMoney = connect.getBuffs().getValueByDecPassiveBuff(Common.PASSIVEBUFF_TYPE.BUILD_CREATE_MONEY, value.field2());
		if (!itemAttribute.checkItemEnough(value.field1(), needMoney)) {
			return;
		}

		//检测需求的前置建筑等级和
		MT_DataBase needBuild = (MT_DataBase)buildData.GetDataByString(MT_TableEnum.NeedBuild);
		List<MT_DataBase> need = new ArrayList<MT_DataBase>();
		need.add(needBuild);
		need.addAll(limit.Arrays().get(haveBuild));
		for (MT_DataBase pair : need){
			if (TABLE.IsInvalid(pair))
				continue;
			int table_id = (int)pair.GetDataByString("field1");
			int level = (int)pair.GetDataByString("field2");
			if (!buildAttribute.checkBuildLevel(table_id, level, true))
				return ;
			if (pair.getClass() == Int3.class){
				int needPlayerLevel = (int)pair.GetDataByString("field3");
				if (!playerAttribute.CheckPlayerLevel(needPlayerLevel, true))
					return ;
			}
		}
		// 判断全部完成,建造建筑
		BuildInfo buildInfo = buildAttribute.insertBuild(msg.getTableId(), msg.getPointX(), msg.getPointY());

		long s_num = IPOManagerDb.getInstance().getNextId();
		itemAttribute.setItemNumber(value.field1(), needMoney, SETNUMBER_TYPE.MINUS,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, s_num, "", Item_Channel.CREATE_BUILD);
		IPOManagerDb.getInstance().LogBuyService(connect, "build", s_num);

		//建筑创建记录日志
		LogService.logBuildingUpgrade(buildInfo.getBuildId(),buildInfo.getTableId(), buildInfo.getLevel(), buildInfo.getLevel(), s_num, connect.getPlayerId(), 0, connect.getPlayer().getLevel());
		
		int curNum = Util.GetCurWorkNum(connect);
		ClientMessageCommon.getInstance().UpdateCountInfo(connect, Proto_ActionType.QUEUE, curNum, playerAttribute.getQueuesize());
	
		//自动发起一个申请帮助
		auToSeekHelp(connect, buildInfo);
	}

	public void auToSeekHelp(Connection connect, BuildInfo buildInfo)throws Exception {
		if(connect.getPlayer().getBelegionId()!=0){
			ProLegion.Msg_C_SeekHelp.Builder msg_C_SeekHelp = ProLegion.Msg_C_SeekHelp.newBuilder();
			msg_C_SeekHelp.setBuildId(buildInfo.getBuildId());
			ClientMessageLegion.getInstance().OnSeekHelp(connect, msg_C_SeekHelp.build());
		}
	}

	public void OnBuildMove(Connection connect, ProBuild.Msg_C2G_BuildMove msg) throws Exception{
		 //玩家扩地次数
		 connect.getBuild().buildMove(msg.getBuildId(), msg.getPosX(), msg.getPosY());
	}
	
	@SuppressWarnings("unchecked")
	public void OnBuildUpgrade(Connection connect, ProBuild.Msg_C2G_BuildUpgrade msg) throws Exception {
		// 判断是否超过了正在建设个数的上限
		ConPlayerAttr dPlayer = connect.getPlayer();
		if (!connect.getBuild().checkBuildQueue(true))
			return ;

		BuildInfo build = connect.getBuild().getBuild(msg.getBuildId());
		if (build == null) 
			throw new GameException("build is not exist build_id = {}",msg.getBuildId());

		build.checkBuild();
		if (build.getState() == Proto_BuildState.UPGRADE || build.getState() == Proto_BuildState.OPERATE_UPGRADE)
			throw new GameException(PromptType.BUILD_STATE_ERR, "当前建筑已经在升级了");

		// 检测建筑是否满级
		if (build.getLevel() >= build.getDataBuilding().MaxLevel())
			throw new GameException(PromptType.BUILD_MAX_LEVEL, "升级失败 建筑已满级");

		// 等级检测
		int needPlayerLevel = (int)build.getDataBase().GetDataByString(MT_TableEnum.NeedPlayerLv);
		if (!connect.getPlayer().CheckPlayerLevel(needPlayerLevel, true))
			return ;

		// 资源检测
		ConItemAttr itemAttribute = connect.getItem();
		List<Int2> needItems = (List<Int2>)build.getDataBase().GetDataByString(MT_TableEnum.NeedResource);
		for (Int2 pair : needItems){
			if (pair.field1().equals(ITEM_ID.MONEY)){
				int needMoney = connect.getBuffs().getValueByDecPassiveBuff(Common.PASSIVEBUFF_TYPE.BUILD_UP_MONEY, pair.field2());
				if (!itemAttribute.checkItemEnough(pair.field1(), needMoney))
					return ;
				break;
			}
		}

		// 检测是否满足建筑的条件
		Int2 needBuild = (Int2)build.getDataBase().GetDataByString(MT_TableEnum.NeedBuild);
		if (TABLE.IsInvalid(needBuild) == false) {
			int buildTableId = needBuild.field1();
			int buildLevel = needBuild.field2();
			if (!connect.getBuild().checkBuildLevel(buildTableId, buildLevel, true))
				return ;
		}

		long s_num = IPOManagerDb.getInstance().getNextId();
		for (Int2 pair : needItems) {
			if (pair.field1().equals(ITEM_ID.MONEY)){
				int needMoney = connect.getBuffs().getValueByDecPassiveBuff(Common.PASSIVEBUFF_TYPE.BUILD_UP_MONEY, pair.field2());
				itemAttribute.setItemNumber(pair.field1(), needMoney, SETNUMBER_TYPE.MINUS,
						VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, s_num, "", Item_Channel.BUILD_UPGRADE);
			}
			else {
				itemAttribute.setItemNumber(pair.field1(), pair.field2(), SETNUMBER_TYPE.MINUS,
						VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, s_num, "", Item_Channel.BUILD_UPGRADE);
			}
		}
		IPOManagerDb.getInstance().LogBuyService(connect, "buildupgrade", s_num);

		build.buildUpgrade();
		LogService.logBuildingUpgrade(build.getBuildId(),build.getTableId(), 
				build.getLevel(),build.getLevel(), s_num, connect.getPlayerId(), 0, connect.getPlayer().getLevel());
		
		int curNum = Util.GetCurWorkNum(connect);
		ClientMessageCommon.getInstance().UpdateCountInfo(connect, Proto_ActionType.QUEUE, curNum, dPlayer.getQueuesize());
		
		auToSeekHelp(connect, build);
	}
	
	// 升级完成
	public void OnBuildUpgradeOver(Connection connect, ProBuild.Msg_C2G_BuildUpgradeOver msg) throws Exception{
		if (connect.isInState(Connection.Msg_C2G_BuildUpgradeOver))
			return ;
		
		final BuildInfo build = connect.getBuild().getBuild(msg.getBuildId());
       	if (build == null) 
       		throw new GameException("build is not exist build_id = {}",msg.getBuildId());
       	
       	final Connection cn = connect;
       	cn.setState(true, Connection.Msg_C2G_BuildUpgradeOver);
       	ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					buildOver(cn, build);
					cn.getBuild().CheckData();
					cn.setNeed_recalc_fight_val(true);
					cn.getPlayer().tryRecalcFightVal();
				} catch (GameException e) {
				}catch (Exception e) {
					logger.error("OnBuildUpgradeOver error, ", e);
				} finally {
					cn.setState(false, Connection.Msg_C2G_BuildUpgradeOver);
				}
			}
		}, 0);
	}


	private void buildOver(Connection connect, BuildInfo build)
			throws Exception {
		int level = build.getLevel();
       	build.checkBuild();
		
		if (level < build.getLevel()){
			TransFormArgs tfa = TransFormArgs.CreateBuildArgs(build.getDataBuilding().SpriteName(), 1);
			if (build.getLevel() > 1)
				connect.ShowPrompt(PromptType.BUILD_LEVEL_UP, PROMPT_SCENE.CITY, tfa, build.getLevel());
			else
				connect.ShowPrompt(PromptType.BUILD_CREATE, PROMPT_SCENE.CITY, tfa);
		}
		
		//升级完成记录日志(可能是建筑建造完成)
		LogService.logBuildingUpgrade(build.getBuildId(),build.getTableId(), level, 
				build.getLevel(), -1, connect.getPlayerId(), 1, connect.getPlayer().getLevel());
		
		int curNum = Util.GetCurWorkNum(connect);
		ClientMessageCommon.getInstance().UpdateCountInfo(connect, Proto_ActionType.QUEUE, curNum, connect.getPlayer().getQueuesize());
		
		if (build.isMedal())
			connect.getHero().onPlayerLevelUp(true);
	}
	
	// 建筑加速
	public void OnBuildSpeedUp(Connection connect, ProBuild.Msg_C2G_BuildSpeedUp msg) throws Exception{
		BuildInfo build = connect.getBuild().getBuild(msg.getBuildId());
		if (build == null) 
			throw new GameException("build is not exist build_id = {}", msg.getBuildId());

		int level = build.getLevel();
		if (build.buildSpeedUp()){
			connect.getTasks().AddTaskNumber(TASK_TYPE.SPEED_UP, SPEEDUP_TYPE.BUILD_UPGRADE.Number(), 1, 1);
			if (build.isMedal())
				connect.getHero().onPlayerLevelUp(true);
			int curNum = Util.GetCurWorkNum(connect);
			ClientMessageCommon.getInstance().UpdateCountInfo(connect, Proto_ActionType.QUEUE, curNum, connect.getPlayer().getQueuesize());
		}

		if (level < connect.getBuild().getBuild(msg.getBuildId()).getLevel()){
			TransFormArgs tfa = TransFormArgs.CreateBuildArgs(build.getDataBuilding().SpriteName(), 1);
			if (build.getLevel() > 1)
				connect.ShowPrompt(PromptType.BUILD_LEVEL_UP, PROMPT_SCENE.CITY, tfa, build.getLevel());
			else
				connect.ShowPrompt(PromptType.BUILD_CREATE, PROMPT_SCENE.CITY, tfa);
		}
		
		connect.setNeed_recalc_fight_val(true);
	}
	
	public void OnGatherResource(Connection connectioin,ProBuild.Msg_C2G_GatherResource message) throws GameException{
		BuildInfo build = connectioin.getBuild().getBuild(message.getBuildid());
       	if (build == null) 
       		throw new GameException("build is not exist build_id = {}", message.getBuildid());
       	
       	build.gatherResource();
	}
}
