package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class Frozen extends JsonPacket {
	public int getProtocolId() { return 249; }
	public static Frozen parse(String data) {
		 return JsonUtil.JsonToObject(data, Frozen.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
