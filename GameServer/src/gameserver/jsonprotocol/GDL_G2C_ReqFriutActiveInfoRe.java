package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_ReqFriutActiveInfoRe extends JsonPacket {
	public int getProtocolId() { return 124; }
	Integer a;
	public Integer getRetCode() { return this.a;}  // 0 没开始，下面都没值， 1开始了，下面有值 2 等待开始(金砖slots)
	public void setRetCode(Integer a) { this.a = a;}

	Integer b;
	public Integer getGameId() { return this.b;}  // 1 水果  2 金砖
	public void setGameId(Integer b) { this.b = b;}

	Integer c;
	public Integer getLevel() { return this.c;}  // 场次
	public void setLevel(Integer c) { this.c = c;}

	Integer d;
	public Integer getIsJoin() { return this.d;}  // 0 没参加金砖比赛 > 0 表示参加了，并且代表对应的等级
	public void setIsJoin(Integer d) { this.d = d;}

	List< SoltsActivePlayerInfo > e = new ArrayList< SoltsActivePlayerInfo >();   // 玩家信息
	public List< SoltsActivePlayerInfo > getPlayerInfos() { return this.e;}
	public void setPlayerInfos(List< SoltsActivePlayerInfo > e) { this.e = e;}

	SoltsActivePlayerInfo f;   // 自己的信息
	public SoltsActivePlayerInfo getSelf() { return this.f;}
	public void setSelf(SoltsActivePlayerInfo f) { this.f = f;}

	Integer g;
	public Integer getLefttime() { return this.g;}  // 开场倒计时，只有金砖slots才有意义
	public void setLefttime(Integer g) { this.g = g;}

	List< GoldActiveReward > h = new ArrayList< GoldActiveReward >();   // 金砖slots奖励
	public List< GoldActiveReward > getGoldSlotsRewards() { return this.h;}
	public void setGoldSlotsRewards(List< GoldActiveReward > h) { this.h = h;}

	public static GDL_G2C_ReqFriutActiveInfoRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_ReqFriutActiveInfoRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
