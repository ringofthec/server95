package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_BJLMiCard extends JsonPacket {
	public int getProtocolId() { return 370; }
	Integer a;
	public Integer getData1() { return this.a;}  // 
	public void setData1(Integer a) { this.a = a;}

	Integer b;
	public Integer getData2() { return this.b;}  // 
	public void setData2(Integer b) { this.b = b;}

	Integer c;
	public Integer getData3() { return this.c;}  // 
	public void setData3(Integer c) { this.c = c;}

	public static GDL_C2G_BJLMiCard parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_BJLMiCard.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
