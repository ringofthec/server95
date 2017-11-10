package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_GMusic extends MT_DataBase {
    public static String MD5 = "f6c5f6985369a9dd3c488a8b6563c4e5";
    private Integer m_nmusicKey ;
    /** 音效key值 */
    public Integer musicKey() { return m_nmusicKey; }
    public Integer getM_nmusicKey() {return m_nmusicKey; }
    /** 音效key值 */
    public Integer ID() { return m_nmusicKey; }
    private String m_smusicDesc ;
    /** 音效描述 */
    public String musicDesc() { return m_smusicDesc; }
    public String getM_smusicDesc() {return m_smusicDesc; }
    private ArrayList<String> m_arraymusicPath = new ArrayList<String>();
    /** 音效地址 */
    public ArrayList<String> musicPath() { return m_arraymusicPath; }
    public ArrayList<String> getM_arraymusicPath() {return m_arraymusicPath; }
    public static MT_Data_GMusic ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_GMusic Data = new MT_Data_GMusic();

        Integer count;
		Data.m_nmusicKey = reader.getInt();
		Data.m_smusicDesc = TableUtil.ReadString(reader);
		
        count = reader.getInt();
        for (Integer i = 0;i < count; ++i) 
        {
            Data.m_arraymusicPath.add(TableUtil.ReadString(reader));
        }

		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nmusicKey)) return false;
        if (!TableUtil.IsInvalid(this.m_smusicDesc)) return false;
        if (this.m_arraymusicPath.size() > 0) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("musicKey"))
            return musicKey();
        if (str.equals("musicDesc"))
            return musicDesc();
        if (str.equals("musicPath"))
            return musicPath();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("musicKey =" + musicKey() + '\n');
        str += ("musicDesc =" + musicDesc() + '\n');
        str += ("musicPath =" + musicPath() + '\n');
        return str;
    }
}