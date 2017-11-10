package gameserver.network.message.game;

import gameserver.connection.attribute.info.BuildInfo;
import gameserver.connection.attribute.info.ItemInfo;
import gameserver.network.protos.game.CommonProto.Proto_BuildState;
import gameserver.network.protos.game.ProPassiveBuff;
import gameserver.network.server.connection.Connection;
import gameserver.utils.PassiveGain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.EquipAttr;
import table.Int2;
import table.MT_Data_EquipAttribute;
import table.MT_Data_IntensifyConfig;
import table.MT_Data_Item;
import table.MT_Data_Rank;
import table.MT_Data_Vip;
import table.MT_Data_legion;
import table.base.TableManager;

import commonality.Common;

public class ClientMessagePassiveBuff {
	private final static ClientMessagePassiveBuff instance = new ClientMessagePassiveBuff();
	public static ClientMessagePassiveBuff getInstance() { return instance; }
	
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(ClientMessageBuild.class);
	
	public void initialize() {
	}
	
	// 更新Vip 检测被动Buff并向客户端更新
	public void UpdatePassiveBuffByVip(Connection connect, int oldLv, int newLv) {
		List<Integer> oldList = new ArrayList<Integer>();
		List<Integer> newList = new ArrayList<Integer>();
		
		if (oldLv > 0) {
			MT_Data_Vip vipData = TableManager.GetInstance().TableVip().GetElement(oldLv);
			if (vipData == null)
				return ;
			
			for (int i : vipData.PassiveBuffs()) {
				connect.getBuffs().DelPassiveBuff(i, 22);
				oldList.add(i);
			}
		}
		
		if (newLv > 0) {
			MT_Data_Vip vipData = TableManager.GetInstance().TableVip().GetElement(newLv);
			if (vipData == null)
				return ;
			
			for (int i : vipData.PassiveBuffs()) {
				connect.getBuffs().AddPassiveBuff(i, 23);
				newList.add(i);
			}
		}
		UpdatePlayerPassiverBuffs(connect, oldList, newList);
	}
	
	//更新军团buf
	public void UpdateLegionBuf(Connection connect, int oldLv, int newLv){
		List<Integer> oldList = new ArrayList<Integer>();
		List<Integer> newList = new ArrayList<Integer>();
		
		if (TableManager.GetInstance().Tablelegion().Contains(oldLv)) {
			MT_Data_legion oldLegion = TableManager.GetInstance().Tablelegion().GetElement(oldLv);
			if (oldLegion != null){
				for (int bufId : oldLegion.PassiveBuff()) {
					connect.getBuffs().DelPassiveBuff(bufId, 24);
					oldList.add(bufId);
				}
			}
		}
		
		MT_Data_legion newLegion = TableManager.GetInstance().Tablelegion().GetElement(newLv);
		if (newLegion != null){
			for (int bufId : newLegion.PassiveBuff()) {
				connect.getBuffs().AddPassiveBuff(bufId, 25);
				newList.add(bufId);
			}
		}
		UpdatePlayerPassiverBuffs(connect, oldList, newList);
	}
	
	// 更换装备检测被动Buff并向客户端更新
	public void UpdateFeatPassiveBuff(Connection connect, int oldFeat, int newFeat) {
		List<Integer> oldList = new ArrayList<Integer>();
		List<Integer> newList = new ArrayList<Integer>();

		if (oldFeat > 0) {
			MT_Data_Rank rank = TableManager.GetInstance().TableRank().GetElement(oldFeat);
			if (rank.buffid().isEmpty())
				return ;
			
			for (int buffid : rank.buffid()) {
				connect.getBuffs().DelPassiveBuff(buffid, 26);
				oldList.add(buffid);
			}
		}

		if (newFeat > 0) {
			MT_Data_Rank rank = TableManager.GetInstance().TableRank().GetElement(newFeat);
			if (rank.buffid().isEmpty())
				return ;
			
			for (int buffid : rank.buffid()) {
				connect.getBuffs().AddPassiveBuff(buffid, 27);
				newList.add(buffid);
			}
		}

		UpdatePlayerPassiverBuffs(connect, oldList, newList);
	}
	
	public void UpdateTechPassiveBuff(Connection connect, int oldid, int newid) {
		List<Integer> oldList = new ArrayList<Integer>();
		List<Integer> newList = new ArrayList<Integer>();

		if (oldid > 0) {
			connect.getBuffs().DelPassiveBuff(oldid, 28);
			oldList.add(oldid);
		}

		if (newid > 0) {
			connect.getBuffs().AddPassiveBuff(newid, 29);
			newList.add(newid);
		}

		UpdatePlayerPassiverBuffs(connect, oldList, newList);
	}
	
