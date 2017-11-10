package com.commons.network.websock.handler;

import gameserver.jsonprotocol.GDL_C2G_ChangePhone;
import gameserver.jsonprotocol.GDL_C2G_ReqChangePhone;
import gameserver.jsonprotocol.GDL_G2C_ChangePhoneRe;
import gameserver.jsonprotocol.GDL_G2C_ReqChangePhoneRe;
import gameserver.utils.DbMgr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConManager;
import com.commons.network.websock.PlayerConnection;
import com.commons.util.RandomUtil;
import com.commons.util.TimeUtil;
import com.gdl.data.PlayerData;

import databaseshare.DatabaseAccount;

public class ChangePhoneHandler {
	private final static ChangePhoneHandler instance = new ChangePhoneHandler();
	public static ChangePhoneHandler getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(ChangePhoneHandler.class);
	
	public void init() {
		HandlerManager.getInstance().pushNornalHandler(ChangePhoneHandler.class, this, 
				"OnReqChangePhone", new GDL_C2G_ReqChangePhone());
		HandlerManager.getInstance().pushNornalHandler(ChangePhoneHandler.class, this, 
				"OnChangePhone", new GDL_C2G_ChangePhone());
	}
	
	private char randomKey() {
		char a = 'a';
		char z = 'z';
		char n0 = '0';
		char n9 = '9';
		
		int rand = RandomUtil.RangeRandom(0, 36);
		if (rand < 26) {
			return (char)(RandomUtil.RangeRandom(a, z));
		} else {
			return (char)(RandomUtil.RangeRandom(n0, n9));
		}
	}
	
	private String genChangeCode() {
		String lll = "";
		for (int i = 0; i < 4; ++i) {
			lll += randomKey();
		}
		
		lll += " ";
		
		for (int i = 0; i < 4; ++i) {
			lll += randomKey();
		}
		
		lll += " ";
		
		for (int i = 0; i < 4; ++i) {
			lll += randomKey();
		}
		return lll;
	}
	
	public void OnReqChangePhone(PlayerConnection con, GDL_C2G_ReqChangePhone message) {
		PlayerData p = con.getPlayer();
		String co = p.getChangePhoneCode();
		if (co.isEmpty()) {
			co = genChangeCode();
			p.flushChangePhoneCode(co.replace(" ", ""));
		}
		
		long ttt = p.getChangePhoneExpireTime() - TimeUtil.GetDateTime();
		int second = (int)(ttt / 1000);
		
		GDL_G2C_ReqChangePhoneRe msg = new GDL_G2C_ReqChangePhoneRe();
		msg.setCode(co);
		msg.setLifeTimeSecond(second);
		con.send(msg);
	}
	
	public void OnChangePhone(PlayerConnection con, GDL_C2G_ChangePhone message) {
		PlayerData p = con.getPlayer();
		String co = message.getCode();
		if (co == null || co.isEmpty() || co.length() != 12)
			return ;
		
		GDL_G2C_ChangePhoneRe msg = new GDL_G2C_ChangePhoneRe();
		DatabaseAccount ac = DbMgr.getInstance().getShareDB().SelectOne(DatabaseAccount.class, 
				"change_phone_code=?", co);
		if (ac == null) {
			msg.setRetCode(2);
			con.send(msg);
			return ;
		}
		
		if (TimeUtil.GetDateTime() > ac.change_phone_code_expire_time + 5 * 1000) {
			msg.setRetCode(1);
			con.send(msg);
			return ;
		}
		
		long selfPlayerId = p.getPlayerId();
		logger.error("player {}-{} change phone with {}-{}", ac.getPlayer_id(), ac.getTemp_uid()
				,selfPlayerId, p.getTempUUId() );
		
		// 开始操作
		DbMgr.getInstance().getShareDB().Execute("update account set temp_uid=? where player_id=?",
								p.getTempUUId(), ac.getPlayer_id());
		DbMgr.getInstance().getShareDB().Execute("update account set temp_uid=? where player_id=?",
				ac.getTemp_uid(), selfPlayerId);
		msg.setRetCode(0);
		con.send(msg);
		
		ItemHandler.newshowToastDir(con, 10001);
		con.close();
		PlayerConnection pc = PlayerConManager.getInstance().getCon(ac.getPlayer_id());
		if (pc != null) {
			ItemHandler.newshowToastDir(con, 10001);
			pc.close();
		}
	}
}
