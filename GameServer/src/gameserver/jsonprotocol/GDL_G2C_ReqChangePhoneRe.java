package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_ReqChangePhoneRe extends JsonPacket {
	public int getProtocolId() { return 102; }
	String a;
	public String getCode() { return this.a;}  // 切换码
	public void setCode(String a) { this.a = a;}

	Integer b;
	public Integer getLifeTimeSecond() { return this.b;}  // 切换码剩余生效时间，单位是秒
	public void setLifeTimeSecond(Integer b) { this.b = b;}

	public static GDL_G2C_ReqChangePhoneRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_ReqChangePhoneRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
