package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GItem extends MT_DataBase {
    public static String MD5 = "9f9ccbce43ffc038858837888f4cd8fa";
    private Integer m_nid ;
    /** 物品模板id */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** 物品模板id */
    public Integer ID() { return m_nid; }
    private String m_sname ;
    /** 名称 */
    public String name() { return m_sname; }
    public String getM_sname() {return m_sname; }
    private String m_sicon ;
    /** 图标 */
    public String icon() { return m_sicon; }
    public String getM_sicon() {return m_sicon; }
    private Integer m_nbagState ;
    /** 包裹状态 */
    public Integer bagState() { return m_nbagState; }
    public Integer getM_nbagState() {return m_nbagState; }
    private Integer m_nsvr_isOverlay ;
    /** 可否叠加 */
    public Integer svr_isOverlay() { return m_nsvr_isOverlay; }
    public Integer getM_nsvr_isOverlay() {return m_nsvr_isOverlay; }
    private Integer m_nsvr_initnum ;
    /** 初始化数量 */
    public Integer svr_initnum() { return m_nsvr_initnum; }
    public Integer getM_nsvr_initnum() {return m_nsvr_initnum; }
    private Integer m_ncanUse ;
    /** 使用赠送标志 */
    public Integer canUse() { return m_ncanUse; }
    public Integer getM_ncanUse() {return m_ncanUse; }
    private Integer m_nviplimit ;
    /** vip限制 */
    public Integer viplimit() { return m_nviplimit; }
    public Integer getM_nviplimit() {return m_nviplimit; }
    private String m_sdesc ;
    /** 描述 */
    public String desc() { return m_sdesc; }
    public String getM_sdesc() {return m_sdesc; }
    private Integer m_nsvr_giftexpire ;
    /** 礼物显示小时数 */
    public Integer svr_giftexpire() { return m_nsvr_giftexpire; }
    public Integer getM_nsvr_giftexpire() {return m_nsvr_giftexpire; }
    private String m_ssvr_useAction ;
    /** 使用动作 */
    public String svr_useAction() { return m_ssvr_useAction; }
    public String getM_ssvr_useAction() {return m_ssvr_useAction; }
    public static MT_Data_GItem ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GItem Data = new MT_Data_GItem();
		Data.m_nid = reader.getInt();
		Data.m_sname = TableUtil.ReadLString(reader, fileName,"name",Data.ID());
		Data.m_sicon = TableUtil.ReadString(reader);
		Data.m_nbagState = reader.getInt();
		Data.m_nsvr_isOverlay = reader.getInt();
		Data.m_nsvr_initnum = reader.getInt();
		Data.m_ncanUse = reader.getInt();
		Data.m_nviplimit = reader.getInt();
		Data.m_sdesc = TableUtil.ReadLString(reader, fileName,"desc",Data.ID());
		Data.m_nsvr_giftexpire = reader.getInt();
		Data.m_ssvr_useAction = TableUtil.ReadString(reader);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_sname)) return false;
        if (!TableUtil.IsInvalid(this.m_sicon)) return false;
        if (!TableUtil.IsInvalid(this.m_nbagState)) return false;
        if (!TableUtil.IsInvalid(this.m_nsvr_isOverlay)) return false;
        if (!TableUtil.IsInvalid(this.m_nsvr_initnum)) return false;
        if (!TableUtil.IsInvalid(this.m_ncanUse)) return false;
        if (!TableUtil.IsInvalid(this.m_nviplimit)) return false;
        if (!TableUtil.IsInvalid(this.m_sdesc)) return false;
        if (!TableUtil.IsInvalid(this.m_nsvr_giftexpire)) return false;
        if (!TableUtil.IsInvalid(this.m_ssvr_useAction)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("name"))
            return name();
        if (str.equals("icon"))
            return icon();
        if (str.equals("bagState"))
            return bagState();
        if (str.equals("svr_isOverlay"))
            return svr_isOverlay();
        if (str.equals("svr_initnum"))
            return svr_initnum();
        if (str.equals("canUse"))
            return canUse();
        if (str.equals("viplimit"))
            return viplimit();
        if (str.equals("desc"))
            return desc();
        if (str.equals("svr_giftexpire"))
            return svr_giftexpire();
        if (str.equals("svr_useAction"))
            return svr_useAction();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("name =" + name() + '\n');
        str += ("icon =" + icon() + '\n');
        str += ("bagState =" + bagState() + '\n');
        str += ("svr_isOverlay =" + svr_isOverlay() + '\n');
        str += ("svr_initnum =" + svr_initnum() + '\n');
        str += ("canUse =" + canUse() + '\n');
        str += ("viplimit =" + viplimit() + '\n');
        str += ("desc =" + desc() + '\n');
        str += ("svr_giftexpire =" + svr_giftexpire() + '\n');
        str += ("svr_useAction =" + svr_useAction() + '\n');
        return str;
    }
}