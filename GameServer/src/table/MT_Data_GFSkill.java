package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GFSkill extends MT_DataBase {
    public static String MD5 = "6615a67fa58818c6b8c53a3503b652bb";
    private Integer m_nid ;
    /** ItemID */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** ItemID */
    public Integer ID() { return m_nid; }
    private String m_sname ;
    /** 名称 */
    public String name() { return m_sname; }
    public String getM_sname() {return m_sname; }
    private Integer m_nduration ;
    /** 持续时间 */
    public Integer duration() { return m_nduration; }
    public Integer getM_nduration() {return m_nduration; }
    private Integer m_ncd ;
    /** CD */
    public Integer cd() { return m_ncd; }
    public Integer getM_ncd() {return m_ncd; }
    private Integer m_ncostId ;
    /** 花费 */
    public Integer costId() { return m_ncostId; }
    public Integer getM_ncostId() {return m_ncostId; }
    private Integer m_ndamage ;
    /** 伤害（倍数） */
    public Integer damage() { return m_ndamage; }
    public Integer getM_ndamage() {return m_ndamage; }
    private Integer m_nmaxCount ;
    /** 最大叠加数量 */
    public Integer maxCount() { return m_nmaxCount; }
    public Integer getM_nmaxCount() {return m_nmaxCount; }
    private Integer m_nviplevel ;
    /** vip限制 */
    public Integer viplevel() { return m_nviplevel; }
    public Integer getM_nviplevel() {return m_nviplevel; }
    private ArrayList<Integer> m_arrayscene_limit = new ArrayList<Integer>();
    /** 场景使用限制 */
    public ArrayList<Integer> scene_limit() { return m_arrayscene_limit; }
    public ArrayList<Integer> getM_arrayscene_limit() {return m_arrayscene_limit; }
    private ArrayList<Integer> m_arrayunuseSkills = new ArrayList<Integer>();
    /** 不能使用技能 */
    public ArrayList<Integer> unuseSkills() { return m_arrayunuseSkills; }
    public ArrayList<Integer> getM_arrayunuseSkills() {return m_arrayunuseSkills; }
    private Integer m_naddRate ;
    /** 提升捕鱼的概率 */
    public Integer addRate() { return m_naddRate; }
    public Integer getM_naddRate() {return m_naddRate; }
    private Integer m_ncdItemId ;
    /** cd_id */
    public Integer cdItemId() { return m_ncdItemId; }
    public Integer getM_ncdItemId() {return m_ncdItemId; }
    public static MT_Data_GFSkill ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GFSkill Data = new MT_Data_GFSkill();

        Integer count;
		Data.m_nid = reader.getInt();
		Data.m_sname = TableUtil.ReadString(reader);
		Data.m_nduration = reader.getInt();
		Data.m_ncd = reader.getInt();
		Data.m_ncostId = reader.getInt();
		Data.m_ndamage = reader.getInt();
		Data.m_nmaxCount = reader.getInt();
		Data.m_nviplevel = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayscene_limit.add(reader.getInt());
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayunuseSkills.add(reader.getInt());
        }

		Data.m_naddRate = reader.getInt();
		Data.m_ncdItemId = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_sname)) return false;
        if (!TableUtil.IsInvalid(this.m_nduration)) return false;
        if (!TableUtil.IsInvalid(this.m_ncd)) return false;
        if (!TableUtil.IsInvalid(this.m_ncostId)) return false;
        if (!TableUtil.IsInvalid(this.m_ndamage)) return false;
        if (!TableUtil.IsInvalid(this.m_nmaxCount)) return false;
        if (!TableUtil.IsInvalid(this.m_nviplevel)) return false;
        if (this.m_arrayscene_limit.size() > 0) return false;
        if (this.m_arrayunuseSkills.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_naddRate)) return false;
        if (!TableUtil.IsInvalid(this.m_ncdItemId)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("name"))
            return name();
        if (str.equals("duration"))
            return duration();
        if (str.equals("cd"))
            return cd();
        if (str.equals("costId"))
            return costId();
        if (str.equals("damage"))
            return damage();
        if (str.equals("maxCount"))
            return maxCount();
        if (str.equals("viplevel"))
            return viplevel();
        if (str.equals("scene_limit"))
            return scene_limit();
        if (str.equals("unuseSkills"))
            return unuseSkills();
        if (str.equals("addRate"))
            return addRate();
        if (str.equals("cdItemId"))
            return cdItemId();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("name =" + name() + '\n');
        str += ("duration =" + duration() + '\n');
        str += ("cd =" + cd() + '\n');
        str += ("costId =" + costId() + '\n');
        str += ("damage =" + damage() + '\n');
        str += ("maxCount =" + maxCount() + '\n');
        str += ("viplevel =" + viplevel() + '\n');
        str += ("scene_limit =" + scene_limit() + '\n');
        str += ("unuseSkills =" + unuseSkills() + '\n');
        str += ("addRate =" + addRate() + '\n');
        str += ("cdItemId =" + cdItemId() + '\n');
        return str;
    }
}