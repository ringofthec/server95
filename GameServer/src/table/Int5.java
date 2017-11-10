package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class Int5 extends MT_DataBase {
    public static String MD5 = "26c33e7e63cac52304fd7bdf97b91119";
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
    private Integer m_nfield4 ;
    /**  */
    public Integer field4() { return m_nfield4; }
    public Integer getM_nfield4() {return m_nfield4; }
    private Integer m_nfield5 ;
    /**  */
    public Integer field5() { return m_nfield5; }
    public Integer getM_nfield5() {return m_nfield5; }
    public static Int5 ReadMemory ( ByteBuffer reader, String fileName ) {
        Int5 Data = new Int5();
		Data.m_nfield1 = reader.getInt();
		Data.m_nfield2 = reader.getInt();
		Data.m_nfield3 = reader.getInt();
		Data.m_nfield4 = reader.getInt();
		Data.m_nfield5 = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nfield1)) return false;
        if (!TableUtil.IsInvalid(this.m_nfield2)) return false;
        if (!TableUtil.IsInvalid(this.m_nfield3)) return false;
        if (!TableUtil.IsInvalid(this.m_nfield4)) return false;
        if (!TableUtil.IsInvalid(this.m_nfield5)) return false;
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
        if (str.equals("field4"))
            return field4();
        if (str.equals("field5"))
            return field5();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("field1 =" + field1() + '\n');
        str += ("field2 =" + field2() + '\n');
        str += ("field3 =" + field3() + '\n');
        str += ("field4 =" + field4() + '\n');
        str += ("field5 =" + field5() + '\n');
        return str;
    }
}