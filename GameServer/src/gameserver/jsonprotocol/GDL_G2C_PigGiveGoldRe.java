package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_PigGiveGoldRe extends JsonPacket {
	public int getProtocolId() { return 76; }
	Integer a;
	public Integer getRetCode() { return this.a;}  // 0 成功 1 没有这个玩家
	public void setRetCode(Integer a) { this.a = a;}

	public static GDL_G2C_PigGiveGoldRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_PigGiveGoldRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
