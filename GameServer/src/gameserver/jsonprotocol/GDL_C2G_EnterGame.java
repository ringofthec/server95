package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_EnterGame extends JsonPacket {
	public int getProtocolId() { return 200; }
	Integer a;
	public Integer getGameId() { return this.a;}  // 游戏id 1 水果slot  2 金砖 slot  10 捕鱼
	public void setGameId(Integer a) { this.a = a;}

	Integer b;
	public Integer getGameLevel() { return this.b;}  // 游戏等级
	public void setGameLevel(Integer b) { this.b = b;}

	public static GDL_C2G_EnterGame parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_EnterGame.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
