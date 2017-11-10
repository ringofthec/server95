package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_NNBankerChange extends JsonPacket {
	public int getProtocolId() { return 339; }
	NNPlayerInfo a;   // 
	public NNPlayerInfo getBanker() { return this.a;}
	public void setBanker(NNPlayerInfo a) { this.a = a;}

	public static GDL_G2C_NNBankerChange parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_NNBankerChange.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
