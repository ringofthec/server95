package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_C2G_Guess extends JsonPacket {
	public int getProtocolId() { return 94; }
	Integer a;
	public Integer getGuess() { return this.a;}  // 如果有的话，猜花色小游戏的选择 0是2选1， 1是4选1
	public void setGuess(Integer a) { this.a = a;}

	public static GDL_C2G_Guess parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_C2G_Guess.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
