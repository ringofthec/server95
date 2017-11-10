package com.commons.network.websock.handler;

import gameserver.MethodStatitic;
import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.GDL_C2G_BeginSlots;
import gameserver.jsonprotocol.GDL_C2G_EndSlots;
import gameserver.jsonprotocol.GDL_C2G_Guess;
import gameserver.jsonprotocol.GDL_C2G_JoinGoldSlotActive;
import gameserver.jsonprotocol.GDL_C2G_ReqFriutActiveInfo;
import gameserver.jsonprotocol.GDL_C2G_ReqSlotPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_GSlotsMoney;
import table.base.TableManager;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConnection;
import com.gdl.data.PlayerData;
import com.gdl.game.GameInstanceMrg;
import com.gdl.game.SlotActiveMrg;
import com.gdl.game.SlotGameMrg;
import com.gdl.manager.GamePoolManager;

public class SlotGameHandler {
	private final static SlotGameHandler instance = new SlotGameHandler();
	public static SlotGameHandler getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(SlotGameHandler.class);
	
	public void init() {
		HandlerManager.getInstance().pushNornalHandler(SlotGameHandler.class, this, 
				"OnBeginSlots", new GDL_C2G_BeginSlots());
		HandlerManager.getInstance().pushNornalHandler(SlotGameHandler.class, this, 
				"OnEndSlots", new GDL_C2G_EndSlots());
		HandlerManager.getInstance().pushNornalHandler(SlotGameHandler.class, this, 
				"OnGuess", new GDL_C2G_Guess());
		
		HandlerManager.getInstance().pushNornalHandler(SlotGameHandler.class, this, 
				"OnReqFriutActiveInfo", new GDL_C2G_ReqFriutActiveInfo());
		
		HandlerManager.getInstance().pushNornalHandler(SlotGameHandler.class, this, 
				"OnJoinGoldSlotActive", new GDL_C2G_JoinGoldSlotActive());
		
		HandlerManager.getInstance().pushNornalHandler(SlotGameHandler.class, this, 
				"OnReqSlotPool", new GDL_C2G_ReqSlotPool());
	}
	
	public void OnReqSlotPool(PlayerConnection con, GDL_C2G_ReqSlotPool message) {
		int gameid = message.getGameId();
		int levelid = message.getLevelId();
		if (!GameInstanceMrg.isRightGameId(gameid))
			return ;
		
		if (!GameInstanceMrg.isRightLevelId(gameid, levelid))
			return ;
		
		GamePoolManager.getInstance().reqPoolCoin(con, gameid, levelid);
	}
	
	public void OnJoinGoldSlotActive(PlayerConnection con, GDL_C2G_JoinGoldSlotActive message) {
		int uid = con.getPlayer().getGameUID();
		int levelid = GameInstanceMrg.getLevelId(uid);
		if (!GameInstanceMrg.isRightLevelId(Consts.getGoldSlot(), levelid))
			return ;
		
		SlotActiveMrg.getInstance().joinGoldSlotActive(con, levelid);
	}
	
	public void OnReqFriutActiveInfo(PlayerConnection con, GDL_C2G_ReqFriutActiveInfo message) {
		int gameid = message.getGameId();
		int levelid = message.getLevel();
		if (!GameInstanceMrg.isRightGameId(gameid))
			return ;
		
		if (!GameInstanceMrg.isRightLevelId(gameid, levelid))
			return ;
		
		SlotActiveMrg.getInstance().reqSoltActiveState(con, gameid, levelid);
	}
	
	public void OnGuess(PlayerConnection con, GDL_C2G_Guess message) {
		SlotGameMrg.getInstance().guess(con, message.getGuess());
	}
	
	public void OnEndSlots(PlayerConnection con, GDL_C2G_EndSlots message) {
		SlotGameMrg.getInstance().endSlot(con, false);
	}
	
	public void OnBeginSlots(PlayerConnection con, GDL_C2G_BeginSlots message) {
		PlayerData p = con.getPlayer();
		
		int lineNum = 30;//message.getLineNum();
		int MoneyPreLine = message.getMoneyPreLine();
		if (lineNum <= 0 || lineNum > 30)
			return ;
		
		if (MoneyPreLine < 0)
			return ;
		
		boolean is_free = p.getFreeSlot() > 0;
		int allMoney = lineNum * MoneyPreLine;
		if (allMoney <= 0)
			return ;
		
		if (is_free) {
			MoneyPreLine = p.getFreeMoney();
			allMoney = lineNum * MoneyPreLine;
		} else {
			if (p.getItemCountByTempId(Consts.getCOIN_ID()) < allMoney)
				return ;
		}
			
		boolean test = false;
		if (test) {
			for (int k = 1; k <= 3; k++) {
				MT_Data_GSlotsMoney ffff = TableManager.GetInstance().TableGSlotsMoney().GetElement(k);
				for (int money : ffff.moneys()) {
					allMoney = money * lineNum;
					for (int i = 0; i < 5; ++i)
						test(con, lineNum, money, allMoney, ffff.levelid(), i, k);
					
					for (int i = 5; i < 10; ++i)
						test(con, lineNum, money, allMoney, ffff.levelid(), i, k);
				}
			}
		} else {
			SlotGameMrg.getInstance().beginSlot(con, allMoney, lineNum, MoneyPreLine, 0, false, 0);
		}
	}

	private void test(PlayerConnection con, int lineNum, int MoneyPreLine,
			int allMoney, int level, int count, int k) {
		if (k == 1)
			con.getPlayer().getItemData().setItem(con, 1, 100* 10000, -1);
		else if (k == 2)
			con.getPlayer().getItemData().setItem(con, 1, 1000* 10000, -1);
		else if (k == 3)
			con.getPlayer().getItemData().setItem(con, 1, 10000* 10000, -1);
			
		con.getPlayer().getItemData().setItem(con, 101, 0, -1);
		con.getPlayer().getItemData().setItem(con, 102, 0, -1);
		con.getPlayer().getItemData().setItem(con, 112, 1, -1);
		con.getPlayer().getItemData().setItem(con, 113, 0, -1);
		con.getPlayer().getItemData().setItem(con, 114, 0, -1);
		con.getPlayer().getItemData().setItem(con, 116, 0, -1);
		
		int iddd = TableManager.GetInstance().getTargetPolicy(1, MoneyPreLine).step_id();
		con.getPlayer().getItemData().setItem(con, iddd, 0, -1);
		
		int MoneyPreLine11 = MoneyPreLine;
		MethodStatitic.getInstance().clear();
		con.setSendFlsg(false);
		for (int i = 0; i < 100; ++i) {
			if (con.getPlayer().getItemData().getItemCountByTempId(1) < allMoney)
				break;
			
			MethodStatitic.getInstance().recd(con);
			SlotGameMrg.getInstance().beginSlot(con, allMoney, lineNum, MoneyPreLine, 0, true, level);
			SlotGameMrg.getInstance().endSlot(con, true);
		}
		con.setSendFlsg(true);
		MethodStatitic.getInstance().save(level, MoneyPreLine11, count);
	}
}
