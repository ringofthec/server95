package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_FishSyncTimeRe extends JsonPacket {
	public int getProtocolId() { return 231; }
	Integer a;
	public Integer getPass_time() { return this.a;}  // 游戏流逝时间 =  正常时间 - 冰冻时间
	public void setPass_time(Integer a) { this.a = a;}

	Integer b;
	public Integer getFrozening_time() { return this.b;}  // 剩余冰冻时间， 如果这个大于0，冰冻技能正在起效中，这里说明了还有几秒解冻
	public void setFrozening_time(Integer b) { this.b = b;}

	public static GDL_G2C_FishSyncTimeRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_FishSyncTimeRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
