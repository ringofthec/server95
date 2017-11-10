package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_BJLBankerRe extends JsonPacket {
	public int getProtocolId() { return 366; }
	Integer a;
	public Integer getRetcode() { return this.a;}  // 
	public void setRetcode(Integer a) { this.a = a;}

	public static GDL_G2C_BJLBankerRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_BJLBankerRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
