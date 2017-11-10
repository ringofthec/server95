package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class BJLGameStatus extends JsonPacket {
	List< BJLPlayerInfo > a = new ArrayList< BJLPlayerInfo >();   // 座位上的玩家信息
	public List< BJLPlayerInfo > getSitPlayerInfos() { return this.a;}
	public void setSitPlayerInfos(List< BJLPlayerInfo > a) { this.a = a;}

	Integer b;
	public Integer getState() { return this.b;}  // 游戏当前状态
	public void setState(Integer b) { this.b = b;}

	BJLBetPool c;   // 下注池中的钱
	public BJLBetPool getBetPools() { return this.c;}
	public void setBetPools(BJLBetPool c) { this.c = c;}

	BJLOpenResults d;   // 开牌结果、如果在开牌状态
	public BJLOpenResults getOpenResults() { return this.d;}
	public void setOpenResults(BJLOpenResults d) { this.d = d;}

	Integer e;
	public Integer getNextStartDuration() { return this.e;}  // 距离下次开牌的时长（ms）
	public void setNextStartDuration(Integer e) { this.e = e;}

	BJLPlayerInfo f;   // 庄家
	public BJLPlayerInfo getBanker() { return this.f;}
	public void setBanker(BJLPlayerInfo f) { this.f = f;}

	Long g;
	public Long getBankerCoin() { return this.g;}  // 做庄的钱数
	public void setBankerCoin(Long g) { this.g = g;}

	Integer h;
	public Integer getCardsNum() { return this.h;}  // 剩余的牌数
	public void setCardsNum(Integer h) { this.h = h;}

	Long i;
	public Long getPlayerId() { return this.i;}  // 切牌玩家 0 表示没有
	public void setPlayerId(Long i) { this.i = i;}

	public static BJLGameStatus parse(String data) {
		 return JsonUtil.JsonToObject(data, BJLGameStatus.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
