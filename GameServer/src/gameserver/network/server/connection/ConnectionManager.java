package gameserver.network.server.connection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javolution.util.FastMap;
import net.schst.EventDispatcher.Event;
import net.schst.EventDispatcher.EventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.util.TimeUtil;
import com.google.protobuf.GeneratedMessage;
import commonality.Common;
import commonality.ConveyCondition;

public class ConnectionManager implements EventListener {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
	private final static ConnectionManager instance = new ConnectionManager();
	public static ConnectionManager GetInstance() { return instance; }
	private Map<Long, Connection> m_Connections = new FastMap<Long, Connection>().shared();
	private ArrayList<DestroyConInfo> m_destroyList = new ArrayList<>();
	public static int last_update_connection_num = 0;
	public long lastCheckKeepaliveTime = 0;
	
	private class DestroyConInfo {
		public DestroyConInfo(long playerId, GeneratedMessage messages) {
			this.playerId = playerId;
			this.messages = messages;
			this.cur_time = TimeUtil.GetDateTime();
		}
		public long playerId = 0;
		public GeneratedMessage messages = null;
		public long cur_time;
	}
	
	@Override
	public void handleEvent(Event e) throws Exception {
		checkConnectionAlive(false);
	}
	public void checkConnectionAlive(boolean shutdown) {
		synchronized (this) {
			int WaitCount = 0;
			do {
				WaitCount++;
				if (m_destroyList.size() < 1 ||
						WaitCount > 30)
					break;
				Iterator<DestroyConInfo> iter = m_destroyList.iterator();
				while(iter.hasNext()) {
					DestroyConInfo conInfo = iter.next();
					if (close(conInfo.playerId,conInfo.messages)) {
						logger.debug("connection destory time:{}ms", TimeUtil.GetDateTime() - conInfo.cur_time);
						iter.remove();
					}
				}
				if (m_destroyList.size() > 0) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
					
			}while(shutdown);
			
		}
		long curTime = TimeUtil.GetDateTime();
		if (curTime - lastCheckKeepaliveTime >= 10*1000) {
			Collection<Connection> cons = m_Connections.values();
			for (Connection cn : cons) {
				if (curTime - cn.getLastkeeplive() > 10 * 60 * 1000) {
					logger.error("checkConnectionAlive playerId={} connection timeout {} millssecond!", cn.getPlayerId(), curTime - cn.getLastkeeplive());
					destroyConnect(cn.getPlayerId(), null);
				}
			}
			lastCheckKeepaliveTime = curTime;
		}
	}
	public void destroyConnect(long player_id,GeneratedMessage messages) {
		synchronized (this) {
			Connection connection = GetConnection(player_id);
			if (connection != null && !m_destroyList.contains(player_id)) {
				connection.setDestroy(true);
				m_destroyList.add(new DestroyConInfo(player_id, messages));
			}
		}
	}
	
	public void AddConnection(long player_id,Connection connection)
	{
		logger.info("AddConnection  playerid = " + player_id);
		m_Connections.put(player_id, connection);
	}
	public void DelConnection(long player_id)
	{
		m_Connections.remove(player_id);
	}
	public Connection GetConnection(long player_id)
	{
		if (m_Connections.containsKey(player_id)) {
			Connection con = m_Connections.get(player_id);
			if (con != null && !con.isDestroy())
				return con;
		}
		return null;
	}
	public boolean close(long playerId,GeneratedMessage... messages)
	{
		Connection connection = m_Connections.get(playerId);
		if (connection == null)
			return true;
		if (connection.isWorking())
			return false;
		connection.close(true, Common.BREAKING_MESSAGE_ID, messages);
		return true;
	}
	public void sendMessage(long playerId,byte[] bytes)
	{
		Connection connection = GetConnection(playerId);
		if (connection != null) connection.sendPushMessage(bytes);
	}
	public void broadcastMsg(GeneratedMessage msg) {
		Set<Map.Entry<Long,Connection>> set = m_Connections.entrySet();
		for (Map.Entry<Long, Connection> pair : set)
		{
			if (pair.getValue() != null && pair.getValue().isInit())
			{
				pair.getValue().sendPushMessage(msg);
			}
		}
	}
	public void broadcastMessage(int con,String conditionArgs, byte[] bytes)
	{
		ConveyCondition condition = ConveyCondition.valueOf(con);
		if (condition == ConveyCondition.NONE)
		{
			Set<Map.Entry<Long,Connection>> set = m_Connections.entrySet();
			for (Map.Entry<Long, Connection> pair : set)
			{
				if (pair.getValue() != null && pair.getValue().isInit())
				{
					pair.getValue().sendPushMessage(bytes);
				}
			}
		}
		else if (condition == ConveyCondition.NEED_LEGION)
		{
			int legion = Integer.parseInt(conditionArgs);
			Set<Map.Entry<Long,Connection>> set = m_Connections.entrySet();
			for (Map.Entry<Long, Connection> pair : set)
			{
				Connection connection = pair.getValue();
				try {
					if (connection != null && connection.isInit() && connection.getPlayer().getBelegionId() == legion)
					{
						connection.sendPushMessage(bytes);
					}
				} catch (Exception e) {
				}
			}
		}
		else if (condition == ConveyCondition.NEED_LEVEL)
		{
			Set<Map.Entry<Long,Connection>> set = m_Connections.entrySet();
			for (Map.Entry<Long, Connection> pair : set){
				Connection connection = pair.getValue();
				if (connection != null && connection.isInit() && connection.getPlayer().getLevel()>=10)
					connection.sendPushMessage(bytes);
			}
		}
	}
	public void saveAll() {
		for (Connection con : m_Connections.values()) {
			con.onDisconnect();
		}
	}
	public int Count()
	{
		return m_Connections.size();
	}
	public Map<Long, Connection> getConnections() {
		return m_Connections;
	}
}
