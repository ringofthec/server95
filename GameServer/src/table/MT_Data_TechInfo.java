package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_TechInfo extends MT_DataBase {
    public static String MD5 = "969c62089befb1d6e964b80b70824c63";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private String m_sName ;
    /** 名字 */
    public String Name() { return m_sName; }
    public String getM_sName() {return m_sName; }
    private String m_sImage ;
    /** 图片 */
    public String Image() { return m_sImage; }
    public String getM_sImage() {return m_sImage; }
    private Integer m_nUpgradeNeedMoney ;
    /** 升级需要金钱 */
    public Integer UpgradeNeedMoney() { return m_nUpgradeNeedMoney; }
    public Integer getM_nUpgradeNeedMoney() {return m_nUpgradeNeedMoney; }
    private Integer m_nUpgradeNeedRare ;
    /** 升级需要稀有 */
    public Integer UpgradeNeedRare() { return m_nUpgradeNeedRare; }
    public Integer getM_nUpgradeNeedRare() {return m_nUpgradeNeedRare; }
    private Integer m_nUpgradeNeedTime ;
    /** 升级需要时间 */
    public Integer UpgradeNeedTime() { return m_nUpgradeNeedTime; }
    public Integer getM_nUpgradeNeedTime() {return m_nUpgradeNeedTime; }
    private Integer m_nUpgradePlayerLv ;
    /** 升级需要玩家等级 */
    public Integer UpgradePlayerLv() { return m_nUpgradePlayerLv; }
    public Integer getM_nUpgradePlayerLv() {return m_nUpgradePlayerLv; }
    private Int2 m_UpgradeBuildLv ;
    /** 升级需要建筑等级 */
    public Int2 UpgradeBuildLv() { return m_UpgradeBuildLv; }
    public Int2 getM_UpgradeBuildLv() {return m_UpgradeBuildLv; }
    private Integer m_nSkill ;
    /** 技能 */
    public Integer Skill() { return m_nSkill; }
    public Integer getM_nSkill() {return m_nSkill; }
    private String m_sText ;
    /** 技能描述 */
    public String Text() { return m_sText; }
    public String getM_sText() {return m_sText; }
    public static MT_Data_TechInfo ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_TechInfo Data = new MT_Data_TechInfo();
		Data.m_nindex = reader.getInt();
		Data.m_sName = TableUtil.ReadLString(reader, fileName,"Name",Data.ID());
		Data.m_sImage = TableUtil.ReadString(reader);
		Data.m_nUpgradeNeedMoney = reader.getInt();
		Data.m_nUpgradeNeedRare = reader.getInt();
		Data.m_nUpgradeNeedTime = reader.getInt();
		Data.m_nUpgradePlayerLv = reader.getInt();
		Data.m_UpgradeBuildLv = Int2.ReadMemory(reader, fileName);
		Data.m_nSkill = reader.getInt();
		Data.m_sText = TableUtil.ReadLString(reader, fileName,"Text",Data.ID());
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_sName)) return false;
        if (!TableUtil.IsInvalid(this.m_sImage)) return false;
        if (!TableUtil.IsInvalid(this.m_nUpgradeNeedMoney)) return false;
        if (!TableUtil.IsInvalid(this.m_nUpgradeNeedRare)) return false;
        if (!TableUtil.IsInvalid(this.m_nUpgradeNeedTime)) return false;
        if (!TableUtil.IsInvalid(this.m_nUpgradePlayerLv)) return false;
        if (!TableUtil.IsInvalid(this.m_UpgradeBuildLv)) return false;
        if (!TableUtil.IsInvalid(this.m_nSkill)) return false;
        if (!TableUtil.IsInvalid(this.m_sText)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("Name"))
            return Name();
        if (str.equals("Image"))
            return Image();
        if (str.equals("UpgradeNeedMoney"))
            return UpgradeNeedMoney();
        if (str.equals("UpgradeNeedRare"))
            return UpgradeNeedRare();
        if (str.equals("UpgradeNeedTime"))
            return UpgradeNeedTime();
        if (str.equals("UpgradePlayerLv"))
            return UpgradePlayerLv();
        if (str.equals("UpgradeBuildLv"))
            return UpgradeBuildLv();
        if (str.equals("Skill"))
            return Skill();
        if (str.equals("Text"))
            return Text();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("Name =" + Name() + '\n');
        str += ("Image =" + Image() + '\n');
        str += ("UpgradeNeedMoney =" + UpgradeNeedMoney() + '\n');
        str += ("UpgradeNeedRare =" + UpgradeNeedRare() + '\n');
        str += ("UpgradeNeedTime =" + UpgradeNeedTime() + '\n');
        str += ("UpgradePlayerLv =" + UpgradePlayerLv() + '\n');
        str += ("UpgradeBuildLv =" + UpgradeBuildLv() + '\n');
        str += ("Skill =" + Skill() + '\n');
        str += ("Text =" + Text() + '\n');
        return str;
    }
}