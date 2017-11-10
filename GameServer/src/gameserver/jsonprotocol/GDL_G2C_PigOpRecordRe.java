package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_PigOpRecordRe extends JsonPacket {
	public int getProtocolId() { return 78; }
	List< PigOpRecord > a = new ArrayList< PigOpRecord >();   // 记录列表
	public List< PigOpRecord > getRecords() { return this.a;}
	public void setRecords(List< PigOpRecord > a) { this.a = a;}

	public static GDL_G2C_PigOpRecordRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_PigOpRecordRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
