package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class Speed extends JsonPacket {
	public int getProtocolId() { return 255; }
	public static Speed parse(String data) {
		 return JsonUtil.JsonToObject(data, Speed.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
