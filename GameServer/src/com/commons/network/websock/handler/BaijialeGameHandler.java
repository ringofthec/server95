package com.commons.network.websock.handler;

import gameserver.jsonprotocol.BJLBetPool;
import gameserver.jsonprotocol.BJLBets;
import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.GDL_C2G_BJLBanker;
import gameserver.jsonprotocol.GDL_C2G_BJLBankerList;
import gameserver.jsonprotocol.GDL_C2G_BJLBetOn;
import gameserver.jsonprotocol.GDL_C2G_BJLBetOnAll;
import gameserver.jsonprotocol.GDL_C2G_BJLBetPools;
import gameserver.jsonprotocol.GDL_C2G_BJLDiscardBet;
import gameserver.jsonprotocol.GDL_C2G_BJLMiCard;
import gameserver.jsonprotocol.GDL_C2G_BJLMiCardEnd;
import gameserver.jsonprotocol.GDL_C2G_BJLNearBetResult;
import gameserver.jsonprotocol.GDL_C2G_BJLPlayerNotSitList;
import gameserver.jsonprotocol.GDL_C2G_BJLQieCard;
import gameserver.jsonprotocol.GDL_C2G_BJLQieCardEnd;
import gameserver.jsonprotocol.GDL_C2G_BJLResignBanker;
import gameserver.jsonprotocol.GDL_C2G_BJLSitDown;
import gameserver.jsonprotocol.GDL_C2G_BJLStandUp;
import gameserver.jsonprotocol.GDL_G2C_BJLBankerListRe;
import gameserver.jsonprotocol.GDL_G2C_BJLBankerRe;
import gameserver.jsonprotocol.GDL_G2C_BJLBetOnAllSync;
import gameserver.jsonprotocol.GDL_G2C_BJLBetPoolsRe;
import gameserver.jsonprotocol.GDL_G2C_BJLDiscardBetSync;
import gameserver.jsonprotocol.GDL_G2C_BJLMiCardSync;
import gameserver.jsonprotocol.GDL_G2C_BJLPlayerBetOn;
import gameserver.jsonprotocol.GDL_G2C_BJLPlayerNotSitList;
import gameserver.jsonprotocol.GDL_G2C_BJLQieCardEndSync;
import gameserver.jsonprotocol.GDL_G2C_BJLResignBankerRe;
import gameserver.jsonprotocol.GDL_G2C_BJLSitDownSync;
import gameserver.jsonprotocol.GDL_G2C_BJLStandUpSync;

import java.util.Map.Entry;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_GBJLBet;
import table.base.TableManager;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConnection;
import com.gdl.game.BaijialeGameInfo;
import com.gdl.game.BaijialeGameInfo.BankerInfo;
import com.gdl.game.BaijialeGameInstanceManager;

public class BaijialeGameHandler {
	private final static BaijialeGameHandler instance = new BaijialeGameHandler();
	public static BaijialeGameHandler getInstance() { return instance; }
	
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(BaijialeGameHandler.class);
	
	public void init() {
		HandlerManager.getInstance().pushNornalHandler(BaijialeGameHandler.class, this, 
				"OBJLNBetOn", new GDL_C2G_BJLBetOn());
		HandlerManager.getInstance().pushNornalHandler(BaijialeGameHandler.class, this, 
				"OBJLNBanker", new GDL_C2G_BJLBanker());
		HandlerManager.getInstance().pushNornalHandler(BaijialeGameHandler.class, this, 
				"OBJLNBankerList", new GDL_C2G_BJLBankerList());
		HandlerManager.getInstance().pushNornalHandler(BaijialeGameHandler.class, this, 
				"OBJLNBetPools", new GDL_C2G_BJLBetPools());
		HandlerManager.getInstance().pushNornalHandler(BaijialeGameHandler.class, this, 
				"OBJLBJLearBetResult", new GDL_C2G_BJLNearBetResult());
		HandlerManager.getInstance().pushNornalHandler(BaijialeGameHandler.class, this, 
				"OBJLNPlayerNotSitList", new GDL_C2G_BJLPlayerNotSitList());
		HandlerManager.getInstance().pushNornalHandler(BaijialeGameHandler.class, this, 
				"OBJLNSitDown", new GDL_C2G_BJLSitDown());
		HandlerManager.getInstance().pushNornalHandler(BaijialeGameHandler.class, this, 
				"OBJLNStandUp", new GDL_C2G_BJLStandUp());
		HandlerManager.getInstance().pushNornalHandler(BaijialeGameHandler.class, this, 
				"OBJLNResignBanker", new GDL_C2G_BJLResignBanker());
		HandlerManager.getInstance().pushNornalHandler(BaijialeGameHandler.class, this, 
				"OBJLMiCard", new GDL_C2G_BJLMiCard());
		HandlerManager.getInstance().pushNornalHandler(BaijialeGameHandler.class, this, 
				"OBJLMiCardEnd", new GDL_C2G_BJLMiCardEnd());
		HandlerManager.getInstance().pushNornalHandler(BaijialeGameHandler.class, this, 
				"OBJLDiscardBet", new GDL_C2G_BJLDiscardBet());
		HandlerManager.getInstance().pushNornalHandler(BaijialeGameHandler.class, this, 
				"OBJLQieCard", new GDL_C2G_BJLQieCard());
		HandlerManager.getInstance().pushNornalHandler(BaijialeGameHandler.class, this, 
				"OBJLQieCardEnd", new GDL_C2G_BJLQieCardEnd());
		HandlerManager.getInstance().pushNornalHandler(BaijialeGameHandler.class, this, 
				"OBJLBetOnAll", new GDL_C2G_BJLBetOnAll());
	}
	
