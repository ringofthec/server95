package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GBJLHelpTable extends MT_DataBase {
    public static String MD5 = "6ca7c1fa4d7229387859fbd6fc952a90";
    private Integer m_nid ;
    /** id */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** id */
    public Integer ID() { return m_nid; }
    private String m_spoint ;
    /** 兩張牌點數合計 */
    public String point() { return m_spoint; }
    public String getM_spoint() {return m_spoint; }
    private String m_splayer ;
    /** 閑 */
    public String player() { return m_splayer; }
    public String getM_splayer() {return m_splayer; }
    private String m_sbanker ;
    /** 莊 */
    public String banker() { return m_sbanker; }
    public String getM_sbanker() {return m_sbanker; }
    public static MT_Data_GBJLHelpTable ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GBJLHelpTable Data = new MT_Data_GBJLHelpTable();
		Data.m_nid = reader.getInt();
		Data.m_spoint = TableUtil.ReadString(reader);
		Data.m_splayer = TableUtil.ReadString(reader);
		Data.m_sbanker = TableUtil.ReadString(reader);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_spoint)) return false;
        if (!TableUtil.IsInvalid(this.m_splayer)) return false;
        if (!TableUtil.IsInvalid(this.m_sbanker)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("point"))
            return point();
        if (str.equals("player"))
            return player();
        if (str.equals("banker"))
            return banker();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("point =" + point() + '\n');
        str += ("player =" + player() + '\n');
        str += ("banker =" + banker() + '\n');
        return str;
    }
}