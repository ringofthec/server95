package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class BJLPlayerOpenResults extends JsonPacket {
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家id
	public void setPlayerId(Long a) { this.a = a;}

	BJLBetPool b;   // 每个池子的输赢
	public BJLBetPool getBetPools() { return this.b;}
	public void setBetPools(BJLBetPool b) { this.b = b;}

	Long c;
	public Long getCoin() { return this.c;}  // 玩家金币
	public void setCoin(Long c) { this.c = c;}

	Long d;
	public Long getPool_coin() { return this.d;}  // 从奖池中额外获得的金币数
	public void setPool_coin(Long d) { this.d = d;}

	public static BJLPlayerOpenResults parse(String data) {
		 return JsonUtil.JsonToObject(data, BJLPlayerOpenResults.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
