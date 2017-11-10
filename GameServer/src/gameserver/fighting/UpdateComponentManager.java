package gameserver.fighting;

import gameserver.fighting.creature.FightingObject;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateComponentManager {
	private static final Logger logger = LoggerFactory.getLogger(UpdateComponentManager.class);
    private List<FightingObject> m_listComponents = new ArrayList<FightingObject>();
    private List<FightingObject> m_listRemoveComponents = new ArrayList<FightingObject>();
    private float m_timeLine = 0;
    
    public void Shutdown() {
    	m_listComponents.clear();
    	m_listRemoveComponents.clear();
    }
    
    public float GetTimeLine()
    {
    	return m_timeLine;
    }
    public void StartFighting()
    {
        m_timeLine = 0;
    }
    public void OverFighting()
    {
    	logger.info("结束战斗 战斗时长为 " + m_timeLine);
    }
    public void AddUpdateComponent(FightingObject component)
    {
        if (!m_listComponents.contains(component))
            m_listComponents.add(component);
    }
    public void DelUpdateComponent(FightingObject component)
    {
        m_listRemoveComponents.add(component);
    }
    public void OnUpdate_impl(float deltaTime)
    {
        m_timeLine += deltaTime;
        int size = m_listRemoveComponents.size();
        if (size > 0)
        {
            for (int i = 0; i < size; ++i)
            {
                m_listComponents.remove(m_listRemoveComponents.get(i));
            }
            m_listRemoveComponents.clear();
        }
        size = m_listComponents.size();
        if (size > 0)
        {
            for (int i = 0; i < size; ++i)
            {
            	try {
					m_listComponents.get(i).OnUpdate(deltaTime);
				} catch (Exception e) {
					logger.error("OnUpdate_impl is error : ",e);
				}
            }
        }
    }
}
