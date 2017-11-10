package com.gdl.game;

import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.GDL_G2C_EnterGameRe;
import gameserver.jsonprotocol.GDL_G2C_NNBankerChange;
import gameserver.jsonprotocol.GDL_G2C_NNPlayerNotSitList;
import gameserver.jsonprotocol.GDL_G2C_NNReqLastPoolWin;
import gameserver.jsonprotocol.GDL_G2C_NNStateChange;
import gameserver.jsonprotocol.GDL_G2C_PlayerJoinGame;
import gameserver.jsonprotocol.GDL_G2C_PlayerLeaveGame;
import gameserver.jsonprotocol.NNBetPool;
import gameserver.jsonprotocol.NNBetResult;
import gameserver.jsonprotocol.NNCardsStraight;
import gameserver.jsonprotocol.NNGameStatus;
import gameserver.jsonprotocol.NNOpenResults;
import gameserver.jsonprotocol.NNPlayerInfo;
import gameserver.jsonprotocol.NNPlayerOpenResults;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_GNiuCardStraight;
import table.base.TableManager;

import com.commons.network.websock.JsonPacket;
import com.commons.network.websock.PlayerConManager;
import com.commons.network.websock.PlayerConnection;
import com.commons.network.websock.handler.ChatHandler;
import com.commons.util.RandomUtil;
import com.commons.util.TimeUtil;
import com.gdl.data.PlayerBriefInfo;
import com.gdl.game.NiuniuGen2.ResList;
import com.gdl.manager.GamePoolManager;
import com.gdl.manager.PlayerBriefInfoManager;

public class NiuniuGameInfo {
	
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(NiuniuGameInfo.class);
	
	int gameUid;
	long begin_time;
	long[] pos = new long[6]; 
	int status; // 游戏当前状态 0 等待中 1 下注中 2 开牌中
	long last_action_time;
	
	public static class BankerInfo {
		public long player_id;
		public long coin_total;
		public long cur_total;
		public int num;
	}
	
	FastList<BankerInfo> _bankers = new FastList<BankerInfo>();
	BankerInfo _cur_banker;
	
	public boolean isInBank(long player_id) {
		if (player_id == _cur_banker.player_id)
			return true;
		
		for (BankerInfo p : _bankers)
			if (p.player_id == player_id)
				return true;
		
		return false;
	}
	
	public void leaveBanker(PlayerConnection con) {
		if (con.getPlayerId() == _cur_banker.player_id) {
			_cur_banker = _system_bank;
			GDL_G2C_NNBankerChange change = new GDL_G2C_NNBankerChange();
			con.send(change);
			return ;
		}
		
		for (BankerInfo p : _bankers) {
			if (p.player_id == con.getPlayerId()) {
				_bankers.remove(p);
				return ;
			}
		}
	}
	
	public void reqBanker(PlayerConnection con, long total_coin) {
		BankerInfo bi = new BankerInfo();
		bi.player_id = con.getPlayerId();
		bi.coin_total = total_coin;
		bi.cur_total = total_coin;
		_bankers.addLast(bi);
		
		Collections.sort(_bankers, new Comparator<BankerInfo>() {
			@Override
			public int compare(BankerInfo o1, BankerInfo o2) {
				if (o1.coin_total > o2.coin_total)
					return -1;
				else if (o1.coin_total < o2.coin_total)
					return 1;
				else
					return 0;
			}
		});
	}
	
	public FastList<BankerInfo> getBankers() {
		return _bankers;
	}
	
	public void checkBanker() {
		if (_cur_banker.player_id > 0) {
			if (isValidBanker( _cur_banker ))
				return ;
		}
		
		if (_bankers.isEmpty()) {
			_cur_banker = _system_bank;
			return ;
		}
		
		Iterator<BankerInfo> iter = _bankers.iterator();
		while (iter.hasNext()) {
			if (!isValidBanker( iter.next() )) {
				iter.remove();
				continue;
			}
		}
		
		if (_bankers.isEmpty()) {
			_cur_banker = _system_bank;
			return ;
		}
		
		_cur_banker = _bankers.removeFirst();
	}
	
