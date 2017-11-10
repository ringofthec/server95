package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_NNPlayerMoneySync extends JsonPacket {
	public int getProtocolId() { return 340; }
	List< Long > playersId = new ArrayList< Long >();   // 玩家Id
	public List< Long > getPlayersId() { return this.playersId;}
	public void setPlayersId(List< Long > playersId) { this.playersId = playersId;}

	List< Long > coin = new ArrayList< Long >();   // 玩家coin
	public List< Long > getCoin() { return this.coin;}
	public void setCoin(List< Long > coin) { this.coin = coin;}

	public static GDL_G2C_NNPlayerMoneySync parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_NNPlayerMoneySync.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
