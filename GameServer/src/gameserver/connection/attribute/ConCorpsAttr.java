package gameserver.connection.attribute;

import gameserver.cache.CorpsCache;
import gameserver.connection.attribute.info.BuildInfo;
import gameserver.connection.attribute.info.CorpsInfo;
import gameserver.fighting.FightCorpsInfo;
import gameserver.network.protos.game.ProBuild;
import gameserver.network.server.connection.ConnectionAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import table.MT_Data_Corps;
import table.MT_TableEnum;
import table.base.TableManager;

import com.commons.util.DatabaseInsertUpdateResult;

import commonality.Common;
import commonality.Common.SETNUMBER_TYPE;
import database.DatabaseCorps;

public class ConCorpsAttr extends ConnectionAttribute {
	// <corp_table_tableId, CorpsInfo>

	private Map<Integer, CorpsInfo> m_CorpsArray = new HashMap<Integer, CorpsInfo>();

	@Override
	protected void Initialize_impl() {
		boolean isLoadDb = false;
		m_CorpsArray.clear();
		HashMap<Object, Object> corpss = CorpsCache.getAllCorps(m_Con
				.getPlayerId());
		if (corpss == null) {
			isLoadDb = true;
			corpss = new HashMap<Object, Object>();
		}
		if (isLoadDb) {
			List<DatabaseCorps> corpsArray = getDb().Select(
					DatabaseCorps.class, "player_id = ?", m_Con.getPlayerId());
			for (DatabaseCorps corps : corpsArray) {
				m_CorpsArray.put(corps.table_id, new CorpsInfo(m_Con, this,
						corps));
				DatabaseCorps temp = new DatabaseCorps();
				temp.set(corps);
				corpss.put(temp.corps_id, temp);
			}
		} else {
			Set<Object> keys = corpss.keySet();
			for (Object key : keys) {
				DatabaseCorps corps = (DatabaseCorps) corpss.get(key);
				corps.sync();
				corps.setDatabaseSimple(getDb().getM_Simple());
				m_CorpsArray.put(corps.table_id, new CorpsInfo(m_Con, this,
						corps));
			}
		}
		CorpsCache.setAllCorps(m_Con.getPlayerId(), corpss);
	}

	private ProBuild.Proto_CorpsInfo GetProtoData(CorpsInfo info) {
		ProBuild.Proto_CorpsInfo.Builder builder = ProBuild.Proto_CorpsInfo
				.newBuilder();
		builder.setTableId(info.getTableId());
		builder.setLevel(info.getLevel());
		builder.setNumber(info.getNumber());
		return builder.build();
	}

	public void SendDataArray() {
		ProBuild.Msg_G2C_CorpsInfo.Builder message = ProBuild.Msg_G2C_CorpsInfo
				.newBuilder();
		for (CorpsInfo info : m_CorpsArray.values())
			message.addInfo(GetProtoData(info));
		m_Con.sendPushMessage(message.build());
	}

	@Override
	public void CheckData() {
		if (m_NeedUpdate.isEmpty())
			return;
		ProBuild.Msg_G2C_CorpsInfo.Builder message = ProBuild.Msg_G2C_CorpsInfo
				.newBuilder();
		for (Long id : m_NeedUpdate) {
			CorpsInfo info = getCorpsByTableId(id.intValue());
			if (info == null)
				continue;
			message.addInfo(GetProtoData(info));
		}
		m_Con.sendPushMessage(message.build());
		m_NeedUpdate.clear();
	}

	public Collection<CorpsInfo> getCorpsArray() {
		return m_CorpsArray.values();
	}

	public CorpsInfo getCorpsByTableId(int corps_table_id) {
		return m_CorpsArray.get(corps_table_id);
	}

	private CorpsInfo insertCorps(int corps_table_id) {
		DatabaseCorps data = new DatabaseCorps();
		data.table_id = corps_table_id;
		data.number = 0;
		data.player_id = m_Con.getPlayerId();
		data.level = Common.CORPS_START_LEVEL;
		DatabaseInsertUpdateResult result = getDb().Insert(data);
		data.corps_id = result.identity.intValue();
		CorpsInfo info = new CorpsInfo(m_Con, this, data);
		m_CorpsArray.put(corps_table_id, info);
		addCache(data);
		return info;
	}

