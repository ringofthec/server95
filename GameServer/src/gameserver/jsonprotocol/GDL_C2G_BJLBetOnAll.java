package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_BJLBetOnAll extends JsonPacket {
	public int getProtocolId() { return 379; }
	List< Integer > a = new ArrayList< Integer >();   // 
	public List< Integer > getPoolId() { return this.a;}
	public void setPoolId(List< Integer > a) { this.a = a;}

	List< BJLBets > b = new ArrayList< BJLBets >();   // 
	public List< BJLBets > getBets() { return this.b;}
	public void setBets(List< BJLBets > b) { this.b = b;}

	public static GDL_C2G_BJLBetOnAll parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_BJLBetOnAll.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
