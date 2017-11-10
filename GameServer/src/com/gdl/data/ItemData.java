package com.gdl.data;


import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.GDL_G2C_GetItemInfoRe;
import gameserver.jsonprotocol.GDL_G2C_ShowToast;
import gameserver.jsonprotocol.ItemInfoMsg;
import gameserver.logging.LogService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_GItem;
import table.MT_Data_GVip;
import table.base.TableManager;

import com.commons.network.websock.PlayerConnection;
import com.commons.network.websock.handler.ChatHandler;
import com.commons.network.websock.handler.ItemHandler;
import com.commons.util.DatabaseInsertUpdateResult;
import com.commons.util.DatabaseUtil;
import com.commons.util.TimeUtil;

import database.gdl.gameserver.DatabaseItem;

public class ItemData {
	private static final Logger logger = LoggerFactory.getLogger(ItemData.class);
			
	Map<Long, DatabaseItem> m_Items = new TreeMap<Long, DatabaseItem>();
	Map<Integer, DatabaseItem> m_vItems = new TreeMap<Integer, DatabaseItem>();
	List<Long> m_syncItemId = new ArrayList<Long>();
	boolean is_dirty = false;	
	
	public ItemData(List<DatabaseItem> items) {
		for (DatabaseItem it : items) {
			m_Items.put(it.item_id, it);
			
			if (isVirtualItem(it.table_id))
				m_vItems.put(it.table_id, it);
		}
		
		is_dirty = false;
	}
	
	public boolean costCoolDown(PlayerConnection con, int temp_id, int cooltime) {
		return costCoolDown(con, temp_id, cooltime, true);
	}
	
	public boolean costCoolDown(PlayerConnection con, int temp_id, int cooltime, boolean dec) {
		long last_t = getItemCountByTempId(temp_id);
		long now = TimeUtil.GetDateTime();
		if (last_t + cooltime > now)
			return false;
		
		if (dec)
			setItem(con, temp_id, now, -5);
		return true;
	}
	
	public boolean init(DatabaseUtil db, long player_id, int lvl, int vlvl) {
		return tryRepairItem(db, player_id, lvl, vlvl);
	}
	
	private boolean tryRepairItem(DatabaseUtil db, long player_id, int lvl, int vlvl) {
		for (Integer itemid : TableManager.GetInstance().getAllVItemId()) {
			MT_Data_GItem temp = TableManager.GetInstance().TableGItem().GetElement(itemid);
			if (temp == null)
				continue;
			
			if (m_vItems.containsKey(itemid))
				continue;
			
			int init_num = temp.svr_initnum();
			if (-1L == rawAddItem(db, player_id, lvl, vlvl, itemid, init_num, 0))
				return false;
		}
		
		return true; 
	}
	
	public int getTempIdByItemId(long item_id) {
		DatabaseItem it = m_Items.get(item_id);
		if (it == null)
			return -1;
		
		return it.getTable_id();
	}
	
	public boolean checkItemNumByItemId(long item_id, int num) {
		DatabaseItem it = m_Items.get(item_id);
		if (it == null)
			return false;
		
		if (it.number < num)
			return false;
		
		return true;
	}
	
	public boolean checkItemNumByTempId(int temp_id, long num) {
		DatabaseItem it = m_vItems.get(temp_id);
		if (it == null) {
			it = getItemDataByTempId(temp_id);
			if (it == null)
				return false;
		}
			
		if (it.number < num)
			return false;
		
		return true;
	}
	
	public GDL_G2C_GetItemInfoRe packetAllItem() {
		return packetAllItem(m_Items);
	}
	
	public static GDL_G2C_GetItemInfoRe packetAllItem(Map<Long, DatabaseItem> Items) {
		GDL_G2C_GetItemInfoRe re = new GDL_G2C_GetItemInfoRe();
		re.setMode(0);
		List<ItemInfoMsg> tt = re.getItemList();
		for (DatabaseItem rr : Items.values()) {
			if (isNotSync(rr.table_id))
				continue;
			
			if (rr.number >0 || isVirtualItem(rr.table_id)) {
				ItemInfoMsg oo = new ItemInfoMsg();
				oo.setItemId(rr.item_id);
				oo.setItemTempId(rr.table_id);
				oo.setItemNum(rr.number);
				tt.add(oo);
			}
		}
		return re;
	}
	
