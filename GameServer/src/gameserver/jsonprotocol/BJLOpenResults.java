package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class BJLOpenResults extends JsonPacket {
	Integer a;
	public Integer getShuffle() { return this.a;}  // 洗牌 0 不洗牌 1 洗牌  百家乐在牌太少时会洗牌，这个可能有动画，以这个值作为动画是否播放的依据
	public void setShuffle(Integer a) { this.a = a;}

	BJLCardsStraight b;   // 庄家 牌型
	public BJLCardsStraight getBankerCS() { return this.b;}
	public void setBankerCS(BJLCardsStraight b) { this.b = b;}

	BJLCardsStraight c;   // 闲家 牌型
	public BJLCardsStraight getPlayerCS() { return this.c;}
	public void setPlayerCS(BJLCardsStraight c) { this.c = c;}

	BJLPlayerOpenResults d;   // 本人对每个池子的输赢
	public BJLPlayerOpenResults getMybetPools() { return this.d;}
	public void setMybetPools(BJLPlayerOpenResults d) { this.d = d;}

	BJLBetPool e;   // 庄家对每个池子的输赢
	public BJLBetPool getBankerBetPools() { return this.e;}
	public void setBankerBetPools(BJLBetPool e) { this.e = e;}

	List< BJLPlayerOpenResults > f = new ArrayList< BJLPlayerOpenResults >();   // 座位上的对每个池子的输赢
	public List< BJLPlayerOpenResults > getSitPlayerBetPools() { return this.f;}
	public void setSitPlayerBetPools(List< BJLPlayerOpenResults > f) { this.f = f;}

	List< BJLPlayerInfo > g = new ArrayList< BJLPlayerInfo >();   // 赢的最多的三个
	public List< BJLPlayerInfo > getWinners() { return this.g;}
	public void setWinners(List< BJLPlayerInfo > g) { this.g = g;}

	BJLPlayerInfo h;   // s输的最多的一个
	public BJLPlayerInfo getLoser() { return this.h;}
	public void setLoser(BJLPlayerInfo h) { this.h = h;}

	BJLBetResult i;   // 池子输赢
	public BJLBetResult getPoolwin() { return this.i;}
	public void setPoolwin(BJLBetResult i) { this.i = i;}

	Long j;
	public Long getPlayer_z() { return this.j;}  // 庄咪牌玩家 0 表示没有
	public void setPlayer_z(Long j) { this.j = j;}

	Long k;
	public Long getPlayer_x() { return this.k;}  // 闲咪牌玩家 0 表示没有
	public void setPlayer_x(Long k) { this.k = k;}

	public static BJLOpenResults parse(String data) {
		 return JsonUtil.JsonToObject(data, BJLOpenResults.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
