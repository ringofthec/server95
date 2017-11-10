package gameserver.fighting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import commonality.Common;
import table.EquipAttr;
import table.Float3;
import table.MT_Data_EquipAttribute;
import table.MT_Data_Hero;
import table.MT_Data_HeroInforce;
import table.MT_Data_IEffect;
import table.MT_Data_Item;
import table.MT_Data_MedalAttribute;
import table.MT_Data_MedalStar;
import table.MT_Data_Skill;
import table.base.TABLE;
import table.base.TableManager;

public class FightingHeroInfo {
	// 装备列表
	private List<FightingEquip> m_Equips = new ArrayList<FightingEquip>();
	public List<FightingEquip> Equips() { return m_Equips; }
	// 勋章列表
	private HashMap<Integer, FightingMedal> m_Medals = new HashMap<Integer, FightingMedal>();
	public HashMap<Integer, FightingMedal> Medals() {
		return m_Medals;
	}
	// 英雄技能列表
	private List<MT_Data_IEffect> m_Skills = new ArrayList<MT_Data_IEffect>();
	public List<MT_Data_IEffect> Skills() { return m_Skills; }
	// 
	public int TableId;
	// 英雄Data
	public MT_Data_Hero Data;
	// 是否上阵
	public boolean Used;
	
	/// <summary> 生命等级 </summary>
    public int HpLevel;
    /// <summary> 生命添加的值 </summary>
    public int HpValue;
    /// <summary> 攻击等级 </summary>
    public int AtkLevel;
    /// <summary> 攻击添加的值 </summary>
    public int AtkValue;
    /// <summary> 防御等级 </summary>
    public int DefLevel;
    /// <summary> 防御添加的值 </summary>
    public int DefValue;
    /// <summary> 星级 </summary>
    public int Star;
    /// <summary> 当前星级数据 </summary>
    public MT_Data_HeroInforce StarData;
	
	public FightingHeroInfo(int tableId, int star, int hpLv, int atkLv, int defLv, List<FightingEquip> equips, HashMap<Integer, FightingMedal> medals) {
		TableId = tableId;
		Star = star;
        HpLevel = hpLv;
        AtkLevel = atkLv;
        DefLevel = defLv;
		m_Equips = equips;
		m_Medals = medals;
		Data = TableManager.GetInstance().TableHero().GetElement(TableId);
        this.StarData = TableManager.GetInstance().TableHeroInforce().GetElement(TableId * 10000 + Star);
        HpValue = (int)(TableManager.GetInstance().TableHeroInforceLv().GetElement(tableId * 10000 + hpLv).hp());
        AtkValue = (int)(TableManager.GetInstance().TableHeroInforceLv().GetElement(tableId * 10000 + atkLv).atk());
        DefValue = (int)(TableManager.GetInstance().TableHeroInforceLv().GetElement(tableId * 10000 + defLv).def());
		GetSkills();
		Reset();
	}
	/* 获得技能列表 */
	private void GetSkills()
    {
        for (FightingEquip temp : m_Equips) {
        	MT_Data_Item item = temp.ItemData;
            int attrId = item.Attr();
            if (TABLE.IsInvalid(attrId))
                continue;
            MT_Data_EquipAttribute data = TableManager.GetInstance().TableEquipAttribute().GetElement(attrId);
            for (EquipAttr pair : data.Arrays()) {
                if (pair.type().equals(3)) {
                    MT_Data_Skill skill = TableManager.GetInstance().TableSkill().GetElement(pair.num());
                    for (Float3 effect : skill.SkillEvent()) {
                        m_Skills.add(TableManager.GetInstance().TableIEffect().GetElement((int)effect.field2()));
                    }
                }
            }
        }
    }
	/* 获得勋章加成 */
    public int2 GetMedalAttribute(Common.ATTRENUM type) throws Exception
    {
        int2 value = new int2(0, 0);
        for (FightingMedal pair : m_Medals.values())
        {
            MT_Data_MedalStar starData = pair.StarData;
            MT_Data_MedalAttribute data = pair.BaseData;
        	EquipAttr attr = (EquipAttr)data.GetDataByString("Attr" + type.Number());
            if (TABLE.IsInvalid(attr) == false)
            {
            	int level = data.index() % 10000;
                if (attr.type() == 0)
                    value.field1 += attr.num() + (level - 1) * starData.growing();
                else
                    value.field2 += attr.num() + (level - 1) * starData.growing();
            }
        }
        return value;
    }
    /* 获得装备加成 */
    public int2 GetEquipAttribute(Common.ATTRENUM type)
    {
        int2 val = new int2(0, 0);
        int index = type.Number();
        for (FightingEquip pair : m_Equips)
        {
        	MT_Data_EquipAttribute attr = pair.AttrData;
            if (index < 0 || index > Common.ATTRENUM.Critical.Number() || index >= attr.Arrays().size())
                continue;
            EquipAttr info = attr.Arrays().get(index);
            if (!TABLE.IsInvalid(info))
            {
                if (info.type().equals(0))
                	if (TABLE.IsIntensify(type.Number()))
                        val.field1 += info.num() + pair.GetIntensifyValue(type.Number());
                    else
                        val.field1 += info.num();
                else
                    val.field2 += info.num();
            }
        }
        return val;
    }
    public boolean CanPlace() {
        return !Used;
    }
	public void Reset() {
        Used = false;
    }
	public void Place() {
        Used = true;
    }
    public void PickUp() {
        Used = false;
    }
}
