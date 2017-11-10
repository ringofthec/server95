package gameserver.fighting.creature;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.util.RandomUtil;

import gameserver.fighting.CorpsSet;
import gameserver.fighting.FightingHeroInfo;
import gameserver.fighting.FightingManager;
import gameserver.fighting.int2;
import gameserver.fighting.stats.FightUtil;
import gameserver.fighting.stats.STATS_ENUM;
import table.Float2;
import table.Int2;
import table.Int3;
import table.MT_DataBase;
import table.MT_Data_HeroInforce;
import table.MT_TableEnum;
import table.base.TableManager;
import commonality.ATTACK_TYPE;
import commonality.CAMP_ENUM;
import commonality.CREATURE_ENUM;
import commonality.Common;
import commonality.FIGHTING_STATE;
import commonality.TARGET_ENUM;

public abstract class CreatureCorps extends Creature {
	private static final Logger logger = LoggerFactory.getLogger(CreatureCorps.class);
	private class Attr
    {
        public Common.PASSIVEBUFF_TYPE BuffType;
        public Common.ATTRENUM AttrType;
        public STATS_ENUM StatsType;
        public Attr(Common.PASSIVEBUFF_TYPE buffType, Common.ATTRENUM attrType, STATS_ENUM statsType)
        {
            this.BuffType = buffType;
            this.AttrType = attrType;
            this.StatsType = statsType;
        }
    }
	private float m_AttackSpace = 0;				// 选择目标是 相差距离
	private float m_LastAttackTime = -1;			// 上次攻击时间
	protected Creature m_AttackTarget = null;
    protected Creature m_FinishedTarget = null;
    public List<Creature> m_AttackCreatures = new ArrayList<Creature>();		//攻击目标
    public List<Creature> m_MoveCreatures = new ArrayList<Creature>();			//移动目标
    public boolean Invite;		// 是否邀请
    //攻击距离(加上偏移之后的距离)
    private float AttackDistance;
    //可以攻击距离(没有计算偏移的距离)
    private float AttackDistanceBase;
    
    public int GetSkill() throws Exception { 
    	return (int)DataBase().GetDataByString(MT_TableEnum.Skill);
    }
	public int GetAttackType() throws Exception {
		return (int)DataBase().GetDataByString(MT_TableEnum.AttackType);
	}
    private int GetAttackTarget() throws Exception {
    	return (int)DataBase().GetDataByString(MT_TableEnum.AttackTarget);
	}
    public int GetArmorType() throws Exception {
    	return (int)DataBase().GetDataByString(MT_TableEnum.ArmorType);
    }
    public int GetPopulation() throws Exception {
    	return (int)DataBase().GetDataByString(MT_TableEnum.Population);
    }
    public String GetShadowModel() throws Exception {
    	return DataBase().GetDataByString(MT_TableEnum.ShadowModel).toString();
    }
    public Float2 GetShadowScale() throws Exception { 
    	return (Float2)DataBase().GetDataByString(MT_TableEnum.ShadowScale);
    }
    public Float2 GetShadowOffset() throws Exception {
    	return (Float2)DataBase().GetDataByString(MT_TableEnum.ShadowOffset);
    }
    public String GetDieSound() throws Exception {
    	return DataBase().GetDataByString(MT_TableEnum.DieSound).toString();
    }
    public float GetMoveSpeed() {
    	return (float)GetValue(STATS_ENUM.MOVE_SPEED);
    }
    public int Skill() throws Exception {
        return (int)DataBase().GetDataByString(MT_TableEnum.Skill);
    }
    public int AttackTarget() throws Exception {
        return (int)DataBase().GetDataByString(MT_TableEnum.AttackTarget);
    }
    public int AttackDistance() throws Exception {
        return (int)DataBase().GetDataByString(MT_TableEnum.AttackDistance);
    }
    public float MoveSpeed() {
        return (float)GetValue(STATS_ENUM.MOVE_SPEED);
    }
    
    
    public int CorpsId;
    private MT_DataBase m_DataBase = null;
    protected MT_DataBase DataBase() { return m_DataBase; }

