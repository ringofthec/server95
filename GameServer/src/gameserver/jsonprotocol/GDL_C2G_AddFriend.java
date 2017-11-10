package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_AddFriend extends JsonPacket {
	public int getProtocolId() { return 30; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 对方的player_id
	public void setPlayerId(Long a) { this.a = a;}

	public static GDL_C2G_AddFriend parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_AddFriend.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
