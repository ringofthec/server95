package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class RageSync extends JsonPacket {
	public int getProtocolId() { return 258; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家id
	public void setPlayerId(Long a) { this.a = a;}

	public static RageSync parse(String data) {
		 return JsonUtil.JsonToObject(data, RageSync.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
