package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class SGOpen extends JsonPacket {
	List< Integer > a = new ArrayList< Integer >();   // 玩家翻牌的倍数，注意是相加的关系
	public List< Integer > getIdx() { return this.a;}
	public void setIdx(List< Integer > a) { this.a = a;}

	public static SGOpen parse(String data) {
		 return JsonUtil.JsonToObject(data, SGOpen.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
