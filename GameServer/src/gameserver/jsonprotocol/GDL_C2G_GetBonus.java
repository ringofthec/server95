package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_GetBonus extends JsonPacket {
	public int getProtocolId() { return 49; }
	Integer a;
	public Integer getId() { return this.a;}  // 红包id
	public void setId(Integer a) { this.a = a;}

	public static GDL_C2G_GetBonus parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_GetBonus.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
