package gameserver.ipo;
import gameserver.logging.LogEntry;
import gameserver.network.server.connection.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

import com.commons.util.TimeUtil;
import commonality.ProductChannel;
import commonality.VmChannel;

public class IPOManagerFile extends IPOManager {
	public static final Logger logger = LoggerFactory.getLogger(IPOManagerFile.class);

	public IPOManagerFile() {
	}
	
	@Override
	public void UserRegister(Connection con)
	{
		LogEntry ent = new LogEntry(PROJECT_ID, 1, con.getAccount().getLastDeviceUniqueIdentifier(),
				con.getIP(), con.getPlayerId(), con.getPlayerName(),
				TimeUtil.GetLogDateString(), "1.0", "1.0", "dev", 1, 1, "us");
		logger.info(MarkerFactory.getMarker("IPO_REGISTER"), ent.toStringEx());
	}
	
	@Override
	public void UserLogin(Connection con)
	{
		LogEntry ent = new LogEntry(PROJECT_ID, 1, con.getAccount().getLastDeviceUniqueIdentifier(),
				con.getIP(), con.getPlayerId(), con.getPlayerName(),
				TimeUtil.GetLogDateString(), "1.0", "1.0", "dev", 1, 1, "us");
		logger.info(MarkerFactory.getMarker("IPO_LOGIN"), ent.toStringEx());
	}
	
	@Override
	public void UserMoneyLog(Connection connection,long serial_no,int log_type,long vm_num,String chain,VmChannel channel,
			int currency_type,Double pay_price,String product_id,long balance)
	{
		LogEntry ent = new LogEntry(PROJECT_ID, 1 ,connection.getAccount().getLastDeviceUniqueIdentifier(),
				connection.getIP(), connection.getPlayerId(), connection.getPlayerName(),  TimeUtil.GetLogDateString(),
				TimeUtil.GetDateTime()*1000,serial_no,log_type,12,vm_num,chain,channel.Number(),balance,currency_type,pay_price, product_id);
		logger.info(MarkerFactory.getMarker("IPO_MONEY"), ent.toStringEx());
	}
	
	@Override
	public void UserProductLog(Connection connection,long serial_no,int log_type,long product_num,long vm_num,
			String chain,ProductChannel channel,String product_id,long balance)
	{
		LogEntry ent = new LogEntry(PROJECT_ID, 1 ,connection.getAccount().getLastDeviceUniqueIdentifier(),
				connection.getIP(), connection.getPlayerId(), connection.getPlayerName(),  TimeUtil.GetLogDateString(),
				TimeUtil.GetDateTime()*1000,serial_no,log_type,12,vm_num,chain,channel.Number(),balance,product_id,product_num);
		logger.info(MarkerFactory.getMarker("IPO_PRODUCT"), ent.toStringEx());
	}
}
