package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class CatchFish extends JsonPacket {
	public int getProtocolId() { return 243; }
	Integer a;
	public Integer getType() { return this.a;}  // 子弹类型
	public void setType(Integer a) { this.a = a;}

	Integer b;
	public Integer getNum() { return this.b;}  // 子弹数量
	public void setNum(Integer b) { this.b = b;}

	List< Integer > c = new ArrayList< Integer >();   // 鱼的列表[idx,num,idx,num...]
	public List< Integer > getIndexAndNum() { return this.c;}
	public void setIndexAndNum(List< Integer > c) { this.c = c;}

	public static CatchFish parse(String data) {
		 return JsonUtil.JsonToObject(data, CatchFish.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
