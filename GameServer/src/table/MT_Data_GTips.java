package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GTips extends MT_DataBase {
    public static String MD5 = "2e7bc20c3572587aa8ca13d9cf93a412";
    private Integer m_nindex ;
    /** 序号 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 序号 */
    public Integer ID() { return m_nindex; }
    private String m_stext ;
    /** 提示文字 */
    public String text() { return m_stext; }
    public String getM_stext() {return m_stext; }
    public static MT_Data_GTips ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GTips Data = new MT_Data_GTips();
		Data.m_nindex = reader.getInt();
		Data.m_stext = TableUtil.ReadLString(reader, fileName,"text",Data.ID());
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_stext)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("text"))
            return text();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("text =" + text() + '\n');
        return str;
    }
}