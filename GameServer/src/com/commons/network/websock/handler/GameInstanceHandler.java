package com.commons.network.websock.handler;

import gameserver.jsonprotocol.GDL_C2G_EnterGame;
import gameserver.jsonprotocol.GDL_C2G_LeaveGame;
import gameserver.jsonprotocol.GDL_C2G_ReqOnlinePlayerNumber;
import gameserver.jsonprotocol.GDL_G2C_ReqOnlinePlayerNumberRe;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConManager;
import com.commons.network.websock.PlayerConnection;
import com.gdl.data.PlayerData;
import com.gdl.game.BaijialeGameInstanceManager;
import com.gdl.game.FishGameInstanceManager;
import com.gdl.game.FriutSlotsGameInstanceManager;
import com.gdl.game.GameInstanceMrg;
import com.gdl.game.GoldSlotsGameInstanceManager;
import com.gdl.game.NiuniuGameInstanceManager;

public class GameInstanceHandler {
	private final static GameInstanceHandler instance = new GameInstanceHandler();
	public static GameInstanceHandler getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(GameInstanceHandler.class);
	
	Method[] enterMethods = new Method[3];
	Method[] leaveMethods = new Method[3];
	
	public void init() {
		HandlerManager.getInstance().pushNornalHandler(GameInstanceHandler.class, this, 
				"OnEnterGame", new GDL_C2G_EnterGame());
		HandlerManager.getInstance().pushNornalHandler(GameInstanceHandler.class, this, 
				"OnLeaveGame", new GDL_C2G_LeaveGame());
		HandlerManager.getInstance().pushNornalHandler(GameInstanceHandler.class, this, 
				"OnReqOnlinePlayerNumber", new GDL_C2G_ReqOnlinePlayerNumber());
	}
	
	public void OnReqOnlinePlayerNumber(PlayerConnection con, GDL_C2G_ReqOnlinePlayerNumber message) {
		int num = PlayerConManager.getInstance().getPlayerNum();
		GDL_G2C_ReqOnlinePlayerNumberRe re = new GDL_G2C_ReqOnlinePlayerNumberRe();
		re.setNum(num + 214);
		con.send(re);
	}
	
	public void OnEnterGame(PlayerConnection con, GDL_C2G_EnterGame message) {
		int gameid = message.getGameId();
		int levelid = message.getGameLevel();
		
		if (!GameInstanceMrg.isRightGameId(gameid))
			return ;
		
		if (!GameInstanceMrg.isRightLevelId(gameid, levelid))
			return ;
		
		PlayerData pData = con.getPlayer();
		int uid = pData.getGameUID();
		if (uid != 0) {
			logger.error("player{} will join {}-{}, but already in {}-{}", 
					pData.getPlayerId(), gameid, levelid, 
					GameInstanceMrg.getGameId(uid), GameInstanceMrg.getLevelId(uid));
			return ;
		}
		
		switch (gameid) {
			case 1:
				FriutSlotsGameInstanceManager.getInstance().joinGame(con, levelid);
				break;
				
			case 2:
				GoldSlotsGameInstanceManager.getInstance().joinGame(con, levelid);
				break;
				
			case 10:
				FishGameInstanceManager.getInstance().joinGame(con, levelid);
			break;
			
			case 15:
				NiuniuGameInstanceManager.getInstance().joinGame(con, levelid);
			break;
			
			case 20:
				BaijialeGameInstanceManager.getInstance().joinGame(con, levelid);
			break;
		}
	}
	
	public void OnLeaveGame(PlayerConnection con, GDL_C2G_LeaveGame message) {
		int uid = con.getPlayer().getGameUID();
		if (uid == 0)
			return ;
		
		int gameid = GameInstanceMrg.getGameId(uid);
		int levelid = GameInstanceMrg.getLevelId(uid);
		if (!GameInstanceMrg.isRightGameId(gameid))
			return ;
		
		switch (gameid) {
		case 1:
			FriutSlotsGameInstanceManager.getInstance().leaveGame(con, uid, gameid, levelid);
			break;
			
		case 2:
			GoldSlotsGameInstanceManager.getInstance().leaveGame(con, uid, gameid, levelid);
			break;
			
		case 10:
			FishGameInstanceManager.getInstance().leaveGame(con, uid, gameid, levelid);
		break;
		
		case 15:
			NiuniuGameInstanceManager.getInstance().leaveGame(con, uid, gameid, levelid, false);
		break;
		
		case 20:
			BaijialeGameInstanceManager.getInstance().leaveGame(con, uid, gameid, levelid, false);
		break;
	}
	}
}
