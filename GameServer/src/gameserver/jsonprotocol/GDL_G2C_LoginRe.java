package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_LoginRe extends JsonPacket {
	public int getProtocolId() { return 2; }
	Integer a;
	public Integer getRetCode() { return this.a;}  // 返回码, 0表示成功登陆，1表示玩家需要创建角色，2账号验证失败, 3 账号被封禁 4 账号已登录，请稍后再试 99 其他错误
	public void setRetCode(Integer a) { this.a = a;}

	Long b;
	public Long getPlayerId() { return this.b;}  // 玩家id
	public void setPlayerId(Long b) { this.b = b;}

	String c;
	public String getUuid() { return this.c;}  // 服务器处理过的玩家id，创建角色的时候，要再发上来
	public void setUuid(String c) { this.c = c;}

	String d;
	public String getName() { return this.d;}  // 随机名字，只有在retCode是1的时候有效
	public void setName(String d) { this.d = d;}

	public static GDL_G2C_LoginRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_LoginRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
