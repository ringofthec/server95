package com.gdl.game;

//import gameserver.MethodStatitic;
import gameserver.MethodStatitic;
import gameserver.config.ServerConfig;
import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.GDL_G2C_BeginSlotsRe;
import gameserver.jsonprotocol.GDL_G2C_EndSlotsRe;
import gameserver.jsonprotocol.GDL_G2C_GuessRe;
import gameserver.jsonprotocol.GDL_G2C_PlayerMoneySync;
import gameserver.jsonprotocol.SGLotty;
import gameserver.jsonprotocol.SGOpen;
import gameserver.logging.LogService;
import gameserver.utils.Util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import table.MT_Data_GGamePolicy;
import table.MT_Data_GRechargePolicy;
import table.MT_Data_GSlotsGuess;
import table.MT_Data_GSlotsLine;
import table.MT_Data_GSlotsOpen;
import table.MT_Data_GSlotsRandom;
import table.MT_Data_GSlotsRate;
import table.base.TableManager;

import com.commons.network.websock.PlayerConnection;
import com.commons.network.websock.handler.ChatHandler;
import com.commons.util.RandomUtil;
import com.gdl.data.ItemData;
import com.gdl.data.PlayerData;
import com.gdl.manager.GamePoolManager;



public class SlotGameMrg {
	private static SlotGameMrg m_int = new SlotGameMrg();
	private SlotGameMrg() {}
	public static SlotGameMrg getInstance() {return m_int;}
	
	public static class Policy {
		public int policy_id;
		public int max_fall_count;
		
		public Policy() {
			max_fall_count = -1;
		}
	}
	
	public static class LineInfo {
		public int num;
		public int id;
		public int rate;
		public int lineid;
	}
	
	public static int getRechargePolicyId(PlayerConnection con, int preMoney, int Gameid) {
		
		PlayerData pdata = con.getPlayer();
		ItemData idata = con.getPlayer().getItemData();
		
		long back_money = idata.getItemCountByTempId(140);
		if (back_money > 0) {
			long new_cost_money = idata.getItemCountByTempId(141);
			long new_recv_money = idata.getItemCountByTempId(142);
			System.err.println("jjsdfsdf = " + (new_recv_money - new_cost_money));
			if ( (new_recv_money - new_cost_money) < back_money) {
				return 6;
			} else {
				idata.setItem(con, 140, 0, 0);
				idata.setItem(con, 141, 0, 0);
				idata.setItem(con, 142, 0, 0);
			}
		}
		
		MT_Data_GRechargePolicy grp = TableManager.GetInstance().getTargetPolicy(Gameid, preMoney);
		long earnCount = idata.getEran();
		int policy_step = (int)idata.getItemCountByTempId(grp.getM_nstep_id());
		int policy_change = (int)idata.getItemCountByTempId(114);
		if (policy_change == 0) {
			policy_change = RandomUtil.RangeRandom(grp.bodong0().field1(), grp.bodong0().field2());
			idata.setItem(con, 114, policy_change, 0);
		}
		
		
		/*
		int policy_mode = (int)idata.getItemCountByTempId(116);
		 
		if (policy_mode == 1) {
			// 充值模式，要大赢到指定的值
			long all_coin = idata.getItemCountByTempId(117);
			if (earnCount < all_coin)
				return 2;
			
			pdata.tryBeginNewPolicy(con, idata, false, preMoney, Gameid);
		} else {
			pdata.tryBeginNewPolicy(con, idata, true, preMoney, Gameid);
		}
		
		
		earnCount = idata.getEran();
		policy_mode = (int)idata.getItemCountByTempId(116);
		if (policy_mode == 1)
			return 2;
		*/
		
		
		while (true) {
			policy_step = (int)idata.getItemCountByTempId( grp.step_id() );
			
			for (int k = policy_step; k < grp.win().size(); ++k) {
				int tes = k % 2;
				if (tes == 0) {
					long val = grp.win().get(k);
					/*
					if (earnCount < val)
						return 2;
					*/
					if (val > 0) {
						if (earnCount < val * policy_change / 10000)
							return 2;
					} else {
						val = Math.abs(val * policy_change / 10000) - Math.abs(val) + val;
						if (earnCount < val)
							return 2;
					}
				} else {
					long val = grp.win().get(k);
					/*
					if (earnCount > val)
						return 5;
					*/
					
					if (val < 0) {
						if (earnCount > val * policy_change / 10000)
							return 5;
					} else {
						val = val - (val * policy_change / 10000 - val);
						if (earnCount > val)
							return 5;
					}
					
				}
				idata.setItem(con, grp.step_id(), k+1, 0);
			}
			
			pdata.beginNewPolicy(con, idata, false, grp);
		}
	}
	
