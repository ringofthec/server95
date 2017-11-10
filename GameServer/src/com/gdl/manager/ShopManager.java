package com.gdl.manager;

import gameserver.jsonprotocol.GDL_G2C_FlushGiftCountRe;
import gameserver.jsonprotocol.ShopGiftInfo;
import gameserver.utils.DbMgr;

import java.util.List;

import javolution.util.FastMap;
import table.MT_Data_GGiftShop;
import table.base.TableManager;
import databaseshare.DatabaseShop;

public class ShopManager {
	private static ShopManager m_int = new ShopManager();
	private ShopManager() {}
	public static ShopManager getInstance() {return m_int;}
	
	FastMap<Integer, DatabaseShop> m_itemInfos = new FastMap<Integer,DatabaseShop>();
	
	public void refresh() {
		List<DatabaseShop> list = DbMgr.getInstance().getShareDB().Select(DatabaseShop.class, "");
		for (DatabaseShop d : list)
			m_itemInfos.put(d.id, d);
		
		for (MT_Data_GGiftShop it : TableManager.GetInstance().TableGGiftShop().Datas().values()) {
			if (!m_itemInfos.containsKey(it.id())) {
				DatabaseShop s = new DatabaseShop();
				s.id = it.id();
				s.count = it.svr_sum();
				s.total = it.svr_sum();
				DbMgr.getInstance().getShareDB().Insert(s);
			}
		}
	}
	
	public void save() {
		for (DatabaseShop d : m_itemInfos.values())
			d.save();
	}
	
	public GDL_G2C_FlushGiftCountRe genFlushGiftCount() {
		GDL_G2C_FlushGiftCountRe re = new GDL_G2C_FlushGiftCountRe();
		
		for (DatabaseShop d : m_itemInfos.values()) {
			ShopGiftInfo sgi = new ShopGiftInfo();
			sgi.setGiftId(d.id);
			sgi.setCount(d.count);
			sgi.setTotal(d.total);
			
			re.getGift().add(sgi);
		}
		
		return re;
	}
	
	
	public boolean testAndCost(int id, boolean cost) {
		if (!m_itemInfos.containsKey(id))
			return false;
		
		DatabaseShop ss = m_itemInfos.get(id);
		if (ss.count > 0) {
			if (cost)
				ss.count -= 1;
			return true;
		}
		
		return false;
	}
}
