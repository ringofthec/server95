package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GiftShowInfo extends JsonPacket {
	Integer a;
	public Integer getItemTempId() { return this.a;}  // 物品模板id
	public void setItemTempId(Integer a) { this.a = a;}

	Integer b;
	public Integer getNum() { return this.b;}  // 数量
	public void setNum(Integer b) { this.b = b;}

	String c;
	public String getExpireTime() { return this.c;}  // 到期时间
	public void setExpireTime(String c) { this.c = c;}

	public static GiftShowInfo parse(String data) {
		 return JsonUtil.JsonToObject(data, GiftShowInfo.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
