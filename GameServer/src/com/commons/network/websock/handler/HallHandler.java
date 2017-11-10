package com.commons.network.websock.handler;

import gameserver.config.ServerConfig;
import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.FuncSetting;
import gameserver.jsonprotocol.GDL_C2G_EnterHall;
import gameserver.jsonprotocol.GDL_C2G_GetRotaryReward;
import gameserver.jsonprotocol.GDL_G2C_EnterHallRe;
import gameserver.jsonprotocol.GDL_G2C_FunctionOpen;
import gameserver.jsonprotocol.RewardItem;
import gameserver.thread.ThreadPoolManager;

import java.util.Calendar;
import java.util.List;

import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int3;
import table.MT_Data_GCLoginReward;
import table.MT_Data_RotaryReward;
import table.base.TableManager;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConnection;
import com.commons.util.RandomUtil;
import com.commons.util.TimeUtil;
import com.gdl.data.MailData;
import com.gdl.data.PlayerData;
import com.gdl.manager.RankManager;
import commonality.Common;

public class HallHandler {
	private final static HallHandler instance = new HallHandler();
	public static HallHandler getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(HallHandler.class);
	
	public void init() {
		HandlerManager.getInstance().pushNornalHandler(HallHandler.class, this, 
				"OnEnterHall", new GDL_C2G_EnterHall());
		HandlerManager.getInstance().pushNornalHandler(HallHandler.class, this, 
				"OnGetRotaryReward", new GDL_C2G_GetRotaryReward());
	}
	
	public void OnGetContinueLoginReward(PlayerConnection con) {
		PlayerData p = con.getPlayer();
		if (p.getContinueLoginDay() > p.getContinueLoginReward()) {
			for (int i = p.getContinueLoginReward() + 1 ; i <= p.getContinueLoginDay(); ++i) {
				int re_id = i;
				if (re_id >= TableManager.GetInstance().getMaxLoginReward())
					re_id = TableManager.GetInstance().getMaxLoginReward();
				
				MT_Data_GCLoginReward conf = TableManager.GetInstance().TableGCLoginReward().GetElement(re_id);
				if (conf == null)
					continue;
				
				Integer[] iteminfos = {conf.rewards1().field1(), conf.rewards1().field2()};
				MailData.sendSystemMail(con, "連續登錄獎勵", "系统", "恭喜妳獲得連續登錄 " + i + " 天獎勵", 3, iteminfos);
			}
		}
		
		p.setContinueLoginReward(p.getContinueLoginDay());
	}
	
	public void OnGetRotaryReward(PlayerConnection con, GDL_C2G_GetRotaryReward message) {
		PlayerData p = con.getPlayer();
		int rotary_reward_idx = p.getRotaryRewardIdx();
		if (rotary_reward_idx > 0) {
			// 发奖了
			MT_Data_RotaryReward zp = TableManager.GetInstance().TableRotaryReward().GetElement(rotary_reward_idx);
			p.getItemData().addItem(con, 
					zp.getM_rewards1().field1(), zp.getM_rewards1().field2(), Consts.getItemEventRotaryReward());
			p.setRotaryReward(-rotary_reward_idx);
			ItemHandler.showAddItem(con, zp.getM_rewards1().field1(), zp.getM_rewards1().field2());
		}
	}
	
	FastMap<Long, Long> rank_notice_login = new FastMap<Long, Long>();
	private boolean testCD(long pid) {
		long now_t = TimeUtil.GetDateTime();
		long next_t = 0;
		if (rank_notice_login.containsKey(pid))
			next_t = rank_notice_login.get(pid);
		if (next_t > now_t)
			return false;
		
		rank_notice_login.put(pid, now_t + 4 * Common.HOUR_MILLISECOND);
		return true;
	}
	
