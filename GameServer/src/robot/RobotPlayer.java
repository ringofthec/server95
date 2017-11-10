package robot;

import gameserver.network.IOServer;
import gameserver.network.protos.common.ProLegion.Msg_C2G_Contribution_Item;
import gameserver.network.protos.common.ProLegion.Msg_C2G_Create_Legion;
import gameserver.network.protos.common.ProLegion.Msg_G2C_LegionInfo;
import gameserver.network.protos.game.CommonProto.PLATFORM_TYPE;
import gameserver.network.protos.game.CommonProto.Proto_BuildState;
import gameserver.network.protos.game.ProBuild;
import gameserver.network.protos.game.ProBuild.Msg_C2G_AskBuildingInfo;
import gameserver.network.protos.game.ProBuild.Msg_C2G_BuildMove;
import gameserver.network.protos.game.ProBuild.Msg_C2G_BuildUpgradeOver;
import gameserver.network.protos.game.ProBuild.Msg_C2G_CreateBuild;
import gameserver.network.protos.game.ProBuild.Msg_C2G_ExpandLand;
import gameserver.network.protos.game.ProBuild.Proto_BuildingInfo;
import gameserver.network.protos.game.ProBuild.Proto_UpdateState;
import gameserver.network.protos.game.ProChat.Msg_C2G_ChatMessage;
import gameserver.network.protos.game.ProChat.Proto_Enum_ChatType;
import gameserver.network.protos.game.ProLogin;
import gameserver.network.protos.game.ProLogin.LOGIN_TYPE;
import gameserver.network.protos.game.ProLogin.Msg_C2G_AskEnter;
import gameserver.network.protos.game.ProLogin.Msg_C2G_AskLogin;
import gameserver.network.protos.game.ProPvp.Msg_C2G_PvpMatching;
import gameserver.network.server.connection.RobotConnection;
import gameserver.utils.HttpUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_Building;
import table.base.TableManager;

import com.commons.util.RandomUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import commonality.ITEM_ID;

public class RobotPlayer {
	
	private static final Logger logger = LoggerFactory.getLogger(RobotPlayer.class);
	private RobotConnection m_con;
	private STATE m_status = STATE.NON;
	private boolean isCreate = false;
	private FastMap<Integer, Proto_BuildingInfo> m_builds = new FastMap<Integer, Proto_BuildingInfo>();
	private FastMap<Integer, MT_Data_Building> m_buildsconfig = new FastMap<Integer, MT_Data_Building>();
	private int id;
	private String chats[] = {"Hi man, What's up?", 
			"aaaaaaaaaaaa", 
			"bbbbbbbbbbbb", 
			"cccccccccccc",
			"dddddddddddd",
			"eeeeeeeeeeee",
			"ffffffffffff",
			"gggggggggggg"};
	
	private String gm_chats[] = {
			"/gm additem 10000 99", 
			"/gm additem 10001 99", 
			"/gm additem 10002 99",
			"/gm additem 10003 99",
			"/gm additem 10004 99",
			"/gm additem 2 1",
			"/gm additem 10006 99"};
	
	private int build_ids[] = {2,3,4,100,500,200,201,202,300,301,302,303,400,401,402,403};
	public RobotPlayer(int id) {
		this.id = id;
	}
	
	public enum STATE {
		WAIT,
		NON,
		CREAT_CONNECTION,
		CREATE_PLAYER,
		ASK_LOGIN,
		ASK_ENTER,
		ASK_BUILD_INFO,
		LOGIC,
	}
	
	public void run() throws IOException {
		switch (m_status)
		{
		case NON:
			createCon();
		break;
		
		case CREAT_CONNECTION:
			login();
		break;
		
		case CREATE_PLAYER:
			createPlayer();
		break;
		
		case ASK_LOGIN:
			askLogin();
		break;
		
		case ASK_ENTER:
			askEnter();
		break;
		
		case ASK_BUILD_INFO:
			askBuildInfo();
			break;
		
		case LOGIC:
			logic();
		break;
		}
		
	}
	
	public void logic() {
		
		checkAllBuild();
		
		int rad = RandomUtil.RangeRandom(0, 5);
		
		switch (rad) {
		case 0:
			logger.error("说话了");
			speakSomeThing(chatPool());
			break;
			
		case 1:
			logger.error("移动建筑了");
			moveBuild();
			break;
			
		case 2:
			pvpMatch();
			break;

		case 3:
			moveBuild();
			break;

		case 4:
			//m_con.close(true);
			logger.error("下线啦，不玩啦！！！！！！！！！！！！！！！");
			m_status = STATE.NON;
			break;
			
		case 5:
			logger.error("打gm指令了");
			speakSomeThing(gmchatPool());
			break;
			
		case 6:
			logger.error("扩地了");
			Msg_C2G_ExpandLand extend = Msg_C2G_ExpandLand.newBuilder().build();
			m_con.sendReceiptMessage(extend);;
			break;
		case 7:
			logger.error("捐物资了");
			contrbuteItem();
			break;
		case 8:
			pvpMatch();
			break;
		}
	}
	
