package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_ShowToast extends JsonPacket {
	public int getProtocolId() { return 1001; }
	String a;
	public String getMsg() { return this.a;}  // 显示的内容
	public void setMsg(String a) { this.a = a;}

	Integer b;
	public Integer getShowTime() { return this.b;}  // 显示时间
	public void setShowTime(Integer b) { this.b = b;}

	public static GDL_G2C_ShowToast parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_ShowToast.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
