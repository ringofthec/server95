package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_ItemStrengthen extends MT_DataBase {
    public static String MD5 = "430a648105f3f68709d689a864639f88";
    private Integer m_nlevel ;
    /** 强化等级 */
    public Integer level() { return m_nlevel; }
    public Integer getM_nlevel() {return m_nlevel; }
    /** 强化等级 */
    public Integer ID() { return m_nlevel; }
    private ArrayList<Int2> m_arrayexpmax = new ArrayList<Int2>();
    /** 强化经验上限 */
    public ArrayList<Int2> expmax() { return m_arrayexpmax; }
    public ArrayList<Int2> getM_arrayexpmax() {return m_arrayexpmax; }
    private ArrayList<Int2> m_arraymoneyCost = new ArrayList<Int2>();
    /** 各颜色装备金币消耗 */
    public ArrayList<Int2> moneyCost() { return m_arraymoneyCost; }
    public ArrayList<Int2> getM_arraymoneyCost() {return m_arraymoneyCost; }
    private ArrayList<Int2> m_arrayitemCost = new ArrayList<Int2>();
    /** 各颜色装备材料消耗 */
    public ArrayList<Int2> itemCost() { return m_arrayitemCost; }
    public ArrayList<Int2> getM_arrayitemCost() {return m_arrayitemCost; }
    private Integer m_npower ;
    /** 战斗力系数 */
    public Integer power() { return m_npower; }
    public Integer getM_npower() {return m_npower; }
    public static MT_Data_ItemStrengthen ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_ItemStrengthen Data = new MT_Data_ItemStrengthen();

        Integer count;
		Data.m_nlevel = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayexpmax.add(Int2.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arraymoneyCost.add(Int2.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayitemCost.add(Int2.ReadMemory(reader, fileName));
        }

		Data.m_npower = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nlevel)) return false;
        if (this.m_arrayexpmax.size() > 0) return false;
        if (this.m_arraymoneyCost.size() > 0) return false;
        if (this.m_arrayitemCost.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_npower)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("level"))
            return level();
        if (str.equals("expmax"))
            return expmax();
        if (str.equals("moneyCost"))
            return moneyCost();
        if (str.equals("itemCost"))
            return itemCost();
        if (str.equals("power"))
            return power();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("level =" + level() + '\n');
        str += ("expmax =" + expmax() + '\n');
        str += ("moneyCost =" + moneyCost() + '\n');
        str += ("itemCost =" + itemCost() + '\n');
        str += ("power =" + power() + '\n');
        return str;
    }
}