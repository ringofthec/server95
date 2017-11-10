package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class BJLBets extends JsonPacket {
	List< Integer > a = new ArrayList< Integer >();   // 
	public List< Integer > getBetId() { return this.a;}
	public void setBetId(List< Integer > a) { this.a = a;}

	List< Long > b = new ArrayList< Long >();   // 次数
	public List< Long > getNum() { return this.b;}
	public void setNum(List< Long > b) { this.b = b;}

	public static BJLBets parse(String data) {
		 return JsonUtil.JsonToObject(data, BJLBets.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
