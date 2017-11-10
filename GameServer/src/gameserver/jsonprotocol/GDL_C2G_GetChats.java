package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_GetChats extends JsonPacket {
	public int getProtocolId() { return 52; }
	Integer a;
	public Integer getMode() { return this.a;}  // 0 聊天， 1 客服反馈
	public void setMode(Integer a) { this.a = a;}

	public static GDL_C2G_GetChats parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_GetChats.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
