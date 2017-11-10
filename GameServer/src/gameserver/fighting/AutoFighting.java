package gameserver.fighting;

import gameserver.connection.attribute.info.HeroInfo;
import gameserver.fighting.stats.INSERT_CORPS_ERROR;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.protos.game.ProFight.Msg_C2G_Pvp_OverFight;
import gameserver.network.protos.game.ProFight.Proto_LossCorps;
import gameserver.network.protos.game.ProPve.Proto_CorpsType;
import gameserver.network.protos.game.ProPve.Proto_Formation;
import gameserver.network.protos.game.ProWanted.Msg_G2C_OverFighting.Proto_FightReward;
import gameserver.network.protos.game.ProPvp;
import gameserver.network.server.connection.Connection;
import gameserver.pvp.PvpManager;
import gameserver.utils.Item_Channel;
import gameserver.utils.UtilItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import table.Int2;
import table.MT_Data_Corps;
import table.MT_Data_Head;
import table.MT_Data_Hero;
import table.MT_Data_Instance;
import table.MT_Data_Rampart;
import table.MT_Data_Spawn;
import table.MT_Data_main;
import table.MT_TableManager.Rampart;
import table.MT_TableManager.main;
import table.MT_Table_Spawn;
import table.base.TableManager;

import com.google.protobuf.ByteString;

import commonality.Common;
import commonality.Common.SETNUMBER_TYPE;
import commonality.Common.TASK_TYPE;
import commonality.FIGHTING_TYPE;
import commonality.ProductChannel;
import commonality.VmChannel;

public class AutoFighting {
	
	private Connection m_Connection;
	
	public AutoFighting(Connection connection) {
		m_Connection = connection;
	}
	
	private void ShutDown() {
		m_Connection = null;
	}
	
	public void AutoFighingPVE(int instanceId, List<Proto_Formation> formationList) throws Exception {
		FightingManager fightingManager = new FightingManager(m_Connection);
		// 初始化战斗
		fightingManager.InitializeFightingLeft();
		//初始化自己阵形 如果返回false表示士兵数量不足
		if (fightingManager.LeftCorps.InitializeFormation(formationList, true) != INSERT_CORPS_ERROR.NONE) {
			return;
		}
		//初始化敌方阵形
		fightingManager.RightCorps.InitializeInstance(instanceId);
		//开始战斗
		fightingManager.StartFighting();
		//开始计算战斗数据
		fightingManager.FastOverFighting();
		//设置死亡兵种
		if (m_Connection.getPlayer().getLevel() >= Common.NO_DIE_CORPS) {
			m_Connection.getCorps().setCorpsArrayNumber(fightingManager.LeftCorps.GetDieSoliders(), SETNUMBER_TYPE.MINUS);
		}
		
		long costId = IPOManagerDb.getInstance().getNextId();
		int starCount = 1;
		List<UtilItem> list = new ArrayList<>();
		if (fightingManager.GetWin()) {
			MT_Data_Instance instanceData = TableManager.GetInstance().TableInstance().GetElement(instanceId);
			
			//更新玩家经验属性等
			for (Int2 pair : instanceData.Exp())
				m_Connection.getItem().setItemNumber(pair.field1(), pair.field2(), SETNUMBER_TYPE.ADDITION,
						VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,costId,"",Item_Channel.PVE_DROP);
			
			//更新奖励
			HeroInfo hero = m_Connection.getHero().getRandomHero();
			if (hero != null) {
				for (Int2 dropinfo : instanceData.DropOut()){
					int lv = m_Connection.getPlayer().getLevel();
					if (dropinfo.field1() == 0 || hero.getTableId() == dropinfo.field1()) {
						list = TableManager.GetInstance().getDropOut(lv, dropinfo.field2());
						m_Connection.getItem().setItemNumberArrayByUtilItem(list, SETNUMBER_TYPE.ADDITION,
								VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, costId,"",Item_Channel.PVE_DROP);
					}
				}
			}
			starCount = calcStar(m_Connection, instanceId, fightingManager.LeftCorps.GetDieSoliders(), fightingManager.LeftCorps.GetDieHeros());
			
			//更新Pve表中数据
			m_Connection.getPlayer().UpdatePlayerInstanceId(instanceId, starCount);
			m_Connection.getTasks().AddTaskNumber(TASK_TYPE.CLEAN_INSTANCE, instanceId, 1, starCount);
		}
		else {
			if (m_Connection.getPlayer().getsuperiorInfo().size() <= instanceId /10000) {
				m_Connection.getPlayer().UpdatePlayerAllStarCount(instanceId / 10000 - 1,
						0, SETNUMBER_TYPE.ADDITION);
			}
		}
		//检测能否领取英雄
		if (fightingManager.GetWin()){
			int needInstanceId = 0;
			boolean bType = false;
			HashMap<Integer, MT_Data_Hero> datas = TableManager.GetInstance().TableHero().Datas();
			for (Entry<Integer, MT_Data_Hero> pair : datas.entrySet()){
				for (int index : pair.getValue().needInstanceId()){
					if (index == instanceId && m_Connection.getHero().isHaveHeroByTableId(pair.getValue().ID()) == false &&
							m_Connection.getHero().isHaveHeroByInstanceId(index) == false){
						needInstanceId = index;
						bType = true;
						break;
					}
					else if (index == instanceId && 
							(m_Connection.getHero().isHaveHeroByTableId(pair.getValue().ID()) == true || m_Connection.getHero().isHaveHeroByInstanceId(index) == true))
						break;
				}
				if (bType == true)
					break;
			}
		}
		//更新关卡
		LogService.logPve(instanceId, starCount, fightingManager.GetWin() ? 1:0, costId, m_Connection.getPlayerId(), 1, m_Connection.getPlayer().getLevel());
		
		ShutDown();
	}
	
