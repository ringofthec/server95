package gameserver.network.message.game;

import gameserver.connection.attribute.ConPlayerAttr;
import gameserver.connection.attribute.info.CorpsInfo;
import gameserver.connection.attribute.info.HeroInfo;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.IOServer;
import gameserver.network.protos.game.ProPve;
import gameserver.network.protos.game.ProPve.Proto_Instance;
import gameserver.network.protos.game.ProPvp;
import gameserver.network.server.connection.Connection;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;
import gameserver.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import table.InstanceReward;
import table.MT_Data_Hero;
import table.MT_Data_Instance;
import table.MT_Data_InstanceReward;
import table.base.TABLE;
import table.base.TableManager;

import commonality.Common.LIMIT_TYPE;
import commonality.Common.SETNUMBER_TYPE;
import commonality.Common.TASK_TYPE;
import commonality.ITEM_ID;
import commonality.ProductChannel;
import commonality.PromptType;
import commonality.VmChannel;

import database.CustomFormation;
import database.CustomInstanceStar;

public class ClientMessagePve {
	private final static ClientMessagePve instance = new ClientMessagePve();

	public static ClientMessagePve getInstance() {
		return instance;
	}

	public void initialize() {
		IOServer.getInstance().regMsgProcess(ProPve.Msg_C2G_AskPveData.class,this, "OnAskPveDatas");
		IOServer.getInstance().regMsgProcess(ProPve.Msg_C2G_AskBattleground.class,this, "OnAskBattleground");
		IOServer.getInstance().regMsgProcess(ProPve.Msg_C2G_StartAttackPve.class,this, "OnStartAttack");
		IOServer.getInstance().regMsgProcess(ProPve.Msg_C2G_ExchangeAward.class,this, "OnExchangeAward");
		IOServer.getInstance().regMsgProcess(ProPve.Msg_C2G_SaveFormation.class,this, "OnSaveFormation");
	}
	//更新Pve数据
	public void UpdataPveDatas(Connection connect, int index, int type) throws GameException
	{
		ConPlayerAttr player = connect.getPlayer();
		player.repairPlayerInstanceId();
		ProPve.Msg_G2C_UpdatePveData.Builder message = ProPve.Msg_G2C_UpdatePveData.newBuilder();
		if (player.getinstance_id() == 0)
			throw new GameException("player.instanceId is null");
		if (player.getinstance_star_array() != null) {
			for (CustomInstanceStar pair : player.getinstance_star_array()) {
				if (pair.id / 10000 == index) {
					Proto_Instance.Builder info = Proto_Instance.newBuilder();
					info.setID(pair.id);
					info.setStarNum(pair.starCount);
					info.setCount(pair.count);
					message.addInfo(info.build());
				}
			}
		}
		message.setType(type == 1 ? ProPve.Proto_AttackState.INFO : ProPve.Proto_AttackState.DATA);
		message.setInstanceId(player.getinstance_id());
		message.setSuperiorId(index);
		if (player.getsuperiorInfo().size() < index) {
			message.setAllStar(0);
			message.setAwardType(0);
		} else {
			message.setAllStar(player.getsuperiorInfo().get(index-1).starCount);
			message.setAwardType(player.getsuperiorInfo().get(index-1).awardType);
		}
		connect.sendReceiptMessage(message.build());
	}
	public void OnUpdateOneData(Connection connect, int instanceId){
		ConPlayerAttr player = connect.getPlayer();
		ProPve.Msg_G2C_UpdatePveData.Builder message = ProPve.Msg_G2C_UpdatePveData.newBuilder();
		if (player.getinstance_star_array() != null) {
			for (CustomInstanceStar pair : player.getinstance_star_array()) {
				if (pair.id == instanceId || pair.id == player.getinstance_id()) {
					Proto_Instance.Builder info = Proto_Instance.newBuilder();
					info.setID(pair.id);
					info.setStarNum(pair.starCount);
					info.setCount(pair.count);
					message.addInfo(info.build());
				}
			}
		}
		
		if (player.getsuperiorInfo().size() == 0) {
			message.setAllStar(0);
			message.setAwardType(0);
		} else {
			int index = instanceId / 10000;
			message.setAllStar(player.getsuperiorInfo().get(index-1).starCount);
			message.setAwardType(player.getsuperiorInfo().get(index-1).awardType);
		}
		
		message.setType(ProPve.Proto_AttackState.UPDATE);
		message.setInstanceId(player.getinstance_id());
		message.setSuperiorId(instanceId/10000);
		connect.sendReceiptMessage(message.build());
	}
	
