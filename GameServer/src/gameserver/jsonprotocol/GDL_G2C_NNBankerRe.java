package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_NNBankerRe extends JsonPacket {
	public int getProtocolId() { return 336; }
	Integer a;
	public Integer getRetcode() { return this.a;}  // 
	public void setRetcode(Integer a) { this.a = a;}

	public static GDL_G2C_NNBankerRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_NNBankerRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
