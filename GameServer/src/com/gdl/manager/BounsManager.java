package com.gdl.manager;

import java.util.ArrayList;

import javolution.util.FastMap;

import com.commons.util.RandomUtil;
import com.gdl.data.PlayerData;

public class BounsManager {
	private static BounsManager m_int = new BounsManager();
	private BounsManager() {}
	public static BounsManager getInstance() {return m_int;}
	
	public static class BounsInfo {
		int pre_money;
		int count;
		public String name;
		public int viplvl;
		public ArrayList<Long> reci_players = new ArrayList<Long>();
		
		BounsInfo(int pre, int c, String name, int viplvl) {
			this.pre_money = pre;
			this.count = c;
			this.name = name;
			this.viplvl = viplvl;
		}
	}
	
	FastMap<Integer, BounsInfo> m_itemInfos = new FastMap<Integer,BounsInfo>();
	
	public int newBouns(long total_money, int count, PlayerData p) {
		int bouns_id = RandomUtil.RangeRandom(1, 9999999);
		
		m_itemInfos.put(bouns_id, new BounsInfo((int)(total_money / count) , count, p.getName(), p.getVipLvl()));
		
		return bouns_id;
	}
	
	public int getMoney(int bounsid) {
		if (!m_itemInfos.containsKey(bounsid))
			return -1;
		
		return m_itemInfos.get(bounsid).pre_money;
	}
	
	public BounsInfo getInfo(int id) {
		return m_itemInfos.get(id);
	}
	
	public boolean testAndRecord(long player_id, int bounsid) {
		if (isPlayerInRecord(player_id, bounsid))
			return false;
		
		recordPlayer(player_id, bounsid);
		return true;
	}
	
	// 玩家是否领取过该批次红包了？
	private boolean isPlayerInRecord(long player_id, int bounsid) {
		if (!m_itemInfos.containsKey(bounsid))
			return true;
		
		ArrayList<Long> players = m_itemInfos.get(bounsid).reci_players;
		for (Long id : players) {
			if (id.longValue() == player_id)
				return true;
		}
		
		return false;
	}
	
	private void recordPlayer(long player_id, int bounsid) {
		if (!m_itemInfos.containsKey(bounsid))
			return ;
		
		m_itemInfos.get(bounsid).reci_players.add(player_id);
	}
}
