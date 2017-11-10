package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_EquipAttribute extends MT_DataBase {
    public static String MD5 = "6d7bc0160e51a933f2329c923d413512";
    private Integer m_nlevel ;
    /** 索引 */
    public Integer ID() { return m_nlevel; }
    private EquipAttr m_Attr1 ;
    private EquipAttr m_Attr2 ;
    private EquipAttr m_Attr3 ;
    private EquipAttr m_Attr4 ;
    private EquipAttr m_Attr5 ;
    private EquipAttr m_Attr6 ;
    private EquipAttr m_Attr7 ;
    private ArrayList<EquipAttr> m_Array = new ArrayList<EquipAttr>();
    public ArrayList<EquipAttr> Arrays() { return m_Array; }
    public static MT_Data_EquipAttribute ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_EquipAttribute Data = new MT_Data_EquipAttribute();
		Data.m_nlevel = reader.getInt();
		Data.m_Attr1 = EquipAttr.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_Attr1);
		Data.m_Attr2 = EquipAttr.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_Attr2);
		Data.m_Attr3 = EquipAttr.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_Attr3);
		Data.m_Attr4 = EquipAttr.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_Attr4);
		Data.m_Attr5 = EquipAttr.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_Attr5);
		Data.m_Attr6 = EquipAttr.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_Attr6);
		Data.m_Attr7 = EquipAttr.ReadMemory(reader, fileName);
		Data.m_Array.add(Data.m_Attr7);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nlevel)) return false;
        if (!TableUtil.IsInvalid(this.m_Attr1)) return false;
        if (!TableUtil.IsInvalid(this.m_Attr2)) return false;
        if (!TableUtil.IsInvalid(this.m_Attr3)) return false;
        if (!TableUtil.IsInvalid(this.m_Attr4)) return false;
        if (!TableUtil.IsInvalid(this.m_Attr5)) return false;
        if (!TableUtil.IsInvalid(this.m_Attr6)) return false;
        if (!TableUtil.IsInvalid(this.m_Attr7)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
            return null;
        }
    @Override
    public String toString ( ) {
        String str = "";
        return str;
    }
}