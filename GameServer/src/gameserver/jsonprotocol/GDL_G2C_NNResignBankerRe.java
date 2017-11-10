package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_NNResignBankerRe extends JsonPacket {
	public int getProtocolId() { return 338; }
	Integer a;
	public Integer getRetcode() { return this.a;}  // 1
	public void setRetcode(Integer a) { this.a = a;}

	public static GDL_G2C_NNResignBankerRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_NNResignBankerRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
