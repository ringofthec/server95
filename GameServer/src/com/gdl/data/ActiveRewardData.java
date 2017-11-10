package com.gdl.data;
import gameserver.active.ActiveInfo;
import gameserver.active.ActiveService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.network.websock.PlayerConnection;

import database.CustomActiveStateValues;
import database.DatabaseActive_state;
import databaseshare.CustomActiveValues;

public class ActiveRewardData  {
	private static final Logger logger = LoggerFactory.getLogger(ActiveRewardData.class);
	
	private Map<Integer, DatabaseActive_state> m_rewards = new HashMap<Integer, DatabaseActive_state>();
	
	public void init(PlayerData pc) {
		List<DatabaseActive_state> rewad = pc
				.getDB().Select(DatabaseActive_state.class, "player_id = ?", pc.getPlayerId());
		for (DatabaseActive_state air : rewad)
			m_rewards.put(air.active_id, air);
	}
	
	private DatabaseActive_state get(PlayerConnection pc, int activeId) {
		DatabaseActive_state dbinfo = m_rewards.get(activeId);
		if (dbinfo != null) {
			return dbinfo;
		}
		
		DatabaseActive_state dds = new DatabaseActive_state();
		dds.active_id = activeId;
		dds.player_id = pc.getPlayerId();
		dds.reward_idx = 0;
		dds.param1 = 0;
		ActiveInfo ai = ActiveService.getInstance().getActiveInfo(activeId);
		if (ai != null && ai.getData().active_values != null) {
			if (dds.state_values == null)
				dds.state_values = new ArrayList<>();
			else
				dds.state_values.clear();
			for(CustomActiveValues value:ai.getData().active_values) {
				dds.state_values.add(new CustomActiveStateValues(value.value1,value.value2,0));
			}
		}
		dds.id = pc.getPlayer().getDB().Insert(dds).identity;
		m_rewards.put(activeId, dds);
		save(true, dds);
		return dds;
	}
	
	public void setActiveRewardIdx(PlayerConnection pc, int activeId, int idx) {
		DatabaseActive_state info = get(pc, activeId);
		info.reward_idx = idx;
		if (info.state_values != null)
			info.state_values.clear();
		save(true, info);
	}
	
	public void setActiveRewardIdx(PlayerConnection pc, int activeId, int idx, List<CustomActiveValues> values) {
		DatabaseActive_state info = get(pc, activeId);
		info.reward_idx = idx;
		if (values != null) {
			if (info.state_values == null)
				info.state_values = new ArrayList<>();
			else
				info.state_values.clear();
			for(CustomActiveValues value:values) {
				info.state_values.add(new CustomActiveStateValues(value.value1,value.value2,0));
			}
		}
		save(true, info);
	}
	
	public int getActiveRewardIdx(PlayerConnection pc, int activeId) {
		DatabaseActive_state info = get(pc, activeId);
		return info.reward_idx;
	}
	public void incActiveRewardIdx(PlayerConnection pc, int activeId, int inc) {
		DatabaseActive_state info = get(pc, activeId);
		info.reward_idx += inc;
		save(true, info);
	}
	public void sendActive(PlayerConnection pc, int activeId) {
		DatabaseActive_state dbinfo = get(pc, activeId);
		save(true, dbinfo);
	}
	
	protected void save(boolean dummy, DatabaseActive_state active) {
		active.save();
	}
	
	public void incActiveParam1(PlayerConnection pc, int activeId, int inc) {
		DatabaseActive_state info = get(pc,activeId);
		info.param1 += inc;
		save(true, info);
	}
	public void setActiveParam1(PlayerConnection pc, int activeId, int inc) {
		DatabaseActive_state info = get(pc,activeId);
		info.param1 = inc;
		save(true, info);
	}
	public int getActiveParam1(PlayerConnection pc, int activeId) {
		DatabaseActive_state info = get(pc,activeId);
		return info.getParam1();
	}
	public CustomActiveStateValues getActiveStateValues(DatabaseActive_state info,int id) {
		if (info.state_values != null) {
			for(CustomActiveStateValues value:info.state_values) {
				if (value.value1 != id)
					continue;
				return value;
			}
		}
		ActiveInfo ai = ActiveService.getInstance().getActiveInfo(info.active_id);
		if (ai != null) {
			CustomActiveValues activeValue = ai.getActiveValues(id);
			if (activeValue != null) {
				CustomActiveStateValues value = new CustomActiveStateValues(activeValue.value1,activeValue.value2,0);
				info.state_values.add(value);
				save(true, info);
				return value;
			}
		}
		return null;
	}
	public CustomActiveStateValues getActiveStateValues(PlayerConnection pc, int activeId,int id) {
		ActiveInfo ai = ActiveService.getInstance().getActiveInfo(activeId);
		if (ai == null)
			return null;
		CustomActiveValues activeValue = ai.getActiveValues(id);
		if (activeValue == null)
			return null;
		DatabaseActive_state info = get(pc,activeId);
		if (info.state_values == null)
			return null;
		for(CustomActiveStateValues value:info.state_values) {
			if (value.value1 != id)
				continue;
			value.value2 = activeValue.value2;
			return value;
		}
		CustomActiveStateValues value = new CustomActiveStateValues(activeValue.value1,activeValue.value2,0);
		info.state_values.add(value);
		save(true, info);
		return value;
	}
	
	public void incActiveStateValues(PlayerConnection pc, int activeId,int id, int inc) {
		DatabaseActive_state info = get(pc,activeId);
		if (info.state_values == null)
			return;
		CustomActiveStateValues values = getActiveStateValues(info,id);
		if (values == null) 
			return;
		values.value3 += inc;
		save(true, info);
	}
}
