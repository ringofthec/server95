package gameserver.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

public class LogService {
	public static final Logger logger = LoggerFactory.getLogger(LogService.class);
	
	// type
	public static void logEvent(long playerId, long costId, int type) {
		logEvent(playerId, costId, type, -1, -1);
	}
	public static void logEvent(long playerId, long costId, int type, int param1) {
		logEvent(playerId, costId, type, param1, -1);
	}
	public static void logEvent(long playerId, long costId, int type, int param1, int param2) {
		LogEntry log = new LogEntry(playerId, costId, type, param1, param2);
		logger.info(MarkerFactory.getMarker("Event"), log.toString());
	}
	
	// 玩家升级
	public static void logPlayerLevelUp(long playerId, int oldlevel, int level) {
		LogEntry log = new LogEntry(playerId, oldlevel, level);
		logger.info(MarkerFactory.getMarker("LevelUp"), log.toString());
	}
	
	// 物品变化
	public static void logItem(long playerId, long costId, int type, int itemId, long changeNum, long oldNum, long newNum, int itemch,String nation,int level,int vlvl) {
		LogEntry log = new LogEntry(playerId, costId, type, itemId, changeNum, oldNum, newNum, itemch,nation,level,vlvl);
		logger.info(MarkerFactory.getMarker("Item"), log.toString());
	}
	
	// 新玩家
	public static void logNewPlayer(long playerId, int isPasser, String account, String ip, String platform, String iditify,String nation,String createTime) {
		LogEntry log = new LogEntry(playerId, isPasser, account, ip, platform, iditify,nation,createTime);
		logger.info(MarkerFactory.getMarker("NewPlayer"), log.toString());
	}
	
	// 玩家登陆登出
	public static void logLogInOut(long playerId, int login, int time,String nation,String createTime,int activeTime, int level) {
		LogEntry log = new LogEntry(playerId,login, time,nation,createTime,activeTime,level);
		logger.info(MarkerFactory.getMarker("LogInOut"), log.toString());
	}
	
	// 玩家登陆日志，一天记录一条
	public static void logLogIn(long playerId,String nation,String createTime, int level) {
		LogEntry log = new LogEntry(playerId,nation,createTime,level);
		logger.info(MarkerFactory.getMarker("LogIn"), log.toString());
	}
	
	// 升级
	public static void logBuildingUpgrade(int buildid,int tableId,int oldLevel, int curLevel, long costId, long playerId,int isOver, int level){
		LogEntry log = new LogEntry(playerId,tableId, costId, buildid, oldLevel,curLevel, isOver,level);
		logger.info(MarkerFactory.getMarker("BuildingUpgrade"), log.toString());
	}
	
	// 兵种升级
	public static void logCropUp(int cropid,int oldlevel,int newlevel,int outNum, long costId, long playerId,int isOver, int level) {
		LogEntry log = new LogEntry(playerId, costId, cropid, oldlevel,newlevel,outNum,isOver,level);
		logger.info(MarkerFactory.getMarker("CropUp"), log.toString());
	}

	// 科技升级
	public static void logTechUp(int techid,int oldlevel,int newlevel, long costId, long playerId,int isOver, int level) {
		LogEntry log = new LogEntry(playerId, costId, techid, oldlevel,newlevel,isOver,level);
		logger.info(MarkerFactory.getMarker("TechUp"), log.toString());
	}

	// 飞机升级
	public static void logPlaneLevelup(int planeId,int oldLev, int newlevel, long costId, long playerId, int level) {
		LogEntry log = new LogEntry(playerId, costId, planeId, oldLev,newlevel, level);
		logger.info(MarkerFactory.getMarker("PlaneLvlup"), log.toString());
	}
	
	// PVE
	public static void logPve(int instanceId, int star, int isWin, long costId, long playerId,int isOver, int level) {
		LogEntry log = new LogEntry(playerId, costId, instanceId, star, isWin ,isOver, level);
		logger.info(MarkerFactory.getMarker("Pve"), log.toString());
	}
	
	// PVP
	public static void logPvp(int canMoneyCount,int canFeat,int reciveMoneyCount, int winFeat,int failFeat,int iswin,long targetId,long playerId,int isOver, int level) {
		LogEntry log = new LogEntry(playerId, canMoneyCount,canFeat,reciveMoneyCount,winFeat,failFeat,iswin,targetId,isOver, level);
		logger.info(MarkerFactory.getMarker("Pvp"), log.toString());
	}
	
	// 商城购买
	public static void logStore(int commodityId, long costId, long playerId ,int storetype, int count, int level) {
		LogEntry log = new LogEntry(playerId,costId,commodityId,storetype,count, level);
		logger.info(MarkerFactory.getMarker("Store"), log.toString());
	}
	
	// 充值
	public static void logRecharge(long playerId, int channel, String order, String jsonstr, String sign, String nation, String clientversion, int level) {
		LogEntry log = new LogEntry(playerId, channel, order, jsonstr, sign, nation, clientversion, level);
		logger.info(MarkerFactory.getMarker("Recharge"), log.toString());
	}
	
	// 军团创建
	public static void logLegionCreate(int legionId,long costId, long playerId, int level) {
		LogEntry log = new LogEntry(playerId, costId, legionId, level);
		logger.info(MarkerFactory.getMarker("LegionCreate"), log.toString());
	}
	// 军团加入
	public static void logLegionJoin(int legionId,long playerId, int level) {
		LogEntry log = new LogEntry(playerId, legionId, level);
		logger.info(MarkerFactory.getMarker("LegionJoin"), log.toString());
	}
	// 捐献
	public static void logLegionContr(int contrType, int count, int rewardExp, int legionid,long playerId, int level) {
		LogEntry log = new LogEntry(playerId, legionid, contrType, count, rewardExp, level);
		logger.info(MarkerFactory.getMarker("LegionContrbute"), log.toString());
	}
	// 商店刷新
	public static void logLegionFlush( long costid, int flushcount, int legionid, long playerId, int level) {
		LogEntry log = new LogEntry(playerId, costid, legionid, flushcount, level);
		logger.info(MarkerFactory.getMarker("LegionFlush"), log.toString());
	}
	
	public static void sysErr(long playerId, String error, int level) {
		LogEntry log = new LogEntry(playerId, error, level);
		logger.info(MarkerFactory.getMarker("SystemError"), log.toString());
	}
}
