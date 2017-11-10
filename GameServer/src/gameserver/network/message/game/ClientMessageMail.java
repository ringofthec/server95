package gameserver.network.message.game;

import gameserver.http.HttpProcessManager;
import gameserver.network.IOServer;
import gameserver.network.protos.game.ProChat;
import gameserver.network.protos.game.ProChat.Proto_Enum_Refuse_ChatType;
import gameserver.network.protos.game.ProMail;
import gameserver.network.protos.game.ProMail.Proto_MailInfo;
import gameserver.network.server.connection.Connection;
import gameserver.network.server.connection.ConnectionManager;
import gameserver.utils.DbMgr;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.commons.util.DatabaseUtil;
import com.commons.util.TimeUtil;

import database.DatabaseMail;
import database.DatabasePlayer;

public class ClientMessageMail {
	private final static ClientMessageMail instance = new ClientMessageMail();
	public static ClientMessageMail getInstance() {
		return instance;
	}
	public void initialize() {
		IOServer.getInstance().regMsgProcess(ProMail.Msg_C2G_MailOper.class,this, "OnMailOper");
		HttpProcessManager.getInstance().regMsgProcess(ProMail.Msg_G2C_MailInfo.class,this, "OnPushMail");
		HttpProcessManager.getInstance().regMsgProcess(ProChat.Msg_S2G_refuseChat.class,this, "OnRefuseChat");
		HttpProcessManager.getInstance().regMsgProcess(ProChat.Msg_G2C_KikPlayerOut.class,this, "OnKikPlayerOut");
	}
	//领取奖励
	public void OnMailOper(Connection connect,ProMail.Msg_C2G_MailOper msg) throws Exception {
		int operId = msg.getOperId();
		
		if (operId == 0)
			connect.getMails().read(msg.getMailId());
		else if (operId == 1)
			connect.getMails().recive(msg.getMailId());
		else
			connect.getMails().delete(msg.getMailId());
	}
	//收到后台发来的邮件(目前只支持一次发送一封邮件)
	public void OnPushMail(ProMail.Msg_G2C_MailInfo msg) throws Exception {
		
		Connection connection = ConnectionManager.GetInstance().GetConnection(msg.getPlayerId());
		if (connection==null) 
			return;
		List<Proto_MailInfo>  mails = msg.getInfoList();
		Proto_MailInfo mail = mails.get(0);
		DatabaseMail databaseMail = DbMgr.getInstance().getDbByPlayerId(connection.getPlayerId()).SelectOne(DatabaseMail.class, "mail_id = ? ",mail.getMailId());
		connection.getMails().addMail(databaseMail,true);
	}
	
	//禁言
	public void OnRefuseChat(ProChat.Msg_S2G_refuseChat msg) throws Exception {
		Long endtime = TimeUtil.GetDateTime()+msg.getTimeLong()*60*60*1000;
		Connection connection = ConnectionManager.GetInstance().GetConnection(msg.getPlayerId());
		Proto_Enum_Refuse_ChatType type = msg.getType();
		if (connection==null){
			//玩家不在线
			DatabaseUtil databaseUtil = DbMgr.getInstance().getDbByPlayerId(msg.getPlayerId());
			DatabasePlayer player = databaseUtil.SelectOne(DatabasePlayer.class, "player_id = ? ", msg.getPlayerId());
			if(type==Proto_Enum_Refuse_ChatType.REFUSE){
				//禁言
				player.refuse_chat = 1;
				player.refuse_chat_time = endtime;
			}else if(type==Proto_Enum_Refuse_ChatType.FREE){
				//解言
				player.refuse_chat = 0;
				player.refuse_chat_time = TimeUtil.GetDateTime();
			}
			databaseUtil.Update(player," player_id = ? ", msg.getPlayerId());
			return;
		} 
		connection.getPlayer().refuseChat(type,endtime);
	}
	
	//踢人
	public void OnKikPlayerOut(ProChat.Msg_G2C_KikPlayerOut msg) throws Exception {
		ConnectionManager.GetInstance().destroyConnect(msg.getPlayerId(),null);
	}
}