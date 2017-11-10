package com.gdl.data;

import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.GDL_C2G_UseItem;
import gameserver.jsonprotocol.GDL_G2C_BeginSlotsRe;
import gameserver.jsonprotocol.GDL_G2C_GetItemInfoRe;
import gameserver.jsonprotocol.GDL_G2C_GetLevelShopRewardRe;
import gameserver.jsonprotocol.GDL_G2C_SetGiftShowRe;
import gameserver.jsonprotocol.GiftShowInfo;
import gameserver.logging.LogService;
import gameserver.stat.StatManger;
import gameserver.utils.DbMgr;
import gameserver.utils.Util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.Int3;
import table.MT_Data_GGiftPackageShop;
import table.MT_Data_GRechargeBack;
import table.MT_Data_GRechargePolicy;
import table.MT_Data_GStoreItem;
import table.base.TableManager;

import com.commons.network.websock.PlayerConnection;
import com.commons.network.websock.handler.ItemHandler;
import com.commons.util.DatabaseUtil;
import com.commons.util.RandomUtil;
import com.commons.util.TimeUtil;
import com.gdl.game.BaijialeGameInstanceManager;
import com.gdl.game.FishGameInstanceManager;
import com.gdl.game.FriutSlotsGameInstanceManager;
import com.gdl.game.GameInstanceMrg;
import com.gdl.game.GoldSlotsGameInstanceManager;
import com.gdl.game.NiuniuGameInstanceManager;
import com.gdl.manager.LottyManager;
import commonality.Common;

import database.gdl.gameserver.CustomGiftShow;
import database.gdl.gameserver.CustomInt1Info;
import database.gdl.gameserver.DatabaseItem;
import database.gdl.gameserver.DatabasePlayer;
import databaseshare.CustomLong1Info;
import databaseshare.CustomMillStone;
import databaseshare.DatabaseAccount;
import databaseshare.DatabaseMoney_static;
import databaseshare.DatabasePlayer_brief_info;
import databaseshare.DatabaseRecharge_cache2;

public class PlayerData {
	
	private static final Logger logger = LoggerFactory.getLogger(PlayerData.class);
	
	DatabaseUtil m_db;
	DatabaseAccount m_account;
	DatabasePlayer m_Player;
	DatabasePlayer_brief_info m_Brief_info;
	DatabaseMoney_static m_moneyInfo;
	boolean is_dirty = false;
	
	ItemData m_itemData;
	MailData m_mailData;
	ActiveRewardData m_activeRewardData;
	PlayerMem m_mem;
	GDL_G2C_BeginSlotsRe m_curSlot;
	int pre_line_money;
	int line_num;
	int guess_rate;
	int free_slots_count;
	int free_slots_money;
	boolean isFree;
	boolean isForce;
	long next_check_show_gift_time;
	long next_save_time;
	int slot_level;
	int game_uid;
	
	long last_chat_time;
	
	public boolean isChatCD() {
		long noe = TimeUtil.GetDateTime();
		if (last_chat_time + 1000 > noe)
			return true;
		
		last_chat_time = noe;
		return false;
	}
	
	public int getGameUID() {
		return game_uid;
	}

	public void setGameUID(int game_id) {
		this.game_uid = game_id;
	}
	
	public void flushRecharge(PlayerConnection con) {
		List<DatabaseRecharge_cache2> res = DbMgr.getInstance().getShareDB().Select(DatabaseRecharge_cache2.class, "player_id=? and mask = 0", getPlayerId());
		if (res.isEmpty())
			return ;
		
		for (DatabaseRecharge_cache2 re : res) {
			re.mask = 1;
			re.get_time = TimeUtil.GetDateTime();
			re.save();
			
			if (re.shop_type == 1) {
				MT_Data_GStoreItem gst = TableManager.GetInstance().TableGStoreItem().GetElement(re.tid);
				int org_p = gst.price() * 100;
				Float data_p = re.amount;
				
				if (org_p != data_p.intValue()) {
					continue;
				}
			} else {
				MT_Data_GGiftPackageShop gst = TableManager.GetInstance().TableGGiftPackageShop().GetElement(re.tid);
				int org_p = gst.price() * 100;
				Float data_p = re.amount;
				
				if (org_p != data_p.intValue()) {
					continue;
				}
			}
			
			giveRecharge(con, re.shop_type, re.tid);
		}
		
		save();
	}
	
