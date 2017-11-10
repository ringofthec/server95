package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GBJLBet extends MT_DataBase {
    public static String MD5 = "dbd5d76a51d8d524ac195e4bdb5c2272";
    private Integer m_nid ;
    /** id */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** id */
    public Integer ID() { return m_nid; }
    private Integer m_nmoney ;
    /** 下注金额 */
    public Integer money() { return m_nmoney; }
    public Integer getM_nmoney() {return m_nmoney; }
    private Integer m_nneedmoney_rate ;
    /** 身上必须携带的倍数 */
    public Integer needmoney_rate() { return m_nneedmoney_rate; }
    public Integer getM_nneedmoney_rate() {return m_nneedmoney_rate; }
    public static MT_Data_GBJLBet ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GBJLBet Data = new MT_Data_GBJLBet();
		Data.m_nid = reader.getInt();
		Data.m_nmoney = reader.getInt();
		Data.m_nneedmoney_rate = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_nmoney)) return false;
        if (!TableUtil.IsInvalid(this.m_nneedmoney_rate)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("money"))
            return money();
        if (str.equals("needmoney_rate"))
            return needmoney_rate();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("money =" + money() + '\n');
        str += ("needmoney_rate =" + needmoney_rate() + '\n');
        return str;
    }
}