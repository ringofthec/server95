package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class NuclearBomb extends JsonPacket {
	public int getProtocolId() { return 247; }
	List< Integer > a = new ArrayList< Integer >();   // 鱼的idx列表
	public List< Integer > getFishIndexs() { return this.a;}
	public void setFishIndexs(List< Integer > a) { this.a = a;}

	Integer b;
	public Integer getPosX() { return this.b;}  // 爆炸位置
	public void setPosX(Integer b) { this.b = b;}

	Integer c;
	public Integer getPosY() { return this.c;}  // 爆炸位置
	public void setPosY(Integer c) { this.c = c;}

	public static NuclearBomb parse(String data) {
		 return JsonUtil.JsonToObject(data, NuclearBomb.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
