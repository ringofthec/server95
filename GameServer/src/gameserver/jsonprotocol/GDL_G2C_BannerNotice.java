package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_BannerNotice extends JsonPacket {
	public int getProtocolId() { return 43; }
	Integer a;
	public Integer getNid() { return this.a;}  // GNotice表中id
	public void setNid(Integer a) { this.a = a;}

	List< String > b = new ArrayList< String >();   // 参数列表
	public List< String > getArgs() { return this.b;}
	public void setArgs(List< String > b) { this.b = b;}

	public static GDL_G2C_BannerNotice parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_BannerNotice.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
