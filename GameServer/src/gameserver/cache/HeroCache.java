package gameserver.cache;

import gameserver.connection.attribute.info.HeroInfo;
import gameserver.connection.attribute.info.MedalInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.commons.cache.CacheManager;
import com.commons.util.CacheMapUtil;
import com.commons.util.CacheUtil;

import database.DatabaseHero;
import database.DatabaseMedal;

public class HeroCache {

	public static final String HERO_DATA = "_all_hero_data_";

	public static final String MEDAL_DATA = "_medal_data_";

	/** 每次启动游戏时调用 将key存入缓存 方便下线时做 过期处理 */
	public static void init() {
		CacheUtil.add(CacheManager.KEYS_NAME, HeroCache.class.getName()
				+ HERO_DATA);
		// CacheUtil.add(CacheManager.KEYS_NAME, HeroCache.class.getName()
		// + MEDAL_DATA);
	}

	/**
	 * 获取 所有的英雄 为了方便修改所以存放map
	 * 
	 * @param pid
	 * @return
	 */
	public static HashMap<Object, Object> getAllHero(long pid) {
		String key = HeroCache.class.getName() + HERO_DATA + pid;
		return CacheMapUtil.get(key);
	}

	/** 获取 英雄 */
	public static ArrayList<DatabaseHero> getAllHeroForList(long pid) {
		HashMap<Object, Object> herosMap = getAllHero(pid);
		if (herosMap == null)
			return null;
		ArrayList<DatabaseHero> heros = new ArrayList<DatabaseHero>();
		Set<Object> keys = herosMap.keySet();
		for (Object key : keys) {
			DatabaseHero hero = (DatabaseHero) herosMap.get(key);
			heros.add(hero);
		}
		return heros;
	}

	public static DatabaseHero getHero(long pid, int heroId) {
		HashMap<Object, Object> herosMap = getAllHero(pid);
		if (herosMap != null)
			return (DatabaseHero) herosMap.get(heroId);
		return null;
	}

	/** 设置缓存存英雄数据 value 中的对象必须是处理过的 set */
	public static void setHeros(long pid, HashMap<Object, Object> value) {
		String key = HeroCache.class.getName() + HERO_DATA + pid;
		CacheMapUtil.set(key, value);
	}

	/**
	 * 添加英雄 叠加以前的数据
	 * 
	 * @param pid
	 * @param hero
	 */
	public static void addHero(long pid, DatabaseHero hero) {
		String key = HeroCache.class.getName() + HERO_DATA + pid;
		HashMap<Object, Object> value = CacheMapUtil.get(key);
		if (value == null)
			value = new HashMap<Object, Object>();
		DatabaseHero heroTemp = new DatabaseHero();
		heroTemp.set(hero);
		value.put(hero.hero_id, heroTemp);
		CacheMapUtil.set(key, value);
	}

	/** 修改指定的英雄 */
	public static void updateHero(long pid, DatabaseHero hero) {
		String key = HeroCache.class.getName() + HERO_DATA + pid;
		HashMap<Object, Object> value = CacheMapUtil.get(key);
		if (value == null)
			value = new HashMap<Object, Object>();
		DatabaseHero heroTemp = new DatabaseHero();
		heroTemp.set(hero);
		value.put(hero.hero_id, heroTemp);
		CacheMapUtil.set(key, value);
	}

	/** 修改指定的勋章数据 */
	public static void updateMedal(int hero_id, long pid, DatabaseMedal medal) {
		DatabaseMedal tempMedal = new DatabaseMedal();
		tempMedal.set(medal);
		HashMap<Object, Object> medals = getMedals(hero_id, pid);
		if (medals == null)
			medals = new HashMap<>();
		medals.put(tempMedal.medal_id, tempMedal);
		setMedals(hero_id, pid, medals);
	}

	/** 添加新的勋章 如果存在那么就覆盖 */
	public static void addMedal(int hero_id, long pid, DatabaseMedal medal) {
		DatabaseMedal tempMedal = new DatabaseMedal();
		tempMedal.set(medal);
		HashMap<Object, Object> medals = getMedals(hero_id, pid);
		if (medals == null)
			medals = new HashMap<>();
		medals.put(tempMedal.medal_id, tempMedal);
		setMedals(hero_id, pid, medals);
	}

	/** 设置勋章数据 */
	public static void setMedals(int hero_id, long pid,
			HashMap<Object, Object> medals) {
		String key = HeroCache.class.getName() + MEDAL_DATA + pid + hero_id;
		CacheMapUtil.set(key, medals);
	}

	/** 获取所有的勋章 */
	public static HashMap<Object, Object> getMedals(int hero_id, long pid) {
		String key = HeroCache.class.getName() + MEDAL_DATA + pid + hero_id;
		return CacheMapUtil.get(key);
	}

	/** 获取单个的勋章 */
	public static DatabaseMedal getMedal(int hero_id, long pid, int medal_id) {
		HashMap<Object, Object> medals = getMedals(hero_id, pid);
		if (medals == null)
			return null;
		Object obj = medals.get(medal_id);
		if (obj == null)
			return null;
		return (DatabaseMedal) obj;
	}

	/** 勋章离线处理 min数据过期时间 */
	public static void outOlineMedalHandle(long pid, int min) {
		HashMap<Object, Object> heros = getAllHero(pid);
		if (heros == null || heros.size() == 0)
			return;
		Set<Object> heroIds = heros.keySet();
		for (Object key : heroIds) {
			String cacheKey = HeroCache.class.getName() + MEDAL_DATA + pid
					+ key;
			CacheMapUtil
					.setForMin(cacheKey, getMedals((Integer) key, pid), min);
		}

	}

	/** 是否需要把数据全部重新添加一次 */
	public static boolean isNeedAllIncr(long pid) {
		if (getAllHero(pid) == null)
			return true;
		return false;
	}

	public static boolean isNeedIncrMedal(long pid, int hero_id) {
		if (getMedals(hero_id, pid) == null)
			return true;
		return false;
	}

	public static void setHeros(Long player_id, Map<Integer, HeroInfo> m_Heros) {
		HashMap<Object, Object> allHeros = new HashMap<Object, Object>();
		Set<Integer> keys = m_Heros.keySet();
		for (int obj : keys) {
			HeroInfo info=m_Heros.get(obj);
			DatabaseHero hero=new DatabaseHero();
			hero.set(info.getHero());
			allHeros.put(obj, hero);
		}
		setHeros(player_id, allHeros);
	}

	public static void setMedals(int hero_id, long pid,
			List<MedalInfo> m_medalList) {
		HashMap<Object, Object> medals=new HashMap<Object, Object>();
		for(MedalInfo info:m_medalList)
		{
			DatabaseMedal tempMedal = new DatabaseMedal();
			tempMedal.set(info.getMedal());
			medals.put(tempMedal.medal_id, tempMedal);
		}
		setMedals(hero_id, pid, medals);
	}
}
