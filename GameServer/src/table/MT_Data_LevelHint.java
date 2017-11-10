package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_LevelHint extends MT_DataBase {
    public static String MD5 = "24c01f576b0dbd4442bc41e0248ef8e0";
    private Integer m_nIndex ;
    /** 索引 */
    public Integer Index() { return m_nIndex; }
    public Integer getM_nIndex() {return m_nIndex; }
    /** 索引 */
    public Integer ID() { return m_nIndex; }
    private Integer m_nLevel ;
    /** 等级 */
    public Integer Level() { return m_nLevel; }
    public Integer getM_nLevel() {return m_nLevel; }
    private Integer m_nType ;
    /** 功能类型 */
    public Integer Type() { return m_nType; }
    public Integer getM_nType() {return m_nType; }
    private String m_sImage ;
    /** 开放功能图片 */
    public String Image() { return m_sImage; }
    public String getM_sImage() {return m_sImage; }
    private String m_sName ;
    /** 开放功能名称 */
    public String Name() { return m_sName; }
    public String getM_sName() {return m_sName; }
    private Integer m_nNewLevel ;
    /** 等级 */
    public Integer NewLevel() { return m_nNewLevel; }
    public Integer getM_nNewLevel() {return m_nNewLevel; }
    public static MT_Data_LevelHint ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_LevelHint Data = new MT_Data_LevelHint();
		Data.m_nIndex = reader.getInt();
		Data.m_nLevel = reader.getInt();
		Data.m_nType = reader.getInt();
		Data.m_sImage = TableUtil.ReadString(reader);
		Data.m_sName = TableUtil.ReadLString(reader, fileName,"Name",Data.ID());
		Data.m_nNewLevel = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nIndex)) return false;
        if (!TableUtil.IsInvalid(this.m_nLevel)) return false;
        if (!TableUtil.IsInvalid(this.m_nType)) return false;
        if (!TableUtil.IsInvalid(this.m_sImage)) return false;
        if (!TableUtil.IsInvalid(this.m_sName)) return false;
        if (!TableUtil.IsInvalid(this.m_nNewLevel)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("Index"))
            return Index();
        if (str.equals("Level"))
            return Level();
        if (str.equals("Type"))
            return Type();
        if (str.equals("Image"))
            return Image();
        if (str.equals("Name"))
            return Name();
        if (str.equals("NewLevel"))
            return NewLevel();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("Index =" + Index() + '\n');
        str += ("Level =" + Level() + '\n');
        str += ("Type =" + Type() + '\n');
        str += ("Image =" + Image() + '\n');
        str += ("Name =" + Name() + '\n');
        str += ("NewLevel =" + NewLevel() + '\n');
        return str;
    }
}