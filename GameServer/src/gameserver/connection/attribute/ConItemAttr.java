package gameserver.connection.attribute;

import gameserver.cache.ItemCache;
import gameserver.connection.attribute.info.ItemInfo;
import gameserver.network.protos.game.ProBuild.Proto_UpdateState;
import gameserver.network.protos.game.ProItemProto;
import gameserver.network.server.connection.ConnectionAttribute;
import gameserver.utils.Item_Channel;
import gameserver.utils.TransFormArgs;
import gameserver.utils.Util;
import gameserver.utils.UtilItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.Int3;
import table.MT_Data_Item;
import table.base.TABLE;
import table.base.TableManager;

import com.commons.util.DatabaseInsertUpdateResult;
import com.commons.util.RandomUtil;

import commonality.Common;
import commonality.Common.SETNUMBER_TYPE;
import commonality.ITEM_ID;
import commonality.ProductChannel;
import commonality.PromptType;
import commonality.VmChannel;
import database.DatabaseItem;

public class ConItemAttr extends ConnectionAttribute {
	private static final Logger logger = LoggerFactory
			.getLogger(ConItemAttr.class);
	// 物品数组 <item_id, ItemInfo>
	private Map<Integer, ItemInfo> m_ItemArray = new HashMap<Integer, ItemInfo>();

	@Override
	protected void Initialize_impl() {
		boolean isLoadDb = false;
		m_ItemArray.clear();
		HashMap<Object, Object> itemMap = ItemCache.getAllItem(m_Con
				.getPlayerId());
		
		if (itemMap == null||itemMap.size()==0) {
			isLoadDb = true;
			itemMap = new HashMap<Object, Object>();
		}

		if (isLoadDb) {
			List<DatabaseItem> items = getDb().Select(DatabaseItem.class,
					"player_id = ? and number >= 0", m_Con.getPlayerId());
			for (DatabaseItem item : items) {
				m_ItemArray.put(item.item_id, new ItemInfo(m_Con, this, item));
				DatabaseItem item1=new DatabaseItem();
				item1.set(item);
				itemMap.put(item1.item_id, item1);
			}
		} else {
			Set<Object> keys=itemMap.keySet();
			for(Object key:keys)
			{
				DatabaseItem item=(DatabaseItem) itemMap.get(key);
				item.sync();
				item.setDatabaseSimple(getDb().getM_Simple());
				m_ItemArray.put(item.item_id, new ItemInfo(m_Con, this, item));
			}
		}
		//将数据设置到缓存用于清除缓存时间or 添加数据
		ItemCache.setAllItem(m_Con.getPlayerId(),itemMap);
	}

	private ProItemProto.Proto_ItemInfo GetProtoData(ItemInfo info) {
		ProItemProto.Proto_ItemInfo.Builder builder = ProItemProto.Proto_ItemInfo
				.newBuilder();
		builder.setItemId(info.getItemId());
		builder.setTableId(info.getTableId());
		builder.setNum(info.getNumber());
		if (info.getData().Type() == Common.ITEMTYPE.Equip.Number()) {
			builder.setOwner(info.getOwner());
			ProItemProto.Proto_EquipInfo.Builder equipInfo = ProItemProto.Proto_EquipInfo
					.newBuilder();
			equipInfo.setStarLv(info.getStar());
			equipInfo.setLevel(info.getLvl());
			equipInfo.setExp(info.getExp());
			builder.setEquipInfo(equipInfo);
		}
		return builder.build();
	}

	public void SendDataArray() {
		ProItemProto.Msg_G2C_UpdateItem.Builder message = ProItemProto.Msg_G2C_UpdateItem
				.newBuilder();
		for (ItemInfo item : m_ItemArray.values()) {
			if (item.getNumber() <= 0)
				continue;
			message.addInfo(GetProtoData(item));
		}
		message.setType(Proto_UpdateState.LIST);
		m_Con.sendPushMessage(message.build());
	}

