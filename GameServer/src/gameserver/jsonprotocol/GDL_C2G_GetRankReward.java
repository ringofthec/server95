package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_GetRankReward extends JsonPacket {
	public int getProtocolId() { return 39; }
	Integer a;
	public Integer getType() { return this.a;}  // 0 财富榜 1 等级榜 2 人气榜
	public void setType(Integer a) { this.a = a;}

	public static GDL_C2G_GetRankReward parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_GetRankReward.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