	// 每日slot计数， 策略要求的赢取数，策略累计的赢取数
	private Policy checkPolicy(PlayerConnection con, int slottype, boolean is_in_active, int preLineMoney) {
		ItemData idata = con.getPlayer().getItemData();
		long slotsDayCount = idata.getItemCountByTempId(Consts.getDay_Slot_Count()); 
		long reliefSlotCount = idata.getItemCountByTempId(Consts.getRelief_Slot_Count());
		long day_recharge_count = idata.getItemCountByTempId(Consts.getDay_Recharge_Value());
		
		Policy policy = new Policy();
		if (slottype == Consts.getGoldSlot()) {
			if (is_in_active) {
				policy.policy_id = 5;
				return policy;
			}
		}
		
		if (true) {
			policy.policy_id = 6;
			return policy;
		}
		
		if (day_recharge_count > 0) {
			// 今天充过值
			policy.policy_id = getRechargePolicyId(con, preLineMoney, slottype);
			return policy;
		} else if ( slotsDayCount <= 5 ) {
			// 前5次策略
			policy.policy_id = 2;
			policy.max_fall_count = 1;
			return policy;
		} else if (slotsDayCount <= 18) {
			// 前18次策略
			policy.policy_id = 2;
			policy.max_fall_count = 3;
			return policy;
		} else if (slotsDayCount <= reliefSlotCount + 18) {
			// 救济金策略生效
			policy.policy_id = 2;
			return policy;
		} else {
			// 默认策略
			policy.policy_id = getRechargePolicyId(con, preLineMoney, slottype);
			return policy;
		}
	}
	
	private LineInfo getLineInfo(int a0, int a1, int a2, int a3, int a4) {
		int[] eles = new int[5];
		eles[0] = a0;
		eles[1] = a1;
		eles[2] = a2;
		eles[3] = a3;
		eles[4] = a4;
		
		int begin_idx = 0;
		int end_idx = 5;

		if (eles[begin_idx] == 0) {
			int i = begin_idx + 1;
			for (; i < end_idx; ++i) {
				if (eles[i] != 0)
					break;
			}

			if (i == end_idx) {
				// 全是听用
				return null;
			} else {
				// 部分听用
				for (int k = begin_idx; k < i; ++k) {
					eles[k] = eles[i];
				}
			}
		}

		for (int k = begin_idx + 1; k < end_idx; k++) {
			if (eles[k] == 0)
				eles[k] = eles[k - 1];
		}
		
		if (eles[0] != eles[1])
			return null;

		if (eles[1] != eles[2])
			return null;

		if (eles[2] != eles[3]) {
			LineInfo li = new LineInfo();
			li.id = eles[0];
			li.num = 3;
			return li;
		}

		if (eles[3] != eles[4]) {
			LineInfo li = new LineInfo();
			li.id = eles[0];
			li.num = 4;
			return li;
		}

		LineInfo li = new LineInfo();
		li.id = eles[0];
		li.num = 5;
		return li;
	}
	
	private int flost_count = 0;
	private boolean _is_test = false;
	
