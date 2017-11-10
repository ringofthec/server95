package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class Float2 extends MT_DataBase {
    public static String MD5 = "e136c2b5f8027248a99a1c4ebf2fbb39";
    private float m_ffield1 ;
    /**  */
    public float field1() { return m_ffield1; }
    public float getM_ffield1() {return m_ffield1; }
    private float m_ffield2 ;
    /**  */
    public float field2() { return m_ffield2; }
    public float getM_ffield2() {return m_ffield2; }
    public static Float2 ReadMemory ( ByteBuffer reader, String fileName ) {
        Float2 Data = new Float2();
		Data.m_ffield1 = reader.getFloat();
		Data.m_ffield2 = reader.getFloat();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_ffield1)) return false;
        if (!TableUtil.IsInvalid(this.m_ffield2)) return false;
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