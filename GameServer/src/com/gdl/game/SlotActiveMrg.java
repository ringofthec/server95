package com.gdl.game;

import gameserver.event.GameEventDispatcher;
import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.GDL_G2C_FlushSelfActiveInfoRe;
import gameserver.jsonprotocol.GDL_G2C_GoldSlotActiveSync;
import gameserver.jsonprotocol.GDL_G2C_JoinGoldSlotActiveRe;
import gameserver.jsonprotocol.GDL_G2C_ReqFriutActiveInfoRe;
import gameserver.jsonprotocol.GDL_G2C_SlotsActiveStateRe;
import gameserver.jsonprotocol.GoldActiveReward;
import gameserver.jsonprotocol.SoltsActivePlayerInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javolution.util.FastMap;
import net.schst.EventDispatcher.Event;
import net.schst.EventDispatcher.EventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.MT_Data_GSlotsFriutActiveReward;
import table.MT_Data_GSlotsJZActiveReward;
import table.base.TableManager;

import com.commons.network.websock.PlayerConManager;
import com.commons.network.websock.PlayerConnection;
import com.commons.network.websock.handler.ChatHandler;
import com.commons.network.websock.handler.ShopHandler;
import com.commons.thread.WorldEvents;
import com.commons.util.RandomUtil;
import com.commons.util.TimeUtil;
import com.gdl.data.MailData;




public class SlotActiveMrg implements EventListener {
	private static SlotActiveMrg m_int = new SlotActiveMrg();
	private SlotActiveMrg() {}
	public static SlotActiveMrg getInstance() {return m_int;}
	
	private final Logger logger = LoggerFactory.getLogger(SlotActiveMrg.class);
	
	public static class SoltsActiveInfo {
		int uid;
		int status = 0;  // 0 初始化  1 等待开始 2 开始 3 即将结束4结束
		long begin_time;
		long end_time;
		int gameid;
		int levelId;
		boolean dirty = false;
		public List<SoltsActivePlayerInfo> sorted_playerInfos = new ArrayList<SoltsActivePlayerInfo>();
		private FastMap<Long, SoltsActivePlayerInfo> player_records = new FastMap<Long, SoltsActivePlayerInfo>();
		private List<Long> players = new ArrayList<Long>();
		
		public SoltsActivePlayerInfo addPlayer(PlayerConnection con) {
			
			players.add(con.getPlayerId());
			
			if (gameid == Consts.getGoldSlot()) {
				GDL_G2C_GoldSlotActiveSync sync = new GDL_G2C_GoldSlotActiveSync();
				sync.setNum(players.size() + 1);
				
				fillGoldRewards(sync.getGoldSlotsRewards());
				PlayerConManager.getInstance().broadcastMsg1(sync, players, con.getPlayerId());
				
				if (!ChatHandler.isRobot(con.getPlayerId()))
					ChatHandler.newbanner(21 + levelId, con.getPlayer().getName(), con.getPlayerId());
				else if (RandomUtil.RangeRandom(1, 10) < 2) {
					ChatHandler.newbanner(21 + levelId, con.getPlayer().getName(), con.getPlayerId());
				}
			}
			
			SoltsActivePlayerInfo info = new SoltsActivePlayerInfo();
			info.setPlayer_id(con.getPlayerId());
			info.setName(con.getPlayer().getName());
			info.setEran(0L);
			info.setOrder(0);
			sorted_playerInfos.add(info);
			player_records.put(con.getPlayerId(), info);
			return info;
		}

		private void fillGoldRewards(List< GoldActiveReward > list) {
			int num1_gold = calcGoldSlotsRewards(players.size());
			
			GoldActiveReward tgoldSlotsRewards1 = new GoldActiveReward();
			list.add(tgoldSlotsRewards1);
			tgoldSlotsRewards1.getItemTempId().add(Consts.getGOLD_ID());
			tgoldSlotsRewards1.getItemNum().add(num1_gold);
			
			for (int i = 2; i <= 3; ++i) {
				MT_Data_GSlotsJZActiveReward res_js_reward = TableManager.GetInstance()
						.TableGSlotsJZActiveReward().GetElement(50 * (levelId - 1) + i);
				GoldActiveReward tgoldSlotsRewards2 = new GoldActiveReward();
				for (Int2 rewarditem : res_js_reward.rewards()) {
					tgoldSlotsRewards2.getItemTempId().add(rewarditem.field1());
					tgoldSlotsRewards2.getItemNum().add(rewarditem.field2());
				}
				list.add(tgoldSlotsRewards2);
			}
		}
		
