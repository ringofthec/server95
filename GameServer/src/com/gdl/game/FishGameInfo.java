package com.gdl.game;

import gameserver.config.GameConfig;
import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.DropItem;
import gameserver.jsonprotocol.FishGamePlayerInfo;
import gameserver.jsonprotocol.FishGameStatus;
import gameserver.jsonprotocol.GDL_G2C_EnterGameRe;
import gameserver.jsonprotocol.GDL_G2C_FishTaskComplete;
import gameserver.jsonprotocol.GDL_G2C_PlayerJoinGame;
import gameserver.jsonprotocol.GDL_G2C_PlayerLeaveGame;
import gameserver.jsonprotocol.GDL_G2C_SwitchScene;
import gameserver.jsonprotocol.KillFishes;
import gameserver.jsonprotocol.SummoneSync;

import java.util.List;

import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_GFSkill;

import com.commons.network.websock.JsonPacket;
import com.commons.network.websock.PlayerConManager;
import com.commons.network.websock.PlayerConnection;
import com.commons.network.websock.handler.ChatHandler;
import com.commons.util.RandomUtil;
import com.commons.util.TimeUtil;
import com.gdl.data.PlayerData;
import com.gdl.game.FishGameConfig.DropRange;
import com.gdl.game.FishGameConfig.FishConfig;
import com.gdl.game.FishGameConfig.GroupConInfo;
import com.gdl.game.FishGameConfig.SceneConfig;
import com.gdl.manager.GamePoolManager;

public class FishGameInfo {
	
	private final static Logger logger = LoggerFactory.getLogger(FishGameInfo.class);
	
	
	int gameUid;
	long begin_time;
	long last_frozen_time;
	long frozen_times;
	int m_scene_id;
	SceneConfig m_curSceneConfig;
	int scene_step;
	int m_boss_complete_type = 0;
	boolean is_task_complete = false;
	
	public static class FishInfo {
		int hp;
		int temp_id;
		int idx;
		int group_id;
		FishConfig config;
		
		int bron_time;    // 出生时间 毫秒
		int leave_time;  // 过期时间 毫秒
	
		public int isExists(int delte_time) {
			if (delte_time >= bron_time && delte_time <= leave_time)
				return 0;
			
			if (delte_time < bron_time)
				return delte_time - bron_time;
			else
				return delte_time - leave_time;
		}
		
		public int getRate() {
			for (DropRange dr : config.drop) {
				if (dr.id == Consts.getCOIN_ID())
					return dr.m;
			}
			
			return 0;
		}
	}
	FastMap<Integer, FishInfo> m_all_fish = new FastMap<Integer, FishGameInfo.FishInfo>();
	FastMap<Integer, GroupConInfo> m_all_group = new FastMap<Integer, GroupConInfo>();
	
	public static class PlayerInfo {
		long player_id;
		long join_time;
		long speed_end_time;
		int pos;
		// 1  2
		// 0  3
		long money_calc;
		int batteryId;
		int bulletId;
		boolean testTask = true;
		FastMap<Integer, Integer> taskFishs;
		FastMap<Integer, Integer> bulletInfos = new FastMap<Integer, Integer>();
	}
	FastMap<Long, PlayerInfo> player_infos = new FastMap<Long, FishGameInfo.PlayerInfo>();
	
	public void addBullet(long pid, int bulletType) {
		PlayerInfo pi = player_infos.get(pid);
		if (pi != null) {
			int temp_num = 0;
			if (pi.bulletInfos.containsKey(bulletType))
				temp_num = pi.bulletInfos.get(bulletType);
			
			temp_num ++;
			pi.bulletInfos.put(bulletType, temp_num);
		}
	}
	
	public boolean costBullet(long pid, int bulletType, int num) {
		PlayerInfo pi = player_infos.get(pid);
		if (pi != null) {
			int temp_num = 0;
			if (pi.bulletInfos.containsKey(bulletType))
				temp_num = pi.bulletInfos.get(bulletType);
			
			if (temp_num < num)
				return false;
			
			temp_num -= num;
			pi.bulletInfos.put(bulletType, temp_num);
			return true;
		}
		
		return false;
	}
	
