package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_IEffect extends MT_DataBase {
    public static String MD5 = "cf446a48b6445270da54b08018c05fb8";
    private Integer m_nEffectID ;
    /** 效果ID */
    public Integer EffectID() { return m_nEffectID; }
    public Integer getM_nEffectID() {return m_nEffectID; }
    /** 效果ID */
    public Integer ID() { return m_nEffectID; }
    private String m_sResource ;
    /** 资源 */
    public String Resource() { return m_sResource; }
    public String getM_sResource() {return m_sResource; }
    private String m_sFireSound ;
    /** 发出声音 */
    public String FireSound() { return m_sFireSound; }
    public String getM_sFireSound() {return m_sFireSound; }
    private String m_sFireBone ;
    /** 特效发出点 */
    public String FireBone() { return m_sFireBone; }
    public String getM_sFireBone() {return m_sFireBone; }
    private Float3 m_Offset ;
    /** 偏移 */
    public Float3 Offset() { return m_Offset; }
    public Float3 getM_Offset() {return m_Offset; }
    private Float3 m_Angle ;
    /** 角度 */
    public Float3 Angle() { return m_Angle; }
    public Float3 getM_Angle() {return m_Angle; }
    private Float3 m_Scale ;
    /** 缩放 */
    public Float3 Scale() { return m_Scale; }
    public Float3 getM_Scale() {return m_Scale; }
    private float m_fTimes ;
    /** 特效循环时间 */
    public float Times() { return m_fTimes; }
    public float getM_fTimes() {return m_fTimes; }
    private float m_fSpeed ;
    /** 速度 */
    public float Speed() { return m_fSpeed; }
    public float getM_fSpeed() {return m_fSpeed; }
    private float m_fPoint ;
    /** 飞行路径经过的点(抛物线最高点) */
    public float Point() { return m_fPoint; }
    public float getM_fPoint() {return m_fPoint; }
    private String m_sEffectBone ;
    /** 特效生成绑点 */
    public String EffectBone() { return m_sEffectBone; }
    public String getM_sEffectBone() {return m_sEffectBone; }
    private String m_sHitEffect ;
    /** 命中后目标点产生特效 */
    public String HitEffect() { return m_sHitEffect; }
    public String getM_sHitEffect() {return m_sHitEffect; }
    private String m_sHitSound ;
    /** 音效 */
    public String HitSound() { return m_sHitSound; }
    public String getM_sHitSound() {return m_sHitSound; }
    private String m_sVibratingScreen ;
    /** 振屏 */
    public String VibratingScreen() { return m_sVibratingScreen; }
    public String getM_sVibratingScreen() {return m_sVibratingScreen; }
    private ArrayList<String> m_arrayanimation = new ArrayList<String>();
    /** 动作列表 */
    public ArrayList<String> animation() { return m_arrayanimation; }
    public ArrayList<String> getM_arrayanimation() {return m_arrayanimation; }
    private Float3 m_EffectOffset ;
    /** 特效偏移 */
    public Float3 EffectOffset() { return m_EffectOffset; }
    public Float3 getM_EffectOffset() {return m_EffectOffset; }
    private Float3 m_EffectScale ;
    /** 特效缩放 */
    public Float3 EffectScale() { return m_EffectScale; }
    public Float3 getM_EffectScale() {return m_EffectScale; }
    private ArrayList<Int2> m_arrayBeginAttackerBuff = new ArrayList<Int2>();
    /** 技能释放后释放者增加BUFF */
    public ArrayList<Int2> BeginAttackerBuff() { return m_arrayBeginAttackerBuff; }
    public ArrayList<Int2> getM_arrayBeginAttackerBuff() {return m_arrayBeginAttackerBuff; }
    private ArrayList<Int2> m_arrayBeginTargetBuff = new ArrayList<Int2>();
    /** 技能释放后目标增加BUFF */
    public ArrayList<Int2> BeginTargetBuff() { return m_arrayBeginTargetBuff; }
    public ArrayList<Int2> getM_arrayBeginTargetBuff() {return m_arrayBeginTargetBuff; }
    private ArrayList<Int2> m_arrayHitTargetBuff = new ArrayList<Int2>();
    /** 命中后目标增加BUFF */
    public ArrayList<Int2> HitTargetBuff() { return m_arrayHitTargetBuff; }
    public ArrayList<Int2> getM_arrayHitTargetBuff() {return m_arrayHitTargetBuff; }
    private ArrayList<Int2> m_arrayCritBuff = new ArrayList<Int2>();
    /** 暴击后攻击者增加BUFF */
    public ArrayList<Int2> CritBuff() { return m_arrayCritBuff; }
    public ArrayList<Int2> getM_arrayCritBuff() {return m_arrayCritBuff; }
    private ArrayList<Int2> m_arrayCritTargetBuff = new ArrayList<Int2>();
    /** 暴击后目标增加BUFF */
    public ArrayList<Int2> CritTargetBuff() { return m_arrayCritTargetBuff; }
    public ArrayList<Int2> getM_arrayCritTargetBuff() {return m_arrayCritTargetBuff; }
    public static MT_Data_IEffect ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_IEffect Data = new MT_Data_IEffect();

        Integer count;
		Data.m_nEffectID = reader.getInt();
		Data.m_sResource = TableUtil.ReadString(reader);
		Data.m_sFireSound = TableUtil.ReadString(reader);
		Data.m_sFireBone = TableUtil.ReadString(reader);
		Data.m_Offset = Float3.ReadMemory(reader, fileName);
		Data.m_Angle = Float3.ReadMemory(reader, fileName);
		Data.m_Scale = Float3.ReadMemory(reader, fileName);
		Data.m_fTimes = reader.getFloat();
		Data.m_fSpeed = reader.getFloat();
		Data.m_fPoint = reader.getFloat();
		Data.m_sEffectBone = TableUtil.ReadString(reader);
		Data.m_sHitEffect = TableUtil.ReadString(reader);
		Data.m_sHitSound = TableUtil.ReadString(reader);
		Data.m_sVibratingScreen = TableUtil.ReadString(reader);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayanimation.add(TableUtil.ReadString(reader));
        }

		Data.m_EffectOffset = Float3.ReadMemory(reader, fileName);
		Data.m_EffectScale = Float3.ReadMemory(reader, fileName);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayBeginAttackerBuff.add(Int2.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayBeginTargetBuff.add(Int2.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayHitTargetBuff.add(Int2.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayCritBuff.add(Int2.ReadMemory(reader, fileName));
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayCritTargetBuff.add(Int2.ReadMemory(reader, fileName));
        }

		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nEffectID)) return false;
        if (!TableUtil.IsInvalid(this.m_sResource)) return false;
        if (!TableUtil.IsInvalid(this.m_sFireSound)) return false;
        if (!TableUtil.IsInvalid(this.m_sFireBone)) return false;
        if (!TableUtil.IsInvalid(this.m_Offset)) return false;
        if (!TableUtil.IsInvalid(this.m_Angle)) return false;
        if (!TableUtil.IsInvalid(this.m_Scale)) return false;
        if (!TableUtil.IsInvalid(this.m_fTimes)) return false;
        if (!TableUtil.IsInvalid(this.m_fSpeed)) return false;
        if (!TableUtil.IsInvalid(this.m_fPoint)) return false;
        if (!TableUtil.IsInvalid(this.m_sEffectBone)) return false;
        if (!TableUtil.IsInvalid(this.m_sHitEffect)) return false;
        if (!TableUtil.IsInvalid(this.m_sHitSound)) return false;
        if (!TableUtil.IsInvalid(this.m_sVibratingScreen)) return false;
        if (this.m_arrayanimation.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_EffectOffset)) return false;
        if (!TableUtil.IsInvalid(this.m_EffectScale)) return false;
        if (this.m_arrayBeginAttackerBuff.size() > 0) return false;
        if (this.m_arrayBeginTargetBuff.size() > 0) return false;
        if (this.m_arrayHitTargetBuff.size() > 0) return false;
        if (this.m_arrayCritBuff.size() > 0) return false;
        if (this.m_arrayCritTargetBuff.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("EffectID"))
            return EffectID();
        if (str.equals("Resource"))
            return Resource();
        if (str.equals("FireSound"))
            return FireSound();
        if (str.equals("FireBone"))
            return FireBone();
        if (str.equals("Offset"))
            return Offset();
        if (str.equals("Angle"))
            return Angle();
        if (str.equals("Scale"))
            return Scale();
        if (str.equals("Times"))
            return Times();
        if (str.equals("Speed"))
            return Speed();
        if (str.equals("Point"))
            return Point();
        if (str.equals("EffectBone"))
            return EffectBone();
        if (str.equals("HitEffect"))
            return HitEffect();
        if (str.equals("HitSound"))
            return HitSound();
        if (str.equals("VibratingScreen"))
            return VibratingScreen();
        if (str.equals("animation"))
            return animation();
        if (str.equals("EffectOffset"))
            return EffectOffset();
        if (str.equals("EffectScale"))
            return EffectScale();
        if (str.equals("BeginAttackerBuff"))
            return BeginAttackerBuff();
        if (str.equals("BeginTargetBuff"))
            return BeginTargetBuff();
        if (str.equals("HitTargetBuff"))
            return HitTargetBuff();
        if (str.equals("CritBuff"))
            return CritBuff();
        if (str.equals("CritTargetBuff"))
            return CritTargetBuff();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("EffectID =" + EffectID() + '\n');
        str += ("Resource =" + Resource() + '\n');
        str += ("FireSound =" + FireSound() + '\n');
        str += ("FireBone =" + FireBone() + '\n');
        str += ("Offset =" + Offset() + '\n');
        str += ("Angle =" + Angle() + '\n');
        str += ("Scale =" + Scale() + '\n');
        str += ("Times =" + Times() + '\n');
        str += ("Speed =" + Speed() + '\n');
        str += ("Point =" + Point() + '\n');
        str += ("EffectBone =" + EffectBone() + '\n');
        str += ("HitEffect =" + HitEffect() + '\n');
        str += ("HitSound =" + HitSound() + '\n');
        str += ("VibratingScreen =" + VibratingScreen() + '\n');
        str += ("animation =" + animation() + '\n');
        str += ("EffectOffset =" + EffectOffset() + '\n');
        str += ("EffectScale =" + EffectScale() + '\n');
        str += ("BeginAttackerBuff =" + BeginAttackerBuff() + '\n');
        str += ("BeginTargetBuff =" + BeginTargetBuff() + '\n');
        str += ("HitTargetBuff =" + HitTargetBuff() + '\n');
        str += ("CritBuff =" + CritBuff() + '\n');
        str += ("CritTargetBuff =" + CritTargetBuff() + '\n');
        return str;
    }
}