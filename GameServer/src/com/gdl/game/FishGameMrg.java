package com.gdl.game;

import gameserver.jsonprotocol.CatchFish;
import gameserver.jsonprotocol.CatchFishSync;
import gameserver.jsonprotocol.ChangeBattery;
import gameserver.jsonprotocol.ChangeBatterySync;
import gameserver.jsonprotocol.ChangeBullet;
import gameserver.jsonprotocol.ChangeBulletSync;
import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.DropItem;
import gameserver.jsonprotocol.FishMoneySync;
import gameserver.jsonprotocol.Frozen;
import gameserver.jsonprotocol.FrozenSync;
import gameserver.jsonprotocol.KillFishes;
import gameserver.jsonprotocol.KillFishesSync;
import gameserver.jsonprotocol.Laser;
import gameserver.jsonprotocol.LaserSync;
import gameserver.jsonprotocol.Locking;
import gameserver.jsonprotocol.LockingSync;
import gameserver.jsonprotocol.NuclearBomb;
import gameserver.jsonprotocol.NuclearBombSync;
import gameserver.jsonprotocol.Rage;
import gameserver.jsonprotocol.RageSync;
import gameserver.jsonprotocol.Shooting;
import gameserver.jsonprotocol.ShootingSync;
import gameserver.jsonprotocol.Speed;
import gameserver.jsonprotocol.SpeedSync;
import gameserver.jsonprotocol.Summone;
import gameserver.jsonprotocol.SummoneSync;
import gameserver.jsonprotocol.SwitchLocking;
import gameserver.jsonprotocol.SwitchLockingSync;
import gameserver.jsonprotocol.UnlockBullet;
import gameserver.jsonprotocol.UnlockBulletSync;
import gameserver.logging.LogService;

import java.util.List;

import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_GFBullet;
import table.MT_Data_GFCannon;
import table.MT_Data_GFSkill;
import table.base.TableManager;

import com.commons.network.websock.PlayerConnection;
import com.commons.network.websock.handler.ChatHandler;
import com.commons.network.websock.handler.ShopHandler;
import com.gdl.data.ItemData;
import com.gdl.data.PlayerData;
import com.gdl.manager.GamePoolManager;

public class FishGameMrg {
	
	private final Logger logger = LoggerFactory.getLogger(FishGameMrg.class);
	
	private static FishGameMrg m_int = new FishGameMrg();
	private FishGameMrg() {}
	public static FishGameMrg getInstance() {return m_int;}
	
	boolean[] isPoolSceneRunning = {false, false, false, false};
	public int enterPoolScene(int levelid) {
		synchronized (FishGameMrg.class) {
			if (isPoolSceneRunning[levelid]) {
				LogService.sysErr(1, "pool is doing", levelid);
				return 0;
			}
			
			long coin = GamePoolManager.getInstance().checkPoolReward(Consts.getFisher(), levelid, false);
			if (coin <= 0) {
				LogService.sysErr(2, "coin is not enough " + coin, levelid);
				return 0;
			}
			
			isPoolSceneRunning[levelid] = true;
		}
		
		return FishGameConfig.getInstance().getPoolScene(levelid);
	}
	
	public void leavePoolScene(int levelid) {
		synchronized (FishGameMrg.class) {
			if (!isPoolSceneRunning[levelid]) {
				LogService.sysErr(1, "奖池严重错误", levelid);
			}
			
			LogService.sysErr(1, "fish pool leave~~~~~~~~~~~~", 1);
			isPoolSceneRunning[levelid] = false;
		}
	}
	
