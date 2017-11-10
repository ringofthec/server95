package gameserver.fighting;

import gameserver.fighting.clock.OnTimeCallBack;
import gameserver.fighting.clock.TimeCallBack;
import gameserver.fighting.creature.CreatureObject;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Float3;
import table.MT_Data_Skill;
import table.base.TABLE;
import table.base.TableManager;

import com.commons.util.GridPoint;

public class SkillEffectManager {
	private static final Logger logger = LoggerFactory.getLogger(SkillEffectManager.class);
	private FightingManager m_FightingManager;
	public SkillEffectManager(FightingManager fightingManager)
	{
		m_FightingManager = fightingManager;
	}
	public void Shutdown() {
		m_FightingManager = null;
	}
    /// <summary>
    /// 技能目标类型
    /// </summary>
    public enum SKILL_TARGET_TYPE
    {
        /// <summary>
        /// 一个单位
        /// </summary>
        CREATURE,
        /// <summary>
        /// 一个坐标
        /// </summary>
        POSITION,
    }
    /// <summary>
    /// 技能类型
    /// </summary>
    public enum SKILLEFFECT_TYPE
    {
        /// <summary>
        /// 普通攻击
        /// </summary>
        ATTACK,
        /// <summary>
        /// 普通效果 添加一个特效  只有显示 没有意义
        /// </summary>
        NORMAL,
        /// <summary>
        /// 飞行球特效
        /// </summary>
        FLYBALL,
    }
    private class EffectData
    {
        public CreatureObject fireCreature;	    //释放者
        public SKILL_TARGET_TYPE targetType;    //技能目标类型
        public CreatureObject targetCreature;	//目标
        public GridPoint position;              //目标坐标
        public Vector2 offset;                  //导弹位置偏移
        public MT_Data_Skill skill;             //释放的技能
        public Float3 val;				        //SKILLEFFECT 一个单元格数据
    }
    private IEffect CreateSkillEffect(SKILLEFFECT_TYPE Id)
    {
    	if (Id == SKILLEFFECT_TYPE.FLYBALL || Id == SKILLEFFECT_TYPE.NORMAL)
    		return new FlyballEffect();
        return null;
    }
  //触发一个技能动作回调
    public void fireAnimationEvent(CreatureObject creature, GridPoint position, int skillId)
    {
        fireAnimationEvent_impl(SKILL_TARGET_TYPE.POSITION, creature, null, position, Vector2.zero(), skillId);
    }
    //触发一个技能动作回调
    public void fireAnimationEvent(CreatureObject creature, GridPoint position, Vector2 offset, int skillId)
    {
        fireAnimationEvent_impl(SKILL_TARGET_TYPE.POSITION, creature, null, position, offset, skillId);
    }
    //触发一个技能动作回调
    public void fireAnimationEvent(CreatureObject creature, CreatureObject target, int skillId)
    {
        fireAnimationEvent_impl(SKILL_TARGET_TYPE.CREATURE, creature, target, GridPoint.zero(), Vector2.zero(), skillId);
    }
    //触发一个技能动作回调
    public void fireAnimationEvent(CreatureObject creature, CreatureObject target, Vector2 offset, int skillId)
    {
        fireAnimationEvent_impl(SKILL_TARGET_TYPE.CREATURE, creature, target, GridPoint.zero(), offset, skillId);
    }
    void fireAnimationEvent_impl(SKILL_TARGET_TYPE type, CreatureObject creature, CreatureObject target, GridPoint position, Vector2 offset, int skillId)
    {
        if (creature == null) return;
        MT_Data_Skill skillData = TableManager.GetInstance().TableSkill().GetElement(skillId);
        if (skillData == null)
        {
        	logger.error("skill id is null {0}", skillId);
            return;
        }
        List<Float3> animationData = skillData.SkillEvent();
        for (int i = 0; i < animationData.size(); ++i)
        {
            Float3 val = animationData.get(i);
            EffectData data = new EffectData();
            data.fireCreature = creature;
            data.targetType = type;
            data.targetCreature = target;
            data.position = position;
            data.offset = offset;
            data.skill = skillData;
            data.val = val;
            if (TABLE.IsInvalid(val.field1()) || val.field1() <= 0)
            {
                OnAnimationSpace(data);
            }
            else
            {
            	m_FightingManager.ClockManager.addGameTimeCallBack(val.field1(), new OnTimeCallBack() {
					@Override
					public void run(TimeCallBack timeCallBack, Object[] args) {
						((SkillEffectManager)args[0]).OnAnimationSpace((EffectData)args[1]);
					}
				}, this, data);
            }
        }
    }
    //动作回调 关键帧 时间回调
    void OnAnimationSpace(EffectData data)
    {
        IEffect effect = CreateSkillEffect(SKILLEFFECT_TYPE.values()[((Float)data.val.field3()).intValue()]);
        if (effect == null) return;
        effect.fireEffect(m_FightingManager,((Float)data.val.field2()).intValue(), 0, data.fireCreature, data.targetType, data.targetCreature, data.position, data.offset, data.skill);
    }
}
