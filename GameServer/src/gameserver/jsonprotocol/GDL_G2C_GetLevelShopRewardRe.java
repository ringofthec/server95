package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_GetLevelShopRewardRe extends JsonPacket {
	public int getProtocolId() { return 75; }
	List< Integer > a = new ArrayList< Integer >();   // 已经领取了的奖励等级
	public List< Integer > getLevel() { return this.a;}
	public void setLevel(List< Integer > a) { this.a = a;}

	public static GDL_G2C_GetLevelShopRewardRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_GetLevelShopRewardRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
