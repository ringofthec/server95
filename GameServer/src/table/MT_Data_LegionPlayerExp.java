package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_LegionPlayerExp extends MT_DataBase {
    public static String MD5 = "dbd5d76a51d8d524ac195e4bdb5c2272";
    private Integer m_nlevel ;
    /** 索引等级 */
    public Integer level() { return m_nlevel; }
    public Integer getM_nlevel() {return m_nlevel; }
    /** 索引等级 */
    public Integer ID() { return m_nlevel; }
    private Integer m_nexp ;
    /** 奖励经验 */
    public Integer exp() { return m_nexp; }
    public Integer getM_nexp() {return m_nexp; }
    private Integer m_nmoney ;
    /** 金币数量 */
    public Integer money() { return m_nmoney; }
    public Integer getM_nmoney() {return m_nmoney; }
    public static MT_Data_LegionPlayerExp ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_LegionPlayerExp Data = new MT_Data_LegionPlayerExp();
		Data.m_nlevel = reader.getInt();
		Data.m_nexp = reader.getInt();
		Data.m_nmoney = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nlevel)) return false;
        if (!TableUtil.IsInvalid(this.m_nexp)) return false;
        if (!TableUtil.IsInvalid(this.m_nmoney)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("level"))
            return level();
        if (str.equals("exp"))
            return exp();
        if (str.equals("money"))
            return money();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("level =" + level() + '\n');
        str += ("exp =" + exp() + '\n');
        str += ("money =" + money() + '\n');
        return str;
    }
}