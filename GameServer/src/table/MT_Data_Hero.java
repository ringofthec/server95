package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Hero extends MT_DataBase {
    public static String MD5 = "c978f5ee4dde3e10bd7dbe8a43b0cc9c";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private ArrayList<Integer> m_arrayneedInstanceId = new ArrayList<Integer>();
    /** 英雄开放关卡 */
    public ArrayList<Integer> needInstanceId() { return m_arrayneedInstanceId; }
    public ArrayList<Integer> getM_arrayneedInstanceId() {return m_arrayneedInstanceId; }
    private ArrayList<String> m_arrayBone = new ArrayList<String>();
    /** 英雄骨骼路径 */
    public ArrayList<String> Bone() { return m_arrayBone; }
    public ArrayList<String> getM_arrayBone() {return m_arrayBone; }
    private ArrayList<String> m_arrayHead = new ArrayList<String>();
    /** 头像 */
    public ArrayList<String> Head() { return m_arrayHead; }
    public ArrayList<String> getM_arrayHead() {return m_arrayHead; }
    private String m_sName ;
    /** 名字 */
    public String Name() { return m_sName; }
    public String getM_sName() {return m_sName; }
    private String m_sText ;
    /** 英雄介绍 */
    public String Text() { return m_sText; }
    public String getM_sText() {return m_sText; }
    private Integer m_nPopulation ;
    /** 占用人口 */
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
    private Integer m_nFightVal ;
    /** 战斗力 */
    public Integer FightVal() { return m_nFightVal; }
    public Integer getM_nFightVal() {return m_nFightVal; }
    private Int2 m_Pos ;
    /** pvp默认位置 */
    public Int2 Pos() { return m_Pos; }
    public Int2 getM_Pos() {return m_Pos; }
    private ArrayList<Int2> m_arrayFirstEquip = new ArrayList<Int2>();
    /** 初始默认装备 */
    public ArrayList<Int2> FirstEquip() { return m_arrayFirstEquip; }
    public ArrayList<Int2> getM_arrayFirstEquip() {return m_arrayFirstEquip; }
    private Integer m_nInitSkill ;
    /** 默认技能 */
    public Integer InitSkill() { return m_nInitSkill; }
    public Integer getM_nInitSkill() {return m_nInitSkill; }
    public static MT_Data_Hero ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Hero Data = new MT_Data_Hero();

        Integer count;
		Data.m_nindex = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayneedInstanceId.add(reader.getInt());
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayBone.add(TableUtil.ReadString(reader));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayHead.add(TableUtil.ReadString(reader));
        }

		Data.m_sName = TableUtil.ReadLString(reader, fileName,"Name",Data.ID());
		Data.m_sText = TableUtil.ReadLString(reader, fileName,"Text",Data.ID());
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
		Data.m_nFightVal = reader.getInt();
		Data.m_Pos = Int2.ReadMemory(reader, fileName);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayFirstEquip.add(Int2.ReadMemory(reader, fileName));
        }

		Data.m_nInitSkill = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (this.m_arrayneedInstanceId.size() > 0) return false;
        if (this.m_arrayBone.size() > 0) return false;
        if (this.m_arrayHead.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_sName)) return false;
        if (!TableUtil.IsInvalid(this.m_sText)) return false;
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
        if (!TableUtil.IsInvalid(this.m_nFightVal)) return false;
        if (!TableUtil.IsInvalid(this.m_Pos)) return false;
        if (this.m_arrayFirstEquip.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_nInitSkill)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("needInstanceId"))
            return needInstanceId();
        if (str.equals("Bone"))
            return Bone();
        if (str.equals("Head"))
            return Head();
        if (str.equals("Name"))
            return Name();
        if (str.equals("Text"))
            return Text();
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
        if (str.equals("FightVal"))
            return FightVal();
        if (str.equals("Pos"))
            return Pos();
        if (str.equals("FirstEquip"))
            return FirstEquip();
        if (str.equals("InitSkill"))
            return InitSkill();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("needInstanceId =" + needInstanceId() + '\n');
        str += ("Bone =" + Bone() + '\n');
        str += ("Head =" + Head() + '\n');
        str += ("Name =" + Name() + '\n');
        str += ("Text =" + Text() + '\n');
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
        str += ("FightVal =" + FightVal() + '\n');
        str += ("Pos =" + Pos() + '\n');
        str += ("FirstEquip =" + FirstEquip() + '\n');
        str += ("InitSkill =" + InitSkill() + '\n');
        return str;
    }
}