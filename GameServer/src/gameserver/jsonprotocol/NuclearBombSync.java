package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class NuclearBombSync extends JsonPacket {
	public int getProtocolId() { return 248; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家id
	public void setPlayerId(Long a) { this.a = a;}

	List< DropItem > b = new ArrayList< DropItem >();   // 鱼的idx列表，包括掉落物品
	public List< DropItem > getDropInfo() { return this.b;}
	public void setDropInfo(List< DropItem > b) { this.b = b;}

	Integer c;
	public Integer getPosX() { return this.c;}  // 爆炸位置
	public void setPosX(Integer c) { this.c = c;}

	Integer d;
	public Integer getPosY() { return this.d;}  // 爆炸位置
	public void setPosY(Integer d) { this.d = d;}

	public static NuclearBombSync parse(String data) {
		 return JsonUtil.JsonToObject(data, NuclearBombSync.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
