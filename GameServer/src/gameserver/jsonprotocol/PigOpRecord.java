package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class PigOpRecord extends JsonPacket {
	Integer a;
	public Integer getOp() { return this.a;}  // 0 存入 1 取出 2 我送人金币 3 我送人金砖 4 别人送我金币 5  别人送我金砖
	public void setOp(Integer a) { this.a = a;}

	Long b;
	public Long getNum() { return this.b;}  // 数量
	public void setNum(Long b) { this.b = b;}

	Long c;
	public Long getOtherId() { return this.c;}  // 目标玩家id
	public void setOtherId(Long c) { this.c = c;}

	String d;
	public String getOtherName() { return this.d;}  // 目标玩家名字
	public void setOtherName(String d) { this.d = d;}

	String e;
	public String getOptime() { return this.e;}  // 操作时间
	public void setOptime(String e) { this.e = e;}

	Long f;
	public Long getPigcoin() { return this.f;}  // 银行存款
	public void setPigcoin(Long f) { this.f = f;}

	Long g;
	public Long getMoney() { return this.g;}  // 当前身上金币
	public void setMoney(Long g) { this.g = g;}

	Long h;
	public Long getGold() { return this.h;}  // 当前身上金砖
	public void setGold(Long h) { this.h = h;}

	public static PigOpRecord parse(String data) {
		 return JsonUtil.JsonToObject(data, PigOpRecord.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