		public int getPlayerNum() {
			return players.size();
		}
		
		public void check(PlayerConnection con, long res_money) {
			if (gameid == Consts.getGoldSlot()) {
				if (players.contains(con.getPlayerId())) {
					recordPlayerMoney(con, res_money);
				}
			} else {
				recordPlayerMoney(con, res_money);
			}
		}

		private void recordPlayerMoney(PlayerConnection con, long res_money) {
			SoltsActivePlayerInfo info = null;
			if (player_records.containsKey(con.getPlayerId())) {
				info = player_records.get(con.getPlayerId());
			} else {
				info = addPlayer(con);
			}
			
			long temp_money = info.getEran() + res_money;
			info.setEran(temp_money);
			dirty = true;// 脏标志，需要排序了		
			
			GDL_G2C_FlushSelfActiveInfoRe re = new GDL_G2C_FlushSelfActiveInfoRe();
			re.setGameId(gameid);
			re.setLevel(levelId);
			re.setEran(temp_money);
			re.setOrder(-2);
			con.send(re);
		}
		
		private boolean isJoin(long playerid) {
			for (long pi : players) {
				if (pi == playerid)
					return true;
			}
			return false;
		}
		
		public void sendSortList(PlayerConnection con) {
			GDL_G2C_ReqFriutActiveInfoRe re = new GDL_G2C_ReqFriutActiveInfoRe();
			
			long now = TimeUtil.GetDateTime();
			long diff = end_time - now;
			if (diff < 0) diff = 0;
			re.setRetCode(0);
			if (now < begin_time)
				re.setRetCode(0);
			else if (now < end_time)
				re.setRetCode(1);
			re.setLefttime((int)diff / 1000);
			re.setIsJoin(0);
			if (gameid == Consts.getGoldSlot()) {
				if (now < begin_time) {
					re.setRetCode(2);
					re.setLefttime((int) ( (begin_time - now) / 1000 ));
				}
				
				re.setIsJoin(isJoin(con.getPlayerId()) ? levelId : 0);
				fillGoldRewards(re.getGoldSlotsRewards());
			}
			
			re.setGameId(gameid);
			re.setLevel(levelId);
			
			int max = Math.min(100, sorted_playerInfos.size());
			re.setPlayerInfos( sorted_playerInfos.subList(0, max) );
			if (player_records.containsKey(con.getPlayerId()))
				re.setSelf( player_records.get(con.getPlayerId()) );
			else {
				SoltsActivePlayerInfo self = new SoltsActivePlayerInfo();
				self.setEran(0L);
				self.setName(con.getPlayer().getName());
				self.setOrder(0);
				self.setPlayer_id(con.getPlayerId());
				re.setSelf(self);
			}
			con.send(re);
		}

		// 计算金砖奖励
		private int calcGoldSlotsRewards(int playerNum) {
			int per_person = ShopHandler.getCostCoinNum(14 + levelId);
			return playerNum * per_person;
		}

		private void sortList() {
			if (dirty) {
				Collections.sort(sorted_playerInfos, new Comparator<SoltsActivePlayerInfo>() {
		            public int compare(SoltsActivePlayerInfo arg0, SoltsActivePlayerInfo arg1) {
		                if ( arg0.getEran() > arg1.getEran() )
		                	return -1;
		                else if (arg0.getEran() == arg1.getEran())
		                	return 0;
		                else
		                	return 1;
		            }
		        });
				
				int order = 1;
				for (SoltsActivePlayerInfo sd : sorted_playerInfos) {
					sd.setOrder(order);
					
					PlayerConnection ppp = PlayerConManager.getInstance().getCon(sd.getPlayer_id());
					if (ppp != null) {
						GDL_G2C_FlushSelfActiveInfoRe re = new GDL_G2C_FlushSelfActiveInfoRe();
						re.setGameId(gameid);
						re.setLevel(levelId);
						re.setEran(sd.getEran());
						re.setOrder(order);
						ppp.directSendPack(re);
					}
					
					order ++;
				}
				dirty = false;
			}
		}
		
