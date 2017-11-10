package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_NNReqLastPoolWin extends JsonPacket {
	public int getProtocolId() { return 341; }
	NNPlayerInfo a;   // 
	public NNPlayerInfo getPlayer() { return this.a;}
	public void setPlayer(NNPlayerInfo a) { this.a = a;}

	Long b;
	public Long getPoolCoin() { return this.b;}  // 
	public void setPoolCoin(Long b) { this.b = b;}

	public static GDL_G2C_NNReqLastPoolWin parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_NNReqLastPoolWin.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
