package gameserver.config;

import com.commons.configuration.Property;

public class AccountVerifyConfig {
	@Property(key = "accountverifyconfig.address",defaultValue = "http://www.toydog.com.cn:10000/Account/src/process/verify.php")
	public static String address = "";
}
