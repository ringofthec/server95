package com.commons.network.websock.handler;

import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.GDL_C2G_NNBanker;
import gameserver.jsonprotocol.GDL_C2G_NNBankerList;
import gameserver.jsonprotocol.GDL_C2G_NNBetOn;
import gameserver.jsonprotocol.GDL_C2G_NNBetPools;
import gameserver.jsonprotocol.GDL_C2G_NNNearBetResult;
import gameserver.jsonprotocol.GDL_C2G_NNPlayerNotSitList;
import gameserver.jsonprotocol.GDL_C2G_NNReqLastPoolWin;
import gameserver.jsonprotocol.GDL_C2G_NNResignBanker;
import gameserver.jsonprotocol.GDL_C2G_NNSitDown;
import gameserver.jsonprotocol.GDL_C2G_NNStandUp;
import gameserver.jsonprotocol.GDL_G2C_NNBankerListRe;
import gameserver.jsonprotocol.GDL_G2C_NNBankerRe;
import gameserver.jsonprotocol.GDL_G2C_NNBetPoolsRe;
import gameserver.jsonprotocol.GDL_G2C_NNNearBetResultRe;
import gameserver.jsonprotocol.GDL_G2C_NNPlayerBetOn;
import gameserver.jsonprotocol.GDL_G2C_NNPlayerNotSitList;
import gameserver.jsonprotocol.GDL_G2C_NNReqLastPoolWin;
import gameserver.jsonprotocol.GDL_G2C_NNResignBankerRe;
import gameserver.jsonprotocol.GDL_G2C_NNSitDownSync;
import gameserver.jsonprotocol.GDL_G2C_NNStandUpSync;
import gameserver.jsonprotocol.NNBetPool;
import javolution.util.FastList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_GNiuBet;
import table.base.TableManager;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConnection;
import com.gdl.game.NiuniuGameInfo;
import com.gdl.game.NiuniuGameInfo.BankerInfo;
import com.gdl.game.NiuniuGameInstanceManager;

public class NiuniuGameHandler {
	private final static NiuniuGameHandler instance = new NiuniuGameHandler();
	public static NiuniuGameHandler getInstance() { return instance; }
	
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(NiuniuGameHandler.class);
	
	public void init() {
		HandlerManager.getInstance().pushNornalHandler(NiuniuGameHandler.class, this, 
				"OnNNBetOn", new GDL_C2G_NNBetOn());
		HandlerManager.getInstance().pushNornalHandler(NiuniuGameHandler.class, this, 
				"OnNNBanker", new GDL_C2G_NNBanker());
		HandlerManager.getInstance().pushNornalHandler(NiuniuGameHandler.class, this, 
				"OnNNBankerList", new GDL_C2G_NNBankerList());
		HandlerManager.getInstance().pushNornalHandler(NiuniuGameHandler.class, this, 
				"OnNNBetPools", new GDL_C2G_NNBetPools());
		HandlerManager.getInstance().pushNornalHandler(NiuniuGameHandler.class, this, 
				"OnNNNearBetResult", new GDL_C2G_NNNearBetResult());
		HandlerManager.getInstance().pushNornalHandler(NiuniuGameHandler.class, this, 
				"OnNNPlayerNotSitList", new GDL_C2G_NNPlayerNotSitList());
		HandlerManager.getInstance().pushNornalHandler(NiuniuGameHandler.class, this, 
				"OnNNSitDown", new GDL_C2G_NNSitDown());
		HandlerManager.getInstance().pushNornalHandler(NiuniuGameHandler.class, this, 
				"OnNNStandUp", new GDL_C2G_NNStandUp());
		HandlerManager.getInstance().pushNornalHandler(NiuniuGameHandler.class, this, 
				"OnNNResignBanker", new GDL_C2G_NNResignBanker());
		HandlerManager.getInstance().pushNornalHandler(NiuniuGameHandler.class, this, 
				"OnNNReqLastPoolWin", new GDL_C2G_NNReqLastPoolWin());
	}
	
