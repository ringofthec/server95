package gameserver.active;

import gameserver.http.HttpProcessManager;
import gameserver.network.protos.game.ProActive.Msg_S2G_FlushActive;
import gameserver.network.server.connection.Connection;
import gameserver.network.server.connection.ConnectionManager;
import gameserver.utils.DbMgr;

import java.util.List;

import javolution.util.FastMap;
import net.schst.EventDispatcher.Event;
import net.schst.EventDispatcher.EventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.util.TimeUtil;

import table.MT_Data_Active;
import table.base.TableManager;
import databaseshare.DatabaseActive;

public class ActiveService implements EventListener{
	private final static Logger logger = LoggerFactory.getLogger(ActiveService.class);
	private static FastMap<Integer, ActiveInfo> m_infos = new FastMap<Integer, ActiveInfo>();
	
	private final static ActiveService instance = new ActiveService();
	public static ActiveService getInstance() {
		return instance;
	}
	
	public void init() {
		HttpProcessManager.getInstance().regMsgProcess(Msg_S2G_FlushActive.class,this, "onFlushActive");
    }
	
	public void onFlushActive(Msg_S2G_FlushActive message) throws Exception {
		flush();
	}

	@Override
	public void handleEvent(Event e) throws Exception {
		for (ActiveInfo info : m_infos.values())
			info.testActivite();
	}
	
	public void flush() {
		List<DatabaseActive> actives = DbMgr.getInstance().getShareDB().
				Select(DatabaseActive.class, "end_time>?",TimeUtil.GetDateString());
		
		for (DatabaseActive ac : actives) {
			MT_Data_Active active = TableManager.GetInstance().TableActive().GetElement(ac.active_id);
			if (active == null) {
				logger.error("ActiveService error-------will insert not exist active, id={}", ac.active_id);
				continue;
			}
			
			ActiveInfo ai = m_infos.get(ac.active_id);
			if (ai != null) {
				ai.setDBInfo(ac);
			} else {
				ai =  new ActiveInfo(ac);
				m_infos.put(ac.active_id,ai);
			}
			
			ai.testActivite();
			
		}
		sendAllActiveToAllClient();
	}
	
	public boolean isActiveRun(int activeId) {
		if (!m_infos.containsKey(activeId))
			return false;
		
		return m_infos.get(activeId).isRun();
	}
	
	public void sendAllActive(Connection con) {
		for (ActiveInfo info : m_infos.values()) {
			if (info.isRun()) {
				con.getReward().sendActive(info.getActiveId());
			}
		}
	}
	
	public int getActiveLeftTime(int activeId) {
		if (!m_infos.containsKey(activeId))
			return 0;
		
		return m_infos.get(activeId).getLeftTime();
	}
	
	public ActiveInfo getActiveInfo(int activeId) {
		return m_infos.get(activeId);
	}
	
	public void sendAllActiveToAllClient() {
		for (Connection con : ConnectionManager.GetInstance().getConnections().values()) {
			sendAllActive(con);
			con.getReward().CheckData();
		}
	}
}
