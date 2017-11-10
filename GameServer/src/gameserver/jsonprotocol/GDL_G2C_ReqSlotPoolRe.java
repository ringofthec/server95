package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_ReqSlotPoolRe extends JsonPacket {
	public int getProtocolId() { return 97; }
	Integer a;
	public Integer getGameId() { return this.a;}  // 
	public void setGameId(Integer a) { this.a = a;}

	Integer b;
	public Integer getLevelId() { return this.b;}  // 
	public void setLevelId(Integer b) { this.b = b;}

	Long c;
	public Long getPoolCoin() { return this.c;}  // 奖池中的金币数
	public void setPoolCoin(Long c) { this.c = c;}

	Long d;
	public Long getPoolCoinTotal() { return this.d;}  // 奖池满的金币数
	public void setPoolCoinTotal(Long d) { this.d = d;}

	public static GDL_G2C_ReqSlotPoolRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_ReqSlotPoolRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
