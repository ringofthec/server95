package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_BJLBankerListRe extends JsonPacket {
	public int getProtocolId() { return 353; }
	List< BJLPlayerInfo > a = new ArrayList< BJLPlayerInfo >();   // 列表上的玩家
	public List< BJLPlayerInfo > getPlayers() { return this.a;}
	public void setPlayers(List< BJLPlayerInfo > a) { this.a = a;}

	List< Long > b = new ArrayList< Long >();   // 玩家的上庄钱数
	public List< Long > getBankerCoin() { return this.b;}
	public void setBankerCoin(List< Long > b) { this.b = b;}

	public static GDL_G2C_BJLBankerListRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_BJLBankerListRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
