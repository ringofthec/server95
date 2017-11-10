package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_OpenEggSync extends JsonPacket {
	public int getProtocolId() { return 401; }
	Integer a;
	public Integer getEggid() { return this.a;}  // 
	public void setEggid(Integer a) { this.a = a;}

	Long b;
	public Long getNum() { return this.b;}  // 
	public void setNum(Long b) { this.b = b;}

	public static GDL_G2C_OpenEggSync parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_OpenEggSync.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
