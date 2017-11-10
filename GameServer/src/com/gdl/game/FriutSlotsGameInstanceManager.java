package com.gdl.game;

import gameserver.jsonprotocol.Consts;

import java.util.ArrayList;
import java.util.List;

import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.network.websock.JsonPacket;
import com.commons.network.websock.PlayerConManager;
import com.commons.network.websock.PlayerConnection;
import com.commons.network.websock.handler.ChatHandler;
import com.commons.util.RandomUtil;

public class FriutSlotsGameInstanceManager {
	private final static FriutSlotsGameInstanceManager instance = new FriutSlotsGameInstanceManager();
	public static FriutSlotsGameInstanceManager getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(FriutSlotsGameInstanceManager.class);
	
	// <levelid, <uid, info>>
	FastMap<Integer, FastMap<Integer, SlotsGameInfo> > m_all_games = 
			new FastMap<Integer, FastMap<Integer, SlotsGameInfo> >();
	List<Long> all_player = new ArrayList<>();
	
	public SlotsGameInfo getPlayerSlotsGameInfo(int levelid, int uid) {
		FastMap<Integer, SlotsGameInfo> lists = m_all_games.get(levelid);
		if (lists == null)
			return null;
		
		return lists.get(uid);
	}
	
	public void broadAllPlayer(JsonPacket msg) {
		synchronized (FriutSlotsGameInstanceManager.class) {
			PlayerConManager.getInstance().broadcastMsg1(msg, all_player);
		}
	}
	
	public void joinGame(PlayerConnection con, int levelId) {
		SlotsGameInfo temp = null;
		synchronized (FriutSlotsGameInstanceManager.class) {
			FastMap<Integer, SlotsGameInfo> lists = m_all_games.get(levelId);
			if (lists == null) {
				lists = new FastMap<Integer, SlotsGameInfo>();
				m_all_games.put(levelId, lists);
			}
			
			for (SlotsGameInfo fgi : lists.values()) {
				if (fgi.isFull(ChatHandler.isRobot(con.getPlayerId())))
					continue;
				
				temp = fgi;
				break;
			}
			
			// 没有找到，需要新建一个
			if (temp == null) {
				int uid = GameInstanceMrg.genUnitId( Consts.getFriutSlot(), 
						levelId, 
						RandomUtil.RangeRandom(0, Short.MAX_VALUE) );
				temp = new SlotsGameInfo(uid);
				lists.put(uid, temp);
			}

			synchronized (temp) {
				temp.joinGame(con, temp.getUid());
			}
			all_player.add(con.getPlayerId());
			
			logger.error("frist slots has {} player, instance has {}", all_player.size());
		}
	}
	
	public void leaveGame(PlayerConnection con, int uid, int gid, int lid) {
		synchronized (FriutSlotsGameInstanceManager.class) {
			FastMap<Integer, SlotsGameInfo> tempmap = m_all_games.get(lid);
			if (tempmap == null)
				return ;
			
			SlotsGameInfo temp = tempmap.get(uid);
			if (temp == null)
				return ;
			
			synchronized (temp) {
				temp.leaveGame(con);
				if (temp.isEmpty()) {
					tempmap.remove(uid);
				}
			}
			all_player.remove(con.getPlayerId());
		}
	}
}
