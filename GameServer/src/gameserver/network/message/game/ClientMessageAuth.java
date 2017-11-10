package gameserver.network.message.game;

import gameserver.config.AccountVerifyConfig;
import gameserver.config.ServerConfig;
import gameserver.connection.attribute.ConAccountAttr;
import gameserver.connection.attribute.ConPlayerAttr;
import gameserver.http.HttpProcessManager;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.IOServer;
import gameserver.network.protos.common.ProPvpMatch;
import gameserver.network.protos.common.ProPvpMatch.ACCOUNT_OPER_TYPE;
import gameserver.network.protos.game.ProGameInfo;
import gameserver.network.protos.game.ProLogin;
import gameserver.network.protos.game.ProLogin.LOGIN_TYPE;
import gameserver.network.protos.game.ProLogin.Msg_C2G_BindAccount;
import gameserver.network.protos.game.ProLogin.Msg_C2G_ReleaseAccount;
import gameserver.network.protos.game.ProLogin.Msg_C2G_isBindAccount;
import gameserver.network.protos.game.ProLogin.Msg_G2C_AskLogin;
import gameserver.network.protos.game.ProLogin.Msg_G2C_BindAccount_Re;
import gameserver.network.protos.game.ProLogin.Msg_G2C_CreatePlayer;
import gameserver.network.protos.game.ProLogin.Msg_G2C_isBindAccount_Re;
import gameserver.network.server.connection.Connection;
import gameserver.share.ShareServerManager;
import gameserver.stat.StatManger;
import gameserver.thread.ThreadPoolManager;
import gameserver.utils.DbMgr;
import gameserver.utils.Item_Channel;
import gameserver.utils.NameFilter;
import gameserver.utils.Util;

import java.util.ArrayList;
import java.util.List;

import loginserver.utils.qihoo.HttpClient;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.Int4;
import table.MT_Data_AirSupport;
import table.MT_Data_PlayerConfig;
import table.base.TableManager;

import com.commons.util.DatabaseInsertUpdateResult;
import com.commons.util.DatabaseUtil;
import com.commons.util.FileUtil;
import com.commons.util.HttpProcessResult;
import com.commons.util.HttpUtil;
import com.commons.util.JsonUtil;
import com.commons.util.TimeUtil;
import com.commons.util.string;
import commonality.AccountResult;
import commonality.AccountVerify;
import commonality.CHANNEL_TYPE;
import commonality.Common;
import commonality.Common.SETNUMBER_TYPE;
import commonality.ErrorCode;
import commonality.ErrorCodeAccount;
import commonality.ProductChannel;
import commonality.PromptType;
import commonality.QueryPlayerDBRequest;
import commonality.QueryPlayerDBResult;
import commonality.SHARE_SERVER_TYPE;
import commonality.VmChannel;

import database.CustomInstanceStar;
import database.DatabasePlayer;
import databaseshare.DatabaseAccount;
import databaseshare.DatabaseDeviceToken;
import databaseshare.DatabasePvp_match;
import databaseshare.DatabaseWhite_list;

public class ClientMessageAuth {
	private final static ClientMessageAuth instance = new ClientMessageAuth();
	public static ClientMessageAuth getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(ClientMessageAuth.class);
	HttpClient httpClient = new HttpClient();

