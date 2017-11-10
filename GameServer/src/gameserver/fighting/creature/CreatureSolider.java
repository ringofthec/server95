package gameserver.fighting.creature;

import table.MT_DataBase;
import gameserver.fighting.FightingManager;
import commonality.CAMP_ENUM;
import commonality.CREATURE_ENUM;

public class CreatureSolider extends CreatureCorps {
	public void Initialize(int x, int y, int corpsId, MT_DataBase database, CAMP_ENUM camp,CREATURE_ENUM type, FightingManager fightingManager) throws Exception
    {
        super.Initialize_impl(x, y, corpsId, database, camp, type, fightingManager);
    }
	protected void Die_impl()
	{
		m_FightingManager.CheckFinghtingOver(this);
	}
	@Override
	protected void Update_impl(float deltaTime) throws Exception {
		if (!Attack()) {
			if (!Move(deltaTime)) {
				Attack_impl();
			}
		}
	}
	@Override
	protected void Update_Pre() {
		// TODO Auto-generated method stub
	}
	@Override
	public void StartFighting() {
		// TODO Auto-generated method stub
	}
}
