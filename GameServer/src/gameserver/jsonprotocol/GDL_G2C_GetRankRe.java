package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_GetRankRe extends JsonPacket {
	public int getProtocolId() { return 41; }
	Integer a;
	public Integer getType() { return this.a;}  // 0 财富榜 1 等级榜 2 人气榜
	public void setType(Integer a) { this.a = a;}

	Integer b;
	public Integer getMode() { return this.b;}  // 0 全部排行榜  1 各榜第一名
	public void setMode(Integer b) { this.b = b;}

	List< RankPlayerInfo > c = new ArrayList< RankPlayerInfo >();   // 列表
	public List< RankPlayerInfo > getRank() { return this.c;}
	public void setRank(List< RankPlayerInfo > c) { this.c = c;}

	RankPlayerInfo d;   // 自己的信息
	public RankPlayerInfo getSelf() { return this.d;}
	public void setSelf(RankPlayerInfo d) { this.d = d;}

	public static GDL_G2C_GetRankRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_GetRankRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
