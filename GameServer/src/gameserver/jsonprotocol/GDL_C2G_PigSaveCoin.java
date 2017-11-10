package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_PigSaveCoin extends JsonPacket {
	public int getProtocolId() { return 66; }
	Long a;
	public Long getCount() { return this.a;}  // 要存入的金币数
	public void setCount(Long a) { this.a = a;}

	public static GDL_C2G_PigSaveCoin parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_PigSaveCoin.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
