package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Item extends MT_DataBase {
    public static String MD5 = "a18cdd4d855e6dbcb0b766219739da64";
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
    private String m_sImage ;
    /** UI图片 */
    public String Image() { return m_sImage; }
    public String getM_sImage() {return m_sImage; }
    private String m_sImage3D ;
    /** 3DIcon */
    public String Image3D() { return m_sImage3D; }
    public String getM_sImage3D() {return m_sImage3D; }
    private String m_sEquipModel ;
    /** 换装模型 */
    public String EquipModel() { return m_sEquipModel; }
    public String getM_sEquipModel() {return m_sEquipModel; }
    private String m_sName ;
    /** 名字 */
    public String Name() { return m_sName; }
    public String getM_sName() {return m_sName; }
    private Int2 m_Debris ;
    /** 碎片属性 */
    public Int2 Debris() { return m_Debris; }
    public Int2 getM_Debris() {return m_Debris; }
    private Integer m_nfightVal ;
    /** 战斗力 */
    public Integer fightVal() { return m_nfightVal; }
    public Integer getM_nfightVal() {return m_nfightVal; }
    private Integer m_nOverlayCount ;
    /** 叠加上限 */
    public Integer OverlayCount() { return m_nOverlayCount; }
    public Integer getM_nOverlayCount() {return m_nOverlayCount; }
    private Integer m_nexp ;
    /** 经验 */
    public Integer exp() { return m_nexp; }
    public Integer getM_nexp() {return m_nexp; }
    private Integer m_nQuality ;
    /** 品质 */
    public Integer Quality() { return m_nQuality; }
    public Integer getM_nQuality() {return m_nQuality; }
    private Int2 m_SalesPrice ;
    /** 出售价格 */
    public Int2 SalesPrice() { return m_SalesPrice; }
    public Int2 getM_SalesPrice() {return m_SalesPrice; }
    private Integer m_nItemPart ;
    /** 装备部位 */
    public Integer ItemPart() { return m_nItemPart; }
    public Integer getM_nItemPart() {return m_nItemPart; }
    private Integer m_nJewelry ;
    /** 饰品分类 */
    public Integer Jewelry() { return m_nJewelry; }
    public Integer getM_nJewelry() {return m_nJewelry; }
    private ArrayList<Int2> m_arrayOpenNeed = new ArrayList<Int2>();
    /** 开启需求 */
    public ArrayList<Int2> OpenNeed() { return m_arrayOpenNeed; }
    public ArrayList<Int2> getM_arrayOpenNeed() {return m_arrayOpenNeed; }
    private Integer m_nOpenLevel ;
    /** 开启等级 */
    public Integer OpenLevel() { return m_nOpenLevel; }
    public Integer getM_nOpenLevel() {return m_nOpenLevel; }
    private Integer m_nHeroLimite ;
    /** 英雄限制 */
    public Integer HeroLimite() { return m_nHeroLimite; }
    public Integer getM_nHeroLimite() {return m_nHeroLimite; }
    private Integer m_nAttr ;
    /** 装备属性 */
    public Integer Attr() { return m_nAttr; }
    public Integer getM_nAttr() {return m_nAttr; }
    private ArrayList<Int2> m_arrayDropOut = new ArrayList<Int2>();
    /** 掉落 */
    public ArrayList<Int2> DropOut() { return m_arrayDropOut; }
    public ArrayList<Int2> getM_arrayDropOut() {return m_arrayDropOut; }
    private Integer m_nCardColor ;
    /** 勋章花色 */
    public Integer CardColor() { return m_nCardColor; }
    public Integer getM_nCardColor() {return m_nCardColor; }
    private Integer m_nExternParam ;
    /** 扩展参数 */
    public Integer ExternParam() { return m_nExternParam; }
    public Integer getM_nExternParam() {return m_nExternParam; }
    private String m_sDescription ;
    /** 物品描述 */
    public String Description() { return m_sDescription; }
    public String getM_sDescription() {return m_sDescription; }
    public static MT_Data_Item ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Item Data = new MT_Data_Item();

        Integer count;
		Data.m_nindex = reader.getInt();
		Data.m_nType = reader.getInt();
		Data.m_sImage = TableUtil.ReadString(reader);
		Data.m_sImage3D = TableUtil.ReadString(reader);
		Data.m_sEquipModel = TableUtil.ReadString(reader);
		Data.m_sName = TableUtil.ReadLString(reader, fileName,"Name",Data.ID());
		Data.m_Debris = Int2.ReadMemory(reader, fileName);
		Data.m_nfightVal = reader.getInt();
		Data.m_nOverlayCount = reader.getInt();
		Data.m_nexp = reader.getInt();
		Data.m_nQuality = reader.getInt();
		Data.m_SalesPrice = Int2.ReadMemory(reader, fileName);
		Data.m_nItemPart = reader.getInt();
		Data.m_nJewelry = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayOpenNeed.add(Int2.ReadMemory(reader, fileName));
        }

		Data.m_nOpenLevel = reader.getInt();
		Data.m_nHeroLimite = reader.getInt();
		Data.m_nAttr = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayDropOut.add(Int2.ReadMemory(reader, fileName));
        }

		Data.m_nCardColor = reader.getInt();
		Data.m_nExternParam = reader.getInt();
		Data.m_sDescription = TableUtil.ReadLString(reader, fileName,"Description",Data.ID());
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_nType)) return false;
        if (!TableUtil.IsInvalid(this.m_sImage)) return false;
        if (!TableUtil.IsInvalid(this.m_sImage3D)) return false;
        if (!TableUtil.IsInvalid(this.m_sEquipModel)) return false;
        if (!TableUtil.IsInvalid(this.m_sName)) return false;
        if (!TableUtil.IsInvalid(this.m_Debris)) return false;
        if (!TableUtil.IsInvalid(this.m_nfightVal)) return false;
        if (!TableUtil.IsInvalid(this.m_nOverlayCount)) return false;
        if (!TableUtil.IsInvalid(this.m_nexp)) return false;
        if (!TableUtil.IsInvalid(this.m_nQuality)) return false;
        if (!TableUtil.IsInvalid(this.m_SalesPrice)) return false;
        if (!TableUtil.IsInvalid(this.m_nItemPart)) return false;
        if (!TableUtil.IsInvalid(this.m_nJewelry)) return false;
        if (this.m_arrayOpenNeed.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_nOpenLevel)) return false;
        if (!TableUtil.IsInvalid(this.m_nHeroLimite)) return false;
        if (!TableUtil.IsInvalid(this.m_nAttr)) return false;
        if (this.m_arrayDropOut.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_nCardColor)) return false;
        if (!TableUtil.IsInvalid(this.m_nExternParam)) return false;
        if (!TableUtil.IsInvalid(this.m_sDescription)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("Type"))
            return Type();
        if (str.equals("Image"))
            return Image();
        if (str.equals("Image3D"))
            return Image3D();
        if (str.equals("EquipModel"))
            return EquipModel();
        if (str.equals("Name"))
            return Name();
        if (str.equals("Debris"))
            return Debris();
        if (str.equals("fightVal"))
            return fightVal();
        if (str.equals("OverlayCount"))
            return OverlayCount();
        if (str.equals("exp"))
            return exp();
        if (str.equals("Quality"))
            return Quality();
        if (str.equals("SalesPrice"))
            return SalesPrice();
        if (str.equals("ItemPart"))
            return ItemPart();
        if (str.equals("Jewelry"))
            return Jewelry();
        if (str.equals("OpenNeed"))
            return OpenNeed();
        if (str.equals("OpenLevel"))
            return OpenLevel();
        if (str.equals("HeroLimite"))
            return HeroLimite();
        if (str.equals("Attr"))
            return Attr();
        if (str.equals("DropOut"))
            return DropOut();
        if (str.equals("CardColor"))
            return CardColor();
        if (str.equals("ExternParam"))
            return ExternParam();
        if (str.equals("Description"))
            return Description();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("Type =" + Type() + '\n');
        str += ("Image =" + Image() + '\n');
        str += ("Image3D =" + Image3D() + '\n');
        str += ("EquipModel =" + EquipModel() + '\n');
        str += ("Name =" + Name() + '\n');
        str += ("Debris =" + Debris() + '\n');
        str += ("fightVal =" + fightVal() + '\n');
        str += ("OverlayCount =" + OverlayCount() + '\n');
        str += ("exp =" + exp() + '\n');
        str += ("Quality =" + Quality() + '\n');
        str += ("SalesPrice =" + SalesPrice() + '\n');
        str += ("ItemPart =" + ItemPart() + '\n');
        str += ("Jewelry =" + Jewelry() + '\n');
        str += ("OpenNeed =" + OpenNeed() + '\n');
        str += ("OpenLevel =" + OpenLevel() + '\n');
        str += ("HeroLimite =" + HeroLimite() + '\n');
        str += ("Attr =" + Attr() + '\n');
        str += ("DropOut =" + DropOut() + '\n');
        str += ("CardColor =" + CardColor() + '\n');
        str += ("ExternParam =" + ExternParam() + '\n');
        str += ("Description =" + Description() + '\n');
        return str;
    }
}