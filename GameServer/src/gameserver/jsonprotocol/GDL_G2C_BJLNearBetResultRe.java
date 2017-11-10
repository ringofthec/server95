package gameserver.jsonprotocol;
import java.util.List;
import java.util.ArrayList;
import com.commons.util.JsonUtil;
import com.commons.network.websock.JsonPacket;

public final class GDL_G2C_BJLNearBetResultRe extends JsonPacket {
	public int getProtocolId() { return 363; }
	List< Integer > a = new ArrayList< Integer >();   // 珠盘路 = 庄对(2) * 100 + 闲对(3) * 10 + 庄(0)/闲(1)/和(4) , 比如201 表示闲赢且庄对  30 表示庄赢且闲对, 绘图时请注意，珠盘路是连续的，每盘都是一个新的珠子依次排
	public List< Integer > getZhupanlu() { return this.a;}
	public void setZhupanlu(List< Integer > a) { this.a = a;}

	List< Integer > b = new ArrayList< Integer >();   // 10 + n 表示庄赢，并且之后有n次平局, 60 + n 表示闲赢，并且之后有n次平局，绘图时请注意，赢家变化时另起一列开始画图，否则往下画，往下没有位置的话开始往右画
	public List< Integer > getDalu() { return this.b;}
	public void setDalu(List< Integer > b) { this.b = b;}

	List< BJLXiaolu > c = new ArrayList< BJLXiaolu >();   // 大眼仔，每个BJLXiaolu代表一列
	public List< BJLXiaolu > getDayanzai() { return this.c;}
	public void setDayanzai(List< BJLXiaolu > c) { this.c = c;}

	List< BJLXiaolu > d = new ArrayList< BJLXiaolu >();   // 小路，每个BJLXiaolu代表一列
	public List< BJLXiaolu > getXiaolu() { return this.d;}
	public void setXiaolu(List< BJLXiaolu > d) { this.d = d;}

	List< BJLXiaolu > e = new ArrayList< BJLXiaolu >();   // 曱甴路，每个BJLXiaolu代表一列
	public List< BJLXiaolu > getYueyoulu() { return this.e;}
	public void setYueyoulu(List< BJLXiaolu > e) { this.e = e;}

	public static GDL_G2C_BJLNearBetResultRe parse(String data) {
		 return JsonUtil.JsonToObject(data, GDL_G2C_BJLNearBetResultRe.class); 
	}

	public String json() {
		 return JsonUtil.ObjectToJson(this); 
	}

}
