package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_NNSitDown extends JsonPacket {
	public int getProtocolId() { return 326; }
	Integer a;
	public Integer getSit() { return this.a;}  // 坐下的位置
	public void setSit(Integer a) { this.a = a;}

	public static GDL_C2G_NNSitDown parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_NNSitDown.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
