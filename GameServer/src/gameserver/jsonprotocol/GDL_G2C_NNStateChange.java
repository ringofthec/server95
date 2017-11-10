package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_NNStateChange extends JsonPacket {
	public int getProtocolId() { return 331; }
	Integer a;
	public Integer getState() { return this.a;}  // 游戏当前状态 0 等待中 1 下注中 2 开牌中
	public void setState(Integer a) { this.a = a;}

	NNOpenResults b;   // 开牌结果、如果在开牌状态
	public NNOpenResults getOpenResults() { return this.b;}
	public void setOpenResults(NNOpenResults b) { this.b = b;}

	Integer c;
	public Integer getNextStartDuration() { return this.c;}  // 距离下次切换状态的时长（ms）
	public void setNextStartDuration(Integer c) { this.c = c;}

	NNPlayerInfo d;   // 庄家id 0是系统庄  state为1时有效，下注开始的那一刻，庄家就决定了不能再变了
	public NNPlayerInfo getBanker() { return this.d;}
	public void setBanker(NNPlayerInfo d) { this.d = d;}

	Long e;
	public Long getBankerCoin() { return this.e;}  // 做庄的钱数
	public void setBankerCoin(Long e) { this.e = e;}

	List< Long > f = new ArrayList< Long >();   // 座位上的玩家id
	public List< Long > getSitPlayersId() { return this.f;}
	public void setSitPlayersId(List< Long > f) { this.f = f;}

	List< Long > g = new ArrayList< Long >();   // 座位上的玩家的钱 与  sitPlayersId 对应
	public List< Long > getSitPlayersCoin() { return this.g;}
	public void setSitPlayersCoin(List< Long > g) { this.g = g;}

	public static GDL_G2C_NNStateChange parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_NNStateChange.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
