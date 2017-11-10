package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GGiftShop extends MT_DataBase {
    public static String MD5 = "b64a2095dbf77fe19842658a4b7403fd";
    private Integer m_nid ;
    /** 编号 */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** 编号 */
    public Integer ID() { return m_nid; }
    private Integer m_nitemTempId ;
    /** 商品item模板id */
    public Integer itemTempId() { return m_nitemTempId; }
    public Integer getM_nitemTempId() {return m_nitemTempId; }
    private String m_sdesc ;
    /** 描述 */
    public String desc() { return m_sdesc; }
    public String getM_sdesc() {return m_sdesc; }
    private Integer m_nviplimit ;
    /** vip等级限制 */
    public Integer viplimit() { return m_nviplimit; }
    public Integer getM_nviplimit() {return m_nviplimit; }
    private Integer m_ncostId ;
    /** 消耗id */
    public Integer costId() { return m_ncostId; }
    public Integer getM_ncostId() {return m_ncostId; }
    private Integer m_nsvr_sum ;
    /** 商品总个数 */
    public Integer svr_sum() { return m_nsvr_sum; }
    public Integer getM_nsvr_sum() {return m_nsvr_sum; }
    private String m_sname ;
    /** 名字 */
    public String name() { return m_sname; }
    public String getM_sname() {return m_sname; }
    private Integer m_nfans ;
    /** 人气 */
    public Integer fans() { return m_nfans; }
    public Integer getM_nfans() {return m_nfans; }
    public static MT_Data_GGiftShop ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GGiftShop Data = new MT_Data_GGiftShop();
		Data.m_nid = reader.getInt();
		Data.m_nitemTempId = reader.getInt();
		Data.m_sdesc = TableUtil.ReadLString(reader, fileName,"desc",Data.ID());
		Data.m_nviplimit = reader.getInt();
		Data.m_ncostId = reader.getInt();
		Data.m_nsvr_sum = reader.getInt();
		Data.m_sname = TableUtil.ReadLString(reader, fileName,"name",Data.ID());
		Data.m_nfans = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_nitemTempId)) return false;
        if (!TableUtil.IsInvalid(this.m_sdesc)) return false;
        if (!TableUtil.IsInvalid(this.m_nviplimit)) return false;
        if (!TableUtil.IsInvalid(this.m_ncostId)) return false;
        if (!TableUtil.IsInvalid(this.m_nsvr_sum)) return false;
        if (!TableUtil.IsInvalid(this.m_sname)) return false;
        if (!TableUtil.IsInvalid(this.m_nfans)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("itemTempId"))
            return itemTempId();
        if (str.equals("desc"))
            return desc();
        if (str.equals("viplimit"))
            return viplimit();
        if (str.equals("costId"))
            return costId();
        if (str.equals("svr_sum"))
            return svr_sum();
        if (str.equals("name"))
            return name();
        if (str.equals("fans"))
            return fans();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("itemTempId =" + itemTempId() + '\n');
        str += ("desc =" + desc() + '\n');
        str += ("viplimit =" + viplimit() + '\n');
        str += ("costId =" + costId() + '\n');
        str += ("svr_sum =" + svr_sum() + '\n');
        str += ("name =" + name() + '\n');
        str += ("fans =" + fans() + '\n');
        return str;
    }
}