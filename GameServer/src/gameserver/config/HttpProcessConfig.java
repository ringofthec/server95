package gameserver.config;

import com.commons.configuration.Property;

public class HttpProcessConfig {
	@Property(key = "httpprocessconfig.address",defaultValue = "http://192.168.0.1/Management/src/process/server/")
	public static String address = "";
}
