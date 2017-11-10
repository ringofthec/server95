package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GLevelShop extends MT_DataBase {
    public static String MD5 = "7c6803ec828e8fbe4e0f680732bac521";
    private Integer m_nlevel ;
    /** 等级 */
    public Integer level() { return m_nlevel; }
    public Integer getM_nlevel() {return m_nlevel; }
    /** 等级 */
    public Integer ID() { return m_nlevel; }
    private String m_stitle ;
    /** 等级名称 */
    public String title() { return m_stitle; }
    public String getM_stitle() {return m_stitle; }
    private ArrayList<Int2> m_arrayrewards = new ArrayList<Int2>();
    /** 奖励的物品 */
    public ArrayList<Int2> rewards() { return m_arrayrewards; }
    public ArrayList<Int2> getM_arrayrewards() {return m_arrayrewards; }
    private Integer m_ncostid ;
    /** 购买等级消耗 */
    public Integer costid() { return m_ncostid; }
    public Integer getM_ncostid() {return m_ncostid; }
    private String m_stitle2 ;
    /** 奖励标题 */
    public String title2() { return m_stitle2; }
    public String getM_stitle2() {return m_stitle2; }
    public static MT_Data_GLevelShop ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GLevelShop Data = new MT_Data_GLevelShop();

        Integer count;
		Data.m_nlevel = reader.getInt();
		Data.m_stitle = TableUtil.ReadLString(reader, fileName,"title",Data.ID());
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayrewards.add(Int2.ReadMemory(reader, fileName));
        }

		Data.m_ncostid = reader.getInt();
		Data.m_stitle2 = TableUtil.ReadLString(reader, fileName,"title2",Data.ID());
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nlevel)) return false;
        if (!TableUtil.IsInvalid(this.m_stitle)) return false;
        if (this.m_arrayrewards.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_ncostid)) return false;
        if (!TableUtil.IsInvalid(this.m_stitle2)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("level"))
            return level();
        if (str.equals("title"))
            return title();
        if (str.equals("rewards"))
            return rewards();
        if (str.equals("costid"))
            return costid();
        if (str.equals("title2"))
            return title2();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("level =" + level() + '\n');
        str += ("title =" + title() + '\n');
        str += ("rewards =" + rewards() + '\n');
        str += ("costid =" + costid() + '\n');
        str += ("title2 =" + title2() + '\n');
        return str;
    }
}