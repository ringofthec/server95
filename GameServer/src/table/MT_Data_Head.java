package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Head extends MT_DataBase {
    public static String MD5 = "7897dcf15da18852cd76bd8757e10b47";
    private Integer m_nIndex ;
    /** 索引 */
    public Integer Index() { return m_nIndex; }
    public Integer getM_nIndex() {return m_nIndex; }
    /** 索引 */
    public Integer ID() { return m_nIndex; }
    private String m_sHead ;
    /** 头像 */
    public String Head() { return m_sHead; }
    public String getM_sHead() {return m_sHead; }
    public static MT_Data_Head ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Head Data = new MT_Data_Head();
		Data.m_nIndex = reader.getInt();
		Data.m_sHead = TableUtil.ReadString(reader);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nIndex)) return false;
        if (!TableUtil.IsInvalid(this.m_sHead)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("Index"))
            return Index();
        if (str.equals("Head"))
            return Head();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("Index =" + Index() + '\n');
        str += ("Head =" + Head() + '\n');
        return str;
    }
}