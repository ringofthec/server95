package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GSlotsOpen extends MT_DataBase {
    public static String MD5 = "d73056d3d90c139173075054159c1d93";
    private Integer m_nid ;
    /** id */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** id */
    public Integer ID() { return m_nid; }
    private ArrayList<Integer> m_arrayrate = new ArrayList<Integer>();
    /** 奖励倍数 */
    public ArrayList<Integer> rate() { return m_arrayrate; }
    public ArrayList<Integer> getM_arrayrate() {return m_arrayrate; }
    private ArrayList<Integer> m_arraysvr_rate = new ArrayList<Integer>();
    /** 奖励概率 */
    public ArrayList<Integer> svr_rate() { return m_arraysvr_rate; }
    public ArrayList<Integer> getM_arraysvr_rate() {return m_arraysvr_rate; }
    public static MT_Data_GSlotsOpen ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GSlotsOpen Data = new MT_Data_GSlotsOpen();

        Integer count;
		Data.m_nid = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayrate.add(reader.getInt());
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arraysvr_rate.add(reader.getInt());
        }

		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (this.m_arrayrate.size() > 0) return false;
        if (this.m_arraysvr_rate.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("rate"))
            return rate();
        if (str.equals("svr_rate"))
            return svr_rate();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("rate =" + rate() + '\n');
        str += ("svr_rate =" + svr_rate() + '\n');
        return str;
    }
}