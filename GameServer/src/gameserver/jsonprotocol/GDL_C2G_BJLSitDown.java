package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_BJLSitDown extends JsonPacket {
	public int getProtocolId() { return 356; }
	Integer a;
	public Integer getSit() { return this.a;}  // 坐下的位置
	public void setSit(Integer a) { this.a = a;}

	public static GDL_C2G_BJLSitDown parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_BJLSitDown.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
