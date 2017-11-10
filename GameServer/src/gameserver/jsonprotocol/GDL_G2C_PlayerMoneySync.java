package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_PlayerMoneySync extends JsonPacket {
	public int getProtocolId() { return 130; }
	Long a;
	public Long getPlayer_id() { return this.a;}  // 
	public void setPlayer_id(Long a) { this.a = a;}

	Long b;
	public Long getCoin() { return this.b;}  // 
	public void setCoin(Long b) { this.b = b;}

	Long c;
	public Long getRewardCoin() { return this.c;}  // 本把赢取
	public void setRewardCoin(Long c) { this.c = c;}

	Integer d;
	public Integer getType() { return this.d;}  // 0 普通  1-3 免费转（对应三个小游戏）9 奖池 10 超过两百倍
	public void setType(Integer d) { this.d = d;}

	public static GDL_G2C_PlayerMoneySync parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_PlayerMoneySync.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
