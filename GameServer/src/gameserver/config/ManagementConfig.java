package gameserver.config;


import com.commons.configuration.Property;

public class ManagementConfig {
	@Property(key = "managementconfig.scriptDescriptor",defaultValue = "data/scripts/management")
	public static String scriptDescriptor;
}
