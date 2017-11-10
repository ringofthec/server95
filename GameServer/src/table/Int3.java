package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class Int3 extends MT_DataBase {
    public static String MD5 = "db15c4144ce1d4e6836901941fa9c235";
    private Integer m_nfield1 ;
    /**  */
    public Integer field1() { return m_nfield1; }
    public Integer getM_nfield1() {return m_nfield1; }
    private Integer m_nfield2 ;
    /**  */
    public Integer field2() { return m_nfield2; }
    public Integer getM_nfield2() {return m_nfield2; }
    private Integer m_nfield3 ;
    /**  */
    public Integer field3() { return m_nfield3; }
    public Integer getM_nfield3() {return m_nfield3; }
    public static Int3 ReadMemory ( ByteBuffer reader, String fileName ) {
        Int3 Data = new Int3();
		Data.m_nfield1 = reader.getInt();
		Data.m_nfield2 = reader.getInt();
		Data.m_nfield3 = reader.getInt();
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