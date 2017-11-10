package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_BJLPlayerNotSitList extends JsonPacket {
	public int getProtocolId() { return 365; }
	List< BJLPlayerInfo > a = new ArrayList< BJLPlayerInfo >();   // 
	public List< BJLPlayerInfo > getLists() { return this.a;}
	public void setLists(List< BJLPlayerInfo > a) { this.a = a;}

	public static GDL_G2C_BJLPlayerNotSitList parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_BJLPlayerNotSitList.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
