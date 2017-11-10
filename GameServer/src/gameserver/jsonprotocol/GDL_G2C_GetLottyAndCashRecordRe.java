package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_GetLottyAndCashRecordRe extends JsonPacket {
	public int getProtocolId() { return 85; }
	List< LottyRecord > a = new ArrayList< LottyRecord >();   // 抽奖的记录
	public List< LottyRecord > getLottys() { return this.a;}
	public void setLottys(List< LottyRecord > a) { this.a = a;}

	List< CashRecord > b = new ArrayList< CashRecord >();   // 兑奖的记录
	public List< CashRecord > getCashs() { return this.b;}
	public void setCashs(List< CashRecord > b) { this.b = b;}

	public static GDL_G2C_GetLottyAndCashRecordRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_GetLottyAndCashRecordRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
