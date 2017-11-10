package gameserver.network.message.game;

import gameserver.connection.attribute.info.AirSupportInfo;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.IOServer;
import gameserver.network.protos.game.ProAirSupport;
import gameserver.network.server.connection.Connection;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;
import gameserver.utils.TransFormArgs;
import table.MT_Data_AirSupport;
import table.base.TABLE;
import table.base.TableManager;

import commonality.Common.AIRSUPPORT_OPERATE;
import commonality.Common.SETNUMBER_TYPE;
import commonality.Common.TASK_TYPE;
import commonality.ProductChannel;
import commonality.PromptType;
import commonality.VmChannel;

public class ClientMessageAirSupport {
	private final static ClientMessageAirSupport instance = new ClientMessageAirSupport();
	public static ClientMessageAirSupport getInstance() { return instance; }
	public void initialize()
	{
		IOServer.getInstance().regMsgProcess(ProAirSupport.Msg_C2G_PlaneUpgrade.class, this, "OnPlaneUpgrade");
		IOServer.getInstance().regMsgProcess(ProAirSupport.Msg_C2G_AirSupportBuy.class, this, "OnAirSupportBuy");
		IOServer.getInstance().regMsgProcess(ProAirSupport.Msg_C2G_AirSupportRepair.class, this, "OnAirSupportRepair");
		IOServer.getInstance().regMsgProcess(ProAirSupport.Msg_C2G_AirSupportOutFighting.class, this, "OnAirSupportOutFighting");
	}

	// 飞机升级
	public void OnPlaneUpgrade(Connection connection, ProAirSupport.Msg_C2G_PlaneUpgrade message) throws GameException{
		int tableId = message.getTableId();
		AirSupportInfo info = connection.getAir().GetAirSupportByTableId(tableId);
		if (info == null)
			throw new GameException("此{}空中支援还未购买", tableId);
		
		MT_Data_AirSupport airData = info.GetData();
		if (airData == null)
			throw new GameException("{}空中支援不存在 ", tableId + info.GetLevel());
		int oldLev = info.GetLevel();
		MT_Data_AirSupport nextData = TableManager.GetInstance().TableAirSupport().GetElement(tableId + info.GetLevel() + 1);
		if (nextData == null)
			throw new GameException("飞机已经达到最大等级了");
		if (TABLE.IsInvalid(airData.UpgradeNeed()))
			throw new GameException("该飞机没有配置升级需要的资源!");
		if (!connection.getItem().checkItemEnoughByInt2(airData.UpgradeNeed()))
			return ;
		long nextId = IPOManagerDb.getInstance().getNextId();
		connection.getItem().setItemNumberByInt2(airData.UpgradeNeed(), SETNUMBER_TYPE.MINUS,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,nextId,"",Item_Channel.PLANE_UP);
		info.UpgradePlane();
		connection.getTasks().AddTaskNumber(TASK_TYPE.AIRSUPPORT_OPERATE, message.getTableId(), 1, AIRSUPPORT_OPERATE.LEVEL_UP.Number());
		
		connection.ShowPrompt(PromptType.TARGET_LEVEL_UP, TransFormArgs.CreateAirArgs(tableId), info.GetLevel());
		
		//记录飞机升级日志
		AirSupportInfo airSupportInfo = connection.getAir().GetAirSupportByTableId(tableId);
		LogService.logPlaneLevelup(tableId, oldLev,airSupportInfo.GetLevel(),nextId,connection.getPlayerId(), connection.getPlayer().getLevel());
		connection.setNeed_recalc_fight_val(true);
	}
	
	// 购买飞机
	public void OnAirSupportBuy(Connection connection,ProAirSupport.Msg_C2G_AirSupportBuy message) throws GameException{
		int tableId = message.getTableId();
		AirSupportInfo info = connection.getAir().GetAirSupportByTableId(tableId);
		if (info != null)
			throw new GameException(" 此{}空中支援已经购买过", tableId);
		
		MT_Data_AirSupport airData = TableManager.GetInstance().TableAirSupport().GetElement(tableId);
		if (airData == null)
			throw new GameException("{}空中支援不存在 ", tableId);
		if (!connection.getPlayer().CheckPlayerLevel(airData.Level(), true))
			return ;
		if (!connection.getItem().checkItemEnough(airData.Price().field1(),airData.Price().field2()))
			return ;
		
		long serial_num = IPOManagerDb.getInstance().getNextId();
		connection.getItem().setItemNumber(airData.Price().field1(),airData.Price().field2(),
				SETNUMBER_TYPE.MINUS, VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, serial_num, "",Item_Channel.BUY_PLANE);
		IPOManagerDb.getInstance().LogBuyService(connection, "buyairsupport", serial_num);
		
		connection.getAir().AirSupportBuy(airData);
		connection.setNeed_recalc_fight_val(true);
	}
	
	// 飞机出战或休息
	public void OnAirSupportOutFighting(Connection connection,ProAirSupport.Msg_C2G_AirSupportOutFighting message) throws GameException{
		if (connection.getAir().AirSupportOutFighting(message.getTableId(), message.getType())) {
			connection.getTasks().AddTaskNumber(TASK_TYPE.AIRSUPPORT_OPERATE, 
					message.getTableId(), 1, 
					message.getType() ? AIRSUPPORT_OPERATE.OUT_FIGHTING.Number() : AIRSUPPORT_OPERATE.TAKE_BACK.Number());
			
			//记录飞机出战或者休息的日志
			LogService.logEvent(connection.getPlayerId(),-1,18,message.getType()?1:0);
		}
		
	}
	
	// 修复飞机最大耐久度
	public void OnAirSupportRepair(Connection connection,ProAirSupport.Msg_C2G_AirSupportRepair message) 
			throws GameException
	{
		int tableId = message.getTableId();
		AirSupportInfo info = connection.getAir().GetAirSupportByTableId(tableId);
		if (info == null)
			throw new GameException("此空中支援还未购买 {}", tableId);
		
		MT_Data_AirSupport airData = info.GetData();
		if (info.GetDurable() >= airData.MaxDurable())
			throw new GameException("此空中支援耐久已满不用修理 {}", tableId);
		int needNumber = airData.RepairPrice().field2() * (airData.MaxDurable() - info.GetDurable());
		if (!connection.getItem().checkItemEnough(airData.RepairPrice().field1(), needNumber))
			return ;
		
		long serial_num = IPOManagerDb.getInstance().getNextId();
		connection.getItem().setItemNumber(airData.RepairPrice().field1(), needNumber
				, SETNUMBER_TYPE.MINUS, VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, serial_num, "",Item_Channel.REPIAR_PLANE);
		IPOManagerDb.getInstance().LogBuyService(connection, "repairairsupport", serial_num);
		
		info.Repair();
		connection.getTasks().AddTaskNumber(TASK_TYPE.AIRSUPPORT_OPERATE, tableId, 1, AIRSUPPORT_OPERATE.REPAIR.Number());
	}
}
