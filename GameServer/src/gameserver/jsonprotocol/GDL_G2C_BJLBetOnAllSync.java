package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_BJLBetOnAllSync extends JsonPacket {
	public int getProtocolId() { return 380; }
	Long a;
	public Long getPlayer_id() { return this.a;}  // 
	public void setPlayer_id(Long a) { this.a = a;}

	List< Integer > b = new ArrayList< Integer >();   // 
	public List< Integer > getPoolId() { return this.b;}
	public void setPoolId(List< Integer > b) { this.b = b;}

	List< BJLBets > c = new ArrayList< BJLBets >();   // 
	public List< BJLBets > getBets() { return this.c;}
	public void setBets(List< BJLBets > c) { this.c = c;}

	BJLBetPool d;   // 当前下注池中的钱
	public BJLBetPool getBetPools() { return this.d;}
	public void setBetPools(BJLBetPool d) { this.d = d;}

	public static GDL_G2C_BJLBetOnAllSync parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_BJLBetOnAllSync.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
