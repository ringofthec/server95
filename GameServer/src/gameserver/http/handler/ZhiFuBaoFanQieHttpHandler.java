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

public class ZhiFuBaoFanQieHttpHandler extends MyHttpHandler {
	private final static Logger logger = LoggerFactory.getLogger(ZhiFuBaoFanQieHttpHandler.class);
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
				
				if (!AlipayNotify.verify(keyvalmap, "2088221283349073")) {
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
		return "/alipayret_fanqie";
	}
	
	public static void main(String[] args) {
		String requestData = "discount=0.00&payment_type=1&subject=价值6万金币&trade_no=2016060121001004090295219380&buyer_email=13891534209&gmt_create=2016-06-01 20:16:49&notify_type=trade_status_sync&quantity=1&out_trade_no=175dfa3294bb4de0baece69a36c92152&seller_id=2088911437022414&notify_time=2016-06-01 20:16:49&body=1226:1:9&trade_status=WAIT_BUYER_PAY&is_total_fee_adjust=Y&total_fee=6.00&seller_email=3168624126@qq.com&price=6.00&buyer_id=2088902273520091&notify_id=80235b6a511918ffd5f3a500b2c3d07gp2&use_coupon=N&sign_type=RSA&sign=AdQuV/Kif9gQljb373cMChdEAELA30cZuP92HhPVAHLa/0fyYVKwOhqTvWymnN2u3zpixjkJXYy0KX1BTQg7kNZ1rd5+86BapbVixlq0hwvViOjd7rox0JIlCfTXa5cBQn9DApDLXuCoJxOKS6loxPKLQXeR/wMHtYCWx2UEVSI=";
		String req2 = "discount=0.00&payment_type=1&subject=%E4%BB%B7%E5%80%BC6%E4%B8%87%E9%87%91%E5%B8%81&trade_no=2016060121001004090291858118&buyer_email=13891534209&gmt_create=2016-06-01+20%3A32%3A07&notify_type=trade_status_sync&quantity=1&out_trade_no=1bd8be10312f4df69d821c96c737c179&seller_id=2088911437022414&notify_time=2016-06-01+20%3A32%3A07&body=1226%3A1%3A9&trade_status=WAIT_BUYER_PAY&is_total_fee_adjust=Y&total_fee=6.00&seller_email=3168624126%40qq.com&price=6.00&buyer_id=2088902273520091&notify_id=3c9f0d1faf621f0dc1380d5000826b4gp2&use_coupon=N&sign_type=RSA&sign=PI2oZ4f7UfU82ZcO2Ibg%2BZwHIRjYCAP6Le4DsidpfyU5zdbACBy12xOFChPPYpITKm0GKm3spPZS1b0hw5VQ0067AOt1aFSiBXgULshtcyZTkGcNU9%2F%2F27jn52k7rezjMjH6vOBRGMJjmKFtF7cMI%2FWaT26JxubgYKbwcZtoX%2Bw%3D";
		String poce = HttpUtil.urldecode(req2);
		logger.error("recharge_back_data1:{}", poce);
		Map<String, String> keyvalmap = parseFormatString2(poce, 3, false);
		if (keyvalmap == null)
			return;
		
		if (!AlipayNotify.verify(keyvalmap, "2088221283349073"))
			return;
		
		System.err.println("dffgdfgdfg");
	}
}
