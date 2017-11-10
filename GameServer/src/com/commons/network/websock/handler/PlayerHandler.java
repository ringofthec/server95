package com.commons.network.websock.handler;

import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.GDL_C2G_ChangeName;
import gameserver.jsonprotocol.GDL_C2G_GetBattleRecord;
import gameserver.jsonprotocol.GDL_C2G_GetPlayerInfo;
import gameserver.jsonprotocol.GDL_C2G_HeadUpdate;
import gameserver.jsonprotocol.GDL_C2G_SetPlayerInfo;
import gameserver.jsonprotocol.GDL_G2C_ChangeNameRe;
import gameserver.jsonprotocol.GDL_G2C_GetBattleRecordRe;
import gameserver.jsonprotocol.GDL_G2C_GetPlayerInfoRe;
import gameserver.jsonprotocol.GDL_G2C_HeadUpdateRe;
import gameserver.jsonprotocol.GiftShowInfo;
import gameserver.utils.DbMgr;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConnection;
import com.gdl.data.PlayerBriefInfo;
import com.gdl.data.PlayerData;
import com.gdl.data.PlayerMoneyStatisInfo;
import com.gdl.game.GameInstanceMrg;
import com.gdl.game.SlotActiveMrg;
import com.gdl.manager.PlayerBriefInfoManager;
import com.gdl.manager.RankManager;

import database.gdl.gameserver.CustomGiftShow;



public class PlayerHandler {
	private final static PlayerHandler instance = new PlayerHandler();
	public static PlayerHandler getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(PlayerHandler.class);
	
	public void init() {
		HandlerManager.getInstance().pushNornalHandler(PlayerHandler.class, this, 
				"OnGetPlayerInfo", new GDL_C2G_GetPlayerInfo());
		HandlerManager.getInstance().pushNornalHandler(PlayerHandler.class, this, 
				"OnChangeName", new GDL_C2G_ChangeName());
		HandlerManager.getInstance().pushNornalHandler(PlayerHandler.class, this, 
				"OnSetPlayerInfo", new GDL_C2G_SetPlayerInfo());
		HandlerManager.getInstance().pushNornalHandler(PlayerHandler.class, this, 
				"OnGetBattleRecord", new GDL_C2G_GetBattleRecord());
		HandlerManager.getInstance().pushNornalHandler(PlayerHandler.class, this, 
				"OnHeadUpdate", new GDL_C2G_HeadUpdate());
	}
	
	public void OnHeadUpdate(PlayerConnection ctx, GDL_C2G_HeadUpdate message) {
		String newhead = message.getHead();
		PlayerData pd = ctx.getPlayer();
		pd.setHead(newhead);
		RankManager.getInstance().updateMyRankHead(pd.getPlayerId(), newhead);
		
		GDL_G2C_HeadUpdateRe re = new GDL_G2C_HeadUpdateRe();
		re.setRetCode(0);
		re.setHead(newhead);
		ctx.send(re);
		
		ItemHandler.newshowToast(ctx, 10006);
	}
	
	public void OnSetPlayerInfo(PlayerConnection ctx, GDL_C2G_SetPlayerInfo message) {
		PlayerData pd = ctx.getPlayer();
		if (message.getMode() == 1) {
			pd.setSex(message.getSex());
		} else {
			pd.setSign(message.getSign());
		}
		
		ItemHandler.newshowToast(ctx, 10007);
	}
	
