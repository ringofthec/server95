package com.commons.network.websock.handler;

import gameserver.jsonprotocol.GDL_G2C_Fallback;
import gameserver.jsonprotocol.GDL_G2C_GetItemInfoRe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConnection;
import com.commons.util.TimeUtil;
import com.gdl.data.PlayerData;

public class UtilHandler {
	private final static UtilHandler instance = new UtilHandler();
	public static UtilHandler getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(UtilHandler.class);
	
	public void init() {
		HandlerManager.getInstance().pushNornalHandler(UtilHandler.class, this, 
				"OnFallback", new GDL_G2C_Fallback());
	}
	
	public void OnFallback(PlayerConnection con, GDL_G2C_Fallback message) {
		String data = message.getData();
		PlayerData pdata = con.getPlayer();
		
		logger.debug("OnFallback data:{} funcId:{}", data, message.getFuncId());
		
		switch (message.getFuncId()) {
			case 1:
				pdata.flushRecharge(con);
			break;
			
			case 2:
				long now_time = TimeUtil.GetDateTime();
				pdata.onNewDay(now_time, false);
				GDL_G2C_GetItemInfoRe re = pdata.packetAllItem();
				con.send(re);
				break;
			
			default:
			break;
		}
	}
}
