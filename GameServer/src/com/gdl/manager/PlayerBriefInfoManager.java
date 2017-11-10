package com.gdl.manager;

import gameserver.utils.DbMgr;

import com.commons.network.websock.PlayerConManager;
import com.commons.network.websock.PlayerConnection;
import com.gdl.data.PlayerBriefInfo;
import com.gdl.data.PlayerMoneyStatisInfo;
import com.gdl.game.GameInstanceMrg;

import databaseshare.DatabaseMoney_static;
import databaseshare.DatabasePlayer_brief_info;

public class PlayerBriefInfoManager {
	private static PlayerBriefInfoManager m_int = new PlayerBriefInfoManager();
	private PlayerBriefInfoManager() {}
	public static PlayerBriefInfoManager getInstance() {return m_int;}
	
	public PlayerBriefInfo getPlayerBriefInfo(long player_id) {
		PlayerConnection pc = PlayerConManager.getInstance().getCon(player_id);
		if (pc != null) {
			int gameid = GameInstanceMrg.getGameId( pc.getPlayer().getGameUID() );
			PlayerBriefInfo pi = new PlayerBriefInfo(pc.getPlayer(), pc.getPlayer().getBriefInfo(), gameid, true);
			return pi;
		} else {
			DatabasePlayer_brief_info pvp = DbMgr.getInstance().getShareDB()
					.SelectOne(DatabasePlayer_brief_info.class, "player_id=?", player_id);
			if (pvp != null) {
				return new PlayerBriefInfo(null, pvp, -1, false);
			}
		}
		
		return null;
	}
	
	public PlayerMoneyStatisInfo getPlayerMoneyStatisInfo(long player_id) {
		PlayerConnection pc = PlayerConManager.getInstance().getCon(player_id);
		if (pc != null) {
			PlayerMoneyStatisInfo pi = new PlayerMoneyStatisInfo(pc.getPlayer().getMoneyStatic());
			return pi;
		} else {
			DatabaseMoney_static pvp = DbMgr.getInstance().getShareDB()
					.SelectOne(DatabaseMoney_static.class, "player_id=?", player_id);
			if (pvp != null) {
				return new PlayerMoneyStatisInfo(pvp);
			}
		}
		
		return null;
	}
}
