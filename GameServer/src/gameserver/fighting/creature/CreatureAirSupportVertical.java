package gameserver.fighting.creature;

import com.commons.util.GridPoint;

public class CreatureAirSupportVertical extends CreatureAirSupport {
	private boolean m_Fire = false;
    protected void Initialize_impl()
    {
    	m_Fire = false;
    }
    protected void Update_impl()
    {
        if (m_Fire == false && CanFire())
        {
            m_Fire = true;
            for (int i = 0; i < m_FightingManager.Terrain.GetMaxY(); ++i)
            {
            	m_FightingManager.SkillEffectManager.fireAnimationEvent(this, new GridPoint(m_Target.x, i), AirData.Skill());
            }
        }
    }
}
