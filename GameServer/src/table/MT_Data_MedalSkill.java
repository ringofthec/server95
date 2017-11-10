package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_MedalSkill extends MT_DataBase {
    public static String MD5 = "3d23d4688badca15fc539761a77a7ae4";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private String m_sName ;
    /** 技能名字 */
    public String Name() { return m_sName; }
    public String getM_sName() {return m_sName; }
    private String m_sImage ;
    /** 图标 */
    public String Image() { return m_sImage; }
    public String getM_sImage() {return m_sImage; }
    private String m_sText ;
    /** 描述 */
    public String Text() { return m_sText; }
    public String getM_sText() {return m_sText; }
    private Integer m_nLevel ;
    /** 等级 */
    public Integer Level() { return m_nLevel; }
    public Integer getM_nLevel() {return m_nLevel; }
    private Integer m_nType ;
    /** 开启类型 */
    public Integer Type() { return m_nType; }
    public Integer getM_nType() {return m_nType; }
    private Integer m_nNeedNum ;
    /** 升级需求 */
    public Integer NeedNum() { return m_nNeedNum; }
    public Integer getM_nNeedNum() {return m_nNeedNum; }
    private ArrayList<Int2> m_arrayBuffs = new ArrayList<Int2>();
    /** BUFF列表 */
    public ArrayList<Int2> Buffs() { return m_arrayBuffs; }
    public ArrayList<Int2> getM_arrayBuffs() {return m_arrayBuffs; }
    public static MT_Data_MedalSkill ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_MedalSkill Data = new MT_Data_MedalSkill();

        Integer count;
		Data.m_nindex = reader.getInt();
		Data.m_sName = TableUtil.ReadLString(reader, fileName,"Name",Data.ID());
		Data.m_sImage = TableUtil.ReadString(reader);
		Data.m_sText = TableUtil.ReadLString(reader, fileName,"Text",Data.ID());
		Data.m_nLevel = reader.getInt();
		Data.m_nType = reader.getInt();
		Data.m_nNeedNum = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayBuffs.add(Int2.ReadMemory(reader, fileName));
        }

		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_sName)) return false;
        if (!TableUtil.IsInvalid(this.m_sImage)) return false;
        if (!TableUtil.IsInvalid(this.m_sText)) return false;
        if (!TableUtil.IsInvalid(this.m_nLevel)) return false;
        if (!TableUtil.IsInvalid(this.m_nType)) return false;
        if (!TableUtil.IsInvalid(this.m_nNeedNum)) return false;
        if (this.m_arrayBuffs.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("Name"))
            return Name();
        if (str.equals("Image"))
            return Image();
        if (str.equals("Text"))
            return Text();
        if (str.equals("Level"))
            return Level();
        if (str.equals("Type"))
            return Type();
        if (str.equals("NeedNum"))
            return NeedNum();
        if (str.equals("Buffs"))
            return Buffs();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("Name =" + Name() + '\n');
        str += ("Image =" + Image() + '\n');
        str += ("Text =" + Text() + '\n');
        str += ("Level =" + Level() + '\n');
        str += ("Type =" + Type() + '\n');
        str += ("NeedNum =" + NeedNum() + '\n');
        str += ("Buffs =" + Buffs() + '\n');
        return str;
    }
}