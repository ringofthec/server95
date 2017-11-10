package gameserver;

import gameserver.active.ActiveService;
import gameserver.cache.CacheMgr;
import gameserver.config.AccountVerifyConfig;
import gameserver.config.GameConfig;
import gameserver.config.HttpProcessConfig;
import gameserver.config.HttpServerConfig;
import gameserver.config.ManagementConfig;
import gameserver.config.PvpConfig;
import gameserver.config.ServerConfig;
import gameserver.config.TableConfig;
import gameserver.connection.attribute.LegionRepair;
import gameserver.event.GameEvent;
import gameserver.event.GameEventDispatcher;
import gameserver.event.listener.CheckTableChangeListener;
import gameserver.event.listener.FlushRankListListener;
import gameserver.event.listener.FushLegionListListener;
import gameserver.event.listener.NewHourListener;
import gameserver.event.listener.PlayerOnNewDayListener;
import gameserver.event.listener.RechargeListener;
import gameserver.http.HttpProcessManager;
import gameserver.http.HttpServerManager;
import gameserver.http.handler.AibeiweiHttpHandler;
import gameserver.http.handler.ExitHttpHandler;
import gameserver.http.handler.FuncHttpHandler;
import gameserver.http.handler.MyInvalidHttpHandler;
import gameserver.http.handler.MyInvokeHttpHandler;
import gameserver.http.handler.MyVerifyHttpHandler;
import gameserver.http.handler.ProcessMessageHttpHandler;
import gameserver.http.handler.WeiXinHttpHandler;
import gameserver.http.handler.ZhiFuBaoFanQieHttpHandler;
import gameserver.http.handler.ZhiFuBaoHttpHandler;
import gameserver.ipo.IPOManagerDb;
import gameserver.network.server.connection.ConnectionManager;
import gameserver.service.ServerVariableService;
import gameserver.stat.StatManger;
import gameserver.time.GameTimeService;
import gameserver.utils.DbMgr;
import gameserver.utils.Util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.cache.CacheManager;
import com.commons.database.DatabaseConfig;
import com.commons.network.websock.GameWebSocketServer;
import com.commons.network.websock.handler.BaijialeGameHandler;
import com.commons.network.websock.handler.ChangePhoneHandler;
import com.commons.network.websock.handler.ChatHandler;
import com.commons.network.websock.handler.ExchangeCodeHandler;
import com.commons.network.websock.handler.FishGameHandler;
import com.commons.network.websock.handler.FreeMoneyHandler;
import com.commons.network.websock.handler.FriendHandler;
import com.commons.network.websock.handler.GameInstanceHandler;
import com.commons.network.websock.handler.HallHandler;
import com.commons.network.websock.handler.ItemHandler;
import com.commons.network.websock.handler.LoginHandler;
import com.commons.network.websock.handler.LottyHandler;
import com.commons.network.websock.handler.MailHandler;
import com.commons.network.websock.handler.NiuniuGameHandler;
import com.commons.network.websock.handler.NoticeHandler;
import com.commons.network.websock.handler.PayHandler;
import com.commons.network.websock.handler.PigHandler;
import com.commons.network.websock.handler.PlayerHandler;
import com.commons.network.websock.handler.RankHandler;
import com.commons.network.websock.handler.ShopHandler;
import com.commons.network.websock.handler.SlotGameHandler;
import com.commons.network.websock.handler.UtilHandler;
import com.commons.service.DatabaseService;
import com.commons.service.LoggingService;
import com.commons.thread.WorldEvents;
import com.commons.util.ConfigLoader;
import com.commons.util.DatabaseUtil;
import com.commons.util.HttpProcessResult;
import com.commons.util.HttpUtil;
import com.commons.util.JsonUtil;
import com.commons.util.PathUtil;
import com.commons.util.RandomUtil;
import com.commons.util.TimeUtil;
import com.commons.util.string;
import com.gdl.game.BaijialeGameInstanceManager;
import com.gdl.game.FishGameConfig;
import com.gdl.game.FishGameInstanceManager;
import com.gdl.game.NiuniuGameInstanceManager;
import com.gdl.game.NiuniuGen2;
import com.gdl.game.SlotActiveMrg;
import com.gdl.manager.GamePoolManager;
import com.gdl.manager.RankManager;
import com.gdl.manager.ServerVarManager;
import com.gdl.manager.ShopManager;

import commonality.Common;
import commonality.ErrorCode;
import commonality.GameServerRegisterResult;
import databaseshare.DatabaseAccount;
import databaseshare.DatabaseDelete_player;
import databaseshare.DatabasePlayer_brief_info;
import databaseshare.DatabaseServer_var;

