package com.commons.network.websock.handler;

import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.GDL_C2G_GetBonus;
import gameserver.jsonprotocol.GDL_C2G_GetRateReward;
import gameserver.jsonprotocol.GDL_C2G_GetRelief;
import gameserver.jsonprotocol.GDL_C2G_SendBonus;
import gameserver.jsonprotocol.GDL_G2C_Bonus;
import gameserver.jsonprotocol.GDL_G2C_GetBonusRe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConManager;
import com.commons.network.websock.PlayerConnection;
import com.gdl.data.PlayerData;
import com.gdl.manager.BounsManager;

public class FreeMoneyHandler {
	private final static FreeMoneyHandler instance = new FreeMoneyHandler();
	public static FreeMoneyHandler getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(FreeMoneyHandler.class);
	
	public void init() {
		HandlerManager.getInstance().pushNornalHandler(FreeMoneyHandler.class, this, 
				"OnGetRelief", new GDL_C2G_GetRelief());
		HandlerManager.getInstance().pushNornalHandler(FreeMoneyHandler.class, this, 
				"OnGetRateReward", new GDL_C2G_GetRateReward());
		HandlerManager.getInstance().pushNornalHandler(FreeMoneyHandler.class, this, 
				"OnSendBonus", new GDL_C2G_SendBonus());
		HandlerManager.getInstance().pushNornalHandler(FreeMoneyHandler.class, this, 
				"OnGetBonus", new GDL_C2G_GetBonus());
	}
	
	public void OnGetRelief(PlayerConnection con, GDL_C2G_GetRelief message) {
		PlayerData p = con.getPlayer();
		long cur_coin = p.getItemCountByTempId(Consts.getCOIN_ID());
		if (cur_coin >= 1000)
			return ;
		
		long count = p.getItemCountByTempId(Consts.getRelief_ID());
		if (count >= 3)
			return ;
		
		p.getItemData().addItem(con, Consts.getCOIN_ID(), 5000, Consts.getItemEventRelief());
		p.getItemData().addItem(con, Consts.getRelief_ID(), 1, Consts.getItemEventRelief());
		ItemHandler.showAddItem(con, Consts.getCOIN_ID(), 5000);
	}
	
	public void OnGetRateReward(PlayerConnection con, GDL_C2G_GetRateReward message) {
		PlayerData p = con.getPlayer();
		long count = p.getItemCountByTempId(Consts.getRate_Star_ID());
		if (count >= 1)
			return ;
		
		p.getItemData().addItem(con, Consts.getCOIN_ID(), 10000, Consts.getItemEventRate());
		p.getItemData().addItem(con, Consts.getRate_Star_ID(), 1, Consts.getItemEventRate());
		ItemHandler.showAddItem(con, Consts.getCOIN_ID(), 10000);
	}
	
	public void OnSendBonus(PlayerConnection con, GDL_C2G_SendBonus message) {
		PlayerData p = con.getPlayer();
		long count = p.getItemCountByTempId(Consts.getCOIN_ID());
		long total_money = message.getMoney() * 10000;
		if (count < total_money)
			return ;
		
		// 扣除金币
		p.getItemData().decItemByTempId(con, Consts.getCOIN_ID(), total_money, Consts.getItemEventBouns());
		
		int bouns_id = BounsManager.getInstance().newBouns(total_money, message.getCount(), p);
		GDL_G2C_Bonus msg = new GDL_G2C_Bonus();
		msg.setId(bouns_id);
		msg.setSay(message.getSay());
		PlayerConManager.getInstance().broadcastMsgWithOut(msg, p.getPlayerId());
	}
	
	public void OnGetBonus(PlayerConnection con, GDL_C2G_GetBonus message) {
		PlayerData p = con.getPlayer();
		GDL_G2C_GetBonusRe re = new GDL_G2C_GetBonusRe();
		if (!BounsManager.getInstance().testAndRecord(p.getPlayerId(), message.getId())) {
			re.setRetCode(1);
			con.send(re);
			return ;
		}
		
		int money = BounsManager.getInstance().getMoney(message.getId());
		p.getItemData().addItem(con, Consts.getCOIN_ID(), money, Consts.getItemEventBouns());
		
		BounsManager.BounsInfo info = BounsManager.getInstance().getInfo(message.getId());
		ItemHandler.getInstance().saveMsg(con, 2, "抢到 " + info.name + " 发的 " + money + " 金币");
		
		re.setRetCode(0);
		re.setMoney(money);
		con.send(re);
	}
}
