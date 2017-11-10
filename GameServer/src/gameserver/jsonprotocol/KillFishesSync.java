package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class KillFishesSync extends JsonPacket {
	public int getProtocolId() { return 262; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家id
	public void setPlayerId(Long a) { this.a = a;}

	List< DropItem > b = new ArrayList< DropItem >();   // 顺带杀死的鱼的idx列表
	public List< DropItem > getDropInfo() { return this.b;}
	public void setDropInfo(List< DropItem > b) { this.b = b;}

	public static KillFishesSync parse(String data) {
		 return JsonUtil.JsonToObject(data, KillFishesSync.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
