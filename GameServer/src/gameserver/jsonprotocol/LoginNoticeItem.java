package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class LoginNoticeItem extends JsonPacket {
	Integer a;
	public Integer getId() { return this.a;}  // id
	public void setId(Integer a) { this.a = a;}

	String b;
	public String getTitle() { return this.b;}  // 标题
	public void setTitle(String b) { this.b = b;}

	String c;
	public String getContext() { return this.c;}  // 内容
	public void setContext(String c) { this.c = c;}

	String d;
	public String getIconName() { return this.d;}  // icon名称
	public void setIconName(String d) { this.d = d;}

	public static LoginNoticeItem parse(String data) {
		 return JsonUtil.JsonToObject(data, LoginNoticeItem.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
