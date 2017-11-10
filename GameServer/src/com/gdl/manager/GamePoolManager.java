package com.gdl.manager;

import gameserver.event.GameEventDispatcher;
import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.GDL_G2C_ReqSlotPoolRe;
import gameserver.logging.LogService;
import gameserver.utils.DbMgr;

import java.util.List;

import javolution.util.FastMap;
import net.schst.EventDispatcher.Event;
import net.schst.EventDispatcher.EventListener;

import com.commons.network.websock.PlayerConnection;
import com.commons.network.websock.handler.ChatHandler;
import com.commons.thread.WorldEvents;
import com.commons.util.RandomUtil;
import com.commons.util.TimeUtil;
import com.gdl.game.GameInstanceMrg;
import commonality.Common;

import databaseshare.DatabaseSlot_pool;

public class GamePoolManager implements EventListener {
	private static GamePoolManager m_int = new GamePoolManager();
	private GamePoolManager() {}
	public static GamePoolManager getInstance() {return m_int;}
	
	List<DatabaseSlot_pool> m_pools;
	
	FastMap<Integer, Long> m_notice_cd = new FastMap<Integer, Long>().shared();
	
	private long getNextNoticeTime(long now) {
		return now + RandomUtil.RangeRandom(20 * Common.MINUTE_MILLISECOND, 40 * Common.MINUTE_MILLISECOND); 
	}
	
	public void initNoticeCd() {
		int[] gameid = {Consts.getFriutSlot(), Consts.getGoldSlot(), Consts.getFisher(), Consts.getNiuNiu()};
		int[] levelid = {1, 2, 3};
		long now = TimeUtil.GetDateTime();
		
		for (int gi : gameid) {
			for (int li : levelid) {
				int mask = GameInstanceMrg.genUnitId(gi, li, 0);
				m_notice_cd.put(mask, getNextNoticeTime(now));
			}
		}
	}
	
	public boolean testCd(int gid, int lid) {
		if (m_notice_cd.isEmpty())
			return false;
		
		long now = TimeUtil.GetDateTime();
		int mask = GameInstanceMrg.genUnitId(gid, lid, 0);
		long cdtime = m_notice_cd.get(mask);
		if (cdtime > now)
			return false;
		
		m_notice_cd.put(mask, getNextNoticeTime(now));
		return true;
	}
	
	public void init() {
		float pool_rate = Consts.getPoolRate().floatValue();
		
		for (int i = 1; i <= 3; ++i) {
			DatabaseSlot_pool p = DbMgr.getInstance().getShareDB().SelectOne(DatabaseSlot_pool.class, "game_id=? and level_id=?", 
					Consts.getFriutSlot(), i);;
			if (p != null)
				continue;

			long full_coin = Consts.getSlotsPoolCoins(i);
			p = new DatabaseSlot_pool();
			p.game_id = Consts.getFriutSlot();
			p.level_id = i;
			p.coin = Consts.getSlotsPoolInitCoins(i);
			p.reward_coin = RandomUtil.RangeRandom((long)(full_coin * pool_rate), full_coin);
			p.full_coin = full_coin;
			DbMgr.getInstance().getShareDB().Insert(p);
		}
		
		for (int i = 1; i <= 3; ++i) {
			DatabaseSlot_pool p = DbMgr.getInstance().getShareDB().SelectOne(DatabaseSlot_pool.class, "game_id=? and level_id=?", 
					Consts.getGoldSlot(), i);;
			if (p != null)
				continue;

			long full_coin = Consts.getGoldSlotsPoolCoins(i);
			p = new DatabaseSlot_pool();
			p.game_id = Consts.getGoldSlot();
			p.level_id = i;
			p.coin = Consts.getGoldSlotsPoolInitCoins(i);
			p.reward_coin = RandomUtil.RangeRandom((long)(full_coin * pool_rate), full_coin);
			p.full_coin = full_coin;
			DbMgr.getInstance().getShareDB().Insert(p);
		}
		
		for (int i = 1; i <= 3; ++i) {
			DatabaseSlot_pool p = DbMgr.getInstance().getShareDB().SelectOne(DatabaseSlot_pool.class, "game_id=? and level_id=?", Consts.getFisher(), i);
			if (p != null)
				continue;
			
			long full_coin = Consts.getFishPoolCoins(i);
			p = new DatabaseSlot_pool();
			p.game_id = Consts.getFisher();
			p.level_id = i;
			p.coin = Consts.getFishPoolInitCoins(i);
			p.reward_coin = RandomUtil.RangeRandom((long)(full_coin * 0.65), full_coin);
			p.full_coin = full_coin;
			DbMgr.getInstance().getShareDB().Insert(p);
		}
		
		{
			DatabaseSlot_pool p = DbMgr.getInstance().getShareDB().SelectOne(DatabaseSlot_pool.class, "game_id=? and level_id=?", Consts.getNiuNiu(), 1);
			if (p == null) {
				long full_coin = 10 * 10000 * 10000;
				p = new DatabaseSlot_pool();
				p.game_id = Consts.getNiuNiu();
				p.level_id = 1;
				p.coin = 10000 * 10000L;
				p.reward_coin = RandomUtil.RangeRandom((long)(full_coin * 0.65), full_coin);
				p.full_coin = full_coin;

				DbMgr.getInstance().getShareDB().Insert(p);
			}
		}

		m_pools = DbMgr.getInstance().getShareDB().Select(DatabaseSlot_pool.class, "");
		GameEventDispatcher.getInstance().addListener(WorldEvents.TIME_TICK_MINUTE, this);
		
		initNoticeCd();
	}
	
