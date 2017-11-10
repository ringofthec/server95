package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class Long3 extends MT_DataBase {
    public static String MD5 = "f20f2f93c3e563abf31250e3c21bfd59";
    private long m_nfield1 ;
    /**  */
    public long field1() { return m_nfield1; }
    public long getM_nfield1() {return m_nfield1; }
    private long m_nfield2 ;
    /**  */
    public long field2() { return m_nfield2; }
    public long getM_nfield2() {return m_nfield2; }
    private long m_nfield3 ;
    /**  */
    public long field3() { return m_nfield3; }
    public long getM_nfield3() {return m_nfield3; }
    public static Long3 ReadMemory ( ByteBuffer reader, String fileName ) {
        Long3 Data = new Long3();
		Data.m_nfield1 = reader.getLong();
		Data.m_nfield2 = reader.getLong();
		Data.m_nfield3 = reader.getLong();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nfield1)) return false;
        if (!TableUtil.IsInvalid(this.m_nfield2)) return false;
        if (!TableUtil.IsInvalid(this.m_nfield3)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("field1"))
            return field1();
        if (str.equals("field2"))
            return field2();
        if (str.equals("field3"))
            return field3();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("field1 =" + field1() + '\n');
        str += ("field2 =" + field2() + '\n');
        str += ("field3 =" + field3() + '\n');
        return str;
    }
}