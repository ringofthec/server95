package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_DropOut extends MT_DataBase {
    public static String MD5 = "cb355fe8801af14a2ce29ded802fc0f2";
    private Integer m_nIndex ;
    /** 索引 */
    public Integer ID() { return m_nIndex; }
    private Int6 m_DropOut1 ;
    private Int6 m_DropOut2 ;
    private Int6 m_DropOut3 ;
    private Int6 m_DropOut4 ;
    private Int6 m_DropOut5 ;
    private Int6 m_DropOut6 ;
    private Int6 m_DropOut7 ;
    private Int6 m_DropOut8 ;
    private Int6 m_DropOut9 ;
    private Int6 m_DropOut10 ;
    private Int6 m_DropOut11 ;
    private Int6 m_DropOut12 ;
    private Int6 m_DropOut13 ;
    private Int6 m_DropOut14 ;
    private Int6 m_DropOut15 ;
    private Int6 m_DropOut16 ;
    private Int6 m_DropOut17 ;
    private ArrayList<Int6> m_Array = new ArrayList<Int6>();
    public ArrayList<Int6> Arrays() { return m_Array; }
    public static MT_Data_DropOut ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_DropOut Data = new MT_Data_DropOut();
		Data.m_nIndex = reader.getInt();
		Data.m_DropOut1 = Int6.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_DropOut1);
		Data.m_DropOut2 = Int6.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_DropOut2);
		Data.m_DropOut3 = Int6.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_DropOut3);
		Data.m_DropOut4 = Int6.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_DropOut4);
		Data.m_DropOut5 = Int6.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_DropOut5);
		Data.m_DropOut6 = Int6.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_DropOut6);
		Data.m_DropOut7 = Int6.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_DropOut7);
		Data.m_DropOut8 = Int6.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_DropOut8);
		Data.m_DropOut9 = Int6.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_DropOut9);
		Data.m_DropOut10 = Int6.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_DropOut10);
		Data.m_DropOut11 = Int6.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_DropOut11);
		Data.m_DropOut12 = Int6.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_DropOut12);
		Data.m_DropOut13 = Int6.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_DropOut13);
		Data.m_DropOut14 = Int6.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_DropOut14);
		Data.m_DropOut15 = Int6.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_DropOut15);
		Data.m_DropOut16 = Int6.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_DropOut16);
		Data.m_DropOut17 = Int6.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_DropOut17);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nIndex)) return false;
        if (!TableUtil.IsInvalid(this.m_DropOut1)) return false;
        if (!TableUtil.IsInvalid(this.m_DropOut2)) return false;
        if (!TableUtil.IsInvalid(this.m_DropOut3)) return false;
        if (!TableUtil.IsInvalid(this.m_DropOut4)) return false;
        if (!TableUtil.IsInvalid(this.m_DropOut5)) return false;
        if (!TableUtil.IsInvalid(this.m_DropOut6)) return false;
        if (!TableUtil.IsInvalid(this.m_DropOut7)) return false;
        if (!TableUtil.IsInvalid(this.m_DropOut8)) return false;
        if (!TableUtil.IsInvalid(this.m_DropOut9)) return false;
        if (!TableUtil.IsInvalid(this.m_DropOut10)) return false;
        if (!TableUtil.IsInvalid(this.m_DropOut11)) return false;
        if (!TableUtil.IsInvalid(this.m_DropOut12)) return false;
        if (!TableUtil.IsInvalid(this.m_DropOut13)) return false;
        if (!TableUtil.IsInvalid(this.m_DropOut14)) return false;
        if (!TableUtil.IsInvalid(this.m_DropOut15)) return false;
        if (!TableUtil.IsInvalid(this.m_DropOut16)) return false;
        if (!TableUtil.IsInvalid(this.m_DropOut17)) return false;
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