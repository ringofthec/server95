package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_GetBattleRecordRe extends JsonPacket {
	public int getProtocolId() { return 34; }
	Long a;
	public Long getPlayerId() { return this.a;}  // 
	public void setPlayerId(Long a) { this.a = a;}

	Integer b;
	public Integer getFunc() { return this.b;}  // 服务回传字段
	public void setFunc(Integer b) { this.b = b;}

	Long c;
	public Long getTotal() { return this.c;}  // 总赢取
	public void setTotal(Long c) { this.c = c;}

	Long d;
	public Long getMax_day() { return this.d;}  // 单日最高
	public void setMax_day(Long d) { this.d = d;}

	List< Long > e = new ArrayList< Long >();   // 7日赢取
	public List< Long > getDays7() { return this.e;}
	public void setDays7(List< Long > e) { this.e = e;}

	Long f;
	public Long getFruits_slot_total() { return this.f;}  // 水果slots累计总赢取
	public void setFruits_slot_total(Long f) { this.f = f;}

	Long g;
	public Long getFruits_slot_max() { return this.g;}  // 水果slots最高单次赢取
	public void setFruits_slot_max(Long g) { this.g = g;}

	Long h;
	public Long getFruits_slot_count() { return this.h;}  // 水果slots游戏局数
	public void setFruits_slot_count(Long h) { this.h = h;}

	Long i;
	public Long getFruits_slot_rose_top3_count() { return this.i;}  // 水果slots玫瑰赛前三次数
	public void setFruits_slot_rose_top3_count(Long i) { this.i = i;}

	Long j;
	public Long getFruits_slot_pool_total() { return this.j;}  // 水果slots游戏奖池总中奖金额
	public void setFruits_slot_pool_total(Long j) { this.j = j;}

	Integer k;
	public Integer getFruits_slot_pool_count() { return this.k;}  // 水果slots游戏奖池总中奖次数
	public void setFruits_slot_pool_count(Integer k) { this.k = k;}

	Long l;
	public Long getGold_slot_total() { return this.l;}  // 金砖slots累计总赢取
	public void setGold_slot_total(Long l) { this.l = l;}

	Long m;
	public Long getGold_slot_max() { return this.m;}  // 金砖slots最高单次赢取
	public void setGold_slot_max(Long m) { this.m = m;}

	Long n;
	public Long getGold_slot_count() { return this.n;}  // 金砖slots参与比赛次数
	public void setGold_slot_count(Long n) { this.n = n;}

	Long o;
	public Long getGold_slot_gold() { return this.o;}  // 金砖slots赢取金砖数
	public void setGold_slot_gold(Long o) { this.o = o;}

	Long p;
	public Long getGold_slot_pool_total() { return this.p;}  // 金砖slots游戏奖池总中奖金额
	public void setGold_slot_pool_total(Long p) { this.p = p;}

	Integer q;
	public Integer getGold_slot_pool_count() { return this.q;}  // 金砖slots游戏奖池总中奖次数
	public void setGold_slot_pool_count(Integer q) { this.q = q;}

	Long r;
	public Long getFish_total() { return this.r;}  // 捕鱼游戏总赢取
	public void setFish_total(Long r) { this.r = r;}

	Long s;
	public Long getFish_max() { return this.s;}  // 捕鱼游戏最高单次赢取
	public void setFish_max(Long s) { this.s = s;}

	Long t;
	public Long getFish_battle_kingfish() { return this.t;}  // 捕鱼游戏击杀金龙鱼次数
	public void setFish_battle_kingfish(Long t) { this.t = t;}

	Long u;
	public Long getFish_battle_doomfish() { return this.u;}  // 捕鱼游戏击杀魔鬼鱼次数
	public void setFish_battle_doomfish(Long u) { this.u = u;}

	Long v;
	public Long getFish_task_count() { return this.v;}  // 捕鱼游戏完成任务数
	public void setFish_task_count(Long v) { this.v = v;}

	Long w;
	public Long getFish_catch_fish_total() { return this.w;}  // 捕鱼游戏捕鱼总条数
	public void setFish_catch_fish_total(Long w) { this.w = w;}

	List< String > x = new ArrayList< String >();   // 里程碑
	public List< String > getMillstone() { return this.x;}
	public void setMillstone(List< String > x) { this.x = x;}

	public static GDL_G2C_GetBattleRecordRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_GetBattleRecordRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
