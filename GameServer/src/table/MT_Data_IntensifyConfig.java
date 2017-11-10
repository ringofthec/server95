package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_IntensifyConfig extends MT_DataBase {
    public static String MD5 = "b86a788b0945bd7dbcaf856de2bb20d4";
    private Integer m_nIndex ;
    /** 物品TableId */
    public Integer Index() { return m_nIndex; }
    public Integer getM_nIndex() {return m_nIndex; }
    /** 物品TableId */
    public Integer ID() { return m_nIndex; }
    private ArrayList<Int2> m_arrayIntensify_0 = new ArrayList<Int2>();
    /** 强化等级0 */
    public ArrayList<Int2> Intensify_0() { return m_arrayIntensify_0; }
    public ArrayList<Int2> getM_arrayIntensify_0() {return m_arrayIntensify_0; }
    private ArrayList<Int2> m_arrayIntensify_1 = new ArrayList<Int2>();
    /** 强化等级1 */
    public ArrayList<Int2> Intensify_1() { return m_arrayIntensify_1; }
    public ArrayList<Int2> getM_arrayIntensify_1() {return m_arrayIntensify_1; }
    private ArrayList<Int2> m_arrayIntensify_2 = new ArrayList<Int2>();
    /** 强化等级2 */
    public ArrayList<Int2> Intensify_2() { return m_arrayIntensify_2; }
    public ArrayList<Int2> getM_arrayIntensify_2() {return m_arrayIntensify_2; }
    private ArrayList<Int2> m_arrayIntensify_3 = new ArrayList<Int2>();
    /** 强化等级3 */
    public ArrayList<Int2> Intensify_3() { return m_arrayIntensify_3; }
    public ArrayList<Int2> getM_arrayIntensify_3() {return m_arrayIntensify_3; }
    private ArrayList<Int2> m_arrayIntensify_4 = new ArrayList<Int2>();
    /** 强化等级4 */
    public ArrayList<Int2> Intensify_4() { return m_arrayIntensify_4; }
    public ArrayList<Int2> getM_arrayIntensify_4() {return m_arrayIntensify_4; }
    private ArrayList<Int2> m_arrayIntensify_5 = new ArrayList<Int2>();
    /** 强化等级5 */
    public ArrayList<Int2> Intensify_5() { return m_arrayIntensify_5; }
    public ArrayList<Int2> getM_arrayIntensify_5() {return m_arrayIntensify_5; }
    private ArrayList<Int2> m_arrayIntensify_6 = new ArrayList<Int2>();
    /** 强化等级6 */
    public ArrayList<Int2> Intensify_6() { return m_arrayIntensify_6; }
    public ArrayList<Int2> getM_arrayIntensify_6() {return m_arrayIntensify_6; }
    private ArrayList<Int2> m_arrayIntensify_7 = new ArrayList<Int2>();
    /** 强化等级7 */
    public ArrayList<Int2> Intensify_7() { return m_arrayIntensify_7; }
    public ArrayList<Int2> getM_arrayIntensify_7() {return m_arrayIntensify_7; }
    private ArrayList<Int2> m_arrayIntensify_8 = new ArrayList<Int2>();
    /** 强化等级8 */
    public ArrayList<Int2> Intensify_8() { return m_arrayIntensify_8; }
    public ArrayList<Int2> getM_arrayIntensify_8() {return m_arrayIntensify_8; }
    private ArrayList<Int2> m_arrayIntensify_9 = new ArrayList<Int2>();
    /** 强化等级9 */
    public ArrayList<Int2> Intensify_9() { return m_arrayIntensify_9; }
    public ArrayList<Int2> getM_arrayIntensify_9() {return m_arrayIntensify_9; }
    public static MT_Data_IntensifyConfig ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_IntensifyConfig Data = new MT_Data_IntensifyConfig();

        Integer count;
		Data.m_nIndex = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayIntensify_0.add(Int2.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayIntensify_1.add(Int2.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayIntensify_2.add(Int2.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayIntensify_3.add(Int2.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayIntensify_4.add(Int2.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayIntensify_5.add(Int2.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayIntensify_6.add(Int2.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayIntensify_7.add(Int2.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayIntensify_8.add(Int2.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayIntensify_9.add(Int2.ReadMemory(reader, fileName));
        }

		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nIndex)) return false;
        if (this.m_arrayIntensify_0.size() > 0) return false;
        if (this.m_arrayIntensify_1.size() > 0) return false;
        if (this.m_arrayIntensify_2.size() > 0) return false;
        if (this.m_arrayIntensify_3.size() > 0) return false;
        if (this.m_arrayIntensify_4.size() > 0) return false;
        if (this.m_arrayIntensify_5.size() > 0) return false;
        if (this.m_arrayIntensify_6.size() > 0) return false;
        if (this.m_arrayIntensify_7.size() > 0) return false;
        if (this.m_arrayIntensify_8.size() > 0) return false;
        if (this.m_arrayIntensify_9.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("Index"))
            return Index();
        if (str.equals("Intensify_0"))
            return Intensify_0();
        if (str.equals("Intensify_1"))
            return Intensify_1();
        if (str.equals("Intensify_2"))
            return Intensify_2();
        if (str.equals("Intensify_3"))
            return Intensify_3();
        if (str.equals("Intensify_4"))
            return Intensify_4();
        if (str.equals("Intensify_5"))
            return Intensify_5();
        if (str.equals("Intensify_6"))
            return Intensify_6();
        if (str.equals("Intensify_7"))
            return Intensify_7();
        if (str.equals("Intensify_8"))
            return Intensify_8();
        if (str.equals("Intensify_9"))
            return Intensify_9();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("Index =" + Index() + '\n');
        str += ("Intensify_0 =" + Intensify_0() + '\n');
        str += ("Intensify_1 =" + Intensify_1() + '\n');
        str += ("Intensify_2 =" + Intensify_2() + '\n');
        str += ("Intensify_3 =" + Intensify_3() + '\n');
        str += ("Intensify_4 =" + Intensify_4() + '\n');
        str += ("Intensify_5 =" + Intensify_5() + '\n');
        str += ("Intensify_6 =" + Intensify_6() + '\n');
        str += ("Intensify_7 =" + Intensify_7() + '\n');
        str += ("Intensify_8 =" + Intensify_8() + '\n');
        str += ("Intensify_9 =" + Intensify_9() + '\n');
        return str;
    }
}