	public void OBJLQieCardEnd(PlayerConnection con, GDL_C2G_BJLQieCardEnd message) {
		BaijialeGameInfo ninfo = BaijialeGameInstanceManager.getInstance().getPlayerBjlInstance(con);
		if (ninfo == null)
			return ;
		
		synchronized (ninfo) {
			if (ninfo.getStatus() != 7)
				return ;
			
			if (con.getPlayerId() != ninfo.getQieCardPlayerId())
				return ;
			
			ninfo.quickSwitch();
		}
	}
	
	public void OBJLQieCard(PlayerConnection con, GDL_C2G_BJLQieCard message) {
		BaijialeGameInfo ninfo = BaijialeGameInstanceManager.getInstance().getPlayerBjlInstance(con);
		if (ninfo == null)
			return ;
		
		synchronized (ninfo) {
			if (ninfo.getStatus() != 7)
				return ;
			
			if (con.getPlayerId() != ninfo.getQieCardPlayerId())
				return ;
			
			GDL_G2C_BJLQieCardEndSync sync = new GDL_G2C_BJLQieCardEndSync();
			sync.setData1(message.getData1());
			sync.setData2(message.getData2());
			ninfo.broadcast(sync);
		}
	}
	
	public void OBJLMiCardEnd(PlayerConnection con, GDL_C2G_BJLMiCardEnd message) {
		BaijialeGameInfo ninfo = BaijialeGameInstanceManager.getInstance().getPlayerBjlInstance(con);
		if (ninfo == null)
			return ;
		
		synchronized (ninfo) {
			if (ninfo.getStatus() < 2)
				return ;
			
			if (ninfo.getStatus() >= 6)
				return ;
			
			int status = ninfo.getStatus();
			if ((status == 2 || status == 4) && ninfo.getMiCardPlayerX() != con.getPlayerId())
				return ;
			
			if ((status == 3 || status == 5) && ninfo.getMiCardPlayerZ() != con.getPlayerId())
				return ;
			
			ninfo.quickSwitch();
		}
	}
	
	public void OBJLMiCard(PlayerConnection con, GDL_C2G_BJLMiCard message) {
		BaijialeGameInfo ninfo = BaijialeGameInstanceManager.getInstance().getPlayerBjlInstance(con);
		if (ninfo == null)
			return ;
		
		synchronized (ninfo) {
			if (ninfo.getStatus() < 2)
				return ;
			
			if (ninfo.getStatus() >= 6)
				return ;
			
			int status = ninfo.getStatus();
			if ((status == 2 || status == 4) && ninfo.getMiCardPlayerX() != con.getPlayerId())
				return ;
			
			if ((status == 3 || status == 5) && ninfo.getMiCardPlayerZ() != con.getPlayerId())
				return ;
			
			GDL_G2C_BJLMiCardSync sync = new GDL_G2C_BJLMiCardSync();
			sync.setData1(message.getData1());
			sync.setData2(message.getData2());
			sync.setData3(message.getData3());
			ninfo.broadcast(sync);
		}
	}
	
	public void OBJLNSitDown(PlayerConnection con, GDL_C2G_BJLSitDown message) {
		BaijialeGameInfo ninfo = BaijialeGameInstanceManager.getInstance().getPlayerBjlInstance(con);
		if (ninfo == null)
			return ;
		
		int pos = -1;
		synchronized (ninfo) {
			pos = ninfo.sitDown(con, message.getSit());
		}
		
		if (pos >= 0) {
			GDL_G2C_BJLSitDownSync sync = new GDL_G2C_BJLSitDownSync();
			sync.setSit(pos);
			sync.setPlayer(ninfo.fillPlayerInfo(con.getPlayerId()));
			ninfo.broadcast(sync);
		}
	}

