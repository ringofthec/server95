package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_RechargeActive extends MT_DataBase {
    public static String MD5 = "9408e27895918f296c0629160b490006";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private Integer m_nactiveid ;
    /** 活动id */
    public Integer activeid() { return m_nactiveid; }
    public Integer getM_nactiveid() {return m_nactiveid; }
    private Integer m_nisResetOnFinish ;
    /** 活动结束后重置额度 */
    public Integer isResetOnFinish() { return m_nisResetOnFinish; }
    public Integer getM_nisResetOnFinish() {return m_nisResetOnFinish; }
    private Integer m_nclac ;
    /** 额度 */
    public Integer clac() { return m_nclac; }
    public Integer getM_nclac() {return m_nclac; }
    private ArrayList<Int2> m_arrayrewards = new ArrayList<Int2>();
    /** 奖励 */
    public ArrayList<Int2> rewards() { return m_arrayrewards; }
    public ArrayList<Int2> getM_arrayrewards() {return m_arrayrewards; }
    private ArrayList<Int3> m_arrayrewardsbyhero = new ArrayList<Int3>();
    /** 根据英雄类别奖励 */
    public ArrayList<Int3> rewardsbyhero() { return m_arrayrewardsbyhero; }
    public ArrayList<Int3> getM_arrayrewardsbyhero() {return m_arrayrewardsbyhero; }
    private Integer m_nworth ;
    /** 价值 */
    public Integer worth() { return m_nworth; }
    public Integer getM_nworth() {return m_nworth; }
    public static MT_Data_RechargeActive ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_RechargeActive Data = new MT_Data_RechargeActive();

        Integer count;
		Data.m_nindex = reader.getInt();
		Data.m_nactiveid = reader.getInt();
		Data.m_nisResetOnFinish = reader.getInt();
		Data.m_nclac = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayrewards.add(Int2.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayrewardsbyhero.add(Int3.ReadMemory(reader, fileName));
        }

		Data.m_nworth = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_nactiveid)) return false;
        if (!TableUtil.IsInvalid(this.m_nisResetOnFinish)) return false;
        if (!TableUtil.IsInvalid(this.m_nclac)) return false;
        if (this.m_arrayrewards.size() > 0) return false;
        if (this.m_arrayrewardsbyhero.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_nworth)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("activeid"))
            return activeid();
        if (str.equals("isResetOnFinish"))
            return isResetOnFinish();
        if (str.equals("clac"))
            return clac();
        if (str.equals("rewards"))
            return rewards();
        if (str.equals("rewardsbyhero"))
            return rewardsbyhero();
        if (str.equals("worth"))
            return worth();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("activeid =" + activeid() + '\n');
        str += ("isResetOnFinish =" + isResetOnFinish() + '\n');
        str += ("clac =" + clac() + '\n');
        str += ("rewards =" + rewards() + '\n');
        str += ("rewardsbyhero =" + rewardsbyhero() + '\n');
        str += ("worth =" + worth() + '\n');
        return str;
    }
}