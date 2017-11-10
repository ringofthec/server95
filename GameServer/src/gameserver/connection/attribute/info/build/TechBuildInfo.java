package gameserver.connection.attribute.info.build;

import gameserver.connection.attribute.ConBuildAttr;
import gameserver.connection.attribute.info.BuildInfo;
import gameserver.network.protos.game.CommonProto.Proto_BuildState;
import gameserver.network.server.connection.Connection;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;

import java.util.List;

import table.MT_Data_TechInfo;
import table.MT_TableEnum;
import table.base.TableManager;

import com.commons.util.TimeUtil;
import commonality.Common;
import commonality.Common.TASK_TYPE;

import database.CustomPossessions;
import database.DatabaseBuild;

public class TechBuildInfo extends BuildInfo {
	public TechBuildInfo(Connection connection,ConBuildAttr attribute,DatabaseBuild build) throws Exception{
		super(connection, attribute, build);
	}
	
	@Override
	public long getTechLeftNeedTimeByMilliSecond() throws Exception {
		long passTime = TimeUtil.GetDateTime() - m_Build.lastoperatetime;
		long need_time = calcCorpUpgradeNeedTimeBySecond() * 1000L;
		
		if (passTime > need_time)
			return 0;
		else
			return need_time - passTime;
	}
	
	// 计算升级需要的时间
	public int calcCorpUpgradeNeedTimeBySecond() throws Exception {
		if ( m_Build.corpsArray.isEmpty())
			return 0;
		
		int totalNeedTime = 0;
		Integer timerType = (Integer)m_DataBase.GetDataByString(MT_TableEnum.TimerType);
		Integer timer = (Integer)m_DataBase.GetDataByString(MT_TableEnum.Timer);
		CustomPossessions corps = m_Build.corpsArray.get(0);
		MT_Data_TechInfo data = m_Connection.getTech().getTechData(corps.id);
		if (timerType == 0)
			totalNeedTime = (int)(data.UpgradeNeedTime() * (1f - timer / 10000f));
		else
			totalNeedTime += data.UpgradeNeedTime() > timer ? (data.UpgradeNeedTime() - timer) : 0;
			
		if (m_player.getBelegionId()!=0)
			totalNeedTime = queryHelp(totalNeedTime);
		
		return totalNeedTime;
	}

	// 检测科技科技升级 
	@Override
	protected void checkBuild_impl(long nowTime) throws Exception {
		long leftTime = getTechLeftNeedTimeByMilliSecond();
		if (leftTime > 0)
			return ;
		
		finish(0);
	}

	private void finish(long sum_cost) throws Exception {
		CustomPossessions corp = m_Build.corpsArray.get(0);
		deleteHelp();
		m_Build.state = Proto_BuildState.NONE_BUILD;
		m_Connection.getTech().techUpgrade(corp.id);
		m_Build.corpsArray.clear();
		logger.info("=====ID为{}的科技升级成功 ",corp.id);

		save();
	}
	
	@Override
	protected boolean speedup_imp() throws Exception {
		long totalNeedTime = getTechLeftNeedTimeByMilliSecond();
		long s_num = m_Connection.getCommon().speedUp((int)(totalNeedTime / 1000), Item_Channel.SPEED_TEC_UP);
		if (s_num!=-1) {
			finish(s_num);
			m_Connection.getTasks().AddTaskNumber(TASK_TYPE.SPEED_UP, Common.SPEEDUP_TYPE.TECH_UPGRADE.Number(), 1, 0);
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean IsTechLegal(int techId) throws Exception {
		@SuppressWarnings("unchecked")
		List<Integer> makeCorps = (List<Integer>)m_DataBase.GetDataByString(MT_TableEnum.Tech);
		for (Integer pair : makeCorps){
			if (pair == techId) {
				MT_Data_TechInfo corpData = TableManager.GetInstance().TableTechInfo().GetElement(techId);
				if (corpData == null)
					return false;
				return true;
			}
		}
		return false;
	}

	/** 科技升级 */
	@Override
	public void techUpgrade(int tech_table_id) throws GameException{
		//该科技已经在升级不能重复升级
		if (!m_Build.corpsArray.isEmpty()) 
			throw new GameException("已经有科技在升级");
		CustomPossessions customPossessions = new CustomPossessions();
		customPossessions.id = tech_table_id;
		customPossessions.number = 1;
		m_Build.corpsArray.add(customPossessions);
		if (isNormalState()){
			m_Build.state = Proto_BuildState.OPERATE;
			m_Build.lastoperatetime = TimeUtil.GetDateTime();
		}
		save();	
	}
}
