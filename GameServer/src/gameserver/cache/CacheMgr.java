package gameserver.cache;

import java.util.ArrayList;

import com.commons.cache.CacheManager;
import com.commons.util.CacheUtil;

public class CacheMgr {

	/** 初始化需要下线处理过期的key_head */
	public static void init_key_head() {
		PlayerCache.init();
		BuildCache.init();
		HeroCache.init();
		ItemCache.init();
		TaskCache.init();
		AirsupportCache.init();
		TechCache.init();
		CorpsCache.init();
	}

	/** 玩家下线处理 设置过期时间 */
	public static void outOline(long pid, String session) {
		//需要做单个缓存下线处理的在这里调用
		HeroCache.outOlineMedalHandle(pid, CacheManager.DATE_MIN);
		ArrayList<Object> arrs = CacheUtil.get(CacheManager.KEYS_NAME);
		if(arrs==null||arrs.size()==0)
			return ;
		String key;
		for (Object str : arrs) {
			key = (String) str + pid;
			CacheManager.setForMin(key, CacheManager.get(key), CacheManager.DATE_MIN);
		}
		// SESSION 处理  key结构不一致 so 单独处理
		CacheManager.setForMin(session, CacheManager.get(session), CacheManager.DATE_MIN);
		
		
	}
}
