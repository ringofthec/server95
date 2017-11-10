package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class LineInfo extends JsonPacket {
	Integer a;
	public Integer getLineId() { return this.a;}  // 线的id, 对应GSlotsLine表
	public void setLineId(Integer a) { this.a = a;}

	Integer b;
	public Integer getNum() { return this.b;}  // 线上连续的数量
	public void setNum(Integer b) { this.b = b;}

	public static LineInfo parse(String data) {
		 return JsonUtil.JsonToObject(data, LineInfo.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
