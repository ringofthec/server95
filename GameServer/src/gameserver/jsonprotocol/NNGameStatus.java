package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class NNGameStatus extends JsonPacket {
	List< NNPlayerInfo > a = new ArrayList< NNPlayerInfo >();   // 座位上的玩家信息
	public List< NNPlayerInfo > getSitPlayerInfos() { return this.a;}
	public void setSitPlayerInfos(List< NNPlayerInfo > a) { this.a = a;}

	Integer b;
	public Integer getState() { return this.b;}  // 游戏当前状态 0 等待中 1 下注中 2 开牌中
	public void setState(Integer b) { this.b = b;}

	NNBetPool c;   // 下注池中的钱
	public NNBetPool getBetPools() { return this.c;}
	public void setBetPools(NNBetPool c) { this.c = c;}

	NNOpenResults d;   // 开牌结果、如果在开牌状态
	public NNOpenResults getOpenResults() { return this.d;}
	public void setOpenResults(NNOpenResults d) { this.d = d;}

	Integer e;
	public Integer getNextStartDuration() { return this.e;}  // 距离下次开牌的时长（ms）
	public void setNextStartDuration(Integer e) { this.e = e;}

	NNPlayerInfo f;   // 庄家
	public NNPlayerInfo getBanker() { return this.f;}
	public void setBanker(NNPlayerInfo f) { this.f = f;}

	Long g;
	public Long getBankerCoin() { return this.g;}  // 做庄的钱数
	public void setBankerCoin(Long g) { this.g = g;}

	public static NNGameStatus parse(String data) {
		 return JsonUtil.JsonToObject(data, NNGameStatus.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
