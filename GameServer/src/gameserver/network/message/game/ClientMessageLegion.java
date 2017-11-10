package gameserver.network.message.game;

import gameserver.cache.CorpsCache;
import gameserver.cache.ItemCache;
import gameserver.cache.PlayerCache;
import gameserver.connection.attribute.ConPlayerAttr;
import gameserver.connection.attribute.LegionManager;
import gameserver.connection.attribute.info.BuildInfo;
import gameserver.connection.attribute.info.CorpsInfo;
import gameserver.http.HttpProcessManager;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.IOServer;
import gameserver.network.protos.common.ProLegion;
import gameserver.network.protos.common.ProLegion.LegionInfo_enum;
import gameserver.network.protos.common.ProLegion.Msg_C2G_Legion_Buy;
import gameserver.network.protos.common.ProLegion.Msg_C2G_PeopleManage;
import gameserver.network.protos.common.ProLegion.Msg_G2C_LegionInfo;
import gameserver.network.protos.common.ProLegion.Msg_G2C_LegionInfo.Builder;
import gameserver.network.protos.common.ProLegion.Msg_G2C_Legion_Members;
import gameserver.network.protos.common.ProLegion.Msg_G2S_DelLegionRequest;
import gameserver.network.protos.common.ProLegion.Msg_S2G_LegionInfo_Share;
import gameserver.network.protos.common.ProLegion.PeopleManage_enum;
import gameserver.network.protos.common.ProLegion.Proto_LegionShopType;
import gameserver.network.protos.common.ProLegion.Proto_SeekHelp_BuildType;
import gameserver.network.protos.common.ProLegion.Request_enum;
import gameserver.network.protos.common.ProLegionHelp;
import gameserver.network.protos.common.ProLegionHelp.Msg_C2G_CoprsAssist;
import gameserver.network.protos.common.ProLegionHelp.Msg_CorpPair;
import gameserver.network.protos.common.ProLegionHelp.Msg_G2C_AssistCount;
import gameserver.network.protos.common.ProLegionHelp.Msg_G2S_LegionAssist;
import gameserver.network.protos.common.ProLegionHelp.Msg_G2S_Request;
import gameserver.network.protos.common.ProLegionHelp.Proto_SeekHelp_Type;
import gameserver.network.protos.common.ProRanking;
import gameserver.network.protos.common.ProRanking.Proto_RankingForm;
import gameserver.network.protos.common.ProRanking.Proto_RankingType;
import gameserver.network.protos.game.CommonProto.Proto_BuildState;
import gameserver.network.protos.game.ProGameInfo.CLOCK_TYPE;
import gameserver.network.protos.game.ProGameInfo.Msg_G2C_ClockInfo;
import gameserver.network.protos.game.ProHint.Msg_G2C_Prompt;
import gameserver.network.protos.game.ProHint.Msg_G2C_Prompt.PROMPT_SCENE;
import gameserver.network.protos.game.ProPvp.Proto_ActionType;
import gameserver.network.server.connection.Connection;
import gameserver.network.server.connection.ConnectionManager;
import gameserver.ranking.ClientMessageRanking;
import gameserver.share.ShareServerManager;
import gameserver.thread.ThreadPoolManager;
import gameserver.utils.DbMgr;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;
import gameserver.utils.NameFilter;
import gameserver.utils.TransFormArgs;
import gameserver.utils.Util;
import gameserver.utils.WordFilter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_Corps;
import table.MT_Data_LegionPlayerExp;
import table.MT_Data_LegionStore;
import table.MT_Data_commodity;
import table.MT_Data_legionConfig;
import table.base.TableManager;

import com.commons.util.TimeUtil;
import commonality.Common;
import commonality.Common.LIMIT_TYPE;
import commonality.Common.SETNUMBER_TYPE;
import commonality.Common.TASK_TYPE;
import commonality.ConveyCondition;
import commonality.ITEM_ID;
import commonality.ProductChannel;
import commonality.PromptType;
import commonality.SHARE_SERVER_TYPE;
import commonality.VmChannel;

import database.CustomLegionCommodity;
import database.DatabasePlayer;
import databaseshare.DatabaseLegion;
import databaseshare.DatabasePvp_match;
import databaseshare.DatabaseRequestMoneyOrCropHelp;

public class ClientMessageLegion {
	private final static ClientMessageLegion instance = new ClientMessageLegion();
	public static ClientMessageLegion getInstance() { return instance; }
	private final Logger logger = LoggerFactory.getLogger(ClientMessageLegion.class);
	public void initialize() {
		IOServer.getInstance().regMsgProcess(ProLegion.Msg_C2G_Create_Legion.class, this, "OnCreateLegion");//创建军团
		IOServer.getInstance().regMsgProcess(ProLegion.Msg_C2G_join_Legion.class, this, 	"OnJoinLegion");//加入军团
		IOServer.getInstance().regMsgProcess(ProLegion.Msg_C2G_PeopleManage.class, this, "OnPeopleDo");//成员操作
		IOServer.getInstance().regMsgProcess(ProLegion.Msg_C2G_Legion_Members.class, this, "OnMembers");//成员列表
		IOServer.getInstance().regMsgProcess(ProLegion.Msg_C2G_Signout_Legion.class, this, "OnSignoutLegion");//退出军团
		IOServer.getInstance().regMsgProcess(ProLegion.Msg_C2G_Contribution_Item.class, this, "OnContributionDo");//捐献
		IOServer.getInstance().regMsgProcess(ProLegion.Msg_C2G_Modify_Notice.class, this, "OnModifyNotice");//修改公告
		IOServer.getInstance().regMsgProcess(ProLegion.Msg_C2G_Look_Request_List.class, this, "OnLookRequestList");//查看申请列表
		IOServer.getInstance().regMsgProcess(ProLegion.Msg_C2G_query_Legions.class, this, "OnQueryLegions");//查询军团列表
		IOServer.getInstance().regMsgProcess(ProLegion.Msg_C2G_SetVerifyState.class, this, "OnSetVerifyState");//修改状态
		IOServer.getInstance().regMsgProcess(ProLegion.Msg_C2G_Request_Pass.class, this, "OnRequestPassOrReject");//通过申请/拒绝
		IOServer.getInstance().regMsgProcess(ProLegion.Msg_C2G_Legion_List.class, this, "OnLegionStoreList");
		IOServer.getInstance().regMsgProcess(ProLegion.Msg_C2G_Legion_Buy.class, this, "OnBuyCommodityInLegionStore");
		IOServer.getInstance().regMsgProcess(ProLegion.Msg_C_SeekHelp.class, this, "OnSeekHelp");//请求成员帮助
		IOServer.getInstance().regMsgProcess(ProLegion.Msg_C_HelpPlayer.class, this, "OnHelpPlayer");//帮助玩家
		
		//士兵金币求助相关
		IOServer.getInstance().regMsgProcess(ProLegionHelp.Msg_G2S_RequestList.class, this, "OnHelpList");//请求帮助列表
		IOServer.getInstance().regMsgProcess(ProLegionHelp.Msg_C2G_MoneyAssist.class, this, "OnSeekHelpMoney");//请求帮助列表
		IOServer.getInstance().regMsgProcess(ProLegionHelp.Msg_C2G_CoprsAssist.class, this, "OnSeekHelpCrop");//请求帮助列表
		IOServer.getInstance().regMsgProcess(ProLegionHelp.Msg_C2G_LegionAssist.class, this, "OnHelpMoneyOrCrop");//帮助
	}

	public void init() {
		ShareServerManager.getInstance().regMsgProcess(ProLegion.Msg_G2C_LegionInfo.class,this, "OnCreateLegionShare");
		ShareServerManager.getInstance().regMsgProcess(ProLegion.Msg_S2G_LegionInfo_Share.class,this, "OnAllLegionShare");
		ShareServerManager.getInstance().regMsgProcess(ProLegion.Msg_G2C_query_Legions.class,this, "OnJoinLegionShareNeedVald");
		ShareServerManager.getInstance().regMsgProcess(ProLegion.Msg_G2C_Legion_Members.class,this, "OnMembersShare");
		ShareServerManager.getInstance().regMsgProcess(ProLegion.Msg_G2C_Look_Request_List.class,this, "OnLookRequestList");
		ShareServerManager.getInstance().regMsgProcess(ProLegion.Msg_G2C_isSeekHelp.class, this, "OnIsSeekHelp");
		ShareServerManager.getInstance().regMsgProcess(ProLegion.Msg_S2G_help_re.class, this, "OnHelpEnd");

		HttpProcessManager.getInstance().regMsgProcess(ProLegion.Msg_S2G_Update_Player.class,this, "OnUpdatePlayer");
		HttpProcessManager.getInstance().regMsgProcess(ProLegion.Msg_G2C_LegionInfo.class,this, "OnSendLegionMsgToBePassPlayer");
		HttpProcessManager.getInstance().regMsgProcess(ProLegion.Msg_G2S_Update_Buf.class,this, "OnUpdatePlayerBuf");

		//资源兵种求助相关
		ShareServerManager.getInstance().regMsgProcess(ProLegionHelp.Msg_G2C_LegionAssistInfo.class,this, "OnSorseOrCropList");
		HttpProcessManager.getInstance().regMsgProcess(ProLegionHelp.Msg_S2G_LegionAssist.class,this, "OnHelpOne");
		ShareServerManager.getInstance().regMsgProcess(ProLegionHelp.Msg_S2G_DecMoneyForHelp.class,this, "OnDecMoneyForHelp");
		ShareServerManager.getInstance().regMsgProcess(ProLegionHelp.Msg_S2G_DecCorpForHelp.class,this, "OnCorpForHelp");
		ShareServerManager.getInstance().regMsgProcess(ProLegionHelp.Msg_S2G_Request_re.class,this,"OnRequestRe");
	}
	 
