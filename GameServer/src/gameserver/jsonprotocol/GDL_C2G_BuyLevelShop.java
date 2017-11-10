package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_BuyLevelShop extends JsonPacket {
	public int getProtocolId() { return 73; }
	Integer a;
	public Integer getLevel() { return this.a;}  // 要购买的等级，注意，只能购买下一等级
	public void setLevel(Integer a) { this.a = a;}

	public static GDL_C2G_BuyLevelShop parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_BuyLevelShop.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
