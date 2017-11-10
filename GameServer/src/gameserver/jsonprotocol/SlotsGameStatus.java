package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class SlotsGameStatus extends JsonPacket {
	List< SlotGamePlayerInfo > a = new ArrayList< SlotGamePlayerInfo >();   // 玩家信息列表
	public List< SlotGamePlayerInfo > getPlayerInfos() { return this.a;}
	public void setPlayerInfos(List< SlotGamePlayerInfo > a) { this.a = a;}

	public static SlotsGameStatus parse(String data) {
		 return JsonUtil.JsonToObject(data, SlotsGameStatus.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
