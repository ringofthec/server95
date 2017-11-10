package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_NNPlayerNotSitList extends JsonPacket {
	public int getProtocolId() { return 335; }
	List< NNPlayerInfo > a = new ArrayList< NNPlayerInfo >();   // 
	public List< NNPlayerInfo > getLists() { return this.a;}
	public void setLists(List< NNPlayerInfo > a) { this.a = a;}

	public static GDL_G2C_NNPlayerNotSitList parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_NNPlayerNotSitList.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