	public void addCache(DatabaseCorps data)
	{
		if ( CorpsCache.isNeedAllIncr(data.player_id)) 
			CorpsCache.setAllCorps(data.player_id, m_CorpsArray);
		else
			CorpsCache.addCorps(data.player_id, data);
	}
	
	public int getCorpsLevel(int corps_table_id) {
		CorpsInfo info = getCorpsByTableId(corps_table_id);
		if (info == null)
			return Common.CORPS_START_LEVEL;
		return info.getLevel();
	}

	public void corpsLevelUp(int corps_table_id) {
		if (m_CorpsArray.containsKey(corps_table_id)) {
			m_CorpsArray.get(corps_table_id).levelUp();
			return;
		}
		// 如果没有此兵种则创建一个新的 玩家可能先升级 没建造过兵种
		CorpsInfo info = insertCorps(corps_table_id);
		info.levelUp();
	}

	public MT_Data_Corps getCorpsDataByTableId(int corps_table_id) {
		CorpsInfo info = getCorpsByTableId(corps_table_id);
		if (info != null)
			return info.getData();
		// 没有数组 标识自己还没有拥有此种兵 肯定是0级
		return TableManager.GetInstance().TableCorps()
				.GetElement(corps_table_id);
	}

	public void setCorpsArrayNumber(Map<Integer, Integer> numbers,
			SETNUMBER_TYPE type) {
		for (Map.Entry<Integer, Integer> entry : numbers.entrySet())
			setCorpsNumber(entry.getKey(), entry.getValue(), type);
	}

	public void setCorpsNumber(int corps_table_id, int value,
			SETNUMBER_TYPE type) {
		if (m_CorpsArray.containsKey(corps_table_id)) {
			m_CorpsArray.get(corps_table_id).setNumber(value, type);
			return;
		}
		CorpsInfo info = insertCorps(corps_table_id);
		info.setNumber(value, type);
	}

	public int getAllPopulation() {
		int num = 0;
		Collection<CorpsInfo> corpsArray = m_CorpsArray.values();
		for (CorpsInfo corps : corpsArray)
			num += corps.getPopulation();
		return num;
	}

	public int GetAllPopulation(int corpsId, int number) {
		int corpsLevel = getCorpsLevel(corpsId);
		return GetAllPopulation(corpsId, corpsLevel, number);
	}

	public int GetAllPopulation(int corpsId, int level, int number) {
		MT_Data_Corps corps = TableManager.GetInstance().TableCorps()
				.GetElement(corpsId + level);
		return corps.Population() * number;
	}

	public Map<Integer, FightCorpsInfo> GetFightCorpsInfo() {
		Map<Integer, FightCorpsInfo> infos = new HashMap<Integer, FightCorpsInfo>();
		Collection<CorpsInfo> corpsArray = m_CorpsArray.values();
		for (CorpsInfo corps : corpsArray)
			infos.put(corps.getTableId(), new FightCorpsInfo(
					corps.getTableId(), corps.getNumber(), corps.getLevel()));
		return infos;
	}

	public int getFightCropsVal() {
		int total = 0;
		try {
			int playerlevel = m_Con.getPlayer().getLevel();
			List<Integer> factoryids = new ArrayList<Integer>();
			for (BuildInfo build : m_Con.getBuild().getBuildArray()) {
				if (!build.isFactory())
					continue;

				if (factoryids.contains(build.getDataBuilding().index()))
					continue;

				factoryids.add(build.getDataBuilding().index());

				List<Integer> playerlevelss = (List<Integer>) build
						.getDataBase().GetDataByString(MT_TableEnum.Corps);
				for (int val : playerlevelss) {
					MT_Data_Corps data = TableManager.GetInstance()
							.TableCorps().GetElement(val);
					if (data.UpgradePlayerLv() > playerlevel)
						continue;

					int corpsLv = m_Con.getCorps().getCorpsLevel(val);
					MT_Data_Corps corpsData = TableManager.GetInstance()
							.TableCorps().GetElement(val + corpsLv);
					total += corpsData.Power();
				}
			}
		} catch (Exception e) {
		}
		return total;
	}
}
