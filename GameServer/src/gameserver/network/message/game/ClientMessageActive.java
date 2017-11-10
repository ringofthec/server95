package gameserver.network.message.game;

import gameserver.active.ActiveService;
import gameserver.connection.attribute.info.HeroInfo;
import gameserver.ipo.IPOManager;
import gameserver.logging.LogService;
import gameserver.network.IOServer;
import gameserver.network.protos.game.ProActive.Msg_C2G_GetReward;
import gameserver.network.server.connection.Connection;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;
import gameserver.utils.Util;

import java.util.Map;

import table.Int3;
import table.MT_Data_RechargeActive;
import table.base.TableManager;

import commonality.Common.SETNUMBER_TYPE;
import commonality.ProductChannel;
import commonality.VmChannel;

public class ClientMessageActive {
	private final static ClientMessageActive instance = new ClientMessageActive();
	public static ClientMessageActive getInstance() { return instance; }
	public void initialize()
	{
		IOServer.getInstance().regMsgProcess(Msg_C2G_GetReward.class, this, "OnRewards");
	}

	public void OnRewards(Connection connection, Msg_C2G_GetReward message) throws GameException{
		int activeIdx = message.getRewardIdx();
		MT_Data_RechargeActive reac = TableManager.GetInstance().TableRechargeActive().GetElement(activeIdx);
		if (reac == null)
			return ;
		
		if (!ActiveService.getInstance().isActiveRun(reac.activeid()))
			return ;
		
		int cur_reward_idx = connection.getReward().getActiveRewardIdx(reac.activeid());
		int count = connection.getReward().getActiveParam1(reac.activeid());
		if (count < reac.clac())
			return ;
		
		if (activeIdx <= cur_reward_idx)
			return ;
		
		Map<Integer, Integer> items = Util.getInt2ListToMap(reac.rewards());
		int eqiepCount = Util.getEqiepOrDesCount(items);
		if (!connection.getItem().checkEquipFragCount(eqiepCount))
			return ;
		
		long sum = IPOManager.getInstance().getNextId();
		connection.getReward().setActiveRewardIdx(reac.activeid(), activeIdx);
		connection.getItem().setItemNumberArrayByInt2(reac.rewards(), SETNUMBER_TYPE.ADDITION, 
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, sum, "", Item_Channel.ACTIVE_REWARD);
		
		HeroInfo hero = connection.getHero().getRandomHero();
		if (hero != null) {
			for (Int3 otherred : reac.rewardsbyhero()) {
				if (otherred.field1() == hero.getTableId()) {
					connection.getItem().setItemNumber(otherred.field2(), otherred.field3(), SETNUMBER_TYPE.ADDITION, 
							VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, sum, "", Item_Channel.ACTIVE_REWARD);
				}
			}
		}
		
		LogService.logEvent(connection.getPlayerId(), sum, 25, reac.activeid(), activeIdx);
	}
}
