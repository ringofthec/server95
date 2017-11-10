package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_PayEnd extends JsonPacket {
	public int getProtocolId() { return 304; }
	Integer a;
	public Integer getShopType() { return this.a;}  // 商店类型
	public void setShopType(Integer a) { this.a = a;}

	Integer b;
	public Integer getId() { return this.b;}  // 商店里的id
	public void setId(Integer b) { this.b = b;}

	Integer c;
	public Integer getPayType() { return this.c;}  // 支付类型
	public void setPayType(Integer c) { this.c = c;}

	Double d;
	public Double getPrice() { return this.d;}  // 货币数
	public void setPrice(Double d) { this.d = d;}

	public static GDL_G2C_PayEnd parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_PayEnd.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
