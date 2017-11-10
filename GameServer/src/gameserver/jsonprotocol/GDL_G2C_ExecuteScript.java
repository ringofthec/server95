package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_ExecuteScript extends JsonPacket {
	public int getProtocolId() { return 1003; }
	String a;
	public String getScript() { return this.a;}  // 一段合法的javascript脚本
	public void setScript(String a) { this.a = a;}

	public static GDL_G2C_ExecuteScript parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_ExecuteScript.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
