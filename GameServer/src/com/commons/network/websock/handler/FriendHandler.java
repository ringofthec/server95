package com.commons.network.websock.handler;

import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.Friend;
import gameserver.jsonprotocol.GDL_C2G_AddFriend;
import gameserver.jsonprotocol.GDL_C2G_DelFriend;
import gameserver.jsonprotocol.GDL_C2G_GetFriendList;
import gameserver.jsonprotocol.GDL_C2G_Like;
import gameserver.jsonprotocol.GDL_G2C_AddFriendRe;
import gameserver.jsonprotocol.GDL_G2C_ChatRe;
import gameserver.jsonprotocol.GDL_G2C_GetFriendListRe;
import gameserver.jsonprotocol.GDL_G2C_LikeRe;
import gameserver.utils.DbMgr;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConManager;
import com.commons.network.websock.PlayerConnection;
import com.commons.util.DatabaseInsertUpdateResult;
import com.gdl.data.PlayerBriefInfo;
import com.gdl.data.PlayerData;
import com.gdl.manager.PlayerBriefInfoManager;

import databaseshare.DatabaseAccount;
import databaseshare.DatabaseFriends;

public class FriendHandler {
	private final static FriendHandler instance = new FriendHandler();
	public static FriendHandler getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(FriendHandler.class);
	
	public void init() {
		HandlerManager.getInstance().pushNornalHandler(FriendHandler.class, this, 
				"OnAddFriend", new GDL_C2G_AddFriend());
		HandlerManager.getInstance().pushNornalHandler(FriendHandler.class, this, 
				"OnDelFriend", new GDL_C2G_DelFriend());
		HandlerManager.getInstance().pushNornalHandler(FriendHandler.class, this, 
				"OnGetFriendList", new GDL_C2G_GetFriendList());
		HandlerManager.getInstance().pushNornalHandler(FriendHandler.class, this, 
				"OnLike", new GDL_C2G_Like());
	}
	
	public void OnLike(PlayerConnection con, GDL_C2G_Like message) {
		long player_id = message.getPlayerId();
		PlayerData p = con.getPlayer();
		int mask_id = (int)player_id + 10000;
		if (p.getItemCountByTempId(mask_id) == 1)
			return ;
		
		p.getItemData().addItem(con, mask_id, 1, Consts.getItemEventLiked());
		
		PlayerConnection other_player = PlayerConManager.getInstance().getCon(player_id);
		if (other_player != null) {
			other_player.getPlayer().getItemData().addItem(other_player, Consts.getLIKEID(), 1, Consts.getItemEventLiked());
			
			GDL_G2C_ChatRe chat = new GDL_G2C_ChatRe();
			chat.setMode(0);
			chat.setPlayerId(0L);
			chat.setPlayerName("[点赞]");
			chat.setMsg(String.format("%s 点赞了你", con.getPlayer().getName()));
			other_player.directSendPack(chat);
		} else {
			DbMgr.getInstance().getDbByPlayerId(player_id).Execute("update item set number=number+1 where player_id=? and table_id=?", 
					player_id, Consts.getLIKEID());
			DbMgr.getInstance().getShareDB().Execute("update player_brief_info set liked=liked+1 where player_id=?", player_id);
		}
		
		GDL_G2C_LikeRe msg = new GDL_G2C_LikeRe();
		con.send(msg);
	}

	public void OnAddFriend(PlayerConnection con, GDL_C2G_AddFriend message) {
		long t_player_id = message.getPlayerId();
		GDL_G2C_AddFriendRe msg = new GDL_G2C_AddFriendRe();
		int coun = DbMgr.getInstance().getShareDB().Count(DatabaseFriends.class,
				"player_id=? and friend_id=?", con.getPlayer().getPlayerId(), t_player_id);
		if (coun == -1 || t_player_id == con.getPlayer().getPlayerId()) {
			logger.error("数据库查询发生严重错误");
			msg.setRetCode(5);
			con.send(msg);
			return ;
		}
		
		if (coun > 0) {
			msg.setRetCode(4);
			con.send(msg);
			return ;
		}
		
		int player_exist = DbMgr.getInstance().getShareDB().Count(DatabaseAccount.class
				, "player_id=?", t_player_id);
		if (player_exist == 0) {
			msg.setRetCode(6);
			con.send(msg);
			return ;
		}
		
		int my_friend_count = DbMgr.getInstance().getShareDB().Count(DatabaseFriends.class,
				"player_id=?", con.getPlayer().getPlayerId());
		if (my_friend_count == -1) {
			msg.setRetCode(5);
			con.send(msg);
			return ;
		}
		
		if (my_friend_count >= 20) {
			msg.setRetCode(1);
			con.send(msg);
			return ;
		}
		
		DatabaseFriends df = new DatabaseFriends();
		df.player_id = con.getPlayer().getPlayerId();
		df.friend_id = t_player_id;
		DatabaseInsertUpdateResult res = DbMgr.getInstance().getShareDB().Insert(df);
		if (res != null && res.succeed) {
			msg.setRetCode(0);
			
			// 同步好友列表
			GDL_G2C_GetFriendListRe re = new GDL_G2C_GetFriendListRe();
			re.setMode(1);
			Friend f = new Friend();
			fillFriendInfo(f, t_player_id);
			re.getFriends().add(f);
			con.send(re);
			ItemHandler.newshowToast(con, 10003);
		} else {
			msg.setRetCode(5);
		}
		
		con.send(msg);
	}
	
	private void fillFriendInfo(Friend f, long player_id) {
		PlayerBriefInfo pbi = PlayerBriefInfoManager.getInstance().getPlayerBriefInfo(player_id);
		if (pbi != null) {
			f.setPlayerId(player_id);
			f.setHead(pbi.getHeadUrl());
			f.setLevel(pbi.getLevel());
			f.setMoney(pbi.getMoney());
			f.setName(pbi.getName());
			f.setViplevel(pbi.getViplevel());
			f.setSex(pbi.getSex());
			f.setOnline(pbi.isOnline() ? 1 : 0);
		}
	}
	
	public void OnDelFriend(PlayerConnection con, GDL_C2G_DelFriend message) {
		DatabaseFriends fir = DbMgr.getInstance().getShareDB().SelectOne(DatabaseFriends.class, 
				"player_id=? and friend_id=?", con.getPlayer().getPlayerId(), message.getPlayerId());
		if (fir == null)
			return ;
		
		DbMgr.getInstance().getShareDB().Delete(DatabaseFriends.class, "player_id=? and friend_id=?", 
				con.getPlayer().getPlayerId(), message.getPlayerId());
		ItemHandler.newshowToast(con, 10004);
	}
	
	public void OnGetFriendList(PlayerConnection con, GDL_C2G_GetFriendList message) {
		List<DatabaseFriends> fir = DbMgr.getInstance().getShareDB().Select(DatabaseFriends.class, 
									"player_id=?", con.getPlayer().getPlayerId());
		if (fir == null) {
			return;
		}
		
		GDL_G2C_GetFriendListRe re = new GDL_G2C_GetFriendListRe();
		re.setMode(0);
		for (DatabaseFriends df : fir) {
			Friend f = new Friend();
			fillFriendInfo(f, df.friend_id);
			re.getFriends().add(f);
		}
		con.send(re);
	}
}
