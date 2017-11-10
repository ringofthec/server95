package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_Fallback extends JsonPacket {
	public int getProtocolId() { return 1004; }
	Integer a;
	public Integer getFuncId() { return this.a;}  // 参数1
	public void setFuncId(Integer a) { this.a = a;}

	String b;
	public String getData() { return this.b;}  // 参数2
	public void setData(String b) { this.b = b;}

	public static GDL_G2C_Fallback parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_Fallback.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
