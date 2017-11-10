package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class RankPlayerInfo extends JsonPacket {
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家id
	public void setPlayerId(Long a) { this.a = a;}

	String b;
	public String getPlayerName() { return this.b;}  // 玩家名字
	public void setPlayerName(String b) { this.b = b;}

	String c;
	public String getHeadUrl() { return this.c;}  // 玩家头像url
	public void setHeadUrl(String c) { this.c = c;}

	Integer d;
	public Integer getSex() { return this.d;}  // 性别
	public void setSex(Integer d) { this.d = d;}

	Integer e;
	public Integer getLevel() { return this.e;}  // 玩家等级
	public void setLevel(Integer e) { this.e = e;}

	Integer f;
	public Integer getVipLevel() { return this.f;}  // 玩家vip等级
	public void setVipLevel(Integer f) { this.f = f;}

	Long g;
	public Long getMoney() { return this.g;}  // 玩家金币数
	public void setMoney(Long g) { this.g = g;}

	Long h;
	public Long getExp() { return this.h;}  // 总赢取
	public void setExp(Long h) { this.h = h;}

	Long i;
	public Long getLiked() { return this.i;}  // 玩家人气
	public void setLiked(Long i) { this.i = i;}

	Integer j;
	public Integer getDiff() { return this.j;}  // 名次变化
	public void setDiff(Integer j) { this.j = j;}

	Integer k;
	public Integer getOrder() { return this.k;}  // 排名
	public void setOrder(Integer k) { this.k = k;}

	public static RankPlayerInfo parse(String data) {
		 return JsonUtil.JsonToObject(data, RankPlayerInfo.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
