package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class ChangeBatterySync extends JsonPacket {
	public int getProtocolId() { return 240; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家id
	public void setPlayerId(Long a) { this.a = a;}

	Integer b;
	public Integer getType() { return this.b;}  // 类型
	public void setType(Integer b) { this.b = b;}

	public static ChangeBatterySync parse(String data) {
		 return JsonUtil.JsonToObject(data, ChangeBatterySync.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