    protected void Initialize_impl(int x, int y, int corpsId, MT_DataBase database, CAMP_ENUM camp, CREATURE_ENUM type, FightingManager fightingManager) throws Exception {
        this.CorpsId = corpsId;
        this.Type = type;
        this.m_DataBase = database;
        Int2 size = (Int2)DataBase().GetDataByString(MT_TableEnum.Size);
        Integer hp = (Integer)DataBase().GetDataByString(MT_TableEnum.Hp);
        super.Initialize_impl(x, y, size.field1(), size.field2(), hp, camp, type, fightingManager);
        Initialize_BaseStats();
        Initialize_Stats();
        AttackDistanceBase = (int)DataBase().GetDataByString(MT_TableEnum.AttackDistance) * Terrain.GetGridWidth();
        //增加一点攻击距离 算的太精确 有时会有问题
        AttackDistance = AttackDistanceBase + (Terrain.GetGridWidth() / 5);
        m_LastAttackTime = -1;
        m_AttackSpace = Terrain.GetGridWidth() / 2;
    }
    protected void Initialize_BaseStats() throws Exception
    {
    	int hpValue = 0, atkValue = 0, defValue = 0;
    	if (this.Type == CREATURE_ENUM.HERO)
        {
            FightingHeroInfo heroInfo = null;
            if (Invite)
            {
//                heroInfo = m_FightingManager.InviteHero;
            }
            else if (this.Camp == CAMP_ENUM.LEFT && m_FightingManager.LeftCorps.HeroInfos().containsKey(CorpsId))
                heroInfo = m_FightingManager.LeftCorps.HeroInfos().get(CorpsId);
            else if (this.Camp == CAMP_ENUM.RIGHT && m_FightingManager.RightCorps.HeroInfos().containsKey(CorpsId))
                heroInfo = m_FightingManager.RightCorps.HeroInfos().get(CorpsId);
            if (heroInfo != null)
            {
                hpValue = heroInfo.HpValue;
                atkValue = heroInfo.AtkValue;
                defValue = heroInfo.AtkValue;
                
                if (heroInfo.Star > 0)
                {
                    MT_Data_HeroInforce data = TableManager.GetInstance().TableHeroInforce().GetElement(heroInfo.TableId * 10000 + heroInfo.Star - 1);
                    hpValue += (int)data.Hp();
                    atkValue += (int)data.Atk();
                    defValue += (int)data.Def();
                }
            }
        }
    	int hp = (int)DataBase().GetDataByString(MT_TableEnum.Hp);
    	mBaseStats.SetValue(STATS_ENUM.MAXHP, hp + hpValue);
    	mBaseStats.SetValue(STATS_ENUM.CURHP, hp + hpValue);
    	mBaseStats.SetValue(STATS_ENUM.ATTACK, (int)DataBase().GetDataByString(MT_TableEnum.Attack) + atkValue);
    	mBaseStats.SetValue(STATS_ENUM.ARMOR, (int)DataBase().GetDataByString(MT_TableEnum.Armor) + defValue);
    	mBaseStats.SetValue(STATS_ENUM.ATTACK_TYPE, (int)DataBase().GetDataByString(MT_TableEnum.AttackType));
    	mBaseStats.SetValue(STATS_ENUM.ARMOR_TYPE, (int)DataBase().GetDataByString(MT_TableEnum.ArmorType));
    	mBaseStats.SetValue(STATS_ENUM.CRITICAL, (int)DataBase().GetDataByString(MT_TableEnum.Crit));
    	mBaseStats.SetValue(STATS_ENUM.DODGE, (int)DataBase().GetDataByString(MT_TableEnum.Dodge));
    	mBaseStats.SetValue(STATS_ENUM.MOVE_SPEED, (float)DataBase().GetDataByString(MT_TableEnum.MoveSpeed) * m_FightingManager.Terrain.GetGridWidth());
    	mBaseStats.SetValue(STATS_ENUM.ATTACK_SPEED, (float)DataBase().GetDataByString(MT_TableEnum.AttackSpeed));
    	mBaseStats.SetValue(STATS_ENUM.IMMUNECRIT, (int)DataBase().GetDataByString(MT_TableEnum.ImmuneCrit));
    	mBaseStats.SetValue(STATS_ENUM.HIT, (int)DataBase().GetDataByString(MT_TableEnum.Hit));
        Int3 vampire = (Int3)DataBase().GetDataByString(MT_TableEnum.Vampire);
        mBaseStats.SetValue(STATS_ENUM.VAMPIRE_TYPE, vampire.field1());
        mBaseStats.SetValue(STATS_ENUM.VAMPIRE_ODDS, vampire.field2());
        mBaseStats.SetValue(STATS_ENUM.VAMPIRE, vampire.field3());
    }
    private void Initialize_Stats() throws Exception {
    	CorpsSet ownCorps = Camp == CAMP_ENUM.LEFT ? m_FightingManager.LeftCorps : m_FightingManager.RightCorps;
        CorpsSet enemyCoprs = Camp == CAMP_ENUM.LEFT ? m_FightingManager.RightCorps : m_FightingManager.LeftCorps;
        List<Attr> attributes = new ArrayList<Attr>();
        attributes.add(new Attr(Common.PASSIVEBUFF_TYPE.HP, Common.ATTRENUM.Hp, STATS_ENUM.MAXHP));
        attributes.add(new Attr(Common.PASSIVEBUFF_TYPE.ATTACK, Common.ATTRENUM.Attack, STATS_ENUM.ATTACK));
        attributes.add(new Attr(Common.PASSIVEBUFF_TYPE.DEFEND, Common.ATTRENUM.Defense, STATS_ENUM.ARMOR));
        attributes.add(new Attr(Common.PASSIVEBUFF_TYPE.CRIT, Common.ATTRENUM.Critical, STATS_ENUM.CRITICAL));
        attributes.add(new Attr(Common.PASSIVEBUFF_TYPE.DODGE, Common.ATTRENUM.Dodge, STATS_ENUM.DODGE));
        attributes.add(new Attr(Common.PASSIVEBUFF_TYPE.RESIST_CRIT, Common.ATTRENUM.ImmuneCrit, STATS_ENUM.IMMUNECRIT));
        attributes.add(new Attr(Common.PASSIVEBUFF_TYPE.Hit, Common.ATTRENUM.Hit, STATS_ENUM.HIT));
        for (Attr attr : attributes)
        {
            int2 plus1 = ownCorps.getCreatureValue(Type.Number(), attr.BuffType.Number(), true, CorpsId);
            int2 plus2 = ownCorps.GetHeroMedalAttr(Type, CorpsId, attr.AttrType);
            int2 plus3 = ownCorps.GetHeroEquipAttr(Type, CorpsId, attr.AttrType);
            int2 minus = enemyCoprs.getCreatureValue(Type.Number(), attr.BuffType.Number(), false, CorpsId);
            //作用于对方的BUF 策划填的负值 直接计算就可以了
            mBaseStats.UpdateValue(attr.StatsType, plus1, plus2, plus3, minus);
        }
        mBaseStats.SetValue(STATS_ENUM.CURHP, mBaseStats.GetValue(STATS_ENUM.MAXHP));
        {
            int2 plus = ownCorps.getCreatureValue(Type.Number(), Common.PASSIVEBUFF_TYPE.MOVE_SPEED.Number(), true, CorpsId);
            int2 minus = ownCorps.getCreatureValue(Type.Number(), Common.PASSIVEBUFF_TYPE.MOVE_SPEED.Number(), false, CorpsId);
            float ratio = 1 + (plus.field2 + minus.field2) / 10000f;
            float val = (float)mBaseStats.GetValue(STATS_ENUM.MOVE_SPEED);
            mBaseStats.SetValue(STATS_ENUM.MOVE_SPEED, val * ratio);
        }
        {
            int2 plus = ownCorps.getCreatureValue(Type.Number(), Common.PASSIVEBUFF_TYPE.ATTACK_SPEED.Number(), true, CorpsId);
            int2 minus = ownCorps.getCreatureValue(Type.Number(), Common.PASSIVEBUFF_TYPE.ATTACK_SPEED.Number(), false, CorpsId);
            float ratio = 1 + (plus.field2 + minus.field2) / 10000f;
            float val = (float)mBaseStats.GetValue(STATS_ENUM.ATTACK_SPEED);
            mBaseStats.SetValue(STATS_ENUM.ATTACK_SPEED, val * ratio);
        }
        int2 medalVampire = ownCorps.GetHeroMedalAttr(Type, CorpsId, Common.ATTRENUM.Vampire);
        mBaseStats.SetValue(STATS_ENUM.VAMPIRE_NUM, medalVampire.field1);
        mBaseStats.SetValue(STATS_ENUM.VAMPIRE_RATIO, medalVampire.field2);
        mStats.Set(mBaseStats);
    }
	@Override
	public void OverFighting(CAMP_ENUM camp) 
	{
		
	}
    /// <summary> 判断攻击间隔是否时间到 </summary>
	protected boolean CanAttackSpeed()
	{
		float timeLine = m_FightingManager.UpdateManager.GetTimeLine();
		if (m_LastAttackTime != -1 && timeLine - m_LastAttackTime < (float)GetValue(STATS_ENUM.ATTACK_SPEED))
			return false;
		return true;
	}
    /// <summary> 是否能继续执行AI 每次攻击玩 休息0.4秒不计算任何逻辑 </summary>
    protected boolean CanExecute()
    {
        float timeLine = m_FightingManager.UpdateManager.GetTimeLine();
        if (m_LastAttackTime == -1 || timeLine - m_LastAttackTime > 0.4f)
            return true;
        return false;
    }
    public void OnUpdate(float deltaTime) throws Exception {
        Update_Pre();
        if (m_FightingManager.State != FIGHTING_STATE.FIGHTING || IsDie)
            return;
        if (CanExecute())
        	Update_impl(deltaTime);
    }
    /// <summary> 返回值越小 优先级越高 </summary>
    public int GetAttackPriority(int y) throws Exception
    {
    	TARGET_ENUM attackTarget = TARGET_ENUM.valueOf(GetAttackTarget());
        if (attackTarget == TARGET_ENUM.FRONT_TARGET || attackTarget == TARGET_ENUM.FRONT_TARGET) {
            return m_Rect.bottom() - y;
        } else {
            if (y == m_Rect.bottom() + 1) {
                return 101;
            } else if (y == m_Rect.top() - 1) {
                return 102;
            } else {
                return m_Rect.bottom() - y;
            }
        }
    }
    /// <summary> 获得最新的攻击对象数组(如果已死亡或者已走过自己 就重新获取) </summary>
    private void CheckAttackCreatures() throws Exception
    {
        int count = m_AttackCreatures.size();
        boolean check = (count <= 0);
        for (int i = 0; i < count; ++i)
        {
            Creature creature = m_AttackCreatures.get(i);
            if (creature.IsDie || FightUtil.GetDistancePrimitive(this, creature) < 0)
            {
                check = true;
                break;
            }
        }
        if (check == false) return;
        int attackTarget = GetAttackTarget();
        switch (TARGET_ENUM.valueOf(attackTarget))
        {
            case FRONT_TARGET:
            case FRONT_ALL_TARGET:
                m_AttackCreatures = m_FightingManager.GetTargetCreature(this, false);
                break;
            case FRONT_TARGET_UPDOWN:
                m_AttackCreatures = m_FightingManager.GetTargetCreature(this, true);
                break;
            case FRONT_TEAMMATE:
                m_AttackCreatures = m_FightingManager.GetFrontCreature(this, false);
                break;
            case FRONT_TEAMMATE_UPDOWN:
                m_AttackCreatures = m_FightingManager.GetFrontCreature(this, true);
                break;
            default:
                logger.error("错误的目标类型 {0} - {1}", CorpsId, attackTarget);
                break;
        }
    }
    protected void GetAttackCreature() throws Exception
    {
        CheckAttackCreatures();
        m_AttackTarget = null;
        m_FinishedTarget = null;
        float minDistance = 99999;
        int minPriority = 99999;
        int count = m_AttackCreatures.size();
        for (int i = 0; i < count; ++i)
        {
            Creature creature = m_AttackCreatures.get(i);
            float distance = FightUtil.GetDistance(this, creature);
            if (distance <= AttackDistanceBase)
                m_AttackTarget = creature;
            if (distance <= AttackDistance)
            {
                int priority = GetAttackPriority(creature.BeginY());
                if (distance < minDistance - m_AttackSpace || (priority < minPriority && Math.abs(distance - minDistance) < m_AttackSpace))
                {
                    minPriority = priority;
                    minDistance = Math.min(distance, minDistance);
                    m_FinishedTarget = creature;
                }
            }
            creature.updateBuff();
        }
    }
    //攻击一个目标
    protected boolean Attack() throws Exception
    {
    	if (!CanAttackSpeed())
    		return true;
        GetAttackCreature();
        if (m_AttackTarget == null) return false;
        return Attack_impl();
    }
    protected boolean Attack_impl() throws Exception
    {
        if (m_FinishedTarget == null) return false;
        if (GetAttackType() == ATTACK_TYPE.TREAT.Number())
        {
            int curHp = (int)m_FinishedTarget.GetValue(STATS_ENUM.CURHP);
            int maxHp = (int)m_FinishedTarget.GetValue(STATS_ENUM.MAXHP);
            if (curHp >= maxHp)
                return true;
        }
        float timeLine = m_FightingManager.UpdateManager.GetTimeLine();
        m_LastAttackTime = timeLine + RandomUtil.RangeRandom(0, 0.3f);
        
        TARGET_ENUM attackTarget = TARGET_ENUM.valueOf(GetAttackTarget());
        if (attackTarget == TARGET_ENUM.FRONT_ALL_TARGET) {
            float dis = FightUtil.GetDistance(this, m_FinishedTarget);
            int length = m_AttackCreatures.size();
            Creature creature = null;
            for (int i = 0; i < length; ++i) {
                creature = m_AttackCreatures.get(i);
                if (Math.abs(FightUtil.GetDistance(this, creature) - dis) < m_AttackSpace) {
                	m_FightingManager.VideoManager.AddAttackAction(this, creature);
                	m_FightingManager.SkillEffectManager.fireAnimationEvent(this, creature, GetSkill());
                }
            }
        } else {
        	m_FightingManager.VideoManager.AddAttackAction(this, m_FinishedTarget);
        	m_FightingManager.SkillEffectManager.fireAnimationEvent(this, m_FinishedTarget, GetSkill());
        }
        return true;
    }
    protected void CheckMoveCreatures()
    {
        int count = m_MoveCreatures.size();
        boolean check = (count <= 0);
        for (int i = 0; i < count; ++i)
        {
            Creature creature = m_MoveCreatures.get(i);
            if (creature.IsDie)
            {
                check = true;
                break;
            }
        }
        if (check == false) return;
        m_MoveCreatures = m_FightingManager.GetMoveCreature(this);
    }
    protected boolean Move(float deltaTime)
    {
        CheckMoveCreatures();
        boolean move = true;
        int count = m_MoveCreatures.size();
        Creature target = null;
        float minDistance = 99999;
        for (int i = 0; i < count; ++i)
        {
            Creature creature = m_MoveCreatures.get(i);
            float distance = FightUtil.GetDistance(this, creature);
            if (distance <= 0)
            {
                move = false;
                break;
            }
            if (distance < minDistance)
            {
                minDistance = distance;
                target = creature;
            }
        }
        if (move == false)
            return false;
        float limitX = FightUtil.GetLimitPoint(this, target);
        float direction = Camp == CAMP_ENUM.LEFT ? 1 : -1;
        float xPosition = getPositionX();
        xPosition += deltaTime * GetMoveSpeed() * direction;
        if ((Camp == CAMP_ENUM.LEFT && xPosition > limitX) ||
            (Camp == CAMP_ENUM.RIGHT && xPosition < limitX))
        {
            xPosition = limitX;
        }
        boolean ret = false;
        if (Math.abs(getPositionX() - xPosition) <= 0) {
            ret = false;
        } else {
            ret = true;
        }
    	setPositionX(xPosition);
        return ret;
    }
    protected abstract void Update_Pre();
    protected abstract void Update_impl(float deltaTime) throws Exception;
}
