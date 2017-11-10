package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_BuildBuyMoney extends MT_DataBase {
    public static String MD5 = "b86a788b0945bd7dbcaf856de2bb20d4";
    private Integer m_nindex ;
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private Int2 m_Count_1 ;
    private Int2 m_Count_2 ;
    private Int2 m_Count_3 ;
    private Int2 m_Count_4 ;
    private Int2 m_Count_5 ;
    private Int2 m_Count_6 ;
    private Int2 m_Count_7 ;
    private Int2 m_Count_8 ;
    private Int2 m_Count_9 ;
    private Int2 m_Count_10 ;
    private ArrayList<Int2> m_Array = new ArrayList<Int2>();
    public ArrayList<Int2> Arrays() { return m_Array; }
    public static MT_Data_BuildBuyMoney ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_BuildBuyMoney Data = new MT_Data_BuildBuyMoney();
		Data.m_nindex = reader.getInt();
		Data.m_Count_1 = Int2.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_Count_1);
		Data.m_Count_2 = Int2.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_Count_2);
		Data.m_Count_3 = Int2.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_Count_3);
		Data.m_Count_4 = Int2.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_Count_4);
		Data.m_Count_5 = Int2.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_Count_5);
		Data.m_Count_6 = Int2.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_Count_6);
		Data.m_Count_7 = Int2.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_Count_7);
		Data.m_Count_8 = Int2.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_Count_8);
		Data.m_Count_9 = Int2.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_Count_9);
		Data.m_Count_10 = Int2.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_Count_10);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_Count_1)) return false;
        if (!TableUtil.IsInvalid(this.m_Count_2)) return false;
        if (!TableUtil.IsInvalid(this.m_Count_3)) return false;
        if (!TableUtil.IsInvalid(this.m_Count_4)) return false;
        if (!TableUtil.IsInvalid(this.m_Count_5)) return false;
        if (!TableUtil.IsInvalid(this.m_Count_6)) return false;
        if (!TableUtil.IsInvalid(this.m_Count_7)) return false;
        if (!TableUtil.IsInvalid(this.m_Count_8)) return false;
        if (!TableUtil.IsInvalid(this.m_Count_9)) return false;
        if (!TableUtil.IsInvalid(this.m_Count_10)) return false;
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