	private boolean isValidBanker(BankerInfo banker) {
		PlayerConnection con = PlayerConManager.getInstance().getCon(banker.player_id);
		if (con == null) {
			return false;
		}
		
		if (banker.num >= 5) {
			//PlayerBriefInfo pppb = PlayerBriefInfoManager.getInstance().getPlayerBriefInfo(banker.player_id);
			//if (pppb != null && banker.cur_total > 0) {
			//	ChatHandler.newbanner(82, pppb.getName(), pppb.getPlayer_id(), banker.cur_total);
			//}
			return false;
		}
		
		// 赢取不能超过坐庄时投入的钱
		if (banker.cur_total >= banker.coin_total * 2) {
			PlayerBriefInfo pppb = PlayerBriefInfoManager.getInstance().getPlayerBriefInfo(banker.player_id);
			if (pppb != null) {
				ChatHandler.newbanner(82, pppb.getName(), pppb.getPlayer_id(), banker.cur_total);
			}
			return false;
		}
		
		if (banker.cur_total < Consts.getNiuniu_ZZ_Min_Coin()) {
			return false;
		}
			
		if (con.getPlayer().getCoin() < Consts.getNiuniu_ZZ_Min_Coin()) {
			return false;
		}
		
		return true;
	}
	
	public BankerInfo getCurBankerId() {
		return _cur_banker;
	}
	
	public void trySwitchState() {
		long time_del = getNextOpenTime();
		
		if (time_del > 0)
			return ;
		
		status++;
		status %= 3;
		last_action_time = TimeUtil.GetDateTime();
		
		if (status == 2) {
			// 计算开牌结果
			calcOpen();
		} else {
			GDL_G2C_NNStateChange cha = new GDL_G2C_NNStateChange();
			cha.setState(status);
			cha.setNextStartDuration((int)getNextOpenTime());
			if (status == 1) {
				checkBanker();
				
				if (_cur_banker.player_id > 0) {
					cha.setBanker(fillPlayerInfo(_cur_banker.player_id));
				}
				cha.setBankerCoin(_cur_banker.cur_total);
				
				for (NiuniuPoolInfo pi : m_pool_info)
					pi.clear();
			}
				
			broadcast(cha);
		}
	}
	
	List<Integer> _all_suffle_car = new ArrayList<Integer>();
	List<NNBetResult> _near_bet_result = new ArrayList<NNBetResult>(); 
	
	public List<NNBetResult> getNearRes() {
		return _near_bet_result;
	}
	
	long max_pool_player_id = 0;
	long max_pool_coin = 0;
	
	public void getLastPoolWin(GDL_G2C_NNReqLastPoolWin msg) {
		if (max_pool_player_id == 0)
			return ;
		
		NNPlayerInfo ppp = fillPlayerInfo(max_pool_player_id);
		msg.setPlayer(ppp);
		msg.setPoolCoin(max_pool_coin);
	}
	
