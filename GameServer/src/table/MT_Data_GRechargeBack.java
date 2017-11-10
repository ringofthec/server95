package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GRechargeBack extends MT_DataBase {
    public static String MD5 = "9d7049dc44f885eb192b203dcf10757f";
    private Integer m_nprice ;
    /** 价格(元) */
    public Integer price() { return m_nprice; }
    public Integer getM_nprice() {return m_nprice; }
    /** 价格(元) */
    public Integer ID() { return m_nprice; }
    private ArrayList<Int2> m_arraymailitem = new ArrayList<Int2>();
    /** 首充邮件奖励 */
    public ArrayList<Int2> mailitem() { return m_arraymailitem; }
    public ArrayList<Int2> getM_arraymailitem() {return m_arraymailitem; }
    private Int2 m_vip0 ;
    /** vip0 */
    public Int2 vip0() { return m_vip0; }
    public Int2 getM_vip0() {return m_vip0; }
    private Int2 m_vip1 ;
    /** vip1 */
    public Int2 vip1() { return m_vip1; }
    public Int2 getM_vip1() {return m_vip1; }
    private Int2 m_vip2 ;
    /** vip2 */
    public Int2 vip2() { return m_vip2; }
    public Int2 getM_vip2() {return m_vip2; }
    private Int2 m_vip3 ;
    /** vip3 */
    public Int2 vip3() { return m_vip3; }
    public Int2 getM_vip3() {return m_vip3; }
    private Int2 m_vip4 ;
    /** vip4 */
    public Int2 vip4() { return m_vip4; }
    public Int2 getM_vip4() {return m_vip4; }
    private Int2 m_vip5 ;
    /** vip5 */
    public Int2 vip5() { return m_vip5; }
    public Int2 getM_vip5() {return m_vip5; }
    private Int2 m_vip6 ;
    /** vip6 */
    public Int2 vip6() { return m_vip6; }
    public Int2 getM_vip6() {return m_vip6; }
    private Int2 m_vip7 ;
    /** vip7 */
    public Int2 vip7() { return m_vip7; }
    public Int2 getM_vip7() {return m_vip7; }
    private Int2 m_vip8 ;
    /** vip8 */
    public Int2 vip8() { return m_vip8; }
    public Int2 getM_vip8() {return m_vip8; }
    private Int2 m_vip9 ;
    /** vip9 */
    public Int2 vip9() { return m_vip9; }
    public Int2 getM_vip9() {return m_vip9; }
    private Int2 m_vip10 ;
    /** vip10 */
    public Int2 vip10() { return m_vip10; }
    public Int2 getM_vip10() {return m_vip10; }
    public static MT_Data_GRechargeBack ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GRechargeBack Data = new MT_Data_GRechargeBack();

        Integer count;
		Data.m_nprice = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arraymailitem.add(Int2.ReadMemory(reader, fileName));
        }

		Data.m_vip0 = Int2.ReadMemory(reader, fileName);
		Data.m_vip1 = Int2.ReadMemory(reader, fileName);
		Data.m_vip2 = Int2.ReadMemory(reader, fileName);
		Data.m_vip3 = Int2.ReadMemory(reader, fileName);
		Data.m_vip4 = Int2.ReadMemory(reader, fileName);
		Data.m_vip5 = Int2.ReadMemory(reader, fileName);
		Data.m_vip6 = Int2.ReadMemory(reader, fileName);
		Data.m_vip7 = Int2.ReadMemory(reader, fileName);
		Data.m_vip8 = Int2.ReadMemory(reader, fileName);
		Data.m_vip9 = Int2.ReadMemory(reader, fileName);
		Data.m_vip10 = Int2.ReadMemory(reader, fileName);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nprice)) return false;
        if (this.m_arraymailitem.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_vip0)) return false;
        if (!TableUtil.IsInvalid(this.m_vip1)) return false;
        if (!TableUtil.IsInvalid(this.m_vip2)) return false;
        if (!TableUtil.IsInvalid(this.m_vip3)) return false;
        if (!TableUtil.IsInvalid(this.m_vip4)) return false;
        if (!TableUtil.IsInvalid(this.m_vip5)) return false;
        if (!TableUtil.IsInvalid(this.m_vip6)) return false;
        if (!TableUtil.IsInvalid(this.m_vip7)) return false;
        if (!TableUtil.IsInvalid(this.m_vip8)) return false;
        if (!TableUtil.IsInvalid(this.m_vip9)) return false;
        if (!TableUtil.IsInvalid(this.m_vip10)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("price"))
            return price();
        if (str.equals("mailitem"))
            return mailitem();
        if (str.equals("vip0"))
            return vip0();
        if (str.equals("vip1"))
            return vip1();
        if (str.equals("vip2"))
            return vip2();
        if (str.equals("vip3"))
            return vip3();
        if (str.equals("vip4"))
            return vip4();
        if (str.equals("vip5"))
            return vip5();
        if (str.equals("vip6"))
            return vip6();
        if (str.equals("vip7"))
            return vip7();
        if (str.equals("vip8"))
            return vip8();
        if (str.equals("vip9"))
            return vip9();
        if (str.equals("vip10"))
            return vip10();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("price =" + price() + '\n');
        str += ("mailitem =" + mailitem() + '\n');
        str += ("vip0 =" + vip0() + '\n');
        str += ("vip1 =" + vip1() + '\n');
        str += ("vip2 =" + vip2() + '\n');
        str += ("vip3 =" + vip3() + '\n');
        str += ("vip4 =" + vip4() + '\n');
        str += ("vip5 =" + vip5() + '\n');
        str += ("vip6 =" + vip6() + '\n');
        str += ("vip7 =" + vip7() + '\n');
        str += ("vip8 =" + vip8() + '\n');
        str += ("vip9 =" + vip9() + '\n');
        str += ("vip10 =" + vip10() + '\n');
        return str;
    }
}