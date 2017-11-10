package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class BJLXiaolu extends JsonPacket {
	List< Integer > a = new ArrayList< Integer >();   // 1 红色 2 蓝色
	public List< Integer > getRes() { return this.a;}
	public void setRes(List< Integer > a) { this.a = a;}

	public static BJLXiaolu parse(String data) {
		 return JsonUtil.JsonToObject(data, BJLXiaolu.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
