package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_BJLZXChange extends JsonPacket {
	public int getProtocolId() { return 372; }
	Long a;
	public Long getPlayer_z() { return this.a;}  // 庄咪牌玩家 0 表示没有
	public void setPlayer_z(Long a) { this.a = a;}

	Long b;
	public Long getPlayer_x() { return this.b;}  // 闲咪牌玩家 0 表示没有
	public void setPlayer_x(Long b) { this.b = b;}

	public static GDL_G2C_BJLZXChange parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_BJLZXChange.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
