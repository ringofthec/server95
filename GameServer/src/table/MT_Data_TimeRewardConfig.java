package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_TimeRewardConfig extends MT_DataBase {
    public static String MD5 = "1501530288428ba5ac750a467d60cf29";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private Int2 m_Reward ;
    /** 大转盘第一次抽取奖励 */
    public Int2 Reward() { return m_Reward; }
    public Int2 getM_Reward() {return m_Reward; }
    private Integer m_ntimes ;
    /** 幸运开启的奖励倍数 */
    public Integer times() { return m_ntimes; }
    public Integer getM_ntimes() {return m_ntimes; }
    public static MT_Data_TimeRewardConfig ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_TimeRewardConfig Data = new MT_Data_TimeRewardConfig();
		Data.m_nindex = reader.getInt();
		Data.m_Reward = Int2.ReadMemory(reader, fileName);
		Data.m_ntimes = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_Reward)) return false;
        if (!TableUtil.IsInvalid(this.m_ntimes)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("Reward"))
            return Reward();
        if (str.equals("times"))
            return times();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("Reward =" + Reward() + '\n');
        str += ("times =" + times() + '\n');
        return str;
    }
}