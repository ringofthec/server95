package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class LottyRecord extends JsonPacket {
	String a;
	public String getName() { return this.a;}  // 玩家名字
	public void setName(String a) { this.a = a;}

	Integer b;
	public Integer getItemId() { return this.b;}  // 得奖物品id GItem表
	public void setItemId(Integer b) { this.b = b;}

	Integer c;
	public Integer getNum() { return this.c;}  // 得奖物品数量
	public void setNum(Integer c) { this.c = c;}

	public static LottyRecord parse(String data) {
		 return JsonUtil.JsonToObject(data, LottyRecord.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
