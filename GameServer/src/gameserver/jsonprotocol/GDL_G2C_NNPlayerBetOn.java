package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_NNPlayerBetOn extends JsonPacket {
	public int getProtocolId() { return 330; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家Id
	public void setPlayerId(Long a) { this.a = a;}

	Integer b;
	public Integer getBetId() { return this.b;}  // 下注id
	public void setBetId(Integer b) { this.b = b;}

	Integer c;
	public Integer getPoolIdx() { return this.c;}  // 下注的池子序号 0,1,2,3
	public void setPoolIdx(Integer c) { this.c = c;}

	NNBetPool d;   // 当前下注池中的钱
	public NNBetPool getBetPools() { return this.d;}
	public void setBetPools(NNBetPool d) { this.d = d;}

	public static GDL_G2C_NNPlayerBetOn parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_NNPlayerBetOn.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
