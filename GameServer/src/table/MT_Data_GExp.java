package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GExp extends MT_DataBase {
    public static String MD5 = "24696e632ffab47f5596940e16da0438";
    private Integer m_nlevel ;
    /** 段位名称 */
    public Integer level() { return m_nlevel; }
    public Integer getM_nlevel() {return m_nlevel; }
    /** 段位名称 */
    public Integer ID() { return m_nlevel; }
    private long m_nexp ;
    /** 升到这一级需要净赢 */
    public long exp() { return m_nexp; }
    public long getM_nexp() {return m_nexp; }
    public static MT_Data_GExp ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GExp Data = new MT_Data_GExp();
		Data.m_nlevel = reader.getInt();
		Data.m_nexp = reader.getLong();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nlevel)) return false;
        if (!TableUtil.IsInvalid(this.m_nexp)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("level"))
            return level();
        if (str.equals("exp"))
            return exp();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("level =" + level() + '\n');
        str += ("exp =" + exp() + '\n');
        return str;
    }
}