	// 更换装备检测被动Buff并向客户端更新
	public void UpdateEquipPassiveBuff(Connection connect, int oldId, int newId) {
		try {
			List<Integer> oldList = new ArrayList<Integer>();
			List<Integer> newList = new ArrayList<Integer>();

			if (oldId > 0) {
				ItemInfo oldItemInfo = connect.getItem().getItemById(oldId);
				MT_Data_Item itemData = TableManager.GetInstance().TableItem().GetElement(oldItemInfo.getTableId());
				MT_Data_EquipAttribute itemAttr = TableManager.GetInstance().TableEquipAttribute().GetElement(itemData.Attr());
				for(EquipAttr pair : itemAttr.Arrays()) {
					if (pair.type() == 2) {
						if (itemData.ItemPart() == Common.EQUIPPART.Accessories.Number() && oldItemInfo.getLvl() > 0)
							continue;
						connect.getBuffs().DelPassiveBuff(pair.num(), 20);
						oldList.add(pair.num());
					}
				}

				if (oldItemInfo.getLvl() > 0) {
					MT_Data_IntensifyConfig ic = TableManager.GetInstance().TableIntensifyConfig().GetElement(oldItemInfo.getTableId());
					if (ic != null) {
						List<Int2> buffconfig = (List<Int2>)ic.GetDataByString("Intensify_" + oldItemInfo.getLvl());
						for (Int2 value : buffconfig) {
							if (value.field1() == 3) {
								connect.getBuffs().DelPassiveBuff(value.field2(), 21);
								oldList.add(value.field2());
							}
						}
					}
				}
			}

			if (newId > 0) {
				ItemInfo newItemInfo = connect.getItem().getItemById(newId);
				MT_Data_Item itemData = TableManager.GetInstance().TableItem().GetElement(newItemInfo.getTableId());
				MT_Data_EquipAttribute itemAttr = TableManager.GetInstance().TableEquipAttribute().GetElement(itemData.Attr());
				for(EquipAttr pair : itemAttr.Arrays()) {
					if (pair.type() == 2) {
						if (itemData.ItemPart() == Common.EQUIPPART.Accessories.Number() && newItemInfo.getLvl() > 0)
							continue;
						connect.getBuffs().AddPassiveBuff(pair.num(), 30);
						newList.add(pair.num());
					}
				}

				if (newItemInfo.getLvl() > 0) {
					MT_Data_IntensifyConfig ic = TableManager.GetInstance().TableIntensifyConfig().GetElement(newItemInfo.getTableId());
					if (ic != null) {
						List<Int2> buffconfig = (List<Int2>)ic.GetDataByString("Intensify_" + newItemInfo.getLvl());
						for (Int2 value : buffconfig) {
							if (value.field1() == 3) {
								connect.getBuffs().AddPassiveBuff(value.field2(), 31);
								newList.add(value.field2());
							}
						}
					}
				}
			}

			UpdatePlayerPassiverBuffs(connect, oldList, newList);
		} catch (Exception e) {
			logger.error("PassiverBuffs error, ", e);
		}
	}
	
	// 更新被动Buff列表
	public void UpdatePlayerPassiverBuffs(Connection connect, List<Integer> oldList, List<Integer> newList) {
		ProPassiveBuff.Msg_G2C_PassiveBuff.Builder message = ProPassiveBuff.Msg_G2C_PassiveBuff.newBuilder();
		for (int i=0; i<oldList.size(); ++i)
			message.addDelTables(oldList.get(i));
		for (int i=0; i<newList.size(); ++i)
			message.addAddTables(newList.get(i));
		message.setType(ProPassiveBuff.Proto_PassiveBuffType.UPDATE);
		for (Entry<Integer, PassiveGain> pair : connect.getBuffs().getPassiveGain().entrySet()) {
			message.addBuffType(pair.getKey());
			ProPassiveBuff.Msg_G2C_PassiveBuff.Msg_PassiveGain.Builder bu = ProPassiveBuff.Msg_G2C_PassiveBuff.Msg_PassiveGain.newBuilder();
			bu.setValue(pair.getValue().value).setValueF(pair.getValue().value_f);
			message.addBuffEffect(bu.build());
		}
		connect.sendReceiptMessage(message.build());
		
		// 这里刷一下建筑的更新时间？
		for (BuildInfo bi : connect.getBuild().getBuildArray()) {
			if (bi.getState() == Proto_BuildState.OPERATE ||
					bi.getState() == Proto_BuildState.OPERATE_UPGRADE ||
					bi.getState() == Proto_BuildState.UPGRADE) {
				bi.save();
				connect.getBuild().CheckData();
			}
		}
	}
}
