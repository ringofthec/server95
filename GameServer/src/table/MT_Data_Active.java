package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Active extends MT_DataBase {
    public static String MD5 = "39651d1f8325af373ca53f433c6cc61b";
    private Integer m_nactiveid ;
    /** 活动id */
    public Integer activeid() { return m_nactiveid; }
    public Integer getM_nactiveid() {return m_nactiveid; }
    /** 活动id */
    public Integer ID() { return m_nactiveid; }
    private String m_sSpriteName ;
    /** 相关脚本 */
    public String SpriteName() { return m_sSpriteName; }
    public String getM_sSpriteName() {return m_sSpriteName; }
    private String m_sactiveName ;
    /** 活动名称 */
    public String activeName() { return m_sactiveName; }
    public String getM_sactiveName() {return m_sactiveName; }
    public static MT_Data_Active ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Active Data = new MT_Data_Active();
		Data.m_nactiveid = reader.getInt();
		Data.m_sSpriteName = TableUtil.ReadString(reader);
		Data.m_sactiveName = TableUtil.ReadLString(reader, fileName,"activeName",Data.ID());
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nactiveid)) return false;
        if (!TableUtil.IsInvalid(this.m_sSpriteName)) return false;
        if (!TableUtil.IsInvalid(this.m_sactiveName)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("activeid"))
            return activeid();
        if (str.equals("SpriteName"))
            return SpriteName();
        if (str.equals("activeName"))
            return activeName();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("activeid =" + activeid() + '\n');
        str += ("SpriteName =" + SpriteName() + '\n');
        str += ("activeName =" + activeName() + '\n');
        return str;
    }
}