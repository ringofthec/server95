package gameserver.network.message.game;

import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.IOServer;
import gameserver.network.protos.game.ProGameInfo;
import gameserver.network.protos.game.ProGameInfo.Proto_VipTime;
import gameserver.network.server.connection.Connection;
import gameserver.utils.Item_Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_Vip;
import commonality.Common;
import commonality.ProductChannel;
import commonality.VmChannel;
import commonality.Common.SETNUMBER_TYPE;
import commonality.Common.TASK_TYPE;
import commonality.ITEM_ID;

public class ClientMessageVip {
	private final static ClientMessageVip instance = new ClientMessageVip();
	public static ClientMessageVip getInstance() { return instance; }
	
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(ClientMessageVip.class);
	public void initialize() {
    	IOServer.getInstance().regMsgProcess(ProGameInfo.Msg_C2G_OpenVip.class, this, "OnOpenVip");
    	IOServer.getInstance().regMsgProcess(ProGameInfo.Msg_C2G_VipTimeOver.class, this, "OnVipTimeOver");
    }
	
	// Vip时间到
	public void OnVipTimeOver(Connection connection, ProGameInfo.Msg_C2G_VipTimeOver message) {
		if (connection.getPlayer().IsVipValid() == true)
			return ;
		
		//更新VipBuff
		int vipLv = connection.getPlayer().getVipLevel();
		ClientMessagePassiveBuff.getInstance().UpdatePassiveBuffByVip(connection, vipLv, 0);
	}
	
	// 开通VIP
	public void OnOpenVip(Connection connection,ProGameInfo.Msg_C2G_OpenVip message) {
		MT_Data_Vip vipData = connection.getPlayer().GetPlayerVipData();
		int needGold = 0;
		long deferTime = 0;
		if (message.getTime() == Proto_VipTime.Day) {
			needGold = vipData.DayGold();
			deferTime = Common.DAY_MILLISECOND;
		}
		else if (message.getTime() == Proto_VipTime.Week) {
			needGold = vipData.WeekGold();
			deferTime = Common.WEEK_MILLISECOND;
		}
		else if (message.getTime() == Proto_VipTime.Month) {
			needGold = vipData.MonthGold();
			deferTime = Common.MONTH_MILLISECOND;
		} else {
			return;
		}
		
		if (!connection.getItem().checkItemEnough(ITEM_ID.GOLD, needGold))
			return;
		
		long s_sum = IPOManagerDb.getInstance().getNextId();
		connection.getItem().setItemNumber(ITEM_ID.GOLD, needGold, SETNUMBER_TYPE.MINUS,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_sum,"",Item_Channel.OPEN_VIP);
		connection.getPlayer().OpenVip(deferTime);
		connection.getTasks().AddTaskNumber(TASK_TYPE.OPEN_VIP, 0, 1, 0);
		LogService.logEvent(connection.getPlayerId(), s_sum, 16, (int)(deferTime / 1000));
		
		// 更新Vip的备用BUFF
		ClientMessagePassiveBuff.getInstance().UpdatePassiveBuffByVip(connection, 0, vipData.Level());
	}
}
