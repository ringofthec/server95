package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class SummoneSync extends JsonPacket {
	public int getProtocolId() { return 246; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家id
	public void setPlayerId(Long a) { this.a = a;}

	Integer b;
	public Integer getFishType() { return this.b;}  // 鱼的类型
	public void setFishType(Integer b) { this.b = b;}

	Integer c;
	public Integer getLineId() { return this.c;}  // 鱼的移动线路
	public void setLineId(Integer c) { this.c = c;}

	Integer d;
	public Integer getLifeTime() { return this.d;}  // 剩余生存时间(秒)
	public void setLifeTime(Integer d) { this.d = d;}

	Integer e;
	public Integer getFishIndex() { return this.e;}  // 鱼的序号
	public void setFishIndex(Integer e) { this.e = e;}

	public static SummoneSync parse(String data) {
		 return JsonUtil.JsonToObject(data, SummoneSync.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