	public void cachePlayerMoney(long pid, long coin) {
		PlayerInfo pi = player_infos.get(pid);
		if (pi != null) {
			pi.money_calc += coin;
		}
	}
	
	public void flushButtetId(long pid, int bid) {
		PlayerInfo pi = player_infos.get(pid);
		if (pi != null) {
			pi.bulletId = bid;
		}
	}
	
	public void flushBatteryId(long pid, int buid) {
		PlayerInfo pi = player_infos.get(pid);
		if (pi != null) {
			pi.batteryId = buid;
		}
	}
	
	public long getPlayerCacheMoney(long pid) {
		if (player_infos.containsKey(pid))
			return player_infos.get(pid).money_calc;
		
		return 0L;
	}
	
	private void tryCompleteTask(PlayerConnection con, PlayerInfo pi, FishInfo fs, int delte_time) {
		if (m_curSceneConfig.scene.type != 4)
			return ;
		
		if (delte_time >= m_curSceneConfig.task.duration * 1000)
			return ;
		
		if (is_task_complete)
			return ;
		
		if (!pi.testTask)
			return ;
		
		if (pi.taskFishs == null)
			pi.taskFishs = new FastMap<Integer, Integer>();
		
		int c = 0;
		if (pi.taskFishs.containsKey(fs.temp_id))
			c = pi.taskFishs.get(fs.temp_id);
		c += 1;
		pi.taskFishs.put(fs.temp_id, c);
		
		boolean isComplete = true;
		int count =  m_curSceneConfig.task.fishes_type.size();
		for (int i = 0; i < count; ++i) {
			int ft = m_curSceneConfig.task.fishes_type.get(i);
			int fc = m_curSceneConfig.task.fishes_count.get(i);
			
			if (!pi.taskFishs.containsKey(ft)) {
				isComplete = false;
				break;
			}
			
			if (pi.taskFishs.get(ft) < fc) {
				isComplete = false;
				break;
			}
		}
		
		if (isComplete) {
			is_task_complete = true;
			pi.testTask = false;
			GDL_G2C_FishTaskComplete com = new GDL_G2C_FishTaskComplete();
			com.setPlayerId(con.getPlayerId());
			broadcast(com);
			
			con.getPlayer().playerFishTaskComplete();
			
			con.getPlayer().getItemData().addItem(con, Consts.getCOIN_ID(), m_curSceneConfig.task.reward_coin, Consts.getItemEventFishTaskComplete());
			con.getPlayer().getItemData().addItem(con, Consts.getGOLD_ID(), m_curSceneConfig.task.reward_gold, Consts.getItemEventFishTaskComplete());
		}
	}
	
	public int getInstanceId() {
		return gameUid;
	}

	public void setGameUID(int gameUid) {
		this.gameUid = gameUid;
	}
	
	public int getSceneType() {
		return this.m_curSceneConfig.scene.type;
	}
	
	public int getLevelId() {
		return GameInstanceMrg.getLevelId(this.gameUid);
	}
	
	public void broadcast(JsonPacket msg) {
		PlayerConManager.getInstance().broadcastMsg(msg, player_infos.keySet());
	}
	
	private int getFreePos() {
		for (int i =0 ; i<4; ++i) {
			boolean findfree = true;
			for (PlayerInfo pi : player_infos.values()) {
				if (pi.pos == i) {
					findfree = false;
					break;
				}
			}
			
			if (findfree)
				return i;
		}
		
		return -1;
	}
	
