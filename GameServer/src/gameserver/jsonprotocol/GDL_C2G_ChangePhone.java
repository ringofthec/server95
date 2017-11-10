package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_ChangePhone extends JsonPacket {
	public int getProtocolId() { return 103; }
	String a;
	public String getCode() { return this.a;}  // 切换码
	public void setCode(String a) { this.a = a;}

	public static GDL_C2G_ChangePhone parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_ChangePhone.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