	public void OBJLNStandUp(PlayerConnection con, GDL_C2G_BJLStandUp message) {
		BaijialeGameInfo ninfo = BaijialeGameInstanceManager.getInstance().getPlayerBjlInstance(con);
		if (ninfo == null)
			return ;
		
		int pos = -1;
		synchronized (ninfo) {
			pos = ninfo.standUp(con);
		}
		
		if (pos >= 0) {
			GDL_G2C_BJLStandUpSync sync = new GDL_G2C_BJLStandUpSync();
			sync.setPlayer_id(con.getPlayerId());
			ninfo.broadcast(sync);
		}
	}
	
	public void OBJLNPlayerNotSitList(PlayerConnection con, GDL_C2G_BJLPlayerNotSitList message) {
		BaijialeGameInfo ninfo = BaijialeGameInstanceManager.getInstance().getPlayerBjlInstance(con);
		if (ninfo == null)
			return ;

		synchronized (ninfo) {
			GDL_G2C_BJLPlayerNotSitList relist = ninfo.buildallPlayer();
			con.send(relist);
		}
	}
	
	public void OBJLBJLearBetResult(PlayerConnection con, GDL_C2G_BJLNearBetResult message) {
		BaijialeGameInfo ninfo = BaijialeGameInstanceManager.getInstance().getPlayerBjlInstance(con);
		if (ninfo == null)
			return ;
		
		synchronized (ninfo) {
			con.send(ninfo.getNearRes());
		}
	}
	
	public void OBJLDiscardBet(PlayerConnection con, GDL_C2G_BJLDiscardBet message) {
		BaijialeGameInfo ninfo = BaijialeGameInstanceManager.getInstance().getPlayerBjlInstance(con);
		if (ninfo == null)
			return ;
		
		synchronized (ninfo) {
			if (ninfo.getStatus() != 1)
				return ;
			
			ninfo.playerUnBetOn(con);
			
			GDL_G2C_BJLDiscardBetSync sync = new GDL_G2C_BJLDiscardBetSync();
			sync.setPlayer_id(con.getPlayerId());
			sync.setBetPools(ninfo.getPoolCoin());
			ninfo.broadcast(sync);
		}
	}
	
	public void OBJLBetOnAll(PlayerConnection con, GDL_C2G_BJLBetOnAll msg) {
		BaijialeGameInfo ninfo = BaijialeGameInstanceManager.getInstance().getPlayerBjlInstance(con);
		if (ninfo == null)
			return ;
		
		GDL_G2C_BJLBetOnAllSync re = new GDL_G2C_BJLBetOnAllSync();
		FastMap<Integer, Long> pool_coin_temp = new FastMap<Integer, Long>();
		boolean is_sit = false;
		synchronized (ninfo) {
			// 庄家怎么能下注呢？
			BankerInfo cur_banker = ninfo.getCurBankerId();
			if (cur_banker.player_id == con.getPlayerId())
				return ;
			
			// 只有下注中才能下注
			if (ninfo.getStatus() != 1)
				return ;
			
			int temp_count = msg.getPoolId().size();
			for (int i = 0; i < temp_count; ++i) {
				int poolid = msg.getPoolId().get(i);
				BJLBets bet = msg.getBets().get(i);
				
				int tt_c = bet.getBetId().size();
				BJLBets ooo = new BJLBets();
				for (int k = 0; k < tt_c; ++k) {
					int bet_id = bet.getBetId().get(k);
					long num = bet.getNum().get(k);
					
					MT_Data_GBJLBet config = TableManager.GetInstance().TableGBJLBet().GetElement(bet_id);
					if (config == null)
						return ;
		
					long read_bet_num = 0;
					for (int j = 0; j < num; ++j) {
						long total_money_req = config.money() * config.needmoney_rate();
						if (!con.getPlayer().getItemData().checkItemNumByTempId(Consts.getCOIN_ID(), total_money_req))
							break ;

						read_bet_num++;
						con.getPlayer().getItemData().decItemByTempId(con, Consts.getCOIN_ID(), total_money_req, 0, false);
						long co = total_money_req;
						if (pool_coin_temp.containsKey(poolid))
							co += pool_coin_temp.get(poolid);
						pool_coin_temp.put(poolid, co);
					}
					
					if (read_bet_num > 0) {
						ooo.getBetId().add(bet_id);
						ooo.getNum().add(read_bet_num);
					}
				}
				
				if (pool_coin_temp.containsKey(poolid)) {
					re.getPoolId().add(poolid);
					re.getBets().add(ooo);
				}
			}
			
			if (pool_coin_temp.isEmpty())
				return ;

			re.setPlayer_id(con.getPlayerId());
			for (Entry<Integer, Long> en : pool_coin_temp.entrySet())
				ninfo.playerBetOn(con, en.getKey(), en.getValue());
			
			re.setBetPools(ninfo.getPoolCoin());
			ninfo.broadcast(re);
		}
		
		con.getPlayer().getItemData().syncItemV(con, Consts.getCOIN_ID(), 0);
	}
	
