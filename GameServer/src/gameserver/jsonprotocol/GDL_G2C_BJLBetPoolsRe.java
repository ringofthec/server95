package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_BJLBetPoolsRe extends JsonPacket {
	public int getProtocolId() { return 355; }
	BJLBetPool a;   // 下注池中的钱
	public BJLBetPool getBetPools() { return this.a;}
	public void setBetPools(BJLBetPool a) { this.a = a;}

	public static GDL_G2C_BJLBetPoolsRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_BJLBetPoolsRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
