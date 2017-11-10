package gameserver.connection.attribute;
import gameserver.active.ActiveInfo;
import gameserver.active.ActiveService;
import gameserver.network.protos.game.ProActive.Msg_G2C_ActiveList;
import gameserver.network.protos.game.ProActive.Proto_Active;
import gameserver.network.server.connection.Connection;
import gameserver.network.server.connection.ConnectionAttribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.util.HttpUtil;
import com.commons.util.TimeUtil;

import database.CustomActiveStateValues;
import database.DatabaseActive_state;
import databaseshare.CustomActiveValues;

public class ConActiveRewardAttr extends ConnectionAttribute {
	private static final Logger logger = LoggerFactory.getLogger(ConActiveRewardAttr.class);
	
	private Map<Integer, DatabaseActive_state> m_rewards = new HashMap<Integer, DatabaseActive_state>();
	
	public void init(Connection con) {
		m_Con = con;
		List<DatabaseActive_state> rewad = getDb().Select(DatabaseActive_state.class, "player_id = ?", m_Con.getPlayerId());
		m_rewards.clear();
		for (DatabaseActive_state air : rewad)
			m_rewards.put(air.active_id, air);
	}
	
	@Override
	protected void Initialize_impl() {
		
	}
	private Proto_Active GetProtoData(DatabaseActive_state info) {
		Proto_Active.Builder builder = Proto_Active.newBuilder();
		builder.setActiveId(info.active_id);
		builder.setClac(info.param1);
		builder.setCurRewardIdx(info.reward_idx);
		builder.setLeftTime(ActiveService.getInstance().getActiveLeftTime(info.active_id));
		return builder.build();
	}
	@Override
	public void CheckData() {
		if (m_NeedUpdate.size() <= 0) 
			return;
		try {
			Msg_G2C_ActiveList.Builder message = Msg_G2C_ActiveList.newBuilder();
			message.setType(2);
			for (Long id : m_NeedUpdate) {
				DatabaseActive_state info = m_rewards.get(id.intValue());
				if (info != null)
					message.addActives(GetProtoData(info));
			}
			m_Con.sendPushMessage(message.build());
		} catch (Exception e) {
			logger.error("CheckData is error : " , e);
		}
		m_NeedUpdate.clear();
	}
	private DatabaseActive_state get(int activeId) {
		DatabaseActive_state dbinfo = m_rewards.get(activeId);
		if (dbinfo != null) {
			return dbinfo;
		}
		
		DatabaseActive_state dds = new DatabaseActive_state();
		dds.active_id = activeId;
		dds.player_id = m_Con.getPlayerId();
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
		dds.id = m_Con.getDb().Insert(dds).identity;
		m_rewards.put(activeId, dds);
		save(true, dds);
		return dds;
	}
	public void setActiveRewardIdx(int activeId, int idx) {
		DatabaseActive_state info = get(activeId);
		info.reward_idx = idx;
		if (info.state_values != null)
			info.state_values.clear();
		save(true, info);
	}
	public void setActiveRewardIdx(int activeId, int idx, List<CustomActiveValues> values) {
		DatabaseActive_state info = get(activeId);
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
	public int getActiveRewardIdx(int activeId) {
		DatabaseActive_state info = get(activeId);
		return info.reward_idx;
	}
	public void incActiveRewardIdx(int activeId, int inc) {
		DatabaseActive_state info = get(activeId);
		info.reward_idx += inc;
		save(true, info);
	}
	public void sendActive(int activeId) {
		DatabaseActive_state dbinfo = get(activeId);
		save(true, dbinfo);
	}
	protected void save(boolean isSync, DatabaseActive_state active) {
		if (isSync == true)
			InsertNeedUpdate(active.active_id);
		m_Con.pushSave(active);
	}
	public void incActiveParam1(int activeId, int inc) {
		DatabaseActive_state info = get(activeId);
		info.param1 += inc;
		save(true, info);
	}
	public void setActiveParam1(int activeId, int inc) {
		DatabaseActive_state info = get(activeId);
		info.param1 = inc;
		save(true, info);
	}
	public int getActiveParam1(int activeId) {
		DatabaseActive_state info = get(activeId);
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
	public CustomActiveStateValues getActiveStateValues(int activeId,int id) {
		ActiveInfo ai = ActiveService.getInstance().getActiveInfo(activeId);
		if (ai == null)
			return null;
		CustomActiveValues activeValue = ai.getActiveValues(id);
		if (activeValue == null)
			return null;
		DatabaseActive_state info = get(activeId);
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
	public void incActiveStateValues(int activeId,int id, int inc) {
		DatabaseActive_state info = get(activeId);
		if (info.state_values == null)
			return;
		CustomActiveStateValues values = getActiveStateValues(info,id);
		if (values == null) 
			return;
		values.value3 += inc;
		save(true, info);
	}
}
