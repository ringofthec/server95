package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_GetPlayerInfoRe extends JsonPacket {
	public int getProtocolId() { return 11; }
	Integer a;
	public Integer getFunc() { return this.a;}  // 功能
	public void setFunc(Integer a) { this.a = a;}

	Long b;
	public Long getPlayerId() { return this.b;}  // 玩家id
	public void setPlayerId(Long b) { this.b = b;}

	Integer c;
	public Integer getLevel() { return this.c;}  // 等级
	public void setLevel(Integer c) { this.c = c;}

	Integer d;
	public Integer getVipLevel() { return this.d;}  // vip等级
	public void setVipLevel(Integer d) { this.d = d;}

	String e;
	public String getName() { return this.e;}  // 名字
	public void setName(String e) { this.e = e;}

	String f;
	public String getUrl() { return this.f;}  // 头像url
	public void setUrl(String f) { this.f = f;}

	Integer g;
	public Integer getSex() { return this.g;}  // 性别 0 男  1女
	public void setSex(Integer g) { this.g = g;}

	Integer h;
	public Integer getGamepos() { return this.h;}  // 玩家在游戏中的位置
	public void setGamepos(Integer h) { this.h = h;}

	Long i;
	public Long getTotal() { return this.i;}  // 总赢取
	public void setTotal(Long i) { this.i = i;}

	Long j;
	public Long getMoney() { return this.j;}  // 总金币数
	public void setMoney(Long j) { this.j = j;}

	Long k;
	public Long getGold() { return this.k;}  // 总金砖数
	public void setGold(Long k) { this.k = k;}

	Long l;
	public Long getLiked() { return this.l;}  // 总人气
	public void setLiked(Long l) { this.l = l;}

	Long m;
	public Long getPigcoin() { return this.m;}  // 金猪储蓄数量
	public void setPigcoin(Long m) { this.m = m;}

	String n;
	public String getSign() { return this.n;}  // 个性签名
	public void setSign(String n) { this.n = n;}

	List< GiftShowInfo > o = new ArrayList< GiftShowInfo >();   // 礼物展示列表
	public List< GiftShowInfo > getShowGiftList() { return this.o;}
	public void setShowGiftList(List< GiftShowInfo > o) { this.o = o;}

	Integer p;
	public Integer getIsJoin() { return this.p;}  // 0 没参加金砖比赛 > 0 表示参加了，并且代表对应的等级
	public void setIsJoin(Integer p) { this.p = p;}

	public static GDL_G2C_GetPlayerInfoRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_GetPlayerInfoRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