	public void OBJLNBetOn(PlayerConnection con, GDL_C2G_BJLBetOn message) {
		BaijialeGameInfo ninfo = BaijialeGameInstanceManager.getInstance().getPlayerBjlInstance(con);
		if (ninfo == null)
			return ;
		
		MT_Data_GBJLBet config = TableManager.GetInstance().TableGBJLBet().GetElement(message.getBetId());
		if (config == null)
			return ;
		
		long total_money_req = config.money() * config.needmoney_rate();
		if (!con.getPlayer().getItemData().checkItemNumByTempId(Consts.getCOIN_ID(), total_money_req))
			return ;
		
		synchronized (ninfo) {
			// 庄家怎么能下注呢？
			BankerInfo cur_banker = ninfo.getCurBankerId();
			if (cur_banker.player_id == con.getPlayerId())
				return ;
			
			// 只有下注中才能下注
			if (ninfo.getStatus() != 1)
				return ;
			
			long mon = ninfo.getPlayerTotalMoney(con.getPlayerId());
			if (mon >= 200 * 10000)
				return ;
			
			con.getPlayer().getItemData().decItemByTempId(con, Consts.getCOIN_ID(), total_money_req, 0);
			boolean zxchange = ninfo.playerBetOn(con, message.getPoolIdx(), total_money_req);
			
			GDL_G2C_BJLPlayerBetOn bet = new GDL_G2C_BJLPlayerBetOn();
			bet.setPlayerId(con.getPlayerId());
			bet.setBetId(message.getBetId());
			bet.setPoolIdx(message.getPoolIdx());
			bet.setBetPools(ninfo.getPoolCoin());
			bet.setX(message.getX());
			bet.setY(message.getY());
			bet.setCoin(con.getPlayer().getItemCountByTempId(Consts.getCOIN_ID()));
			ninfo.broadcast(bet);
			
			if (zxchange)
				ninfo.sendXZChange();
		}
	}
	
	public void OBJLNResignBanker(PlayerConnection con, GDL_C2G_BJLResignBanker message) {
		BaijialeGameInfo ninfo = BaijialeGameInstanceManager.getInstance().getPlayerBjlInstance(con);
		if (ninfo == null)
			return ;
		
		GDL_G2C_BJLResignBankerRe re = new GDL_G2C_BJLResignBankerRe();
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
	
	public void OBJLNBanker(PlayerConnection con, GDL_C2G_BJLBanker message) {
		GDL_G2C_BJLBankerRe re = new GDL_G2C_BJLBankerRe();
		re.setRetcode(1);
		BaijialeGameInfo ninfo = BaijialeGameInstanceManager.getInstance().getPlayerBjlInstance(con);
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

	public void OBJLNBankerList(PlayerConnection con, GDL_C2G_BJLBankerList message) {
		BaijialeGameInfo ninfo = BaijialeGameInstanceManager.getInstance().getPlayerBjlInstance(con);
		if (ninfo == null)
			return ;
		
		synchronized (ninfo) {
			sendBankerList(con, ninfo);
		}
	}

	private void sendBankerList(PlayerConnection con, BaijialeGameInfo ninfo) {
		GDL_G2C_BJLBankerListRe re = new GDL_G2C_BJLBankerListRe();
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
	
	public void OBJLNBetPools(PlayerConnection con, GDL_C2G_BJLBetPools message) {
		BaijialeGameInfo ninfo = BaijialeGameInstanceManager.getInstance().getPlayerBjlInstance(con);
		if (ninfo == null)
			return ;
		
		synchronized (ninfo) {
			BJLBetPool np = ninfo.getPoolCoin();
			GDL_G2C_BJLBetPoolsRe re = new GDL_G2C_BJLBetPoolsRe();
			re.setBetPools(np);
			con.send(re);
		}
	}
}