	@Override
	public void CheckData() {
		if (m_NeedUpdate.size() <= 0)
			return;
		try {
			ProItemProto.Msg_G2C_UpdateItem.Builder message = ProItemProto.Msg_G2C_UpdateItem
					.newBuilder();
			for (Long item_id : m_NeedUpdate) {
				ItemInfo item = getItemById(item_id.intValue());
				message.addInfo(GetProtoData(item));
			}
			message.setType(Proto_UpdateState.UPDATE);
			m_Con.sendPushMessage(message.build());
		} catch (Exception e) {
			logger.error("ConnectionItemAttribute.CheckData is error : ", e);
		}
		m_NeedUpdate.clear();
	}

	/**
	 * 返回玩家物品数组
	 * 
	 * @return
	 */
	public Collection<ItemInfo> getItemArray() {
		return m_ItemArray.values();
	}

	/** 根据物品类型获得物品数量 */
	public int getItemCountByTableId(int table_id) {
		int count = 0;
		for (ItemInfo item : m_ItemArray.values()) {
			if (item.getTableId() == table_id) {
				count += item.getNumber();
				// 不是装备就直接跳出 因为其他物品 在数据库没有叠加上限
				if (!item.isEquip())
					break;
			}
		}
		return count;
	}

	/** 获得物品类型和品质获得物品数量（最后一个参数如果是装备物品 物品的装备部位也得相同） */
	public int getItemCount(int type, int quality, int part) {
		int count = 0;
		for (ItemInfo item : m_ItemArray.values()) {
			if (item.getData().Type() == type
					&& item.getData().Quality() == quality) {
				if (item.getData().Type() != Common.ITEMTYPE.Equip.Number())
					count += item.getNumber();
				else if (item.getData().Type() == Common.ITEMTYPE.Equip
						.Number() && item.getData().ItemPart() == part) {
					count += item.getNumber();
				}
			}
		}
		return count;
	}

	public int getEquipFragFreeCount() {
		int count = 0;
		for (ItemInfo item : m_ItemArray.values()) {
			if (item.getOwner() == 0
					&& Util.isEqiupFragByType(item.getData().Type())) {
				count += item.getNumber() > 0 ? 1 : 0;
			}
		}
		int free = 100 - count;
		return free < 0 ? 0 : free;
	}

	public boolean checkEquipFragCount(int need) {
		if (need > getEquipFragFreeCount()) {
			m_Con.ShowPrompt(PromptType.EQUIP_BAG_IS_FULL);
			return false;
		}

		return true;
	}

	// 检测物品是否足够
	public boolean checkItemEnough(int item_table_id, int number) {
		return checkItemEnough(item_table_id, number, true);
	}

	public boolean checkItemEnough(int item_table_id, int number, boolean bType) {
		if (getItemCountByTableId(item_table_id) >= number)
			return true;

		if (bType == false)
			return false;

		sendItemNotEnoughMsg(item_table_id);
		return false;
	}

	public void sendItemNotEnoughMsg(int item_table_id) {
		if (item_table_id == ITEM_ID.MONEY)
			m_Con.ShowPrompt(PromptType.MONEY_NOT_ENOUGH);
		else if (item_table_id == ITEM_ID.GOLD)
			m_Con.ShowPrompt(PromptType.GOLD_NOT_ENOUGH);
		else if (item_table_id == ITEM_ID.RARE)
			m_Con.ShowPrompt(PromptType.RARE_NOT_ENOUGH);
		else if (item_table_id == ITEM_ID.CP)
			m_Con.ShowPrompt(PromptType.CP_NOT_ENOUGH);
		else {
			m_Con.ShowPrompt(PromptType.ITEM_NOT_ENOUGH,
					TransFormArgs.CreateItemArgs(item_table_id));
		}
	}

	public boolean checkItemEnoughByInt2(Int2 item) {
		return checkItemEnough(item.field1(), item.field2());
	}