	public void procUnlockBullet(FishGameInfo gfi, PlayerConnection con, PlayerData pData, ItemData iData, UnlockBullet msg) {
		int cur_bullet_level = (int)iData.getItemCountByTempId(Consts.getMaxBullet());
		int next_bullet_level = cur_bullet_level + 1;
		if (!TableManager.GetInstance().TableGFBullet().Contains(next_bullet_level))
			return ;
		
		// vip等级判定
		MT_Data_GFBullet config = TableManager.GetInstance().TableGFBullet().GetElement(next_bullet_level);
		if (pData.getVipLvl() < config.unlock_vip())
			return ;
		
		// 门厅限制
		int instanceId = pData.getGameUID();
		int levelid = GameInstanceMrg.getLevelId(instanceId);
		if (!config.unlock_door().contains(levelid))
			return ;
		
		// 这一步是最后一步，判定并扣除花费
		if (!ShopHandler.testCost(con, config.unlock_costId(), 1))
			return ;
		
		// 子弹等级+1
		iData.setItem(con, Consts.getMaxBullet(), next_bullet_level, -13);
		iData.setItem(con, Consts.getCurBullet(), next_bullet_level, -13);
		
		UnlockBulletSync sync = new UnlockBulletSync();
		sync.setType(next_bullet_level);
		sync.setPlayerId(con.getPlayerId());
		ChangeBulletSync sync1 = new ChangeBulletSync();
		sync1.setType(next_bullet_level);
		sync1.setPlayerId(con.getPlayerId());
		synchronized (gfi) {
			gfi.broadcast(sync);
			gfi.broadcast(sync1);
		}
		
		if (config.rate() >= 1000)
			ChatHandler.newbanner(49, con.getPlayer().getName(), con.getPlayerId(), config.rate());
	}
	
	public void procChangeBullet(FishGameInfo gfi, PlayerConnection con, PlayerData pData, ItemData iData, ChangeBullet msg) {
		int max_bullet = (int)iData.getItemCountByTempId(Consts.getMaxBullet());
		int will_bullet = msg.getType();
		
		// 不能切换到
		if (will_bullet > max_bullet)
			return ;
		
		// 神奇的错误，应该不会发生
		if (!TableManager.GetInstance().TableGFBullet().Contains(will_bullet))
			return ;
		
		// 门厅限制
		int instanceId = pData.getGameUID();
		int levelid = GameInstanceMrg.getLevelId(instanceId);
		MT_Data_GFBullet config = TableManager.GetInstance().TableGFBullet().GetElement(will_bullet);
		if (!config.unlock_door().contains(levelid))
			return ;
		
		iData.setItem(con, Consts.getCurBullet(), will_bullet, -11);
		
		ChangeBulletSync sync = new ChangeBulletSync();
		sync.setType(will_bullet);
		sync.setPlayerId(con.getPlayerId());
		synchronized (gfi) {
			gfi.flushButtetId(con.getPlayerId(), will_bullet);
			gfi.broadcast(sync);
		}
	}
	
	public void procChangeBattery(FishGameInfo gfi, PlayerConnection con, PlayerData pData, ItemData iData, ChangeBattery msg) {
		int vip_lvl = pData.getVipLvl();
		MT_Data_GFCannon config = TableManager.GetInstance().TableGFCannon().GetElement(vip_lvl);
		
		// vip 等级限制
		if (vip_lvl < config.bullet_skin())
			return ;
		
		if (msg.getType() > vip_lvl)
			return ;
		
		iData.setItem(con, Consts.getCurBattery(), msg.getType(), -10);
		
		ChangeBatterySync sync = new ChangeBatterySync();
		sync.setType(msg.getType());
		sync.setPlayerId(con.getPlayerId());
		synchronized (gfi) {
			gfi.flushButtetId(con.getPlayerId(), msg.getType());
			gfi.broadcast(sync);
		}
	}
	
