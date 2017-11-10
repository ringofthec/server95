package com.commons.network.websock.handler;

import gameserver.config.AccountVerifyConfig;
import gameserver.http.HttpProcessManager;
import gameserver.jsonprotocol.GDL_C2G_CreateRole;
import gameserver.jsonprotocol.GDL_C2G_Login;
import gameserver.jsonprotocol.GDL_C2G_RandomName;
import gameserver.jsonprotocol.GDL_G2C_CreateRoleRe;
import gameserver.jsonprotocol.GDL_G2C_LoginRe;
import gameserver.jsonprotocol.GDL_G2C_RandomNameRe;
import gameserver.logging.LogService;
import gameserver.stat.StatManger;
import gameserver.utils.DbMgr;
import gameserver.utils.NameFilter;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;

import loginserver.utils.qihoo.HttpClient;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_PlayerConfig;
import table.base.TableManager;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConManager;
import com.commons.network.websock.PlayerConnection;
import com.commons.util.DatabaseInsertUpdateResult;
import com.commons.util.DatabaseUtil;
import com.commons.util.FileUtil;
import com.commons.util.HttpProcessResult;
import com.commons.util.HttpUtil;
import com.commons.util.JsonUtil;
import com.commons.util.RandomUtil;
import com.commons.util.TimeUtil;
import com.commons.util.string;
import com.gdl.data.PlayerData;
import commonality.AccountResult;
import commonality.AccountVerify;
import commonality.CHANNEL_TYPE;
import commonality.Common;
import commonality.ErrorCode;
import commonality.ErrorCodeAccount;
import commonality.QueryPlayerDBRequest;
import commonality.QueryPlayerDBResult;

import database.gdl.gameserver.DatabasePlayer;
import databaseshare.CustomGiftShow;
import databaseshare.CustomLong1Info;
import databaseshare.CustomMillStone;
import databaseshare.DatabaseAccount;
import databaseshare.DatabaseMoney_static;
import databaseshare.DatabasePlayer_brief_info;
import databaseshare.DatabasePvp_match;

public class LoginHandler {
	private final static LoginHandler instance = new LoginHandler();
	public static LoginHandler getInstance() { return instance; }
	public static boolean can_login = true;
	private final Logger logger = LoggerFactory.getLogger(LoginHandler.class);
	
	public void init() {
		HandlerManager.getInstance().pushLoginHandler(LoginHandler.class, this, "OnLogin", new GDL_C2G_Login());
		HandlerManager.getInstance().pushLoginHandler(LoginHandler.class, this, "OnCreateRole", new GDL_C2G_CreateRole());
		HandlerManager.getInstance().pushLoginHandler(LoginHandler.class, this, "OnRandomName", new GDL_C2G_RandomName());
	}
	
	private QueryPlayerDBResult LoginOrCreate(ChannelHandlerContext ctx,int channel, String uid,String session,int type,String platform,String identifier ) {
		QueryPlayerDBRequest query = new QueryPlayerDBRequest();
		query.type = type;
		query.channel = channel;
		query.uid = uid;
		query.player_id = 0L;
		query.session = session;
		query.last_login_ip = ctx.channel().localAddress().toString();
		query.last_login_platform = platform.toUpperCase();
		query.last_login_time = TimeUtil.GetDateString();
		query.last_device_unique_identifier = identifier;

		HttpProcessResult httpResult = HttpProcessManager.getInstance().httpPostAskLogin(query);
		if (httpResult.succeed == false) {
			logger.error("请求服务器地址出错   status : {}", httpResult.statusCode);
			return null;
		}
		String strResult = HttpUtil.urldecode(HttpUtil.toString(httpResult));
		logger.info("请求服务器地址返回字符串 : {}", strResult);
		QueryPlayerDBResult registerResult = JsonUtil.JsonToObject(strResult, QueryPlayerDBResult.class);
		if (registerResult == null || registerResult.code != ErrorCode.SUCCEED.ordinal()) {
			logger.error("请求地址出错  errorcode : {}  errormessage : {}",registerResult.code, registerResult.message);
			return null;
		}
		
		return registerResult;
	}
	
	private class ResInfo {
		DatabasePlayer m_player;
		DatabasePlayer_brief_info m_pvpinfo;
		DatabaseMoney_static m_money;
		
		public ResInfo(DatabasePlayer player, DatabasePlayer_brief_info pvpinfo, DatabaseMoney_static money) {
			m_player = player;
			m_pvpinfo = pvpinfo;
			m_money = money;
		}
	}
	
