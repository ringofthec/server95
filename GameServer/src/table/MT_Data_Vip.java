package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Vip extends MT_DataBase {
    public static String MD5 = "1f728bf8dfed7e3abd896bac555d1aec";
    private Integer m_nLevel ;
    /** 索引 */
    public Integer Level() { return m_nLevel; }
    public Integer getM_nLevel() {return m_nLevel; }
    /** 索引 */
    public Integer ID() { return m_nLevel; }
    private Integer m_nExp ;
    /** 升下一级需要经验 */
    public Integer Exp() { return m_nExp; }
    public Integer getM_nExp() {return m_nExp; }
    private Integer m_nDayExp ;
    /** 每天获得点数 */
    public Integer DayExp() { return m_nDayExp; }
    public Integer getM_nDayExp() {return m_nDayExp; }
    private Integer m_nRightsCount ;
    /** 特权数量 */
    public Integer RightsCount() { return m_nRightsCount; }
    public Integer getM_nRightsCount() {return m_nRightsCount; }
    private String m_sDescribe1 ;
    /** 描述 */
    public String Describe1() { return m_sDescribe1; }
    public String getM_sDescribe1() {return m_sDescribe1; }
    private String m_sDescribe2 ;
    /** 描述 */
    public String Describe2() { return m_sDescribe2; }
    public String getM_sDescribe2() {return m_sDescribe2; }
    private String m_sDescribe3 ;
    /** 描述 */
    public String Describe3() { return m_sDescribe3; }
    public String getM_sDescribe3() {return m_sDescribe3; }
    private Integer m_nDayGold ;
    /** 激活1天需要金砖数量 */
    public Integer DayGold() { return m_nDayGold; }
    public Integer getM_nDayGold() {return m_nDayGold; }
    private Integer m_nWeekGold ;
    /** 激活7天需要金砖数量 */
    public Integer WeekGold() { return m_nWeekGold; }
    public Integer getM_nWeekGold() {return m_nWeekGold; }
    private Integer m_nMonthGold ;
    /** 激活30天需要金砖数量 */
    public Integer MonthGold() { return m_nMonthGold; }
    public Integer getM_nMonthGold() {return m_nMonthGold; }
    private ArrayList<Integer> m_arrayPassiveBuffs = new ArrayList<Integer>();
    /** 技能buff */
    public ArrayList<Integer> PassiveBuffs() { return m_arrayPassiveBuffs; }
    public ArrayList<Integer> getM_arrayPassiveBuffs() {return m_arrayPassiveBuffs; }
    public static MT_Data_Vip ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Vip Data = new MT_Data_Vip();

        Integer count;
		Data.m_nLevel = reader.getInt();
		Data.m_nExp = reader.getInt();
		Data.m_nDayExp = reader.getInt();
		Data.m_nRightsCount = reader.getInt();
		Data.m_sDescribe1 = TableUtil.ReadLString(reader, fileName,"Describe1",Data.ID());
		Data.m_sDescribe2 = TableUtil.ReadLString(reader, fileName,"Describe2",Data.ID());
		Data.m_sDescribe3 = TableUtil.ReadLString(reader, fileName,"Describe3",Data.ID());
		Data.m_nDayGold = reader.getInt();
		Data.m_nWeekGold = reader.getInt();
		Data.m_nMonthGold = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayPassiveBuffs.add(reader.getInt());
        }

		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nLevel)) return false;
        if (!TableUtil.IsInvalid(this.m_nExp)) return false;
        if (!TableUtil.IsInvalid(this.m_nDayExp)) return false;
        if (!TableUtil.IsInvalid(this.m_nRightsCount)) return false;
        if (!TableUtil.IsInvalid(this.m_sDescribe1)) return false;
        if (!TableUtil.IsInvalid(this.m_sDescribe2)) return false;
        if (!TableUtil.IsInvalid(this.m_sDescribe3)) return false;
        if (!TableUtil.IsInvalid(this.m_nDayGold)) return false;
        if (!TableUtil.IsInvalid(this.m_nWeekGold)) return false;
        if (!TableUtil.IsInvalid(this.m_nMonthGold)) return false;
        if (this.m_arrayPassiveBuffs.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("Level"))
            return Level();
        if (str.equals("Exp"))
            return Exp();
        if (str.equals("DayExp"))
            return DayExp();
        if (str.equals("RightsCount"))
            return RightsCount();
        if (str.equals("Describe1"))
            return Describe1();
        if (str.equals("Describe2"))
            return Describe2();
        if (str.equals("Describe3"))
            return Describe3();
        if (str.equals("DayGold"))
            return DayGold();
        if (str.equals("WeekGold"))
            return WeekGold();
        if (str.equals("MonthGold"))
            return MonthGold();
        if (str.equals("PassiveBuffs"))
            return PassiveBuffs();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("Level =" + Level() + '\n');
        str += ("Exp =" + Exp() + '\n');
        str += ("DayExp =" + DayExp() + '\n');
        str += ("RightsCount =" + RightsCount() + '\n');
        str += ("Describe1 =" + Describe1() + '\n');
        str += ("Describe2 =" + Describe2() + '\n');
        str += ("Describe3 =" + Describe3() + '\n');
        str += ("DayGold =" + DayGold() + '\n');
        str += ("WeekGold =" + WeekGold() + '\n');
        str += ("MonthGold =" + MonthGold() + '\n');
        str += ("PassiveBuffs =" + PassiveBuffs() + '\n');
        return str;
    }
}