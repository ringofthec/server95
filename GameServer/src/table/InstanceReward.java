package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class InstanceReward extends MT_DataBase {
    public static String MD5 = "ac78ff6387da666165d20fef3daecd1a";
    private Integer m_nfield1 ;
    /**  */
    public Integer field1() { return m_nfield1; }
    public Integer getM_nfield1() {return m_nfield1; }
    private Int2 m_field2 ;
    /**  */
    public Int2 field2() { return m_field2; }
    public Int2 getM_field2() {return m_field2; }
    private Int2 m_field3 ;
    /**  */
    public Int2 field3() { return m_field3; }
    public Int2 getM_field3() {return m_field3; }
    public static InstanceReward ReadMemory ( ByteBuffer reader, String fileName ) {
        InstanceReward Data = new InstanceReward();
		Data.m_nfield1 = reader.getInt();
		Data.m_field2 = Int2.ReadMemory(reader, fileName);
		Data.m_field3 = Int2.ReadMemory(reader, fileName);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nfield1)) return false;
        if (!TableUtil.IsInvalid(this.m_field2)) return false;
        if (!TableUtil.IsInvalid(this.m_field3)) return false;
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