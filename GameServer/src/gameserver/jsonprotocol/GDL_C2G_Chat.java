package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_Chat extends JsonPacket {
	public int getProtocolId() { return 50; }
	Integer a;
	public Integer getMode() { return this.a;}  // 0 私聊， 2 世界聊天  3 滚动公告 4 客服反馈
	public void setMode(Integer a) { this.a = a;}

	Long b;
	public Long getPlayerId() { return this.b;}  // 如果是私聊，这个就是对方的player_id，如果是时间聊天，这个没有任何意义
	public void setPlayerId(Long b) { this.b = b;}

	String c;
	public String getMsg() { return this.c;}  // 内容
	public void setMsg(String c) { this.c = c;}

	public static GDL_C2G_Chat parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_Chat.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
