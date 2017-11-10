package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class Locking extends JsonPacket {
	public int getProtocolId() { return 251; }
	public static Locking parse(String data) {
		 return JsonUtil.JsonToObject(data, Locking.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
