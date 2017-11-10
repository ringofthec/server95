package gameserver.network.message.game;

import gameserver.connection.attribute.ConBuildAttr;
import gameserver.connection.attribute.ConPlayerAttr;
import gameserver.network.IOServer;
import gameserver.network.protos.game.ProGameInfo;
import gameserver.network.protos.game.ProSee;
import gameserver.network.protos.game.ProSee.Msg_G2C_SeePlayerInfo;
import gameserver.network.server.connection.Connection;
import gameserver.network.server.connection.ConnectionManager;
import gameserver.utils.DbMgr;

import java.util.List;

import database.DatabaseBuild;
import database.DatabasePlayer;
import databaseshare.DatabasePvp_match;

public class ClientMessageSee {
	private final static ClientMessageSee instance = new ClientMessageSee();
	public static ClientMessageSee getInstance() { return instance; }
	public void initialize()
	{
		IOServer.getInstance().regMsgProcess(ProSee.Msg_C2G_ReqPlayerSeeInfo.class, this, "OnSeePlayer");
	}

	// 飞机升级
	public void OnSeePlayer(Connection connection, ProSee.Msg_C2G_ReqPlayerSeeInfo message) throws Exception{
		long playerid = message.getPlayerId();
		
		Connection playercon = ConnectionManager.GetInstance().GetConnection(playerid);
		if (playercon != null && playercon.isInit()) {
			// 玩家在线就取内存数据
			connection.sendReceiptMessage(playercon.getPlayer().buildSeeData());
			return ;
		}
		
		ProSee.Msg_G2C_SeePlayerInfo.Builder seemsg = Msg_G2C_SeePlayerInfo.newBuilder();
		{
			DatabasePlayer dbPlayer = DbMgr.getInstance().
					getDbByPlayerId(playerid).SelectOne(DatabasePlayer.class, "player_id=?", playerid);
			DatabasePvp_match dbPvpInfo = DbMgr.getInstance()
					.getShareDB().SelectOne(DatabasePvp_match.class, "player_id=?", playerid);
			if (dbPlayer == null || dbPvpInfo == null)
				return ;
			
			ProGameInfo.Msg_G2C_AskPlayerInfo msg = ConPlayerAttr.buildPlayerInfo(dbPlayer, dbPvpInfo);
			seemsg.setPlayerInfo(msg);
			seemsg.setExp(dbPvpInfo.exp);
			seemsg.setFightValue(dbPlayer.fight_value);
			
			List<DatabaseBuild> builds = DbMgr.getInstance().getDbByPlayerId(playerid).Select(DatabaseBuild.class, "player_id=?", playerid);
			for (DatabaseBuild bui : builds)
				seemsg.addBuildInfo(ConBuildAttr.GetProtoData(bui));
		}
		
		connection.sendReceiptMessage(seemsg.build());
	}
}
