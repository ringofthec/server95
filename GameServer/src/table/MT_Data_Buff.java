package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Buff extends MT_DataBase {
    public static String MD5 = "607ebdc99b60c2b8880191af087dc1b0";
    private Integer m_nBuffID ;
    /** 状态ID */
    public Integer BuffID() { return m_nBuffID; }
    public Integer getM_nBuffID() {return m_nBuffID; }
    /** 状态ID */
    public Integer ID() { return m_nBuffID; }
    private String m_sAddEffect ;
    /** 添加状态时生成特效 */
    public String AddEffect() { return m_sAddEffect; }
    public String getM_sAddEffect() {return m_sAddEffect; }
    private Float3 m_AddEffectOffset ;
    /** 特效偏移 */
    public Float3 AddEffectOffset() { return m_AddEffectOffset; }
    public Float3 getM_AddEffectOffset() {return m_AddEffectOffset; }
    private Float3 m_AddEffectAngle ;
    /** 特效角度 */
    public Float3 AddEffectAngle() { return m_AddEffectAngle; }
    public Float3 getM_AddEffectAngle() {return m_AddEffectAngle; }
    private Float3 m_AddEffectScale ;
    /** 特效缩放 */
    public Float3 AddEffectScale() { return m_AddEffectScale; }
    public Float3 getM_AddEffectScale() {return m_AddEffectScale; }
    private float m_fAddEffectLength ;
    /** 特效持续时间(秒) */
    public float AddEffectLength() { return m_fAddEffectLength; }
    public float getM_fAddEffectLength() {return m_fAddEffectLength; }
    private String m_sTriggerEffect ;
    /** 持续BUF每次触发调用特效 */
    public String TriggerEffect() { return m_sTriggerEffect; }
    public String getM_sTriggerEffect() {return m_sTriggerEffect; }
    private Float3 m_TriggerEffectOffset ;
    /** 特效偏移 */
    public Float3 TriggerEffectOffset() { return m_TriggerEffectOffset; }
    public Float3 getM_TriggerEffectOffset() {return m_TriggerEffectOffset; }
    private Float3 m_TriggerEffectAngle ;
    /** 特效角度 */
    public Float3 TriggerEffectAngle() { return m_TriggerEffectAngle; }
    public Float3 getM_TriggerEffectAngle() {return m_TriggerEffectAngle; }
    private Float3 m_TriggerEffectScale ;
    /** 特效缩放 */
    public Float3 TriggerEffectScale() { return m_TriggerEffectScale; }
    public Float3 getM_TriggerEffectScale() {return m_TriggerEffectScale; }
    private float m_fTriggerEffectLength ;
    /** 特效持续时间 */
    public float TriggerEffectLength() { return m_fTriggerEffectLength; }
    public float getM_fTriggerEffectLength() {return m_fTriggerEffectLength; }
    private String m_sOverEffect ;
    /** 状态失效时生成特效 */
    public String OverEffect() { return m_sOverEffect; }
    public String getM_sOverEffect() {return m_sOverEffect; }
    private Float3 m_OverEffectOffset ;
    /** 特效偏移 */
    public Float3 OverEffectOffset() { return m_OverEffectOffset; }
    public Float3 getM_OverEffectOffset() {return m_OverEffectOffset; }
    private Float3 m_OverEffectAngle ;
    /** 特效角度 */
    public Float3 OverEffectAngle() { return m_OverEffectAngle; }
    public Float3 getM_OverEffectAngle() {return m_OverEffectAngle; }
    private Float3 m_OverEffectScale ;
    /** 特效缩放 */
    public Float3 OverEffectScale() { return m_OverEffectScale; }
    public Float3 getM_OverEffectScale() {return m_OverEffectScale; }
    private float m_fOverEffectLength ;
    /** 特效持续时间 */
    public float OverEffectLength() { return m_fOverEffectLength; }
    public float getM_fOverEffectLength() {return m_fOverEffectLength; }
    private String m_sLastEffect ;
    /** 持续状态特效（此状态存在特效就一直存在） */
    public String LastEffect() { return m_sLastEffect; }
    public String getM_sLastEffect() {return m_sLastEffect; }
    private Float3 m_LastEffectOffset ;
    /** 特效偏移 */
    public Float3 LastEffectOffset() { return m_LastEffectOffset; }
    public Float3 getM_LastEffectOffset() {return m_LastEffectOffset; }
    private Float3 m_LastEffectAngle ;
    /** 特效角度 */
    public Float3 LastEffectAngle() { return m_LastEffectAngle; }
    public Float3 getM_LastEffectAngle() {return m_LastEffectAngle; }
    private Float3 m_LastEffectScale ;
    /** 特效缩放 */
    public Float3 LastEffectScale() { return m_LastEffectScale; }
    public Float3 getM_LastEffectScale() {return m_LastEffectScale; }
    private Int2 m_Hp ;
    /** 血量 */
    public Int2 Hp() { return m_Hp; }
    public Int2 getM_Hp() {return m_Hp; }
    private float m_fSpeed ;
    /** 移动速度 */
    public float Speed() { return m_fSpeed; }
    public float getM_fSpeed() {return m_fSpeed; }
    private float m_fAtkSpeed ;
    /** 攻击速度 */
    public float AtkSpeed() { return m_fAtkSpeed; }
    public float getM_fAtkSpeed() {return m_fAtkSpeed; }
    private Integer m_nExpAdd ;
    /** 经验获得加成 */
    public Integer ExpAdd() { return m_nExpAdd; }
    public Integer getM_nExpAdd() {return m_nExpAdd; }
    private Integer m_nMoneyAdd ;
    /** 金钱获得加成 */
    public Integer MoneyAdd() { return m_nMoneyAdd; }
    public Integer getM_nMoneyAdd() {return m_nMoneyAdd; }
    private Int2 m_MaxHp ;
    /** 最大血量 */
    public Int2 MaxHp() { return m_MaxHp; }
    public Int2 getM_MaxHp() {return m_MaxHp; }
    private Int2 m_Attack ;
    /** 攻击力 */
    public Int2 Attack() { return m_Attack; }
    public Int2 getM_Attack() {return m_Attack; }
    private Int2 m_Defense ;
    /** 护甲 */
    public Int2 Defense() { return m_Defense; }
    public Int2 getM_Defense() {return m_Defense; }
    private Int2 m_Crit ;
    /** 暴击 */
    public Int2 Crit() { return m_Crit; }
    public Int2 getM_Crit() {return m_Crit; }
    private Int2 m_Dodge ;
    /** 闪避 */
    public Int2 Dodge() { return m_Dodge; }
    public Int2 getM_Dodge() {return m_Dodge; }
    private ArrayList<Integer> m_arrayProhibitActions = new ArrayList<Integer>();
    /** 禁止的行为 */
    public ArrayList<Integer> ProhibitActions() { return m_arrayProhibitActions; }
    public ArrayList<Integer> getM_arrayProhibitActions() {return m_arrayProhibitActions; }
    private ArrayList<Integer> m_arrayCannotCoexist = new ArrayList<Integer>();
    /** 不能并存的状态 */
    public ArrayList<Integer> CannotCoexist() { return m_arrayCannotCoexist; }
    public ArrayList<Integer> getM_arrayCannotCoexist() {return m_arrayCannotCoexist; }
    private Integer m_nPriority ;
    /** 状态优先级 */
    public Integer Priority() { return m_nPriority; }
    public Integer getM_nPriority() {return m_nPriority; }
    private float m_fSpace ;
    /** 调用间隔 */
    public float Space() { return m_fSpace; }
    public float getM_fSpace() {return m_fSpace; }
    private float m_fTimes ;
    /** 持续时间(秒) */
    public float Times() { return m_fTimes; }
    public float getM_fTimes() {return m_fTimes; }
    public static MT_Data_Buff ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Buff Data = new MT_Data_Buff();

        Integer count;
		Data.m_nBuffID = reader.getInt();
		Data.m_sAddEffect = TableUtil.ReadString(reader);
		Data.m_AddEffectOffset = Float3.ReadMemory(reader, fileName);
		Data.m_AddEffectAngle = Float3.ReadMemory(reader, fileName);
		Data.m_AddEffectScale = Float3.ReadMemory(reader, fileName);
		Data.m_fAddEffectLength = reader.getFloat();
		Data.m_sTriggerEffect = TableUtil.ReadString(reader);
		Data.m_TriggerEffectOffset = Float3.ReadMemory(reader, fileName);
		Data.m_TriggerEffectAngle = Float3.ReadMemory(reader, fileName);
		Data.m_TriggerEffectScale = Float3.ReadMemory(reader, fileName);
		Data.m_fTriggerEffectLength = reader.getFloat();
		Data.m_sOverEffect = TableUtil.ReadString(reader);
		Data.m_OverEffectOffset = Float3.ReadMemory(reader, fileName);
		Data.m_OverEffectAngle = Float3.ReadMemory(reader, fileName);
		Data.m_OverEffectScale = Float3.ReadMemory(reader, fileName);
		Data.m_fOverEffectLength = reader.getFloat();
		Data.m_sLastEffect = TableUtil.ReadString(reader);
		Data.m_LastEffectOffset = Float3.ReadMemory(reader, fileName);
		Data.m_LastEffectAngle = Float3.ReadMemory(reader, fileName);
		Data.m_LastEffectScale = Float3.ReadMemory(reader, fileName);
		Data.m_Hp = Int2.ReadMemory(reader, fileName);
		Data.m_fSpeed = reader.getFloat();
		Data.m_fAtkSpeed = reader.getFloat();
		Data.m_nExpAdd = reader.getInt();
		Data.m_nMoneyAdd = reader.getInt();
		Data.m_MaxHp = Int2.ReadMemory(reader, fileName);
		Data.m_Attack = Int2.ReadMemory(reader, fileName);
		Data.m_Defense = Int2.ReadMemory(reader, fileName);
		Data.m_Crit = Int2.ReadMemory(reader, fileName);
		Data.m_Dodge = Int2.ReadMemory(reader, fileName);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayProhibitActions.add(reader.getInt());
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayCannotCoexist.add(reader.getInt());
        }

		Data.m_nPriority = reader.getInt();
		Data.m_fSpace = reader.getFloat();
		Data.m_fTimes = reader.getFloat();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nBuffID)) return false;
        if (!TableUtil.IsInvalid(this.m_sAddEffect)) return false;
        if (!TableUtil.IsInvalid(this.m_AddEffectOffset)) return false;
        if (!TableUtil.IsInvalid(this.m_AddEffectAngle)) return false;
        if (!TableUtil.IsInvalid(this.m_AddEffectScale)) return false;
        if (!TableUtil.IsInvalid(this.m_fAddEffectLength)) return false;
        if (!TableUtil.IsInvalid(this.m_sTriggerEffect)) return false;
        if (!TableUtil.IsInvalid(this.m_TriggerEffectOffset)) return false;
        if (!TableUtil.IsInvalid(this.m_TriggerEffectAngle)) return false;
        if (!TableUtil.IsInvalid(this.m_TriggerEffectScale)) return false;
        if (!TableUtil.IsInvalid(this.m_fTriggerEffectLength)) return false;
        if (!TableUtil.IsInvalid(this.m_sOverEffect)) return false;
        if (!TableUtil.IsInvalid(this.m_OverEffectOffset)) return false;
        if (!TableUtil.IsInvalid(this.m_OverEffectAngle)) return false;
        if (!TableUtil.IsInvalid(this.m_OverEffectScale)) return false;
        if (!TableUtil.IsInvalid(this.m_fOverEffectLength)) return false;
        if (!TableUtil.IsInvalid(this.m_sLastEffect)) return false;
        if (!TableUtil.IsInvalid(this.m_LastEffectOffset)) return false;
        if (!TableUtil.IsInvalid(this.m_LastEffectAngle)) return false;
        if (!TableUtil.IsInvalid(this.m_LastEffectScale)) return false;
        if (!TableUtil.IsInvalid(this.m_Hp)) return false;
        if (!TableUtil.IsInvalid(this.m_fSpeed)) return false;
        if (!TableUtil.IsInvalid(this.m_fAtkSpeed)) return false;
        if (!TableUtil.IsInvalid(this.m_nExpAdd)) return false;
        if (!TableUtil.IsInvalid(this.m_nMoneyAdd)) return false;
        if (!TableUtil.IsInvalid(this.m_MaxHp)) return false;
        if (!TableUtil.IsInvalid(this.m_Attack)) return false;
        if (!TableUtil.IsInvalid(this.m_Defense)) return false;
        if (!TableUtil.IsInvalid(this.m_Crit)) return false;
        if (!TableUtil.IsInvalid(this.m_Dodge)) return false;
        if (this.m_arrayProhibitActions.size() > 0) return false;
        if (this.m_arrayCannotCoexist.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_nPriority)) return false;
        if (!TableUtil.IsInvalid(this.m_fSpace)) return false;
        if (!TableUtil.IsInvalid(this.m_fTimes)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("BuffID"))
            return BuffID();
        if (str.equals("AddEffect"))
            return AddEffect();
        if (str.equals("AddEffectOffset"))
            return AddEffectOffset();
        if (str.equals("AddEffectAngle"))
            return AddEffectAngle();
        if (str.equals("AddEffectScale"))
            return AddEffectScale();
        if (str.equals("AddEffectLength"))
            return AddEffectLength();
        if (str.equals("TriggerEffect"))
            return TriggerEffect();
        if (str.equals("TriggerEffectOffset"))
            return TriggerEffectOffset();
        if (str.equals("TriggerEffectAngle"))
            return TriggerEffectAngle();
        if (str.equals("TriggerEffectScale"))
            return TriggerEffectScale();
        if (str.equals("TriggerEffectLength"))
            return TriggerEffectLength();
        if (str.equals("OverEffect"))
            return OverEffect();
        if (str.equals("OverEffectOffset"))
            return OverEffectOffset();
        if (str.equals("OverEffectAngle"))
            return OverEffectAngle();
        if (str.equals("OverEffectScale"))
            return OverEffectScale();
        if (str.equals("OverEffectLength"))
            return OverEffectLength();
        if (str.equals("LastEffect"))
            return LastEffect();
        if (str.equals("LastEffectOffset"))
            return LastEffectOffset();
        if (str.equals("LastEffectAngle"))
            return LastEffectAngle();
        if (str.equals("LastEffectScale"))
            return LastEffectScale();
        if (str.equals("Hp"))
            return Hp();
        if (str.equals("Speed"))
            return Speed();
        if (str.equals("AtkSpeed"))
            return AtkSpeed();
        if (str.equals("ExpAdd"))
            return ExpAdd();
        if (str.equals("MoneyAdd"))
            return MoneyAdd();
        if (str.equals("MaxHp"))
            return MaxHp();
        if (str.equals("Attack"))
            return Attack();
        if (str.equals("Defense"))
            return Defense();
        if (str.equals("Crit"))
            return Crit();
        if (str.equals("Dodge"))
            return Dodge();
        if (str.equals("ProhibitActions"))
            return ProhibitActions();
        if (str.equals("CannotCoexist"))
            return CannotCoexist();
        if (str.equals("Priority"))
            return Priority();
        if (str.equals("Space"))
            return Space();
        if (str.equals("Times"))
            return Times();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("BuffID =" + BuffID() + '\n');
        str += ("AddEffect =" + AddEffect() + '\n');
        str += ("AddEffectOffset =" + AddEffectOffset() + '\n');
        str += ("AddEffectAngle =" + AddEffectAngle() + '\n');
        str += ("AddEffectScale =" + AddEffectScale() + '\n');
        str += ("AddEffectLength =" + AddEffectLength() + '\n');
        str += ("TriggerEffect =" + TriggerEffect() + '\n');
        str += ("TriggerEffectOffset =" + TriggerEffectOffset() + '\n');
        str += ("TriggerEffectAngle =" + TriggerEffectAngle() + '\n');
        str += ("TriggerEffectScale =" + TriggerEffectScale() + '\n');
        str += ("TriggerEffectLength =" + TriggerEffectLength() + '\n');
        str += ("OverEffect =" + OverEffect() + '\n');
        str += ("OverEffectOffset =" + OverEffectOffset() + '\n');
        str += ("OverEffectAngle =" + OverEffectAngle() + '\n');
        str += ("OverEffectScale =" + OverEffectScale() + '\n');
        str += ("OverEffectLength =" + OverEffectLength() + '\n');
        str += ("LastEffect =" + LastEffect() + '\n');
        str += ("LastEffectOffset =" + LastEffectOffset() + '\n');
        str += ("LastEffectAngle =" + LastEffectAngle() + '\n');
        str += ("LastEffectScale =" + LastEffectScale() + '\n');
        str += ("Hp =" + Hp() + '\n');
        str += ("Speed =" + Speed() + '\n');
        str += ("AtkSpeed =" + AtkSpeed() + '\n');
        str += ("ExpAdd =" + ExpAdd() + '\n');
        str += ("MoneyAdd =" + MoneyAdd() + '\n');
        str += ("MaxHp =" + MaxHp() + '\n');
        str += ("Attack =" + Attack() + '\n');
        str += ("Defense =" + Defense() + '\n');
        str += ("Crit =" + Crit() + '\n');
        str += ("Dodge =" + Dodge() + '\n');
        str += ("ProhibitActions =" + ProhibitActions() + '\n');
        str += ("CannotCoexist =" + CannotCoexist() + '\n');
        str += ("Priority =" + Priority() + '\n');
        str += ("Space =" + Space() + '\n');
        str += ("Times =" + Times() + '\n');
        return str;
    }
}