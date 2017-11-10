package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class BJLBetPool extends JsonPacket {
	List< Integer > a = new ArrayList< Integer >();   // 池子id 0 庄 1 闲 2 庄对 3 闲对 4 和
	public List< Integer > getPoolId() { return this.a;}
	public void setPoolId(List< Integer > a) { this.a = a;}

	List< Long > b = new ArrayList< Long >();   // 钱数
	public List< Long > getPoolCoin() { return this.b;}
	public void setPoolCoin(List< Long > b) { this.b = b;}

	public static BJLBetPool parse(String data) {
		 return JsonUtil.JsonToObject(data, BJLBetPool.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
