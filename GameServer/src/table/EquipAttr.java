package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class EquipAttr extends MT_DataBase {
    public static String MD5 = "f392ceb64152992a2ea8afda46069812";
    private Integer m_ntype ;
    /**  */
    public Integer type() { return m_ntype; }
    public Integer getM_ntype() {return m_ntype; }
    private Integer m_nnum ;
    /**  */
    public Integer num() { return m_nnum; }
    public Integer getM_nnum() {return m_nnum; }
    public static EquipAttr ReadMemory ( ByteBuffer reader, String fileName ) {
        EquipAttr Data = new EquipAttr();
		Data.m_ntype = reader.getInt();
		Data.m_nnum = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_ntype)) return false;
        if (!TableUtil.IsInvalid(this.m_nnum)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("type"))
            return type();
        if (str.equals("num"))
            return num();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("type =" + type() + '\n');
        str += ("num =" + num() + '\n');
        return str;
    }
}