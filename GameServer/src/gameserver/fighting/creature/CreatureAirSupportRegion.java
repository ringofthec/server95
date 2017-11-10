package gameserver.fighting.creature;

public class CreatureAirSupportRegion extends CreatureAirSupport {
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
            m_FightingManager.SkillEffectManager.fireAnimationEvent(this, m_Target, AirData.Skill());
        }
    }
}
