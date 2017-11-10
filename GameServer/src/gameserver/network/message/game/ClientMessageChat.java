package gameserver.network.message.game;

import gameserver.connection.attribute.ConPlayerAttr;
import gameserver.http.HttpProcessManager;
import gameserver.logging.LogService;
import gameserver.messageprocess.base.ClientMessageBase;
import gameserver.messageprocess.base.MessageProcess;
import gameserver.network.IOServer;
import gameserver.network.protos.common.ProRanking;
import gameserver.network.protos.common.ProRanking.Msg_G2S_Rank_MAP.Proto_playerInfo_Rank;
import gameserver.network.protos.game.CommonProto;
import gameserver.network.protos.game.CommonProto.Msg_S2G_UpdateTable;
import gameserver.network.protos.game.ProChat.Msg_C2G_ChatMessage;
import gameserver.network.protos.game.ProChat.Msg_C2G_ChatReportPlayer;
import gameserver.network.protos.game.ProChat.Msg_C2G_SubmitBug;
import gameserver.network.protos.game.ProChat.Msg_G2C_ChatMessage;
import gameserver.network.protos.game.ProChat.Proto_Enum_ChatType;
import gameserver.network.protos.game.ProDebug.Msg_S2G_OpenFunction;
import gameserver.network.protos.game.ProLogin.Msg_C2G_ErrorLog;
import gameserver.network.server.connection.Connection;
import gameserver.network.server.connection.ConnectionManager;
import gameserver.share.ShareServerManager;
import gameserver.thread.ThreadPoolManager;
import gameserver.utils.DbMgr;
import gameserver.utils.GameException;
import gameserver.utils.WordFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import table.base.TableManager;

import com.commons.cache.CacheManager;
import com.commons.util.TimeUtil;

import commonality.Common;
import commonality.ConveyCondition;
import commonality.PromptType;
import databaseshare.DatabaseBug_report;
import databaseshare.DatabaseChat;
import databaseshare.DatabasePvp_match;

public class ClientMessageChat extends ClientMessageBase {
	private static List<Msg_G2C_ChatMessage> msgs = new ArrayList<Msg_G2C_ChatMessage>();
	private static HashMap<Integer, List<Msg_G2C_ChatMessage>> legion_msgs = new HashMap<Integer, List<Msg_G2C_ChatMessage>>();
	private final static ClientMessageChat instance = new ClientMessageChat();
	private static boolean REPORT_OFF=false;
	//保存排行榜前100名玩家和他的名次
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ConcurrentHashMap<Long,Integer> map = new ConcurrentHashMap();
	
	public static ClientMessageChat getInstance() {
		return instance; 
	}
	public void init() {
		IOServer.getInstance().regMsgProcess(Msg_C2G_SubmitBug.class, this, "onSubmitBug");
		IOServer.getInstance().regMsgProcess(Msg_C2G_ErrorLog.class, this, "onSubmitSysBug");
		IOServer.getInstance().regMsgProcess(Msg_C2G_ChatReportPlayer.class, this,"onChatReport");//聊天举报
		HttpProcessManager.getInstance().regMsgProcess(Msg_G2C_ChatMessage.class,this, "cacheLegionChat");
		HttpProcessManager.getInstance().regMsgProcess(ProRanking.Msg_G2S_Rank_MAP.class,this, "OnInitRankMap");
		HttpProcessManager.getInstance().regMsgProcess(Msg_S2G_OpenFunction.class,this,"onOpenFunction");
		HttpProcessManager.getInstance().regMsgProcess(Msg_S2G_UpdateTable.class,this, "OnUpdateTable");
	}
	
	public void onOpenFunction(Msg_S2G_OpenFunction msg) {
		boolean isopen = msg.getOpening();
		for (int funid : msg.getFunctionIdList()) {
			if (funid == 1) {
				if (isopen)
					CacheManager.setClose(false);
				else
					CacheManager.setClose(true);
			}else if(funid==2)
			{
				if(REPORT_OFF)
					REPORT_OFF=false;
				else
					REPORT_OFF=true;
			}
		}
	}
	
	public void onSubmitSysBug(Connection con, Msg_C2G_ErrorLog msg) {
		LogService.sysErr(con.getPlayerId(), msg.getMsg(), con.getPlayer().getLevel());
	}
	
	public void OnInitRankMap(ProRanking.Msg_G2S_Rank_MAP msg) throws Exception{
		java.util.List<gameserver.network.protos.common.ProRanking.Msg_G2S_Rank_MAP.Proto_playerInfo_Rank> list = msg.getPlayerInfoList();
		for (Proto_playerInfo_Rank rank : list) 
			map.putIfAbsent(rank.getPlayerId(), rank.getPlayerOrder());
	}
	
