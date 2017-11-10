package gameserver.config;
import com.commons.configuration.Property;

public class HttpServerConfig {
	
	@Property(key = "httpserverconfig.scriptDescriptor",defaultValue = "data/scripts/httpserver")
	public static String scriptDescriptor;
}
