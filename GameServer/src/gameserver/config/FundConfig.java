package gameserver.config;

import com.commons.configuration.Property;

public class FundConfig {
	@Property(key = "fund.fund_day_count",defaultValue = "30")
	public static int fund_day_count;	
	
	@Property(key = "fund.fund_day_back_gold",defaultValue = "100")
	public static int fund_day_back_gold;			
}
