package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_PlayerLeaveGame extends JsonPacket {
	public int getProtocolId() { return 205; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家id
	public void setPlayerId(Long a) { this.a = a;}

	public static GDL_G2C_PlayerLeaveGame parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_PlayerLeaveGame.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
