package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_GetGiveGiftRecord extends JsonPacket {
	public int getProtocolId() { return 37; }
	Integer a;
	public Integer getType() { return this.a;}  // 1 赠送礼物记录 2 抢到红包记录
	public void setType(Integer a) { this.a = a;}

	public static GDL_C2G_GetGiveGiftRecord parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_GetGiveGiftRecord.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