	public void swtichScene(int sid, boolean begin) {
		SceneConfig sc = FishGameConfig.getInstance().getSceneConfig(sid);
		if (sc == null)
			return ;
		
		// 如果是从将奖池切换回来的话，需要离开
		if (FishGameConfig.getInstance().isPoolScene(m_scene_id)) {
			FishGameMrg.getInstance().leavePoolScene(getLevelId());
		}
		
		begin_time = TimeUtil.GetDateTime();
		last_frozen_time = 0;
		frozen_times = 0;
		m_scene_id = sid;
		m_curSceneConfig = sc;
		m_boss_complete_type = 0;
		m_all_fish.clear();
		is_task_complete = false;
		int fish_count = sc.scene.ids.size();
		for (int i = 0; i < fish_count; ++i ) {
			FishInfo fi = new FishInfo();
			fi.temp_id = sc.scene.ids.get(i);
			fi.idx = sc.scene.index.get(i);
			fi.group_id = sc.scene.gids.get(i);
			fi.config = FishGameConfig.getInstance().getFishConfig(fi.temp_id);
			fi.hp = fi.config.hp;
			
			fi.bron_time = sc.scene.born.get(i);
			fi.leave_time = sc.scene.leave.get(i);
			m_all_fish.put(fi.idx, fi);
		}
		
		m_all_group.clear();
		for (GroupConInfo gci : sc.groups) {
			m_all_group.put(gci.index, gci);
		}
		
		for (PlayerInfo pi : player_infos.values()) {
			pi.testTask = true;
			pi.taskFishs = null;
		}
		
		if (!begin) {
			GDL_G2C_SwitchScene ss = new GDL_G2C_SwitchScene();
			ss.setSceneId(m_scene_id);
			broadcast(ss);
		}
	}
	
	public void checkBoss(PlayerConnection con) {
		if (m_curSceneConfig.scene.type != m_boss_complete_type)
			return ;
		
		if (m_boss_complete_type == 0)
			return ;
		
		if (m_boss_complete_type == 2) {
			long coin = GamePoolManager.getInstance().checkPoolReward(Consts.getFisher(), getLevelId(), true);
			if (coin <= 0) {
				return ;
			}
			
			con.getPlayer().getItemData().addItem(con, Consts.getCOIN_ID(), coin, Consts.getItemEventFishPoolBoss());
			int scene_id = FishGameConfig.getInstance().getNextScene(getLevelId(), scene_step);
			swtichScene(scene_id, false);
			ChatHandler.newbanner(8 + getLevelId(), con.getPlayer().getName(), con.getPlayerId(), coin);
		} else if (m_boss_complete_type == 3) {
			scene_step++;
			int scene_id = FishGameConfig.getInstance().getNextScene(getLevelId(), scene_step);
			swtichScene(scene_id, false);
		}
		
	}
	
	private void fishGoDie(PlayerConnection con, FishInfo fi, int coin_rate,
			List< DropItem > dropinfo, int delte_time) {
		PlayerData pdata = con.getPlayer();
		CalcFishDrop(con, fi, coin_rate, dropinfo);
		m_all_fish.remove(fi.idx);
		pdata.playerCatchFish(fi.config.t);
		
		tryCompleteTask(con, player_infos.get(con.getPlayerId()), fi, delte_time);
		
		if (m_curSceneConfig.scene.type == 2 && fi.config.isPoolBoss()) {
			// 奖池 boss被打死了
			m_boss_complete_type = 2;
		} else if (m_curSceneConfig.scene.type == 3 && fi.config.isInstanceBoss()) {
			m_boss_complete_type = 3;
		}
		
		if (fi.group_id <= 0)
			return ;
		
		GroupConInfo groups = m_all_group.get(fi.group_id);
		if (groups == null)
			return ;
		
		if (groups.head.contains( fi.idx )) {
			for (int fishidx : groups.head) {
				FishInfo tfi = m_all_fish.get(fishidx);
				if (tfi == null)
					continue ;
				
				CalcFishDrop(con, tfi, coin_rate, dropinfo);
				m_all_fish.remove(tfi.idx);
				pdata.playerCatchFish(tfi.config.t);
			}
			
			
			for (int fishidx : groups.member) {
				FishInfo tfi = m_all_fish.get(fishidx);
				if (tfi == null)
					continue ;
				
				CalcFishDrop(con, tfi, coin_rate, dropinfo);
				m_all_fish.remove(tfi.idx);
				pdata.playerCatchFish(tfi.config.t);
			}
		}
	}
	
	private long calcCoin(long drop_coin, int coin_rate) {
		long res =  drop_coin * coin_rate;
		return res;
	}