	public boolean canByGift(PlayerConnection con, int tid) {
		ItemData idata = con.getPlayer().getItemData();
		MT_Data_GGiftPackageShop gst = TableManager.GetInstance().TableGGiftPackageShop().GetElement(tid);
		if (idata.getItemCountByTempId(gst.bought_itemTempId()) >= gst.max_count())
			return false;
		
		return true;
	}
	
	public void beginNewPolicy(PlayerConnection con, ItemData idata, boolean isRechargeMode, MT_Data_GRechargePolicy grp) {
		idata.setItem(con, Consts.getGame_Cost_Count(), 0, 0);
		idata.setItem(con, Consts.getGame_Earn_Count(), 0, 0);
		idata.setItem(con, grp.step_id(), 0, 0);
		
		int change_num = RandomUtil.RangeRandom(grp.bodong0().field1(), grp.bodong0().field2());
		idata.setItem(con, 114, change_num, 0);
	}
	
	public static class Ran {
		public int b;
		public int h;
		public Ran(int b, int h) {
			this.b = b;
			this.h = h;
		}
	}
	
	private void afterRecharge(PlayerConnection con, int price) {
		ItemData idata = con.getPlayer().getItemData();
		idata.addItem(con, Consts.getDay_Recharge_Value(), price, Consts.getItemEventRecharge());
		
		//?
		
		long read = price * 10 * 1000;
		MT_Data_GRechargeBack gg = TableManager.GetInstance().TableGRechargeBack().GetElement(price);
		if (gg != null) {
			int viplvl = getVipLvl();
			Int2 yyyy = null;
			switch (viplvl) {
			case 0:
				yyyy = gg.vip0();
				break;
			case 1:
				yyyy = gg.vip1();
				break;
			case 2:
				yyyy = gg.vip2();
				break;
			case 3:
				yyyy = gg.vip3();
				break;
			case 4:
				yyyy = gg.vip4();
				break;
			case 5:
				yyyy = gg.vip5();
				break;
			case 6:
				yyyy = gg.vip6();
				break;
			case 7:
				yyyy = gg.vip7();
				break;
			case 8:
				yyyy = gg.vip8();
				break;
			case 9:
				yyyy = gg.vip9();
				break;
			case 10:
				yyyy = gg.vip10();
				break;
			}
			
			if (yyyy != null)
				read = read * RandomUtil.RangeRandom(yyyy.field1(), yyyy.field2()) / 10000;
		}
		
		// 充值相当于系统欠玩家的钱
		//idata.addItem(con, Consts.getGame_Cost_Count(), read, Consts.getItemEventRecharge());
		idata.addItem(con, 140, read, Consts.getItemEventRecharge());
		idata.addItem(con, Consts.getVIP_EXP_ID(), price, Consts.getItemEventRecharge());
		
		int[] arte_low = {1000, 80, 80, 80, 80, 80, 80, 80, 60, 60, 60};
		int[] arte_top = {1000, 120, 120, 120, 120, 120, 120, 120, 100, 100, 100};
		double price_d = price;
		price_d = price_d * RandomUtil.RangeRandom(arte_low[getVipLvl()], arte_top[getVipLvl()]) / 1000;
		idata.addItem(con, 119, (long)price_d, Consts.getItemEventRecharge());
		
		/*
		int mode = (int)idata.getItemCountByTempId(116);
		if (mode == 0) {
			// 不是充值曲线，就加入到充值累计返还量中
			idata.addItem(con, 115, price, Consts.getItemEventRecharge());
		} else {
			// 是充值曲线，就抬高充值返回点位
			idata.addItem(con, 117, price * 10000, Consts.getItemEventRecharge());
		}
		*/
	}
	
