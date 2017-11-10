package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_Prompt extends MT_DataBase {
    public static String MD5 = "f7581dd366bd59cf880bf70d26a76e6f";
    private Integer m_nindex ;
    /** 索引 */
    public Integer index() { return m_nindex; }
    public Integer getM_nindex() {return m_nindex; }
    /** 索引 */
    public Integer ID() { return m_nindex; }
    private Integer m_ntype ;
    /** 类型 */
    public Integer type() { return m_ntype; }
    public Integer getM_ntype() {return m_ntype; }
    private Integer m_ncanMutli ;
    /** 是否可重复显示 */
    public Integer canMutli() { return m_ncanMutli; }
    public Integer getM_ncanMutli() {return m_ncanMutli; }
    private Integer m_ncloseType ;
    /** 关闭按钮是否可用 */
    public Integer closeType() { return m_ncloseType; }
    public Integer getM_ncloseType() {return m_ncloseType; }
    private String m_sTitle ;
    /** 标题 */
    public String Title() { return m_sTitle; }
    public String getM_sTitle() {return m_sTitle; }
    private String m_stext ;
    /** 内容 */
    public String text() { return m_stext; }
    public String getM_stext() {return m_stext; }
    public static MT_Data_Prompt ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_Prompt Data = new MT_Data_Prompt();
		Data.m_nindex = reader.getInt();
		Data.m_ntype = reader.getInt();
		Data.m_ncanMutli = reader.getInt();
		Data.m_ncloseType = reader.getInt();
		Data.m_sTitle = TableUtil.ReadLString(reader, fileName,"Title",Data.ID());
		Data.m_stext = TableUtil.ReadLString(reader, fileName,"text",Data.ID());
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nindex)) return false;
        if (!TableUtil.IsInvalid(this.m_ntype)) return false;
        if (!TableUtil.IsInvalid(this.m_ncanMutli)) return false;
        if (!TableUtil.IsInvalid(this.m_ncloseType)) return false;
        if (!TableUtil.IsInvalid(this.m_sTitle)) return false;
        if (!TableUtil.IsInvalid(this.m_stext)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("index"))
            return index();
        if (str.equals("type"))
            return type();
        if (str.equals("canMutli"))
            return canMutli();
        if (str.equals("closeType"))
            return closeType();
        if (str.equals("Title"))
            return Title();
        if (str.equals("text"))
            return text();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("index =" + index() + '\n');
        str += ("type =" + type() + '\n');
        str += ("canMutli =" + canMutli() + '\n');
        str += ("closeType =" + closeType() + '\n');
        str += ("Title =" + Title() + '\n');
        str += ("text =" + text() + '\n');
        return str;
    }
}