package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_legionConfig extends MT_DataBase {
    public static String MD5 = "243e38d1d64632f71ad726e6093fa979";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private Int2 m_money ;
    /** 创建军团需要的金钱 */
    public Int2 money() { return m_money; }
    public Int2 getM_money() {return m_money; }
    private Integer m_ncontribution ;
    /** 捐献金币/物资一次贡献值 */
    public Integer contribution() { return m_ncontribution; }
    public Integer getM_ncontribution() {return m_ncontribution; }
    private Integer m_nnum ;
    /** 军团列表显示个数 */
    public Integer num() { return m_nnum; }
    public Integer getM_nnum() {return m_nnum; }
    private Integer m_nmaxlv ;
    /** 军团开放的最高等级 */
    public Integer maxlv() { return m_nmaxlv; }
    public Integer getM_nmaxlv() {return m_nmaxlv; }
    private Integer m_ntimeInterval ;
    /** 军团商店间隔时间/小时 */
    public Integer timeInterval() { return m_ntimeInterval; }
    public Integer getM_ntimeInterval() {return m_ntimeInterval; }
    private ArrayList<String> m_arrayoutType = new ArrayList<String>();
    /** 对应幸运值必出类型 */
    public ArrayList<String> outType() { return m_arrayoutType; }
    public ArrayList<String> getM_arrayoutType() {return m_arrayoutType; }
    private String m_saddType ;
    /** 随机选中商品个数不足用某类型补满 */
    public String addType() { return m_saddType; }
    public String getM_saddType() {return m_saddType; }
    private ArrayList<String> m_arraycurType = new ArrayList<String>();
    /** 商店列表现有类型 */
    public ArrayList<String> curType() { return m_arraycurType; }
    public ArrayList<String> getM_arraycurType() {return m_arraycurType; }
    private Integer m_nstoreSize ;
    /** 商店列表显示个数 */
    public Integer storeSize() { return m_nstoreSize; }
    public Integer getM_nstoreSize() {return m_nstoreSize; }
    private Int3 m_luckVal ;
    /** 玩家幸运值 */
    public Int3 luckVal() { return m_luckVal; }
    public Int3 getM_luckVal() {return m_luckVal; }
    private String m_snotice ;
    /** 公告 */
    public String notice() { return m_snotice; }
    public String getM_snotice() {return m_snotice; }
    private Int2 m_goodsNum ;
    /** 捐献物资数量(1一次捐1个，一次捐10个) */
    public Int2 goodsNum() { return m_goodsNum; }
    public Int2 getM_goodsNum() {return m_goodsNum; }
    private Int3 m_LessTime ;
    /** 求助操作总耗时低于2小时时(单位是分钟)，每次求组会减少1分钟的耗时(单位是分钟),大于2个小时，减少1% */
    public Int3 LessTime() { return m_LessTime; }
    public Int3 getM_LessTime() {return m_LessTime; }
    private Integer m_nopenLv ;
    /** 军团商店开启等级 */
    public Integer openLv() { return m_nopenLv; }
    public Integer getM_nopenLv() {return m_nopenLv; }
    private Integer m_nmaxNum ;
    /** 帮助的最大次数 */
    public Integer maxNum() { return m_nmaxNum; }
    public Integer getM_nmaxNum() {return m_nmaxNum; }
    private Integer m_nfreshNum ;
    /** 刷新商店需要的劵数量 */
    public Integer freshNum() { return m_nfreshNum; }
    public Integer getM_nfreshNum() {return m_nfreshNum; }
    public static MT_Data_legionConfig ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_legionConfig Data = new MT_Data_legionConfig();

        Integer count;
		Data.m_nindex = reader.getInt();
		Data.m_money = Int2.ReadMemory(reader, fileName);
		Data.m_ncontribution = reader.getInt();
		Data.m_nnum = reader.getInt();
		Data.m_nmaxlv = reader.getInt();
		Data.m_ntimeInterval = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayoutType.add(TableUtil.ReadString(reader));
        }

		Data.m_saddType = TableUtil.ReadString(reader);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arraycurType.add(TableUtil.ReadString(reader));
        }

		Data.m_nstoreSize = reader.getInt();
		Data.m_luckVal = Int3.ReadMemory(reader, fileName);
		Data.m_snotice = TableUtil.ReadLString(reader, fileName,"notice",Data.ID());
		Data.m_goodsNum = Int2.ReadMemory(reader, fileName);
		Data.m_LessTime = Int3.ReadMemory(reader, fileName);
		Data.m_nopenLv = reader.getInt();
		Data.m_nmaxNum = reader.getInt();
		Data.m_nfreshNum = reader.getInt();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_money)) return false;
        if (!TableUtil.IsInvalid(this.m_ncontribution)) return false;
        if (!TableUtil.IsInvalid(this.m_nnum)) return false;
        if (!TableUtil.IsInvalid(this.m_nmaxlv)) return false;
        if (!TableUtil.IsInvalid(this.m_ntimeInterval)) return false;
        if (this.m_arrayoutType.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_saddType)) return false;
        if (this.m_arraycurType.size() > 0) return false;
        if (!TableUtil.IsInvalid(this.m_nstoreSize)) return false;
        if (!TableUtil.IsInvalid(this.m_luckVal)) return false;
        if (!TableUtil.IsInvalid(this.m_snotice)) return false;
        if (!TableUtil.IsInvalid(this.m_goodsNum)) return false;
        if (!TableUtil.IsInvalid(this.m_LessTime)) return false;
        if (!TableUtil.IsInvalid(this.m_nopenLv)) return false;
        if (!TableUtil.IsInvalid(this.m_nmaxNum)) return false;
        if (!TableUtil.IsInvalid(this.m_nfreshNum)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("money"))
            return money();
        if (str.equals("contribution"))
            return contribution();
        if (str.equals("num"))
            return num();
        if (str.equals("maxlv"))
            return maxlv();
        if (str.equals("timeInterval"))
            return timeInterval();
        if (str.equals("outType"))
            return outType();
        if (str.equals("addType"))
            return addType();
        if (str.equals("curType"))
            return curType();
        if (str.equals("storeSize"))
            return storeSize();
        if (str.equals("luckVal"))
            return luckVal();
        if (str.equals("notice"))
            return notice();
        if (str.equals("goodsNum"))
            return goodsNum();
        if (str.equals("LessTime"))
            return LessTime();
        if (str.equals("openLv"))
            return openLv();
        if (str.equals("maxNum"))
            return maxNum();
        if (str.equals("freshNum"))
            return freshNum();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("money =" + money() + '\n');
        str += ("contribution =" + contribution() + '\n');
        str += ("num =" + num() + '\n');
        str += ("maxlv =" + maxlv() + '\n');
        str += ("timeInterval =" + timeInterval() + '\n');
        str += ("outType =" + outType() + '\n');
        str += ("addType =" + addType() + '\n');
        str += ("curType =" + curType() + '\n');
        str += ("storeSize =" + storeSize() + '\n');
        str += ("luckVal =" + luckVal() + '\n');
        str += ("notice =" + notice() + '\n');
        str += ("goodsNum =" + goodsNum() + '\n');
        str += ("LessTime =" + LessTime() + '\n');
        str += ("openLv =" + openLv() + '\n');
        str += ("maxNum =" + maxNum() + '\n');
        str += ("freshNum =" + freshNum() + '\n');
        return str;
    }
}