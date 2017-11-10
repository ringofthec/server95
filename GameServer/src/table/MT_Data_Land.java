package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Land extends MT_DataBase {
    public static String MD5 = "e438831e06d3f8f61bfaa678ab4449d1";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private String m_sName ;
    /** 名字 */
    public String Name() { return m_sName; }
    public String getM_sName() {return m_sName; }
    private Integer m_nUpgradeNeedMoney ;
    /** 需要金钱 */
    public Integer UpgradeNeedMoney() { return m_nUpgradeNeedMoney; }
    public Integer getM_nUpgradeNeedMoney() {return m_nUpgradeNeedMoney; }
    private Integer m_nUpgradePlayerLv ;
    /** 升级需要玩家等级 */
    public Integer UpgradePlayerLv() { return m_nUpgradePlayerLv; }
    public Integer getM_nUpgradePlayerLv() {return m_nUpgradePlayerLv; }
    private Integer m_nUpgradeMainLv ;
    /** 升级需要主城等级 */
    public Integer UpgradeMainLv() { return m_nUpgradeMainLv; }
    public Integer getM_nUpgradeMainLv() {return m_nUpgradeMainLv; }
    public static MT_Data_Land ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Land Data = new MT_Data_Land();
		Data.m_nindex = reader.getInt();
		Data.m_sName = TableUtil.ReadString(reader);
		Data.m_nUpgradeNeedMoney = reader.getInt();
		Data.m_nUpgradePlayerLv = reader.getInt();
		Data.m_nUpgradeMainLv = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_sName)) return false;
        if (!TableUtil.IsInvalid(this.m_nUpgradeNeedMoney)) return false;
        if (!TableUtil.IsInvalid(this.m_nUpgradePlayerLv)) return false;
        if (!TableUtil.IsInvalid(this.m_nUpgradeMainLv)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("Name"))
            return Name();
        if (str.equals("UpgradeNeedMoney"))
            return UpgradeNeedMoney();
        if (str.equals("UpgradePlayerLv"))
            return UpgradePlayerLv();
        if (str.equals("UpgradeMainLv"))
            return UpgradeMainLv();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("Name =" + Name() + '\n');
        str += ("UpgradeNeedMoney =" + UpgradeNeedMoney() + '\n');
        str += ("UpgradePlayerLv =" + UpgradePlayerLv() + '\n');
        str += ("UpgradeMainLv =" + UpgradeMainLv() + '\n');
        return str;
    }
}