	private ResInfo createPlayer(ChannelHandlerContext ctx, QueryPlayerDBResult registerResult, 
			DatabaseUtil dbHelper, MT_Data_PlayerConfig config, GDL_C2G_CreateRole message, DatabaseAccount account) {
		try {
			String nation = "CN";
			
			DatabasePlayer player = new DatabasePlayer();
			player.name = message.getPlayerName().substring(0, Math.min(message.getPlayerName().length(), 6));
			player.head = message.getHead();
			player.player_id = registerResult.id;
			player.sex = message.getSex();
			player.nation = nation;
			player.total_login_num = 0;
			player.continue_login_num = 0;
			player.rotary_reward_get = 0;
			player.last_offline_time = TimeUtil.GetDateTime() - 24 * 3 * 3600 * 1000;
			player.last_flush_time = TimeUtil.GetDateTime() - 24 * 3 * 3600 * 1000;
			player.create_time = account.create_time;
			player.showgift_config = new ArrayList<database.gdl.gameserver.CustomGiftShow>();
			player.level_rewards = new ArrayList<database.gdl.gameserver.CustomInt1Info>();
			player.lotty_reward_coin_idx = 0;
			player.lotty_reward_gold_idx = 0;
			
			//初始化Pve初始Id
			DatabaseInsertUpdateResult result = dbHelper.Insert(player);
			if (result == null || !result.succeed) {
				logger.error("创建player出错1, 插入player错误了");
				return null;
			}
			
			//创建pvp_match
			DatabasePlayer_brief_info pvpinfo = new DatabasePlayer_brief_info();
			pvpinfo.player_id = registerResult.id;
			pvpinfo.level = 1;
			pvpinfo.name = player.name;
			pvpinfo.head = 0;
			pvpinfo.head_url = "TX" + RandomUtil.RangeRandom(1, 10) + ".png";
			pvpinfo.money = 0L;
			pvpinfo.online = true;
			pvpinfo.viplevel = 0;
			pvpinfo.showgift = new ArrayList<CustomGiftShow>();
			pvpinfo.sign = "彪悍的人生需要解釋嘛？不需要！！！";
			pvpinfo.sex = message.getSex();
			pvpinfo.rank_level = 0L;
			pvpinfo.rank_liked = 0L;
			pvpinfo.rank_money = 0L;
			pvpinfo.exp = 0L;
			pvpinfo.last_active_time = TimeUtil.GetDateTime();
			result = DbMgr.getInstance().getShareDB().Insert(pvpinfo);
			if (result == null || !result.succeed) {
				logger.error("创建player出错2, 插入 Player_brief_info 错误了");
				return null;
			}
			
			DatabaseMoney_static moneyinfo = new DatabaseMoney_static();
			moneyinfo.player_id = registerResult.id;
			moneyinfo.days7 = new ArrayList<CustomLong1Info>();
			moneyinfo.fish_battle_doomfish = 0L;
			moneyinfo.fish_battle_kingfish = 0L;
			moneyinfo.fish_catch_fish_total = 0L;
			moneyinfo.fish_max = 0L;
			moneyinfo.fish_task_count = 0L;
			moneyinfo.fish_total = 0L;
			
			moneyinfo.fruits_slot_count = 0L;
			moneyinfo.fruits_slot_max = 0L;
			moneyinfo.fruits_slot_pool_count = 0;
			moneyinfo.fruits_slot_pool_total = 0L;
			moneyinfo.fruits_slot_rose_top3_count = 0L;
			moneyinfo.fruits_slot_total = 0L;
			
			moneyinfo.gold_slot_count = 0L;
			moneyinfo.gold_slot_gold = 0L;
			moneyinfo.gold_slot_max = 0L;
			moneyinfo.gold_slot_pool_count = 0;
			moneyinfo.gold_slot_pool_total = 0L;
			moneyinfo.gold_slot_total = 0L;
			moneyinfo.millstone = new ArrayList<CustomMillStone>();
			result = DbMgr.getInstance().getShareDB().Insert(moneyinfo);
			if (result == null || !result.succeed) {
				logger.error("创建player出错3, 插入 Money_static 错误了");
				return null;
			}
			moneyinfo = DbMgr.getInstance().getShareDB().SelectOne(DatabaseMoney_static.class, 
					"player_id=?", registerResult.id);
			
			return new ResInfo(player, pvpinfo, moneyinfo);
		} catch (Exception e) {
			DbMgr.getInstance().getShareDB().Delete(DatabaseAccount.class, "player_id = ? ", registerResult.id);
			DbMgr.getInstance().getShareDB().Delete(DatabasePlayer_brief_info.class, "player_id = ? ", registerResult.id);
			DbMgr.getInstance().getShareDB().Delete(DatabaseMoney_static.class, "player_id = ? ", registerResult.id);
			dbHelper.Delete(DatabasePlayer.class, "player_id = ? ", registerResult.id);
			logger.error("创建player出错3", e);
		}
		return null;
	}
	
	
	public void OnCreateRole(ChannelHandlerContext ctx, GDL_C2G_CreateRole message) {
		
		
		//名称不重复，直接创建账户
		int channel = message.getChannel();
		String uid = message.getUuid();
		int account_type = message.getType();
		long datenum = TimeUtil.GetDateTime();
		String session = FileUtil.GetMD5FromString(uid + datenum);
		
		GDL_G2C_CreateRoleRe msg = new GDL_G2C_CreateRoleRe();
		int ret = isNameVaild(ctx, message.getPlayerName());
		if (ret != 0) {
			msg.setRetCode(ret);
			HandlerManager.getInstance().sendMsg(ctx, msg);
			return ;
		}
		
		if (account_type == 0) {
			uid = message.getPlatform() + "_" + uid;
		}
		
		//创建角色
		QueryPlayerDBResult registerResult = LoginOrCreate(ctx, channel, 
				uid, session, account_type,message.getPlatform().toString(),message.getDev_id());
		if (registerResult == null) {
			msg.setRetCode(3);
			HandlerManager.getInstance().sendMsg(ctx, msg);
			return ;
		}
		
		DatabaseUtil dbHelper = DbMgr.getInstance().getDb(registerResult.db_id);
		MT_Data_PlayerConfig config = TableManager.GetInstance().TablePlayerConfig().GetElement(0);
		//创建player
		
		DatabaseAccount account = DbMgr.getInstance().getShareDB().SelectOne(DatabaseAccount.class, "player_id=?", registerResult.id);
		if (account == null) {
			msg.setRetCode(3);
			HandlerManager.getInstance().sendMsg(ctx, msg);
			return ;
		}
		
		ResInfo rinfo = createPlayer(ctx, registerResult,dbHelper,config, message, account);
		if (rinfo == null || rinfo.m_player == null || rinfo.m_pvpinfo == null) {
			msg.setRetCode(3);
			HandlerManager.getInstance().sendMsg(ctx, msg);
			return ;
		}
		
		PlayerData pd = new PlayerData(account, rinfo.m_player, rinfo.m_pvpinfo, rinfo.m_money);
		pd.init();
		PlayerConnection pc = new PlayerConnection(ctx, pd);
		PlayerConManager.getInstance().putCon(pd.getPlayerId(), pc);
		
		msg.setPlayerId(account.player_id);
		msg.setRetCode(0);
		HandlerManager.getInstance().sendMsg(ctx, msg);

		StatManger.getInstance().onNewPlayer();
		LogService.logNewPlayer(registerResult.id, message.getType(), uid, 
				ctx.channel().localAddress().toString(), message.getPlatform().toString(), message.getUuid(),
				"CN",TimeUtil.GetDateString(rinfo.m_player.create_time));
	}

