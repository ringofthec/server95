package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_LeaveGame extends JsonPacket {
	public int getProtocolId() { return 202; }
	public static GDL_C2G_LeaveGame parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_LeaveGame.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