	public void procShooting(FishGameInfo gfi, PlayerConnection con, PlayerData pData, ItemData iData, Shooting msg) {
		int cur_bullet_level = (int)iData.getItemCountByTempId(Consts.getCurBullet());
		if (!TableManager.GetInstance().TableGFBullet().Contains(cur_bullet_level))
			return ;
		
		// 检查是否有金币可以发射
		// 注意这里只是检查有没有金币，并不扣
		MT_Data_GFBullet bullconfig = TableManager.GetInstance().TableGFBullet().GetElement(cur_bullet_level);
		if (!ShopHandler.testCost(con, bullconfig.cost_id(), Consts.getItemEventFishCatchCost(), true, false)) {
			moneySync(gfi, con, iData);
			return ;
		}
		
		int pre_money = ShopHandler.getCostCoinNum(bullconfig.cost_id());
		if (iData.getItemCountByTempId(140) > 0)
			iData.addItem(con, 141, pre_money, Consts.getItemEventFishCatchCost());
		else
			iData.addItem(con, Consts.getGame_Cost_Count(), pre_money, Consts.getItemEventFishCatchCost());
		
		ShootingSync sync = new ShootingSync();
		sync.setAngle(msg.getAngle());
		sync.setXp((int)iData.getItemCountByTempId(Consts.getFishXPSkillPoint()));
		sync.setPlayerId(con.getPlayerId());
		synchronized (gfi) {
			gfi.addBullet(con.getPlayerId(), cur_bullet_level);
			gfi.broadcast(sync);
			moneySync(gfi, con, iData);
		}
	}
	
	public void procCatchFish(FishGameInfo gfi, PlayerConnection con, PlayerData pData, ItemData iData, CatchFish msg) {	
		int cur_bullet_level = msg.getType();
		int num = msg.getNum();
		if (num <= 0 && num >= 30)
			return ;
		
		if (!TableManager.GetInstance().TableGFBullet().Contains(cur_bullet_level))
			return ;
		
		synchronized (gfi) {
			if (!gfi.costBullet(con.getPlayerId(), cur_bullet_level, num))
				return ;
		}
		
		MT_Data_GFBullet bullconfig = TableManager.GetInstance().TableGFBullet().GetElement(cur_bullet_level);
		int pre_money = ShopHandler.getCostCoinNum(bullconfig.cost_id());
		int money = pre_money * num;
		
		// 
		int policy_id = SlotGameMrg.getRechargePolicyId(con, pre_money, Consts.getFisher());
		
		int catch_fish_rate = 0;
		CatchFishSync sync = new CatchFishSync();
		sync.setPlayerId(con.getPlayerId());
		sync.setType(cur_bullet_level);
		synchronized (gfi) {
			if (gfi.isInSpeed(con)) {
				MT_Data_GFSkill skillcon = TableManager.GetInstance().TableGFSkill().GetElement(16);
				catch_fish_rate += skillcon.addRate();
			}
			
			List<Integer> fishcatch_data = msg.getIndexAndNum();
			int fcount = fishcatch_data.size() / 2;
			for (int i = 0; i < fcount; ++i) {
				int fishIdx = fishcatch_data.get(i * 2);
				int fishCount = fishcatch_data.get(i * 2 + 1);
				if (fishCount > num)
					continue;
				
				gfi.catchFish(con, fishIdx, bullconfig.damage(), 
						catch_fish_rate, bullconfig.rate(), 
						sync.getDropInfo(), 0, policy_id, fishCount, pre_money);
			}
			
			if (!sync.getDropInfo().isEmpty())
				gfi.broadcast(sync);
			gfi.checkBoss(con);
		}
		
		if (sync.getDropInfo().isEmpty()) {
			int cost_coin = money;
			// xp 值累计
			if (cost_coin != -1) {
				int temp_xp = (int)(cost_coin * 0.05);
				if (temp_xp == 0)
					temp_xp = 1;
				iData.addItem(con, Consts.getFishXPSkillPoint(), temp_xp, Consts.getItemEventFishCatchCost(), false);
			}
			
			if (cost_coin > 0) {
				GamePoolManager.getInstance().addPoolReward(Consts.getFisher(), gfi.getLevelId(), cost_coin, 0.05f);
			}
			return ;
		}
		
		realGiveDropItem(con, gfi, iData, sync.getDropInfo(), Consts.getItemEventFishCatchGet(), pre_money);
	}
	
