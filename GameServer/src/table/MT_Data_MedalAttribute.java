package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_MedalAttribute extends MT_DataBase {
    public static String MD5 = "d208ba4f179303208ced7588aa42e4c8";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private String m_sname ;
    /** 勋章名称 */
    public String name() { return m_sname; }
    public String getM_sname() {return m_sname; }
    private Integer m_nfightVal ;
    /** 战斗力 */
    public Integer fightVal() { return m_nfightVal; }
    public Integer getM_nfightVal() {return m_nfightVal; }
    private Integer m_nQuality ;
    /** 品质 */
    public Integer Quality() { return m_nQuality; }
    public Integer getM_nQuality() {return m_nQuality; }
    private Integer m_nCardColor ;
    /** 花色 */
    public Integer CardColor() { return m_nCardColor; }
    public Integer getM_nCardColor() {return m_nCardColor; }
    private Integer m_nopenLev ;
    /** 开启等级 */
    public Integer openLev() { return m_nopenLev; }
    public Integer getM_nopenLev() {return m_nopenLev; }
    private Int2 m_OpenNeedMoney ;
    /** 开启花费 */
    public Int2 OpenNeedMoney() { return m_OpenNeedMoney; }
    public Int2 getM_OpenNeedMoney() {return m_OpenNeedMoney; }
    private Integer m_nMaxLevel ;
    /** 最大等级 */
    public Integer MaxLevel() { return m_nMaxLevel; }
    public Integer getM_nMaxLevel() {return m_nMaxLevel; }
    private String m_sImage ;
    /** 勋章图片 */
    public String Image() { return m_sImage; }
    public String getM_sImage() {return m_sImage; }
    private Integer m_nneedExp ;
    /** 升级需要的经验 */
    public Integer needExp() { return m_nneedExp; }
    public Integer getM_nneedExp() {return m_nneedExp; }
    private EquipAttr m_Attr0 ;
    /** 攻击 */
    public EquipAttr Attr0() { return m_Attr0; }
    public EquipAttr getM_Attr0() {return m_Attr0; }
    private EquipAttr m_Attr1 ;
    /** 防御 */
    public EquipAttr Attr1() { return m_Attr1; }
    public EquipAttr getM_Attr1() {return m_Attr1; }
    private EquipAttr m_Attr2 ;
    /** 血量 */
    public EquipAttr Attr2() { return m_Attr2; }
    public EquipAttr getM_Attr2() {return m_Attr2; }
    private EquipAttr m_Attr3 ;
    /** 暴击 */
    public EquipAttr Attr3() { return m_Attr3; }
    public EquipAttr getM_Attr3() {return m_Attr3; }
    private EquipAttr m_Attr4 ;
    /** 免爆 */
    public EquipAttr Attr4() { return m_Attr4; }
    public EquipAttr getM_Attr4() {return m_Attr4; }
    private EquipAttr m_Attr5 ;
    /** 命中 */
    public EquipAttr Attr5() { return m_Attr5; }
    public EquipAttr getM_Attr5() {return m_Attr5; }
    private EquipAttr m_Attr6 ;
    /** 闪避 */
    public EquipAttr Attr6() { return m_Attr6; }
    public EquipAttr getM_Attr6() {return m_Attr6; }
    private EquipAttr m_Attr7 ;
    /** 吸血 */
    public EquipAttr Attr7() { return m_Attr7; }
    public EquipAttr getM_Attr7() {return m_Attr7; }
    private EquipAttr m_Attr8 ;
    /** 攻速 */
    public EquipAttr Attr8() { return m_Attr8; }
    public EquipAttr getM_Attr8() {return m_Attr8; }
    public static MT_Data_MedalAttribute ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_MedalAttribute Data = new MT_Data_MedalAttribute();
		Data.m_nindex = reader.getInt();
		Data.m_sname = TableUtil.ReadLString(reader, fileName,"name",Data.ID());
		Data.m_nfightVal = reader.getInt();
		Data.m_nQuality = reader.getInt();
		Data.m_nCardColor = reader.getInt();
		Data.m_nopenLev = reader.getInt();
		Data.m_OpenNeedMoney = Int2.ReadMemory(reader, fileName);
		Data.m_nMaxLevel = reader.getInt();
		Data.m_sImage = TableUtil.ReadString(reader);
		Data.m_nneedExp = reader.getInt();
		Data.m_Attr0 = EquipAttr.ReadMemory(reader, fileName);
		Data.m_Attr1 = EquipAttr.ReadMemory(reader, fileName);
		Data.m_Attr2 = EquipAttr.ReadMemory(reader, fileName);
		Data.m_Attr3 = EquipAttr.ReadMemory(reader, fileName);
		Data.m_Attr4 = EquipAttr.ReadMemory(reader, fileName);
		Data.m_Attr5 = EquipAttr.ReadMemory(reader, fileName);
		Data.m_Attr6 = EquipAttr.ReadMemory(reader, fileName);
		Data.m_Attr7 = EquipAttr.ReadMemory(reader, fileName);
		Data.m_Attr8 = EquipAttr.ReadMemory(reader, fileName);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_sname)) return false;
        if (!TableUtil.IsInvalid(this.m_nfightVal)) return false;
        if (!TableUtil.IsInvalid(this.m_nQuality)) return false;
        if (!TableUtil.IsInvalid(this.m_nCardColor)) return false;
        if (!TableUtil.IsInvalid(this.m_nopenLev)) return false;
        if (!TableUtil.IsInvalid(this.m_OpenNeedMoney)) return false;
        if (!TableUtil.IsInvalid(this.m_nMaxLevel)) return false;
        if (!TableUtil.IsInvalid(this.m_sImage)) return false;
        if (!TableUtil.IsInvalid(this.m_nneedExp)) return false;
        if (!TableUtil.IsInvalid(this.m_Attr0)) return false;
        if (!TableUtil.IsInvalid(this.m_Attr1)) return false;
        if (!TableUtil.IsInvalid(this.m_Attr2)) return false;
        if (!TableUtil.IsInvalid(this.m_Attr3)) return false;
        if (!TableUtil.IsInvalid(this.m_Attr4)) return false;
        if (!TableUtil.IsInvalid(this.m_Attr5)) return false;
        if (!TableUtil.IsInvalid(this.m_Attr6)) return false;
        if (!TableUtil.IsInvalid(this.m_Attr7)) return false;
        if (!TableUtil.IsInvalid(this.m_Attr8)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("name"))
            return name();
        if (str.equals("fightVal"))
            return fightVal();
        if (str.equals("Quality"))
            return Quality();
        if (str.equals("CardColor"))
            return CardColor();
        if (str.equals("openLev"))
            return openLev();
        if (str.equals("OpenNeedMoney"))
            return OpenNeedMoney();
        if (str.equals("MaxLevel"))
            return MaxLevel();
        if (str.equals("Image"))
            return Image();
        if (str.equals("needExp"))
            return needExp();
        if (str.equals("Attr0"))
            return Attr0();
        if (str.equals("Attr1"))
            return Attr1();
        if (str.equals("Attr2"))
            return Attr2();
        if (str.equals("Attr3"))
            return Attr3();
        if (str.equals("Attr4"))
            return Attr4();
        if (str.equals("Attr5"))
            return Attr5();
        if (str.equals("Attr6"))
            return Attr6();
        if (str.equals("Attr7"))
            return Attr7();
        if (str.equals("Attr8"))
            return Attr8();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("name =" + name() + '\n');
        str += ("fightVal =" + fightVal() + '\n');
        str += ("Quality =" + Quality() + '\n');
        str += ("CardColor =" + CardColor() + '\n');
        str += ("openLev =" + openLev() + '\n');
        str += ("OpenNeedMoney =" + OpenNeedMoney() + '\n');
        str += ("MaxLevel =" + MaxLevel() + '\n');
        str += ("Image =" + Image() + '\n');
        str += ("needExp =" + needExp() + '\n');
        str += ("Attr0 =" + Attr0() + '\n');
        str += ("Attr1 =" + Attr1() + '\n');
        str += ("Attr2 =" + Attr2() + '\n');
        str += ("Attr3 =" + Attr3() + '\n');
        str += ("Attr4 =" + Attr4() + '\n');
        str += ("Attr5 =" + Attr5() + '\n');
        str += ("Attr6 =" + Attr6() + '\n');
        str += ("Attr7 =" + Attr7() + '\n');
        str += ("Attr8 =" + Attr8() + '\n');
        return str;
    }
}