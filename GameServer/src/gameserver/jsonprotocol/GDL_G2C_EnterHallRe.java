package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_EnterHallRe extends JsonPacket {
	public int getProtocolId() { return 6; }
	Integer a;
	public Integer getIsDayFirstLogin() { return this.a;}  // 1是今天的第一次登陆，0表示不是
	public void setIsDayFirstLogin(Integer a) { this.a = a;}

	Long b;
	public Long getServerTimeStamp() { return this.b;}  // 服务器当前时间戳
	public void setServerTimeStamp(Long b) { this.b = b;}

	Integer c;
	public Integer getIsRotaryRewardGet() { return this.c;}  // 0 没有领取转轮奖励，1 已结领取过了
	public void setIsRotaryRewardGet(Integer c) { this.c = c;}

	Integer d;
	public Integer getRewardId() { return this.d;}  // 玩家领取到的奖励编号
	public void setRewardId(Integer d) { this.d = d;}

	Integer e;
	public Integer getNoticeVersion() { return this.e;}  // 公告编号，如果是-1，表示强制显示，其他的话就和本地版本号比对，不同的话，就请求公告
	public void setNoticeVersion(Integer e) { this.e = e;}

	List< RewardItem > f = new ArrayList< RewardItem >();   // 转轮奖励物品列表
	public List< RewardItem > getRotaryRewardList() { return this.f;}
	public void setRotaryRewardList(List< RewardItem > f) { this.f = f;}

	List< Integer > g = new ArrayList< Integer >();   // 玩家可以领取的连续登录奖励id，对应的奖励要在GCLoginReward表中找
	public List< Integer > getContinueLoginRewards() { return this.g;}
	public void setContinueLoginRewards(List< Integer > g) { this.g = g;}

	String h;
	public String getHeadupdateurl() { return this.h;}  // 玩家头像更新使用的url
	public void setHeadupdateurl(String h) { this.h = h;}

	String i;
	public String getHeadrequrl() { return this.i;}  // 玩家头像获取时使用的url
	public void setHeadrequrl(String i) { this.i = i;}

	public static GDL_G2C_EnterHallRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_EnterHallRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