	public void procKillFishes(FishGameInfo gfi, PlayerConnection con, PlayerData pData, ItemData iData, KillFishes msg) {	
		int cur_bullet_level = msg.getType();
		if (!TableManager.GetInstance().TableGFBullet().Contains(cur_bullet_level))
			return ;
		
		synchronized (gfi) {
			if (!gfi.costBullet(con.getPlayerId(), cur_bullet_level, 1))
				return ;
		}
		
		// 注意这里是检查并扣除金币
		MT_Data_GFBullet bullconfig = TableManager.GetInstance().TableGFBullet().GetElement(cur_bullet_level);
		int cost_coin = ShopHandler.getCostCoinNum(bullconfig.cost_id());
		
		// 
		int policy_id = SlotGameMrg.getRechargePolicyId(con, cost_coin, Consts.getFisher());
		
		int catch_fish_rate = 0;
		KillFishesSync sync = new KillFishesSync();
		sync.setPlayerId(con.getPlayerId());
		synchronized (gfi) {
			if (gfi.isInSpeed(con)) {
				MT_Data_GFSkill skillcon = TableManager.GetInstance().TableGFSkill().GetElement(16);
				catch_fish_rate += skillcon.addRate();
			}
			
			boolean ret = gfi.killFish(con, msg, bullconfig.damage(), catch_fish_rate, bullconfig.rate(), sync.getDropInfo(), 0, policy_id);
			if (ret)
				gfi.broadcast(sync);
			gfi.checkBoss(con);
		}
		
		if (sync.getDropInfo().isEmpty()) {
			iData.addItem(con, Consts.getFishXPSkillPoint(), cost_coin, Consts.getItemEventFishCatchCost());
			if (cost_coin > 0) {
				GamePoolManager.getInstance().addPoolReward(Consts.getFisher(), gfi.getLevelId(), cost_coin, 0.05f);
			}
			return ;
		}
		
		realGiveDropItem(con, gfi, iData, sync.getDropInfo(), Consts.getItemEventFishCatchGetKill(), 0);
	}
	
	private int genCoinValue(FastMap<Integer, Integer> item_add, int pre_money) {
		int coin_val = 0; 
		for (int item_id : item_add.keySet()) {
			int num = item_add.get(item_id);
			if (item_id == Consts.getCOIN_ID()) {
				coin_val += num;
			} else if (item_id == Consts.getGOLD_ID()) {
				coin_val += num * Consts.getCoin2Gold();
			}
		}
		return coin_val;
	}
	
	private void realGiveDropItem(PlayerConnection con, FishGameInfo gfi, ItemData iData,
			List< DropItem > droplist, int itemevent, int pre_money) {
		FastMap<Integer, Integer> item_add = new FastMap<Integer, Integer>();
		for (DropItem di : droplist) {
			int count = di.getId().size();
			for (int i = 0 ; i < count; ++i) {
				int item_id = di.getId().get(i);
				long num = di.getNum().get(i);
				
				int org_num = 0;
				if (item_add.containsKey(item_id))
					org_num = item_add.get(item_id);
				
				org_num += num;
				item_add.put(item_id, org_num);
			}
		}
		
		int coin_value = genCoinValue(item_add, pre_money);
		if (coin_value > 0) {
			iData.addItem(con, Consts.getEXP_ID(), coin_value, 0, false);
			// 记录净赢取
			iData.addItem(con, Consts.getDay_Earn_Count(), coin_value, itemevent, false);
			if (iData.getItemCountByTempId(140) > 0)
				iData.addItem(con, 142, coin_value, itemevent, false);
			else
				iData.addItem(con, Consts.getGame_Earn_Count(), coin_value, itemevent, false);
			gfi.cachePlayerMoney(con.getPlayerId(), coin_value);
		}
		
		boolean money_change = false;
		for (int item_id : item_add.keySet()) {
			int num = item_add.get(item_id);
			int limit = TableManager.GetInstance().getSkillLimit(item_id);
			if (limit > 0) {
				int org_num = (int)iData.getItemCountByTempId(item_id);
				if (num + org_num >= limit) {
					num = limit - org_num;
				}
			}
			
			iData.addItem(con, item_id, num, itemevent, false);
			if (item_id == Consts.getCOIN_ID() ||
					item_id == Consts.getGOLD_ID())
				money_change = true;
		}
		
		if (money_change)
			moneySync(gfi, con, iData);
	}
	
