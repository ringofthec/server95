package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class FuncSetting extends JsonPacket {
	Integer a;
	public Integer getFunId() { return this.a;}  // 功能号 1 支付宝支付 2 微信支付 3 抽奖兑奖 4 好评
	public void setFunId(Integer a) { this.a = a;}

	Integer b;
	public Integer getOpen() { return this.b;}  // 0 关闭  1 打开
	public void setOpen(Integer b) { this.b = b;}

	public static FuncSetting parse(String data) {
		 return JsonUtil.JsonToObject(data, FuncSetting.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
