package com.commons.network.websock.handler;

import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.GDL_C2G_GetRank;
import gameserver.jsonprotocol.GDL_C2G_GetRankReward;
import gameserver.jsonprotocol.GDL_G2C_GetRankRe;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.MT_Data_GRankReward;
import table.base.TableManager;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConnection;
import com.gdl.manager.RankManager;

public class RankHandler {
	private final static RankHandler instance = new RankHandler();
	public static RankHandler getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(RankHandler.class);
	
	public void init() {
		HandlerManager.getInstance().pushNornalHandler(RankHandler.class, this, 
				"OnGetRank", new GDL_C2G_GetRank());
		HandlerManager.getInstance().pushNornalHandler(RankHandler.class, this, 
				"OnGetRankReward", new GDL_C2G_GetRankReward());
	}
	
	public void OnGetRankReward(PlayerConnection con, GDL_C2G_GetRankReward message) {
		int i = message.getType();
		if (i < 0 || i > 2)
			return ;
		
		int limit_id = Consts.getIsMoneyRankRewardGet() + i;
		if (con.getPlayer().getItemData().getItemCountByTempId(limit_id) == 1)
			return ;
		
		int myRank = RankManager.getInstance().getMyRank(i, con.getPlayerId());
		MT_Data_GRankReward rec_c = TableManager.GetInstance().TableGRankReward().GetElement(myRank);
		if (rec_c == null)
			return ;

		List<Int2> rewards;
		if (i == 0)
			rewards = rec_c.money_reward();
		else if (i == 1)
			rewards = rec_c.level_reward();
		else
			rewards = rec_c.like_reward();

		con.getPlayer().getItemData().addItem(con, limit_id, 1, Consts.getItemEventRankReward());
		for (Int2 re : rewards) {
			con.getPlayer().getItemData().addItem(con, re.field1(), re.field2(), Consts.getItemEventRankReward());
			
			ItemHandler.showAddItem(con, re.field1(), re.field2());
		}
		
		if (i == 0)
			ChatHandler.newbanner(72 + myRank, con.getPlayer().getName(), con.getPlayerId());
		else if ( i == 1)
			ChatHandler.newbanner(75 + myRank, con.getPlayer().getName(), con.getPlayerId());
		else
			ChatHandler.newbanner(78 + myRank, con.getPlayer().getName(), con.getPlayerId());
	}
	
	public void OnGetRank(PlayerConnection con, GDL_C2G_GetRank message) {
		GDL_G2C_GetRankRe re = RankManager.getInstance().genRank(con.getPlayerId(), message.getType(), message.getMode());
		con.send(re);
	}
}