	public void OnUpdateTable(Msg_S2G_UpdateTable message) throws Exception {
    	for(String name:message.getTableNameList()) {
    		TableManager.GetInstance().pushModifiedTable(name);
    	}
    }
	
	//发送请求排行榜消息 G-S
	public void UpdateRank() throws Exception {
		if (getOrderCount() > 0)
			return;
		
		ProRanking.Msg_G2S_Ranking.Builder msg = ProRanking.Msg_G2S_Ranking.newBuilder();
		msg.setType(ProRanking.Proto_RankingType.TOTAL_FINGTH);//排行榜类型
		msg.setNation(ProRanking.Proto_RankingForm.GLOBAL1);//排行榜国别
		msg.setPlayerId(0);

		final ProRanking.Msg_G2S_Ranking rmsg = msg.build();

		ThreadPoolManager.getInstance().schedule(new Runnable() {
			public void run() {
				try {
					ShareServerManager.getInstance().sendMsgRank(null, rmsg);
				} catch (GameException e) {
				}catch (Exception e) {
				}
			}
		}, 0);
	}
	
	public Integer getOrderCount() {
		return map.size();
	}
	
	public Integer getOrderByPlayerId(Long playerId) {
		Integer order = map.get(playerId);
		if (order == null) order = 0;
		return order;
	}
	
	public void onSubmitBug(Connection con, Msg_C2G_SubmitBug msg) {
		
		DatabaseBug_report report = new DatabaseBug_report();
		report.bug = msg.getText();
		report.ip = con.getIP();
		report.player_id = con.getPlayerId();
		report.player_name = con.getPlayerName();
		report.bug_time = TimeUtil.GetTimestamp();
		DbMgr.getInstance().getShareDB().Insert(report);
		
		con.ShowPrompt(PromptType.BUG_SUPPORT);
	}
	
	public void onPlayerLogin(Connection con) {
		synchronized (msgs) {
			for (Msg_G2C_ChatMessage mm : msgs){
				con.sendPushMessage(mm);
			}
		}
		
		int legionId = con.getPlayer().getBelegionId();
		if (legionId != 0) {
			synchronized (legion_msgs) {
				if (legion_msgs.containsKey(legionId)) {
					List<Msg_G2C_ChatMessage> templist = legion_msgs.get(legionId);
					for (Msg_G2C_ChatMessage mm : templist)
						con.sendPushMessage(mm);
				}
			}
		}
	}
	
	public void cacheLegionChat(Msg_G2C_ChatMessage msg) {
		synchronized (legion_msgs) {
			List<Msg_G2C_ChatMessage> chats = null;
			if (legion_msgs.containsKey(msg.getLegionId()))
				chats = legion_msgs.get(msg.getLegionId());
			else {
				chats = new ArrayList<Msg_G2C_ChatMessage>();
				legion_msgs.put(msg.getLegionId(), chats);
			}
			
			Msg_G2C_ChatMessage.Builder temp = Msg_G2C_ChatMessage.newBuilder(msg);
			temp.setOnlineSync(true);
			
			if (chats.size() >= 10) {
				chats.remove(0);
				chats.add(temp.build());
			}
			else 
				chats.add(temp.build());
		}
	}
	
