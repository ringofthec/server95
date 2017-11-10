package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GNiuCardStraight extends MT_DataBase {
    public static String MD5 = "29f35e40a7fbe8ff024cb0b67b1382d3";
    private Integer m_nid ;
    /** id */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** id */
    public Integer ID() { return m_nid; }
    private String m_sname ;
    /** 牌型名字 */
    public String name() { return m_sname; }
    public String getM_sname() {return m_sname; }
    private Integer m_nrate ;
    /** 倍率 */
    public Integer rate() { return m_nrate; }
    public Integer getM_nrate() {return m_nrate; }
    private Integer m_nran ;
    /** 概率 */
    public Integer ran() { return m_nran; }
    public Integer getM_nran() {return m_nran; }
    private String m_saudio ;
    /** 牌型对应的音效 */
    public String audio() { return m_saudio; }
    public String getM_saudio() {return m_saudio; }
    public static MT_Data_GNiuCardStraight ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GNiuCardStraight Data = new MT_Data_GNiuCardStraight();
		Data.m_nid = reader.getInt();
		Data.m_sname = TableUtil.ReadString(reader);
		Data.m_nrate = reader.getInt();
		Data.m_nran = reader.getInt();
		Data.m_saudio = TableUtil.ReadString(reader);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_sname)) return false;
        if (!TableUtil.IsInvalid(this.m_nrate)) return false;
        if (!TableUtil.IsInvalid(this.m_nran)) return false;
        if (!TableUtil.IsInvalid(this.m_saudio)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("name"))
            return name();
        if (str.equals("rate"))
            return rate();
        if (str.equals("ran"))
            return ran();
        if (str.equals("audio"))
            return audio();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("name =" + name() + '\n');
        str += ("rate =" + rate() + '\n');
        str += ("ran =" + ran() + '\n');
        str += ("audio =" + audio() + '\n');
        return str;
    }
}