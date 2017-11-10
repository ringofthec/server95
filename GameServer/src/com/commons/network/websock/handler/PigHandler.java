package com.commons.network.websock.handler;

import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.GDL_C2G_PigCoinReward;
import gameserver.jsonprotocol.GDL_C2G_PigGetCoin;
import gameserver.jsonprotocol.GDL_C2G_PigGiveGold;
import gameserver.jsonprotocol.GDL_C2G_PigGold2Coin;
import gameserver.jsonprotocol.GDL_C2G_PigSaveCoin;
import gameserver.jsonprotocol.GDL_C2G_ReqPigOpRecord;
import gameserver.jsonprotocol.GDL_G2C_PigGiveGoldRe;
import gameserver.jsonprotocol.GDL_G2C_PigGold2CoinRe;
import gameserver.jsonprotocol.GDL_G2C_PigOpRecordRe;
import gameserver.jsonprotocol.PigOpRecord;
import gameserver.utils.DbMgr;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_GVip;
import table.base.TableManager;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConManager;
import com.commons.network.websock.PlayerConnection;
import com.commons.util.TimeUtil;
import com.gdl.data.MailData;
import com.gdl.data.PlayerBriefInfo;
import com.gdl.manager.PlayerBriefInfoManager;

import databaseshare.DatabaseAccount;
import databaseshare.DatabasePigop;

public class PigHandler {
	private final static PigHandler instance = new PigHandler();
	public static PigHandler getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(PigHandler.class);
	
	public void init() {
		HandlerManager.getInstance().pushNornalHandler(PigHandler.class, this, 
				"OnPigGetCoin", new GDL_C2G_PigGetCoin());
		HandlerManager.getInstance().pushNornalHandler(PigHandler.class, this, 
				"OnPigGiveGold", new GDL_C2G_PigGiveGold());
		HandlerManager.getInstance().pushNornalHandler(PigHandler.class, this, 
				"OnPigGold2Coin", new GDL_C2G_PigGold2Coin());
		HandlerManager.getInstance().pushNornalHandler(PigHandler.class, this, 
				"OnPigSaveCoin", new GDL_C2G_PigSaveCoin());
		HandlerManager.getInstance().pushNornalHandler(PigHandler.class, this, 
				"OnPigCoinReward", new GDL_C2G_PigCoinReward());
		HandlerManager.getInstance().pushNornalHandler(PigHandler.class, this, 
				"OnReqPigOpRecord", new GDL_C2G_ReqPigOpRecord());
	}
	
	public void OnReqPigOpRecord(PlayerConnection con, GDL_C2G_ReqPigOpRecord message) {
		List<DatabasePigop> pps = DbMgr.getInstance().getShareDB().Select(DatabasePigop.class, 
				"player_id=?", con.getPlayerId());
		
		GDL_G2C_PigOpRecordRe ree = new GDL_G2C_PigOpRecordRe();
		for (DatabasePigop o : pps) {
			PigOpRecord pty = new PigOpRecord();
			pty.setOp(o.optype);
			pty.setNum(o.number);
			pty.setOptime(TimeUtil.GetDateString(o.op_time));
			pty.setOtherId(o.otherid);
			pty.setOtherName(o.othername);
			
			if (o.optype == 0 ||
					o.optype == 1)
				pty.setPigcoin(o.pigcoin);
			
			if (o.optype == 2 ||
					o.optype == 3) {
				pty.setMoney(o.money);
				pty.setGold(o.gold);
			}
			
			ree.getRecords().add(pty);
		}
		
		con.send(ree);
	}
	
	// 获取金猪金币奖励
	public void OnPigCoinReward(PlayerConnection con, GDL_C2G_PigCoinReward message) {
		long c = con.getPlayer().getItemData().getItemCountByTempId(Consts.getPIG_COIN_ID());
		if (c <= 0)
			return ;
		
		con.getPlayer().getItemData().decItemByTempId(con, Consts.getPIG_COIN_ID(), c, Consts.getItemEventPigReward());
		con.getPlayer().getItemData().addItem(con, Consts.getCOIN_ID(), c, Consts.getItemEventPigReward());
		
		if (c >= 100 * 10000) {
			ChatHandler.newbanner(55, con.getPlayer().getName(), con.getPlayerId(), c);
		}
	}
	
	private void saveRecord(long player_id, int type, long num, long otherid, long pc, long mycoin, long mygold) {
		DatabasePigop p = new DatabasePigop();
		p.player_id = player_id;
		p.optype = type;
		p.number = num;
		p.op_time = TimeUtil.GetDateTime();
		p.otherid = otherid;
		p.othername = "";
		p.pigcoin = pc;
		p.money = mycoin;
		p.gold = mygold;
		if (otherid != 0) { 
			PlayerBriefInfo oooo = PlayerBriefInfoManager.getInstance().getPlayerBriefInfo(otherid);
			if (oooo != null)
				p.othername = oooo.getName();
		}
		
		DbMgr.getInstance().getShareDB().Insert(p);
	}
	
	// 取金币
	public void OnPigGetCoin(PlayerConnection con, GDL_C2G_PigGetCoin message) {
		long c = con.getPlayer().getItemData().getItemCountByTempId(Consts.getPIG_COIN_ID());
		if (c <= 0)
			return ;
		
		if (message.getCount() <= 0L)
			return ;
		
		if (message.getCount() > c)
			return ;
		
		con.getPlayer().getItemData().decItemByTempId(con, Consts.getPIG_COIN_ID(), message.getCount(), Consts.getItemEventPigGet());
		con.getPlayer().getItemData().addItem(con, Consts.getCOIN_ID(), message.getCount(), Consts.getItemEventPigGet());
		ItemHandler.showAddItem(con, Consts.getCOIN_ID(), message.getCount().intValue());
		
		saveRecord(con.getPlayerId(), 1, message.getCount(), 0, 
				con.getPlayer().getItemCountByTempId(Consts.getPIG_COIN_ID()),
				con.getPlayer().getItemCountByTempId(Consts.getCOIN_ID()),
				con.getPlayer().getItemCountByTempId(Consts.getGOLD_ID()));
	}
	
