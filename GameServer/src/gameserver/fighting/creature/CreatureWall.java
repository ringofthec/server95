package gameserver.fighting.creature;

import table.MT_DataBase;
import gameserver.fighting.FightingManager;
import commonality.CAMP_ENUM;
import commonality.CREATURE_ENUM;

public class CreatureWall extends CreatureCorps {
    public void Initialize(int x, int y, int corpsId, MT_DataBase dataBase, CAMP_ENUM camp, FightingManager fightingManager) throws Exception
    {
        super.Initialize_impl(x, y, corpsId, dataBase, camp, CREATURE_ENUM.TOWER, fightingManager);
    }
    public void StartFighting() { }
    protected void Update_Pre() { }
    protected void Update_impl(float deltaTime) throws Exception
    {
    	if (!CanAttackSpeed())
            return;
    	Attack();
    }
}
