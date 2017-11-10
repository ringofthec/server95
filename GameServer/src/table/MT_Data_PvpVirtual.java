package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_PvpVirtual extends MT_DataBase {
    public static String MD5 = "c7051cbe5ab64c3793d73920afdca4f8";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private Integer m_nfeat ;
    /** 军衔 */
    public Integer feat() { return m_nfeat; }
    public Integer getM_nfeat() {return m_nfeat; }
    private Int2 m_lv ;
    /** 等级区间 */
    public Int2 lv() { return m_lv; }
    public Int2 getM_lv() {return m_lv; }
    private Int2 m_cropNum ;
    /** 兵种排数随机量 */
    public Int2 cropNum() { return m_cropNum; }
    public Int2 getM_cropNum() {return m_cropNum; }
    private ArrayList<Integer> m_arraycropType = new ArrayList<Integer>();
    /** 每排兵种类型 */
    public ArrayList<Integer> cropType() { return m_arraycropType; }
    public ArrayList<Integer> getM_arraycropType() {return m_arraycropType; }
    private Int2 m_cropLv ;
    /** 兵种等级随机 */
    public Int2 cropLv() { return m_cropLv; }
    public Int2 getM_cropLv() {return m_cropLv; }
    private Int2 m_bulidLv ;
    /** 城防中心等级 */
    public Int2 bulidLv() { return m_bulidLv; }
    public Int2 getM_bulidLv() {return m_bulidLv; }
    private Integer m_nminNum ;
    /** 抢夺资源最低数量 */
    public Integer minNum() { return m_nminNum; }
    public Integer getM_nminNum() {return m_nminNum; }
    private Integer m_nmaxNum ;
    /** 抢夺资源最高数量 */
    public Integer maxNum() { return m_nmaxNum; }
    public Integer getM_nmaxNum() {return m_nmaxNum; }
    private ArrayList<Integer> m_arrayHeroType = new ArrayList<Integer>();
    /** 英雄 */
    public ArrayList<Integer> HeroType() { return m_arrayHeroType; }
    public ArrayList<Integer> getM_arrayHeroType() {return m_arrayHeroType; }
    public static MT_Data_PvpVirtual ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_PvpVirtual Data = new MT_Data_PvpVirtual();

        Integer count;
		Data.m_nindex = reader.getInt();
		Data.m_nfeat = reader.getInt();
		Data.m_lv = Int2.ReadMemory(reader, fileName);
		Data.m_cropNum = Int2.ReadMemory(reader, fileName);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arraycropType.add(reader.getInt());
        }

		Data.m_cropLv = Int2.ReadMemory(reader, fileName);
		Data.m_bulidLv = Int2.ReadMemory(reader, fileName);
		Data.m_nminNum = reader.getInt();
		Data.m_nmaxNum = reader.getInt();
		
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
        if (!TableUtil.IsInvalid(this.m_nfeat)) return false;
        if (!TableUtil.IsInvalid(this.m_lv)) return false;
        if (!TableUtil.IsInvalid(this.m_cropNum)) return false;
        if (this.m_arraycropType.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_cropLv)) return false;
        if (!TableUtil.IsInvalid(this.m_bulidLv)) return false;
        if (!TableUtil.IsInvalid(this.m_nminNum)) return false;
        if (!TableUtil.IsInvalid(this.m_nmaxNum)) return false;
        if (this.m_arrayHeroType.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("feat"))
            return feat();
        if (str.equals("lv"))
            return lv();
        if (str.equals("cropNum"))
            return cropNum();
        if (str.equals("cropType"))
            return cropType();
        if (str.equals("cropLv"))
            return cropLv();
        if (str.equals("bulidLv"))
            return bulidLv();
        if (str.equals("minNum"))
            return minNum();
        if (str.equals("maxNum"))
            return maxNum();
        if (str.equals("HeroType"))
            return HeroType();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("feat =" + feat() + '\n');
        str += ("lv =" + lv() + '\n');
        str += ("cropNum =" + cropNum() + '\n');
        str += ("cropType =" + cropType() + '\n');
        str += ("cropLv =" + cropLv() + '\n');
        str += ("bulidLv =" + bulidLv() + '\n');
        str += ("minNum =" + minNum() + '\n');
        str += ("maxNum =" + maxNum() + '\n');
        str += ("HeroType =" + HeroType() + '\n');
        return str;
    }
}