	public void OnChangeName(PlayerConnection con, GDL_C2G_ChangeName message) {
		PlayerData p = con.getPlayer();
		GDL_G2C_ChangeNameRe re = new GDL_G2C_ChangeNameRe();
		String new_name = message.getName();
		int ret = LoginHandler.getInstance().isNameVaild(con.getRawCon(), new_name);
		if (ret != 0) {
			re.setRetCode(ret);
			con.send(re);
			return ;
		}
		
		if (!ShopHandler.testCost(con, 1, -1))
			return ;
		
		DbMgr.getInstance().getShareDB().Execute("update player_brief_info set name=? where player_id=?", 
				new_name, p.getPlayerId());
		p.getDB().Execute("update player set name=? where player_id=?", 
				new_name, p.getPlayerId());
		p.setName(new_name);
		RankManager.getInstance().updateMyRankName(p.getPlayerId(), new_name);
		
		re.setRetCode(0);
		re.setName(new_name);
		con.send(re);
	}
	
	public void OnGetBattleRecord(PlayerConnection con, GDL_C2G_GetBattleRecord message) {
		PlayerMoneyStatisInfo info = PlayerBriefInfoManager.getInstance().getPlayerMoneyStatisInfo(message.getPlayerId());
		if (info == null)
			return ;
		
		GDL_G2C_GetBattleRecordRe re = info.genBattleRecord(message.getFunc());
		if (re == null)
			return ;
		
		con.send(re);
	}
	
	public void OnGetPlayerInfo(PlayerConnection con, GDL_C2G_GetPlayerInfo message) {
		PlayerData p = con.getPlayer();
		
		GDL_G2C_GetPlayerInfoRe re = new GDL_G2C_GetPlayerInfoRe();
		if (message.getPlayerId() == null || message.getPlayerId().longValue() == con.getPlayerId()) {
			re.setFunc(message.getFunc());
			re.setName(p.getName());
			if (message.getMode() == 0) {
				re.setPlayerId(p.getPlayerId());
				re.setLevel(p.getLvl());
				re.setVipLevel(p.getVipLvl());
				re.setSex(p.getSex());
				re.setGamepos(GameInstanceMrg.getGameId(p.getGameUID()));
				re.setSign(p.getSign());
				re.setUrl(p.getHeadUrl());
				
				re.setPigcoin(p.getItemCountByTempId(Consts.getPIG_COIN_ID()));
				re.setMoney(p.getItemCountByTempId(Consts.getCOIN_ID()));
				re.setGold(p.getItemCountByTempId(Consts.getGOLD_ID()));
				re.setTotal(p.getExp());
				re.setLiked(p.getItemCountByTempId(Consts.getLIKEID()));
				re.setIsJoin(SlotActiveMrg.getInstance().getGoldAvtiveLevelId(con));
				
				List<CustomGiftShow> new_gift = p.getGiftShow();
				for (CustomGiftShow jj : new_gift) {
					GiftShowInfo temp = new GiftShowInfo();
					temp.setItemTempId(jj.id);
					temp.setNum(jj.num);
					temp.setExpireTime(jj.exp_time);
					re.getShowGiftList().add(temp);
				}
			}
		} else {
			PlayerBriefInfo pbi = PlayerBriefInfoManager.getInstance().getPlayerBriefInfo(message.getPlayerId());
			if (pbi != null) {
				re.setFunc(message.getFunc());
				re.setName(pbi.getName());
				if (message.getMode() == 0) {
					re.setPlayerId(pbi.getPlayer_id());
					re.setLevel(pbi.getLevel());
					re.setVipLevel(pbi.getViplevel());
					re.setSex(pbi.getSex());
					re.setGamepos(pbi.getGameid());
					re.setSign(pbi.getSign());
					re.setUrl(pbi.getHeadUrl());
					
					re.setPigcoin(pbi.getPigCoin());
					re.setTotal(pbi.getExp());
					re.setMoney(pbi.getMoney());
					re.setLiked(pbi.getLiked());
					re.setGold(pbi.getGold());
					
					for (databaseshare.CustomGiftShow cgs : pbi.getGift()) {
						GiftShowInfo temp = new GiftShowInfo();
						temp.setItemTempId(cgs.id);
						temp.setNum(cgs.num);
						re.getShowGiftList().add(temp);
					}
				}
			}
		}
		
		con.send(re);
	}
}
