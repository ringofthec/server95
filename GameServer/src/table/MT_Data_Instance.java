package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Instance extends MT_DataBase {
    public static String MD5 = "0023bd6e0e8beae7d31f52d75c159b48";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private ArrayList<Int2> m_arrayExp = new ArrayList<Int2>();
    /** 通关奖励 */
    public ArrayList<Int2> Exp() { return m_arrayExp; }
    public ArrayList<Int2> getM_arrayExp() {return m_arrayExp; }
    private String m_sScene ;
    /** 战斗场景 */
    public String Scene() { return m_sScene; }
    public String getM_sScene() {return m_sScene; }
    private Integer m_nCameraConfig ;
    /** 摄像机配置 */
    public Integer CameraConfig() { return m_nCameraConfig; }
    public Integer getM_nCameraConfig() {return m_nCameraConfig; }
    private Integer m_nMaxCount ;
    /** 挑战次数 */
    public Integer MaxCount() { return m_nMaxCount; }
    public Integer getM_nMaxCount() {return m_nMaxCount; }
    private Integer m_nNeedCP ;
    /** 体力消耗 */
    public Integer NeedCP() { return m_nNeedCP; }
    public Integer getM_nNeedCP() {return m_nNeedCP; }
    private Int2 m_Pos ;
    /** 摆放位置 */
    public Int2 Pos() { return m_Pos; }
    public Int2 getM_Pos() {return m_Pos; }
    private ArrayList<Int2> m_arrayDropOut = new ArrayList<Int2>();
    /** 掉落 */
    public ArrayList<Int2> DropOut() { return m_arrayDropOut; }
    public ArrayList<Int2> getM_arrayDropOut() {return m_arrayDropOut; }
    private String m_sName ;
    /** 名字 */
    public String Name() { return m_sName; }
    public String getM_sName() {return m_sName; }
    private String m_sHead ;
    /** 头像 */
    public String Head() { return m_sHead; }
    public String getM_sHead() {return m_sHead; }
    private String m_sImage ;
    /** 关卡图片 */
    public String Image() { return m_sImage; }
    public String getM_sImage() {return m_sImage; }
    private Int3 m_ArrowsAngle ;
    /** 箭头角度 */
    public Int3 ArrowsAngle() { return m_ArrowsAngle; }
    public Int3 getM_ArrowsAngle() {return m_ArrowsAngle; }
    private Int2 m_ArrowsVolume ;
    /** 箭头大小 */
    public Int2 ArrowsVolume() { return m_ArrowsVolume; }
    public Int2 getM_ArrowsVolume() {return m_ArrowsVolume; }
    private Int2 m_ArrowsPos ;
    /** 箭头位置 */
    public Int2 ArrowsPos() { return m_ArrowsPos; }
    public Int2 getM_ArrowsPos() {return m_ArrowsPos; }
    private String m_sArrowImage ;
    /** 箭头图片 */
    public String ArrowImage() { return m_sArrowImage; }
    public String getM_sArrowImage() {return m_sArrowImage; }
    private Integer m_nHp ;
    /** 城堡血量 */
    public Integer Hp() { return m_nHp; }
    public Integer getM_nHp() {return m_nHp; }
    private Integer m_nLevel ;
    /** 敌方城堡等级 */
    public Integer Level() { return m_nLevel; }
    public Integer getM_nLevel() {return m_nLevel; }
    private Integer m_nWallLayout ;
    /** 敌方城墙布局 */
    public Integer WallLayout() { return m_nWallLayout; }
    public Integer getM_nWallLayout() {return m_nWallLayout; }
    private Integer m_nTeachLayout ;
    /** 虚影教学布局 */
    public Integer TeachLayout() { return m_nTeachLayout; }
    public Integer getM_nTeachLayout() {return m_nTeachLayout; }
    private ArrayList<Int2> m_arrayFirstWin = new ArrayList<Int2>();
    /** 首次通关 */
    public ArrayList<Int2> FirstWin() { return m_arrayFirstWin; }
    public ArrayList<Int2> getM_arrayFirstWin() {return m_arrayFirstWin; }
    public static MT_Data_Instance ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Instance Data = new MT_Data_Instance();

        Integer count;
		Data.m_nindex = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayExp.add(Int2.ReadMemory(reader, fileName));
        }

		Data.m_sScene = TableUtil.ReadString(reader);
		Data.m_nCameraConfig = reader.getInt();
		Data.m_nMaxCount = reader.getInt();
		Data.m_nNeedCP = reader.getInt();
		Data.m_Pos = Int2.ReadMemory(reader, fileName);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayDropOut.add(Int2.ReadMemory(reader, fileName));
        }

		Data.m_sName = TableUtil.ReadLString(reader, fileName,"Name",Data.ID());
		Data.m_sHead = TableUtil.ReadString(reader);
		Data.m_sImage = TableUtil.ReadString(reader);
		Data.m_ArrowsAngle = Int3.ReadMemory(reader, fileName);
		Data.m_ArrowsVolume = Int2.ReadMemory(reader, fileName);
		Data.m_ArrowsPos = Int2.ReadMemory(reader, fileName);
		Data.m_sArrowImage = TableUtil.ReadString(reader);
		Data.m_nHp = reader.getInt();
		Data.m_nLevel = reader.getInt();
		Data.m_nWallLayout = reader.getInt();
		Data.m_nTeachLayout = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayFirstWin.add(Int2.ReadMemory(reader, fileName));
        }

		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (this.m_arrayExp.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_sScene)) return false;
        if (!TableUtil.IsInvalid(this.m_nCameraConfig)) return false;
        if (!TableUtil.IsInvalid(this.m_nMaxCount)) return false;
        if (!TableUtil.IsInvalid(this.m_nNeedCP)) return false;
        if (!TableUtil.IsInvalid(this.m_Pos)) return false;
        if (this.m_arrayDropOut.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_sName)) return false;
        if (!TableUtil.IsInvalid(this.m_sHead)) return false;
        if (!TableUtil.IsInvalid(this.m_sImage)) return false;
        if (!TableUtil.IsInvalid(this.m_ArrowsAngle)) return false;
        if (!TableUtil.IsInvalid(this.m_ArrowsVolume)) return false;
        if (!TableUtil.IsInvalid(this.m_ArrowsPos)) return false;
        if (!TableUtil.IsInvalid(this.m_sArrowImage)) return false;
        if (!TableUtil.IsInvalid(this.m_nHp)) return false;
        if (!TableUtil.IsInvalid(this.m_nLevel)) return false;
        if (!TableUtil.IsInvalid(this.m_nWallLayout)) return false;
        if (!TableUtil.IsInvalid(this.m_nTeachLayout)) return false;
        if (this.m_arrayFirstWin.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("Exp"))
            return Exp();
        if (str.equals("Scene"))
            return Scene();
        if (str.equals("CameraConfig"))
            return CameraConfig();
        if (str.equals("MaxCount"))
            return MaxCount();
        if (str.equals("NeedCP"))
            return NeedCP();
        if (str.equals("Pos"))
            return Pos();
        if (str.equals("DropOut"))
            return DropOut();
        if (str.equals("Name"))
            return Name();
        if (str.equals("Head"))
            return Head();
        if (str.equals("Image"))
            return Image();
        if (str.equals("ArrowsAngle"))
            return ArrowsAngle();
        if (str.equals("ArrowsVolume"))
            return ArrowsVolume();
        if (str.equals("ArrowsPos"))
            return ArrowsPos();
        if (str.equals("ArrowImage"))
            return ArrowImage();
        if (str.equals("Hp"))
            return Hp();
        if (str.equals("Level"))
            return Level();
        if (str.equals("WallLayout"))
            return WallLayout();
        if (str.equals("TeachLayout"))
            return TeachLayout();
        if (str.equals("FirstWin"))
            return FirstWin();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("Exp =" + Exp() + '\n');
        str += ("Scene =" + Scene() + '\n');
        str += ("CameraConfig =" + CameraConfig() + '\n');
        str += ("MaxCount =" + MaxCount() + '\n');
        str += ("NeedCP =" + NeedCP() + '\n');
        str += ("Pos =" + Pos() + '\n');
        str += ("DropOut =" + DropOut() + '\n');
        str += ("Name =" + Name() + '\n');
        str += ("Head =" + Head() + '\n');
        str += ("Image =" + Image() + '\n');
        str += ("ArrowsAngle =" + ArrowsAngle() + '\n');
        str += ("ArrowsVolume =" + ArrowsVolume() + '\n');
        str += ("ArrowsPos =" + ArrowsPos() + '\n');
        str += ("ArrowImage =" + ArrowImage() + '\n');
        str += ("Hp =" + Hp() + '\n');
        str += ("Level =" + Level() + '\n');
        str += ("WallLayout =" + WallLayout() + '\n');
        str += ("TeachLayout =" + TeachLayout() + '\n');
        str += ("FirstWin =" + FirstWin() + '\n');
        return str;
    }
}