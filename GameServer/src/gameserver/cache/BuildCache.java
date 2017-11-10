package gameserver.cache;

import gameserver.connection.attribute.info.BuildInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.commons.cache.CacheManager;
import com.commons.util.CacheMapUtil;
import com.commons.util.CacheUtil;

import database.DatabaseBuild;

/**
 * 建筑缓存
 * 
 * @author Administrator
 * 
 */
public class BuildCache {

	public static final String ALL_BUILD = "_all_database_build_cache_";

	/** 每次启动游戏时调用 将key存入缓存 方便下线时做 过期处理 */
	public static void init() {
		CacheUtil.add(CacheManager.KEYS_NAME, BuildCache.class.getName()
				+ ALL_BUILD);
	}

	/** 将所有的建筑放到缓存中 value 不能是刚走数据库取出未做处理的数据 */
	public static void setAllBuilder(long pid, HashMap<Object, Object> value) {
		String key = BuildCache.class.getName() + ALL_BUILD + pid;
		CacheMapUtil.set(key, value);
	}
	
	public static void deleteAllBuilder(long pid) {
		String key = BuildCache.class.getName() + ALL_BUILD + pid;
		CacheMapUtil.delete(key);
	}

	public static void setAllBuilder(Long player_id,
			Map<Integer, BuildInfo> m_BuildArray) {
		
		HashMap<Object, Object> allhashMap=new HashMap<>();
		Set<Integer> keys = m_BuildArray.keySet();
			for (int obj : keys) {
				BuildInfo info = m_BuildArray.get(obj);
				DatabaseBuild build=new DatabaseBuild();
				build.set(info.getM_Build());
				allhashMap.put(obj, build);
			}
			setAllBuilder(player_id, allhashMap);
		}
	
	/**
	 * 修改指定的build 添加新的建筑可以调用
	 * 
	 * @param pid
	 * @param build
	 *            需要修改的建筑 or 新的建筑
	 */
	public static void updateBuild(long pid, DatabaseBuild value) {
		String key = BuildCache.class.getName() + ALL_BUILD + pid;

		HashMap<Object, Object> allBuild = CacheMapUtil.get(key);
		// 做处理 如果不做 的话 数据存入数据库会出错
		DatabaseBuild build = new DatabaseBuild();
		build.set(value);
		if (allBuild == null)
			allBuild = new HashMap<Object, Object>();

		allBuild.put(build.build_id, build);
		CacheMapUtil.set(key, allBuild);
	}

	/** 添加新的建筑 */
	public static void addBuild(long pid, DatabaseBuild build) {
		updateBuild(pid, build);
	}

	/** 获取指定id的建筑 */
	public static DatabaseBuild getBuild(long pid, int buildId) {
		String key = BuildCache.class.getName() + ALL_BUILD + pid;
		HashMap<Object, Object> allBuild = CacheMapUtil.get(key);
		return (DatabaseBuild) allBuild.get(buildId);
	}

	/**
	 * 获取所有的建筑
	 * 
	 * @param pid
	 * @return
	 */
	public static HashMap<Object, Object> getAllBuild(long pid) {
		String key = BuildCache.class.getName() + ALL_BUILD + pid;
		return CacheMapUtil.get(key);
	}

	/**
	 * 获取所有建筑arrayList
	 * 
	 * @param pid
	 * @return
	 */
	public static ArrayList<DatabaseBuild> getAllBuildForList(long pid) {
		HashMap<Object, Object> allBuild = getAllBuild(pid);
		if (allBuild != null) {
			ArrayList<DatabaseBuild> builds = new ArrayList<DatabaseBuild>();
			Set<Object> keys = allBuild.keySet();
			for (Object obj : keys) {
				DatabaseBuild build = (DatabaseBuild) allBuild.get(obj);
				builds.add(build);
			}
			return builds;
		}
		return null;
	}
	
	/**是否需要把数据全部重新添加一次*/
	public static boolean isNeedAllIncr(long pid)
	{
		if(getAllBuild(pid)==null)
			return true;
		return false;
	}

}
