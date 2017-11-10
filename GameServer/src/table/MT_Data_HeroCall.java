package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_HeroCall extends MT_DataBase {
    public static String MD5 = "dbd5d76a51d8d524ac195e4bdb5c2272";
    private Integer m_nheronum ;
    /** 英雄个数 */
    public Integer heronum() { return m_nheronum; }
    public Integer getM_nheronum() {return m_nheronum; }
    /** 英雄个数 */
    public Integer ID() { return m_nheronum; }
    private Integer m_nlevelneed ;
    /** 英雄招募等级 */
    public Integer levelneed() { return m_nlevelneed; }
    public Integer getM_nlevelneed() {return m_nlevelneed; }
    private Integer m_ngoldneed ;
    /** 金砖消耗 */
    public Integer goldneed() { return m_ngoldneed; }
    public Integer getM_ngoldneed() {return m_ngoldneed; }
    public static MT_Data_HeroCall ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_HeroCall Data = new MT_Data_HeroCall();
		Data.m_nheronum = reader.getInt();
		Data.m_nlevelneed = reader.getInt();
		Data.m_ngoldneed = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nheronum)) return false;
        if (!TableUtil.IsInvalid(this.m_nlevelneed)) return false;
        if (!TableUtil.IsInvalid(this.m_ngoldneed)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("heronum"))
            return heronum();
        if (str.equals("levelneed"))
            return levelneed();
        if (str.equals("goldneed"))
            return goldneed();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("heronum =" + heronum() + '\n');
        str += ("levelneed =" + levelneed() + '\n');
        str += ("goldneed =" + goldneed() + '\n');
        return str;
    }
}