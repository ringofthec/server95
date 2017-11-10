package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_BeginSlots extends JsonPacket {
	public int getProtocolId() { return 90; }
	Integer a;
	public Integer getLineNum() { return this.a;}  // 押注线数
	public void setLineNum(Integer a) { this.a = a;}

	Integer b;
	public Integer getMoneyPreLine() { return this.b;}  // 每线押注数
	public void setMoneyPreLine(Integer b) { this.b = b;}

	Integer c;
	public Integer getAuto() { return this.c;}  // 自动转
	public void setAuto(Integer c) { this.c = c;}

	public static GDL_C2G_BeginSlots parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_BeginSlots.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