	static int[] corpid = {};
//	private void makeCorp() {
//		for (Entry<Integer, MT_Data_Building> config : m_buildsconfig.entrySet()) {
//			if ()
//		}
//	}
	
	private void pvpMatch() {
		Msg_C2G_PvpMatching pvpmsg = Msg_C2G_PvpMatching.newBuilder().setMatchCount(1).setReportId(0).build();
		m_con.sendReceiptMessage(pvpmsg);
	}
	
	private void contrbuteItem() {
		Msg_C2G_Contribution_Item msg = Msg_C2G_Contribution_Item.newBuilder().setType(1).build();
		m_con.sendReceiptMessage(msg);
	}
	private void moveBuild() {
		try {
			Proto_BuildingInfo buildInfo = null;
			for (Proto_BuildingInfo bi : m_builds.values()) {
				if (RandomUtil.RangeRandom(0, 1) == 1) {
					buildInfo = bi;
				}
			}
			
			if (buildInfo == null)
				return ;
			
			Msg_C2G_BuildMove.Builder bi = Msg_C2G_BuildMove.newBuilder();
			bi.setBuildId(buildInfo.getBuildid());
			bi.setPosX(RandomUtil.RangeRandom(0, 18));
			bi.setPosY(RandomUtil.RangeRandom(0, 18));
			m_con.sendReceiptMessage(bi.build());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private String gmchatPool() {
		return gm_chats[RandomUtil.RangeRandom(0, gm_chats.length - 1)];
	}
	
	private void speakSomeThing(String text) {
		Msg_C2G_ChatMessage.Builder money = Msg_C2G_ChatMessage.newBuilder();
		money.setType(Proto_Enum_ChatType.WORLD);
		money.setText(text);
		money.setVideoId(0);
		money.setContextType(0);
		money.setTargetPlayerName("");
		m_con.sendReceiptMessage(money.build());
	}
	
	private String chatPool() {
		return chats[RandomUtil.RangeRandom(0, chats.length - 1)];
	}
	
	private boolean createCon() throws IOException {
		String url = "http://192.168.1.199:10000/Management/src/process/client/request.php";
		String res = HttpUtil.httpSynSend(url, HttpUtil.POST_METHOD, "{\"ip\":\"\",\"needNetIp\":0}");
		
		JsonParser parse = new JsonParser();
		JsonObject netInfo = (JsonObject) parse.parse(res);
		int isOk = netInfo.get("code").getAsInt();
		if (isOk != 0) {
			logger.error("服务器 请求http请求出错 ");
			return false;
		}
		
		String ip = netInfo.get("gameIP").getAsString();
		int port = netInfo.get("gamePort").getAsInt();
		System.err.println("ip="+ip+",port="+port);
		SocketChannel sc = SocketChannel.open(new InetSocketAddress(ip, port));
		sc.configureBlocking(false);
		m_con = null;//new RobotConnection(sc, IOServer.getInstance().getDis());
		m_con.player = this;
		
		logger.error("我来啦，上线走起~~~~~~~~~~~~~~！");
		m_status = STATE.CREAT_CONNECTION;
		return true;
	}
	
	public void login() throws IOException {
		Msg_C2G_AskLogin.Builder askLogin = Msg_C2G_AskLogin.newBuilder();
		askLogin.setChannel(0);
		askLogin.setDeviceUniqueIdentifier("1dfv34" + id);
		askLogin.setPlatform(PLATFORM_TYPE.WINDOWS);
		askLogin.setRegistrationID("");
		askLogin.setSession("dfsgdfgsdfgsdfgdfg");
		askLogin.setType(LOGIN_TYPE.PASSER);
		askLogin.setUid("");
		m_con.sendReceiptMessage(askLogin.build());
		
		m_status = STATE.WAIT;
	}
	
	public void createPlayer() {
		ProLogin.Msg_C2G_CreatePlayer.Builder msg = ProLogin.Msg_C2G_CreatePlayer.newBuilder();
		msg.setType(LOGIN_TYPE.PASSER);
		msg.setUid("");
		msg.setSession(m_con.session);
		msg.setPlatform(PLATFORM_TYPE.WINDOWS);
		msg.setDeviceUniqueIdentifier("1dfv34" + id);
		msg.setName("te_1robot_" + id);
		msg.setHead(RandomUtil.RangeRandom(0, 4));
		msg.setChannel(0);
		m_con.sendReceiptMessage(msg.build());
		
		m_status = STATE.WAIT;
	}
	
	public void askLogin() {
		ProLogin.Msg_C2G_AskEnter.Builder msg = ProLogin.Msg_C2G_AskEnter.newBuilder();
		m_con.sendReceiptMessage(msg.build());
		m_status = STATE.WAIT;
	}
	
	public void askEnter() {
		Msg_C2G_AskEnter msg = Msg_C2G_AskEnter.newBuilder().build();
		m_con.sendReceiptMessage(msg);
		m_status = STATE.WAIT;
	}
	
	public void askBuildInfo() {
		Msg_C2G_AskBuildingInfo.Builder msg = Msg_C2G_AskBuildingInfo.newBuilder();
		msg.setLanguage("Chinese_Simplified");
		msg.setVersion("1.0");
		m_con.sendReceiptMessage(msg.build());
		m_status = STATE.WAIT;
	}
	
	private void checkAllBuild() {
		for (Proto_BuildingInfo bu : m_builds.values()) {
			if (bu.getState() == Proto_BuildState.NONE_BUILD)
				continue;
			else if (bu.getState() == Proto_BuildState.UPGRADE) {
				long lastOpTime = bu.getOperateTime();
				long passTime = bu.getPassTime() * 1000;
				
				if (System.currentTimeMillis() - lastOpTime > passTime) {
					buildOver(bu);
				}
			}
		}
	}
	
	public void buildOver(Proto_BuildingInfo bu) {
		Msg_C2G_BuildUpgradeOver.Builder msg = Msg_C2G_BuildUpgradeOver.newBuilder();
		msg.setBuildId(bu.getBuildid());
		m_con.sendReceiptMessage(msg.build());
	}
	
	public void OnBuildInfo(ProBuild.Msg_G2C_AskBuildingInfo message) {
		if (message.getType() == Proto_UpdateState.LIST)
			m_builds.clear();
		
		for (Proto_BuildingInfo bu : message.getInfoList()) {
			m_builds.put(bu.getBuildid(), bu);
			MT_Data_Building bui = TableManager.GetInstance().TableBuilding().GetElement(bu.getTableid());
			if (bui != null)
				m_buildsconfig.put(bu.getBuildid(), bui);
		}
	}
	
	public void OnAskPlayerInfo(Msg_G2C_LegionInfo message) {
		if (message.getId() == 0) {
			Msg_C2G_Create_Legion.Builder legion = Msg_C2G_Create_Legion.newBuilder()
					.setName("legion_test_" + id)
					.setIsValidate(0).setImageIndex(0);
			m_con.sendReceiptMessage(legion.build());
		}
	}
	
	public void OnInfoFinish() {
		m_status = STATE.LOGIC;
		
		if (isCreate)
			superMan();
	}
	
	public void OnAskEnter(ProLogin.Msg_G2C_AskEnter message) {
		m_status = STATE.ASK_BUILD_INFO;
	}
	
	void build(int buildTableId, int x, int y) {
		Msg_C2G_CreateBuild.Builder bui = Msg_C2G_CreateBuild.newBuilder();
		bui.setTableId(buildTableId);
		bui.setPointX(x);
		bui.setPointY(y);
		m_con.sendReceiptMessage(bui.build());
	}
	
	void superMan() {
		// 给自己加钱加经验, 砖石
		Msg_C2G_ChatMessage.Builder money = Msg_C2G_ChatMessage.newBuilder();
		money.setType(Proto_Enum_ChatType.WORLD);
		money.setText("/gm additem " + ITEM_ID.MONEY + " 999999");
		m_con.sendPushMessage(money.build());

		Msg_C2G_ChatMessage.Builder money2 = Msg_C2G_ChatMessage.newBuilder();
		money2.setType(Proto_Enum_ChatType.WORLD);
		money2.setText("/gm additem " + ITEM_ID.RARE + " 999999");
		m_con.sendPushMessage(money2.build());

		Msg_C2G_ChatMessage.Builder money3 = Msg_C2G_ChatMessage.newBuilder();
		money3.setType(Proto_Enum_ChatType.WORLD);
		money3.setText("/gm additem " + ITEM_ID.GOLD + " 999999");
		m_con.sendPushMessage(money3.build());

		Msg_C2G_ChatMessage.Builder money4 = Msg_C2G_ChatMessage.newBuilder();
		money4.setType(Proto_Enum_ChatType.WORLD);
		money4.setText("/gm additem " + ITEM_ID.EXP + " 999999");
		m_con.sendPushMessage(money4.build());
		
		Msg_C2G_ChatMessage.Builder money5 = Msg_C2G_ChatMessage.newBuilder();
		money5.setType(Proto_Enum_ChatType.WORLD);
		money5.setText("/gm additem " + ITEM_ID.LEGION_GOODS + " 999999");
		m_con.sendPushMessage(money5.build());
		
		Msg_C2G_Create_Legion.Builder legion = Msg_C2G_Create_Legion.newBuilder()
				.setName("legion_test_" + id)
				.setIsValidate(0).setImageIndex(0);
		m_con.sendReceiptMessage(legion.build());
	}

	public void OnCreatePlayer(ProLogin.Msg_G2C_CreatePlayer message) {
		if (message.getSucceed() == 0) {
			m_status = STATE.ASK_ENTER;
			m_con.session = message.getSession();
			isCreate = true;
		} else {
			// create role error
		}
	}
	
	public void OnAskLogin(ProLogin.Msg_G2C_AskLogin message) {
		int res = message.getHaveAccount();
		if (res == 3) {
			m_con.session = message.getSession();
			m_status = STATE.ASK_ENTER;
		}
		else if (res == 2) {
			m_con.session = message.getSession();
			m_status = STATE.CREATE_PLAYER;
		}
		else{
			
		}
	}
}
