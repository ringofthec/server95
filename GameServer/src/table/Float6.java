package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class Float6 extends MT_DataBase {
    public static String MD5 = "47b85ee2737cc9bd3c04da6a061d5e40";
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
    private float m_ffield4 ;
    /**  */
    public float field4() { return m_ffield4; }
    public float getM_ffield4() {return m_ffield4; }
    private float m_ffield5 ;
    /**  */
    public float field5() { return m_ffield5; }
    public float getM_ffield5() {return m_ffield5; }
    private float m_ffield6 ;
    /**  */
    public float field6() { return m_ffield6; }
    public float getM_ffield6() {return m_ffield6; }
    public static Float6 ReadMemory ( ByteBuffer reader, String fileName ) {
        Float6 Data = new Float6();
		Data.m_ffield1 = reader.getFloat();
		Data.m_ffield2 = reader.getFloat();
		Data.m_ffield3 = reader.getFloat();
		Data.m_ffield4 = reader.getFloat();
		Data.m_ffield5 = reader.getFloat();
		Data.m_ffield6 = reader.getFloat();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_ffield1)) return false;
        if (!TableUtil.IsInvalid(this.m_ffield2)) return false;
        if (!TableUtil.IsInvalid(this.m_ffield3)) return false;
        if (!TableUtil.IsInvalid(this.m_ffield4)) return false;
        if (!TableUtil.IsInvalid(this.m_ffield5)) return false;
        if (!TableUtil.IsInvalid(this.m_ffield6)) return false;
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
        return str;
    }
}