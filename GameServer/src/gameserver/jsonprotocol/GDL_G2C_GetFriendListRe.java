package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_GetFriendListRe extends JsonPacket {
	public int getProtocolId() { return 36; }
	Integer a;
	public Integer getMode() { return this.a;}  // 0 全部同步 1 更新同步
	public void setMode(Integer a) { this.a = a;}

	List< Friend > b = new ArrayList< Friend >();   // 好友列表
	public List< Friend > getFriends() { return this.b;}
	public void setFriends(List< Friend > b) { this.b = b;}

	public static GDL_G2C_GetFriendListRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_GetFriendListRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