	public void giveRecharge(PlayerConnection con, int shoptype, int tid) {
		ItemData idata = con.getPlayer().getItemData();
		if (shoptype == 1) {
			MT_Data_GStoreItem gst = TableManager.GetInstance().TableGStoreItem().GetElement(tid);
			
			int item_number = gst.itemCount();
			/*
			int first_flag = 77 + gst.id();
			long flag = idata.getItemCountByTempId(first_flag);
			if (flag == 0) {
				idata.setItem(con, first_flag, 1, -1);
				item_number = item_number * 2;
			}*/
			
			long global_flag = idata.getItemCountByTempId(77);
			if (global_flag == 0) {
				idata.setItem(con, 77, 1, -1);
				
				MT_Data_GRechargeBack grb = TableManager.GetInstance().TableGRechargeBack().GetElement(gst.price());
				if (grb != null) {
					if (!grb.mailitem().isEmpty()) {
						Integer[] its = new Integer[grb.mailitem().size() * 2];
						for (int i = 0; i < grb.mailitem().size(); ++i) {
							its[i * 2] = grb.mailitem().get(i).field1();
							its[i * 2 + 1] = grb.mailitem().get(i).field2();
						}
						MailData.sendSystemMail(con, "首充禮包", "系统", "首充大禮包！！！", 5, its);
					}
				}
			}
			
			idata.addItem(con, gst.itemId(), item_number, Consts.getItemEventRecharge());
			ItemHandler.showAddItem(con, gst.itemId(), item_number);
			
			{
				idata.addItem(con, gst.itemId2(), gst.itemCount2(), Consts.getItemEventRecharge());
				ItemHandler.showAddItem(con, gst.itemId2(), gst.itemCount2());
			}
			
			afterRecharge(con, gst.price());
		} else {
			MT_Data_GGiftPackageShop gst = TableManager.GetInstance().TableGGiftPackageShop().GetElement(tid);
			if (!canByGift(con, tid)) {
				logger.error("player{} will buy already buy {} giftpackageshop", con.getPlayerId(), tid);
				return ;
			}
			
			idata.addItem(con, gst.bought_itemTempId(), 1, Consts.getItemEventRecharge());
			long item_id = idata.addItem(con, gst.itemTempId(), 1, Consts.getItemEventRecharge());
			GDL_C2G_UseItem msg = new GDL_C2G_UseItem();
			msg.setItemObjId(item_id);
			msg.setMode(0);
			ItemHandler.getInstance().OnUseItem(con, msg);
			afterRecharge(con, gst.price());
		}
	}

	public PlayerData(DatabaseAccount acc, DatabasePlayer dp, DatabasePlayer_brief_info binfo, DatabaseMoney_static moneyInfo) {
		m_db = DbMgr.getInstance().getDb(acc.getDb_id());
		m_Player = dp;
		m_account = acc;
		m_Brief_info = binfo;
		m_moneyInfo = moneyInfo;
		is_dirty = false;
		
		flushCheckGiftExpireTIme();
		m_mem = new PlayerMem();
	}
	
	public void setHead(String head) {
		m_Brief_info.head_url = head;
		afterSet();
	}
	
	public int getFristGiftId() {
		if (m_Player.showgift_config.isEmpty())
			return 0;
		
		return m_Player.showgift_config.get(0).id;
	}
	
