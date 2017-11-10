package gameserver.network.server.connection;

import gameserver.utils.GameException;

import java.util.ArrayList;
import java.util.List;

import com.commons.util.DatabaseUtil;

public abstract class ConnectionAttribute {
	/** 玩家连接对象 */
	protected Connection m_Con = null;
	/** 需要更新的数据数组*/
	protected List<Long> m_NeedUpdate = new ArrayList<Long>();
	
	/**
	 * 添加需要更新的数据ID
	 * @param 
	 */
	public void InsertNeedUpdate(Long id){
		InsertNeedUpdate(id.longValue());
	}
	/**
	 * 添加需要更新的数据ID
	 * @param 
	 */
	public void InsertNeedUpdate(long id){
		if (!m_Con.GetInitializeUpLine())
			return;
		if (!m_NeedUpdate.contains(id))
			m_NeedUpdate.add(id);
	}
	/** 初始数据 */
	public final void Initialize(Connection connection) { 
		m_Con = connection;
		Initialize_impl();
	}
	public DatabaseUtil getDb() {
		return m_Con.getDb();
	}
	public Connection getCon() {
		return m_Con;
	}
	
	protected abstract void Initialize_impl();
	/** 第一次登录会调用此函数  在所有 Attribute.Initialize 之后 
	 * @throws GameException */
	protected void CheckUpline() throws GameException { }
	/** 每次收到消息都会调用此函数 */
	protected void CheckData() { }
}
