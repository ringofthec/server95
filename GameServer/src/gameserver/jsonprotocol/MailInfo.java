package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class MailInfo extends JsonPacket {
	Long a;
	public Long getMailId() { return this.a;}  // 邮件id
	public void setMailId(Long a) { this.a = a;}

	String b;
	public String getTitle() { return this.b;}  // 邮件标题
	public void setTitle(String b) { this.b = b;}

	String c;
	public String getComment() { return this.c;}  // 邮件内容
	public void setComment(String c) { this.c = c;}

	String d;
	public String getSender() { return this.d;}  // 邮件发送者
	public void setSender(String d) { this.d = d;}

	String e;
	public String getExpireTime() { return this.e;}  // 邮件过期时间
	public void setExpireTime(String e) { this.e = e;}

	List< MailItemInfo > f = new ArrayList< MailItemInfo >();   // 邮件中附带的物品
	public List< MailItemInfo > getItems() { return this.f;}
	public void setItems(List< MailItemInfo > f) { this.f = f;}

	public static MailInfo parse(String data) {
		 return JsonUtil.JsonToObject(data, MailInfo.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
