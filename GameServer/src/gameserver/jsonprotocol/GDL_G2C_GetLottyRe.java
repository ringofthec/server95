package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_GetLottyRe extends JsonPacket {
	public int getProtocolId() { return 81; }
	Integer a;
	public Integer getType() { return this.a;}  // 0 金币  1 金砖
	public void setType(Integer a) { this.a = a;}

	Integer b;
	public Integer getId() { return this.b;}  // 中奖表中的 id
	public void setId(Integer b) { this.b = b;}

	public static GDL_G2C_GetLottyRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_GetLottyRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
