package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_RotaryReward extends MT_DataBase {
    public static String MD5 = "4a5779147612b2c37eb3ca09150bca55";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private Int3 m_rewards1 ;
    /** 奖品套系1 */
    public Int3 rewards1() { return m_rewards1; }
    public Int3 getM_rewards1() {return m_rewards1; }
    private Int3 m_rewards2 ;
    /** 奖品套系2 */
    public Int3 rewards2() { return m_rewards2; }
    public Int3 getM_rewards2() {return m_rewards2; }
    private Int3 m_rewards3 ;
    /** 奖品套系3 */
    public Int3 rewards3() { return m_rewards3; }
    public Int3 getM_rewards3() {return m_rewards3; }
    private Int3 m_rewards4 ;
    /** 奖品套系4 */
    public Int3 rewards4() { return m_rewards4; }
    public Int3 getM_rewards4() {return m_rewards4; }
    private Int3 m_rewards5 ;
    /** 奖品套系5 */
    public Int3 rewards5() { return m_rewards5; }
    public Int3 getM_rewards5() {return m_rewards5; }
    private Int3 m_rewards6 ;
    /** 奖品套系6 */
    public Int3 rewards6() { return m_rewards6; }
    public Int3 getM_rewards6() {return m_rewards6; }
    private Int3 m_rewards7 ;
    /** 奖品套系7 */
    public Int3 rewards7() { return m_rewards7; }
    public Int3 getM_rewards7() {return m_rewards7; }
    private Int3 m_rewards8 ;
    /** 奖品套系8 */
    public Int3 rewards8() { return m_rewards8; }
    public Int3 getM_rewards8() {return m_rewards8; }
    private Int3 m_rewards9 ;
    /** 奖品套系9 */
    public Int3 rewards9() { return m_rewards9; }
    public Int3 getM_rewards9() {return m_rewards9; }
    private Int3 m_rewards10 ;
    /** 奖品套系10 */
    public Int3 rewards10() { return m_rewards10; }
    public Int3 getM_rewards10() {return m_rewards10; }
    public static MT_Data_RotaryReward ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_RotaryReward Data = new MT_Data_RotaryReward();
		Data.m_nindex = reader.getInt();
		Data.m_rewards1 = Int3.ReadMemory(reader, fileName);
		Data.m_rewards2 = Int3.ReadMemory(reader, fileName);
		Data.m_rewards3 = Int3.ReadMemory(reader, fileName);
		Data.m_rewards4 = Int3.ReadMemory(reader, fileName);
		Data.m_rewards5 = Int3.ReadMemory(reader, fileName);
		Data.m_rewards6 = Int3.ReadMemory(reader, fileName);
		Data.m_rewards7 = Int3.ReadMemory(reader, fileName);
		Data.m_rewards8 = Int3.ReadMemory(reader, fileName);
		Data.m_rewards9 = Int3.ReadMemory(reader, fileName);
		Data.m_rewards10 = Int3.ReadMemory(reader, fileName);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_rewards1)) return false;
        if (!TableUtil.IsInvalid(this.m_rewards2)) return false;
        if (!TableUtil.IsInvalid(this.m_rewards3)) return false;
        if (!TableUtil.IsInvalid(this.m_rewards4)) return false;
        if (!TableUtil.IsInvalid(this.m_rewards5)) return false;
        if (!TableUtil.IsInvalid(this.m_rewards6)) return false;
        if (!TableUtil.IsInvalid(this.m_rewards7)) return false;
        if (!TableUtil.IsInvalid(this.m_rewards8)) return false;
        if (!TableUtil.IsInvalid(this.m_rewards9)) return false;
        if (!TableUtil.IsInvalid(this.m_rewards10)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("rewards1"))
            return rewards1();
        if (str.equals("rewards2"))
            return rewards2();
        if (str.equals("rewards3"))
            return rewards3();
        if (str.equals("rewards4"))
            return rewards4();
        if (str.equals("rewards5"))
            return rewards5();
        if (str.equals("rewards6"))
            return rewards6();
        if (str.equals("rewards7"))
            return rewards7();
        if (str.equals("rewards8"))
            return rewards8();
        if (str.equals("rewards9"))
            return rewards9();
        if (str.equals("rewards10"))
            return rewards10();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("rewards1 =" + rewards1() + '\n');
        str += ("rewards2 =" + rewards2() + '\n');
        str += ("rewards3 =" + rewards3() + '\n');
        str += ("rewards4 =" + rewards4() + '\n');
        str += ("rewards5 =" + rewards5() + '\n');
        str += ("rewards6 =" + rewards6() + '\n');
        str += ("rewards7 =" + rewards7() + '\n');
        str += ("rewards8 =" + rewards8() + '\n');
        str += ("rewards9 =" + rewards9() + '\n');
        str += ("rewards10 =" + rewards10() + '\n');
        return str;
    }
}