	private void calcOpen() {
		// 1. 随机公平发牌
		Collections.shuffle(_all_suffle_car);
		
		// 2. 列出牌型
		ArrayList<ResList> reslist = new ArrayList<NiuniuGen2.ResList>();
		FastMap<Integer, ResList> map_herl = new FastMap<Integer, NiuniuGen2.ResList>();
		for (int i = 0; i < 5; ++i) {
			ResList res1 = NiuniuGen2.getInstance().genWuniu(_all_suffle_car.subList(i * 5, (i + 1) *5));
			res1.id = i;
			reslist.add(res1);
			map_herl.put(i, res1);
		}
		
		// 3. 牌型数值花色大小排序
		Collections.sort(reslist, new Comparator<NiuniuGen2.ResList>() {
			@Override
			public int compare(ResList o1, ResList o2) {
				if (o1.type > o2.type)
					return -1;
				else if (o1.type < o2.type)
					return 1;
				else {
					if (o1.max_point > o2.max_point)
						return -1;
					else if (o1.max_point < o2.max_point)
						return 1;
					else {
						if (o1.mask > o2.mask)
							return -1;
						else
							return 1;
					}
				}
			}
		});
		
		int order = 1;
		for (ResList pa : reslist) {
			pa.order = order;
			order++;
		}
		
		// 4. 庄家拿牌，顺便填充庄家拿牌信息
		int z_order = 0;
		{
			int order_temp = RandomUtil.RangeRandom(0, 10000);
			if (order_temp < 2500)
				z_order = 0;
			else if (order_temp < 4000)
				z_order = 4;
			else if (order_temp < 6200)
				z_order = 2;
			else 
				z_order = RandomUtil.RangeRandom(0, 100) > 50 ? 1 : 3;
				
		}
		
		System.err.println("z_order = " + z_order);
		
		ResList z_resList = reslist.get(z_order);
		int z_get_id = z_resList.id;
		NNCardsStraight z_cardstraight = new NNCardsStraight();
		z_cardstraight.setType(z_resList.type);
		z_cardstraight.setCards(z_resList.res_list);
		
		MT_Data_GNiuCardStraight z_config = TableManager.GetInstance()
				.TableGNiuCardStraight().GetElement(z_resList.type);
		
		// 5. 庄家和4个池子的结算
		Map<Long, Long> static_map = new HashMap<Long, Long>();
		List<NNCardsStraight> pool_card_straight = new ArrayList<NNCardsStraight>();
		Map<NiuniuPoolInfo, Long> z_coin_map = new HashMap<NiuniuPoolInfo, Long>();
		int pool_count = 0;
		for (ResList pa : map_herl.values()) {
			if (pa.id == z_get_id)
				continue;
			
			NNCardsStraight l_card_straight = new NNCardsStraight();
			l_card_straight.setType(pa.type);
			l_card_straight.setCards(pa.res_list);
			pool_card_straight.add(l_card_straight);
			
			// 计算每个池子的输赢
			NiuniuPoolInfo pool_info = m_pool_info.get(pool_count++);
			pool_info.straight = l_card_straight;
			pool_info.win = pa.order < z_resList.order;
			
			// 求池子的赢输，以及池子里玩家的输赢
			int target_rate = 0;
			if (pool_info.win) {
				MT_Data_GNiuCardStraight ll_config = TableManager.GetInstance()
						.TableGNiuCardStraight().GetElement(pool_info.straight.getType());
				target_rate = ll_config.rate();
			} else {
				target_rate = z_config.rate();
			}
			
			// 统计玩家输赢
			pool_info.total_earn_coin = pool_info.calcEveryOne(target_rate, static_map);
			
			// 统计庄家输赢
			z_coin_map.put(pool_info, 
						target_rate * pool_info.total_bet_coin);
		}
		
		// 统计庄家全部池子的总输赢
		long z_coin_total = 0L;
		for (NiuniuPoolInfo ccc : z_coin_map.keySet()) {
			long money_coin = z_coin_map.get(ccc);
			if (ccc.win)
				z_coin_total -= money_coin;
			else
				z_coin_total += money_coin;
		}
		
		// 计算奖池
		long z_pool_coin_get = 0L;
		FastMap<Long, Long> pool_coin_map = new FastMap<Long, Long>();
		for (ResList pa : map_herl.values()) {
			if (true)
				break;
			
			if (pa.type == 13 || 
					pa.type == 12) {
				//if (true) {
				if (pa.id == z_get_id) {
					if (_cur_banker.player_id > 0) {
						// 庄
						z_pool_coin_get = GamePoolManager.getInstance().checkPoolReward(Consts.getNiuNiu(), 1, 0.5, true);
						if (z_pool_coin_get > max_pool_coin) {
							max_pool_coin = z_pool_coin_get;
							max_pool_player_id = _cur_banker.player_id;
						}
					}
				} else {
					// 闲
					for (NiuniuPoolInfo pppppp : m_pool_info) {
						if (pppppp.id == pa.id) {
							double all_coin_pool = pppppp.total_bet_coin;
							double rate_ret_pool = 0;
							
							if (pppppp.all_bet_player_info.isEmpty())
								break;
							
							long poo = GamePoolManager.getInstance().checkPoolReward(Consts.getNiuNiu(), 1, 0.5, true);
							for (Entry<Long, NiuniuPlayerInfo> npientry : pppppp.all_bet_player_info.entrySet()) {
								rate_ret_pool = npientry.getValue().bet_coin / all_coin_pool;
								long give_poo = (long)(poo * rate_ret_pool);
								
								if (give_poo > max_pool_coin) {
									max_pool_coin = give_poo;
									max_pool_player_id = npientry.getKey();
								}
								
								pool_coin_map.put(npientry.getKey(), give_poo);
							}
						}
					}
				}
			}
		}

		// 结算庄家
		long pool_in_coin = 0L;
		if (_cur_banker.player_id > 0) {
			if (z_coin_total >= 0) {
				pool_in_coin += (long)(z_coin_total * 0.01);
				z_coin_total = (long)(z_coin_total * 0.94);
			}
			real_modify_money(_cur_banker.player_id, z_coin_total , _cur_banker, z_pool_coin_get);
		}
		
		// 结算玩家
		List<Entry<Long, Long>> lists = new FastList<Map.Entry<Long,Long>>();
		for (Entry<Long, Long> en : static_map.entrySet()) {
			long pl_id = en.getKey();
			long money = en.getValue();
			if (money > 0) {
				pool_in_coin += (long)(money * 0.01);
				money = (long)(money * 0.94);
			}
			en.setValue(money);
			
			long ext_add_coin = 0;
			if (pool_coin_map.containsKey(pl_id))
				ext_add_coin = pool_coin_map.get(pl_id);
			real_modify_money(pl_id, money, null, ext_add_coin);
			lists.add(en);
		}
		
		if (pool_in_coin > 0) {
			GamePoolManager.getInstance().addPoolReward(Consts.getNiuNiu(), 1, pool_in_coin, 1);
		}
		
		Collections.sort(lists, new Comparator<Entry<Long, Long>>() {
			@Override
			public int compare(Entry<Long, Long> o1, Entry<Long, Long> o2) {
				if (o1.getValue() > o2.getValue())
					return -1;
				else if (o1.getValue() == o2.getValue())
					return 0;
				else
					return 1;
			}
		});
		
		List<NNPlayerInfo> win_top_3 = new ArrayList<NNPlayerInfo>();
		for (Entry<Long, Long> en : lists) {
			if (win_top_3.size() >= 3)
				break;
			
			NNPlayerInfo topp = fillPlayerInfo(en.getKey());
			topp.setWinCoin(en.getValue());
			win_top_3.add(topp);
		}
		
		NNPlayerInfo lose_top_1 = new NNPlayerInfo();
		if (!lists.isEmpty()) {
			Entry<Long, Long> lose_entry = lists.get(lists.size() - 1);
			lose_top_1 = fillPlayerInfo(lose_entry.getKey());
			lose_top_1.setWinCoin(lose_entry.getValue());
		}
		
		
		// 填充协议部分
		
		// 1. 庄家对每个池子的输赢
		NNBetPool z_status = new NNBetPool();
		for (NiuniuPoolInfo pi111 : m_pool_info) {
			if (pi111.total_earn_coin != 0) {
				z_status.getPoolId().add(pi111.id);
				if (!pi111.win) {
					z_status.getPoolCoin().add(pi111.total_earn_coin);
				} else {
					z_status.getPoolCoin().add(-pi111.total_earn_coin);
				}
			}
		}
		
		// 2. 座位上的玩家的情况
		List<NNPlayerOpenResults> z_lsits = new ArrayList<NNPlayerOpenResults>();
		for (long player_id : pos) {
			if (player_id > 0) {
				NNPlayerOpenResults rrros = buildPlayerOpenRes(player_id, pool_coin_map);
				z_lsits.add(rrros);
			}
		}
		
		// 记录完成情况
		NNBetResult resnn = new NNBetResult();
		for (NiuniuPoolInfo pi111 : m_pool_info) {
			resnn.getWin().add(pi111.win ? 1 : 0);
		}
		
		// 3. 计算自己的赢取并发送
		for (PlayerInfo pi : player_infos.values()) {
			PlayerConnection con = PlayerConManager.getInstance().getCon(pi.player_id);
			if (con != null) {
				GDL_G2C_NNStateChange change = new GDL_G2C_NNStateChange();
				change.setState(2);
				change.setNextStartDuration((int)getNextOpenTime());
				change.setBankerCoin(_cur_banker.cur_total);

				NNPlayerOpenResults my_res = buildPlayerOpenRes(pi.player_id, pool_coin_map);
				NNOpenResults res = new NNOpenResults();
				res.setBankerCS(z_cardstraight);   //庄家 牌型
				res.setBetCS(pool_card_straight);  //下注池 牌型
				res.setBankerBetPools(z_status);   //庄家对每个池子的输赢
				res.setSitPlayerBetPools(z_lsits); //座位上的对每个池子的赢
				res.setWinners(win_top_3);
				res.setPoolwin(resnn);
				res.setLoser(lose_top_1);
				res.setMybetPools(my_res);		   //本人对每个池子的输赢
				if (_cur_banker.player_id == pi.player_id)
					my_res.setBetPools(z_status);
				
				change.setOpenResults(res);
				con.directSendPack(change);
			}
		}
		
		_near_bet_result.add(resnn);
		if (_near_bet_result.size() > 10)
			_near_bet_result.remove(0);
	}

