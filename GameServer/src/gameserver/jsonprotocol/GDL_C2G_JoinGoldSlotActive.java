package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_JoinGoldSlotActive extends JsonPacket {
	public int getProtocolId() { return 120; }
	public static GDL_C2G_JoinGoldSlotActive parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_JoinGoldSlotActive.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
