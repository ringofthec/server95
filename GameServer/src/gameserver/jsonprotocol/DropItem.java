package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class DropItem extends JsonPacket {
	Integer a;
	public Integer getFishIndex() { return this.a;}  // 鱼的idx
	public void setFishIndex(Integer a) { this.a = a;}

	List< Integer > b = new ArrayList< Integer >();   // 物品类型
	public List< Integer > getId() { return this.b;}
	public void setId(List< Integer > b) { this.b = b;}

	List< Long > c = new ArrayList< Long >();   // 数量
	public List< Long > getNum() { return this.c;}
	public void setNum(List< Long > c) { this.c = c;}

	public static DropItem parse(String data) {
		 return JsonUtil.JsonToObject(data, DropItem.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
