package gameserver;

import gameserver.http.HttpProcessManager;
import gameserver.http.HttpServerManager;
import gameserver.ipo.IPOManagerDb;
import gameserver.management.ManagementManager;
import gameserver.network.server.connection.ConnectionManager;
import gameserver.stat.StatManger;
import gameserver.thread.ThreadPoolManager;
import gameserver.utils.DbMgr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.network.websock.PlayerConManager;
import com.commons.network.websock.handler.LoginHandler;

import databaseshare.DatabaseServer_var;


public class Shutdown extends Thread {
	private static final Logger logger = LoggerFactory.getLogger(Shutdown.class);
	private static Shutdown instance = new Shutdown();
	
	private static String uid ;
	
    public static void setUid(String uid) {
		Shutdown.uid = uid;
	}
	public static Shutdown getInstance() { return instance; }
    @Override
    public void run() {
    	try {
    		LoginHandler.can_login = false;
    		PlayerConManager.getInstance().disAll();
    		ConnectionManager.GetInstance().saveAll();
			HttpProcessManager.getInstance().shutdown();
	    	ManagementManager.getInstance().shutdown();
	    	HttpServerManager.getInstance().shutdown();
	    	StatManger.getInstance().Save();
	    	MethodStatitic.getInstance().putToFile();
	    	saveIpoLogId();
	    	ThreadPoolManager.getInstance().shutdown();
	    	ConnectionManager.GetInstance().checkConnectionAlive(true);
	    	logger.info("shut down ");
		} catch (Exception e) {
			logger.error("Shutdown is error : ",e);
		}
    }
	private void saveIpoLogId() {
		DatabaseServer_var map = DbMgr.getInstance().getShareDB().SelectOne(DatabaseServer_var.class," var_key = ? ",uid);
		if (map ==null ) {
			map = new DatabaseServer_var();
			map.var_key = uid;
			map.var_value = String.valueOf(IPOManagerDb.getInstance().getM_log_id());
			DbMgr.getInstance().getShareDB().Insert(map);
		}else {
			map.var_value = String.valueOf(IPOManagerDb.getInstance().getM_log_id());
			DbMgr.getInstance().getShareDB().Update(map, "var_key = ? ",map.var_key);
		}
	}
	public void setAddressAndPort(String uid) {
		Shutdown.setUid(uid);
	}
}
