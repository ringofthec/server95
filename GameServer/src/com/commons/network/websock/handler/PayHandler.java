package com.commons.network.websock.handler;

import gameserver.config.ServerConfig;
import gameserver.jsonprotocol.GDL_C2G_AppPayOver;
import gameserver.jsonprotocol.GDL_C2G_ReqPay;
import gameserver.jsonprotocol.GDL_G2C_Fallback;
import gameserver.jsonprotocol.GDL_G2C_PayEnd;
import gameserver.jsonprotocol.GDL_G2C_ReqPayRe;
import gameserver.utils.DbMgr;
import gameserver.utils.HttpUtil;
import gameserver.utils.Util;
import gameserver.utils.UuidGenerator;

import java.io.Serializable;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.base.TableManager;
import table.base.TableManager.AppStoreInfo;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConnection;
import com.commons.util.TimeUtil;
import com.iapppay.demo.Order;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import databaseshare.DatabaseRecharge_cache2;

public class PayHandler {
	private final static PayHandler instance = new PayHandler();
	public static PayHandler getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(PayHandler.class);
	
	
	/////////////////// 下面是微信支付的统一下单
	public static class PayInfo implements Serializable{  
		 public static final long serialVersionUID = 0L;  
		 public String appid;  
		 public String mch_id; 
		 public String device_info;  
		 public String nonce_str;  
		 public String sign;  
		 public String body;  
		 public String attach;  
		 public String out_trade_no;  
		 public int total_fee;  
		 public String spbill_create_ip;  
		 public String notify_url;  
		 public String trade_type;  
		public String getAppid() {
			return appid;
		}
		public void setAppid(String appid) {
			this.appid = appid;
		}
		public String getMch_id() {
			return mch_id;
		}
		public void setMch_id(String mch_id) {
			this.mch_id = mch_id;
		}
		public String getDevice_info() {
			return device_info;
		}
		public void setDevice_info(String device_info) {
			this.device_info = device_info;
		}
		public String getNonce_str() {
			return nonce_str;
		}
		public void setNonce_str(String nonce_str) {
			this.nonce_str = nonce_str;
		}
		public String getSign() {
			return sign;
		}
		public void setSign(String sign) {
			this.sign = sign;
		}
		public String getBody() {
			return body;
		}
		public void setBody(String body) {
			this.body = body;
		}
		public String getAttach() {
			return attach;
		}
		public void setAttach(String attach) {
			this.attach = attach;
		}
		public String getOut_trade_no() {
			return out_trade_no;
		}
		public void setOut_trade_no(String out_trade_no) {
			this.out_trade_no = out_trade_no;
		}
		public int getTotal_fee() {
			return total_fee;
		}
		public void setTotal_fee(int total_fee) {
			this.total_fee = total_fee;
		}
		public String getSpbill_create_ip() {
			return spbill_create_ip;
		}
		public void setSpbill_create_ip(String spbill_create_ip) {
			this.spbill_create_ip = spbill_create_ip;
		}
		public String getNotify_url() {
			return notify_url;
		}
		public void setNotify_url(String notify_url) {
			this.notify_url = notify_url;
		}
		public String getTrade_type() {
			return trade_type;
		}
		public void setTrade_type(String trade_type) {
			this.trade_type = trade_type;
		}
	}
	
	public static PayInfo createPayInfo(long player_id, 
			int stype,
			int id,
			String appid, 
			String mch_id, 
			String ip,
			int money_fen,
			String randomkey,
			String url) 
	{   
		String body = player_id + ":" + stype + ":" + id;
		
		PayInfo payInfo = new PayInfo();   
		payInfo.setAppid(appid);   
		payInfo.setDevice_info("WEB");   
		payInfo.setMch_id(mch_id);   
		payInfo.setNonce_str(randomkey);   
		payInfo.setBody(body);   
		payInfo.setAttach(body);   
		payInfo.setOut_trade_no(UuidGenerator.newUuid());   
		payInfo.setTotal_fee(money_fen);   
		payInfo.setSpbill_create_ip(ip);   
		payInfo.setNotify_url(url);  
		payInfo.setTrade_type("APP");   
		createSign(payInfo, apkey);
		return payInfo;  
	} 
	
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
	
