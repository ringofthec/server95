package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_ChangeName extends JsonPacket {
	public int getProtocolId() { return 21; }
	String a;
	public String getName() { return this.a;}  // 名字
	public void setName(String a) { this.a = a;}

	public static GDL_C2G_ChangeName parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_ChangeName.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
