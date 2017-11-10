package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GCLoginReward extends MT_DataBase {
    public static String MD5 = "f38652875b8af2f151dc9af3bd29ee0d";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private Int2 m_rewards1 ;
    /** 奖品套系1 */
    public Int2 rewards1() { return m_rewards1; }
    public Int2 getM_rewards1() {return m_rewards1; }
    public static MT_Data_GCLoginReward ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GCLoginReward Data = new MT_Data_GCLoginReward();
		Data.m_nindex = reader.getInt();
		Data.m_rewards1 = Int2.ReadMemory(reader, fileName);
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_rewards1)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("rewards1"))
            return rewards1();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("rewards1 =" + rewards1() + '\n');
        return str;
    }
}