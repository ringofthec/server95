package com.gdl.game;

import gameserver.jsonprotocol.BJLBetPool;
import gameserver.jsonprotocol.BJLBetResult;
import gameserver.jsonprotocol.BJLCardsStraight;
import gameserver.jsonprotocol.BJLGameStatus;
import gameserver.jsonprotocol.BJLOpenResults;
import gameserver.jsonprotocol.BJLPlayerInfo;
import gameserver.jsonprotocol.BJLPlayerOpenResults;
import gameserver.jsonprotocol.BJLSlu;
import gameserver.jsonprotocol.BJLXiaolu;
import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.GDL_G2C_BJLBankerChange;
import gameserver.jsonprotocol.GDL_G2C_BJLNearBetResultRe;
import gameserver.jsonprotocol.GDL_G2C_BJLPlayerNotSitList;
import gameserver.jsonprotocol.GDL_G2C_BJLStateChange;
import gameserver.jsonprotocol.GDL_G2C_BJLZXChange;
import gameserver.jsonprotocol.GDL_G2C_EnterGameRe;
import gameserver.jsonprotocol.GDL_G2C_PlayerJoinGame;
import gameserver.jsonprotocol.GDL_G2C_PlayerLeaveGame;
import gameserver.logging.LogService;

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

import table.base.TableManager;

import com.commons.network.websock.JsonPacket;
import com.commons.network.websock.PlayerConManager;
import com.commons.network.websock.PlayerConnection;
import com.commons.network.websock.handler.ChatHandler;
import com.commons.network.websock.handler.ChatHandler.b;
import com.commons.util.RandomUtil;
import com.commons.util.TimeUtil;
import com.gdl.data.PlayerBriefInfo;
import com.gdl.manager.GamePoolManager;
import com.gdl.manager.PlayerBriefInfoManager;
import commonality.Common.PLAYER_STATE;

public class BaijialeGameInfo {
	
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(BaijialeGameInfo.class);
	
	int gameUid;
	long begin_time;
	long[] pos = new long[8]; 
	int status; // 游戏当前状态 0 等待中 1 下注中 2 闲家咪牌  3 庄家米牌 4 闲家(补牌)咪牌 5 庄家(补牌)咪牌 6 结算 7 切牌
	long last_action_time;
	long top_bet_player_x = 0;
	long top_bet_player_bet_x = 0;
	long top_bet_player_z = 0;
	long top_bet_player_bet_z = 0;
	long qie_card_player_id = 0;
	
	public long getQieCardPlayerId() {
		return qie_card_player_id;
	}
	
	public long getMiCardPlayerZ() {
		return top_bet_player_z;
	}
	