	@Override
	public void handleEvent(Event e) throws Exception {
		save();
		notice();
	}
	
	public void notice() {
		if (m_notice_cd.isEmpty())
			return ;
		
		int[] gameids = {Consts.getFriutSlot(), Consts.getGoldSlot(), Consts.getFisher(), Consts.getNiuNiu()};
		int[] levelids = {1, 2, 3};

		for (int gameid : gameids) {
			for (int levelid : levelids) {
				if (!testCd(gameid, levelid))
					continue;
				
				DatabaseSlot_pool sp = getTargetpoolCoin(gameid, levelid);
				if (sp == null)
					continue;
				
				if (sp.coin <= sp.reward_coin)
					continue;
				
				if (gameid == Consts.getFriutSlot())
					ChatHandler.newbanner(34 + levelid, sp.coin);
				else if (gameid == Consts.getGoldSlot())
					ChatHandler.newbanner(37 + levelid, sp.coin);
				else if (gameid == Consts.getFisher())
					ChatHandler.newbanner(40 + levelid, sp.coin);
			}
		}
	}
	
	public void save() {
		for (DatabaseSlot_pool pp : m_pools) {
			synchronized (pp) {
				pp.save();
			}
		}
	}
	
	private DatabaseSlot_pool getTargetPool(int gameid, int levelid) {
		for (DatabaseSlot_pool pp : m_pools)
		{
			if (pp.game_id == gameid && pp.level_id == levelid)
				return pp;
		}
		
		return null;
	}
	
	public DatabaseSlot_pool getTargetpoolCoin(int gameid, int levelid) {
		DatabaseSlot_pool pool = getTargetPool(gameid, levelid);
		if (pool == null)
			return null;
		
		return pool;
	}
	
	public void addPoolReward(int gameid, int levelid, long money, float rate) {
		DatabaseSlot_pool pool = getTargetPool(gameid, levelid);
		if (pool == null)
			return ;
		
		synchronized (pool) {
			if (pool.coin < pool.reward_coin)
				pool.coin += (long)(money * rate);
			//LogService.sysErr(1, "gid: " + gameid + ", levelid: " + levelid + ", money: " + money
			//			+ ", rate: " + rate + ", ov: " + (long)(money * rate) + ", coin: " + pool.coin + ", pool: " + pool.reward_coin,  1);
		}
	}
	
	public long checkPoolReward(int gameid, int levelid) {
		return checkPoolReward(gameid, levelid, true);
	}
	
	public long checkPoolReward(int gameid, int levelid, double dec_rate, boolean dec) {
		DatabaseSlot_pool pool = getTargetPool(gameid, levelid);
		if (pool == null) {
			LogService.sysErr(0, "gameid: " + gameid + "level: " + levelid, levelid);
			return 0L;
		}
		
		synchronized (pool) {
			long co = (long)(pool.coin * dec_rate);
			if (dec) {
				pool.coin = (long)(pool.coin * (1 - dec_rate));
				if (gameid == Consts.getNiuNiu()) {
					if (pool.coin < 10000 * 10000) {
						pool.coin = 10000 * 10000L;
					}
				}
				pool.save();
			}

			return co;
		}
	}
	
	public long checkPoolReward(int gameid, int levelid, boolean dec) {
		if (levelid >= 2)
			return 0;
		
		DatabaseSlot_pool pool = getTargetPool(gameid, levelid);
		if (pool == null) {
			LogService.sysErr(0, "gameid: " + gameid + "level: " + levelid, levelid);
			return 0L;
		}
		
		synchronized (pool) {
			if (pool.coin >= pool.reward_coin) {
				long co = pool.coin;
				
				if (dec) {
					float pool_rate = Consts.getPoolRate().floatValue();
					long full_coin = 0L;
					if (gameid == Consts.getFisher()) {
						full_coin = Consts.getFishPoolCoins(levelid);
					} else {
						full_coin = Consts.getSlotsPoolCoins(levelid);
					}
					
					pool.coin = 0L;
					pool.reward_coin = RandomUtil.RangeRandom((long)(full_coin * pool_rate), full_coin);
					pool.save();
				}
				
				return co;
			}
			else {
				//LogService.sysErr(0, "gameid: " + gameid + "level: " + levelid + ", coin: " + pool.coin + ", reward_coin: " + pool.reward_coin, levelid);
				return 0L;
			}
		}
	}
	
	public void reqPoolCoin(PlayerConnection con, int gid, int lid) {
		DatabaseSlot_pool coin = GamePoolManager.getInstance().getTargetpoolCoin(gid, lid);
		GDL_G2C_ReqSlotPoolRe re = new GDL_G2C_ReqSlotPoolRe();
		re.setGameId(gid);
		re.setLevelId(lid);
		re.setPoolCoin(coin.coin);
		re.setPoolCoinTotal(coin.reward_coin);
		con.send(re);
	}
}
