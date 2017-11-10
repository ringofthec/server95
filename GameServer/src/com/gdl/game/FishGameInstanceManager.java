package com.gdl.game;

import gameserver.event.GameEventDispatcher;
import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.GDL_G2C_LeaveGameRe;
import javolution.util.FastMap;
import net.schst.EventDispatcher.Event;
import net.schst.EventDispatcher.EventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.network.websock.PlayerConnection;
import com.commons.network.websock.handler.ChatHandler;
import com.commons.thread.WorldEvents;
import com.commons.util.RandomUtil;
import com.commons.util.TimeUtil;

public class FishGameInstanceManager implements EventListener {
	private final static FishGameInstanceManager instance = new FishGameInstanceManager();
	public static FishGameInstanceManager getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(FishGameInstanceManager.class);
	
	// <levelid, <uid, info>>
	FastMap<Integer, FastMap<Integer, FishGameInfo> > m_all_games = 
			new FastMap<Integer, FastMap<Integer, FishGameInfo> >();
	
	public void init() {
		GameEventDispatcher.getInstance().addListener(WorldEvents.TIME_TICK_5_SECOND, this);
	}
	
	@Override
	public void handleEvent(Event e) throws Exception {
		long begin_try_switch = TimeUtil.GetDateTime();
		int count = 0;
		synchronized (FishGameInstanceManager.class) {
			for (FastMap<Integer, FishGameInfo> mps : m_all_games.values()) {
				for (FishGameInfo fgi : mps.values()) {
					fgi.trySwitch();
					count++;
				}
			}
		}
		
		logger.info("all fish instance count = {}, cost time = {}", count, TimeUtil.GetDateTime() - begin_try_switch);
	}

	public void joinGame(PlayerConnection con, int levelId) {
		FishGameInfo temp = null;
		
		synchronized (FishGameInstanceManager.class) {
			FastMap<Integer, FishGameInfo> lists = m_all_games.get(levelId);
			if (lists == null) {
				lists = new FastMap<Integer, FishGameInfo>();
				m_all_games.put(levelId, lists);
			}
			
			for (FishGameInfo fgi : lists.values()) {
				if (fgi.isFull(ChatHandler.isRobot(con.getPlayerId())))
					continue;
				
				temp = fgi;
				break;
			}
			
			if (temp == null) {
				int uid = GameInstanceMrg.genUnitId(Consts.getFisher(), levelId, RandomUtil.RangeRandom(0, Short.MAX_VALUE) );
				temp = new FishGameInfo();
				temp.setGameUID(uid);
				temp.beginGame();
				lists.put(uid, temp);
			}
		}
		
		// 上面是操作全局数据，必须要锁
		// 下面只是操作部分玩家的共享数据，所以先把全局数据解开，单独锁住共享数据就行
		
		synchronized (temp) {
			temp.joinGame(con, temp.getInstanceId());
		}
	}
	
	// 注意，这个接口返回的是部分玩家的共享数据，外部要适当的锁
	public FishGameInfo getPlayerFishInstance(PlayerConnection con) {
		int uid = con.getPlayer().getGameUID();
		int levelid = GameInstanceMrg.getLevelId(uid);
		FastMap<Integer, FishGameInfo> tempmap = m_all_games.get(levelid);
		if (tempmap == null)
			return null;
		
		return tempmap.get(uid);
	}
	
	public FishGameInfo getPlayerFishInstance(int uid) {
		int levelid = GameInstanceMrg.getLevelId(uid);
		FastMap<Integer, FishGameInfo> tempmap = m_all_games.get(levelid);
		if (tempmap == null)
			return null;
		
		return tempmap.get(uid);
	}
	
	public void leaveGame(PlayerConnection con, int uid, int gid, int lid) {
		// 操作全局数据
		synchronized (FishGameInstanceManager.class) {
			FastMap<Integer, FishGameInfo> tempmap = m_all_games.get(lid);
			if (tempmap == null)
				return ;
			
			FishGameInfo temp = tempmap.get(uid);
			if (temp == null)
				return ;
			
			synchronized (temp) {
				temp.leaveGame(con);
				if (temp.isEmpty()) {
					tempmap.remove(uid);
					if (temp.getSceneType() == 2) {
						FishGameMrg.getInstance().leavePoolScene(lid);
					}
				}
			}
		}
		
		con.getPlayer().getItemData().syncItemV(con, Consts.getCOIN_ID(), 0);
		con.getPlayer().getItemData().syncItemV(con, Consts.getGOLD_ID(), 0);
		con.getPlayer().getItemData().syncItemV(con, Consts.getEXP_ID(), 0);
		
		GDL_G2C_LeaveGameRe re = new GDL_G2C_LeaveGameRe();
		con.send(re);
	}
}