	 public void OnRequestRe(Connection connect,SHARE_SERVER_TYPE type,int id,ProLegionHelp.Msg_S2G_Request_re msg){
		 sendHelpCool(connect);
	 }
	 
	 public void sendHelpCool(Connection con) {
		 List<DatabaseRequestMoneyOrCropHelp> lsitCropHelps = DbMgr.getInstance().getShareDB()
				 .Select(DatabaseRequestMoneyOrCropHelp.class, "player_id=?", con.getPlayerId());
		 
		 if (lsitCropHelps.isEmpty())
			 return ;
		 
		 long nowTime = TimeUtil.GetDateTime();
		 Msg_G2C_ClockInfo.Builder ci = Msg_G2C_ClockInfo.newBuilder();
		 for (DatabaseRequestMoneyOrCropHelp dbr : lsitCropHelps) {
			 if (dbr.type == Proto_SeekHelp_Type.Source_help) {
				 Msg_G2C_ClockInfo.Pro_Info.Builder pibBuilder = Msg_G2C_ClockInfo.Pro_Info.newBuilder();
				 pibBuilder.setType(CLOCK_TYPE.ASSIST_MONEY);
				 long timediff = nowTime - dbr.time;
				 if (timediff > 30 * 60 * 1000) {
					 timediff = 0;
				 } else {
					 timediff = 30 * 60 * 1000 - timediff;
				 }
				 pibBuilder.setTimes((int)(timediff/1000));
				 ci.addDatas(pibBuilder.build());
			 }
			 else if (dbr.type == Proto_SeekHelp_Type.Crop_help) {
				 Msg_G2C_ClockInfo.Pro_Info.Builder pibBuilder = Msg_G2C_ClockInfo.Pro_Info.newBuilder();
				 pibBuilder.setType(CLOCK_TYPE.ASSIST_CORPS);
				 long timediff = nowTime - dbr.time;
				 if (timediff > 30 * 60 * 1000) {
					 timediff = 0;
				 } else {
					 timediff = 30 * 60 * 1000 - timediff;
				 }
				 pibBuilder.setTimes((int)(timediff/1000));
				 ci.addDatas(pibBuilder.build());
			 }
		 }
		 con.sendPushMessage(ci.build());
	 }
	 
	 public void OnDecMoneyForHelp(Connection connect,SHARE_SERVER_TYPE type,int id,ProLegionHelp.Msg_S2G_DecMoneyForHelp msg){
		 long s_num = IPOManagerDb.getInstance().getNextId();
		 connect.getItem().setItemNumber(ITEM_ID.MONEY, msg.getMoneyDecCount(),
				 SETNUMBER_TYPE.MINUS,VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"",Item_Channel.HELP_MONEY);
		 connect.getItem().CheckData();
		 connect.getTasks().AddTaskNumber(TASK_TYPE.HELP_LEGION_MEMBER, 
				 0, msg.getMoneyDecCount() / Common.LEGION_HELP_MONEY_NUM, 0);
		 connect.getTasks().CheckData();
	 }
	 
	 public void OnCorpForHelp(Connection connect,SHARE_SERVER_TYPE type,int id,ProLegionHelp.Msg_S2G_DecCorpForHelp msg){
		 int num = 0;
		 for (Msg_CorpPair cpCorpPair : msg.getCorpsList()) {
			 CorpsInfo info = connect.getCorps().getCorpsByTableId(cpCorpPair.getCorpTableId());
			 if (info.getNumber() <= 0) 
				continue ;
			 
			 //扣除士兵
			 num += cpCorpPair.getNum();
			 connect.getCorps().setCorpsNumber(cpCorpPair.getCorpTableId(), cpCorpPair.getNum(), SETNUMBER_TYPE.MINUS);
			 connect.getCorps().CheckData();
		 }
		 
		 connect.getTasks().AddTaskNumber(TASK_TYPE.HELP_LEGION_MEMBER, 
				 0, num, 0);
		 connect.getTasks().CheckData();
	 }
	 
	 public void OnHelpOne(ProLegionHelp.Msg_S2G_LegionAssist msg){
		 Connection con = ConnectionManager.GetInstance().GetConnection(msg.getBePlayerId());
		 //1：金币被帮助	
		if (msg.getType() == Proto_SeekHelp_Type.Source_help) {
			if (con == null) {
				// 玩家不在线就直接修改数据库
				Integer today_totle_count = (Integer) DbMgr.getInstance().getDbByPlayerId(msg.getBePlayerId())
					.selectOneField(DatabasePlayer.class, "player_id=?", "today_help_money_count", Integer.class, msg.getBePlayerId());
				
				if (today_totle_count < getMaxMoneyHelpNum(msg.getLegionLevel())) {
					ItemCache.deleteAllItem(msg.getBePlayerId());
					PlayerCache.deletePlayer(msg.getBePlayerId());
					DbMgr.getInstance().getDbByPlayerId(msg.getBePlayerId())
						.Execute("update item set number=number+? where table_id = ? and player_id = ?",Common.LEGION_HELP_MONEY_NUM,ITEM_ID.MONEY, msg.getBePlayerId());
					DbMgr.getInstance().getDbByPlayerId(msg.getBePlayerId())
						.Execute("update player set today_help_money_count=today_help_money_count+? where player_id = ?",1, msg.getBePlayerId());
				}
			} else {
				// 玩家在线
				if (con.getPlayer().getTodayMoneyCount() < getMaxMoneyHelpNum(msg.getLegionLevel())) {
					long s_num = IPOManagerDb.getInstance().getNextId();
					con.getItem().setItemNumber(ITEM_ID.MONEY, Common.LEGION_HELP_MONEY_NUM,
							SETNUMBER_TYPE.ADDITION, VmChannel.InGameDrop,ProductChannel.InGameDrop, s_num, "", false,Item_Channel.HELP_MONEY);
					con.getItem().CheckData();
					con.getPlayer().addTodayMoneyCount(1);
					sendHelpCount(con);
					
					con.ShowPrompt(PromptType.LEGION_HELP_MONEY_HIT, msg.getPlayerName(),
							Common.LEGION_HELP_MONEY_NUM);
				}
			}
		}
		// 士兵被帮助
		else if (msg.getType() == Proto_SeekHelp_Type.Crop_help) {
			if (con == null) {
				// 玩家不在线就直接修改数据库
				DatabasePvp_match pvpMatch = DbMgr.getInstance().getShareDB().SelectOne(DatabasePvp_match.class, "player_id=?", msg.getBePlayerId());
				if (pvpMatch != null) {
					MT_Data_Corps corpconfigCorps = TableManager.GetInstance().TableCorps().GetElement(msg.getCorpId());
					if (corpconfigCorps != null) {
						if (corpconfigCorps.Population() + pvpMatch.cur_population <= pvpMatch.totle_population) {
							Integer today_totle_count = (Integer) DbMgr.getInstance().getDbByPlayerId(msg.getBePlayerId())
									.selectOneField(DatabasePlayer.class, "player_id=?", "today_help_corp_count", Integer.class, msg.getBePlayerId());
							
							if (today_totle_count + corpconfigCorps.Population() <= getMaxCorpHelpNum(msg.getLegionLevel())) {
								CorpsCache.deleteAllCorps(msg.getBePlayerId());
								PlayerCache.deletePlayer(msg.getBePlayerId());
								PlayerCache.deletePvpMatch(msg.getBePlayerId());
								
								DbMgr.getInstance().getDbByPlayerId(msg.getBePlayerId())
									.Execute("update corps set number=number+? where table_id = ? and player_id = ?",1,msg.getCorpId(), msg.getBePlayerId());
								DbMgr.getInstance().getDbByPlayerId(msg.getBePlayerId())
									.Execute("update player set today_help_corp_count=today_help_corp_count+? where player_id = ?",corpconfigCorps.Population(), msg.getBePlayerId());
								pvpMatch.cur_population += corpconfigCorps.Population();
								pvpMatch.save();
							}
						}
					}
				}
			} else {
				// 玩家在线
				MT_Data_Corps corpconfigCorps = TableManager.GetInstance().TableCorps().GetElement(msg.getCorpId());
				if (corpconfigCorps != null) {
					if (con.getPlayer().totlePopulation() + corpconfigCorps.Population() <= con.getCommon().GetValue(LIMIT_TYPE.PEOPLE)) {
						if (con.getPlayer().getTodayCorpCount() + corpconfigCorps.Population() <= getMaxCorpHelpNum(con.getPlayer().getLegionLv())) {
							con.getCorps().setCorpsNumber(msg.getCorpId(), 1, SETNUMBER_TYPE.ADDITION);
							con.getCorps().CheckData();
							con.getPlayer().addTodayCorpCount(corpconfigCorps.Population());
							sendHelpCount(con);
							
							con.ShowPrompt(PromptType.LEGION_HELP_CORP_HIT, msg.getPlayerName(),
									TransFormArgs.CreateCorpArgs(msg.getCorpId()));
						}
					}
				}
			}
		}
	}

