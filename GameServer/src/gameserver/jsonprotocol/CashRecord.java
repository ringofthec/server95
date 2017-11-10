package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class CashRecord extends JsonPacket {
	Integer a;
	public Integer getItemId() { return this.a;}  // 得奖物品id 兑奖表
	public void setItemId(Integer a) { this.a = a;}

	String b;
	public String getCashtime() { return this.b;}  // 兑奖时间
	public void setCashtime(String b) { this.b = b;}

	Integer c;
	public Integer getStatus() { return this.c;}  // 0 已提交 1 已发货 2 完成 3 打回
	public void setStatus(Integer c) { this.c = c;}

	public static CashRecord parse(String data) {
		 return JsonUtil.JsonToObject(data, CashRecord.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
