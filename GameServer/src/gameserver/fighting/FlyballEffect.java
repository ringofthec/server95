package gameserver.fighting;

public class FlyballEffect extends IEffect {
	@Override
    protected void fireEffect_impl()
    {
        BulletController.CreateBullet(m_FightingManager, mFireCreature, mTargetType, mTargetCreature, mTargetPosition, mData);
    }
}
