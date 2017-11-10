package gameserver.http.handler;

import gameserver.jsonprotocol.GDL_G2C_Fallback;
import gameserver.utils.DbMgr;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.util.AlipayNotify;
import com.commons.http.MyHttpHandler;
import com.commons.network.websock.PlayerConManager;
import com.commons.network.websock.PlayerConnection;
import com.commons.util.CharsetUtil;
import com.commons.util.HttpUtil;
import com.commons.util.HttpUtil.HTTP_METHOD;
import com.commons.util.TimeUtil;
import com.iapppay.demo.IAppPaySDKConfig;
import com.iapppay.sign.SignHelper;
import com.sun.net.httpserver.HttpExchange;

import databaseshare.DatabaseAccount;
import databaseshare.DatabaseRecharge_cache2;

public class AibeiweiHttpHandler extends MyHttpHandler {
	private final static Logger logger = LoggerFactory.getLogger(AibeiweiHttpHandler.class);
	@Override
	public void handle(HttpExchange httpExchange) {
		try
		{
			while (true) {
				String method = httpExchange.getRequestMethod();
				String uri = httpExchange.getRequestURI().toString();
				InetSocketAddress address = httpExchange.getRemoteAddress();
				logger.debug("接收到http请求[{}]  uri : '{}'  method : '{}'",address,uri,method);
				if (!method.toUpperCase().contains(HTTP_METHOD.POST.getName()))
					break;
				
				InputStream stream = httpExchange.getRequestBody();
				String requestData = IOUtils.toString(stream,CharsetUtil.defaultCharset);
				
				logger.error("before=" + requestData);
				
				requestData = HttpUtil.urldecode(requestData);
				
				logger.error("after=" + requestData);
				/*
				transdata={"transtype":0,"cporderid":"1","transid":"2","appuserid":"test","appid":"3","waresid":31,"feetype":
					4,"money":5.00,"currency":"RMB","result":0,"transtime":"2012-12-12 12:11:10","cpprivate":"test","paytype":1}
					&sign=xxxxxx&signtype=RSA
					*/
				
				Map<String, String> keyvalmap = parseFormatString2(requestData, 3, false);
				if (keyvalmap == null) {
					logger.error("pay sign error");
					HttpUtil.sendResponse(httpExchange, "fail");
					return ;
				}
				
				String transdata = keyvalmap.get("transdata"); 
				String sign = keyvalmap.get("sign"); 
			
				if (!SignHelper.verify(transdata, sign, IAppPaySDKConfig.PLATP_KEY)) {
					logger.error("pay sign error");
					HttpUtil.sendResponse(httpExchange, "fail");
					return ;
				}
				
				JSONParser parse = new JSONParser();
				JSONObject target_data = (JSONObject)parse.parse(transdata);
				
				String out_order = (String)target_data.get("cporderid");
				int count = DbMgr.getInstance().getShareDB().Count(DatabaseRecharge_cache2.class, "pay_order=?", out_order);
				if (count >= 1) {
					logger.error("pay already process");
					HttpUtil.sendResponse(httpExchange, "success");
					return ;
				}
				
				String body_str = (String)target_data.get("cpprivate");
				String[] info = body_str.split(":");
				long pid = Long.parseLong(info[0]);
				int st = Integer.parseInt(info[1]);
				int ti = Integer.parseInt(info[2]);
				// 处理逻辑重复订单，和支付逻辑
				
				int channel_id = 0;
				DatabaseAccount ac = DbMgr.getInstance().getShareDB().SelectOne(DatabaseAccount.class, "player_id=?", pid);
				if (ac != null) {
					channel_id = ac.getChannel();
				}
				
				Double amount = (Double)target_data.get("money");
				DatabaseRecharge_cache2 cache = new DatabaseRecharge_cache2();
				cache.amount = amount.floatValue() * 100;
				cache.player_id = pid;
				cache.recharge_str = requestData;
				cache.mask = 0;
				cache.shop_type = st;
				cache.tid = ti;
				cache.pay_order = out_order;
				cache.create_time = TimeUtil.GetDateTime();
				cache.get_time = TimeUtil.getTime("2012-12-12 00:00:00");
				cache.recharge_str = requestData;
				cache.paytype = 4;
				cache.channel = channel_id;
				DbMgr.getInstance().getShareDB().Insert(cache);
				
				PlayerConnection p = PlayerConManager.getInstance().getCon(pid);
				if (p != null) {
					GDL_G2C_Fallback re = new GDL_G2C_Fallback();
					re.setFuncId(1);
					re.setData(" ");
					p.directSendPack(re);
				}
				
				logger.error("pay process");
				HttpUtil.sendResponse(httpExchange, "SUCCESS");
				return ;
			}
		}
		catch (Exception e)
		{
			logger.error("AibeiweiHttpHandler handle is error : ",e);
		}
		
		HttpUtil.sendResponse(httpExchange, "fail");
		return ;
	}
	@Override
	public String getPattern() {
		return "/aibeiwei_pay_ret";
	}
	
	public static void main(String[] args) throws ParseException {
		String requestData = "transdata={\"appid\":\"3006139651\",\"appuserid\":\"2057\",\"cporderid\":\"3c8cb2e6d0524062948d412e43b982fe\",\"cpprivate\":\"2057:2:2\",\"currency\":\"RMB\",\"feetype\":0,\"money\":1.00,\"paytype\":403,\"result\":0,\"transid\":\"32241607112127190889\",\"transtime\":\"2016-07-11 21:28:00\",\"transtype\":0,\"waresid\":1}&sign=fjfWRjukcum9dgaVBXiP+Eue7sYWrv+QQIv/jfkiubWtREHGJTNL2SLxw6URmNSXuMKrI2mLD3N94XEGlTGWWrWtbXoktCcqEXNXjDQ1szYMdJPsTbOLJPzcFJdHSKzu/5kBAaFQWl4qdw8lh933FlNHquZzE+JF3LXguBR5+c4=&signtype=RSA";
		
		logger.error("after=" + requestData);
		/*
		transdata={"transtype":0,"cporderid":"1","transid":"2","appuserid":"test","appid":"3","waresid":31,"feetype":
			4,"money":5.00,"currency":"RMB","result":0,"transtime":"2012-12-12 12:11:10","cpprivate":"test","paytype":1}
			&sign=xxxxxx&signtype=RSA
			*/
		
		Map<String, String> keyvalmap = parseFormatString2(requestData, 3, false);
		if (keyvalmap == null) {
			logger.error("pay sign error");
			return ;
		}
		
		String transdata = keyvalmap.get("transdata"); 
		String sign = keyvalmap.get("sign"); 
	
		if (!SignHelper.verify(transdata, sign, IAppPaySDKConfig.PLATP_KEY)) {
			logger.error("pay sign error");
			return ;
		}
		
		JSONParser parse = new JSONParser();
		JSONObject target_data = (JSONObject)parse.parse(transdata);
		
		String out_order = (String)target_data.get("cporderid");
		
		String body_str = (String)target_data.get("cpprivate");
		String[] info = body_str.split(":");
		long pid = Long.parseLong(info[0]);
		int st = Integer.parseInt(info[1]);
		int ti = Integer.parseInt(info[2]);
		// 处理逻辑重复订单，和支付逻辑
		
		Double amount = (Double)target_data.get("money");
		System.err.println(amount);
	}
}
