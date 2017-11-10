package gameserver.pvp;

import gameserver.cache.BuildCache;
import gameserver.cache.CorpsCache;
import gameserver.cache.ItemCache;
import gameserver.cache.PlayerCache;
import gameserver.config.PvpConfig;
import gameserver.fighting.creature.RobotCreater;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.message.game.ClientMessageCommon;
import gameserver.network.message.game.ClientMessagePassiveBuff;
import gameserver.network.protos.game.CommonProto.Proto_BuildState;
import gameserver.network.protos.game.ProFight;
import gameserver.network.protos.game.ProPve;
import gameserver.network.protos.game.ProPvp;
import gameserver.network.server.connection.Connection;
import gameserver.thread.ThreadPoolManager;
import gameserver.utils.DbMgr;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;
import gameserver.utils.PassiveGain;
import gameserver.utils.Util;
import gameserver.utils.VideoManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.MT_DataBase;
import table.MT_Data_Building;
import table.MT_Data_Corps;
import table.MT_Data_Hero;
import table.MT_Data_PassiveBuff;
import table.MT_Data_PvpVirtual;
import table.MT_Data_Rank;
import table.MT_TableEnum;
import table.base.TableManager;

import com.commons.util.DatabaseInsertUpdateResult;
import com.commons.util.DatabaseUtil;
import com.commons.util.RandomUtil;
import com.commons.util.TimeUtil;

import commonality.Common;
import commonality.Common.AIRSUPPORT_OPERATE;
import commonality.Common.BUILDTYPE;
import commonality.Common.LIMIT_TYPE;
import commonality.Common.PLAYER_STATE;
import commonality.Common.SETNUMBER_TYPE;
import commonality.Common.TASK_TYPE;
import commonality.CommonPvp;
import commonality.ITEM_ID;
import commonality.ProductChannel;
import commonality.PromptType;
import commonality.VmChannel;
import database.DatabaseBuild;
import database.DatabaseCorps;
import database.DatabaseHero;
import database.DatabaseItem;
import database.DatabaseMedal;
import database.DatabasePlayer;
import databaseshare.DatabaseAttack_report;
import databaseshare.DatabaseDefend_report;
import databaseshare.DatabasePvp_match;
import databaseshare.DatabaseVideo;

public class PvpMatch {
	private static final Logger logger = LoggerFactory.getLogger(PvpMatch.class);
	
	private static List<Float> pvp_robot_money_params = new ArrayList<Float>();
	private static List<Integer> pvp_robot_money_rate = new ArrayList<Integer>();
	public static void init() {
		String[] tempStr = PvpConfig.pvp_robot_money_param.split(",");
		for (String pp : tempStr) {
			pvp_robot_money_params.add(Float.parseFloat(pp));
		}
		
		tempStr = PvpConfig.pvp_robot_money_rate.split(",");
		for (String pp : tempStr) {
			pvp_robot_money_rate.add(Integer.parseInt(pp));
		}
	}
	
	public static float getMoneyParam() {
		int idx = Util.rateInt(pvp_robot_money_rate, 100);
		if (idx == -1)
			idx = 0;
		return pvp_robot_money_params.get(idx);
	}
	
	private class AssetsBuild {
		public MT_Data_Building dataBuilding;
		public MT_DataBase dataBase;
		public DatabaseBuild build;
		public int gatchMoney;  // 该建筑可以掠夺的资源数量
		public long matchTime;
		public boolean Initialize(DatabaseBuild build,long matchTime) throws Exception {
			this.build = build;
			this.matchTime = matchTime;
			dataBuilding = TableManager.GetInstance().TableBuilding().GetElement(build.table_id);
			dataBase = Util.GetDataBaseByData(dataBuilding, build.level);
			
			// Earnings 是每小时的参数
			Int2 Earnings = (Int2)dataBase.GetDataByString(MT_TableEnum.Earnings);
			
			// 计算产出一单位资源需要的秒数
			double earningsTime = 3600.0 / Earnings.field2().doubleValue();
			
			// 计算从上次收集资源到现在一共过去的秒数
			double lastTime = ((Long)Math.min((matchTime - build.gathertime)/1000, Common.ASSET_MAX_TIME)).doubleValue();
			
			// 如果小于一个最小收集时间段，说明这个建筑没有金钱可以掠夺
			if (lastTime < (int)dataBase.GetDataByString(MT_TableEnum.AddUpTime)) 
				return false;
			
			// 储备时间 / 一单位资源的生成时间  = 生成资源的数量
			Double gather = Math.floor(lastTime / earningsTime);
			
			// 生成资源个数小于1 直接返回
			if (gather < 1) 
				return false;
			
			gatchMoney = gather.intValue();
			return true;
		}
		
		public void UpdateGatherTime(float scale)
		{
			double lastTime = ((Long)Math.min((matchTime - build.gathertime)/1000, Common.ASSET_MAX_TIME)).doubleValue();
			build.gathertime = (long) (TimeUtil.GetDateTime() - (lastTime * (1 - 0.4f * scale) * 1000));
			build.save();
		}
	}
	
//	private long m_PlayerID;					//PVP的玩家
	private Connection m_Connection;			//请求PVP玩家的连接
	private int m_Rank;							//玩家军衔
	private DatabasePvp_match m_TargetPlayer;	//对手信息
	private int m_TargetRank;					//对手军衔
	private DatabaseItem m_TargetMoney;			//对手的金钱
	private int m_TargetAssets = 0;				//对手的金库资源总量
	private int m_CanLootMoney = 0;				//可以抢夺的身上金钱
	private int m_CanLootAssetsMoney = 0;		//可以抢夺的总金钱量（身上金钱和金库金钱）
	private DatabaseBuild m_TargetCity;			//对手的主城
	private DatabaseBuild m_TargetWall;			//对手的城墙
	private int m_TargetAllPopulation = 0;		//对手的阵形总人口
	private List<AssetsBuild> m_TargetAssetsArray = new ArrayList<AssetsBuild>();		//对手的所有金库
	private Map<Integer,DatabaseCorps> m_TargetCorpsArray = new HashMap<Integer,DatabaseCorps>();	//对手的兵种信息
	private int m_reportid = 0;
	private boolean isBeginFight = false;
	
