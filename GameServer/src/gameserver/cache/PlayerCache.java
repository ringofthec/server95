package gameserver.cache;

import java.util.ArrayList;
import java.util.List;

import com.commons.cache.CacheManager;
import com.commons.database.DatabaseSimple;
import com.commons.util.CacheUtil;

import database.DatabaseLevReward;
import database.DatabasePlayer;
import databaseshare.DatabasePvp_match;

/**
 * 玩家数据缓存 key格式建议 String key = 类名.class.getName() + PLAYER_DATA(任意字符串) + pid;
 * 
 * 在缓存中 使用 DatabaseTableDataBase 的子类 时 需要用一个新的对象 然后把老的对象的值传入 才可以使用
 * 
 * @author Administrator
 * 
 */
public class PlayerCache {

	/** keys name 每个需要单独存储的数据 做成一个key 在本类中不能重复 */
	public static final String PLAYER_DATA = "_player_data_cache_";
	public static final String PVP_MATCH = "_databasepvp_match_";
	public static final String LEVREWARDS = "_levRewards_"; // 玩家已经领取的等级奖励

	/** 每次启动游戏时调用 将key存入缓存 方便下线时做 过期处理 */
	public static void init() {
		CacheUtil.add(CacheManager.KEYS_NAME, PlayerCache.class.getName()
				+ PLAYER_DATA);
		CacheUtil.add(CacheManager.KEYS_NAME, PlayerCache.class.getName()
				+ PVP_MATCH);
		CacheUtil.add(CacheManager.KEYS_NAME, PlayerCache.class.getName()
				+ LEVREWARDS);
	}

	@SuppressWarnings("unchecked")
	public static DatabasePlayer getPlayer(long pid) {
		String key = PlayerCache.class.getName() + PLAYER_DATA + pid;
		Object obj = CacheUtil.get(key);
		if (obj != null) {
			DatabasePlayer player = ((ArrayList<DatabasePlayer>) obj).get(0);
			return player;
		}
		return null;
	}

	/** 设置 因为Player 只有一个 所以不传ArrayList */
	public static void setPlayer(long pid, DatabasePlayer player) {
		String key = PlayerCache.class.getName() + PLAYER_DATA + pid;
		ArrayList<Object> value = new ArrayList<Object>();
		DatabasePlayer p = new DatabasePlayer();
		p.set(player);
		value.add(p);
		CacheUtil.set(key, value);
	}

	/** 删除指定key的数据 */
	public static void deletePlayer(long pid) {
		String key = PlayerCache.class.getName() + PLAYER_DATA + pid;
		CacheUtil.delete(key);
	}

	public static void setPvpMatch(long pid, DatabasePvp_match pvpMatch) {
		String key = PlayerCache.class.getName() + PVP_MATCH + pid;
		ArrayList<Object> value = new ArrayList<Object>();
		DatabasePvp_match p = new DatabasePvp_match();
		p.set(pvpMatch);
		value.add(p);
		CacheUtil.set(key, value);
	}

	/** 获取缓存数据 */
	@SuppressWarnings("unchecked")
	public static DatabasePvp_match getPvpMatch(long pid) {
		String key = PlayerCache.class.getName() + PVP_MATCH + pid;
		Object obj = CacheUtil.get(key);
		if (obj != null) {
			DatabasePvp_match pvp_match = ((ArrayList<DatabasePvp_match>) obj)
					.get(0);
			return pvp_match;
		}
		return null;
	}

	/** 删除指定key的数据 */
	public static void deletePvpMatch(long pid) {
		String key = PlayerCache.class.getName() + PVP_MATCH + pid;
		CacheUtil.delete(key);
	}

	/** 添加一个新的等级奖励 */
	public static void addLvAward(long pid, DatabaseLevReward levReward) {
		String key = PlayerCache.class.getName() + LEVREWARDS + pid;
		DatabaseLevReward lvAward = new DatabaseLevReward();
		lvAward.set(levReward);
		CacheUtil.add(key, lvAward);
	}

	/** 添加等级奖励集合(上线时调用) */
	public static void setLvAward(long pid, List<DatabaseLevReward> levReward) {
		String key = PlayerCache.class.getName() + LEVREWARDS + pid;
		ArrayList<Object> lvAwards = new ArrayList<Object>();
		for (DatabaseLevReward award : levReward) {
			DatabaseLevReward reward = new DatabaseLevReward();
			reward.set(award);
			lvAwards.add(reward);
		}
		CacheUtil.set(key, lvAwards);
	}

	public static boolean isNeedIncr(long pid) {
		String key = PlayerCache.class.getName() + LEVREWARDS + pid;
		ArrayList<Object> lvAwards = CacheUtil.get(key);
		if (lvAwards == null)
			return true;
		return false;
	}

	public static void getLvAward(long pid, List<DatabaseLevReward> levRewards,
			DatabaseSimple simple) {
		String key = PlayerCache.class.getName() + LEVREWARDS + pid;
		ArrayList<Object> lvAwards = CacheUtil.get(key);
		if (lvAwards == null)
			return;
		for (Object obj : lvAwards) {
			DatabaseLevReward reward = (DatabaseLevReward) obj;
			reward.sync();
			reward.setDatabaseSimple(simple);
			levRewards.add(reward);

		}
	}
}
