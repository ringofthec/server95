package com.gdl.game;

import gameserver.jsonprotocol.Consts;

public class GameInstanceMrg {
	private static GameInstanceMrg m_int = new GameInstanceMrg();
	private GameInstanceMrg() {}
	public static GameInstanceMrg getInstance() {return m_int;}
	
	public static int getGameId(int uid) {
		int game_id = uid >> 24;
		return game_id;
	}
	
	public static int getLevelId(int uid) {
		int mid = (uid & 0x00ff0000) >> 16;
		return mid;
	}
	
	public static int getInstanceId(int uid) {
		int ins_id = uid & 0x0000ffff;
		return ins_id;
	}
	
	public static int genUnitId(int gameid, int levelid, int instanceid) {
		int uid = (gameid << 24) + (levelid << 16) + instanceid;
		return uid;
	}
	
	public static int convertUIdToGameLevelId(int uid) {
		return uid & 0xffff0000;
	}
	
	public static boolean isRightLevelId(int gameid, int levelid) {
		if (gameid == Consts.getFriutSlot()) {
			return levelid >= 1 && levelid <= 3;
		} else if (gameid == Consts.getGoldSlot()) {
			return levelid >= 1 && levelid <= 3;
		} else if (gameid == Consts.getFisher()) {
			return levelid >= 1 && levelid <= 3;
		}  else if (gameid == Consts.getNiuNiu()) {
			return levelid == 1;
		} else if (gameid == Consts.getBaijiale()) {
			return levelid == 1;
		}
		
		return false;
	}
	
	public static boolean isRightGameId(int gameid) {
		return gameid == Consts.getFriutSlot() || 
				gameid == Consts.getGoldSlot() ||
				gameid == Consts.getFisher() ||
				gameid == Consts.getBaijiale()||
				gameid == Consts.getNiuNiu();
	}
}
