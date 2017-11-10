package gameserver.network.message.game;

import gameserver.connection.attribute.ConPlayerAttr;
import gameserver.connection.attribute.info.BuildInfo;
import gameserver.http.HttpProcessManager;
import gameserver.network.protos.common.ProLegion;
import gameserver.network.server.connection.Connection;
import gameserver.network.server.connection.ConnectionManager;
import gameserver.utils.DbMgr;

import commonality.PromptType;

import databaseshare.DatabaseRequestHelp;

public class ClientMessageHelp {
	private final static ClientMessageHelp instance = new ClientMessageHelp();
	public static ClientMessageHelp getInstance() { return instance; }
	public void initialize(){
		HttpProcessManager.getInstance().regMsgProcess(ProLegion.Msg_G2S_Help_ID.class,this, "addHelp");
		HttpProcessManager.getInstance().regMsgProcess(ProLegion.Msg_G2S_Request_Help.class,this, "addRequest");
	}

	//玩家得到一个帮助,share-中心服务器-GS,这样的路径过来的消息
	public void addHelp(ProLegion.Msg_G2S_Help_ID msg) throws Exception{
		//被帮助玩家的连接
		Connection connection = ConnectionManager.GetInstance().GetConnection(msg.getReqPlayerId());
		if (connection==null) 
			return;
		
		String pName = ConPlayerAttr.getPlayerNameById(msg.getDoPlayerId());
		synchronized (connection) 
		{
			BuildInfo bi = connection.getHelp().addHelp(msg.getHelpId(), msg.getDoPlayerId());
			if (bi == null || bi.isNormalState())
				return ;
				
			if (pName != null)
				connection.ShowPrompt(PromptType.LEGION_HELP_YOU, pName);
		}
	}
	
	//玩家申请帮助在Gs保存一份,share-中心服务器-GS,这样的路径过来的消息
	public void addRequest(ProLegion.Msg_G2S_Request_Help msg) throws Exception{
		Connection connection = ConnectionManager.GetInstance().GetConnection(msg.getPlayerId());
		//玩家不在线
		if (connection==null) 
			return;
		
		DatabaseRequestHelp requestHelp = DbMgr.getInstance().getShareDB().SelectOne(DatabaseRequestHelp.class, "help_id=?", msg.getRequestHelpId());
		
		synchronized (connection) 
		{
			connection.getHelp().addRequest(requestHelp);
		}
	}
}
