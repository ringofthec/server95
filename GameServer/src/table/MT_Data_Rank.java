package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Rank extends MT_DataBase {
    public static String MD5 = "031f60b34a4e79d772dae7619c27f4d9";
    private Integer m_nRankId ;
    /** 索引 */
    public Integer RankId() { return m_nRankId; }
    public Integer getM_nRankId() {return m_nRankId; }
    /** 索引 */
    public Integer ID() { return m_nRankId; }
    private Integer m_nExp ;
    /** 类型 */
    public Integer Exp() { return m_nExp; }
    public Integer getM_nExp() {return m_nExp; }
    private String m_sName ;
    /** 名称 */
    public String Name() { return m_sName; }
    public String getM_sName() {return m_sName; }
    private String m_sIcon ;
    /** 图标 */
    public String Icon() { return m_sIcon; }
    public String getM_sIcon() {return m_sIcon; }
    private String m_smiaoshu ;
    /** 属性描述 */
    public String miaoshu() { return m_smiaoshu; }
    public String getM_smiaoshu() {return m_smiaoshu; }
    private ArrayList<Integer> m_arraybuffid = new ArrayList<Integer>();
    /** 被动技能 */
    public ArrayList<Integer> buffid() { return m_arraybuffid; }
    public ArrayList<Integer> getM_arraybuffid() {return m_arraybuffid; }
    private Integer m_nPower ;
    /** 战斗力 */
    public Integer Power() { return m_nPower; }
    public Integer getM_nPower() {return m_nPower; }
    public static MT_Data_Rank ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Rank Data = new MT_Data_Rank();

        Integer count;
		Data.m_nRankId = reader.getInt();
		Data.m_nExp = reader.getInt();
		Data.m_sName = TableUtil.ReadLString(reader, fileName,"Name",Data.ID());
		Data.m_sIcon = TableUtil.ReadString(reader);
		Data.m_smiaoshu = TableUtil.ReadLString(reader, fileName,"miaoshu",Data.ID());
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arraybuffid.add(reader.getInt());
        }

		Data.m_nPower = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nRankId)) return false;
        if (!TableUtil.IsInvalid(this.m_nExp)) return false;
        if (!TableUtil.IsInvalid(this.m_sName)) return false;
        if (!TableUtil.IsInvalid(this.m_sIcon)) return false;
        if (!TableUtil.IsInvalid(this.m_smiaoshu)) return false;
        if (this.m_arraybuffid.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_nPower)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("RankId"))
            return RankId();
        if (str.equals("Exp"))
            return Exp();
        if (str.equals("Name"))
            return Name();
        if (str.equals("Icon"))
            return Icon();
        if (str.equals("miaoshu"))
            return miaoshu();
        if (str.equals("buffid"))
            return buffid();
        if (str.equals("Power"))
            return Power();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("RankId =" + RankId() + '\n');
        str += ("Exp =" + Exp() + '\n');
        str += ("Name =" + Name() + '\n');
        str += ("Icon =" + Icon() + '\n');
        str += ("miaoshu =" + miaoshu() + '\n');
        str += ("buffid =" + buffid() + '\n');
        str += ("Power =" + Power() + '\n');
        return str;
    }
}