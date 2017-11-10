package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class BJLSlu extends JsonPacket {
	Integer a;
	public Integer getX() { return this.a;}  // 
	public void setX(Integer a) { this.a = a;}

	Integer b;
	public Integer getY() { return this.b;}  // 
	public void setY(Integer b) { this.b = b;}

	Integer c;
	public Integer getRes() { return this.c;}  // 对大路来说（10 + n 表示庄赢，并且之后有n次平局, 60 + n 表示闲赢，并且之后有n次平局），对其他路来说 1 红色 2 蓝色
	public void setRes(Integer c) { this.c = c;}

	public static BJLSlu parse(String data) {
		 return JsonUtil.JsonToObject(data, BJLSlu.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
