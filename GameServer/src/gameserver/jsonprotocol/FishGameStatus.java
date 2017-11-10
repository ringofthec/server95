package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class FishGameStatus extends JsonPacket {
	Integer a;
	public Integer getPass_time() { return this.a;}  // 游戏流逝时间 =  正常时间 - 冰冻时间 毫秒
	public void setPass_time(Integer a) { this.a = a;}

	Integer b;
	public Integer getFrozening_time() { return this.b;}  // 剩余冰冻时间， 如果这个大于0，说明进入场景时别的玩家正在释放冰冻技能，这里说明了还有几秒解冻
	public void setFrozening_time(Integer b) { this.b = b;}

	Integer c;
	public Integer getSceneId() { return this.c;}  // 场景id
	public void setSceneId(Integer c) { this.c = c;}

	Integer d;
	public Integer getTaskComplate() { return this.d;}  // 如果是任务场景的话，这个表示任务有没有被完成 0 没完成 1 完成
	public void setTaskComplate(Integer d) { this.d = d;}

	List< FishGamePlayerInfo > e = new ArrayList< FishGamePlayerInfo >();   // 玩家信息
	public List< FishGamePlayerInfo > getPlayerInfos() { return this.e;}
	public void setPlayerInfos(List< FishGamePlayerInfo > e) { this.e = e;}

	List< SummoneSync > f = new ArrayList< SummoneSync >();   // 召唤大鱼信息
	public List< SummoneSync > getSummoneInfos() { return this.f;}
	public void setSummoneInfos(List< SummoneSync > f) { this.f = f;}

	public static FishGameStatus parse(String data) {
		 return JsonUtil.JsonToObject(data, FishGameStatus.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
