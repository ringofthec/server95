package gameserver.fighting.creature;

import gameserver.fighting.FightData;
import gameserver.fighting.FightingManager;
import gameserver.fighting.Vector2;
import gameserver.fighting.stats.Addition;
import gameserver.fighting.stats.Addition.Profit;
import gameserver.fighting.stats.CreatureStats;
import gameserver.fighting.stats.FightUtil;
import gameserver.fighting.stats.HurtResult;
import gameserver.fighting.stats.STATS_ENUM;
import gameserver.utils.Util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.base.TABLE;

import com.commons.util.GridPoint;
import com.commons.util.GridRect;
import com.commons.util.GridSize;

import commonality.CAMP_ENUM;
import commonality.CREATURE_ENUM;

public abstract class Creature extends CreatureObject {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(Creature.class);
	protected GridRect m_Rect = new GridRect();                     //单位初始矩形
    private int m_ProhibitAction = 0;                               //禁止的行为
    public boolean IsDie;                       					//是否死亡
    protected CreatureStats mBaseStats = new CreatureStats();       //单位初始属性
    protected CreatureStats mStats = new CreatureStats();           //单位现有属性
    private CreatureBuff m_CreatureBuff;                                //战斗Buff系统
    public GridPoint BeginPosition() { return m_Rect.leftTop(); }
    public int BeginX() { return m_Rect.x; }
    public int BeginY() { return m_Rect.y; }
    public int EndX() { return m_Rect.right(); }
    public int EndY() { return m_Rect.bottom(); }
    public GridPoint CurrentPosition() { return m_FightingManager.Terrain.GetRanksPerfect(this.getPosition(), m_Rect.width, m_Rect.height); }
    public int CurrentX() { return CurrentPosition().x; }
    public int CurrentY() { return CurrentPosition().y; }
    public GridRect CurrentRect() { return new GridRect(CurrentPosition(), m_Rect.getSize()); }
    public GridRect Rect() { return new GridRect(m_Rect); }
    public GridSize Size() { return m_Rect.getSize(); }
    public float WorldWidth;
    public float WorldHeight;
    public float HalfWorldWidth;
    public float HalfWorldHeight;
    protected void Initialize_impl(int x, int y, int width, int height, int hp, CAMP_ENUM camp, CREATURE_ENUM type, FightingManager fightingManager) {
        Initialize_impl(camp, type, fightingManager);
        m_FightingManager.UpdateManager.AddUpdateComponent(this);
        m_CreatureBuff = new CreatureBuff(m_FightingManager, this);
        m_Rect.x = x;
        m_Rect.y = y;
        m_Rect.width = width;
        m_Rect.height = height;
        WorldWidth = fightingManager.Terrain.GetGridWidth() * width;
        WorldHeight = fightingManager.Terrain.GetGridHeight() * height;
        HalfWorldWidth = WorldWidth / 2;
        HalfWorldHeight = WorldHeight / 2;
        IsDie = false;
     
        mBaseStats.SetValue(STATS_ENUM.MAXHP, hp);
        mBaseStats.SetValue(STATS_ENUM.CURHP, hp);
        mStats.Set(mBaseStats);
    }
    public boolean ContainsBegin(int x,int y) {
        return ContainsBegin(new GridPoint(x, y));
    }
    public boolean ContainsBegin(GridPoint point) {
        return m_Rect.Contains(point);
    }
    public boolean ContainsCurrent(int x,int y) {
        return ContainsCurrent(new GridPoint(x, y));
    }
    public boolean ContainsCurrent(GridPoint point) {
        return CurrentRect().Contains(point);
    }
    public void SetValue(STATS_ENUM type, Object value) {
        mStats.SetValue(type, value);
    }
    public Object GetValue(STATS_ENUM type){
        return mStats.GetValue(type);
    }
    public void ClearValue() {
        mStats.ClearValue();
    }
    public void Hurt(int attack, int attackType, int crit) {
        Hurt_impl(FightUtil.GetHurt(attack, attackType, crit, this), null, null);
    }
    public void Hurt(Creature attack, FightData data) {
        Hurt_impl(FightUtil.GetHurt(attack, this), attack, data);
    }
    private void Hurt_impl(HurtResult result, Creature attack, FightData data) {
        if (result.Miss) return;
        //暴击
        if (result.Crit && data != null && this.Type != CREATURE_ENUM.AIRSUPPORT) {
            if (data.effect.CritBuff().size() > 0)
                attack.AddBuffArray(data.effect.CritBuff());
            if (data.effect.CritTargetBuff().size() > 0)
                this.AddBuffArray(data.effect.CritTargetBuff());

            m_FightingManager.AddHeroEquipBuff(attack, this, 3);
        }
        ChangeHp(0 - result.Hurt);
        if (attack != null && !TABLE.IsInvalid(result.Vampire) && result.Vampire > 0)
            attack.ChangeHp(0 + result.Vampire);
    }
    void Die() {
        if (IsDie) return;
        IsDie = true;
        m_FightingManager.VideoManager.AddCreatureDieAction(this);
        Die_impl();
    }
    public void MakePerfect() {
    	GridPoint point = new GridPoint(m_Rect.x, m_Rect.y);
    	GridSize size = new GridSize(m_Rect.width, m_Rect.height);
    	Vector2 position = m_FightingManager.Terrain.GetPixelPerfect(point, size);
        this.setPosition(position);
    }
    public void AddBuffArray(List<Int2> bufArray) {
        for (Int2 buf : bufArray) {
            if (Util.GetOdds(buf.field1())) 
            	AddBuff(buf.field2());
        }
    }
    public void AddBuff(int buffId)
    {
        if (m_CreatureBuff != null) m_CreatureBuff.AddBuff(buffId);
    }
    //更新BUFF更改角色属性
    public void updateProperty(Addition addition) {
        updateValue(STATS_ENUM.MAXHP, addition.maxHp);
        updateValue(STATS_ENUM.ATTACK, addition.attack);
        updateValue(STATS_ENUM.ARMOR, addition.armor);
        updateValue(STATS_ENUM.CRITICAL, addition.critical);
        updateValue(STATS_ENUM.DODGE, addition.dodge);
        SetValue(STATS_ENUM.MOVE_SPEED, (float)mBaseStats.GetValue(STATS_ENUM.MOVE_SPEED) * addition.speed);
        Integer maxHp = (Integer)GetValue(STATS_ENUM.MAXHP);
        Integer curHp = (Integer)GetValue(STATS_ENUM.CURHP);
        int hp = addition.hp + ((Double)Math.floor(maxHp.floatValue() * addition.hp_max_f)).intValue() + ((Double)Math.floor(curHp.floatValue() * addition.hp_cur_f)).intValue();
        ChangeHp(hp);
        m_ProhibitAction = addition.prohibitAction;
    }
    void ChangeHp(int value) {
        int curhp = (int)mStats.GetValue(STATS_ENUM.CURHP);
        int maxhp = (int)GetValue(STATS_ENUM.MAXHP);
        curhp = (int)mStats.SetValue(STATS_ENUM.CURHP, curhp + value);
        if (IsProhibitAction(TABLE.ACTIONS_TYPE.NODIE) && curhp <= 0)
            curhp = 1;
        ChangeHp_impl(value, curhp, maxhp);
        m_FightingManager.VideoManager.AddChangeHpAction(this, value);
        if (curhp <= 0) 
        	Die();
    }
    void updateValue(STATS_ENUM type, Profit profit) {
        int value = (int)mBaseStats.GetValue(type);
        value += profit.value + Math.floor(value * profit.value_f);
        mStats.SetValue(type, value);
    }
    
