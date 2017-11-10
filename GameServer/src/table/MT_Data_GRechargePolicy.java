package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GRechargePolicy extends MT_DataBase {
    public static String MD5 = "b110ded5ec49c40aa9dd7be143b1686e";
    private Integer m_nid ;
    /** id */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** id */
    public Integer ID() { return m_nid; }
    private Integer m_npre_money ;
    /** 每线押注 */
    public Integer pre_money() { return m_npre_money; }
    public Integer getM_npre_money() {return m_npre_money; }
    private Integer m_ngameid ;
    /** 游戏类型 */
    public Integer gameid() { return m_ngameid; }
    public Integer getM_ngameid() {return m_ngameid; }
    private Int2 m_bodong0 ;
    /** 默认波动 */
    public Int2 bodong0() { return m_bodong0; }
    public Int2 getM_bodong0() {return m_bodong0; }
    private ArrayList<Int2> m_arrayslots_times_limit = new ArrayList<Int2>();
    /** slots倍率区间 */
    public ArrayList<Int2> slots_times_limit() { return m_arrayslots_times_limit; }
    public ArrayList<Int2> getM_arrayslots_times_limit() {return m_arrayslots_times_limit; }
    private ArrayList<Integer> m_arraywin = new ArrayList<Integer>();
    /** 体验点 */
    public ArrayList<Integer> win() { return m_arraywin; }
    public ArrayList<Integer> getM_arraywin() {return m_arraywin; }
    private Integer m_nstep_id ;
    /** 策略步骤计数器id */
    public Integer step_id() { return m_nstep_id; }
    public Integer getM_nstep_id() {return m_nstep_id; }
    public static MT_Data_GRechargePolicy ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GRechargePolicy Data = new MT_Data_GRechargePolicy();

        Integer count;
		Data.m_nid = reader.getInt();
		Data.m_npre_money = reader.getInt();
		Data.m_ngameid = reader.getInt();
		Data.m_bodong0 = Int2.ReadMemory(reader, fileName);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayslots_times_limit.add(Int2.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arraywin.add(reader.getInt());
        }

		Data.m_nstep_id = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_npre_money)) return false;
        if (!TableUtil.IsInvalid(this.m_ngameid)) return false;
        if (!TableUtil.IsInvalid(this.m_bodong0)) return false;
        if (this.m_arrayslots_times_limit.size() > 0) return false;
        if (this.m_arraywin.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_nstep_id)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("pre_money"))
            return pre_money();
        if (str.equals("gameid"))
            return gameid();
        if (str.equals("bodong0"))
            return bodong0();
        if (str.equals("slots_times_limit"))
            return slots_times_limit();
        if (str.equals("win"))
            return win();
        if (str.equals("step_id"))
            return step_id();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("pre_money =" + pre_money() + '\n');
        str += ("gameid =" + gameid() + '\n');
        str += ("bodong0 =" + bodong0() + '\n');
        str += ("slots_times_limit =" + slots_times_limit() + '\n');
        str += ("win =" + win() + '\n');
        str += ("step_id =" + step_id() + '\n');
        return str;
    }
}