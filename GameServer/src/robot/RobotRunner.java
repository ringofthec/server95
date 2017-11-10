package robot;

import gameserver.config.AccountVerifyConfig;
import gameserver.config.HttpProcessConfig;
import gameserver.config.HttpServerConfig;
import gameserver.config.ManagementConfig;
import gameserver.config.ServerConfig;
import gameserver.config.TableConfig;
import gameserver.messageprocess.ServerMessageProcessRegister;
import gameserver.network.IOServer;

import com.commons.database.DatabaseConfig;
import com.commons.service.LoggingService;
import com.commons.util.ConfigLoader;
import com.commons.util.PathUtil;

public class RobotRunner {
	public static void main(String[] args) {
		try {
			LoggingService.initialize();
			RobotMsgProcess.getInstance().initMsgProcess();
			
			ConfigLoader base = new ConfigLoader();
			base.loadPath(PathUtil.GetCurrentDirectory() + "data/config",new String[] { "config" }, true);
			base.loadConfig(DatabaseConfig.class,
					TableConfig.class, ServerConfig.class,
					HttpServerConfig.class, ManagementConfig.class,
					HttpProcessConfig.class, AccountVerifyConfig.class);
			
			ServerMessageProcessRegister.isRobot = true;
			IOServer.getInstance().start("localhost",12323);
			RobotManager.getInstace().beginRobeter(5, 200);
			
		} catch (Exception e) {
		}
	}
	
	
}
