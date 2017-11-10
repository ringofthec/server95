package gameserver.connection.attribute;

import gameserver.connection.attribute.info.BuildInfo;
import gameserver.ipo.IPOManagerDb;
import gameserver.network.message.game.ClientMessageCommon;
import gameserver.network.protos.game.ProPvp;
import gameserver.network.server.connection.ConnectionAttribute;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;
import gameserver.utils.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_commodity;
import table.MT_TableEnum;
import table.base.TableManager;

import commonality.Common;
import commonality.Common.LIMIT_TYPE;
import commonality.Common.SETNUMBER_TYPE;
import commonality.ITEM_ID;
import commonality.ProductChannel;
import commonality.VmChannel;

public class ConCommonAttr extends ConnectionAttribute {
	private static final Logger logger = LoggerFactory.getLogger(ConCommonAttr.class);
	private Map<Common.LIMIT_TYPE, Integer> m_LimitArray = new HashMap<Common.LIMIT_TYPE, Integer>();
	private int dirtyMask = 0;
	
	@Override
	protected void Initialize_impl() {
	}
	public void CheckUpline()
	{
		InitCommon();
	}
	public void InitCommon()
	{
		UpdatePeople(false);
		UpdatePvpNumber(false);
		UpdateMaxCp(false);
		UpdateBagCount(false);
		UpdateQueue(false);
		UpdateCpUseCount(false);
		UpdatePvpUseCount(false);
	}
	public int GetValue(Common.LIMIT_TYPE type){
		if (m_LimitArray.containsKey(type))
			return m_LimitArray.get(type);
		return 0;
	}
	private void SetValue(Common.LIMIT_TYPE type,int value)
	{
		m_LimitArray.put(type, value);
	}
	
	public void UpdatePeople() {
		UpdatePeople(true);
	}
	
	/** 更新人口上限 */
	public void UpdatePeople(boolean issync)
	{
		try {
			int count = 0;
			Collection<BuildInfo> builds = m_Con.getBuild().getBuildArray();
			for (BuildInfo build : builds)
			{
				if (build.isFlag())
				{
					count += (int)build.getDataBase().GetDataByString(MT_TableEnum.PeopleNum);
				}
				else if (build.isMainCity())
				{
					count += (int)build.getDataBase().GetDataByString(MT_TableEnum.PeopleNum);
				}
			}
			count = m_Con.getBuffs().getValueByIncPassiveBuff(Common.PASSIVEBUFF_TYPE.PEOPLEMAX, count);
			SetValue(Common.LIMIT_TYPE.PEOPLE,count);
			if (issync) 
				setDirtyMask(Common.LIMIT_TYPE.PEOPLE);
		} catch (Exception e) {
			logger.error("UpdatePeople is error : ", e);
		}
	}
	
	private void setDirtyMask(Common.LIMIT_TYPE id) {
		dirtyMask = dirtyMask | 1 << id.Number();
	}
	
	private boolean isDirty(Common.LIMIT_TYPE id) {
		return (dirtyMask & (1 << id.Number())) != 0;
	}
	
	public void onlineSync() {
		dirtyMask = 0xffffffff;
		CheckData();
	}
	
