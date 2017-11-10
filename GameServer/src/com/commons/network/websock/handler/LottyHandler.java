package com.commons.network.websock.handler;

import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.GDL_C2G_CashPrize;
import gameserver.jsonprotocol.GDL_C2G_GetLottyAndCashRecord;
import gameserver.jsonprotocol.GDL_C2G_GetLottyBegin;
import gameserver.jsonprotocol.GDL_C2G_GetLottyEnd;
import gameserver.jsonprotocol.GDL_G2C_CashPrizeRe;
import gameserver.jsonprotocol.GDL_G2C_GetLottyRe;
import gameserver.utils.DbMgr;
import gameserver.utils.Util;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int3;
import table.MT_Data_GCash;
import table.MT_Data_GItem;
import table.base.TableManager;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConnection;
import com.commons.util.DatabaseInsertUpdateResult;
import com.commons.util.TimeUtil;
import com.gdl.data.PlayerData;
import com.gdl.manager.LottyManager;

import databaseshare.DatabaseCashs;

public class LottyHandler {
	private final static LottyHandler instance = new LottyHandler();
	public static LottyHandler getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(LottyHandler.class);
	ArrayList<String> strs = new ArrayList<String>();
	
	public void init() {
		HandlerManager.getInstance().pushNornalHandler(LottyHandler.class, this, 
				"OnGetLottyBegin", new GDL_C2G_GetLottyBegin());
		HandlerManager.getInstance().pushNornalHandler(LottyHandler.class, this, 
				"OnGetLottyEnd", new GDL_C2G_GetLottyEnd());
		HandlerManager.getInstance().pushNornalHandler(LottyHandler.class, this, 
				"OnGetLottyAndCashRecord", new GDL_C2G_GetLottyAndCashRecord());
		HandlerManager.getInstance().pushNornalHandler(LottyHandler.class, this, 
				"OnCashPrize", new GDL_C2G_CashPrize());
		initName();
	}
	
	public void initName() {
		String[] uu = {"iPhone6S","iPadAir2","APPwatch","索尼耳機","皮爾卡丹真皮錢包","暴龍眼鏡","施華洛世奇水晶吊墜","zippo打火機","瑞士軍刀","泰迪抱枕24寸","100元話費","50元話費","金士頓U盤16G","小米充電寶","飛科剃鬚刀","泰迪熊公仔"};
		strs.clear();
		strs.add("");
		
		for (String dfdf : uu) {
			strs.add(dfdf);
		}
	}
	
	public void OnCashPrize(PlayerConnection con, GDL_C2G_CashPrize message) {
		PlayerData p = con.getPlayer();
		GDL_G2C_CashPrizeRe re = new GDL_G2C_CashPrizeRe();
		re.setRetCode(1);
		
		while (true) {
			MT_Data_GCash gc = TableManager.GetInstance().TableGCash().GetElement(message.getId());
			if (gc == null) {
				break;
			}
			
			/*
			if (p.getLvl() < gc.lvl())
				break;
			*/
			
			if (p.getVipLvl() < gc.viplvl())
				break;
			
			if (!ShopHandler.testCost(con, gc.costId(), Consts.getItemEventCash(), true, true))
				return ;
			
			DatabaseCashs ca = new DatabaseCashs();
			ca.player_id = con.getPlayerId();
			ca.rid = message.getId();
			ca.name = message.getName();
			ca.address = message.getAddress();
			ca.phone = message.getPhone();
			ca.weixin = message.getWinxin();
			ca.status = 0;
			ca.ctime = TimeUtil.GetDateTime();
			DatabaseInsertUpdateResult dbre = DbMgr.getInstance().getShareDB().Insert(ca);
			
			if (!dbre.succeed)
				break;
			
			re.setRetCode(0);
			break;
		}
		
		MT_Data_GCash gc = TableManager.GetInstance().TableGCash().GetElement(message.getId());
		if (gc != null)
			ChatHandler.newbanner(65, con.getPlayer().getName(), con.getPlayerId(), strs.get(message.getId()));
		ItemHandler.newshowToast(con, 10005);
		con.send(re);
	}
	
	public void OnGetLottyAndCashRecord(PlayerConnection con, GDL_C2G_GetLottyAndCashRecord message) {
		LottyManager.getInstance().sendLottyMsg(con);
	}
	
	public void OnGetLottyEnd(PlayerConnection con, GDL_C2G_GetLottyEnd message) {
		PlayerData p = con.getPlayer();
		int ci = p.getLottyRewardCoinIdx();
		int gi = p.getLottyRewardGoldIdx();
		if (ci == 0 && gi == 0)
			return ;
		
		Int3 reward = null;
		if (ci != 0) {
			reward = TableManager.GetInstance().TableLotty().GetElement(ci).coinitem();
			p.setLottyRewardCoinIdx(0);
		} else {
			reward = TableManager.GetInstance().TableLotty().GetElement(gi).golditem();
			p.setLottyRewardGoldIdx(0);
		}
		
		p.getItemData().addItem(con, reward.field1(), reward.field2(), Consts.getItemEventLotty());
		ItemHandler.showAddItem(con, reward.field1(), reward.field2());
		LottyManager.getInstance().putNewLotty(reward, p.getName());
	}
	
	public void OnGetLottyBegin(PlayerConnection con, GDL_C2G_GetLottyBegin message) {
		PlayerData p = con.getPlayer();
		
		ArrayList<Int3> ran = new ArrayList<Int3>();
		if (message.getType() == 0) {
			if (!ShopHandler.testCost(con, 10, Consts.getItemEventLotty()))
				return ;
			
			if (p.getItemCountByTempId(Consts.getVIP_EXP_ID()) == 0) {
				for (int i = 1; i <= 18; ++i) {
					ran.add( TableManager.GetInstance().TableLotty().GetElement(i).coinitem1() );
				}
			} else {
				for (int i = 1; i <= 18; ++i) {
					ran.add( TableManager.GetInstance().TableLotty().GetElement(i).coinitem() );
				}
			}
		} else {
			if (!ShopHandler.testCost(con, 11, Consts.getItemEventLotty()))
				return ;
			
			for (int i = 1; i <= 18; ++i) {
				ran.add( TableManager.GetInstance().TableLotty().GetElement(i).golditem() );
			}
		}
		
		int idx = Util.rateInt3(ran, 10000);
		if (idx == -1)
			return ;
		
		idx = idx + 1;
		if (message.getType() == 0) {
			p.setLottyRewardCoinIdx(idx);
		}
		else {
			p.setLottyRewardGoldIdx(idx);
		}
		
		GDL_G2C_GetLottyRe re = new GDL_G2C_GetLottyRe();
		re.setId(idx);
		re.setType(message.getType());
		con.send(re);
	}
}
