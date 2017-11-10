package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_ReqOnlinePlayerNumberRe extends JsonPacket {
	public int getProtocolId() { return 55; }
	Integer a;
	public Integer getNum() { return this.a;}  // 
	public void setNum(Integer a) { this.a = a;}

	public static GDL_G2C_ReqOnlinePlayerNumberRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_ReqOnlinePlayerNumberRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
