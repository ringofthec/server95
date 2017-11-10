package gameserver.cache;

import gameserver.connection.attribute.info.TechInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.commons.cache.CacheManager;
import com.commons.util.CacheMapUtil;
import com.commons.util.CacheUtil;

import database.DatabaseTech;

public class TechCache {

	public static final String TECH_CACHE = "_tech_data_cache_";

	/** 每次启动游戏时调用 将key存入缓存 方便下线时做 过期处理 */
	public static void init() {
		CacheUtil.add(CacheManager.KEYS_NAME, TechCache.class.getName()
				+ TECH_CACHE);
	}

	/** 获取科技数据 */
	public static HashMap<Object, Object> getTechs(long pid) {
		String key = TechCache.class.getName() + TECH_CACHE + pid;
		return CacheMapUtil.get(key);
	}

	/** 获取指定table id 的科技 */
	public static DatabaseTech getTech(long pid, int table_id) {
		HashMap<Object, Object> techs = getTechs(pid);
		if (techs == null)
			return null;
		Object obj = techs.get(table_id);
		if (obj == null)
			return null;
		return (DatabaseTech) obj;
	}

	/** 添加科技 */
	public static void addTech(long pid, DatabaseTech tech) {
		String key = TechCache.class.getName() + TECH_CACHE + pid;
		HashMap<Object, Object> techs = getTechs(pid);
		if (techs == null)
			techs = new HashMap<Object, Object>();
		DatabaseTech tempTech = new DatabaseTech();
		tempTech.set(tech);
		techs.put(tempTech.table_id, tempTech);
		CacheMapUtil.set(key, techs);
	}

	/** 设置所有的科技数据 */
	public static void setTechs(long pid, HashMap<Object, Object> techs) {
		String key = TechCache.class.getName() + TECH_CACHE + pid;
		CacheMapUtil.set(key, techs);
	}

	/** 更新一个科技 */
	public static void updateTech(long pid, DatabaseTech tech) {
		String key = TechCache.class.getName() + TECH_CACHE + pid;
		HashMap<Object, Object> techs = getTechs(pid);
		if (techs == null)
			techs = new HashMap<Object, Object>();
		DatabaseTech tempTech = new DatabaseTech();
		tempTech.set(tech);
		techs.put(tempTech.table_id, tempTech);
		CacheMapUtil.set(key, techs);
	}
	
	/** 是否需要把数据全部重新添加一次 */
	public static boolean isNeedAllIncr(long pid) {
		if (getTechs(pid) == null)
			return true;
		return false;
	}

	public static void setTechs(Long player_id,
			Map<Integer, TechInfo> m_TechArray) {
		Set<Integer> keys=m_TechArray.keySet();
		HashMap<Object, Object>techs=new HashMap<Object, Object>();
		for(int key:keys)
		{
			TechInfo info=m_TechArray.get(key);
			DatabaseTech tech=new DatabaseTech();
			tech.set(info.getM_Tech());
			techs.put(key, tech);
		}
		setTechs(player_id, techs);
	}
}
