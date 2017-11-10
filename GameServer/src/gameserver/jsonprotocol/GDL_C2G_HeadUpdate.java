package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_HeadUpdate extends JsonPacket {
	public int getProtocolId() { return 56; }
	String a;
	public String getHead() { return this.a;}  // 
	public void setHead(String a) { this.a = a;}

	public static GDL_C2G_HeadUpdate parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_HeadUpdate.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
