package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class Int7 extends MT_DataBase {
    public static String MD5 = "4f514c8431ab38ef1ac9d0315c59c7d7";
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
    private Integer m_nfield6 ;
    /**  */
    public Integer field6() { return m_nfield6; }
    public Integer getM_nfield6() {return m_nfield6; }
    private Integer m_nfield7 ;
    /**  */
    public Integer field7() { return m_nfield7; }
    public Integer getM_nfield7() {return m_nfield7; }
    public static Int7 ReadMemory ( ByteBuffer reader, String fileName ) {
        Int7 Data = new Int7();
		Data.m_nfield1 = reader.getInt();
		Data.m_nfield2 = reader.getInt();
		Data.m_nfield3 = reader.getInt();
		Data.m_nfield4 = reader.getInt();
		Data.m_nfield5 = reader.getInt();
		Data.m_nfield6 = reader.getInt();
		Data.m_nfield7 = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nfield1)) return false;
        if (!TableUtil.IsInvalid(this.m_nfield2)) return false;
        if (!TableUtil.IsInvalid(this.m_nfield3)) return false;
        if (!TableUtil.IsInvalid(this.m_nfield4)) return false;
        if (!TableUtil.IsInvalid(this.m_nfield5)) return false;
        if (!TableUtil.IsInvalid(this.m_nfield6)) return false;
        if (!TableUtil.IsInvalid(this.m_nfield7)) return false;
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
        if (str.equals("field6"))
            return field6();
        if (str.equals("field7"))
            return field7();
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
        str += ("field6 =" + field6() + '\n');
        str += ("field7 =" + field7() + '\n');
        return str;
    }
}