	public static String payInfoToXML(PayInfo pi) 
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
		Map<String, String> map = new HashMap<String, String>(); 
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
	
	
	public static void main(String[] args) {
		if (true) {
			
		} else {
			PayInfo pi = createPayInfo(123L, 1, 2, appid, parnterId, "192.168.0.1", 
					 12, UuidGenerator.newUuid(), "http://req.jzdwc888.com:12760/weixinret");
			String sdfsdf = payInfoToXML(pi);
			String pricess = sdfsdf.replace("__", "_").replace("<![CDATA[", "").replace("]]>", ""); 
			System.err.println(pi);
			System.err.println(sdfsdf);
			System.err.println(pricess);
			
			String ddf = HttpUtil.httpSynSend("https://api.mch.weixin.qq.com/pay/unifiedorder", HttpUtil.POST_METHOD, pricess);
			System.err.println(ddf);
			String repricess = ddf.replace("__", "_").replace("<![CDATA[", "").replace("]]>", ""); 
			System.err.println(repricess);
			parseXml(repricess);
		}
	}


	////////////////////////////// 上面是微信支付
	
	public void init() {
		HandlerManager.getInstance().pushNornalHandler(PayHandler.class, this, 
				"OnReqPay", new GDL_C2G_ReqPay());
		HandlerManager.getInstance().pushNornalHandler(PayHandler.class, this, 
				"OnAppPayOver", new GDL_C2G_AppPayOver());
	}
	
	public Map<String, String> getWeiXinInfo(PlayerConnection con, int st, int id, int price, String timestr, 
			String uuidsss,
			GDL_G2C_ReqPayRe re) {
		
		String url = "http://req.jzdwc888.com:12760/weixinret";
		if (ServerConfig.app_test) {
			url = "http://test.jzdwc888.com:12760/weixinret";
		}
		
		PayInfo pi = createPayInfo(con.getPlayerId(), st, id, appid, parnterId, "192.168.0.1", 
				price, uuidsss, url);
		String sdfsdf = payInfoToXML(pi);
		String pricess = sdfsdf.replace("__", "_").replace("<![CDATA[", "").replace("]]>", ""); 
		System.err.println(pi);
		System.err.println(sdfsdf);
		System.err.println(pricess);
		
		String ddf = HttpUtil.httpSynSend("https://api.mch.weixin.qq.com/pay/unifiedorder", HttpUtil.POST_METHOD, pricess);
		System.err.println(ddf);
		String repricess = ddf.replace("__", "_").replace("<![CDATA[", "").replace("]]>", ""); 
		System.err.println(repricess);
		Map<String, String> mapdfd = parseXml(repricess);
		if (mapdfd == null)
			return null;
		
		if (mapdfd.get("return_code").equals("SUCCESS") 
				&& mapdfd.get("return_msg").equals("OK")
				&& mapdfd.get("result_code").equals("SUCCESS")) {
			re.setNonce_str(uuidsss);
			re.setPrepay_id(mapdfd.get("prepay_id"));
			re.setTimes(timestr);
			return mapdfd;
		}
		
		return null;
	}
	
	public static String appid = "wx5594532b28201f6d";
	public static String parnterId = "1350028601";
	public static String apkey = "UIGedued233gHD32er322FujhGdefg2C";
	
	public void genSign(Map<String, String> mapdfd, String timesss, String uuidss, GDL_G2C_ReqPayRe re) {
		StringBuilder sb = new StringBuilder();
		sb.append("appid=").append(appid)
			.append("&noncestr=").append(uuidss)
			.append("&package=").append("Sign=WXPay")
			.append("&partnerid=").append(parnterId)
			.append("&prepayid=").append(mapdfd.get("prepay_id"))
			.append("&timestamp=").append(timesss)
			.append("&key=").append(apkey);
		String org = sb.toString();
		String md5old = Util.string2MD5(org).toUpperCase();
		System.err.println("org: " + org);
		System.err.println("md5old: " + md5old);
		re.setSign(md5old);
	}
	
