package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GSlotsJoin extends MT_DataBase {
    public static String MD5 = "a02e5cc1e0ff869bca794fd83f6a92d2";
    private Integer m_nid ;
    /** slotid */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** slotid */
    public Integer ID() { return m_nid; }
    private ArrayList<Int2> m_arraylimits1 = new ArrayList<Int2>();
    /** 初级限制 */
    public ArrayList<Int2> limits1() { return m_arraylimits1; }
    public ArrayList<Int2> getM_arraylimits1() {return m_arraylimits1; }
    private Integer m_nmin1 ;
    /** 初级最小押注 */
    public Integer min1() { return m_nmin1; }
    public Integer getM_nmin1() {return m_nmin1; }
    private Integer m_nmax1 ;
    /** 初级最大押注 */
    public Integer max1() { return m_nmax1; }
    public Integer getM_nmax1() {return m_nmax1; }
    private ArrayList<Int2> m_arraylimits2 = new ArrayList<Int2>();
    /** 中级限制 */
    public ArrayList<Int2> limits2() { return m_arraylimits2; }
    public ArrayList<Int2> getM_arraylimits2() {return m_arraylimits2; }
    private Integer m_nmin2 ;
    /** 中级最小押注 */
    public Integer min2() { return m_nmin2; }
    public Integer getM_nmin2() {return m_nmin2; }
    private Integer m_nmax2 ;
    /** 中级最大押注 */
    public Integer max2() { return m_nmax2; }
    public Integer getM_nmax2() {return m_nmax2; }
    private ArrayList<Int2> m_arraylimits3 = new ArrayList<Int2>();
    /** 高级限制 */
    public ArrayList<Int2> limits3() { return m_arraylimits3; }
    public ArrayList<Int2> getM_arraylimits3() {return m_arraylimits3; }
    private Integer m_nmin3 ;
    /** 高级最小押注 */
    public Integer min3() { return m_nmin3; }
    public Integer getM_nmin3() {return m_nmin3; }
    private Integer m_nmax3 ;
    /** 高级最大押注 */
    public Integer max3() { return m_nmax3; }
    public Integer getM_nmax3() {return m_nmax3; }
    public static MT_Data_GSlotsJoin ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GSlotsJoin Data = new MT_Data_GSlotsJoin();

        Integer count;
		Data.m_nid = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arraylimits1.add(Int2.ReadMemory(reader, fileName));
        }

		Data.m_nmin1 = reader.getInt();
		Data.m_nmax1 = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arraylimits2.add(Int2.ReadMemory(reader, fileName));
        }

		Data.m_nmin2 = reader.getInt();
		Data.m_nmax2 = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arraylimits3.add(Int2.ReadMemory(reader, fileName));
        }

		Data.m_nmin3 = reader.getInt();
		Data.m_nmax3 = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (this.m_arraylimits1.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_nmin1)) return false;
        if (!TableUtil.IsInvalid(this.m_nmax1)) return false;
        if (this.m_arraylimits2.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_nmin2)) return false;
        if (!TableUtil.IsInvalid(this.m_nmax2)) return false;
        if (this.m_arraylimits3.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_nmin3)) return false;
        if (!TableUtil.IsInvalid(this.m_nmax3)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("limits1"))
            return limits1();
        if (str.equals("min1"))
            return min1();
        if (str.equals("max1"))
            return max1();
        if (str.equals("limits2"))
            return limits2();
        if (str.equals("min2"))
            return min2();
        if (str.equals("max2"))
            return max2();
        if (str.equals("limits3"))
            return limits3();
        if (str.equals("min3"))
            return min3();
        if (str.equals("max3"))
            return max3();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("limits1 =" + limits1() + '\n');
        str += ("min1 =" + min1() + '\n');
        str += ("max1 =" + max1() + '\n');
        str += ("limits2 =" + limits2() + '\n');
        str += ("min2 =" + min2() + '\n');
        str += ("max2 =" + max2() + '\n');
        str += ("limits3 =" + limits3() + '\n');
        str += ("min3 =" + min3() + '\n');
        str += ("max3 =" + max3() + '\n');
        return str;
    }
}