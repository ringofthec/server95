package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_ShowAlert extends JsonPacket {
	public int getProtocolId() { return 1002; }
	String a;
	public String getTitle() { return this.a;}  // 标题
	public void setTitle(String a) { this.a = a;}

	String b;
	public String getMsg() { return this.b;}  // 内容
	public void setMsg(String b) { this.b = b;}

	String c;
	public String getSureButtonText() { return this.c;}  // 右按钮文字
	public void setSureButtonText(String c) { this.c = c;}

	String d;
	public String getSureButtonAction() { return this.d;}  // 右按钮动作
	public void setSureButtonAction(String d) { this.d = d;}

	String e;
	public String getCancelButtonText() { return this.e;}  // 左按钮文字
	public void setCancelButtonText(String e) { this.e = e;}

	String f;
	public String getCancelButtonAction() { return this.f;}  // 左按钮动作
	public void setCancelButtonAction(String f) { this.f = f;}

	public static GDL_G2C_ShowAlert parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_ShowAlert.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
