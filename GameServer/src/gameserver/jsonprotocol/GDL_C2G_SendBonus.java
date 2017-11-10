package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_SendBonus extends JsonPacket {
	public int getProtocolId() { return 47; }
	Integer a;
	public Integer getMoney() { return this.a;}  // 红包总额 百万为单位
	public void setMoney(Integer a) { this.a = a;}

	Integer b;
	public Integer getCount() { return this.b;}  // 红包个数
	public void setCount(Integer b) { this.b = b;}

	String c;
	public String getSay() { return this.c;}  // 宣言
	public void setSay(String c) { this.c = c;}

	public static GDL_C2G_SendBonus parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_SendBonus.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
