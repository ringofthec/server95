package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GSlotsJZActiveReward extends MT_DataBase {
    public static String MD5 = "43afb385cc18da8872c87f28d028a8ed";
    private Integer m_nid ;
    /** id */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** id */
    public Integer ID() { return m_nid; }
    private Integer m_nlevel ;
    /** 场次 */
    public Integer level() { return m_nlevel; }
    public Integer getM_nlevel() {return m_nlevel; }
    private Integer m_norder ;
    /** 名次 */
    public Integer order() { return m_norder; }
    public Integer getM_norder() {return m_norder; }
    private ArrayList<Int2> m_arrayrewards = new ArrayList<Int2>();
    /** 奖励 */
    public ArrayList<Int2> rewards() { return m_arrayrewards; }
    public ArrayList<Int2> getM_arrayrewards() {return m_arrayrewards; }
    public static MT_Data_GSlotsJZActiveReward ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GSlotsJZActiveReward Data = new MT_Data_GSlotsJZActiveReward();

        Integer count;
		Data.m_nid = reader.getInt();
		Data.m_nlevel = reader.getInt();
		Data.m_norder = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayrewards.add(Int2.ReadMemory(reader, fileName));
        }

		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_nlevel)) return false;
        if (!TableUtil.IsInvalid(this.m_norder)) return false;
        if (this.m_arrayrewards.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("level"))
            return level();
        if (str.equals("order"))
            return order();
        if (str.equals("rewards"))
            return rewards();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("level =" + level() + '\n');
        str += ("order =" + order() + '\n');
        str += ("rewards =" + rewards() + '\n');
        return str;
    }
}