	public void initialize(){
		//login不检测session 消息返回客户端session
    	IOServer.getInstance().regMsgProcess(ProLogin.Msg_C2G_AskLogin.class, this, "OnAskLogin",false);
    	//请求进入游戏
    	IOServer.getInstance().regMsgProcess(ProLogin.Msg_C2G_AskEnter.class, this, "OnAskEnter", true);
    	//注册推送Token
    	IOServer.getInstance().regMsgProcess(ProLogin.Msg_C2G_RegisterDeviceToken.class, this, "OnRegisterDeviceToken");
    	
    	IOServer.getInstance().regMsgProcess(ProLogin.Msg_C2G_CreatePlayer.class, this, "OnCreatePlayer",false);
    	
    	IOServer.getInstance().regMsgProcess(ProGameInfo.Msg_C2G_KeepAlive.class, this, "OnKeepAlive");
    	
    	//玩家将游戏暂停
    	IOServer.getInstance().regMsgProcess(ProGameInfo.Msg_C2G_OnPause.class, this, "OnPause",false);
    	
    	IOServer.getInstance().regMsgProcess(ProLogin.Msg_C2G_BindAccount.class, this, "OnBindAccount", false);
    	IOServer.getInstance().regMsgProcess(ProLogin.Msg_C2G_ReleaseAccount.class, this, "OnReleaseAccount", false);
    	IOServer.getInstance().regMsgProcess(ProLogin.Msg_C2G_isBindAccount.class, this, "OnIsBindAccount", false);
    	
    	
    }
		
	public void init() {
//		ShareServerManager.getInstance().regMsgProcess(ProPvpMatch.Msg_S2G_CreateAccount.class, this, "");
    	ShareServerManager.getInstance().regMsgProcess(ProPvpMatch.Msg_S2G_AccountOnline.class, this, "OnAccountOnlineShare");
	}
	
	public void OnAccountOnlineShare(Connection connect,SHARE_SERVER_TYPE type,int id,ProPvpMatch.Msg_S2G_AccountOnline msg){
		if (msg.getOnlineType() == ACCOUNT_OPER_TYPE.ONLINE && msg.getErrorCode() != 0) {
			connect.doKickOutPlayer(connect.getAccountTemp().getPlayerId());
			connect.sendReceiptMessage(Msg_G2C_AskLogin.newBuilder().setHaveAccount(-1).build());
			return;
		}
		
		if (msg.getOnlineType() == ACCOUNT_OPER_TYPE.ONLINE) {
			sendAskLoginRe(connect, 
					3, 
					connect.getAccountTemp().getSession(), 
					connect.getAccountTemp().getLoginInfo(),
					connect.getAccountTemp().getUid(),
					connect.getAccountTemp().getToken(),
					connect.getAccountTemp().isPasser());
		}
		else if (msg.getOnlineType() == ACCOUNT_OPER_TYPE.CREATE){
			ProLogin.Msg_G2C_CreatePlayer message = ProLogin.Msg_G2C_CreatePlayer.newBuilder().
					setSucceed(0).
					setSession(connect.getAccountTemp().getSession()).
					setTime(TimeUtil.GetDateTime()).build();
			connect.sendReceiptMessage(message);
		}
	}
	
	public void OnIsBindAccount(Connection con, Msg_C2G_isBindAccount msg) {
		String account_id = msg.getAccountId();
		DatabaseAccount ac = DbMgr.getInstance().getShareDB().selectFieldFrist(DatabaseAccount.class, "uid=?", "passer", account_id);
		if (ac != null && !ac.passer) {
			con.sendReceiptMessage(Msg_G2C_isBindAccount_Re.newBuilder().setState(1)
					.setAccountId(account_id).setLoginInfo(msg.getLoginInfo()).build());
			return ;
		}
		
		con.sendReceiptMessage(Msg_G2C_isBindAccount_Re.newBuilder()
				.setState(0).setAccountId(account_id).setLoginInfo(msg.getLoginInfo()).build());
	}
	
