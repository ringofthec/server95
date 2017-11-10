package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_depot extends MT_DataBase {
    public static String MD5 = "6792520131b8932314d3ad1463e9ee07";
    private Integer m_nLevel ;
    /** 等级 */
    public Integer Level() { return m_nLevel; }
    public Integer getM_nLevel() {return m_nLevel; }
    /** 等级 */
    public Integer ID() { return m_nLevel; }
    private String m_sFile ;
    /** 路径 */
    public String File() { return m_sFile; }
    public String getM_sFile() {return m_sFile; }
    private Int2 m_Volume ;
    /** 体积 */
    public Int2 Volume() { return m_Volume; }
    public Int2 getM_Volume() {return m_Volume; }
    private String m_sName ;
    /** 名字 */
    public String Name() { return m_sName; }
    public String getM_sName() {return m_sName; }
    private Int2 m_MaxStore ;
    /** 存储上限 */
    public Int2 MaxStore() { return m_MaxStore; }
    public Int2 getM_MaxStore() {return m_MaxStore; }
    private Integer m_nDelete ;
    /** 是否可拆除 */
    public Integer Delete() { return m_nDelete; }
    public Integer getM_nDelete() {return m_nDelete; }
    private Integer m_nNeedPlayerLv ;
    /** 升级所需玩家等级 */
    public Integer NeedPlayerLv() { return m_nNeedPlayerLv; }
    public Integer getM_nNeedPlayerLv() {return m_nNeedPlayerLv; }
    private Int2 m_NeedBuild ;
    /** 升级所需建筑 */
    public Int2 NeedBuild() { return m_NeedBuild; }
    public Int2 getM_NeedBuild() {return m_NeedBuild; }
    private ArrayList<Int2> m_arrayNeedResource = new ArrayList<Int2>();
    /** 升级所需资源 */
    public ArrayList<Int2> NeedResource() { return m_arrayNeedResource; }
    public ArrayList<Int2> getM_arrayNeedResource() {return m_arrayNeedResource; }
    private Integer m_nUpgradeTimer ;
    /** 升级时间(秒) */
    public Integer UpgradeTimer() { return m_nUpgradeTimer; }
    public Integer getM_nUpgradeTimer() {return m_nUpgradeTimer; }
    private Integer m_nLevelUpExp ;
    /** 升级加成 */
    public Integer LevelUpExp() { return m_nLevelUpExp; }
    public Integer getM_nLevelUpExp() {return m_nLevelUpExp; }
    public static MT_Data_depot ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_depot Data = new MT_Data_depot();

        Integer count;
		Data.m_nLevel = reader.getInt();
		Data.m_sFile = TableUtil.ReadString(reader);
		Data.m_Volume = Int2.ReadMemory(reader, fileName);
		Data.m_sName = TableUtil.ReadLString(reader, fileName,"Name",Data.ID());
		Data.m_MaxStore = Int2.ReadMemory(reader, fileName);
		Data.m_nDelete = reader.getInt();
		Data.m_nNeedPlayerLv = reader.getInt();
		Data.m_NeedBuild = Int2.ReadMemory(reader, fileName);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayNeedResource.add(Int2.ReadMemory(reader, fileName));
        }

		Data.m_nUpgradeTimer = reader.getInt();
		Data.m_nLevelUpExp = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nLevel)) return false;
        if (!TableUtil.IsInvalid(this.m_sFile)) return false;
        if (!TableUtil.IsInvalid(this.m_Volume)) return false;
        if (!TableUtil.IsInvalid(this.m_sName)) return false;
        if (!TableUtil.IsInvalid(this.m_MaxStore)) return false;
        if (!TableUtil.IsInvalid(this.m_nDelete)) return false;
        if (!TableUtil.IsInvalid(this.m_nNeedPlayerLv)) return false;
        if (!TableUtil.IsInvalid(this.m_NeedBuild)) return false;
        if (this.m_arrayNeedResource.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_nUpgradeTimer)) return false;
        if (!TableUtil.IsInvalid(this.m_nLevelUpExp)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("Level"))
            return Level();
        if (str.equals("File"))
            return File();
        if (str.equals("Volume"))
            return Volume();
        if (str.equals("Name"))
            return Name();
        if (str.equals("MaxStore"))
            return MaxStore();
        if (str.equals("Delete"))
            return Delete();
        if (str.equals("NeedPlayerLv"))
            return NeedPlayerLv();
        if (str.equals("NeedBuild"))
            return NeedBuild();
        if (str.equals("NeedResource"))
            return NeedResource();
        if (str.equals("UpgradeTimer"))
            return UpgradeTimer();
        if (str.equals("LevelUpExp"))
            return LevelUpExp();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("Level =" + Level() + '\n');
        str += ("File =" + File() + '\n');
        str += ("Volume =" + Volume() + '\n');
        str += ("Name =" + Name() + '\n');
        str += ("MaxStore =" + MaxStore() + '\n');
        str += ("Delete =" + Delete() + '\n');
        str += ("NeedPlayerLv =" + NeedPlayerLv() + '\n');
        str += ("NeedBuild =" + NeedBuild() + '\n');
        str += ("NeedResource =" + NeedResource() + '\n');
        str += ("UpgradeTimer =" + UpgradeTimer() + '\n');
        str += ("LevelUpExp =" + LevelUpExp() + '\n');
        return str;
    }
}