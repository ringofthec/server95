package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_GetMailList extends JsonPacket {
	public int getProtocolId() { return 60; }
	public static GDL_C2G_GetMailList parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_GetMailList.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
