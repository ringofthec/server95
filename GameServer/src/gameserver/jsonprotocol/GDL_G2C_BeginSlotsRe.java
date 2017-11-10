package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_BeginSlotsRe extends JsonPacket {
	public int getProtocolId() { return 91; }
	Integer a;
	public Integer getSlotsType() { return this.a;}  // slot类型 1 水果 2 金砖
	public void setSlotsType(Integer a) { this.a = a;}

	Integer b;
	public Integer getRetCode() { return this.b;}  // 0 没中奖，1 普通 2 奖池奖励
	public void setRetCode(Integer b) { this.b = b;}

	Long c;
	public Long getRewardcoin() { return this.c;}  // 总赢取金币
	public void setRewardcoin(Long c) { this.c = c;}

	Integer d;
	public Integer getRewardrate() { return this.d;}  // 总赢取倍数
	public void setRewardrate(Integer d) { this.d = d;}

	List< Integer > e = new ArrayList< Integer >();   // 1~15个水果
	public List< Integer > getRes() { return this.e;}
	public void setRes(List< Integer > e) { this.e = e;}

	List< LineInfo > f = new ArrayList< LineInfo >();   // 中奖线信息
	public List< LineInfo > getLines() { return this.f;}
	public void setLines(List< LineInfo > f) { this.f = f;}

	Integer g;
	public Integer getGameType() { return this.g;}  // 0 没有小游戏 1 转盘 2 翻牌 3 猜花色
	public void setGameType(Integer g) { this.g = g;}

	SGLotty h;   // 
	public SGLotty getLotty() { return this.h;}
	public void setLotty(SGLotty h) { this.h = h;}

	SGOpen i;   // 
	public SGOpen getOpen() { return this.i;}
	public void setOpen(SGOpen i) { this.i = i;}

	public static GDL_G2C_BeginSlotsRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_BeginSlotsRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
