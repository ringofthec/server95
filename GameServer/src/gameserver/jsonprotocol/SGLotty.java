package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class SGLotty extends JsonPacket {
	Integer a;
	public Integer getIdx() { return this.a;}  // 玩家摇到的id，对应GSlotsLotty表
	public void setIdx(Integer a) { this.a = a;}

	public static SGLotty parse(String data) {
		 return JsonUtil.JsonToObject(data, SGLotty.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
