package com.commons.network.websock.handler;

import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.GDL_C2G_ExchangeCode;
import gameserver.jsonprotocol.GDL_G2C_ExchangeCodeRe;
import gameserver.utils.DbMgr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConnection;

import databaseshare.DatabaseExchangecodegift;

public class ExchangeCodeHandler {
	private final static ExchangeCodeHandler instance = new ExchangeCodeHandler();
	public static ExchangeCodeHandler getInstance() { return instance; }
	
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(ExchangeCodeHandler.class);
	
	public void init() {
		HandlerManager.getInstance().pushNornalHandler(ExchangeCodeHandler.class, this, 
				"OnExchangeCode", new GDL_C2G_ExchangeCode());
	}
	
	public void OnExchangeCode(PlayerConnection con, GDL_C2G_ExchangeCode message) {
		GDL_G2C_ExchangeCodeRe msg = new GDL_G2C_ExchangeCodeRe();
		String code = message.getCode();
		if (code == null) {
			msg.setRetCode(1);
			con.send(msg);
			return ;
		}
		
		DatabaseExchangecodegift patch = DbMgr.getInstance().getShareDB().SelectOne(DatabaseExchangecodegift.class, 
				"exchangecode=?", code.toLowerCase());
		
		if (patch == null) {
			msg.setRetCode(1);
			con.send(msg);
			return ;
		}
		
		int count = DbMgr.getInstance().getShareDB().Count(DatabaseExchangecodegift.class, "patch_id=? and player_id=?", 
				patch.patch_id, con.getPlayer().getPlayerId());
		if (count > 0) {
			msg.setRetCode(2);
			con.send(msg);
			return ;
		}
		
		// send reward
		con.getPlayer().getItemData().addItem(con, patch.item_id, patch.item_num, Consts.getItemEventExchangeCode());
		ItemHandler.showAddItem(con, patch.item_id, patch.item_num);
		patch.player_id = con.getPlayer().getPlayerId();
		patch.save();
		
		msg.setRetCode(0);
		con.send(msg);
		//HandlerManager.getInstance().showToast(con, "领取成功!");
	}
}
