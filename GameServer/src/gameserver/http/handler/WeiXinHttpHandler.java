package gameserver.http.handler;

import gameserver.jsonprotocol.GDL_G2C_Fallback;
import gameserver.utils.DbMgr;
import gameserver.utils.Util;

import java.io.InputStream;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.util.AlipayNotify;
import com.commons.http.MyHttpHandler;
import com.commons.network.websock.PlayerConManager;
import com.commons.network.websock.PlayerConnection;
import com.commons.network.websock.handler.PayHandler;
import com.commons.network.websock.handler.PayHandler.PayInfo;
import com.commons.util.CharsetUtil;
import com.commons.util.HttpUtil;
import com.commons.util.HttpUtil.HTTP_METHOD;
import com.commons.util.TimeUtil;
import com.sun.net.httpserver.HttpExchange;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import databaseshare.DatabaseAccount;
import databaseshare.DatabaseRecharge_cache2;

public class WeiXinHttpHandler extends MyHttpHandler {
	private final static Logger logger = LoggerFactory.getLogger(WeiXinHttpHandler.class);
	
	private static XStream xstream = new XStream(new XppDriver() {  
		public HierarchicalStreamWriter createWriter(Writer out) {  
			return new PrettyPrintWriter(out) {   
				//增加CDATA标记   
				boolean cdata = true;   
				@SuppressWarnings("rawtypes")   
				public void startNode(String name, Class clazz) {   
					super.startNode(name, clazz);   
				}   
				protected void writeText(QuickWriter writer, String text) {   
					if (cdata) {    
						writer.write("<![CDATA[");   
						writer.write(text);    
						writer.write("]]>");   
					} else {    
						writer.write(text);   
					}   
				}  
			};  
		} 
	}); 
	
	public static String payInfoToXML(ResInfo pi) 
	{  
		xstream.alias("xml", pi.getClass());  
		return xstream.toXML(pi); 
	} 

	
	public static void createSign(PayInfo payInfo, String key)
	{   
		String signTemp = "appid="+payInfo.getAppid()    
				+"&attach="+payInfo.getAttach()    
				+"&body="+payInfo.getBody()    
				+"&device_info="+payInfo.getDevice_info()    
				+"&mch_id="+payInfo.getMch_id()    
				+"&nonce_str="+payInfo.getNonce_str()    
				+"&notify_url="+payInfo.getNotify_url()    
				+"&out_trade_no="+payInfo.getOut_trade_no()    
				+"&spbill_create_ip="+payInfo.getSpbill_create_ip()    
				+"&total_fee="+payInfo.getTotal_fee()    
				+"&trade_type="+payInfo.getTrade_type()    
				+"&key="+key; //这个key注意  
		
		String sign = Util.string2MD5(signTemp).toUpperCase();
		payInfo.setSign(sign);
	} 
	
	public static Map<String, String> parseXml(String xml) { 
		Map<String, String> map = new TreeMap<String, String>(); 
		Document document;
		try {
			document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement(); 
			List<Element> elementList = root.elements(); 
			for (Element e : elementList) {
				map.put(e.getName(), e.getText());
				System.err.println(e.getName() + "    " + e.getText());
			}
			return map;
		} catch (DocumentException e1) {
			e1.printStackTrace();
		} 
		return null;
	}
	
	public class ResInfo {
		public String return_code;
		public String return_msg;
		public String getReturn_code() {
			return return_code;
		}
		public void setReturn_code(String return_code) {
			this.return_code = return_code;
		}
		public String getReturn_msg() {
			return return_msg;
		}
		public void setReturn_msg(String return_msg) {
			this.return_msg = return_msg;
		}
	}
	
	private void success(HttpExchange httpExchange) {
		ResInfo ri = new ResInfo();
		ri.setReturn_code("SUCCESS");
		ri.setReturn_msg("");
		String xn = payInfoToXML(ri);
		HttpUtil.sendResponse(httpExchange, xn);
	}
	
