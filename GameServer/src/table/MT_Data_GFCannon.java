package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GFCannon extends MT_DataBase {
    public static String MD5 = "8a41c835b9ac9523335f0aa3de6d0a3e";
    private Integer m_nid ;
    /** VIP等级 */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** VIP等级 */
    public Integer ID() { return m_nid; }
    private Integer m_ncannon_skin ;
    /** 炮塔皮肤ID */
    public Integer cannon_skin() { return m_ncannon_skin; }
    public Integer getM_ncannon_skin() {return m_ncannon_skin; }
    private Integer m_nbullet_skin ;
    /** 炮弹皮肤ID */
    public Integer bullet_skin() { return m_nbullet_skin; }
    public Integer getM_nbullet_skin() {return m_nbullet_skin; }
    private Integer m_neffectId ;
    /** 炮弹爆炸效果ID */
    public Integer effectId() { return m_neffectId; }
    public Integer getM_neffectId() {return m_neffectId; }
    private Integer m_nautoFire ;
    /** 自动开炮 */
    public Integer autoFire() { return m_nautoFire; }
    public Integer getM_nautoFire() {return m_nautoFire; }
    private Integer m_nrateUp ;
    /** 提升命中率 */
    public Integer rateUp() { return m_nrateUp; }
    public Integer getM_nrateUp() {return m_nrateUp; }
    private String m_sdesc ;
    /** 描述 */
    public String desc() { return m_sdesc; }
    public String getM_sdesc() {return m_sdesc; }
    public static MT_Data_GFCannon ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GFCannon Data = new MT_Data_GFCannon();
		Data.m_nid = reader.getInt();
		Data.m_ncannon_skin = reader.getInt();
		Data.m_nbullet_skin = reader.getInt();
		Data.m_neffectId = reader.getInt();
		Data.m_nautoFire = reader.getInt();
		Data.m_nrateUp = reader.getInt();
		Data.m_sdesc = TableUtil.ReadLString(reader, fileName,"desc",Data.ID());
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_ncannon_skin)) return false;
        if (!TableUtil.IsInvalid(this.m_nbullet_skin)) return false;
        if (!TableUtil.IsInvalid(this.m_neffectId)) return false;
        if (!TableUtil.IsInvalid(this.m_nautoFire)) return false;
        if (!TableUtil.IsInvalid(this.m_nrateUp)) return false;
        if (!TableUtil.IsInvalid(this.m_sdesc)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("cannon_skin"))
            return cannon_skin();
        if (str.equals("bullet_skin"))
            return bullet_skin();
        if (str.equals("effectId"))
            return effectId();
        if (str.equals("autoFire"))
            return autoFire();
        if (str.equals("rateUp"))
            return rateUp();
        if (str.equals("desc"))
            return desc();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("cannon_skin =" + cannon_skin() + '\n');
        str += ("bullet_skin =" + bullet_skin() + '\n');
        str += ("effectId =" + effectId() + '\n');
        str += ("autoFire =" + autoFire() + '\n');
        str += ("rateUp =" + rateUp() + '\n');
        str += ("desc =" + desc() + '\n');
        return str;
    }
}