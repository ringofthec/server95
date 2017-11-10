package gameserver.fighting.stats;

import gameserver.fighting.CorpsSet;
import gameserver.fighting.FightingManager;
import gameserver.fighting.creature.Creature;
import gameserver.utils.Util;

import com.commons.util.RandomUtil;
import commonality.ARMOR_TYPE;
import commonality.ATTACK_TYPE;
import commonality.CAMP_ENUM;

public class FightUtil {
    public static int GetRightXByLeftX(int x, int width, FightingManager fightingManager)
    {
        CorpsSet corpsSet = fightingManager.RightCorps;
        return Math.abs(x + width - corpsSet.Rect().width) + corpsSet.Rect().left();
    }
    public static int GetLeftXByRightX(int x, int width, FightingManager fightingManager)
    {
    	CorpsSet corpsSet = fightingManager.RightCorps;
        return Math.abs(x - corpsSet.Rect().left() - (corpsSet.Rect().width - width));
    }
    /// <summary> 获得角色移动的临界点 </summary>
    public static float GetLimitPoint(Creature creature, Creature target)
    {
    	float limit = 0;
        if (creature.Camp == CAMP_ENUM.LEFT)
            limit = target.getPositionX() - target.HalfWorldWidth - creature.HalfWorldWidth;
        else
            limit = target.getPositionX() + target.HalfWorldWidth + creature.HalfWorldWidth;
        return limit;
    }
    /// <summary>
    /// 计算两个单位的最小距离(如果是同阵营的 creature1代表后面的单位 creature2代表前面的单位
    /// 也就是 左边阵营 creature2 X坐标比较大  右边阵营 creature1 X坐标比较大)
    /// </summary>
    public static float GetDistance(Creature creature1, Creature creature2)
    {
        return Math.abs(GetDistancePrimitive(creature1, creature2));
    }
    public static float GetDistancePrimitive(Creature creature1, Creature creature2)
    {
    	float maxX = 0;
        float minX = 0;
        if (creature1.Camp == creature2.Camp) {
            if (creature1.Camp == CAMP_ENUM.LEFT) {
                minX = creature1.getPositionX() + creature1.HalfWorldWidth;
                maxX = creature2.getPositionX() - creature2.HalfWorldWidth;
            }  else {
                minX = creature2.getPositionX() + creature2.HalfWorldWidth;
                maxX = creature1.getPositionX() - creature1.HalfWorldWidth;
            }
        } else {
            Creature left = creature1.Camp == CAMP_ENUM.LEFT ? creature1 : creature2;
            Creature right = creature1.Camp == CAMP_ENUM.LEFT ? creature2 : creature1;
            minX = left.getPositionX() + left.HalfWorldWidth;
            maxX = right.getPositionX() - right.HalfWorldWidth;
        }
        return maxX - minX;
    }
    /// <summary> 计算Target相对于creature是否为有效目标（超过creature就算无效目标） </summary>
    public static boolean IsValidCreature(Creature creature, Creature target)
    {
        return (creature.Camp == CAMP_ENUM.LEFT ? target.getPositionX() > creature.getPositionX() : target.getPositionX() < creature.getPositionX());
    }
    /// <summary> 获得伤害数值 </summary>
    public static HurtResult GetHurt(Creature attack, Creature target)
    {
        return GetHurt((int)(attack.GetValue(STATS_ENUM.ATTACK)),
        		(int)(target.GetValue(STATS_ENUM.ARMOR)),
        		(int)(attack.GetValue(STATS_ENUM.ATTACK_TYPE)),
        		(int)(target.GetValue(STATS_ENUM.ARMOR_TYPE)),
        		(int)(attack.GetValue(STATS_ENUM.CRITICAL)),
        		(int)(target.GetValue(STATS_ENUM.IMMUNECRIT)),
        		(int)(attack.GetValue(STATS_ENUM.HIT)),
        		(int)(target.GetValue(STATS_ENUM.DODGE)),
        		(int)(attack.GetValue(STATS_ENUM.VAMPIRE_TYPE)),
        		(int)(attack.GetValue(STATS_ENUM.VAMPIRE_ODDS)),
        		(int)(attack.GetValue(STATS_ENUM.VAMPIRE)),
                (int)(attack.GetValue(STATS_ENUM.VAMPIRE_NUM)),
                (int)(attack.GetValue(STATS_ENUM.VAMPIRE_RATIO)));
    }
    /// <summary> 获得伤害数值 </summary>
    public static HurtResult GetHurt(int attack, int attackType, int crit, Creature target)
    {
        return GetHurt(attack,
				   (int)(target.GetValue(STATS_ENUM.ARMOR)),
	               attackType,
	               (int)(target.GetValue(STATS_ENUM.ARMOR_TYPE)),
	               crit,
	               (int)(target.GetValue(STATS_ENUM.IMMUNECRIT)),
	               0,
	               (int)(target.GetValue(STATS_ENUM.DODGE)),
	               0,
	               0,
	               0,
	               0,
	               0);
    }
    /// <summary> 获得伤害数值 </summary>
    public static HurtResult GetHurt(int Attack, int Armor, int AttackType, int ArmorType, int Crit, int ImmuneCrit, int Hit, int Dodge, int VampireType, int VampireOdds, int Vampire, int Vampire_Num, int Vampire_Ratio)
    {
        return GetHurt(Attack, Armor, ATTACK_TYPE.valueOf(AttackType), ARMOR_TYPE.valueOf(ArmorType), Crit, ImmuneCrit, Hit, Dodge, VampireType, VampireOdds, Vampire, Vampire_Num, Vampire_Ratio);
    }
    /// <summary> 获得伤害数值 </summary>
    public static HurtResult GetHurt(int Attack, int Armor, ATTACK_TYPE AttackType, ARMOR_TYPE ArmorType, int Crit, int ImmuneCrit, int Hit, int Dodge, int VampireType, int VampireOdds, int Vampire, int Vampire_Num, int Vampire_Ratio)
    {
        HurtResult result = new HurtResult();
        if (Util.GetOdds(Dodge - Hit))
        {
            result.Miss = true;
            return result;
        }
        result.Crit = Util.GetOdds(Crit - ImmuneCrit);
        if (Armor >= Attack)
        {
            result.Hurt = 1;
            return result;
        }
        result.Hurt = (Attack - Armor) / 10 + RandomUtil.RangeRandom(1, 5);
        //伤害系数
        float Coefficient = 1;
        if (AttackType == ATTACK_TYPE.IMPALE)
        {
            if (ArmorType == ARMOR_TYPE.STRONG)
                Coefficient = 2;
            else if (ArmorType == ARMOR_TYPE.COMPLEX)
                Coefficient = 0.75f;
            else if (ArmorType == ARMOR_TYPE.HERO)
                Coefficient = 0.5f;
        }
        else if (AttackType == ATTACK_TYPE.BIGBANG)
        {
            if (ArmorType == ARMOR_TYPE.LIGHT)
                Coefficient = 2;
            else if (ArmorType == ARMOR_TYPE.COMPLEX)
                Coefficient = 0.75f;
            else if (ArmorType == ARMOR_TYPE.HERO)
                Coefficient = 0.5f;
        }
        else if (AttackType == ATTACK_TYPE.POWER)
        {
            if (ArmorType == ARMOR_TYPE.LIGHT)
                Coefficient = 0.75f;
            else if (ArmorType == ARMOR_TYPE.COMPLEX)
                Coefficient = 2;
            else if (ArmorType == ARMOR_TYPE.HERO)
                Coefficient = 0.5f;
        }
        else if (AttackType == ATTACK_TYPE.HERO)
        {
            if (ArmorType != ARMOR_TYPE.HERO)
                Coefficient = 1.2f;
        }
        result.Hurt = ((Double)Math.floor(result.Hurt.floatValue() * Coefficient)).intValue();
        if (result.Crit) result.Hurt *= 2;
        if (result.Hurt <= 0) result.Hurt = 1;
        if (Vampire > 0 && Util.GetOdds(VampireOdds))
        {
            if (VampireType == 0)       //万分比
            {
                float num = (float)Vampire / 10000f;
                result.Vampire = ((Float)(result.Hurt.floatValue() * num)).intValue();
            }
            else        //具体指
                result.Vampire = Vampire;
        }
        if (Vampire_Num > 0)
            result.Vampire += Vampire_Num;
        if (Vampire_Ratio > 0)
            result.Vampire += (int)(result.Hurt * ((float)Vampire_Ratio / 10000f));
        return result;
    }

}
