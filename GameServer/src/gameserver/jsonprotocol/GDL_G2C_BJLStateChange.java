package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_BJLStateChange extends JsonPacket {
	public int getProtocolId() { return 361; }
	Integer a;
	public Integer getState() { return this.a;}  // 游戏当前状态 0 等待中 1 下注中 2 闲家咪牌  3 庄家米牌 4 闲家(补牌)咪牌 5 庄家(补牌)咪牌 
	public void setState(Integer a) { this.a = a;}

	BJLOpenResults b;   // 开牌结果、如果在开牌状态
	public BJLOpenResults getOpenResults() { return this.b;}
	public void setOpenResults(BJLOpenResults b) { this.b = b;}

	Integer c;
	public Integer getNextStartDuration() { return this.c;}  // 距离下次切换状态的时长（ms）
	public void setNextStartDuration(Integer c) { this.c = c;}

	BJLPlayerInfo d;   // 庄家id 0是系统庄  state为1时有效，下注开始的那一刻，庄家就决定了不能再变了
	public BJLPlayerInfo getBanker() { return this.d;}
	public void setBanker(BJLPlayerInfo d) { this.d = d;}

	Long e;
	public Long getBankerCoin() { return this.e;}  // 做庄的钱数
	public void setBankerCoin(Long e) { this.e = e;}

	List< Long > f = new ArrayList< Long >();   // 座位上的玩家id
	public List< Long > getSitPlayersId() { return this.f;}
	public void setSitPlayersId(List< Long > f) { this.f = f;}

	List< Long > g = new ArrayList< Long >();   // 座位上的玩家的钱 与  sitPlayersId 对应
	public List< Long > getSitPlayersCoin() { return this.g;}
	public void setSitPlayersCoin(List< Long > g) { this.g = g;}

	Integer h;
	public Integer getCardsNum() { return this.h;}  // 剩余的牌数
	public void setCardsNum(Integer h) { this.h = h;}

	Long i;
	public Long getPlayerId() { return this.i;}  // 切牌玩家id 0的话表示系统切牌
	public void setPlayerId(Long i) { this.i = i;}

	public static GDL_G2C_BJLStateChange parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_BJLStateChange.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
