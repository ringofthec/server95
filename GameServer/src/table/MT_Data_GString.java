package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GString extends MT_DataBase {
    public static String MD5 = "6ca7c1fa4d7229387859fbd6fc952a90";
    private Integer m_nid ;
    /** 标识位 */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** 标识位 */
    public Integer ID() { return m_nid; }
    private String m_skey ;
    /** key */
    public String key() { return m_skey; }
    public String getM_skey() {return m_skey; }
    private String m_sen ;
    /** EN */
    public String en() { return m_sen; }
    public String getM_sen() {return m_sen; }
    private String m_scn ;
    /** CN */
    public String cn() { return m_scn; }
    public String getM_scn() {return m_scn; }
    public static MT_Data_GString ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GString Data = new MT_Data_GString();
		Data.m_nid = reader.getInt();
		Data.m_skey = TableUtil.ReadString(reader);
		Data.m_sen = TableUtil.ReadString(reader);
		Data.m_scn = TableUtil.ReadString(reader);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_skey)) return false;
        if (!TableUtil.IsInvalid(this.m_sen)) return false;
        if (!TableUtil.IsInvalid(this.m_scn)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("key"))
            return key();
        if (str.equals("en"))
            return en();
        if (str.equals("cn"))
            return cn();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("key =" + key() + '\n');
        str += ("en =" + en() + '\n');
        str += ("cn =" + cn() + '\n');
        return str;
    }
}