	@Override
	public void CheckData() {
		if (dirtyMask == 0)
			return ;
		
		List<ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.Builder> list = new ArrayList<ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.Builder>();
		
		if (isDirty(Common.LIMIT_TYPE.PEOPLE)){
			ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.Builder info = ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.newBuilder();
			info.setType(ProPvp.Proto_ActionType.PEOPLE_MAX);
			info.setMaxNum(m_Con.getCommon().GetValue(LIMIT_TYPE.PEOPLE));
			list.add(info);
		}
		
		if (isDirty(Common.LIMIT_TYPE.PVP_NUMBER)){
			ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.Builder info = ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.newBuilder();
			info.setType(ProPvp.Proto_ActionType.PVP);
			info.setDoNum(m_Con.getPlayer().getPvpNumber());
			info.setMaxNum(m_Con.getCommon().GetValue(LIMIT_TYPE.PVP_NUMBER));
			list.add(info);
		}
		
		if (isDirty(Common.LIMIT_TYPE.CP)){
			ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.Builder info = ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.newBuilder();
			info.setType(ProPvp.Proto_ActionType.CP);
			info.setDoNum(m_Con.getItem().getItemCountByTableId(ITEM_ID.CP));
			info.setMaxNum(m_Con.getCommon().GetValue(LIMIT_TYPE.CP));
			list.add(info);
		}
		
		if (isDirty(Common.LIMIT_TYPE.USE_CP_ITEM)) {
			try {
				ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.Builder info = ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.newBuilder();
				info.setType(ProPvp.Proto_ActionType.USE_CP_ITEM);
				info.setDoNum(m_Con.getPlayer().getCount(0));
				info.setMaxNum(m_Con.getCommon().GetValue(LIMIT_TYPE.USE_CP_ITEM));
				list.add(info);
			} catch(Exception e) {}
		}
		
		if (isDirty(Common.LIMIT_TYPE.USE_PVP_ITEM)) {
			try {
				ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.Builder info = ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.newBuilder();
				info.setType(ProPvp.Proto_ActionType.USE_PVP_ITEM);
				info.setDoNum(m_Con.getPlayer().getCount(1));
				info.setMaxNum(m_Con.getCommon().GetValue(LIMIT_TYPE.USE_PVP_ITEM));
				list.add(info);
			} catch(Exception e) {}
		}
		
		if (isDirty(Common.LIMIT_TYPE.QUEUE)){
			try {
				ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.Builder info = ProPvp.Msg_G2C_doSthNum.Proto_CountInfo.newBuilder();
				info.setType(ProPvp.Proto_ActionType.QUEUE);
				info.setDoNum(Util.GetCurWorkNum(m_Con));
				info.setMaxNum(m_Con.getPlayer().getQueuesize());
				list.add(info);
			} catch (Exception e) {
				logger.info("CheckData QUEUE err", e);
			}
			
		}
		dirtyMask = 0;
		ClientMessageCommon.getInstance().UpdateCountInfo(m_Con, list);
	}
	
	public void UpdatePvpNumber() {
		UpdatePvpNumber(true);
	}
	
	/** 更新PVP次数 */
	public void UpdatePvpNumber(boolean issync){
		try {
			int count = m_Con.getBuffs().getValueByIncPassiveBuff(Common.PASSIVEBUFF_TYPE.PVPCOUNT, Common.PVP_MAX_NUM);
			SetValue(Common.LIMIT_TYPE.PVP_NUMBER,count);
			if (issync) 
				setDirtyMask(Common.LIMIT_TYPE.PVP_NUMBER);
		} catch (Exception e) {
			logger.error("UpdatePvpNumber is error : ", e);
		}
	}
	
	public void UpdateMaxCp() {
		UpdateMaxCp(true);
	}
	/** 更新体力上限 */
	public void UpdateMaxCp(boolean issync){
		try {
			int oldMax = GetValue(Common.LIMIT_TYPE.CP);
			
			int maxCp = m_Con.getPlayer().GetPlayerExpData().CP();
			maxCp = m_Con.getBuffs().getValueByIncPassiveBuff(Common.PASSIVEBUFF_TYPE.CPMAX, maxCp);
			SetValue(Common.LIMIT_TYPE.CP,maxCp);
			
			int curCp = m_Con.getItem().getItemCountByTableId(ITEM_ID.CP);
			
			if (issync && curCp == oldMax && curCp < maxCp)
				m_Con.getPlayer().UpdatePlayerCpTime();
			
			if (issync) 
				setDirtyMask(Common.LIMIT_TYPE.CP);
		} catch (Exception e) {
			logger.error("UpdateMaxCp is error : ", e);
		}
	}
	
