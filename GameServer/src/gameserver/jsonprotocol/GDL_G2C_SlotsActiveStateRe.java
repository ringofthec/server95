package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_SlotsActiveStateRe extends JsonPacket {
	public int getProtocolId() { return 122; }
	Integer a;
	public Integer getGameId() { return this.a;}  // 1 水果  2 金砖
	public void setGameId(Integer a) { this.a = a;}

	Integer b;
	public Integer getLevel() { return this.b;}  // 场次
	public void setLevel(Integer b) { this.b = b;}

	Integer c;
	public Integer getMode() { return this.c;}  // 0 开始  1 结束 2 等待开始(金砖slots) 3 金砖活动取消了 4 活动即将开始 5 活动即将结束
	public void setMode(Integer c) { this.c = c;}

	Integer d;
	public Integer getLefttime() { return this.d;}  // 开场倒计时，只有金砖slots才有意义
	public void setLefttime(Integer d) { this.d = d;}

	public static GDL_G2C_SlotsActiveStateRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_SlotsActiveStateRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
