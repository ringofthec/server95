package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class UnlockBullet extends JsonPacket {
	public int getProtocolId() { return 235; }
	public static UnlockBullet parse(String data) {
		 return JsonUtil.JsonToObject(data, UnlockBullet.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
