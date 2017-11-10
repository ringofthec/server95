package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_WallLayout extends MT_DataBase {
    public static String MD5 = "710b2f2a3b7f23fe057763524512e10c";
    private Integer m_nIndex ;
    /** 索引 */
    public Integer Index() { return m_nIndex; }
    public Integer getM_nIndex() {return m_nIndex; }
    /** 索引 */
    public Integer ID() { return m_nIndex; }
    private String m_sWallOnly ;
    /** 城墙 */
    public String WallOnly() { return m_sWallOnly; }
    public String getM_sWallOnly() {return m_sWallOnly; }
    private Int2 m_Wall1 ;
    /** 城墙 */
    public Int2 Wall1() { return m_Wall1; }
    public Int2 getM_Wall1() {return m_Wall1; }
    private Int2 m_Wall2 ;
    /** 城墙 */
    public Int2 Wall2() { return m_Wall2; }
    public Int2 getM_Wall2() {return m_Wall2; }
    private Int2 m_Wall3 ;
    /** 城墙 */
    public Int2 Wall3() { return m_Wall3; }
    public Int2 getM_Wall3() {return m_Wall3; }
    private Int2 m_Wall4 ;
    /** 城墙 */
    public Int2 Wall4() { return m_Wall4; }
    public Int2 getM_Wall4() {return m_Wall4; }
    private Int2 m_Wall5 ;
    /** 城墙 */
    public Int2 Wall5() { return m_Wall5; }
    public Int2 getM_Wall5() {return m_Wall5; }
    private Int2 m_Wall6 ;
    /** 城墙 */
    public Int2 Wall6() { return m_Wall6; }
    public Int2 getM_Wall6() {return m_Wall6; }
    public static MT_Data_WallLayout ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_WallLayout Data = new MT_Data_WallLayout();
		Data.m_nIndex = reader.getInt();
		Data.m_sWallOnly = TableUtil.ReadString(reader);
		Data.m_Wall1 = Int2.ReadMemory(reader, fileName);
		Data.m_Wall2 = Int2.ReadMemory(reader, fileName);
		Data.m_Wall3 = Int2.ReadMemory(reader, fileName);
		Data.m_Wall4 = Int2.ReadMemory(reader, fileName);
		Data.m_Wall5 = Int2.ReadMemory(reader, fileName);
		Data.m_Wall6 = Int2.ReadMemory(reader, fileName);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nIndex)) return false;
        if (!TableUtil.IsInvalid(this.m_sWallOnly)) return false;
        if (!TableUtil.IsInvalid(this.m_Wall1)) return false;
        if (!TableUtil.IsInvalid(this.m_Wall2)) return false;
        if (!TableUtil.IsInvalid(this.m_Wall3)) return false;
        if (!TableUtil.IsInvalid(this.m_Wall4)) return false;
        if (!TableUtil.IsInvalid(this.m_Wall5)) return false;
        if (!TableUtil.IsInvalid(this.m_Wall6)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("Index"))
            return Index();
        if (str.equals("WallOnly"))
            return WallOnly();
        if (str.equals("Wall1"))
            return Wall1();
        if (str.equals("Wall2"))
            return Wall2();
        if (str.equals("Wall3"))
            return Wall3();
        if (str.equals("Wall4"))
            return Wall4();
        if (str.equals("Wall5"))
            return Wall5();
        if (str.equals("Wall6"))
            return Wall6();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("Index =" + Index() + '\n');
        str += ("WallOnly =" + WallOnly() + '\n');
        str += ("Wall1 =" + Wall1() + '\n');
        str += ("Wall2 =" + Wall2() + '\n');
        str += ("Wall3 =" + Wall3() + '\n');
        str += ("Wall4 =" + Wall4() + '\n');
        str += ("Wall5 =" + Wall5() + '\n');
        str += ("Wall6 =" + Wall6() + '\n');
        return str;
    }
}