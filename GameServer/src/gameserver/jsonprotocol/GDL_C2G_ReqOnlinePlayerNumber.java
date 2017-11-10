package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_ReqOnlinePlayerNumber extends JsonPacket {
	public int getProtocolId() { return 54; }
	public static GDL_C2G_ReqOnlinePlayerNumber parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_ReqOnlinePlayerNumber.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
