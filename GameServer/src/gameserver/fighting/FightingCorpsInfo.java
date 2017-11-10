package gameserver.fighting;

import table.MT_Data_Corps;
import table.base.TableManager;

public class FightingCorpsInfo {
	private int Used;
    // 表ID
    public int TableId;
    // 表数据
    public MT_Data_Corps Data;
    // 等级
    public int Level;
    // 兵数量
    public int Count;
    
	public FightingCorpsInfo(int tableId, int level, int count)
    {
        TableId = tableId;
        Level = level;
        Count = count;
        Data = TableManager.GetInstance().TableCorps().GetElement(TableId + level);
        Reset();
    }
    public boolean CanPlace()
    {
        return (Count > Used);
    }
    public void Reset()
    {
        Used = 0;
    }
    public void Place()
    {
        ++Used;
    }
    public void PickUp()
    {
        --Used;
    }
    public int GetCorpsNumber()
    {
        return Count - Used;
    }
    public void repairCount()
    {
        Count++;
    }
}
