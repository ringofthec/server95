package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class RewardItem extends JsonPacket {
	Integer a;
	public Integer getId() { return this.a;}  // 编号
	public void setId(Integer a) { this.a = a;}

	Integer b;
	public Integer getItemId() { return this.b;}  // 物品名称
	public void setItemId(Integer b) { this.b = b;}

	Integer c;
	public Integer getItemNum() { return this.c;}  // 物品数量
	public void setItemNum(Integer c) { this.c = c;}

	public static RewardItem parse(String data) {
		 return JsonUtil.JsonToObject(data, RewardItem.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
