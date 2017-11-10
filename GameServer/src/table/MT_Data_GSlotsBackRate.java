package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GSlotsBackRate extends MT_DataBase {
    public static String MD5 = "18c42a5a6eb911ded2cb16280e64c376";
    private Integer m_nid ;
    /** 唯一id */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** 唯一id */
    public Integer ID() { return m_nid; }
    private Integer m_nbackmoney ;
    /** 返奖金额(万) */
    public Integer backmoney() { return m_nbackmoney; }
    public Integer getM_nbackmoney() {return m_nbackmoney; }
    private Integer m_nmoney100 ;
    /** 押注100 */
    public Integer money100() { return m_nmoney100; }
    public Integer getM_nmoney100() {return m_nmoney100; }
    private Integer m_nmoney200 ;
    /** 押注200 */
    public Integer money200() { return m_nmoney200; }
    public Integer getM_nmoney200() {return m_nmoney200; }
    private Integer m_nmoney500 ;
    /** 押注500 */
    public Integer money500() { return m_nmoney500; }
    public Integer getM_nmoney500() {return m_nmoney500; }
    private Integer m_nmoney1000 ;
    /** 押注1000 */
    public Integer money1000() { return m_nmoney1000; }
    public Integer getM_nmoney1000() {return m_nmoney1000; }
    private Integer m_nmoney2000 ;
    /** 押注2000 */
    public Integer money2000() { return m_nmoney2000; }
    public Integer getM_nmoney2000() {return m_nmoney2000; }
    private Integer m_nmoney5000 ;
    /** 押注5000 */
    public Integer money5000() { return m_nmoney5000; }
    public Integer getM_nmoney5000() {return m_nmoney5000; }
    private Integer m_nmoney10000 ;
    /** 押注10000 */
    public Integer money10000() { return m_nmoney10000; }
    public Integer getM_nmoney10000() {return m_nmoney10000; }
    public static MT_Data_GSlotsBackRate ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GSlotsBackRate Data = new MT_Data_GSlotsBackRate();
		Data.m_nid = reader.getInt();
		Data.m_nbackmoney = reader.getInt();
		Data.m_nmoney100 = reader.getInt();
		Data.m_nmoney200 = reader.getInt();
		Data.m_nmoney500 = reader.getInt();
		Data.m_nmoney1000 = reader.getInt();
		Data.m_nmoney2000 = reader.getInt();
		Data.m_nmoney5000 = reader.getInt();
		Data.m_nmoney10000 = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_nbackmoney)) return false;
        if (!TableUtil.IsInvalid(this.m_nmoney100)) return false;
        if (!TableUtil.IsInvalid(this.m_nmoney200)) return false;
        if (!TableUtil.IsInvalid(this.m_nmoney500)) return false;
        if (!TableUtil.IsInvalid(this.m_nmoney1000)) return false;
        if (!TableUtil.IsInvalid(this.m_nmoney2000)) return false;
        if (!TableUtil.IsInvalid(this.m_nmoney5000)) return false;
        if (!TableUtil.IsInvalid(this.m_nmoney10000)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("backmoney"))
            return backmoney();
        if (str.equals("money100"))
            return money100();
        if (str.equals("money200"))
            return money200();
        if (str.equals("money500"))
            return money500();
        if (str.equals("money1000"))
            return money1000();
        if (str.equals("money2000"))
            return money2000();
        if (str.equals("money5000"))
            return money5000();
        if (str.equals("money10000"))
            return money10000();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("backmoney =" + backmoney() + '\n');
        str += ("money100 =" + money100() + '\n');
        str += ("money200 =" + money200() + '\n');
        str += ("money500 =" + money500() + '\n');
        str += ("money1000 =" + money1000() + '\n');
        str += ("money2000 =" + money2000() + '\n');
        str += ("money5000 =" + money5000() + '\n');
        str += ("money10000 =" + money10000() + '\n');
        return str;
    }
}