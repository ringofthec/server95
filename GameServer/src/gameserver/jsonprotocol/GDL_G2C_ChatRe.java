package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_ChatRe extends JsonPacket {
	public int getProtocolId() { return 51; }
	Integer a;
	public Integer getMode() { return this.a;}  // 0 私聊， 2 世界聊天  3 滚动公告 4 客服反馈
	public void setMode(Integer a) { this.a = a;}

	Long b;
	public Long getPlayerId() { return this.b;}  // 如果是私聊，这个就是对方的player_id，如果是其他聊天，这个没有任何意义
	public void setPlayerId(Long b) { this.b = b;}

	String c;
	public String getPlayerName() { return this.c;}  // 如果是私聊，这个就是对方的名字，如果是其他聊天，这个没有任何意义
	public void setPlayerName(String c) { this.c = c;}

	String d;
	public String getMsg() { return this.d;}  // 内容
	public void setMsg(String d) { this.d = d;}

	public static GDL_G2C_ChatRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_ChatRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
