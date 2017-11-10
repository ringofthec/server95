package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GSlotsLotty extends MT_DataBase {
    public static String MD5 = "dbd5d76a51d8d524ac195e4bdb5c2272";
    private Integer m_nid ;
    /** id */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** id */
    public Integer ID() { return m_nid; }
    private Integer m_nrate ;
    /** 奖励倍数 */
    public Integer rate() { return m_nrate; }
    public Integer getM_nrate() {return m_nrate; }
    private Integer m_nsvr_rate ;
    /** 奖励概率 */
    public Integer svr_rate() { return m_nsvr_rate; }
    public Integer getM_nsvr_rate() {return m_nsvr_rate; }
    public static MT_Data_GSlotsLotty ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GSlotsLotty Data = new MT_Data_GSlotsLotty();
		Data.m_nid = reader.getInt();
		Data.m_nrate = reader.getInt();
		Data.m_nsvr_rate = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_nrate)) return false;
        if (!TableUtil.IsInvalid(this.m_nsvr_rate)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("rate"))
            return rate();
        if (str.equals("svr_rate"))
            return svr_rate();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("rate =" + rate() + '\n');
        str += ("svr_rate =" + svr_rate() + '\n');
        return str;
    }
}