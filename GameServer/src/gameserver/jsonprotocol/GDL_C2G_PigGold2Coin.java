package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_PigGold2Coin extends JsonPacket {
	public int getProtocolId() { return 68; }
	Long a;
	public Long getCount() { return this.a;}  // 要兑换的金砖数
	public void setCount(Long a) { this.a = a;}

	public static GDL_C2G_PigGold2Coin parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_PigGold2Coin.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
