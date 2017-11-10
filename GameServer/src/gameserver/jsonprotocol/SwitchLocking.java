package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class SwitchLocking extends JsonPacket {
	public int getProtocolId() { return 253; }
	Integer a;
	public Integer getFishIndex() { return this.a;}  // 选中的鱼的id
	public void setFishIndex(Integer a) { this.a = a;}

	public static SwitchLocking parse(String data) {
		 return JsonUtil.JsonToObject(data, SwitchLocking.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
