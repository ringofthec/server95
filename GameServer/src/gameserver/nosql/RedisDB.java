package gameserver.nosql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.commons.database.DatabaseTableDataBase;
import com.commons.util.JsonUtil;
import com.commons.util.string;

public class RedisDB {
	
	private static Logger logger = LoggerFactory.getLogger(RedisDB.class);
	
	private Jedis jedis;
	static RedisDB _instance;
	static RedisDB getInstance() {return _instance; }
	
	public RedisDB(String ip, int port) {
		jedis = new Jedis(ip, port);
		logger.debug("init redis connection");
	}
	
	public <T extends DatabaseTableDataBase<?>> T get(Class<T> clazz, String key) {
		String value = jedis.get(key);
		if (value == null)
			return null;
		
		T temp = JsonUtil.JsonToObject(value, clazz);
		return temp;
	}
	
	public <T extends DatabaseTableDataBase<?>> void set(T obj, String key) {
		String str = JsonUtil.ObjectToJson(obj);
		if (str == null)
			return ;
		
		jedis.set(key, str);
	}
}
