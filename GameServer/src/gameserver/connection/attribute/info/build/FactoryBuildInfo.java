package gameserver.connection.attribute.info.build;

import gameserver.connection.attribute.ConBuildAttr;
import gameserver.connection.attribute.info.BuildInfo;
import gameserver.connection.attribute.info.CorpsInfo;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.protos.game.CommonProto.Proto_BuildState;
import gameserver.network.server.connection.Connection;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;
import gameserver.utils.UtilItem;

import java.util.ArrayList;
import java.util.List;

import table.Int2;
import table.MT_Data_Corps;
import table.MT_TableEnum;
import table.base.TableManager;

import com.commons.util.TimeUtil;
import commonality.Common;
import commonality.Common.SETNUMBER_TYPE;
import commonality.Common.TASK_TYPE;
import commonality.ProductChannel;
import commonality.VmChannel;

import database.CustomPossessions;
import database.DatabaseBuild;

public class FactoryBuildInfo extends BuildInfo {
	public FactoryBuildInfo(Connection connection,ConBuildAttr attribute,DatabaseBuild build) throws Exception{
		super(connection, attribute, build);
	}
	
	@Override
	public long getCorpFinishNeedTime() throws Exception {
		if (m_Build.corpsArray.isEmpty())
			return 0;
		
		long passTime = TimeUtil.GetDateTime() - m_Build.lastoperatetime;
		if (passTime < 0)
			return 0;
		
		long need_time = getCorpNeedTime();
		
		if (need_time > passTime)
			return need_time - passTime;
		else
			return 0;
	}

	public long getCorpNeedTime() throws Exception {
		long need_time = 0;
		for (CustomPossessions corps : m_Build.corpsArray) {
			MT_Data_Corps dataCorps = m_Connection.getCorps().getCorpsDataByTableId(corps.id);
			if (dataCorps == null) {
				continue;
			}
			
			long oneCorpOutTime = calcCorpOutTime(dataCorps);
			need_time += oneCorpOutTime * corps.number;
		}
		return need_time;
	}
	
	// 检测兵种产出升级
	@Override
	protected void checkBuild_impl(long nowTime) throws Exception {
		List<CustomPossessions> finshedCorpsArray = new ArrayList<CustomPossessions>();
		List<UtilItem> outCorpsArray = new ArrayList<UtilItem>();
		for (CustomPossessions corps : m_Build.corpsArray) {
			MT_Data_Corps dataCorps = m_Connection.getCorps().getCorpsDataByTableId(corps.id);
			if (dataCorps == null) {
				corps.number = 0;
				continue;
			}
			
			//产出一个兵需要的时间
			long oneCorpOutTime = calcCorpOutTime(dataCorps);

			// 过去的时间除产出一个兵需要的时间=产出的兵种数量
			long outCorpNumber = (nowTime - m_Build.lastoperatetime) / oneCorpOutTime;

			if (outCorpNumber < 1) {
				// 一个兵都产出不了，那么只更新passtime
				// 这种情况下不产兵，不更新last_operator_time
				break;
			}

			if (corps.number > outCorpNumber) {
				// 要产出的数量大于可以产出的数量
				outCorpsArray.add(new UtilItem(corps.id, (int) outCorpNumber));
				corps.number = (int) (corps.number - outCorpNumber);
				m_Build.lastoperatetime += outCorpNumber * oneCorpOutTime;
				break;
			} else {
				//某一个兵种的兵全部已经产出完成
				finshedCorpsArray.add(corps);
				outCorpsArray.add(new UtilItem(corps.id, corps.number));
				m_Build.lastoperatetime += corps.number * oneCorpOutTime;
			}
		}

		if (!finshedCorpsArray.isEmpty())
			m_Build.corpsArray.removeAll(finshedCorpsArray);

		if (m_Build.corpsArray.isEmpty())
			m_Build.state = Proto_BuildState.NONE_BUILD;

		for (UtilItem pair : outCorpsArray) {
			int corpsId = pair.GetItemId();
			int number = pair.GetCount();
			m_Connection.getCorps().setCorpsNumber(corpsId, number, SETNUMBER_TYPE.ADDITION);
			
			CorpsInfo info = m_Connection.getCorps().getCorpsByTableId(corpsId);
			LogService.logCropUp(corpsId, info.getLevel(), info.getLevel(), number, 0, m_Connection.getPlayerId(), 1, m_Connection.getPlayer().getLevel());
		}
		
		save();
	}

	// 计算产出一个兵需要的时间(毫秒)
	private long calcCorpOutTime(MT_Data_Corps dataCorps) throws Exception {
		int time = m_Connection.getBuffs().getValueByDecPassiveBuff(Common.PASSIVEBUFF_TYPE.CORPS_OUT_TIME, dataCorps.Timer());
		Integer timerType = (Integer)m_DataBase.GetDataByString(MT_TableEnum.TimerType);
		Integer timer = (Integer)m_DataBase.GetDataByString(MT_TableEnum.Timer);
		
		if (timerType == 0)
			time = (int)(time * (1f - timer / 10000f));
		else
			time = time - timer;
		return (long)(time * 1000);
	}
	
