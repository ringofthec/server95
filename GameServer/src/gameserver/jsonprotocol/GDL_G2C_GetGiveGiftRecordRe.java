package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_GetGiveGiftRecordRe extends JsonPacket {
	public int getProtocolId() { return 38; }
	Integer a;
	public Integer getMode() { return this.a;}  // 0 全部同步 1 更新同步
	public void setMode(Integer a) { this.a = a;}

	Integer b;
	public Integer getType() { return this.b;}  // 1 赠送礼物记录 2 抢到红包记录
	public void setType(Integer b) { this.b = b;}

	List< String > c = new ArrayList< String >();   // 记录列表
	public List< String > getMsg() { return this.c;}
	public void setMsg(List< String > c) { this.c = c;}

	public static GDL_G2C_GetGiveGiftRecordRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_GetGiveGiftRecordRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
