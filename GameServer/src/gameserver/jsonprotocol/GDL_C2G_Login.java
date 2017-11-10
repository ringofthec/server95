package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_Login extends JsonPacket {
	public int getProtocolId() { return 1; }
	String a;
	public String getUuid() { return this.a;}  // 玩家唯一id
	public void setUuid(String a) { this.a = a;}

	Integer b;
	public Integer getChannel() { return this.b;}  // 渠道号
	public void setChannel(Integer b) { this.b = b;}

	Integer c;
	public Integer getType() { return this.c;}  // 0 唯一id登录  1 账号登录
	public void setType(Integer c) { this.c = c;}

	String d;
	public String getUid() { return this.d;}  // 第三方账号
	public void setUid(String d) { this.d = d;}

	String e;
	public String getSession() { return this.e;}  // 第三方session
	public void setSession(String e) { this.e = e;}

	String f;
	public String getPlatform() { return this.f;}  // 平台
	public void setPlatform(String f) { this.f = f;}

	String g;
	public String getVersion() { return this.g;}  // 版本号
	public void setVersion(String g) { this.g = g;}

	public static GDL_C2G_Login parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_Login.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
