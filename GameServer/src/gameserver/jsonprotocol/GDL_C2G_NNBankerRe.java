package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_NNBankerRe extends JsonPacket {
	public int getProtocolId() { return 336; }
	Integer retcode;
	public Integer getRetcode() { return this.retcode;}  // 
	public void setRetcode(Integer retcode) { this.retcode = retcode;}

	public static GDL_C2G_NNBankerRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_NNBankerRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
