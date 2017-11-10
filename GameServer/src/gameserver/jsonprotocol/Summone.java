package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class Summone extends JsonPacket {
	public int getProtocolId() { return 245; }
	public static Summone parse(String data) {
		 return JsonUtil.JsonToObject(data, Summone.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
