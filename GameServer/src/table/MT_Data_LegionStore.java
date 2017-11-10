package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_LegionStore extends MT_DataBase {
    public static String MD5 = "c7ff97db39e2c5dadcdc6fabb9505530";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private Integer m_ntypeRadom ;
    /** 组别概率 */
    public Integer typeRadom() { return m_ntypeRadom; }
    public Integer getM_ntypeRadom() {return m_ntypeRadom; }
    private String m_sType ;
    /** 所属组别 */
    public String Type() { return m_sType; }
    public String getM_sType() {return m_sType; }
    private Integer m_nprobability ;
    /** 出现概率 */
    public Integer probability() { return m_nprobability; }
    public Integer getM_nprobability() {return m_nprobability; }
    private Integer m_nbelong ;
    /** 所属英雄 */
    public Integer belong() { return m_nbelong; }
    public Integer getM_nbelong() {return m_nbelong; }
    private Integer m_nitemTableId ;
    /** item表的tableId */
    public Integer itemTableId() { return m_nitemTableId; }
    public Integer getM_nitemTableId() {return m_nitemTableId; }
    private Int2 m_needMoney ;
    /** 购买需要的金币 */
    public Int2 needMoney() { return m_needMoney; }
    public Int2 getM_needMoney() {return m_needMoney; }
    public static MT_Data_LegionStore ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_LegionStore Data = new MT_Data_LegionStore();
		Data.m_nindex = reader.getInt();
		Data.m_ntypeRadom = reader.getInt();
		Data.m_sType = TableUtil.ReadString(reader);
		Data.m_nprobability = reader.getInt();
		Data.m_nbelong = reader.getInt();
		Data.m_nitemTableId = reader.getInt();
		Data.m_needMoney = Int2.ReadMemory(reader, fileName);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_ntypeRadom)) return false;
        if (!TableUtil.IsInvalid(this.m_sType)) return false;
        if (!TableUtil.IsInvalid(this.m_nprobability)) return false;
        if (!TableUtil.IsInvalid(this.m_nbelong)) return false;
        if (!TableUtil.IsInvalid(this.m_nitemTableId)) return false;
        if (!TableUtil.IsInvalid(this.m_needMoney)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("typeRadom"))
            return typeRadom();
        if (str.equals("Type"))
            return Type();
        if (str.equals("probability"))
            return probability();
        if (str.equals("belong"))
            return belong();
        if (str.equals("itemTableId"))
            return itemTableId();
        if (str.equals("needMoney"))
            return needMoney();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("typeRadom =" + typeRadom() + '\n');
        str += ("Type =" + Type() + '\n');
        str += ("probability =" + probability() + '\n');
        str += ("belong =" + belong() + '\n');
        str += ("itemTableId =" + itemTableId() + '\n');
        str += ("needMoney =" + needMoney() + '\n');
        return str;
    }
}