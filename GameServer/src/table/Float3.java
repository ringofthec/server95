package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class Float3 extends MT_DataBase {
    public static String MD5 = "63217251ef6804c6288b14a07849a779";
    private float m_ffield1 ;
    /**  */
    public float field1() { return m_ffield1; }
    public float getM_ffield1() {return m_ffield1; }
    private float m_ffield2 ;
    /**  */
    public float field2() { return m_ffield2; }
    public float getM_ffield2() {return m_ffield2; }
    private float m_ffield3 ;
    /**  */
    public float field3() { return m_ffield3; }
    public float getM_ffield3() {return m_ffield3; }
    public static Float3 ReadMemory ( ByteBuffer reader, String fileName ) {
        Float3 Data = new Float3();
		Data.m_ffield1 = reader.getFloat();
		Data.m_ffield2 = reader.getFloat();
		Data.m_ffield3 = reader.getFloat();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_ffield1)) return false;
        if (!TableUtil.IsInvalid(this.m_ffield2)) return false;
        if (!TableUtil.IsInvalid(this.m_ffield3)) return false;
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