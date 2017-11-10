package gameserver.fighting;

import table.MT_Data_MedalAttribute;
import table.MT_Data_MedalStar;
import table.base.TableManager;

public class FightingMedal {
	public int TableId;
    public int Level;
    public int StarLv;
    public FightingMedal(int tableId, int level, int starLv)
    {
        this.TableId = tableId / 10000 * 10000;
        this.Level = level;
        this.StarLv = starLv;
        BaseData = TableManager.GetInstance().TableMedalAttribute().GetElement(TableId + 1);
        Data = TableManager.GetInstance().TableMedalAttribute().GetElement(TableId + level);
        StarData = TableManager.GetInstance().TableMedalStar().GetElement(TableId + StarLv);
    }
    /// <summary> 勋章基础数据 </summary>
    public MT_Data_MedalAttribute BaseData = null;
    /// <summary> 勋章数据 </summary>
    public MT_Data_MedalAttribute Data = null;
    /// <summary> 勋章星级数据 </summary>
    public MT_Data_MedalStar StarData = null;
}
