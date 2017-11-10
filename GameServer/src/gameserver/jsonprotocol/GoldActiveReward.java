package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GoldActiveReward extends JsonPacket {
	List< Integer > a = new ArrayList< Integer >();   // 物品id
	public List< Integer > getItemTempId() { return this.a;}
	public void setItemTempId(List< Integer > a) { this.a = a;}

	List< Integer > b = new ArrayList< Integer >();   // 物品数量
	public List< Integer > getItemNum() { return this.b;}
	public void setItemNum(List< Integer > b) { this.b = b;}

	public static GoldActiveReward parse(String data) {
		 return JsonUtil.JsonToObject(data, GoldActiveReward.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
