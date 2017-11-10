package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Exp extends MT_DataBase {
    public static String MD5 = "aac619f15f1cbf59013dbbef77edb67f";
    private Integer m_nlevel ;
    /** 索引 */
    public Integer level() { return m_nlevel; }
    public Integer getM_nlevel() {return m_nlevel; }
    /** 索引 */
    public Integer ID() { return m_nlevel; }
    private Integer m_nexp ;
    /** 经验 */
    public Integer exp() { return m_nexp; }
    public Integer getM_nexp() {return m_nexp; }
    private Integer m_nCP ;
    /** 体力上限 */
    public Integer CP() { return m_nCP; }
    public Integer getM_nCP() {return m_nCP; }
    private Integer m_ncp_reward ;
    /** 体力奖励 */
    public Integer cp_reward() { return m_ncp_reward; }
    public Integer getM_ncp_reward() {return m_ncp_reward; }
    public static MT_Data_Exp ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Exp Data = new MT_Data_Exp();
		Data.m_nlevel = reader.getInt();
		Data.m_nexp = reader.getInt();
		Data.m_nCP = reader.getInt();
		Data.m_ncp_reward = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nlevel)) return false;
        if (!TableUtil.IsInvalid(this.m_nexp)) return false;
        if (!TableUtil.IsInvalid(this.m_nCP)) return false;
        if (!TableUtil.IsInvalid(this.m_ncp_reward)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("level"))
            return level();
        if (str.equals("exp"))
            return exp();
        if (str.equals("CP"))
            return CP();
        if (str.equals("cp_reward"))
            return cp_reward();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("level =" + level() + '\n');
        str += ("exp =" + exp() + '\n');
        str += ("CP =" + CP() + '\n');
        str += ("cp_reward =" + cp_reward() + '\n');
        return str;
    }
}