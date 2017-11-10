package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_SwitchScene extends JsonPacket {
	public int getProtocolId() { return 204; }
	Integer a;
	public Integer getSceneId() { return this.a;}  // 场景id
	public void setSceneId(Integer a) { this.a = a;}

	public static GDL_G2C_SwitchScene parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_SwitchScene.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
