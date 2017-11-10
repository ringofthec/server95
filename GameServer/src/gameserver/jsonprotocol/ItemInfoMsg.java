package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class ItemInfoMsg extends JsonPacket {
	Long a;
	public Long getItemId() { return this.a;}  // 物品唯一id
	public void setItemId(Long a) { this.a = a;}

	Integer b;
	public Integer getItemTempId() { return this.b;}  // 物品种类id
	public void setItemTempId(Integer b) { this.b = b;}

	Long c;
	public Long getItemNum() { return this.c;}  // 物品数量
	public void setItemNum(Long c) { this.c = c;}

	public static ItemInfoMsg parse(String data) {
		 return JsonUtil.JsonToObject(data, ItemInfoMsg.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
