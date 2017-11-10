package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_UseItem extends JsonPacket {
	public int getProtocolId() { return 64; }
	Long a;
	public Long getItemObjId() { return this.a;}  // 要使用的item obj id
	public void setItemObjId(Long a) { this.a = a;}

	Integer b;
	public Integer getMode() { return this.b;}  // 0 使用一个  1 全部使用
	public void setMode(Integer b) { this.b = b;}

	public static GDL_C2G_UseItem parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_UseItem.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
