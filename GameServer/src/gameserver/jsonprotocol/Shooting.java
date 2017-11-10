package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class Shooting extends JsonPacket {
	public int getProtocolId() { return 241; }
	Double a;
	public Double getAngle() { return this.a;}  // 角度
	public void setAngle(Double a) { this.a = a;}

	public static Shooting parse(String data) {
		 return JsonUtil.JsonToObject(data, Shooting.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
