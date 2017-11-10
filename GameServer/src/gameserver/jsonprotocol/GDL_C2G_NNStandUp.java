package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_NNStandUp extends JsonPacket {
	public int getProtocolId() { return 328; }
	public static GDL_C2G_NNStandUp parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_NNStandUp.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
