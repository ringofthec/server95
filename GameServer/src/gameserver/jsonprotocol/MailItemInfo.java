package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class MailItemInfo extends JsonPacket {
	Integer a;
	public Integer getItemTempId() { return this.a;}  // 物品种类id
	public void setItemTempId(Integer a) { this.a = a;}

	Long b;
	public Long getItemNum() { return this.b;}  // 物品数量
	public void setItemNum(Long b) { this.b = b;}

	public static MailItemInfo parse(String data) {
		 return JsonUtil.JsonToObject(data, MailItemInfo.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
