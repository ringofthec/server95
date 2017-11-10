package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_TimeReward extends MT_DataBase {
    public static String MD5 = "a68e610e8d081db1faa6d566018d99eb";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private Integer m_nTableId ;
    /** 物品Id */
    public Integer TableId() { return m_nTableId; }
    public Integer getM_nTableId() {return m_nTableId; }
    private Integer m_nRandom ;
    /** 选中概率 */
    public Integer Random() { return m_nRandom; }
    public Integer getM_nRandom() {return m_nRandom; }
    private ArrayList<Int2> m_arrayLevelExtent = new ArrayList<Int2>();
    /** 等级区间 */
    public ArrayList<Int2> LevelExtent() { return m_arrayLevelExtent; }
    public ArrayList<Int2> getM_arrayLevelExtent() {return m_arrayLevelExtent; }
    private Int2 m_CountExtent0 ;
    /** 奖励数量区间 */
    public Int2 CountExtent0() { return m_CountExtent0; }
    public Int2 getM_CountExtent0() {return m_CountExtent0; }
    private Int2 m_CountExtent1 ;
    /** 奖励数量区间 */
    public Int2 CountExtent1() { return m_CountExtent1; }
    public Int2 getM_CountExtent1() {return m_CountExtent1; }
    private Int2 m_CountExtent2 ;
    /** 奖励数量区间 */
    public Int2 CountExtent2() { return m_CountExtent2; }
    public Int2 getM_CountExtent2() {return m_CountExtent2; }
    private Int2 m_CountExtent3 ;
    /** 奖励数量区间 */
    public Int2 CountExtent3() { return m_CountExtent3; }
    public Int2 getM_CountExtent3() {return m_CountExtent3; }
    public static MT_Data_TimeReward ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_TimeReward Data = new MT_Data_TimeReward();

        Integer count;
		Data.m_nindex = reader.getInt();
		Data.m_nTableId = reader.getInt();
		Data.m_nRandom = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayLevelExtent.add(Int2.ReadMemory(reader, fileName));
        }

		Data.m_CountExtent0 = Int2.ReadMemory(reader, fileName);
		Data.m_CountExtent1 = Int2.ReadMemory(reader, fileName);
		Data.m_CountExtent2 = Int2.ReadMemory(reader, fileName);
		Data.m_CountExtent3 = Int2.ReadMemory(reader, fileName);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_nTableId)) return false;
        if (!TableUtil.IsInvalid(this.m_nRandom)) return false;
        if (this.m_arrayLevelExtent.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_CountExtent0)) return false;
        if (!TableUtil.IsInvalid(this.m_CountExtent1)) return false;
        if (!TableUtil.IsInvalid(this.m_CountExtent2)) return false;
        if (!TableUtil.IsInvalid(this.m_CountExtent3)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("TableId"))
            return TableId();
        if (str.equals("Random"))
            return Random();
        if (str.equals("LevelExtent"))
            return LevelExtent();
        if (str.equals("CountExtent0"))
            return CountExtent0();
        if (str.equals("CountExtent1"))
            return CountExtent1();
        if (str.equals("CountExtent2"))
            return CountExtent2();
        if (str.equals("CountExtent3"))
            return CountExtent3();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("TableId =" + TableId() + '\n');
        str += ("Random =" + Random() + '\n');
        str += ("LevelExtent =" + LevelExtent() + '\n');
        str += ("CountExtent0 =" + CountExtent0() + '\n');
        str += ("CountExtent1 =" + CountExtent1() + '\n');
        str += ("CountExtent2 =" + CountExtent2() + '\n');
        str += ("CountExtent3 =" + CountExtent3() + '\n');
        return str;
    }
}