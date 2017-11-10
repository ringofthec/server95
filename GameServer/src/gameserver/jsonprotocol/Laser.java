package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class Laser extends JsonPacket {
	public int getProtocolId() { return 259; }
	Double a;
	public Double getAngle() { return this.a;}  // 角度
	public void setAngle(Double a) { this.a = a;}

	List< Integer > b = new ArrayList< Integer >();   // 鱼的idx列表
	public List< Integer > getFishIndex() { return this.b;}
	public void setFishIndex(List< Integer > b) { this.b = b;}

	public static Laser parse(String data) {
		 return JsonUtil.JsonToObject(data, Laser.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
