package gameserver.network.message.management;

import gameserver.http.HttpProcessManager;
import gameserver.messageprocess.base.ManagementMessageBase;
import gameserver.network.protos.common.CommonMessage;
import gameserver.network.protos.common.ProLegion;
import gameserver.network.server.connection.Connection;
import gameserver.network.server.connection.ConnectionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ManagementMessage extends ManagementMessageBase {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ManagementMessageBase.class);
	private final static ManagementMessage instance = new ManagementMessage();
	public static ManagementMessage getInstance() { return instance; }
	@Override
	protected void initialize_impl()
	{
		HttpProcessManager.getInstance().regMsgProcess(ProLegion.Msg_C2G_PeopleManage.class, this, "OnPeopleDo");
		HttpProcessManager.getInstance().regMsgProcess(CommonMessage.Msg_S_RepeatLogin.class, this, "OnRepeatLogin");
	}

	public void OnPeopleDo(ProLegion.Msg_C2G_PeopleManage msg)
	{
		Connection connection = ConnectionManager.GetInstance().GetConnection(msg.getBePlayerId());
		if (connection == null)
			return;
	}
	public void OnRepeatLogin(CommonMessage.Msg_S_RepeatLogin message)
	{
		ConnectionManager.GetInstance().destroyConnect(message.getPlayerId(),
				CommonMessage.Msg_S_RepeatLogin.newBuilder().build());
	}
}
