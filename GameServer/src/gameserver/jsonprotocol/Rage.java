package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class Rage extends JsonPacket {
	public int getProtocolId() { return 257; }
	public static Rage parse(String data) {
		 return JsonUtil.JsonToObject(data, Rage.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