public class GameServer {
	public static final Logger logger = LoggerFactory
			.getLogger(GameServer.class);

	public static void main(String[] args) {
		try {
			
			// 初始化日志系统
			LoggingService.initialize();

			ConfigLoader base = new ConfigLoader();
			base.loadPath(PathUtil.GetCurrentDirectory() + "data/config",
					new String[] { "config" }, true);
			base.loadConfig(DatabaseConfig.class, TableConfig.class,
					ServerConfig.class, HttpServerConfig.class,
					ManagementConfig.class, HttpProcessConfig.class,
					AccountVerifyConfig.class, PvpConfig.class,
					GameConfig.class);

			// 从中心服务器获取 GameServer启动的配置数据，获取到的所有配置信息以 GameServerRegisterResult
			// 对象体现出来
			GameServerRegisterResult registerResult = getShareInfo();
			
			LoginHandler.getInstance().init();
			HallHandler.getInstance().init();
			ItemHandler.getInstance().init();
			NoticeHandler.getInstance().init();
			PlayerHandler.getInstance().init();
			UtilHandler.getInstance().init();
			ChangePhoneHandler.getInstance().init();
			ExchangeCodeHandler.getInstance().init();
			FriendHandler.getInstance().init();
			ChatHandler.getInstance().init();
			MailHandler.getInstance().init();
			ShopHandler.getInstance().init();
			FreeMoneyHandler.getInstance().init();
			PigHandler.getInstance().init();
			LottyHandler.getInstance().init();
			RankHandler.getInstance().init();
			SlotGameHandler.getInstance().init();
			GameInstanceHandler.getInstance().init();
			FishGameHandler.getInstance().init();
			NiuniuGameHandler.getInstance().init();
			NiuniuGameInstanceManager.getInstance().init();
			BaijialeGameHandler.getInstance().init();
			BaijialeGameInstanceManager.getInstance().init();
			PayHandler.getInstance().init();
			NiuniuGen2.getInstance().setupAllCom();
			FishGameConfig.getInstance().init(true);
			FishGameInstanceManager.getInstance().init();
			
			// 初始化数据库
			DatabaseService.initialize();
			DbMgr.getInstance().setShareDb(registerResult.databaseUrl,
					registerResult.databaseUsername,
					registerResult.databasePassword);
			
			// 初始化时间
			TimeUtil.Initialize(registerResult.time);
			logger.info("中心服务器当前时间 : " + TimeUtil.GetDateString());
			
			if (args.length > 0 && args[0].equals("nation")) {
				repairNation();
				System.exit(-1);
			} else if (args.length > 0 && args[0].equals("delete_player")) {
				clearDB();
				System.exit(-1);
			} else if (args.length > 0 && args[0].equals("repair_active")) {
				ServerVariableService.init();
				System.exit(-1);
			} else if (args.length > 0 && args[0].equals("legion_merge")) {
				LegionRepair.DBMerge();
				System.exit(-1);
			} else if (args.length > 0 && args[0].equals("exchange_code")) {
				LegionRepair.DBMerge();
				System.exit(-1);
			} else if (args.length > 0 && args[0].equals("flushrank")) {
				RankManager.getInstance().flushRank();
				System.exit(-1);
			}
			
			List<DatabasePlayer_brief_info> pbilist = DbMgr.getInstance().getShareDB()
					.Select(DatabasePlayer_brief_info.class, "head_url=?", "");
			for (DatabasePlayer_brief_info b : pbilist) {
				b.head_url = "TX" + RandomUtil.RangeRandom(1, 10) + ".png";
				b.save();
			}
			
			RankManager.getInstance().reloadRank();
			GamePoolManager.getInstance().init();

			// 初始化管理模块
			//ManagementManager.getInstance().initialize();

			// 初始化http处理模块
			HttpProcessManager.getInstance().setInfo(registerResult);
			HttpServerManager.getInstance().initialize(registerResult.httpPort);
			HttpServerManager.getInstance().registerHandler(
					MyInvalidHttpHandler.class);
			HttpServerManager.getInstance().registerHandler(
					MyInvokeHttpHandler.class);
			HttpServerManager.getInstance().registerHandler(
					MyVerifyHttpHandler.class);
			HttpServerManager.getInstance().registerHandler(
					ProcessMessageHttpHandler.class);
			HttpServerManager.getInstance().registerHandler(
					ZhiFuBaoHttpHandler.class);
			HttpServerManager.getInstance().registerHandler(
					ZhiFuBaoFanQieHttpHandler.class);
			HttpServerManager.getInstance().registerHandler(
					WeiXinHttpHandler.class);
			HttpServerManager.getInstance().registerHandler(
					ExitHttpHandler.class);
			HttpServerManager.getInstance().registerHandler(
					FuncHttpHandler.class);
			HttpServerManager.getInstance().registerHandler(
					AibeiweiHttpHandler.class);
			HttpServerManager.getInstance().startService();

			// 初始化客户端协议处理
			//ClientMessage.getInstance().initialize();

			//PvpMatch.init();
			
			ShopManager.getInstance().refresh();
			ServerVarManager.getInstance().init();

			// 初始化管理模块
			//ManagementMessage.getInstance().initialize();

			// 初始化share服务器协议
			//ShareMessage.getInstance().initialize();

			GameEventDispatcher.getInstance().addListener(
					WorldEvents.TIME_TICK_DAY,
					new PlayerOnNewDayListener());
			
			GameEventDispatcher.getInstance().addListener(
					WorldEvents.TIME_TICK_DAY, StatManger.getInstance());
			GameEventDispatcher.getInstance().addListener(
					WorldEvents.TIME_TICK_5_MINUTES, StatManger.getInstance());
			GameEventDispatcher.getInstance().addListener(
					WorldEvents.TIME_TICK_HOUR, new FushLegionListListener());
			GameEventDispatcher.getInstance().addListener(
					GameEvent.rechargeEvent, new RechargeListener());
			GameEventDispatcher.getInstance().addListener(
					WorldEvents.TIME_TICK_TEN_SECOND,
					ActiveService.getInstance());
			GameEventDispatcher.getInstance().addListener(
					WorldEvents.TIME_TICK_MINUTE,
					FlushRankListListener.getInstance());
			 GameEventDispatcher.getInstance().addListener(
					 WorldEvents.TIME_TICK_SECOND,
					 ConnectionManager.GetInstance());
			GameEventDispatcher.getInstance().addListener(
					WorldEvents.TIME_TICK_HOUR,
					MethodStatitic.getInstance());
			GameEventDispatcher.getInstance().addListener(
					WorldEvents.TIME_TICK_HOUR,
					new NewHourListener());
			GameEventDispatcher.getInstance().addListener(
					WorldEvents.TIME_TICK_5_MINUTES, 
					new CheckTableChangeListener());
			ActiveService.getInstance().flush();
			
			SlotActiveMrg.getInstance().init(true);

//			RobotCreater.createPvpVirtualPerson();

			
			// 初始化时间服务
			GameTimeService.getInstance().init();

			// 初始化军团manager
			//LegionManager.getInstance().init();

			// 通知中心服务器服务器启动完毕
			HttpProcessManager.getInstance().finish();

			// 初始化IPO的logId
			//initIpoLogId(registerResult.uid);

			// 初始化 缓存
			CacheManager.getInstance().init(ServerConfig.CACHE_IP_PORT,
					ServerConfig.KEYS_NAME, ServerConfig.DATA_OVER_TIME,
					ServerConfig.CLOSE_CACHE);
			// 初始化 key_head
			CacheMgr.init_key_head();

			// 初始化客户端连接端口
			//IOServer.getInstance().start(registerResult.gamePort);
			
			GameWebSocketServer gm = new GameWebSocketServer();
			gm.run(registerResult.gamePort);
			
			FlushRankListListener.getInstance().flush();
			
			System.gc();

			// 设置停服Hook钩子
			Runtime.getRuntime().addShutdownHook(Shutdown.getInstance());
		} catch (Exception e) {
			logger.error("main is error : ", e);
		}
	}

