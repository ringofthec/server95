package gameserver.network.message.game;

import gameserver.event.GameEvent;
import gameserver.http.HttpProcessManager;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.IOServer;
import gameserver.network.protos.game.ProGameInfo;
import gameserver.network.protos.game.ProPvp.Proto_ActionType;
import gameserver.network.protos.share.ProRecharge.Msg_S2G_RechargeCallBack;
import gameserver.network.server.connection.Connection;
import gameserver.network.server.connection.ConnectionManager;
import gameserver.stat.StatManger;
import gameserver.utils.DbMgr;
import gameserver.utils.GameException;
import gameserver.utils.HttpUtil;
import gameserver.utils.Item_Channel;
import gameserver.utils.Security;
import gameserver.utils.UuidGenerator;

import java.util.Map;

import javolution.util.FastMap;
import net.schst.EventDispatcher.EventDispatcher;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.MT_DataBase;
import table.MT_Data_Recharge1;
import table.MT_TableManager;
import table.base.TableManager;

import com.commons.util.TimeUtil;
import com.commons.util.string;
import commonality.CHANNEL_TYPE;
import commonality.Common;
import commonality.Common.SETNUMBER_TYPE;
import commonality.ProductChannel;
import commonality.VmChannel;

import databaseshare.DatabaseRecharge_log;

public class ClientMessageRecharge {
	private final static ClientMessageRecharge instance = new ClientMessageRecharge();
	public static ClientMessageRecharge getInstance() { return instance; }
	private final Logger logger = LoggerFactory.getLogger(ClientMessageRecharge.class);
	
	public void initialize()
    {
    	IOServer.getInstance().regMsgProcess(ProGameInfo.Msg_C2G_Recharge.class, this, "OnRecharge");
    	IOServer.getInstance().regMsgProcess(ProGameInfo.Msg_C2G_ReqRechargeOrder.class, this, "OnReqOrder");
    	
    	HttpProcessManager.getInstance().regMsgProcess(Msg_S2G_RechargeCallBack.class,this, "OnPayOver");
    }
	
	public void OnPayOver(Msg_S2G_RechargeCallBack msg) {
		long player_id = msg.getPlayerId();
		Connection con = ConnectionManager.GetInstance().GetConnection(player_id);
		if (con != null && con.isInit()) {
			con.getPlayer().getRechargeCache();
		}
	}
	
	public void OnReqOrder(Connection connection,ProGameInfo.Msg_C2G_ReqRechargeOrder message) {
		String order = UuidGenerator.newUuid();
		ProGameInfo.Msg_G2C_RechargeOrder.Builder builder = ProGameInfo.Msg_G2C_RechargeOrder.newBuilder();
		builder.setOrders(order);
		builder.setItemid(message.getItemid());
		builder.setUrl("http://120.24.70.68:20000/360_pay_frgh23iuiuGYTG3g2r");
		connection.sendReceiptMessage(builder.build());
		
		logger.error("req pay order, player={}-{},PayOrder={},itemId={}", 
				connection.getPlayerId(), connection.getPlayerName(), order, message.getItemid());
	}
	
	private static String[] base64key = 
		{"",
		"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhNXiCoUkCo5q8nL7j92wBaxHeu48r4X8yE//2XKNk1TZviSbz39ib+whw7YnHSWcsqWjXCq4bF+XA82iMUrMXa7dUfs2Ro8WK8AbdvF/tydwTBkb68D4Dx/ZUaL5jfXmOPKEfa24aRwQpcEqkpfCX2cnAfM40ECFGJ0KE4cKUEeg4vlx8ehUumbnxCU0rIAnI+LQIM9N62tsYr8TBzOFLLevuBj3A8UQc0lG73kTagxHtK1eUeYf7b7aepVSyuJc4Ds3DZxRCkQErIQ2GQySyEaMwMnRS8GDnlEyoO3m7s35ldC1iDrzgxvxtFWmk0EiE6TGLkXtHofjdyLkpMwzBwIDAQAB",
		"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgoHxa8JgW6TrPWJzK+jfnVgf4NZG0sPAUwzgJd7rZezqEV27xqT1eYiJf7vuVT+AyIBsfIGBTaaLnE7Yytmc7DFOPWG6+oH2a1ikJt5aE/0xmYaiazU/GHGvIU4hzfM4Dha54onhg408fmWnyh6dJ+Atd0+WFwgS83uhqG9XfyAHIw/REh1IJx0BAUrqa3RyZF0aBidXC/+vOQ8vz/EVCmE8XpASJf5m0RpVIcLSEQm/QemHv/MWehSkL32KVSFR2I53yyvdPMKvlfgc0bn/WeII7elVu6mPAVXtiBZg2a2PpIpofe0XVhoAqHyw7ieSveqgGKjJZpmkvYljPbZx+QIDAQAB",
		};
	