	private long read_calc(PlayerConnection con, int slotType, int levelId,
			int lineNum,
			int preMoney, boolean isFree, int viplevel, 
			int auto, boolean is_in_active, GDL_G2C_BeginSlotsRe re, boolean test) {
		// 1. 决定策略
		Policy policy = checkPolicy(con, slotType, is_in_active, preMoney);
		long friut_slot_fall_count = con.getPlayer().getItemData().getItemCountByTempId(Consts.getFriut_Slot_Fall_Count());
		
		if (test)
			MethodStatitic.getInstance().recd(policy.policy_id, (int)con.getPlayer().getItemCountByTempId(113));
		
		// 2. 修正策略，获取策略配置
		MT_Data_GSlotsRandom slot_policy_config = TableManager.GetInstance()
				.TableGSlotsRandom().GetElement(policy.policy_id);
		// 如果没有给定最大连输参数，那么使用策略中的默认最大连输参数
		int target_max_fail_count = policy.max_fall_count;
		if (target_max_fail_count == -1)
			target_max_fail_count = slot_policy_config.max_fall();
		
		List<Integer> config = TableManager.GetInstance().getGSlotsList(policy.policy_id);
		MT_Data_GRechargePolicy gamePolicy = TableManager.GetInstance().getTargetPolicy(slotType, preMoney);
		
		long res_money = 0;
		int[] all = new int[15];
		if (_is_test) {
			if (flost_count % 10 == 0) {
				int[] ttall = {9,1,3,2,3,10,10,10,10,1,4,8,1,4,1};
				for (int o = 0; o < all.length; ++o)
					all[o] = ttall[o];
			}
		}
		
		
		
		ArrayList<LineInfo> all_lines = new ArrayList<LineInfo>();
		int count = 0;
		boolean is_force = false;
		while (true) {
			{
				// 判断新的充值策略
				// 1. 看看还有没有充值返利的空间 140物品的值
				// 2. 根据当前押注数，从大到小找返利的返回倍率
				// 3. 如果找到了，那么就强制使用赔率下的固定图形进行返利
				ItemData items = con.getPlayer().getItemData();
				long back_money = items.getItemCountByTempId(140);
				if (back_money > 600 * 100) {
					if (RandomUtil.RangeRandom(0,  100) < 8) {
						int rate = TableManager.GetInstance().getBackRate(preMoney, back_money);
						if (rate > 0) {
							System.err.println("force-------------------------" + rate);
							is_force = true;
							ArrayList<Integer> res_arr = TableManager.GetInstance().getRateRes(rate);
							for (int i = 0; i < all.length; ++i)
								all[i] = res_arr.get(i);
						}
					}
				}
			}
			
			boolean re_calc = false;
			all_lines.clear();
			count = 0;
			
			// 3. 按策略，随机15个水果id
			if (!_is_test && !is_force) {
				for (int i = 0 ; i < all.length; ++i) {
					int idx = Util.rateInt(config, 10000);
					all[i] = idx;
				}
			}

			// 4. 检查30条线, 记录连接了几个，是什么水果
			List<Integer> line_dfdf = TableManager.GetInstance().getLineInfo(lineNum);
			for (int lineid : line_dfdf) {
				MT_Data_GSlotsLine line = TableManager.GetInstance().TableGSlotsLine().GetElement(lineid);

				int idx0 = line.lines().get(0) - 1;
				int idx1 = line.lines().get(1) - 1;
				int idx2 = line.lines().get(2) - 1;
				int idx3 = line.lines().get(3) - 1;
				int idx4 = line.lines().get(4) - 1;

				LineInfo li = getLineInfo(all[idx0], all[idx1], all[idx2], all[idx3], all[idx4]);
				if (li == null)
					continue ;
				
				if (li.id == 11 && li.num == 5) {
					re_calc = true;
					break;
				}
				
				li.lineid = lineid;
				all_lines.add(li);
				continue;
			}
			
			if (re_calc)
				continue;
			
			// 输了，然后最大连续输了
			if (all_lines.isEmpty() && friut_slot_fall_count >= target_max_fail_count)
				continue;

			// 5. 查倍率表, 根据水果类型，和连接个数，得出倍率
			for (LineInfo li : all_lines) {
				MT_Data_GSlotsRate srate = TableManager.GetInstance().TableGSlotsRate().GetElement(li.id);
				li.rate = srate.rates().get(li.num);
				
				if (!is_force) {
					if (li.rate > 1000)
						re_calc = true;
				}
			}
			
			if (re_calc)
				continue;

			// 6. 相加倍率 
			boolean has_samll_game = false;
			re.setGameType(0);
			for (LineInfo li : all_lines) {
				count += li.rate;
				// 注意，这里是判断是否有小游戏
				if (li.id == 10) {
					has_samll_game = true;
				}
			}
			
			/*
			List<Integer> alltemp = new ArrayList<>();
			for (int i : all)
				alltemp.add(i);
			
			if (count <= 3190)
				continue;
			
			if (Math.abs(256 - count) < 10)
				LogService.sysErr(256, StringUtils.join(alltemp, ","), 1);
			else if (Math.abs(300 - count) < 10)
				LogService.sysErr(300, StringUtils.join(alltemp, ","), 1);
			else if (Math.abs(328 - count) < 10)
				LogService.sysErr(328, StringUtils.join(alltemp, ","), 1);
			else if (Math.abs(340 - count) < 10)
				LogService.sysErr(340, StringUtils.join(alltemp, ","), 1);
			else if (Math.abs(600 - count) < 10)
				LogService.sysErr(600, StringUtils.join(alltemp, ","), 1);
			else if (Math.abs(640 - count) < 10)
				LogService.sysErr(640, StringUtils.join(alltemp, ","), 1);
			else if (Math.abs(648 - count) < 10)
				LogService.sysErr(648, StringUtils.join(alltemp, ","), 1);
			else if (Math.abs(656 - count) < 10)
				LogService.sysErr(656, StringUtils.join(alltemp, ","), 1);
			else if (Math.abs(680 - count) < 10)
				LogService.sysErr(680, StringUtils.join(alltemp, ","), 1);
			else if (Math.abs(750 - count) < 10)
				LogService.sysErr(750, StringUtils.join(alltemp, ","), 1);
			else if (Math.abs(820 - count) < 10)
				LogService.sysErr(820, StringUtils.join(alltemp, ","), 1);
			else if (Math.abs(1080 - count) < 10)
				LogService.sysErr(1080, StringUtils.join(alltemp, ","), 1);
			else if (Math.abs(1093 - count) < 10)
				LogService.sysErr(1093, StringUtils.join(alltemp, ","), 1);
			else if (Math.abs(1280 - count) < 10)
				LogService.sysErr(1280, StringUtils.join(alltemp, ","), 1);
			else if (Math.abs(1500 - count) < 10)
				LogService.sysErr(1500, StringUtils.join(alltemp, ","), 1);
			if (Math.abs(1700 - count) < 10)
				LogService.sysErr(1700, StringUtils.join(alltemp, ","), 1);
			else if (Math.abs(2133 - count) < 10)
				LogService.sysErr(2133, StringUtils.join(alltemp, ","), 1);
			else if (Math.abs(2160 - count) < 10)
				LogService.sysErr(2160, StringUtils.join(alltemp, ","), 1);
			else if (Math.abs(2187 - count) < 10)
				LogService.sysErr(2187, StringUtils.join(alltemp, ","), 1);
			else if (Math.abs(2267 - count) < 10)
				LogService.sysErr(2267, StringUtils.join(alltemp, ","), 1);
			else if (Math.abs(3200 - count) < 10)
				LogService.sysErr(3200, StringUtils.join(alltemp, ","), 1);
			else if (Math.abs(3240 - count) < 10)
				LogService.sysErr(3240, StringUtils.join(alltemp, ","), 1);
			 */
			
			// 总倍率不能超过每日充值限定的范围
			if (policy.policy_id == 5) {
				if (count > gamePolicy.slots_times_limit().get(levelId - 1).field2())
					continue;
			} else if (policy.policy_id == 2 && count > 0) {
				if (count < gamePolicy.slots_times_limit().get(levelId - 1).field1())
					continue;
			}
			
			// 如果正在小游戏，就不要摇到小游戏了
			if (has_samll_game && isFree)
				continue ;
			
			// 自动转也不要摇到小游戏
			if (has_samll_game && auto == 1)
				continue ;

			// 7. 计算总赢取数
			res_money = preMoney * count;
			break;
		}
		
		flost_count++;
		
		if (test)
			MethodStatitic.getInstance().recdM(res_money);
		
		// 9. 填充协议
		for (int i = 0 ; i < all.length; ++i) {
			re.getRes().add( all[i] );
		}
		for (LineInfo li : all_lines) {
			gameserver.jsonprotocol.LineInfo lineinfo = new gameserver.jsonprotocol.LineInfo();
			lineinfo.setLineId(li.lineid);
			lineinfo.setNum(li.num);
			re.getLines().add(lineinfo);
		}
		re.setGameType(0);
		for (LineInfo li : all_lines) {
			// 注意，这里是判断是否有小游戏
			if (li.id == 10) {
				if (li.num == 3) {
					re.setGameType(1);
					int idx = getLotty();
					re.setLotty(new SGLotty());
					re.getLotty().setIdx(idx);
				} else if (li.num == 4) {
					re.setGameType(2);
					List<Integer> result = getOpenGame();
					re.setOpen(new SGOpen());
					re.getOpen().setIdx(result);
				} else if (li.num == 5) {
					re.setGameType(3);
				}
			}
		}
		re.setRewardcoin(res_money);
		re.setRewardrate(count);
		con.getPlayer().setIsForce(is_force);
		return res_money;
	}
	
