package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_ExchangeCodeRe extends JsonPacket {
	public int getProtocolId() { return 18; }
	Integer a;
	public Integer getRetCode() { return this.a;}  // 0 成功，1 兑换码不正确，2 你已经领取过本批次的兑换码，不能重复领取
	public void setRetCode(Integer a) { this.a = a;}

	public static GDL_G2C_ExchangeCodeRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_ExchangeCodeRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