	public static void repairNation() {
		List<DatabaseAccount> accs = DbMgr
				.getInstance()
				.getShareDB()
				.Select(DatabaseAccount.class, "nation='0' or nation ='UNKOWN'");

		for (DatabaseAccount dbacc : accs) {
			if (string.IsNullOrEmpty(dbacc.create_ip))
				continue;

			String nationStr = Util.ipToCountry(dbacc.create_ip);
			DbMgr.getInstance()
					.getShareDB()
					.Execute("update account set nation=? where player_id=?",
							nationStr, dbacc.player_id);

			DbMgr.getInstance()
					.getDb(dbacc.db_id)
					.Execute("update player set nation=? where player_id=?",
							nationStr, dbacc.player_id);

			DbMgr.getInstance()
					.getShareDB()
					.Execute("update pvp_match set nation=? where player_id=?",
							nationStr, dbacc.player_id);
		}

		DbMgr.getInstance()
				.getShareDB()
				.Execute(
						"UPDATE legion,pvp_match set legion.nation = pvp_match.nation  where legion.boss_id = pvp_match.player_id");
	}

	private static void initIpoLogId(String uid) {
		DatabaseServer_var map = DbMgr.getInstance().getShareDB()
				.SelectOne(DatabaseServer_var.class, " var_key = ? ", uid);
		if (map != null) {
			IPOManagerDb.getInstance().setM_log_id(Long.valueOf(map.var_value));
		} else {
			DatabaseServer_var var = DbMgr
					.getInstance()
					.getShareDB()
					.SelectOne(DatabaseServer_var.class, " var_key = ? ",
							Common.IPO_KEY);
			if (var == null) {
				DatabaseServer_var initVar = new DatabaseServer_var();
				initVar.var_key = Common.IPO_KEY;
				initVar.var_value = String.valueOf(Common.IPO_VAL);
				DbMgr.getInstance().getShareDB().Insert(initVar);
				IPOManagerDb.getInstance().setM_log_id(
						Long.valueOf(initVar.var_value));
			} else {
				var.var_value = String
						.valueOf((Long.valueOf(var.var_value) + Common.IPO_VAL));
				DbMgr.getInstance().getShareDB()
						.Update(var, " var_key = ? ", Common.IPO_KEY);
				IPOManagerDb.getInstance().setM_log_id(
						Long.valueOf(var.var_value));
			}
		}
		// 保存本服的地址和端口
		Shutdown.getInstance().setAddressAndPort(uid);
	}

