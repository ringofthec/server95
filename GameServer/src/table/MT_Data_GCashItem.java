package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GCashItem extends MT_DataBase {
    public static String MD5 = "5d7e6c797469c5e0257b004f34a68b0b";
    private Integer m_nid ;
    /** 编号 */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** 编号 */
    public Integer ID() { return m_nid; }
    private String m_sitemName ;
    /** 名字 */
    public String itemName() { return m_sitemName; }
    public String getM_sitemName() {return m_sitemName; }
    private String m_sdesc ;
    /** 描述 */
    public String desc() { return m_sdesc; }
    public String getM_sdesc() {return m_sdesc; }
    private Integer m_nprice ;
    /** 价格 */
    public Integer price() { return m_nprice; }
    public Integer getM_nprice() {return m_nprice; }
    private String m_sicon ;
    /** 图片 */
    public String icon() { return m_sicon; }
    public String getM_sicon() {return m_sicon; }
    public static MT_Data_GCashItem ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GCashItem Data = new MT_Data_GCashItem();
		Data.m_nid = reader.getInt();
		Data.m_sitemName = TableUtil.ReadString(reader);
		Data.m_sdesc = TableUtil.ReadString(reader);
		Data.m_nprice = reader.getInt();
		Data.m_sicon = TableUtil.ReadString(reader);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_sitemName)) return false;
        if (!TableUtil.IsInvalid(this.m_sdesc)) return false;
        if (!TableUtil.IsInvalid(this.m_nprice)) return false;
        if (!TableUtil.IsInvalid(this.m_sicon)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("itemName"))
            return itemName();
        if (str.equals("desc"))
            return desc();
        if (str.equals("price"))
            return price();
        if (str.equals("icon"))
            return icon();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("itemName =" + itemName() + '\n');
        str += ("desc =" + desc() + '\n');
        str += ("price =" + price() + '\n');
        str += ("icon =" + icon() + '\n');
        return str;
    }
}