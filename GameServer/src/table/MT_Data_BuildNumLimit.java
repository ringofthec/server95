package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_BuildNumLimit extends MT_DataBase {
    public static String MD5 = "4a5779147612b2c37eb3ca09150bca55";
    private Integer m_nindex ;
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private ArrayList<Int3> m_arrayCount_1 = new ArrayList<Int3>();
    private ArrayList<Int3> m_arrayCount_2 = new ArrayList<Int3>();
    private ArrayList<Int3> m_arrayCount_3 = new ArrayList<Int3>();
    private ArrayList<Int3> m_arrayCount_4 = new ArrayList<Int3>();
    private ArrayList<Int3> m_arrayCount_5 = new ArrayList<Int3>();
    private ArrayList<Int3> m_arrayCount_6 = new ArrayList<Int3>();
    private ArrayList<Int3> m_arrayCount_7 = new ArrayList<Int3>();
    private ArrayList<Int3> m_arrayCount_8 = new ArrayList<Int3>();
    private ArrayList<Int3> m_arrayCount_9 = new ArrayList<Int3>();
    private ArrayList<Int3> m_arrayCount_10 = new ArrayList<Int3>();
    private ArrayList<ArrayList<Int3>> m_Array = new ArrayList<ArrayList<Int3>>();
    public ArrayList<ArrayList<Int3>> Arrays() { return m_Array; }
    public static MT_Data_BuildNumLimit ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_BuildNumLimit Data = new MT_Data_BuildNumLimit();

        Integer count;
		Data.m_nindex = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayCount_1.add(Int3.ReadMemory(reader, fileName));
        }

		Data.m_Array.add(Data.m_arrayCount_1);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayCount_2.add(Int3.ReadMemory(reader, fileName));
        }

		Data.m_Array.add(Data.m_arrayCount_2);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayCount_3.add(Int3.ReadMemory(reader, fileName));
        }

		Data.m_Array.add(Data.m_arrayCount_3);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayCount_4.add(Int3.ReadMemory(reader, fileName));
        }

		Data.m_Array.add(Data.m_arrayCount_4);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayCount_5.add(Int3.ReadMemory(reader, fileName));
        }

		Data.m_Array.add(Data.m_arrayCount_5);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayCount_6.add(Int3.ReadMemory(reader, fileName));
        }

		Data.m_Array.add(Data.m_arrayCount_6);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayCount_7.add(Int3.ReadMemory(reader, fileName));
        }

		Data.m_Array.add(Data.m_arrayCount_7);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayCount_8.add(Int3.ReadMemory(reader, fileName));
        }

		Data.m_Array.add(Data.m_arrayCount_8);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayCount_9.add(Int3.ReadMemory(reader, fileName));
        }

		Data.m_Array.add(Data.m_arrayCount_9);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayCount_10.add(Int3.ReadMemory(reader, fileName));
        }

		Data.m_Array.add(Data.m_arrayCount_10);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (this.m_arrayCount_1.size() > 0) return false;
        if (this.m_arrayCount_2.size() > 0) return false;
        if (this.m_arrayCount_3.size() > 0) return false;
        if (this.m_arrayCount_4.size() > 0) return false;
        if (this.m_arrayCount_5.size() > 0) return false;
        if (this.m_arrayCount_6.size() > 0) return false;
        if (this.m_arrayCount_7.size() > 0) return false;
        if (this.m_arrayCount_8.size() > 0) return false;
        if (this.m_arrayCount_9.size() > 0) return false;
        if (this.m_arrayCount_10.size() > 0) return false;
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