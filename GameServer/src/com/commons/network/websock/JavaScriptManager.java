package com.commons.network.websock;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public final class JavaScriptManager {
	private static JavaScriptManager _int = new JavaScriptManager();
	public static JavaScriptManager getInstance() {
		return _int;
	}
	
	ScriptEngineManager sem;
	ScriptEngine scriptEngine;
	
	private JavaScriptManager() {
		sem = new ScriptEngineManager();
		scriptEngine = sem.getEngineByExtension("js");
	}
	
	public Object executeJs(PlayerConnection con, String code) {
		scriptEngine.put("player_id", con.getPlayer().getPlayerId());
		scriptEngine.put("level", con.getPlayer().getLvl());
		scriptEngine.put("viplevel", con.getPlayer().getVipLvl());
		
		try {
			return scriptEngine.eval(code);
		} catch (ScriptException e) {
			e.printStackTrace();
			return null;
		}
	}
}
