package gameserver.cache;

import gameserver.connection.attribute.info.ItemInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.commons.cache.CacheManager;
import com.commons.util.CacheMapUtil;
import com.commons.util.CacheUtil;

import database.DatabaseItem;

public class ItemCache {

	public static final String ITEM = "_all_item_data_";
	/** 每次启动游戏时调用 将key存入缓存 方便下线时做 过期处理 */
	public static void init() {
		CacheUtil.add(CacheManager.KEYS_NAME, ItemCache.class.getName() + ITEM);

	}

	/** 获取道具列表 */
	public static HashMap<Object, Object> getAllItem(long pid) {
		String key = ItemCache.class.getName() + ITEM + pid;
		return CacheMapUtil.get(key);
	}
	
	/** 删除缓存 */
	public static void deleteAllItem(long pid) {
		String key = ItemCache.class.getName() + ITEM + pid;
		CacheMapUtil.delete(key);
	}

	/** 获取所有的道具列表 */
	public static ArrayList<DatabaseItem> getAllItemForList(long pid) {
		HashMap<Object, Object> itemMap = getAllItem(pid);
		if (itemMap == null)
			return null;

		ArrayList<DatabaseItem> items = new ArrayList<DatabaseItem>();
		Set<Object> keys = itemMap.keySet();
		for (Object key : keys) {
			DatabaseItem item = (DatabaseItem) itemMap.get(key);
			items.add(item);
		}
		return items;
	}

	/** 获取单个道具的属性 */
	public static DatabaseItem getItem(long pid, int itemId) {

		HashMap<Object, Object> itemMap = getAllItem(pid);
		if (itemMap != null)
			return (DatabaseItem) itemMap.get(itemId);
		return null;
	}

	/** 设置数据到缓存 */
	public static void setAllItem(long pid, HashMap<Object, Object> itemMap) {
		String key = ItemCache.class.getName() + ITEM + pid;
		CacheMapUtil.set(key, itemMap);
	}

	public static void updateItem(long pid,DatabaseItem item) {
		HashMap<Object, Object> itemMap = getAllItem(pid);
		if (itemMap == null)
			itemMap=new HashMap<Object, Object>();
		DatabaseItem tempItem=new DatabaseItem();
		tempItem.set(item);
		itemMap.put(item.item_id,tempItem );
		setAllItem(pid, itemMap);
	}

	/**添加单个Item到缓存  也可以使修改*/
	public static void addItem(long pid,DatabaseItem item) {
	
		HashMap<Object, Object> itemMap = getAllItem(pid);
		if (itemMap == null)
			itemMap=new HashMap<Object, Object>();
		DatabaseItem tempItem=new DatabaseItem();
		tempItem.set(item);
		itemMap.put(item.item_id, tempItem);
		setAllItem(pid, itemMap);
	}

	/**
	 * 删除指定Id的道具
	 * @param pid
	 * @param itemId
	 */
	public static void deleteItem(long pid, int itemId) {
		HashMap<Object, Object> itemMap = getAllItem(pid);
		if (itemMap == null)
			return ;
		itemMap.remove(itemId);
		setAllItem(pid, itemMap);
	}
	
	/** 是否需要把数据全部重新添加一次 */
	public static boolean isNeedAllIncr(long pid) {
		if (getAllItem(pid)== null)
			return true;
		return false;
	}

	public static void setAllItem(long playerId,
			Map<Integer, ItemInfo> m_ItemArray) {
		HashMap<Object, Object> items=new HashMap<Object, Object>();
		Set<Integer> keys=m_ItemArray.keySet();
		for(Integer key:keys)
		{
			ItemInfo info=m_ItemArray.get(key);
			DatabaseItem item=new DatabaseItem();
			item.set(info.getM_Item());
			items.put(key, item);
		}
		setAllItem(playerId, items);
	}
}