	private NNPlayerOpenResults buildPlayerOpenRes(long player_id, FastMap<Long, Long> pool_coin_map) {
		PlayerConnection con = PlayerConManager.getInstance().getCon(player_id);
		NNPlayerOpenResults player_open_res = new NNPlayerOpenResults();
		NNBetPool player_bet_pool_res = new NNBetPool();
		player_open_res.setPlayerId(player_id);
		player_open_res.setBetPools(player_bet_pool_res);
		long p_coin = 0L;
		if (pool_coin_map.containsKey(player_id))
			p_coin = pool_coin_map.get(player_id);
		player_open_res.setPool_coin(p_coin);
		if (con != null)
			player_open_res.setCoin(con.getPlayer().getCoin());
		
		for (NiuniuPoolInfo pi111 : m_pool_info) {
			long get_co111 = pi111.getGetCoin(player_id);
			if (get_co111 != 0) {
				player_bet_pool_res.getPoolId().add(pi111.id);
				if (pi111.win) {
					player_bet_pool_res.getPoolCoin().add(get_co111);
				} else {
					player_bet_pool_res.getPoolCoin().add(-get_co111);
				}
			}
		}
		
		return player_open_res;
	}
	
	public static class NiuniuPlayerInfo {
		public long bet_coin;
		public long get_coin;
	}
	