	public void OnNNReqLastPoolWin(PlayerConnection con, GDL_C2G_NNReqLastPoolWin message) {
		NiuniuGameInfo ninfo = NiuniuGameInstanceManager.getInstance().getPlayerNiuniuInstance(con);
		if (ninfo == null)
			return ;
		
		GDL_G2C_NNReqLastPoolWin msg = new GDL_G2C_NNReqLastPoolWin();
		ninfo.getLastPoolWin(msg);
		con.send(msg);
	}
	
	public void OnNNSitDown(PlayerConnection con, GDL_C2G_NNSitDown message) {
		NiuniuGameInfo ninfo = NiuniuGameInstanceManager.getInstance().getPlayerNiuniuInstance(con);
		if (ninfo == null)
			return ;
		
		int pos = -1;
		synchronized (ninfo) {
			pos = ninfo.sitDown(con);
		}
		
		if (pos >= 0) {
			GDL_G2C_NNSitDownSync sync = new GDL_G2C_NNSitDownSync();
			sync.setSit(pos);
			sync.setPlayer(ninfo.fillPlayerInfo(con.getPlayerId()));
			ninfo.broadcast(sync);
		}
	}

	public void OnNNStandUp(PlayerConnection con, GDL_C2G_NNStandUp message) {
		NiuniuGameInfo ninfo = NiuniuGameInstanceManager.getInstance().getPlayerNiuniuInstance(con);
		if (ninfo == null)
			return ;
		
		int pos = -1;
		synchronized (ninfo) {
			pos = ninfo.standUp(con);
		}
		
		if (pos >= 0) {
			GDL_G2C_NNStandUpSync sync = new GDL_G2C_NNStandUpSync();
			sync.setPlayer_id(con.getPlayerId());
			ninfo.broadcast(sync);
		}
	}
	
	public void OnNNPlayerNotSitList(PlayerConnection con, GDL_C2G_NNPlayerNotSitList message) {
		NiuniuGameInfo ninfo = NiuniuGameInstanceManager.getInstance().getPlayerNiuniuInstance(con);
		if (ninfo == null)
			return ;

		synchronized (ninfo) {
			GDL_G2C_NNPlayerNotSitList relist = ninfo.buildallPlayer();
			con.send(relist);
		}
	}
	
	public void OnNNNearBetResult(PlayerConnection con, GDL_C2G_NNNearBetResult message) {
		NiuniuGameInfo ninfo = NiuniuGameInstanceManager.getInstance().getPlayerNiuniuInstance(con);
		if (ninfo == null)
			return ;
		
		GDL_G2C_NNNearBetResultRe re = new GDL_G2C_NNNearBetResultRe();
		re.setRes(ninfo.getNearRes());
		con.send(re);
	}
	
	public void OnNNBetOn(PlayerConnection con, GDL_C2G_NNBetOn message) {
		NiuniuGameInfo ninfo = NiuniuGameInstanceManager.getInstance().getPlayerNiuniuInstance(con);
		if (ninfo == null)
			return ;
		
		MT_Data_GNiuBet config = TableManager.GetInstance().TableGNiuBet().GetElement(message.getBetId());
		if (config == null)
			return ;
		
		long player_all_money = con.getPlayer().getCoin();
		if (player_all_money * 0.3 < config.money())
			return ;
		
		long total_money_req = config.money() * config.needmoney_rate();
		if (!con.getPlayer().getItemData().checkItemNumByTempId(Consts.getCOIN_ID(), total_money_req))
			return ;
		
		boolean is_sit = false;
		synchronized (ninfo) {
			// 庄家怎么能下注呢？
			BankerInfo cur_banker = ninfo.getCurBankerId();
			if (cur_banker.player_id == con.getPlayerId())
				return ;
			
			// 只有下注中才能下注
			if (ninfo.getStatus() != 1)
				return ;
			
			ninfo.playerBetOn(con, message.getPoolIdx(), config.money());
			is_sit = ninfo.isSit(con.getPlayerId());
		}
		
		GDL_G2C_NNPlayerBetOn bet = new GDL_G2C_NNPlayerBetOn();
		bet.setPlayerId(con.getPlayerId());
		bet.setBetId(message.getBetId());
		bet.setPoolIdx(message.getPoolIdx());
		if (is_sit) {
			ninfo.broadcast(bet);
		} else {
			con.send(bet);
		}
	}
	
