package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GSlotsRandom extends MT_DataBase {
    public static String MD5 = "a4ef81bfa946c78f2cbc40533bfe1a0a";
    private Integer m_nid ;
    /** 水果随机策略id */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** 水果随机策略id */
    public Integer ID() { return m_nid; }
    private Integer m_nmax_fall ;
    /** 默认最大连输次数 */
    public Integer max_fall() { return m_nmax_fall; }
    public Integer getM_nmax_fall() {return m_nmax_fall; }
    private Integer m_nitem1 ;
    /** WILD */
    public Integer item1() { return m_nitem1; }
    public Integer getM_nitem1() {return m_nitem1; }
    private Integer m_nitem2 ;
    /** 樱桃 */
    public Integer item2() { return m_nitem2; }
    public Integer getM_nitem2() {return m_nitem2; }
    private Integer m_nitem3 ;
    /** 芒果 */
    public Integer item3() { return m_nitem3; }
    public Integer getM_nitem3() {return m_nitem3; }
    private Integer m_nitem4 ;
    /** 葡萄 */
    public Integer item4() { return m_nitem4; }
    public Integer getM_nitem4() {return m_nitem4; }
    private Integer m_nitem5 ;
    /** 西瓜 */
    public Integer item5() { return m_nitem5; }
    public Integer getM_nitem5() {return m_nitem5; }
    private Integer m_nitem6 ;
    /** 橘子 */
    public Integer item6() { return m_nitem6; }
    public Integer getM_nitem6() {return m_nitem6; }
    private Integer m_nitem7 ;
    /** 哈密瓜 */
    public Integer item7() { return m_nitem7; }
    public Integer getM_nitem7() {return m_nitem7; }
    private Integer m_nitem8 ;
    /** 香蕉 */
    public Integer item8() { return m_nitem8; }
    public Integer getM_nitem8() {return m_nitem8; }
    private Integer m_nitem9 ;
    /** 草莓 */
    public Integer item9() { return m_nitem9; }
    public Integer getM_nitem9() {return m_nitem9; }
    private Integer m_nitem10 ;
    /** 大水果 */
    public Integer item10() { return m_nitem10; }
    public Integer getM_nitem10() {return m_nitem10; }
    private Integer m_nitem11 ;
    /** BOUNS */
    public Integer item11() { return m_nitem11; }
    public Integer getM_nitem11() {return m_nitem11; }
    private Integer m_nitem12 ;
    /** JACKPOT */
    public Integer item12() { return m_nitem12; }
    public Integer getM_nitem12() {return m_nitem12; }
    public static MT_Data_GSlotsRandom ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GSlotsRandom Data = new MT_Data_GSlotsRandom();
		Data.m_nid = reader.getInt();
		Data.m_nmax_fall = reader.getInt();
		Data.m_nitem1 = reader.getInt();
		Data.m_nitem2 = reader.getInt();
		Data.m_nitem3 = reader.getInt();
		Data.m_nitem4 = reader.getInt();
		Data.m_nitem5 = reader.getInt();
		Data.m_nitem6 = reader.getInt();
		Data.m_nitem7 = reader.getInt();
		Data.m_nitem8 = reader.getInt();
		Data.m_nitem9 = reader.getInt();
		Data.m_nitem10 = reader.getInt();
		Data.m_nitem11 = reader.getInt();
		Data.m_nitem12 = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_nmax_fall)) return false;
        if (!TableUtil.IsInvalid(this.m_nitem1)) return false;
        if (!TableUtil.IsInvalid(this.m_nitem2)) return false;
        if (!TableUtil.IsInvalid(this.m_nitem3)) return false;
        if (!TableUtil.IsInvalid(this.m_nitem4)) return false;
        if (!TableUtil.IsInvalid(this.m_nitem5)) return false;
        if (!TableUtil.IsInvalid(this.m_nitem6)) return false;
        if (!TableUtil.IsInvalid(this.m_nitem7)) return false;
        if (!TableUtil.IsInvalid(this.m_nitem8)) return false;
        if (!TableUtil.IsInvalid(this.m_nitem9)) return false;
        if (!TableUtil.IsInvalid(this.m_nitem10)) return false;
        if (!TableUtil.IsInvalid(this.m_nitem11)) return false;
        if (!TableUtil.IsInvalid(this.m_nitem12)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("max_fall"))
            return max_fall();
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
        if (str.equals("item6"))
            return item6();
        if (str.equals("item7"))
            return item7();
        if (str.equals("item8"))
            return item8();
        if (str.equals("item9"))
            return item9();
        if (str.equals("item10"))
            return item10();
        if (str.equals("item11"))
            return item11();
        if (str.equals("item12"))
            return item12();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("max_fall =" + max_fall() + '\n');
        str += ("item1 =" + item1() + '\n');
        str += ("item2 =" + item2() + '\n');
        str += ("item3 =" + item3() + '\n');
        str += ("item4 =" + item4() + '\n');
        str += ("item5 =" + item5() + '\n');
        str += ("item6 =" + item6() + '\n');
        str += ("item7 =" + item7() + '\n');
        str += ("item8 =" + item8() + '\n');
        str += ("item9 =" + item9() + '\n');
        str += ("item10 =" + item10() + '\n');
        str += ("item11 =" + item11() + '\n');
        str += ("item12 =" + item12() + '\n');
        return str;
    }
}