package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class Long2 extends MT_DataBase {
    public static String MD5 = "6b578ac2f72fa3d199ae0f30aafdaf39";
    private long m_nfield1 ;
    /**  */
    public long field1() { return m_nfield1; }
    public long getM_nfield1() {return m_nfield1; }
    private long m_nfield2 ;
    /**  */
    public long field2() { return m_nfield2; }
    public long getM_nfield2() {return m_nfield2; }
    public static Long2 ReadMemory ( ByteBuffer reader, String fileName ) {
        Long2 Data = new Long2();
		Data.m_nfield1 = reader.getLong();
		Data.m_nfield2 = reader.getLong();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nfield1)) return false;
        if (!TableUtil.IsInvalid(this.m_nfield2)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("field1"))
            return field1();
        if (str.equals("field2"))
            return field2();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("field1 =" + field1() + '\n');
        str += ("field2 =" + field2() + '\n');
        return str;
    }
}