package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_MedalStar extends MT_DataBase {
    public static String MD5 = "12179ece29de88ba62946d1258fb6485";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private String m_sname ;
    /** 名称 */
    public String name() { return m_sname; }
    public String getM_sname() {return m_sname; }
    private Int2 m_needNum ;
    /** 升星需要材料个数 */
    public Int2 needNum() { return m_needNum; }
    public Int2 getM_needNum() {return m_needNum; }
    private Integer m_nmaxLev ;
    /** 等级上限 */
    public Integer maxLev() { return m_nmaxLev; }
    public Integer getM_nmaxLev() {return m_nmaxLev; }
    private Integer m_nmaxStar ;
    /** 星级 */
    public Integer maxStar() { return m_nmaxStar; }
    public Integer getM_nmaxStar() {return m_nmaxStar; }
    private float m_fgrowing ;
    /** 成长 */
    public float growing() { return m_fgrowing; }
    public float getM_fgrowing() {return m_fgrowing; }
    private float m_ffightVal ;
    /** 战斗力系数 */
    public float fightVal() { return m_ffightVal; }
    public float getM_ffightVal() {return m_ffightVal; }
    public static MT_Data_MedalStar ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_MedalStar Data = new MT_Data_MedalStar();
		Data.m_nindex = reader.getInt();
		Data.m_sname = TableUtil.ReadLString(reader, fileName,"name",Data.ID());
		Data.m_needNum = Int2.ReadMemory(reader, fileName);
		Data.m_nmaxLev = reader.getInt();
		Data.m_nmaxStar = reader.getInt();
		Data.m_fgrowing = reader.getFloat();
		Data.m_ffightVal = reader.getFloat();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_sname)) return false;
        if (!TableUtil.IsInvalid(this.m_needNum)) return false;
        if (!TableUtil.IsInvalid(this.m_nmaxLev)) return false;
        if (!TableUtil.IsInvalid(this.m_nmaxStar)) return false;
        if (!TableUtil.IsInvalid(this.m_fgrowing)) return false;
        if (!TableUtil.IsInvalid(this.m_ffightVal)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("name"))
            return name();
        if (str.equals("needNum"))
            return needNum();
        if (str.equals("maxLev"))
            return maxLev();
        if (str.equals("maxStar"))
            return maxStar();
        if (str.equals("growing"))
            return growing();
        if (str.equals("fightVal"))
            return fightVal();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("name =" + name() + '\n');
        str += ("needNum =" + needNum() + '\n');
        str += ("maxLev =" + maxLev() + '\n');
        str += ("maxStar =" + maxStar() + '\n');
        str += ("growing =" + growing() + '\n');
        str += ("fightVal =" + fightVal() + '\n');
        return str;
    }
}