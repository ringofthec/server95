package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_ip_lib extends MT_DataBase {
    public static String MD5 = "dd13635a9bd2140e15ec83a12bcb93ef";
    private String m_sindex ;
    /** 索引 */
    public String index() { return m_sindex; }
    public String getM_sindex() {return m_sindex; }
    /** 索引 */
    public String ID() { return m_sindex; }
    private String m_sname ;
    /** 中文名称 */
    public String name() { return m_sname; }
    public String getM_sname() {return m_sname; }
    public static MT_Data_ip_lib ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_ip_lib Data = new MT_Data_ip_lib();
		Data.m_sindex = TableUtil.ReadString(reader);
		Data.m_sname = TableUtil.ReadString(reader);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_sindex)) return false;
        if (!TableUtil.IsInvalid(this.m_sname)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("name"))
            return name();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("name =" + name() + '\n');
        return str;
    }
}