	public void OnReqPay(PlayerConnection con, GDL_C2G_ReqPay message) {
		GDL_G2C_ReqPayRe re = new GDL_G2C_ReqPayRe();
		int st = message.getShopType();
		int tid = message.getId();
		int price = 0;
		if (st == 2) {
			if (!TableManager.GetInstance().TableGGiftPackageShop().Contains(tid))
				return ;
			
			if (!con.getPlayer().canByGift(con, message.getId())) {
				re.setRetCode(1);
				con.send(re);
				return ;
			}
			
			price = TableManager.GetInstance().TableGGiftPackageShop().GetElement(tid).price();
		} else {
			if (!TableManager.GetInstance().TableGStoreItem().Contains(tid))
				return ;
			
			price = TableManager.GetInstance().TableGStoreItem().GetElement(tid).price();
		}
		
		if (message.getPaytype() == 9) {
			String url = "http://req.jzdwc888.com:12760/aibeiwei_pay_ret";
			if (ServerConfig.app_test) {
				url = "http://test.jzdwc888.com:12760/aibeiwei_pay_ret";
			}
			
			String tan_id = Order.getAndCheckOder(con.getPlayerId(),  
					message.getShopType(), message.getId(), 
					price, UuidGenerator.newUuid(), url);
			if (tan_id == null)
				return ;
			
			re.setPrepay_id(tan_id);
		} else if (message.getPaytype() == 2) {
			String retstr = "alipayret";
			if (con.getChannel() == 7 || 
					con.getChannel() == 4 || 
					con.getChannel() == 12){
				retstr = "alipayret_fanqie";
			}
			re.setNotifyUrl("http://req.jzdwc888.com:12760/" + retstr);
			if (ServerConfig.app_test)
				re.setNotifyUrl("http://test.jzdwc888.com:12760/" + retstr);
		} else if (message.getPaytype() == 3) {
			String fdfdf = (int)(TimeUtil.GetDateTime() / 1000) + "";
			String uuidsss = UuidGenerator.newUuid();
			Map<String, String> mmp = getWeiXinInfo(con, message.getShopType(), message.getId(), price * 100, fdfdf,uuidsss, re);
			if (mmp == null) {
				re.setRetCode(1);
				con.send(re);
				return ;
			}
			
			genSign(mmp, fdfdf, uuidsss, re);
		}
		
		String order = UuidGenerator.newUuid();
		String body = con.getPlayerId() + ":" + message.getShopType() + ":" + message.getId();
		re.setRetCode(0);
		re.setOrder(order);
		re.setId(message.getId());
		re.setBody(body);
		re.setPayType(message.getPaytype());
		re.setShopType(message.getShopType());
		
		con.send(re);
	}
	
	public void OnAppPayOver(PlayerConnection con, GDL_C2G_AppPayOver message) {
		String[] oooo = message.getSign().split(":");
		if (!verifyingInAppPurchase(oooo[0], oooo[1]))
			return ;
		
		String signMd5 = Util.string2MD5(message.getSign());
		int count = DbMgr.getInstance().getShareDB().Count(DatabaseRecharge_cache2.class, "pay_order=?", signMd5);
		if (count >= 1) {
			return ;
		}
		
		int storeT = 0;
		int tid = 0;
		float price = 0.0f;
		AppStoreInfo asi = TableManager.GetInstance().getConfigByItemInfo(oooo[0]);
		if (asi == null)
			return ;
		
		if (asi.m_storeItem != null) {
			storeT = 1;
			tid = asi.m_storeItem.id();
			price = asi.m_storeItem.price();
		} else {
			storeT = 2;
			tid = asi.m_giftShop.id();
			price = asi.m_giftShop.price();
		}
		
		DatabaseRecharge_cache2 cache = new DatabaseRecharge_cache2();
		cache.amount = price * 100; // 转化为分
		cache.player_id = con.getPlayerId();
		cache.recharge_str = oooo[1];
		cache.mask = 0;
		cache.shop_type = storeT;
		cache.tid = tid;
		cache.pay_order = signMd5;
		cache.create_time = TimeUtil.GetDateTime();
		cache.get_time = TimeUtil.getTime("2012-12-12 00:00:00");
		cache.paytype = 0;
		cache.channel = con.getPlayer().getChannel();
		DbMgr.getInstance().getShareDB().Insert(cache);
		
		GDL_G2C_PayEnd pay_end = new GDL_G2C_PayEnd();
		pay_end.setId(tid);
		pay_end.setShopType(storeT);
		pay_end.setPayType(0);
		pay_end.setPrice((double)price);
		con.send(pay_end);
		
		GDL_G2C_Fallback re = new GDL_G2C_Fallback();
		re.setFuncId(1);
		re.setData(" ");
		con.send(re);
	}
	
	public boolean verifyingInAppPurchase(String pid, String transactionReceipt) {
		if (ServerConfig.app_test) 
			return true;
		
		JSONObject postFeild = new JSONObject();
		postFeild.put("receipt-data", transactionReceipt);
		String oop = null;
		
		if (oop == null) {
			try
			{
				oop = HttpUtil.httpSynSend("https://buy.itunes.apple.com/verifyReceipt", "POST", postFeild.toString());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		JSONParser retParse = new JSONParser();
		boolean isValid = false;
		try{
			logger.error("app pay ret = " + oop);
			JSONObject tempJosnObj = (JSONObject)retParse.parse(oop);
			long retcode = (Long)tempJosnObj.get("status");
			if (retcode == 0)
				isValid = true;
			
			if (isValid) {
				JSONObject obj = (JSONObject)tempJosnObj.get("receipt");
				String protid = (String)obj.get("product_id");
				if (!protid.equals(pid))
					isValid = false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isValid;
	}
}
