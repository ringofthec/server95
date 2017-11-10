package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class BJLBetResult extends JsonPacket {
	Integer a;
	public Integer getZhupanlu() { return this.a;}  // 珠盘路 = 庄对(2) * 100 + 闲对(3) * 10 + 庄(0)/闲(1)/和(4) , 比如201 表示闲赢且庄对  30 表示庄赢且闲对, 绘图时请注意，珠盘路是连续的，每盘都是一个新的珠子依次排
	public void setZhupanlu(Integer a) { this.a = a;}

	BJLSlu b;   // 大路
	public BJLSlu getDalu() { return this.b;}
	public void setDalu(BJLSlu b) { this.b = b;}

	BJLSlu c;   // 大眼仔
	public BJLSlu getDayanzai() { return this.c;}
	public void setDayanzai(BJLSlu c) { this.c = c;}

	BJLSlu d;   // 小路
	public BJLSlu getXiaolu() { return this.d;}
	public void setXiaolu(BJLSlu d) { this.d = d;}

	BJLSlu e;   // 曱甴路
	public BJLSlu getYueyoulu() { return this.e;}
	public void setYueyoulu(BJLSlu e) { this.e = e;}

	public static BJLBetResult parse(String data) {
		 return JsonUtil.JsonToObject(data, BJLBetResult.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