	public static class NiuniuPoolInfo {
		public NiuniuPoolInfo(int id) {
			this.id = id;
		}
		
		int id;
		long total_bet_coin;
		long total_earn_coin;
		FastMap<Long, NiuniuPlayerInfo> all_bet_player_info = new FastMap<Long, NiuniuPlayerInfo>();
		boolean win = false;
		NNCardsStraight straight = null;
		
		public void clearPlayerBet(long pid) {
			if (!all_bet_player_info.containsKey(pid))
				return ;
			
			NiuniuPlayerInfo npi = all_bet_player_info.get(pid);
			total_bet_coin -= npi.bet_coin;
			all_bet_player_info.remove(pid);
		}
		
		public void clear() {
			total_bet_coin = 0;
			total_earn_coin = 0;
			all_bet_player_info.clear();
			win = false;
			straight = null;
		}
		
		public void addCoin(long pid, int coin) {
			NiuniuPlayerInfo self = null;
			if (all_bet_player_info.containsKey(pid))
				self = all_bet_player_info.get(pid);
			else{
				self = new NiuniuPlayerInfo();
				all_bet_player_info.put(pid, self);
			}
			
			self.bet_coin += coin;
			total_bet_coin += coin;
		}
		
		public long getCoin(long pid) {
			if (!all_bet_player_info.containsKey(pid))
				return 0;
			
			NiuniuPlayerInfo self = all_bet_player_info.get(pid);
			return self.bet_coin;
		}
		
		public long getGetCoin(long pid) {
			if (!all_bet_player_info.containsKey(pid))
				return 0;
			
			NiuniuPlayerInfo self = all_bet_player_info.get(pid);
			return self.get_coin;
		}
		
		public long getTotalCoin() {
			return total_bet_coin;
		}
		
		public long calcEveryOne(int rate, Map<Long, Long> static_map) {
			long ret = 0L;
			for (long pid : all_bet_player_info.keySet()) {
				NiuniuPlayerInfo self = all_bet_player_info.get(pid);
				self.get_coin = rate * self.bet_coin;
				ret += self.get_coin;
				
				long old_v = 0;
				if (static_map.containsKey(pid))
					old_v = static_map.get(pid);
				if (win)
					old_v += self.get_coin;
				else
					old_v -= self.get_coin;
				static_map.put(pid, old_v);
			}
			
			return ret;
		}
	}
	
