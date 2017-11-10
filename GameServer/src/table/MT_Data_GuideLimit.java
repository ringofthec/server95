package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GuideLimit extends MT_DataBase {
    public static String MD5 = "aac619f15f1cbf59013dbbef77edb67f";
    private Integer m_nLimitID ;
    /** 条件ID */
    public Integer LimitID() { return m_nLimitID; }
    public Integer getM_nLimitID() {return m_nLimitID; }
    /** 条件ID */
    public Integer ID() { return m_nLimitID; }
    private Integer m_nLevel ;
    /** 玩家等级 */
    public Integer Level() { return m_nLevel; }
    public Integer getM_nLevel() {return m_nLevel; }
    private ArrayList<Integer> m_arrayGuide = new ArrayList<Integer>();
    /** 完成某个新手指导(指导链第一个ID) */
    public ArrayList<Integer> Guide() { return m_arrayGuide; }
    public ArrayList<Integer> getM_arrayGuide() {return m_arrayGuide; }
    private ArrayList<Integer> m_arrayTask = new ArrayList<Integer>();
    /** 玩家完成某个任务 */
    public ArrayList<Integer> Task() { return m_arrayTask; }
    public ArrayList<Integer> getM_arrayTask() {return m_arrayTask; }
    public static MT_Data_GuideLimit ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GuideLimit Data = new MT_Data_GuideLimit();

        Integer count;
		Data.m_nLimitID = reader.getInt();
		Data.m_nLevel = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayGuide.add(reader.getInt());
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
        if (!TableUtil.IsInvalid(this.m_nLimitID)) return false;
        if (!TableUtil.IsInvalid(this.m_nLevel)) return false;
        if (this.m_arrayGuide.size() > 0) return false;
        if (this.m_arrayTask.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("LimitID"))
            return LimitID();
        if (str.equals("Level"))
            return Level();
        if (str.equals("Guide"))
            return Guide();
        if (str.equals("Task"))
            return Task();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("LimitID =" + LimitID() + '\n');
        str += ("Level =" + Level() + '\n');
        str += ("Guide =" + Guide() + '\n');
        str += ("Task =" + Task() + '\n');
        return str;
    }
}