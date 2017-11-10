package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GSlotsLine extends MT_DataBase {
    public static String MD5 = "c74db51b97474bd903647630fafc4e36";
    private Integer m_nlineid ;
    /** 线 */
    public Integer lineid() { return m_nlineid; }
    public Integer getM_nlineid() {return m_nlineid; }
    /** 线 */
    public Integer ID() { return m_nlineid; }
    private ArrayList<Integer> m_arraylines = new ArrayList<Integer>();
    /** 线型 */
    public ArrayList<Integer> lines() { return m_arraylines; }
    public ArrayList<Integer> getM_arraylines() {return m_arraylines; }
    private ArrayList<Integer> m_arraygroup = new ArrayList<Integer>();
    /** 所属组 */
    public ArrayList<Integer> group() { return m_arraygroup; }
    public ArrayList<Integer> getM_arraygroup() {return m_arraygroup; }
    private ArrayList<Integer> m_arrayrgb = new ArrayList<Integer>();
    /** RGB */
    public ArrayList<Integer> rgb() { return m_arrayrgb; }
    public ArrayList<Integer> getM_arrayrgb() {return m_arrayrgb; }
    public static MT_Data_GSlotsLine ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GSlotsLine Data = new MT_Data_GSlotsLine();

        Integer count;
		Data.m_nlineid = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arraylines.add(reader.getInt());
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arraygroup.add(reader.getInt());
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayrgb.add(reader.getInt());
        }

		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nlineid)) return false;
        if (this.m_arraylines.size() > 0) return false;
        if (this.m_arraygroup.size() > 0) return false;
        if (this.m_arrayrgb.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("lineid"))
            return lineid();
        if (str.equals("lines"))
            return lines();
        if (str.equals("group"))
            return group();
        if (str.equals("rgb"))
            return rgb();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("lineid =" + lineid() + '\n');
        str += ("lines =" + lines() + '\n');
        str += ("group =" + group() + '\n');
        str += ("rgb =" + rgb() + '\n');
        return str;
    }
}