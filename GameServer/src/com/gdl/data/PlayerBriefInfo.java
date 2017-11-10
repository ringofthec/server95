package com.gdl.data;

import gameserver.jsonprotocol.Consts;

import java.util.List;

import databaseshare.CustomGiftShow;
import databaseshare.DatabasePlayer_brief_info;

public class PlayerBriefInfo {
	private DatabasePlayer_brief_info m_info;
	private int m_gameid;
	private boolean m_online;
	
	public long getPlayer_id() {
		return m_info.player_id;
	}
	public String getHeadUrl() {
		return m_info.head_url;
	}
	public String getName() {
		return m_info.name;
	}
	public int getLevel() {
		return m_info.level;
	}
	public int getViplevel() {
		return m_info.viplevel;
	}
	public long getMoney() {
		return m_info.money;
	}
	public long getGold() {
		return m_info.gold;
	}
	public int getSex() {
		return m_info.sex;
	}
	public String getSign() {
		return m_info.sign;
	}
	public long getLiked() {
		return m_info.liked;
	}
	public long getExp() {
		return m_info.exp;
	}
	public long getPigCoin() {
		return 0L;
	}
	public int getFristGiftId() {
		if (m_info.getShowgift().isEmpty())
			return 0;
		
		return m_info.getShowgift().get(0).id;
	}
	public List<CustomGiftShow> getGift() {
		return m_info.getShowgift();
	}
	public int getGameid() {
		return m_gameid;
	}
	public boolean isOnline() {
		return m_online;
	}
	
	public PlayerBriefInfo(PlayerData pdata, DatabasePlayer_brief_info info, int gameid, boolean online) {
		m_info = info;
		if (pdata != null) {
			m_info.money = pdata.getItemCountByTempId(Consts.getCOIN_ID());
			m_info.gold = pdata.getItemCountByTempId(Consts.getGOLD_ID());
		}
		m_gameid = gameid;
		m_online = online;
	}
}
