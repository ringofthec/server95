package gameserver.fighting.creature;

import gameserver.fighting.FightingManager;
import gameserver.fighting.Vector2;
import table.MT_Data_AirSupport;

import com.commons.util.GridPoint;
import commonality.CAMP_ENUM;
import commonality.CREATURE_ENUM;

public abstract class CreatureAirSupport extends CreatureObject {
    protected GridPoint m_Target;                     //目标格子
    protected float m_FirePosition;                   //投放的X坐标
    protected float m_FinshedPosition = 0;            //结束的X轴
    public MT_Data_AirSupport AirData;					//空中支援数据
    protected float m_Speed = 0;                       //速度
    public void Initialize(CAMP_ENUM camp,MT_Data_AirSupport airData, GridPoint target,FightingManager fightingManager)
    {
        super.Initialize_impl(camp , CREATURE_ENUM.AIRSUPPORT, fightingManager);
        m_FightingManager.UpdateManager.AddUpdateComponent(this);
        this.AirData = airData;
        m_Target = target;
        m_Speed = airData.Speed() * m_FightingManager.Terrain.GetGridWidth();
        {
            CreatureCastle Castle = (camp == CAMP_ENUM.LEFT ? m_FightingManager.RightCorps.Castle : m_FightingManager.LeftCorps.Castle);
            m_FinshedPosition = Castle.getPositionX() + m_FightingManager.Terrain.GetGridWidth() * 5 * (camp == CAMP_ENUM.LEFT ? 1 : -1);
        }
        Vector2 targetPosition = m_FightingManager.Terrain.GetPositionByRanks(target, true);
        {
            m_FirePosition = targetPosition.x + m_FightingManager.Terrain.GetGridWidth() * airData.PreDistance() * (camp == CAMP_ENUM.LEFT ? -1 : 1);
        }
        {
            CreatureCastle Castle = (camp == CAMP_ENUM.LEFT ? m_FightingManager.LeftCorps.Castle : m_FightingManager.RightCorps.Castle);
            setPositionX(Castle.getPositionX() + m_FightingManager.Terrain.GetGridWidth() * 5 * (camp == CAMP_ENUM.LEFT ? -1 : 1));
        }
        Initialize_impl();
    }
    public void OnUpdate(float deltaTime)
    {
        if (Camp == CAMP_ENUM.LEFT)
        {
            moveRight(deltaTime, m_Speed);
            if (this.getPositionX() > m_FinshedPosition)
            {
                ShutDown();
            }
        }
        else
        {
            moveLeft(deltaTime, m_Speed);
            if (this.getPositionX() < m_FinshedPosition)
            {
                ShutDown();
            }
        }
        Update_impl();
    }
    protected boolean CanFire()
    {
        if (Camp == CAMP_ENUM.LEFT)
            return this.getPositionX() > m_FirePosition;
        return this.getPositionX() < m_FirePosition;
    }
    private void ShutDown()
    {
    	m_FightingManager.UpdateManager.DelUpdateComponent(this);
    }
    protected abstract void Initialize_impl();
    protected abstract void Update_impl();
}
