package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_PushEvent extends MT_DataBase {
    public static String MD5 = "2e7bc20c3572587aa8ca13d9cf93a412";
    private Integer m_nindex ;
    /** 推送事件 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 推送事件 */
    public Integer ID() { return m_nindex; }
    private String m_sType ;
    /** 类型 */
    public String Type() { return m_sType; }
    public String getM_sType() {return m_sType; }
    public static MT_Data_PushEvent ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_PushEvent Data = new MT_Data_PushEvent();
		Data.m_nindex = reader.getInt();
		Data.m_sType = TableUtil.ReadLString(reader, fileName,"Type",Data.ID());
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_sType)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("Type"))
            return Type();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("Type =" + Type() + '\n');
        return str;
    }
}