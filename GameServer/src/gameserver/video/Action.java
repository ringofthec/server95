package gameserver.video;

import commonality.CAMP_ENUM;
import commonality.CREATURE_ENUM;
import gameserver.fighting.FightingManager;
import gameserver.fighting.SkillEffectManager.SKILL_TARGET_TYPE;
import gameserver.fighting.creature.Creature;
import gameserver.fighting.stats.FightUtil;
import gameserver.video.VideoManager.ACTION_ENUM;

public class Action {
	public Action(FightingManager fightingManager)
    {
        timeLine = fightingManager.UpdateManager.GetTimeLine();
    }
    public Action(FightingManager fightingManager, Creature creature)
    {
        timeLine = fightingManager.UpdateManager.GetTimeLine();
        creatureType = creature.Type.Number();
        creatureCamp = creature.Camp.Number();
        creatureWidth = creature.Size().width;
        creatureX = (creature.Camp == CAMP_ENUM.LEFT ? creature.BeginX() : FightUtil.GetLeftXByRightX(creature.BeginX(), creature.Size().width, fightingManager));
        creatureY = creature.BeginY();
    }
    public float timeLine;                  //时间轴
    public int action;              //行动类型

    public int creatureType;      //士兵类型
    public int creatureCamp;          //士兵阵营
    public int creatureWidth;               //士兵宽
    public int creatureX, creatureY;        //士兵格子

    public int targetType;        //目标类型
    public int targetCamp;            //目标阵营
    public int targetWidth;                 //目标宽
    public int targetX, targetY;            //目标士兵格子或者格子
    public int type;          //目标类型
    public int hp;                          //改变的血量
    public int airId;                       //释放空中支援的ID
    public int fireX, fireY;                //释放空中支援的目标格子
}
