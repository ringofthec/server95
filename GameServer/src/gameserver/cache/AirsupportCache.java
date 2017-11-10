package gameserver.cache;

import gameserver.connection.attribute.info.AirSupportInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.commons.cache.CacheManager;
import com.commons.util.CacheMapUtil;
import com.commons.util.CacheUtil;

import database.DatabaseAirsupport;

public class AirsupportCache {

	public static final String AIRSUPPORT_CACHE = "_airsupport_data_cache_";

	/** 每次启动游戏时调用 将key存入缓存 方便下线时做 过期处理 */
	public static void init() {
		CacheUtil.add(CacheManager.KEYS_NAME, AirsupportCache.class.getName()
				+ AIRSUPPORT_CACHE);
	}

	/** 设置空中援助数据 */
	public static void setAirsupport(long pid,
			HashMap<Object, Object> airsupports) {
		String key = AirsupportCache.class.getName() + AIRSUPPORT_CACHE + pid;
		CacheMapUtil.set(key, airsupports);
	}
	

	/** 添加新的空中援助 如果存在 那么会覆盖掉 */
	public static void addAirsupport(long pid, DatabaseAirsupport airsupport) {
		String key = AirsupportCache.class.getName() + AIRSUPPORT_CACHE + pid;
		HashMap<Object, Object> airsupports = getAirsupports(pid);
		if (airsupports == null)
			airsupports = new HashMap<>();
		DatabaseAirsupport air = new DatabaseAirsupport();
		air.set(airsupport);
		airsupports.put(air.table_id, air);
		CacheMapUtil.set(key, airsupports);
	}
	

	/** 获取所有 的空中支援 */
	public static HashMap<Object, Object> getAirsupports(long pid) {
		String key = AirsupportCache.class.getName() + AIRSUPPORT_CACHE + pid;
		return CacheMapUtil.get(key);
	}

	/**获取空中支援*/
	public static DatabaseAirsupport getAirsupport(long pid, int table_id) {
		HashMap<Object, Object> airsupports = getAirsupports(pid);
		if (airsupports == null)
			return null;
		Object obj = airsupports.get(table_id);
		if (obj == null)
			return null;
		return (DatabaseAirsupport) obj;
	}

	/** 更新空中支援 如果不存在 则添加 */
	public static void updateAirsupport(long pid, DatabaseAirsupport airsupport) {
		String key = AirsupportCache.class.getName() + AIRSUPPORT_CACHE + pid;
		HashMap<Object, Object> airsupports = getAirsupports(pid);
		if (airsupports == null)
			airsupports = new HashMap<Object, Object>();
		DatabaseAirsupport air = new DatabaseAirsupport();
		air.set(airsupport);
		airsupports.put(air.table_id, air);
		CacheMapUtil.set(key, airsupports);
	}
	
	/**是否需要把数据全部重新添加一次*/
	public static boolean isNeedAllIncr(long pid)
	{
		if(getAirsupports(pid)==null)
			return true;
		return false;
	}

	public static void setAllAirsupport(Long player_id,
			Map<Integer, AirSupportInfo> m_AirSupportArray) {
		
		HashMap<Object, Object> allAirs=new HashMap<>();
		Set<Integer> keys =m_AirSupportArray.keySet();
		for(int key:keys)
		{
			AirSupportInfo info=m_AirSupportArray.get(key);
			DatabaseAirsupport air=new DatabaseAirsupport();
			air.set(info.getM_AirSupport());
			allAirs.put(key, air);
		}
	}
}
