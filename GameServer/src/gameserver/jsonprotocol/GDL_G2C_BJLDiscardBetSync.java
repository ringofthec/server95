package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_BJLDiscardBetSync extends JsonPacket {
	public int getProtocolId() { return 375; }
	Long a;
	public Long getPlayer_id() { return this.a;}  // 
	public void setPlayer_id(Long a) { this.a = a;}

	BJLBetPool b;   // 当前下注池中的钱
	public BJLBetPool getBetPools() { return this.b;}
	public void setBetPools(BJLBetPool b) { this.b = b;}

	public static GDL_G2C_BJLDiscardBetSync parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_BJLDiscardBetSync.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