	public void OnNNResignBanker(PlayerConnection con, GDL_C2G_NNResignBanker message) {
		NiuniuGameInfo ninfo = NiuniuGameInstanceManager.getInstance().getPlayerNiuniuInstance(con);
		if (ninfo == null)
			return ;
		
		GDL_G2C_NNResignBankerRe re = new GDL_G2C_NNResignBankerRe();
		re.setRetcode(1);
		
		synchronized (ninfo) {
			if (!ninfo.isInBank(con.getPlayerId())) {
				con.send(re);
				return ;
			}
			
			ninfo.leaveBanker(con);
			re.setRetcode(0);
			con.send(re);
			sendBankerList(con, ninfo);
		}
	}
	
	public void OnNNBanker(PlayerConnection con, GDL_C2G_NNBanker message) {
		GDL_G2C_NNBankerRe re = new GDL_G2C_NNBankerRe();
		re.setRetcode(1);
		NiuniuGameInfo ninfo = NiuniuGameInstanceManager.getInstance().getPlayerNiuniuInstance(con);
		if (ninfo == null) {
			con.send(re);
			return ;
		}
		
		long total_coin = message.getTotalCoin();
		if (total_coin < Consts.getNiuniu_ZZ_Min_Coin() ||
				total_coin > Consts.getNiuniu_ZZ_Max_Coin()) {
			con.send(re);
			return ;
		}
		
		if (!con.getPlayer().getItemData().checkItemNumByTempId(Consts.getCOIN_ID(), total_coin)) {
			con.send(re);
			return ;
		}
		
		synchronized (ninfo) {
			if (ninfo.isInBank(con.getPlayerId())) {
				con.send(re);
				return ;
			}

			ninfo.reqBanker(con, message.getTotalCoin());
			re.setRetcode(0);
			con.send(re);
			sendBankerList(con, ninfo);
		}
	}

	public void OnNNBankerList(PlayerConnection con, GDL_C2G_NNBankerList message) {
		NiuniuGameInfo ninfo = NiuniuGameInstanceManager.getInstance().getPlayerNiuniuInstance(con);
		if (ninfo == null)
			return ;
		
		synchronized (ninfo) {
			sendBankerList(con, ninfo);
		}
	}

	private void sendBankerList(PlayerConnection con, NiuniuGameInfo ninfo) {
		GDL_G2C_NNBankerListRe re = new GDL_G2C_NNBankerListRe();
		BankerInfo cur_banker = ninfo.getCurBankerId();
		if (cur_banker.player_id > 0) {
			re.getPlayers().add(ninfo.fillPlayerInfo(cur_banker.player_id));
			re.getBankerCoin().add(cur_banker.cur_total);
		}
		
		FastList<BankerInfo> ranks = ninfo.getBankers();
		for (BankerInfo pi : ranks) {
			re.getPlayers().add(ninfo.fillPlayerInfo(pi.player_id));
			re.getBankerCoin().add(pi.cur_total);
		}
		con.send(re);
	}
	
	public void OnNNBetPools(PlayerConnection con, GDL_C2G_NNBetPools message) {
		NiuniuGameInfo ninfo = NiuniuGameInstanceManager.getInstance().getPlayerNiuniuInstance(con);
		if (ninfo == null)
			return ;
		
		synchronized (ninfo) {
			NNBetPool np = ninfo.getPoolCoin();
			GDL_G2C_NNBetPoolsRe re = new GDL_G2C_NNBetPoolsRe();
			re.setBetPools(np);
			con.send(re);
		}
	}
}