	public int isNameVaild(ChannelHandlerContext ctx,
			String name) {
		if (NameFilter.getIns().containWord(name)) {
			HandlerManager.getInstance().showToast(ctx, "名字中包含不合法字符串");
			return 1;
		}

		//检查名称是否重复
		int count = DbMgr.getInstance().getShareDB().Count(DatabasePvp_match.class, "name=?", name);
		if (count != 0){
			HandlerManager.getInstance().showToast(ctx, "已经有人用这个名字啦");
			return 2;
		}
		
		return 0;
	}
	
	String[] first_name={"冯","陈","诸","卫","蒋","沈","韩","杨","朱","秦","尤","许","何","吕","施","张","孔","曹","严","华","金","魏","陶","姜","戚","谢","邹","喻","柏","水","窦","章","云","苏","潘","葛","奚","范","彭","郎","鲁","韦","昌","马","苗","凤","花","方","俞","任","袁","柳","鲍","史","唐","费","廉","岑","薛","雷","贺","倪","汤","滕","殷","罗","毕","郝","邬","安","常","乐","于","时","傅","皮","卡","齐","康","伍","余","元","卜","顾","孟","平","黄","和","穆","萧","尹","姚","邵","堪","汪","祁","毛","禹","狄","米","贝","明","臧","计","伏","成","戴","谈","宋","茅","庞","熊","纪","舒","屈","项","祝","董","粱","杜","阮","蓝","闵","席","季","麻","强","贾","路","娄","危","江","童","颜","郭","梅","盛","林","刁","钟","徐","邱","骆","高","夏","蔡","田","樊","胡","凌","霍","虞","万","支","柯","咎","管","卢","莫","经","房","裘","缪","干","解","应","宗","丁","宣","贲","邓","郁","单","杭","洪","包","诸","左","石","崔","吉","钮","龚","程","嵇","邢","滑","裴","陆","荣","翁","荀","羊","於","惠","甄","魏","家","封","芮","羿","储","靳","汲","邴","糜","松","井","段","富","巫","乌","焦","巴","弓","牧","隗","山","谷","车","侯","宓","蓬","全","郗","班","仰","秋","仲","伊","宫","宁","仇","栾","暴","甘","钭","厉","戎","祖","武","符","刘","景","詹","束","龙","叶","幸","司","韶","郜","黎","蓟","薄","印","宿","白","怀","蒲","台","从","鄂","索","咸","籍","赖","卓","蔺","屠","蒙","池","乔","阴","郁","胥","能","苍","双","闻","莘","党","翟","谭","贡","劳","逄","姬","申","扶","堵","冉","宰","郦","雍","桑","桂","牛","寿","通","边","燕","冀","郏","浦","尚","农","温","别","庄","晏","柴","翟","阎","充","慕","连","茹","习","宦","艾","鱼","容","向","古","易","慎","戈","廖","庚","终","暨","居","衡","步","都","耿","满","弘","匡","国","文","寇","广","禄","阙","东","殴","殳","沃","利","蔚","越","隆","师","巩","厍","聂","晁","勾","敖","融","冷","訾","辛","阚","那","简","饶","空","曾","毋","沙","乜","养","须","丰","巢","关","蒯","相","查","后","荆","红","游","竺","权","逯","盖","后","桓","公","万","俟","司","马","上","官","欧","阳","夏","侯","诸","葛","闻","人","东","方","赫","连","皇","甫","尉","迟","公","羊","澹","台","公","冶","宗","政","濮","阳","淳","于","单","于","太","叔","申","屠","公","孙","仲","孙","轩","辕","令","狐","钟","离","宇","文","长","孙","慕","容","鲜","于","闾","丘","司","徒","司","空","亓","官","司","寇","仉","督","子","车","孙","端","木","巫","马","公","西","漆","雕","乐","正","公","良","拓","拔","夹","谷","宰","父","谷","粱","晋","楚","闫","法","汝","鄢","涂","钦","段","干","百","里","东","郭","南","门","呼","延","归","海","羊","舌","微","生"};
	String[] second_name={"嘉懿","煜城","懿轩","烨伟","苑博","伟泽","熠彤","鸿煊","博涛","烨霖","烨华","煜祺","智宸","正豪","昊然","明杰","立诚","立轩","立辉","峻熙","弘文","熠彤","鸿煊","烨霖","哲瀚","鑫鹏","致远","俊驰","雨泽","烨磊","晟睿","天佑","文昊","修洁","黎昕","远航","旭尧","鸿涛","伟祺","荣轩","越泽","浩宇","瑾瑜","皓轩","擎苍","擎宇","志泽","睿渊","楷瑞","子轩","弘文","哲瀚","雨泽","鑫磊","修杰","伟诚","建辉","晋鹏","天磊","绍辉","泽洋","明轩","健柏","鹏煊","昊强","伟宸","博超","君浩","子骞","明辉","鹏涛","炎彬","鹤轩","越彬","风华","靖琪","明诚","高格","光华","国源","冠宇","晗昱","涵润","翰飞","翰海","昊乾","浩博","和安","弘博","宏恺","鸿朗","华奥","华灿","嘉慕","坚秉","建明","金鑫","锦程","瑾瑜","晋鹏","经赋","景同","靖琪","君昊","俊明","季同","开济","凯安","康成","乐语","力勤","良哲","理群","茂彦","敏博","明达","朋义","彭泽","鹏举","濮存","溥心","璞瑜","浦泽","奇邃","祺祥","荣轩","锐达","睿慈","绍祺","圣杰","晟睿","思源","斯年","泰宁","天佑","同巍","奕伟","祺温","文虹","向笛","心远","欣德","新翰","兴言","星阑","修为","旭尧","炫明","学真","雪风","雅昶","阳曦","烨熠","英韶","永贞","咏德","宇寰","雨泽","玉韵","越彬","蕴和","哲彦","振海","正志","子晋","自怡","德赫","君平"};
	public String randomName() {
		int total_1 = first_name.length - 1;
		int total_2 = second_name.length - 1;
		
		String new_name = "";
		int count = 10;
		while (count > 0) {
			count = count - 1;
			
			int r1 = RandomUtil.RangeRandom(0, total_1);
			int r2 = RandomUtil.RangeRandom(0, total_2);
			new_name = first_name[r1]+first_name[r2];
			
			int name_count = DbMgr.getInstance().getShareDB().Count(DatabasePvp_match.class, "name=?", new_name);
			if (name_count == 0) {
				break;
			}
			
			new_name = "";
		}
		
		if (!new_name.isEmpty())
			return new_name;
		
		return "玩家"+RandomUtil.RangeRandom(1, 99999999); 
	}
	public void OnRandomName(ChannelHandlerContext ctx, GDL_C2G_RandomName message) {
		GDL_G2C_RandomNameRe msg = new GDL_G2C_RandomNameRe();
		msg.setName(randomName());
		HandlerManager.getInstance().sendMsg(ctx, msg);
	}
	
