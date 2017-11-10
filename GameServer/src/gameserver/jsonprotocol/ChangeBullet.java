package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class ChangeBullet extends JsonPacket {
	public int getProtocolId() { return 237; }
	Integer a;
	public Integer getType() { return this.a;}  // 类型
	public void setType(Integer a) { this.a = a;}

	public static ChangeBullet parse(String data) {
		 return JsonUtil.JsonToObject(data, ChangeBullet.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