	public PvpMatch(Connection connection) throws Exception{
		randomPvpEnemy(connection);
		ProPvp.Msg_G2C_PvpMatching.Builder builder = connection.getPlayer().pvpMessage.toBuilder();
		m_TargetPlayer = new DatabasePvp_match();
		m_TargetPlayer.robot = true;
		m_Connection = connection;
		m_TargetPlayer.level = builder.getPlayerLevel();
		m_TargetPlayer.name = builder.getPlayerName();
		m_TargetPlayer.feat = builder.getPlayerFeat();
		m_TargetPlayer.player_id = builder.getPlayerId();
		m_CanLootMoney = builder.getLootMoney();
		long nowTime = TimeUtil.GetDateTime();
		if (nowTime < connection.getPlayer().getShieldEndTime())
			connection.getPlayer().SetPlayerShieldEndTime(nowTime);
		//PVP匹配的日志
		LogService.logPvp(m_CanLootAssetsMoney + m_CanLootMoney,m_TargetPlayer.feat, 0, 0, 0, -1, m_TargetPlayer.player_id, m_Connection.getPlayerId(), 0, m_Connection.getPlayer().getLevel());
		connection.sendReceiptMessage(builder.build());
	}
	
	public PvpMatch(Connection connection,DatabasePvp_match targetPlayer, int reportid) throws Exception{
		long nowTime = TimeUtil.GetDateTime();
		
		CorpsCache.deleteAllCorps(targetPlayer.player_id);
		PlayerCache.deletePvpMatch(targetPlayer.player_id);
		ItemCache.deleteAllItem(targetPlayer.player_id);
		BuildCache.deleteAllBuilder(targetPlayer.player_id);
		
		m_reportid = reportid;
		m_Connection = connection;
		m_TargetPlayer = targetPlayer;
		m_TargetPlayer.sync();
		m_Rank = m_Connection.getPlayer().getRank();
		m_TargetRank = Util.getRank(m_TargetPlayer.feat);
		DatabaseUtil targetPlayerDB = DbMgr.getInstance().getDbByPlayerId(targetPlayer.player_id);
		m_TargetMoney = targetPlayerDB.SelectOne(DatabaseItem.class, "player_id = ? and table_id = ?", targetPlayer.player_id,ITEM_ID.MONEY);
		Map<Integer, MT_Data_PassiveBuff> buffs = connection.getBuffs().GetTargetPassiveBuffs(targetPlayer.player_id);
		
		// 计算目标玩家建筑的全部信息
		checkPlayerBuildAssets(targetPlayer, nowTime, targetPlayerDB);
		if (m_TargetCity == null) 
			throw new GameException("pvp 对手主城不存在  player={}", targetPlayer.player_id);
		
		List<DatabaseCorps> corpsArray = targetPlayerDB.Select(DatabaseCorps.class, "player_id = ?", targetPlayer.player_id);
		for (DatabaseCorps corps : corpsArray) {
			m_TargetCorpsArray.put(corps.table_id, corps);
		}
		
		List<DatabaseHero> heroArray = targetPlayerDB.Select(DatabaseHero.class, "player_id = ?", targetPlayer.player_id);
		calcMoneyEffect(m_Connection.getPlayerId());
		
		connection.getPlayer().MatchTarget(targetPlayer.player_id);
		
		ProPvp.Msg_G2C_PvpMatching.Builder builder = ProPvp.Msg_G2C_PvpMatching.newBuilder();
		builder.setPlayerId(targetPlayer.player_id);
		builder.setPlayerName(targetPlayer.name);
		builder.setPlayerLevel(targetPlayer.level);
		builder.setPlayerFeat(targetPlayer.feat);
		//查询对手，取出头像
//		Integer playerHead  = (Integer)DbMgr.getInstance().getDbByPlayerId(targetPlayer.player_id)
//				.selectOneField(DatabasePlayer.class, "player_id = ?", "head", Integer.class, targetPlayer.player_id);
		DatabasePlayer temp= DbMgr.getInstance().getDbByPlayerId(targetPlayer.player_id).selectFieldFrist(DatabasePlayer.class, "player_id = ?", "head,vipLevel", targetPlayer.player_id);
		builder.setTargetHead(temp.head);
		builder.setVipLevel(temp.vipLevel);
		
		builder.setLootMoney(m_CanLootAssetsMoney + m_CanLootMoney);
		builder.setWallLevel(m_TargetWall == null ? 0 : m_TargetWall.level);
		builder.setCityLevel(m_TargetCity.level);
		builder.setMatchTime(nowTime);
		
		for (MT_Data_PassiveBuff buff : buffs.values())
			builder.addPassiveBuffId(buff.ID());
		
		List<DatabaseItem> items = targetPlayerDB.Select(DatabaseItem.class, "player_id = ? and number >= 0 and is_on > 0", targetPlayer.player_id);
		
		if (targetPlayer.defenseformation.size() > 0) {
			for (databaseshare.CustomFormation formation : targetPlayer.defenseformation) {
				if (formation.type == ProPve.Proto_CorpsType.SOLIDER.getNumber()) {
					DatabaseCorps corp = m_TargetCorpsArray.get(formation.id);
					MT_Data_Corps dataCorps = TableManager.GetInstance().TableCorps().GetElement(formation.id + corp.level);
					m_TargetAllPopulation += dataCorps.Population();
					insertCorp(builder, formation);
				} else {
					MT_Data_Hero dataHero = TableManager.GetInstance().TableHero().GetElement(formation.id);
					m_TargetAllPopulation += dataHero.Population();
					
					DatabaseHero findHero = findHero(heroArray, formation.id);
					List<DatabaseMedal> medals = targetPlayerDB.Select(DatabaseMedal.class, "hero_id = ?",findHero.hero_id);
					insertHero(builder, formation, dataHero);
					insertHeroItem(builder, dataHero, items, findHero, medals);
				}
			}
			
			for (DatabaseCorps corp : m_TargetCorpsArray.values())
			{
				ProPvp.Proto_CorpsInfoPvp.Builder corpInfo = ProPvp.Proto_CorpsInfoPvp.newBuilder();
				corpInfo.setCorpsId(corp.table_id);
				corpInfo.setCorpsLevel(corp.level);
				corpInfo.setCorpsNum(corp.number);
				builder.addCorps(corpInfo);
			}
		} else {
			List<DatabaseHero> heros = DbMgr.getInstance().getDbByPlayerId(targetPlayer.player_id).Select(DatabaseHero.class,"player_id = ? ", targetPlayer.player_id);
			for (DatabaseHero hero : heros) {
				MT_Data_Hero mtHero = TableManager.GetInstance().TableHero().GetElement(hero.hero_table_id);
				insertDefaultHero(builder, mtHero);
				List<DatabaseMedal> medals = targetPlayerDB.Select(DatabaseMedal.class, "hero_id = ?",hero.hero_id);
				insertHeroItem(builder, mtHero, items, hero, medals);
			}
		}
		
		builder.setWinFeat(calcWinFeat(m_TargetAllPopulation, m_Rank, m_TargetRank));
		builder.setHeroAttrPlus(targetPlayer.hero_attr_plus);
		connection.sendReceiptMessage(builder.build());
		if (nowTime < connection.getPlayer().getShieldEndTime())
			connection.getPlayer().SetPlayerShieldEndTime(nowTime);
		
		//PVP匹配的日志
		LogService.logPvp(m_CanLootAssetsMoney + m_CanLootMoney,targetPlayer.feat, 0, 0, 0, -1, targetPlayer.player_id, m_Connection.getPlayerId(), 0, m_Connection.getPlayer().getLevel());
		//保存匹配到的数据
		connection.getPlayer().pvpMessage = builder.build();
	}
	private void calcMoneyEffect(long player_id) {
		PassiveGain inc = m_Connection.getBuffs().GetPassiveBuffValue(Common.PASSIVEBUFF_TYPE.INC_PVP_LOOT.Number());
		PassiveGain dec = m_Connection.getBuffs().GetTargetBuffValue(Common.PASSIVEBUFF_TYPE.LESSEN_PVP_LOOT.Number());
		float res = inc.value_f - dec.value_f;
		res += 1.0f;
		if (m_TargetPlayer.robot)
			m_CanLootMoney = (int) (m_TargetMoney.number * Math.pow(0.9f, (m_Rank - m_TargetRank)) * res);
		else
			m_CanLootMoney = (int) (m_TargetMoney.number * 0.05f * Math.pow(0.9f, (m_Rank - m_TargetRank)) * res);// CommonalityFormula.GetPvpLootMoneyFunction(m_TargetMoney.number, m_Rank, m_TargetRank);
		m_CanLootAssetsMoney = (int) (m_TargetAssets * 0.3f * Math.pow(0.9f, (m_Rank - m_TargetRank)) * res);//m_CanLootMoney + CommonalityFormula.GetPvpLootAssetsFunction(m_TargetAssets, m_Rank, m_TargetRank);
		if (m_CanLootMoney <= 0)
			m_CanLootMoney = 1;
		if (m_CanLootAssetsMoney <= 0)
			m_CanLootAssetsMoney = 1;
	}
	private void checkPlayerBuildAssets(DatabasePvp_match targetPlayer,
			long nowTime, DatabaseUtil targetPlayerDB) throws Exception {
		List<DatabaseBuild> targetBuilds = targetPlayerDB.Select(DatabaseBuild.class, "player_id = ?", targetPlayer.player_id);
		for (DatabaseBuild build : targetBuilds){
			MT_Data_Building data = TableManager.GetInstance().TableBuilding().GetElement(build.table_id);
			if (data.Type() == BUILDTYPE.Wall.Number()){
				m_TargetWall = build;
			}
			else if (data.Type() == BUILDTYPE.MainCity.Number()){
				m_TargetCity = build;
			}
			else if (data.Type() == BUILDTYPE.Assets.Number() && build.state == Proto_BuildState.NONE_BUILD){
				AssetsBuild assets = new AssetsBuild();
				if (assets.Initialize(build, nowTime)){
					m_TargetAssets += assets.gatchMoney;
					m_TargetAssetsArray.add(assets);
				}
			}
		}
	}
	private DatabaseHero findHero(List<DatabaseHero> heroArray, int id) {
		for (DatabaseHero hero : heroArray)
			if (hero.hero_table_id == id)
				return hero;
		return null;
	}
	private void insertHeroItem(ProPvp.Msg_G2C_PvpMatching.Builder builder, 
			MT_Data_Hero dataHero, List<DatabaseItem> items, DatabaseHero hero, List<DatabaseMedal> medals) {
		ProPvp.Proto_HeroInfoPvp.Builder heroinfo = ProPvp.Proto_HeroInfoPvp.newBuilder();
		heroinfo.setHeroId(dataHero.ID());
		heroinfo.setStar(hero.star);
		heroinfo.setHpLv(hero.hp_lvl);
		heroinfo.setAtkLv(hero.attack_lvl);
		heroinfo.setDefLv(hero.defen_lvl);
		
		for (DatabaseItem it : items) {
			if (hero.hero_id.intValue() == it.is_on) {
				ProPvp.Msg_Item.Builder itembuilder = ProPvp.Msg_Item.newBuilder();
				itembuilder.setTableid(it.table_id);
				itembuilder.setLvl(it.level);
				heroinfo.addEquips(itembuilder);
			}
		}
		
		for (DatabaseMedal med : medals) {
			ProPvp.Msg_Medal.Builder medalBuilder = ProPvp.Msg_Medal.newBuilder();
			medalBuilder.setTableid(med.medal_table_id);
			medalBuilder.setLvl(med.cur_level);
			medalBuilder.setStar(med.cur_star);
			heroinfo.addMedals(medalBuilder);
		}
		
		for (int skillid : hero.skills) 
			heroinfo.addSkills(skillid);
		
		builder.addHeros(heroinfo);
	}
	private void insertDefaultHero(ProPvp.Msg_G2C_PvpMatching.Builder builder, MT_Data_Hero mtHero) {
		ProPve.Proto_Formation.Builder info = ProPve.Proto_Formation.newBuilder();
		info.setPosX(mtHero.Pos().field1());
		info.setPosY(mtHero.Pos().field2());
		info.setId(mtHero.ID());
		info.setType(ProPve.Proto_CorpsType.valueOf(1));
		builder.addFormationInfo(info);
	}
	private void insertHero(ProPvp.Msg_G2C_PvpMatching.Builder builder, databaseshare.CustomFormation formation, MT_Data_Hero dataHero) {
		ProPve.Proto_Formation.Builder info = ProPve.Proto_Formation.newBuilder();
		info.setPosX(formation.x);
		info.setPosY(formation.y);
		info.setId(dataHero.ID());
		info.setType(ProPve.Proto_CorpsType.valueOf(1));
		builder.addFormationInfo(info);
	}
	private void insertCorp(ProPvp.Msg_G2C_PvpMatching.Builder builder, databaseshare.CustomFormation formation) {
		ProPve.Proto_Formation.Builder info = ProPve.Proto_Formation.newBuilder();
		info.setPosX(formation.x);
		info.setPosY(formation.y);
		info.setId(formation.id);
		info.setType(ProPve.Proto_CorpsType.valueOf(formation.type));
		
		builder.addFormationInfo(info);
	}
	
