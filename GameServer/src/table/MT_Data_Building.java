package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Building extends MT_DataBase {
    public static String MD5 = "56fe81f79dceac470a26d7117adf5f11";
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
    private Integer m_nShopType ;
    /** 商店中的出售位置 */
    public Integer ShopType() { return m_nShopType; }
    public Integer getM_nShopType() {return m_nShopType; }
    private String m_sImageName ;
    /** 模型路径 */
    public String ImageName() { return m_sImageName; }
    public String getM_sImageName() {return m_sImageName; }
    private String m_sSpriteName ;
    /** 相关脚本名 */
    public String SpriteName() { return m_sSpriteName; }
    public String getM_sSpriteName() {return m_sSpriteName; }
    private Integer m_nMaxLevel ;
    /** 建筑开放最高等级 */
    public Integer MaxLevel() { return m_nMaxLevel; }
    public Integer getM_nMaxLevel() {return m_nMaxLevel; }
    private Float3 m_Offset ;
    /** 建筑文字偏移 */
    public Float3 Offset() { return m_Offset; }
    public Float3 getM_Offset() {return m_Offset; }
    private Float3 m_LevelOffset ;
    /** 等级偏移 */
    public Float3 LevelOffset() { return m_LevelOffset; }
    public Float3 getM_LevelOffset() {return m_LevelOffset; }
    private String m_sWarEffect ;
    /** 战火特效 */
    public String WarEffect() { return m_sWarEffect; }
    public String getM_sWarEffect() {return m_sWarEffect; }
    private Float3 m_WarScale ;
    /** 战火缩放 */
    public Float3 WarScale() { return m_WarScale; }
    public Float3 getM_WarScale() {return m_WarScale; }
    private Float3 m_WarOffset ;
    /** 战火偏移 */
    public Float3 WarOffset() { return m_WarOffset; }
    public Float3 getM_WarOffset() {return m_WarOffset; }
    private Integer m_nWarOdds ;
    /** 战火出现概率 */
    public Integer WarOdds() { return m_nWarOdds; }
    public Integer getM_nWarOdds() {return m_nWarOdds; }
    private String m_sBuildText ;
    /** 建筑描述 */
    public String BuildText() { return m_sBuildText; }
    public String getM_sBuildText() {return m_sBuildText; }
    private String m_sname ;
    /** 建筑名称(后台管理使用) */
    public String name() { return m_sname; }
    public String getM_sname() {return m_sname; }
    public static MT_Data_Building ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Building Data = new MT_Data_Building();
		Data.m_nindex = reader.getInt();
		Data.m_nType = reader.getInt();
		Data.m_nShopType = reader.getInt();
		Data.m_sImageName = TableUtil.ReadString(reader);
		Data.m_sSpriteName = TableUtil.ReadString(reader);
		Data.m_nMaxLevel = reader.getInt();
		Data.m_Offset = Float3.ReadMemory(reader, fileName);
		Data.m_LevelOffset = Float3.ReadMemory(reader, fileName);
		Data.m_sWarEffect = TableUtil.ReadString(reader);
		Data.m_WarScale = Float3.ReadMemory(reader, fileName);
		Data.m_WarOffset = Float3.ReadMemory(reader, fileName);
		Data.m_nWarOdds = reader.getInt();
		Data.m_sBuildText = TableUtil.ReadLString(reader, fileName,"BuildText",Data.ID());
		Data.m_sname = TableUtil.ReadString(reader);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_nType)) return false;
        if (!TableUtil.IsInvalid(this.m_nShopType)) return false;
        if (!TableUtil.IsInvalid(this.m_sImageName)) return false;
        if (!TableUtil.IsInvalid(this.m_sSpriteName)) return false;
        if (!TableUtil.IsInvalid(this.m_nMaxLevel)) return false;
        if (!TableUtil.IsInvalid(this.m_Offset)) return false;
        if (!TableUtil.IsInvalid(this.m_LevelOffset)) return false;
        if (!TableUtil.IsInvalid(this.m_sWarEffect)) return false;
        if (!TableUtil.IsInvalid(this.m_WarScale)) return false;
        if (!TableUtil.IsInvalid(this.m_WarOffset)) return false;
        if (!TableUtil.IsInvalid(this.m_nWarOdds)) return false;
        if (!TableUtil.IsInvalid(this.m_sBuildText)) return false;
        if (!TableUtil.IsInvalid(this.m_sname)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("Type"))
            return Type();
        if (str.equals("ShopType"))
            return ShopType();
        if (str.equals("ImageName"))
            return ImageName();
        if (str.equals("SpriteName"))
            return SpriteName();
        if (str.equals("MaxLevel"))
            return MaxLevel();
        if (str.equals("Offset"))
            return Offset();
        if (str.equals("LevelOffset"))
            return LevelOffset();
        if (str.equals("WarEffect"))
            return WarEffect();
        if (str.equals("WarScale"))
            return WarScale();
        if (str.equals("WarOffset"))
            return WarOffset();
        if (str.equals("WarOdds"))
            return WarOdds();
        if (str.equals("BuildText"))
            return BuildText();
        if (str.equals("name"))
            return name();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("Type =" + Type() + '\n');
        str += ("ShopType =" + ShopType() + '\n');
        str += ("ImageName =" + ImageName() + '\n');
        str += ("SpriteName =" + SpriteName() + '\n');
        str += ("MaxLevel =" + MaxLevel() + '\n');
        str += ("Offset =" + Offset() + '\n');
        str += ("LevelOffset =" + LevelOffset() + '\n');
        str += ("WarEffect =" + WarEffect() + '\n');
        str += ("WarScale =" + WarScale() + '\n');
        str += ("WarOffset =" + WarOffset() + '\n');
        str += ("WarOdds =" + WarOdds() + '\n');
        str += ("BuildText =" + BuildText() + '\n');
        str += ("name =" + name() + '\n');
        return str;
    }
}