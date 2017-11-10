package com.gdl.manager;

import gameserver.event.GameEventDispatcher;
import gameserver.utils.DbMgr;

import java.util.List;
import java.util.Map.Entry;

import javolution.util.FastMap;
import net.schst.EventDispatcher.Event;
import net.schst.EventDispatcher.EventListener;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.commons.thread.WorldEvents;

import databaseshare.DatabaseServer_var;


public class ServerVarManager implements EventListener {
	private static ServerVarManager m_int = new ServerVarManager();
	private ServerVarManager() {}
	public static ServerVarManager getInstance() {return m_int;}
	
	FastMap<String, JSONObject> vars = new FastMap<String, JSONObject>();
	
	public void init() {
		GameEventDispatcher.getInstance().addListener(WorldEvents.TIME_TICK_MINUTE, this);
		
		List<DatabaseServer_var> ddd = DbMgr.getInstance().getShareDB().Select(DatabaseServer_var.class, "");
		if (ddd.isEmpty())
			return ;
		
		for (DatabaseServer_var v : ddd) {
			JSONParser p = new JSONParser();
			try {
				JSONObject vvv = (JSONObject) p.parse(v.var_value);
				vars.put(v.var_key, vvv);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	void save() {
		DatabaseServer_var vvvvvv = new DatabaseServer_var();
		for (Entry<String, JSONObject> en : vars.entrySet()) {
			String k = en.getKey();
			String v = en.getValue().toString();
			
			vvvvvv.var_key = k;
			vvvvvv.var_value = v;
			DbMgr.getInstance().getShareDB().InsertOrUpdate(vvvvvv);
		}
	}
	
	static String niuniu = "niuniu";
	public void addNiuNiuValue(long v) {
		JSONObject vvv = null;
		long cur_ = 0L;
		if (vars.containsKey(niuniu)) {
			vvv = vars.get(vars);
			cur_ = (long)vvv.get("val");
		}
		else {
			vvv = new JSONObject();
			vars.put(niuniu, vvv);
		}
		
		cur_ += v;
		vvv.put("val", cur_);
	}
	
	@Override
	public void handleEvent(Event e) throws Exception {
		save();
	}
}
