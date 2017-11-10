package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class FishMoneySync extends JsonPacket {
	public int getProtocolId() { return 263; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家id
	public void setPlayerId(Long a) { this.a = a;}

	Long b;
	public Long getCoin() { return this.b;}  // 
	public void setCoin(Long b) { this.b = b;}

	Long c;
	public Long getGold() { return this.c;}  // 
	public void setGold(Long c) { this.c = c;}

	public static FishMoneySync parse(String data) {
		 return JsonUtil.JsonToObject(data, FishMoneySync.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
