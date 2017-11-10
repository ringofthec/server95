package com.commons.network.websock.handler;

import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.GDL_C2G_BuyGiftShop;
import gameserver.jsonprotocol.GDL_C2G_BuyLevelShop;
import gameserver.jsonprotocol.GDL_C2G_FlushGiftCount;
import gameserver.jsonprotocol.GDL_C2G_GetLevelShopReward;
import gameserver.jsonprotocol.GDL_G2C_FlushGiftCountRe;
import gameserver.jsonprotocol.GDL_G2C_GetLevelShopRewardRe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.MT_Data_GCost;
import table.MT_Data_GExp;
import table.MT_Data_GGiftShop;
import table.MT_Data_GLevelShop;
import table.base.TableManager;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConnection;
import com.gdl.data.ItemData;
import com.gdl.data.PlayerData;
import com.gdl.manager.ShopManager;

public class ShopHandler {
	private final static ShopHandler instance = new ShopHandler();
	public static ShopHandler getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(ShopHandler.class);
	
	public void init() {
		HandlerManager.getInstance().pushNornalHandler(ShopHandler.class, this, 
				"OnFlushGiftCount", new GDL_C2G_FlushGiftCount());
		HandlerManager.getInstance().pushNornalHandler(ShopHandler.class, this, 
				"OnBuyGiftShop", new GDL_C2G_BuyGiftShop());
		HandlerManager.getInstance().pushNornalHandler(ShopHandler.class, this, 
				"OnBuyLevelShop", new GDL_C2G_BuyLevelShop());
		HandlerManager.getInstance().pushNornalHandler(ShopHandler.class, this, 
				"OnGetLevelShopReward", new GDL_C2G_GetLevelShopReward());
	}
	
	public void OnFlushGiftCount(PlayerConnection con, GDL_C2G_FlushGiftCount message) {
		// 发送商店存货信息
		GDL_G2C_FlushGiftCountRe re = ShopManager.getInstance().genFlushGiftCount();
		con.send(re);
	}
	
	public static boolean testItemOrCost(PlayerConnection con, int item_temp_id, int num, 
			int costId, int itemevent) {
		return testItemOrCost(con, item_temp_id, num, costId, itemevent, true);
	}
	
	public static boolean testItemOrCost(PlayerConnection con, int item_temp_id, int num, 
			int costId, int itemevent, boolean dec) {
		ItemData idata = con.getPlayer().getItemData();
		
		long cur_num = idata.getItemCountByTempId(item_temp_id);
		if (cur_num >= num) {
			if (dec)
				idata.decItemByTempId(con, item_temp_id, num, itemevent);
			return true;
		}
		
		long diff = num - cur_num;
		MT_Data_GCost co = TableManager.GetInstance().TableGCost().GetElement(costId);
		if (co == null)
			return false;
		
		int money_id = 0;
		if (co.costtype() == 1) {
			money_id = Consts.getCOIN_ID();
		} else if (co.costtype() == 2){
			money_id = Consts.getGOLD_ID();
		} else if (co.costtype() == 3) {
			money_id = Consts.getBill_ID();
		} else {
			return false;
		}
		
		long total_money = co.costnum() * diff;
		if (!idata.checkItemNumByTempId(money_id, total_money))
			return false;
		
		if (dec) {
			idata.decItemByTempId(con, item_temp_id, cur_num, itemevent);
			idata.decItemByTempId(con, money_id, total_money, itemevent);
		}
		
		return true;
	}
	
	public static boolean testCost(PlayerConnection con, int costId, int itemevent, boolean sync) {
		return testCost(con, costId, itemevent, true, sync);
	}
	
	public static boolean testCost(PlayerConnection con, int costId, int itemevent) {
		return testCost(con, costId, itemevent, true, true);
	}
	
	public static int getCostCoinNum(int costId) {
		MT_Data_GCost co = TableManager.GetInstance().TableGCost().GetElement(costId);
		if (co == null)
			return -1;
		
		return co.costnum();
	}
	
