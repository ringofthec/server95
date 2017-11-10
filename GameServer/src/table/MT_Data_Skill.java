package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Skill extends MT_DataBase {
    public static String MD5 = "1d579119f88c36a878b029c1125ff9bf";
    private Integer m_nSkillID ;
    /** 技能ID */
    public Integer SkillID() { return m_nSkillID; }
    public Integer getM_nSkillID() {return m_nSkillID; }
    /** 技能ID */
    public Integer ID() { return m_nSkillID; }
    private String m_sAnimation ;
    /** 技能调用动作 */
    public String Animation() { return m_sAnimation; }
    public String getM_sAnimation() {return m_sAnimation; }
    private Int2 m_Range ;
    /** 技能目标范围 */
    public Int2 Range() { return m_Range; }
    public Int2 getM_Range() {return m_Range; }
    private byte m_uCastleValid ;
    /** 技能范围伤害是否对城堡有效 */
    public byte CastleValid() { return m_uCastleValid; }
    public byte getM_uCastleValid() {return m_uCastleValid; }
    private Integer m_nRangePercent ;
    /** 对范围伤害的攻击力百分比 */
    public Integer RangePercent() { return m_nRangePercent; }
    public Integer getM_nRangePercent() {return m_nRangePercent; }
    private ArrayList<Float3> m_arraySkillEvent = new ArrayList<Float3>();
    /** 技能效果 */
    public ArrayList<Float3> SkillEvent() { return m_arraySkillEvent; }
    public ArrayList<Float3> getM_arraySkillEvent() {return m_arraySkillEvent; }
    private float m_fCoolDown ;
    /** 技能CD */
    public float CoolDown() { return m_fCoolDown; }
    public float getM_fCoolDown() {return m_fCoolDown; }
    private String m_sName ;
    /** 技能名字 */
    public String Name() { return m_sName; }
    public String getM_sName() {return m_sName; }
    private String m_sImage ;
    /** 技能图标 */
    public String Image() { return m_sImage; }
    public String getM_sImage() {return m_sImage; }
    private String m_sText ;
    /** 技能描述(主要用于装备显示) */
    public String Text() { return m_sText; }
    public String getM_sText() {return m_sText; }
    public static MT_Data_Skill ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Skill Data = new MT_Data_Skill();

        Integer count;
		Data.m_nSkillID = reader.getInt();
		Data.m_sAnimation = TableUtil.ReadString(reader);
		Data.m_Range = Int2.ReadMemory(reader, fileName);
		Data.m_uCastleValid = reader.get();
		Data.m_nRangePercent = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arraySkillEvent.add(Float3.ReadMemory(reader, fileName));
        }

		Data.m_fCoolDown = reader.getFloat();
		Data.m_sName = TableUtil.ReadLString(reader, fileName,"Name",Data.ID());
		Data.m_sImage = TableUtil.ReadString(reader);
		Data.m_sText = TableUtil.ReadLString(reader, fileName,"Text",Data.ID());
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nSkillID)) return false;
        if (!TableUtil.IsInvalid(this.m_sAnimation)) return false;
        if (!TableUtil.IsInvalid(this.m_Range)) return false;
        if (!TableUtil.IsInvalid(this.m_uCastleValid)) return false;
        if (!TableUtil.IsInvalid(this.m_nRangePercent)) return false;
        if (this.m_arraySkillEvent.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_fCoolDown)) return false;
        if (!TableUtil.IsInvalid(this.m_sName)) return false;
        if (!TableUtil.IsInvalid(this.m_sImage)) return false;
        if (!TableUtil.IsInvalid(this.m_sText)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("SkillID"))
            return SkillID();
        if (str.equals("Animation"))
            return Animation();
        if (str.equals("Range"))
            return Range();
        if (str.equals("CastleValid"))
            return CastleValid();
        if (str.equals("RangePercent"))
            return RangePercent();
        if (str.equals("SkillEvent"))
            return SkillEvent();
        if (str.equals("CoolDown"))
            return CoolDown();
        if (str.equals("Name"))
            return Name();
        if (str.equals("Image"))
            return Image();
        if (str.equals("Text"))
            return Text();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("SkillID =" + SkillID() + '\n');
        str += ("Animation =" + Animation() + '\n');
        str += ("Range =" + Range() + '\n');
        str += ("CastleValid =" + CastleValid() + '\n');
        str += ("RangePercent =" + RangePercent() + '\n');
        str += ("SkillEvent =" + SkillEvent() + '\n');
        str += ("CoolDown =" + CoolDown() + '\n');
        str += ("Name =" + Name() + '\n');
        str += ("Image =" + Image() + '\n');
        str += ("Text =" + Text() + '\n');
        return str;
    }
}