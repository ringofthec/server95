package gameserver.fighting.clock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeCallBack {
	private static final Logger logger = LoggerFactory.getLogger(TimeCallBack.class);
	/* 计时器ID */
    public int Key;
    /* 计时器时长 */
    public float Length;
    /* 计时器回调 */
    public OnTimeCallBack CallBack;
    /* 计时器是否已经结束 */
    public boolean IsOver;
    /* 计时器参数 */
    private Object[] m_Args;
    private float m_SurplusTime;
    public float SurplusTime() {
    	return m_SurplusTime;
    }
    /// <summary> 计时器持续时间 </summary>
    public float LastTime() {
        return Length - m_SurplusTime;
    }
    /// <summary> 持续时间百分比 </summary>
    public float LastPercent() {
    	return LastTime() / Length;
    }
    /// <summary> 剩余时间百分比 </summary>
    public float SurplusPercent() {
    	return SurplusTime() / Length;
    }
    public TimeCallBack(OnTimeCallBack callBack)
    {
        this.CallBack = callBack;
        this.IsOver = true;
    }
    public TimeCallBack(int key, float length, OnTimeCallBack callBack, Object[] args)
    {
        Initialize(key, length, args);
        this.CallBack = callBack;
    }
    private void Initialize(int key, float length, Object[] args)
    {
        this.Key = key;
        this.Length = length;
        m_Args = args;
        m_SurplusTime = length;
        IsOver = false;
    }
    public int getKey() {
    	return Key;
    }
    /* 重置计时器时间 不检测时间长度 */
    public int ResetLength(float length, Object[] args)
    {
        this.Length = length;
        this.m_SurplusTime = length;
        this.m_Args = args;
        if (IsOver) ClockManager.GetInstance().ResetTimeCallBack(this);
        IsOver = false;
        return Key;
    }
    public void Shutdown()
    {
        IsOver = true;
        ClockManager.GetInstance().removeTimeCallBack(this);
    }
    /* 计时器更新 s */
    public boolean OnUpdate(float deltaTime)
    {
    	m_SurplusTime -= deltaTime;
        if (m_SurplusTime < 0)
        {
            Call();
            return true;
        }

        return false;
    }
    private void Call()
    {
        try {
            if (IsOver) return;
            IsOver = true;
            if (CallBack != null) CallBack.run(this, m_Args);
        } catch (Exception ex) {
                logger.error("TimeCallBack is error : {0}", ex.toString());
        }
    }
	//------------------------------------------------------------
//	private int mKey;                                                       //计时器ID
//    private OnTimeCallBack mCallBack;                                       //计时器回调
//    private Object[] mArgs;                                                 //计时器参数
//    private float mLength;                                                  //计时器时长
//    private float mSurplusTime;                                             //剩余时间
//    public TimeCallBack() { }
//    public TimeCallBack(int key, float length, OnTimeCallBack callBack, Object[] args)
//    {
//        mKey = key;
//        mLength = length;
//        mCallBack = callBack;
//        mArgs = args;
//        mLength = length;
//        mSurplusTime = mLength;
//    }
//    /// <summary>
//    /// Key
//    /// </summary>
//    public int getKey() { return mKey; }
//    /// <summary>
//    /// 计时器更新 
//    /// </summary>
//    /// <returns>true 表示计时器完成 false表示没有完成</returns>
//    public boolean OnUpdate(float deltaTime)
//    {
//        mSurplusTime -= deltaTime;
//        if (mSurplusTime <= 0)
//        {
//            try
//            {
//                if (mCallBack != null) mCallBack.run(this,mArgs);
//            }
//            catch (Exception e)
//            {
//            	logger.error("TimeCallBack is error : ", e);
//            }
//            return true;
//        }
//        return false;
//    }
}
