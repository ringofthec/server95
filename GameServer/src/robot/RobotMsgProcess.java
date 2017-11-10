package robot;

import gameserver.network.IOServer;
import gameserver.network.protos.common.ProLegion.Msg_G2C_LegionInfo;
import gameserver.network.protos.game.ProBuild;
import gameserver.network.protos.game.ProGameInfo.Msg_G2C_InfoFinished;
import gameserver.network.protos.game.ProLogin;
import gameserver.network.server.connection.Connection;

public class RobotMsgProcess {
	private static RobotMsgProcess instance = new RobotMsgProcess();
	public static RobotMsgProcess getInstance() { return instance; }
	
	public void initMsgProcess() {
		IOServer.getInstance().regMsgProcess(ProLogin.Msg_G2C_AskLogin.class, this, "OnAskLogin",false);
		IOServer.getInstance().regMsgProcess(ProLogin.Msg_G2C_CreatePlayer.class, this, "OnCreatePlayer",false);
		IOServer.getInstance().regMsgProcess(ProLogin.Msg_G2C_AskEnter.class, this, "OnAskEnter",false);
		IOServer.getInstance().regMsgProcess(ProBuild.Msg_G2C_AskBuildingInfo.class, this, "OnBuildInfo",false);
		IOServer.getInstance().regMsgProcess(Msg_G2C_InfoFinished.class, this, "OnInfoFinish",false);
		IOServer.getInstance().regMsgProcess(Msg_G2C_LegionInfo.class, this, "OnLegionInfo",false);
	}
	
	public void OnLegionInfo(Connection connection, Msg_G2C_LegionInfo message) {
		connection.player.OnAskPlayerInfo(message);
	}
	
	public void OnInfoFinish(Connection connection, Msg_G2C_InfoFinished message) {
		connection.player.OnInfoFinish();
	}
	
	public void OnAskEnter(Connection connection, ProLogin.Msg_G2C_AskEnter message) {
		connection.player.OnAskEnter(message);
	}
	
	public void OnCreatePlayer(Connection connection, ProLogin.Msg_G2C_CreatePlayer message) {
		connection.player.OnCreatePlayer(message);
	}
	
	public void OnAskLogin(Connection connection, ProLogin.Msg_G2C_AskLogin message) {
		connection.player.OnAskLogin(message);
	}
	
	public void OnBuildInfo(Connection connection, ProBuild.Msg_G2C_AskBuildingInfo message) {
		connection.player.OnBuildInfo(message);
	}
}
