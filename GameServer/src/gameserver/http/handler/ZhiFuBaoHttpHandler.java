package gameserver.http.handler;

import gameserver.jsonprotocol.GDL_G2C_Fallback;
import gameserver.utils.DbMgr;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Map;

import org.apache.commons.io.IOUtils;
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
import com.sun.net.httpserver.HttpExchange;

import databaseshare.DatabaseAccount;
import databaseshare.DatabaseRecharge_cache2;

public class ZhiFuBaoHttpHandler extends MyHttpHandler {
	private final static Logger logger = LoggerFactory.getLogger(ZhiFuBaoHttpHandler.class);
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
				requestData = HttpUtil.urldecode(requestData);
				logger.error("recharge_back_data1:{}", requestData);
				Map<String, String> keyvalmap = parseFormatString2(requestData, 3, false);
				if (keyvalmap == null) {
					logger.error("parse pay map error");
					break;
				}
				
				if (!keyvalmap.get("trade_status").equals("TRADE_SUCCESS")) {
					logger.error("trade_status is not TRADE_SUCCESS");
					return ;
				}
				
				if (!AlipayNotify.verify(keyvalmap, "2088911437022414")) {
					logger.error("verify pay sign error");
					break;
				}
				
				String out_order = keyvalmap.get("out_trade_no");
				int count = DbMgr.getInstance().getShareDB().Count(DatabaseRecharge_cache2.class, "pay_order=?", out_order);
				if (count >= 1) {
					logger.error("pay already process");
					HttpUtil.sendResponse(httpExchange, "success");
					return ;
				}
				
				String body_str = keyvalmap.get("body");
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
				
				DatabaseRecharge_cache2 cache = new DatabaseRecharge_cache2();
				cache.amount = Float.parseFloat( keyvalmap.get("total_fee") ) * 100;
				cache.player_id = pid;
				cache.recharge_str = requestData;
				cache.mask = 0;
				cache.shop_type = st;
				cache.tid = ti;
				cache.pay_order = out_order;
				cache.create_time = TimeUtil.GetDateTime();
				cache.get_time = TimeUtil.getTime("2012-12-12 00:00:00");
				cache.recharge_str = requestData;
				cache.paytype = 1;
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
				HttpUtil.sendResponse(httpExchange, "success");
				return ;
			}
		}
		catch (Exception e)
		{
			logger.error("ZhiFuBaoHttpHandler handle is error : ",e);
		}
		
		HttpUtil.sendResponse(httpExchange, "fail");
		return ;
	}
	@Override
	public String getPattern() {
		return "/alipayret";
	}
	
	public static void main(String[] args) {
		String requestData = "discount=0.00&payment_type=1&subject=价值6万金币&trade_no=2016060121001004090295219380&buyer_email=13891534209&gmt_create=2016-06-01 20:16:49&notify_type=trade_status_sync&quantity=1&out_trade_no=175dfa3294bb4de0baece69a36c92152&seller_id=2088911437022414&notify_time=2016-06-01 20:16:49&body=1226:1:9&trade_status=WAIT_BUYER_PAY&is_total_fee_adjust=Y&total_fee=6.00&seller_email=3168624126@qq.com&price=6.00&buyer_id=2088902273520091&notify_id=80235b6a511918ffd5f3a500b2c3d07gp2&use_coupon=N&sign_type=RSA&sign=AdQuV/Kif9gQljb373cMChdEAELA30cZuP92HhPVAHLa/0fyYVKwOhqTvWymnN2u3zpixjkJXYy0KX1BTQg7kNZ1rd5+86BapbVixlq0hwvViOjd7rox0JIlCfTXa5cBQn9DApDLXuCoJxOKS6loxPKLQXeR/wMHtYCWx2UEVSI=";
		String req2 = "discount=0.00&payment_type=1&subject=1元宝箱&trade_no=2016062421001004730292457086&buyer_email=13569396726&gmt_create=2016-06-24 17:53:34&notify_type=trade_status_sync&quantity=1&out_trade_no=4fe6777e0ae04c7bad3f7b458b33c9bf&seller_id=2088911437022414&notify_time=2016-06-24 17:57:42&body=3471:2:2&trade_status=WAIT_BUYER_PAY&is_total_fee_adjust=Y&total_fee=1.00&seller_email=3168624126@qq.com&price=1.00&buyer_id=2088122589751730&notify_id=29edb67a99040cb40f269d586856a78lmu&use_coupon=N&sign_type=RSA&sign=UfNDsAyeuiTJhpbTsJPIOJUMzkAyzsW4JYgmhd3Ep5OeZsZAQpZAUqXK0QMc+HQv7Ozcpu2vJgSp7wO33nj74wPqUqifEz7/JPgPIKmKBh8BHude2wVTrK4Fyc4qBEoLM27kMnVK+b/fibKm1FvptSOfheFGDLsQ02R3COlr3Ao=";
		String poce = req2;
		logger.error("recharge_back_data1:{}", poce);
		Map<String, String> keyvalmap = parseFormatString2(poce, 3, false);
		if (keyvalmap == null)
			return;
		
		if (!AlipayNotify.verify(keyvalmap, "2088911437022414"))
			return;
		
		System.err.println("dffgdfgdfg");
	}
}
