package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_PassiveBuff extends MT_DataBase {
    public static String MD5 = "e2ebd68846817d84fc563ea3ebbcfea5";
    private Integer m_nbuffId ;
    /** 状态ID */
    public Integer buffId() { return m_nbuffId; }
    public Integer getM_nbuffId() {return m_nbuffId; }
    /** 状态ID */
    public Integer ID() { return m_nbuffId; }
    private Integer m_ntype ;
    /** 目标类型 */
    public Integer type() { return m_ntype; }
    public Integer getM_ntype() {return m_ntype; }
    private Integer m_ntargetCorps ;
    /** 目标参数 */
    public Integer targetCorps() { return m_ntargetCorps; }
    public Integer getM_ntargetCorps() {return m_ntargetCorps; }
    private Int3 m_buffs ;
    /** 效果 */
    public Int3 buffs() { return m_buffs; }
    public Int3 getM_buffs() {return m_buffs; }
    private String m_sText ;
    /** Buff描述(主要用于装备显示) */
    public String Text() { return m_sText; }
    public String getM_sText() {return m_sText; }
    public static MT_Data_PassiveBuff ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_PassiveBuff Data = new MT_Data_PassiveBuff();
		Data.m_nbuffId = reader.getInt();
		Data.m_ntype = reader.getInt();
		Data.m_ntargetCorps = reader.getInt();
		Data.m_buffs = Int3.ReadMemory(reader, fileName);
		Data.m_sText = TableUtil.ReadLString(reader, fileName,"Text",Data.ID());
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nbuffId)) return false;
        if (!TableUtil.IsInvalid(this.m_ntype)) return false;
        if (!TableUtil.IsInvalid(this.m_ntargetCorps)) return false;
        if (!TableUtil.IsInvalid(this.m_buffs)) return false;
        if (!TableUtil.IsInvalid(this.m_sText)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("buffId"))
            return buffId();
        if (str.equals("type"))
            return type();
        if (str.equals("targetCorps"))
            return targetCorps();
        if (str.equals("buffs"))
            return buffs();
        if (str.equals("Text"))
            return Text();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("buffId =" + buffId() + '\n');
        str += ("type =" + type() + '\n');
        str += ("targetCorps =" + targetCorps() + '\n');
        str += ("buffs =" + buffs() + '\n');
        str += ("Text =" + Text() + '\n');
        return str;
    }
}