	// 绑定账号
	public void OnBindAccount(Connection con, Msg_C2G_BindAccount msg) {
		ConAccountAttr acc = con.getAccount();
		String account_id = msg.getAccountId();
		
		int count = DbMgr.getInstance().getShareDB().Count(DatabaseAccount.class, "uid=?", msg.getAccountId());
		if (count > 0) {
			return ;
		}
		
		if (!acc.getUid().isEmpty()) {
			// 已经绑定了
			con.sendReceiptMessage(Msg_G2C_BindAccount_Re.newBuilder().setSuccess(1).setAccountId(account_id).build());
			return ;
		}
		
		DbMgr.getInstance().getShareDB().Execute("update account set uid=?,login_info=?,passer=0 where player_id=?", 
						msg.getAccountId(), msg.getLoginInfo(), con.getPlayerId());
		con.sendReceiptMessage(Msg_G2C_BindAccount_Re.newBuilder()
				.setSuccess(0)
				.setAccountId(account_id)
				.setLoginInfo(msg.getLoginInfo())
				.build());
	}
	
	public void OnReleaseAccount(Connection con, Msg_C2G_ReleaseAccount msg) {
		ConAccountAttr acc = con.getAccount();
		if (acc.getUid() == null) {
			// 还没有绑定
			return ;
		}
		
		if (acc.getUid().isEmpty()) {
			// 还没有绑定
			return ;
		}
		
		DbMgr.getInstance().getShareDB().Execute("update account set passer=1 where player_id=?", 
				con.getPlayerId());
	}
	
	private static final String AppKey = "a39014d9651aef103924135fb013af3d";
	private static final String AppSecret = "38f944e4dfb1e21f16e2bc4178beed51";
	private static final String ServerURL_360 = "https://openapi.360.cn";
	private static final String AccessToken = "/oauth2/access_token?";
	private static final String GetUserInfo = "/user/me.json?";
	private String build360AuthString(String session)
	{
		String info = "grant_type=authorization_code" 
				+ "&client_id=" + AppKey
				+ "&client_secret=" + AppSecret
				+ "&redirect_uri=oob" 
				+ "&code=" + session;
		logger.debug("360 LoginInfo: " + info);		
		return info;
	}
	
	private String analyse360AccessToken(String retStr)
	{
		if (retStr == null)
			return null;
		
		try
		{
			JSONParser parser = new JSONParser();
			JSONObject result = (JSONObject)parser.parse(retStr);
			
			logger.debug("get Token=" + result.toString());
			
			String ret = (String)result.get("access_token");
			ret += "&";
			ret += result.get("refresh_token");
			return ret;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	private String build360GetUserInfo(String accessToken)
	{
		String info = "access_token=" + accessToken
				+ "&fields=id,name,avatar,sex,area";
				      
		logger.debug("360 GetUserInfo: " + info);		
		return info;
	}
	
	private String analyse360UserInfo(String retStr)
	{
		if (retStr == null)
			return null;
		
		try
		{
			JSONParser parser = new JSONParser();
			JSONObject result = (JSONObject)parser.parse(retStr);
			
			logger.debug("360 GetUserInfo=" + result.toString());
			
			String uid = (String)result.get("id");
			return uid;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	String Verify(CHANNEL_TYPE channel, String uid, String session){
		try{
			if (channel == CHANNEL_TYPE.GooglePlayWar ||
					channel == CHANNEL_TYPE.GooglePlayWar) {
				AccountVerify data = new AccountVerify(uid,session,1000000);
				HttpProcessResult result = HttpUtil.httpPost(AccountVerifyConfig.address, JsonUtil.ObjectToJson(data));
				AccountResult account = JsonUtil.JsonToObject(HttpUtil.toString(result), AccountResult.class);
				if (account.code == ErrorCodeAccount.SUCCEED.ordinal())
					return uid;
				else
					return null;
			} else if (channel == CHANNEL_TYPE.Q360) {
				logger.error("360 session:{}", uid);
				String queryStr = build360AuthString(uid);
				String retStr = httpClient.get(ServerURL_360 + AccessToken + queryStr, null); // Util.httpSSLSynSend(ServerURL_360 + AccessToken + queryStr, "get", null);
				String tokStr = analyse360AccessToken(retStr);
				
				if (tokStr == null)
					return null;
				
				String[] tokens = tokStr.split("&");
				
				// 2. 获取 360 用户信息，主要是为了析出其中的 userId
				queryStr = build360GetUserInfo(tokens[0]);
				retStr = httpClient.get(ServerURL_360 + GetUserInfo + queryStr, null);
				String userid = analyse360UserInfo(retStr);
				
				return userid + "&" + tokens[0];
			}
		}catch (Exception e){
			logger.error("Verify is error : " , e);
		}
		return null;
	}
	//玩家没有账户---创建角色，客户端传来名称
	public void OnCreatePlayer(Connection connection, ProLogin.Msg_C2G_CreatePlayer message) throws Exception{
		if (connection.isInState(Connection.Msg_C2G_CreatePlayer))
			return ;
		
		connection.setState(true, Connection.Msg_C2G_CreatePlayer);
		final Connection cn = connection;
		final ProLogin.Msg_C2G_CreatePlayer msg = message;
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					asyncCreatePlayer(cn, msg);
				} catch (Exception e) {
					logger.error("OnCreatePlayer err, ", e);
				} finally {
					cn.setState(false, Connection.Msg_C2G_CreatePlayer);
				}
			}
		}, 0);
	}

