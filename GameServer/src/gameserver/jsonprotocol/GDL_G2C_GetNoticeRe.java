package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_GetNoticeRe extends JsonPacket {
	public int getProtocolId() { return 9; }
	List< LoginNoticeItem > a = new ArrayList< LoginNoticeItem >();   // 登陆奖励物品列表
	public List< LoginNoticeItem > getNoticeList() { return this.a;}
	public void setNoticeList(List< LoginNoticeItem > a) { this.a = a;}

	public static GDL_G2C_GetNoticeRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_GetNoticeRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