	public long getMiCardPlayerX() {
		return top_bet_player_x;
	}
	
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
			GDL_G2C_BJLBankerChange change = new GDL_G2C_BJLBankerChange();
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
			return false;
		}
		
		// 赢取不能超过坐庄时投入的钱
		if (banker.cur_total >= banker.coin_total * 2) {
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
	
	public void quickSwitch() {
		last_action_time = TimeUtil.GetDateTime() - 100 * 1000;
		trySwitchState();
	}
	
	public void trySwitchState() {
		long time_del = getNextOpenTime();
		
		if (time_del > 0)
			return ;
		
		status++;
		if (isNeedShuffeCard())
			status %= 8;
		else
			status %= 7;
		
		if (x_card_get.size() == 3 && x_card_get.get(2) == 0) {
			if (status == 4) {
				status = 5;
			}
		}
		
		if (z_card_get.size() == 3 && z_card_get.get(2) == 0) {
			if (status == 5) {
				status = 6;
			}
		}
		
		last_action_time = TimeUtil.GetDateTime();
		
		if (status == 2) {
			// 切换到闲家咪牌的时候，马上开牌
			calcOpen();
		} else {
			GDL_G2C_BJLStateChange cha = new GDL_G2C_BJLStateChange();
			cha.setState(status);
			cha.setNextStartDuration((int)getNextOpenTime());
			
			if (status == 7)
				cha.setCardsNum(0);
			else
				cha.setCardsNum(_all_suffle_car.size());
			
			if (status == 1) {
				checkBanker();
				top_bet_player_x = 0L;
				top_bet_player_bet_x = 0L;
				top_bet_player_z = 0L;
				top_bet_player_bet_z = 0L;
				
				if (_cur_banker.player_id > 0) {
					cha.setBanker(fillPlayerInfo(_cur_banker.player_id));
				}
				cha.setBankerCoin(_cur_banker.cur_total);
				
				for (BjlPoolInfo pi : m_pool_info)
					pi.clear();
			} else if (status == 7){
				List<Long> all_qie_ps = new ArrayList<Long>();
				for (long pi : pos) {
					if (pi > 0)
						all_qie_ps.add(pi);
				}
				
				if (!all_qie_ps.isEmpty()) {
					qie_card_player_id = all_qie_ps.get(RandomUtil.RangeRandom(0, all_qie_ps.size() - 1));
					cha.setPlayerId(qie_card_player_id);
				}
			}
				
			broadcast(cha);
		}
	}
	
	List<Integer> _all_suffle_car = new ArrayList<Integer>();
	
	List<Integer> _zhupan_lu = new FastList<Integer>();
	int[][] _da_lu = new int[100][20];
	int _da_lu_x = 0;
	int _da_lu_y = 0;
	int _count = 0;
	int last_result = -1;
	
	int[][] _da_yan_zai = new int[100][20];
	int _da_yan_zai_x = 0;
	int _da_yan_zai_y = 0;
	int last_da_yan_zai_result = -1;
	
	int[][] _xiao_lu = new int[100][20];
	int _xiao_lu_x = 0;
	int _xiao_lu_y = 0;
	int last_xiao_lu_result = -1;
	
	int[][] _yue_you_lu = new int[100][20];
	int _yue_you_lu_x = 0;
	int _yue_you_lu_y = 0;
	int last_yue_you_lu_result = -1;
	
	GDL_G2C_BJLNearBetResultRe bjl_near_bet_result_re = new GDL_G2C_BJLNearBetResultRe();
	BJLOpenResults _last_open_res;
	
	public GDL_G2C_BJLNearBetResultRe getNearRes() {
		return bjl_near_bet_result_re;
	}
	
	private boolean isdaluCountEqual(int x1, int x2) {
		int x1_count = 0;
		for (int i = 0; i < _da_lu[0].length; ++i) {
			if (_da_lu[x1][i] != 0)
				x1_count++;
			else
				break;
		}
		
		int x2_count = 0;
		for (int i = 0; i < _da_lu[0].length; ++i) {
			if (_da_lu[x2][i] != 0)
				x2_count++;
			else
				break;
		}
		
		return x1_count == x2_count;
	}
	
	List<Integer> z_card_get = new ArrayList<Integer>();
	List<Integer> x_card_get = new ArrayList<Integer>();
	private void calcOpen() {
		
		while (true) {
			// 1. 拿四张牌
			z_card_get.clear();
			x_card_get.clear();
			boolean is_re_flush_card = reShuffleCard();
			
			_count ++;
			
			for (int i = 0; i < 4; ++i) {
				int temp_card = getOneCard();
				if (i % 2 == 0)
					z_card_get.add(temp_card);
				else
					x_card_get.add(temp_card);
			}
			// 计算点数，计算对子出现情况
			int z_p = BaijialeGen2.calcPoint(z_card_get.get(0), z_card_get.get(1));
			int x_p = BaijialeGen2.calcPoint(x_card_get.get(0), x_card_get.get(1));
			boolean z_d = BaijialeGen2.hasDouble(z_card_get.get(0), z_card_get.get(1));
			boolean x_d = BaijialeGen2.hasDouble(x_card_get.get(0), x_card_get.get(1));

			// 2. 计算是否补牌
			int x_bu = 0;
			int z_bu = 0;
			int bupai_starg = TableManager.GetInstance().getBJLaddCardStrage(z_p, x_p);
			switch (bupai_starg) {
			case 0:
				// 不补牌
				break;

			case 1:
				// 庄补
				z_bu = getOneCard();
				break;

			case 2:
				// 闲补
				x_bu = getOneCard();
				break;

			case 3:
				// 都补
				x_bu = getOneCard();
				z_bu = getOneCard();
				break;

			case 4:
				// 闲家补牌是8,不补牌，否则补牌
				x_bu = getOneCard();
				if (x_bu == 8)
					break;

				z_bu = getOneCard();
				break;

			case 5:
				// 闲家补牌是0,1,8,9，不补牌，否则补
				x_bu = getOneCard();
				if (x_bu == 0 ||
						x_bu == 1 ||
						x_bu == 8 ||
						x_bu == 9 ) 
					break;

				z_bu = getOneCard();
				break;

			case 6:
				// 闲家补牌是0,1,2,3,8,9，不补牌，否则补
				x_bu = getOneCard();
				if (x_bu == 0 ||
						x_bu == 1 ||
						x_bu == 2 ||
						x_bu == 3 ||
						x_bu == 8 ||
						x_bu == 9 ) 
					break;

				z_bu = getOneCard();
				break;

			case 7:
				// 闲家补牌是0，1,2,3,4,5,8,9，不补牌，否则补
				x_bu = getOneCard();
				if (x_bu == 0 ||
						x_bu == 1 ||
						x_bu == 2 ||
						x_bu == 3 ||
						x_bu == 4 ||
						x_bu == 5 ||
						x_bu == 8 ||
						x_bu == 9 ) 
					break;

				z_bu = getOneCard();
				break;

			default:
				break;
			}

			z_card_get.add(z_bu);
			x_card_get.add(x_bu);

			z_p = BaijialeGen2.calcPoint(z_card_get.get(0), z_card_get.get(1), z_card_get.get(2));
			x_p = BaijialeGen2.calcPoint(x_card_get.get(0), x_card_get.get(1), x_card_get.get(2));

			// 记录完成情况
			BJLBetResult resnn = new BJLBetResult();
			resnn.setDalu(new BJLSlu());
			resnn.setDayanzai(new BJLSlu());
			resnn.setXiaolu(new BJLSlu());
			resnn.setYueyoulu(new BJLSlu());
						
			// 得出结论
			List<Integer> res = new ArrayList<Integer>();
			if (z_d)
				res.add(2);
			if (x_d)
				res.add(3);

			int cur_res = 0;
			if (z_p == x_p) {
				// 和
				if (_count == 1) {
					// 第一把，一定不能和牌，退牌并重新发牌
					for (int x : x_card_get) {
						if (x > 0)
							_all_suffle_car.add(x);
					}

					for (int y : z_card_get){
						if (y > 0)
							_all_suffle_car.add(y);
					}
					
					_count --;
					continue;
				}

				cur_res = 4;
				res.add(4);
				
			} else if (z_p > x_p) {
				// 庄赢
				cur_res = 0;
				res.add(0);
			} else if (z_p < x_p) {
				// 闲赢
				cur_res = 1;
				res.add(1);
			}
			// 记录珠盘路
			int zhu_pan_res = 0;
			if (res.size() == 3) {
				zhu_pan_res = res.get(0) * 100 + res.get(1) * 10 + res.get(2);
			} else if (res.size() == 2) {
				zhu_pan_res = res.get(0) * 10 + res.get(1);
			} else {
				zhu_pan_res = res.get(0);
			}
			_zhupan_lu.add(zhu_pan_res);
			bjl_near_bet_result_re.getZhupanlu().add(zhu_pan_res);
			resnn.setZhupanlu(zhu_pan_res);
			
			// 记录大路
			if (cur_res == 4) {
				int now_now = _da_lu[_da_lu_x][_da_lu_y - 1];
				int final_res = now_now + 1;
				_da_lu[_da_lu_x][_da_lu_y - 1] = final_res;
				int lastIdx = bjl_near_bet_result_re.getDalu().size() - 1;
				bjl_near_bet_result_re.getDalu().remove(lastIdx);
				bjl_near_bet_result_re.getDalu().add(final_res);
				resnn.getDalu().setX(_da_lu_x);
				resnn.getDalu().setY(_da_lu_y - 1);
				resnn.getDalu().setRes(final_res);
				
				LogService.sysErr(1, "dalu x: " + _da_lu_x + ", y: " + (_da_lu_y - 1) + ", res : " + final_res, 1);
			} else {	
				if (last_result != -1 && last_result != cur_res) {
					// 上一次赢是闲赢,要另开一列啦
					_da_lu_x++;
					_da_lu_y = 0;
				}
				
				int final_res = cur_res == 0 ? 10 : 60;
				_da_lu[_da_lu_x][_da_lu_y] = final_res;
				bjl_near_bet_result_re.getDalu().add(final_res);
				resnn.getDalu().setX(_da_lu_x);
				resnn.getDalu().setY(_da_lu_y);
				resnn.getDalu().setRes(final_res);
				
				LogService.sysErr(1, "dalu x: " + _da_lu_x + ", y: " + _da_lu_y + ", res : " + final_res, 1);
				
				// 计算大眼仔
				if (_da_lu[1][1] != 0 || _da_lu[2][0] != 0) {
					boolean isread = false;
					if (_da_lu_y == 0) {
						// 路头
						if (!isdaluCountEqual(_da_lu_x - 1, _da_lu_x - 2)) {
							isread = false;
							LogService.sysErr(1, "lutou1", 1);
						} else {
							isread = true;
							LogService.sysErr(1, "lutou2", 1);
						}
					} else {
						// 路中
						if (_da_lu[_da_lu_x - 1][_da_lu_y] != 0) {
							isread = true;
							LogService.sysErr(1, "luz1", 1);
						} else {
							if (_da_lu[_da_lu_x - 1][_da_lu_y - 1] == 0) {
								isread = true;
								LogService.sysErr(1, "luz2", 1);
							}
							else {
								isread = false;
								LogService.sysErr(1, "luz3", 1);
							}
						}
					}
					
					int res_ll = isread ? 1 : 2;
					if (last_da_yan_zai_result != -1 && res_ll != last_da_yan_zai_result) {
						_da_yan_zai_x ++;
						_da_yan_zai_y = 0;
						bjl_near_bet_result_re.getDayanzai().add(new BJLXiaolu());
					}
					
					if (last_da_yan_zai_result == -1) {
						bjl_near_bet_result_re.getDayanzai().add(new BJLXiaolu());
					}
					
					LogService.sysErr(1, "dayanzai x: " + _da_yan_zai_x + ", y: " + _da_yan_zai_y + ", res : " + res_ll, 1);
					_da_yan_zai[_da_yan_zai_x][_da_yan_zai_y] = res_ll;
					int last_idx_da_yan_zai = bjl_near_bet_result_re.getDayanzai().size() - 1;
					if (last_idx_da_yan_zai >= 0)
						bjl_near_bet_result_re.getDayanzai().get(last_idx_da_yan_zai).getRes().add(res_ll);
					resnn.getDayanzai().setX(_da_yan_zai_x);
					resnn.getDayanzai().setY(_da_yan_zai_y);
					resnn.getDayanzai().setRes(res_ll);
					_da_yan_zai_y ++;
					last_da_yan_zai_result = res_ll;
				}
				
				// 计算小路
				if (_da_lu[2][1] != 0 || _da_lu[3][0] != 0) {
					boolean isread = false;
					if (_da_lu_y == 0) {
						// 路头
						if (!isdaluCountEqual(_da_lu_x - 1, _da_lu_x - 3)) {
							isread = false;
						} else {
							isread = true;
						}
					} else {
						// 路中
						if (_da_lu[_da_lu_x - 2][_da_lu_y] != 0) {
							isread = true;
						} else {
							if (_da_lu[_da_lu_x - 2][_da_lu_y - 1] == 0)
								isread = true;
							else
								isread = false;
						}
					}
					
					int res_ll = isread ? 1 : 2;
					if (last_xiao_lu_result != -1 && res_ll != last_xiao_lu_result) {
						_xiao_lu_x ++;
						_xiao_lu_y = 0;
						bjl_near_bet_result_re.getXiaolu().add(new BJLXiaolu());
					}
					
					if (last_xiao_lu_result == -1) {
						bjl_near_bet_result_re.getXiaolu().add(new BJLXiaolu());
					}
					
					_xiao_lu[_xiao_lu_x][_xiao_lu_y] = res_ll;
					int last_idx_xiao_lu = bjl_near_bet_result_re.getXiaolu().size() - 1;
					if (last_idx_xiao_lu >= 0)
						bjl_near_bet_result_re.getXiaolu().get(last_idx_xiao_lu).getRes().add(res_ll);
					resnn.getXiaolu().setX(_xiao_lu_x);
					resnn.getXiaolu().setY(_xiao_lu_y);
					resnn.getXiaolu().setRes(res_ll);
					_xiao_lu_y ++;
					last_xiao_lu_result = res_ll;
				}
				
				// 计算曱甴路
				if (_da_lu[3][1] != 0 || _da_lu[4][0] != 0) {
					boolean isread = false;
					if (_da_lu_y == 0) {
						// 路头
						if (!isdaluCountEqual(_da_lu_x - 1, _da_lu_x - 4)) {
							isread = false;
						} else {
							isread = true;
						}
					} else {
						// 路中
						if (_da_lu[_da_lu_x - 3][_da_lu_y] != 0) {
							isread = true;
						} else {
							if (_da_lu[_da_lu_x - 3][_da_lu_y - 1] == 0)
								isread = true;
							else
								isread = false;
						}
					}
					
					int res_ll = isread ? 1 : 2;
					if (last_yue_you_lu_result != -1 && res_ll != last_yue_you_lu_result) {
						_yue_you_lu_x ++;
						_yue_you_lu_y = 0;
						bjl_near_bet_result_re.getYueyoulu().add(new BJLXiaolu());
					}
					
					if (last_yue_you_lu_result == -1) {
						bjl_near_bet_result_re.getYueyoulu().add(new BJLXiaolu());
					}
					
					
					_yue_you_lu[_yue_you_lu_x][_yue_you_lu_y] = res_ll;
					int last_idx_yue_you_lu = bjl_near_bet_result_re.getYueyoulu().size() - 1;
					if (last_idx_yue_you_lu >= 0)
						bjl_near_bet_result_re.getYueyoulu().get(last_idx_yue_you_lu).getRes().add(res_ll);
					resnn.getYueyoulu().setX(_yue_you_lu_x);
					resnn.getYueyoulu().setY(_yue_you_lu_y);
					resnn.getYueyoulu().setRes(res_ll);
					_yue_you_lu_y ++;
					last_yue_you_lu_result = res_ll;
				}
				
				_da_lu_y ++;
				last_result = cur_res;
			}
			// 2. 庄家的牌型
			BJLCardsStraight z_cardstraight = new BJLCardsStraight();
			z_cardstraight.setCards(z_card_get);

			// 5. 庄家和5个池子的结算
			Map<Long, Long> static_map = new HashMap<Long, Long>();
			BJLCardsStraight x_cardstraight = new BJLCardsStraight();
			x_cardstraight.setCards(x_card_get);

			Map<BjlPoolInfo, Long> z_coin_map = new HashMap<BjlPoolInfo, Long>();

			if (cur_res == 4) {
				// 和要特别处理
				
				// 庄闲都认为赢，相当于退钱
				for (int i = 0; i < 2; i++) {
					// 计算每个池子的输赢
					BjlPoolInfo pool_info = m_pool_info.get(i);
					pool_info.win = true;
					// 统计玩家输赢
					pool_info.total_earn_coin = pool_info.calcEveryOne(1, static_map, false);
					// 统计庄家输赢
					z_coin_map.put(pool_info, pool_info.total_bet_coin);
				}
				
				// 2 庄对 3 闲对 4和
				for (int i = 2; i < 5; i++) {
					// 计算每个池子的输赢
					BjlPoolInfo pool_info = m_pool_info.get(i);
					pool_info.win = res.contains(i); // 只有在res中的，才说明获胜，否则就是失败
	
					// 统计玩家输赢
					if (i == 2 || i == 3)
						pool_info.total_earn_coin = pool_info.calcEveryOne(11, static_map, true);
					else
						pool_info.total_earn_coin = pool_info.calcEveryOne(8, static_map, true);
					
					// 统计庄家输赢
					z_coin_map.put(pool_info, pool_info.total_bet_coin);
				}
			} else {
				// 分别检查 0 庄 1 闲 2 庄对 3 闲对 
				for (int i = 0; i < 5; i++) {
					// 计算每个池子的输赢
					BjlPoolInfo pool_info = m_pool_info.get(i);
					pool_info.win = res.contains(i); // 只有在res中的，才说明获胜，否则就是失败
	
					// 统计玩家输赢
					if (i == 2 || i == 3)
						pool_info.total_earn_coin = pool_info.calcEveryOne(11, static_map, true);
					else if (i == 0)
						pool_info.total_earn_coin = pool_info.calcEveryOne(0.95f, static_map, true);
					else
						pool_info.total_earn_coin = pool_info.calcEveryOne(1, static_map, true);
	
					// 统计庄家输赢
					z_coin_map.put(pool_info, pool_info.total_bet_coin);
				}
			}
			// 统计庄家全部池子的总输赢
			long z_coin_total = 0L;
			for (BjlPoolInfo ccc : z_coin_map.keySet()) {
				long money_coin = z_coin_map.get(ccc);
				if (ccc.win)
					z_coin_total -= money_coin;
				else
					z_coin_total += money_coin;
			}

			/*
		// 计算奖池
		long z_pool_coin_get = 0L;
		FastMap<Long, Long> pool_coin_map = new FastMap<Long, Long>();
		for (ResList pa : map_herl.values()) {
			if (pa.type == 13 || 
					pa.type == 12) {
				if (pa.id == z_get_id) {
					if (_cur_banker.player_id > 0) {
						// 庄
						z_pool_coin_get = GamePoolManager.getInstance().checkPoolReward(Consts.getNiuNiu(), 1, 0.5, true);
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
								pool_coin_map.put(npientry.getKey(), give_poo);
							}
						}
					}
				}
			}
		}
			 */
			// 结算庄家
			long pool_in_coin = 0L;
			if (_cur_banker.player_id > 0) {
				if (z_coin_total >= 0) {
					pool_in_coin += (long)(z_coin_total * 0.01);
					z_coin_total = (long)(z_coin_total * 0.95);
				}
				real_modify_money(_cur_banker.player_id, z_coin_total , _cur_banker, 0);
			}

			// 结算玩家
			List<Entry<Long, Long>> lists = new FastList<Map.Entry<Long,Long>>();
			for (Entry<Long, Long> en : static_map.entrySet()) {
				long pl_id = en.getKey();
				long money = en.getValue();
				if (money > 0) {
					pool_in_coin += (long)(money * 0.01);
					money = (long)(money * 1);
				}
				en.setValue(money);

				//long ext_add_coin = pool_coin_map.get(pl_id);
				real_modify_money(pl_id, money, null, 0);
				lists.add(en);
			}
			if (pool_in_coin > 0) {
				GamePoolManager.getInstance().addPoolReward(Consts.getBaijiale(), 1, pool_in_coin, 1);
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
			List<BJLPlayerInfo> win_top_3 = new ArrayList<BJLPlayerInfo>();
			for (Entry<Long, Long> en : lists) {
				if (win_top_3.size() >= 5)
					break;

				BJLPlayerInfo topp = fillPlayerInfo(en.getKey());
				if (topp != null) {
					topp.setWinCoin(en.getValue());
					win_top_3.add(topp);
				}
			}

			BJLPlayerInfo lose_top_1 = new BJLPlayerInfo();
			if (!lists.isEmpty()) {
				Entry<Long, Long> lose_entry = lists.get(lists.size() - 1);
				lose_top_1 = fillPlayerInfo(lose_entry.getKey());
				if (lose_top_1 != null)
					lose_top_1.setWinCoin(lose_entry.getValue());
			}


			// 填充协议部分
			// 1. 庄家对每个池子的输赢
			BJLBetPool z_status = new BJLBetPool();
			for (BjlPoolInfo pi111 : m_pool_info) {
				if (pi111.total_earn_coin != 0) {
					z_status.getPoolId().add(pi111.id);
					if (!pi111.win) {
						z_status.getPoolCoin().add( pi111.total_earn_coin);
					} else {
						z_status.getPoolCoin().add(-pi111.total_earn_coin);
					}
				}
			}

			// 2. 座位上的玩家的情况
			List<BJLPlayerOpenResults> z_lsits = new ArrayList<BJLPlayerOpenResults>();
			for (long player_id : pos) {
				if (player_id > 0) {
					BJLPlayerOpenResults rrros = buildPlayerOpenRes(player_id, null);
					z_lsits.add(rrros);
				}
			}
			_last_open_res = new BJLOpenResults();
			_last_open_res.setShuffle(is_re_flush_card ? 1 : 0);
			_last_open_res.setBankerCS(z_cardstraight);   //庄家 牌型
			_last_open_res.setPlayerCS(x_cardstraight);	  //闲家 牌型
			_last_open_res.setBankerBetPools(z_status);   //庄家对每个池子的输赢
			_last_open_res.setSitPlayerBetPools(z_lsits); //座位上的对每个池子的赢
			_last_open_res.setWinners(win_top_3);
			_last_open_res.setPoolwin(resnn);
			_last_open_res.setLoser(lose_top_1);
			_last_open_res.setPlayer_z(top_bet_player_z);
			_last_open_res.setPlayer_x(top_bet_player_x);
			

			// 3. 计算自己的赢取并发送
			for (PlayerInfo pi : player_infos.values()) {
				PlayerConnection con = PlayerConManager.getInstance().getCon(pi.player_id);
				if (con != null) {
					GDL_G2C_BJLStateChange change = new GDL_G2C_BJLStateChange();
					change.setState(2);
					change.setNextStartDuration((int)getNextOpenTime());
					change.setBankerCoin(_cur_banker.cur_total);
					change.setCardsNum(_all_suffle_car.size());

					BJLPlayerOpenResults my_res = buildPlayerOpenRes(pi.player_id, null);
					BJLOpenResults res111 = new BJLOpenResults();
					res111.setShuffle(is_re_flush_card ? 1 : 0);
					res111.setBankerCS(z_cardstraight);   //庄家 牌型
					res111.setPlayerCS(x_cardstraight);	  //闲家 牌型
					res111.setBankerBetPools(z_status);   //庄家对每个池子的输赢
					res111.setSitPlayerBetPools(z_lsits); //座位上的对每个池子的赢
					res111.setWinners(win_top_3);
					res111.setPoolwin(resnn);
					res111.setLoser(lose_top_1);
					res111.setMybetPools(my_res);		   //本人对每个池子的输赢
					res111.setPlayer_z(top_bet_player_z);
					res111.setPlayer_x(top_bet_player_x);
					if (_cur_banker.player_id == pi.player_id)
						my_res.setBetPools(z_status);

					change.setOpenResults(res111);
					con.directSendPack(change);
					
				}
			}
			break;
		}
	}

	private BJLPlayerOpenResults buildPlayerOpenRes(long player_id, FastMap<Long, Long> pool_coin_map) {
		PlayerConnection con = PlayerConManager.getInstance().getCon(player_id);
		BJLPlayerOpenResults player_open_res = new BJLPlayerOpenResults();
		BJLBetPool player_bet_pool_res = new BJLBetPool();
		player_open_res.setPlayerId(player_id);
		player_open_res.setBetPools(player_bet_pool_res);
		if (pool_coin_map != null)
			player_open_res.setPool_coin(pool_coin_map.get(player_id));
		if (con != null)
			player_open_res.setCoin(con.getPlayer().getCoin());
		
		for (BjlPoolInfo pi111 : m_pool_info) {
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
	
	public static class BjlPoolInfo {
		public BjlPoolInfo(int id) {
			this.id = id;
		}
		
		int id;
		long total_bet_coin;
		long total_earn_coin;
		FastMap<Long, NiuniuPlayerInfo> all_bet_player_info = new FastMap<Long, NiuniuPlayerInfo>();
		boolean win = false;
		BJLCardsStraight straight = null;
		
		public Entry<Long, NiuniuPlayerInfo> sort(FastMap<Long, PlayerInfo> player_infos) {
			if (all_bet_player_info.isEmpty())
				return null;
			
			List<Entry<Long, NiuniuPlayerInfo>> _sort = new ArrayList<Map.Entry<Long,NiuniuPlayerInfo>>();
			for (Entry<Long, NiuniuPlayerInfo> en : all_bet_player_info.entrySet()) {
				if (en.getValue().bet_coin <= 0)
					continue ;
				
				PlayerInfo ppooo = player_infos.get(en.getKey());
				if (ppooo == null)
					continue;
				
				if (ppooo.pos == -1)
					continue;
				
				_sort.add(en);
			}
			
			if (_sort.isEmpty())
				return null;

			Collections.sort(_sort, new Comparator<Entry<Long, NiuniuPlayerInfo>>() {
				@Override
				public int compare(Entry<Long, NiuniuPlayerInfo> o1, Entry<Long, NiuniuPlayerInfo> o2) {
					if (o1.getValue().bet_coin > o2.getValue().bet_coin)
						return -1;
					else if (o1.getValue().bet_coin < o2.getValue().bet_coin)
						return 1;
					else
						return 0;
				}
			});
			
			return _sort.get(0);
		}
		
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
		
		public long unbet(long pid) {
			NiuniuPlayerInfo self = null;
			if (all_bet_player_info.containsKey(pid))
				self = all_bet_player_info.get(pid);
			else{
				self = new NiuniuPlayerInfo();
				all_bet_player_info.put(pid, self);
			}
			
			total_bet_coin -= self.bet_coin;
			long eee = self.bet_coin;
			self.bet_coin = 0;
			return eee;
		}
		
		public void addCoin(long pid, long coin) {
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
		
		public long calcEveryOne(float rate, Map<Long, Long> static_map, boolean reback) {
			long ret = 0L;
			for (long pid : all_bet_player_info.keySet()) {
				NiuniuPlayerInfo self = all_bet_player_info.get(pid);
				if (!win)
					rate = 1;
				self.get_coin = (long)(rate * self.bet_coin);
				ret += self.get_coin;
				
				long temp_coin = self.get_coin;
				if (reback) {
					ret += self.bet_coin;
					temp_coin = self.get_coin + self.bet_coin;
				} else {
					self.get_coin = 0;
				}
				
				long old_v = 0;
				if (static_map.containsKey(pid))
					old_v = static_map.get(pid);
				if (win)
					old_v += temp_coin;
				else
					old_v -= self.get_coin;
				static_map.put(pid, old_v);
			}
			
			return ret;
		}
	}
	
	private static void real_modify_money(long pid, long money, BankerInfo curBanker, long ext_add_money) {
		PlayerConnection con = PlayerConManager.getInstance().getCon(pid);
		
		int item_ying_event = Consts.getItemEventBJLxianyin();
		int item_shu_event = Consts.getItemEventBJLxianshu();
		if (curBanker != null) {
			item_ying_event = Consts.getItemEventBJLzhuangyin();
			item_shu_event = Consts.getItemEventBJLzhuangshu();
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
			} 
			/*
			else {
				con.getPlayer().getItemData().decItemByTempId(con, Consts.getCOIN_ID(), 
						dec_money, item_shu_event);
			}*/
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
	
	FastList<BjlPoolInfo> m_pool_info = new FastList<BjlPoolInfo>();
	
	public long getPlayerTotalMoney(long pid) {
		long total = 0;
		
		for (BjlPoolInfo nn : m_pool_info) {
			total += nn.getCoin(pid);
		}
		
		return total;
	}
	
	public static class PlayerInfo {
		long player_id;
		long join_time;
		int pos = -1;
		long money_calc;
		boolean off_line = false;
		
		public void flushPos(int p) {
			this.pos = p;
		}
		
		public void offline(boolean of) {
			this.off_line = of;
		}
	}
	
	FastMap<Long, PlayerInfo> player_infos = new FastMap<Long, BaijialeGameInfo.PlayerInfo>();
	
	public GDL_G2C_BJLPlayerNotSitList buildallPlayer() {
		GDL_G2C_BJLPlayerNotSitList re = new GDL_G2C_BJLPlayerNotSitList();
		for (PlayerInfo pinfo : player_infos.values()) {
			if (pinfo.pos != -1)
				continue;
			
			PlayerConnection pcon = PlayerConManager.getInstance().getCon(pinfo.player_id);
			if (pcon == null)
				continue;
			
			BJLPlayerInfo ngp = fillPlayerInfo(pinfo.player_id);
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
	
	public void playerUnBetOn(PlayerConnection con) {
		long pid = con.getPlayerId();
		
		long ret_coin = 0L;
		for (BjlPoolInfo bpi : m_pool_info) {
			ret_coin += bpi.unbet(pid);
		}
		con.getPlayer().getItemData().addItem(con, Consts.getCOIN_ID(), ret_coin, 0);
		
		if (top_bet_player_z == pid) {
			Entry<Long, NiuniuPlayerInfo> nop = m_pool_info.get(0).sort(player_infos);
			if (nop == null) {
				top_bet_player_bet_z = 0;
				top_bet_player_z = 0;
			} else {
				top_bet_player_bet_z = nop.getValue().bet_coin;
				top_bet_player_z = nop.getKey();
			}
			
			sendXZChange();
		}
		
		if (top_bet_player_x == pid) {
			Entry<Long, NiuniuPlayerInfo> nop = m_pool_info.get(1).sort(player_infos);
			if (nop == null) {
				top_bet_player_bet_x = 0;
				top_bet_player_x = 0;
			} else {
				top_bet_player_bet_x = nop.getValue().bet_coin;
				top_bet_player_x = nop.getKey();
			}
			
			sendXZChange();
		}
	}
	
	public boolean playerBetOn(PlayerConnection con, int poolidx, long coin) {
		long pid = con.getPlayerId();
		m_pool_info.get(poolidx).addCoin(pid, coin);
		long bet = m_pool_info.get(poolidx).getCoin(pid);
		
		if (poolidx == 0 || poolidx == 1) {
			PlayerInfo p = player_infos.get(pid);
			if (p == null)
				return false;

			boolean send_change = false;
			if (p.pos != -1) {
				if (poolidx == 0) {
					if (bet > top_bet_player_bet_z) {
						top_bet_player_bet_z = bet;
						top_bet_player_z = pid;
						send_change = true;
					}
				} else {
					if (bet > top_bet_player_bet_x) {
						top_bet_player_bet_x = bet;
						top_bet_player_x = pid;
						send_change = true;
					}
				}
			}
			
			return send_change;
		}
		
		return false;
	}

	public void sendXZChange() {
		GDL_G2C_BJLZXChange chan = new GDL_G2C_BJLZXChange();
		chan.setPlayer_x(top_bet_player_x);
		chan.setPlayer_z(top_bet_player_z);
		broadcast(chan);
	}
	
	public int standUp(PlayerConnection con) {
		PlayerInfo p = player_infos.get(con.getPlayerId());
		if (p == null)
			return -1;
		
		for (int i = 0; i < pos.length; ++i) {
			if (pos[i] == con.getPlayerId()) {
				pos[i] = 0;
				p.flushPos(-1);
				return i;
			}
		}
		
		return -1;
	}
	
	public int sitDown(PlayerConnection con, int sit) {
		PlayerInfo p = player_infos.get(con.getPlayerId());
		if (p == null)
			return -1;
		
		if (p.pos != -1)
			return -1;
		
		int new_pos = trySitPos(con, sit);
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
	
	public int trySitPos(PlayerConnection con, int sit) {
		if (con.getPlayer().getItemCountByTempId(Consts.getCOIN_ID()) < Consts.getNiuniu_Sit_Min_Coin())
			return -1;
		
		if (pos[sit] == 0) {
			pos[sit] = con.getPlayerId();
			return sit;
		}
		
		return -1;
	}
	
	BankerInfo _system_bank = new BankerInfo();

	// 开始游戏
	public void beginGame(PlayerConnection con) {
		last_action_time = TimeUtil.GetDateTime();
		status = 7;
		
		_system_bank.player_id = 0;
		_system_bank.coin_total = 88880000;
		_system_bank.cur_total = 88880000;
		_cur_banker = _system_bank;
		qie_card_player_id = con.getPlayerId();
		
		// 池子初始化
		m_pool_info.add(new BjlPoolInfo(0));
		m_pool_info.add(new BjlPoolInfo(1));
		m_pool_info.add(new BjlPoolInfo(2));
		m_pool_info.add(new BjlPoolInfo(3));
		m_pool_info.add(new BjlPoolInfo(4));
		
		// 扑克牌初始化
		reShuffleCard();
	}
	
	private boolean isNeedShuffeCard() {
		return _all_suffle_car.size() < 6;
	}
	
	private boolean reShuffleCard() {
		if (_all_suffle_car.size() < 6) {
			// 牌小于6张了，重新发牌
			for (int j = 0; j < 8; j ++) {
				for (int i = 1; i <= 52; ++i) {
					_all_suffle_car.add(i + j * 100);
				}
			}
			
			// 拿掉9张牌
			for (int i = 0; i < 9; ++i)
				getOneCard();
			
			// 重新发牌
			_zhupan_lu.clear();
			for (int[] arr : _da_lu) {
				for (int j = 0; j < arr.length; ++j) {
					arr[j] = 0;
				}
			}
			_da_lu_x = 0;
			_da_lu_y = 0;
			_count = 0;
			last_result = -1;
			
			for (int[] arr : _da_yan_zai) {
				for (int j = 0; j < arr.length; ++j) {
					arr[j] = 0;
				}
			}
			_da_yan_zai_x = 0;
			_da_yan_zai_y = 0;
			last_da_yan_zai_result = -1;
			
			for (int[] arr : _xiao_lu) {
				for (int j = 0; j < arr.length; ++j) {
					arr[j] = 0;
				}
			}
			_xiao_lu_x = 0;
			_xiao_lu_y = 0;
			last_xiao_lu_result = -1;
			
			for (int[] arr : _yue_you_lu) {
				for (int j = 0; j < arr.length; ++j) {
					arr[j] = 0;
				}
			}
			_yue_you_lu_x = 0;
			_yue_you_lu_y = 0;
			last_yue_you_lu_result = -1;
			
			bjl_near_bet_result_re = new GDL_G2C_BJLNearBetResultRe();
			Collections.shuffle(_all_suffle_car);
			return true;
		}
		else {
			Collections.shuffle(_all_suffle_car);
			return false;
		}
	}
	
	// 拿一张牌
	private int getOneCard() {
		return _all_suffle_car.remove(_all_suffle_car.size() - 1);
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
		
		/*
		return 1000 - (now_time - last_action_time);
		
		*/
		
		if (status == 0)
			return Consts.getBJLS0() - (now_time - last_action_time);
		else if (status == 1)
			return Consts.getBJLS1() - (now_time - last_action_time);
		else if (status == 2) {
			if (top_bet_player_x > 0)
				return Consts.getBJLS2() - (now_time - last_action_time);
			else
				return Consts.getBJLS2_2() - (now_time - last_action_time);
		}
		else if (status == 3) {
			if (top_bet_player_z > 0)
				return Consts.getBJLS3() - (now_time - last_action_time);
			else
				return Consts.getBJLS3_2() - (now_time - last_action_time);
		}
		else if (status == 4) {
			if (top_bet_player_x > 0)
				return Consts.getBJLS4() - (now_time - last_action_time);
			else
				return Consts.getBJLS4_2() - (now_time - last_action_time);
		}
		else if (status == 5) {
			if (top_bet_player_z > 0)
				return Consts.getBJLS5() - (now_time - last_action_time);
			else
				return Consts.getBJLS5_2() - (now_time - last_action_time);
		}
		else if (status == 6)
			return Consts.getBJLS6() - (now_time - last_action_time);
		else 
			return Consts.getBJLS7() - (now_time - last_action_time);
	}
	
	// 加入游戏
	public void joinGame(PlayerConnection con, int instanceId) {
		int sit_pos = trySitPos(con);
		GDL_G2C_PlayerJoinGame joinmsg = new GDL_G2C_PlayerJoinGame();
		joinmsg.setPlayerId(con.getPlayerId());
		joinmsg.setGameId(Consts.getBaijiale());
		
		// 把我加入游戏的消息告诉同场其他人
		BJLPlayerInfo ngp = fillPlayerInfo(con.getPlayerId(), sit_pos);
		joinmsg.setBplayerInfos(ngp);
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
		re.setGameId(Consts.getBaijiale());
		re.setInstanceId(instanceId);
		
		
		BJLGameStatus nnGameInfo = new BJLGameStatus();
		// 注意，如果玩家进入游戏就是开牌状态，那么要调整为休息状态
		int l_status = status;
		long next_time = getNextOpenTime();
		if (_last_open_res != null) {
			nnGameInfo.setOpenResults(_last_open_res);
			_last_open_res.setPlayer_x(top_bet_player_x);
			_last_open_res.setPlayer_z(top_bet_player_z);
		}
		nnGameInfo.setState(l_status);
		nnGameInfo.setNextStartDuration((int)next_time);
		if (_cur_banker.player_id > 0) {
			nnGameInfo.setBanker(fillPlayerInfo(_cur_banker.player_id));
		}
		nnGameInfo.setBankerCoin(_cur_banker.coin_total);
		nnGameInfo.setCardsNum(_all_suffle_car.size());
		nnGameInfo.setPlayerId(qie_card_player_id);
		
		BJLBetPool poolinfo = getPoolCoin();
		nnGameInfo.setBetPools(poolinfo);
		
		for (Entry<Long, PlayerInfo> entr : player_infos.entrySet()) {
			BJLPlayerInfo ppi = fillPlayerInfo(entr.getKey());
			if (ppi == null)
				continue;
			
			nnGameInfo.getSitPlayerInfos().add(ppi);
		}
		
		re.setBaijialeGameInfo(nnGameInfo);
		con.send(re);
	}
	
	public BJLPlayerInfo fillPlayerInfo(long pid) {
		return fillPlayerInfo(pid, -99);
	}

	public BJLPlayerInfo fillPlayerInfo(long pid, int pos) {
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
		
		BJLPlayerInfo ngp = new BJLPlayerInfo();
		ngp.setPlayerId(pid);
		ngp.setPos(cpos);
		ngp.setName(pbi.getName());
		ngp.setHeadUrl(pbi.getHeadUrl());
		ngp.setViplevel(pbi.getViplevel());
		ngp.setCoin( pbi.getMoney() );
		ngp.setBetcoin(new BJLBetPool());
		
		for (BjlPoolInfo bpi : m_pool_info) {
			long bertc = bpi.getCoin(pid);
			if (bertc > 0) {
				ngp.getBetcoin().getPoolId().add(bpi.id);
				ngp.getBetcoin().getPoolCoin().add(bertc);
			}
		}
		
		return ngp;
	}

	public BJLBetPool getPoolCoin() {
		BJLBetPool poolinfo = new BJLBetPool();
		for (BjlPoolInfo pinfo : m_pool_info) {
			poolinfo.getPoolId().add(pinfo.id);
			poolinfo.getPoolCoin().add(pinfo.total_bet_coin);
		}
		return poolinfo;
	}
	
	private void clearPlayerBet(long player_id) {
		for (BjlPoolInfo po : m_pool_info) {
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
				GDL_G2C_BJLBankerChange change = new GDL_G2C_BJLBankerChange();
				broadcast(change);
			}
		}
		
		if (status == 2 ||
				status == 4) {
			if (pid == top_bet_player_x) {
				GDL_G2C_BJLZXChange change = new GDL_G2C_BJLZXChange();
				change.setPlayer_x(0L);
				change.setPlayer_z(top_bet_player_z);
				broadcast(change);
				top_bet_player_x = 0;
			}
		}
		
		if (status == 3 ||
				status == 5) {
			if (pid == top_bet_player_z) {
				GDL_G2C_BJLZXChange change = new GDL_G2C_BJLZXChange();
				change.setPlayer_x(top_bet_player_x);
				change.setPlayer_z(0L);
				broadcast(change);
				top_bet_player_z = 0;
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
		
		if (status == 1)
			playerUnBetOn(con);
		con.getPlayer().setGameUID(0);
		
		GDL_G2C_PlayerLeaveGame leavemsg = new GDL_G2C_PlayerLeaveGame();
		leavemsg.setPlayerId(con.getPlayerId());
		broadcast(leavemsg);
	}
}
