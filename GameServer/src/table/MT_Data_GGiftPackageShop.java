package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GGiftPackageShop extends MT_DataBase {
    public static String MD5 = "2346d11900a96b96e6f148a97a519a66";
    private Integer m_nid ;
    /** 编号 */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** 编号 */
    public Integer ID() { return m_nid; }
    private Integer m_nitemTempId ;
    /** 礼包item模板id */
    public Integer itemTempId() { return m_nitemTempId; }
    public Integer getM_nitemTempId() {return m_nitemTempId; }
    private String m_spay_name ;
    /** 支付名字 */
    public String pay_name() { return m_spay_name; }
    public String getM_spay_name() {return m_spay_name; }
    private String m_sdesc ;
    /** 描述 */
    public String desc() { return m_sdesc; }
    public String getM_sdesc() {return m_sdesc; }
    private Integer m_nprice ;
    /** 价格(元) */
    public Integer price() { return m_nprice; }
    public Integer getM_nprice() {return m_nprice; }
    private Integer m_nbought_itemTempId ;
    /** 是否已经购买的标志（Gitem) */
    public Integer bought_itemTempId() { return m_nbought_itemTempId; }
    public Integer getM_nbought_itemTempId() {return m_nbought_itemTempId; }
    private Integer m_nmax_count ;
    /** 最大购买次数 */
    public Integer max_count() { return m_nmax_count; }
    public Integer getM_nmax_count() {return m_nmax_count; }
    private String m_sapp_item ;
    /** appstore */
    public String app_item() { return m_sapp_item; }
    public String getM_sapp_item() {return m_sapp_item; }
    private ArrayList<Integer> m_arrayitems = new ArrayList<Integer>();
    /** 包含的物品（tempId) */
    public ArrayList<Integer> items() { return m_arrayitems; }
    public ArrayList<Integer> getM_arrayitems() {return m_arrayitems; }
    private Integer m_nchprice ;
    /** 人民币价格 */
    public Integer chprice() { return m_nchprice; }
    public Integer getM_nchprice() {return m_nchprice; }
    private Integer m_namprice ;
    /** 美元价格 */
    public Integer amprice() { return m_namprice; }
    public Integer getM_namprice() {return m_namprice; }
    public static MT_Data_GGiftPackageShop ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GGiftPackageShop Data = new MT_Data_GGiftPackageShop();

        Integer count;
		Data.m_nid = reader.getInt();
		Data.m_nitemTempId = reader.getInt();
		Data.m_spay_name = TableUtil.ReadLString(reader, fileName,"pay_name",Data.ID());
		Data.m_sdesc = TableUtil.ReadLString(reader, fileName,"desc",Data.ID());
		Data.m_nprice = reader.getInt();
		Data.m_nbought_itemTempId = reader.getInt();
		Data.m_nmax_count = reader.getInt();
		Data.m_sapp_item = TableUtil.ReadString(reader);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayitems.add(reader.getInt());
        }

		Data.m_nchprice = reader.getInt();
		Data.m_namprice = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_nitemTempId)) return false;
        if (!TableUtil.IsInvalid(this.m_spay_name)) return false;
        if (!TableUtil.IsInvalid(this.m_sdesc)) return false;
        if (!TableUtil.IsInvalid(this.m_nprice)) return false;
        if (!TableUtil.IsInvalid(this.m_nbought_itemTempId)) return false;
        if (!TableUtil.IsInvalid(this.m_nmax_count)) return false;
        if (!TableUtil.IsInvalid(this.m_sapp_item)) return false;
        if (this.m_arrayitems.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_nchprice)) return false;
        if (!TableUtil.IsInvalid(this.m_namprice)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("itemTempId"))
            return itemTempId();
        if (str.equals("pay_name"))
            return pay_name();
        if (str.equals("desc"))
            return desc();
        if (str.equals("price"))
            return price();
        if (str.equals("bought_itemTempId"))
            return bought_itemTempId();
        if (str.equals("max_count"))
            return max_count();
        if (str.equals("app_item"))
            return app_item();
        if (str.equals("items"))
            return items();
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
        str += ("itemTempId =" + itemTempId() + '\n');
        str += ("pay_name =" + pay_name() + '\n');
        str += ("desc =" + desc() + '\n');
        str += ("price =" + price() + '\n');
        str += ("bought_itemTempId =" + bought_itemTempId() + '\n');
        str += ("max_count =" + max_count() + '\n');
        str += ("app_item =" + app_item() + '\n');
        str += ("items =" + items() + '\n');
        str += ("chprice =" + chprice() + '\n');
        str += ("amprice =" + amprice() + '\n');
        return str;
    }
}