	public void UpdateCpUseCount() {
		UpdateCpUseCount(true);
	}
	public void UpdateCpUseCount(boolean issync) {
		try {
			SetValue(Common.LIMIT_TYPE.USE_CP_ITEM,10);
			if (issync) 
				setDirtyMask(Common.LIMIT_TYPE.USE_CP_ITEM);
		} catch (Exception e) {
			logger.error("UpdateMaxCp is error : ", e);
		}
	}
	
	public void UpdatePvpUseCount() {
		UpdatePvpUseCount(true);
	}
	public void UpdatePvpUseCount(boolean issync) {
		try {
			SetValue(Common.LIMIT_TYPE.USE_PVP_ITEM,10);
			if (issync) 
				setDirtyMask(Common.LIMIT_TYPE.USE_PVP_ITEM);
		} catch (Exception e) {
			logger.error("UpdateMaxCp is error : ", e);
		}
	}
	
	public void UpdateBagCount() {
		UpdateBagCount(true);
	}
	
	/** 背包上限 */
	public void UpdateBagCount(boolean issync){
		try {
			int count = 50;
			count = m_Con.getBuffs().getValueByIncPassiveBuff(Common.PASSIVEBUFF_TYPE.BAGMAX, count);
			SetValue(Common.LIMIT_TYPE.BAG_COUNT,count);
			if (issync) 
				setDirtyMask(Common.LIMIT_TYPE.BAG_COUNT);
		} catch (Exception e) {
			logger.error("UpdateBagCount is error : ", e);
		}
	}
	
	public void UpdateQueue() {
		UpdateQueue(true);
	}
	/** 队列上限 */
	public void UpdateQueue(boolean issync){
		try {
			int count = m_Con.getBuffs().getValueByIncPassiveBuff(Common.PASSIVEBUFF_TYPE.QUEUEMAX, 5);
			SetValue(Common.LIMIT_TYPE.QUEUE,count);
			if (issync) 
				setDirtyMask(Common.LIMIT_TYPE.QUEUE);
		} catch (Exception e) {
			logger.error("UpdateQueue is error : ", e);
		}
	}
	
	// 根据剩余时间，判断并扣除加速的消耗
	public long speedUp(int leftTime, Item_Channel itemch) throws GameException {
		MT_Data_commodity obj = null;
		for (MT_Data_commodity data : TableManager.GetInstance().Tablecommodity().Datas().values())
		{
			if (data.itemTableId() == ITEM_ID.SPEED_UP) {
				obj = data;
				break;
			}
		}
		if (obj == null)
			return -1;
		
		// 根据剩余时间计算需要的道具个数
		int num = leftTime / Common.SPPED_ITEM_TIME;
		if (leftTime % 300 != 0)
			++num;
		
		int count = m_Con.getItem().getItemCountByTableId(ITEM_ID.SPEED_UP);// 玩家拥有的道具个数
		//加速道具材料不足
		long s_num = IPOManagerDb.getInstance().getNextId();
		if (count < num) {
			// 加速道具不足，则计算需要的补偿钱数
			int needCount = (num - count) * obj.Attr().field2();
			if (!m_Con.getItem().checkItemEnough(obj.Attr().field1(), needCount))
				return -1;
			
			if (count > 0)
				m_Con.getItem().setItemNumber(ITEM_ID.SPEED_UP, count, SETNUMBER_TYPE.MINUS,
						VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"",itemch);    // 扣除材料
			m_Con.getItem().setItemNumber(obj.Attr().field1(), needCount, SETNUMBER_TYPE.MINUS,
					VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"", itemch);		// 扣除金币
			
		} else {
			m_Con.getItem().setItemNumber(ITEM_ID.SPEED_UP,num, SETNUMBER_TYPE.MINUS,
					VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"", itemch);
		}
		IPOManagerDb.getInstance().LogBuyService(m_Con, "speedup", s_num);
		
		return s_num;
	}
}
