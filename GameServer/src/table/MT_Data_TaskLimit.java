package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_TaskLimit extends MT_DataBase {
    public static String MD5 = "dbd5d76a51d8d524ac195e4bdb5c2272";
    private Integer m_nLimitID ;
    /** 显示ID */
    public Integer LimitID() { return m_nLimitID; }
    public Integer getM_nLimitID() {return m_nLimitID; }
    /** 显示ID */
    public Integer ID() { return m_nLimitID; }
    private Integer m_nPlayerLevel ;
    /** 需要角色等级 */
    public Integer PlayerLevel() { return m_nPlayerLevel; }
    public Integer getM_nPlayerLevel() {return m_nPlayerLevel; }
    private ArrayList<Integer> m_arrayPreTask = new ArrayList<Integer>();
    /** 前置任务 */
    public ArrayList<Integer> PreTask() { return m_arrayPreTask; }
    public ArrayList<Integer> getM_arrayPreTask() {return m_arrayPreTask; }
    public static MT_Data_TaskLimit ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_TaskLimit Data = new MT_Data_TaskLimit();

        Integer count;
		Data.m_nLimitID = reader.getInt();
		Data.m_nPlayerLevel = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayPreTask.add(reader.getInt());
        }

		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nLimitID)) return false;
        if (!TableUtil.IsInvalid(this.m_nPlayerLevel)) return false;
        if (this.m_arrayPreTask.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("LimitID"))
            return LimitID();
        if (str.equals("PlayerLevel"))
            return PlayerLevel();
        if (str.equals("PreTask"))
            return PreTask();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("LimitID =" + LimitID() + '\n');
        str += ("PlayerLevel =" + PlayerLevel() + '\n');
        str += ("PreTask =" + PreTask() + '\n');
        return str;
    }
}