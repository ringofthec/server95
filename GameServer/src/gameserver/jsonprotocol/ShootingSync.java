package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class ShootingSync extends JsonPacket {
	public int getProtocolId() { return 242; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家id
	public void setPlayerId(Long a) { this.a = a;}

	Double b;
	public Double getAngle() { return this.b;}  // 角度
	public void setAngle(Double b) { this.b = b;}

	Integer c;
	public Integer getXp() { return this.c;}  // xp累计值
	public void setXp(Integer c) { this.c = c;}

	public static ShootingSync parse(String data) {
		 return JsonUtil.JsonToObject(data, ShootingSync.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
