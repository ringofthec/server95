package gameserver.cache;

import gameserver.connection.attribute.info.CorpsInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.commons.cache.CacheManager;
import com.commons.util.CacheMapUtil;
import com.commons.util.CacheUtil;

import database.DatabaseCorps;

public class CorpsCache {

	public static final String CORPS_DATA="_corps_cache_data_";
	public static void init()
	{
		CacheUtil.add(CacheManager.KEYS_NAME, CorpsCache.class.getName()+CORPS_DATA);
	}
	
	public static HashMap<Object, Object> getAllCorps(long pid) {
		String key = CorpsCache.class.getName() + CORPS_DATA + pid;
		return CacheMapUtil.get(key);
	}

	public static ArrayList<DatabaseCorps> getAllCorpsForList(long pid) {
		HashMap<Object, Object> corpsMap = getAllCorps(pid);
		if (corpsMap == null)
			return null;

		ArrayList<DatabaseCorps> corpss = new ArrayList<DatabaseCorps>();
		Set<Object> keys = corpsMap.keySet();
		for (Object key : keys) {
			DatabaseCorps item = (DatabaseCorps) corpsMap.get(key);
			corpss.add(item);
		}
		return corpss;
	}

	/** 获取单个兵种 */
	public static DatabaseCorps getCorps(long pid, int corpsId) {

		HashMap<Object, Object> itemMap = getAllCorps(pid);
		if (itemMap != null)
			return (DatabaseCorps) itemMap.get(corpsId);
		return null;
	}

	/** 设置数据到缓存 */
	public static void setAllCorps(long pid, HashMap<Object, Object> corpsMap) {
		String key = CorpsCache.class.getName() + CORPS_DATA + pid;
		CacheMapUtil.set(key, corpsMap);
	}
	
	/** 删除缓存 */
	public static void deleteAllCorps(long pid) {
		String key = CorpsCache.class.getName() + CORPS_DATA + pid;
		CacheMapUtil.delete(key);
	}

	public static void updateCorps(long pid,DatabaseCorps corps) {
		HashMap<Object, Object> corpsMap = getAllCorps(pid);
		if (corpsMap == null)
			corpsMap=new HashMap<Object, Object>();
		DatabaseCorps corps1=new DatabaseCorps();
		corps1.set(corps);
		corpsMap.put(corps.corps_id, corps1);
		setAllCorps(pid, corpsMap);
	}

	/**添加*/
	public static void addCorps(long pid,DatabaseCorps item) {
	
		HashMap<Object, Object> corpsMap = getAllCorps(pid);
		if (corpsMap == null)
			corpsMap=new HashMap<Object, Object>();
		DatabaseCorps tempCorps=new DatabaseCorps();
		tempCorps.set(item);
		corpsMap.put(item.corps_id, tempCorps);
		setAllCorps(pid, corpsMap);
	}

	/**
	 * @param pid
	 * @param itemId
	 */
	public static void deleteCorps(long pid, int corpsId) {
		HashMap<Object, Object> itemMap = getAllCorps(pid);
		if (itemMap == null)
			return ;
		itemMap.remove(corpsId);
		setAllCorps(pid, itemMap);
	}
	
	/**是否需要把数据全部重新添加一次*/
	public static boolean isNeedAllIncr(long pid)
	{
		if(getAllCorps(pid)==null)
			return true;
		return false;
	}

	public static void setAllCorps(Long player_id,
			Map<Integer, CorpsInfo> m_CorpsArray) {
		
		HashMap<Object, Object> allCorps=new HashMap<Object, Object>();
		Set<Integer> keys=m_CorpsArray.keySet();
		for(int key:keys)
		{
			CorpsInfo info=m_CorpsArray.get(key);
			DatabaseCorps corps=new DatabaseCorps();
			corps.set(info.getM_Corps());
			allCorps.put(key, corps);
		}
		setAllCorps(player_id, allCorps);
	}
}
