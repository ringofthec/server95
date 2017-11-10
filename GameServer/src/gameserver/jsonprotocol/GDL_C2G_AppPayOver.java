package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_AppPayOver extends JsonPacket {
	public int getProtocolId() { return 302; }
	Integer a;
	public Integer getPayType() { return this.a;}  // 0 app 1 google
	public void setPayType(Integer a) { this.a = a;}

	String b;
	public String getSign() { return this.b;}  // pay发票
	public void setSign(String b) { this.b = b;}

	String c;
	public String getItemid() { return this.c;}  // 
	public void setItemid(String c) { this.c = c;}

	String d;
	public String getOrders() { return this.d;}  // 
	public void setOrders(String d) { this.d = d;}

	Integer e;
	public Integer getClientinfo() { return this.e;}  // 
	public void setClientinfo(Integer e) { this.e = e;}

	Integer f;
	public Integer getChannel() { return this.f;}  // 
	public void setChannel(Integer f) { this.f = f;}

	String g;
	public String getSignature() { return this.g;}  // 
	public void setSignature(String g) { this.g = g;}

	String h;
	public String getOriginalJson() { return this.h;}  // 
	public void setOriginalJson(String h) { this.h = h;}

	public static GDL_C2G_AppPayOver parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_AppPayOver.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
