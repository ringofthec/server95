package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_NNPlayerNotSitList extends JsonPacket {
	public int getProtocolId() { return 334; }
	public static GDL_C2G_NNPlayerNotSitList parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_NNPlayerNotSitList.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
