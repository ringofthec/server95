package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_LegionStoreGroup extends MT_DataBase {
    public static String MD5 = "3ff66af259f82f0fa972d412aface2ba";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private String m_sType ;
    /** 所属组别 */
    public String Type() { return m_sType; }
    public String getM_sType() {return m_sType; }
    private Integer m_nprobability ;
    /** 出现概率 */
    public Integer probability() { return m_nprobability; }
    public Integer getM_nprobability() {return m_nprobability; }
    private Int2 m_Num ;
    /** 出现个数区间 */
    public Int2 Num() { return m_Num; }
    public Int2 getM_Num() {return m_Num; }
    public static MT_Data_LegionStoreGroup ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_LegionStoreGroup Data = new MT_Data_LegionStoreGroup();
		Data.m_nindex = reader.getInt();
		Data.m_sType = TableUtil.ReadString(reader);
		Data.m_nprobability = reader.getInt();
		Data.m_Num = Int2.ReadMemory(reader, fileName);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_sType)) return false;
        if (!TableUtil.IsInvalid(this.m_nprobability)) return false;
        if (!TableUtil.IsInvalid(this.m_Num)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("Type"))
            return Type();
        if (str.equals("probability"))
            return probability();
        if (str.equals("Num"))
            return Num();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("Type =" + Type() + '\n');
        str += ("probability =" + probability() + '\n');
        str += ("Num =" + Num() + '\n');
        return str;
    }
}