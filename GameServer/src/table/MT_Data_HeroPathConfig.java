package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_HeroPathConfig extends MT_DataBase {
    public static String MD5 = "1fe13d50c6b17306d1186a178971e1f7";
    private Integer m_nindex ;
    /** 兵种等级 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 兵种等级 */
    public Integer ID() { return m_nindex; }
    private Integer m_nstarLv ;
    /** 需要的星级 */
    public Integer starLv() { return m_nstarLv; }
    public Integer getM_nstarLv() {return m_nstarLv; }
    private String m_scoment ;
    /** 注释 */
    public String coment() { return m_scoment; }
    public String getM_scoment() {return m_scoment; }
    public static MT_Data_HeroPathConfig ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_HeroPathConfig Data = new MT_Data_HeroPathConfig();
		Data.m_nindex = reader.getInt();
		Data.m_nstarLv = reader.getInt();
		Data.m_scoment = TableUtil.ReadString(reader);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_nstarLv)) return false;
        if (!TableUtil.IsInvalid(this.m_scoment)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("starLv"))
            return starLv();
        if (str.equals("coment"))
            return coment();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("starLv =" + starLv() + '\n');
        str += ("coment =" + coment() + '\n');
        return str;
    }
}