	private int getLotty() {
		int i = Util.rateInt(TableManager.GetInstance().getSlotLottryRateList(), 10000);
		return i + 1;
	}
	
	private List<Integer> getOpenGame() {
		List<Integer> res = new ArrayList<Integer>();
		
		for (MT_Data_GSlotsOpen li : TableManager.GetInstance().getSlotOpenRateLists()) {
			int idx = Util.rateInt(li.svr_rate(), 10000);
			int ccc = li.rate().get(idx);
			
			if (ccc == 0)
				break;
			
			res.add( ccc );
		}
		
		while (res.size() < 4)
			res.add(0);
		
		return res;
	}
	
	int[] rates = {2, 4};
	public void guess(PlayerConnection con, int i) {
		PlayerData pdata = con.getPlayer();
		
		if (i != 0 && i != 1)
			return ;
		
		int guess_rate = pdata.getGuessRate();
		guess_rate = guess_rate * this.rates[i];
		MT_Data_GSlotsGuess guess_conf = TableManager.GetInstance().TableGSlotsGuess().GetElement(guess_rate);
		if (guess_conf == null)
			return ;
		
		if (RandomUtil.RangeRandom(1, 10000) < guess_conf.svr_rate()) {
			pdata.setGuessRate(guess_rate);
		}
		else {
			pdata.setGuessRate(0);
		}
		
		guess_rate = pdata.getGuessRate();
		GDL_G2C_GuessRe re = new GDL_G2C_GuessRe();
		re.setRate(guess_rate);
		con.send(re);
	}
	
