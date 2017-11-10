package gameserver.fighting.clock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClockManager {
	public Map<Integer, TimeCallBack> mTimeCallBacks = new HashMap<Integer, TimeCallBack>();	//计时器数组
    private List<TimeCallBack> mAddCallBack = new ArrayList<TimeCallBack>();					//要添加的计时器数组 下一帧统一添加
    private List<Integer> mDelCallBack = new ArrayList<Integer>();								//要删除的计时器数组 下一帧统一删除
    private int mValidKey = 0;																	//计时器有效ID
    
    private static ClockManager m_Instance = null;
    public static ClockManager GetInstance() {
    	if (m_Instance == null)
    		m_Instance = new ClockManager();
    	return m_Instance;
    }
    public void Shutdown() {
    	mTimeCallBacks.clear();
    	mAddCallBack.clear();
    	mDelCallBack.clear();
    }
	public void OnUpdate(float deltaTime)
    {
        for (TimeCallBack callBack : mAddCallBack)
        {
            mTimeCallBacks.put(callBack.Key, callBack);
        }
        for (Integer key : mDelCallBack)
        {
            if (mTimeCallBacks.containsKey(key)) mTimeCallBacks.remove(key);
        }
        mAddCallBack.clear();
        mDelCallBack.clear();
        List<Integer> endKeys = new ArrayList<Integer>();
        for (Map.Entry<Integer, TimeCallBack> pair : mTimeCallBacks.entrySet())
        {
            int Key = pair.getKey();
            TimeCallBack Value = pair.getValue();
            if (Value.OnUpdate(deltaTime))
                endKeys.add(Key);
        }
        for (Integer key : endKeys)
        {
            removeTimeCallBack(key);
        }
    }
    private int GetFreeKey()
    {
        ++mValidKey;
        if (mValidKey == Integer.MAX_VALUE) mValidKey = 0;
        return mValidKey;
    }
    /// <summary>
    /// 添加计时器
    /// </summary>
    public int addGameTimeCallBack(float length, OnTimeCallBack callBack, Object... args)
    {
        int Key = GetFreeKey();
        mAddCallBack.add(new TimeCallBack(Key, length, callBack, args));
        return Key;
    }
    public void removeTimeCallBack(TimeCallBack timeCall)
    {
        if (timeCall != null) removeTimeCallBack(timeCall.getKey());
    }
    public void removeTimeCallBack(int Key)
    {
        mDelCallBack.add(Key);
    }
    public TimeCallBack getTimeCallBack(int Key)
    {
        if (mTimeCallBacks.containsKey(Key))
        {
            return mTimeCallBacks.get(Key);
        }
        for (TimeCallBack callBack : mAddCallBack)
        {
            if (callBack != null && callBack.getKey() == Key)
                return callBack;
        }
        return null;
    }
    public int ResetTimeCallBack(TimeCallBack timeCallBack)
    {
        int Key = GetFreeKey();
        timeCallBack.Key = Key;
        mAddCallBack.add(timeCallBack);
        return Key;
    }
}
