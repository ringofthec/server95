package gameserver.connection.attribute.info.build;

import table.Int2;
import table.MT_TableEnum;

import com.commons.util.TimeUtil;
import commonality.ProductChannel;
import commonality.VmChannel;
import commonality.Common.SETNUMBER_TYPE;

import database.DatabaseBuild;
import gameserver.connection.attribute.ConBuildAttr;
import gameserver.connection.attribute.info.BuildInfo;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.server.connection.Connection;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;

public class AssetsBuildInfo extends BuildInfo{
	public AssetsBuildInfo(Connection connection,ConBuildAttr attribute,DatabaseBuild build) throws Exception{
		super(connection, attribute, build);
		UpdateEarningsTime();
	}
	
	@Override
	public void gatherResource(){
		try {
			if (!isNormalState()) 
				throw new GameException("当前状态不能收取金币 " + getState());
			
			long nowTime = TimeUtil.GetDateTime();
			//换算成秒 持续时间 (超过最大收取时间按最大收取时间算)
			double lastTime = ((Long)Math.min((nowTime - m_Build.gathertime)/1000, (int)m_DataBase.GetDataByString(MT_TableEnum.FullTime))).doubleValue();
			Double gather = Math.floor(lastTime / m_EarningsTime);
			if (gather < 1)
				return ;
			double remainder = gather * m_EarningsTime;
			Int2 Earnings = (Int2)m_DataBase.GetDataByString(MT_TableEnum.Earnings);
			long costId = IPOManagerDb.getInstance().getNextId();
			m_Connection.getItem().setItemNumber(Earnings.field1(), gather.intValue(), SETNUMBER_TYPE.ADDITION,
					VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,costId,"",Item_Channel.GATHER);
			LogService.logEvent(m_Connection.getPlayerId(), costId, 3);
			m_Build.gathertime = nowTime - ((Double)(lastTime - remainder)).longValue() * 1000;
			save();
		} catch (Exception e) {
			logger.error("gatherResource is error : " , e);
		}
	}
	
	@Override
	protected void UpdateEarningsTime() throws Exception {
		int type = (int)m_DataBase.GetDataByString(MT_TableEnum.EarningsType);
		Int2 Earnings = (Int2)m_DataBase.GetDataByString(MT_TableEnum.Earnings);
		m_EarningsTime = (type == 0 ? 3600.0 : 86400.0) / Earnings.field2().doubleValue();
	}
}
