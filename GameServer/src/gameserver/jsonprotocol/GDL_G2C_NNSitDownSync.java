package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_NNSitDownSync extends JsonPacket {
	public int getProtocolId() { return 327; }
	NNPlayerInfo a;   // 谁？
	public NNPlayerInfo getPlayer() { return this.a;}
	public void setPlayer(NNPlayerInfo a) { this.a = a;}

	Integer b;
	public Integer getSit() { return this.b;}  // 坐下的位置
	public void setSit(Integer b) { this.b = b;}

	public static GDL_G2C_NNSitDownSync parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_NNSitDownSync.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
