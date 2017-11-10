package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_Bonus extends JsonPacket {
	public int getProtocolId() { return 48; }
	String a;
	public String getSay() { return this.a;}  // 宣言
	public void setSay(String a) { this.a = a;}

	Integer b;
	public Integer getId() { return this.b;}  // 红包id
	public void setId(Integer b) { this.b = b;}

	public static GDL_G2C_Bonus parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_Bonus.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