	private boolean isValidPay(Connection con,ProGameInfo.Msg_C2G_Recharge message) {
		
		if (message.getChannel() == CHANNEL_TYPE.GooglePlayBedroom.Number() || 
				message.getChannel() == CHANNEL_TYPE.GooglePlayWar.Number()) {
			String json_str = message.getOriginalJson();
			String sign = message.getSignature();
			int channel = message.getChannel();

			LogService.logRecharge(con.getPlayerId(), channel, message.getOrders(), 
					json_str, sign, con.getPlayer().getNation(), con.getAccount().getClientVersion(),con.getPlayer().getLevel());

			if (!string.IsNullOrEmpty(json_str) && !string.IsNullOrEmpty(sign)) {
				if (!Security.verifyPurchase(base64key[channel], json_str, sign)) {
					logger.error("invalid pay, playerid={},channel={},json_str={},sign={}",con.getPlayerId(),channel,json_str,sign);
					return false;
				}
			}

			int pay_count = DbMgr.getInstance().getShareDB().Count(DatabaseRecharge_log.class, "pay_order=?", message.getOrders());
			if (pay_count > 0) {
				logger.error("invalid pay, playerid={},payorder={}",con.getPlayerId(),message.getOrders());
				ProGameInfo.Msg_G2C_RechargeFinish.Builder bui = ProGameInfo.Msg_G2C_RechargeFinish.newBuilder();
				bui.setClientinfo(message.getClientinfo());
				con.sendReceiptMessage(bui.build());
				return false;
			}
		} else if (message.getChannel() == CHANNEL_TYPE.AppStoreTW.Number() ||
				message.getChannel() == CHANNEL_TYPE.AppStoreWWT.Number()) {
			if (!verifyingInAppPurchase(message.getSignature()))
				return false;
		}
		
		return true;
	}
	
