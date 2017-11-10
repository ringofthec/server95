package gameserver.pvp;

import gameserver.fighting.creature.RobotCreater;
import gameserver.ipo.IPOManager;
import table.MT_Data_PvpVirtual;
import gameserver.network.protos.game.ProFight;
import gameserver.network.protos.game.ProPve;
import gameserver.network.protos.game.ProPvp;
import gameserver.network.server.connection.Connection;
import gameserver.utils.DbMgr;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;
import gameserver.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import table.Int2;
import table.MT_Data_Corps;
import table.MT_Data_Exp;
import table.MT_Data_HeroPath;
import table.MT_Data_Instance;
import table.base.TABLE;
import table.base.TableManager;

import com.commons.util.RandomUtil;
import com.commons.util.TimeUtil;

import commonality.Common.SETNUMBER_TYPE;
import commonality.Common;
import commonality.ITEM_ID;
import commonality.ProductChannel;
import commonality.VmChannel;
import database.CustomFormation;
import database.CustomHeroPathCrop;
import databaseshare.DatabasePvp_match;

public class PvpManager {
	private final static PvpManager instance = new PvpManager();
	public static PvpManager getInstance() { return instance; }
	
	private Map<Long, PvpMatch> m_PvpArray = new HashMap<Long, PvpMatch>();
	public void Match(Connection connection, long targetPlayerId, int reportid, int needMoney) throws Exception
	{
		long playerId = connection.getPlayerId();
		if (m_PvpArray.containsKey(playerId)){
			m_PvpArray.get(playerId).Shutdown();
			m_PvpArray.remove(playerId);
		}
		
		if (targetPlayerId != 0)
		{
			DatabasePvp_match targetPlayer = DbMgr.getInstance().getShareDB()
				.SelectOne(DatabasePvp_match.class, "player_id=?", targetPlayerId);
			m_PvpArray.put(playerId, new PvpMatch(connection,targetPlayer, reportid));
		}
		else
		{
			m_PvpArray.put(playerId, new PvpMatch(connection));
			
		}
		
		connection.getPlayer().incPvpCount();
		connection.getItem().setItemNumber(ITEM_ID.MONEY, needMoney, SETNUMBER_TYPE.MINUS,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,IPOManager.getInstance().getNextId(),"",Item_Channel.PVP_MATCH);
	}
	public void StartFighting(Connection connection) throws GameException
	{
		long playerId = connection.getPlayerId();
		if (m_PvpArray.containsKey(playerId))
			m_PvpArray.get(playerId).StartFighting();
	}
	public void OverFighting(Connection connection,ProFight.Msg_C2G_Pvp_OverFight message) throws GameException
	{
		long playerId = connection.getPlayerId();
		if (m_PvpArray.containsKey(playerId))
			m_PvpArray.get(playerId).OverFighting(message);
		else 
			connection.sendReceiptMessage(ProPvp.Msg_G2C_PvpOver.newBuilder().setErrorCode(1).build());
		m_PvpArray.remove(playerId);
	}
	public void OverFighting(long playerId, ProFight.Msg_C2G_Pvp_OverFight message) throws GameException
	{
		m_PvpArray.get(playerId).OverFighting(message);
		m_PvpArray.remove(playerId);
	}
	public void Offline(long playerId)
	{
		if (m_PvpArray.containsKey(playerId))
		{
			m_PvpArray.get(playerId).Shutdown();
			m_PvpArray.remove(playerId);
		}
	}
	public boolean IsFighting(long playerId) {
		return m_PvpArray.containsKey(playerId) && m_PvpArray.get(playerId).isBeginFight();
	}
	
}
