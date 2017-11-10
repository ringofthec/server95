package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GStoreItem extends MT_DataBase {
    public static String MD5 = "d6a731ebc27550f882007f3bb5933608";
    private Integer m_nid ;
    /** 商品ID */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** 商品ID */
    public Integer ID() { return m_nid; }
    private Integer m_nitemId ;
    /** 物品模板ID */
    public Integer itemId() { return m_nitemId; }
    public Integer getM_nitemId() {return m_nitemId; }
    private Integer m_nitemIcon ;
    /** 商品图标 */
    public Integer itemIcon() { return m_nitemIcon; }
    public Integer getM_nitemIcon() {return m_nitemIcon; }
    private Integer m_nitemCount ;
    /** 物品数量 */
    public Integer itemCount() { return m_nitemCount; }
    public Integer getM_nitemCount() {return m_nitemCount; }
    private Integer m_nitemId2 ;
    /** 赠送物品模板ID */
    public Integer itemId2() { return m_nitemId2; }
    public Integer getM_nitemId2() {return m_nitemId2; }
    private Integer m_nitemCount2 ;
    /** 赠送物品数量 */
    public Integer itemCount2() { return m_nitemCount2; }
    public Integer getM_nitemCount2() {return m_nitemCount2; }
    private Integer m_nprice ;
    /** 价格(元) */
    public Integer price() { return m_nprice; }
    public Integer getM_nprice() {return m_nprice; }
    private Integer m_niconId ;
    /** 附加图标 */
    public Integer iconId() { return m_niconId; }
    public Integer getM_niconId() {return m_niconId; }
    private String m_sdesc ;
    /** 描述 */
    public String desc() { return m_sdesc; }
    public String getM_sdesc() {return m_sdesc; }
    private String m_sapp_item ;
    /** appstore */
    public String app_item() { return m_sapp_item; }
    public String getM_sapp_item() {return m_sapp_item; }
    private String m_spay_name ;
    /** 支付名称 */
    public String pay_name() { return m_spay_name; }
    public String getM_spay_name() {return m_spay_name; }
    private Integer m_nchprice ;
    /** 人民币价格 */
    public Integer chprice() { return m_nchprice; }
    public Integer getM_nchprice() {return m_nchprice; }
    private Integer m_namprice ;
    /** 美元价格 */
    public Integer amprice() { return m_namprice; }
    public Integer getM_namprice() {return m_namprice; }
    public static MT_Data_GStoreItem ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GStoreItem Data = new MT_Data_GStoreItem();
		Data.m_nid = reader.getInt();
		Data.m_nitemId = reader.getInt();
		Data.m_nitemIcon = reader.getInt();
		Data.m_nitemCount = reader.getInt();
		Data.m_nitemId2 = reader.getInt();
		Data.m_nitemCount2 = reader.getInt();
		Data.m_nprice = reader.getInt();
		Data.m_niconId = reader.getInt();
		Data.m_sdesc = TableUtil.ReadLString(reader, fileName,"desc",Data.ID());
		Data.m_sapp_item = TableUtil.ReadString(reader);
		Data.m_spay_name = TableUtil.ReadLString(reader, fileName,"pay_name",Data.ID());
		Data.m_nchprice = reader.getInt();
		Data.m_namprice = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_nitemId)) return false;
        if (!TableUtil.IsInvalid(this.m_nitemIcon)) return false;
        if (!TableUtil.IsInvalid(this.m_nitemCount)) return false;
        if (!TableUtil.IsInvalid(this.m_nitemId2)) return false;
        if (!TableUtil.IsInvalid(this.m_nitemCount2)) return false;
        if (!TableUtil.IsInvalid(this.m_nprice)) return false;
        if (!TableUtil.IsInvalid(this.m_niconId)) return false;
        if (!TableUtil.IsInvalid(this.m_sdesc)) return false;
        if (!TableUtil.IsInvalid(this.m_sapp_item)) return false;
        if (!TableUtil.IsInvalid(this.m_spay_name)) return false;
        if (!TableUtil.IsInvalid(this.m_nchprice)) return false;
        if (!TableUtil.IsInvalid(this.m_namprice)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("itemId"))
            return itemId();
        if (str.equals("itemIcon"))
            return itemIcon();
        if (str.equals("itemCount"))
            return itemCount();
        if (str.equals("itemId2"))
            return itemId2();
        if (str.equals("itemCount2"))
            return itemCount2();
        if (str.equals("price"))
            return price();
        if (str.equals("iconId"))
            return iconId();
        if (str.equals("desc"))
            return desc();
        if (str.equals("app_item"))
            return app_item();
        if (str.equals("pay_name"))
            return pay_name();
        if (str.equals("chprice"))
            return chprice();
        if (str.equals("amprice"))
            return amprice();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("itemId =" + itemId() + '\n');
        str += ("itemIcon =" + itemIcon() + '\n');
        str += ("itemCount =" + itemCount() + '\n');
        str += ("itemId2 =" + itemId2() + '\n');
        str += ("itemCount2 =" + itemCount2() + '\n');
        str += ("price =" + price() + '\n');
        str += ("iconId =" + iconId() + '\n');
        str += ("desc =" + desc() + '\n');
        str += ("app_item =" + app_item() + '\n');
        str += ("pay_name =" + pay_name() + '\n');
        str += ("chprice =" + chprice() + '\n');
        str += ("amprice =" + amprice() + '\n');
        return str;
    }
}