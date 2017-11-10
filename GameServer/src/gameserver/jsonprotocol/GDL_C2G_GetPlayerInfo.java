package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_GetPlayerInfo extends JsonPacket {
	public int getProtocolId() { return 10; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家id
	public void setPlayerId(Long a) { this.a = a;}

	Integer b;
	public Integer getFunc() { return this.b;}  // 功能, 服务器不解释，完全回传
	public void setFunc(Integer b) { this.b = b;}

	Integer c;
	public Integer getMode() { return this.c;}  // 0 完整的GetPlayerInfoRe回包，1 只是回复玩家姓名
	public void setMode(Integer c) { this.c = c;}

	public static GDL_C2G_GetPlayerInfo parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_GetPlayerInfo.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
