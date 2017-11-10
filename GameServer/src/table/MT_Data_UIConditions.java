package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_UIConditions extends MT_DataBase {
    public static String MD5 = "53924cdd886d177c0a1e5101114d29fe";
    private Integer m_nIndex ;
    /** 索引 */
    public Integer Index() { return m_nIndex; }
    public Integer getM_nIndex() {return m_nIndex; }
    /** 索引 */
    public Integer ID() { return m_nIndex; }
    private String m_sUserInterface ;
    /** 所在UI */
    public String UserInterface() { return m_sUserInterface; }
    public String getM_sUserInterface() {return m_sUserInterface; }
    private String m_sPath ;
    /** Obj路径 */
    public String Path() { return m_sPath; }
    public String getM_sPath() {return m_sPath; }
    private Integer m_nPlayerLevel ;
    /** 玩家等级 */
    public Integer PlayerLevel() { return m_nPlayerLevel; }
    public Integer getM_nPlayerLevel() {return m_nPlayerLevel; }
    private Boolean m_bNeedLegion ;
    /** 是否需要加入军团 */
    public Boolean NeedLegion() { return m_bNeedLegion; }
    public Boolean getM_bNeedLegion() {return m_bNeedLegion; }
    public static MT_Data_UIConditions ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_UIConditions Data = new MT_Data_UIConditions();
		Data.m_nIndex = reader.getInt();
		Data.m_sUserInterface = TableUtil.ReadString(reader);
		Data.m_sPath = TableUtil.ReadString(reader);
		Data.m_nPlayerLevel = reader.getInt();
		Data.m_bNeedLegion = (reader.get() == 1 ? true : false);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nIndex)) return false;
        if (!TableUtil.IsInvalid(this.m_sUserInterface)) return false;
        if (!TableUtil.IsInvalid(this.m_sPath)) return false;
        if (!TableUtil.IsInvalid(this.m_nPlayerLevel)) return false;
        if (!TableUtil.IsInvalid(this.m_bNeedLegion)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("Index"))
            return Index();
        if (str.equals("UserInterface"))
            return UserInterface();
        if (str.equals("Path"))
            return Path();
        if (str.equals("PlayerLevel"))
            return PlayerLevel();
        if (str.equals("NeedLegion"))
            return NeedLegion();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("Index =" + Index() + '\n');
        str += ("UserInterface =" + UserInterface() + '\n');
        str += ("Path =" + Path() + '\n');
        str += ("PlayerLevel =" + PlayerLevel() + '\n');
        str += ("NeedLegion =" + NeedLegion() + '\n');
        return str;
    }
}