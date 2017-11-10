package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_ReqFriutActiveInfo extends JsonPacket {
	public int getProtocolId() { return 123; }
	Integer a;
	public Integer getGameId() { return this.a;}  // 1 水果  2 金砖
	public void setGameId(Integer a) { this.a = a;}

	Integer b;
	public Integer getLevel() { return this.b;}  // 场次
	public void setLevel(Integer b) { this.b = b;}

	public static GDL_C2G_ReqFriutActiveInfo parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_ReqFriutActiveInfo.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
