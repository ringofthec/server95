package com.commons.network.websock.handler;

import gameserver.jsonprotocol.CatchFish;
import gameserver.jsonprotocol.ChangeBattery;
import gameserver.jsonprotocol.ChangeBullet;
import gameserver.jsonprotocol.Frozen;
import gameserver.jsonprotocol.GDL_C2G_FishSyncTime;
import gameserver.jsonprotocol.GDL_G2C_FishSyncTimeRe;
import gameserver.jsonprotocol.KillFishes;
import gameserver.jsonprotocol.Laser;
import gameserver.jsonprotocol.Locking;
import gameserver.jsonprotocol.NuclearBomb;
import gameserver.jsonprotocol.Rage;
import gameserver.jsonprotocol.Shooting;
import gameserver.jsonprotocol.Speed;
import gameserver.jsonprotocol.Summone;
import gameserver.jsonprotocol.SwitchLocking;
import gameserver.jsonprotocol.UnlockBullet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConnection;
import com.commons.util.TimeUtil;
import com.gdl.data.ItemData;
import com.gdl.data.PlayerData;
import com.gdl.game.FishGameInfo;
import com.gdl.game.FishGameInstanceManager;
import com.gdl.game.FishGameMrg;

public class FishGameHandler {
	private final static FishGameHandler instance = new FishGameHandler();
	public static FishGameHandler getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(FishGameHandler.class);
	
	public void init() {
		HandlerManager.getInstance().pushNornalHandler(FishGameHandler.class, this, 
				"UnlockBullet", new UnlockBullet());
		
		HandlerManager.getInstance().pushNornalHandler(FishGameHandler.class, this, 
				"ChangeBullet", new ChangeBullet());
		
		HandlerManager.getInstance().pushNornalHandler(FishGameHandler.class, this, 
				"ChangeBattery", new ChangeBattery());
		
		HandlerManager.getInstance().pushNornalHandler(FishGameHandler.class, this, 
				"Shooting", new Shooting());
		
		HandlerManager.getInstance().pushNornalHandler(FishGameHandler.class, this, 
				"CatchFish", new CatchFish());
		
		HandlerManager.getInstance().pushNornalHandler(FishGameHandler.class, this, 
				"Summone", new Summone());
		
		HandlerManager.getInstance().pushNornalHandler(FishGameHandler.class, this, 
				"NuclearBomb", new NuclearBomb());
		
		HandlerManager.getInstance().pushNornalHandler(FishGameHandler.class, this, 
				"Frozen", new Frozen());
		
		HandlerManager.getInstance().pushNornalHandler(FishGameHandler.class, this, 
				"Locking", new Locking());
		
		HandlerManager.getInstance().pushNornalHandler(FishGameHandler.class, this, 
				"SwitchLocking", new SwitchLocking());
		
		HandlerManager.getInstance().pushNornalHandler(FishGameHandler.class, this, 
				"Speed", new Speed());
		
		HandlerManager.getInstance().pushNornalHandler(FishGameHandler.class, this, 
				"Rage", new Rage());
		
		HandlerManager.getInstance().pushNornalHandler(FishGameHandler.class, this, 
				"Laser", new Laser());
		
		HandlerManager.getInstance().pushNornalHandler(FishGameHandler.class, this, 
				"KillFishes", new KillFishes());
		
		HandlerManager.getInstance().pushNornalHandler(FishGameHandler.class, this, 
				"OnFishSyncTime", new GDL_C2G_FishSyncTime());
	}
	
	public void KillFishes(PlayerConnection con, KillFishes message) {
		FishGameInfo gfi = FishGameInstanceManager.getInstance().getPlayerFishInstance(con);
		if (gfi == null)
			return ;
		
		PlayerData pData = con.getPlayer();
		ItemData pItem = pData.getItemData();
		FishGameMrg.getInstance().procKillFishes(gfi, con, pData, pItem, message);
	}
	
	public void Rage(PlayerConnection con, Rage message) {
		FishGameInfo gfi = FishGameInstanceManager.getInstance().getPlayerFishInstance(con);
		if (gfi == null)
			return ;
		
		PlayerData pData = con.getPlayer();
		ItemData pItem = pData.getItemData();
		FishGameMrg.getInstance().procRage(gfi, con, pData, pItem, message);
	}
	
	public void Laser(PlayerConnection con, Laser message) {
		FishGameInfo gfi = FishGameInstanceManager.getInstance().getPlayerFishInstance(con);
		if (gfi == null)
			return ;
		
		PlayerData pData = con.getPlayer();
		ItemData pItem = pData.getItemData();
		FishGameMrg.getInstance().procLaser(gfi, con, pData, pItem, message);
	}
	
	public void Speed(PlayerConnection con, Speed message) {
		FishGameInfo gfi = FishGameInstanceManager.getInstance().getPlayerFishInstance(con);
		if (gfi == null)
			return ;
		
		PlayerData pData = con.getPlayer();
		ItemData pItem = pData.getItemData();
		FishGameMrg.getInstance().procSpeed(gfi, con, pData, pItem, message);
	}
	
