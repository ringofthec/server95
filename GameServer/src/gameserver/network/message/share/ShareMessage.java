package gameserver.network.message.share;

import gameserver.network.message.game.ClientMessageAuth;
import gameserver.network.message.game.ClientMessageLegion;
import gameserver.network.protos.share.Test;
import gameserver.network.server.connection.Connection;
import gameserver.ranking.ClientMessageRanking;
import gameserver.share.ShareServerManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import commonality.SHARE_SERVER_TYPE;

public class ShareMessage {
	private static final Logger logger = LoggerFactory.getLogger(ShareMessage.class);
	private final static ShareMessage instance = new ShareMessage();
	public static ShareMessage getInstance() { return instance; }
	public void initialize()
	{
		ClientMessageRanking.getInstance().init();
		ShareMessageCommodity.getInstance().initialize();
		ClientMessageLegion.getInstance().init();
		ClientMessageAuth.getInstance().init();
		
		
		ShareServerManager.getInstance().regMsgProcess(Test.Msg_S_Test2.class, this, "OnTest2");
	}
	public void OnTest2(Connection connection,SHARE_SERVER_TYPE type,int id,Test.Msg_S_Test2 message)
	{
		logger.error("接收到 test2消息");
	}
}
