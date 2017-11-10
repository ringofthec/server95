package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GRankReward extends MT_DataBase {
    public static String MD5 = "baa90b314a1cc89701e32b5d60ee68c7";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private ArrayList<Int2> m_arraymoney_reward = new ArrayList<Int2>();
    /** 财富榜奖励 */
    public ArrayList<Int2> money_reward() { return m_arraymoney_reward; }
    public ArrayList<Int2> getM_arraymoney_reward() {return m_arraymoney_reward; }
    private ArrayList<Int2> m_arraylevel_reward = new ArrayList<Int2>();
    /** 等级榜奖励 */
    public ArrayList<Int2> level_reward() { return m_arraylevel_reward; }
    public ArrayList<Int2> getM_arraylevel_reward() {return m_arraylevel_reward; }
    private ArrayList<Int2> m_arraylike_reward = new ArrayList<Int2>();
    /** 人气榜奖励 */
    public ArrayList<Int2> like_reward() { return m_arraylike_reward; }
    public ArrayList<Int2> getM_arraylike_reward() {return m_arraylike_reward; }
    public static MT_Data_GRankReward ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GRankReward Data = new MT_Data_GRankReward();

        Integer count;
		Data.m_nindex = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arraymoney_reward.add(Int2.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arraylevel_reward.add(Int2.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arraylike_reward.add(Int2.ReadMemory(reader, fileName));
        }

		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (this.m_arraymoney_reward.size() > 0) return false;
        if (this.m_arraylevel_reward.size() > 0) return false;
        if (this.m_arraylike_reward.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("money_reward"))
            return money_reward();
        if (str.equals("level_reward"))
            return level_reward();
        if (str.equals("like_reward"))
            return like_reward();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("money_reward =" + money_reward() + '\n');
        str += ("level_reward =" + level_reward() + '\n');
        str += ("like_reward =" + like_reward() + '\n');
        return str;
    }
}