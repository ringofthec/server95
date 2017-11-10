package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_GiveGiftRe extends JsonPacket {
	public int getProtocolId() { return 24; }
	Integer a;
	public Integer getRetCode() { return this.a;}  // 0 成功 1 对面不在线
	public void setRetCode(Integer a) { this.a = a;}

	public static GDL_G2C_GiveGiftRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_GiveGiftRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
