package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_RandomNameRe extends JsonPacket {
	public int getProtocolId() { return 20; }
	String a;
	public String getName() { return this.a;}  // 名字
	public void setName(String a) { this.a = a;}

	public static GDL_G2C_RandomNameRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_RandomNameRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