	private void flushCheckGiftExpireTIme() {
		long now_time = TimeUtil.GetDateTime();
		if (m_Player.showgift_config.isEmpty()) {
			next_check_show_gift_time = now_time + 3600 * 1000 * 300;
		} else {
			long near_t = now_time;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (CustomGiftShow sg : m_Player.showgift_config) {
				Date exp_t;
				try {
					exp_t = sdf.parse(sg.exp_time);
					if (exp_t.getTime() < near_t)
						near_t = exp_t.getTime();
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			m_Brief_info.showgift.clear();
			for (CustomGiftShow iii : m_Player.showgift_config) {
				m_Brief_info.showgift.add(new databaseshare.CustomGiftShow(iii.id, iii.num, iii.exp_time));
			}
			next_check_show_gift_time = near_t;
		}
	}
	
	public GDL_G2C_BeginSlotsRe getCurSlots() {
		return this.m_curSlot;
	}
	
	public void setCurSlots(GDL_G2C_BeginSlotsRe re, int pre_money, int lineNum, boolean isFree) {
		m_curSlot = re;
		this.pre_line_money = pre_money;
		this.line_num = lineNum;
		this.guess_rate = 1;
		this.isFree = isFree;
		if (re == null)
			this.isForce = false;
	}
	
	public void setIsForce(boolean f) {
		this.isForce = f;
	}
	
	public boolean isForce() {
		return this.isForce;
	}
	
	public boolean isFreeSlot() {
		return isFree;
	}
	
	public int getGuessRate() {
		return this.guess_rate;
	}
	
	public int getFreeSlot() {
		return this.free_slots_count;
	}
	
	public void decFreeSlot() {
		this.free_slots_count--;
	}
	
	public int getFreeMoney() {
		return this.free_slots_money;
	}
	
	public void setFreeSlot(int fsc, int free_money) {
		this.free_slots_count = fsc;
		this.free_slots_money = free_money;
	}
	
	public void setGuessRate(int rate) {
		this.guess_rate = rate;
	}
	
	public int getPreMoney() {
		return this.pre_line_money;
	}
	
	public int getLintNum() {
		return this.line_num;
	}
	
	public long getCoin() {
		return this.m_itemData.getItemCountByTempId(Consts.getCOIN_ID());
	}
	
	public long getGold() {
		return this.m_itemData.getItemCountByTempId(Consts.getGOLD_ID());
	}
	
	public long getLike() {
		return this.m_itemData.getItemCountByTempId(Consts.getLIKEID());
	}
	
	public boolean init() {
		List<DatabaseItem> dbutems = m_db.Select(DatabaseItem.class, "player_id=?", m_Player.getPlayer_id());
		if (dbutems == null)
			return false;
		
		m_itemData = new ItemData(dbutems);
		int lvl = (int)m_itemData.getItemCountByTempId(Consts.getLVL_ID());
		int vlvl = (int)m_itemData.getItemCountByTempId(Consts.getVIP_LVL_ID());
		if (!m_itemData.init(m_db, m_Player.getPlayer_id(), lvl, vlvl) )
			return false;
		
		m_mailData = new MailData();
		m_mailData.init(this);
		
		m_activeRewardData = new ActiveRewardData();
		m_activeRewardData.init(this);
		
		
		next_save_time = TimeUtil.GetDateTime() + 10 * 60 * 1000;
		return true;
	}
	
	public void checkData(PlayerConnection con) {
		long now_time = TimeUtil.GetDateTime();
		if (now_time > next_check_show_gift_time) {
			
			Iterator<CustomGiftShow> iter = m_Player.showgift_config.iterator();
			while (iter.hasNext()) {
				CustomGiftShow temp = iter.next();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				long exp_t;
				try {
					exp_t = sdf.parse(temp.exp_time).getTime();
					if (exp_t < now_time) {
						iter.remove();
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			flushCheckGiftExpireTIme();
		}
		
		m_itemData.syncItemChange(con);
		
		
		//save
		if (now_time > next_save_time) {
			next_save_time = now_time + 10 * 60 * 1000;
			save();
		}
	}
	
	public DatabasePlayer_brief_info getBriefInfo() {
		return m_Brief_info;
	}
	
	public DatabaseMoney_static getMoneyStatic() {
		return m_moneyInfo;
	}
	
	public void playerPool(int gid, long coin) {
		String info = null;
		String time = TimeUtil.GetDateString().substring(11);
		if (gid == Consts.getFriutSlot()) {
			info = " 获得水果奖池奖励 " +coin + "金币";
		} else if (gid == Consts.getGoldSlot()) {
			info = " 获得金砖奖池奖励 " +coin + "金币";
		} else if (gid == Consts.getFisher()) {
			info = " 获得捕鱼奖池奖励 " +coin + "金币";
		}
		
		if (m_moneyInfo.millstone.size() >= 7)
			m_moneyInfo.millstone.remove(m_moneyInfo.millstone.size() -1);
		m_moneyInfo.millstone.add(0, new CustomMillStone(time + info));
		afterSet();
	}
	
	public void playerFishTaskComplete() {
		m_moneyInfo.fish_task_count += 1;
		afterSet();
	}
	
	public void playerRecordDayInfo(long dayc) {
		if (m_moneyInfo.days7.size() >=7) 
			m_moneyInfo.days7.remove(6);
		m_moneyInfo.days7.add(0, new CustomLong1Info(dayc));
		if (dayc > m_moneyInfo.max_day)
			m_moneyInfo.max_day = dayc;
		afterSet();
	}
	
	public void playerCatchFish(int ft) {
		m_moneyInfo.fish_catch_fish_total += 1;
		if (ft == 3)
			m_moneyInfo.fish_battle_kingfish += 1;
		else if (ft == 4)
			m_moneyInfo.fish_battle_doomfish += 1;
		afterSet();
	}
	
	public void playerFishTotal(Long coin) {
		m_moneyInfo.fish_total += coin;
		m_moneyInfo.total = getExp();
		m_Brief_info.yestday_eran += coin;
		if (coin > m_moneyInfo.fish_max)
			m_moneyInfo.fish_max = coin;
		afterSet();
	}
	
	public void playerSlotBegin(long res) {
		m_moneyInfo.fruits_slot_count += 1;
		afterSet();
	}
	
	public void playerFruitsActiveTop3() {
		m_moneyInfo.fruits_slot_rose_top3_count += 1;
		afterSet();
	}
	
	public void playerGoldActive(int gold) {
		m_moneyInfo.gold_slot_gold += gold;
		afterSet();
	}
	
	public void playerSlotCount(int gameid) {
		if (gameid == Consts.getFriutSlot()) {
			m_moneyInfo.fruits_slot_count += 1;
		} else {
			m_moneyInfo.gold_slot_count += 1;
		}
		afterSet();
	}
	
	public void playerSlotEarn(int gameid, long res) {
		if (gameid == Consts.getFriutSlot()) {
			m_moneyInfo.fruits_slot_total += res;
			if (res > m_moneyInfo.fruits_slot_max)
				m_moneyInfo.fruits_slot_max = res;
		}
		else {
			m_moneyInfo.gold_slot_total += res;
			if (res > m_moneyInfo.gold_slot_max)
				m_moneyInfo.gold_slot_max = res;
		}
		m_Brief_info.yestday_eran += res;
		m_moneyInfo.total = getExp();
		afterSet();
	}
	
	public void playerSlotPool(int gameid, long res) {
		if (gameid == Consts.getFriutSlot()) {
			m_moneyInfo.fruits_slot_total += res;
			m_moneyInfo.fruits_slot_pool_total += res;
			m_moneyInfo.fruits_slot_pool_count += 1;
		} else {
			m_moneyInfo.gold_slot_total += res;
			m_moneyInfo.gold_slot_pool_total += res;
			m_moneyInfo.gold_slot_pool_count += 1;
		}
		afterSet();
	}
	
	public void playerNiuNiuStatic(boolean win, long money, boolean z) {
		if (win) {
			if (z) {
				m_moneyInfo.niuniu_z_win += money;
			} else {
				m_moneyInfo.niuniu_x_win += money;
			}
		} else {
			if (z) {
				m_moneyInfo.niuniu_z_lose += money;
			} else {
				m_moneyInfo.niuniu_x_lose += money;
			}
		}
		
		afterSet();
	}
	
	public GDL_G2C_GetItemInfoRe packetAllItem() {
		return m_itemData.packetAllItem();
	}
	
	public boolean isLevelRewardGot(int lvl) {
		for (CustomInt1Info i : m_Player.level_rewards) {
			if (i.val1 == lvl)
				return true;
		}
		
		return false;
	}
	
	public GDL_G2C_GetLevelShopRewardRe getLevelRewardInfo() {
		GDL_G2C_GetLevelShopRewardRe re = new GDL_G2C_GetLevelShopRewardRe();
		
		for (CustomInt1Info i : m_Player.level_rewards) {
			re.getLevel().add(i.val1);
		}
		
		return re;
	}
	
	public void setLevelRewardGot(int lvl) {
		m_Player.level_rewards.add(new CustomInt1Info(lvl));
		afterSet();
	}
	
	public MailData getMailData() {
		return m_mailData;
	}
	
	public ActiveRewardData getActiveData() {
		return m_activeRewardData;
	}
	
	public DatabaseUtil getDB() {
		return m_db;
	}
	
	public ItemData getItemData() {
		return m_itemData;
	}
	
	public int getChannel() {
		return m_account.channel;
	}
	
	public void flushChangePhoneCode(String code) {
		m_account.change_phone_code = code;
		m_account.change_phone_code_expire_time = TimeUtil.GetDateTime() + 2 * 60 * 1000;
		m_account.save();
	}
	
	public void setName(String name) {
		m_Player.name = name;
		m_Brief_info.name = name;
		afterSet();
	}
	
	public void setSex(int sex) {
		m_Player.sex = sex;
		m_Brief_info.sex = sex;
		afterSet();
	}
	
	public void setLottyRewardCoinIdx(int ridx) {
		m_Player.lotty_reward_coin_idx = ridx;
		afterSet();
	}
	
	public int getLottyRewardCoinIdx() {
		return m_Player.lotty_reward_coin_idx;
	}
	
	public void setLottyRewardGoldIdx(int ridx) {
		m_Player.lotty_reward_gold_idx = ridx;
		afterSet();
	}
	
	public int getLottyRewardGoldIdx() {
		return m_Player.lotty_reward_gold_idx;
	}
	
	public void sendLottyReward() {
		if (m_Player.lotty_reward_gold_idx == 0 && m_Player.lotty_reward_coin_idx == 0)
			return ;
		
		Int3 reward = null;
		if (m_Player.lotty_reward_coin_idx != 0) {
			reward = TableManager.GetInstance().TableLotty().GetElement(m_Player.lotty_reward_coin_idx).coinitem();
			setLottyRewardCoinIdx(0);
		} else {
			reward = TableManager.GetInstance().TableLotty().GetElement(m_Player.lotty_reward_gold_idx).golditem();
			setLottyRewardGoldIdx(0);
		}
		
		Integer[] re = {reward.field1(), reward.field2()};
		MailData.createSysOfflineMail(m_Player.player_id, "抽獎補發", "系统", "上次抽獎過程中掉線了，這裏補發獎勵", 10, re);
		LottyManager.getInstance().putNewLotty(reward, getName());
	}
	
	public void setSign(String sign) {
		m_Brief_info.sign = sign;
		afterSet();
	}
	
	public String getChangePhoneCode() {
		if (TimeUtil.GetDateTime() + 15 * 1000 > m_account.change_phone_code_expire_time) {
			return "";
		}
		return m_account.change_phone_code;
	}
	
	public String getTempUUId() {
		return m_account.temp_uid;
	}
	
	public long getChangePhoneExpireTime() {
		return m_account.change_phone_code_expire_time;
	}
	
	public boolean isGiftAlreadyShow(int table_id) {
		for (CustomGiftShow cgw : m_Player.showgift_config) {
			if (cgw.id == table_id)
				return false;
		}
		
		return true;
	}
	
	public void setConfigGift(PlayerConnection con, int table_id, int num, String exp_time) {
		m_Player.showgift_config.add(0, new CustomGiftShow(table_id, num, exp_time));
		if (m_Player.showgift_config.size() > 4)
			m_Player.showgift_config = m_Player.showgift_config.subList(0, 4);
		m_Brief_info.showgift.clear();
		for (CustomGiftShow iii : m_Player.showgift_config) {
			m_Brief_info.showgift.add(new databaseshare.CustomGiftShow(iii.id, iii.num, iii.exp_time));
		}
		afterSet();
		syncGiftShow(con);
	}
	
	public List<CustomGiftShow> getGiftShow() {
		return m_Player.showgift_config;
	}
	
	public void syncGiftShow(PlayerConnection con) {
		GDL_G2C_SetGiftShowRe sgsr = new GDL_G2C_SetGiftShowRe();
		for (CustomGiftShow cgw : m_Player.showgift_config) {
			GiftShowInfo t = new GiftShowInfo();
			t.setItemTempId(cgw.id);
			t.setNum(cgw.num);
			t.setExpireTime(cgw.exp_time);
			sgsr.getShowGiftList().add(t);
		}
		con.send(sgsr);
	}
	
	public long getPlayerId() {
		return m_Player.getPlayer_id();
	}
	
	public int getLvl() {
		return (int)m_itemData.getItemCountByTempId(Consts.getLVL_ID());
	}
	
	public long getExp() {
		return m_itemData.getItemCountByTempId(Consts.getEXP_ID());
	}
	
	public int getSex() {
		return m_Player.getSex();
	}
	
	public String getSign() {
		return m_Brief_info.getSign();
	}
	
	public int getVipLvl() {
		return (int)m_itemData.getItemCountByTempId(Consts.getVIP_LVL_ID());
	}
	
	public long getVipExp() {
		return m_itemData.getItemCountByTempId(Consts.getVIP_EXP_ID());
	}
	
	public String getName() {
		return m_Player.getName();
	}
	
	public String getHeadUrl() {
		return m_Brief_info.head_url;
	}
	
	public int getContinueLoginDay() {
		return m_Player.continue_login_num;
	}
	
	public int getContinueLoginReward() {
		return m_Player.continue_login_reward;
	}
	
	public void setContinueLoginReward(int v) {
		m_Player.continue_login_reward = v;
		afterSet();
	}
	
	// 注意，这里保存数据库直接在外面做了
	public void changeName(String name) {
		m_Player.name = name;
	}
	
	public String getNation() {
		return m_Player.getNation();
	}
	
	public int getRotaryRewardIdx() {
		return m_Player.getRotary_reward_get();
	}
	
	public String getCreateTime() {
		Timestamp timestamp = TimeUtil.GetTimestamp(m_account.create_time);
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateformat.format(timestamp);
	}
	
	public String getLastActiveTime() {
		Timestamp timestamp = TimeUtil.GetTimestamp(m_Player.last_offline_time);
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateformat.format(timestamp);
	}
	
	public void setRotaryReward(int idx) {
		m_Player.rotary_reward_get = idx;
		afterSet();
	}
	
	public void afterSet() {
		is_dirty = true;
	}
	
	public void onDisconnection(PlayerConnection con) {
		m_Player.last_offline_time = TimeUtil.GetDateTime();
		if (game_uid <= 0)
			return ;
		
		int gameid = GameInstanceMrg.getGameId(game_uid);
		int levelid = GameInstanceMrg.getLevelId(game_uid);
		if (gameid == Consts.getFriutSlot())
			FriutSlotsGameInstanceManager.getInstance().leaveGame(con, game_uid, gameid, levelid);
		else if (gameid == Consts.getGoldSlot())
			GoldSlotsGameInstanceManager.getInstance().leaveGame(con, game_uid, gameid, levelid);
		else if (gameid == Consts.getFisher())
			FishGameInstanceManager.getInstance().leaveGame(con, game_uid, gameid, levelid);
		else if (gameid == Consts.getNiuNiu())
			NiuniuGameInstanceManager.getInstance().leaveGame(con, game_uid, gameid, levelid, true);
		else if (gameid == Consts.getBaijiale())
			BaijialeGameInstanceManager.getInstance().leaveGame(con, game_uid, gameid, levelid, true);
	}
	
	public void ban() {
		m_account.banned = 1;
		m_account.banned_time = TimeUtil.GetDateTime() + Common.MONTH_MILLISECOND * 6;
		is_dirty = true;
	}
	
	public void save() {
		if (is_dirty = true) {
			is_dirty = false;
			this.m_account.save();
			this.m_Player.save();
			flushPlayerBriefInfo();
			this.m_Brief_info.save();
			this.m_moneyInfo.save();
		}
		
		this.m_itemData.save();
		m_mem = null;
	}
	
	public void flushPlayerBriefInfo() {
		m_Brief_info.name = m_Player.name;
		m_Brief_info.level = getLvl();
		m_Brief_info.viplevel = getVipLvl();
		m_Brief_info.money = m_itemData.getItemCountByTempId(Consts.getCOIN_ID());
		m_Brief_info.gold = m_itemData.getItemCountByTempId(Consts.getGOLD_ID());
		m_Brief_info.exp = m_itemData.getItemCountByTempId(Consts.getEXP_ID());
		m_Brief_info.liked = m_itemData.getItemCountByTempId(Consts.getLIKEID());
		m_Brief_info.last_active_time = TimeUtil.GetDateTime();
		
		m_Brief_info.showgift.clear();
		for (CustomGiftShow iii : m_Player.showgift_config) {
			m_Brief_info.showgift.add(new databaseshare.CustomGiftShow(iii.id, iii.num, iii.exp_time));
		}
		
		// 用来排行的金币数 = 身上的金币 + 身上的金砖 * 10000
		m_Brief_info.rank_money = m_itemData.getItemCountByTempId(Consts.getCOIN_ID()) 
					+ m_itemData.getItemCountByTempId(Consts.getGOLD_ID()) * 10000;
		
		// 用来排行的经验数 = 累计经验数 
		m_Brief_info.rank_level = m_itemData.getItemCountByTempId(Consts.getEXP_ID());
		
		// 
		m_Brief_info.rank_liked = m_itemData.getItemCountByTempId(Consts.getLIKEID());
	}
	
	public boolean isFirstLogin() {
		long now = TimeUtil.GetDateTime();
		if (!TimeUtil.IsSameDay(m_Player.last_flush_time, now)) {
			return true;
		}
		
		return false;
	}
	
	public void checkOnNewDayOnLogin() {
		long now = TimeUtil.GetDateTime();
		if (!TimeUtil.IsSameDay(m_Player.last_flush_time, now)) {
			onNewDay(now, true);
		}
	}
	
	public long getItemCountByTempId(int item_temp_id) {
		return m_itemData.getItemCountByTempId(item_temp_id);
	}
	
	public void onNewDay(long now, boolean isLogin) {
		logger.debug("{}[{}] 于 {} 进行了每天一次的初始化操作", m_Player.getName(),
				m_Player.player_id, TimeUtil.GetDateString());
		m_Player.total_login_num++;
		m_Player.rotary_reward_get = 0;
		if (Util.iscontinueLogin(now, m_Player.last_flush_time)) {
			m_Player.continue_login_num++;
			/*
			if (m_Player.continue_login_reward == 5) {
				m_Player.continue_login_reward = 0;
			}
			if (ActiveService.getInstance().isActiveRun(ACTIVETYPE.ACTIVE_SEVEN_DAY.Number())) {
				int dayNum = m_Con.getReward().getActiveRewardIdx(ACTIVETYPE.ACTIVE_SEVEN_DAY.Number());
				if (dayNum < 7) {
					m_Con.getMails().addSevenDayMail(dayNum+1);
					m_Con.getReward().incActiveRewardIdx(ACTIVETYPE.ACTIVE_SEVEN_DAY.Number(), 1);
				}
			}
			*/
			
		} else {
			m_Player.continue_login_num = 1;
			m_Player.continue_login_reward = 0; // 连续登录奖励领取状态清零
			/*
			m_Player.continue_login_reward = 0;
			if (ActiveService.getInstance().isActiveRun(ACTIVETYPE.ACTIVE_SEVEN_DAY.Number())) {
				int dayNum = m_Con.getReward().getActiveRewardIdx(ACTIVETYPE.ACTIVE_SEVEN_DAY.Number());
				if (dayNum < 7) {
					m_Con.getMails().addSevenDayMail(1);
					m_Con.getReward().setActiveRewardIdx(ACTIVETYPE.ACTIVE_SEVEN_DAY.Number(), 1);
				}
			}*/
		}
		
		m_itemData.onNewDay(now, isLogin, this);
		if ( !TimeUtil.IsSameWeek(now,  m_Player.last_flush_time) ) {
			m_itemData.onNewWeek(isLogin);
		}
		
		long old_last_flush_time = m_Player.last_flush_time;
		m_Player.last_flush_time = TimeUtil.GetDateTime() + 2000;
		save();

		LogService.logEvent(m_Player.getPlayer_id(), 0, 27,
				(int) (m_Player.last_flush_time / 1000), (int)(old_last_flush_time / 1000));
		LogService.logLogIn(m_Player.getPlayer_id(), getNation(), getCreateTime(), getLvl());
		StatManger.getInstance().onNewDay(m_Player.create_time);
	}
}
