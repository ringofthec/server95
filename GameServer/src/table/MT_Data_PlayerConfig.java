package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_PlayerConfig extends MT_DataBase {
    public static String MD5 = "f86a44776c00da92785a7c6630846415";
    private Integer m_nIndex ;
    /** 索引 */
    public Integer Index() { return m_nIndex; }
    public Integer getM_nIndex() {return m_nIndex; }
    /** 索引 */
    public Integer ID() { return m_nIndex; }
    private ArrayList<Int4> m_arrayBuilds = new ArrayList<Int4>();
    /** 初始建筑 */
    public ArrayList<Int4> Builds() { return m_arrayBuilds; }
    public ArrayList<Int4> getM_arrayBuilds() {return m_arrayBuilds; }
    private ArrayList<Int2> m_arrayItems = new ArrayList<Int2>();
    /** 初始物品 */
    public ArrayList<Int2> Items() { return m_arrayItems; }
    public ArrayList<Int2> getM_arrayItems() {return m_arrayItems; }
    private ArrayList<Int2> m_arrayCorps = new ArrayList<Int2>();
    /** 初始兵种 */
    public ArrayList<Int2> Corps() { return m_arrayCorps; }
    public ArrayList<Int2> getM_arrayCorps() {return m_arrayCorps; }
    private Integer m_nInstanceId ;
    /** Pve初始关卡Id */
    public Integer InstanceId() { return m_nInstanceId; }
    public Integer getM_nInstanceId() {return m_nInstanceId; }
    private ArrayList<Integer> m_arrayAirSupports = new ArrayList<Integer>();
    /** 初始空中支援 */
    public ArrayList<Integer> AirSupports() { return m_arrayAirSupports; }
    public ArrayList<Integer> getM_arrayAirSupports() {return m_arrayAirSupports; }
    private ArrayList<Integer> m_arrayTask = new ArrayList<Integer>();
    /** 初始任务 */
    public ArrayList<Integer> Task() { return m_arrayTask; }
    public ArrayList<Integer> getM_arrayTask() {return m_arrayTask; }
    public static MT_Data_PlayerConfig ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_PlayerConfig Data = new MT_Data_PlayerConfig();

        Integer count;
		Data.m_nIndex = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayBuilds.add(Int4.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayItems.add(Int2.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayCorps.add(Int2.ReadMemory(reader, fileName));
        }

		Data.m_nInstanceId = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayAirSupports.add(reader.getInt());
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayTask.add(reader.getInt());
        }

		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nIndex)) return false;
        if (this.m_arrayBuilds.size() > 0) return false;
        if (this.m_arrayItems.size() > 0) return false;
        if (this.m_arrayCorps.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_nInstanceId)) return false;
        if (this.m_arrayAirSupports.size() > 0) return false;
        if (this.m_arrayTask.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("Index"))
            return Index();
        if (str.equals("Builds"))
            return Builds();
        if (str.equals("Items"))
            return Items();
        if (str.equals("Corps"))
            return Corps();
        if (str.equals("InstanceId"))
            return InstanceId();
        if (str.equals("AirSupports"))
            return AirSupports();
        if (str.equals("Task"))
            return Task();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("Index =" + Index() + '\n');
        str += ("Builds =" + Builds() + '\n');
        str += ("Items =" + Items() + '\n');
        str += ("Corps =" + Corps() + '\n');
        str += ("InstanceId =" + InstanceId() + '\n');
        str += ("AirSupports =" + AirSupports() + '\n');
        str += ("Task =" + Task() + '\n');
        return str;
    }
}