	public void endSlot(PlayerConnection con, boolean test) {
		PlayerData pdata = con.getPlayer();
		ItemData idata = pdata.getItemData();
		int uid = pdata.getGameUID();
		
		GDL_G2C_BeginSlotsRe slotinfo = pdata.getCurSlots();
		if (slotinfo == null || slotinfo.getRetCode() == 0) {
			// 没中奖，直接返回
			GDL_G2C_EndSlotsRe res = new GDL_G2C_EndSlotsRe();
			con.send(res);
			pdata.setCurSlots(null, 0, 0, false);
			return ;
		}
		
		int gameId = GameInstanceMrg.getGameId( uid );
		int levelId = GameInstanceMrg.getLevelId( uid );
		boolean is_in_active = false;
		if (gameId == Consts.getGoldSlot()) {
			is_in_active = SlotActiveMrg.getInstance().isInGoldActive(con);
		}
		
		SlotsGameInfo sgi = null;
		if (gameId == Consts.getFriutSlot())
			sgi = FriutSlotsGameInstanceManager.getInstance().getPlayerSlotsGameInfo(levelId, pdata.getGameUID());
		else
			sgi = GoldSlotsGameInstanceManager.getInstance().getPlayerSlotsGameInfo(levelId, pdata.getGameUID());
		
		if (sgi == null) {
			pdata.setCurSlots(null, 0, 0, false);
			return ;
		}
		
		long res_money = slotinfo.getRewardcoin();
		int small_game_rate_effect = 0;
		int small_game_id = slotinfo.getGameType();
		if (small_game_id == 1) {
			int idx = slotinfo.getLotty().getIdx();
			small_game_rate_effect = TableManager.GetInstance().TableGSlotsLotty().GetElement(idx).rate();
			res_money = res_money + pdata.getPreMoney() * small_game_rate_effect;
		} else if (small_game_id == 2) {
			for (Integer pa : slotinfo.getOpen().getIdx())
				small_game_rate_effect += pa;
			res_money = res_money + pdata.getPreMoney() * small_game_rate_effect;
		} else if (small_game_id == 3) {
			small_game_rate_effect = pdata.getGuessRate();
			if (res_money == 0)
				res_money = pdata.getPreMoney();
			
			res_money = res_money * small_game_rate_effect;
			if (small_game_rate_effect == 0) {
				// 输了
				GDL_G2C_EndSlotsRe res = new GDL_G2C_EndSlotsRe();
				con.send(res);
				
				if (!pdata.isFreeSlot()) {
					GamePoolManager.getInstance().addPoolReward(1, levelId, pdata.getLintNum() * pdata.getPreMoney(), 0.005f);
				}
				pdata.setCurSlots(null, 0, 0, false);
				idata.addItem(con, Consts.getFriut_Slot_Fall_Count(), 1, Consts.getItemEventBeginFriutSlot());
				return ;
			}
		}
		
		if (slotinfo.getRetCode() == 1) {
			// 2. 给玩家加钱
			idata.addItem(con, Consts.getCOIN_ID(), res_money, 
						Consts.getItemEventFriutSlotEarn());
			
			// 记录赢取
			if (!is_in_active) {
				idata.addItem(con, Consts.getGame_Earn_Count(), res_money, Consts.getItemEventFriutSlotEarn());
			}
			idata.addItem(con, Consts.getDay_Earn_Count(), res_money, Consts.getItemEventFriutSlotEarn(), false);
			
			// 3. 统计战绩
			pdata.playerSlotEarn(gameId, res_money);
			
			if (pdata.isForce()) {
				long min_dec = Math.min(idata.getItemCountByTempId(140), res_money);
				idata.decItemByTempId(con, 140, min_dec, Consts.getItemEventFriutSlotEarn(), false);
			}
			
			if (gameId == Consts.getFriutSlot() && slotinfo.getRewardrate()>=1000) {
				ChatHandler.newbanner(32, con.getPlayer().getName(), con.getPlayerId(), slotinfo.getRewardrate(), res_money);
			}
		} else {
			// 2. 给玩家加钱
			idata.addItem(con, Consts.getCOIN_ID(), res_money, Consts.getItemEventFriutSlotPool());
			idata.addItem(con, Consts.getDay_Earn_Count(), res_money, Consts.getItemEventFriutSlotPool(), false);
			
			// 3. 统计战绩
			pdata.playerSlotPool(gameId, res_money);
			
			if (gameId == Consts.getFriutSlot())
				ChatHandler.newbanner(2 + levelId, con.getPlayer().getName(), con.getPlayerId(), res_money);
			else
				ChatHandler.newbanner(5 + levelId, con.getPlayer().getName(), con.getPlayerId(), res_money);
		}
		
		idata.addItem(con, Consts.getEXP_ID(), res_money, 0);
		SlotActiveMrg.getInstance().recordActive(con, gameId, res_money);
		
		GDL_G2C_PlayerMoneySync sync = new GDL_G2C_PlayerMoneySync();
		sync.setCoin(con.getPlayer().getCoin());
		sync.setRewardCoin(res_money);
		sync.setType(small_game_id);
		if (slotinfo.getRetCode() == 2) {
			sync.setType(9);
		} else if (slotinfo.getRewardrate() >= 200) {
			sync.setType(10);
		}
		sync.setPlayer_id(pdata.getPlayerId());
		
		if (sgi != null) {
			synchronized (sgi) {
				sgi.broadcast(sync);
			}
		}
		
		if (test)
			MethodStatitic.getInstance().setreward_count();
		
		pdata.setCurSlots(null, 0, 0, false);
		GDL_G2C_EndSlotsRe res = new GDL_G2C_EndSlotsRe();
		con.send(res);
	}
	
