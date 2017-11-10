package gameserver.config;

import com.commons.configuration.Property;

public class GameConfig {
	// 基金
	@Property(key = "fund.fund_day_count",defaultValue = "30")
	public static int fund_day_count;	
	
	@Property(key = "fund.fund_day_back_gold",defaultValue = "100")
	public static int fund_day_back_gold;
	
	
	// 英雄招募等级
	@Property(key = "hero.second_level_need",defaultValue = "35")
	public static int hero_second_level_need;
	
	@Property(key = "hero.thrid_level_need",defaultValue = "45")
	public static int hero_thrid_level_need;
	
	@Property(key = "hero.second_gold_need",defaultValue = "1000")
	public static int hero_second_gold_need;
	
	@Property(key = "hero.thrid_gold_need",defaultValue = "5000")
	public static int hero_thrid_gold_need;
	
	@Property(key = "fish.ban_pool_and_instance",defaultValue = "false")
	public static boolean fish_ban_pool_and_instance;
}
