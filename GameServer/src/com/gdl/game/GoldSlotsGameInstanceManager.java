package com.gdl.game;

import gameserver.jsonprotocol.Consts;
import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.network.websock.PlayerConnection;
import com.commons.network.websock.handler.ChatHandler;
import com.commons.util.RandomUtil;

public class GoldSlotsGameInstanceManager {
	private final static GoldSlotsGameInstanceManager instance = new GoldSlotsGameInstanceManager();
	public static GoldSlotsGameInstanceManager getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(GoldSlotsGameInstanceManager.class);
	
	// <levelid, <uid, info>>
	FastMap<Integer, FastMap<Integer, SlotsGameInfo> > m_all_games = 
			new FastMap<Integer, FastMap<Integer, SlotsGameInfo> >();
	
	public SlotsGameInfo getPlayerSlotsGameInfo(int levelid, int uid) {
		FastMap<Integer, SlotsGameInfo> lists = m_all_games.get(levelid);
		if (lists == null)
			return null;
		
		return lists.get(uid);
	}
	
	public void joinGame(PlayerConnection con, int levelId) {
		SlotsGameInfo temp = null;
		
		synchronized (GoldSlotsGameInstanceManager.class) {
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
			
			if (temp == null) {
				int uid = GameInstanceMrg.genUnitId(Consts.getGoldSlot(), 
						levelId, 
						RandomUtil.RangeRandom(0, Short.MAX_VALUE) );
				temp = new SlotsGameInfo(uid);
				lists.put(uid, temp);
			}
		
			synchronized (temp) {
				temp.joinGame(con, temp.getUid());
			}
			
			logger.error("gold slots has {} player, instance has {}", 0);
		}
	}
	
	public void leaveGame(PlayerConnection con, int uid, int gid, int lid) {
		synchronized (GoldSlotsGameInstanceManager.class) {
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
		}
	}
}
