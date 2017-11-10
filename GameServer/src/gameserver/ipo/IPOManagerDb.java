package gameserver.ipo;
import gameserver.network.server.connection.Connection;

import com.commons.database.DatabaseSimple;
import com.commons.util.TimeUtil;
import commonality.ProductChannel;
import commonality.VmChannel;

import database.ipo.DatabaseProduct_logs;
import database.ipo.DatabaseUser_login_logs;
import database.ipo.DatabaseUser_register_logs;
import database.ipo.DatabaseVir_money_logs;

public class IPOManagerDb extends IPOManager{
	private DatabaseSimple m_Database = new DatabaseSimple();
	
	public IPOManagerDb() {
	}
	
	public void Initialize(String url,String username,String password) {
		m_Database.Initialize(url, username, password);
	}
	
	@Override
	public void UserRegister(Connection connection)
	{
		DatabaseUser_register_logs registerLog = new DatabaseUser_register_logs();
		registerLog.project_id = PROJECT_ID;
		registerLog.server_id = "0";
		registerLog.open_udid = connection.getAccount().getLastDeviceUniqueIdentifier();
		registerLog.ip = connection.getIP();
		registerLog.user_id = connection.getPlayerId();
		registerLog.user_name = connection.getPlayerName();
		registerLog.created_at = TimeUtil.GetDateTime();
		registerLog.app_id = "1.0";
		registerLog.os_version = "";
		registerLog.device_name = "";
		registerLog.device_id = "";
		registerLog.device_id_type = 2;
		registerLog.locale = "cn";
		m_Database.Insert(registerLog);
	}
	
	@Override
	public void UserLogin(Connection connection)
	{
		DatabaseUser_login_logs loginLog = new DatabaseUser_login_logs();
		loginLog.project_id = PROJECT_ID;
		loginLog.server_id = "0";
		loginLog.open_udid = connection.getAccount().getLastDeviceUniqueIdentifier();
		loginLog.ip = connection.getIP();
		loginLog.user_id = connection.getPlayerId();
		loginLog.user_name = connection.getPlayerName();
		loginLog.created_at = TimeUtil.GetDateTime();
		loginLog.app_id = "1.0";
		loginLog.os_version = "";
		loginLog.device_name = "";
		loginLog.device_id = "";
		loginLog.device_id_type = 2;
		loginLog.locale = "cn";
		m_Database.Insert(loginLog);
	}
	
	@Override
	public void UserMoneyLog(Connection connection,long serial_no,int log_type,long vm_num,String chain,VmChannel channel,
			int currency_type,Double pay_price,String product_id,long balance)
	{
		DatabaseVir_money_logs moneyLog = new DatabaseVir_money_logs();
		moneyLog.project_id = PROJECT_ID;
		moneyLog.server_id = "0";
		moneyLog.open_udid = connection.getAccount().getLastDeviceUniqueIdentifier();
		moneyLog.ip = connection.getIP();
		moneyLog.user_id = connection.getPlayerId();
		moneyLog.user_name = connection.getPlayerName();
		long nowTime = TimeUtil.GetDateTime();
		moneyLog.created_at = nowTime;
		moneyLog.created_ts = nowTime * 100L;
		moneyLog.serial_no = 0L;
		moneyLog.vm_type = 12;
		moneyLog.log_type = log_type;
		moneyLog.vm_num = vm_num;
		//变动类型
		moneyLog.chain = chain;
		moneyLog.channel = channel.Number();
		moneyLog.currency_type = currency_type;
		moneyLog.pay_price = pay_price;
		moneyLog.product_id = product_id;
		moneyLog.balance = balance;
		m_Database.Insert(moneyLog);
	}
	
	@Override
	public void UserProductLog(Connection connection,long serial_no,int log_type,long product_num,long vm_num,
			String chain,ProductChannel channel,String product_id,long balance)
	{
		DatabaseProduct_logs productLog = new DatabaseProduct_logs();
		productLog.project_id = PROJECT_ID;
		productLog.server_id = "0";
		productLog.open_udid = connection.getAccount().getLastDeviceUniqueIdentifier();
		productLog.ip = connection.getIP();
		productLog.user_id = connection.getPlayerId();
		productLog.user_name = connection.getPlayerName();
		long nowTime = TimeUtil.GetDateTime();
		productLog.created_at = nowTime;
		productLog.created_ts = nowTime * 100L;
		productLog.serial_no = serial_no;
		productLog.vm_type = 12;
		productLog.log_type = log_type;
		productLog.product_num = product_num;
		productLog.vm_num = vm_num;
		//变动类型
		productLog.chain = chain;
		productLog.channel = channel.Number();
		productLog.product_id = product_id;
		productLog.balance = balance;
		m_Database.Insert(productLog);
	}
}
