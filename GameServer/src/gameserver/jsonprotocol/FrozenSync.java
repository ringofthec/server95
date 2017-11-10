package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class FrozenSync extends JsonPacket {
	public int getProtocolId() { return 250; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家id
	public void setPlayerId(Long a) { this.a = a;}

	public static FrozenSync parse(String data) {
		 return JsonUtil.JsonToObject(data, FrozenSync.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
