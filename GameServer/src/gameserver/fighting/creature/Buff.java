package gameserver.fighting.creature;

import gameserver.fighting.clock.ClockManager;
import gameserver.fighting.clock.TimeCallBack;
import table.MT_Data_Buff;

public class Buff {
	private MT_Data_Buff mTemplate = null;      //状态信息
    private int mTimerId = -1;					//状态计时器ID
    private int mSpaceTimeId = -1;				//每次触发计时器ID
    private int mStatusNumber = 0;				//BUFF触发次数
    private boolean mInstantProperty = false;	//是否加过属性
    private float mHurtScale = 1;				//状态伤害倍数
    public int TimerId() {
    	return mTimerId;
    }
    public void TimerId(int value) {
    	mTimerId = value;
    }
    public int SpaceTimeId() {
    	return mSpaceTimeId;
    }
    public void SpaceTimeId(int value) {
    	mSpaceTimeId = value;
    }
    public int StatusNumber() {
    	return mStatusNumber;
    }
    public void StatusNumber(int value) {
    	mStatusNumber = value;
    }
    public boolean InstantProperty() {
    	return mInstantProperty;
    }
    public void InstantProperty(boolean b) {
    	mInstantProperty = b;
    }
    public float HurtScale() {
    	return mHurtScale;
    }
    public void HurtScale(float value) {
    	mHurtScale = value;
    }
    public MT_Data_Buff Template() {
    	return mTemplate;
    }
    public void Template(MT_Data_Buff data) {
    	mTemplate = data;
    }
    /// <summary>
    /// BUFF计时器
    /// </summary>
    private TimeCallBack TimeCall() { 
    	return ClockManager.GetInstance().getTimeCallBack(mTimerId);
	}
    /// <summary>
    /// BUFF持续时间
    /// </summary>
    public float LastTime() {
        if (mTimerId != -1 && TimeCall() != null)
            return TimeCall().LastTime();
        return 0f;
    }
    /// <summary>
    /// BUFF剩余时间
    /// </summary>
    public float SurplusTime() {
        if (mTimerId != -1 && TimeCall() != null)
            return TimeCall().SurplusTime();
        return 0f;

    }
    /// <summary>
    /// 状态持续百分比
    /// </summary>
    public float LastPercent(){
        if (mTimerId != -1 && TimeCall() != null)
            return TimeCall().LastPercent();
        return 0f;
    }
    /// <summary>
    /// 状态剩余百分比
    /// </summary>
    public float surplusPercent() {
        if (mTimerId != -1 && TimeCall() != null)
            return TimeCall().SurplusPercent();
        return 0f;
    }
}
