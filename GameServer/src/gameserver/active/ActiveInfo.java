package gameserver.active;

import gameserver.network.message.game.ClientMessageCommodity;
import gameserver.network.server.connection.Connection;
import gameserver.network.server.connection.ConnectionManager;

import java.sql.Timestamp;

import com.commons.util.TimeUtil;

import databaseshare.CustomActiveValues;
import databaseshare.DatabaseActive;

public class ActiveInfo {
	private DatabaseActive m_dbInfo;
	private boolean isActivity = false;
	
	public ActiveInfo(DatabaseActive dbInfo) {
		m_dbInfo = dbInfo;
	}
	
	public void setDBInfo(DatabaseActive dbInfo) {
		m_dbInfo = dbInfo;
	}
	
	public int getActiveId() {
		return m_dbInfo.active_id;
	}
	
	public long getId() {
		return m_dbInfo.id;
	}
	
	public void testActivite() {
		boolean oldActivity = isActivity;
		
		Timestamp cur = TimeUtil.GetTimestamp();
		if (cur.after(TimeUtil.GetTimestamp(m_dbInfo.begin_time)) && cur.before(TimeUtil.GetTimestamp(m_dbInfo.end_time)))
			isActivity = true;
		else
			isActivity = false;
		
		if (m_dbInfo.enable == 0) {
			isActivity = false;
		}
		
		if (oldActivity == false && isActivity == true)
			onBegin();
		
		if (oldActivity == true && isActivity == false)
			onEnd();
	}
	
	public void onBegin() {
		for (Connection con : ConnectionManager.GetInstance().getConnections().values()) {
			con.getReward().setActiveRewardIdx(m_dbInfo.active_id, 0, m_dbInfo.active_values);
			con.getReward().CheckData();
		}
	}
	
	public void onEnd() {
		for (Connection con : ConnectionManager.GetInstance().getConnections().values()) {
			con.getReward().sendActive(m_dbInfo.active_id);
			con.getReward().CheckData();
		}
	}
	
	public boolean isRun() {
		return isActivity;
	}
	
	public int getLeftTime() {
		if (!isActivity)
			return 0;
		
		long left = m_dbInfo.end_time - TimeUtil.GetDateTime();
		if (left < 0)
			return 0;
		
		return (int)(left / 1000);
	}
	
	public DatabaseActive getData() {
		return m_dbInfo;
	}
	
	public CustomActiveValues getActiveValues(int id) {
		if (m_dbInfo.active_values != null) {
			for(CustomActiveValues value:m_dbInfo.active_values) {
				if (value.value1 != id)
					continue;
				return value;
			}
		}
		return null;
	}
}
