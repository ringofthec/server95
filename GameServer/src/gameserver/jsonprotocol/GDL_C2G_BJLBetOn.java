package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_BJLBetOn extends JsonPacket {
	public int getProtocolId() { return 350; }
	Integer a;
	public Integer getBetId() { return this.a;}  // 下注id
	public void setBetId(Integer a) { this.a = a;}

	Integer b;
	public Integer getPoolIdx() { return this.b;}  // 下注的池子 池子id 0 庄 1 闲 2 庄对 3 闲对 4 和
	public void setPoolIdx(Integer b) { this.b = b;}

	Integer c;
	public Integer getX() { return this.c;}  // 
	public void setX(Integer c) { this.c = c;}

	Integer d;
	public Integer getY() { return this.d;}  // 
	public void setY(Integer d) { this.d = d;}

	public static GDL_C2G_BJLBetOn parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_BJLBetOn.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
