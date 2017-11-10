package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_legion extends MT_DataBase {
    public static String MD5 = "456d2a2cddcd88dcee81d1d96f7ca894";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private Integer m_nLevel ;
    /** 军团等级 */
    public Integer Level() { return m_nLevel; }
    public Integer getM_nLevel() {return m_nLevel; }
    private String m_sSpecialEffect ;
    /** 军团增益效果 */
    public String SpecialEffect() { return m_sSpecialEffect; }
    public String getM_sSpecialEffect() {return m_sSpecialEffect; }
    private Integer m_nUpgradeContribution ;
    /** 升级需要资金 */
    public Integer UpgradeContribution() { return m_nUpgradeContribution; }
    public Integer getM_nUpgradeContribution() {return m_nUpgradeContribution; }
    private Integer m_nPeople ;
    /** 人口上限 */
    public Integer People() { return m_nPeople; }
    public Integer getM_nPeople() {return m_nPeople; }
    private ArrayList<Integer> m_arrayPassiveBuff = new ArrayList<Integer>();
    /** 被动技能 */
    public ArrayList<Integer> PassiveBuff() { return m_arrayPassiveBuff; }
    public ArrayList<Integer> getM_arrayPassiveBuff() {return m_arrayPassiveBuff; }
    public static MT_Data_legion ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_legion Data = new MT_Data_legion();

        Integer count;
		Data.m_nindex = reader.getInt();
		Data.m_nLevel = reader.getInt();
		Data.m_sSpecialEffect = TableUtil.ReadLString(reader, fileName,"SpecialEffect",Data.ID());
		Data.m_nUpgradeContribution = reader.getInt();
		Data.m_nPeople = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayPassiveBuff.add(reader.getInt());
        }

		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_nLevel)) return false;
        if (!TableUtil.IsInvalid(this.m_sSpecialEffect)) return false;
        if (!TableUtil.IsInvalid(this.m_nUpgradeContribution)) return false;
        if (!TableUtil.IsInvalid(this.m_nPeople)) return false;
        if (this.m_arrayPassiveBuff.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("Level"))
            return Level();
        if (str.equals("SpecialEffect"))
            return SpecialEffect();
        if (str.equals("UpgradeContribution"))
            return UpgradeContribution();
        if (str.equals("People"))
            return People();
        if (str.equals("PassiveBuff"))
            return PassiveBuff();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("Level =" + Level() + '\n');
        str += ("SpecialEffect =" + SpecialEffect() + '\n');
        str += ("UpgradeContribution =" + UpgradeContribution() + '\n');
        str += ("People =" + People() + '\n');
        str += ("PassiveBuff =" + PassiveBuff() + '\n');
        return str;
    }
}