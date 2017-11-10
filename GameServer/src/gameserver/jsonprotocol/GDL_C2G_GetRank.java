package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_GetRank extends JsonPacket {
	public int getProtocolId() { return 40; }
	Integer a;
	public Integer getType() { return this.a;}  // 0 财富榜 1 等级榜 2 人气榜
	public void setType(Integer a) { this.a = a;}

	Integer b;
	public Integer getMode() { return this.b;}  // 0 全部排行榜  1 各榜第一名
	public void setMode(Integer b) { this.b = b;}

	public static GDL_C2G_GetRank parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_GetRank.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
