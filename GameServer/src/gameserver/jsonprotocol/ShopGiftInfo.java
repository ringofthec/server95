package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class ShopGiftInfo extends JsonPacket {
	Integer a;
	public Integer getGiftId() { return this.a;}  // 礼物商品id，对应着GGiftShop 表相同id的行
	public void setGiftId(Integer a) { this.a = a;}

	Integer b;
	public Integer getCount() { return this.b;}  // 剩余商品数量，分子
	public void setCount(Integer b) { this.b = b;}

	Integer c;
	public Integer getTotal() { return this.c;}  // 总商品数量，分母
	public void setTotal(Integer c) { this.c = c;}

	public static ShopGiftInfo parse(String data) {
		 return JsonUtil.JsonToObject(data, ShopGiftInfo.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
