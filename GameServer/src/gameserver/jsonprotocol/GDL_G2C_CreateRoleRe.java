package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_CreateRoleRe extends JsonPacket {
	public int getProtocolId() { return 4; }
	Integer a;
	public Integer getRetCode() { return this.a;}  // 返回码, 0表示成功，1表示名字不合法，2表示名字重复 3 其他错误
	public void setRetCode(Integer a) { this.a = a;}

	Long b;
	public Long getPlayerId() { return this.b;}  // 玩家id
	public void setPlayerId(Long b) { this.b = b;}

	public static GDL_G2C_CreateRoleRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_CreateRoleRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
