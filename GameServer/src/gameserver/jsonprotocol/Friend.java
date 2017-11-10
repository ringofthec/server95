package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class Friend extends JsonPacket {
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家id
	public void setPlayerId(Long a) { this.a = a;}

	String b;
	public String getName() { return this.b;}  // 玩家名称
	public void setName(String b) { this.b = b;}

	String c;
	public String getHead() { return this.c;}  // 头像url
	public void setHead(String c) { this.c = c;}

	Integer d;
	public Integer getLevel() { return this.d;}  // 等级
	public void setLevel(Integer d) { this.d = d;}

	Integer e;
	public Integer getViplevel() { return this.e;}  // vip等级
	public void setViplevel(Integer e) { this.e = e;}

	Long f;
	public Long getMoney() { return this.f;}  // 金币数
	public void setMoney(Long f) { this.f = f;}

	Integer g;
	public Integer getSex() { return this.g;}  // 性别
	public void setSex(Integer g) { this.g = g;}

	Integer h;
	public Integer getOnline() { return this.h;}  // 0 离线 1 在线
	public void setOnline(Integer h) { this.h = h;}

	public static Friend parse(String data) {
		 return JsonUtil.JsonToObject(data, Friend.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
