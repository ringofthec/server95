package com.commons.network.websock.handler;

import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.GDL_C2G_DropItem;
import gameserver.jsonprotocol.GDL_C2G_GetGiveGiftRecord;
import gameserver.jsonprotocol.GDL_C2G_GetItemInfo;
import gameserver.jsonprotocol.GDL_C2G_GiveGift;
import gameserver.jsonprotocol.GDL_C2G_OpenEgg;
import gameserver.jsonprotocol.GDL_C2G_SetGiftShow;
import gameserver.jsonprotocol.GDL_C2G_UseItem;
import gameserver.jsonprotocol.GDL_G2C_BannerNotice;
import gameserver.jsonprotocol.GDL_G2C_GetGiveGiftRecordRe;
import gameserver.jsonprotocol.GDL_G2C_GetItemInfoRe;
import gameserver.jsonprotocol.GDL_G2C_GiveGiftRe;
import gameserver.jsonprotocol.GDL_G2C_ItemAddSync;
import gameserver.jsonprotocol.GDL_G2C_OpenEggSync;
import gameserver.jsonprotocol.GDL_G2C_ShowToast;
import gameserver.logging.LogService;

import java.util.List;

import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_GEgg;
import table.MT_Data_GItem;
import table.base.TableManager;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConManager;
import com.commons.network.websock.PlayerConnection;
import com.commons.util.RandomUtil;
import com.commons.util.TimeUtil;
import com.gdl.data.PlayerData;

import database.gdl.gameserver.DatabaseGive_gift_record;

public class ItemHandler {
	private final static ItemHandler instance = new ItemHandler();
	public static ItemHandler getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(ItemHandler.class);
	
	public static void showToast(PlayerConnection con, String msg) {
		showToast(con, msg, false);
	}
	
	public static void showToast(PlayerConnection con, String msg, boolean isDir) {
		GDL_G2C_ShowToast toast = new GDL_G2C_ShowToast();
		toast.setMsg(msg);
		
		if (!isDir)
			con.send(toast);
		else
			con.directSendPack(toast);
	}
	
	public static void newshowToastDir(PlayerConnection con, int tid, Object... args) {
		GDL_G2C_BannerNotice bn = new GDL_G2C_BannerNotice();
		bn.setNid(tid);
		
		for (Object o : args)
			bn.getArgs().add(o.toString());
		
		con.directSendPack(bn);
	}
	
	public static void newshowToast(PlayerConnection con, int tid, Object... args) {
		GDL_G2C_BannerNotice bn = new GDL_G2C_BannerNotice();
		bn.setNid(tid);
		
		for (Object o : args)
			bn.getArgs().add(o.toString());
		
		con.send(bn);
	}
	
	public static void showAddItem(PlayerConnection con, int item_temp_id, long num) {
		GDL_G2C_ItemAddSync sync = new GDL_G2C_ItemAddSync();
		sync.setItemTempId(item_temp_id);
		sync.setNum(num);
		con.send(sync);
	}
	
	public interface ItemAction {
		public boolean execute(PlayerConnection p, String[] obj);
	}
	
	public static class AddItem implements ItemAction {

		@Override
		public boolean execute(PlayerConnection p, String[] obj) {
			Integer[] all_param = new Integer[obj.length];
			
			for (int i = 0; i < obj.length; ++i) {
				all_param[i] = Integer.parseInt(obj[i]);
			}
			
			for (int i = 0; i < all_param.length / 2; ++i) {
				int item_temp_id = all_param[i * 2];
				int num = all_param[i * 2 + 1];
				
				p.getPlayer().getItemData().addItem(p, item_temp_id, num, Consts.getItemEventUseItem());
				
				showAddItem(p, item_temp_id, num);
			}
			
			return true;
		}
		
	}
	
	private FastMap<String , ItemAction> m_itemActions = new FastMap<String, ItemHandler.ItemAction>();
	
