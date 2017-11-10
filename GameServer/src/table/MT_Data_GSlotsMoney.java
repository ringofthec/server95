package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GSlotsMoney extends MT_DataBase {
    public static String MD5 = "c327cfe9412b5d0185a02780bb93af05";
    private Integer m_nid ;
    /** id */
    public Integer id() { return m_nid; }
    public Integer getM_nid() {return m_nid; }
    /** id */
    public Integer ID() { return m_nid; }
    private Integer m_ngameid ;
    /** 游戏 */
    public Integer gameid() { return m_ngameid; }
    public Integer getM_ngameid() {return m_ngameid; }
    private Integer m_nlevelid ;
    /** 等级 */
    public Integer levelid() { return m_nlevelid; }
    public Integer getM_nlevelid() {return m_nlevelid; }
    private ArrayList<Integer> m_arraymoneys = new ArrayList<Integer>();
    /** 单线押注金币数量 */
    public ArrayList<Integer> moneys() { return m_arraymoneys; }
    public ArrayList<Integer> getM_arraymoneys() {return m_arraymoneys; }
    private ArrayList<Integer> m_arraylines = new ArrayList<Integer>();
    /** 可押注的线数 */
    public ArrayList<Integer> lines() { return m_arraylines; }
    public ArrayList<Integer> getM_arraylines() {return m_arraylines; }
    public static MT_Data_GSlotsMoney ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GSlotsMoney Data = new MT_Data_GSlotsMoney();

        Integer count;
		Data.m_nid = reader.getInt();
		Data.m_ngameid = reader.getInt();
		Data.m_nlevelid = reader.getInt();
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arraymoneys.add(reader.getInt());
        }

		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arraylines.add(reader.getInt());
        }

		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nid)) return false;
        if (!TableUtil.IsInvalid(this.m_ngameid)) return false;
        if (!TableUtil.IsInvalid(this.m_nlevelid)) return false;
        if (this.m_arraymoneys.size() > 0) return false;
        if (this.m_arraylines.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("id"))
            return id();
        if (str.equals("gameid"))
            return gameid();
        if (str.equals("levelid"))
            return levelid();
        if (str.equals("moneys"))
            return moneys();
        if (str.equals("lines"))
            return lines();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("id =" + id() + '\n');
        str += ("gameid =" + gameid() + '\n');
        str += ("levelid =" + levelid() + '\n');
        str += ("moneys =" + moneys() + '\n');
        str += ("lines =" + lines() + '\n');
        return str;
    }
}