package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GEgg extends MT_DataBase {
    public static String MD5 = "fe800bd5e093bdd4dbe6014ae01b66aa";
    private Integer m_nid ;
    /** 编号 */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** 编号 */
    public Integer ID() { return m_nid; }
    private Integer m_ncostid ;
    /** 消耗(GCost表中的id) */
    public Integer costid() { return m_ncostid; }
    public Integer getM_ncostid() {return m_ncostid; }
    private Int2 m_svr_cashfree ;
    /** 免费奖券获得 */
    public Int2 svr_cashfree() { return m_svr_cashfree; }
    public Int2 getM_svr_cashfree() {return m_svr_cashfree; }
    private Int2 m_svr_cashmoney ;
    /** 付费奖券获得 */
    public Int2 svr_cashmoney() { return m_svr_cashmoney; }
    public Int2 getM_svr_cashmoney() {return m_svr_cashmoney; }
    public static MT_Data_GEgg ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GEgg Data = new MT_Data_GEgg();
		Data.m_nid = reader.getInt();
		Data.m_ncostid = reader.getInt();
		Data.m_svr_cashfree = Int2.ReadMemory(reader, fileName);
		Data.m_svr_cashmoney = Int2.ReadMemory(reader, fileName);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_ncostid)) return false;
        if (!TableUtil.IsInvalid(this.m_svr_cashfree)) return false;
        if (!TableUtil.IsInvalid(this.m_svr_cashmoney)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("costid"))
            return costid();
        if (str.equals("svr_cashfree"))
            return svr_cashfree();
        if (str.equals("svr_cashmoney"))
            return svr_cashmoney();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("costid =" + costid() + '\n');
        str += ("svr_cashfree =" + svr_cashfree() + '\n');
        str += ("svr_cashmoney =" + svr_cashmoney() + '\n');
        return str;
    }
}