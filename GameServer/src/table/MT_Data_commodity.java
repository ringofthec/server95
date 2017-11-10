package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_commodity extends MT_DataBase {
    public static String MD5 = "6fe985a7eae4be2199e9b2fe6139935c";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private Integer m_nType ;
    /** 类型 */
    public Integer Type() { return m_nType; }
    public Integer getM_nType() {return m_nType; }
    private String m_sName ;
    /** 名字 */
    public String Name() { return m_sName; }
    public String getM_sName() {return m_sName; }
    private String m_sImage ;
    /** 图片 */
    public String Image() { return m_sImage; }
    public String getM_sImage() {return m_sImage; }
    private Int2 m_Attr ;
    /** 出售价格 */
    public Int2 Attr() { return m_Attr; }
    public Int2 getM_Attr() {return m_Attr; }
    private Integer m_nneedLev ;
    /** 所需玩家等级 */
    public Integer needLev() { return m_nneedLev; }
    public Integer getM_nneedLev() {return m_nneedLev; }
    private Integer m_nquality ;
    /** 品质 */
    public Integer quality() { return m_nquality; }
    public Integer getM_nquality() {return m_nquality; }
    private Integer m_nnum ;
    /** 购买次数/天 */
    public Integer num() { return m_nnum; }
    public Integer getM_nnum() {return m_nnum; }
    private Integer m_nmaxNum ;
    /** 全服购买最大值 */
    public Integer maxNum() { return m_nmaxNum; }
    public Integer getM_nmaxNum() {return m_nmaxNum; }
    private Integer m_nitemTableId ;
    /** Item表id */
    public Integer itemTableId() { return m_nitemTableId; }
    public Integer getM_nitemTableId() {return m_nitemTableId; }
    private Integer m_nbuyNum ;
    /** 单次购买数量 */
    public Integer buyNum() { return m_nbuyNum; }
    public Integer getM_nbuyNum() {return m_nbuyNum; }
    private Int2 m_ratio ;
    /** 兑换比例 */
    public Int2 ratio() { return m_ratio; }
    public Int2 getM_ratio() {return m_ratio; }
    private Integer m_nbelong ;
    /** 是否属于某一物品 */
    public Integer belong() { return m_nbelong; }
    public Integer getM_nbelong() {return m_nbelong; }
    private Integer m_nsuitHero ;
    /** 适合用英雄 */
    public Integer suitHero() { return m_nsuitHero; }
    public Integer getM_nsuitHero() {return m_nsuitHero; }
    private String m_sDescription ;
    /** 商品描述 */
    public String Description() { return m_sDescription; }
    public String getM_sDescription() {return m_sDescription; }
    public static MT_Data_commodity ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_commodity Data = new MT_Data_commodity();
		Data.m_nindex = reader.getInt();
		Data.m_nType = reader.getInt();
		Data.m_sName = TableUtil.ReadLString(reader, fileName,"Name",Data.ID());
		Data.m_sImage = TableUtil.ReadString(reader);
		Data.m_Attr = Int2.ReadMemory(reader, fileName);
		Data.m_nneedLev = reader.getInt();
		Data.m_nquality = reader.getInt();
		Data.m_nnum = reader.getInt();
		Data.m_nmaxNum = reader.getInt();
		Data.m_nitemTableId = reader.getInt();
		Data.m_nbuyNum = reader.getInt();
		Data.m_ratio = Int2.ReadMemory(reader, fileName);
		Data.m_nbelong = reader.getInt();
		Data.m_nsuitHero = reader.getInt();
		Data.m_sDescription = TableUtil.ReadLString(reader, fileName,"Description",Data.ID());
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_nType)) return false;
        if (!TableUtil.IsInvalid(this.m_sName)) return false;
        if (!TableUtil.IsInvalid(this.m_sImage)) return false;
        if (!TableUtil.IsInvalid(this.m_Attr)) return false;
        if (!TableUtil.IsInvalid(this.m_nneedLev)) return false;
        if (!TableUtil.IsInvalid(this.m_nquality)) return false;
        if (!TableUtil.IsInvalid(this.m_nnum)) return false;
        if (!TableUtil.IsInvalid(this.m_nmaxNum)) return false;
        if (!TableUtil.IsInvalid(this.m_nitemTableId)) return false;
        if (!TableUtil.IsInvalid(this.m_nbuyNum)) return false;
        if (!TableUtil.IsInvalid(this.m_ratio)) return false;
        if (!TableUtil.IsInvalid(this.m_nbelong)) return false;
        if (!TableUtil.IsInvalid(this.m_nsuitHero)) return false;
        if (!TableUtil.IsInvalid(this.m_sDescription)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("Type"))
            return Type();
        if (str.equals("Name"))
            return Name();
        if (str.equals("Image"))
            return Image();
        if (str.equals("Attr"))
            return Attr();
        if (str.equals("needLev"))
            return needLev();
        if (str.equals("quality"))
            return quality();
        if (str.equals("num"))
            return num();
        if (str.equals("maxNum"))
            return maxNum();
        if (str.equals("itemTableId"))
            return itemTableId();
        if (str.equals("buyNum"))
            return buyNum();
        if (str.equals("ratio"))
            return ratio();
        if (str.equals("belong"))
            return belong();
        if (str.equals("suitHero"))
            return suitHero();
        if (str.equals("Description"))
            return Description();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("Type =" + Type() + '\n');
        str += ("Name =" + Name() + '\n');
        str += ("Image =" + Image() + '\n');
        str += ("Attr =" + Attr() + '\n');
        str += ("needLev =" + needLev() + '\n');
        str += ("quality =" + quality() + '\n');
        str += ("num =" + num() + '\n');
        str += ("maxNum =" + maxNum() + '\n');
        str += ("itemTableId =" + itemTableId() + '\n');
        str += ("buyNum =" + buyNum() + '\n');
        str += ("ratio =" + ratio() + '\n');
        str += ("belong =" + belong() + '\n');
        str += ("suitHero =" + suitHero() + '\n');
        str += ("Description =" + Description() + '\n');
        return str;
    }
}