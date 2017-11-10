package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_PigGiveGold extends JsonPacket {
	public int getProtocolId() { return 69; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家id
	public void setPlayerId(Long a) { this.a = a;}

	Long b;
	public Long getCount() { return this.b;}  // 数量
	public void setCount(Long b) { this.b = b;}

	Integer c;
	public Integer getType() { return this.c;}  // 0 金币 1 金砖
	public void setType(Integer c) { this.c = c;}

	public static GDL_C2G_PigGiveGold parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_PigGiveGold.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
