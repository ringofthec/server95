package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_TeachShadow extends MT_DataBase {
    public static String MD5 = "fd1a05afb247228d627f3055875ba15a";
    private Integer m_nIndex ;
    /** 索引 */
    public Integer ID() { return m_nIndex; }
    private Integer m_nPosition1 ;
    private Integer m_nPosition2 ;
    private Integer m_nPosition3 ;
    private Integer m_nPosition4 ;
    private Integer m_nPosition5 ;
    private Integer m_nPosition6 ;
    private Integer m_nPosition7 ;
    private ArrayList<Integer> m_Array = new ArrayList<Integer>();
    public ArrayList<Integer> Arrays() { return m_Array; }
    public static MT_Data_TeachShadow ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_TeachShadow Data = new MT_Data_TeachShadow();
		Data.m_nIndex = reader.getInt();
		Data.m_nPosition1 = reader.getInt();
		Data.m_Array.add(Data.m_nPosition1);
		Data.m_nPosition2 = reader.getInt();
		Data.m_Array.add(Data.m_nPosition2);
		Data.m_nPosition3 = reader.getInt();
		Data.m_Array.add(Data.m_nPosition3);
		Data.m_nPosition4 = reader.getInt();
		Data.m_Array.add(Data.m_nPosition4);
		Data.m_nPosition5 = reader.getInt();
		Data.m_Array.add(Data.m_nPosition5);
		Data.m_nPosition6 = reader.getInt();
		Data.m_Array.add(Data.m_nPosition6);
		Data.m_nPosition7 = reader.getInt();
		Data.m_Array.add(Data.m_nPosition7);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nIndex)) return false;
        if (!TableUtil.IsInvalid(this.m_nPosition1)) return false;
        if (!TableUtil.IsInvalid(this.m_nPosition2)) return false;
        if (!TableUtil.IsInvalid(this.m_nPosition3)) return false;
        if (!TableUtil.IsInvalid(this.m_nPosition4)) return false;
        if (!TableUtil.IsInvalid(this.m_nPosition5)) return false;
        if (!TableUtil.IsInvalid(this.m_nPosition6)) return false;
        if (!TableUtil.IsInvalid(this.m_nPosition7)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
            return null;
        }
    @Override
    public String toString ( ) {
        String str = "";
        return str;
    }
}