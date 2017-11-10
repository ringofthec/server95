package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_EnterGameRe extends JsonPacket {
	public int getProtocolId() { return 201; }
	Integer a;
	public Integer getInstanceId() { return this.a;}  // 游戏副本唯一id
	public void setInstanceId(Integer a) { this.a = a;}

	Integer b;
	public Integer getGameId() { return this.b;}  // 游戏id 1 水果slot  2 金砖 slot  10 捕鱼
	public void setGameId(Integer b) { this.b = b;}

	FishGameStatus c;   // 捕鱼游戏场景信息
	public FishGameStatus getFishGameInfo() { return this.c;}
	public void setFishGameInfo(FishGameStatus c) { this.c = c;}

	SlotsGameStatus d;   // slots游戏场景信息
	public SlotsGameStatus getSlotsGameInfo() { return this.d;}
	public void setSlotsGameInfo(SlotsGameStatus d) { this.d = d;}

	NNGameStatus e;   // 百人牛牛
	public NNGameStatus getBRNNGameInfo() { return this.e;}
	public void setBRNNGameInfo(NNGameStatus e) { this.e = e;}

	BJLGameStatus f;   // 百家乐
	public BJLGameStatus getBaijialeGameInfo() { return this.f;}
	public void setBaijialeGameInfo(BJLGameStatus f) { this.f = f;}

	public static GDL_G2C_EnterGameRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_EnterGameRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
