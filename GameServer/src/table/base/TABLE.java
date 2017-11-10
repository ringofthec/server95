package table.base;
import commonality.Common;

import table.MT_DataBase;
import table.TableUtil;
public class TABLE {
    public static boolean IsInvalid(Boolean val)
    {
    	return TableUtil.IsInvalid(val);
    }
    public static boolean IsInvalid(Byte val)
    {
    	return TableUtil.IsInvalid(val);
    }
    public static boolean IsInvalid(Short val)
    {
    	return TableUtil.IsInvalid(val);
    }
    public static boolean IsInvalid(Integer val)
    {
    	return TableUtil.IsInvalid(val);
    }
    public static boolean IsInvalid(Long val)
    {
    	return TableUtil.IsInvalid(val);
    }
    public static boolean IsInvalid(Float val)
    {
    	return TableUtil.IsInvalid(val);
    }
    public static boolean IsInvalid(Double val)
    {
    	return TableUtil.IsInvalid(val);
    }
    public static boolean IsInvalid(String val)
    {
    	return TableUtil.IsInvalid(val);
    }
    public static <T extends MT_DataBase> boolean IsInvalid(T val)
    {
    	return TableUtil.IsInvalid(val);
    }
    public enum ACTIONS_TYPE	//行为类型
    {
        MOVE,			//移动
        ATTACK,			//攻击
        BEATEN,		    //免疫
        NODIE,			//不能死亡
    }
    /// <summary>
    /// 空中支援目标类型
    /// </summary>
    public enum AIR_SUPPORT_TARGET
    {
        /// <summary>
        /// 一横排
        /// </summary>
        HORIZONTAL,
        /// <summary>
        /// 一纵排
        /// </summary>
        VERTICAL,
        /// <summary>
        /// 区域轰炸
        /// </summary>
        REGION,
    }
    public static boolean IsIntensify(int type)
    {
        return (type == Common.ATTRENUM.Attack.Number() || type == Common.ATTRENUM.Defense.Number() || type == Common.ATTRENUM.Hp.Number());
    }
}
