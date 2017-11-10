package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_Liked extends JsonPacket {
	public int getProtocolId() { return 16; }
	Long a;
	public Long getSrcPlayerId() { return this.a;}  // 点赞的玩家id
	public void setSrcPlayerId(Long a) { this.a = a;}

	String b;
	public String getSrcPlayerName() { return this.b;}  // 点赞的玩家名字
	public void setSrcPlayerName(String b) { this.b = b;}

	public static GDL_G2C_Liked parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_Liked.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
