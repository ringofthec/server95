package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GCost extends MT_DataBase {
    public static String MD5 = "5f0736e522b3eec7b8522cc7623b2ffc";
    private Integer m_ntype ;
    /** 消耗编号而已 */
    public Integer type() { return m_ntype; }
    public Integer getM_ntype() {return m_ntype; }
    /** 消耗编号而已 */
    public Integer ID() { return m_ntype; }
    private Integer m_ncosttype ;
    /** 消耗类型 */
    public Integer costtype() { return m_ncosttype; }
    public Integer getM_ncosttype() {return m_ncosttype; }
    private Integer m_ncostnum ;
    /** 消耗数量 */
    public Integer costnum() { return m_ncostnum; }
    public Integer getM_ncostnum() {return m_ncostnum; }
    private Integer m_nisShow ;
    /** 客户端是否跳出消耗提升 */
    public Integer isShow() { return m_nisShow; }
    public Integer getM_nisShow() {return m_nisShow; }
    private String m_scontent ;
    /** 提升内容 */
    public String content() { return m_scontent; }
    public String getM_scontent() {return m_scontent; }
    public static MT_Data_GCost ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GCost Data = new MT_Data_GCost();
		Data.m_ntype = reader.getInt();
		Data.m_ncosttype = reader.getInt();
		Data.m_ncostnum = reader.getInt();
		Data.m_nisShow = reader.getInt();
		Data.m_scontent = TableUtil.ReadLString(reader, fileName,"content",Data.ID());
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_ntype)) return false;
        if (!TableUtil.IsInvalid(this.m_ncosttype)) return false;
        if (!TableUtil.IsInvalid(this.m_ncostnum)) return false;
        if (!TableUtil.IsInvalid(this.m_nisShow)) return false;
        if (!TableUtil.IsInvalid(this.m_scontent)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("type"))
            return type();
        if (str.equals("costtype"))
            return costtype();
        if (str.equals("costnum"))
            return costnum();
        if (str.equals("isShow"))
            return isShow();
        if (str.equals("content"))
            return content();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("type =" + type() + '\n');
        str += ("costtype =" + costtype() + '\n');
        str += ("costnum =" + costnum() + '\n');
        str += ("isShow =" + isShow() + '\n');
        str += ("content =" + content() + '\n');
        return str;
    }
}