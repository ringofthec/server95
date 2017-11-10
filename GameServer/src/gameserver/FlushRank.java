package gameserver;

import gameserver.config.AccountVerifyConfig;
import gameserver.config.GameConfig;
import gameserver.config.HttpProcessConfig;
import gameserver.config.HttpServerConfig;
import gameserver.config.ManagementConfig;
import gameserver.config.PvpConfig;
import gameserver.config.ServerConfig;
import gameserver.config.TableConfig;
import gameserver.http.HttpProcessManager;
import gameserver.ipo.IPOManagerDb;
import gameserver.utils.DbMgr;
import gameserver.utils.Util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.database.DatabaseConfig;
import com.commons.service.DatabaseService;
import com.commons.service.LoggingService;
import com.commons.util.ConfigLoader;
import com.commons.util.DatabaseUtil;
import com.commons.util.HttpProcessResult;
import com.commons.util.HttpUtil;
import com.commons.util.JsonUtil;
import com.commons.util.PathUtil;
import com.commons.util.TimeUtil;
import com.commons.util.string;
import com.gdl.manager.RankManager;
import commonality.Common;
import commonality.ErrorCode;
import commonality.GameServerRegisterResult;

import databaseshare.DatabaseAccount;
import databaseshare.DatabaseDelete_player;
import databaseshare.DatabaseServer_var;

public class FlushRank {
	public static final Logger logger = LoggerFactory
			.getLogger(FlushRank.class);

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
			
			// 初始化数据库
			DatabaseService.initialize();
			DbMgr.getInstance().setShareDb(registerResult.databaseUrl,
					registerResult.databaseUsername,
					registerResult.databasePassword);
			
			// 初始化时间
			TimeUtil.Initialize(registerResult.time);
			logger.info("中心服务器当前时间 : " + TimeUtil.GetDateString());
			RankManager.getInstance().flushRank();
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
