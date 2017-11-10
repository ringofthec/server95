package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_ChangeNameRe extends JsonPacket {
	public int getProtocolId() { return 22; }
	Integer a;
	public Integer getRetCode() { return this.a;}  // 0 成功，2 名字含有非法字符 3 名字重复啦
	public void setRetCode(Integer a) { this.a = a;}

	String b;
	public String getName() { return this.b;}  // 名字
	public void setName(String b) { this.b = b;}

	public static GDL_G2C_ChangeNameRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_ChangeNameRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
