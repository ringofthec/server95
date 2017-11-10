package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_SetPlayerInfo extends JsonPacket {
	public int getProtocolId() { return 28; }
	Integer a;
	public Integer getMode() { return this.a;}  // 1 性别 2 签名
	public void setMode(Integer a) { this.a = a;}

	Integer b;
	public Integer getSex() { return this.b;}  // 0 男 1 女
	public void setSex(Integer b) { this.b = b;}

	String c;
	public String getSign() { return this.c;}  // 签名
	public void setSign(String c) { this.c = c;}

	public static GDL_C2G_SetPlayerInfo parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_SetPlayerInfo.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
