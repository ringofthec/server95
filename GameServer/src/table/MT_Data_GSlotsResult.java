package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GSlotsResult extends MT_DataBase {
    public static String MD5 = "ca1aeb7e8d65cb8883174f46d3393683";
    private Integer m_nid ;
    /** 唯一id */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** 唯一id */
    public Integer ID() { return m_nid; }
    private Integer m_nrate ;
    /** 倍率 */
    public Integer rate() { return m_nrate; }
    public Integer getM_nrate() {return m_nrate; }
    private ArrayList<Integer> m_arrayres = new ArrayList<Integer>();
    /** 结果 */
    public ArrayList<Integer> res() { return m_arrayres; }
    public ArrayList<Integer> getM_arrayres() {return m_arrayres; }
    public static MT_Data_GSlotsResult ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GSlotsResult Data = new MT_Data_GSlotsResult();

        Integer count;
		Data.m_nid = reader.getInt();
		Data.m_nrate = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayres.add(reader.getInt());
        }

		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_nrate)) return false;
        if (this.m_arrayres.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("rate"))
            return rate();
        if (str.equals("res"))
            return res();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("rate =" + rate() + '\n');
        str += ("res =" + res() + '\n');
        return str;
    }
}