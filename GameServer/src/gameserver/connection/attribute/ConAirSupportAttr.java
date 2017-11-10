package gameserver.connection.attribute;

import gameserver.cache.AirsupportCache;
import gameserver.connection.attribute.info.AirSupportInfo;
import gameserver.connection.attribute.info.BuildInfo;
import gameserver.network.protos.game.ProAirSupport;
import gameserver.network.server.connection.ConnectionAttribute;
import gameserver.utils.GameException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_AirSupport;
import table.base.TableManager;

import com.commons.util.DatabaseInsertUpdateResult;

import commonality.Common;
import commonality.Common.BUILDTYPE;
import commonality.Common.SETNUMBER_TYPE;
import database.DatabaseAirsupport;

public class ConAirSupportAttr extends ConnectionAttribute {
	private static final Logger logger = LoggerFactory
			.getLogger(ConAirSupportAttr.class);

	// <air_tableId, AirSupportInfo>
	private Map<Integer, AirSupportInfo> m_AirSupportArray = new HashMap<Integer, AirSupportInfo>();

	@Override
	protected void Initialize_impl() {
		long pid = m_Con.getPlayerId();
		boolean isLoadDd = false;// 是否需要从DB加载数据
		HashMap<Object, Object> airsMap = AirsupportCache.getAirsupports(pid);
		if (airsMap == null) {
			isLoadDd = true;
			airsMap = new HashMap<Object, Object>();
		}
		m_AirSupportArray.clear();
		if (isLoadDd) {
			List<DatabaseAirsupport> airs = getDb().Select(
					DatabaseAirsupport.class, "player_id = ?", pid);
			for (DatabaseAirsupport air : airs) {
				m_AirSupportArray.put(air.table_id, new AirSupportInfo(m_Con,
						this, air));
				DatabaseAirsupport tempAir = new DatabaseAirsupport();
				tempAir.set(air);
				airsMap.put(air.table_id, tempAir);
			}
		} else {
			Set<Object> keys = airsMap.keySet();
			for (Object key : keys) {
				DatabaseAirsupport air = (DatabaseAirsupport) airsMap.get(key);
				air.sync();
				air.setDatabaseSimple(getDb().getM_Simple());
				m_AirSupportArray.put(air.table_id, new AirSupportInfo(m_Con,
						this, air));
			}
		}
		AirsupportCache.setAirsupport(pid, airsMap);
	}

	private ProAirSupport.Proto_AirSupportInfo GetProtoData(AirSupportInfo info) {
		ProAirSupport.Proto_AirSupportInfo.Builder builder = ProAirSupport.Proto_AirSupportInfo
				.newBuilder();
		builder.setTableId(info.GetTableId());
		builder.setLevel(info.GetLevel());
		builder.setDurable(info.GetDurable());
		builder.setOutFighting(info.IsOutFighting());
		return builder.build();
	}

	public void SendDataArray() {
		ProAirSupport.Msg_G2C_AirSupportInfo.Builder message = ProAirSupport.Msg_G2C_AirSupportInfo
				.newBuilder();
		for (AirSupportInfo info : m_AirSupportArray.values())
			message.addInfo(GetProtoData(info));
		m_Con.sendPushMessage(message.build());
	}

	@Override
	public void CheckData() {
		if (m_NeedUpdate.size() <= 0)
			return;
		try {
			ProAirSupport.Msg_G2C_AirSupportInfo.Builder message = ProAirSupport.Msg_G2C_AirSupportInfo
					.newBuilder();
			for (Long id : m_NeedUpdate) {
				AirSupportInfo info = GetAirSupportByTableId(id.intValue());
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

	public AirSupportInfo GetAirSupportByTableId(int air_table_id) {
		if (m_AirSupportArray.containsKey(air_table_id))
			return m_AirSupportArray.get(air_table_id);
		return null;
	}

	public Collection<AirSupportInfo> GetAirSupportArray() {
		return m_AirSupportArray.values();
	}

	public void SetAirSupportDurable(int air_table_id, int value,
			SETNUMBER_TYPE type) {
		if (m_AirSupportArray.containsKey(air_table_id)) {
			m_AirSupportArray.get(air_table_id).SetDurable(value, type);
			return;
		}
	}

	// 计算当前出战状态的飞机个数
	public int GetOutFightingNumbger() {
		int number = 0;
		for (AirSupportInfo info : m_AirSupportArray.values()) {
			if (info.IsOutFighting())
				++number;
		}
		return number;
	}

	public boolean AirSupportOutFighting(int air_table_id, boolean outFighting)
			throws GameException {
		AirSupportInfo info = GetAirSupportByTableId(air_table_id);
		if (info == null)
			throw new GameException("此空中支援还未购买");
		if (info.IsOutFighting() == outFighting)
			throw new GameException("此空中支援已经是这种状态{}了", outFighting);
		if (outFighting == true) {
			if (GetOutFightingNumbger() >= Common.AIRSUPPORT_OUT_FIGHTING_MAX_NUMBER)
				throw new GameException("已经达到最大出战个数");
			info.OutFighting();
		} else {
			info.TakeBack();
		}
		return true;
	}

	public void AirSupportBuy(MT_Data_AirSupport airData) {
		DatabaseAirsupport data = new DatabaseAirsupport();
		data.table_id = airData.AirID();
		data.durable = airData.MaxDurable();
		data.level = 1;
		data.out_fighting = false;
		data.player_id = m_Con.getPlayerId();
		DatabaseInsertUpdateResult result = getDb().Insert(data);
		data.air_id = result.identity.intValue();
		AirSupportInfo info = new AirSupportInfo(m_Con, this, data);
		m_AirSupportArray.put(data.table_id, info);
		InsertNeedUpdate(airData.AirID());
		addCache(data);
	}

	public void addCache(DatabaseAirsupport data) {
		if (AirsupportCache.isNeedAllIncr(data.player_id))
			AirsupportCache.setAllAirsupport(data.player_id, m_AirSupportArray);
		else
			AirsupportCache.addAirsupport(data.player_id, data);
	}

	public int getFightVal() {
		int totalFihtVal = 0;
		BuildInfo airbuildinfo = m_Con.getBuild()
				.getBuild(BUILDTYPE.AirSupport);
		if (airbuildinfo != null) {
			for (AirSupportInfo info : m_AirSupportArray.values()) {
				MT_Data_AirSupport airSupport = TableManager.GetInstance()
						.TableAirSupport()
						.GetElement(info.GetTableId() + info.GetLevel());
				totalFihtVal += airSupport.Power();
			}
		}
		return totalFihtVal;
	}

	public int getMaxLv() {
		int lv = 0;
		for (AirSupportInfo info : m_AirSupportArray.values()) {
			if (lv > info.GetLevel())
				continue;
			lv = info.GetLevel();
		}
		return lv;
	}

	public int getLv(int tableId) {
		AirSupportInfo info = m_AirSupportArray.get(tableId);
		if (info == null)
			return 0;
		return info.GetLevel();
	}

}
