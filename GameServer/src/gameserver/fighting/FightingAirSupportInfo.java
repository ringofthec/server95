package gameserver.fighting;

import gameserver.fighting.clock.OnTimeCallBack;
import gameserver.fighting.clock.TimeCallBack;
import table.MT_Data_AirSupport;
import table.base.TableManager;

public class FightingAirSupportInfo {
	/* 表ID */
    public int TableId;
    /* 等级 */
    public int Level;
    /* 已经使用的次数 */
    public int Used;
    /* 耐久 */
    public int Durable;
    /* 表数据 */
    public MT_Data_AirSupport Data;
    private FightingManager m_FightingManager;
	private TimeCallBack m_CoolDown = null;
	private int CoolDownKey = 0;
    public TimeCallBack CoolDown() {
    	if (m_FightingManager.ClockManager.getTimeCallBack(CoolDownKey) != null)
    		m_CoolDown = m_FightingManager.ClockManager.getTimeCallBack(CoolDownKey);
    	return m_CoolDown;
    }
    public FightingAirSupportInfo(FightingManager fightingManager)
    {
    	m_FightingManager = fightingManager;
    }
    public FightingAirSupportInfo(int tableId, int level, FightingManager fightingManager)
    {
    	m_FightingManager = fightingManager;
        TableId = tableId;
        Level = level;
        Data = TableManager.GetInstance().TableAirSupport().GetElement(TableId + Level);
        Used = 0;
        m_CoolDown = new TimeCallBack(new OnTimeCallBack() {
        	@Override
			public void run(TimeCallBack timeCallBack, Object[] args) {
        		OnCoolDownTimeOver(timeCallBack, args);
        	}
        });
    }
    public void Shutdown() {
    	m_FightingManager = null;
    	m_CoolDown = null;
    }
    public void Use()
    {
        ++Used;
        CoolDownKey = m_FightingManager.ClockManager.addGameTimeCallBack(Data.CoolDown(), m_CoolDown.CallBack);
    }
    void OnCoolDownTimeOver(TimeCallBack call, Object[] args)
    {
    	try {
    		m_FightingManager.OnAutoFireAirSupport();
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    public void StartFighting()
    {
    	CoolDownKey = m_FightingManager.ClockManager.addGameTimeCallBack(Data.CoolDown(), m_CoolDown.CallBack);
//        m_CoolDown.ResetLength(Data.CoolDown(), null);
    }
}
