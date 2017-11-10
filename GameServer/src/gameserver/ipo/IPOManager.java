package gameserver.ipo;
import gameserver.network.server.connection.Connection;

import java.util.concurrent.atomic.AtomicLong;

import commonality.ProductChannel;
import commonality.VmChannel;

public class IPOManager {
	private static IPOManager instance = null;
	public static IPOManager getInstance() { return instance; }
	
	private AtomicLong m_log_id ;
	public static final String PROJECT_ID = "toy_war";
	
	public static void init(boolean isDb, String url,String username,String password)
	{
		if (isDb)
		{
			instance = new IPOManagerDb();
			instance.Initialize(url, username, password);
		} else {
			instance = new IPOManagerFile();
		}
	}
	
	public void Initialize(String url,String username,String password) {
		
	}
	
	public AtomicLong getM_log_id() {
		return m_log_id;
	}
	public void setM_log_id(long logId) {
		this.m_log_id = new AtomicLong(logId);
	}
	
	public long getNextId() {
		return m_log_id.getAndIncrement();
	}
	
	public void LogBuyService(Connection connection, String servicename, long serial_num) {
		String sname = "service_" + servicename;
		UserProductLog(connection, serial_num, 1, 1L, 0, "", ProductChannel.Buy, 
				sname, 1L);
		UserProductLog(connection, serial_num, 2, 1L, 0, "", ProductChannel.PurchaseGoods, 
				sname, 0L);
	}
	public void UserRegister(Connection connection) {	
	}
	public void UserLogin(Connection connection) {
	}
	public void UserMoneyLog(Connection connection,long serial_no,int log_type,
			long vm_num,String chain,VmChannel channel,
			int currency_type,Double pay_price,String product_id,long balance) {
	}
	public void UserProductLog(Connection connection,long serial_no,int log_type,
			long product_num,long vm_num,
			String chain,ProductChannel channel,String product_id,long balance) {
	}
}
