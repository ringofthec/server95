package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_ChangePhoneRe extends JsonPacket {
	public int getProtocolId() { return 104; }
	Integer a;
	public Integer getRetCode() { return this.a;}  // 0 成功  1 切换码已过期  2 切换码错误
	public void setRetCode(Integer a) { this.a = a;}

	public static GDL_G2C_ChangePhoneRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_ChangePhoneRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
