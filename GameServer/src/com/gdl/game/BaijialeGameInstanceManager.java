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

public class BaijialeGameInstanceManager implements EventListener {
	private final static BaijialeGameInstanceManager instance = new BaijialeGameInstanceManager();
	public static BaijialeGameInstanceManager getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(BaijialeGameInstanceManager.class);
	
	// <levelid, <uid, info>>
	FastMap<Integer, FastMap<Integer, BaijialeGameInfo> > m_all_games = 
			new FastMap<Integer, FastMap<Integer, BaijialeGameInfo> >();
	
	public void init() {
		GameEventDispatcher.getInstance().addListener(WorldEvents.TIME_TICK_SECOND, this);
	}
	
	@Override
	public void handleEvent(Event e) throws Exception {
		checkStatus();
	}

	public void checkStatus() {
		synchronized (BaijialeGameInstanceManager.class) {
			for (FastMap<Integer, BaijialeGameInfo> mps : m_all_games.values()) {
				for (BaijialeGameInfo fgi : mps.values()) {
					fgi.trySwitchState();
				}
			}
		}
		
		//logger.info("all niuniu instance count = {}, cost time = {}", count, TimeUtil.GetDateTime() - begin_try_switch);
	}

	public void joinGame(PlayerConnection con, int levelId) {
		BaijialeGameInfo temp = null;
		
		synchronized (BaijialeGameInstanceManager.class) {
			FastMap<Integer, BaijialeGameInfo> lists = m_all_games.get(levelId);
			if (lists == null) {
				lists = new FastMap<Integer, BaijialeGameInfo>();
				m_all_games.put(levelId, lists);
			}
			
			for (BaijialeGameInfo fgi : lists.values()) {
				if (fgi.isFull(ChatHandler.isRobot(con.getPlayerId())))
					continue;
				
				temp = fgi;
				break;
			}
			
			if (temp == null) {
				int uid = GameInstanceMrg.genUnitId(Consts.getBaijiale(), 
						levelId, RandomUtil.RangeRandom(0, Short.MAX_VALUE) );
				temp = new BaijialeGameInfo();
				temp.setGameUID(uid);
				temp.beginGame(con);
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
	public BaijialeGameInfo getPlayerBjlInstance(PlayerConnection con) {
		int uid = con.getPlayer().getGameUID();
		int levelid = GameInstanceMrg.getLevelId(uid);
		FastMap<Integer, BaijialeGameInfo> tempmap = m_all_games.get(levelid);
		if (tempmap == null)
			return null;
		
		return tempmap.get(uid);
	}
	
	public BaijialeGameInfo getPlayerNiuniuInstance(int uid) {
		int levelid = GameInstanceMrg.getLevelId(uid);
		FastMap<Integer, BaijialeGameInfo> tempmap = m_all_games.get(levelid);
		if (tempmap == null)
			return null;
		
		return tempmap.get(uid);
	}
	
	public void leaveGame(PlayerConnection con, int uid, int gid, int lid, boolean isDisconnect) {
		// 操作全局数据
		synchronized (BaijialeGameInstanceManager.class) {
			FastMap<Integer, BaijialeGameInfo> tempmap = m_all_games.get(lid);
			if (tempmap == null)
				return ;
			
			BaijialeGameInfo temp = tempmap.get(uid);
			if (temp == null)
				return ;
			
			// 不在下注状态可以正常离开
			synchronized (temp) {
				temp.leaveGame(con);
				if (temp.isEmpty()) {
					tempmap.remove(uid);
				}
			}
			
			GDL_G2C_LeaveGameRe re = new GDL_G2C_LeaveGameRe();
			con.send(re);
		}
	}
}
