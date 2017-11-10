package gameserver.utils;

import java.util.List;
import java.util.Map;

import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.util.DatabaseUtil;

import databaseshare.DatabaseAccount;
import databaseshare.DatabaseGamedb;

// 管理所有的数据库连接
public class DbMgr {
	public static final Logger logger = LoggerFactory.getLogger(DbMgr.class);
	private FastMap<Integer, DatabaseUtil> dbMaps = new FastMap<Integer, DatabaseUtil>().shared();
	private DatabaseUtil shareDB;
	private FastMap<Long, Integer> playerIdtoDbIdMap = new FastMap<Long, Integer>().shared();
	private final static DbMgr instance = new DbMgr();
    public final static DbMgr getInstance() { return instance; }
    
    public DatabaseUtil getShareDB() {
    	return shareDB;
    }
    
    public void setShareDb(String dburl, String user, String passwd) {
    	shareDB = new DatabaseUtil();
    	shareDB.Initialize(dburl, user, passwd);
    }
    
    public DatabaseUtil getDbByPlayerId(long playerid) {
		if (playerIdtoDbIdMap.containsKey(playerid))
			return getDb(playerIdtoDbIdMap.get(playerid));
		
		DatabaseAccount account = getShareDB().selectFieldFrist(DatabaseAccount.class,
				"player_id = ?", "db_id", playerid);
		if (account == null)
			return null;
		
		playerIdtoDbIdMap.put(playerid, account.db_id);
		return getDb(account.db_id);
	}
    
    public int getDbIdByPlayerId(long playerid) {
    	if (playerIdtoDbIdMap.containsKey(playerid))
			return playerIdtoDbIdMap.get(playerid);
		
		DatabaseAccount account = getShareDB().selectFieldFrist(DatabaseAccount.class,
				"player_id = ?", "db_id", playerid);
		playerIdtoDbIdMap.put(playerid, account.db_id);
		return playerIdtoDbIdMap.get(playerid);
    }
	
	public DatabaseUtil getDb(int db_id) {
    	if (dbMaps.containsKey(db_id)) {
    		return dbMaps.get(db_id);
    	}
    	
    	flushGameDB();
    	return dbMaps.get(db_id);
    }
	
	private void flushGameDB() {
		List<DatabaseGamedb> gamedbs = DbMgr.getInstance().getShareDB()
				.Select(DatabaseGamedb.class,"");
		for (DatabaseGamedb db : gamedbs) {
			if (!dbMaps.containsKey(db.id)) {
				DatabaseUtil newDb = new DatabaseUtil();
				String dburl = "jdbc:mysql://" + db.address + ":" + db.port +
		    			"/" + db.db_name + "?" + db.parameters;
		    	newDb.Initialize(dburl, db.username, db.password);
		    	dbMaps.put(db.id, newDb);
			}
		}
	}
	
	public Map<Integer, DatabaseUtil> getGSDb() {
		return dbMaps;
	}
	
	public int getGSCount() {
		flushGameDB();
		return dbMaps.size();
	}
}