	private void sendHelpCount(Connection connection) {
		Msg_G2C_AssistCount.Builder acmsg = Msg_G2C_AssistCount.newBuilder().setCorpsAssistNum(connection.getPlayer().getTodayCorpCount())
				.setMoneyAssistNum(connection.getPlayer().getTodayMoneyCount());
		connection.sendPushMessage(acmsg.build());
	}
	 
	//列表返回S-G
	 public void OnSorseOrCropList(Connection connect,SHARE_SERVER_TYPE type,int id,ProLegionHelp.Msg_G2C_LegionAssistInfo msg){
		 connect.sendPushMessage(msg);
		 sendHelpCool(connect);
		 sendHelpCount(connect);
	 }
	 //G-S 请求可以帮助的列表
	 public void OnHelpList(Connection connect, ProLegionHelp.Msg_G2S_RequestList msg) throws Exception{
		 ShareServerManager.getInstance().sendMsgLegion(connect, msg.getLegionId(), msg);
	 }
	 
	 //帮助（金币/士兵）
	 public void OnHelpMoneyOrCrop(Connection connect, ProLegionHelp.Msg_C2G_LegionAssist msg) throws Exception{
		 int legionId = connect.getPlayer().getBelegionId();
		 
		 Msg_G2S_LegionAssist.Builder requBuilder = createMsg(connect, msg,legionId);
		 
		//金币帮助
		 if (Proto_SeekHelp_Type.Source_help == msg.getType()) {
			 if (!connect.getItem().checkItemEnough(ITEM_ID.MONEY, Common.LEGION_HELP_MONEY_NUM)) 
				 return;
			
			 requBuilder.setPlayerMoney(connect.getItem().getItemCountByTableId(ITEM_ID.MONEY));
		}
		//士兵帮助
		else if (Proto_SeekHelp_Type.Crop_help == msg.getType()) {
			if (msg.getId() != 0) {
				CorpsInfo info = connect.getCorps().getCorpsByTableId(msg.getCorpId());
				if (info.getNumber() <= 0) 
					return ;
				
				DatabasePvp_match pvp = DbMgr.getInstance().getShareDB()
						.selectFieldFrist(DatabasePvp_match.class, "player_id=?", "cur_population,totle_population,online", msg.getPlayerId());
				if (!pvp.online) {
					if (info.getData().Population() + pvp.cur_population > pvp.totle_population)
						throw new GameException(PromptType.POPULATION_FULL);
				}
			}
			
			for (CorpsInfo cInfo : connect.getCorps().getCorpsArray()) {
				Msg_CorpPair.Builder cBuilder = Msg_CorpPair.newBuilder();
				cBuilder.setCorpTableId(cInfo.getTableId());
				cBuilder.setNum(cInfo.getNumber());
				requBuilder.addCorps(cBuilder.build());
			}
		}
		 
		 ShareServerManager.getInstance().sendMsgLegion(connect,legionId, requBuilder.build());
	 }

	private Msg_G2S_LegionAssist.Builder createMsg(Connection connect,ProLegionHelp.Msg_C2G_LegionAssist msg, int legionId) {
		Msg_G2S_LegionAssist.Builder requBuilder = Msg_G2S_LegionAssist.newBuilder();
		 requBuilder.setType(msg.getType());
		 requBuilder.setId(msg.getId());
		 requBuilder.setCorpId(msg.getCorpId());
		 requBuilder.setLegionId(legionId);
		 requBuilder.setPlayerId(connect.getPlayerId());
		 requBuilder.setPlayerName(connect.getPlayerName());
		return requBuilder;
	}

	//请求帮助（金币）
	 public void OnSeekHelpMoney(Connection connect, ProLegionHelp.Msg_C2G_MoneyAssist msg) throws Exception{
		 if (connect.getPlayer().getBelegionId() == 0) {
			 logger.error("玩家还没有帮会呢");
			 return ;
		 }
		 
		 if (connect.getPlayer().getTodayMoneyCount() >= getMaxMoneyHelpNum(connect.getPlayer().getLegionLv())) {
			 logger.error("玩家今天的金币求助次数用完了，总次数是{}", getMaxMoneyHelpNum(connect.getPlayer().getLegionLv()));
			 return ;
		 }
		 
		 Msg_G2S_Request.Builder requBuilder = createMoneyMsg(connect, msg);
		 ShareServerManager.getInstance().sendMsgLegion(connect, msg.getLegionId(), requBuilder.build());
	 }
	
	 //请求帮助（士兵）
	 public void OnSeekHelpCrop(Connection connect, ProLegionHelp.Msg_C2G_CoprsAssist msg) throws Exception{
		 if (connect.getPlayer().getBelegionId() == 0)
			 return ;
		 
		 MT_Data_Corps corpconfigCorps = TableManager.GetInstance().TableCorps().GetElement(msg.getCorpsId());
		if (corpconfigCorps == null) 
			return ;
		 
		 if (connect.getPlayer().getTodayCorpCount() + corpconfigCorps.Population() >= getMaxCorpHelpNum(connect.getPlayer().getLegionLv()))
			 return ;
		 
		 if (connect.getPlayer().totlePopulation() + corpconfigCorps.Population() > connect.getCommon().GetValue(LIMIT_TYPE.PEOPLE))
			 throw new GameException(PromptType.LEGION_HELP_CORP_POPU_FULL);
		 
		 Msg_G2S_Request.Builder requBuilder = createCropMsg(connect, msg);
		 ShareServerManager.getInstance().sendMsgLegion(connect, msg.getLegionId(), requBuilder.build());
	 } 
	 
	 private int getMaxCorpHelpNum(int legion_lvl) {
		 return 16 + legion_lvl;
	 }
	 
	 private int getMaxMoneyHelpNum(int legion_lvl) {
		 return 40 + legion_lvl;
	 }
	 
	 //创建士兵消息
	 private ProLegionHelp.Msg_G2S_Request.Builder createCropMsg(Connection connect, Msg_C2G_CoprsAssist msg) {
		 Msg_G2S_Request.Builder requBuilder = Msg_G2S_Request.newBuilder();
		 requBuilder.setLegionId(msg.getLegionId());
		 requBuilder.setPlayerId(connect.getPlayerId());
		 requBuilder.setPlayerName(connect.getPlayerName());
		 requBuilder.setText("");
		 requBuilder.setCorpsId(msg.getCorpsId());
		 return requBuilder;
	}

	 //创建金币消息
	private Msg_G2S_Request.Builder createMoneyMsg(Connection connect,ProLegionHelp.Msg_C2G_MoneyAssist msg) {
		 Msg_G2S_Request.Builder requBuilder = Msg_G2S_Request.newBuilder();
		 requBuilder.setLegionId(msg.getLegionId());
		 requBuilder.setPlayerId(connect.getPlayerId());
		 requBuilder.setPlayerName(connect.getPlayerName());
		 requBuilder.setText(msg.getText());
		 requBuilder.setCorpsId(0);
		 return requBuilder;
	} 
	 
	 
	public void OnUpdatePlayer(ProLegion.Msg_S2G_Update_Player msg) throws Exception {
		Connection connection = ConnectionManager.GetInstance().GetConnection(msg.getPlayerId());
		
		//玩家不在线就直接修改数据库
		if (connection==null) {
			DbMgr.getInstance().getDbByPlayerId(msg.getPlayerId())
				.Execute("update player set belong_legion=? where player_id=?", msg.getLegionId(), msg.getPlayerId());
			DbMgr.getInstance().getShareDB()
				.Execute("update pvp_match set belong_legion=? where player_id=?", msg.getLegionId(), msg.getPlayerId());
		}else {
			//玩家在线
			connection.getPlayer().updatePlayerLegion(msg.getLegionId());
			connection.getPlayer().setLegionInfo(msg.getLegionId(),msg.getLegionLv());
			connection.setNeed_recalc_fight_val(true);
			//被踢出
			if (msg.getType().endsWith("KIKOUT")){
				sendKitOutLegionMsg(connection);
				ClientMessagePassiveBuff.getInstance().UpdateLegionBuf(connection, msg.getLegionLv(), 0);
			} 
			//通过申请 
			if (msg.getType().endsWith("PASS"))
				ClientMessagePassiveBuff.getInstance().UpdateLegionBuf(connection, 0, msg.getLegionLv());
		}
	}
	
	//玩家被通过发送军团消息
	public void OnSendLegionMsgToBePassPlayer(ProLegion.Msg_G2C_LegionInfo msg) throws Exception {
		Connection connection = ConnectionManager.GetInstance().GetConnection(msg.getBePassPlayerId());
		if (connection!=null)
			connection.sendReceiptMessage(msg);
	}
	