	private void asyncCreatePlayer(Connection connection,
			ProLogin.Msg_C2G_CreatePlayer message) throws Exception {
		if (NameFilter.getIns().containWord(message.getName())) {
			connection.ShowPrompt(PromptType.PLAYER_NAME_INVAILD);
			Msg_G2C_CreatePlayer.Builder  meBuilder = Msg_G2C_CreatePlayer.newBuilder();
			meBuilder.setSucceed(1);
			connection.sendReceiptMessage(meBuilder.build());
			return;
		}
		
		//检查名称是否重复
		int count = DbMgr.getInstance().getShareDB().Count(DatabasePvp_match.class, "name=?", message.getName());
		if (count != 0){
			Msg_G2C_CreatePlayer.Builder  meBuilder = Msg_G2C_CreatePlayer.newBuilder();
			meBuilder.setSucceed(2);
			connection.sendReceiptMessage(meBuilder.build());
			return;
		}
		
		//名称不重复，直接创建账户
		int channel = message.getChannel();
		String uid = message.getUid();
		int account_type = 0;
		if (message.getType() == LOGIN_TYPE.ACCOUNT) {
			account_type = 0;
		}else {
			account_type = 1;
		}
		long datenum = TimeUtil.GetDateTime();
		String session = FileUtil.GetMD5FromString(uid + datenum);
		
		//创建角色
		QueryPlayerDBResult registerResult = LoginOrCreate(connection, channel, 
				uid, session, account_type,message.getPlatform().toString(),message.getDeviceUniqueIdentifier());
		if (registerResult == null) {
			Msg_G2C_CreatePlayer.Builder  meBuilder = Msg_G2C_CreatePlayer.newBuilder();
			meBuilder.setSucceed(3);
			connection.sendReceiptMessage(meBuilder.build());
			return ;
		}
		
		DatabaseUtil dbHelper = DbMgr.getInstance().getDb(registerResult.db_id);
		MT_Data_PlayerConfig config = TableManager.GetInstance().TablePlayerConfig().GetElement(0);
		//创建player
		DatabasePlayer player = createPlayer(connection, registerResult,dbHelper,config,message.getName(),message.getHead());
		if (player == null) {
			Msg_G2C_CreatePlayer.Builder  meBuilder = Msg_G2C_CreatePlayer.newBuilder();
			meBuilder.setSucceed(4);
			connection.sendReceiptMessage(meBuilder.build());
			return ;
		}
		connection.setPlayerId(registerResult.id);
		//初始化玩家所有的conAttrbute,把玩家相关的数据读取到内存
		connection.CheckSession(session,false);
		
		//初始化建筑
		for (Int4 build : config.Builds())
			connection.getBuild().insertBuild(build.field1(), build.field3(), build.field4(),build.field2());
		//初始化物品
		long s_num = IPOManagerDb.getInstance().getNextId();
		for (Int2 item : config.Items())
			connection.getItem().setItemNumber(item.field1(), item.field2(), SETNUMBER_TYPE.SET,
					VmChannel.InGameDrop, ProductChannel.InGameDrop, s_num, "", Item_Channel.CREATE_ROLE);
		//初始化兵种
		for (Int2 corps : config.Corps())
			connection.getCorps().setCorpsNumber(corps.field1(), corps.field2(), SETNUMBER_TYPE.SET);
		
		//初始化空中支援
		for (Integer id : config.AirSupports()){
			MT_Data_AirSupport data = TableManager.GetInstance().TableAirSupport().GetElement(id);
			if (data != null)
				connection.getAir().AirSupportBuy(data);
		}
		connection.CheckUpline();
		StatManger.getInstance().onNewPlayer();
		LogService.logNewPlayer(registerResult.id, message.getType() == LOGIN_TYPE.ACCOUNT ? 0:1, uid, 
				connection.getIP(), message.getPlatform().toString(), message.getDeviceUniqueIdentifier(),player.nation,TimeUtil.GetDateString(player.create_time));
		try{
			IPOManagerDb.getInstance().UserRegister(connection);
			IPOManagerDb.getInstance().UserLogin(connection);
		}catch (Exception e){
			logger.error("register log is error : ", e);
		}
		
		connection.setAccountTemp(true,registerResult.id,uid,null,session,null);
		connection.sendAccountStateToShare(registerResult.id, ACCOUNT_OPER_TYPE.CREATE);
	}

