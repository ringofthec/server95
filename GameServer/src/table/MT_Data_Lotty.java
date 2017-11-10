package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Lotty extends MT_DataBase {
    public static String MD5 = "8060864403d52eeadbe4d33a8c40708a";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private Int3 m_coinitem ;
    /** 金币抽奖 */
    public Int3 coinitem() { return m_coinitem; }
    public Int3 getM_coinitem() {return m_coinitem; }
    private Int3 m_golditem ;
    /** 金砖抽奖 */
    public Int3 golditem() { return m_golditem; }
    public Int3 getM_golditem() {return m_golditem; }
    private Int3 m_coinitem1 ;
    /** 金币抽奖 */
    public Int3 coinitem1() { return m_coinitem1; }
    public Int3 getM_coinitem1() {return m_coinitem1; }
    public static MT_Data_Lotty ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Lotty Data = new MT_Data_Lotty();
		Data.m_nindex = reader.getInt();
		Data.m_coinitem = Int3.ReadMemory(reader, fileName);
		Data.m_golditem = Int3.ReadMemory(reader, fileName);
		Data.m_coinitem1 = Int3.ReadMemory(reader, fileName);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_coinitem)) return false;
        if (!TableUtil.IsInvalid(this.m_golditem)) return false;
        if (!TableUtil.IsInvalid(this.m_coinitem1)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("coinitem"))
            return coinitem();
        if (str.equals("golditem"))
            return golditem();
        if (str.equals("coinitem1"))
            return coinitem1();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("coinitem =" + coinitem() + '\n');
        str += ("golditem =" + golditem() + '\n');
        str += ("coinitem1 =" + coinitem1() + '\n');
        return str;
    }
}