	//军团升级
	public void OnUpdatePlayerBuf(ProLegion.Msg_G2S_Update_Buf msg) throws Exception {
		for (Connection connection : ConnectionManager.GetInstance().getConnections().values()) {
			if (connection.getPlayer().getBelegionId() == msg.getLegionId()){
				ClientMessagePassiveBuff.getInstance().UpdateLegionBuf(connection, msg.getOldLv(), msg.getNewLv());
				connection.getPlayer().updateLegionInfo(msg.getNewLv());
				connection.setNeed_recalc_fight_val(true);
			} 
		}
	}

	//被踢出发的消息 
	private void sendKitOutLegionMsg(Connection connection) {
		ProLegion.Msg_G2C_LegionInfo.Builder result = ProLegion.Msg_G2C_LegionInfo.newBuilder();
		result.setType(LegionInfo_enum.Delete);
		result.setId(0);
		connection.sendReceiptMessage(result.build());
	}

	//是否可以请求帮助
	public void OnIsSeekHelp(Connection connect,SHARE_SERVER_TYPE type,int id,ProLegion.Msg_G2C_isSeekHelp msg)  {
		connect.sendReceiptMessage(msg);//游戏服务器--客服端
	}
	
	public void OnHelpEnd(Connection connect,SHARE_SERVER_TYPE type,int id,ProLegion.Msg_S2G_help_re msg)  {
		if (msg.getHelpCount() > 0) {
			connect.getTasks().AddTaskNumber(TASK_TYPE.HELP_LEGION_MEMBER, 0, msg.getHelpCount(), 0);
			connect.getTasks().CheckData();
		}
	}
	//帮助成员 客户端-游戏服务器 
	public void OnHelpPlayer(Connection connect, ProLegion.Msg_C_HelpPlayer msg) throws Exception{
		ConPlayerAttr player = connect.getPlayer();

		ProLegion.Msg_G2S_HelpPlayer.Builder message = ProLegion.Msg_G2S_HelpPlayer.newBuilder();
		message.setId(msg.getId());
		message.setPlayerId(player.getPlayerId());
		
		final Connection cn = connect;
		final ProLegion.Msg_G2S_HelpPlayer helpmsg = message.build();
		final int legionid = player.getBelegionId();
		
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					ShareServerManager.getInstance().sendMsgLegion(cn, legionid, helpmsg);
				} catch (GameException e) {
				}catch (Exception e) {
					logger.error("OnHelpPlayer, error:", e);
				}
			}
		}, 0);
	}
	 
	//军团成员请求帮助 客户端-游戏服务器 
	public void OnSeekHelp(Connection connect, ProLegion.Msg_C_SeekHelp msg) throws Exception  {
		ConPlayerAttr player = connect.getPlayer();
		if (player.getBelegionId() == 0) 
			throw new GameException("没有加入军团，不能请求帮助");
		BuildInfo buildInfo = connect.getBuild().getBuild(msg.getBuildId());
		if (buildInfo == null)
			throw new GameException("玩家没有这样的建筑");
		Proto_SeekHelp_BuildType buildstate = buildInfo.getWorkState();
		if (buildstate == Proto_SeekHelp_BuildType.Build_none)
			return ;
		
		Proto_BuildState state = buildInfo.getState();
		ProLegion.Msg_G2S_SeekHelp.Builder message = ProLegion.Msg_G2S_SeekHelp.newBuilder();
		message.setBuildId(msg.getBuildId());
		message.setLegionId(player.getBelegionId());
		message.setBuildType(buildInfo.getWorkState());
		if (state == Proto_BuildState.OPERATE)
			message.setTableId(buildInfo.getCurWorkTableId());
		else if (state == Proto_BuildState.UPGRADE || state == Proto_BuildState.OPERATE_UPGRADE)
			message.setTableId(buildInfo.getTableId());
		
		int maxHelpCount = TableManager.GetInstance().TablelegionConfig().GetElement(1).maxNum();
		if (connect.getPlayer().IsVipValid()) {
			maxHelpCount = connect.getBuffs().getValueByIncPassiveBuff(Common.PASSIVEBUFF_TYPE.INC_LEGION_HELP_COUNT,maxHelpCount);
		}
		message.setLv(buildInfo.getLevel());
		message.setMaxNum(maxHelpCount);
		message.setPlayerId(player.getPlayerId());
		
		final ProLegion.Msg_G2S_SeekHelp join = message.build();
		final Connection cn = connect;
		final int legionid = player.getBelegionId();
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					ShareServerManager.getInstance().sendMsgLegion(cn, legionid, join);
				} catch (GameException e) {
				}catch (Exception e) {
					logger.error("OnSeekHelp err, ", e);
				}
			}
		}, 0);
	}
	
	//创建军团  客户端-游戏服务器 
	public void OnCreateLegion(Connection connect, ProLegion.Msg_C2G_Create_Legion msg) throws Exception{
		if (connect.isInState(Connection.Msg_C2G_Create_Legion))
			return ;
		
		if ("".equals(msg.getName())) 
			throw new GameException("军团名称不能为空");
		
		if (NameFilter.getIns().containWord(msg.getName())) {
			connect.ShowPrompt(PromptType.LEGION_NAME_INVAILD);
			return;
		}

		ConPlayerAttr player = connect.getPlayer();
		if (player.getBelegionId() != 0)
			return;

		MT_Data_legionConfig legionConfig = TableManager.GetInstance().TablelegionConfig().GetElement(1);
		if (!connect.getItem().checkItemEnough(legionConfig.money().field1(), legionConfig.money().field2()))
			return;
		
		int count = DbMgr.getInstance().getShareDB().Count(DatabaseLegion.class, "name = ? ",msg.getName());
		if (count!=0){
			connect.ShowPrompt(PromptType.LEGION_NAME_FAIL);
			return;
		} 
		
		final ProLegion.Msg_G2S_Create_Legion message = ProLegion.Msg_G2S_Create_Legion.newBuilder()
				.setName(msg.getName())
				.setImageIndex(msg.getImageIndex())
				.setIsValidate(msg.getIsValidate())
				.setPlayerId(player.getPlayerId())
				.setWord(player.getLanguage()).build();
		final Connection cn = connect;
		
		cn.setState(true, Connection.Msg_C2G_Create_Legion);
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					ShareServerManager.getInstance().sendMsgCreateLegion(cn,message);
				} catch (GameException e) {
				} catch (Exception e) {
					logger.error("OnCreateLegion err, ", e);
				} finally {
					cn.setState(false, Connection.Msg_C2G_Create_Legion);
				}
			}
		}, 0);
	}
	
	//创建军团  分流服务器-游戏服务器 
	public void OnCreateLegionShare(Connection connect,SHARE_SERVER_TYPE type,int id,ProLegion.Msg_G2C_LegionInfo msg){
		//修改玩家所属军团字段
		connect.getPlayer().updatePlayerLegion(msg.getId());
		
		long s_num = IPOManagerDb.getInstance().getNextId();
		MT_Data_legionConfig legionConfig = TableManager.GetInstance().TablelegionConfig().GetElement(1);
		connect.getItem().setItemNumber(legionConfig.money().field1(), legionConfig.money().field2(), SETNUMBER_TYPE.MINUS,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,IPOManagerDb.getInstance().getNextId(),"",Item_Channel.CREATE_LEGION);
		IPOManagerDb.getInstance().LogBuyService(connect, "createlegion", s_num);
		
		//更新玩家的buf
		ClientMessagePassiveBuff.getInstance().UpdateLegionBuf(connect, 0, msg.getLevel());
		//创建军团日志
		LogService.logLegionCreate(msg.getId(),s_num,connect.getPlayerId(), connect.getPlayer().getLevel());
		connect.sendReceiptMessage(msg);//游戏服务器--客服端
	}
	
	//查看申请列表 分流服务器-游戏服务器 
	public void OnLookRequestList(Connection connect,SHARE_SERVER_TYPE type,int id,ProLegion.Msg_G2C_Look_Request_List msg){
		connect.sendReceiptMessage(msg);//游戏服务器--客服端
	}
	
	//加入军团 客户端-游戏服务器
	public void OnJoinLegion(Connection connect, ProLegion.Msg_C2G_join_Legion msg) throws Exception  {
		if (connect.isInState(Connection.Msg_C2G_join_Legion))
			return ;
		
		ConPlayerAttr player = connect.getPlayer();
		if (player.getBelegionId() != 0) 
			throw new GameException("你已经加入了军团，不能重复加入");

		final ProLegion.Msg_G2S_join_Legion message = ProLegion.Msg_G2S_join_Legion.newBuilder()
				.setId(msg.getId())
				.setPlayerId(player.getPlayerId()).build();
		final int legionid = msg.getId();
		final Connection cn = connect;
		cn.setState(true, Connection.Msg_C2G_join_Legion);
		
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					ShareServerManager.getInstance().sendMsgLegion(cn, legionid, message);
				} catch (GameException e) {
				} catch (Exception e) {
					logger.error("OnJoinLegion err,", e);
				}finally{
					cn.setState(false, Connection.Msg_C2G_join_Legion);
				}
			}
		}, 0);
	}
	
	//加入军团,委派官员,开除人员 分流服务器-游戏服务器 
	public void OnAllLegionShare(Connection connect,SHARE_SERVER_TYPE type,int id,ProLegion.Msg_S2G_LegionInfo_Share msg) throws Exception{
		if (msg.getTypeName().equals("OnJoinLegion")) {
			//加入军团，不需要验证,直接更新数据库
			connect.getPlayer().updatePlayerLegion(msg.getId());
			//修改玩家身上的军团信息
			connect.getPlayer().setLegionInfo(msg.getId(),msg.getLevel());
			connect.setNeed_recalc_fight_val(true);
			
			ProLegion.Msg_G2C_LegionInfo.Builder message = createLegionInfoMsg(msg);
			HttpProcessManager.getInstance().sendMessageToAllPlayerSync(ConveyCondition.NEED_LEGION,String.valueOf(msg.getId()), message.build());
//			connect.sendReceiptMessage(message.build());
			//更新玩家的buf
			ClientMessagePassiveBuff.getInstance().UpdateLegionBuf(connect, 0, msg.getLevel());
			
			//加入军团系统提示：XXX加入了军团
			Msg_G2C_Prompt msg_G2C_Prompt = Connection.buildPromptMsg(PromptType.LEGION_JOIN, PROMPT_SCENE.NONE, null, connect.getPlayerName());
			HttpProcessManager.getInstance().sendMessageToAllPlayerSync(ConveyCondition.NEED_LEGION,String.valueOf(msg.getId()),msg_G2C_Prompt);
			
			//加入军团日志记录
			LogService.logLegionJoin(msg.getId(),connect.getPlayerId(), connect.getPlayer().getLevel());
			connect.setState(false, Connection.Msg_C2G_join_Legion);
			
			ProRanking.Msg_C2G_Ranking msgrank = ProRanking.Msg_C2G_Ranking.newBuilder()
					.setForm(Proto_RankingForm.GLOBAL1)
					.setType(Proto_RankingType.LEGION_RANKING).build();
			ClientMessageRanking.getInstance().OnComputer(connect, msgrank);
			ProRanking.Msg_C2G_Ranking msgrank1 = ProRanking.Msg_C2G_Ranking.newBuilder()
					.setForm(Proto_RankingForm.REGION)
					.setType(Proto_RankingType.LEGION_RANKING).build();
			ClientMessageRanking.getInstance().OnComputer(connect, msgrank1);
		}else if (msg.getTypeName().equals("OnAppointOfficial")) {
			//委派官员
			ProLegion.Msg_G2C_LegionInfo.Builder message = createLegionInfoMsg(msg);
			HttpProcessManager.getInstance().sendMessageToAllPlayerSync(ConveyCondition.NEED_LEGION,String.valueOf(msg.getId()), message.build());
		}else if (msg.getTypeName().equals("kikOut")){
			//开除人员
			ProLegion.Msg_G2C_LegionInfo.Builder message = createLegionInfoMsg(msg);//发送军团信息
			HttpProcessManager.getInstance().sendMessageToAllPlayerSync(ConveyCondition.NEED_LEGION,String.valueOf(msg.getId()), message.build());
		}else if (msg.getTypeName().equals("demotion")){
			//降职人员
			ProLegion.Msg_G2C_LegionInfo.Builder message = createLegionInfoMsg(msg);//发送军团信息
			HttpProcessManager.getInstance().sendMessageToAllPlayerSync(ConveyCondition.NEED_LEGION,String.valueOf(msg.getId()), message.build());
		}else if (msg.getTypeName().equals("assignment")){
			//转让军团长
			ProLegion.Msg_G2C_LegionInfo.Builder message = createLegionInfoMsg(msg);//发送军团信息
			HttpProcessManager.getInstance().sendMessageToAllPlayerSync(ConveyCondition.NEED_LEGION,String.valueOf(msg.getId()), message.build());
		}else if (msg.getTypeName().equals("signoutLegion")){
			//退出军团
			connect.getPlayer().updatePlayerLegion(0);
			
			ProLegion.Msg_G2C_LegionInfo.Builder result = ProLegion.Msg_G2C_LegionInfo.newBuilder();
			result.setType(LegionInfo_enum.Delete);
			result.setId(0);
			connect.sendReceiptMessage(result.build());
			
			ProLegion.Msg_G2C_LegionInfo.Builder message = createLegionInfoMsg(msg);//发送军团信息
			HttpProcessManager.getInstance().sendMessageToAllPlayerSync(ConveyCondition.NEED_LEGION,String.valueOf(msg.getId()), message.build());
			//更新军团buf
			ClientMessagePassiveBuff.getInstance().UpdateLegionBuf(connect, msg.getLevel(),0);
			
			//加入军团系统提示：XXX退出了军团 
			Msg_G2C_Prompt msg_G2C_Prompt = Connection.buildPromptMsg(PromptType.LEGION_OUT, PROMPT_SCENE.NONE, null, connect.getPlayerName());
			HttpProcessManager.getInstance().sendMessageToAllPlayerSync(ConveyCondition.NEED_LEGION,String.valueOf(msg.getId()),msg_G2C_Prompt);
		
			
			ProRanking.Msg_C2G_Ranking msgrank = ProRanking.Msg_C2G_Ranking.newBuilder()
					.setForm(Proto_RankingForm.GLOBAL1)
					.setType(Proto_RankingType.LEGION_RANKING).build();
			ClientMessageRanking.getInstance().OnComputer(connect, msgrank);
			ProRanking.Msg_C2G_Ranking msgrank1 = ProRanking.Msg_C2G_Ranking.newBuilder()
					.setForm(Proto_RankingForm.REGION)
					.setType(Proto_RankingType.LEGION_RANKING).build();
			ClientMessageRanking.getInstance().OnComputer(connect, msgrank1);
		}else if (msg.getTypeName().equals("setVerifyState")){
			//修改状态
			ProLegion.Msg_G2C_LegionInfo.Builder message = createLegionInfoMsg(msg);//发送军团信息
			connect.sendReceiptMessage(message.build());
			
		}else if (msg.getTypeName().equals("contributionDo")){
			//捐献
			ProLegion.Msg_G2C_LegionInfo.Builder message = createLegionInfoMsg(msg);//发送军团信息
			HttpProcessManager.getInstance().sendMessageToAllPlayerSync(ConveyCondition.NEED_LEGION, String.valueOf(msg.getId()), message.build());
		}else if (msg.getTypeName().equals("modifyNotice")){
			//修改公告
			ProLegion.Msg_G2C_LegionInfo.Builder message = createLegionInfoMsg(msg);
			HttpProcessManager.getInstance().sendMessageToAllPlayerSync(ConveyCondition.NEED_LEGION,String.valueOf(msg.getId()), message.build());
		}
	}
	
	private Builder createLegionInfoMsg(Msg_S2G_LegionInfo_Share msg) {
		ProLegion.Msg_G2C_LegionInfo.Builder message = ProLegion.Msg_G2C_LegionInfo.newBuilder();//服务器返回客服端军团信息
		message.setType(msg.getType());
		message.setId(msg.getId());
		message.setName(msg.getName());
		message.setTitle(msg.getTitle());
		message.setLevel(msg.getLevel());
		message.setIsValidate(msg.getIsValidate());
		message.setBossId(msg.getBossId());
		message.setImgIndex(msg.getImgIndex());
		message.setCurNum(msg.getCurNum());
		message.setBossName(msg.getBossName());
		message.setMoney(msg.getMoney());
		message.setAsk(message.getAsk());
		for(Long officerId:msg.getOfficerIdList())
			message.addOfficerId(officerId);
		for(Long eliteId:msg.getJyIdList())
			message.addJyId(eliteId);
		return message;
	}

	//捐献操作 客服端-游戏服务器
	public void OnContributionDo(Connection connect, ProLegion.Msg_C2G_Contribution_Item msg) throws Exception  {
		if (msg.getType()== 0) {
			OnContributionMoney(connect,msg);  // 0 捐赠金币
		}else if (msg.getType()== 1) {
			OnContributionGoods(connect,msg,1);//1 捐赠物资
		}else if (msg.getType()== 2) {
			OnContributionGoods(connect,msg,10);//捐赠物资 X10
		}
	}
	
	//加入军团 ,军团列表 分流服务器-游戏服务器 需要验证
	public void OnJoinLegionShareNeedVald(Connection connect,SHARE_SERVER_TYPE type,int id,ProLegion.Msg_G2C_query_Legions msg){
		connect.sendReceiptMessage(msg);
		
		//给客户端提示有新的申请(只有加入军团需要验证的时候才发下面的协议)
		if (msg.getIsJionNeedValidate()){
			int legionId = msg.getInfo(0).getId();
			Msg_G2C_LegionInfo.Builder info = Msg_G2C_LegionInfo.newBuilder();
			info.setType(LegionInfo_enum.Request);
			info.setId(legionId);
			info.setAsk(true);
			HttpProcessManager.getInstance().sendMessageToAllPlayerSync(ConveyCondition.NEED_LEGION,String.valueOf(legionId), info.build());
		}
	}
	
	//成员操作 
	public void OnPeopleDo(Connection connect, ProLegion.Msg_C2G_PeopleManage msg) throws Exception  {
		if (msg.getType()== PeopleManage_enum.Appoint_Official) {
			OnAppointOfficial(connect,msg); //委任官员
		}else if (msg.getType()== PeopleManage_enum.Kick_Out) {
			OnKickOut(connect,msg);			//开除
		}else if (msg.getType()== PeopleManage_enum.Assignment) {
			OnAssignment(connect,msg);		//转让军团长
		}else if (msg.getType()== PeopleManage_enum.Demotion) {
			OnDemotion(connect,msg);		//降职
		}
	}
	
	//转让军团长 客服端-游戏服务器
	public void OnAssignment(final Connection connect, ProLegion.Msg_C2G_PeopleManage msg) throws Exception  {
		//被转让的人员
		Integer beplayerlegionid = connect.getPlayer().getPlayerLegionIdById(msg.getBePlayerId());
		if (beplayerlegionid == null)
			return ;

		ConPlayerAttr player = connect.getPlayer();
		final ProLegion.Msg_G2S_PeopleManage message = ProLegion.Msg_G2S_PeopleManage.newBuilder()
				.setType(msg.getType())
				.setBePlayerId(msg.getBePlayerId())
				.setPlayerId(player.getPlayerId())
				.setLegionId(beplayerlegionid).build();
		final int legionid = player.getBelegionId();
		
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					ShareServerManager.getInstance().sendMsgLegion(connect, legionid, message);
				} catch (GameException e) {
				} catch (Exception e) {
					logger.error("OnAssignment err, ", e);
				}
			}
		}, 0);
	}

	//降职 客服端-游戏服务器
	public void OnDemotion(final Connection connect, Msg_C2G_PeopleManage msg) throws Exception {
		ConPlayerAttr player = connect.getPlayer();

		final ProLegion.Msg_G2S_PeopleManage message = ProLegion.Msg_G2S_PeopleManage.newBuilder()
				.setType(msg.getType())
				.setBePlayerId(msg.getBePlayerId())
				.setPlayerId(player.getPlayerId())
				.setLegionId(player.getBelegionId()).build();
		final int legionid = player.getBelegionId();

		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					ShareServerManager.getInstance().sendMsgLegion(connect, legionid, message);
				} catch (GameException e) {
				} catch (Exception e) {
					logger.error("OnDemotion err, ", e);
				}
			}
		}, 0);
	}
	
	//委派官员 客服端-游戏服务器
	public void OnAppointOfficial(final Connection connect, ProLegion.Msg_C2G_PeopleManage msg) throws Exception  {
		//被委派的官员
		ConPlayerAttr player = connect.getPlayer();
		if (player.getBelegionId() ==0) 
			throw new GameException("当前玩家还没有加入军团，没有权限委派官员");
		
		final ProLegion.Msg_G2S_PeopleManage message = ProLegion.Msg_G2S_PeopleManage.newBuilder()
				.setType(msg.getType())
				.setBePlayerId(msg.getBePlayerId())
				.setPlayerId(player.getPlayerId())
				.setLegionId(player.getBelegionId()).build();
		final int legionid = player.getBelegionId();
		
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					ShareServerManager.getInstance().sendMsgLegion(connect, legionid, message);
				} catch (GameException e) {
				} catch (Exception e) {
					logger.error("OnAppointOfficial err, ", e);
				}
			}
		}, 0);
	}
	
	//开除人员 客服端-游戏服务器
	public void OnKickOut(final Connection connect, ProLegion.Msg_C2G_PeopleManage msg) throws Exception  {
		ConPlayerAttr player = connect.getPlayer();
		if (msg.getBePlayerId() == player.getPlayerId())  
			throw new GameException("不能踢自己");
		
		final int legionid = player.getBelegionId();
		final ProLegion.Msg_G2S_PeopleManage message = ProLegion.Msg_G2S_PeopleManage.newBuilder()
				.setType(msg.getType())
				.setBePlayerId(msg.getBePlayerId())
				.setPlayerId(player.getPlayerId())
				.setLegionId(player.getBelegionId()).build();
		
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					ShareServerManager.getInstance().sendMsgLegion(connect, legionid, message);
				} catch (GameException e) {
				} catch (Exception e) {
					logger.error("OnKickOut err, ", e);
				}
			}
		}, 0);
	}
	
	//成员列表 客服端-游戏服务器
	public void OnMembers(Connection connect, ProLegion.Msg_C2G_Legion_Members msg) throws Exception  {
		ConPlayerAttr player = connect.getPlayer();
		if (player.getBelegionId() == 0) 
			throw new GameException("你还没有加入军团");

		ProLegion.Msg_G2S_Legion_Members.Builder message = ProLegion.Msg_G2S_Legion_Members.newBuilder();
		message.setPlayerId(player.getPlayerId());
		message.setLegionId(player.getBelegionId());
		
		final Connection cn = connect;
		final int legionid = player.getBelegionId();
		final ProLegion.Msg_G2S_Legion_Members mem = message.build();
		
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					ShareServerManager.getInstance().sendMsgLegion(cn,legionid, mem);
				} catch (GameException e) {
				}catch (Exception e) {
					logger.error("OnMembers error, ", e);
				}
			}
		}, 0);
	}
	
	//成员列表,开除人员,退出军团 分流服务器-游戏服务器
	public void OnMembersShare(Connection connect,SHARE_SERVER_TYPE type,int id,ProLegion.Msg_G2C_Legion_Members msg){
		ConPlayerAttr player = connect.getPlayer();
		Integer legionId = player.getBelegionId();
		if (msg.getTypeName().equals("OnMembers")) {
			//列表
			connect.sendReceiptMessage(msg);
		}else if (msg.getTypeName().equals("kikOut")) {
			//踢人
			connect.sendReceiptMessage(msg);
			HttpProcessManager.getInstance().sendMessageToAllPlayerSync(ConveyCondition.NEED_LEGION, legionId.toString(), msg);
		}else if (msg.getTypeName().equals("signoutLegion")) {
			//退出军团
			HttpProcessManager.getInstance().sendMessageToAllPlayerSync(ConveyCondition.NEED_LEGION, legionId.toString(), msg);
		}else if (msg.getTypeName().equals("setVerifyState")) {
			//修改验证状态
			connect.sendReceiptMessage(msg);
			HttpProcessManager.getInstance().sendMessageToAllPlayerSync(ConveyCondition.NEED_LEGION, legionId.toString(), msg);
			
			//记录加入军团日志
			List<Msg_G2C_Legion_Members.playerInfo> playerInfos =  msg.getInfoList();
			for (Msg_G2C_Legion_Members.playerInfo pInfo : playerInfos) {
				LogService.logLegionJoin(legionId, pInfo.getPlayerId(), connect.getPlayer().getLevel());
			}
		}else if (msg.getTypeName().equals("contributionDo")) {
			//捐献
			long s_num = IPOManagerDb.getInstance().getNextId();
			if (msg.getInfo(0).getContributionType()==0) {
				//金币
				player.setContributeMoneyNum(1);
				
				MT_Data_LegionPlayerExp mT_Data_LegionPlayerExp = TableManager.GetInstance().TableLegionPlayerExp().GetElement(player.getLevel());
				
				connect.getItem().setItemNumber(ITEM_ID.MONEY,mT_Data_LegionPlayerExp.money(), SETNUMBER_TYPE.MINUS,
						VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"",Item_Channel.LEGION_CONTRBUTE);
				connect.getItem().setItemNumber(ITEM_ID.EXP,mT_Data_LegionPlayerExp.exp(), SETNUMBER_TYPE.ADDITION,
						VmChannel.InGameDrop, ProductChannel.InGameDrop,s_num,"",Item_Channel.LEGION_CONTRBUTE);//加经验，勋章开启
				ClientMessageCommon.getInstance().UpdateCountInfo(connect, Proto_ActionType.DONATE_MONEY,player.getContributionMoneyNum(),1);
				HttpProcessManager.getInstance().sendMessageToAllPlayerSync(ConveyCondition.NEED_LEGION, legionId.toString(), msg);

				//捐献金币记录日志
				LogService.logLegionContr(1,mT_Data_LegionPlayerExp.money(),
						mT_Data_LegionPlayerExp.exp(),legionId, connect.getPlayerId(), connect.getPlayer().getLevel());
				
				//提示其他人
//				Msg_G2C_Prompt msg_G2C_Prompt = Util.buildPromptMsg(PromptType.LEGION_CONTRBUTE_NOTIFY, connect.getPlayerName(),mT_Data_LegionPlayerExp.money());
//				HttpProcessManager.getInstance().sendMessageToAllPlayerSync(ConveyCondition.NEED_LEGION,String.valueOf(legionId),msg_G2C_Prompt);
				
				//提示自己
				MT_Data_legionConfig legionConfig = TableManager.GetInstance().TablelegionConfig().GetElement(1);
				connect.ShowPrompt(PromptType.LEGION_MONEY_CONTRBUTE, mT_Data_LegionPlayerExp.money(),mT_Data_LegionPlayerExp.exp(),legionConfig.contribution());
			}else{
				//物资
				MT_Data_legionConfig legionConfig = TableManager.GetInstance().TablelegionConfig().GetElement(1);
				//捐献物资一个
				if (msg.getInfo(0).getContributionType()==1){
					//物资不足
					MT_Data_commodity commodity  = null;
					if (!connect.getItem().checkItemEnough(ITEM_ID.LEGION_GOODS,1,false)){
						commodity = TableManager.GetInstance().Tablecommodity().GetElement(301);
						connect.getItem().setItemNumber(commodity.Attr().field1(),commodity.Attr().field2(),SETNUMBER_TYPE.MINUS,
									VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"",Item_Channel.LEGION_CONTRBUTE);
					}else{
						connect.getItem().setItemNumber(ITEM_ID.LEGION_GOODS,1,SETNUMBER_TYPE.MINUS,
								VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"",Item_Channel.LEGION_CONTRBUTE);
					} 
					connect.getItem().CheckData();
					
					//捐献物资日志
					LogService.logLegionContr(2,legionConfig.goodsNum().field1(),0,legionId,
							connect.getPlayerId(), connect.getPlayer().getLevel());
					
					//提示其他人
					Msg_G2C_Prompt msg_G2C_Prompt = Util.buildPromptMsg(PromptType.LEGION_WUZI_HECI,connect.getPlayerName(),1);
					HttpProcessManager.getInstance().sendMessageToAllPlayerSync(ConveyCondition.NEED_LEGION,String.valueOf(legionId),msg_G2C_Prompt);
					
					//提示自己
					connect.ShowPrompt(PromptType.LEGION_ITEM_CONTRBUTE,1,legionConfig.contribution());
					
				}
				//捐献物资十个
				else if (msg.getInfo(0).getContributionType()==2){
					connect.getItem().setItemNumber(ITEM_ID.LEGION_GOODS,legionConfig.goodsNum().field2(),SETNUMBER_TYPE.MINUS,
							VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"",Item_Channel.LEGION_CONTRBUTE);
					//捐献物资日志
					LogService.logLegionContr(3,legionConfig.goodsNum().field2(),0,legionId,connect.getPlayerId(), connect.getPlayer().getLevel());
				}
				HttpProcessManager.getInstance().sendMessageToAllPlayerSync(ConveyCondition.NEED_LEGION, legionId.toString(), msg);
			}
		}else if (msg.getTypeName().equals("requestPass")){
			//通过申请
			HttpProcessManager.getInstance().sendMessageToAllPlayerSync(ConveyCondition.NEED_LEGION, legionId.toString(), msg);
		
			//记录加入军团日志
			List<Msg_G2C_Legion_Members.playerInfo> playerInfos =  msg.getInfoList();
			for (Msg_G2C_Legion_Members.playerInfo pInfo : playerInfos) 
				LogService.logLegionJoin(legionId, pInfo.getPlayerId(), pInfo.getLevel());
		}
	}
	
	//退出军团 客服端-游戏服务器
	public void OnSignoutLegion(Connection connect, ProLegion.Msg_C2G_Signout_Legion msg) throws Exception  {
		if (connect.isInState(Connection.Msg_C2G_Signout_Legion))
			return ;
		
		ConPlayerAttr player = connect.getPlayer();
		final ProLegion.Msg_G2S_Signout_Legion message = ProLegion.Msg_G2S_Signout_Legion.newBuilder()
				.setPlayerId(player.getPlayerId())
				.setLegionId(player.getBelegionId()).build();
		final Connection cn = connect;
		final int legionid = player.getBelegionId();
		cn.setState(true, Connection.Msg_C2G_Signout_Legion);
		
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					ShareServerManager.getInstance().sendMsgLegion(cn, legionid, message);
				} catch (GameException e) {
				}catch (Exception e) {
					logger.error("OnSignoutLegion error, ", e);
				} finally {
					cn.setState(false, Connection.Msg_C2G_Signout_Legion);
				}
			}
		}, 0);
	}
	
	//修改验证状态 客服端-游戏服务器
	public void OnSetVerifyState(Connection connect, ProLegion.Msg_C2G_SetVerifyState msg) throws Exception{
		ConPlayerAttr player = connect.getPlayer();
		final ProLegion.Msg_G2S_SetVerifyState message = ProLegion.Msg_G2S_SetVerifyState.newBuilder()
				.setState(msg.getState())
				.setPlayerId(player.getPlayerId())
				.setLegionId(player.getBelegionId()).build();
		final Connection cn = connect;
		final int legion_id = player.getBelegionId();
		
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					ShareServerManager.getInstance().sendMsgLegion(cn, legion_id, message);
				} catch (GameException e) {
				}catch (Exception e) {
					logger.error("OnSetVerifyState error, ", e);
				}
			}
		}, 0);
	}
	
	//捐献金币 客服端-游戏服务器
	public void OnContributionMoney(final Connection connect, ProLegion.Msg_C2G_Contribution_Item msg) throws Exception  {
		if (connect.isInState(Connection.Msg_C2G_Contribution_Item))
			return ;
		ConPlayerAttr player = connect.getPlayer();
		MT_Data_LegionPlayerExp mT_Data_LegionPlayerExp = TableManager.GetInstance().TableLegionPlayerExp().GetElement(player.getLevel());
		if(!connect.getItem().checkItemEnough(ITEM_ID.MONEY, mT_Data_LegionPlayerExp.money()))
			return ;
		if (msg.getType()==0&&player.getContributionMoneyNum()==1)
	    	 throw new GameException(PromptType.CONTRBUTE_IS_FULL);
		
		final ProLegion.Msg_G2S_Contribution_Item message = ProLegion.Msg_G2S_Contribution_Item.newBuilder()
				.setType(msg.getType())
			.setPlayerId(player.getPlayerId())
			.setLegionId(player.getBelegionId()).build();
		final int legionid = player.getBelegionId();
		connect.setState(true, Connection.Msg_C2G_Contribution_Item);
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					ShareServerManager.getInstance().sendMsgLegion(connect, legionid, message);
				} catch (GameException e) {
				}catch (Exception e) {
					logger.error("OnContributionMoney error, ", e);
				} finally {
					connect.setState(false, Connection.Msg_C2G_Contribution_Item);
				}
			}
		}, 0);
	}
	
	//捐献物资
	public void OnContributionGoods(final Connection connect, ProLegion.Msg_C2G_Contribution_Item msg,int num) throws Exception  {
		if (connect.isInState(Connection.Msg_C2G_Contribution_Item))
			return ;
		//物资不足,判断金砖
		if (!connect.getItem().checkItemEnough(ITEM_ID.LEGION_GOODS, num,false)){
			MT_Data_commodity commodity = TableManager.GetInstance().Tablecommodity().GetElement(301);
			if (!connect.getItem().checkItemEnough(commodity.Attr().field1(), commodity.Attr().field2()))
				return ;
		} 
			
		ConPlayerAttr player = connect.getPlayer();
		final ProLegion.Msg_G2S_Contribution_Item message = ProLegion.Msg_G2S_Contribution_Item.newBuilder()
				.setType(msg.getType())
				.setPlayerId(player.getPlayerId())
				.setLegionId(player.getBelegionId()).build();
		final int legionid = player.getBelegionId();
		connect.setState(true, Connection.Msg_C2G_Contribution_Item);
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					ShareServerManager.getInstance().sendMsgLegion(connect, legionid, message);
				} catch (GameException e) {
				}catch (Exception e) {
					logger.error("OnContributionMoney error, ", e);
				} finally {
					connect.setState(false, Connection.Msg_C2G_Contribution_Item);
				}
			}
		}, 0);
	}	
	
	//查询军团列表 客服端-游戏服务器
	public void OnQueryLegions(Connection connect, ProLegion.Msg_C2G_query_Legions msg) throws Exception{
		ConPlayerAttr player = connect.getPlayer();
		ProLegion.Msg_G2S_query_Legions.Builder message = ProLegion.Msg_G2S_query_Legions.newBuilder();
		message.setName(msg.getName());
		message.setNation(connect.getPlayer().getNation());
		message.setPlayerId(player.getPlayerId());
		
		final Connection cn = connect;
		final ProLegion.Msg_G2S_query_Legions legion = message.build();
		
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					ShareServerManager.getInstance().sendMsgCreateLegion(cn, legion);
				} catch (GameException e) {
				}catch (Exception e) {
					logger.error("OnQueryLegions err:", e);
				}
			}
		}, 0);
		
	}
	
	//修改公告 客服端-游戏服务器
	public void OnModifyNotice(Connection connect, ProLegion.Msg_C2G_Modify_Notice msg) throws Exception  {
		String notice = WordFilter.getIns().filter(msg.getNotice());
		ConPlayerAttr player = connect.getPlayer();
		final ProLegion.Msg_G2S_Modify_Notice message = ProLegion.Msg_G2S_Modify_Notice.newBuilder()
				.setNotice(notice)
				.setPlayerId(player.getPlayerId())
				.setLegionId(player.getBelegionId()).build();
		final int legionid = player.getBelegionId();
		final Connection cn = connect;
		
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					ShareServerManager.getInstance().sendMsgLegion(cn, legionid, message);
					cn.ShowPrompt(PromptType.MODITY_LEGION_PUBLIC);
				} catch (GameException e) {
				}catch (Exception e) {
					logger.error("OnModifyNotice err, ", e);
				}
			}
		}, 0);
	}
	
	//军团长或官员查看申请入团列表 客服端-游戏服务器
	public void OnLookRequestList(Connection connect, ProLegion.Msg_C2G_Look_Request_List msg) throws Exception  {
		ConPlayerAttr player = connect.getPlayer();	
		final ProLegion.Msg_G2S_Look_Request_List message = ProLegion.Msg_G2S_Look_Request_List.newBuilder()
				.setPlayerId(player.getPlayerId())
				.setLegionId(player.getBelegionId()).build();
		final int legionid = player.getBelegionId();
		final Connection cn = connect;
		
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					ShareServerManager.getInstance().sendMsgLegion(cn,legionid, message);
				} catch (GameException e) {
				}catch (Exception e) {
					logger.error("OnHelpPlayer, error:", e);
				}
			}
		}, 0);
	}

	//申请通过或者拒绝 客服端-游戏服务器
	public void OnRequestPassOrReject(Connection connect, ProLegion.Msg_C2G_Request_Pass msg) throws Exception  {
		OnRequestPassOrNot(connect,msg);
	}
	
	//军团长或官员通过申请(回成员列表)
	public void OnRequestPassOrNot(final Connection connect, ProLegion.Msg_C2G_Request_Pass msg) throws Exception  {
		ConPlayerAttr player = connect.getPlayer();
		final int legionid = player.getBelegionId();
		
		if (msg.getType()== Request_enum.Yes) {
			if (msg.getPlayerId()!=0) {
				//判断玩家是否已经加入了军团
				DatabasePlayer bePlayer = DbMgr.getInstance().getDbByPlayerId(msg.getPlayerId()).selectFieldFrist(DatabasePlayer.class, "player_id = ? ", "belong_legion,name",msg.getPlayerId());
				if (bePlayer != null && bePlayer.belong_legion != 0){
					
					connect.ShowPrompt(PromptType.BELONG_LEGION, bePlayer.getName());
					
					final Msg_G2S_DelLegionRequest delmsg = Msg_G2S_DelLegionRequest.newBuilder()
							.setLegionId(player.getBelegionId())
							.setPlayerId(msg.getPlayerId()).build();
					
					ThreadPoolManager.getInstance().schedule(new Runnable() {
						@Override
						public void run() {
							try {
								ShareServerManager.getInstance().sendMsgLegion(connect, legionid, delmsg);
							} catch (GameException e) {
							}catch (Exception e) {
								logger.error("OnRequestPassOrNot del err, ", e);
							}
						}
					}, 0);
					return;
				} 
			}
		}
		
		final ProLegion.Msg_G2S_Request_Pass message = ProLegion.Msg_G2S_Request_Pass.newBuilder()
				.setType(msg.getType())
				.setBePlayerId(msg.getPlayerId())
				.setCurPlayerId(player.getPlayerId())
				.setLegionId(player.getBelegionId()).build();
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					ShareServerManager.getInstance().sendMsgLegion(connect,legionid, message);
				} catch (GameException e) {
				}catch (Exception e) {
					logger.error("OnRequestPassOrNot pass err, ", e);
				}
			}
		}, 0);
	}

	//购买军团商店物品
	public void OnBuyCommodityInLegionStore(Connection connect, ProLegion.Msg_C2G_Legion_Buy msg) throws ParseException, GameException  {
		MT_Data_LegionStore mT_Data_LegionStore = TableManager.GetInstance().TableLegionStore().GetElement(msg.getTableID());
		if (mT_Data_LegionStore ==null) 
			throw new GameException("购买物品在table表不存在");
		if (!connect.getItem().checkItemEnoughByInt2(mT_Data_LegionStore.needMoney()))
			return;
		
		int item_count = 1;
		if (Util.isEqiupFragByTableId(mT_Data_LegionStore.itemTableId())) {
			if (!connect.getItem().checkEquipFragCount(item_count))
				return ;
		}
		
		ConPlayerAttr player = connect.getPlayer();
		long s_num = IPOManagerDb.getInstance().getNextId();
		//减钱
		connect.getItem().setItemNumber(mT_Data_LegionStore.needMoney().field1(),mT_Data_LegionStore.needMoney().field2(), SETNUMBER_TYPE.MINUS,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"", Item_Channel.BUY_LEGION_SHOP);
		//加物品
		connect.getItem().setItemNumber(mT_Data_LegionStore.itemTableId(), item_count, SETNUMBER_TYPE.ADDITION,
				VmChannel.InGameDrop, ProductChannel.Buy,s_num,"", Item_Channel.BUY_LEGION_SHOP);
		//记录军团商店购买日志
		LogService.logStore(mT_Data_LegionStore.itemTableId(), s_num, connect.getPlayerId(),0,1,connect.getPlayer().getLevel());
		//修改player列表已经被购买物品
		player.setlegion_store_list(mT_Data_LegionStore.ID());

		List<Integer> updateItems = new ArrayList<Integer>();
		updateItems.add(mT_Data_LegionStore.itemTableId());

		connect.getTasks().AddTaskNumber(TASK_TYPE.BUY_FROM_LEGION_STORE,0, 1, 0);

		//发送已经购买商品信息
		sendBuyCommodityMsg(msg,connect,player);
	}
	
	private void sendBuyCommodityMsg(Msg_C2G_Legion_Buy msg, Connection connect, ConPlayerAttr player) throws ParseException {
		ProLegion.Msg_G2C_Legion_Commoditys.Builder resultBuilder = ProLegion.Msg_G2C_Legion_Commoditys.newBuilder();
		ProLegion.Msg_G2C_Legion_Commoditys.commodity.Builder commodity = ProLegion.Msg_G2C_Legion_Commoditys.commodity.newBuilder();
		commodity.setTableID(msg.getTableID());
		commodity.setBType(true);
		resultBuilder.addInfo(commodity);
		resultBuilder.setCount(player.getluck_val());
		resultBuilder.setType(Proto_LegionShopType.UPDATE);
		resultBuilder.setTime(LegionManager.getInstance().getTime());
		connect.sendReceiptMessage(resultBuilder.build());
	}

	public void OnLegionStoreList(Connection connect, ProLegion.Msg_C2G_Legion_List msg,boolean isSendMsg) throws Exception{

		MT_Data_legionConfig legionConfig = TableManager.GetInstance().TablelegionConfig().GetElement(1);
		int needFreshNum = 0 ;
		int moneyId = 0;
		int needMoneyNum = 0;
		long s_num = IPOManagerDb.getInstance().getNextId();
		
		//现在每次刷新需要一个刷新劵
		int needNum = legionConfig.freshNum();
		//手动刷新
		if(msg.getIsHandRefresh()==1){
			if (connect.getItem().getItemCountByTableId(ITEM_ID.FRESH_ID) < needNum){
				//刷新劵不足
				MT_Data_commodity commodity = TableManager.GetInstance().Tablecommodity().GetElement(302);
				if (!connect.getItem().checkItemEnough(commodity.Attr().field1(), commodity.Attr().field2()))
					return;
					
				moneyId = commodity.Attr().field1();
				needMoneyNum = commodity.Attr().field2();
				connect.getItem().setItemNumber(moneyId, needMoneyNum, SETNUMBER_TYPE.MINUS,
						VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"", Item_Channel.FLUSH_LEGION_SHOP);			// 扣除金币
			}else{
				//刷新劵足够
				needFreshNum = needNum;
				connect.getItem().setItemNumber(ITEM_ID.FRESH_ID,needFreshNum, SETNUMBER_TYPE.MINUS,
						VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"", Item_Channel.FLUSH_LEGION_SHOP);			// 扣除材料
			}
		}
		List<CustomLegionCommodity> commodities = LegionManager.getInstance().legionStoreList(connect,msg.getIsHandRefresh());
		//发消息
		if(isSendMsg)
			sendLegionStoreMsg(connect, needFreshNum, moneyId, needMoneyNum,commodities);
		//刷新记录日志
		LogService.logLegionFlush(s_num,1,connect.getPlayer().getBelegionId(),connect.getPlayerId(),connect.getPlayer().getLevel());
	}
	
	//军团商店列表
	public void OnLegionStoreList(Connection connect, ProLegion.Msg_C2G_Legion_List msg) throws Exception{
		OnLegionStoreList(connect, msg, true);
	}

	private void sendLegionStoreMsg(Connection connect, int needFreshNum,int moneyId, int needMoneyNum,
			List<CustomLegionCommodity> commodities) throws ParseException {
		ProLegion.Msg_G2C_Legion_Commoditys.Builder message = ProLegion.Msg_G2C_Legion_Commoditys.newBuilder();
		ProLegion.Msg_G2C_Legion_Commoditys.commodity.Builder commodity = ProLegion.Msg_G2C_Legion_Commoditys.commodity.newBuilder();
		for (CustomLegionCommodity customLegionCommodity : commodities) {
			commodity.setTableID(customLegionCommodity.table_id);
			if (customLegionCommodity.is_buy ==1 )
				commodity.setBType(true);
			else
				commodity.setBType(false);
			message.addInfo(commodity);
		}
		message.setCount(connect.getPlayer().getluck_val());
		message.setType(Proto_LegionShopType.LIST);
		message.setTime(LegionManager.getInstance().getTime());
		message.setNeedFreshNum(needFreshNum);
		message.setMisMoneyId(moneyId);
		message.setMisMoneyCount(needMoneyNum);
		
		connect.sendReceiptMessage(message.build());
	}
}
