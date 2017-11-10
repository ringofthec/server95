package gameserver.cache;

import com.commons.cache.CacheManager;

import databaseshare.DatabaseAccount;

/**
 * 账号信息 缓存
 * 
 * @author Administrator
 * 
 */
public class AccountCache {

	/** 添加账号信息缓存 */
	public static void setAccountCache(String session, DatabaseAccount account) {
		DatabaseAccount value = new DatabaseAccount();
		value.set(account);
		// 因为存储单个所以 可以直接使用cachemanager
		CacheManager.set(session, value);
	}

	/** 获取账号信息 */
	public static DatabaseAccount getAccount(String session) {
		Object obj = CacheManager.get(session);
		if (obj != null)
			return (DatabaseAccount) obj;
		return null;
	}

	/** 修改账号 所在服务器信息 */
	public static void updateAccountServer(String session, String serveruid) {
		DatabaseAccount value = getAccount(session);
		if (value == null)
			return;
		value.serveruid = serveruid;
		CacheManager.set(session, value);
	}

	public static void updateAccount(String session, String model, String lang,
			String version, String operatorsys, String operatorsys_lang) {
		DatabaseAccount value = getAccount(session);
		if (value == null)
			return;
		value.last_model = model;
		value.last_lang = lang;
		value.last_version = version;
		value.last_opsys = operatorsys;
		value.last_opsys_lang = operatorsys_lang;
		CacheManager.set(session, value);

	}
}
