package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_HeroInforceLv extends MT_DataBase {
    public static String MD5 = "a9f98eae9b5ac9a6d5ef377b7bca7bcb";
    private Integer m_nlevel ;
    /** 强化等级 */
    public Integer level() { return m_nlevel; }
    public Integer getM_nlevel() {return m_nlevel; }
    /** 强化等级 */
    public Integer ID() { return m_nlevel; }
    private float m_fhp ;
    /** 血加成 */
    public float hp() { return m_fhp; }
    public float getM_fhp() {return m_fhp; }
    private float m_fatk ;
    /** 攻击加成 */
    public float atk() { return m_fatk; }
    public float getM_fatk() {return m_fatk; }
    private float m_fdef ;
    /** 防御加成 */
    public float def() { return m_fdef; }
    public float getM_fdef() {return m_fdef; }
    public static MT_Data_HeroInforceLv ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_HeroInforceLv Data = new MT_Data_HeroInforceLv();
		Data.m_nlevel = reader.getInt();
		Data.m_fhp = reader.getFloat();
		Data.m_fatk = reader.getFloat();
		Data.m_fdef = reader.getFloat();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nlevel)) return false;
        if (!TableUtil.IsInvalid(this.m_fhp)) return false;
        if (!TableUtil.IsInvalid(this.m_fatk)) return false;
        if (!TableUtil.IsInvalid(this.m_fdef)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("level"))
            return level();
        if (str.equals("hp"))
            return hp();
        if (str.equals("atk"))
            return atk();
        if (str.equals("def"))
            return def();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("level =" + level() + '\n');
        str += ("hp =" + hp() + '\n');
        str += ("atk =" + atk() + '\n');
        str += ("def =" + def() + '\n');
        return str;
    }
}