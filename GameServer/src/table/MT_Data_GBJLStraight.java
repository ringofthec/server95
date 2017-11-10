package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GBJLStraight extends MT_DataBase {
    public static String MD5 = "5e915da519a75958b8ef363afe61cc72";
    private Integer m_nid ;
    /** id */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** id */
    public Integer ID() { return m_nid; }
    private Integer m_nz0 ;
    /** 0 */
    public Integer z0() { return m_nz0; }
    public Integer getM_nz0() {return m_nz0; }
    private Integer m_nz1 ;
    /** 1 */
    public Integer z1() { return m_nz1; }
    public Integer getM_nz1() {return m_nz1; }
    private Integer m_nz2 ;
    /** 2 */
    public Integer z2() { return m_nz2; }
    public Integer getM_nz2() {return m_nz2; }
    private Integer m_nz3 ;
    /** 3 */
    public Integer z3() { return m_nz3; }
    public Integer getM_nz3() {return m_nz3; }
    private Integer m_nz4 ;
    /** 4 */
    public Integer z4() { return m_nz4; }
    public Integer getM_nz4() {return m_nz4; }
    private Integer m_nz5 ;
    /** 5 */
    public Integer z5() { return m_nz5; }
    public Integer getM_nz5() {return m_nz5; }
    private Integer m_nz6 ;
    /** 6 */
    public Integer z6() { return m_nz6; }
    public Integer getM_nz6() {return m_nz6; }
    private Integer m_nz7 ;
    /** 7 */
    public Integer z7() { return m_nz7; }
    public Integer getM_nz7() {return m_nz7; }
    private Integer m_nz8 ;
    /** 8 */
    public Integer z8() { return m_nz8; }
    public Integer getM_nz8() {return m_nz8; }
    private Integer m_nz9 ;
    /** 9 */
    public Integer z9() { return m_nz9; }
    public Integer getM_nz9() {return m_nz9; }
    public static MT_Data_GBJLStraight ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GBJLStraight Data = new MT_Data_GBJLStraight();
		Data.m_nid = reader.getInt();
		Data.m_nz0 = reader.getInt();
		Data.m_nz1 = reader.getInt();
		Data.m_nz2 = reader.getInt();
		Data.m_nz3 = reader.getInt();
		Data.m_nz4 = reader.getInt();
		Data.m_nz5 = reader.getInt();
		Data.m_nz6 = reader.getInt();
		Data.m_nz7 = reader.getInt();
		Data.m_nz8 = reader.getInt();
		Data.m_nz9 = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_nz0)) return false;
        if (!TableUtil.IsInvalid(this.m_nz1)) return false;
        if (!TableUtil.IsInvalid(this.m_nz2)) return false;
        if (!TableUtil.IsInvalid(this.m_nz3)) return false;
        if (!TableUtil.IsInvalid(this.m_nz4)) return false;
        if (!TableUtil.IsInvalid(this.m_nz5)) return false;
        if (!TableUtil.IsInvalid(this.m_nz6)) return false;
        if (!TableUtil.IsInvalid(this.m_nz7)) return false;
        if (!TableUtil.IsInvalid(this.m_nz8)) return false;
        if (!TableUtil.IsInvalid(this.m_nz9)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("z0"))
            return z0();
        if (str.equals("z1"))
            return z1();
        if (str.equals("z2"))
            return z2();
        if (str.equals("z3"))
            return z3();
        if (str.equals("z4"))
            return z4();
        if (str.equals("z5"))
            return z5();
        if (str.equals("z6"))
            return z6();
        if (str.equals("z7"))
            return z7();
        if (str.equals("z8"))
            return z8();
        if (str.equals("z9"))
            return z9();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("z0 =" + z0() + '\n');
        str += ("z1 =" + z1() + '\n');
        str += ("z2 =" + z2() + '\n');
        str += ("z3 =" + z3() + '\n');
        str += ("z4 =" + z4() + '\n');
        str += ("z5 =" + z5() + '\n');
        str += ("z6 =" + z6() + '\n');
        str += ("z7 =" + z7() + '\n');
        str += ("z8 =" + z8() + '\n');
        str += ("z9 =" + z9() + '\n');
        return str;
    }
}