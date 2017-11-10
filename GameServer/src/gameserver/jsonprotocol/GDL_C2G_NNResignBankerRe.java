package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_NNResignBankerRe extends JsonPacket {
	public int getProtocolId() { return 338; }
	Integer retcode;
	public Integer getRetcode() { return this.retcode;}  // 1
	public void setRetcode(Integer retcode) { this.retcode = retcode;}

	public static GDL_C2G_NNResignBankerRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_NNResignBankerRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