	public void OnEnterHall(PlayerConnection con, GDL_C2G_EnterHall message) {
		PlayerData p = con.getPlayer();
		boolean is_first_login = p.isFirstLogin();
		
		// 注意，这个是每天刷新，这个非常重要，一定要在这里调用
		p.checkOnNewDayOnLogin();
		p.setCurSlots(null, 0, 0, false);
		p.setFreeSlot(0, 0);
		
		OnGetContinueLoginReward(con);
		p.getMailData().addLoginMail(con);
		p.sendLottyReward();
		p.flushRecharge(con);
		
		// -a 表示今天已经领取过啦 a 表示领取的那个编号的物品
		// 0 表示今天还米有领取过呢,也没有随机过
		// a 表示没有领取过，但是已经随机过了
		int rotary_reward_idx = p.getRotaryRewardIdx();
		
		GDL_G2C_EnterHallRe hh = new GDL_G2C_EnterHallRe();
		hh.setIsDayFirstLogin(is_first_login ? 1 : 0);
		hh.setServerTimeStamp(TimeUtil.GetDateTime());
		if (ServerConfig.app_test) {
			hh.setHeadupdateurl("http://test.jzdwc888.com:10000/Management/src/uploadPricture.php");
			hh.setHeadrequrl("http://test.jzdwc888.com:10000/Management/src/uploadimg/");
		} else {
			hh.setHeadupdateurl("http://req.jzdwc888.com:10000/Management/src/uploadPricture.php");
			hh.setHeadrequrl("http://req.jzdwc888.com:10000/Management/src/uploadimg/");
		}
		
		for (int i = p.getContinueLoginReward(); i <= p.getContinueLoginDay(); ++i) {
			MT_Data_GCLoginReward conf = TableManager.GetInstance().TableGCLoginReward().GetElement(i);
			if (conf == null)
				continue;
			
			hh.getContinueLoginRewards().add(i);
		}
		
		if (rotary_reward_idx >= 0) {
			if (rotary_reward_idx == 0) {
				int ran = RandomUtil.RangeRandom(1, 9999);
				int sum = 0;
				int target_reward_idx = 0;
				for (int i = 1; i <=8 ; i++) {
					MT_Data_RotaryReward zp = TableManager.GetInstance().TableRotaryReward().GetElement(i);
					if (target_reward_idx == 0) {
						sum = sum + zp.getM_rewards1().field3();
						if (ran <= sum) {
							target_reward_idx = i;
						}
					}
				}
				
				p.setRotaryReward(target_reward_idx);
				rotary_reward_idx = target_reward_idx;
			}
			
			for (int i = 1; i <= 8; i++) {
				MT_Data_RotaryReward zp = TableManager.GetInstance().TableRotaryReward().GetElement(i);
				addItem(i, zp.getM_rewards1(), hh.getRotaryRewardList());
			}
			
			hh.setRewardId(rotary_reward_idx);
		}
		
		hh.setNoticeVersion(1);
		con.send(hh);
		
		final String pname = con.getPlayer().getName();
		final long pid = con.getPlayerId();
		ranklogin(pname, pid);
		
		if (con.getChannel() == 11) {
			sendLottyDis(con);
		}
	}
	
	public void sendLottyDis(PlayerConnection con) {
		GDL_G2C_FunctionOpen fun_open = new GDL_G2C_FunctionOpen();
		
		if (con.getChannel() == 11) {
			FuncSetting lotty = new FuncSetting();
			lotty.setFunId(3);
			lotty.setOpen(0);
			fun_open.getFuncs().add(lotty);
		}
		
		if (con.getChannel() == 4 || 
				con.getChannel() == 7 || 
				con.getChannel() == 12) {
			FuncSetting lotty1 = new FuncSetting();
			lotty1.setFunId(1);
			lotty1.setOpen(0);
			fun_open.getFuncs().add(lotty1);
			
			FuncSetting lotty2 = new FuncSetting();
			lotty2.setFunId(2);
			lotty2.setOpen(0);
			fun_open.getFuncs().add(lotty2);
		}
		con.send(fun_open);
	}

	public static void sendFuncOpen(PlayerConnection con) {
		GDL_G2C_FunctionOpen fun_open = new GDL_G2C_FunctionOpen();
		if (!ServerConfig.app_test) {
			Calendar calendar2 = TimeUtil.GetCalendar();
	    	if (calendar2.get(Calendar.HOUR_OF_DAY) >= 0 && calendar2.get(Calendar.HOUR_OF_DAY) <= 9) {
//	    		for (int fi = 1; fi <= 2; ++fi) {
//					FuncSetting alipay = new FuncSetting();
//					alipay.setFunId(fi);
//					alipay.setOpen(0);
//					fun_open.getFuncs().add(alipay);
//				}
	    	}
		}
		con.send(fun_open);
	}
	
	private void ranklogin(final String pname, final long pid) {
		int rk = RankManager.getInstance().getMyRank(0, pid);
		if (rk == 1) {
			if (!testCD(pid))
				return ;
			
			ThreadPoolManager.getInstance().schedule(new Runnable() {
				@Override
				public void run() {
					ChatHandler.newbanner(56, pname, pid);
				}
			}, 5 * 1000);
			
			return ;
		}
		
		rk = RankManager.getInstance().getMyRank(1, pid);
		if (rk == 1) {
			if (!testCD(pid))
				return ;
			
			ThreadPoolManager.getInstance().schedule(new Runnable() {
				@Override
				public void run() {
					ChatHandler.newbanner(57, pname, pid);
				}
			}, 5 * 1000);
			
			return ;
		}
		
		rk = RankManager.getInstance().getMyRank(2, pid);
		if (rk == 1) {
			if (!testCD(pid))
				return ;
			
			ThreadPoolManager.getInstance().schedule(new Runnable() {
				@Override
				public void run() {
					ChatHandler.newbanner(58, pname, pid);
				}
			}, 5 * 1000);
			
			return ;
		}
	}
	
	private void addItem(int id, Int3 it, List<RewardItem> list) {
		RewardItem k = new RewardItem();
		k.setId(id);
		k.setItemId(it.field1());
		k.setItemNum(it.field2());
		list.add(k);
	}
}