	private void CalcFishDrop(PlayerConnection con, FishInfo fi, int coin_rate, List< DropItem > dropinfo) {
		DropItem di = new DropItem();
		di.setFishIndex(fi.idx);
		
		long money_t = 0;
		long gold_t = 0;
		
		for (DropRange rn : fi.config.drop) {
			long num = RandomUtil.RangeRandom(rn.m, rn.x);
			if (num  <= 0)
				continue ;
			
			if (rn.id == Consts.getCOIN_ID())
				num = calcCoin(num, coin_rate);
			
			di.getId().add(rn.id);
			di.getNum().add(num);
			
			if (fi.config.id == 4) {
				if (rn.id == Consts.getCOIN_ID())
					money_t = num;
				else if (rn.id == Consts.getGOLD_ID())
					gold_t = num;
			}
		}
		
		if (money_t > 0) {
			ChatHandler.newbanner(45, con.getPlayer().getName(), con.getPlayerId(), money_t, gold_t);
		}
		
		dropinfo.add(di);
	}
	
	public static FastMap<Long, Long> _speri_fisher = new FastMap<Long, Long>();
	public static void init_register() {
		_speri_fisher.put(1326L,1326L);
		_speri_fisher.put(1316L,1316L);
		_speri_fisher.put(1320L,1320L);
		_speri_fisher.put(1328L,1328L);
		_speri_fisher.put(1336L,1336L);
		_speri_fisher.put(1337L,1337L);
		_speri_fisher.put(1338L,1338L);
		_speri_fisher.put(1358L,1358L);
		_speri_fisher.put(1321L,1321L);
		_speri_fisher.put(1341L,1341L);
		_speri_fisher.put(1347L,1347L);
		_speri_fisher.put(1355L,1355L);
		_speri_fisher.put(1360L,1360L);
		_speri_fisher.put(1362L,1362L);
		_speri_fisher.put(1377L,1377L);
		_speri_fisher.put(1372L,1372L);
		_speri_fisher.put(3318L,3318L);
		_speri_fisher.put(1385L,1385L);
		_speri_fisher.put(1382L,1382L);
		_speri_fisher.put(1394L,1394L);
		_speri_fisher.put(5842L,5842L);
		_speri_fisher.put(1398L,1398L);
		_speri_fisher.put(5930L,5930L);
	}
	
	static {
		init_register();
	}
	
	public static void register_spec_fisher(long player_id) {
		_speri_fisher.put(player_id, player_id);
	}
	private static boolean is_spec_fisher(long player_id) {
		return _speri_fisher.containsKey(player_id);
	}
	