	@Override
	protected boolean speedup_imp() throws Exception
	{
		int totalNeedTime = (int)(getCorpFinishNeedTime() / 1000);
		
		long s_num = m_Connection.getCommon().speedUp(totalNeedTime, Item_Channel.SPEED_CORP);
		if (s_num!=-1) {
			for (CustomPossessions out : m_Build.corpsArray){
				m_Connection.getCorps().setCorpsNumber(out.id,out.number, SETNUMBER_TYPE.ADDITION);
				
				//兵种产出加速日志记录
				CorpsInfo crInfo = m_Connection.getCorps().getCorpsByTableId(out.id);
				LogService.logCropUp(out.id,crInfo.getLevel(),crInfo.getLevel(),out.number,s_num,m_Connection.getPlayerId(),1, m_Connection.getPlayer().getLevel());
			}
			
			m_Build.state = Proto_BuildState.NONE_BUILD;
			m_Build.corpsArray.clear();
			save();
			
			m_Connection.getTasks().AddTaskNumber(TASK_TYPE.SPEED_UP, Common.SPEEDUP_TYPE.MAKE_CORPS.Number(), 1, 0);
			return true;
		}
		return false;
	}
	
	// 产出兵种 
	@Override
	public void addMakeCorps(int corps_table_id, int count) throws GameException{
		int level = m_Connection.getCorps().getCorpsLevel(corps_table_id);
		boolean isExisted = false;
		for (CustomPossessions tempCorps : m_Build.corpsArray) {
			// 如果id和等级都相同，那行就增加现有的要造的兵的个数
			if (tempCorps.id == corps_table_id && tempCorps.level == level) {
				isExisted = true;
				tempCorps.number += count;
				break;
			}
		}

		if (!isExisted) {
			CustomPossessions customPossessions = new CustomPossessions();
			customPossessions.id = corps_table_id;
			customPossessions.level = level;
			customPossessions.number = count;
			m_Build.corpsArray.add(customPossessions);
		}

		if (isNormalState()) {
			m_Build.state = Proto_BuildState.OPERATE;
			m_Build.lastoperatetime = TimeUtil.GetDateTime();
		}
		save();
	}

	/** 删除一个指定类型的正在制造中的兵种 */	
	@Override
	public void delMakeCorps(int corps_table_id, int count) throws GameException
	{
		boolean hasCorps = false;
		boolean isClear = false;
		//当前兵种等级
		long s_num = IPOManagerDb.getInstance().getNextId();
		int index = 0;
		for (CustomPossessions corps : m_Build.corpsArray) {
			index++;
			if (corps.id == corps_table_id) {
				count = Math.min(count, corps.number);
				
				MT_Data_Corps mtDataCorps = TableManager.GetInstance().TableCorps().GetElement(corps_table_id + corps.level);
				Int2 needResources = mtDataCorps.NeedResource();
				int temp = (int)(needResources.field2() * 0.5);
				m_Connection.getItem().setItemNumber(needResources.field1(), temp, SETNUMBER_TYPE.ADDITION,
						VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"",Item_Channel.CACLE_MAKE_CORP);
				corps.number -= count;

				if (corps.number == 0)
					isClear = true;

				hasCorps = true;
				break;
			}
		}
		if (hasCorps == false)
			throw new GameException("此兵种不在生产列表 {}", corps_table_id);

		LogService.logEvent(m_Connection.getPlayerId(), s_num, 4, corps_table_id, count);
		if (isClear) {
			m_Build.lastoperatetime = TimeUtil.GetDateTime();
			m_Build.check();
		}

		if (m_Build.corpsArray == null || m_Build.corpsArray.isEmpty())
			m_Build.state = Proto_BuildState.NONE_BUILD;

		save();
	}
	
	@Override
	public boolean IsCorpsLegal(int corpsId) throws Exception{
		@SuppressWarnings("unchecked")
		List<Integer> makeCorps = (List<Integer>)m_DataBase.GetDataByString(MT_TableEnum.Corps);
		for (Integer pair : makeCorps){
			if (pair == corpsId) {
				MT_Data_Corps corpData = TableManager.GetInstance().TableCorps().GetElement(corpsId);
				if (corpData == null)
					return false;
				
				if (m_Connection.getPlayer().CheckPlayerLevel(corpData.UpgradePlayerLv(), true))
					return true;
			}
		}
		return false;
	}
	
	@Override
	public int getMakingPopulation() {
		int population = 0;
		for (CustomPossessions tempCorps : m_Build.corpsArray) {
			MT_Data_Corps tempCorpsData = m_Connection.getCorps().getCorpsDataByTableId(tempCorps.id);
			population += tempCorpsData.Population() * tempCorps.number;
		}
		return population;
	}
	
	// 获取当前建筑正在造兵的总个数
	@Override
	public int getCurMakeCorpNum() {
		int number = 0;
		for (CustomPossessions pos : m_Build.corpsArray)
		{
			number += pos.number;
		}
		return number;
	}
	
	// 获取当前建筑能够造兵的最大个数
	@Override
	public int getCurBuildMaxCorp()
			throws Exception {
		int maxNum = (int)getDataBase().GetDataByString(MT_TableEnum.Max);
		maxNum = m_Connection.getBuffs().getValueByIncPassiveBuff(Common.PASSIVEBUFF_TYPE.ADD_MARKNUM, maxNum);
		return maxNum;
	}
}