	public void SwitchLocking(PlayerConnection con, SwitchLocking message) {
		FishGameInfo gfi = FishGameInstanceManager.getInstance().getPlayerFishInstance(con);
		if (gfi == null)
			return ;
		
		PlayerData pData = con.getPlayer();
		ItemData pItem = pData.getItemData();
		FishGameMrg.getInstance().procSwitchLocking(gfi, con, pData, pItem, message);
	}
	
	public void Locking(PlayerConnection con, Locking message) {
		FishGameInfo gfi = FishGameInstanceManager.getInstance().getPlayerFishInstance(con);
		if (gfi == null)
			return ;
		
		PlayerData pData = con.getPlayer();
		ItemData pItem = pData.getItemData();
		FishGameMrg.getInstance().procLocking(gfi, con, pData, pItem, message);
	}
	
	public void Frozen(PlayerConnection con, Frozen message) {
		FishGameInfo gfi = FishGameInstanceManager.getInstance().getPlayerFishInstance(con);
		if (gfi == null)
			return ;
		
		PlayerData pData = con.getPlayer();
		ItemData pItem = pData.getItemData();
		FishGameMrg.getInstance().procFrozen(gfi, con, pData, pItem, message);
	}
	
	public void NuclearBomb(PlayerConnection con, NuclearBomb message) {
		FishGameInfo gfi = FishGameInstanceManager.getInstance().getPlayerFishInstance(con);
		if (gfi == null)
			return ;
		
		PlayerData pData = con.getPlayer();
		ItemData pItem = pData.getItemData();
		FishGameMrg.getInstance().procNuclearBomb(gfi, con, pData, pItem, message);
	}
	
	public void Summone(PlayerConnection con, Summone message) {
		FishGameInfo gfi = FishGameInstanceManager.getInstance().getPlayerFishInstance(con);
		if (gfi == null)
			return ;
		
		PlayerData pData = con.getPlayer();
		ItemData pItem = pData.getItemData();
		FishGameMrg.getInstance().procSummone(gfi, con, pData, pItem, message);
	}
	
	public void CatchFish(PlayerConnection con, CatchFish message) {
		FishGameInfo gfi = FishGameInstanceManager.getInstance().getPlayerFishInstance(con);
		if (gfi == null)
			return ;
		
		PlayerData pData = con.getPlayer();
		ItemData pItem = pData.getItemData();
		FishGameMrg.getInstance().procCatchFish(gfi, con, pData, pItem, message);
	}
	
	public void Shooting(PlayerConnection con, Shooting message) {
		FishGameInfo gfi = FishGameInstanceManager.getInstance().getPlayerFishInstance(con);
		if (gfi == null)
			return ;
		
		PlayerData pData = con.getPlayer();
		ItemData pItem = pData.getItemData();
		FishGameMrg.getInstance().procShooting(gfi, con, pData, pItem, message);
	}
	
	public void ChangeBattery(PlayerConnection con, ChangeBattery message) {
		FishGameInfo gfi = FishGameInstanceManager.getInstance().getPlayerFishInstance(con);
		if (gfi == null)
			return ;
		
		PlayerData pData = con.getPlayer();
		ItemData pItem = pData.getItemData();
		FishGameMrg.getInstance().procChangeBattery(gfi, con, pData, pItem, message);
	}
	
	public void ChangeBullet(PlayerConnection con, ChangeBullet message) {
		FishGameInfo gfi = FishGameInstanceManager.getInstance().getPlayerFishInstance(con);
		if (gfi == null)
			return ;
		
		PlayerData pData = con.getPlayer();
		ItemData pItem = pData.getItemData();
		FishGameMrg.getInstance().procChangeBullet(gfi, con, pData, pItem, message);
	}
	
	public void UnlockBullet(PlayerConnection con, UnlockBullet message) {
		FishGameInfo gfi = FishGameInstanceManager.getInstance().getPlayerFishInstance(con);
		if (gfi == null)
			return ;
		
		PlayerData pData = con.getPlayer();
		ItemData pItem = pData.getItemData();
		FishGameMrg.getInstance().procUnlockBullet(gfi, con, pData, pItem, message);
	}
	
	public void OnFishSyncTime(PlayerConnection con, GDL_C2G_FishSyncTime message) {
		FishGameInfo gfi = FishGameInstanceManager.getInstance().getPlayerFishInstance(con);
		if (gfi == null)
			return ;
		
		GDL_G2C_FishSyncTimeRe re = new GDL_G2C_FishSyncTimeRe();
		re.setFrozening_time(gfi.getSurplusFrozenTime(TimeUtil.GetDateTime()));
		re.setPass_time(gfi.delteTime());
		con.send(re);
	}
}