	public void OnLogin(ChannelHandlerContext ctx, GDL_C2G_Login message) {
		GDL_G2C_LoginRe re = new GDL_G2C_LoginRe();
		if (!can_login) {
			re.setRetCode(2);
			HandlerManager.getInstance().sendMsg(ctx,re);
			return;
		}
		
		try{
			int channel = message.getChannel();
			String where = "";
			String uid = "";
			if (message.getType() == 1) {
				uid = message.getUid();
				where = "uid = ? ";
			}else {
				uid = string.Format("{}_{}", message.getPlatform().toString(),message.getUuid());
				where = "temp_uid = ? "; 
			}
			
			//String nation = Util.ipToCountry(connection.getIP());
			//boolean iswhilelist = DbMgr.getInstance().getShareDB().Count(DatabaseWhite_list.class,"create_device_unique_identifier=?",message.getUuid()) > 0;
			//if (nation.equals("CN") && ServerConfig.cn_black && !iswhilelist) {
			//if (!iswhilelist) {
			//	re.setRetCode(99);
			//	HandlerManager.getInstance().sendMsg(ctx, re);
			//	return;
			//}
			
			//账户查询
			DatabaseAccount account = DbMgr.getInstance().getShareDB().SelectOne(DatabaseAccount.class, where, uid);
			if (account == null){
				re.setRetCode(1);
				re.setUuid(uid);
				re.setName(randomName());
				HandlerManager.getInstance().sendMsg(ctx, re);
				return;
			}else{
				if (account.banned == 1 && TimeUtil.GetDateTime() < account.banned_time) {
					re.setRetCode(3);
					HandlerManager.getInstance().sendMsg(ctx, re);
					
					int time = (int) (account.banned_time - TimeUtil.GetDateTime()/1000);
					HandlerManager.getInstance().showToast(ctx, "还有 " + GetCharCountDown(time) + " 才能解除登录封禁状态");
					return;
				}
				
				long player_id = account.getPlayer_id();
				
				PlayerConnection ooopc = PlayerConManager.getInstance().getCon(player_id);
				if (ooopc != null) {
					PlayerConManager.getInstance().delCon(ooopc);
					ooopc.close();
					re.setRetCode(4);
					HandlerManager.getInstance().sendMsg(ctx, re);
					return ;
				}
				
				PlayerData pd = PlayerConManager.getInstance().getPlayerCache(player_id);
				if (pd == null) {
					DatabasePlayer dp = DbMgr.getInstance().getDb(account.getDb_id())
							.SelectOne(DatabasePlayer.class, "player_id=?", player_id);
					DatabasePlayer_brief_info binfo = DbMgr.getInstance().getShareDB()
							.SelectOne(DatabasePlayer_brief_info.class, "player_id=?", player_id);
					DatabaseMoney_static minfo = DbMgr.getInstance().getShareDB()
							.SelectOne(DatabaseMoney_static.class, "player_id=?", player_id);
					pd = new PlayerData(account, dp, binfo, minfo);
					if (!pd.init()) {
						ctx.close();
						return ;
					}
				}
				PlayerConnection pc = new PlayerConnection(ctx, pd);
				PlayerConManager.getInstance().putCon(player_id, pc);
				pc.setChannel(channel);
				
				re.setRetCode(0);
				re.setPlayerId(account.player_id);
				re.setUuid(account.temp_uid);
				HandlerManager.getInstance().sendMsg(ctx, re);
				return;
			}
		}catch (Exception e){
			logger.error("GDL_C2G_Login is error : ",e);
			re.setRetCode(99);
			HandlerManager.getInstance().sendMsg(ctx, re);
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
	
	HttpClient httpClient = new HttpClient();
	
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

}
