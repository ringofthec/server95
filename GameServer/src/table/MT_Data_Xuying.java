package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Xuying extends MT_DataBase {
    public static String MD5 = "1501530288428ba5ac750a467d60cf29";
    private Integer m_nIndex ;
    /** 索引 */
    public Integer Index() { return m_nIndex; }
    /** 索引 */
    public Integer ID() { return m_nIndex; }
    private Int2 m_Position ;
    /** 位置 */
    public Int2 Position() { return m_Position; }
    private Integer m_nCorpsID ;
    /** 士兵ID */
    public Integer CorpsID() { return m_nCorpsID; }
    public static MT_Data_Xuying ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Xuying Data = new MT_Data_Xuying();
		Data.m_nIndex = reader.getInt();
		Data.m_Position = Int2.ReadMemory(reader, fileName);
		Data.m_nCorpsID = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nIndex)) return false;
        if (!TableUtil.IsInvalid(this.m_Position)) return false;
        if (!TableUtil.IsInvalid(this.m_nCorpsID)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("Index"))
            return Index();
        if (str.equals("Position"))
            return Position();
        if (str.equals("CorpsID"))
            return CorpsID();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("Index =" + Index() + '\n');
        str += ("Position =" + Position() + '\n');
        str += ("CorpsID =" + CorpsID() + '\n');
        return str;
    }
}