	private static void real_modify_money(long pid, long money, BankerInfo curBanker, long ext_add_money) {
		PlayerConnection con = PlayerConManager.getInstance().getCon(pid);
		
		int item_ying_event = Consts.getItemEventNNxianyin();
		int item_shu_event = Consts.getItemEventNNxianshu();
		if (curBanker != null) {
			item_ying_event = Consts.getItemEventNNzhuangyin();
			item_shu_event = Consts.getItemEventNNzhuangshu();
		}
		
		boolean win = money >= 0;
		long add_money = Math.abs( money );
		long dec_money = Math.abs( money );
		if (win) {
			if (curBanker != null) {
				long max_eran = curBanker.coin_total * 2;
				if (curBanker.cur_total + add_money > max_eran) {
					add_money = max_eran - curBanker.cur_total;
				}
			}
		} else {
			if (curBanker != null) {
				if (dec_money > curBanker.cur_total) {
					dec_money = curBanker.cur_total;
				}
			} else {
				long player_coin = con.getPlayer().getCoin();
				if (dec_money >= player_coin)
					dec_money = player_coin;
			}
		}
		
		if (curBanker != null) {
			if (win) {
				curBanker.cur_total += add_money;
			}
			else {
				curBanker.cur_total -= dec_money;
			}
			curBanker.num++;
		}
		
		if (con != null) {
			if (win) {
				con.getPlayer().getItemData().addItem(con, Consts.getCOIN_ID(),
						add_money, item_ying_event);
				con.getPlayer().getItemData().addItem(con, Consts.getEXP_ID(), add_money + ext_add_money, item_ying_event);
			} else {
				con.getPlayer().getItemData().decItemByTempId(con, Consts.getCOIN_ID(), 
						dec_money, item_shu_event);
			}
		} else {
			/*
			if (win) {
				DbMgr.getInstance().getDbByPlayerId(pid)
					.Execute("update item set number=number+? where player_id=? and table_id=?", 
							add_money, pid, Consts.getCOIN_ID());
			} else {
				DbMgr.getInstance().getDbByPlayerId(pid)
				.Execute("update item set number=number-if(number>=?, ?, number) where player_id=? and table_id=?", 
						dec_money, dec_money, pid, Consts.getCOIN_ID());
			}
			*/
		}
	}
	
	FastList<NiuniuPoolInfo> m_pool_info = new FastList<NiuniuPoolInfo>();
	
	public long getPlayerTotalMoney(long pid) {
		long total = 0;
		
		for (NiuniuPoolInfo nn : m_pool_info) {
			total += nn.getCoin(pid);
		}
		
		return total;
	}
	
	public static class PlayerInfo {
		long player_id;
		long join_time;
		int pos;
		long money_calc;
		boolean off_line = false;
		
		public void flushPos(int p) {
			this.pos = p;
		}
		
		public void offline(boolean of) {
			this.off_line = of;
		}
	}
	
	FastMap<Long, PlayerInfo> player_infos = new FastMap<Long, NiuniuGameInfo.PlayerInfo>();
	
	public GDL_G2C_NNPlayerNotSitList buildallPlayer() {
		GDL_G2C_NNPlayerNotSitList re = new GDL_G2C_NNPlayerNotSitList();
		for (PlayerInfo pinfo : player_infos.values()) {
			if (pinfo.pos != -1)
				continue;
			
			PlayerConnection pcon = PlayerConManager.getInstance().getCon(pinfo.player_id);
			if (pcon == null)
				continue;
			
			NNPlayerInfo ngp = fillPlayerInfo(pinfo.player_id);
			re.getLists().add(ngp);
		}
		return re;
	}
	
	public void playerOffline(long pid) {
		player_infos.get(pid).offline(true);
	}
	
	public void cachePlayerMoney(long pid, long coin) {
		PlayerInfo pi = player_infos.get(pid);
		if (pi != null) {
			pi.money_calc += coin;
		}
	}
	
	public long getPlayerCacheMoney(long pid) {
		if (player_infos.containsKey(pid))
			return player_infos.get(pid).money_calc;
		
		return 0L;
	}
	
	public int getInstanceId() {
		return gameUid;
	}