	private QueryPlayerDBResult LoginOrCreate(Connection connection,int channel, String uid,String session,int type,String platform,String identifier ) {
		QueryPlayerDBRequest query = new QueryPlayerDBRequest();
		query.type = type;
		query.channel = channel;
		query.uid = uid;
		query.player_id = 0L;
		query.session = session;
		query.last_login_ip = connection.getIP();
		query.last_login_platform = platform;
		query.last_login_time = TimeUtil.GetDateString();
		query.last_device_unique_identifier = identifier;

		HttpProcessResult httpResult = HttpProcessManager.getInstance().httpPostAskLogin(query);
		if (httpResult.succeed == false) {
			logger.error("请求服务器地址出错   status : {}", httpResult.statusCode);
			ProLogin.Msg_G2C_AskLogin msg = ProLogin.Msg_G2C_AskLogin.newBuilder().setHaveAccount(1).build();
			connection.sendReceiptMessage(msg);
			return null;
		}
		String strResult = HttpUtil.urldecode(HttpUtil.toString(httpResult));
		logger.info("请求服务器地址返回字符串 : {}", strResult);
		QueryPlayerDBResult registerResult = JsonUtil.JsonToObject(strResult, QueryPlayerDBResult.class);
		if (registerResult == null || registerResult.code != ErrorCode.SUCCEED.ordinal()) {
			logger.error("请求地址出错  errorcode : {}  errormessage : {}",registerResult.code, registerResult.message);
			ProLogin.Msg_G2C_AskLogin msg = ProLogin.Msg_G2C_AskLogin.newBuilder().setHaveAccount(1).build();
			connection.sendReceiptMessage(msg);
			return null;
		}
		return registerResult;
	}

