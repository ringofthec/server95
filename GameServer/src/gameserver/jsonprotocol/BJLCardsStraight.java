package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class BJLCardsStraight extends JsonPacket {
	List< Integer > a = new ArrayList< Integer >();   // 八副牌分别是 1-52 101-152 .... 801-852，还有这个数组里面是三张牌，第三张牌非0的情况说明是补牌
	public List< Integer > getCards() { return this.a;}
	public void setCards(List< Integer > a) { this.a = a;}

	public static BJLCardsStraight parse(String data) {
		 return JsonUtil.JsonToObject(data, BJLCardsStraight.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
