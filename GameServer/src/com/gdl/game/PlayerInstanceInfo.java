package com.gdl.game;

public class PlayerInstanceInfo {
	public long player_id;
	public int state; // 1 在线  2 离开
	public long enter_time;
	public long leave_time;
	public int uid;
	
	public int getGameId() {
		return GameInstanceMrg.getGameId(uid);
	}
	
	public int getLevelId() {
		return GameInstanceMrg.getLevelId(uid);
	}
	
	public int getInstanceId() {
		return GameInstanceMrg.getInstanceId(uid);
	}
}
