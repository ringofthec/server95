package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Recharge1 extends MT_DataBase {
    public static String MD5 = "0661a52bde03b16e7d51406d3e12c1c3";
    private String m_sindex ;
    /** 索引 */
    public String index() { return m_sindex; }
    public String getM_sindex() {return m_sindex; }
    /** 索引 */
    public String ID() { return m_sindex; }
    private String m_sImage ;
    /** 商城图标 */
    public String Image() { return m_sImage; }
    public String getM_sImage() {return m_sImage; }
    private Int2 m_buyTableId ;
    /** Item表id */
    public Int2 buyTableId() { return m_buyTableId; }
    public Int2 getM_buyTableId() {return m_buyTableId; }
    private float m_fPrice ;
    /** 出售价格 */
    public float Price() { return m_fPrice; }
    public float getM_fPrice() {return m_fPrice; }
    private String m_sName ;
    /** 商品名字 */
    public String Name() { return m_sName; }
    public String getM_sName() {return m_sName; }
    private String m_sAddition ;
    /** 加成 */
    public String Addition() { return m_sAddition; }
    public String getM_sAddition() {return m_sAddition; }
    public static MT_Data_Recharge1 ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Recharge1 Data = new MT_Data_Recharge1();
		Data.m_sindex = TableUtil.ReadString(reader);
		Data.m_sImage = TableUtil.ReadString(reader);
		Data.m_buyTableId = Int2.ReadMemory(reader, fileName);
		Data.m_fPrice = reader.getFloat();
		Data.m_sName = TableUtil.ReadLString(reader, fileName,"Name",Data.ID());
		Data.m_sAddition = TableUtil.ReadString(reader);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_sindex)) return false;
        if (!TableUtil.IsInvalid(this.m_sImage)) return false;
        if (!TableUtil.IsInvalid(this.m_buyTableId)) return false;
        if (!TableUtil.IsInvalid(this.m_fPrice)) return false;
        if (!TableUtil.IsInvalid(this.m_sName)) return false;
        if (!TableUtil.IsInvalid(this.m_sAddition)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("Image"))
            return Image();
        if (str.equals("buyTableId"))
            return buyTableId();
        if (str.equals("Price"))
            return Price();
        if (str.equals("Name"))
            return Name();
        if (str.equals("Addition"))
            return Addition();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("Image =" + Image() + '\n');
        str += ("buyTableId =" + buyTableId() + '\n');
        str += ("Price =" + Price() + '\n');
        str += ("Name =" + Name() + '\n');
        str += ("Addition =" + Addition() + '\n');
        return str;
    }
}