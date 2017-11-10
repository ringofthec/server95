package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_SetGiftShowRe extends JsonPacket {
	public int getProtocolId() { return 26; }
	List< GiftShowInfo > a = new ArrayList< GiftShowInfo >();   // 礼物展示列表
	public List< GiftShowInfo > getShowGiftList() { return this.a;}
	public void setShowGiftList(List< GiftShowInfo > a) { this.a = a;}

	public static GDL_G2C_SetGiftShowRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_SetGiftShowRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
