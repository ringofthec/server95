package gameserver.fighting.creature;

import gameserver.fighting.FightingManager;
import gameserver.fighting.stats.STATS_ENUM;

import commonality.ATTACK_TYPE;
import commonality.CAMP_ENUM;
import commonality.CREATURE_ENUM;

public class CreatureCastle extends Creature {
	
    /// <summary> 城堡名字 </summary>
    public String Name;
    /// <summary> 城堡等级 </summary>
    public int Level;
    /// <summary> 城堡功勋(只有PVP有用到) </summary>
    public int Feat;
    /// <summary> 头像 </summary>
    public String Head;
	
    protected void Die_impl()
    {
    	m_FightingManager.OverFighting(Camp);
    }
    protected void ChangeHp_impl(int value, int curhp,int maxhp)
    {

    }
    public void Initialize(int x, int y, int hp, CAMP_ENUM camp, FightingManager fightingManager, String name, int level, int feat, String head)
    {
    	this.Name = name;
    	this.Level = level;
    	this.Feat = feat;
    	this.Head = head;
    	
        mBaseStats.SetValue(STATS_ENUM.ATTACK_TYPE, ATTACK_TYPE.HERO.Number());
        mBaseStats.SetValue(STATS_ENUM.ARMOR_TYPE, ATTACK_TYPE.HERO.Number());
        super.Initialize_impl(x, y, 1, fightingManager.Terrain.GetMaxY(), hp, camp, CREATURE_ENUM.CASTLE, fightingManager);
    }
    public void StartFighting()
    {

    }
    public void OverFighting(CAMP_ENUM camp)
    {

    }
}