	public void afterSet() {
		is_dirty = true;
	}
	
	public void save() {
		if (is_dirty = true) {
			is_dirty = false;
			for (DatabaseItem it : this.m_Items.values()) {
				it.save();
			}
		}
	}
	
	// 获取净赢取数
	public long getEran() {
		return m_vItems.get(Consts.getGame_Earn_Count()).number -
				m_vItems.get(Consts.getGame_Cost_Count()).number;
	}
	
	public long getItemCountByTempId(int item_temp_id) {
		long count = 0;
		if (m_vItems.containsKey(item_temp_id))
			return m_vItems.get(item_temp_id).number;
					
		for (DatabaseItem it : m_Items.values()) {
			if (it.table_id == item_temp_id)
				count += it.number;
		}
		return count;
	}
	
	public long getItemCountByObjId(long item_id) {
		if (m_Items.containsKey(item_id))
			return m_Items.get(item_id).number;
		
		return 0;
	}
	
	private DatabaseItem getItemDataByTempId(int item_temp_id) {
		for (DatabaseItem it : m_Items.values()) {
			if (it.table_id == item_temp_id)
				return it;
		}
		
		return null;
	}
	
	private DatabaseItem getItemDataByItemId(long item_id) {
		if (m_Items.containsKey(item_id)) {
			return m_Items.get(item_id);
		}
		
		return null;
	}	
	
	public static boolean isVirtualItem(int temp_id) {
		return temp_id < 200 || temp_id > 9000;
	}
	
	public static boolean isNotSync(int temp_id) {
		if (temp_id >= 120 && temp_id < 130)
			return false;
		
		if (temp_id > 9000 && temp_id <= 9999)
			return false;
		
		return temp_id >= 100 && temp_id <= 199;
	}
	
	public void syncItemV(PlayerConnection con, int itemTempid, int event) {
		DatabaseItem di = m_vItems.get(itemTempid);
		if (di == null)
			return ;
		
		GDL_G2C_GetItemInfoRe re = new GDL_G2C_GetItemInfoRe();
		re.setMode(1);
		ItemInfoMsg ii = new ItemInfoMsg();
		ii.setItemId(di.item_id);
		ii.setItemTempId(di.getTable_id());
		ii.setItemNum(di.number);
		re.setEvent(event);
		re.getItemList().add(ii);
		con.send(re);
	}
	
	public void syncItem(PlayerConnection con, long item_id, int event) {
		if (item_id == -1L)
			return ;
		
		DatabaseItem di = getItemDataByItemId(item_id);
		if (isNotSync(di.table_id))
			return ;
		
		GDL_G2C_GetItemInfoRe re = new GDL_G2C_GetItemInfoRe();
		re.setMode(1);
		ItemInfoMsg ii = new ItemInfoMsg();
		ii.setItemId(item_id);
		ii.setItemTempId(di.getTable_id());
		ii.setItemNum(di.number);
		re.setEvent(event);
		re.getItemList().add(ii);
		con.send(re);
				
//		if (!m_syncItemId.contains(item_id))
//			m_syncItemId.add(item_id);
	}
	
	public void syncItemChange(PlayerConnection con) {
		if (m_syncItemId.isEmpty())
			return ;
		
		GDL_G2C_GetItemInfoRe re = new GDL_G2C_GetItemInfoRe();
		re.setMode(1);
		for (long iid : m_syncItemId) {
			DatabaseItem di = getItemDataByItemId(iid);
			ItemInfoMsg ii = new ItemInfoMsg();
			ii.setItemId(iid);
			ii.setItemTempId(di.getTable_id());
			ii.setItemNum(di.number);
			re.getItemList().add(ii);
		}
		m_syncItemId.clear();
		con.send(re);
	}
	
	public boolean decItemByTempId(PlayerConnection con, int tempid, long num, int event) {
		return decItemByTempId( con,  tempid,  num,  event, true);
	}
	
