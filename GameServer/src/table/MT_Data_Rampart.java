package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Rampart extends MT_DataBase {
    public static String MD5 = "e877c14df9a6f46de8bb41356db9fb9e";
    private Integer m_nIndex ;
    /** ID */
    public Integer Index() { return m_nIndex; }
    public Integer getM_nIndex() {return m_nIndex; }
    /** ID */
    public Integer ID() { return m_nIndex; }
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
    private Integer m_nWallHP ;
    /** 城墙血量 */
    public Integer WallHP() { return m_nWallHP; }
    public Integer getM_nWallHP() {return m_nWallHP; }
    private Integer m_nWallLayout ;
    /** 城墙布局（WallLayout） */
    public Integer WallLayout() { return m_nWallLayout; }
    public Integer getM_nWallLayout() {return m_nWallLayout; }
    private Integer m_nLevelUpExp ;
    /** 升级加成 */
    public Integer LevelUpExp() { return m_nLevelUpExp; }
    public Integer getM_nLevelUpExp() {return m_nLevelUpExp; }
    private Integer m_nPower ;
    /** 战斗力 */
    public Integer Power() { return m_nPower; }
    public Integer getM_nPower() {return m_nPower; }
    public static MT_Data_Rampart ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Rampart Data = new MT_Data_Rampart();

        Integer count;
		Data.m_nIndex = reader.getInt();
		Data.m_sFile = TableUtil.ReadString(reader);
		Data.m_Volume = Int2.ReadMemory(reader, fileName);
		Data.m_sName = TableUtil.ReadLString(reader, fileName,"Name",Data.ID());
		Data.m_nDelete = reader.getInt();
		Data.m_nNeedPlayerLv = reader.getInt();
		Data.m_NeedBuild = Int2.ReadMemory(reader, fileName);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayNeedResource.add(Int2.ReadMemory(reader, fileName));
        }

		Data.m_nUpgradeTimer = reader.getInt();
		Data.m_nWallHP = reader.getInt();
		Data.m_nWallLayout = reader.getInt();
		Data.m_nLevelUpExp = reader.getInt();
		Data.m_nPower = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nIndex)) return false;
        if (!TableUtil.IsInvalid(this.m_sFile)) return false;
        if (!TableUtil.IsInvalid(this.m_Volume)) return false;
        if (!TableUtil.IsInvalid(this.m_sName)) return false;
        if (!TableUtil.IsInvalid(this.m_nDelete)) return false;
        if (!TableUtil.IsInvalid(this.m_nNeedPlayerLv)) return false;
        if (!TableUtil.IsInvalid(this.m_NeedBuild)) return false;
        if (this.m_arrayNeedResource.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_nUpgradeTimer)) return false;
        if (!TableUtil.IsInvalid(this.m_nWallHP)) return false;
        if (!TableUtil.IsInvalid(this.m_nWallLayout)) return false;
        if (!TableUtil.IsInvalid(this.m_nLevelUpExp)) return false;
        if (!TableUtil.IsInvalid(this.m_nPower)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("Index"))
            return Index();
        if (str.equals("File"))
            return File();
        if (str.equals("Volume"))
            return Volume();
        if (str.equals("Name"))
            return Name();
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
        if (str.equals("WallHP"))
            return WallHP();
        if (str.equals("WallLayout"))
            return WallLayout();
        if (str.equals("LevelUpExp"))
            return LevelUpExp();
        if (str.equals("Power"))
            return Power();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("Index =" + Index() + '\n');
        str += ("File =" + File() + '\n');
        str += ("Volume =" + Volume() + '\n');
        str += ("Name =" + Name() + '\n');
        str += ("Delete =" + Delete() + '\n');
        str += ("NeedPlayerLv =" + NeedPlayerLv() + '\n');
        str += ("NeedBuild =" + NeedBuild() + '\n');
        str += ("NeedResource =" + NeedResource() + '\n');
        str += ("UpgradeTimer =" + UpgradeTimer() + '\n');
        str += ("WallHP =" + WallHP() + '\n');
        str += ("WallLayout =" + WallLayout() + '\n');
        str += ("LevelUpExp =" + LevelUpExp() + '\n');
        str += ("Power =" + Power() + '\n');
        return str;
    }
}