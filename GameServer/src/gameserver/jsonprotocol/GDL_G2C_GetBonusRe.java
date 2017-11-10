package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_GetBonusRe extends JsonPacket {
	public int getProtocolId() { return 280; }
	Integer a;
	public Integer getRetCode() { return this.a;}  // 0 成功 1 失败
	public void setRetCode(Integer a) { this.a = a;}

	Integer b;
	public Integer getMoney() { return this.b;}  // 
	public void setMoney(Integer b) { this.b = b;}

	public static GDL_G2C_GetBonusRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_GetBonusRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
