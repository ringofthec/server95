package gameserver.config;

import com.commons.configuration.Property;
import com.commons.network.ConnectionFactory;

public class ServerConfig {
	@Property(key = "serverconfig.listenip",defaultValue = "*")
	public static String listenip;			//外网监听的IP 不填默认自动取外网IP
	
	@Property(key = "serverconfig.hostname",defaultValue = "*")
	public static String hostname;			//监听的IP
	
	@Property(key = "serverconfig.name",defaultValue = "Game Server")
	public static String name;				//服务器名称
	
	@Property(key = "serverconfig.connectionFactory",defaultValue = "gameserver.network.server.connection.ConnectionFactoryImpl")
	public static Class<? extends ConnectionFactory> connectionFactory;		//connect仓库
	
	@Property(key = "serverconfig.cn_black",defaultValue = "false")
	public static boolean cn_black;	

	@Property(key = "serverconfig.threadpool_process_mode",defaultValue = "true")
	public static boolean thread_pool_mode;
	
	@Property(key = "serverconfig.robot.num",defaultValue = "10")
	public static int ROBOT_NUM;
	
	@Property(key = "serverconfig.robot.thread",defaultValue = "10")
	public static int ROBOT_THREAD_NUM;
	
	@Property(key = "serverconfig.robot.interval",defaultValue = "800")
	public static int ROBOT_INTERVAL;
	
	@Property(key = "serverconfig.robot.connection",defaultValue = "192.168.1.176")
	public static String ROBOT_CONNECTION_URL;	
	
	@Property(key = "cache_ip_port",defaultValue = "192.168.1.118:11211")
	public static String CACHE_IP_PORT;
	
	@Property(key = "keys_name",defaultValue = "data_key")
	public static String KEYS_NAME;
	
	@Property(key = "data_over_time",defaultValue = "30")
	public static int DATA_OVER_TIME;
	
	@Property(key = "send.compress_len",defaultValue = "150")
	public static int send_compress_len;
	
	/**true关闭   false 未关闭*/
	@Property(key = "close_cache",defaultValue = "true")
	public static boolean CLOSE_CACHE;
	
	@Property(key = "country",defaultValue = "CN")
	public static String country;
	
	@Property(key = "app_test",defaultValue = "false")
	public static boolean app_test;
	
	@Property(key = "szhios",defaultValue = "false")
	public static boolean szhios;
}


