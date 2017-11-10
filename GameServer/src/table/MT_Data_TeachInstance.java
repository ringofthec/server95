package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_TeachInstance extends MT_DataBase {
    public static String MD5 = "52cd55a3f145c626a9e5157cb7399dd6";
    private Integer m_nIndex ;
    /** 索引 */
    public Integer Index() { return m_nIndex; }
    public Integer getM_nIndex() {return m_nIndex; }
    /** 索引 */
    public Integer ID() { return m_nIndex; }
    private ArrayList<Int3> m_arrayCorps = new ArrayList<Int3>();
    /** 我方兵种数量 */
    public ArrayList<Int3> Corps() { return m_arrayCorps; }
    public ArrayList<Int3> getM_arrayCorps() {return m_arrayCorps; }
    private Integer m_nTeachLayout ;
    /** 我方虚影布局 */
    public Integer TeachLayout() { return m_nTeachLayout; }
    public Integer getM_nTeachLayout() {return m_nTeachLayout; }
    private Integer m_nOwnHp ;
    /** 我方城堡血量 */
    public Integer OwnHp() { return m_nOwnHp; }
    public Integer getM_nOwnHp() {return m_nOwnHp; }
    private Integer m_nOwnWallLayout ;
    /** 我方城墙布局 */
    public Integer OwnWallLayout() { return m_nOwnWallLayout; }
    public Integer getM_nOwnWallLayout() {return m_nOwnWallLayout; }
    private Integer m_nEnemyLayout ;
    /** 敌人布阵 */
    public Integer EnemyLayout() { return m_nEnemyLayout; }
    public Integer getM_nEnemyLayout() {return m_nEnemyLayout; }
    private String m_sEnemyName ;
    /** 敌人名字 */
    public String EnemyName() { return m_sEnemyName; }
    public String getM_sEnemyName() {return m_sEnemyName; }
    private String m_sEnemyHead ;
    /** 敌人头像 */
    public String EnemyHead() { return m_sEnemyHead; }
    public String getM_sEnemyHead() {return m_sEnemyHead; }
    private Integer m_nEnemyHp ;
    /** 敌人城堡血量 */
    public Integer EnemyHp() { return m_nEnemyHp; }
    public Integer getM_nEnemyHp() {return m_nEnemyHp; }
    private Integer m_nEnemyLevel ;
    /** 敌人等级 */
    public Integer EnemyLevel() { return m_nEnemyLevel; }
    public Integer getM_nEnemyLevel() {return m_nEnemyLevel; }
    private Integer m_nEnemyWallLayout ;
    /** 敌人城墙布局 */
    public Integer EnemyWallLayout() { return m_nEnemyWallLayout; }
    public Integer getM_nEnemyWallLayout() {return m_nEnemyWallLayout; }
    public static MT_Data_TeachInstance ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_TeachInstance Data = new MT_Data_TeachInstance();

        Integer count;
		Data.m_nIndex = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayCorps.add(Int3.ReadMemory(reader, fileName));
        }

		Data.m_nTeachLayout = reader.getInt();
		Data.m_nOwnHp = reader.getInt();
		Data.m_nOwnWallLayout = reader.getInt();
		Data.m_nEnemyLayout = reader.getInt();
		Data.m_sEnemyName = TableUtil.ReadLString(reader, fileName,"EnemyName",Data.ID());
		Data.m_sEnemyHead = TableUtil.ReadString(reader);
		Data.m_nEnemyHp = reader.getInt();
		Data.m_nEnemyLevel = reader.getInt();
		Data.m_nEnemyWallLayout = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nIndex)) return false;
        if (this.m_arrayCorps.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_nTeachLayout)) return false;
        if (!TableUtil.IsInvalid(this.m_nOwnHp)) return false;
        if (!TableUtil.IsInvalid(this.m_nOwnWallLayout)) return false;
        if (!TableUtil.IsInvalid(this.m_nEnemyLayout)) return false;
        if (!TableUtil.IsInvalid(this.m_sEnemyName)) return false;
        if (!TableUtil.IsInvalid(this.m_sEnemyHead)) return false;
        if (!TableUtil.IsInvalid(this.m_nEnemyHp)) return false;
        if (!TableUtil.IsInvalid(this.m_nEnemyLevel)) return false;
        if (!TableUtil.IsInvalid(this.m_nEnemyWallLayout)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("Index"))
            return Index();
        if (str.equals("Corps"))
            return Corps();
        if (str.equals("TeachLayout"))
            return TeachLayout();
        if (str.equals("OwnHp"))
            return OwnHp();
        if (str.equals("OwnWallLayout"))
            return OwnWallLayout();
        if (str.equals("EnemyLayout"))
            return EnemyLayout();
        if (str.equals("EnemyName"))
            return EnemyName();
        if (str.equals("EnemyHead"))
            return EnemyHead();
        if (str.equals("EnemyHp"))
            return EnemyHp();
        if (str.equals("EnemyLevel"))
            return EnemyLevel();
        if (str.equals("EnemyWallLayout"))
            return EnemyWallLayout();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("Index =" + Index() + '\n');
        str += ("Corps =" + Corps() + '\n');
        str += ("TeachLayout =" + TeachLayout() + '\n');
        str += ("OwnHp =" + OwnHp() + '\n');
        str += ("OwnWallLayout =" + OwnWallLayout() + '\n');
        str += ("EnemyLayout =" + EnemyLayout() + '\n');
        str += ("EnemyName =" + EnemyName() + '\n');
        str += ("EnemyHead =" + EnemyHead() + '\n');
        str += ("EnemyHp =" + EnemyHp() + '\n');
        str += ("EnemyLevel =" + EnemyLevel() + '\n');
        str += ("EnemyWallLayout =" + EnemyWallLayout() + '\n');
        return str;
    }
}