		public boolean isWait() {
			long now = TimeUtil.GetDateTime();
			return now < begin_time;
		}
		
		public boolean isActive() {
			long now = TimeUtil.GetDateTime();
			return now < end_time;
		}
		
		public void giveUp() {
			int gold = ShopHandler.getCostCoinNum(14 + levelId);
			if (gold == -1)
				return ;
			
			for (long pid : players) {
				Integer[] item = {Consts.getGOLD_ID(), gold}; 
				MailData.sendSystemMail(pid, "金磚比賽退款", "系统", 
						"", 15, item);
				
				PlayerConnection pc = PlayerConManager.getInstance().getCon(pid);
				if (pc != null) {
					GDL_G2C_SlotsActiveStateRe re = new GDL_G2C_SlotsActiveStateRe();
					re.setGameId(gameid);
					re.setLefttime(0);
					re.setLevel(levelId);
					re.setMode(3);
					pc.send(re);
				}
			}
		}
		
		public void willBegin() {
			long diff = begin_time - TimeUtil.GetDateTime();
			if (diff < 0) diff = 0;
			GDL_G2C_SlotsActiveStateRe re = new GDL_G2C_SlotsActiveStateRe();
			re.setGameId(gameid);
			re.setLefttime((int)diff/1000);
			re.setLevel(levelId);
			re.setMode(4);
			FriutSlotsGameInstanceManager.getInstance().broadAllPlayer(re);
			
			if (gameid == Consts.getGoldSlot())
				ChatHandler.newbanner(60 + levelId);
			else
				ChatHandler.newbanner(60);
		}
		
		public void onBegin() {
			long diff = end_time - TimeUtil.GetDateTime();
			if (diff < 0) diff = 0;
			GDL_G2C_SlotsActiveStateRe re = new GDL_G2C_SlotsActiveStateRe();
			re.setGameId(gameid);
			re.setLefttime((int)diff/1000);
			re.setLevel(levelId);
			re.setMode(0);
			
			if (gameid == Consts.getFriutSlot())
				FriutSlotsGameInstanceManager.getInstance().broadAllPlayer(re);
			else
				PlayerConManager.getInstance().broadcastMsg1(re, players);
			
			if (gameid == Consts.getGoldSlot())
				; // ChatHandler.newbanner(24 + levelId);
			else
				ChatHandler.newbanner(17);
		}
		
		public void willEnd() {
			long diff = end_time - TimeUtil.GetDateTime();
			if (diff < 0) diff = 0;
			GDL_G2C_SlotsActiveStateRe re = new GDL_G2C_SlotsActiveStateRe();
			re.setGameId(gameid);
			re.setLefttime((int)diff/1000);
			re.setLevel(levelId);
			re.setMode(5);
			
			if (gameid == Consts.getFriutSlot())
				FriutSlotsGameInstanceManager.getInstance().broadAllPlayer(re);
			else
				PlayerConManager.getInstance().broadcastMsg1(re, players);
		}
		
