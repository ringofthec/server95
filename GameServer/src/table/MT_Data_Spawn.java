package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Spawn extends MT_DataBase {
    public static String MD5 = "84ee4c19d8c31ba6ab60d33893a89bdc";
    private Integer m_nIndex ;
    /** 索引 */
    public Integer Index() { return m_nIndex; }
    public Integer getM_nIndex() {return m_nIndex; }
    /** 索引 */
    public Integer ID() { return m_nIndex; }
    private Int2 m_Position ;
    /** 位置 */
    public Int2 Position() { return m_Position; }
    public Int2 getM_Position() {return m_Position; }
    private Integer m_nCorpsID ;
    /** 士兵ID */
    public Integer CorpsID() { return m_nCorpsID; }
    public Integer getM_nCorpsID() {return m_nCorpsID; }
    private Integer m_nLevel ;
    /** 士兵等级 */
    public Integer Level() { return m_nLevel; }
    public Integer getM_nLevel() {return m_nLevel; }
    public static MT_Data_Spawn ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Spawn Data = new MT_Data_Spawn();
		Data.m_nIndex = reader.getInt();
		Data.m_Position = Int2.ReadMemory(reader, fileName);
		Data.m_nCorpsID = reader.getInt();
		Data.m_nLevel = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nIndex)) return false;
        if (!TableUtil.IsInvalid(this.m_Position)) return false;
        if (!TableUtil.IsInvalid(this.m_nCorpsID)) return false;
        if (!TableUtil.IsInvalid(this.m_nLevel)) return false;
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
        if (str.equals("Level"))
            return Level();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("Index =" + Index() + '\n');
        str += ("Position =" + Position() + '\n');
        str += ("CorpsID =" + CorpsID() + '\n');
        str += ("Level =" + Level() + '\n');
        return str;
    }
}