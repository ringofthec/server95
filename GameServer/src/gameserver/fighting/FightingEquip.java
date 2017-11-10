package gameserver.fighting;

import java.util.ArrayList;
import java.util.List;

import table.Int2;
import table.MT_Data_EquipAttribute;
import table.MT_Data_IntensifyConfig;
import table.MT_Data_Item;
import table.base.TableManager;

public class FightingEquip {
	public int TableId;
    public int Level;
    public MT_Data_Item ItemData;
    public MT_Data_EquipAttribute AttrData;

    private List<Int2> m_Value = new ArrayList<Int2>();

    @SuppressWarnings("unchecked")
	public FightingEquip(int tableId, int level) throws Exception
    {
        this.TableId = tableId;
        this.Level = level;
        this.ItemData = TableManager.GetInstance().TableItem().GetElement(tableId);
        if (ItemData != null)
            this.AttrData = TableManager.GetInstance().TableEquipAttribute().GetElement(ItemData.Attr());

        if (level > 0)
        {
            MT_Data_IntensifyConfig config = TableManager.GetInstance().TableIntensifyConfig().GetElement(tableId);
            if (config == null)
                return;

            m_Value = (List<Int2>)config.GetDataByString("Intensify_" + (level - 1));
        }
    }

    public int GetIntensifyValue(int key)
    {
        if (key > 1)
            return 0;

        for (Int2 value : m_Value)
        {
            if (value.field1() == key)
                return value.field2();
        }

        return 0;
    }
}
