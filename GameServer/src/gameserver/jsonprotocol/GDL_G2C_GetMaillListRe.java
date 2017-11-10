package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_GetMaillListRe extends JsonPacket {
	public int getProtocolId() { return 61; }
	Integer a;
	public Integer getMode() { return this.a;}  // 0 全部更新 1 增量更新
	public void setMode(Integer a) { this.a = a;}

	List< MailInfo > b = new ArrayList< MailInfo >();   // 邮件中附带的物品
	public List< MailInfo > getMails() { return this.b;}
	public void setMails(List< MailInfo > b) { this.b = b;}

	public static GDL_G2C_GetMaillListRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_GetMaillListRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
