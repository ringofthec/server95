package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_LikeRe extends JsonPacket {
	public int getProtocolId() { return 15; }
	public static GDL_G2C_LikeRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_LikeRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
