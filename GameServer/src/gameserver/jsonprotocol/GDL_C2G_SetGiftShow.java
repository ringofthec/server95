package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_SetGiftShow extends JsonPacket {
	public int getProtocolId() { return 25; }
	Integer a;
	public Integer getItemTempId() { return this.a;}  // 物品模板id
	public void setItemTempId(Integer a) { this.a = a;}

	Integer b;
	public Integer getNum() { return this.b;}  // 物品数量
	public void setNum(Integer b) { this.b = b;}

	public static GDL_C2G_SetGiftShow parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_SetGiftShow.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
