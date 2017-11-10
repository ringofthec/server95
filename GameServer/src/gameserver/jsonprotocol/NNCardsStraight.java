package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class NNCardsStraight extends JsonPacket {
	Integer a;
	public Integer getType() { return this.a;}  // 牌型id表示？ 牛牛、炸弹、五花、四花、牛牛、有牛（从牛9到牛1）、没牛
	public void setType(Integer a) { this.a = a;}

	List< Integer > b = new ArrayList< Integer >();   // 0-13
	public List< Integer > getCards() { return this.b;}
	public void setCards(List< Integer > b) { this.b = b;}

	public static NNCardsStraight parse(String data) {
		 return JsonUtil.JsonToObject(data, NNCardsStraight.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
