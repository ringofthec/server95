package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class SwitchLockingSync extends JsonPacket {
	public int getProtocolId() { return 254; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家id
	public void setPlayerId(Long a) { this.a = a;}

	Integer b;
	public Integer getFishIndex() { return this.b;}  // 选中的鱼的id
	public void setFishIndex(Integer b) { this.b = b;}

	public static SwitchLockingSync parse(String data) {
		 return JsonUtil.JsonToObject(data, SwitchLockingSync.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
