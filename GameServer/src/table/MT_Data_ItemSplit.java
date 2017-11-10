package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_ItemSplit extends MT_DataBase {
    public static String MD5 = "71af08c406408dffa8e001c41aadf19b";
    private Integer m_nlevel ;
    /** 强化等级 */
    public Integer level() { return m_nlevel; }
    public Integer getM_nlevel() {return m_nlevel; }
    /** 强化等级 */
    public Integer ID() { return m_nlevel; }
    private Integer m_nitems ;
    /** 返回材料 */
    public Integer items() { return m_nitems; }
    public Integer getM_nitems() {return m_nitems; }
    public static MT_Data_ItemSplit ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_ItemSplit Data = new MT_Data_ItemSplit();
		Data.m_nlevel = reader.getInt();
		Data.m_nitems = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nlevel)) return false;
        if (!TableUtil.IsInvalid(this.m_nitems)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("level"))
            return level();
        if (str.equals("items"))
            return items();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("level =" + level() + '\n');
        str += ("items =" + items() + '\n');
        return str;
    }
}