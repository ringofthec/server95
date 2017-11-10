package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class NNBetResult extends JsonPacket {
	List< Integer > a = new ArrayList< Integer >();   // 1 赢 0 输
	public List< Integer > getWin() { return this.a;}
	public void setWin(List< Integer > a) { this.a = a;}

	public static NNBetResult parse(String data) {
		 return JsonUtil.JsonToObject(data, NNBetResult.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
