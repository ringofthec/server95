package com.gdl.game;

import gameserver.jsonprotocol.GDL_G2C_EnterGameRe;
import gameserver.jsonprotocol.GDL_G2C_LeaveGameRe;
import gameserver.jsonprotocol.GDL_G2C_PlayerJoinGame;
import gameserver.jsonprotocol.GDL_G2C_PlayerLeaveGame;
import gameserver.jsonprotocol.SlotGamePlayerInfo;
import gameserver.jsonprotocol.SlotsGameStatus;
import javolution.util.FastMap;

import com.commons.network.websock.JsonPacket;
import com.commons.network.websock.PlayerConManager;
import com.commons.network.websock.PlayerConnection;
import com.commons.network.websock.handler.ChatHandler;
import com.commons.util.TimeUtil;
import com.gdl.data.PlayerBriefInfo;
import com.gdl.data.PlayerData;
import com.gdl.manager.PlayerBriefInfoManager;

public class SlotsGameInfo {
	int gameUid;
	long begin_time;

	public static class PlayerInfo {
		public PlayerInfo(long pid, long jtime) {
			player_id = pid;
			join_time = jtime;
		}
		
		long player_id;
		long join_time;
	}
	
	FastMap<Long, PlayerInfo> player_infos = new FastMap<Long, SlotsGameInfo.PlayerInfo>();
	
	public SlotsGameInfo(int uid) {
		gameUid = uid;
	}
	
	public int getUid() {
		return gameUid;
	}
	
	public int getGameId() {
		return GameInstanceMrg.getGameId(gameUid);
	}

	public void setGameUID(int gameUid) {
		this.gameUid = gameUid;
	}

	// 开始游戏
	private void beginGame() {
		begin_time = TimeUtil.GetDateTime();
	}
	
	private int getRobotNum() {
		int num = 0;
		for (long pid : player_infos.keySet()) {
			if (ChatHandler.isRobot(pid))
				num++;
		}
		return num;
	}
	
	public boolean isFull(boolean isRobot) {
		if (!isRobot){
			return player_infos.size() >= 4;
		}
		else {
			if (player_infos.size() >= 4)
				return true;
			
			int num = getRobotNum();
			return num >= 2;
		}
	}
	
	public boolean isEmpty() {
		return player_infos.isEmpty();
	}
	
	// 向同一副本中的玩家广播消息
	public void broadcast(JsonPacket msg) {
		PlayerConManager.getInstance().broadcastMsg(msg, player_infos.keySet());
	}
	
	// 加入游戏
	public void joinGame(PlayerConnection con, int uid) {
		if (player_infos.isEmpty())
			beginGame();
		
		PlayerData pData = con.getPlayer();
		long player_id = pData.getPlayerId();
		
		{
			// 向其他玩家发送这个玩家加入游戏的消息
			GDL_G2C_PlayerJoinGame joinmsg = new GDL_G2C_PlayerJoinGame();
			joinmsg.setPlayerId(con.getPlayerId());
			joinmsg.setGameId(getGameId());
			joinmsg.setSplayerInfos(fillSlotPlayerInfo(pData));
			broadcast(joinmsg);
		}
		
		{
			// 把玩家加入游戏中
			player_infos.put(player_id, new PlayerInfo(player_id, TimeUtil.GetDateTime()));
			con.getPlayer().setGameUID(uid);
		}
		
		{
			// 向玩家本身同步成功进入游戏的消息
			GDL_G2C_EnterGameRe re = new GDL_G2C_EnterGameRe();
			re.setGameId(getGameId());
			re.setInstanceId(gameUid);
			re.setSlotsGameInfo(fillSlotGameStatus());
			con.send(re);
		}
	}

	private SlotsGameStatus fillSlotGameStatus() {
		SlotsGameStatus slotsGameInfo = new SlotsGameStatus();
		for (PlayerInfo tti : player_infos.values()) {
			PlayerBriefInfo pbreeinf = PlayerBriefInfoManager.getInstance().getPlayerBriefInfo(tti.player_id);
			if (pbreeinf == null)
				continue;
			
			slotsGameInfo.getPlayerInfos().add( fillSlotPlayerInfo(tti, pbreeinf) );
		}
		return slotsGameInfo;
	}

	private SlotGamePlayerInfo fillSlotPlayerInfo(PlayerInfo tti,
			PlayerBriefInfo pbreeinf) {
		SlotGamePlayerInfo jjdfd = new SlotGamePlayerInfo();
		jjdfd.setPlayerId(tti.player_id);
		jjdfd.setHeadUrl(pbreeinf.getHeadUrl());
		jjdfd.setName(pbreeinf.getName());
		jjdfd.setViplevel(pbreeinf.getViplevel());
		jjdfd.setCoin(pbreeinf.getMoney());
		jjdfd.setGiftid(pbreeinf.getFristGiftId());
		return jjdfd;
	}

	private SlotGamePlayerInfo fillSlotPlayerInfo(PlayerData pData) {
		SlotGamePlayerInfo fgp = new SlotGamePlayerInfo();
		fgp.setPlayerId(pData.getPlayerId());
		fgp.setViplevel(pData.getVipLvl());
		fgp.setName(pData.getName());
		fgp.setHeadUrl(pData.getHeadUrl());
		fgp.setCoin(pData.getCoin());
		fgp.setGiftid(pData.getFristGiftId());
		return fgp;
	}
	
	public void leaveGame(PlayerConnection con) {
		player_infos.remove(con.getPlayerId());
		con.getPlayer().setGameUID(0);
		
		GDL_G2C_PlayerLeaveGame leave = new GDL_G2C_PlayerLeaveGame();
		leave.setPlayerId(con.getPlayerId());
		broadcast(leave);
		con.getPlayer().setCurSlots(null, 0, 0, false);
		con.getPlayer().setFreeSlot(0, 0);
		
		GDL_G2C_LeaveGameRe re = new GDL_G2C_LeaveGameRe();
		con.send(re);
	}
}