	public boolean checkItemEnoughByUtilItem(UtilItem item) {
		return checkItemEnough(item.GetItemId(), item.GetCount());
	}

	// 检测物品是否足够
	public boolean checkItemArrayEnoughByInt2(List<Int2> needItems) {
		for (Int2 item : needItems) {
			if (!checkItemEnough(item.field1(), item.field2()))
				return false;
		}
		return true;
	}

	// 检测物品是否足够
	public boolean checkItemArrayEnoughByUtilItem(List<UtilItem> needItems) {
		for (UtilItem item : needItems) {
			if (!checkItemEnough(item.GetItemId(), item.GetCount()))
				return false;
		}
		return true;
	}

	// 添加一个物品
	private ItemInfo addItemByTableId(int item_table_id, int num) {
		DatabaseItem data = new DatabaseItem();
		data.table_id = item_table_id;
		data.number = num;
		data.player_id = m_Con.getPlayerId();
		data.is_on = 0;
		DatabaseInsertUpdateResult result = getDb().Insert(data);
		data.item_id = result.identity.intValue();
		data.level = 0;
		data.exp = 0;
		ItemInfo info = new ItemInfo(m_Con, this, data);
		m_ItemArray.put(data.item_id, info);
		addCache(data);
		
		return info;
	}
	
	public void addCache(DatabaseItem data)
	{
		if(ItemCache.isNeedAllIncr(m_Con.getPlayerId()))
			ItemCache.setAllItem(m_Con.getPlayerId(), m_ItemArray);
		else
		    ItemCache.addItem(m_Con.getPlayerId(), data);
	}

	/** 根据ItemId操作物品 如果是装备 number <= 0 则直接删除 */
	public void setItemNumberByItemId(int item_id, int number,
			SETNUMBER_TYPE type, VmChannel vmChannel,
			ProductChannel productChannel, long serial_num, String chain,
			Item_Channel itemch) {
		ItemInfo item = getItemById(item_id);
		if (item != null)
			item.setItemNumber(number, type, vmChannel, productChannel,
					serial_num, chain, true, itemch);
	}

	// 操作物品数量
	public void setItemNumberArrayByInt2(List<Int2> items, SETNUMBER_TYPE type,
			VmChannel vmChannel, ProductChannel productChannel,
			long serial_num, String chain, Item_Channel itemch) {
		for (Int2 item : items)
			setItemNumber(item.field1(), item.field2(), type, vmChannel,
					productChannel, serial_num, chain, itemch);
	}

	public void setItemNumberArrayByUtilItemList(List<UtilItem> items,
			SETNUMBER_TYPE type, VmChannel vmChannel,
			ProductChannel productChannel, long serial_num, String chain,
			Item_Channel itemch) {
		for (UtilItem item : items)
			setItemNumber(item.GetItemId(), item.GetCount(), type, vmChannel,
					productChannel, serial_num, chain, itemch);
	}

	// 操作物品数量
	public void setItemNumberArrayByInt3(List<Int3> items, SETNUMBER_TYPE type,
			VmChannel vmChannel, ProductChannel productChannel,
			long serial_num, String chain, Item_Channel itemch) {
		for (Int3 item : items)
			setItemNumber(item.field2(), item.field3(), type, vmChannel,
					productChannel, serial_num, chain, itemch);
	}

	// 操作物品数量
	public void setItemNumberArrayByUtilItem(List<UtilItem> items,
			SETNUMBER_TYPE type, VmChannel vmChannel,
			ProductChannel productChannel, long serial_num, String chain,
			Item_Channel itemch) {
		for (UtilItem item : items)
			setItemNumber(item.GetItemId(), item.GetCount(), type, vmChannel,
					productChannel, serial_num, chain, itemch);
	}

	// 操作物品数量
	public void setItemNumberByInt2(Int2 item, SETNUMBER_TYPE type,
			VmChannel vmChannel, ProductChannel productChannel,
			long serial_num, String chain, Item_Channel itemch) {
		setItemNumber(item.field1(), item.field2(), type, vmChannel,
				productChannel, serial_num, chain, itemch);
	}

