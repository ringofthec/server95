package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Corps extends MT_DataBase {
    public static String MD5 = "4a04d7c5fabf99aabfc3132283f48f02";
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
    private String m_sFile ;
    /** 模型路径 */
    public String File() { return m_sFile; }
    public String getM_sFile() {return m_sFile; }
    private Float2 m_FightOffset ;
    /** 战斗偏移 */
    public Float2 FightOffset() { return m_FightOffset; }
    public Float2 getM_FightOffset() {return m_FightOffset; }
    private String m_sImage ;
    /** UI图片 */
    public String Image() { return m_sImage; }
    public String getM_sImage() {return m_sImage; }
    private Int3 m_colour ;
    /** 兵种颜色 */
    public Int3 colour() { return m_colour; }
    public Int3 getM_colour() {return m_colour; }
    private Float2 m_HeadScale ;
    /** 兵种头像大小 */
    public Float2 HeadScale() { return m_HeadScale; }
    public Float2 getM_HeadScale() {return m_HeadScale; }
    private Integer m_nTimer ;
    /** 产出时间 */
    public Integer Timer() { return m_nTimer; }
    public Integer getM_nTimer() {return m_nTimer; }
    private Int2 m_NeedResource ;
    /** 产出需要资源 */
    public Int2 NeedResource() { return m_NeedResource; }
    public Int2 getM_NeedResource() {return m_NeedResource; }
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
    private Integer m_nPopulation ;
    /** 占有人口 */
    public Integer Population() { return m_nPopulation; }
    public Integer getM_nPopulation() {return m_nPopulation; }
    private Int2 m_Size ;
    /** 战斗占用格子大小 */
    public Int2 Size() { return m_Size; }
    public Int2 getM_Size() {return m_Size; }
    private String m_sShadowModel ;
    /** 影子（如果不填用默认影子） */
    public String ShadowModel() { return m_sShadowModel; }
    public String getM_sShadowModel() {return m_sShadowModel; }
    private Float2 m_ShadowScale ;
    /** 影子缩放(单位是格子) */
    public Float2 ShadowScale() { return m_ShadowScale; }
    public Float2 getM_ShadowScale() {return m_ShadowScale; }
    private Float2 m_ShadowOffset ;
    /** 影子偏移(单位是格子) */
    public Float2 ShadowOffset() { return m_ShadowOffset; }
    public Float2 getM_ShadowOffset() {return m_ShadowOffset; }
    private Integer m_nSkill ;
    /** 技能 */
    public Integer Skill() { return m_nSkill; }
    public Integer getM_nSkill() {return m_nSkill; }
    private Integer m_nAttackDistance ;
    /** 攻击距离 */
    public Integer AttackDistance() { return m_nAttackDistance; }
    public Integer getM_nAttackDistance() {return m_nAttackDistance; }
    private Integer m_nAttackTarget ;
    /** 攻击目标 */
    public Integer AttackTarget() { return m_nAttackTarget; }
    public Integer getM_nAttackTarget() {return m_nAttackTarget; }
    private Integer m_nHp ;
    /** 血量 */
    public Integer Hp() { return m_nHp; }
    public Integer getM_nHp() {return m_nHp; }
    private Integer m_nAttack ;
    /** 攻击力 */
    public Integer Attack() { return m_nAttack; }
    public Integer getM_nAttack() {return m_nAttack; }
    private Integer m_nAttackType ;
    /** 攻击类型 */
    public Integer AttackType() { return m_nAttackType; }
    public Integer getM_nAttackType() {return m_nAttackType; }
    private Integer m_nArmor ;
    /** 护甲 */
    public Integer Armor() { return m_nArmor; }
    public Integer getM_nArmor() {return m_nArmor; }
    private Integer m_nArmorType ;
    /** 护甲类型 */
    public Integer ArmorType() { return m_nArmorType; }
    public Integer getM_nArmorType() {return m_nArmorType; }
    private float m_fAttackSpeed ;
    /** 攻击速度 */
    public float AttackSpeed() { return m_fAttackSpeed; }
    public float getM_fAttackSpeed() {return m_fAttackSpeed; }
    private Integer m_nCrit ;
    /** 暴击率 */
    public Integer Crit() { return m_nCrit; }
    public Integer getM_nCrit() {return m_nCrit; }
    private Integer m_nImmuneCrit ;
    /** 免爆 */
    public Integer ImmuneCrit() { return m_nImmuneCrit; }
    public Integer getM_nImmuneCrit() {return m_nImmuneCrit; }
    private Integer m_nHit ;
    /** 命中 */
    public Integer Hit() { return m_nHit; }
    public Integer getM_nHit() {return m_nHit; }
    private Integer m_nDodge ;
    /** 闪避率 */
    public Integer Dodge() { return m_nDodge; }
    public Integer getM_nDodge() {return m_nDodge; }
    private Int3 m_Vampire ;
    /** 吸血 */
    public Int3 Vampire() { return m_Vampire; }
    public Int3 getM_Vampire() {return m_Vampire; }
    private float m_fMoveSpeed ;
    /** 移动速度 */
    public float MoveSpeed() { return m_fMoveSpeed; }
    public float getM_fMoveSpeed() {return m_fMoveSpeed; }
    private Float3 m_FightScale ;
    /** 战斗模型缩放 */
    public Float3 FightScale() { return m_FightScale; }
    public Float3 getM_FightScale() {return m_FightScale; }
    private String m_sDieSound ;
    /** 兵种死亡时的音效 */
    public String DieSound() { return m_sDieSound; }
    public String getM_sDieSound() {return m_sDieSound; }
    private Float2 m_FactoryShadowPos ;
    /** 产兵界面影子偏移 */
    public Float2 FactoryShadowPos() { return m_FactoryShadowPos; }
    public Float2 getM_FactoryShadowPos() {return m_FactoryShadowPos; }
    private Float2 m_FactoryShadowScale ;
    /** 产兵界面影子缩放 */
    public Float2 FactoryShadowScale() { return m_FactoryShadowScale; }
    public Float2 getM_FactoryShadowScale() {return m_FactoryShadowScale; }
    private Float2 m_FactorySlidePos ;
    /** 产兵界面进度条偏移 */
    public Float2 FactorySlidePos() { return m_FactorySlidePos; }
    public Float2 getM_FactorySlidePos() {return m_FactorySlidePos; }
    private Integer m_nPower ;
    /** 战斗力 */
    public Integer Power() { return m_nPower; }
    public Integer getM_nPower() {return m_nPower; }
    public static MT_Data_Corps ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Corps Data = new MT_Data_Corps();
		Data.m_nindex = reader.getInt();
		Data.m_sName = TableUtil.ReadLString(reader, fileName,"Name",Data.ID());
		Data.m_sFile = TableUtil.ReadString(reader);
		Data.m_FightOffset = Float2.ReadMemory(reader, fileName);
		Data.m_sImage = TableUtil.ReadString(reader);
		Data.m_colour = Int3.ReadMemory(reader, fileName);
		Data.m_HeadScale = Float2.ReadMemory(reader, fileName);
		Data.m_nTimer = reader.getInt();
		Data.m_NeedResource = Int2.ReadMemory(reader, fileName);
		Data.m_nUpgradeNeedMoney = reader.getInt();
		Data.m_nUpgradeNeedRare = reader.getInt();
		Data.m_nUpgradeNeedTime = reader.getInt();
		Data.m_nUpgradePlayerLv = reader.getInt();
		Data.m_UpgradeBuildLv = Int2.ReadMemory(reader, fileName);
		Data.m_nPopulation = reader.getInt();
		Data.m_Size = Int2.ReadMemory(reader, fileName);
		Data.m_sShadowModel = TableUtil.ReadString(reader);
		Data.m_ShadowScale = Float2.ReadMemory(reader, fileName);
		Data.m_ShadowOffset = Float2.ReadMemory(reader, fileName);
		Data.m_nSkill = reader.getInt();
		Data.m_nAttackDistance = reader.getInt();
		Data.m_nAttackTarget = reader.getInt();
		Data.m_nHp = reader.getInt();
		Data.m_nAttack = reader.getInt();
		Data.m_nAttackType = reader.getInt();
		Data.m_nArmor = reader.getInt();
		Data.m_nArmorType = reader.getInt();
		Data.m_fAttackSpeed = reader.getFloat();
		Data.m_nCrit = reader.getInt();
		Data.m_nImmuneCrit = reader.getInt();
		Data.m_nHit = reader.getInt();
		Data.m_nDodge = reader.getInt();
		Data.m_Vampire = Int3.ReadMemory(reader, fileName);
		Data.m_fMoveSpeed = reader.getFloat();
		Data.m_FightScale = Float3.ReadMemory(reader, fileName);
		Data.m_sDieSound = TableUtil.ReadString(reader);
		Data.m_FactoryShadowPos = Float2.ReadMemory(reader, fileName);
		Data.m_FactoryShadowScale = Float2.ReadMemory(reader, fileName);
		Data.m_FactorySlidePos = Float2.ReadMemory(reader, fileName);
		Data.m_nPower = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_sName)) return false;
        if (!TableUtil.IsInvalid(this.m_sFile)) return false;
        if (!TableUtil.IsInvalid(this.m_FightOffset)) return false;
        if (!TableUtil.IsInvalid(this.m_sImage)) return false;
        if (!TableUtil.IsInvalid(this.m_colour)) return false;
        if (!TableUtil.IsInvalid(this.m_HeadScale)) return false;
        if (!TableUtil.IsInvalid(this.m_nTimer)) return false;
        if (!TableUtil.IsInvalid(this.m_NeedResource)) return false;
        if (!TableUtil.IsInvalid(this.m_nUpgradeNeedMoney)) return false;
        if (!TableUtil.IsInvalid(this.m_nUpgradeNeedRare)) return false;
        if (!TableUtil.IsInvalid(this.m_nUpgradeNeedTime)) return false;
        if (!TableUtil.IsInvalid(this.m_nUpgradePlayerLv)) return false;
        if (!TableUtil.IsInvalid(this.m_UpgradeBuildLv)) return false;
        if (!TableUtil.IsInvalid(this.m_nPopulation)) return false;
        if (!TableUtil.IsInvalid(this.m_Size)) return false;
        if (!TableUtil.IsInvalid(this.m_sShadowModel)) return false;
        if (!TableUtil.IsInvalid(this.m_ShadowScale)) return false;
        if (!TableUtil.IsInvalid(this.m_ShadowOffset)) return false;
        if (!TableUtil.IsInvalid(this.m_nSkill)) return false;
        if (!TableUtil.IsInvalid(this.m_nAttackDistance)) return false;
        if (!TableUtil.IsInvalid(this.m_nAttackTarget)) return false;
        if (!TableUtil.IsInvalid(this.m_nHp)) return false;
        if (!TableUtil.IsInvalid(this.m_nAttack)) return false;
        if (!TableUtil.IsInvalid(this.m_nAttackType)) return false;
        if (!TableUtil.IsInvalid(this.m_nArmor)) return false;
        if (!TableUtil.IsInvalid(this.m_nArmorType)) return false;
        if (!TableUtil.IsInvalid(this.m_fAttackSpeed)) return false;
        if (!TableUtil.IsInvalid(this.m_nCrit)) return false;
        if (!TableUtil.IsInvalid(this.m_nImmuneCrit)) return false;
        if (!TableUtil.IsInvalid(this.m_nHit)) return false;
        if (!TableUtil.IsInvalid(this.m_nDodge)) return false;
        if (!TableUtil.IsInvalid(this.m_Vampire)) return false;
        if (!TableUtil.IsInvalid(this.m_fMoveSpeed)) return false;
        if (!TableUtil.IsInvalid(this.m_FightScale)) return false;
        if (!TableUtil.IsInvalid(this.m_sDieSound)) return false;
        if (!TableUtil.IsInvalid(this.m_FactoryShadowPos)) return false;
        if (!TableUtil.IsInvalid(this.m_FactoryShadowScale)) return false;
        if (!TableUtil.IsInvalid(this.m_FactorySlidePos)) return false;
        if (!TableUtil.IsInvalid(this.m_nPower)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("Name"))
            return Name();
        if (str.equals("File"))
            return File();
        if (str.equals("FightOffset"))
            return FightOffset();
        if (str.equals("Image"))
            return Image();
        if (str.equals("colour"))
            return colour();
        if (str.equals("HeadScale"))
            return HeadScale();
        if (str.equals("Timer"))
            return Timer();
        if (str.equals("NeedResource"))
            return NeedResource();
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
        if (str.equals("Population"))
            return Population();
        if (str.equals("Size"))
            return Size();
        if (str.equals("ShadowModel"))
            return ShadowModel();
        if (str.equals("ShadowScale"))
            return ShadowScale();
        if (str.equals("ShadowOffset"))
            return ShadowOffset();
        if (str.equals("Skill"))
            return Skill();
        if (str.equals("AttackDistance"))
            return AttackDistance();
        if (str.equals("AttackTarget"))
            return AttackTarget();
        if (str.equals("Hp"))
            return Hp();
        if (str.equals("Attack"))
            return Attack();
        if (str.equals("AttackType"))
            return AttackType();
        if (str.equals("Armor"))
            return Armor();
        if (str.equals("ArmorType"))
            return ArmorType();
        if (str.equals("AttackSpeed"))
            return AttackSpeed();
        if (str.equals("Crit"))
            return Crit();
        if (str.equals("ImmuneCrit"))
            return ImmuneCrit();
        if (str.equals("Hit"))
            return Hit();
        if (str.equals("Dodge"))
            return Dodge();
        if (str.equals("Vampire"))
            return Vampire();
        if (str.equals("MoveSpeed"))
            return MoveSpeed();
        if (str.equals("FightScale"))
            return FightScale();
        if (str.equals("DieSound"))
            return DieSound();
        if (str.equals("FactoryShadowPos"))
            return FactoryShadowPos();
        if (str.equals("FactoryShadowScale"))
            return FactoryShadowScale();
        if (str.equals("FactorySlidePos"))
            return FactorySlidePos();
        if (str.equals("Power"))
            return Power();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("Name =" + Name() + '\n');
        str += ("File =" + File() + '\n');
        str += ("FightOffset =" + FightOffset() + '\n');
        str += ("Image =" + Image() + '\n');
        str += ("colour =" + colour() + '\n');
        str += ("HeadScale =" + HeadScale() + '\n');
        str += ("Timer =" + Timer() + '\n');
        str += ("NeedResource =" + NeedResource() + '\n');
        str += ("UpgradeNeedMoney =" + UpgradeNeedMoney() + '\n');
        str += ("UpgradeNeedRare =" + UpgradeNeedRare() + '\n');
        str += ("UpgradeNeedTime =" + UpgradeNeedTime() + '\n');
        str += ("UpgradePlayerLv =" + UpgradePlayerLv() + '\n');
        str += ("UpgradeBuildLv =" + UpgradeBuildLv() + '\n');
        str += ("Population =" + Population() + '\n');
        str += ("Size =" + Size() + '\n');
        str += ("ShadowModel =" + ShadowModel() + '\n');
        str += ("ShadowScale =" + ShadowScale() + '\n');
        str += ("ShadowOffset =" + ShadowOffset() + '\n');
        str += ("Skill =" + Skill() + '\n');
        str += ("AttackDistance =" + AttackDistance() + '\n');
        str += ("AttackTarget =" + AttackTarget() + '\n');
        str += ("Hp =" + Hp() + '\n');
        str += ("Attack =" + Attack() + '\n');
        str += ("AttackType =" + AttackType() + '\n');
        str += ("Armor =" + Armor() + '\n');
        str += ("ArmorType =" + ArmorType() + '\n');
        str += ("AttackSpeed =" + AttackSpeed() + '\n');
        str += ("Crit =" + Crit() + '\n');
        str += ("ImmuneCrit =" + ImmuneCrit() + '\n');
        str += ("Hit =" + Hit() + '\n');
        str += ("Dodge =" + Dodge() + '\n');
        str += ("Vampire =" + Vampire() + '\n');
        str += ("MoveSpeed =" + MoveSpeed() + '\n');
        str += ("FightScale =" + FightScale() + '\n');
        str += ("DieSound =" + DieSound() + '\n');
        str += ("FactoryShadowPos =" + FactoryShadowPos() + '\n');
        str += ("FactoryShadowScale =" + FactoryShadowScale() + '\n');
        str += ("FactorySlidePos =" + FactorySlidePos() + '\n');
        str += ("Power =" + Power() + '\n');
        return str;
    }
}