	public boolean catchFish(PlayerConnection con, int fishIdx, int damage, int catch_fish_rate, int coin_rate,
			List< DropItem > dropinfo, int attackType, int policy_id, int attackCount, int pre_money) {
		FishInfo fi = m_all_fish.get(fishIdx);
		if (fi == null) {
			logger.debug("{} fish not exist", fishIdx);
			return false;
		}
		
		int delte_time = delteTime();
		int res = fi.isExists(delte_time);
		if (res != 0) {
			logger.debug("{} fish expire time {}", fishIdx, res);
			return false;
		}
		
		// 1. 检查鱼的血量
		/*
		fi.hp = fi.hp - Math.min(damage * attackCount, fi.hp);
		if (fi.hp > 0) {
			logger.debug("{} fish not die", fishIdx);
			return false;
		}*/
		
		if (attackType == 3 && fi.getRate() >= 300) {
			logger.debug("{} fish nuclear rate", fishIdx);
			return false;
		} else if (attackType == 4 && fi.getRate() >= 100) {
			logger.debug("{} fish laser rate", fishIdx);
			return false;
		} else if (attackType == 5) {
			if (!fi.config.isKiller)
				return false;
		}
		
		//if (fi.getRate() * coin_rate > 1200)
		//	return false;
		
		int fish_drop_rate = fi.config.getRate();
		int org_rate = fi.config.getAttackRate();
		if (attackType == 0 ||
				attackType == 5) {
			if (policy_id == 2) {
				if (fish_drop_rate >= 300) {
					org_rate = (int)(org_rate * 2);
				} else if (fish_drop_rate >= 100) {
					org_rate = (int)(org_rate * 3);
				} else if (fish_drop_rate >= 30 && fish_drop_rate < 100) {
					org_rate = (int)(org_rate * 3);
				} else {
					org_rate = (int)(org_rate * 1.5);
				}
			} else if (policy_id == 6) {
				if (fish_drop_rate >= 300) {
					org_rate = (int)(org_rate * 10);
				} else if (fish_drop_rate >= 100) {
					org_rate = (int)(org_rate * 15);
				} else if (fish_drop_rate >= 30 && fish_drop_rate < 100) {
					org_rate = (int)(org_rate * 15);
				} else {
					org_rate = (int)(org_rate * 3);
				}
			} else {
				if (fi.config.isPoolBoss()) {
					// 原来的概率
				} else {
					if (fish_drop_rate >= 300) {
						return false;
					} else if (fish_drop_rate >= 100)
						org_rate = (int)(org_rate * 0.2);
					else if (fish_drop_rate >= 30)
						org_rate = (int)(org_rate * 0.4);
					else
						org_rate = (int)(org_rate * 0.5);
				}
			}
		}
		

		int fishrate = org_rate + catch_fish_rate;
		boolean isCatched = false;
		float attackCount_f = attackCount;
		
		boolean is_test_fail = false;
		if (true) {
			if (pre_money >= 200 && fish_drop_rate <= 30) {
				if (attackType == 0 ||
						attackType == 5) {
					if (policy_id == 5) {
						is_test_fail = true;
					}
				}
			}
			fishrate *= 0.6;
		}

		// 2. 计算打中概率
		while (attackCount > 0) {	
			if (is_test_fail) {
				fishrate = (int)(fishrate * attackCount / attackCount_f);
			}
			if (is_test_fail) {
				fishrate = (int)(fishrate * attackCount / attackCount_f);
			}
			attackCount--;
			
			if (RandomUtil.RangeRandom(0, 10000) > fishrate) {
				logger.debug("{} fish attack rate {}", fishIdx, fishrate);
				continue;
			}
			
			/*
			if (fish_policy_config != null) {
				if (RandomUtil.RangeRandom(0, 10000) > fish_policy_config.rate()) {
					logger.debug("{} fish policy rate", fishIdx);
					continue;
				}
			}*/
			
			isCatched = true;
			break;
		}
		
		if (!isCatched)
			return false;
		
		// 3. 打中了, 计算掉落
		fishGoDie(con, fi, coin_rate, dropinfo, delte_time);
		return true;
	}
	
	public boolean killFish(PlayerConnection con, KillFishes msg, int damage, int catch_fish_rate, int coin_rate,
			List< DropItem > dropinfo, int attackType, int policy_id) {
		FishInfo killer = m_all_fish.get(msg.getKiller());
		boolean isCatchKiller = catchFish(con, msg.getKiller(), damage, 
				catch_fish_rate, coin_rate, dropinfo, 5, policy_id, 1, 0);
		if (!isCatchKiller)
			return false;
		
		for (int fidx : msg.getFishIndex()) {
			FishInfo fi = m_all_fish.get(fidx);
			if (fi == null)
				continue ;
			
			int delte_time = delteTime();
			int res = fi.isExists(delte_time);
			if (res != 0)
				continue ;
			
			if (fi.config.isKiller)
				continue ;
			
			int rat = fi.getRate();
			if (rat < killer.config.killRect.m || rat > killer.config.killRect.x)
				continue ;
			
			fishGoDie(con, fi, coin_rate, dropinfo, delte_time);
		}
		
		return true;
	}
	
	public void Speed(PlayerConnection con, MT_Data_GFSkill skillcon) {
		PlayerInfo pi = player_infos.get(con.getPlayerId());
		if (pi == null)
			return ;
		
		pi.speed_end_time = TimeUtil.GetDateTime() + skillcon.duration() * 1000;
	}
	
	public boolean isInSpeed(PlayerConnection con) {
		PlayerInfo pi = player_infos.get(con.getPlayerId());
		if (pi == null)
			return false;
		
		long kk = TimeUtil.GetDateTime();
		return kk < pi.speed_end_time;
	}
	
