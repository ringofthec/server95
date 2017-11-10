package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class CatchFishSync extends JsonPacket {
	public int getProtocolId() { return 244; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家id
	public void setPlayerId(Long a) { this.a = a;}

	Integer b;
	public Integer getType() { return this.b;}  // 子弹类型
	public void setType(Integer b) { this.b = b;}

	List< DropItem > c = new ArrayList< DropItem >();   // 鱼的idx列表，包括掉落物品
	public List< DropItem > getDropInfo() { return this.c;}
	public void setDropInfo(List< DropItem > c) { this.c = c;}

	public static CatchFishSync parse(String data) {
		 return JsonUtil.JsonToObject(data, CatchFishSync.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
