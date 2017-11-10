package com.gdl.manager;

import gameserver.jsonprotocol.CashRecord;
import gameserver.jsonprotocol.GDL_G2C_GetLottyAndCashRecordRe;
import gameserver.jsonprotocol.LottyRecord;
import gameserver.utils.DbMgr;
import gameserver.utils.UtilItem;

import java.util.List;

import javolution.util.FastList;
import table.Int3;

import com.commons.network.websock.PlayerConnection;
import com.commons.util.TimeUtil;

import databaseshare.DatabaseCashs;

public class LottyManager {
	private static LottyManager m_int = new LottyManager();
	private LottyManager() {}
	public static LottyManager getInstance() {return m_int;}
	
	public class Info {
		UtilItem reward;
		String name;
	}
	
	FastList<Info> m_lotty_infos = new FastList<Info>();
	FastList<Info> m_cash_infos = new FastList<Info>();
	
	public void putNewLotty(Int3 reward, String name) {
		Info i = new Info();
		i.reward = new UtilItem(reward.field1(), reward.field2());
		i.name = name;
		
		synchronized (LottyManager.class) {
			m_lotty_infos.addFirst(i);
			if (m_lotty_infos.size() > 10)
				m_lotty_infos.removeLast();
		}
	}
	
	public void sendLottyMsg1(PlayerConnection con) {
		GDL_G2C_GetLottyAndCashRecordRe re = new GDL_G2C_GetLottyAndCashRecordRe();
		
		synchronized (LottyManager.class) {
			for (Info i : m_lotty_infos) {
				LottyRecord lr = new LottyRecord();
				lr.setName(con.getPlayer().getName());
				lr.setItemId(i.reward.GetItemId());
				lr.setNum(i.reward.GetCount());
				
				re.getLottys().add(lr);
			}
		}
		
		con.send(re);
	}
	
	public void sendLottyMsg(PlayerConnection con) {
		GDL_G2C_GetLottyAndCashRecordRe re = new GDL_G2C_GetLottyAndCashRecordRe();
		
		List<DatabaseCashs> lll = DbMgr.getInstance().getShareDB().Select(DatabaseCashs.class, 
				"player_id=?", con.getPlayerId());
		for (DatabaseCashs ca : lll) {
			CashRecord cr = new CashRecord();
			
			cr.setItemId(ca.getRid());
			cr.setCashtime(TimeUtil.GetDateString(ca.ctime));
			cr.setStatus(ca.status);
			
			re.getCashs().add(cr);
		}
		
		con.send(re);
	}
}
