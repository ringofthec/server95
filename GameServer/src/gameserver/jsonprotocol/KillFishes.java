package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class KillFishes extends JsonPacket {
	public int getProtocolId() { return 261; }
	Integer a;
	public Integer getType() { return this.a;}  // 子弹类型
	public void setType(Integer a) { this.a = a;}

	Integer b;
	public Integer getKiller() { return this.b;}  // 杀手鱼indx
	public void setKiller(Integer b) { this.b = b;}

	List< Integer > c = new ArrayList< Integer >();   // 顺带杀死的鱼的idx列表
	public List< Integer > getFishIndex() { return this.c;}
	public void setFishIndex(List< Integer > c) { this.c = c;}

	public static KillFishes parse(String data) {
		 return JsonUtil.JsonToObject(data, KillFishes.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
