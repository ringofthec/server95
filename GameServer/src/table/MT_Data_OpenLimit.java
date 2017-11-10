package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_OpenLimit extends MT_DataBase {
    public static String MD5 = "9cc8b8e93dee910b05ef715c31d332be";
    private Integer m_nIndex ;
    /** 索引 */
    public Integer Index() { return m_nIndex; }
    /** 索引 */
    public Integer ID() { return m_nIndex; }
    private Integer m_nPlayerLevel ;
    /** 玩家等级 */
    public Integer PlayerLevel() { return m_nPlayerLevel; }
    private Boolean m_bNeedLegion ;
    /** 是否需要加入军团 */
    public Boolean NeedLegion() { return m_bNeedLegion; }
    public static MT_Data_OpenLimit ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_OpenLimit Data = new MT_Data_OpenLimit();
		Data.m_nIndex = reader.getInt();
		Data.m_nPlayerLevel = reader.getInt();
		Data.m_bNeedLegion = (reader.get() == 1 ? true : false);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nIndex)) return false;
        if (!TableUtil.IsInvalid(this.m_nPlayerLevel)) return false;
        if (!TableUtil.IsInvalid(this.m_bNeedLegion)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("Index"))
            return Index();
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
        str += ("PlayerLevel =" + PlayerLevel() + '\n');
        str += ("NeedLegion =" + NeedLegion() + '\n');
        return str;
    }
}