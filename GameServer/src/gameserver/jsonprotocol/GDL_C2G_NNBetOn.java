package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_NNBetOn extends JsonPacket {
	public int getProtocolId() { return 320; }
	Integer a;
	public Integer getBetId() { return this.a;}  // 下注id
	public void setBetId(Integer a) { this.a = a;}

	Integer b;
	public Integer getPoolIdx() { return this.b;}  // 下注的池子序号 0,1,2,3
	public void setPoolIdx(Integer b) { this.b = b;}

	public static GDL_C2G_NNBetOn parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_NNBetOn.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
