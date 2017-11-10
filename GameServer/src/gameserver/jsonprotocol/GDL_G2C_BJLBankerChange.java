package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_BJLBankerChange extends JsonPacket {
	public int getProtocolId() { return 369; }
	BJLPlayerInfo a;   // 
	public BJLPlayerInfo getBanker() { return this.a;}
	public void setBanker(BJLPlayerInfo a) { this.a = a;}

	public static GDL_G2C_BJLBankerChange parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_BJLBankerChange.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
