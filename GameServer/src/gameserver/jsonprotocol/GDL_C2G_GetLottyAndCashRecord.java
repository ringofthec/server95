package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_GetLottyAndCashRecord extends JsonPacket {
	public int getProtocolId() { return 84; }
	public static GDL_C2G_GetLottyAndCashRecord parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_GetLottyAndCashRecord.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
