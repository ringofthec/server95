package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class SlotGamePlayerInfo extends JsonPacket {
	Long a;
	public Long getPlayerId() { return this.a;}  // 
	public void setPlayerId(Long a) { this.a = a;}

	String b;
	public String getName() { return this.b;}  // 
	public void setName(String b) { this.b = b;}

	String c;
	public String getHeadUrl() { return this.c;}  // 
	public void setHeadUrl(String c) { this.c = c;}

	Long d;
	public Long getCoin() { return this.d;}  // 
	public void setCoin(Long d) { this.d = d;}

	Integer e;
	public Integer getViplevel() { return this.e;}  // 
	public void setViplevel(Integer e) { this.e = e;}

	Integer f;
	public Integer getGiftid() { return this.f;}  // 礼物id
	public void setGiftid(Integer f) { this.f = f;}

	public static SlotGamePlayerInfo parse(String data) {
		 return JsonUtil.JsonToObject(data, SlotGamePlayerInfo.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
