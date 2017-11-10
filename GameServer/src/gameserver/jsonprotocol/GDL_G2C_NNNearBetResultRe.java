package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_NNNearBetResultRe extends JsonPacket {
	public int getProtocolId() { return 333; }
	List< NNBetResult > a = new ArrayList< NNBetResult >();   // 
	public List< NNBetResult > getRes() { return this.a;}
	public void setRes(List< NNBetResult > a) { this.a = a;}

	public static GDL_G2C_NNNearBetResultRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_NNNearBetResultRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
