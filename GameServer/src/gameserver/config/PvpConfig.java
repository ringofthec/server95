package gameserver.config;

import com.commons.configuration.Property;

public class PvpConfig {
	@Property(key = "pvp_robot_money_param",defaultValue = "0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1")
	public static String pvp_robot_money_param;			
	
	@Property(key = "pvp_robot_money_rate",defaultValue = "20,20,20,10,10,10,4,4,1,1")
	public static String pvp_robot_money_rate;		
	
	@Property(key = "pvp_match_cost_level_param",defaultValue = "10")
	public static Integer pvp_match_cost_level_param;	
	
	@Property(key = "pvp_match_cost_count_param",defaultValue = "0")
	public static Integer pvp_match_cost_count_param;	
	
	@Property(key = "pvp_fuck_eachother",defaultValue = "0")
	public static Integer pvp_fuck_eachother;	
}
