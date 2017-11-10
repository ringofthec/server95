package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class NNOpenResults extends JsonPacket {
	List< NNCardsStraight > a = new ArrayList< NNCardsStraight >();   // 下注池 牌型
	public List< NNCardsStraight > getBetCS() { return this.a;}
	public void setBetCS(List< NNCardsStraight > a) { this.a = a;}

	NNCardsStraight b;   // 庄家 牌型
	public NNCardsStraight getBankerCS() { return this.b;}
	public void setBankerCS(NNCardsStraight b) { this.b = b;}

	NNPlayerOpenResults c;   // 本人对每个池子的输赢
	public NNPlayerOpenResults getMybetPools() { return this.c;}
	public void setMybetPools(NNPlayerOpenResults c) { this.c = c;}

	NNBetPool d;   // 庄家对每个池子的输赢
	public NNBetPool getBankerBetPools() { return this.d;}
	public void setBankerBetPools(NNBetPool d) { this.d = d;}

	List< NNPlayerOpenResults > e = new ArrayList< NNPlayerOpenResults >();   // 座位上的对每个池子的输赢
	public List< NNPlayerOpenResults > getSitPlayerBetPools() { return this.e;}
	public void setSitPlayerBetPools(List< NNPlayerOpenResults > e) { this.e = e;}

	List< NNPlayerInfo > f = new ArrayList< NNPlayerInfo >();   // 赢的最多的三个
	public List< NNPlayerInfo > getWinners() { return this.f;}
	public void setWinners(List< NNPlayerInfo > f) { this.f = f;}

	NNPlayerInfo g;   // s输的最多的一个
	public NNPlayerInfo getLoser() { return this.g;}
	public void setLoser(NNPlayerInfo g) { this.g = g;}

	NNBetResult h;   // 池子输赢
	public NNBetResult getPoolwin() { return this.h;}
	public void setPoolwin(NNBetResult h) { this.h = h;}

	public static NNOpenResults parse(String data) {
		 return JsonUtil.JsonToObject(data, NNOpenResults.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