		public void onEnd() {
			GDL_G2C_SlotsActiveStateRe re = new GDL_G2C_SlotsActiveStateRe();
			re.setGameId(gameid);
			re.setLefttime(0);
			re.setLevel(levelId);
			re.setMode(1);
			PlayerConManager.getInstance().broadcastMsg1(re, players);
			
			if (gameid == Consts.getFriutSlot()) {
				for (SoltsActivePlayerInfo sd : sorted_playerInfos) {
					MT_Data_GSlotsFriutActiveReward reward_con = TableManager
							.GetInstance().getFriutActiveRewardReward(levelId, sd.getOrder());
					if (reward_con == null)
						return ;
					
					int iii = 0;
					Integer[] tempss = new Integer[reward_con.rewards().size() * 2];
					for (Int2 fff : reward_con.rewards()) {
						tempss[iii++] = fff.field1();
						tempss[iii++] = fff.field2();
					}
					
					MailData.sendSystemMail(sd.getPlayer_id(), 
							"水果比賽獎勵", "系统", "恭喜獲得水果比賽第" + sd.getOrder() + "名", 3, tempss);
					
					if (sd.getOrder() == 1) {
						ChatHandler.newbanner(17 + levelId, sd.getName(), sd.getPlayer_id());
					}
					
					if (sd.getOrder() <= 3) {
						PlayerConnection pc = PlayerConManager.getInstance().getCon(sd.getPlayer_id());
						if (pc != null) {
							pc.getPlayer().playerFruitsActiveTop3();
						} else {
							// 修改玩家数据库
						}
					}
				}
			} else if (gameid == Consts.getGoldSlot()) {
				List< GoldActiveReward > list = new ArrayList<GoldActiveReward>();
				fillGoldRewards(list);
				int num1_gold = calcGoldSlotsRewards(players.size());
				int o = 0;
				int total_player_num = player_records.size();
				for (SoltsActivePlayerInfo sd : sorted_playerInfos) {
					if (o >= 3)
						break;
					
					GoldActiveReward resds = list.get(o++);
					if (resds.getItemTempId().isEmpty())
						continue ;
					
					MailData.sendSystemMail(sd.getPlayer_id(), 
							"金磚比賽獎勵", "系统", "恭喜獲得金磚比賽第" + o + "名", 3, resds.getItemTempId(), resds.getItemNum());
					
					if (o == 1) {
						ChatHandler.newbanner(27 + levelId, sd.getName(), sd.getPlayer_id(), total_player_num, num1_gold);
					
						PlayerConnection pc = PlayerConManager.getInstance().getCon(sd.getPlayer_id());
						if (pc != null) {
							pc.getPlayer().playerGoldActive(num1_gold);
						} else {
							// 修改玩家数据库
						}
					}
				}
			}
			
			sorted_playerInfos.clear();
			players.clear();
		}
	}
	
	@Override
	public void handleEvent(Event e) throws Exception {
		if (e.getName().equals(WorldEvents.TIME_TICK_DAY))
			init(false);
		else
			checkActive();
	}

	public static class DatePair {
		long beginTime;
		long endTime;
		String begin_str;
		String end_str;
	}
	
	private List<DatePair> active_list = new ArrayList<SlotActiveMrg.DatePair>();
	private List<Integer> all_gamelevel_ids = new ArrayList<Integer>();
	public void init(boolean isBoot) {
		String[] begin_time = {"12:00:00","13:00:00","14:00:00","18:00:00","19:00:00","20:00:00"};
		String[] end_time   = {"12:30:00","13:30:00","14:30:00","18:30:00","19:30:00","20:30:00"};
		
		synchronized (SlotActiveMrg.class) {
			active_list.clear();
			String cur_day = TimeUtil.GetDateString().substring(0, 11);
			for (int i = 0; i < begin_time.length; ++i) {
				DatePair dp = new DatePair();
				dp.beginTime = TimeUtil.getTime(cur_day + begin_time[i]);
				dp.endTime  = TimeUtil.getTime(cur_day + end_time[i]);
				dp.begin_str = cur_day + begin_time[i];
				dp.end_str = cur_day + end_time[i];
				active_list.add(dp);
			}

			//只有水果slots
			for (int i = 1; i <= 1; ++i) {
				for (int k = 1; k <=2; ++k) {
					all_gamelevel_ids.add(GameInstanceMrg.genUnitId(i, k, 0));
				}
			}

			if (isBoot) {
				GameEventDispatcher.getInstance().addListener(
						WorldEvents.TIME_TICK_DAY, this);

				GameEventDispatcher.getInstance().addListener(
						WorldEvents.TIME_TICK_TEN_SECOND, this);
			}
		}
	}
	