	//玩家登录
	public void OnAskLogin(Connection connection, ProLogin.Msg_C2G_AskLogin message) {
		try{
			int channel = message.getChannel();
			CHANNEL_TYPE channelEnum = CHANNEL_TYPE.valueOf(channel);
			String where = "";
			String uid = "";
			String token = "";
			if (message.getType() == LOGIN_TYPE.ACCOUNT) {
				if (channelEnum == CHANNEL_TYPE.Q360) {
					String res = Verify(channelEnum ,message.getUid(), message.getSession());
					if (res == null) {
						ProLogin.Msg_G2C_AskLogin msg = ProLogin.Msg_G2C_AskLogin.newBuilder().setHaveAccount(1).build();
						connection.sendReceiptMessage(msg);
						return;
					}

					String[] pairs = res.split("&");
					uid = channelEnum.name() + "_" + pairs[0];
					token = pairs[1];
				} else {
					uid = message.getUid();
				}
				where = "uid = ? ";
			}else {
				uid = string.Format("{}_{}", message.getPlatform().toString(),message.getDeviceUniqueIdentifier());
				where = "temp_uid = ? "; 
			}
			
			String nation = Util.ipToCountry(connection.getIP());
			boolean iswhilelist = DbMgr.getInstance().getShareDB().Count(DatabaseWhite_list.class,"create_device_unique_identifier=?",message.getDeviceUniqueIdentifier()) > 0;
			if (nation.equals("CN") && ServerConfig.cn_black && !iswhilelist) {
				ProLogin.Msg_G2C_AskLogin msg = ProLogin.Msg_G2C_AskLogin.newBuilder().setHaveAccount(5).build();
				connection.sendReceiptMessage(msg);
				return;
			}
			
			//账户查询
			DatabaseAccount account = DbMgr.getInstance().getShareDB().SelectOne(DatabaseAccount.class, where, uid);
			connection.setRegistration(message.getRegistrationID());
			
			if (account == null){
				//没有账户
				Msg_G2C_AskLogin.Builder msg =  Msg_G2C_AskLogin.newBuilder();
				msg.setHaveAccount(2);//2代表没有账号
				msg.setUid(uid);
				msg.setToken(token);
				connection.sendReceiptMessage(msg.build());
				return;
			}else{
				if (account.banned == 1 && TimeUtil.GetDateTime() < account.banned_time) {
					ProLogin.Msg_G2C_AskLogin msg = ProLogin.Msg_G2C_AskLogin.newBuilder().setHaveAccount(4).build();
					connection.sendReceiptMessage(msg);
					int time = (int) (account.banned_time - TimeUtil.GetDateTime()/1000);
					connection.ShowPrompt(PromptType.BANE_TIME,GetCharCountDown(time));
					return;
				}
				
				connection.setAccountTemp(account.passer,
						account.player_id,
						account.passer?uid:account.uid,
						token,
						account.session,
						account.login_info);
				connection.sendAccountStateToShare(account.player_id, ACCOUNT_OPER_TYPE.ONLINE);
			}
		}catch (Exception e){
			logger.error("OnAskLogin is error : ",e);
		}
	}
	
	private void sendAskLoginRe(Connection connection, int code, String session, String login_info, String uid, String token, boolean ispasser) {
		Msg_G2C_AskLogin.Builder msg =  Msg_G2C_AskLogin.newBuilder();
		msg.setHaveAccount(code);
		msg.setSession(session);
		msg.setTime(TimeUtil.GetDateTime());
		msg.setUid(uid);
		msg.setToken(token);
		msg.setIsPasser(ispasser);
		if (login_info != null)
			msg.setLoginInfo(login_info);
		connection.sendReceiptMessage(msg.build());
	}

