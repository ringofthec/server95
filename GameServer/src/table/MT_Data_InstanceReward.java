package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_InstanceReward extends MT_DataBase {
    public static String MD5 = "471d3cfa7795b7eae545d31e9207ef70";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private String m_sName ;
    /** 关卡名称 */
    public String Name() { return m_sName; }
    public String getM_sName() {return m_sName; }
    private String m_sBgImage ;
    /** 背景图片 */
    public String BgImage() { return m_sBgImage; }
    public String getM_sBgImage() {return m_sBgImage; }
    private InstanceReward m_item1 ;
    /** 奖励1 */
    public InstanceReward item1() { return m_item1; }
    public InstanceReward getM_item1() {return m_item1; }
    private InstanceReward m_item2 ;
    /** 奖励2 */
    public InstanceReward item2() { return m_item2; }
    public InstanceReward getM_item2() {return m_item2; }
    private InstanceReward m_item3 ;
    /** 奖励3 */
    public InstanceReward item3() { return m_item3; }
    public InstanceReward getM_item3() {return m_item3; }
    private InstanceReward m_item4 ;
    /** 奖励4 */
    public InstanceReward item4() { return m_item4; }
    public InstanceReward getM_item4() {return m_item4; }
    private InstanceReward m_item5 ;
    /** 奖励5 */
    public InstanceReward item5() { return m_item5; }
    public InstanceReward getM_item5() {return m_item5; }
    public static MT_Data_InstanceReward ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_InstanceReward Data = new MT_Data_InstanceReward();
		Data.m_nindex = reader.getInt();
		Data.m_sName = TableUtil.ReadLString(reader, fileName,"Name",Data.ID());
		Data.m_sBgImage = TableUtil.ReadString(reader);
		Data.m_item1 = InstanceReward.ReadMemory(reader, fileName);
		Data.m_item2 = InstanceReward.ReadMemory(reader, fileName);
		Data.m_item3 = InstanceReward.ReadMemory(reader, fileName);
		Data.m_item4 = InstanceReward.ReadMemory(reader, fileName);
		Data.m_item5 = InstanceReward.ReadMemory(reader, fileName);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_sName)) return false;
        if (!TableUtil.IsInvalid(this.m_sBgImage)) return false;
        if (!TableUtil.IsInvalid(this.m_item1)) return false;
        if (!TableUtil.IsInvalid(this.m_item2)) return false;
        if (!TableUtil.IsInvalid(this.m_item3)) return false;
        if (!TableUtil.IsInvalid(this.m_item4)) return false;
        if (!TableUtil.IsInvalid(this.m_item5)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("Name"))
            return Name();
        if (str.equals("BgImage"))
            return BgImage();
        if (str.equals("item1"))
            return item1();
        if (str.equals("item2"))
            return item2();
        if (str.equals("item3"))
            return item3();
        if (str.equals("item4"))
            return item4();
        if (str.equals("item5"))
            return item5();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("Name =" + Name() + '\n');
        str += ("BgImage =" + BgImage() + '\n');
        str += ("item1 =" + item1() + '\n');
        str += ("item2 =" + item2() + '\n');
        str += ("item3 =" + item3() + '\n');
        str += ("item4 =" + item4() + '\n');
        str += ("item5 =" + item5() + '\n');
        return str;
    }
}