	public void Summone(SummoneSync sync) {
		int fishid = FishGameConfig.getInstance().getSummoneId();
		if (fishid == -1)
			return ;
		
		FishInfo fi = new FishInfo();
		fi.idx = RandomUtil.RangeRandom(1000, 2000);
		fi.temp_id = fishid;
		fi.group_id = 0;
		fi.config = FishGameConfig.getInstance().getFishConfig(fi.temp_id);
		fi.hp = fi.config.hp;
		
		int delte_time = delteTime();
		fi.bron_time = delte_time;
		fi.leave_time = delte_time + 30 * 1000;
		m_all_fish.put(fi.idx, fi);
		
		sync.setFishIndex(fi.idx);
		sync.setFishType(fi.temp_id);
		sync.setLifeTime(fi.leave_time - fi.bron_time);
		sync.setLineId(RandomUtil.RangeRandom(0, 99));
	}
	
	public boolean isFishExsits(int fidx) {
		FishInfo fi = m_all_fish.get(fidx);
		if (fi == null)
			return false;
		
		return fi.isExists(delteTime()) == 0;
	}

	// 开始游戏
	public void beginGame() {
		begin_time = TimeUtil.GetDateTime();
		scene_step = 0 ;
		
		int scene_id = FishGameConfig.getInstance().getNextScene(getLevelId(), scene_step);
		swtichScene(scene_id, true);
	}
	
	public void trySwitch() {
		int delteTime = delteTime();
		if (delteTime >= m_curSceneConfig.scene.duration * 1000) {
			scene_step++;
			int scene_id = FishGameConfig.getInstance().getNextScene(getLevelId(), scene_step);
			swtichScene(scene_id, false);
		}
	}
	
	private int getRobotNum() {
		int num = 0;
		for (long pid : player_infos.keySet()) {
			if (ChatHandler.isRobot(pid))
				num++;
		}
		return num;
	}
	
	public boolean isFull(boolean isRobot) {
		if (GameConfig.fish_ban_pool_and_instance) {
			if (m_curSceneConfig.scene.type == 2 ||
					m_curSceneConfig.scene.type == 3)
				return true;
		}
		
		if (!isRobot){
			return player_infos.size() >= 4;
		}
		else {
			if (player_infos.size() >= 4)
				return true;
			
			int num = getRobotNum();
			return num >= 2;
		}
	}
	
	public boolean isEmpty() {
		return player_infos.isEmpty();
	}
	
	// 加入游戏
	public void joinGame(PlayerConnection con, int instanceId) {
		int freepos = getFreePos();
		GDL_G2C_PlayerJoinGame joinmsg = new GDL_G2C_PlayerJoinGame();
		joinmsg.setPlayerId(con.getPlayerId());
		joinmsg.setGameId(Consts.getFisher());
		FishGamePlayerInfo fgp = new FishGamePlayerInfo();
		fgp.setPlayerId(con.getPlayerId());
		fgp.setPos(freepos);
		fgp.setBullet( (int)con.getPlayer().getItemData().getItemCountByTempId(Consts.getCurBullet()) );
		fgp.setBattery( (int)con.getPlayer().getItemData().getItemCountByTempId(Consts.getCurBattery()) );
		fgp.setName(con.getPlayer().getName());
		fgp.setHeadUrl(con.getPlayer().getHeadUrl());
		fgp.setViplevel(con.getPlayer().getVipLvl());
		fgp.setCoin( con.getPlayer().getItemData().getItemCountByTempId(Consts.getCOIN_ID()) );
		fgp.setGold( con.getPlayer().getItemData().getItemCountByTempId(Consts.getGOLD_ID()) );
		joinmsg.setFplayerInfos(fgp);
		PlayerConManager.getInstance().broadcastMsg(joinmsg, player_infos.keySet());
		
		long now_time = TimeUtil.GetDateTime();
		PlayerInfo pi = new PlayerInfo();
		pi.player_id = con.getPlayerId();
		pi.join_time = now_time;
		pi.pos = freepos;
		pi.batteryId = (int)con.getPlayer().getItemData().getItemCountByTempId(Consts.getCurBattery());
		pi.bulletId = (int)con.getPlayer().getItemData().getItemCountByTempId(Consts.getCurBullet());
		player_infos.put(con.getPlayerId(), pi);
		con.getPlayer().setGameUID(instanceId);
		
		GDL_G2C_EnterGameRe re = new GDL_G2C_EnterGameRe();
		re.setGameId(Consts.getFisher());
		re.setInstanceId(instanceId);
		FishGameStatus fishGameInfo = new FishGameStatus();
		fishGameInfo.setSceneId(m_scene_id);
		fishGameInfo.setTaskComplate(is_task_complete ? 1 : 0);
		int del_time = delteTime();
		int dll = getSurplusFrozenTime(now_time);
		fishGameInfo.setPass_time(del_time);
		fishGameInfo.setFrozening_time( dll );
		for (PlayerInfo playeriii : player_infos.values()) {
			PlayerConnection pbc = PlayerConManager.getInstance().getCon(playeriii.player_id);
			if (pbc == null)
				continue;
			
			PlayerData pbi = pbc.getPlayer();
			
			FishGamePlayerInfo ppi = new FishGamePlayerInfo();
			ppi.setBattery(playeriii.batteryId);
			ppi.setBullet(playeriii.bulletId);
			ppi.setPlayerId(playeriii.player_id);
			ppi.setPos(playeriii.pos);
			
			ppi.setName(pbi.getName());
			ppi.setHeadUrl(pbi.getHeadUrl());
			ppi.setViplevel(pbi.getVipLvl());
			ppi.setCoin( pbi.getCoin() );
			ppi.setGold( pbi.getGold() );
			
			fishGameInfo.getPlayerInfos().add(ppi);
		}
		re.setFishGameInfo(fishGameInfo);
		con.send(re);
	}
	
