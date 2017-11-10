package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_KeepAlive extends JsonPacket {
	public int getProtocolId() { return 88; }
	public static GDL_C2G_KeepAlive parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_KeepAlive.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
