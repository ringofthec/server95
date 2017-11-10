package gameserver.connection.attribute;

import gameserver.connection.attribute.info.ItemInfo;
import gameserver.connection.attribute.info.TechInfo;
import gameserver.network.protos.game.ProPassiveBuff;
import gameserver.network.server.connection.ConnectionAttribute;
import gameserver.utils.DbMgr;
import gameserver.utils.PassiveGain;
import gameserver.utils.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.EquipAttr;
import table.Int2;
import table.Int3;
import table.MT_Data_EquipAttribute;
import table.MT_Data_IntensifyConfig;
import table.MT_Data_Item;
import table.MT_Data_PassiveBuff;
import table.MT_Data_Rank;
import table.MT_Data_TechInfo;
import table.MT_Data_Vip;
import table.MT_Data_legion;
import table.base.TableManager;
import commonality.Common;
import database.DatabaseItem;
import database.DatabasePlayer;
import database.DatabaseTech;
import databaseshare.DatabaseLegion;
import databaseshare.DatabasePvp_match;

public class ConPassiveBuffAttr extends ConnectionAttribute {
	private static final Logger logger = LoggerFactory.getLogger(ConPassiveBuffAttr.class);
	//BUFF列表
	private Map<Integer, MT_Data_PassiveBuff> m_PassiveBuffs = new FastMap<Integer, MT_Data_PassiveBuff>();
	private Map<Integer, MT_Data_PassiveBuff> m_TargetBuffs = new FastMap<Integer, MT_Data_PassiveBuff>();
	private Map<Integer, PassiveGain> m_passiveGainCache = new FastMap<Integer, PassiveGain>();
	private Map<Integer, PassiveGain> m_targetGainCache = new FastMap<Integer, PassiveGain>();
	
	@Override
	protected void Initialize_impl() {
	}
	
	public Map<Integer, MT_Data_PassiveBuff> GetPassiveBuffs() {
		return m_PassiveBuffs;
	}
	
	public Map<Integer, PassiveGain> getPassiveGain() {
		return m_passiveGainCache;
	}
	
	@Override
	public void CheckUpline() {
		m_Con.getBuffs().CheckPlayerEquip();
		m_Con.getBuffs().CheckPlayerVip();
		m_Con.getBuffs().CheckPlayerRank();
		m_Con.getBuffs().CheckLegionBuf();
		m_Con.getBuffs().CheckTechBuf();
		flushAttr(false);
	}

	public void SendPlayerPassiveBuffs() {
		ProPassiveBuff.Msg_G2C_PassiveBuff.Builder message = ProPassiveBuff.Msg_G2C_PassiveBuff.newBuilder();
		for (Integer buffTableId : m_PassiveBuffs.keySet())
			message.addTables(buffTableId);
		message.setType(ProPassiveBuff.Proto_PassiveBuffType.LIST);
		
		for (Entry<Integer, PassiveGain> pair : m_passiveGainCache.entrySet()) {
			message.addBuffType(pair.getKey());
			ProPassiveBuff.Msg_G2C_PassiveBuff.Msg_PassiveGain.Builder bu = ProPassiveBuff.Msg_G2C_PassiveBuff.Msg_PassiveGain.newBuilder();
			bu.setValue(pair.getValue().value).setValueF(pair.getValue().value_f);
			message.addBuffEffect(bu.build());
		}
		
		m_Con.sendReceiptMessage(message.build());
	}
	
	public void AddPassiveBuff(int tableId, int channel) {
		AddPassiveBuff(tableId, true, channel);
	}
	
	// 添加Buff
	public void AddPassiveBuff(int tableId, boolean immn, int channel) {
		if (!TableManager.GetInstance().TablePassiveBuff().Contains(tableId)) {
			logger.error("AddPassiveBuff, not exist buffid:{} channel:{}", tableId, channel);
			return ;
		}
		
		MT_Data_PassiveBuff data = TableManager.GetInstance().TablePassiveBuff().GetElement(tableId);
		if (data == null)
			return;

		if (m_PassiveBuffs.containsKey(tableId) == false) {
			m_PassiveBuffs.put(tableId, data);
			updatePassiveBuffValue(data, true);

			if (immn)
				flushAttr(true);
		}
	}
	
