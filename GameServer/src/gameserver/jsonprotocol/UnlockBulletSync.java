package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class UnlockBulletSync extends JsonPacket {
	public int getProtocolId() { return 236; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家id
	public void setPlayerId(Long a) { this.a = a;}

	Integer b;
	public Integer getType() { return this.b;}  // 解锁的子弹类型
	public void setType(Integer b) { this.b = b;}

	public static UnlockBulletSync parse(String data) {
		 return JsonUtil.JsonToObject(data, UnlockBulletSync.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
