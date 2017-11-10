package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_GuessRe extends JsonPacket {
	public int getProtocolId() { return 95; }
	Integer a;
	public Integer getRate() { return this.a;}  // 倍数, 0表示没中，失败了
	public void setRate(Integer a) { this.a = a;}

	public static GDL_G2C_GuessRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_GuessRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