	public void setGameUID(int gameUid) {
		this.gameUid = gameUid;
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public int getLevelId() {
		return GameInstanceMrg.getLevelId(this.gameUid);
	}
	
	public void broadcast(JsonPacket msg) {
		PlayerConManager.getInstance().broadcastMsg(msg, player_infos.keySet());
	}
	
	public void playerBetOn(PlayerConnection con, int poolidx, int coin) {
		m_pool_info.get(poolidx).addCoin(con.getPlayerId(), coin);
	}
	
	public int standUp(PlayerConnection con) {
		PlayerInfo p = player_infos.get(con.getPlayerId());
		if (p == null)
			return -1;
		
		for (int i = 0; i < pos.length; ++i) {
			if (pos[i] == con.getPlayerId()) {
				pos[i] = 0;
				p.flushPos(0);
				return i;
			}
		}
		
		return -1;
	}
	
	public int sitDown(PlayerConnection con) {
		PlayerInfo p = player_infos.get(con.getPlayerId());
		if (p == null)
			return -1;
		
		if (p.pos != -1)
			return -1;
		
		int new_pos = trySitPos(con);
		if (new_pos == -1)
			return -1;
		
		p.flushPos(new_pos);
		return new_pos;
	}
	
	// 尝试找坐下的位置
	public int trySitPos(PlayerConnection con) {
		if (con.getPlayer().getItemCountByTempId(Consts.getCOIN_ID()) < Consts.getNiuniu_Sit_Min_Coin())
			return -1;
		
		// 有空位就坐下
		for (int i =0 ; i<pos.length; ++i) {
			if (pos[i] == 0) {
				pos[i] = con.getPlayerId();
				return i;
			}

			// 玩家离线或者钱不够了，我也坐下
			PlayerConnection pcon = PlayerConManager.getInstance().getCon(pos[i]);
			if (pcon == null) {
				pos[i] = con.getPlayerId();
				return i;
			}

			if (pcon.getPlayer().getItemCountByTempId(Consts.getCOIN_ID()) < 10 * 0000) {
				pos[i] = con.getPlayerId();
				return i;
			}
		}
		
		return -1;
	}
	
	BankerInfo _system_bank = new BankerInfo();

	// 开始游戏
	public void beginGame() {
		last_action_time = TimeUtil.GetDateTime();
		status = 0;
		
		_system_bank.player_id = 0;
		_system_bank.coin_total = 88880000;
		_system_bank.cur_total = 88880000;
		_cur_banker = _system_bank;
		
		// 池子初始化
		m_pool_info.add(new NiuniuPoolInfo(0));
		m_pool_info.add(new NiuniuPoolInfo(1));
		m_pool_info.add(new NiuniuPoolInfo(2));
		m_pool_info.add(new NiuniuPoolInfo(3));
		
		// 扑克牌初始化
		for (int i = 1; i <= 52; ++i) {
			_all_suffle_car.add(i);
		}
		Collections.shuffle(_all_suffle_car);
	}
	
	public boolean isSit(long p) {
		for (long pid : pos) {
			if (pid == p)
				return true;
		}
		return false;
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
		if (!isRobot){
			return player_infos.size() >= 100;
		}
		else {
			if (player_infos.size() >= 100)
				return true;
			
			int num = getRobotNum();
			return num >= 24;
		}
	}
	
	public boolean isEmpty() {
		return player_infos.isEmpty();
	}
	
	public long getNextOpenTime() {
		long now_time = TimeUtil.GetDateTime();
		
		if (status == 0)
			return Consts.getNiuniuFreeTime() - (now_time - last_action_time);
		else if (status == 1)
			return Consts.getNiuniuBetTime() - (now_time - last_action_time);
		else
			return Consts.getNiuniuOpenTime() - (now_time - last_action_time);
	}
	
	// 加入游戏
	public void joinGame(PlayerConnection con, int instanceId) {
		int sit_pos = trySitPos(con);
		GDL_G2C_PlayerJoinGame joinmsg = new GDL_G2C_PlayerJoinGame();
		joinmsg.setPlayerId(con.getPlayerId());
		joinmsg.setGameId(Consts.getNiuNiu());
		
		// 把我加入游戏的消息告诉同场其他人
		NNPlayerInfo ngp = fillPlayerInfo(con.getPlayerId(), sit_pos);
		joinmsg.setNplayerInfos(ngp);
		PlayerConManager.getInstance().broadcastMsg(joinmsg, player_infos.keySet());
		
		long now_time = TimeUtil.GetDateTime();
		PlayerInfo pi = new PlayerInfo();
		pi.player_id = con.getPlayerId();
		pi.join_time = now_time;
		pi.pos = sit_pos;
		pi.off_line = false;
		player_infos.put(con.getPlayerId(), pi);
		
		con.getPlayer().setGameUID(instanceId);
		
		GDL_G2C_EnterGameRe re = new GDL_G2C_EnterGameRe();
		re.setGameId(Consts.getNiuNiu());
		re.setInstanceId(instanceId);
		
		
		NNGameStatus nnGameInfo = new NNGameStatus();
		// 注意，如果玩家进入游戏就是开牌状态，那么要调整为休息状态
		int l_status = status;
		long next_time = getNextOpenTime();
		if (l_status == 2) {
			l_status = 0;
			next_time += Consts.getNiuniuFreeTime();
		}
		nnGameInfo.setState(l_status);
		nnGameInfo.setNextStartDuration((int)next_time);
		if (_cur_banker.player_id > 0) {
			nnGameInfo.setBanker(fillPlayerInfo(_cur_banker.player_id));
		}
		nnGameInfo.setBankerCoin(_cur_banker.coin_total);
		
		NNBetPool poolinfo = getPoolCoin();
		nnGameInfo.setBetPools(poolinfo);
		
		for (int i = 0; i < pos.length; ++i) {
			NNPlayerInfo ppi = fillPlayerInfo(pos[i]);
			if (ppi == null)
				continue;
			
			nnGameInfo.getSitPlayerInfos().add(ppi);
		}
		
		re.setBRNNGameInfo(nnGameInfo);
		con.send(re);
	}
	
	public NNPlayerInfo fillPlayerInfo(long pid) {
		return fillPlayerInfo(pid, -99);
	}

	public NNPlayerInfo fillPlayerInfo(long pid, int pos) {
		PlayerBriefInfo pbi = PlayerBriefInfoManager.getInstance().getPlayerBriefInfo(pid);
		if (pbi == null)
			return null;
		
		int cpos = -1;
		if (pos == -99) {
			PlayerInfo pinfo = player_infos.get(pid);
			if (pinfo == null)
				return null;
			cpos = pinfo.pos;
		} else {
			cpos = pos;
		}
		
		NNPlayerInfo ngp = new NNPlayerInfo();
		ngp.setPlayerId(pid);
		ngp.setPos(cpos);
		ngp.setName(pbi.getName());
		ngp.setHeadUrl(pbi.getHeadUrl());
		ngp.setViplevel(pbi.getViplevel());
		ngp.setCoin( pbi.getMoney() );
		return ngp;
	}

	public NNBetPool getPoolCoin() {
		NNBetPool poolinfo = new NNBetPool();
		for (NiuniuPoolInfo pinfo : m_pool_info) {
			poolinfo.getPoolId().add(pinfo.id);
			poolinfo.getPoolCoin().add(pinfo.total_bet_coin);
		}
		return poolinfo;
	}
	
	private void clearPlayerBet(long player_id) {
		for (NiuniuPoolInfo po : m_pool_info) {
			po.clearPlayerBet(player_id);
		}
	}
	
	public void leaveGame(PlayerConnection con) {
		long pid = con.getPlayerId();
		player_infos.remove(pid);
		
		if (status == 1) {
			clearPlayerBet(pid);
			
			if (_cur_banker.player_id == pid) {
				_cur_banker = _system_bank;
				GDL_G2C_NNBankerChange change = new GDL_G2C_NNBankerChange();
				broadcast(change);
			}
		}
		
		for (BankerInfo b : _bankers) {
			if (b.player_id == pid) {
				_bankers.remove(b);
				break;
			}
		}
		
		for (int i = 0; i < pos.length; ++i) {
			if (pos[i] == pid) {
				pos[i] = 0;
			}
		}
		
		con.getPlayer().setGameUID(0);
		
		GDL_G2C_PlayerLeaveGame leavemsg = new GDL_G2C_PlayerLeaveGame();
		leavemsg.setPlayerId(con.getPlayerId());
		broadcast(leavemsg);
	}
}
