package gameserver.service;

import gameserver.utils.DbMgr;
import gameserver.utils.UniversalKeyValueStorage;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javolution.util.FastMap;
import net.schst.EventDispatcher.Event;
import net.schst.EventDispatcher.EventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.util.DatabaseUtil;

import database.DatabaseActive_state;
import database.DatabaseGs_server_var;

public class ServerVariableService implements EventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerVariableService.class);
    private static Map<Integer, Map<String, UniversalKeyValueStorage>> serverVariables
    					= new FastMap<Integer, Map<String,UniversalKeyValueStorage>>().shared();
    
    @Override
	public void handleEvent(Event e) throws Exception {
    	save();
	}

	public static void init() {
    	Map<Integer, DatabaseUtil> allgsdb = DbMgr.getInstance().getGSDb();
    	for (Entry<Integer, DatabaseUtil> db : allgsdb.entrySet()) {
    		Map<String, UniversalKeyValueStorage> varmap = new FastMap<String, UniversalKeyValueStorage>().shared();
    		
    		List<DatabaseGs_server_var> vars = db.getValue().Select(DatabaseGs_server_var.class, "");
    		for (DatabaseGs_server_var dbvar : vars) {
    			UniversalKeyValueStorage ukvs = new UniversalKeyValueStorage(dbvar.var_key);
    			ukvs.loadFromString(dbvar.var_value);
    			varmap.put(dbvar.var_key, ukvs);
    			
    			for (Entry<String, Object> entry : ukvs.rawGetStorage().entrySet()) {
    				Long player_id = Long.parseLong(entry.getKey());
    				Long count = (Long) entry.getValue();
    				
    				DatabaseActive_state as = db.getValue().SelectOne(DatabaseActive_state.class, "player_id=?", player_id);
    				if (as != null) {
    					as.param1 = count.intValue();
    					as.save();
    					
    					LOGGER.error("player_id={},count={}", player_id, count);
    				}
    			}
    		}
    		
    		serverVariables.put(db.getKey(), varmap);
    	}
    }

    public static void save() {
        for (Entry<Integer, Map<String, UniversalKeyValueStorage>> sentry : serverVariables.entrySet()) {
        	for (Entry<String, UniversalKeyValueStorage> entry : sentry.getValue().entrySet()) {
        		DatabaseGs_server_var temp = new DatabaseGs_server_var();
            	temp.var_key = entry.getKey();
            	temp.var_value = entry.getValue().toString();
            	
	            try {
	                DbMgr.getInstance().getDb(sentry.getKey()).Update(temp,"");
	            } catch (Exception e) {
	                LOGGER.warn("Failed to save server variable, key={}, value={}", temp.var_key, temp.var_value, e.getMessage());
	            }
        	}
        }
    }

    public static UniversalKeyValueStorage get(long playerId, String key) {
    	int dbid = DbMgr.getInstance().getDbIdByPlayerId(playerId);
    	
    	Map<String, UniversalKeyValueStorage> tempmap = serverVariables.get(dbid);
    	UniversalKeyValueStorage ukvs = tempmap.get(key);
    	if (!tempmap.containsKey(key)) {
    		ukvs = new UniversalKeyValueStorage(key);
    		tempmap.put(key, ukvs);
    		
    		DatabaseGs_server_var temp = new DatabaseGs_server_var();
    		temp.var_key = key;
        	temp.var_value = ukvs.toString();
    		DbMgr.getInstance().getDb(dbid).Insert(temp);
    	}
    	
    	return ukvs;
    }
}