	// 操作物品数量
	public void setItemNumberByUtilItem(UtilItem item, SETNUMBER_TYPE type,
			VmChannel vmChannel, ProductChannel productChannel,
			long serial_num, String chain, Item_Channel itemch) {
		setItemNumber(item.GetItemId(), item.GetCount(), type, vmChannel,
				productChannel, serial_num, chain, itemch);
	}

	public void setItemNumber(int item_table_id, int number,
			SETNUMBER_TYPE type, VmChannel vmChannel,
			ProductChannel productChannel, long serial_num, String chain,
			Item_Channel itemch) {
		setItemNumber(item_table_id, number, type, vmChannel, productChannel,
				serial_num, chain, true, itemch);
	}

	// 根据ItemTableId操作物品 如果是装备 只能添加不能删除
	public void setItemNumber(int item_table_id, int number,
			SETNUMBER_TYPE type, VmChannel vmChannel,
			ProductChannel productChannel, long serial_num, String chain,
			boolean isShowPoto, Item_Channel itemch) {
		MT_Data_Item itemData = TableManager.GetInstance().TableItem()
				.GetElement(item_table_id);
		if (itemData == null) {
			logger.error("MT_Data_Item is null : {0}", item_table_id);
			return;
		}

		// 如果是装备就特殊处理 装备只能添加装备
		if (itemData.Type() == Common.ITEMTYPE.Equip.Number()) {
			if (type == SETNUMBER_TYPE.MINUS || number <= 0) {
				logger.error("装备类物品不能通过TableId删除 请通过ItemId操作");
				return;
			}
			for (int i = 0; i < number; ++i) {
				ItemInfo item = addItemByTableId(item_table_id, 1);
				InsertNeedUpdate(item.getItemId());
			}
		} else {
			ItemInfo item = null;
			for (ItemInfo temp : m_ItemArray.values()) {
				if (temp.getTableId() == item_table_id) {
					item = temp;
					break;
				}
			}
			if (item == null)
				item = addItemByTableId(item_table_id, 0);
			item.setItemNumber(number, type, vmChannel, productChannel,
					serial_num, chain, isShowPoto, itemch);
		}
	}

	// 通过tableid获得Item
	public List<ItemInfo> getItemByTableId(int table_id) {
		List<ItemInfo> list = new ArrayList<ItemInfo>();
		for (ItemInfo item : m_ItemArray.values()) {
			if (item.getTableId() == table_id)
				list.add(item);
		}
		return list;
	}

	// 根据物品ID获得Item
	public ItemInfo getItemById(int item_id) {
		return m_ItemArray.get(item_id);
	}

	public void setItemOwner(int item_id, int id) {
		ItemInfo item = getItemById(item_id);
		item.setOwner(id);
	}

	// 勋章材料所带的经验
	public int calcItemsExpCount(List<Integer> ls) {
		int allExp = 0;
		for (Integer id : ls) {
			MT_Data_Item item = getItemById(id).getData();
			if (!TABLE.IsInvalid(item.exp()))
				allExp += item.exp();
		}
		return allExp;
	}

	public void CombineDebris(int item_id) {
		ItemInfo info = getItemById(item_id);
		if (info == null)
			return;
		info.CombineDebris();
	}
	
	public int randomStrengthenAddExp() {
		int rate = RandomUtil.RangeRandom(0, 100);
		int addExp = 0;
		if (rate < 10) {
			addExp = 5;
		}
		else if (rate >= 10 && rate < 30) {
			addExp = 10;
		}
		else if (rate >= 30 && rate < 50) {
			addExp = 15;
		}
		else if (rate >= 50 && rate < 70) {
			addExp = 20;
		}
		else if (rate >= 70 && rate < 90) {
			addExp = 25;
		}
		else {
			addExp = 30;
		}
		
		return addExp;
	}
}
