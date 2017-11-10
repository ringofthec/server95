package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_GiveGift extends JsonPacket {
	public int getProtocolId() { return 23; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家id
	public void setPlayerId(Long a) { this.a = a;}

	Long b;
	public Long getItemId() { return this.b;}  // 注意，是物品id，不是物品模板id
	public void setItemId(Long b) { this.b = b;}

	public static GDL_C2G_GiveGift parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_GiveGift.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
