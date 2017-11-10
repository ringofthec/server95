package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_CashPrize extends JsonPacket {
	public int getProtocolId() { return 83; }
	Integer a;
	public Integer getId() { return this.a;}  // 兑奖id
	public void setId(Integer a) { this.a = a;}

	String b;
	public String getName() { return this.b;}  // 玩家真实名字
	public void setName(String b) { this.b = b;}

	String c;
	public String getPhone() { return this.c;}  // 手机号
	public void setPhone(String c) { this.c = c;}

	String d;
	public String getAddress() { return this.d;}  // 收货地址
	public void setAddress(String d) { this.d = d;}

	String e;
	public String getWinxin() { return this.e;}  // 微信号
	public void setWinxin(String e) { this.e = e;}

	public static GDL_C2G_CashPrize parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_CashPrize.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
