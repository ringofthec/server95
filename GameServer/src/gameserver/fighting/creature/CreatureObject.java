package gameserver.fighting.creature;

import gameserver.fighting.FightingManager;
import gameserver.fighting.GameTerrain;

import commonality.CAMP_ENUM;
import commonality.CREATURE_ENUM;

public class CreatureObject extends FightingObject {
    /// <summary> 单位阵营 </summary>
    public CAMP_ENUM Camp;
    /// <summary> 单位类型 士兵还是城堡 </summary>
    public CREATURE_ENUM Type;
    /// <summary> 战斗管理 </summary>
    protected FightingManager m_FightingManager;
  /// <summary> 所在地形 </summary>
    protected GameTerrain Terrain;
    /// <summary> 设置属性 </summary>
    protected void Initialize_impl(CAMP_ENUM camp, CREATURE_ENUM type, FightingManager fightingManager)
    {
        Camp = camp;
        Type = type;
        m_FightingManager = fightingManager;
        Terrain = m_FightingManager.Terrain;
    }
}