	public void AutoFighingRewardInstance(List<Proto_Formation> formationList) throws Exception {
		FightingManager fightingManager = new FightingManager(m_Connection);
		fightingManager.FightingTypeEnum = FIGHTING_TYPE.WANTED;
		// 初始化战斗
		fightingManager.InitializeFightingLeft();
		//初始化自己阵形 如果返回false表示士兵数量不足
		if (fightingManager.LeftCorps.InitializeFormation(formationList, true) != INSERT_CORPS_ERROR.NONE) {
			return;
		}
		//初始化敌方阵形
		fightingManager.InitializeFightingRight(m_Connection.getPlayer().curRewardInstanceId,
				m_Connection.getPlayer().getRewardEnemyFormation(),m_Connection.getPlayer().getRewardEnemyCrops());
		//开始战斗
		fightingManager.StartFighting();
		//开始计算战斗数据
		fightingManager.FastOverFighting();
		//设置死亡兵种
		if (m_Connection.getPlayer().getLevel() >= Common.NO_DIE_CORPS) {
			m_Connection.getCorps().setCorpsArrayNumber(fightingManager.LeftCorps.GetDieSoliders(), SETNUMBER_TYPE.MINUS);
		}
		
		long costId = IPOManagerDb.getInstance().getNextId();
		LogService.logEvent(m_Connection.getPlayerId(), costId, 38, m_Connection.getPlayer().curRewardInstanceId, fightingManager.GetWin() ? 1:0);
		
		if (fightingManager.GetWin())
		{
			List<UtilItem> list = m_Connection.getPlayer().randomRewardItem();
			if (list != null)
			{
				m_Connection.getItem().setItemNumberArrayByUtilItem(list, SETNUMBER_TYPE.ADDITION,
						VmChannel.InGameDrop, ProductChannel.InGameDrop, costId,"",Item_Channel.REWARD_INSTANCE_DROP);

			}
			m_Connection.getPlayer().addRewardInstanceId();
		}
		
		m_Connection.getPlayer().curRewardInstanceId = 0;

		ShutDown();
	}
	
	private int calcStar(Connection connection, int instanceId, Map<Integer, Integer> map, List<Integer> heros) {
		int starCount = 1;
		//更新星数
		int enemy = 0;
		int selfPopu = 0;
		MT_Data_Instance data = TableManager.GetInstance().TableInstance().GetElement(instanceId);
		MT_Table_Spawn spawnData = TableManager.GetInstance().getSpawns_Spawn(data.ID().toString());
		HashMap<Integer, MT_Data_Spawn> spawnDatas = spawnData.Datas();
		for (Entry<Integer, MT_Data_Spawn> pair : spawnDatas.entrySet())
			enemy += connection.getCorps().GetAllPopulation(pair.getValue().CorpsID(),pair.getValue().Level(),1);
		for (Entry<Integer, Integer> pair : map.entrySet()){
			int corpsId = pair.getKey();
			int num = pair.getValue();
			MT_Data_Corps corpsData = TableManager.GetInstance().TableCorps().GetElement(corpsId);
			if (corpsData == null)
				continue;
			selfPopu += connection.getCorps().GetAllPopulation(corpsId,num);
		}
		for (int Id : heros) {
			MT_Data_Hero heroData = TableManager.GetInstance().TableHero().GetElement(Id);
			if (heroData == null)
				continue;
			selfPopu += connection.getHero().getHeroPopulation(Id);
		}
		int twoStar = (int) (enemy * 0.5);
		int threeStar = (int) (enemy * 0.2);
		if (selfPopu <= twoStar)
			starCount ++;
		if (selfPopu <= threeStar)
			starCount ++;

		return starCount;
	}
	
