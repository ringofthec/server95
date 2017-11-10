package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_GetItemInfoRe extends JsonPacket {
	public int getProtocolId() { return 13; }
	Integer a;
	public Integer getMode() { return this.a;}  // 0 全部同步 1 更新同步
	public void setMode(Integer a) { this.a = a;}

	List< ItemInfoMsg > b = new ArrayList< ItemInfoMsg >();   // 玩家物品列表
	public List< ItemInfoMsg > getItemList() { return this.b;}
	public void setItemList(List< ItemInfoMsg > b) { this.b = b;}

	Integer c;
	public Integer getEvent() { return this.c;}  // 注意，这个只有在mode==1的情况下，才有效，这个量代表了是何种事件，引起了这次物品变化
	public void setEvent(Integer c) { this.c = c;}

	public static GDL_G2C_GetItemInfoRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_GetItemInfoRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
