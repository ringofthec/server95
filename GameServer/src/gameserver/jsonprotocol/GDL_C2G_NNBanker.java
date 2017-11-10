package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_NNBanker extends JsonPacket {
	public int getProtocolId() { return 321; }
	Long a;
	public Long getTotalCoin() { return this.a;}  // 坐庄钱数
	public void setTotalCoin(Long a) { this.a = a;}

	public static GDL_C2G_NNBanker parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_NNBanker.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
