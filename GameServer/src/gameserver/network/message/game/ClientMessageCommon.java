package gameserver.network.message.game;

import java.util.ArrayList;
import java.util.List;

import commonality.PromptType;
import commonality.SHARE_SERVER_TYPE;
import gameserver.network.IOServer;
import gameserver.network.protos.game.CommonProto;
import gameserver.network.protos.game.ProGameInfo.Msg_C2G_FinishGuide;
import gameserver.network.protos.game.ProPvp;
import gameserver.network.server.connection.Connection;
import gameserver.network.server.connection.ConnectionManager;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import gameserver.share.ShareServerManager;


public class ClientMessageCommon {
//	private final Logger logger = LoggerFactory.getLogger(ClientMessageCommon.class);
	private final static ClientMessageCommon instance = new ClientMessageCommon();
	public static ClientMessageCommon getInstance() { return instance; }
	public void initialize()
	{
		IOServer.getInstance().regMsgProcess(Msg_C2G_FinishGuide.class, this, "OnFinishGuide");
		ShareServerManager.getInstance().regMsgProcess(CommonProto.Msg_S2G_Notice.class,this, "OnErrorNotice");
	}
	public void OnFinishGuide(Connection connection,Msg_C2G_FinishGuide message)
	{
		connection.getPlayer().FinishGuide(message.getGuideID());
	}
	public void OnErrorNotice(Connection connect,SHARE_SERVER_TYPE type,int id,CommonProto.Msg_S2G_Notice message)
	{
		Connection con=ConnectionManager.GetInstance().GetConnection(message.getPlayerId());
		if(con==null)
			return ;
		con.ShowPrompt(PromptType.valueOf(message.getType()));
	} 
	/**
	 * 向客户端更新各种数量
	 * @param connection
	 * @param type
	 * @param count
	 */
	public void UpdateCountInfo(Connection connection, ProPvp.Proto_ActionType type, int count, int maxCount)
	{
		List<ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.Builder> list = new ArrayList<ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.Builder>();
		
		ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.Builder info = ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.newBuilder();
		info.setType(type);
		info.setDoNum(count);
		info.setMaxNum(maxCount);
		
		list.add(info);
		UpdateCountInfo(connection, list);
	}
	public void UpdateCountInfo(Connection connection, List<ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.Builder> list)
	{
		ProPvp.Msg_G2C_doSthNum.Builder message = ProPvp.Msg_G2C_doSthNum.newBuilder();
		
		for (ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.Builder pair : list)
			message.addInfo(pair);
		
		connection.sendReceiptMessage(message.build());
	}
}
