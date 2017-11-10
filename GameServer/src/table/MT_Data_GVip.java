package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GVip extends MT_DataBase {
    public static String MD5 = "3710a884401ee968fa017983ec505a35";
    private Integer m_nlevel ;
    /** 等级 */
    public Integer level() { return m_nlevel; }
    public Integer getM_nlevel() {return m_nlevel; }
    /** 等级 */
    public Integer ID() { return m_nlevel; }
    private long m_nexp ;
    /** 升到这一级需要经验 */
    public long exp() { return m_nexp; }
    public long getM_nexp() {return m_nexp; }
    private long m_ngoldPig_Max ;
    /** 金猪储蓄上限 */
    public long goldPig_Max() { return m_ngoldPig_Max; }
    public long getM_ngoldPig_Max() {return m_ngoldPig_Max; }
    private Integer m_ngoldPig_genCoin ;
    /** 金猪天产出金币数量 */
    public Integer goldPig_genCoin() { return m_ngoldPig_genCoin; }
    public Integer getM_ngoldPig_genCoin() {return m_ngoldPig_genCoin; }
    private String m_sdesc ;
    /** 描述 */
    public String desc() { return m_sdesc; }
    public String getM_sdesc() {return m_sdesc; }
    public static MT_Data_GVip ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GVip Data = new MT_Data_GVip();
		Data.m_nlevel = reader.getInt();
		Data.m_nexp = reader.getLong();
		Data.m_ngoldPig_Max = reader.getLong();
		Data.m_ngoldPig_genCoin = reader.getInt();
		Data.m_sdesc = TableUtil.ReadLString(reader, fileName,"desc",Data.ID());
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nlevel)) return false;
        if (!TableUtil.IsInvalid(this.m_nexp)) return false;
        if (!TableUtil.IsInvalid(this.m_ngoldPig_Max)) return false;
        if (!TableUtil.IsInvalid(this.m_ngoldPig_genCoin)) return false;
        if (!TableUtil.IsInvalid(this.m_sdesc)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("level"))
            return level();
        if (str.equals("exp"))
            return exp();
        if (str.equals("goldPig_Max"))
            return goldPig_Max();
        if (str.equals("goldPig_genCoin"))
            return goldPig_genCoin();
        if (str.equals("desc"))
            return desc();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("level =" + level() + '\n');
        str += ("exp =" + exp() + '\n');
        str += ("goldPig_Max =" + goldPig_Max() + '\n');
        str += ("goldPig_genCoin =" + goldPig_genCoin() + '\n');
        str += ("desc =" + desc() + '\n');
        return str;
    }
}