	public void AutoFighingPVP(ProPvp.Msg_G2C_PvpMatching msg, List<Proto_Formation> formationList) throws Exception {
		FightingManager fightingManager = new FightingManager(m_Connection);
		fightingManager.EnemyPlayerId = msg.getPlayerId();
		fightingManager.FightingTypeEnum = FIGHTING_TYPE.PVP;
		MT_Data_Head Head = TableManager.GetInstance().TableHead().GetElement(msg.getTargetHead());
		// 初始化战斗
		fightingManager.InitializeFightingLeft();
		fightingManager.InitializeFightingRight(GetWallHp(msg), GetWallLayout(msg), msg.getHerosList(), msg.getPassiveBuffIdList(),
				msg.getCorpsList(), msg.getFormationInfoList(), msg.getPlayerName(), msg.getPlayerLevel(), msg.getPlayerFeat(),
				Head != null ? Head.Head() : "");
		//初始化自己阵形 如果返回false表示士兵数量不足
		if (fightingManager.LeftCorps.InitializeFormation(formationList, true) != INSERT_CORPS_ERROR.NONE) {
			return;
		}
		//开始战斗
		fightingManager.StartFighting();
		//开始计算战斗数据
		fightingManager.FastOverFighting();
		// PVP结算处理
		Msg_C2G_Pvp_OverFight.Builder message = Msg_C2G_Pvp_OverFight.newBuilder();
		// 是否胜利
		message.setWin(fightingManager.GetWin());
		// 对手的playerId
		message.setTargetId(msg.getPlayerId());
		// 自己损失的普通兵
		for (Entry<Integer, Integer> pair : fightingManager.LeftCorps.GetDieSoliders().entrySet()) {
			Proto_LossCorps.Builder data = Proto_LossCorps.newBuilder();
			data.setCorpsId(pair.getKey());
			data.setNumber(pair.getValue());
			data.setType(Proto_CorpsType.SOLIDER);
			message.addOwnCorps(data);
		}
		// 自己损失的英雄
		for (int Id : fightingManager.LeftCorps.GetDieHeros()) {
			Proto_LossCorps.Builder data = Proto_LossCorps.newBuilder();
			data.setCorpsId(Id);
			data.setNumber(1);
			data.setType(Proto_CorpsType.HERO);
			message.addOwnCorps(data);
		}
		// 对方损失的普通兵
		for (Entry<Integer, Integer> pair : fightingManager.RightCorps.GetDieSoliders().entrySet()) {
			Proto_LossCorps.Builder data = Proto_LossCorps.newBuilder();
			data.setCorpsId(pair.getKey());
			data.setNumber(pair.getValue());
			data.setType(Proto_CorpsType.SOLIDER);
			message.addTargetCorps(data);
		}
		// 对方损失的英雄
//		for (int Id : fightingManager.RightCorps.GetDieHeros()) {
//			Proto_LossCorps.Builder data = Proto_LossCorps.newBuilder();
//			data.setCorpsId(Id);
//			data.setNumber(1);
//			data.setType(Proto_CorpsType.HERO);
//			message.addOwnCorps(data);
//		}
		// 战斗结束保存录像
		byte[] bytes = fightingManager.VideoManager.Save();
		// 录像数据
		message.setVideoData(ByteString.copyFrom(bytes));
		
		PvpManager.getInstance().OverFighting(m_Connection.getPlayerId(), message.build());
		
		ShutDown();
	}
	private int GetWallHp(ProPvp.Msg_G2C_PvpMatching msg) {
		int hp = 0;
		
		MT_Data_Rampart wallData = TableManager.GetInstance().getSpawns(Rampart.Rampart_wall).GetElement(msg.getWallLevel());
		hp += wallData.WallHP();
		
		MT_Data_main mainData = TableManager.GetInstance().getSpawns(main.main_MainCity).GetElement(msg.getCityLevel());
		hp += mainData.WallHP();
		
		return hp;
	}
	private int GetWallLayout(ProPvp.Msg_G2C_PvpMatching msg) {
		MT_Data_Rampart wallData = TableManager.GetInstance().getSpawns(Rampart.Rampart_wall).GetElement(msg.getWallLevel());
		if (wallData == null)
			return 0;
		
		return wallData.WallLayout();
	}
}