	//请求进入Pve界面
	public void OnAskPveDatas(Connection connect, ProPve.Msg_C2G_AskPveData msg) throws GameException{
		ConPlayerAttr player = connect.getPlayer();
		int index = msg.getId();
		if (index == 0)
			return;
		
		int curIndex = player.getinstance_id() / 10000;
		if (index == -1)
			index = curIndex;
		if (index > curIndex)
			return;
		UpdataPveDatas(connect, index, msg.getType());
	}
	
	//请求进入战场
	public void OnAskBattleground(Connection connect, ProPve.Msg_C2G_AskBattleground msg) throws GameException{
		ConPlayerAttr playerAttribute = connect.getPlayer();
		MT_Data_Instance instance = TableManager.GetInstance().TableInstance().GetElement(msg.getID());
		if (instance == null)
			throw new GameException("副本信息不足");
		
		//体力检查
		if (!connect.getItem().checkItemEnough(ITEM_ID.CP, 1)) 
			return ;
		
		//次数检查
		if (!TABLE.IsInvalid(instance.MaxCount())) {
			if (!playerAttribute.checkCount(msg.getID(),instance.MaxCount()))
				throw new GameException("pve次数不足");
		}
		
		if (!connect.getPlayer().InstanceIdIsvalid(msg.getID()))
			throw new GameException("没打过该关卡");


		if (!TABLE.IsInvalid(instance.MaxCount())) {
			int count = connect.getPlayer().GetInstanceCount(msg.getID());
			if (count >= instance.MaxCount())
				throw new GameException(PromptType.INSTANCE_COUNT_LIMIT);
		}

		ProPve.Msg_G2C_UpdatePveData.Builder message = ProPve.Msg_G2C_UpdatePveData.newBuilder();
		message.setType(ProPve.Proto_AttackState.BATTLEGROUND);
		message.setInstanceId(msg.getID());
		connect.sendReceiptMessage(message.build());
		
		//PVE战斗开始记录日志
		LogService.logPve(msg.getID(), 0, 0, 0, connect.getPlayerId(), 0,connect.getPlayer().getLevel());
	}
	//开始战斗
	public void OnStartAttack(Connection connect, ProPve.Msg_C2G_StartAttackPve msg){
		//更新体力
		MT_Data_Instance instanceData = TableManager.GetInstance().TableInstance().GetElement(msg.getID());
		if (instanceData == null)
			return;
		connect.getItem().setItemNumber(ITEM_ID.CP, instanceData.NeedCP(), SETNUMBER_TYPE.MINUS,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,-1,"",Item_Channel.START_PVE);
		ProPve.Msg_G2C_UpdatePveData.Builder message = ProPve.Msg_G2C_UpdatePveData.newBuilder();
		message.setType(ProPve.Proto_AttackState.STARTATTACK);
		message.setInstanceId(msg.getID());
		connect.sendReceiptMessage(message.build());
		ClientMessageCommon.getInstance().UpdateCountInfo(connect, ProPvp.Proto_ActionType.CP, connect.getItem().getItemCountByTableId(ITEM_ID.CP), connect.getCommon().GetValue(LIMIT_TYPE.CP));
		
		// 玩家开始PVE战斗
		connect.getPlayer().isPve = true;
		connect.getPlayer().pveMessage = msg;
	}
	//兑换奖励
	public void OnExchangeAward(Connection connect, ProPve.Msg_C2G_ExchangeAward msg) throws Exception{
		ConPlayerAttr player = connect.getPlayer();
		MT_Data_InstanceReward rewardData = TableManager.GetInstance().TableInstanceReward().GetElement(msg.getId());
		InstanceReward reward = (InstanceReward)rewardData.GetDataByString("item" + msg.getIndex());

		if (player.getsuperiorInfo().get(msg.getId()-1).starCount < reward.field1())
			throw new GameException("你的星级不够");
		if (Util.getIntegerSomeBit(player.getsuperiorInfo().get(msg.getId()-1).awardType, msg.getIndex()) == 1)
			throw new GameException("奖励已经领取了");

		connect.getPlayer().UpdatePlayerAwardType(msg.getId()-1, msg.getIndex(), true);
		long s_num = IPOManagerDb.getInstance().getNextId();
		connect.getItem().setItemNumber(reward.field2().field1(), reward.field2().field2(), SETNUMBER_TYPE.ADDITION,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"",Item_Channel.PVE_STAR_REWARD);
		connect.getItem().setItemNumber(reward.field3().field1(), reward.field3().field2(), SETNUMBER_TYPE.ADDITION,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"",Item_Channel.PVE_STAR_REWARD);
		LogService.logEvent(connect.getPlayerId(), s_num, 11);
		//connect.getTaskAttribute().AddTaskNumber(TASK_TYPE.GET_REWARD, REWARD_TYPE.PVE_STAR.Number(), 0, 0);
		UpdataPveDatas(connect, msg.getId(), 2);
	}
	//保存阵容
	public void OnSaveFormation(Connection connect, ProPve.Msg_C2G_SaveFormation msg)
	{
		if (msg.getType() == ProPve.Proto_FORMATION_TYPE.DEFENSE) {
			connect.getPlayer().reserveDefence();
			
			Map<Integer, Integer> heroIds = new TreeMap<Integer, Integer>();
			Map<Integer, Integer> corpIds = new TreeMap<Integer, Integer>();
			
			List<databaseshare.CustomFormation> formationList1 = new ArrayList<databaseshare.CustomFormation>();
			for (ProPve.Proto_Formation pair : msg.getInfoList()) {
				if (pair.getType() == ProPve.Proto_CorpsType.HERO) {
					HeroInfo hero = connect.getHero().getHeroByTableId(pair.getId());
					if (hero == null)
						return ;
					
					if (heroIds.containsKey(pair.getId()))
						return ;
					
					heroIds.put(pair.getId(), pair.getId());
				}
				else {
					CorpsInfo corpInfo = connect.getCorps().getCorpsByTableId(pair.getId());
					if (corpInfo == null)
						return ;
					
					if (corpIds.containsKey(pair.getId())) {
						int count = corpIds.get(pair.getId());
						corpIds.put(pair.getId(), count+1);
					}
					else {
						corpIds.put(pair.getId(), 1);
					}
				}
			}
			
			for (Entry<Integer, Integer> pair : corpIds.entrySet()) {
				if (connect.getCorps().getCorpsByTableId(pair.getKey()).getNumber() < pair.getValue())
					return ;
			}
			
			for (ProPve.Proto_Formation pair : msg.getInfoList()) {
				if (pair.getType() == ProPve.Proto_CorpsType.HERO) {
			//		formationList1.add(new databaseshare.CustomFormation(pair.getPosX(), pair.getPosY(), pair.getId(), pair.getType().getNumber()));
			//	else {
					connect.getCorps().setCorpsNumber(pair.getId(), 1, SETNUMBER_TYPE.MINUS);
				//	formationList1.add(new databaseshare.CustomFormation(pair.getPosX(), pair.getPosY(), pair.getId(), pair.getType().getNumber()));
				}
			}
			
			if (msg.getInfoList().isEmpty()) {
				for (HeroInfo hero : connect.getHero().getHeros().values()) {
					MT_Data_Hero hero_data = hero.getData();
				//	formationList1.add(new databaseshare.CustomFormation(hero_data.Pos().field1(), 
				//			hero_data.Pos().field2(), hero_data.ID(), ProPve.Proto_CorpsType.HERO_VALUE));
				}
			}
			
			connect.getPlayer().setDirty();
		//	connect.getPlayer().setDefenseFormation(formationList1);
			connect.getTasks().AddTaskNumber(TASK_TYPE.SET_DEFENCE, 0, 1, 0);
		} else {
			List<CustomFormation> formationList = new ArrayList<CustomFormation>();
			for (ProPve.Proto_Formation crop : msg.getInfoList())
				formationList.add(new CustomFormation(crop.getPosX(), crop.getPosY(), crop.getId(), crop.getType().getNumber()));
			
		//	if (msg.getType() == ProPve.Proto_FORMATION_TYPE.FORMATION1)
		//		connect.getPlayer().setFormation1(formationList);
		//	else if (msg.getType() == ProPve.Proto_FORMATION_TYPE.FORMATION2)
		//		connect.getPlayer().setFormation2(formationList);
		}
		ProPve.Msg_G2C_UpdatePveData.Builder message = ProPve.Msg_G2C_UpdatePveData.newBuilder();
		message.setType(ProPve.Proto_AttackState.Save);
		connect.sendReceiptMessage(message.build());
	}
}
