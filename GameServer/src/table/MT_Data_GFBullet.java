package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GFBullet extends MT_DataBase {
    public static String MD5 = "444beac57c485d97493936b87ccc1735";
    private Integer m_nid ;
    /** 索引 */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** 索引 */
    public Integer ID() { return m_nid; }
    private Integer m_nrate ;
    /** 倍率 */
    public Integer rate() { return m_nrate; }
    public Integer getM_nrate() {return m_nrate; }
    private Integer m_ndamage ;
    /** 伤害 */
    public Integer damage() { return m_ndamage; }
    public Integer getM_ndamage() {return m_ndamage; }
    private Integer m_ncost_id ;
    /** 花费金币 */
    public Integer cost_id() { return m_ncost_id; }
    public Integer getM_ncost_id() {return m_ncost_id; }
    private Integer m_nunlock_costId ;
    /** 解锁花费 */
    public Integer unlock_costId() { return m_nunlock_costId; }
    public Integer getM_nunlock_costId() {return m_nunlock_costId; }
    private Integer m_nunlock_vip ;
    /** 升级所需VIP等级 */
    public Integer unlock_vip() { return m_nunlock_vip; }
    public Integer getM_nunlock_vip() {return m_nunlock_vip; }
    private ArrayList<Integer> m_arrayunlock_door = new ArrayList<Integer>();
    /** 解锁/使用门厅限制 */
    public ArrayList<Integer> unlock_door() { return m_arrayunlock_door; }
    public ArrayList<Integer> getM_arrayunlock_door() {return m_arrayunlock_door; }
    public static MT_Data_GFBullet ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GFBullet Data = new MT_Data_GFBullet();

        Integer count;
		Data.m_nid = reader.getInt();
		Data.m_nrate = reader.getInt();
		Data.m_ndamage = reader.getInt();
		Data.m_ncost_id = reader.getInt();
		Data.m_nunlock_costId = reader.getInt();
		Data.m_nunlock_vip = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayunlock_door.add(reader.getInt());
        }

		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_nrate)) return false;
        if (!TableUtil.IsInvalid(this.m_ndamage)) return false;
        if (!TableUtil.IsInvalid(this.m_ncost_id)) return false;
        if (!TableUtil.IsInvalid(this.m_nunlock_costId)) return false;
        if (!TableUtil.IsInvalid(this.m_nunlock_vip)) return false;
        if (this.m_arrayunlock_door.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("rate"))
            return rate();
        if (str.equals("damage"))
            return damage();
        if (str.equals("cost_id"))
            return cost_id();
        if (str.equals("unlock_costId"))
            return unlock_costId();
        if (str.equals("unlock_vip"))
            return unlock_vip();
        if (str.equals("unlock_door"))
            return unlock_door();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("rate =" + rate() + '\n');
        str += ("damage =" + damage() + '\n');
        str += ("cost_id =" + cost_id() + '\n');
        str += ("unlock_costId =" + unlock_costId() + '\n');
        str += ("unlock_vip =" + unlock_vip() + '\n');
        str += ("unlock_door =" + unlock_door() + '\n');
        return str;
    }
}