	public void checkActive() {
		long now = TimeUtil.GetDateTime();
		long beforesort = now;
		
		synchronized (SlotActiveMrg.class) {
			// 检查活动是否结束
			List<Integer> del_set = new ArrayList<Integer>();
			Iterator<Map.Entry<Integer, SoltsActiveInfo>> iter = m_infos.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<Integer, SoltsActiveInfo> oo = iter.next();
				SoltsActiveInfo temp = oo.getValue();
				synchronized (temp) {
					try {
						if (!temp.isActive()) {
							if (temp.status == 4)
								continue;

							// 处理活动结束事件
							temp.status = 4;
							temp.sortList();
							oo.getValue().onEnd();
							del_set.add(oo.getKey());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			for (Integer key : del_set) {
				m_infos.remove(key);
			}

			// 检查金砖slot是否开始
			iter = m_infos.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<Integer, SoltsActiveInfo> oo = iter.next();
				SoltsActiveInfo si = oo.getValue();
				synchronized (si) {
					try {
						if (si.gameid == Consts.getGoldSlot()) {
							if (si.status == 2 && now > si.end_time - 60 * 1000) {
								si.status = 3;
								si.willEnd();
							}

							if (si.status == 0 && now > si.begin_time) {
								if (si.getPlayerNum() <= 1) {
									si.giveUp();
									iter.remove();
									continue;
								}

								si.status = 2;
								si.onBegin();
								continue ;
							}
						} else if (si.gameid == Consts.getFriutSlot()) {
							if (si.status == 2 && now > si.end_time - 5 * 60 * 1000) {
								si.status = 3;

								if (si.levelId == 1) {
									si.willEnd();
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			// 检查水果slot是否开始
			for (DatePair dp : active_list) {
				if (now > dp.endTime)
					continue;
				
				if (now < dp.beginTime - 5 * 60 * 1000) {
					continue;
				}
				
				for (int gamelevelid : all_gamelevel_ids) {
					try {
						SoltsActiveInfo si = null;
						if (!m_infos.containsKey(gamelevelid)) {
							si = new SoltsActiveInfo();
							si.begin_time = dp.beginTime;
							si.end_time = dp.endTime;
							si.levelId = GameInstanceMrg.getLevelId(gamelevelid);
							si.gameid = GameInstanceMrg.getGameId(gamelevelid);
							m_infos.put(gamelevelid, si);
						} else {
							si = m_infos.get(gamelevelid);
						}

						if (si.status == 0) {
							if (si.levelId == 1) {
								si.willBegin();
							}
							si.status = 1;
						}

						if (now >= dp.beginTime && now < dp.endTime && si.status == 1) {
							si.status = 2;
							// 处理活动开始事件
							if (si.levelId == 1)
								si.onBegin();
							m_infos.put(gamelevelid, si);
							continue;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			beforesort = TimeUtil.GetDateTime();
			
			for (SoltsActiveInfo si : m_infos.values()) {
				synchronized (si) {
					if (now >= si.begin_time && now < si.end_time) {
						si.sortList();
					}
				}
			}
		}
		
		long endsort = TimeUtil.GetDateTime();
		logger.info("slot active cost time: {} sort cost: {}", endsort - now, endsort - beforesort);
	}
	
	private FastMap<Integer, SoltsActiveInfo> m_infos = new FastMap<Integer, SlotActiveMrg.SoltsActiveInfo>();
	public void recordActive(PlayerConnection con, int gameid, long money) {
		SoltsActiveInfo si = null;
		if (gameid == Consts.getFriutSlot()) {
			int gamelevelid = GameInstanceMrg.convertUIdToGameLevelId( con.getPlayer().getGameUID() );
			si = m_infos.get(gamelevelid);
		} else {
			int uid = (int)con.getPlayer().getItemData().getItemCountByTempId(Consts.getGold_Slot_ActiveId());
			si = m_infos.get(uid);
		}
		
		if (si == null)
			return ;
		
		synchronized (si) {
			if (gameid == Consts.getGoldSlot())
				if (si.isWait())
					return ;

			if (si.isWait())
				return ;

			if (!si.isActive())
				return ;

			si.check(con, money);
		}
	}
	
	public void reqSoltActiveState(PlayerConnection con, int gameid, int levelid) {
		SoltsActiveInfo si = null;
		if (gameid == Consts.getFriutSlot()) {
			int gamelevelid = GameInstanceMrg.genUnitId(gameid, levelid, 0);
			si = m_infos.get(gamelevelid);
			if (si == null || !si.isActive() || si.isWait()) {
				GDL_G2C_ReqFriutActiveInfoRe re = new GDL_G2C_ReqFriutActiveInfoRe();
				re.setRetCode(0);
				con.send(re);
				return ;
			}
		} else {
			int uid = (int)con.getPlayer().getItemData().getItemCountByTempId(Consts.getGold_Slot_ActiveId());
			int join_game_level_id = GameInstanceMrg.getLevelId(uid);
			if (join_game_level_id != levelid && m_infos.containsKey(uid)) {
				GDL_G2C_ReqFriutActiveInfoRe re = new GDL_G2C_ReqFriutActiveInfoRe();
				re.setRetCode(10 + join_game_level_id);
				con.send(re);
				return ;
			}
			
			if (uid >= 0 && m_infos.containsKey(uid)) {
				si = m_infos.get(uid);
			} else {
				si = findGoldSlotWait(con, levelid);
				if (si == null) {
					GDL_G2C_ReqFriutActiveInfoRe re = new GDL_G2C_ReqFriutActiveInfoRe();
					re.setRetCode(0);
					con.send(re);
					return ;
				}
			}
		}
		
		synchronized (si) {
			si.sendSortList(con);
		}
	}
	
	private SoltsActiveInfo findGoldSlotWait(PlayerConnection con, int levelid) {
		for (SoltsActiveInfo si : m_infos.values()) {
			if (si.gameid != Consts.getGoldSlot())
				continue;
			
			if (si.isWait())
				return si;
		}
		
		return null;
	}
	
	private SoltsActiveInfo findJoinableGoldSlotActive(PlayerConnection con, int levelid, boolean create) {
		for (SoltsActiveInfo si : m_infos.values()) {
			if (si.gameid != Consts.getGoldSlot())
				continue;
			
			if (si.levelId != levelid)
				continue;

			if (!si.isWait())
				continue;

			return si;
		}
		
		if (create) {
			long now = TimeUtil.GetDateTime();
			SoltsActiveInfo si = null;
			si = new SoltsActiveInfo();
			si.begin_time = now / (10 * 1000) * (10 * 1000) + 1 * 60 * 1000;
			si.end_time = si.begin_time + 5 * 60 * 1000;
			si.levelId = levelid;
			si.gameid = Consts.getGoldSlot();
			si.uid = GameInstanceMrg.genUnitId(Consts.getGoldSlot(), levelid, RandomUtil.RangeRandom(1, Short.MAX_VALUE));
			m_infos.put(si.uid, si);
			return si;
		}
		
		return null;
	}
	
	public boolean isInGoldActive(PlayerConnection con) {
		int uid = (int)con.getPlayer().getItemData().getItemCountByTempId(Consts.getGold_Slot_ActiveId());
		
		SoltsActiveInfo si = null;
		synchronized (SlotActiveMrg.class) {
			if (!m_infos.containsKey(uid))
				return false;
			
			si = m_infos.get(uid);
			if (si.isWait())
				return false;
			
			if (si.isActive())
				return true;
		}
		
		return false;
	}
	
	public int getGoldAvtiveLevelId(PlayerConnection con) {
		int uid = (int)con.getPlayer().getItemData().getItemCountByTempId(Consts.getGold_Slot_ActiveId());
		
		SoltsActiveInfo si = null;
		synchronized (SlotActiveMrg.class) {
			if (!m_infos.containsKey(uid))
				return 0;
			
			si = m_infos.get(uid);
			return si.levelId;
		}
	}
	
	// 参加金砖slots
	public void joinGoldSlotActive(PlayerConnection con, int levelid) {
		int uid = (int)con.getPlayer().getItemData().getItemCountByTempId(Consts.getGold_Slot_ActiveId());
		
		// 找到一个合适的加入进去
		SoltsActiveInfo si = null;
		synchronized (SlotActiveMrg.class) {
			if (uid >= 0 && m_infos.containsKey(uid)) {
				GDL_G2C_JoinGoldSlotActiveRe re = new GDL_G2C_JoinGoldSlotActiveRe();
				re.setRetCode(1);
				con.send(re);
				return ;
			}
			
			if (!ShopHandler.testCost(con, 14 + levelid, 0))
				return ;
			
			si = findJoinableGoldSlotActive(con, levelid, true);
			synchronized (si) {
				si.addPlayer(con);
				si.sendSortList(con);
			}
		}
		
		con.getPlayer().getItemData().setItem(con, Consts.getGold_Slot_ActiveId(), si.uid, -14);
		GDL_G2C_JoinGoldSlotActiveRe re = new GDL_G2C_JoinGoldSlotActiveRe();
		re.setRetCode(0);
		con.send(re);
		return ;
	}
}
