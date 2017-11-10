package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GPromptInfo extends MT_DataBase {
    public static String MD5 = "7897dcf15da18852cd76bd8757e10b47";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private String m_scontent ;
    /** 内容 */
    public String content() { return m_scontent; }
    public String getM_scontent() {return m_scontent; }
    public static MT_Data_GPromptInfo ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GPromptInfo Data = new MT_Data_GPromptInfo();
		Data.m_nindex = reader.getInt();
		Data.m_scontent = TableUtil.ReadString(reader);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_scontent)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("content"))
            return content();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("content =" + content() + '\n');
        return str;
    }
}