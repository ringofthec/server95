package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_BJLPlayerBetOn extends JsonPacket {
	public int getProtocolId() { return 360; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家Id
	public void setPlayerId(Long a) { this.a = a;}

	Integer b;
	public Integer getBetId() { return this.b;}  // 下注id
	public void setBetId(Integer b) { this.b = b;}

	Integer c;
	public Integer getPoolIdx() { return this.c;}  // 下注的池子  池子id 0 庄 1 闲 2 庄对 3 闲对 4 和
	public void setPoolIdx(Integer c) { this.c = c;}

	BJLBetPool d;   // 当前下注池中的钱
	public BJLBetPool getBetPools() { return this.d;}
	public void setBetPools(BJLBetPool d) { this.d = d;}

	Integer e;
	public Integer getX() { return this.e;}  // 
	public void setX(Integer e) { this.e = e;}

	Integer f;
	public Integer getY() { return this.f;}  // 
	public void setY(Integer f) { this.f = f;}

	Long g;
	public Long getCoin() { return this.g;}  // 玩家身上的钱
	public void setCoin(Long g) { this.g = g;}

	public static GDL_G2C_BJLPlayerBetOn parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_BJLPlayerBetOn.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
