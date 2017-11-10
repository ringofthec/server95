package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Language extends MT_DataBase {
    public static String MD5 = "896033c34fe6f1fe29b1fb3d8f28c11c";
    private Integer m_nIndex ;
    /** 索引 */
    public Integer Index() { return m_nIndex; }
    public Integer getM_nIndex() {return m_nIndex; }
    /** 索引 */
    public Integer ID() { return m_nIndex; }
    private ArrayList<String> m_arrayKey = new ArrayList<String>();
    /** 关键字 */
    public ArrayList<String> Key() { return m_arrayKey; }
    public ArrayList<String> getM_arrayKey() {return m_arrayKey; }
    private String m_sText ;
    /** 文字 */
    public String Text() { return m_sText; }
    public String getM_sText() {return m_sText; }
    public static MT_Data_Language ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Language Data = new MT_Data_Language();

        Integer count;
		Data.m_nIndex = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayKey.add(TableUtil.ReadString(reader));
        }

		Data.m_sText = TableUtil.ReadFString(reader);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nIndex)) return false;
        if (this.m_arrayKey.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_sText)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("Index"))
            return Index();
        if (str.equals("Key"))
            return Key();
        if (str.equals("Text"))
            return Text();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("Index =" + Index() + '\n');
        str += ("Key =" + Key() + '\n');
        str += ("Text =" + Text() + '\n');
        return str;
    }
}