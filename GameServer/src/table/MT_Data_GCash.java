package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GCash extends MT_DataBase {
    public static String MD5 = "b19d04841920017f5008ea3f58bda494";
    private Integer m_nid ;
    /** 商品ID */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** 商品ID */
    public Integer ID() { return m_nid; }
    private String m_sname ;
    /** 物品名称 */
    public String name() { return m_sname; }
    public String getM_sname() {return m_sname; }
    private Integer m_ncostId ;
    /** 话费券 */
    public Integer costId() { return m_ncostId; }
    public Integer getM_ncostId() {return m_ncostId; }
    private String m_sicon ;
    /** 图标 */
    public String icon() { return m_sicon; }
    public String getM_sicon() {return m_sicon; }
    private String m_svdesc ;
    /** 价值描述 */
    public String vdesc() { return m_svdesc; }
    public String getM_svdesc() {return m_svdesc; }
    private String m_sldesc ;
    /** 限制描述 */
    public String ldesc() { return m_sldesc; }
    public String getM_sldesc() {return m_sldesc; }
    private Integer m_nviplvl ;
    /** vip等级限制 */
    public Integer viplvl() { return m_nviplvl; }
    public Integer getM_nviplvl() {return m_nviplvl; }
    private Integer m_nlvl ;
    /** 等级限制 */
    public Integer lvl() { return m_nlvl; }
    public Integer getM_nlvl() {return m_nlvl; }
    public static MT_Data_GCash ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GCash Data = new MT_Data_GCash();
		Data.m_nid = reader.getInt();
		Data.m_sname = TableUtil.ReadLString(reader, fileName,"name",Data.ID());
		Data.m_ncostId = reader.getInt();
		Data.m_sicon = TableUtil.ReadString(reader);
		Data.m_svdesc = TableUtil.ReadLString(reader, fileName,"vdesc",Data.ID());
		Data.m_sldesc = TableUtil.ReadLString(reader, fileName,"ldesc",Data.ID());
		Data.m_nviplvl = reader.getInt();
		Data.m_nlvl = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_sname)) return false;
        if (!TableUtil.IsInvalid(this.m_ncostId)) return false;
        if (!TableUtil.IsInvalid(this.m_sicon)) return false;
        if (!TableUtil.IsInvalid(this.m_svdesc)) return false;
        if (!TableUtil.IsInvalid(this.m_sldesc)) return false;
        if (!TableUtil.IsInvalid(this.m_nviplvl)) return false;
        if (!TableUtil.IsInvalid(this.m_nlvl)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("name"))
            return name();
        if (str.equals("costId"))
            return costId();
        if (str.equals("icon"))
            return icon();
        if (str.equals("vdesc"))
            return vdesc();
        if (str.equals("ldesc"))
            return ldesc();
        if (str.equals("viplvl"))
            return viplvl();
        if (str.equals("lvl"))
            return lvl();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("name =" + name() + '\n');
        str += ("costId =" + costId() + '\n');
        str += ("icon =" + icon() + '\n');
        str += ("vdesc =" + vdesc() + '\n');
        str += ("ldesc =" + ldesc() + '\n');
        str += ("viplvl =" + viplvl() + '\n');
        str += ("lvl =" + lvl() + '\n');
        return str;
    }
}