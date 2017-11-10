package table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unused")
public class MT_Data_HeroInforce extends MT_DataBase {
    public static String MD5 = "2c97a85b68ab0e4d56131bcb0c73e3e5";
    private Integer m_nstar ;
    /** 英雄星级 */
    public Integer star() { return m_nstar; }
    public Integer getM_nstar() {return m_nstar; }
    /** 英雄星级 */
    public Integer ID() { return m_nstar; }
    private Integer m_nstarrate ;
    /** 升星成功概率 */
    public Integer starrate() { return m_nstarrate; }
    public Integer getM_nstarrate() {return m_nstarrate; }
    private Integer m_nstaritem ;
    /** 升星英雄之魂消耗 */
    public Integer staritem() { return m_nstaritem; }
    public Integer getM_nstaritem() {return m_nstaritem; }
    private Integer m_nluckmax ;
    /** 幸运值上限 */
    public Integer luckmax() { return m_nluckmax; }
    public Integer getM_nluckmax() {return m_nluckmax; }
    private Integer m_nlvlrate ;
    /** 强化成功概率 */
    public Integer lvlrate() { return m_nlvlrate; }
    public Integer getM_nlvlrate() {return m_nlvlrate; }
    private Integer m_nhplimit ;
    /** 血等级上限 */
    public Integer hplimit() { return m_nhplimit; }
    public Integer getM_nhplimit() {return m_nhplimit; }
    private Integer m_nattacklimit ;
    /** 攻击等级上限 */
    public Integer attacklimit() { return m_nattacklimit; }
    public Integer getM_nattacklimit() {return m_nattacklimit; }
    private Integer m_ndefenlimit ;
    /** 防御等级上限 */
    public Integer defenlimit() { return m_ndefenlimit; }
    public Integer getM_ndefenlimit() {return m_ndefenlimit; }
    private Integer m_nlvlmoney ;
    /** 升级消耗金币 */
    public Integer lvlmoney() { return m_nlvlmoney; }
    public Integer getM_nlvlmoney() {return m_nlvlmoney; }
    private Integer m_nlvlitem ;
    /** 强化消耗英雄印记 */
    public Integer lvlitem() { return m_nlvlitem; }
    public Integer getM_nlvlitem() {return m_nlvlitem; }
    private float m_fHp ;
    /** 生命值 */
    public float Hp() { return m_fHp; }
    public float getM_fHp() {return m_fHp; }
    private float m_fAtk ;
    /** 攻击 */
    public float Atk() { return m_fAtk; }
    public float getM_fAtk() {return m_fAtk; }
    private float m_fDef ;
    /** 防御 */
    public float Def() { return m_fDef; }
    public float getM_fDef() {return m_fDef; }
    public static MT_Data_HeroInforce ReadMemory ( ByteBuffer reader, String fileName ) {
        MT_Data_HeroInforce Data = new MT_Data_HeroInforce();
		Data.m_nstar = reader.getInt();
		Data.m_nstarrate = reader.getInt();
		Data.m_nstaritem = reader.getInt();
		Data.m_nluckmax = reader.getInt();
		Data.m_nlvlrate = reader.getInt();
		Data.m_nhplimit = reader.getInt();
		Data.m_nattacklimit = reader.getInt();
		Data.m_ndefenlimit = reader.getInt();
		Data.m_nlvlmoney = reader.getInt();
		Data.m_nlvlitem = reader.getInt();
		Data.m_fHp = reader.getFloat();
		Data.m_fAtk = reader.getFloat();
		Data.m_fDef = reader.getFloat();
		return Data;
	}
    @Override
    public boolean IsInvalid () {
        if (!TableUtil.IsInvalid(this.m_nstar)) return false;
        if (!TableUtil.IsInvalid(this.m_nstarrate)) return false;
        if (!TableUtil.IsInvalid(this.m_nstaritem)) return false;
        if (!TableUtil.IsInvalid(this.m_nluckmax)) return false;
        if (!TableUtil.IsInvalid(this.m_nlvlrate)) return false;
        if (!TableUtil.IsInvalid(this.m_nhplimit)) return false;
        if (!TableUtil.IsInvalid(this.m_nattacklimit)) return false;
        if (!TableUtil.IsInvalid(this.m_ndefenlimit)) return false;
        if (!TableUtil.IsInvalid(this.m_nlvlmoney)) return false;
        if (!TableUtil.IsInvalid(this.m_nlvlitem)) return false;
        if (!TableUtil.IsInvalid(this.m_fHp)) return false;
        if (!TableUtil.IsInvalid(this.m_fAtk)) return false;
        if (!TableUtil.IsInvalid(this.m_fDef)) return false;
        return true;
    }
    @Override
    public Object GetDataByString ( String str ) throws Exception {
        if (str.equals("star"))
            return star();
        if (str.equals("starrate"))
            return starrate();
        if (str.equals("staritem"))
            return staritem();
        if (str.equals("luckmax"))
            return luckmax();
        if (str.equals("lvlrate"))
            return lvlrate();
        if (str.equals("hplimit"))
            return hplimit();
        if (str.equals("attacklimit"))
            return attacklimit();
        if (str.equals("defenlimit"))
            return defenlimit();
        if (str.equals("lvlmoney"))
            return lvlmoney();
        if (str.equals("lvlitem"))
            return lvlitem();
        if (str.equals("Hp"))
            return Hp();
        if (str.equals("Atk"))
            return Atk();
        if (str.equals("Def"))
            return Def();
            return null;
    }
    @Override
    public String toString ( ) {
        String str = "";
        str += ("star =" + star() + '\n');
        str += ("starrate =" + starrate() + '\n');
        str += ("staritem =" + staritem() + '\n');
        str += ("luckmax =" + luckmax() + '\n');
        str += ("lvlrate =" + lvlrate() + '\n');
        str += ("hplimit =" + hplimit() + '\n');
        str += ("attacklimit =" + attacklimit() + '\n');
        str += ("defenlimit =" + defenlimit() + '\n');
        str += ("lvlmoney =" + lvlmoney() + '\n');
        str += ("lvlitem =" + lvlitem() + '\n');
        str += ("Hp =" + Hp() + '\n');
        str += ("Atk =" + Atk() + '\n');
        str += ("Def =" + Def() + '\n');
        return str;
    }
}