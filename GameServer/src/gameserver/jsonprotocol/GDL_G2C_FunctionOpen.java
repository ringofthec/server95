package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_FunctionOpen extends JsonPacket {
	public int getProtocolId() { return 303; }
	List< FuncSetting > a = new ArrayList< FuncSetting >();   // 功能开关配置
	public List< FuncSetting > getFuncs() { return this.a;}
	public void setFuncs(List< FuncSetting > a) { this.a = a;}

	public static GDL_G2C_FunctionOpen parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_FunctionOpen.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
