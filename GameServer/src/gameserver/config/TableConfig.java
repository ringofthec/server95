package gameserver.config;

import com.commons.configuration.Property;

public class TableConfig {
	@Property(key = "tableconfig.tablePath",defaultValue = "data/tables")
	public static String tablePath;
	
	@Property(key = "tableconfig.tableSuffix",defaultValue = ".data")
	public static String tableSuffix;
	
	@Property(key = "fishconfig.tablePath",defaultValue = "data/sceneDatas")
	public static String fishPath;
	
	@Property(key = "fishconfig.tableSuffix",defaultValue = ".json")
	public static String fishSuffix;
}
