package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_LoginMail extends MT_DataBase {
    public static String MD5 = "37409066772ee78f8b163a7d2d402cee";
    private Integer m_nactiveid ;
    /** 活动id */
    public Integer activeid() { return m_nactiveid; }
    public Integer getM_nactiveid() {return m_nactiveid; }
    /** 活动id */
    public Integer ID() { return m_nactiveid; }
    private String m_ssender ;
    /** 邮件发送者 */
    public String sender() { return m_ssender; }
    public String getM_ssender() {return m_ssender; }
    private String m_stitle ;
    /** 邮件标题 */
    public String title() { return m_stitle; }
    public String getM_stitle() {return m_stitle; }
    private String m_scontent ;
    /** 邮件内容 */
    public String content() { return m_scontent; }
    public String getM_scontent() {return m_scontent; }
    private ArrayList<Int2> m_arrayitems = new ArrayList<Int2>();
    /** 物品列表 */
    public ArrayList<Int2> items() { return m_arrayitems; }
    public ArrayList<Int2> getM_arrayitems() {return m_arrayitems; }
    public static MT_Data_LoginMail ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_LoginMail Data = new MT_Data_LoginMail();

        Integer count;
		Data.m_nactiveid = reader.getInt();
		Data.m_ssender = TableUtil.ReadLString(reader, fileName,"sender",Data.ID());
		Data.m_stitle = TableUtil.ReadLString(reader, fileName,"title",Data.ID());
		Data.m_scontent = TableUtil.ReadLString(reader, fileName,"content",Data.ID());
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arrayitems.add(Int2.ReadMemory(reader, fileName));
        }

		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nactiveid)) return false;
        if (!TableUtil.IsInvalid(this.m_ssender)) return false;
        if (!TableUtil.IsInvalid(this.m_stitle)) return false;
        if (!TableUtil.IsInvalid(this.m_scontent)) return false;
        if (this.m_arrayitems.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("activeid"))
            return activeid();
        if (str.equals("sender"))
            return sender();
        if (str.equals("title"))
            return title();
        if (str.equals("content"))
            return content();
        if (str.equals("items"))
            return items();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("activeid =" + activeid() + '\n');
        str += ("sender =" + sender() + '\n');
        str += ("title =" + title() + '\n');
        str += ("content =" + content() + '\n');
        str += ("items =" + items() + '\n');
        return str;
    }
}