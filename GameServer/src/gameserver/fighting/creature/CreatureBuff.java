package gameserver.fighting.creature;

import gameserver.fighting.FightingManager;
import gameserver.fighting.clock.ClockManager;
import gameserver.fighting.clock.OnTimeCallBack;
import gameserver.fighting.clock.TimeCallBack;
import gameserver.fighting.stats.Addition;
import gameserver.fighting.stats.Addition.Profit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.MT_Data_Buff;
import table.base.TABLE;
import table.base.TableManager;

public class CreatureBuff {
	public static final Logger logger = LoggerFactory.getLogger(CreatureBuff.class);
	private FightingManager m_FightingManager;
	private Creature m_Creature;
	public CreatureBuff(FightingManager fightingManager, Creature creature) {
		m_FightingManager = fightingManager;
		m_Creature = creature;
	}
    private HashMap<Integer, Buff> mBuffs = new HashMap<Integer, Buff>();
    public HashMap<Integer, Buff> Buffs() {
    	return mBuffs; 
    }
    private boolean mNeedUpdate = false;

    void OnDestroy()
    {
        mBuffs.clear();
    }
    public void NeedUpdate()
    {
        mNeedUpdate = true;
    }
    public Buff GetBuff(int buffId)
    {
        if (mBuffs.containsKey(buffId))
            return mBuffs.get(buffId);
        return null;
    }
    public void AddBuff(int buffId)
    {
        try
        {
            MT_Data_Buff buffData = TableManager.GetInstance().TableBuff().GetElement(buffId);
            if (buffData == null)
            	logger.error("buff id {0} is not find", buffId);
            //检测状态是否存在 如果存在则替换
            {
                Buff buff = GetBuff(buffId);
                if (buff != null) DelBuff(buffId);
            }
            //检测时候有冲突并且优先级高的状态存在 有的话返回
            for (int clashId : buffData.CannotCoexist())
            {
                Buff buff = GetBuff(clashId);
                if (buff != null)
                {
                    int Priority1 = TABLE.IsInvalid(buff.Template().Priority()) ? 0 : buff.Template().Priority();
                    int Priority2 = TABLE.IsInvalid(buffData.Priority()) ? 0 : buffData.Priority();
                    if (Priority1 > Priority2)
                        return;
                }
            }
            //去除冲突的BUFF
            for (int clashId : buffData.CannotCoexist())
            {
                DelBuff(clashId);
            }
            {
                Buff buff = new Buff();
                buff.Template(buffData);
                buff.StatusNumber(1);
                buff.HurtScale(1);
                buff.InstantProperty(false);
                mBuffs.put(buffId, buff);
                if (!TABLE.IsInvalid(buffData.Times()) && buffData.Times() > 0f)		//持续时间有限制 则添加计时器
                {
                	int key = m_FightingManager.ClockManager.addGameTimeCallBack(buffData.Times(), new OnTimeCallBack() {
    					@Override
    					public void run(TimeCallBack timeCallBack, Object[] args) {
    						BuffOver(timeCallBack, (int)args[0]);
    					}}, buffId);
                    buff.TimerId(key);
                    if (!TABLE.IsInvalid(buffData.Space()) && buffData.Space() > 0f) {
                    	int k = m_FightingManager.ClockManager.addGameTimeCallBack(buffData.Space(), new OnTimeCallBack() {
        					@Override
        					public void run(TimeCallBack timeCallBack, Object[] args) {
        						BuffSpace(timeCallBack, (int)args[0]);
        					}}, buffId);
                        buff.SpaceTimeId(k);
                    }
                }
            }
            NeedUpdate();
        }
        catch (Exception ex)
        {
            logger.error("AddBuff is error {0} : {1}", buffId, ex.toString());
        }
    }
    public void DelBuff(int buffId)
    {
        if (mBuffs.containsKey(buffId))
        {
            Buff buff = mBuffs.get(buffId);
            m_FightingManager.ClockManager.removeTimeCallBack(buff.TimerId());
            m_FightingManager.ClockManager.removeTimeCallBack(buff.SpaceTimeId());
            mBuffs.remove(buffId);
            NeedUpdate();					//更新单位属性
        }
    }
    public void ClearBuff()
    {
        if (mBuffs.size() > 0)
        {
            for (Buff pair : mBuffs.values())
            {
                ClockManager.GetInstance().removeTimeCallBack(pair.TimerId());
                ClockManager.GetInstance().removeTimeCallBack(pair.SpaceTimeId());
            }
            mBuffs.clear();
            NeedUpdate();						////更新单位属性
        }
    }
    void BuffSpace(TimeCallBack timeCallBack, int buffId)
    {
        if (mBuffs.containsKey(buffId))
        {
            Buff buff = mBuffs.get(buffId);
            buff.StatusNumber(buff.StatusNumber() + 1);
            buff.InstantProperty(false);
            int key = ClockManager.GetInstance().addGameTimeCallBack(buff.Template().Space(), new OnTimeCallBack() {
				@Override
				public void run(TimeCallBack timeCallBack, Object[] args) {
					BuffSpace(timeCallBack, (int)args[0]);
				}}, buffId);
            buff.SpaceTimeId(key);
            NeedUpdate();
        }
    }
    void BuffOver(TimeCallBack timeCallBack, int buffId)
    {
        DelBuff(buffId);
    }
    void CalculateAddition(Profit profit, Int2 value, int number)
    {
        if (TABLE.IsInvalid(value)) return;
        if (value.field1() == 0)
            profit.value += (value.field2() * number);
        else if (value.field1() == 1)
            profit.value_f += ((float)value.field2() / 10000) * number;
    }
    public void Update()
    {
        if (mNeedUpdate == true) {
            Addition addition = new Addition();
            List<Integer> oneStatus = new ArrayList<Integer>();
            if (mBuffs.size() > 0) {
                for (Entry<Integer, Buff> pair : mBuffs.entrySet()) {
                	Buff buff = pair.getValue();
                    if (buff.Template().Times() == 0)
                        oneStatus.add(pair.getKey());
                    if (buff.InstantProperty() == false) {
                        buff.InstantProperty(true);
                        if (!TABLE.IsInvalid(buff.Template().Hp())) {
                            if (buff.Template().Hp().field1() == 0)
                                addition.hp += (int)(buff.Template().Hp().field2());
                            else if (buff.Template().Hp().field1() == 1)
                                addition.hp_max_f += ((float)(buff.Template().Hp().field2()) / 10000f);
                            else if (buff.Template().Hp().field1() == 2)
                                addition.hp_cur_f += ((float)(buff.Template().Hp().field2()) / 10000f);
                        }
                    }
                    CalculateAddition(addition.maxHp, buff.Template().MaxHp(), buff.StatusNumber());
                    CalculateAddition(addition.attack, buff.Template().Attack(), buff.StatusNumber());
                    CalculateAddition(addition.armor, buff.Template().Defense(), buff.StatusNumber());
                    CalculateAddition(addition.critical, buff.Template().Crit(), buff.StatusNumber());
                    CalculateAddition(addition.dodge, buff.Template().Dodge(), buff.StatusNumber());
                    if (!TABLE.IsInvalid(buff.Template().Speed())) addition.speed *= (buff.Template().Speed() * buff.StatusNumber());
                    if (!TABLE.IsInvalid(buff.Template().AtkSpeed())) addition.atkSpeed *= (buff.Template().AtkSpeed() * buff.StatusNumber());
                    if (!TABLE.IsInvalid(buff.Template().ExpAdd())) addition.expadd *= (buff.Template().ExpAdd() * buff.StatusNumber());
                    if (!TABLE.IsInvalid(buff.Template().MoneyAdd())) addition.moneyadd *= (buff.Template().MoneyAdd() * buff.StatusNumber());
                    for (int prohibit : buff.Template().ProhibitActions())
                    {
                        addition.prohibitAction |= (1 << prohibit);
                    }
                }
            }
            m_Creature.updateProperty(addition);
            if (oneStatus.size() > 0)
            {
                for (int i = 0; i < oneStatus.size(); ++i)
                    mBuffs.remove(oneStatus.get(i));
                NeedUpdate();
                return;
            }
            mNeedUpdate = false;
        }
    }
}
