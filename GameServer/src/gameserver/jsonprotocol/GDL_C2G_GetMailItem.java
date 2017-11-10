package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_GetMailItem extends JsonPacket {
	public int getProtocolId() { return 62; }
	Integer a;
	public Integer getMailId() { return this.a;}  // 0 全部领取 其他 领取指定id邮件
	public void setMailId(Integer a) { this.a = a;}

	public static GDL_C2G_GetMailItem parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_GetMailItem.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
