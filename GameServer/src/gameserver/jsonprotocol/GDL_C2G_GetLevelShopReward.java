package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_GetLevelShopReward extends JsonPacket {
	public int getProtocolId() { return 74; }
	Integer a;
	public Integer getMode() { return this.a;}  // 0 查询  1 领取
	public void setMode(Integer a) { this.a = a;}

	Integer b;
	public Integer getLevel() { return this.b;}  // 要领取的等级奖励的等级
	public void setLevel(Integer b) { this.b = b;}

	public static GDL_C2G_GetLevelShopReward parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_GetLevelShopReward.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