	public static boolean testCost(PlayerConnection con, int costId, int itemevent, boolean dec, boolean sync) {
		PlayerData p = con.getPlayer();
		MT_Data_GCost co = TableManager.GetInstance().TableGCost().GetElement(costId);
		if (co == null)
			return false;
		
		int money_id = 0;
	
		if (co.costtype() == 1) {
			money_id = Consts.getCOIN_ID();
		} else if (co.costtype() == 2){
			money_id = Consts.getGOLD_ID();
		} else if (co.costtype() == 10) {
			money_id = Consts.getBill_ID();
		} else {
			return false;
		}
		
		if (p.getItemData().getItemCountByTempId(money_id) < co.costnum())
			return false;
		
		if (!dec)
			return true;
		
		return p.getItemData().decItemByTempId(con, money_id, co.costnum(), itemevent, sync);
	}
	
	public void OnBuyGiftShop(PlayerConnection con, GDL_C2G_BuyGiftShop message) {
		int gift_id = message.getGiftId();
		MT_Data_GGiftShop ggs = TableManager.GetInstance().TableGGiftShop().GetElement(gift_id);
		if (ggs == null)
			return ;
		
		synchronized (ShopManager.class) {
			// 检查商店存货
			if (!ShopManager.getInstance().testAndCost(gift_id, false))
				return ;
			
			// 检查并扣除玩家对应货币
			if (!testCost(con, ggs.costId(), Consts.getItemEventGiftShop()))
				return ;
			
			// 扣除商店存货
			ShopManager.getInstance().testAndCost(gift_id, true);
			
			con.getPlayer().getItemData().addItem(con, ggs.itemTempId(), 1, Consts.getItemEventGiftShop());
			ItemHandler.showAddItem(con, ggs.itemTempId(), 1);
		}
	}
	
	public void OnBuyLevelShop(PlayerConnection con, GDL_C2G_BuyLevelShop message) {
		// 购买等级
		PlayerData p = con.getPlayer();
		ItemData item = p.getItemData();
		int lvl = message.getLevel();
		if (p.getLvl() == TableManager.GetInstance().getMaxGLevel() || lvl != p.getLvl() + 1)
			return ;
		
		// 经验还没达到
		MT_Data_GExp exp = TableManager.GetInstance().TableGExp().GetElement(lvl);
		long cur_exp = item.getItemCountByTempId(Consts.getEXP_ID());
		if (cur_exp < exp.exp())
			return ;
		
		MT_Data_GLevelShop conf = TableManager.GetInstance().TableGLevelShop().GetElement(lvl);
		if (conf == null)
			return ;
		
		if (!testCost(con, conf.costid(), Consts.getItemEventLevelShop()))
			return ;
		
		// 提升一个等级
		item.addItem(con, Consts.getLVL_ID(), 1, Consts.getItemEventLevelUp());
		int level = con.getPlayer().getLvl();
		ItemHandler.newshowToast(con, 10008, level);
		
		conf = TableManager.GetInstance().TableGLevelShop().GetElement(level);
		ChatHandler.newbanner(14, con.getPlayer().getName(), con.getPlayerId(), conf.title());
	}
	
	public void OnGetLevelShopReward(PlayerConnection con, GDL_C2G_GetLevelShopReward message) {
		PlayerData p = con.getPlayer();
		if (message.getMode() == 0) {
			// 获取等级奖励领取情况
			GDL_G2C_GetLevelShopRewardRe re = p.getLevelRewardInfo();
			con.send(re);
		} else {
			// 获取等级奖励
			int lvl = message.getLevel();
			if (lvl > p.getLvl())
				return ;
			
			if (p.isLevelRewardGot(lvl))
				return ;
			
			MT_Data_GLevelShop conf = TableManager.GetInstance().TableGLevelShop().GetElement(lvl);
			if (conf == null)
				return ;
			
			p.setLevelRewardGot(lvl);
			for (Int2 item : conf.rewards()) {
				p.getItemData().addItem(con, item.field1(), item.field2(), Consts.getItemEventLevelShop());
				ItemHandler.showAddItem(con, item.field1(), item.field2());
			}
		}
	}
}