	private void moneySync(FishGameInfo gfi, PlayerConnection con, ItemData iData) {
		FishMoneySync sy = new FishMoneySync();
		sy.setPlayerId(con.getPlayerId());
		sy.setCoin(iData.getItemCountByTempId(Consts.getCOIN_ID()));
		sy.setGold(iData.getItemCountByTempId(Consts.getGOLD_ID()));
		synchronized (gfi) {
			gfi.broadcast(sy);
		}
	}
	
	public void procSummone(FishGameInfo gfi, PlayerConnection con, PlayerData pData, ItemData iData, Summone msg) {
		MT_Data_GFSkill skillcon = TableManager.GetInstance().TableGFSkill().GetElement(15);
		if (skillcon.scene_limit().get(gfi.getSceneType()) != 1)
			return ;
		
		if (!iData.costCoolDown(con, 123, skillcon.cd() * 1000, false))
			return ;
		
		if (!ShopHandler.testItemOrCost(con, 15, 1, skillcon.costId(), Consts.getItemEventSummon()))
			return ;
		
		iData.costCoolDown(con, 123, skillcon.cd() * 1000);
	
		SummoneSync sync = new SummoneSync();
		sync.setPlayerId(con.getPlayerId());
		synchronized (gfi) {
			gfi.Summone(sync);
			gfi.broadcast(sync);
		}
	}

	public void procNuclearBomb(FishGameInfo gfi, PlayerConnection con, PlayerData pData, ItemData iData, NuclearBomb msg) {
		MT_Data_GFSkill skillcon = TableManager.GetInstance().TableGFSkill().GetElement(14);
		if (skillcon.scene_limit().get(gfi.getSceneType()) != 1)
			return ;
		
		if (!iData.costCoolDown(con, skillcon.cdItemId(), skillcon.cd() * 1000, false))
			return ;
		
		if (!ShopHandler.testItemOrCost(con, 14, 1, skillcon.costId(), Consts.getItemEventNuclearBomb()))
			return ;
		
		iData.costCoolDown(con, skillcon.cdItemId(), skillcon.cd() * 1000);
	
		int levelid = GameInstanceMrg.getLevelId(pData.getGameUID());
		NuclearBombSync sync = new NuclearBombSync();
		sync.setPlayerId(con.getPlayerId());
		sync.setPosX(msg.getPosX());
		sync.setPosY(msg.getPosY());
		synchronized (gfi) {
			for (int fidx : msg.getFishIndexs())
				gfi.catchFish(con, fidx, 9999999, 10000, Consts.getFishMenTingNulearRate(levelid), sync.getDropInfo(), 3, 2, 1, 0);
			gfi.broadcast(sync);
			gfi.checkBoss(con);
		}
		realGiveDropItem(con, gfi, iData, sync.getDropInfo(), Consts.getItemEventFishCatchGetNnclear(), 0);
	}

	public void procFrozen(FishGameInfo gfi, PlayerConnection con, PlayerData pData, ItemData iData, Frozen msg) {
		MT_Data_GFSkill skillcon = TableManager.GetInstance().TableGFSkill().GetElement(13);
		if (skillcon.scene_limit().get(gfi.getSceneType()) != 1)
			return ;
		
		if (!iData.costCoolDown(con, skillcon.cdItemId(), skillcon.cd() * 1000, false))
			return ;
		
		if (!ShopHandler.testItemOrCost(con, 13, 1, skillcon.costId(), Consts.getItemEventFrozen()))
			return ;
		
		iData.costCoolDown(con, skillcon.cdItemId(), skillcon.cd() * 1000);
		
		FrozenSync sync = new FrozenSync();
		sync.setPlayerId(con.getPlayerId());
		
		synchronized (gfi) {
			gfi.frozenSkill();
			gfi.broadcast(sync);
		}
	}

