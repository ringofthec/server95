package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_ItemAddSync extends JsonPacket {
	public int getProtocolId() { return 58; }
	Integer a;
	public Integer getItemTempId() { return this.a;}  // 物品id
	public void setItemTempId(Integer a) { this.a = a;}

	Long b;
	public Long getNum() { return this.b;}  // 变化量
	public void setNum(Long b) { this.b = b;}

	public static GDL_G2C_ItemAddSync parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_ItemAddSync.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
