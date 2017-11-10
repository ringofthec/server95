package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GGamePolicy extends MT_DataBase {
    public static String MD5 = "f87e201f045302cdcbd0b1f819807f44";
    private Integer m_nid ;
    /** id */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** id */
    public Integer ID() { return m_nid; }
    private Int2 m_day_recharge ;
    /** 日充值区间 */
    public Int2 day_recharge() { return m_day_recharge; }
    public Int2 getM_day_recharge() {return m_day_recharge; }
    private ArrayList<Int2> m_arrayslots_times_limit = new ArrayList<Int2>();
    /** slots倍率区间 */
    public ArrayList<Int2> slots_times_limit() { return m_arrayslots_times_limit; }
    public ArrayList<Int2> getM_arrayslots_times_limit() {return m_arrayslots_times_limit; }
    private ArrayList<Long3> m_arrayxp_point = new ArrayList<Long3>();
    /** 体验点 */
    public ArrayList<Long3> xp_point() { return m_arrayxp_point; }
    public ArrayList<Long3> getM_arrayxp_point() {return m_arrayxp_point; }
    public static MT_Data_GGamePolicy ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GGamePolicy Data = new MT_Data_GGamePolicy();

        Integer count;
		Data.m_nid = reader.getInt();
		Data.m_day_recharge = Int2.ReadMemory(reader, fileName);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayslots_times_limit.add(Int2.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayxp_point.add(Long3.ReadMemory(reader, fileName));
        }

		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_day_recharge)) return false;
        if (this.m_arrayslots_times_limit.size() > 0) return false;
        if (this.m_arrayxp_point.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("day_recharge"))
            return day_recharge();
        if (str.equals("slots_times_limit"))
            return slots_times_limit();
        if (str.equals("xp_point"))
            return xp_point();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("day_recharge =" + day_recharge() + '\n');
        str += ("slots_times_limit =" + slots_times_limit() + '\n');
        str += ("xp_point =" + xp_point() + '\n');
        return str;
    }
}