package gameserver.fighting.creature;

import gameserver.fighting.CorpsSet;
import gameserver.fighting.Vector2;

import com.commons.util.GridPoint;
import commonality.CAMP_ENUM;

public class CreatureAirSupportHorizontal extends CreatureAirSupport {
    private int m_FireIndex = 0;
    private boolean m_Fire = false;
	@Override
	protected void Initialize_impl() {
		// TODO Auto-generated method stub
        m_FireIndex = 0;
        Vector2 pos = m_FightingManager.Terrain.GetPositionByRanks(new GridPoint(m_FireIndex, m_Target.y), true);
        m_FirePosition = pos.x + m_FightingManager.Terrain.GetGridWidth() * AirData.PreDistance() * (Camp == CAMP_ENUM.LEFT ? -1 : 1);
	}

	@Override
	protected void Update_impl() {
		// TODO Auto-generated method stub
		if (m_FireIndex < m_FightingManager.Terrain.GetMaxX() && CanFire())
        {
            CorpsSet corpsSet = (Camp == CAMP_ENUM.LEFT ? m_FightingManager.RightCorps : m_FightingManager.LeftCorps);
            Creature creature = corpsSet.GetElementByCurrent(m_FireIndex, m_Target.y);
            if (creature != null && !creature.IsDie)
            {
                m_Fire = true;
                m_FightingManager.SkillEffectManager.fireAnimationEvent(this, creature, AirData.Skill());
            }
            else if (m_Fire == true)
            {
            	m_FightingManager.SkillEffectManager.fireAnimationEvent(this, new GridPoint(m_FireIndex, m_Target.y), AirData.Skill());
            }
            ++m_FireIndex;
            Vector2 pos = m_FightingManager.Terrain.GetPositionByRanks(new GridPoint(m_FireIndex, m_Target.y), true);
            m_FirePosition = pos.x + m_FightingManager.Terrain.GetGridWidth() * AirData.PreDistance() * (Camp == CAMP_ENUM.LEFT ? -1 : 1);
        }
	}

}
