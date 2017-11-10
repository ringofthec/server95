package gameserver.fighting;

import gameserver.fighting.SkillEffectManager.SKILL_TARGET_TYPE;
import gameserver.fighting.creature.Creature;
import gameserver.fighting.creature.CreatureObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_IEffect;
import table.MT_Data_Skill;
import table.base.TableManager;

import com.commons.util.GridPoint;
import commonality.CREATURE_ENUM;

public abstract class IEffect {
	private static final Logger logger = LoggerFactory.getLogger(IEffect.class);
	protected FightData mData = new FightData();                 //表数据
	protected CreatureObject mFireCreature, mTargetCreature;	 //释放者 目标
	protected SKILL_TARGET_TYPE mTargetType;                     //目标类型
	protected GridPoint mTargetPosition;                         //目标坐标
	protected FightingManager m_FightingManager;
	//触发一个 IEFFECT
	public void fireEffect(FightingManager fightManager,int effectId, float delay, CreatureObject fireCreature, SKILL_TARGET_TYPE targetType, CreatureObject targetCreature, GridPoint targetPosition, Vector2 offset, MT_Data_Skill skill)
	{
		m_FightingManager = fightManager;
		if (fireCreature == null)
		{
			logger.error("fireCreature is null");
			return;
		}
		MT_Data_IEffect data = TableManager.GetInstance().TableIEffect().GetElement(effectId);
		if (data == null)
		{
	       logger.error("IEffect is null  {0}", effectId);
           return;
		}
		mData.effect = data;
		mData.skill = skill;
		mData.offset = offset;
		mFireCreature = fireCreature;
		mTargetType = targetType;
		mTargetCreature = targetCreature;
		mTargetPosition = targetPosition;

       if (mFireCreature instanceof Creature)
       {
           ((Creature)mFireCreature).AddBuffArray(data.BeginAttackerBuff());
       }
       if (targetCreature instanceof Creature)
       {
    	   m_FightingManager.AddBeginBuff((Creature)targetCreature, mData);
       }
       if (mFireCreature.Type == CREATURE_ENUM.HERO)
           m_FightingManager.AddHeroEquipBuff(mFireCreature, targetCreature, 1);
       OnDelay();
	}
	//IEFFECT延迟调用
	void OnDelay()
	{
       fireEffect_impl();
	}
	protected abstract void fireEffect_impl();
}