	public static boolean pool_test_recharge = true;
	public static boolean friut_pool_open = false;
	public static boolean gold_pool_open = false;
	
	private long canGetPoolReward(PlayerData pdata, ItemData idata, int gameid, int levelId) {
		if (levelId >= 2)
			return 0;
		
		// 1. 领取奖池必须当天充值数到达一个级别
		if (RandomUtil.RangeRandom(0, 10000) < 1)
			return 0;
		
		// 2. 检查奖池是否达到可以领取状态
		long pool_coin = GamePoolManager.getInstance().checkPoolReward(gameid, levelId);
		return pool_coin;
	}
	
	public void beginSlot(PlayerConnection con, int money, int lineNum, int preLineMoney, int auto, boolean test, int level) {
		PlayerData pdata = con.getPlayer();
		ItemData idata = pdata.getItemData();
		int uid = pdata.getGameUID();
		
		// 1. 获取玩家的副本信息
		int gameid = GameInstanceMrg.getGameId( uid ); 
		int levelId = GameInstanceMrg.getLevelId( uid ); 
		
		if (test)
			levelId = level;
		
		if (levelId == 0)
			return ;
		
		if (pdata.getCurSlots() != null)
			return ;
		
		SlotsGameInfo sgi = null;
		if (gameid == Consts.getFriutSlot())
			sgi = FriutSlotsGameInstanceManager.getInstance().getPlayerSlotsGameInfo(levelId, pdata.getGameUID());
		else
			sgi = GoldSlotsGameInstanceManager.getInstance().getPlayerSlotsGameInfo(levelId, pdata.getGameUID());
		
		if (!test)
			if (sgi == null)
				return ;
		
		// 2. 开始记录本次slot的状态
		boolean is_free = pdata.getFreeSlot() > 0;
		GDL_G2C_BeginSlotsRe re = new GDL_G2C_BeginSlotsRe();
		re.setSlotsType(gameid);
		pdata.setCurSlots(re, preLineMoney, lineNum, is_free);
		
		// 3. 先检查一下是否可以领取奖池 
		{
			long money_pool = canGetPoolReward(pdata, idata, gameid, levelId);
			if (money_pool > 0 ) {
				re.setRetCode(2);
				re.setRewardcoin(money_pool);
				re.setRewardrate(1);
				re.setGameType(0);
				
				// 生成奖池类型的水果阵型
				int line_id = RandomUtil.RangeRandom(1, 30);
				MT_Data_GSlotsLine line_config = TableManager.GetInstance().TableGSlotsLine().GetElement(line_id);
				gameserver.jsonprotocol.LineInfo lineinfo = new gameserver.jsonprotocol.LineInfo();
				lineinfo.setLineId(line_id);
				lineinfo.setNum(5);
				re.getLines().add(lineinfo);
				for (int i = 0; i < 15; ++i) {
					if (line_config.lines().contains(i)) {
						re.getRes().add(11);
					} else {
						re.getRes().add(RandomUtil.RangeRandom(0, 9));
					}
				}
				
				idata.setItem(con, Consts.getFriut_Slot_Fall_Count(), 0, -15);
				con.send(re);
				return ;
			}
		}
		
		// 4. 扣除押注
		if (!is_free) {
			idata.decItemByTempId(con, Consts.getCOIN_ID(), money, Consts.getItemEventBeginFriutSlot());
			idata.addItem(con, Consts.getGame_Cost_Count(), money, Consts.getItemEventBeginFriutSlot());
		}
		else
			pdata.decFreeSlot();
		
		boolean is_in_active = false;
		if (gameid == Consts.getGoldSlot()) {
			is_in_active = SlotActiveMrg.getInstance().isInGoldActive(con);
		}
		
		// 5. 进行随机，填充结果
		long res_money = read_calc(con, gameid, levelId,
				lineNum, 
				preLineMoney, is_free, 
				pdata.getVipLvl(), auto, is_in_active, re, test);
		idata.addItem(con, Consts.getDay_Slot_Count(), 1, 0);
		pdata.playerSlotCount(gameid);
		
		if (re.getGameType() == 1)
			pdata.setFreeSlot(5, preLineMoney);
		else if (re.getGameType() == 2)
			pdata.setFreeSlot(10, preLineMoney);
		else if (re.getGameType() == 3)
			pdata.setFreeSlot(20, preLineMoney);
		
		// 6. slot赢钱数为0 而且 没有碰到小游戏算输
		if (res_money == 0 && re.getGameType() == 0) {
			re.setRetCode(0);
			idata.addItem(con, Consts.getFriut_Slot_Fall_Count(), 1, Consts.getItemEventBeginFriutSlot());
			
			// 计入奖池中
			{
				if (!is_free)
					GamePoolManager.getInstance().addPoolReward(gameid, levelId, money, 0.005f);
			}
		} else {
			re.setRetCode(1);
			idata.setItem(con, Consts.getFriut_Slot_Fall_Count(), 0, Consts.getItemEventBeginFriutSlot());
		}
		
		if (test)
			MethodStatitic.getInstance().recdF((int)idata.getItemCountByTempId(Consts.getFriut_Slot_Fall_Count()));
		
		con.send(re);
	}
}
