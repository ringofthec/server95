package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_FishTaskComplete extends JsonPacket {
	public int getProtocolId() { return 207; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家id
	public void setPlayerId(Long a) { this.a = a;}

	public static GDL_G2C_FishTaskComplete parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_FishTaskComplete.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
