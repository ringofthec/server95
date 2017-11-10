package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_GetNotice extends JsonPacket {
	public int getProtocolId() { return 8; }
	public static GDL_C2G_GetNotice parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_GetNotice.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
