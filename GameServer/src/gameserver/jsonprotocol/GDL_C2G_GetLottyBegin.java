package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_GetLottyBegin extends JsonPacket {
	public int getProtocolId() { return 80; }
	Integer a;
	public Integer getType() { return this.a;}  // 0 金币  1 金砖
	public void setType(Integer a) { this.a = a;}

	public static GDL_C2G_GetLottyBegin parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_GetLottyBegin.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