	public boolean decItemByTempId(PlayerConnection con, int tempid, long num, int event, boolean sync) {
		long item_id = rawDecItem(con.getPlayerId(), tempid, num, event, con.getPlayer().getLvl(), con.getPlayer().getVipLvl());
		if (item_id == -1)
			return false;
		
		if (sync)
			syncItem(con, item_id, event);
		return true;
	}
	
	public long setItem(PlayerConnection con, int tempid, long num, int event) {
		long item_id = rawSetItem(con.getPlayer().getDB(), con.getPlayer().getPlayerId(), tempid, num, event,con.getPlayer().getLvl(), con.getPlayer().getVipLvl());
		syncItem(con, item_id, event);
		return item_id;
	}
	
	public long addItem(PlayerConnection con, int tempid, long num, int event) {
		return addItem( con,  tempid,  num,  event,  true);
	}
	
	public long addItem(PlayerConnection con, int tempid, long num, int event, boolean issync) {
		long player_id = con.getPlayer().getPlayerId();
		int lvl = con.getPlayer().getLvl();
		int vlvl = con.getPlayer().getVipLvl();
		
		long item_id = rawAddItem(con.getPlayer().getDB(), player_id, 
				lvl, vlvl, tempid, num, event);
		
		// 虚拟物品，需要额外的处理
		if (tempid < 100) {
			if (tempid == Consts.getLVL_ID()) {
				// 直接设置了等级,exp怎么办别
			}
			else if (tempid == Consts.getVIP_EXP_ID()) {
				long cur_vip_exp = getItemCountByTempId(Consts.getVIP_EXP_ID());
				int cur_viplvl = (int)getItemCountByTempId(Consts.getVIP_LVL_ID());
				boolean is_change = false;
				while (true) {
					if (TableManager.GetInstance().getMaxGVipLevel() > cur_viplvl) {
						MT_Data_GVip vip = TableManager.GetInstance().TableGVip().GetElement(cur_viplvl + 1);
						if (cur_vip_exp < vip.exp())
							break;
						
						addItem(con, Consts.getVIP_LVL_ID(), 1, Consts.getItemEventVipLevelUp());
						cur_viplvl++;
						is_change = true;
					}
				}
				
				if (is_change) {
					cur_viplvl = (int)getItemCountByTempId(Consts.getVIP_LVL_ID());
					ItemHandler.newshowToast(con, 10009, cur_viplvl);
					ChatHandler.newbanner(13, con.getPlayer().getName(), con.getPlayerId(), cur_viplvl);
				}
			}
		}
		
		if (issync)
			syncItem(con, item_id, event);
		return item_id;
	}
	
	private long rawDecItem(long player_id, int tempid, long num, int itemevent, int lvl, int vlvl) {
		DatabaseItem ditem = getItemDataByTempId(tempid);
		if (ditem == null) {
			logger.error("rawDecItem, player not has item, tempid={}, will decnum={}", tempid, num);
			return -1;
		}
		
		if (ditem.number < num) {
			logger.error("rawDecItem, player item not enought, will dec={}, has={}", num, ditem.number);
			return -1;
		}
		
		long old_num = ditem.number;
		ditem.number -= num;
		LogService.logItem(player_id, 0, 2, tempid, num, old_num, ditem.number, itemevent, "CN", lvl, vlvl);
		afterSet();
		return ditem.item_id;
	}
	
	private long rawSetItem(DatabaseUtil db, long player_id, int tempid, long num, int event, int lvl, int vlvl) {
		DatabaseItem ditem = getItemDataByTempId(tempid);
		if (ditem == null) {
			ditem = insertNewItem(db, player_id, tempid, num);
			
			if (ditem == null)
				return -1L;
			else {
				LogService.logItem(player_id, 0, 2, tempid, num, 0, num, event, "CN", lvl, vlvl);
				return ditem.item_id;
			}
		}
		
		long old_num = ditem.number;
		ditem.number = num;
		LogService.logItem(player_id, 0, 2, tempid, num, old_num, ditem.number, event, "CN", lvl, vlvl);
		afterSet();
		return ditem.item_id;
	}
	
