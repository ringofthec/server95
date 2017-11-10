package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GNotice extends MT_DataBase {
    public static String MD5 = "702b00bdb6f84dd27d39a831a2e1c24d";
    private Integer m_nindex ;
    /** 序号 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 序号 */
    public Integer ID() { return m_nindex; }
    private Integer m_nsendtype ;
    /** 广播 */
    public Integer sendtype() { return m_nsendtype; }
    public Integer getM_nsendtype() {return m_nsendtype; }
    private Integer m_nmode ;
    /** 显示方式 */
    public Integer mode() { return m_nmode; }
    public Integer getM_nmode() {return m_nmode; }
    private String m_stext ;
    /** 提示文字 */
    public String text() { return m_stext; }
    public String getM_stext() {return m_stext; }
    public static MT_Data_GNotice ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GNotice Data = new MT_Data_GNotice();
		Data.m_nindex = reader.getInt();
		Data.m_nsendtype = reader.getInt();
		Data.m_nmode = reader.getInt();
		Data.m_stext = TableUtil.ReadLString(reader, fileName,"text",Data.ID());
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_nsendtype)) return false;
        if (!TableUtil.IsInvalid(this.m_nmode)) return false;
        if (!TableUtil.IsInvalid(this.m_stext)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("sendtype"))
            return sendtype();
        if (str.equals("mode"))
            return mode();
        if (str.equals("text"))
            return text();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("sendtype =" + sendtype() + '\n');
        str += ("mode =" + mode() + '\n');
        str += ("text =" + text() + '\n');
        return str;
    }
}