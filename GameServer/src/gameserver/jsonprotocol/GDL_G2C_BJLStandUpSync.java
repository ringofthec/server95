package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_BJLStandUpSync extends JsonPacket {
	public int getProtocolId() { return 359; }
	Long a;
	public Long getPlayer_id() { return this.a;}  // 谁？
	public void setPlayer_id(Long a) { this.a = a;}

	public static GDL_G2C_BJLStandUpSync parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_BJLStandUpSync.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
