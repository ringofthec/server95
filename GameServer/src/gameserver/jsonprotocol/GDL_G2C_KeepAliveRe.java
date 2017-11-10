package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_KeepAliveRe extends JsonPacket {
	public int getProtocolId() { return 89; }
	Long a;
	public Long getTimestamp() { return this.a;}  // 
	public void setTimestamp(Long a) { this.a = a;}

	public static GDL_G2C_KeepAliveRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_KeepAliveRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
