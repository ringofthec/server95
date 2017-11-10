package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GStrings extends MT_DataBase {
    public static String MD5 = "a9d0993a487c1ba555fc51b1ccc2a90d";
    private Integer m_nid ;
    /** 编号 */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** 编号 */
    public Integer ID() { return m_nid; }
    private String m_schinese ;
    /** 中文 */
    public String chinese() { return m_schinese; }
    public String getM_schinese() {return m_schinese; }
    private String m_senglish ;
    /** 英文 */
    public String english() { return m_senglish; }
    public String getM_senglish() {return m_senglish; }
    public static MT_Data_GStrings ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GStrings Data = new MT_Data_GStrings();
		Data.m_nid = reader.getInt();
		Data.m_schinese = TableUtil.ReadString(reader);
		Data.m_senglish = TableUtil.ReadString(reader);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_schinese)) return false;
        if (!TableUtil.IsInvalid(this.m_senglish)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("chinese"))
            return chinese();
        if (str.equals("english"))
            return english();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("chinese =" + chinese() + '\n');
        str += ("english =" + english() + '\n');
        return str;
    }
}