    void updateBuff() {
    	if (m_CreatureBuff != null)
    		m_CreatureBuff.Update();
    }
    //添加一个禁止的行为
    public void AddProhibitAction(TABLE.ACTIONS_TYPE action) {
        if ((m_ProhibitAction & (int)(1 << action.ordinal())) == 0)
            m_ProhibitAction |= (int)(1 << action.ordinal());
    }
    //删除一个禁止的行为
    public void DelProhibitAction(TABLE.ACTIONS_TYPE action) {
        if ((m_ProhibitAction & (int)(1 << action.ordinal())) != 0)
            m_ProhibitAction ^= (int)(1 << action.ordinal());
    }
    //是否包含某个状态
    public boolean IsProhibitAction(TABLE.ACTIONS_TYPE action) {
        int ret = (m_ProhibitAction & (int)(1 << action.ordinal()));
        return (ret != 0);
    }
    /// <summary> 攻击玩家 </summary>
    protected boolean Attack() throws Exception { return false; }
    /// <summary> 开始战斗 </summary>
    public abstract void StartFighting();
    /// <summary> 结束战斗 </summary>
    public abstract void OverFighting(CAMP_ENUM camp);
    /// <summary> 血量变化回调 </summary>
    protected void ChangeHp_impl(int value, int curhp,int maxhp) { }
    /// <summary> 死亡回调 </summary>
    protected void Die_impl() { }
    
}