	public static boolean verifyingInAppPurchase(String transactionReceipt) {
		JSONObject postFeild = new JSONObject();
		postFeild.put("receipt-data", transactionReceipt);
		String oop = null;
		
		if (oop == null) {
			try
			{
				oop = HttpUtil.httpSynSend("https://buy.itunes.apple.com/verifyReceipt", "POST", postFeild.toString());
			}
			catch (Exception e) {
				
			}
		}
		
		JSONParser retParse = new JSONParser();
		boolean isValid = false;
		try{
			JSONObject tempJosnObj = (JSONObject)retParse.parse(oop);
			long retcode = (Long)tempJosnObj.get("status");
			if (retcode == 0)
				isValid = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isValid;
	}
	
	public void buyFund(Connection con,String order, String item_id, int client_info, MT_DataBase data, boolean need_notify_client) {
		realPay(con, order, item_id, client_info, data, false, need_notify_client);
	}
	
	public void buyGold(Connection con,String order, String item_id, int client_info, MT_DataBase data, boolean need_notify_client) {
		realPay(con, order, item_id, client_info, data, true, need_notify_client);
	}
	
	public void realPay(Connection con, String order, String item_id, int client_info, MT_DataBase data, boolean giveGold, boolean need_notify_client) {
		Int2 item_config = null;
		float price = 0;
		
		try {
			item_config = (Int2)data.GetDataByString("buyTableId");
			price = (Float)data.GetDataByString("Price");
		} catch (Exception e) {
			logger.error("Payment Config Error! player_id={},order={},item_id={}", con.getPlayerId(), order, item_id);
			return ;
		}
		
		long s_sum = IPOManagerDb.getInstance().getNextId();
		if (giveGold) {
			int value = item_config.field2();
			boolean bFirstPay = con.getPlayer().isFirstPay();
			con.getPlayer().addPayCount();
			if (bFirstPay) {
				value *= Common.FIRST_PAY_ADDITION;
				ClientMessageCommon.getInstance().UpdateCountInfo(con,
						Proto_ActionType.FIRST_PAY,
						con.getPlayer().isFirstPay()?0:1,1);
			}
			con.getItem().setItemNumber(item_config.field1(), value, SETNUMBER_TYPE.ADDITION,
					VmChannel.GooglePlay, ProductChannel.PurchaseGoods,s_sum,"", Item_Channel.RECHARGE);
		} else {
			con.getPlayer().setFundEndDay();
			con.getPlayer().syncFund();
		}
		
		int vip_exp = item_config.field2() / 10;
		con.getPlayer().AddPlayerVipExp(vip_exp, s_sum, Item_Channel.RECHARGE);
		
		StatManger.getInstance().onRecharge(item_config.field2());
		LogService.logEvent(con.getPlayerId(), s_sum, 12, item_config.field1(), item_config.field2());
		
		DatabaseRecharge_log rec = new DatabaseRecharge_log();
		rec.player_id = con.getPlayerId();
		rec.player_name = con.getPlayerName();
		rec.pay_order = order;
		rec.item_id = item_id;
		rec.goldnum = item_config.field2();
		rec.recharge_time = TimeUtil.GetTimestamp();
		rec.nation = con.getPlayer().getNation();
		rec.cost_num = price;
		rec.create_time = con.getPlayer().getCreateTimeTimeStamp();
		DbMgr.getInstance().getShareDB().Insert(rec);
		
		try {
			Map<String, Object> params = new FastMap<String, Object>();
			params.put("count", item_config.field2());
			params.put("player", con.getPlayer());
			EventDispatcher.getInstance().triggerEvent(GameEvent.rechargeEvent, false, params);}
		catch (Exception e) {}

		if (need_notify_client) {
			ProGameInfo.Msg_G2C_RechargeFinish.Builder bui = ProGameInfo.Msg_G2C_RechargeFinish.newBuilder();
			bui.setClientinfo(client_info);
			con.sendReceiptMessage(bui.build());
		}
	}
	
	public void OnRecharge(Connection con,ProGameInfo.Msg_C2G_Recharge message) throws GameException
	{
		MT_Data_Recharge1 data = null;
		if (message.getChannel() == CHANNEL_TYPE.GooglePlayBedroom.Number() ||
				message.getChannel() == CHANNEL_TYPE.GooglePlayWar.Number())
			data = TableManager.GetInstance().getSpawns(MT_TableManager.Recharge1.Recharge1_GooglePlay).GetElement(message.getItemid());
		else if (message.getChannel() == CHANNEL_TYPE.AppStoreWWT.Number())
			data = TableManager.GetInstance().getSpawns(MT_TableManager.Recharge1.Recharge1_appstore).GetElement(message.getItemid());
		else if (message.getChannel() == CHANNEL_TYPE.AppStoreTW.Number())
			data = TableManager.GetInstance().getSpawns(MT_TableManager.Recharge1.Recharge1_appstoretw).GetElement(message.getItemid());
		if (data == null) {
			throw new GameException("充值失败，玩家[{}]企图充值一个不存在的recharge配置  {}", con.getPlayerId(), message.getItemid());
		}
		
		if (!isValidPay(con, message))
			return ;
		
		logger.error("finish pay order, player={}-{},PayOrder={},itemId={},OrginJson={},Signature={}", 
				con.getPlayerId(), con.getPlayerName(), message.getOrders(),message.getItemid(),message.getOriginalJson(), message.getSignature());
		
		pay(con, message.getOrders(), message.getItemid(), message.getClientinfo(), data, true);
	}

	public void pay(Connection con, String order, String item_id, int client_info,
			MT_DataBase data, boolean need_notify_client) {
		if (item_id.contains("fund"))
			buyFund(con, order, item_id, client_info, data, need_notify_client);
		else
			buyGold(con, order, item_id, client_info, data, need_notify_client);
	}
}
