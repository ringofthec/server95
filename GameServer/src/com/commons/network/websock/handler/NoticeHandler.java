package com.commons.network.websock.handler;

import gameserver.jsonprotocol.GDL_C2G_GetNotice;
import gameserver.jsonprotocol.GDL_G2C_GetNoticeRe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConnection;

public class NoticeHandler {
	private final static NoticeHandler instance = new NoticeHandler();
	public static NoticeHandler getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(NoticeHandler.class);
	
	public void init() {
		HandlerManager.getInstance().pushNornalHandler(NoticeHandler.class, this, 
				"OnGetNotice", new GDL_C2G_GetNotice());
	}
	
	public void OnGetNotice(PlayerConnection con, GDL_C2G_GetNotice message) {
		GDL_G2C_GetNoticeRe re = new GDL_G2C_GetNoticeRe();
		
		/*
		LoginNoticeItem it = new LoginNoticeItem();
		it.setId(1);
		it.setTitle("");
		it.setContext("测试一个服务器公告!!!!!!!!!!!!");
		it.setIconName("NoticeIcon_9.png");
		re.getNoticeList().add(it);
		*/
		con.send(re);
	}
}
