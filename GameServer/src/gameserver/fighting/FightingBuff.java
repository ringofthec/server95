package gameserver.fighting;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import commonality.CREATURE_ENUM;
import commonality.Common;
import table.Int3;
import table.MT_Data_Corps;
import table.MT_Data_Hero;
import table.MT_Data_PassiveBuff;
import table.base.TABLE;
import table.base.TableManager;

public class FightingBuff {
	private static Logger logger = LoggerFactory.getLogger(FightingBuff.class);
	
	//被动Buff列表（战斗时使用，例如增加兵种血量等，还有开关类型）
	protected HashMap<Integer, MT_Data_PassiveBuff> m_PassiveBuffs = new HashMap<Integer, MT_Data_PassiveBuff>();
	public HashMap<Integer, MT_Data_PassiveBuff> PassiveBuffs() { return m_PassiveBuffs; }
	/* 添加被动buff列表 */
    public void AddPassiveBuff(int tableId)
    {
        if (m_PassiveBuffs.containsKey(tableId))
            return;
        
        if (!TableManager.GetInstance().TablePassiveBuff().Contains(tableId)) {
        	logger.error("need add not exist buff, buff_table_id={}", tableId);
        	return ;
        }
        
        MT_Data_PassiveBuff data = TableManager.GetInstance().TablePassiveBuff().GetElement(tableId);
        if (data != null) 
        	m_PassiveBuffs.put(tableId, data);
    }
   /* 添加被动buff列表 */
    public void AddPassiveBuff(MT_Data_PassiveBuff data)
    {
        if (data == null) return;
        m_PassiveBuffs.put(data.ID(), data);
    }
    public int2 getCreatureValue(int creatureType, int type, boolean self, int corpId)
    {
    	if (creatureType == CREATURE_ENUM.HERO.Number())
            return GetHeroBuffValue(type, self, corpId);
        else if (creatureType == CREATURE_ENUM.CASTLE.Number())
            return GetWallBuffValue(type, self);
        else if (creatureType == CREATURE_ENUM.AIRSUPPORT.Number())
            return GetAirSupportBuffValue(type, self, corpId);
        else {
        	int baseId = corpId / 10000 * 10000;
            return GetCorpBuffValue(type, self, baseId);
        }
    }
    private int2 GetCorpBuffValue(int type, boolean self, int corpId)
    {
        int2 point = new int2(0, 0);
        if (!TableManager.GetInstance().TableCorps().Contains(corpId))
            return point;
        MT_Data_Corps corpData = TableManager.GetInstance().TableCorps().GetElement(corpId);
        if (corpData == null)
            return point;

        for (MT_Data_PassiveBuff pair : m_PassiveBuffs.values())
        {
            if ((pair.type() == Common.PASSIVEBUFF_OBJECT_TYPE.OWNARMORTYPE.Number() && self) ||
                (pair.type() == (int)Common.PASSIVEBUFF_OBJECT_TYPE.ENEMYARMORTYPE.Number() && !self))
            {
                if (TABLE.IsInvalid(pair.targetCorps()) || pair.targetCorps().intValue() == corpData.ArmorType())
                    point = getEffect(type, pair.buffs(), point);
            }

            if ((pair.type() == Common.PASSIVEBUFF_OBJECT_TYPE.OWNATTACKTYPE.Number() && self) ||
                (pair.type() == Common.PASSIVEBUFF_OBJECT_TYPE.ENEMYATTACKTYPE.Number() && !self))
            {
                if (TABLE.IsInvalid(pair.targetCorps()) || pair.targetCorps().intValue() == corpData.AttackType())
                    point = getEffect(type, pair.buffs(), point);
            }

            if ((pair.type() == Common.PASSIVEBUFF_OBJECT_TYPE.OWNCORPS.Number() && self) ||
                (pair.type() == Common.PASSIVEBUFF_OBJECT_TYPE.ENEMYCORPS.Number() && !self))
            {
                if (TABLE.IsInvalid(pair.targetCorps()) || corpId == pair.targetCorps())
                {
                    point = getEffect(type, pair.buffs(), point);
                }
            }
        }

        return point;
    }
    private int2 GetAirSupportBuffValue(int type, boolean self, int corpId)
    {
        int2 point = new int2(0, 0);
        for (MT_Data_PassiveBuff pair : m_PassiveBuffs.values())
        {
            if ((pair.type() == Common.PASSIVEBUFF_OBJECT_TYPE.OWNAIRSUPPORT.Number() && self) ||
                (pair.type() == Common.PASSIVEBUFF_OBJECT_TYPE.ENEMYAIRSUPPORT.Number() && !self))
            {
                if (TABLE.IsInvalid(pair.targetCorps()) || pair.targetCorps() == corpId)
                    point = getEffect(type, pair.buffs(), point);
            }
        }
        return point;
    }
    private int2 GetWallBuffValue(int type, boolean self)
    {
        int2 point = new int2(0, 0);

        for (MT_Data_PassiveBuff pair : m_PassiveBuffs.values())
        {
            if ((pair.type() == Common.PASSIVEBUFF_OBJECT_TYPE.OWNWALL.Number() && self) ||
                (pair.type() == Common.PASSIVEBUFF_OBJECT_TYPE.ENEMYWALL.Number() && !self))
            {
                point = getEffect(type, pair.buffs(), point);
            }

        }

        return point;
    }
    private int2 GetHeroBuffValue(int type, boolean self, int heroId)
    {
        int2 point = new int2(0, 0);

        MT_Data_Hero corpData = TableManager.GetInstance().TableHero().GetElement(heroId);
        if (corpData == null)
            return point;

        for (MT_Data_PassiveBuff pair : m_PassiveBuffs.values())
        {
            if ((pair.type() == Common.PASSIVEBUFF_OBJECT_TYPE.OWNARMORTYPE.Number() && self) ||
                (pair.type() == Common.PASSIVEBUFF_OBJECT_TYPE.ENEMYARMORTYPE.Number() && !self))
            {
                if (TABLE.IsInvalid(pair.targetCorps()) || pair.targetCorps().intValue() == corpData.ArmorType())
                    point = getEffect(type, pair.buffs(), point);
            }

            if ((pair.type() == Common.PASSIVEBUFF_OBJECT_TYPE.OWNATTACKTYPE.Number() && self) ||
                (pair.type() == Common.PASSIVEBUFF_OBJECT_TYPE.ENEMYATTACKTYPE.Number() && !self))
            {
                if (TABLE.IsInvalid(pair.targetCorps()) || pair.targetCorps().intValue() == corpData.AttackType())
                    point = getEffect(type, pair.buffs(), point);
            }

            if ((pair.type() == Common.PASSIVEBUFF_OBJECT_TYPE.HERO.Number() && self) ||
                (pair.type() == Common.PASSIVEBUFF_OBJECT_TYPE.ENEMY_HERO.Number() && !self))
            {
                if (TABLE.IsInvalid(pair.targetCorps()) || heroId == pair.targetCorps())
                {
                    point = getEffect(type, pair.buffs(), point);
                }
            }
        }
        return point;
    }
    private int2 getEffect(int type, Int3 buffs, int2 point)
    {
        if (TABLE.IsInvalid(buffs))
            return point;

        if (buffs.field1() != (int)type)
            return point;

        if (buffs.field2() == 0)
            point.field1 += buffs.field3();
        else
            point.field2 += buffs.field3();

        return point;
    }
}
