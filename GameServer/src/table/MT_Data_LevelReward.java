package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_LevelReward extends MT_DataBase {
    public static String MD5 = "079021b6487fea4193aac0e5348df5d7";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private Integer m_nlev ;
    /** 等级 */
    public Integer lev() { return m_nlev; }
    public Integer getM_nlev() {return m_nlev; }
    private Int2 m_Reward1 ;
    /** 奖励1 */
    public Int2 Reward1() { return m_Reward1; }
    public Int2 getM_Reward1() {return m_Reward1; }
    private Int2 m_Reward2 ;
    /** 奖励2 */
    public Int2 Reward2() { return m_Reward2; }
    public Int2 getM_Reward2() {return m_Reward2; }
    private Int2 m_Reward3 ;
    /** 奖励3 */
    public Int2 Reward3() { return m_Reward3; }
    public Int2 getM_Reward3() {return m_Reward3; }
    public static MT_Data_LevelReward ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_LevelReward Data = new MT_Data_LevelReward();
		Data.m_nindex = reader.getInt();
		Data.m_nlev = reader.getInt();
		Data.m_Reward1 = Int2.ReadMemory(reader, fileName);
		Data.m_Reward2 = Int2.ReadMemory(reader, fileName);
		Data.m_Reward3 = Int2.ReadMemory(reader, fileName);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_nlev)) return false;
        if (!TableUtil.IsInvalid(this.m_Reward1)) return false;
        if (!TableUtil.IsInvalid(this.m_Reward2)) return false;
        if (!TableUtil.IsInvalid(this.m_Reward3)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("lev"))
            return lev();
        if (str.equals("Reward1"))
            return Reward1();
        if (str.equals("Reward2"))
            return Reward2();
        if (str.equals("Reward3"))
            return Reward3();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("lev =" + lev() + '\n');
        str += ("Reward1 =" + Reward1() + '\n');
        str += ("Reward2 =" + Reward2() + '\n');
        str += ("Reward3 =" + Reward3() + '\n');
        return str;
    }
}