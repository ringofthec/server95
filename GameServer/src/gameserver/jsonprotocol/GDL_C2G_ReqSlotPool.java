package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_ReqSlotPool extends JsonPacket {
	public int getProtocolId() { return 96; }
	Integer a;
	public Integer getGameId() { return this.a;}  // 
	public void setGameId(Integer a) { this.a = a;}

	Integer b;
	public Integer getLevelId() { return this.b;}  // 
	public void setLevelId(Integer b) { this.b = b;}

	public static GDL_C2G_ReqSlotPool parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_ReqSlotPool.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