	public void StartFighting() throws GameException{
		long nowTime = TimeUtil.GetDateTime();
		if (m_TargetPlayer.robot == false) {
			if (!(m_TargetPlayer.state == PLAYER_STATE.MATCHED && nowTime - m_TargetPlayer.matchtime < CommonPvp.MATCH_TIME_OUT))
			{
				m_Connection.ShowPrompt(PromptType.FIGHT_TIMEOUT);
				m_Connection.sendReceiptMessage(ProPvp.Msg_G2C_PvpOver.newBuilder().setErrorCode(3).build());
				return ;
			}
			
			m_TargetPlayer.state = PLAYER_STATE.DEFENSE;
			m_TargetPlayer.fightingtime = nowTime;
			m_TargetPlayer.save();
		}
		
		ProPvp.Msg_G2C_StartAttackPvp.Builder builder = ProPvp.Msg_G2C_StartAttackPvp.newBuilder();
		builder.setFightingTime(nowTime);
		m_Connection.sendReceiptMessage(builder.build());
		m_Connection.getPlayer().updatePvpNumber();
		ClientMessageCommon.getInstance().UpdateCountInfo(m_Connection, ProPvp.Proto_ActionType.PVP, m_Connection.getPlayer().getPvpNumber(), 
				m_Connection.getCommon().GetValue(LIMIT_TYPE.PVP_NUMBER));
		
		if (m_reportid > 0) {
			DatabaseDefend_report databaseDefend_reports = DbMgr.getInstance().getShareDB()
					.SelectOne(DatabaseDefend_report.class, "defend_report_id = ?", m_reportid);
			if (databaseDefend_reports != null) {
				databaseDefend_reports.isfuckback = 0;
				databaseDefend_reports.save();
			}
		}
		
		setBeginFight(true);
	}
	public void OverFighting(ProFight.Msg_C2G_Pvp_OverFight message) throws GameException{
		long nowTime = TimeUtil.GetDateTime();
		if (m_TargetPlayer.robot == false) {
			if (!(m_TargetPlayer.state == PLAYER_STATE.DEFENSE && nowTime - m_TargetPlayer.fightingtime < CommonPvp.FIGHTING_TIME_OUT))
			{
				m_Connection.ShowPrompt(PromptType.FIGHT_TIMEOUT);
				m_Connection.sendReceiptMessage(ProPvp.Msg_G2C_PvpOver.newBuilder().setErrorCode(2).build());
				return ;
			}
		}
		
		try {
			if (!m_TargetPlayer.robot)
				processTargetCorpsNumber(message, true);
			processOwnCorpsNumber(message);
			processAirForceDurable(message);

			int ownLossPopulation = getOwnLossPopulation(message);
			int targetLossPopulation = targetLossPopulation(message);

			m_Connection.getPlayer().addFightingCount(message.getWin());
			//胜利方获得功勋
			int winFeat = 0;
			//失败方时区功勋
			int failFeat = 0;
			//抢夺的金币数量
			int money = 0;
			m_TargetPlayer.state = PLAYER_STATE.NONE;
			int pvpStar = getPvpStar(message.getWin(), m_TargetAllPopulation, targetLossPopulation);
			long s_num = IPOManagerDb.getInstance().getNextId();
			if (message.getWin()){
				winFeat = calcWinFeat(targetLossPopulation, m_Rank, m_TargetRank);
				failFeat = calcLoseFeat(ownLossPopulation, m_Rank, m_TargetRank);

				m_Connection.getPlayer().setPlayerFeat(winFeat, SETNUMBER_TYPE.ADDITION);
				money = calcWinMoney();
				m_Connection.getItem().setItemNumber(ITEM_ID.MONEY, money, SETNUMBER_TYPE.ADDITION,
						VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"",Item_Channel.PVP_REWARD);
				if (!m_TargetPlayer.robot)
				{
					m_TargetPlayer.feat -= failFeat;
					if (m_TargetPlayer.feat < 0) 
						m_TargetPlayer.feat = 0;
					m_TargetPlayer.by_attack_time = TimeUtil.GetDateTime();
					m_TargetPlayer.shield_end_time = nowTime + getshieldendtime(pvpStar);
					m_TargetMoney.number -= m_CanLootMoney;
					if (m_TargetMoney.number < 0) 
						m_TargetMoney.number = 0;
					for (AssetsBuild build : m_TargetAssetsArray)
						build.UpdateGatherTime(1);
					m_TargetPlayer.beattacked = 1;
				}
			}else{
				winFeat = calcWinFeat(ownLossPopulation, m_TargetRank, m_Rank);
				failFeat = calcLoseFeat(targetLossPopulation, m_TargetRank, m_Rank);

				m_Connection.getPlayer().setPlayerFeat(failFeat, SETNUMBER_TYPE.MINUS);
				if (!m_TargetPlayer.robot)
				{
					m_TargetPlayer.shield_end_time = nowTime + getshieldendtime(pvpStar);
					m_TargetPlayer.feat += winFeat;
					if (m_TargetPlayer.feat < 0) m_TargetPlayer.feat = 0;
				}
				//失败获得金币计算
				if (m_TargetAllPopulation > 0) {
					float earnings = getPvpEarnings(false, pvpStar);
					money = calcLoseMoney(earnings);
					m_Connection.getItem().setItemNumber(ITEM_ID.MONEY, money, SETNUMBER_TYPE.ADDITION,
							VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"",Item_Channel.PVP_REWARD);
					
					if (!m_TargetPlayer.robot)
					{
						int targetMoneyDecCount = (int)(m_CanLootMoney * earnings);
						targetMoneyDecCount = targetMoneyDecCount > m_TargetMoney.number ? m_TargetMoney.number : targetMoneyDecCount;
						int targetOldMoneyCount = m_TargetMoney.number;
						m_TargetMoney.number -= targetMoneyDecCount;
						LogService.logItem(m_TargetPlayer.player_id, s_num, 
								SETNUMBER_TYPE.MINUS.Number(), ITEM_ID.MONEY, 
								targetMoneyDecCount, targetOldMoneyCount, m_TargetMoney.number, 
								Item_Channel.PVP_REWARD.getNum(), m_TargetPlayer.nation, m_TargetPlayer.level, m_TargetPlayer.viplevel);
						for (AssetsBuild build : m_TargetAssetsArray)
							build.UpdateGatherTime(earnings);
					}
				}
			}
			
			int newRank = m_Connection.getPlayer().getRank();
			if (newRank != m_Rank) {
				ClientMessagePassiveBuff.getInstance().UpdateFeatPassiveBuff(m_Connection, m_Rank, newRank);
			}
			//如果是机器人 不计算损失 
			if (m_TargetPlayer.robot == false){
				m_TargetPlayer.save();
				for (DatabaseCorps corps : m_TargetCorpsArray.values())
					corps.save();
				m_TargetMoney.save();
			}
			
			saveReport(message, nowTime, winFeat, failFeat, money, pvpStar);
			m_Connection.getTasks().AddTaskNumber(TASK_TYPE.PVP, message.getWin()?1:2, 1, 0);

			// 同步金钱
			ProPvp.Msg_G2C_PvpRecvMoney.Builder moneySync = ProPvp.Msg_G2C_PvpRecvMoney.newBuilder();
			moneySync.setIsWin(message.getWin());
			moneySync.setMoney(money);
			moneySync.setFeat(message.getWin() ? winFeat : failFeat);
			moneySync.setStar(pvpStar);
			m_Connection.sendPushMessage(moneySync.build());

			if (m_Connection.getPlayer().isFirstPvp()) {
				m_Connection.getPlayer().maskFirstPvp();
			}

			//记录pvp战斗完成日志
			LogService.logPvp(-1, -1, money, winFeat, failFeat, message.getWin()?1:0,m_TargetPlayer.player_id, m_Connection.getPlayerId(), 1, m_Connection.getPlayer().getLevel());
			m_Connection.setNeed_recalc_fight_val(true);
		} catch (Exception e) {
			logger.error("pvp over error: ", e);
			m_Connection.sendReceiptMessage(ProPvp.Msg_G2C_PvpOver.newBuilder().setErrorCode(1).build());
		}
	}
	private void saveReport(final ProFight.Msg_C2G_Pvp_OverFight message,
			final long nowTime, final int winFeat, final int failFeat, final int money, final int pvpStar) {
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				asyncSave(message, nowTime, winFeat, failFeat, money, pvpStar);
			}
		}, 0);
	}

	private void asyncSave(final ProFight.Msg_C2G_Pvp_OverFight message,
			long nowTime, int winFeat, int failFeat, int money, int pvpStar) {
		//保存站报信息
		DatabaseAttack_report databaseAttack_report = saveReport(nowTime,money,message.getWin(),
				m_Connection.getPlayer().getPvpInfo(),m_TargetPlayer,
				winFeat,failFeat,message.getVideoData().toByteArray());
		//发送进攻战报
		ProFight.Msg_G2C_AskAnchorData.Builder result = ProFight.Msg_G2C_AskAnchorData.newBuilder();//发送的类
		result.setType(ProFight.AnchorInfoType.ANCHOR_UPDATE);
		ProFight.Msg_G2C_AskAnchorData.AnchorInfo.Builder anchorInfo = ProFight.Msg_G2C_AskAnchorData.AnchorInfo.newBuilder();
		anchorInfo.setAnchorType(ProFight.AnchorType.ACT);
		anchorInfo.setName(m_TargetPlayer.name);
		anchorInfo.setLevel(m_TargetPlayer.level);
		anchorInfo.setWin(message.getWin());
		anchorInfo.setRankLevel(m_TargetRank);//对手战后军衔
		anchorInfo.setRankNum(m_TargetPlayer.feat);//对手战后功勋
		anchorInfo.setMoney(money);
		anchorInfo.setIsLook(false);
		anchorInfo.setId(databaseAttack_report.attack_report_id);
		anchorInfo.setVideoId(databaseAttack_report.video_id);
		anchorInfo.setRank(message.getWin() ? winFeat : -failFeat);
		anchorInfo.setTime(nowTime); 
		anchorInfo.setStar(pvpStar);
		result.addInfo(anchorInfo.build());
		m_Connection.sendReceiptMessage(result.build());
	}
	public boolean isBeginFight() {
		return isBeginFight;
	}
	public void setBeginFight(boolean isBeginFight) {
		this.isBeginFight = isBeginFight;
	}
	private int getshieldendtime(int star) {
		switch (star) {
		case 0:
			return 0;
			
		case 1:
			return 3 * 3600 * 1000;
			
		case 2:
			return 6 * 3600 * 1000;
			
		case 3:
			return 12 * 3600 * 1000;
		}
		
		return 0;
	}
	private int targetLossPopulation(ProFight.Msg_C2G_Pvp_OverFight message) {
		//地方损失人口
		int targetLossPopulation = 0;
		{
			List<ProFight.Proto_LossCorps> corpsArray = message.getTargetCorpsList();
			for (ProFight.Proto_LossCorps corps : corpsArray)
				targetLossPopulation += m_Connection.getCorps().GetAllPopulation(corps.getCorpsId(), m_TargetCorpsArray.get(corps.getCorpsId()).level, corps.getNumber());
		}
		return targetLossPopulation;
	}
	private int getOwnLossPopulation(ProFight.Msg_C2G_Pvp_OverFight message) {
		//自己损失人口
		int ownLossPopulation = 0;
		{
			List<ProFight.Proto_LossCorps> corpsArray = message.getOwnCorpsList();
			for (ProFight.Proto_LossCorps corps : corpsArray)
				ownLossPopulation += m_Connection.getCorps().GetAllPopulation(corps.getCorpsId(), corps.getNumber());
		}
		return ownLossPopulation;
	}
	
	// 更新空中支援耐久
	private void processAirForceDurable(ProFight.Msg_C2G_Pvp_OverFight message) {
		if (true)
			return ;
		
		for (int i=0; i<message.getAirUseCount(); ++i){
			ProFight.Proto_AirSupportUse air = message.getAirUse(i);
			if (air.getNumber() <= 0)
				continue;
			
			m_Connection.getAir().SetAirSupportDurable(air.getAirId(), air.getNumber(), SETNUMBER_TYPE.MINUS);
			m_Connection.getTasks().AddTaskNumber(TASK_TYPE.AIRSUPPORT_OPERATE, air.getAirId(), air.getNumber(), AIRSUPPORT_OPERATE.FIRE.Number());
		}
	}
	
	// 更新自己兵种【自己的一些兵死鸟】
	private void processOwnCorpsNumber(ProFight.Msg_C2G_Pvp_OverFight message) {
		for (int i=0;i<message.getOwnCorpsCount();++i){
			ProFight.Proto_LossCorps corps = message.getOwnCorps(i);
			if (corps.getNumber() <= 0)
				continue;
			
			m_Connection.getCorps().setCorpsNumber(corps.getCorpsId(), corps.getNumber(), SETNUMBER_TYPE.MINUS);
		}
	}
	
	// 更新被攻击的玩家的兵种和布阵信息【被消灭了一些，然后还有可能从玩家身上的兵补充到阵型中】
	private void processTargetCorpsNumber(ProFight.Msg_C2G_Pvp_OverFight message, boolean isRepairDefence) {
		for (int i=0;i<message.getTargetCorpsCount();++i) {
			ProFight.Proto_LossCorps corps = message.getTargetCorps(i);
			int count = corps.getNumber();
			
			DatabaseCorps corpdb = m_TargetCorpsArray.get(corps.getCorpsId());
			if (corpdb == null)
				break;
			
			List<databaseshare.CustomFormation> remove = new ArrayList<>();
			for (databaseshare.CustomFormation cf : m_TargetPlayer.defenseformation) {
				if (count <= 0)
					break;
				
				if (cf.id == corps.getCorpsId())
				{
					--count;
					if (isRepairDefence)
					{
						if (corpdb.number > 0)
							corpdb.number--;
						else
							remove.add(cf);
					}
					else {
						remove.add(cf);
					}
				}
			}
			
			if (!remove.isEmpty())
				m_TargetPlayer.defenseformation.removeAll(remove);
		}
	}
	private int calcLoseMoney(float earnings) {
		int money = (int) ((m_CanLootMoney + m_CanLootAssetsMoney) * earnings);
		return money;
	}
	private float getPvpEarnings(boolean isWin, int star) {
		if (star == 1)return 0.3f;
		else if (star == 2) return 0.6f;
		else if (star == 3) return 1;
		else return 0;
	}
	private int getPvpStar(boolean isWin, int targetAllPopula, int targetDiePopula) {
		if (isWin) 
			return 3;
		
		if (targetDiePopula >= targetAllPopula * 0.8)
			return 2;
		else if (targetDiePopula >= targetAllPopula * 0.6)
			return 1;
		else
			return 0;
	}
	private int calcWinMoney() {
		return m_CanLootMoney + m_CanLootAssetsMoney;
	}
	private int calcWinFeat(int popu, int myRank, int targetRank) {
		int winFeat = (int) (popu/5 + 10 * Math.pow(0.9, myRank - targetRank));//CommonalityFormula.GetWinFeatFunction(targetLossPopulation, m_Rank, m_TargetRank);
		winFeat = Math.max(0, winFeat);
		winFeat = Math.min(30, winFeat);
		return winFeat;
	}
	private int calcLoseFeat(int popu, int myRank, int targetRank) {
		int failFeat = (int) (20 * Math.pow(0.9, myRank - targetRank) - popu / 10);// CommonalityFormula.GetFailFeatFunction(ownLossPopulation, m_Rank, m_TargetRank);
		failFeat = Math.max(0, failFeat);
		failFeat = Math.min(30, failFeat);
		return failFeat;
	}
	DatabaseAttack_report saveReport(long nowTime,int money,Boolean isWin,DatabasePvp_match player,DatabasePvp_match targetPlayer,int winFeat,int failFeat,byte[] videoData) {
		DatabaseAttack_report databaseAttack_report = new DatabaseAttack_report();//进攻战报
		try {
			DatabaseVideo video = new DatabaseVideo();
			
			databaseAttack_report.owen_id = player.player_id;
			databaseAttack_report.target_id = targetPlayer.player_id;
			databaseAttack_report.time = nowTime;
			databaseAttack_report.win = isWin;
			databaseAttack_report.target_now_feat = targetPlayer.feat;
			databaseAttack_report.target_level = targetPlayer.level;
			databaseAttack_report.target_name = targetPlayer.name;
			databaseAttack_report.islook = false;
			if (isWin) {
				databaseAttack_report.owen_feat = winFeat<0 ? winFeat*-1:winFeat;
				databaseAttack_report.asset = money < 0 ? -money : money;
				video.win_asset = databaseAttack_report.asset;
				video.win_feat = databaseAttack_report.owen_feat;
			}else{
				//进攻失败不扣钱也得不到钱(已改)
				databaseAttack_report.owen_feat = failFeat>0 ? failFeat*-1:failFeat;
				databaseAttack_report.asset = money < 0 ? -money : money;
				video.lose_asset = databaseAttack_report.asset;
				video.lose_feat = databaseAttack_report.owen_feat;
			}
			
			DatabaseDefend_report databaseDefend_report = new DatabaseDefend_report();//防守战报
			databaseDefend_report.owen_id =targetPlayer.player_id;
			databaseDefend_report.time = nowTime;
			databaseDefend_report.target_now_feat = player.feat;
			databaseDefend_report.target_id = player.player_id;
			databaseDefend_report.target_level = player.level;
			databaseDefend_report.target_name = player.name;
			databaseDefend_report.win = !isWin;
			databaseDefend_report.isfuckback = 0;
			if (isWin) {
				//防守失败
				databaseDefend_report.owen_feat = failFeat>0 ? failFeat*-1:failFeat;
				databaseDefend_report.asset = money > 0 ? -money : money;
				video.lose_asset = databaseDefend_report.asset;
				video.lose_feat = databaseDefend_report.owen_feat;
				
				if (m_reportid == 0) {
					databaseDefend_report.isfuckback = 1;
				} else {
					if (PvpConfig.pvp_fuck_eachother == 1) 
						databaseDefend_report.isfuckback = 1;
				}
			}else {
				//防守成功
				databaseDefend_report.owen_feat = winFeat<0 ? winFeat*-1:winFeat;
				databaseDefend_report.asset = money > 0 ? -money : money;
				video.win_asset = databaseDefend_report.asset;
				video.win_feat = databaseDefend_report.owen_feat;
			}
			
			video.video_data = videoData;
			VideoManager.GetInstance().InsertVideo(video);
			
			databaseAttack_report.video_id = video.video_id;
			int count = DbMgr.getInstance().getShareDB().Count(DatabaseAttack_report.class, "owen_id = ? ", player.player_id);
			if (count>=10) 
				DbMgr.getInstance().getShareDB().Execute("delete from attack_report where owen_id=" + player.player_id + " order by attack_report_id limit "+(count-9));
			DatabaseInsertUpdateResult result = DbMgr.getInstance().getShareDB().Insert(databaseAttack_report);
			databaseAttack_report.attack_report_id = result.identity.intValue();
			
			databaseDefend_report.video_id = video.video_id;
			count = DbMgr.getInstance().getShareDB().Count(DatabaseDefend_report.class, "owen_id = ? ", targetPlayer.player_id);
			if (count>=10) 
				DbMgr.getInstance().getShareDB().Execute("delete from defend_report where owen_id=" + targetPlayer.player_id + " order by defend_report_id limit "+(count-9));
			
			DbMgr.getInstance().getShareDB().Insert(databaseDefend_report);
			
		} catch (Exception e) {
			logger.error("saveReport is error : ",e);
		}
		return databaseAttack_report;
	}
	public void Shutdown(){
		if (m_TargetPlayer != null){
			m_TargetPlayer.state = PLAYER_STATE.NONE;
			DbMgr.getInstance().getShareDB().Update(m_TargetPlayer, "player_id = ?", m_TargetPlayer.player_id);
		}
	}
	
	public int getRobotIndex(boolean bFirst,Integer feat)
	{
		if (bFirst)
			return 0;
		
		int selfRank = Util.getRank(feat);
		int robotRank = selfRank;
		int operate = RandomUtil.RangeRandom(0,2);
		if (operate == 0)//向下浮动
		{
			int rand = RandomUtil.RangeRandom(1,3);
			robotRank -= rand;
			if (robotRank < 0)
				robotRank = 0;
		}
		else if (operate == 2)//向上浮动
		{
			int rankCount = TableManager.GetInstance().TableRank().Count();
			int rand = RandomUtil.RangeRandom(1,3);
			robotRank += rand;
			if (robotRank > rankCount-1)
				robotRank = rankCount - 1;
		}
		
		MT_Data_Rank rank = TableManager.GetInstance().TableRank().GetElement(robotRank);
		int robotFeat = rank.Exp();
		
		int index = 0;
		while (true)
		{
			MT_Data_PvpVirtual data = TableManager.GetInstance().TablePvpVirtual().GetElement(index);
			if (data == null)
			{
				--index;
				break;
			}
			if (robotFeat < data.getM_nfeat())
			{
				--index;
				break;
			}
			++index;
		}
		
		if (index < 0)
			index = 0;
		
		return index;
	}
	
	//随机阵型
	public void randomPvpEnemy(Connection connection) {
		long nowTime = TimeUtil.GetDateTime();
		int index = getRobotIndex(connection.getPlayer().isFirstPvp(),connection.getPlayer().getFeat());
		ProPvp.Msg_G2C_PvpMatching.Builder builder = ProPvp.Msg_G2C_PvpMatching.newBuilder();
		MT_Data_PvpVirtual data = TableManager.GetInstance().TablePvpVirtual().GetElement(index);
		int cropRowNum = RandomUtil.RangeRandom(data.cropNum().field1(), data.cropNum().field2());
		int enemyLevel = RandomUtil.RangeRandom(data.lv().field1(), data.lv().field2());
		int lootMoney = RandomUtil.RangeRandom(data.getM_nminNum(), data.getM_nmaxNum());
		int wallLevel = RandomUtil.RangeRandom(data.bulidLv().field1(), data.bulidLv().field2());
		int playerhead = RandomUtil.RangeRandom(0, 4);
		builder.setPlayerId(1);
		builder.setPlayerLevel(enemyLevel);
		builder.setPlayerName(RobotCreater.newName());
		m_TargetRank = Util.getRank(data.getM_nfeat());
		m_Rank = connection.getPlayer().getRank();
		builder.setPlayerFeat(data.getM_nfeat());
		builder.setLootMoney(lootMoney);
		builder.setWallLevel(wallLevel);
		builder.setCityLevel(wallLevel);
		builder.setTargetHead(playerhead);
		builder.setMatchTime(nowTime);
		builder.setWinFeat(1);
		ArrayList<ProPve.Proto_Formation.Builder> fmList = new ArrayList<>();
		
		int usedRow = 7;
		for(int i=0;i<cropRowNum;i++)
		{
			int cropTableId = data.cropType().get(RandomUtil.RangeRandom(0, data.cropType().size() - 1));// 随机选出一个兵种
			int corpLevel = RandomUtil.RangeRandom(data.cropLv().field1(), data.cropLv().field2());
			MT_Data_Corps corpConfig = TableManager.GetInstance().TableCorps().GetElement(cropTableId + corpLevel);
			if (corpConfig == null)
				continue;
			if (usedRow - corpConfig.Size().field1() < 0)
				break;				
			usedRow -= corpConfig.Size().field1();

			ProPvp.Proto_CorpsInfoPvp.Builder corpInfo = ProPvp.Proto_CorpsInfoPvp.newBuilder();
			corpInfo.setCorpsId(cropTableId);
			corpInfo.setCorpsLevel(corpLevel);
			corpInfo.setCorpsNum(50);
			builder.addCorps(corpInfo);
			
			DatabaseCorps dbCorp = new DatabaseCorps();
			dbCorp.corps_id = cropTableId;
			dbCorp.level = corpLevel;
			m_TargetCorpsArray.put(cropTableId, dbCorp);

			// 生成防御阵型 竖排 0-5
			int usedColumn = 0;
			for (int k = 0; k < 6; k++) {
				if (usedColumn >= 6)
					break;
				if (usedColumn + corpConfig.Size().field2() > 6)
					break;
				ProPve.Proto_Formation.Builder fmInfo = ProPve.Proto_Formation.newBuilder();
				fmInfo.setPosX(usedRow);
				fmInfo.setPosY(usedColumn);
				fmInfo.setId(cropTableId);
				fmInfo.setType(ProPve.Proto_CorpsType.SOLIDER);
				fmList.add(fmInfo);
				
				m_TargetAllPopulation += corpConfig.Population();
				usedColumn += corpConfig.Size().field2();
			}
		}
		
		if (!data.HeroType().isEmpty()) 
		{
			int nHeroNum = 1;
			if (enemyLevel >= Common.PVP_STRENG_ENEMY_OPEN_LEVEL)
				nHeroNum = 2;
			int random = RandomUtil.RangeRandom(1, 3);
			for(int i=0;i<nHeroNum;i++)
			{
				ProPve.Proto_Formation.Builder info = ProPve.Proto_Formation.newBuilder();
				info.setType(ProPve.Proto_CorpsType.SOLIDER);
				int heroCorpId = data.HeroType().get(RandomUtil.RangeRandom(0, data.HeroType().size() - 1));
				MT_Data_Hero dataHero = TableManager.GetInstance().TableHero().GetElement(random);
				int x = 0;
				int y = 0;
				if (heroCorpId >= 2200 && heroCorpId < 2300) {
					// 女爵
					x = 0;
					if (random == 1) {
						if (i == 0)
							y = 1;
						else
							y = 4;
					} else if (random == 2) {
						if (i == 0)
							y = 3;
						else
							y = 1;
					} else {
						if (i == 0)
							y = 4;
						else
							y = 1;
					}
				} else {
					// 大壮、军官
					x = 5;
					if (random == 1) {
						if (i == 0)
							y = 1;
						else
							y = 4;
					} else if (random == 2) {
						if (i == 0)
							y = 3;
						else
							y = 1;
					} else {
						if (i == 0)
							y = 4;
						else
							y = 1;
					}
				}
				
				Iterator<ProPve.Proto_Formation.Builder> iter = fmList.iterator();
				while(iter.hasNext()) {
					ProPve.Proto_Formation.Builder fm = iter.next();
					if (fm.getPosX() == x || fm.getPosX() == x+1) {
						if (fm.getPosY() == y || fm.getPosY() == y+1) {
							iter.remove();
							if (!m_TargetCorpsArray.containsKey(fm.getId()))
								continue;
							DatabaseCorps dbCorp = m_TargetCorpsArray.get(fm.getId());
							MT_Data_Corps corpConfig = TableManager.GetInstance().TableCorps().GetElement(dbCorp.corps_id + dbCorp.level);
							if (corpConfig == null)
								continue;
							m_TargetAllPopulation -= corpConfig.Population();
						}
					}
				}
				
				info.setId(heroCorpId);
				info.setPosX(x);
				info.setPosY(y);
				fmList.add(info);
								
				ProPvp.Proto_CorpsInfoPvp.Builder corpInfo = ProPvp.Proto_CorpsInfoPvp.newBuilder();
				corpInfo.setCorpsId(heroCorpId);
				corpInfo.setCorpsLevel(0);
				corpInfo.setCorpsNum(1);
				builder.addCorps(corpInfo);
				
				DatabaseCorps dbCorp = new DatabaseCorps();
				dbCorp.corps_id = heroCorpId;
				dbCorp.level = 0;
				m_TargetCorpsArray.put(heroCorpId, dbCorp);
				
				m_TargetAllPopulation += dataHero.Population();
			}
		}
		
		for(ProPve.Proto_Formation.Builder fmBuilder:fmList) {
			builder.addFormationInfo(fmBuilder);
		}

		builder.setWinFeat(calcWinFeat(m_TargetAllPopulation, m_Rank, m_TargetRank));
		
		connection.getPlayer().pvpMessage = builder.build();
	}
}