	public void init() {
		HandlerManager.getInstance().pushNornalHandler(ItemHandler.class, this, 
				"OnGetItemInfo", new GDL_C2G_GetItemInfo());
		HandlerManager.getInstance().pushNornalHandler(ItemHandler.class, this, 
				"OnGiveGift", new GDL_C2G_GiveGift());
		HandlerManager.getInstance().pushNornalHandler(ItemHandler.class, this, 
				"OnSetGiftShow", new GDL_C2G_SetGiftShow());
		HandlerManager.getInstance().pushNornalHandler(ItemHandler.class, this, 
				"OnDropItem", new GDL_C2G_DropItem());
		HandlerManager.getInstance().pushNornalHandler(ItemHandler.class, this, 
				"OnUseItem", new GDL_C2G_UseItem());
		HandlerManager.getInstance().pushNornalHandler(ItemHandler.class, this, 
				"OnGetGiveGiftRecord", new GDL_C2G_GetGiveGiftRecord());
		HandlerManager.getInstance().pushNornalHandler(ItemHandler.class, this, 
				"OnOpenEgg", new GDL_C2G_OpenEgg());
		
		Class[] allac = ItemHandler.class.getDeclaredClasses();
		for (Class c : allac) {
			if (c.isInterface())
				continue;
			
			try {
				m_itemActions.put(c.getSimpleName().toLowerCase(), (ItemAction)c.newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void OnOpenEgg(PlayerConnection con, GDL_C2G_OpenEgg message) {
		int egg_id = message.getEggid();
		PlayerData p = con.getPlayer();
		
		MT_Data_GEgg co = TableManager.GetInstance().TableGEgg().GetElement(egg_id);
		if (co == null)
			return ;
		
		if (!ShopHandler.testCost(con, co.costid(), 1))
			return ;
		
		if (p.getPlayerId() == 22803) {
			int[] low = {0, 1, 2, 5, 10, 20, 50};
			int[] top = {0, 10, 20, 30, 40, 80, 100};
			
			long real_add = RandomUtil.RangeRandom(low[egg_id], top[egg_id]);
			GDL_G2C_OpenEggSync ysn = new GDL_G2C_OpenEggSync();
			ysn.setEggid(egg_id);
			ysn.setNum(real_add);
			con.send(ysn);
		} else {
			double rel_num = 0;
			int cost_num = ShopHandler.getCostCoinNum(co.costid());
			cost_num = cost_num / 10000;// 转为人民币

			long back_price = p.getItemData().getItemCountByTempId(119);
			if (cost_num >= back_price) {
				rel_num = back_price + (cost_num - back_price) * RandomUtil.RangeRandom(0.03, 0.05);
			} else {
				rel_num = cost_num * RandomUtil.RangeRandom(0.75, 0.85);
			}

			if (p.getVipLvl() == 0 && back_price == 0) {
				int[] low = {0,  1, 2,  5, 10, 20,  50};
				int[] top = {0, 10, 20, 30, 40, 80, 100};
				rel_num = RandomUtil.RangeRandom(low[egg_id], top[egg_id]);
				long real_add = (int)rel_num;
				
				GDL_G2C_OpenEggSync ysn = new GDL_G2C_OpenEggSync();
				ysn.setEggid(egg_id);
				ysn.setNum(real_add);
				con.send(ysn);
			} else {
				long real_add = (int)rel_num;
				if (real_add > 0) {
					long old_back = p.getItemData().getItemCountByTempId(119);

					if (old_back >= real_add)
						p.getItemData().decItemByTempId(con, 119, real_add, 0);
					else
						p.getItemData().decItemByTempId(con, 119, old_back, 0);

					real_add = real_add * 100;
					p.getItemData().addItem(con, Consts.getBill_ID(), real_add, 1);
				}

				GDL_G2C_OpenEggSync ysn = new GDL_G2C_OpenEggSync();
				ysn.setEggid(egg_id);
				ysn.setNum(real_add);
				con.send(ysn);
			}
		}
	}
	
	public void OnUseItem(PlayerConnection con, GDL_C2G_UseItem message) {
		PlayerData p = con.getPlayer();
		if (p.getItemData().getItemCountByObjId(message.getItemObjId()) <= 0)
			return ;
		
		int temp_id = p.getItemData().getTempIdByItemId(message.getItemObjId());
		if (temp_id == -1)
			return ;
		
		MT_Data_GItem itemconfig = TableManager.GetInstance().TableGItem().GetElement(temp_id);
		if (itemconfig == null)
			return ;
		
		p.getItemData().decItemByTempId(con, temp_id, 1, Consts.getItemEventUseItem());
		String act = itemconfig.svr_useAction();
		String[] acts = act.split(";");
		
		for (String a : acts) {
			int idx = a.indexOf('(');
			int idx_2 = a.indexOf(')');
			
			String method_name = a.substring(0, idx).toLowerCase();
			ItemAction m = m_itemActions.get(method_name);
			m.execute(con, a.substring(idx + 1, idx_2).split(","));
		}
		
	}
	
	public void OnDropItem(PlayerConnection con, GDL_C2G_DropItem message) {
		PlayerData p = con.getPlayer();
		if (!p.getItemData().checkItemNumByItemId(message.getItemId(), 1))
			return ;
		
		int temp_id = p.getItemData().getTempIdByItemId(message.getItemId());
		long num = p.getItemData().getItemCountByObjId(message.getItemId());
		p.getItemData().decItemByTempId(con, temp_id, num, Consts.getItemEventDrop());
	}
	
	public void OnSetGiftShow(PlayerConnection con, GDL_C2G_SetGiftShow message) {
		PlayerData p = con.getPlayer();
		int table_id = message.getItemTempId();
		
		MT_Data_GItem itemconfig = TableManager.GetInstance().TableGItem().GetElement(table_id);
		if (itemconfig == null)
			return ;
		
		// 必须可以展示
		if (itemconfig.canUse() != 3)
			return ;
		
		if (p.getItemCountByTempId(table_id) < message.getNum())
			return ;
		
		// 同类型的礼品不能设置多次
		//if (!p.isGiftAlreadyShow(table_id))
		//	return ;
		
		p.getItemData().decItemByTempId(con, table_id, message.getNum(), Consts.getItemEventGiftShow());
		long exp_time = TimeUtil.GetDateTime() + itemconfig.svr_giftexpire() * 3600 * 1000;
		p.setConfigGift(con, table_id, message.getNum(), TimeUtil.GetDateString(exp_time));
	}
	
	public void OnGetItemInfo(PlayerConnection con, GDL_C2G_GetItemInfo message) {
		GDL_G2C_GetItemInfoRe re = con.getPlayer().packetAllItem();
		con.send(re);
	}
	
	public void OnGetGiveGiftRecord(PlayerConnection con, GDL_C2G_GetGiveGiftRecord msg) {
		PlayerData p = con.getPlayer();
		
		List<DatabaseGive_gift_record> list = p.getDB().Select(DatabaseGive_gift_record.class, 
				"player_id=? and type=? order by re_time desc limit 10", p.getPlayerId(), msg.getType());
		if (list == null || list.isEmpty())
			return ;
		
		GDL_G2C_GetGiveGiftRecordRe croed = new GDL_G2C_GetGiveGiftRecordRe();
		croed.setMode(0);
		croed.setType(msg.getType());
		
		for (DatabaseGive_gift_record i : list) {
			croed.getMsg().add(i.msg);
		}
		
		con.send(croed);
	}
	
	public void OnGiveGift(PlayerConnection con, GDL_C2G_GiveGift message) {
		if (message.getPlayerId() == con.getPlayerId())
			return ;
		
		PlayerData p = con.getPlayer();
		if (!p.getItemData().checkItemNumByItemId(message.getItemId(), 1))
			return ;
		
		GDL_G2C_GiveGiftRe re = new GDL_G2C_GiveGiftRe();
		PlayerConnection other = PlayerConManager.getInstance().getCon(message.getPlayerId());
		if (other == null) {
			re.setRetCode(1);
			con.send(re);
			return ;
		}
		
		int temp_id = p.getItemData().getTempIdByItemId(message.getItemId());
		MT_Data_GItem itemconfig = TableManager.GetInstance().TableGItem().GetElement(temp_id);
		if (itemconfig == null)
			return ;
		
		if (itemconfig.canUse() != 2)
			return ;
		
		int gift_target_id = temp_id + 200;
		MT_Data_GItem target_itemconfig = TableManager.GetInstance().TableGItem().GetElement(gift_target_id);
		if (target_itemconfig == null)
			return ;
		
		p.getItemData().decItemByTempId(con, temp_id, 1, Consts.getItemEventGiveGift());
		other.getPlayer().getItemData().addItem(other, gift_target_id, 1, Consts.getItemEventGiveGift());
		other.getPlayer().getItemData().addItem(other, Consts.getLIKEID(), 
				TableManager.GetInstance().getGiftFan(temp_id), Consts.getItemEventGiveGift());
		
		//other.getPlayer().setGiveGift(other, gift_target_id);
		re.setRetCode(0);
		con.send(re);
		
		String cur_time = TimeUtil.GetDateString().substring(5);
		saveMsg(other, 1, p.getName() + " 在  " + cur_time + " 赠送给您了 " + target_itemconfig.name());
		
		ItemHandler.newshowToast(con, 10011);
		ItemHandler.newshowToast(other, 10010, p.getName(), target_itemconfig.name());
		
		if (gift_target_id >= 403 && gift_target_id <= 407) {
			int diff_t = gift_target_id - 403;
			ChatHandler.newbanner(66 + diff_t, con.getPlayer().getName(), con.getPlayerId(), other.getPlayer().getName(), other.getPlayerId());
		} else if (gift_target_id >= 408 && gift_target_id <= 409) {
			int diff_t = gift_target_id - 408;
			ChatHandler.newbanner(71 + diff_t, con.getPlayer().getName(), con.getPlayerId());
		}
	}

	public void saveMsg(PlayerConnection con, int type, String msg) {
		DatabaseGive_gift_record dbrec = new DatabaseGive_gift_record();
		dbrec.player_id = con.getPlayerId();
		dbrec.re_time = TimeUtil.GetDateTime();
		dbrec.type = type;
		dbrec.msg = msg;
		con.getPlayer().getDB().Insert(dbrec);
		
		GDL_G2C_GetGiveGiftRecordRe croed = new GDL_G2C_GetGiveGiftRecordRe();
		croed.setMode(1);
		croed.getMsg().add(dbrec.msg);
		con.send(croed);
	}
}
