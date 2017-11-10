package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Luckdraw extends MT_DataBase {
    public static String MD5 = "dbd5d76a51d8d524ac195e4bdb5c2272";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private Integer m_nnum ;
    /** 抽奖次数 */
    public Integer num() { return m_nnum; }
    public Integer getM_nnum() {return m_nnum; }
    private Integer m_ntime ;
    /** 对应时长 */
    public Integer time() { return m_ntime; }
    public Integer getM_ntime() {return m_ntime; }
    public static MT_Data_Luckdraw ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Luckdraw Data = new MT_Data_Luckdraw();
		Data.m_nindex = reader.getInt();
		Data.m_nnum = reader.getInt();
		Data.m_ntime = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_nnum)) return false;
        if (!TableUtil.IsInvalid(this.m_ntime)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("num"))
            return num();
        if (str.equals("time"))
            return time();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("num =" + num() + '\n');
        str += ("time =" + time() + '\n');
        return str;
    }
}