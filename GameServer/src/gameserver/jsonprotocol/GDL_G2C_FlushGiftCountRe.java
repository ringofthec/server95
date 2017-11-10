package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_FlushGiftCountRe extends JsonPacket {
	public int getProtocolId() { return 71; }
	List< ShopGiftInfo > a = new ArrayList< ShopGiftInfo >();   // 礼品的购买信息
	public List< ShopGiftInfo > getGift() { return this.a;}
	public void setGift(List< ShopGiftInfo > a) { this.a = a;}

	public static GDL_G2C_FlushGiftCountRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_FlushGiftCountRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