	// 注意，里面只能有逻辑操作
	private long rawAddItem(DatabaseUtil db, long player_id, int lvl, int vlvl, int tempid, long num, int event) {
		// 数据库里面没有这个物品，就强势插入
		DatabaseItem ditem = getItemDataByTempId(tempid);
		if (ditem == null) {
			ditem = insertNewItem(db, player_id, tempid, num);
			
			if (ditem == null)
				return -1L;
			else {
				LogService.logItem(player_id, 0, 0, tempid, num, 0, num, event, "CN", lvl, vlvl);
				return ditem.item_id;
			}
		}
		
		MT_Data_GItem gitem = TableManager.GetInstance().TableGItem().GetElement(tempid);
		if (gitem == null || gitem.svr_isOverlay() == 1) {
			// 是可以叠加的物品
			long oldnum = ditem.number;
			ditem.number += num;
			LogService.logItem(player_id, 0, 0, tempid, num, oldnum, ditem.number, event, "CN", lvl, vlvl);
			afterSet();
			return ditem.item_id;
		} else {
			// 不可叠加的物品
			ditem = insertNewItem(db, player_id, tempid, num);
			if (ditem == null)
				return -1L;
			else {
				LogService.logItem(player_id, 0, 0, tempid, num, 0, num, event, "CN", lvl, vlvl);
				return ditem.item_id;
			}
		}
	}

	private DatabaseItem insertNewItem(DatabaseUtil db, long player_id, int tempid, long num) {
		DatabaseItem ditem = new DatabaseItem();
		ditem.level=0;
		ditem.number = (long) num;
		ditem.player_id = player_id;
		ditem.table_id = tempid;
		DatabaseInsertUpdateResult res = db.Insert(ditem);
		if (res.succeed) {
			ditem.item_id = res.identity;
			ditem.setDatabaseSimple(db.getM_Simple());
			m_Items.put(ditem.item_id, ditem);
			
			if (isVirtualItem(tempid)) {
				m_vItems.put(tempid, ditem);
			}
		} else {
			// 插入出错了？ 难以想象啊
			logger.error("additem db error, player_id={}, tempid={}, num={}", player_id, tempid, num);
			return null;
		}
		return ditem;
	}
	
	public void onNewWeek(boolean isLogin) {
		int[] clear_temp_id = {75 /*金砖宝箱*/};
		for (int item_temp_id : clear_temp_id) {
			DatabaseItem item = m_vItems.get(item_temp_id);
			if (item != null) {
				item.number = 0L;
			}
		}
	}
	
	public void onNewDay(long now, boolean isLogin, PlayerData pdata) {
		int[] clear_temp_id = {Consts.getRelief_ID(), Consts.getDay_Recharge_Value(),
				Consts.getIsLevelRankRewardGet(), Consts.getIsMoneyRankRewardGet(), Consts.getIsLikeRankRewardGet(),
				70/*每日礼包领取标志*/, 73/*激励福袋领取标志*/, 74/*玫瑰盒子*/, Consts.getDay_Earn_Count()};
		DatabaseItem ttt = m_vItems.get(Consts.getDay_Earn_Count());
		if (ttt != null) {
			pdata.playerRecordDayInfo(ttt.number);
		}
		for (int item_temp_id : clear_temp_id) {
			DatabaseItem item = m_vItems.get(item_temp_id);
			if (item != null) {
				item.number = 0L;
			}
		}
	
		int viplevel = (int)getItemCountByTempId(Consts.getVIP_LVL_ID());
		MT_Data_GVip vipcc = TableManager.GetInstance().TableGVip()
				.GetElement(viplevel);
		if (vipcc != null) {
			DatabaseItem pitem = m_vItems.get(Consts.getPIG_COIN_ID());
			pitem.number += vipcc.goldPig_genCoin();
			if (pitem.number > vipcc.goldPig_Max())
				pitem.number = vipcc.goldPig_Max();
		}
		
		DatabaseItem item = m_vItems.get(Consts.getCOIN_ID());
		item.getDatabaseSimple().Execute("delete from item where player_id=? and table_id>10000", item.player_id);	
		afterSet();
	}
}