	/**聊天举报*/
	public void onChatReport(Connection con, Msg_C2G_ChatReportPlayer msg)
	{
		if(REPORT_OFF)
			return ;
		long playerId=msg.getPlayerId();//被举报玩家ID 
		 //0成功 1 已经举报 2玩家不存在
		int result=con.getPlayer().ChatReport(playerId);
		
		if(result==0||result==3)
		{
		   //公告并发送邮件
			con.ShowPrompt(PromptType.CHAT_REPORT);
		}
	}
	
	
	 public static String GetCharCountDown(int time){
	    if (time > Common.HOUR_SECOND)
	        return time / 3600+" h "+time % 3600 / 60+" m";
	    else if (time > Common.MINUTE_SECOND)
	        return time / 60 +" m "+time % 60+" s";
	    else
	        return time+" s";
	 }
	public static class ChatMessage extends MessageProcess<Msg_C2G_ChatMessage>{
		@Override
		protected void onProcess_impl(Connection connection,Msg_C2G_ChatMessage message) {
			if (message.getText().startsWith("/gm ")){
				String command = message.getText();
				command = command.replace("/gm", "");
				command = command.trim();
				String[] strs = command.split(";");
				for (String str : strs){
					int index = str.indexOf(" ");
					if (index > 0){
						String c = str.substring(0,index);
						String a = str.substring(index).trim();
						ClientMessageGM.ExecuteCommand(connection, c, a.split(" "));
					}
					else
						ClientMessageGM.ExecuteCommand(connection, str, new String[]{});
				}
				return;
			}
			
			//判断等级是否可以在世界频道发言
			if(message.getType() == Proto_Enum_ChatType.WORLD)
			{
				ConPlayerAttr player = connection.getPlayer();
				if (!player.CheckPlayerLevel(Common.WORLD_CHAR_OPEN_LEVEL, true))
					return;
			}
			
			//判断是否被禁言
			if(connection.getPlayer().isRefuseChat()){
				int seconds = (int) ((connection.getPlayer().getReuseChatTime() - TimeUtil.GetDateTime())/1000);
				connection.ShowPrompt(PromptType.REFUSE_CHAT_TIME,GetCharCountDown(seconds));
				return;
			}
			connection.getPlayer().updateRefuseChatState();
			
			Msg_C2G_ChatMessage msg = (Msg_C2G_ChatMessage)message;
			int legion = connection.getPlayer().getBelegionId();
			if (msg.getType() == Proto_Enum_ChatType.LEGION){
				if (legion <= 0 )
					return;
			}
			//分享战报,时间间隔20分钟
			if(msg.getVideoId()!=0){
				long secdTime = TimeUtil.GetDateTime() - connection.getPerTime();
				if(secdTime < 20 * 60 * 1000){
					connection.ShowPrompt(PromptType.ANCHOR_SHARE_FAIL,20 - secdTime/1000/60);
					return;
				}
			}
			
			//保存聊天信息
			DatabaseChat chat = new DatabaseChat();
			chat.player_id = connection.getPlayerId();
			chat.player_name = connection.getPlayerName();
			chat.content = msg.getText();
			chat.type = msg.getType().getNumber();
			chat.time = TimeUtil.GetTimestamp();
			DbMgr.getInstance().getShareDB().Insert(chat);
			
			String chatText = WordFilter.getIns().filter(message.getText());
			
			Msg_G2C_ChatMessage.Builder builder = Msg_G2C_ChatMessage.newBuilder();
			builder.setType(msg.getType());
			builder.setPlayerId(connection.getPlayerId());
			builder.setPlayerName(connection.getPlayerName());
			builder.setTime(TimeUtil.GetDateTime());
			builder.setFeat(connection.getPlayer().getFeat());
			builder.setText(chatText);
			builder.setHead(connection.getPlayer().getHead());
			builder.setOnlineSync(false);
			builder.setVideoId(msg.getVideoId());
			builder.setContextType(msg.getContextType());
			builder.setTargetPlayerName(msg.getTargetPlayerName());
			builder.setVipState(connection.getPlayer().getVipLevel() >= 1);
			builder.setFightRanking(ClientMessageChat.getInstance().getOrderByPlayerId(connection.getPlayerId()));
			builder.setFightValue(connection.getPlayer().calToTalFightVal());
			builder.setNation(connection.getPlayer().getNation());
			if (msg.getType() == Proto_Enum_ChatType.LEGION) {
				builder.setLegionId(legion);
				Msg_G2C_ChatMessage conMsg = builder.build();
				HttpProcessManager.getInstance().sendMessageToAllPlayerSync(ConveyCondition.NEED_LEGION,legion+"",conMsg);
				HttpProcessManager.getInstance().sendMessageToAllServerSync(conMsg);
			}
			else if (msg.getType() == Proto_Enum_ChatType.PRIVATE){
				builder.setLegionId(0);
				Connection con = ConnectionManager.GetInstance().GetConnection(msg.getPlayerId());
				if (con != null) {
					builder.setPrivateChatPlayerName(con.getPlayerName());
					con.sendPushMessage(builder.build());
				}
				else {
					DatabasePvp_match pvpmatch = DbMgr.getInstance().getShareDB().SelectOne(DatabasePvp_match.class, "player_id=?", msg.getPlayerId());
					builder.setPrivateChatPlayerName(pvpmatch.getName());
					if (pvpmatch.online)
						HttpProcessManager.getInstance().sendMessageToPlayerClientSync(msg.getPlayerId(), builder.build());
					else
						connection.ShowPrompt(PromptType.PLAYER_NOT_ONLINE);
				}
				
				connection.sendPushMessage(builder.build());
			}else{
				builder.setLegionId(0);
				Msg_G2C_ChatMessage conMsg = builder.build();
				HttpProcessManager.getInstance().sendMessageToAllPlayerSync(conMsg);
				
				synchronized (msgs) {
					Msg_G2C_ChatMessage.Builder temp = Msg_G2C_ChatMessage.newBuilder(conMsg);
					temp.setOnlineSync(true);
					if (msgs.size() >= 10) {
						msgs.remove(0);
						msgs.add(temp.build());
					}
					else 
						msgs.add(temp.build());
				}
			}
			//记录战报分享时间
			if(msg.getVideoId()!=0){
				connection.setPerTime(TimeUtil.GetDateTime());
				connection.ShowPrompt(PromptType.ANCHOR_SHARE_SUCCEED);
			}
		}
	}
}
