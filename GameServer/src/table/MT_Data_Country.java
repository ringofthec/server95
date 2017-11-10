package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Country extends MT_DataBase {
    public static String MD5 = "420b0ea7d1e9f1404ea95eeecd7e895c";
    private String m_sIndex ;
    /** 关键字 */
    public String Index() { return m_sIndex; }
    public String getM_sIndex() {return m_sIndex; }
    /** 关键字 */
    public String ID() { return m_sIndex; }
    private String m_sText ;
    /** 文字 */
    public String Text() { return m_sText; }
    public String getM_sText() {return m_sText; }
    public static MT_Data_Country ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Country Data = new MT_Data_Country();
		Data.m_sIndex = TableUtil.ReadString(reader);
		Data.m_sText = TableUtil.ReadLString(reader, fileName,"Text",Data.ID());
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_sIndex)) return false;
        if (!TableUtil.IsInvalid(this.m_sText)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("Index"))
            return Index();
        if (str.equals("Text"))
            return Text();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("Index =" + Index() + '\n');
        str += ("Text =" + Text() + '\n');
        return str;
    }
}