	// 送别人金砖
	public void OnPigGiveGold(PlayerConnection con, GDL_C2G_PigGiveGold message) {
		
		long c = con.getPlayer().getItemData().getItemCountByTempId(Consts.getGOLD_ID());
		if (message.getType() == 0) 
			c = con.getPlayer().getItemData().getItemCountByTempId(Consts.getCOIN_ID());
		
		if (c < message.getCount())
			return ;
		
		GDL_G2C_PigGiveGoldRe re = new GDL_G2C_PigGiveGoldRe();
		int num = DbMgr.getInstance().getShareDB().Count(DatabaseAccount.class, "player_id=?", message.getPlayerId());
		if (num != 1) {
			re.setRetCode(1);
			con.send(re);
			return ;
		}
		
		re.setRetCode(0);
		PlayerConnection p = PlayerConManager.getInstance().getCon(message.getPlayerId());
		if (message.getType() == 1) {
			Integer[] item = {Consts.getGOLD_ID(), message.getCount().intValue()};
			if (p != null) {
				MailData.sendSystemMail(p, "金磚贈送", con.getPlayer().getName(), 
						"", 15, item);
			} else {
				MailData.createSysOfflineMail(message.getPlayerId(), "金磚贈送", con.getPlayer().getName(), 
						"", 15, item);
			}
			con.getPlayer().getItemData().decItemByTempId(con, Consts.getGOLD_ID(), message.getCount(), Consts.getItemEventPigGive());
			
			saveRecord(con.getPlayerId(), 3, message.getCount(), message.getPlayerId(), 0,
					con.getPlayer().getItemCountByTempId(Consts.getCOIN_ID()),
					con.getPlayer().getItemCountByTempId(Consts.getGOLD_ID()));
			
			saveRecord(message.getPlayerId(), 5, message.getCount(), con.getPlayerId(), 0, 0, 0);
		} else {
			int real_coin = (int)(message.getCount().intValue() * 0.95);
			Integer[] item = {Consts.getCOIN_ID(), real_coin};
			if (p != null) {
				MailData.sendSystemMail(p, "金幣贈送", con.getPlayer().getName(), 
						"", 15, item);
			} else {
				MailData.createSysOfflineMail(message.getPlayerId(), "金幣贈送", con.getPlayer().getName(), 
						"", 15, item);
			}
			con.getPlayer().getItemData().decItemByTempId(con, Consts.getCOIN_ID(), message.getCount(), Consts.getItemEventPigGive());
			
			saveRecord(con.getPlayerId(), 2, message.getCount(), message.getPlayerId(), 0,
					con.getPlayer().getItemCountByTempId(Consts.getCOIN_ID()),
					con.getPlayer().getItemCountByTempId(Consts.getGOLD_ID()));
			saveRecord(message.getPlayerId(), 4, real_coin, con.getPlayerId(), 0, 0, 0);
		}
		con.send(re);
	}
	
	// 金砖 2 金币
	public void OnPigGold2Coin(PlayerConnection con, GDL_C2G_PigGold2Coin message) {
		GDL_G2C_PigGold2CoinRe re = new GDL_G2C_PigGold2CoinRe();
		long c = con.getPlayer().getItemData().getItemCountByTempId(Consts.getGOLD_ID());
		if (c < message.getCount()) {
			re.setRetCode(1);
			con.send(re);
			return ;
		}
		
		long add_coin = message.getCount() * 1000;
		con.getPlayer().getItemData().decItemByTempId(con, Consts.getGOLD_ID(), message.getCount(), Consts.getItemEventPigConvert());
		con.getPlayer().getItemData().addItem(con, Consts.getCOIN_ID(), add_coin, Consts.getItemEventPigConvert());
		re.setRetCode(0);
		con.send(re);
		
		ItemHandler.showAddItem(con, Consts.getCOIN_ID(), add_coin);
	}

	// 储蓄金币
	public void OnPigSaveCoin(PlayerConnection con, GDL_C2G_PigSaveCoin message) {
		long c = con.getPlayer().getItemData().getItemCountByTempId(Consts.getCOIN_ID());
		if (c < message.getCount())
			return ;
		
		MT_Data_GVip vipc = TableManager.GetInstance().TableGVip().GetElement(con.getPlayer().getVipLvl());
		if (vipc == null)
			return ;
		
		/*
		long cur_n = con.getPlayer().getItemData().getItemCountByTempId(Consts.getPIG_COIN_ID());
		if (vipc.getM_ngoldPig_Max() < cur_n + message.getCount())
			return ;
			*/
		
		con.getPlayer().getItemData().decItemByTempId(con, Consts.getCOIN_ID(), message.getCount(), Consts.getItemEventPigSave());
		con.getPlayer().getItemData().addItem(con, Consts.getPIG_COIN_ID(), message.getCount(), Consts.getItemEventPigSave());
		
		saveRecord(con.getPlayerId(), 0, message.getCount(), 0, 
				con.getPlayer().getItemCountByTempId(Consts.getPIG_COIN_ID()),
				con.getPlayer().getItemCountByTempId(Consts.getCOIN_ID()),
				con.getPlayer().getItemCountByTempId(Consts.getGOLD_ID()));
	}
}