	public void procLocking(FishGameInfo gfi, PlayerConnection con, PlayerData pData, ItemData iData, Locking msg) {
		MT_Data_GFSkill skillcon = TableManager.GetInstance().TableGFSkill().GetElement(12);
		if (skillcon.scene_limit().get(gfi.getSceneType()) != 1)
			return ;
		
		if (!iData.costCoolDown(con, skillcon.cdItemId(), skillcon.cd() * 1000, false))
			return ;
		
		if (!ShopHandler.testItemOrCost(con, 12, 1, skillcon.costId(), Consts.getItemEventLocking()))
			return ;
		
		iData.costCoolDown(con, skillcon.cdItemId(), skillcon.cd() * 1000);
		
		LockingSync sync = new LockingSync();
		sync.setPlayerId(con.getPlayerId());
		synchronized (gfi) {
			gfi.broadcast(sync);
		}
	}
	
	public void procSwitchLocking(FishGameInfo gfi, PlayerConnection con, PlayerData pData, ItemData iData, SwitchLocking msg) {
		if (!gfi.isFishExsits(msg.getFishIndex()))
			return ;
		
		SwitchLockingSync sync = new SwitchLockingSync();
		sync.setFishIndex(msg.getFishIndex());
		sync.setPlayerId(con.getPlayerId());
		synchronized (gfi) {
			gfi.broadcast(sync);
		}
	}
	
	public void procSpeed(FishGameInfo gfi, PlayerConnection con, PlayerData pData, ItemData iData, Speed msg) {
		MT_Data_GFSkill skillcon = TableManager.GetInstance().TableGFSkill().GetElement(16);
		if (skillcon.scene_limit().get(gfi.getSceneType()) != 1)
			return ;
		
		if (!iData.costCoolDown(con, skillcon.cdItemId(), skillcon.cd() * 1000, false))
			return ;
		
		if (!ShopHandler.testItemOrCost(con, 16, 1, skillcon.costId(), Consts.getItemEventSpeed()))
			return ;
		
		iData.costCoolDown(con, skillcon.cdItemId(), skillcon.cd() * 1000);
		SpeedSync sync = new SpeedSync();
		sync.setPlayerId(con.getPlayerId());
		
		synchronized (gfi) {
			gfi.Speed(con, skillcon);
			gfi.broadcast(sync);
		}
	}
	
	public void procRage(FishGameInfo gfi, PlayerConnection con, PlayerData pData, ItemData iData, Rage msg) {
		MT_Data_GFSkill skillcon = TableManager.GetInstance().TableGFSkill().GetElement(17);
		if (skillcon.scene_limit().get(gfi.getSceneType()) != 1)
			return ;
		
		if (!iData.costCoolDown(con, skillcon.cdItemId(), skillcon.cd() * 1000, false))
			return ;
		
		if (!ShopHandler.testItemOrCost(con, 17, 1, skillcon.costId(), Consts.getItemEventRage()))
			return ;
		
		iData.costCoolDown(con, skillcon.cdItemId(), skillcon.cd() * 1000);
		
		RageSync sync = new RageSync();
		sync.setPlayerId(con.getPlayerId());
		synchronized (gfi) {
			gfi.broadcast(sync);
		}
	}
	
	public void procLaser(FishGameInfo gfi, PlayerConnection con, PlayerData pData, ItemData iData, Laser msg) {
		int limit = Consts.getFishLaserCoinCost( gfi.getLevelId() );
		if (iData.getItemCountByTempId(Consts.getFishXPSkillPoint()) < limit)
			return ;
		
		iData.setItem(con, Consts.getFishXPSkillPoint(), 0, -12);
		
		LaserSync sync = new LaserSync();
		sync.setPlayerId(con.getPlayerId());
		sync.setAngle(msg.getAngle());
		
		synchronized (gfi) {
			for (int fidx : msg.getFishIndex())
				gfi.catchFish(con, fidx, 9999999, 10000, 
						Consts.getFishMenTingLasrRate(gfi.getLevelId()), sync.getDropInfo(), 4, 2, 1, 0);
			
			gfi.broadcast(sync);
			gfi.checkBoss(con);
		}
		
		realGiveDropItem(con, gfi, iData, sync.getDropInfo(), Consts.getItemEventFishCatchGetXP(), 0);
	}
}
