package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_ReqPayRe extends JsonPacket {
	public int getProtocolId() { return 301; }
	Integer a;
	public Integer getRetCode() { return this.a;}  // 返回值 0 可以购买  1 不能购买
	public void setRetCode(Integer a) { this.a = a;}

	Integer b;
	public Integer getShopType() { return this.b;}  // 商店类型
	public void setShopType(Integer b) { this.b = b;}

	Integer c;
	public Integer getId() { return this.c;}  // 商店里的id
	public void setId(Integer c) { this.c = c;}

	String d;
	public String getOrder() { return this.d;}  // pay序列号
	public void setOrder(String d) { this.d = d;}

	String e;
	public String getBody() { return this.e;}  // pay body
	public void setBody(String e) { this.e = e;}

	Integer f;
	public Integer getPayType() { return this.f;}  // 支付类型
	public void setPayType(Integer f) { this.f = f;}

	String g;
	public String getNotifyUrl() { return this.g;}  // 支付宝url
	public void setNotifyUrl(String g) { this.g = g;}

	String h;
	public String getPrepay_id() { return this.h;}  // wx prepay_id
	public void setPrepay_id(String h) { this.h = h;}

	String i;
	public String getNonce_str() { return this.i;}  // wx nonce_str
	public void setNonce_str(String i) { this.i = i;}

	String j;
	public String getTimes() { return this.j;}  // wx times
	public void setTimes(String j) { this.j = j;}

	String k;
	public String getSign() { return this.k;}  // wx sign
	public void setSign(String k) { this.k = k;}

	public static GDL_G2C_ReqPayRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_ReqPayRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
