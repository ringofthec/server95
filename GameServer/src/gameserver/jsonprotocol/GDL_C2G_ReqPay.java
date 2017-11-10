package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_ReqPay extends JsonPacket {
	public int getProtocolId() { return 300; }
	Integer a;
	public Integer getShopType() { return this.a;}  // 商店类型
	public void setShopType(Integer a) { this.a = a;}

	Integer b;
	public Integer getId() { return this.b;}  // 商店里的id
	public void setId(Integer b) { this.b = b;}

	Integer c;
	public Integer getPaytype() { return this.c;}  // 支付类型 1 苹果  2 支付宝 3 微信
	public void setPaytype(Integer c) { this.c = c;}

	public static GDL_C2G_ReqPay parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_ReqPay.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
