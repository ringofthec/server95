package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_GetBattleRecord extends JsonPacket {
	public int getProtocolId() { return 33; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 如果要获取自己的战绩，就填自己的playerid，获取别人的战绩，就填别人的player_id
	public void setPlayerId(Long a) { this.a = a;}

	Integer b;
	public Integer getFunc() { return this.b;}  // 服务回传字段
	public void setFunc(Integer b) { this.b = b;}

	public static GDL_C2G_GetBattleRecord parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_GetBattleRecord.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
