package gameserver.network.message.game;
import gameserver.connection.attribute.ConPlayerAttr;
import gameserver.connection.attribute.info.HeroInfo;
import gameserver.fighting.FightingManager;
import gameserver.fighting.stats.INSERT_CORPS_ERROR;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.IOServer;
import gameserver.network.protos.game.ProFight;
import gameserver.network.protos.game.ProPvp;
import gameserver.network.protos.game.ProFight.Msg_G2C_FastFighting;
import gameserver.network.protos.game.ProFight.Msg_G2C_FastFighting.Proto_Corps;
import gameserver.network.protos.game.ProFight.Msg_G2C_FastFighting.Proto_Item;
import gameserver.network.protos.game.ProFight.Msg_G2C_FastFighting.Proto_Result;
import gameserver.network.protos.game.ProFight.Msg_G2C_ResultVideo;
import gameserver.network.protos.game.ProPve.Proto_CorpsType;
import gameserver.network.server.connection.Connection;
import gameserver.utils.DbMgr;
import gameserver.utils.Item_Channel;
import gameserver.utils.Util;
import gameserver.utils.UtilItem;
import gameserver.utils.VideoManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.MT_Data_Hero;
import table.MT_Data_Instance;
import table.MT_Data_Spawn;
import table.MT_Table_Spawn;
import table.base.TableManager;

import com.google.protobuf.ByteString;

import commonality.Common;
import commonality.Common.LIMIT_TYPE;
import commonality.Common.SETNUMBER_TYPE;
import commonality.Common.TASK_TYPE;
import commonality.ITEM_ID;
import commonality.ProductChannel;
import commonality.VmChannel;
import databaseshare.DatabaseAttack_report;
import databaseshare.DatabaseDefend_report;
import databaseshare.DatabaseVideo;

public class ClientMessageFight {
	private final static ClientMessageFight instance = new ClientMessageFight();
	public static ClientMessageFight getInstance() { return instance; }
	private final Logger logger = LoggerFactory.getLogger(ClientMessageFight.class);
	public void initialize()
    {
    	IOServer.getInstance().regMsgProcess(ProFight.Msg_C2G_Pve_OverFight.class, this, "OnPveOverFight");
    	IOServer.getInstance().regMsgProcess(ProFight.Msg_C2G_FastFighting.class, this, "OnFastFighting");
    	IOServer.getInstance().regMsgProcess(ProFight.Msg_C2G_RequestVideo.class, this, "OnRequestVideo");
    	IOServer.getInstance().regMsgProcess(ProFight.Msg_C2G_AskAnchorData.class, this, "OnAskAnchorData");
    	IOServer.getInstance().regMsgProcess(ProFight.Msg_C2G_Teach_OverFight.class, this, "OnTeachEnd");
    }
	
	
	public void OnTeachEnd(Connection connection,ProFight.Msg_C2G_Teach_OverFight message) {
		if (message.getWin())
			connection.getTasks().AddTaskNumber(TASK_TYPE.COMPLETE_TEACH, message.getTeachInstanceId(), 1, 0);
	}
	
