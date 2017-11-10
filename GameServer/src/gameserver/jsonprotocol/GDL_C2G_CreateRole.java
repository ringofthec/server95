package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_CreateRole extends JsonPacket {
	public int getProtocolId() { return 3; }
	String a;
	public String getPlayerName() { return this.a;}  // 玩家名称
	public void setPlayerName(String a) { this.a = a;}

	String b;
	public String getUuid() { return this.b;}  // 服务器处理过的玩家id
	public void setUuid(String b) { this.b = b;}

	Integer c;
	public Integer getChannel() { return this.c;}  // 渠道号
	public void setChannel(Integer c) { this.c = c;}

	Integer d;
	public Integer getType() { return this.d;}  // 0 唯一id登录  1 账号登录
	public void setType(Integer d) { this.d = d;}

	String e;
	public String getPlatform() { return this.e;}  // 平台
	public void setPlatform(String e) { this.e = e;}

	Integer f;
	public Integer getHead() { return this.f;}  // 系统头像id
	public void setHead(Integer f) { this.f = f;}

	Integer g;
	public Integer getSex() { return this.g;}  // 性别 0 男  1女
	public void setSex(Integer g) { this.g = g;}

	String h;
	public String getDev_id() { return this.h;}  // 玩家唯一id
	public void setDev_id(String h) { this.h = h;}

	String i;
	public String getPackageName() { return this.i;}  // 玩家包名
	public void setPackageName(String i) { this.i = i;}

	public static GDL_C2G_CreateRole parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_CreateRole.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
