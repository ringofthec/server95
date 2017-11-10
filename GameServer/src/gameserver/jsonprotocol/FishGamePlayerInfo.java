package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class FishGamePlayerInfo extends JsonPacket {
	Long a;
	public Long getPlayerId() { return this.a;}  // 
	public void setPlayerId(Long a) { this.a = a;}

	Integer b;
	public Integer getPos() { return this.b;}  // 座位id
	public void setPos(Integer b) { this.b = b;}

	Integer c;
	public Integer getBullet() { return this.c;}  // 子弹类型
	public void setBullet(Integer c) { this.c = c;}

	Integer d;
	public Integer getBattery() { return this.d;}  // 炮台类型
	public void setBattery(Integer d) { this.d = d;}

	String e;
	public String getName() { return this.e;}  // 
	public void setName(String e) { this.e = e;}

	String f;
	public String getHeadUrl() { return this.f;}  // 
	public void setHeadUrl(String f) { this.f = f;}

	Long g;
	public Long getCoin() { return this.g;}  // 
	public void setCoin(Long g) { this.g = g;}

	Long h;
	public Long getGold() { return this.h;}  // 
	public void setGold(Long h) { this.h = h;}

	Integer i;
	public Integer getViplevel() { return this.i;}  // 
	public void setViplevel(Integer i) { this.i = i;}

	public static FishGamePlayerInfo parse(String data) {
		 return JsonUtil.JsonToObject(data, FishGamePlayerInfo.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
