package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class SoltsActivePlayerInfo extends JsonPacket {
	Long a;
	public Long getPlayer_id() { return this.a;}  // 
	public void setPlayer_id(Long a) { this.a = a;}

	String b;
	public String getName() { return this.b;}  // 
	public void setName(String b) { this.b = b;}

	Long c;
	public Long getEran() { return this.c;}  // 赢取数
	public void setEran(Long c) { this.c = c;}

	Integer d;
	public Integer getOrder() { return this.d;}  // 名次
	public void setOrder(Integer d) { this.d = d;}

	public static SoltsActivePlayerInfo parse(String data) {
		 return JsonUtil.JsonToObject(data, SoltsActivePlayerInfo.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
