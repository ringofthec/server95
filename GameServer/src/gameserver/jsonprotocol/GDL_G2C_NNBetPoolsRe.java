package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_NNBetPoolsRe extends JsonPacket {
	public int getProtocolId() { return 325; }
	NNBetPool a;   // 下注池中的钱
	public NNBetPool getBetPools() { return this.a;}
	public void setBetPools(NNBetPool a) { this.a = a;}

	public static GDL_G2C_NNBetPoolsRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_NNBetPoolsRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