	private void fail(HttpExchange httpExchange, String msg) {
		ResInfo ri = new ResInfo();
		ri.setReturn_code("FAIL");
		ri.setReturn_msg(msg);
		String xn = payInfoToXML(ri);
		HttpUtil.sendResponse(httpExchange, xn);
	}
	
	@Override
	public void handle(HttpExchange httpExchange) {
		try
		{
			String method = httpExchange.getRequestMethod();
			String uri = httpExchange.getRequestURI().toString();
			InetSocketAddress address = httpExchange.getRemoteAddress();
			logger.debug("接收到http请求[{}]  uri : '{}'  method : '{}'",address,uri,method);
			if (!method.toUpperCase().contains(HTTP_METHOD.POST.getName()))
				return ;

			InputStream stream = httpExchange.getRequestBody();
			String requestData = IOUtils.toString(stream,CharsetUtil.defaultCharset);
			logger.error("recharge_back_data1:{}", requestData);
			requestData = HttpUtil.urldecode(requestData);
			logger.error("recharge_back_data2:{}", requestData);

			Map<String, String> keyvalmap = parseXml(requestData);
			if (keyvalmap == null)
				return;

			if (!isVec(keyvalmap)) {
				logger.error("签名检查错误");
				fail(httpExchange, "签名检查错误");
				return;
			}

			String out_order = keyvalmap.get("out_trade_no");
			int count = DbMgr.getInstance().getShareDB().Count(DatabaseRecharge_cache2.class, "pay_order=?", out_order);
			if (count >= 1) {
				logger.error("pay already process");
				success(httpExchange);
				return ;
			}

			String body_str = keyvalmap.get("attach");
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
			cache.amount = Float.parseFloat( keyvalmap.get("cash_fee") );
			cache.player_id = pid;
			cache.recharge_str = requestData;
			cache.mask = 0;
			cache.shop_type = st;
			cache.tid = ti;
			cache.pay_order = out_order;
			cache.create_time = TimeUtil.GetDateTime();
			cache.get_time = TimeUtil.getTime("2012-12-12 00:00:00");
			cache.recharge_str = requestData;
			cache.paytype = 2;
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
			success(httpExchange);
			return ;
		}
		catch (Exception e)
		{
			logger.error("ZhiFuBaoHttpHandler handle is error : ",e);
		}
		
		fail(httpExchange, "其他错误");
	}
	@Override
	public String getPattern() {
		return "/weixinret";
	}
	
	public static boolean isVec(Map<String, String> keyvalmap) {
		StringBuilder sb = new StringBuilder();
		Iterator<Map.Entry<String,String>> it = keyvalmap.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String,String> entry = it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if(!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + PayHandler.apkey);
		String eddf = sb.toString();
		String md3 = Util.string2MD5(eddf).toUpperCase();
		if (md3.equals( keyvalmap.get("sign") ))
			return true;
		return false;
	}
	
	public static void main(String[] args) {
		String req2 = "<xml><appid><![CDATA[wx5594532b28201f6d]]></appid><attach><![CDATA[1222:1:1]]></attach><bank_type><![CDATA[CCB_DEBIT]]></bank_type><cash_fee><![CDATA[10]]></cash_fee><device_info><![CDATA[WEB]]></device_info><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1350028601]]></mch_id><nonce_str><![CDATA[66d46b0127a14eb5ac5c7335900c50a4]]></nonce_str><openid><![CDATA[oaHE5w_agsPROMhBEKsYsS1pVoJI]]></openid><out_trade_no><![CDATA[66d46b0127a14eb5ac5c7335900c50a4]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[B6E74C4BD79E3E217BCD779674D6CF44]]></sign><time_end><![CDATA[20160607214110]]></time_end><total_fee>10</total_fee><trade_type><![CDATA[APP]]></trade_type><transaction_id><![CDATA[4007592001201606076927106276]]></transaction_id></xml>";
		String poce = HttpUtil.urldecode(req2);
		logger.error("recharge_back_data1:{}", poce);
		
		Map<String, String> keyvalmap = parseXml(poce);
		if (keyvalmap == null)
			return;
		
		if (!isVec(keyvalmap)) {
			System.err.println("fail");
			return;
		}
		
		System.err.println("success");
	}
}
