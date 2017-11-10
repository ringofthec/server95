package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_PlayerJoinGame extends JsonPacket {
	public int getProtocolId() { return 206; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 玩家id
	public void setPlayerId(Long a) { this.a = a;}

	Integer b;
	public Integer getGameId() { return this.b;}  // 游戏id 1 水果slot  2 金砖 slot  10 捕鱼
	public void setGameId(Integer b) { this.b = b;}

	SlotGamePlayerInfo c;   // slot情况下，玩家信息
	public SlotGamePlayerInfo getSplayerInfos() { return this.c;}
	public void setSplayerInfos(SlotGamePlayerInfo c) { this.c = c;}

	FishGamePlayerInfo d;   // 捕鱼情况下，玩家信息
	public FishGamePlayerInfo getFplayerInfos() { return this.d;}
	public void setFplayerInfos(FishGamePlayerInfo d) { this.d = d;}

	NNPlayerInfo e;   // 牛牛情况下，玩家信息
	public NNPlayerInfo getNplayerInfos() { return this.e;}
	public void setNplayerInfos(NNPlayerInfo e) { this.e = e;}

	BJLPlayerInfo f;   // 百家乐情况下，玩家信息
	public BJLPlayerInfo getBplayerInfos() { return this.f;}
	public void setBplayerInfos(BJLPlayerInfo f) { this.f = f;}

	public static GDL_G2C_PlayerJoinGame parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_PlayerJoinGame.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
