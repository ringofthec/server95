package gameserver.fighting;

import gameserver.fighting.SkillEffectManager.SKILL_TARGET_TYPE;
import gameserver.fighting.creature.CreatureObject;
import gameserver.fighting.creature.FightingObject;

import com.commons.util.GridPoint;

import commonality.CAMP_ENUM;

public class BulletController extends FightingObject {
    public static void CreateBullet(FightingManager fightingManager,CreatureObject fireCreature, SKILL_TARGET_TYPE targetType, CreatureObject targetCreature, GridPoint targetPosition, FightData data)
    {
    	new BulletController().Initialize(fightingManager, fireCreature, targetType, targetCreature, targetPosition, data);
    }
    protected CreatureObject mFireCreature, mTargetCreature;//释放单位 目标单位
    protected SKILL_TARGET_TYPE mTargetType;                //目标类型
    protected GridPoint mTargetPosition;                    //目标格子
    protected Vector2 mTargetVectorPosition;                //目标坐标
    protected FightData mData;                              //效果数据
    protected Vector2 mStartPosition;                       //起始坐标
    private float mTargetY = 0;                             //目标Y点
    protected float mSpeed = 0;                             //火球移动速度
    protected FightingManager m_FightingManager;
    protected boolean mShutdown = false;                    //是否已经销毁 避免二次伤害
    
    protected void ShutDown()
    {
        mShutdown = true;
        m_FightingManager.UpdateManager.DelUpdateComponent(this);
    }
    protected GameTerrain Terrain() 
    { 
    	return m_FightingManager.Terrain; 
    }
    protected float TargetPositionX()
    {
        if (mTargetType != SKILL_TARGET_TYPE.CREATURE)
        {
            return mTargetVectorPosition.x;
        }
        else
        {
            return mTargetCreature.getPositionX();
        }
    }
    private void Initialize(FightingManager fightingManager,CreatureObject fireCreature, SKILL_TARGET_TYPE targetType, CreatureObject targetCreature, GridPoint targetPosition, FightData data)
    {
    	m_FightingManager = fightingManager;
    	m_FightingManager.UpdateManager.AddUpdateComponent(this);
        mFireCreature = fireCreature;
        mTargetType = targetType;
        mTargetCreature = targetCreature;
        mTargetPosition = targetPosition;
        mData = data;
        mShutdown = false;
      //计算目标点Y坐标
        if (targetType == SKILL_TARGET_TYPE.CREATURE)
        {
        	mTargetY = fireCreature.getPositionY();
        }
        else
        {
            mTargetVectorPosition = m_FightingManager.Terrain.GetPositionByRanks(mTargetPosition.x, mTargetPosition.y, true);
            mTargetY = mTargetVectorPosition.y;
        }
        //计算出发点
        mStartPosition = fireCreature.getPosition();
        mStartPosition.x += mData.offset.x;
        mStartPosition.y += mData.offset.y;
        setPosition(mStartPosition);
        mSpeed = Terrain().GetGridWidth() * mData.effect.Speed();
    }
    public void OnUpdate(float deltaTime)
    {
    	if (deltaTime <= 0) return;
        if (mShutdown == true) return;
        if (mFireCreature.Camp == CAMP_ENUM.RIGHT)
            moveLeft(deltaTime, mSpeed);
        else
            moveRight(deltaTime, mSpeed);
        if (distanceX(mStartPosition) > Math.abs(TargetPositionX() - mStartPosition.x))
        {
            ShutDown();
            if (mTargetType == SKILL_TARGET_TYPE.CREATURE)
            {
            	m_FightingManager.ByAttack(mFireCreature, mTargetCreature, mData);
            }
            else
            {
            	m_FightingManager.ByAttack(mFireCreature, mTargetPosition, mData);
            }
        }
    }
    protected float TargetPositionY() {
    	return mTargetY; 
    }
}
