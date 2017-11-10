package gameserver.connection.attribute.info.build;

import gameserver.connection.attribute.ConBuildAttr;
import gameserver.connection.attribute.info.BuildInfo;
import gameserver.logging.LogService;
import gameserver.network.protos.game.CommonProto.Proto_BuildState;
import gameserver.network.server.connection.Connection;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;
import table.MT_Data_Corps;
import table.MT_TableEnum;
import com.commons.util.TimeUtil;
import commonality.Common;
import commonality.Common.TASK_TYPE;
import database.CustomPossessions;
import database.DatabaseBuild;

public class InstituteBuildInfo extends BuildInfo {
	public InstituteBuildInfo(Connection connection,ConBuildAttr attribute,DatabaseBuild build) throws Exception{
		super(connection, attribute, build);
	}
	
	public long getOperNeedTimeByMilliSecond() throws Exception {
		if ( m_Build.corpsArray.isEmpty())
			return 0;
		
		Integer timerType = (Integer)m_DataBase.GetDataByString(MT_TableEnum.TimerType);
		Integer timer = (Integer)m_DataBase.GetDataByString(MT_TableEnum.Timer);
		CustomPossessions corps = m_Build.corpsArray.get(0);
		MT_Data_Corps dataCorps = m_Connection.getCorps().getCorpsDataByTableId(corps.id);
		int needTime = 0;
		if (timerType == 0)
			needTime = (int)(dataCorps.UpgradeNeedTime() * (1f - timer / 10000f));
		else
			needTime = dataCorps.UpgradeNeedTime() > timer ? (dataCorps.UpgradeNeedTime() - timer) : 0;

		needTime = m_Connection.getBuffs().getValueByDecPassiveBuff(Common.PASSIVEBUFF_TYPE.CORPS_UP_TIME, needTime);
		if (m_player.getBelegionId()!=0 && needTime > 0)
			needTime = queryHelp(needTime);

		return needTime * 1000L;
	}
	
	@Override
	public long getInstituteFinishLeftTimeByMilliSecond() throws Exception {
		long passTime = TimeUtil.GetDateTime() - m_Build.lastoperatetime;
		long need_time = getOperNeedTimeByMilliSecond();
		
		if (passTime > need_time)
			return 0;
		else
			return need_time - passTime;
	}
	
	@Override
	protected void checkBuild_impl(long nowTime) throws Exception {
		long leftTime = getInstituteFinishLeftTimeByMilliSecond();
		if (leftTime > 0)
			return ;
		
		finish(0);
	}

	private void finish(long s_num) throws Exception {
		CustomPossessions corp = m_Build.corpsArray.get(0);
		deleteHelp();
		m_Build.state = Proto_BuildState.NONE_BUILD;
		m_Connection.getCorps().corpsLevelUp(corp.id);
		m_Build.corpsArray.clear();
		logger.info("=====ID为{}的兵种升级成功 ",corp.id);
		
		int newLevel = m_Connection.getCorps().getCorpsByTableId(corp.id).getLevel();
		LogService.logCropUp(corp.id,newLevel - 1,newLevel,0,s_num,m_Connection.getPlayerId(),1, m_Connection.getPlayer().getLevel());
		save();
	}
	
	@Override
	protected boolean speedup_imp() throws Exception {
		long totalNeedTime = getInstituteFinishLeftTimeByMilliSecond();
		
		long s_num = m_Connection.getCommon().speedUp((int)(totalNeedTime/1000), Item_Channel.SPEED_INS_UP);
		if (s_num!=-1) {
			finish(s_num);
			m_Connection.getTasks().AddTaskNumber(TASK_TYPE.SPEED_UP, Common.SPEEDUP_TYPE.CORPS_UPGRADE.Number(), 1, 0);
			return true;
		}
		
		return false;
	}
	
	/** 兵种升级 
	 * @throws GameException */
	@Override
	public void corpsUpgrade(int corps_table_id) throws GameException{
		if (!isInstitute())
			throw new GameException("该建筑不能进行兵种升级操作");
		if (!m_Build.corpsArray.isEmpty()) 
			throw new GameException("一次只能升级一个兵种");
		CustomPossessions customPossessions = new CustomPossessions();
		customPossessions.id = corps_table_id;
		customPossessions.number = 1;
		m_Build.corpsArray.add(customPossessions);
		if (isNormalState()) {
			m_Build.state = Proto_BuildState.OPERATE;
			m_Build.lastoperatetime = TimeUtil.GetDateTime();
		}
		save();
	}
	
	@Override
	public boolean isMaxOut() throws Exception {
		return m_Build.corpsArray != null && 
				m_Build.corpsArray.size() >= (int)m_DataBase.GetDataByString(MT_TableEnum.Max);
	}
	
}
