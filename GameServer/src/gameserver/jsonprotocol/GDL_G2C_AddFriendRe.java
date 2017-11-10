package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_AddFriendRe extends JsonPacket {
	public int getProtocolId() { return 31; }
	Integer a;
	public Integer getRetCode() { return this.a;}  // 0 成功，1 你的好友太多啦， 3 对方拒绝被添加为好友 4 他已经是你的好友啦 5 其他奇怪的错误，比如数据库操作失败, 6 没有这个玩家
	public void setRetCode(Integer a) { this.a = a;}

	public static GDL_G2C_AddFriendRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_AddFriendRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
