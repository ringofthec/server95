package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_BJLQieCardEndSync extends JsonPacket {
	public int getProtocolId() { return 377; }
	Integer a;
	public Integer getData1() { return this.a;}  // 
	public void setData1(Integer a) { this.a = a;}

	Integer b;
	public Integer getData2() { return this.b;}  // 
	public void setData2(Integer b) { this.b = b;}

	public static GDL_G2C_BJLQieCardEndSync parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_BJLQieCardEndSync.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
