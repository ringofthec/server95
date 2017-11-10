package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_CashPrizeRe extends JsonPacket {
	public int getProtocolId() { return 86; }
	Integer a;
	public Integer getRetCode() { return this.a;}  // 0 成功 1 失败
	public void setRetCode(Integer a) { this.a = a;}

	public static GDL_G2C_CashPrizeRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_CashPrizeRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
