package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_AirSupport extends MT_DataBase {
    public static String MD5 = "ce8f755fa7cfead1ba5c189184d69d8c";
    private Integer m_nAirID ;
    /** 空军ID */
    public Integer AirID() { return m_nAirID; }
    public Integer getM_nAirID() {return m_nAirID; }
    /** 空军ID */
    public Integer ID() { return m_nAirID; }
    private String m_sName ;
    /** 名称 */
    public String Name() { return m_sName; }
    public String getM_sName() {return m_sName; }
    private String m_sDescribe ;
    /** 描述 */
    public String Describe() { return m_sDescribe; }
    public String getM_sDescribe() {return m_sDescribe; }
    private Int2 m_Price ;
    /** 价格 */
    public Int2 Price() { return m_Price; }
    public Int2 getM_Price() {return m_Price; }
    private Int2 m_UpgradeNeed ;
    /** 升级花费 */
    public Int2 UpgradeNeed() { return m_UpgradeNeed; }
    public Int2 getM_UpgradeNeed() {return m_UpgradeNeed; }
    private Integer m_nLevel ;
    /** 等级开启 */
    public Integer Level() { return m_nLevel; }
    public Integer getM_nLevel() {return m_nLevel; }
    private Integer m_nMaxDurable ;
    /** 最大耐久 */
    public Integer MaxDurable() { return m_nMaxDurable; }
    public Integer getM_nMaxDurable() {return m_nMaxDurable; }
    private Int2 m_RepairPrice ;
    /** 修理一耐久点需要的金钱 */
    public Int2 RepairPrice() { return m_RepairPrice; }
    public Int2 getM_RepairPrice() {return m_RepairPrice; }
    private Integer m_nType ;
    /** 目标类型 */
    public Integer Type() { return m_nType; }
    public Integer getM_nType() {return m_nType; }
    private Int2 m_Range ;
    /** 技能目标范围 */
    public Int2 Range() { return m_Range; }
    public Int2 getM_Range() {return m_Range; }
    private String m_sIcon ;
    /** 图标 */
    public String Icon() { return m_sIcon; }
    public String getM_sIcon() {return m_sIcon; }
    private String m_sImage ;
    /** 飞机原画 */
    public String Image() { return m_sImage; }
    public String getM_sImage() {return m_sImage; }
    private String m_sModelFile ;
    /** 模型路径 */
    public String ModelFile() { return m_sModelFile; }
    public String getM_sModelFile() {return m_sModelFile; }
    private Float2 m_ModelScale ;
    /** 模型大小 */
    public Float2 ModelScale() { return m_ModelScale; }
    public Float2 getM_ModelScale() {return m_ModelScale; }
    private String m_sShadowFile ;
    /** 影子路径 */
    public String ShadowFile() { return m_sShadowFile; }
    public String getM_sShadowFile() {return m_sShadowFile; }
    private Float2 m_ShadowScale ;
    /** 影子大小 */
    public Float2 ShadowScale() { return m_ShadowScale; }
    public Float2 getM_ShadowScale() {return m_ShadowScale; }
    private float m_fCoolDown ;
    /** 冷却时间 */
    public float CoolDown() { return m_fCoolDown; }
    public float getM_fCoolDown() {return m_fCoolDown; }
    private Integer m_nSkill ;
    /** 对应的技能（技能发出点已城堡为原点） */
    public Integer Skill() { return m_nSkill; }
    public Integer getM_nSkill() {return m_nSkill; }
    private float m_fSpeed ;
    /** 飞机飞过速度(以格子为单位) */
    public float Speed() { return m_fSpeed; }
    public float getM_fSpeed() {return m_fSpeed; }
    private float m_fHeight ;
    /** 飞机高度(以格子为单位) */
    public float Height() { return m_fHeight; }
    public float getM_fHeight() {return m_fHeight; }
    private float m_fPreDistance ;
    /** 飞机提前投弹的距离(以格子为单位) */
    public float PreDistance() { return m_fPreDistance; }
    public float getM_fPreDistance() {return m_fPreDistance; }
    private Integer m_nAttack ;
    /** 攻击力 */
    public Integer Attack() { return m_nAttack; }
    public Integer getM_nAttack() {return m_nAttack; }
    private Integer m_nAttackType ;
    /** 攻击类型 */
    public Integer AttackType() { return m_nAttackType; }
    public Integer getM_nAttackType() {return m_nAttackType; }
    private Float3 m_SightAngle ;
    /** 准星角度 */
    public Float3 SightAngle() { return m_SightAngle; }
    public Float3 getM_SightAngle() {return m_SightAngle; }
    private Float2 m_SightScale ;
    /** 准星缩放 */
    public Float2 SightScale() { return m_SightScale; }
    public Float2 getM_SightScale() {return m_SightScale; }
    private Float2 m_SightSkew ;
    /** 准星偏移 */
    public Float2 SightSkew() { return m_SightSkew; }
    public Float2 getM_SightSkew() {return m_SightSkew; }
    private String m_sSightEffect ;
    /** 瞄准特效 */
    public String SightEffect() { return m_sSightEffect; }
    public String getM_sSightEffect() {return m_sSightEffect; }
    private Integer m_nPower ;
    /** 战斗力 */
    public Integer Power() { return m_nPower; }
    public Integer getM_nPower() {return m_nPower; }
    public static MT_Data_AirSupport ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_AirSupport Data = new MT_Data_AirSupport();
		Data.m_nAirID = reader.getInt();
		Data.m_sName = TableUtil.ReadLString(reader, fileName,"Name",Data.ID());
		Data.m_sDescribe = TableUtil.ReadLString(reader, fileName,"Describe",Data.ID());
		Data.m_Price = Int2.ReadMemory(reader, fileName);
		Data.m_UpgradeNeed = Int2.ReadMemory(reader, fileName);
		Data.m_nLevel = reader.getInt();
		Data.m_nMaxDurable = reader.getInt();
		Data.m_RepairPrice = Int2.ReadMemory(reader, fileName);
		Data.m_nType = reader.getInt();
		Data.m_Range = Int2.ReadMemory(reader, fileName);
		Data.m_sIcon = TableUtil.ReadString(reader);
		Data.m_sImage = TableUtil.ReadString(reader);
		Data.m_sModelFile = TableUtil.ReadString(reader);
		Data.m_ModelScale = Float2.ReadMemory(reader, fileName);
		Data.m_sShadowFile = TableUtil.ReadString(reader);
		Data.m_ShadowScale = Float2.ReadMemory(reader, fileName);
		Data.m_fCoolDown = reader.getFloat();
		Data.m_nSkill = reader.getInt();
		Data.m_fSpeed = reader.getFloat();
		Data.m_fHeight = reader.getFloat();
		Data.m_fPreDistance = reader.getFloat();
		Data.m_nAttack = reader.getInt();
		Data.m_nAttackType = reader.getInt();
		Data.m_SightAngle = Float3.ReadMemory(reader, fileName);
		Data.m_SightScale = Float2.ReadMemory(reader, fileName);
		Data.m_SightSkew = Float2.ReadMemory(reader, fileName);
		Data.m_sSightEffect = TableUtil.ReadString(reader);
		Data.m_nPower = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nAirID)) return false;
        if (!TableUtil.IsInvalid(this.m_sName)) return false;
        if (!TableUtil.IsInvalid(this.m_sDescribe)) return false;
        if (!TableUtil.IsInvalid(this.m_Price)) return false;
        if (!TableUtil.IsInvalid(this.m_UpgradeNeed)) return false;
        if (!TableUtil.IsInvalid(this.m_nLevel)) return false;
        if (!TableUtil.IsInvalid(this.m_nMaxDurable)) return false;
        if (!TableUtil.IsInvalid(this.m_RepairPrice)) return false;
        if (!TableUtil.IsInvalid(this.m_nType)) return false;
        if (!TableUtil.IsInvalid(this.m_Range)) return false;
        if (!TableUtil.IsInvalid(this.m_sIcon)) return false;
        if (!TableUtil.IsInvalid(this.m_sImage)) return false;
        if (!TableUtil.IsInvalid(this.m_sModelFile)) return false;
        if (!TableUtil.IsInvalid(this.m_ModelScale)) return false;
        if (!TableUtil.IsInvalid(this.m_sShadowFile)) return false;
        if (!TableUtil.IsInvalid(this.m_ShadowScale)) return false;
        if (!TableUtil.IsInvalid(this.m_fCoolDown)) return false;
        if (!TableUtil.IsInvalid(this.m_nSkill)) return false;
        if (!TableUtil.IsInvalid(this.m_fSpeed)) return false;
        if (!TableUtil.IsInvalid(this.m_fHeight)) return false;
        if (!TableUtil.IsInvalid(this.m_fPreDistance)) return false;
        if (!TableUtil.IsInvalid(this.m_nAttack)) return false;
        if (!TableUtil.IsInvalid(this.m_nAttackType)) return false;
        if (!TableUtil.IsInvalid(this.m_SightAngle)) return false;
        if (!TableUtil.IsInvalid(this.m_SightScale)) return false;
        if (!TableUtil.IsInvalid(this.m_SightSkew)) return false;
        if (!TableUtil.IsInvalid(this.m_sSightEffect)) return false;
        if (!TableUtil.IsInvalid(this.m_nPower)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("AirID"))
            return AirID();
        if (str.equals("Name"))
            return Name();
        if (str.equals("Describe"))
            return Describe();
        if (str.equals("Price"))
            return Price();
        if (str.equals("UpgradeNeed"))
            return UpgradeNeed();
        if (str.equals("Level"))
            return Level();
        if (str.equals("MaxDurable"))
            return MaxDurable();
        if (str.equals("RepairPrice"))
            return RepairPrice();
        if (str.equals("Type"))
            return Type();
        if (str.equals("Range"))
            return Range();
        if (str.equals("Icon"))
            return Icon();
        if (str.equals("Image"))
            return Image();
        if (str.equals("ModelFile"))
            return ModelFile();
        if (str.equals("ModelScale"))
            return ModelScale();
        if (str.equals("ShadowFile"))
            return ShadowFile();
        if (str.equals("ShadowScale"))
            return ShadowScale();
        if (str.equals("CoolDown"))
            return CoolDown();
        if (str.equals("Skill"))
            return Skill();
        if (str.equals("Speed"))
            return Speed();
        if (str.equals("Height"))
            return Height();
        if (str.equals("PreDistance"))
            return PreDistance();
        if (str.equals("Attack"))
            return Attack();
        if (str.equals("AttackType"))
            return AttackType();
        if (str.equals("SightAngle"))
            return SightAngle();
        if (str.equals("SightScale"))
            return SightScale();
        if (str.equals("SightSkew"))
            return SightSkew();
        if (str.equals("SightEffect"))
            return SightEffect();
        if (str.equals("Power"))
            return Power();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("AirID =" + AirID() + '\n');
        str += ("Name =" + Name() + '\n');
        str += ("Describe =" + Describe() + '\n');
        str += ("Price =" + Price() + '\n');
        str += ("UpgradeNeed =" + UpgradeNeed() + '\n');
        str += ("Level =" + Level() + '\n');
        str += ("MaxDurable =" + MaxDurable() + '\n');
        str += ("RepairPrice =" + RepairPrice() + '\n');
        str += ("Type =" + Type() + '\n');
        str += ("Range =" + Range() + '\n');
        str += ("Icon =" + Icon() + '\n');
        str += ("Image =" + Image() + '\n');
        str += ("ModelFile =" + ModelFile() + '\n');
        str += ("ModelScale =" + ModelScale() + '\n');
        str += ("ShadowFile =" + ShadowFile() + '\n');
        str += ("ShadowScale =" + ShadowScale() + '\n');
        str += ("CoolDown =" + CoolDown() + '\n');
        str += ("Skill =" + Skill() + '\n');
        str += ("Speed =" + Speed() + '\n');
        str += ("Height =" + Height() + '\n');
        str += ("PreDistance =" + PreDistance() + '\n');
        str += ("Attack =" + Attack() + '\n');
        str += ("AttackType =" + AttackType() + '\n');
        str += ("SightAngle =" + SightAngle() + '\n');
        str += ("SightScale =" + SightScale() + '\n');
        str += ("SightSkew =" + SightSkew() + '\n');
        str += ("SightEffect =" + SightEffect() + '\n');
        str += ("Power =" + Power() + '\n');
        return str;
    }
}