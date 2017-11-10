package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_HeroPath extends MT_DataBase {
    public static String MD5 = "28fc5e80606b3198a9b7b95eb15c1e71";
    private Integer m_nindex ;
    /** 关卡ID */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 关卡ID */
    public Integer ID() { return m_nindex; }
    private Integer m_nnextId ;
    /** 下一关卡ID */
    public Integer nextId() { return m_nnextId; }
    public Integer getM_nnextId() {return m_nnextId; }
    private Integer m_ntype ;
    /** 类型 */
    public Integer type() { return m_ntype; }
    public Integer getM_ntype() {return m_ntype; }
    private Integer m_ndropOut ;
    /** 掉落 */
    public Integer dropOut() { return m_ndropOut; }
    public Integer getM_ndropOut() {return m_ndropOut; }
    private String m_sname ;
    /** 关卡名字 */
    public String name() { return m_sname; }
    public String getM_sname() {return m_sname; }
    private String m_sScene ;
    /** 战斗场景 */
    public String Scene() { return m_sScene; }
    public String getM_sScene() {return m_sScene; }
    private Integer m_nCameraConfig ;
    /** 摄像机配置 */
    public Integer CameraConfig() { return m_nCameraConfig; }
    public Integer getM_nCameraConfig() {return m_nCameraConfig; }
    private String m_shead ;
    /** 关卡头像 */
    public String head() { return m_shead; }
    public String getM_shead() {return m_shead; }
    private Integer m_nlevel ;
    /** 关卡等级 */
    public Integer level() { return m_nlevel; }
    public Integer getM_nlevel() {return m_nlevel; }
    private Integer m_nwall ;
    /** 城墙等级 */
    public Integer wall() { return m_nwall; }
    public Integer getM_nwall() {return m_nwall; }
    private ArrayList<Integer> m_arraycropType = new ArrayList<Integer>();
    /** 敌方随机兵种 */
    public ArrayList<Integer> cropType() { return m_arraycropType; }
    public ArrayList<Integer> getM_arraycropType() {return m_arraycropType; }
    private Int2 m_cropLv ;
    /** 兵种等级随机 */
    public Int2 cropLv() { return m_cropLv; }
    public Int2 getM_cropLv() {return m_cropLv; }
    private ArrayList<Int2> m_arrayselfCrop = new ArrayList<Int2>();
    /** 本方选择士兵:对应士兵的数量 */
    public ArrayList<Int2> selfCrop() { return m_arrayselfCrop; }
    public ArrayList<Int2> getM_arrayselfCrop() {return m_arrayselfCrop; }
    private Int2 m_radomType ;
    /** 类型:排数(0不放置一列，固定3和4位置;1:放置一列,后面一个字段代表排数 */
    public Int2 radomType() { return m_radomType; }
    public Int2 getM_radomType() {return m_radomType; }
    private ArrayList<Integer> m_arrayHeroType = new ArrayList<Integer>();
    /** 英雄 */
    public ArrayList<Integer> HeroType() { return m_arrayHeroType; }
    public ArrayList<Integer> getM_arrayHeroType() {return m_arrayHeroType; }
    public static MT_Data_HeroPath ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_HeroPath Data = new MT_Data_HeroPath();

        Integer count;
		Data.m_nindex = reader.getInt();
		Data.m_nnextId = reader.getInt();
		Data.m_ntype = reader.getInt();
		Data.m_ndropOut = reader.getInt();
		Data.m_sname = TableUtil.ReadLString(reader, fileName,"name",Data.ID());
		Data.m_sScene = TableUtil.ReadString(reader);
		Data.m_nCameraConfig = reader.getInt();
		Data.m_shead = TableUtil.ReadString(reader);
		Data.m_nlevel = reader.getInt();
		Data.m_nwall = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arraycropType.add(reader.getInt());
        }

		Data.m_cropLv = Int2.ReadMemory(reader, fileName);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayselfCrop.add(Int2.ReadMemory(reader, fileName));
        }

		Data.m_radomType = Int2.ReadMemory(reader, fileName);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayHeroType.add(reader.getInt());
        }

		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_nnextId)) return false;
        if (!TableUtil.IsInvalid(this.m_ntype)) return false;
        if (!TableUtil.IsInvalid(this.m_ndropOut)) return false;
        if (!TableUtil.IsInvalid(this.m_sname)) return false;
        if (!TableUtil.IsInvalid(this.m_sScene)) return false;
        if (!TableUtil.IsInvalid(this.m_nCameraConfig)) return false;
        if (!TableUtil.IsInvalid(this.m_shead)) return false;
        if (!TableUtil.IsInvalid(this.m_nlevel)) return false;
        if (!TableUtil.IsInvalid(this.m_nwall)) return false;
        if (this.m_arraycropType.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_cropLv)) return false;
        if (this.m_arrayselfCrop.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_radomType)) return false;
        if (this.m_arrayHeroType.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("nextId"))
            return nextId();
        if (str.equals("type"))
            return type();
        if (str.equals("dropOut"))
            return dropOut();
        if (str.equals("name"))
            return name();
        if (str.equals("Scene"))
            return Scene();
        if (str.equals("CameraConfig"))
            return CameraConfig();
        if (str.equals("head"))
            return head();
        if (str.equals("level"))
            return level();
        if (str.equals("wall"))
            return wall();
        if (str.equals("cropType"))
            return cropType();
        if (str.equals("cropLv"))
            return cropLv();
        if (str.equals("selfCrop"))
            return selfCrop();
        if (str.equals("radomType"))
            return radomType();
        if (str.equals("HeroType"))
            return HeroType();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("nextId =" + nextId() + '\n');
        str += ("type =" + type() + '\n');
        str += ("dropOut =" + dropOut() + '\n');
        str += ("name =" + name() + '\n');
        str += ("Scene =" + Scene() + '\n');
        str += ("CameraConfig =" + CameraConfig() + '\n');
        str += ("head =" + head() + '\n');
        str += ("level =" + level() + '\n');
        str += ("wall =" + wall() + '\n');
        str += ("cropType =" + cropType() + '\n');
        str += ("cropLv =" + cropLv() + '\n');
        str += ("selfCrop =" + selfCrop() + '\n');
        str += ("radomType =" + radomType() + '\n');
        str += ("HeroType =" + HeroType() + '\n');
        return str;
    }
}