	//查看邮件和战报
	private void OnLookAnchor(Connection connection){
		{
			List<DatabaseAttack_report> datas = DbMgr.getInstance().getShareDB().Select(DatabaseAttack_report.class, "owen_id = ?", connection.getPlayerId());
			for (DatabaseAttack_report pair : datas)
			{
				pair.islook = true;
				DbMgr.getInstance().getShareDB().Update(pair, "attack_report_id = ?", pair.attack_report_id);
			}
		}
		{
			List<DatabaseDefend_report> datas = DbMgr.getInstance().getShareDB().Select(DatabaseDefend_report.class, "owen_id = ?", connection.getPlayerId());
			for (DatabaseDefend_report pair : datas)
			{
				pair.islook = true;
				DbMgr.getInstance().getShareDB().Update(pair, "defend_report_id = ?", pair.defend_report_id);
			}
		}
	}
	//PVE结束战斗
	public void OnPveOverFight(Connection connection,ProFight.Msg_C2G_Pve_OverFight message){
		try{
			//更新兵种 5级以内玩家不会损失士兵
			if (connection.getPlayer().getLevel() >= Common.NO_DIE_CORPS) {
				for (int i=0;i<message.getCorpsCount();++i){
					ProFight.Proto_LossCorps corps = message.getCorps(i);
					if (corps.getType() == Proto_CorpsType.SOLIDER)
						connection.getCorps().setCorpsNumber(corps.getCorpsId(), corps.getNumber(), SETNUMBER_TYPE.MINUS);
				}
			}
			
			long costId = IPOManagerDb.getInstance().getNextId();
			int starCount = 1;
			List<UtilItem> list = new ArrayList<>();
			//赢
			if (message.getWin()){
				MT_Data_Instance instanceData = TableManager.GetInstance().TableInstance().GetElement(message.getInstanceId());
				
				//更新玩家经验属性等
				for (Int2 pair : instanceData.Exp())
					connection.getItem().setItemNumber(pair.field1(), pair.field2(), SETNUMBER_TYPE.ADDITION,
							VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,costId,"",Item_Channel.PVE_DROP);
				
				//更新奖励
				HeroInfo hero = connection.getHero().getRandomHero();
				int lv = connection.getPlayer().getLevel();
				ArrayList<Int2> dropinfos= null;
				
				boolean bFirstPve = false;
				//1:首杀
				if(connection.getPlayer().isFirstPve(message.getInstanceId()))
				{
					bFirstPve = true;
					dropinfos = instanceData.FirstWin();
				}
				else
				{
				//2：不是首杀
					dropinfos = instanceData.DropOut();
				}
				
				if (hero != null) {
					for (Int2 dropinfo : dropinfos){
						if (dropinfo.field1() == 0 || hero.getTableId() == dropinfo.field1()) {
							list.addAll(TableManager.GetInstance().getDropOut(lv, dropinfo.field2()));
						}
					}
				}
				
				connection.getItem().setItemNumberArrayByUtilItem(list, SETNUMBER_TYPE.ADDITION,
						VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, costId,"",Item_Channel.PVE_DROP);
				
				starCount = calcStar(connection, message);
				
				//更新Pve表中数据
				connection.getPlayer().UpdatePlayerInstanceId(message.getInstanceId(), starCount);
				if (bFirstPve)
					connection.getPlayer().openRewardInstance(message.getInstanceId());
				connection.getTasks().AddTaskNumber(TASK_TYPE.CLEAN_INSTANCE, message.getInstanceId(), 1, starCount);
			}
			else {
				if (connection.getPlayer().getsuperiorInfo().size() <= message.getInstanceId() /10000) {
					connection.getPlayer().UpdatePlayerAllStarCount(message.getInstanceId() / 10000 - 1,
							0, SETNUMBER_TYPE.ADDITION);
				}
			}
			//检测能否领取英雄
			if (message.getWin()){
				boolean bType = false;
				HashMap<Integer, MT_Data_Hero> datas = TableManager.GetInstance().TableHero().Datas();
				for (Entry<Integer, MT_Data_Hero> pair : datas.entrySet()){
					for (int index : pair.getValue().needInstanceId()){
						if (index == message.getInstanceId() && connection.getHero().isHaveHeroByTableId(pair.getValue().ID()) == false &&
								connection.getHero().isHaveHeroByInstanceId(index) == false){
							bType = true;
							break;
						}
						else if (index == message.getInstanceId() && 
								(connection.getHero().isHaveHeroByTableId(pair.getValue().ID()) == true || connection.getHero().isHaveHeroByInstanceId(index) == true))
							break;
					}
					if (bType == true)
						break;
				}
			}
			//更新关卡
			ClientMessagePve.getInstance().OnUpdateOneData(connection, message.getInstanceId());
			LogService.logPve(message.getInstanceId(), starCount, message.getWin() ? 1:0, costId, connection.getPlayerId(), 1, connection.getPlayer().getLevel());
			
			//显示结算
			ProFight.Msg_G2C_Pve_OverFight.Builder msg = ProFight.Msg_G2C_Pve_OverFight.newBuilder();
			msg.setWin(message.getWin());
			for (UtilItem item : list){
				ProFight.Msg_G2C_Pve_OverFight.Proto_FightReward.Builder itemInfo = ProFight.Msg_G2C_Pve_OverFight.Proto_FightReward.newBuilder();
				itemInfo.setTableId(item.GetItemId());
				itemInfo.setNum(item.GetCount());
				msg.addItems(itemInfo);
			}
			connection.sendReceiptMessage(msg.build());
			
			// PVE战斗结束
			connection.getPlayer().isPve = false;
		}catch (Exception e){
			logger.info("OnOverFight is error : ",e) ;
		}
	}
	private int calcStar(Connection connection,
			ProFight.Msg_C2G_Pve_OverFight message) {
		int starCount = 1;
		//更新星数
		int enemy = 0;
		int selfPopu = 0;
		MT_Data_Instance data = TableManager.GetInstance().TableInstance().GetElement(message.getInstanceId());
		MT_Table_Spawn spawnData = TableManager.GetInstance().getSpawns_Spawn(data.ID().toString());
		HashMap<Integer, MT_Data_Spawn> spawnDatas = spawnData.Datas();
		for (Entry<Integer, MT_Data_Spawn> pair : spawnDatas.entrySet())
			enemy += connection.getCorps().GetAllPopulation(pair.getValue().CorpsID(),pair.getValue().Level(),1);
		for (int i=0; i<message.getCorpsCount(); ++i){
			ProFight.Proto_LossCorps corps = message.getCorps(i);
			if (corps.getType() == Proto_CorpsType.SOLIDER)
				selfPopu += connection.getCorps().GetAllPopulation(corps.getCorpsId(),corps.getNumber());
			else
				selfPopu += connection.getHero().getHeroPopulation(corps.getCorpsId());
		}
		int twoStar = (int) (enemy * 0.5);
		int threeStar = (int) (enemy * 0.2);
		if (selfPopu <= twoStar)
			starCount ++;
		if (selfPopu <= threeStar)
			starCount ++;
		logger.info("我方损失人口:" + selfPopu);
		logger.info("敌方总人口:" + enemy + " 50%人口:" + twoStar + "  20%人口:" + threeStar);
		logger.info("赢得星数:" + starCount);
		return starCount;
	}
	//扫荡副本
	public void OnFastFighting(Connection connection,ProFight.Msg_C2G_FastFighting message) throws Exception{
		//至少本关卡已经通关
		Integer instanceId = message.getInstanceId();
		
		//本关卡没有通关不能少当
		if (!connection.getPlayer().isPvePass(instanceId)) 
			return ;
		
		Msg_G2C_FastFighting.Builder builder = Msg_G2C_FastFighting.newBuilder();
		int count = message.getCount();
		builder.setType(0);
		long costId = IPOManagerDb.getInstance().getNextId();
		for (int i = 0;i < count && i < 10;++i){
			MT_Data_Instance tempData = TableManager.GetInstance().TableInstance().GetElement(message.getInstanceId());
			if (connection.getItem().getItemCountByTableId(ITEM_ID.CP) >= tempData.NeedCP()){
				// 检测挑战次数
				if (connection.getPlayer().IsMaxCount(message.getInstanceId()))
					break;
				
				FightingManager fightingManager = new FightingManager(connection);
				// 初始化战斗
				fightingManager.InitializeFightingLeft();
				//初始化自己阵形 如果返回false表示士兵数量不足
				if (fightingManager.LeftCorps.InitializeFormation(message.getFormationList(), true) != INSERT_CORPS_ERROR.NONE) {
					builder.setType(2);
					logger.info("兵种数量不足 自动跳出 战斗次数 " + i);
					break;
				}
				//初始化敌方阵形
				fightingManager.RightCorps.InitializeInstance(message.getInstanceId());
				//开始战斗
				fightingManager.StartFighting();
				//开始计算战斗数据
				fightingManager.FastOverFighting();
				//设置死亡兵种
				connection.getCorps().setCorpsArrayNumber(fightingManager.LeftCorps.GetDieSoliders(), SETNUMBER_TYPE.MINUS);
				//扣除体力
				connection.getItem().setItemNumber(ITEM_ID.CP, tempData.NeedCP(), SETNUMBER_TYPE.MINUS,
						VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, costId, "",Item_Channel.FAST_FIGHT);
				//返回成功失败消息
				Proto_Result.Builder result = Proto_Result.newBuilder();
				for (Entry<Integer, Integer> pari : fightingManager.LeftCorps.GetDieSoliders().entrySet()) {
					Proto_Corps.Builder corpInfo = Proto_Corps.newBuilder();
					corpInfo.setCorpsId(pari.getKey());
					corpInfo.setCount(pari.getValue());
					result.addCorps(corpInfo);
				}
				result.setWin(fightingManager.GetWin());
				if (fightingManager.GetWin()){
					MT_Data_Instance instanceData = TableManager.GetInstance().TableInstance().GetElement(message.getInstanceId());
					
					//更新玩家经验属性等
					for (Int2 pair : instanceData.Exp())
						connection.getItem().setItemNumber(pair.field1(), pair.field2(), SETNUMBER_TYPE.ADDITION,
								VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,costId,"",Item_Channel.FAST_FIGHT);
					
					//更新奖励
					for (Int2 pair : instanceData.Exp()){
						Proto_Item.Builder item = Proto_Item.newBuilder();
						item.setItemId(pair.field1());
						item.setCount(pair.field2());
						result.addItem(item);
					}
					
					List<UtilItem> list = new ArrayList<>();
					HeroInfo hero = connection.getHero().getRandomHero();
					if (hero != null) {
						for (Int2 dropinfo : instanceData.DropOut()){
							int lv = connection.getPlayer().getLevel();
							if (dropinfo.field1() == 0 || hero.getTableId() == dropinfo.field1()) {
								list = TableManager.GetInstance().getDropOut(lv, dropinfo.field2());
								connection.getItem().setItemNumberArrayByUtilItem(list, SETNUMBER_TYPE.ADDITION,
										VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, costId,"",Item_Channel.FAST_FIGHT);
								
								for (UtilItem pair : list) {
									Proto_Item.Builder item = Proto_Item.newBuilder();
									item.setItemId(pair.GetItemId());
									item.setCount(pair.GetCount());
									result.addItem(item);
								}
							}
						}
					}
					
					//更新Pve表中数据
					connection.getPlayer().UpdatePlayerInstanceId(message.getInstanceId(), 0);
					connection.getTasks().AddTaskNumber(TASK_TYPE.CLEAN_INSTANCE, message.getInstanceId(), 1, 3);
					LogService.logPve(message.getInstanceId(), 3, 1, costId, connection.getPlayerId(), 2, connection.getPlayer().getLevel());
				} else 
					LogService.logPve(message.getInstanceId(), 0, 0, 0, connection.getPlayerId(), 2, connection.getPlayer().getLevel());
				
				builder.addResult(result);
				
				fightingManager.Shutdown();
				fightingManager = null;
			} else {
				//体力不足
				builder.setType(1);
				break;
			}
		}
		connection.sendReceiptMessage(builder.build());
		//更新关卡
		ClientMessagePve.getInstance().OnUpdateOneData(connection, message.getInstanceId());
		ClientMessageCommon.getInstance().UpdateCountInfo(connection, ProPvp.Proto_ActionType.CP, connection.getItem().getItemCountByTableId(ITEM_ID.CP), connection.getCommon().GetValue(LIMIT_TYPE.CP));
	}
	public void OnAskAnchorData(Connection connection,ProFight.Msg_C2G_AskAnchorData message){
		try {
			//发送攻击战报
			ConPlayerAttr player = connection.getPlayer();
			List<DatabaseAttack_report> databaseAttack_reports = DbMgr.getInstance().getShareDB()
					.Select(DatabaseAttack_report.class, "owen_id = ? ", player.getPlayerId());
			
			ProFight.Msg_G2C_AskAnchorData.Builder result = ProFight.Msg_G2C_AskAnchorData.newBuilder();//发送的类
			result.setType(ProFight.AnchorInfoType.ANCHOR_LIST);
			for (DatabaseAttack_report  databaseAttack_report :databaseAttack_reports) {
				ProFight.Msg_G2C_AskAnchorData.AnchorInfo.Builder anchorInfo = ProFight.Msg_G2C_AskAnchorData.AnchorInfo.newBuilder();
				
				anchorInfo.setAnchorType(ProFight.AnchorType.ACT);
				anchorInfo.setName(databaseAttack_report.target_name);
				anchorInfo.setLevel(databaseAttack_report.target_level);
				anchorInfo.setWin(databaseAttack_report.win);
				//anchorInfo.setRankLevel(databaseAttack_report.target_group);联盟
				anchorInfo.setRankLevel(Util.getRank(databaseAttack_report.target_now_feat));//对手战后军衔
				anchorInfo.setRankNum(databaseAttack_report.target_now_feat);//对手战后功勋
				anchorInfo.setMoney(databaseAttack_report.asset);
				anchorInfo.setRank(databaseAttack_report.owen_feat);
				anchorInfo.setTime(databaseAttack_report.time);
				anchorInfo.setVideoId(databaseAttack_report.video_id);
				anchorInfo.setIsLook(databaseAttack_report.islook);
				anchorInfo.setId(databaseAttack_report.attack_report_id);
				
				result.addInfo(anchorInfo.build());
			}	
			connection.sendReceiptMessage(result.build());
			
			//发送防御战报
			List<DatabaseDefend_report> databaseDefend_reports = DbMgr.getInstance().getShareDB()
					.Select(DatabaseDefend_report.class, "owen_id = ? ", player.getPlayerId());
			
			ProFight.Msg_G2C_AskAnchorData.Builder resultDefend = ProFight.Msg_G2C_AskAnchorData.newBuilder();//发送的类
			resultDefend.setType(ProFight.AnchorInfoType.ANCHOR_LIST);
			for (DatabaseDefend_report  satabaseDefend_report :databaseDefend_reports) {
				ProFight.Msg_G2C_AskAnchorData.AnchorInfo.Builder anchorInfo = ProFight.Msg_G2C_AskAnchorData.AnchorInfo.newBuilder();
				
				anchorInfo.setAnchorType(ProFight.AnchorType.DEF);
				anchorInfo.setName(satabaseDefend_report.target_name);
				anchorInfo.setLevel(satabaseDefend_report.target_level);
				anchorInfo.setWin(satabaseDefend_report.win);
				//anchorInfo.setRankLevel(databaseAttack_report.target_group);联盟
				anchorInfo.setRankLevel(Util.getRank(satabaseDefend_report.target_now_feat));//对手战后军衔
				anchorInfo.setRankNum(satabaseDefend_report.target_now_feat);//对手战后功勋
				anchorInfo.setMoney(satabaseDefend_report.asset);
				anchorInfo.setRank(satabaseDefend_report.owen_feat);
				anchorInfo.setTime(satabaseDefend_report.time);	
				anchorInfo.setVideoId(satabaseDefend_report.video_id);
				anchorInfo.setIsLook(satabaseDefend_report.islook);
				anchorInfo.setId(satabaseDefend_report.defend_report_id);
				anchorInfo.setIsfuckback(satabaseDefend_report.isfuckback);
				resultDefend.addInfo(anchorInfo.build());
			}	
			logger.info("info.Count:" + result.getInfoCount());
			connection.sendReceiptMessage(resultDefend.build());
			
			//所有设置成已读
			OnLookAnchor(connection);
		} catch (Exception e) {
			logger.info("OnAskAnchorData is error : ",e);
		}
		
	}
	public void OnRequestVideo(Connection connection,ProFight.Msg_C2G_RequestVideo message)
	{
		DatabaseVideo video = VideoManager.GetInstance().GetVideo(message.getVideoId());
		if (video == null) return;
		Msg_G2C_ResultVideo.Builder builder = Msg_G2C_ResultVideo.newBuilder();
		builder.setVideoId(message.getVideoId());
		builder.setArg(message.getArg());
		builder.setVideoData(ByteString.copyFrom(video.video_data));
		builder.setWinFeat(video.win_feat);
		builder.setWinMoney(video.win_asset);
		builder.setLoseFeat(video.lose_feat);
		builder.setLoseMoney(video.lose_asset);
		connection.sendReceiptMessage(builder.build());
	}
}
