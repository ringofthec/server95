package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_EndSlotsRe extends JsonPacket {
	public int getProtocolId() { return 93; }
	public static GDL_G2C_EndSlotsRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_EndSlotsRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
