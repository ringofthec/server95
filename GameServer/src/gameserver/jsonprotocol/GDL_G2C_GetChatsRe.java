package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_GetChatsRe extends JsonPacket {
	public int getProtocolId() { return 53; }
	Integer a;
	public Integer getMode() { return this.a;}  // 0 聊天， 1 客服反馈
	public void setMode(Integer a) { this.a = a;}

	List< GDL_G2C_ChatRe > b = new ArrayList< GDL_G2C_ChatRe >();   // 
	public List< GDL_G2C_ChatRe > getMsgs() { return this.b;}
	public void setMsgs(List< GDL_G2C_ChatRe > b) { this.b = b;}

	public static GDL_G2C_GetChatsRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_GetChatsRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