	private static GameServerRegisterResult getShareInfo() {
		HttpUtil.setTimeOutLength(60000000);
		HttpProcessResult httpResult = HttpProcessManager.getInstance()
				.register();
		if (httpResult.succeed == false) {
			logger.error("请求服务器地址出错   status : {}", httpResult.statusCode);
			System.exit(0);
		}
		String strResult = HttpUtil.urldecode(HttpUtil.toString(httpResult));
		logger.info("请求服务器地址返回字符串 : {}", strResult);
		GameServerRegisterResult registerResult = JsonUtil.JsonToObject(
				strResult, GameServerRegisterResult.class);
		if (registerResult == null
				|| registerResult.code != ErrorCode.SUCCEED.ordinal()) {
			logger.error("请求地址出错  errorcode : {}  errormessage : {}",
					registerResult.code, registerResult.message);
			System.exit(0);
		}
		return registerResult;
	}
	
	static void clearDB() {
		List<DatabaseDelete_player> del_players_list = DbMgr.getInstance().getShareDB().Select(DatabaseDelete_player.class, "true");
		
		for (DatabaseDelete_player player_id : del_players_list) {
			DbMgr.getInstance().getShareDB().Execute("delete from pvp_match where player_id=?", player_id.player_id);
			
			DatabaseUtil dbhelp = DbMgr.getInstance().getDbByPlayerId(player_id.player_id);
			if (dbhelp == null)
				continue;
			
			dbhelp.Execute("delete from active_state where player_id=?", player_id.player_id);
			dbhelp.Execute("delete from airsupport where player_id=?", player_id.player_id);
			dbhelp.Execute("delete from build where player_id=?", player_id.player_id);
			dbhelp.Execute("delete from corps where player_id=?", player_id.player_id);
			dbhelp.Execute("delete from Hero where player_id=?", player_id.player_id);
			dbhelp.Execute("delete from item where player_id=?", player_id.player_id);
			dbhelp.Execute("delete from LevReward where player_id=?", player_id.player_id);
			dbhelp.Execute("delete from mail where player_id=?", player_id.player_id);
			dbhelp.Execute("delete from player_offline_val where player_id=?", player_id.player_id);
			dbhelp.Execute("delete from task where player_id=?", player_id.player_id);
			dbhelp.Execute("delete from tech where player_id=?", player_id.player_id);
			dbhelp.Execute("delete from player where player_id=?", player_id.player_id);
			
			DbMgr.getInstance().getShareDB().Execute("delete from attack_report where owen_id=?", player_id.player_id);
			DbMgr.getInstance().getShareDB().Execute("delete from attack_report where target_id=?", player_id.player_id);
			DbMgr.getInstance().getShareDB().Execute("delete from defend_report where owen_id=?", player_id.player_id);
			DbMgr.getInstance().getShareDB().Execute("delete from defend_report where target_id=?", player_id.player_id);
			DbMgr.getInstance().getShareDB().Execute("delete from account where player_id=?", player_id.player_id);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
