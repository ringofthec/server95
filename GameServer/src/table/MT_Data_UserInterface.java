package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_UserInterface extends MT_DataBase {
    public static String MD5 = "420b0ea7d1e9f1404ea95eeecd7e895c";
    private String m_sPath ;
    /** 所在UI+Obj路径 */
    public String Path() { return m_sPath; }
    public String getM_sPath() {return m_sPath; }
    /** 所在UI+Obj路径 */
    public String ID() { return m_sPath; }
    private String m_sText ;
    /** 文字 */
    public String Text() { return m_sText; }
    public String getM_sText() {return m_sText; }
    public static MT_Data_UserInterface ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_UserInterface Data = new MT_Data_UserInterface();
		Data.m_sPath = TableUtil.ReadString(reader);
		Data.m_sText = TableUtil.ReadLString(reader, fileName,"Text",Data.ID());
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_sPath)) return false;
        if (!TableUtil.IsInvalid(this.m_sText)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("Path"))
            return Path();
        if (str.equals("Text"))
            return Text();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("Path =" + Path() + '\n');
        str += ("Text =" + Text() + '\n');
        return str;
    }
}