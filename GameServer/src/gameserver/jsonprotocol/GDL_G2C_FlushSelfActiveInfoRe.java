package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_FlushSelfActiveInfoRe extends JsonPacket {
	public int getProtocolId() { return 125; }
	Integer a;
	public Integer getGameId() { return this.a;}  // 1 水果  2 金砖
	public void setGameId(Integer a) { this.a = a;}

	Integer b;
	public Integer getLevel() { return this.b;}  // 场次
	public void setLevel(Integer b) { this.b = b;}

	Long c;
	public Long getEran() { return this.c;}  // 本次比赛自己的赢取数
	public void setEran(Long c) { this.c = c;}

	Integer d;
	public Integer getOrder() { return this.d;}  // 名次
	public void setOrder(Integer d) { this.d = d;}

	public static GDL_G2C_FlushSelfActiveInfoRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_FlushSelfActiveInfoRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
