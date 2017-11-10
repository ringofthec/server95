package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_BJLResignBankerRe extends JsonPacket {
	public int getProtocolId() { return 368; }
	Integer a;
	public Integer getRetcode() { return this.a;}  // 1
	public void setRetcode(Integer a) { this.a = a;}

	public static GDL_G2C_BJLResignBankerRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_BJLResignBankerRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