	// 删除Buff
	public void DelPassiveBuff(int tableId, int channel) {
		if (!TableManager.GetInstance().TablePassiveBuff().Contains(tableId)) {
			logger.error("DelPassiveBuff, not exist buffid:{} channel:{}", tableId, channel);
			return ;
		}
		
		MT_Data_PassiveBuff data = TableManager.GetInstance().TablePassiveBuff().GetElement(tableId);
		if (data == null)
			return;
		
		if (m_PassiveBuffs.containsKey(tableId) == true) {
			m_PassiveBuffs.remove(tableId);
			updatePassiveBuffValue(data, false);
			flushAttr(true);
		}
	}
	
	public void flushAttr(boolean sync) {
		m_Con.getCommon().UpdatePeople(sync);
		m_Con.getCommon().UpdatePvpNumber(sync);
		m_Con.getCommon().UpdateMaxCp(sync);
		m_Con.getCommon().UpdateBagCount(sync);
		m_Con.getCommon().UpdateQueue(sync);
	}
	
	// 添加装备 buf
	public void CheckPlayerEquip() {
		try {
			Collection<ItemInfo> list = m_Con.getItem().getItemArray();
			for (ItemInfo item : list) {
				if (item.getOwner() > 0) {
					MT_Data_EquipAttribute attr = TableManager.GetInstance().TableEquipAttribute().GetElement(item.getData().Attr());
					for (EquipAttr equipAttr : attr.Arrays()) {
						if (equipAttr.type() == 2) {
							if (item.getData().ItemPart() == Common.EQUIPPART.Accessories.Number() && item.getLvl() > 0)
								continue;
							AddPassiveBuff(equipAttr.num(), false, 1);
						}
					}
				}

				if (item.getOwner() != 0 && item.getLvl() > 0) {
					MT_Data_IntensifyConfig ic = TableManager.GetInstance().TableIntensifyConfig().GetElement(item.getTableId());
					if (ic != null) {
						List<Int2> buffconfig = (List<Int2>)ic.GetDataByString("Intensify_" + item.getLvl());
						for (Int2 value : buffconfig) {
							if (value.field1() == 3) {
								m_Con.getBuffs().AddPassiveBuff(value.field2(), 2);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("CheckPlayerEquip error, ", e);
		}
	}
	
	public void CheckTechBuf() {
		ConTechAttr tech = m_Con.getTech();
		for (TechInfo te : tech.getTechArray()) {
			AddPassiveBuff(te.getBuffId(), false, 3);
		}
	}
	
	// 添加排行榜 buf
	public void CheckPlayerRank() {
		int rank = Util.getRank(m_Con.getPlayer().getFeat());
		MT_Data_Rank rankconf = TableManager.GetInstance().TableRank().GetElement(rank);
		if (rankconf == null)
			return ;
		
		if (rankconf.buffid().isEmpty())
			return ;
		
		for (int buffid : rankconf.buffid())
			AddPassiveBuff(buffid, false, 4);
	}
	
	// 添加vip buf
	public void CheckPlayerVip() {
		if (m_Con.getPlayer().IsVipValid() == false)
			return;
		
		MT_Data_Vip vipData = TableManager.GetInstance().TableVip()
				.GetElement(m_Con.getPlayer().getVipLevel());
		if (vipData == null)
			return;
		
		for (int i=0; i<vipData.PassiveBuffs().size(); ++i) {
			AddPassiveBuff(vipData.PassiveBuffs().get(i), false, 5);
		}
	}
	
	// 添加军团 buf
	private void CheckLegionBuf() {
		int legionId = m_Con.getPlayer().getBelegionId();
		if (legionId == 0) 
			return;
		DatabaseLegion legion = DbMgr.getInstance().getShareDB().SelectOne(DatabaseLegion.class, "legion_id = ? ", legionId);
		MT_Data_legion mt_Data_legion = TableManager.GetInstance().Tablelegion().GetElement(legion.level);
		ArrayList<Integer> bufIds = mt_Data_legion.PassiveBuff();
		for (Integer bufId : bufIds) 
			AddPassiveBuff(bufId, false, 6);
	}
	
	// 刷新某种类型的buff增益效果值
	public void updatePassiveBuffValue(MT_Data_PassiveBuff data, boolean add) {
		if (data.type() != Common.PASSIVEBUFF_OBJECT_TYPE.SELF.Number())
			return ;
		
		try {
			Int3 buf = data.buffs();
			int buffType = buf.field1();
			PassiveGain gain = null;
			if (m_passiveGainCache.containsKey(buffType))
				gain = m_passiveGainCache.get(buffType);
			else
				gain = new PassiveGain();

			if (buf.field2() == 0)
				gain.value += (add ? buf.field3() : -buf.field3());
			else if (buf.field2() == 1)
				gain.value_f += (float)(add ? buf.field3() : -buf.field3()) / 10000f;

			m_passiveGainCache.put(buffType, gain);
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}
	public PassiveGain GetPassiveBuffValue(int type) {
		PassiveGain gain = PassiveGain.default_gain;
		if (m_passiveGainCache.containsKey(type))
			gain = m_passiveGainCache.get(type);
		return gain;
	}
	public int getValueByIncPassiveBuff(Common.PASSIVEBUFF_TYPE valType, int orgVal) {
		PassiveGain gain = GetPassiveBuffValue(valType.Number());
		return (int)((float)(orgVal + gain.value) * (1 + gain.value_f));
	}
	public int getValueByDecPassiveBuff(Common.PASSIVEBUFF_TYPE valType, int orgVal) {
		PassiveGain gain = GetPassiveBuffValue(valType.Number());
		return (int)((float)(orgVal - gain.value) * (1 - gain.value_f));
	}
	

	
	
	
	// 获得目标玩家的被动技能列表
	public Map<Integer, MT_Data_PassiveBuff> GetTargetPassiveBuffs(long playerId) {
		m_TargetBuffs.clear();
		m_targetGainCache.clear();
		CheckPlayerEquip(playerId);
		CheckPlayerVip(playerId);
		CheckPlayerRank(playerId);
		CheckPlayerLegion(playerId);
		CheckPlayerTech(playerId);
		return m_TargetBuffs;
	}

	private void CheckPlayerLegion(long playerId) {
		Integer belong_legion = ConPlayerAttr.getPlayerLegionIdById(playerId);
		if (belong_legion == null || belong_legion.intValue() == 0) 
			return;
		
		Integer level = (Integer)DbMgr.getInstance().getShareDB()
				.selectOneField(DatabaseLegion.class, "legion_id = ?", "level", Integer.class, belong_legion);
		if (level == null)
			return ;
		MT_Data_legion mT_Data_legion = TableManager.GetInstance().Tablelegion().GetElement(level);
		if (mT_Data_legion == null)
			return ;
		
		for (Integer bufId : mT_Data_legion.PassiveBuff())
			AddTargetBuff(bufId, 7);
	}

	public void AddTargetBuff(int tableId, int channel) {
		if (!TableManager.GetInstance().TablePassiveBuff().Contains(tableId)) {
			logger.error("AddTargetBuff, not exist buffid:{} channel:{}", tableId, channel);
			return ;
		}
		
		MT_Data_PassiveBuff data = TableManager.GetInstance().TablePassiveBuff().GetElement(tableId);
		if (data == null)
			return;

		if (m_TargetBuffs.containsKey(tableId) == false) {
			m_TargetBuffs.put(tableId, data);
			updateTargetBuffValue(data, true);
		}
	}
	
	private void CheckPlayerTech(long playerId) {
		List<DatabaseTech> techs = DbMgr.getInstance().getDbByPlayerId(playerId)
				.Select(DatabaseTech.class, "player_id=?", playerId);
		
		for (DatabaseTech tech : techs) {
			MT_Data_TechInfo techinfo = TableManager.GetInstance().TableTechInfo().GetElement(tech.table_id + tech.level);
			if (techinfo != null) {
				AddTargetBuff(techinfo.Skill(), 8);
			}
		}
	}
	
	private void CheckPlayerRank(long playerId) {
		DatabasePvp_match match = DbMgr.getInstance().getShareDB().SelectOne(DatabasePvp_match.class, "player_id=?", playerId);
		if (match == null) {
			logger.error("playerid={} match is null", playerId);
			return ;
		}
		int rank = Util.getRank(match.feat);
		MT_Data_Rank rankconf = TableManager.GetInstance().TableRank().GetElement(rank);
		if (rankconf == null)
			return ;
		
		if (rankconf.buffid().isEmpty())
			return ;
		
		for (int buffid : rankconf.buffid())
			AddTargetBuff(buffid, 9);
	}
	
	private void CheckPlayerEquip(long playerId) {
		try {
			List<DatabaseItem> items = DbMgr.getInstance().getDbByPlayerId(playerId).Select(DatabaseItem.class, "player_id = ? and number >= 0 and is_on > 0", playerId);
			for (DatabaseItem item : items) {
				MT_Data_Item itemData = TableManager.GetInstance().TableItem().GetElement(item.table_id);
				MT_Data_EquipAttribute attr = TableManager.GetInstance().TableEquipAttribute().GetElement(itemData.Attr());
				for (EquipAttr equipAttr : attr.Arrays()) {
					if (equipAttr.type() == 2) {
						if (itemData.ItemPart() == Common.EQUIPPART.Accessories.Number() && item.level > 0)
							continue;
						AddTargetBuff(equipAttr.num(), 10);
					}
				}

				if (item.is_on != 0 && item.level > 0) {
					MT_Data_IntensifyConfig ic = TableManager.GetInstance().TableIntensifyConfig().GetElement(item.table_id);
					if (ic != null) {
						List<Int2> buffconfig = (List<Int2>)ic.GetDataByString("Intensify_" + item.level);
						for (Int2 value : buffconfig) {
							if (value.field1() == 3) {
								m_Con.getBuffs().AddPassiveBuff(value.field2(), 11);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("PassBuff error, ", e);
		}
	}
	
	private void CheckPlayerVip(long playerId) {
		Integer vipLevel = (Integer)DbMgr.getInstance().getDbByPlayerId(playerId)
				.selectOneField(DatabasePlayer.class, "player_id = ?", "vipLevel", Integer.class, playerId);
		if (vipLevel == null)
			return ;
		
		MT_Data_Vip vipData = TableManager.GetInstance().TableVip().GetElement(vipLevel);
		if (vipData == null)
			return ;
		
		for (int i=0; i<vipData.PassiveBuffs().size(); ++i)
			AddTargetBuff(vipData.PassiveBuffs().get(i), 12);
	}
	
	// 刷新某种类型的buff增益效果值
	public void updateTargetBuffValue(MT_Data_PassiveBuff data, boolean add) {
		if (data.type() != Common.PASSIVEBUFF_OBJECT_TYPE.SELF.Number())
			return ;
		
		try {
			Int3 buf = data.buffs();
			int buffType = buf.field1();
			PassiveGain gain = null;
			if (m_targetGainCache.containsKey(buffType))
				gain = m_targetGainCache.get(buffType);
			else
				gain = new PassiveGain();

			if (buf.field2() == 0)
				gain.value += (add ? buf.field3() : -buf.field3());
			else if (buf.field2() == 1)
				gain.value_f += (float)(add ? buf.field3() : -buf.field3()) / 10000f;

			m_targetGainCache.put(buffType, gain);
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}
	public PassiveGain GetTargetBuffValue(int type) {
		PassiveGain gain = PassiveGain.default_gain;
		if (m_targetGainCache.containsKey(type))
			gain = m_targetGainCache.get(type);
		return gain;
	}
	public int getValueByIncTargetBuff(Common.PASSIVEBUFF_TYPE valType, int orgVal) {
		PassiveGain gain = GetTargetBuffValue(valType.Number());
		return (int)((float)(orgVal + gain.value) * (1 + gain.value_f));
	}
	public int getValueByDecTargetBuff(Common.PASSIVEBUFF_TYPE valType, int orgVal) {
		PassiveGain gain = GetTargetBuffValue(valType.Number());
		return (int)((float)(orgVal - gain.value) * (1 - gain.value_f));
	}
}
