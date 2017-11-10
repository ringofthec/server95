package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_DropItem extends JsonPacket {
	public int getProtocolId() { return 29; }
	Long a;
	public Long getItemId() { return this.a;}  // 物品obj id
	public void setItemId(Long a) { this.a = a;}

	public static GDL_C2G_DropItem parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_DropItem.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
