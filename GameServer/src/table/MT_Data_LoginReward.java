package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_LoginReward extends MT_DataBase {
    public static String MD5 = "2359ef51f33a9c2822651d9df7da66f0";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private Integer m_nnum ;
    /** 连续登陆天数 */
    public Integer num() { return m_nnum; }
    public Integer getM_nnum() {return m_nnum; }
    private Integer m_ngold ;
    /** 金砖数 */
    public Integer gold() { return m_ngold; }
    public Integer getM_ngold() {return m_ngold; }
    private Integer m_nitemId ;
    /** 物品ID */
    public Integer itemId() { return m_nitemId; }
    public Integer getM_nitemId() {return m_nitemId; }
    private Integer m_nviplevel ;
    /** vip翻倍等级 */
    public Integer viplevel() { return m_nviplevel; }
    public Integer getM_nviplevel() {return m_nviplevel; }
    public static MT_Data_LoginReward ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_LoginReward Data = new MT_Data_LoginReward();
		Data.m_nindex = reader.getInt();
		Data.m_nnum = reader.getInt();
		Data.m_ngold = reader.getInt();
		Data.m_nitemId = reader.getInt();
		Data.m_nviplevel = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_nnum)) return false;
        if (!TableUtil.IsInvalid(this.m_ngold)) return false;
        if (!TableUtil.IsInvalid(this.m_nitemId)) return false;
        if (!TableUtil.IsInvalid(this.m_nviplevel)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("num"))
            return num();
        if (str.equals("gold"))
            return gold();
        if (str.equals("itemId"))
            return itemId();
        if (str.equals("viplevel"))
            return viplevel();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("num =" + num() + '\n');
        str += ("gold =" + gold() + '\n');
        str += ("itemId =" + itemId() + '\n');
        str += ("viplevel =" + viplevel() + '\n');
        return str;
    }
}