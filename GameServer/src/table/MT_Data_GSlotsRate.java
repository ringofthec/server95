package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GSlotsRate extends MT_DataBase {
    public static String MD5 = "9ea2f01a06558e837c425c24ff348926";
    private Integer m_nid ;
    /** 水果id */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** 水果id */
    public Integer ID() { return m_nid; }
    private ArrayList<Integer> m_arrayrates = new ArrayList<Integer>();
    /** 连线倍数 */
    public ArrayList<Integer> rates() { return m_arrayrates; }
    public ArrayList<Integer> getM_arrayrates() {return m_arrayrates; }
    public static MT_Data_GSlotsRate ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GSlotsRate Data = new MT_Data_GSlotsRate();

        Integer count;
		Data.m_nid = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayrates.add(reader.getInt());
        }

		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (this.m_arrayrates.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("rates"))
            return rates();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("rates =" + rates() + '\n');
        return str;
    }
}