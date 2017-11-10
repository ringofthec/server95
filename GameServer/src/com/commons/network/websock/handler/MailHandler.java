package com.commons.network.websock.handler;

import gameserver.jsonprotocol.GDL_C2G_GetMailItem;
import gameserver.jsonprotocol.GDL_C2G_GetMailList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConnection;
import com.gdl.data.PlayerData;

public class MailHandler {
	private final static MailHandler instance = new MailHandler();
	public static MailHandler getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(MailHandler.class);
	
	public void init() {
		HandlerManager.getInstance().pushNornalHandler(MailHandler.class, this, 
				"OnGetMailList", new GDL_C2G_GetMailList());
		HandlerManager.getInstance().pushNornalHandler(MailHandler.class, this, 
				"OnGetMailItem", new GDL_C2G_GetMailItem());
	}
	
	public void OnGetMailList(PlayerConnection con, GDL_C2G_GetMailList message) {
		con.getPlayer().getMailData().SendAllMailListMsg(con);
	}
	
	public void OnGetMailItem(PlayerConnection con, GDL_C2G_GetMailItem message) {
		PlayerData p = con.getPlayer();
		p.getMailData().reciveAll(con);
	}
}
