package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_BuyGiftShop extends JsonPacket {
	public int getProtocolId() { return 72; }
	Integer a;
	public Integer getGiftId() { return this.a;}  // 礼物商品id，对应着GGiftShop 表相同id的行
	public void setGiftId(Integer a) { this.a = a;}

	public static GDL_C2G_BuyGiftShop parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_BuyGiftShop.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
