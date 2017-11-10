package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class BJLPlayerInfo extends JsonPacket {
	Long a;
	public Long getPlayerId() { return this.a;}  // 
	public void setPlayerId(Long a) { this.a = a;}

	Integer b;
	public Integer getPos() { return this.b;}  // 座位id
	public void setPos(Integer b) { this.b = b;}

	String c;
	public String getName() { return this.c;}  // 
	public void setName(String c) { this.c = c;}

	String d;
	public String getHeadUrl() { return this.d;}  // 
	public void setHeadUrl(String d) { this.d = d;}

	Long e;
	public Long getCoin() { return this.e;}  // 
	public void setCoin(Long e) { this.e = e;}

	Integer f;
	public Integer getViplevel() { return this.f;}  // 
	public void setViplevel(Integer f) { this.f = f;}

	Long g;
	public Long getWinCoin() { return this.g;}  // 总赢取 用正负表示输赢
	public void setWinCoin(Long g) { this.g = g;}

	BJLBetPool h;   // 
	public BJLBetPool getBetcoin() { return this.h;}
	public void setBetcoin(BJLBetPool h) { this.h = h;}

	public static BJLPlayerInfo parse(String data) {
		 return JsonUtil.JsonToObject(data, BJLPlayerInfo.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