	private DatabasePlayer createPlayer(Connection con, QueryPlayerDBResult registerResult, DatabaseUtil dbHelper, MT_Data_PlayerConfig config,String name,int head) {
		try {
			String nation = Util.ipToCountry(con.getIP());
			
			DatabasePlayer player = new DatabasePlayer();
			player.name = name;
			player.head = head;
			player.player_id = registerResult.id;
			player.robot = 0;
			player.level = 1;
			player.feat = 0;
			player.vipLevel = 0;
			player.total_login_num = 0;
			player.last_offline_time = TimeUtil.GetDateTime() - 24 * 3 * 3600 * 1000;
			player.queue_size = Common.QUEUE_SIZE;
			player.nation = nation;
			player.cp_time = TimeUtil.GetDateTime();
			DatabaseAccount account = DbMgr.getInstance().getShareDB().SelectOne(DatabaseAccount.class, "player_id = ? ",player.player_id);
			player.create_time = account.create_time;
			account.nation = nation;
			account.save();
			
			//初始化Pve初始Id
			player.instance_id = config.InstanceId();
			List<CustomInstanceStar> stars = new ArrayList<CustomInstanceStar>();
			stars.add(new CustomInstanceStar(config.InstanceId(), 0, 0, (byte)0));
			player.instance_star_array = stars;
			DatabaseInsertUpdateResult result = dbHelper.Insert(player);
			if (result == null || !result.succeed)
				throw new Exception("save new player error");
			
			//创建pvp_match
			DatabasePvp_match pvpinfo = new DatabasePvp_match();
			pvpinfo.player_id = registerResult.id;
			pvpinfo.level = 1;
			pvpinfo.feat = 0;
			pvpinfo.name = player.name;
			pvpinfo.robot = false;
			pvpinfo.create_time = TimeUtil.GetTimestamp(account.create_time);
			pvpinfo.nation = nation;
			DbMgr.getInstance().getShareDB().Insert(pvpinfo);
			
			return player;
		} catch (Exception e) {
			DbMgr.getInstance().getShareDB().Delete(DatabaseAccount.class, "player_id = ? ", registerResult.id);
			DbMgr.getInstance().getShareDB().Delete(DatabasePvp_match.class, "player_id = ? ", registerResult.id);
			dbHelper.Delete(DatabasePlayer.class, "player_id = ? ", registerResult.id);
			logger.error("创建player出错", e);
		}
		return null;
	}

	public void OnAskEnter(Connection connection,ProLogin.Msg_C2G_AskEnter message){
		connection.UpLine();
		connection.sendReceiptMessage(ProLogin.Msg_G2C_AskEnter.newBuilder().build());
		connection.setNot_send_flag(true);
	}
	
	public void OnKeepAlive(Connection connection, ProGameInfo.Msg_C2G_KeepAlive message) {
		long curTime = TimeUtil.GetDateTime();
		connection.setLastkeeplive(curTime);
		connection.sendPushMessage(ProGameInfo.Msg_G2C_KeepAlive.newBuilder().setCurtime(curTime).build());
	}
	
	public void OnPause(Connection connection, ProGameInfo.Msg_C2G_OnPause message) {
		ConPlayerAttr player = connection.getPlayer();
		player.setGamePause(message.getPause());
	}
	
	public void OnRegisterDeviceToken(Connection connection,ProLogin.Msg_C2G_RegisterDeviceToken message){
		DatabaseDeviceToken deviceToken = DbMgr.getInstance().getShareDB().SelectOne(DatabaseDeviceToken.class, "deviceUniqueIdentifier = ? and platform = ?", message.getDeviceUniqueIdentifier(),message.getPlatform().toString());
		if (deviceToken != null){
			deviceToken.device_token = message.getDeviceToken();
			deviceToken.player_id = connection.getPlayerId();
			DbMgr.getInstance().getShareDB().Update(deviceToken, "id = ?", deviceToken.id);
		}else{
			deviceToken = new DatabaseDeviceToken();
			deviceToken.deviceUniqueIdentifier = message.getDeviceUniqueIdentifier();
			deviceToken.platform = message.getPlatform();
			deviceToken.device_token = message.getDeviceToken();
			deviceToken.player_id = connection.getPlayerId();
			DbMgr.getInstance().getShareDB().Insert(deviceToken);
		}
	}

	public static String GetCharCountDown(int time) {
		if (time > Common.HOUR_SECOND)
			return time / 3600 + " h " + time % 3600 / 60 + " m";
		else if (time > Common.MINUTE_SECOND)
			return time / 60 + " m " + time % 60 + " s";
		else
			return time + " s";
	}
}
