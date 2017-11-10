package gameserver.connection.attribute;

import gameserver.cache.TechCache;
import gameserver.connection.attribute.info.TechInfo;
import gameserver.network.protos.game.ProBuild.Msg_G2C_ScienceInfo;
import gameserver.network.protos.game.ProBuild.Proto_ScienceInfo;
import gameserver.network.server.connection.ConnectionAttribute;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_TechInfo;
import table.base.TableManager;

import com.commons.util.DatabaseInsertUpdateResult;

import database.DatabaseTech;

public class ConTechAttr extends ConnectionAttribute {
	private static final Logger logger = LoggerFactory
			.getLogger(ConTechAttr.class);
	private Map<Integer, TechInfo> m_TechArray = new HashMap<Integer, TechInfo>();

	@Override
	protected void Initialize_impl() {
		// 查询某玩家下所有科技，比如：炮党，坦克
		m_TechArray.clear();
		boolean isLoadDb = false;
		HashMap<Object, Object> techs = TechCache.getTechs(this.m_Con
				.getPlayerId());
		if (techs == null) {
			techs = new HashMap<Object, Object>();
			isLoadDb = true;
		}
		if (isLoadDb) {
			List<DatabaseTech> techArray = getDb().Select(DatabaseTech.class,
					"player_id = ?", this.m_Con.getPlayerId());
			for (DatabaseTech tech : techArray) {
				TechInfo info = new TechInfo(m_Con, this, tech);
				m_TechArray.put(tech.table_id, info);
				DatabaseTech tempTech=new DatabaseTech();
				tempTech.set(tech);
				techs.put(tempTech.table_id, tempTech);
			}
		} else {
			Set<Object> keys = techs.keySet();
			for(Object key:keys)
			{
				DatabaseTech tech=(DatabaseTech) techs.get(key);
				tech.sync();
				tech.setDatabaseSimple(getDb().getM_Simple());
				TechInfo info = new TechInfo(m_Con, this, tech);
				m_TechArray.put(tech.table_id, info);
			}

		}
		TechCache.setTechs(m_Con.getPlayerId(), techs);
	}

	private Proto_ScienceInfo GetProtoData(TechInfo info) {
		Proto_ScienceInfo.Builder builder = Proto_ScienceInfo.newBuilder();
		builder.setTableId(info.getTableId());
		builder.setLevel(info.getLevel());
		return builder.build();
	}

	public void SendDataArray() {
		Msg_G2C_ScienceInfo.Builder message = Msg_G2C_ScienceInfo.newBuilder();
		for (Entry<?, TechInfo> pair : m_TechArray.entrySet()) {
			TechInfo info = pair.getValue();
			if (info == null)
				continue;
			message.addInfo(GetProtoData(info));
		}
		m_Con.sendPushMessage(message.build());
	}

	@Override
	public void CheckData() {
		if (m_NeedUpdate.size() <= 0)
			return;
		try {
			Msg_G2C_ScienceInfo.Builder message = Msg_G2C_ScienceInfo
					.newBuilder();
			for (Long id : m_NeedUpdate) {
				TechInfo info = getTech(id.intValue());
				if (info == null)
					continue;
				message.addInfo(GetProtoData(info));
			}
			m_Con.sendPushMessage(message.build());
		} catch (Exception e) {
			logger.error("CheckData is error : ", e);
		}
		m_NeedUpdate.clear();
	}

	public Collection<TechInfo> getTechArray() {
		return m_TechArray.values();
	}

	public TechInfo getTech(int tech_table_id) {
		if (m_TechArray.containsKey(tech_table_id))
			return m_TechArray.get(tech_table_id);
		return null;
	}

	public int getTechLevel(int tech_table_id) {
		TechInfo tech = getTech(tech_table_id);
		if (tech == null)
			return 0;
		return tech.getLevel();
	}
	
	public int getTechBuffid(int tech_table_id) {
		TechInfo tech = getTech(tech_table_id);
		if (tech == null)
			return 0;
		return tech.getBuffId();
	}

	private TechInfo insertTech(int tech_table_id) {
		DatabaseTech data = new DatabaseTech();
		data.table_id = tech_table_id;
		data.player_id = m_Con.getPlayerId();
		data.level = 0;
		DatabaseInsertUpdateResult result = getDb().Insert(data);
		data.tech_id = result.identity.intValue();
		TechInfo info = new TechInfo(m_Con, this, data);
		m_TechArray.put(tech_table_id, info);
		addHeroCache(data);
		return info;
	}

	public void addHeroCache(DatabaseTech data)
	{
		if(TechCache.isNeedAllIncr(data.player_id))
			TechCache.setTechs(data.player_id, m_TechArray);
		else
		   TechCache.addTech(data.player_id, data);
	}
	public void techUpgrade(int tech_table_id) {
		if (m_TechArray.containsKey(tech_table_id)) {
			m_TechArray.get(tech_table_id).levelUp();
			return;
		}
		// 如果没有此可见则创建一个新的
		TechInfo info = insertTech(tech_table_id);
		info.levelUp();
	}

	public boolean techIsMaxLevel(int tech_table_id) {
		if (m_TechArray.containsKey(tech_table_id))
			return m_TechArray.get(tech_table_id).isMaxLevel();
		return false;
	}

	/****
	 * 
	 * @param science_table_id
	 *            科技类型Id 比如:炮弹Id
	 * @return 返回该科技模板数据
	 */
	public MT_Data_TechInfo getTechData(int tech_table_id) {
		TechInfo tech = getTech(tech_table_id);
		if (tech != null)
			return tech.getData();
		return TableManager.GetInstance().TableTechInfo()
				.GetElement(tech_table_id);
	}

	public int getFightVal() {
		int totalLv = 0;
		for (TechInfo info : m_TechArray.values())
			totalLv += info.getLevel();
		return totalLv * 100;
	}
}
