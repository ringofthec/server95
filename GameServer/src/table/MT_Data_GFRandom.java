package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GFRandom extends MT_DataBase {
    public static String MD5 = "71af08c406408dffa8e001c41aadf19b";
    private Integer m_nid ;
    /** 捕鱼随机策略id */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** 捕鱼随机策略id */
    public Integer ID() { return m_nid; }
    private Integer m_nrate ;
    /** 命中修正概率 */
    public Integer rate() { return m_nrate; }
    public Integer getM_nrate() {return m_nrate; }
    public static MT_Data_GFRandom ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GFRandom Data = new MT_Data_GFRandom();
		Data.m_nid = reader.getInt();
		Data.m_nrate = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_nrate)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("rate"))
            return rate();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("rate =" + rate() + '\n');
        return str;
    }
}