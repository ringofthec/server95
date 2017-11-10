package com.gdl.data;

import gameserver.jsonprotocol.GDL_G2C_GetBattleRecordRe;
import databaseshare.CustomLong1Info;
import databaseshare.CustomMillStone;
import databaseshare.DatabaseMoney_static;

public class PlayerMoneyStatisInfo {
	private DatabaseMoney_static m_info;

	public PlayerMoneyStatisInfo(DatabaseMoney_static info) {
		m_info = info;
	}
	
	public long getPlayer_id() {
		return m_info.player_id;
	}
	
	public GDL_G2C_GetBattleRecordRe genBattleRecord(int func) {
		GDL_G2C_GetBattleRecordRe re = new GDL_G2C_GetBattleRecordRe();
		re.setFunc(func);
		
		re.setPlayerId(m_info.player_id);
		re.setMax_day(m_info.max_day);
		re.setTotal(m_info.total);
		
		for (CustomLong1Info r : m_info.days7) {
			re.getDays7().add(r.val1);
		}
		
		re.setFruits_slot_total(m_info.fruits_slot_total);
		re.setFruits_slot_max(m_info.fruits_slot_max);
		re.setFruits_slot_count(m_info.fruits_slot_count);
		re.setFruits_slot_rose_top3_count(m_info.fruits_slot_rose_top3_count);
		re.setFruits_slot_pool_total(m_info.fruits_slot_pool_total);
		re.setFruits_slot_pool_count(m_info.fruits_slot_pool_count);
		
		re.setGold_slot_total(m_info.gold_slot_total);
		re.setGold_slot_max(m_info.gold_slot_max);
		re.setGold_slot_count(m_info.gold_slot_count);
		re.setGold_slot_gold(m_info.gold_slot_gold);
		re.setGold_slot_pool_total(m_info.gold_slot_pool_total);
		re.setGold_slot_pool_count(m_info.gold_slot_pool_count);
		
		re.setFish_total(m_info.fish_total);
		re.setFish_max(m_info.fish_max);
		re.setFish_battle_kingfish(m_info.fish_battle_kingfish);
		re.setFish_battle_doomfish(m_info.fish_battle_doomfish);
		re.setFish_task_count(m_info.fish_task_count);
		re.setFish_catch_fish_total(m_info.fish_catch_fish_total);
		
		for (CustomMillStone cc : m_info.millstone) {
			re.getMillstone().add(cc.getInfo());
		}
		return re;
	}
}
