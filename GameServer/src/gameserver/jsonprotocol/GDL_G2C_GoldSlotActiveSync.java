package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_GoldSlotActiveSync extends JsonPacket {
	public int getProtocolId() { return 126; }
	Integer a;
	public Integer getNum() { return this.a;}  // 报名人数
	public void setNum(Integer a) { this.a = a;}

	List< GoldActiveReward > b = new ArrayList< GoldActiveReward >();   // 金砖slots奖励
	public List< GoldActiveReward > getGoldSlotsRewards() { return this.b;}
	public void setGoldSlotsRewards(List< GoldActiveReward > b) { this.b = b;}

	public static GDL_G2C_GoldSlotActiveSync parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_GoldSlotActiveSync.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