	public void leaveGame(PlayerConnection con) {
		PlayerInfo pi = player_infos.remove(con.getPlayerId());
		con.getPlayer().setGameUID(0);
		con.getPlayer().playerFishTotal(pi.money_calc);
		
		GDL_G2C_PlayerLeaveGame leavemsg = new GDL_G2C_PlayerLeaveGame();
		leavemsg.setPlayerId(con.getPlayerId());
		PlayerConManager.getInstance().broadcastMsg(leavemsg, player_infos.keySet());
	}
	
	
	
	// 冰冻技能
	public void frozenSkill() {
		long cur_time = TimeUtil.GetDateTime();
		
		// 累计已经起效的冰冻时间
		this.frozen_times = getAllFrozen(cur_time);
		
		//System.err.println(" ------------frozen_times : " + frozen_times);
		
		// 更新最近一次冰冻技能的释放时间
		this.last_frozen_time = cur_time;
	}
	
	// 游戏进度 , 游戏进行的秒数
	public int delteTime() {
		long cur_time = TimeUtil.GetDateTime();
		
		// 获得游戏进行的时间
		long diff = cur_time - begin_time;
		long all_frazen = getAllFrozen(cur_time);
		//System.err.println("------------all_frazen = " + all_frazen);
		
		// 修正因为冰冻导致的时间cd
		diff -= all_frazen;
		return (int)diff;
	}
	
	private int getFrozenTime() {
		return 10 * 1000;
	}
	
	// 获取冰冻技能累计时间
	private long getAllFrozen(long now) {
		if (last_frozen_time == 0)
			return 0;
		
		long last_frozen_end_time = last_frozen_time + getFrozenTime();
		if (last_frozen_end_time <= now)
			return this.frozen_times + getFrozenTime();
		
		return this.frozen_times + getAlreadyFrozenTime(now);
	}
	
	// 如果当前有生效的冰冻技能，那么获取当前生效冰冻技能的剩余时间
	public int getSurplusFrozenTime(long now) {
		if (last_frozen_time == 0)
			return 0;
		
		long last_frozen_end_time = last_frozen_time + getFrozenTime();
		if (last_frozen_end_time <= now)
			return 0;
		
		return (int)(last_frozen_end_time - now);
	}
	
	// 如果当前有生效的冰冻技能，那么这个函数获取当前生效冰冻技能已经生效的时间
	private int getAlreadyFrozenTime(long now) {
		if (last_frozen_time == 0)
			return 0;
		
		long last_frozen_end_time = last_frozen_time + getFrozenTime();
		if (last_frozen_end_time <= now)
			return 0;
		
		return (int)(now - last_frozen_time);
	}
}
