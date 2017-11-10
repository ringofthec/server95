package gameserver.network.message.game;

import gameserver.connection.attribute.ConCommonAttr;
import gameserver.connection.attribute.ConHeroAttr;
import gameserver.connection.attribute.ConItemAttr;
import gameserver.connection.attribute.ConPlayerAttr;
import gameserver.connection.attribute.info.HeroInfo;
import gameserver.connection.attribute.info.ItemInfo;
import gameserver.connection.attribute.info.MedalInfo;
import gameserver.ipo.IPOManager;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.IOServer;
import gameserver.network.protos.game.ProGameInfo.Msg_G2C_PlaySound;
import gameserver.network.protos.game.ProItemProto;
import gameserver.network.protos.game.ProItemProto.Msg_G2C_FunctionComplete;
import gameserver.network.protos.game.ProItemProto.Proto_LuckDrawType;
import gameserver.network.protos.game.ProItemProto.Proto_Luck_Draw_Medal_Free_Info;
import gameserver.network.protos.game.ProItemProto.Proto_UseOrBuyType;
import gameserver.network.protos.game.ProPvp;
import gameserver.network.server.connection.Connection;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;
import gameserver.utils.Util;
import gameserver.utils.UtilItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.Int6;
import table.MT_Data_DropOut;
import table.MT_Data_Item;
import table.MT_Data_ItemSplit;
import table.MT_Data_ItemStrengthen;
import table.MT_Data_LuckDrawMedal;
import table.MT_Data_MedalAttribute;
import table.MT_Data_MedalStar;
import table.MT_Data_commodity;
import table.base.TABLE;
import table.base.TableManager;

import com.commons.util.RandomUtil;

import commonality.Common;
import commonality.Common.LIMIT_TYPE;
import commonality.Common.SETNUMBER_TYPE;
import commonality.Common.TASK_TYPE;
import commonality.ITEM_ID;
import commonality.ProductChannel;
import commonality.PromptType;
import commonality.VmChannel;

public class ClientMessageItem {
	private final Logger logger = LoggerFactory
			.getLogger(ClientMessageItem.class);

	private final static ClientMessageItem instance = new ClientMessageItem();

	public static ClientMessageItem getInstance() {
		return instance;
	}

	public void initialize() {
		IOServer.getInstance().regMsgProcess(
				ProItemProto.Msg_C2G_DressEquip.class, this, "OnDressEquip");
		IOServer.getInstance()
				.regMsgProcess(ProItemProto.Msg_C2G_UndressEquip.class, this,
						"OnUndressEquip");
		IOServer.getInstance().regMsgProcess(
				ProItemProto.Msg_C2G_SellItem.class, this, "OnSellItem");
		IOServer.getInstance().regMsgProcess(
				ProItemProto.Msg_C2G_UseItem.class, this, "OnUseItem");
		IOServer.getInstance().regMsgProcess(
				ProItemProto.Msg_C2G_UpLev_Medal.class, this, "OnUpMedal");
		IOServer.getInstance().regMsgProcess(
				ProItemProto.Msg_C2G_UpStar_Medal.class, this, "OnUpMedalStar");
		IOServer.getInstance().regMsgProcess(
				ProItemProto.Msg_C2G_Luck_Draw_Medal.class, this,
				"OnLuckDrawMedal");
		IOServer.getInstance().regMsgProcess(
				ProItemProto.Msg_C2G_Open_Medal.class, this, "OnOpenMedal");
		IOServer.getInstance().regMsgProcess(
				ProItemProto.Msg_C2G_BuyMedal.class, this, "OnBuyMedal");
		IOServer.getInstance().regMsgProcess(
				ProItemProto.Msg_C2G_UseOrBuy.class, this, "OnUseOrBuyItem");
		IOServer.getInstance().regMsgProcess(
				ProItemProto.Msg_C2G_CombineDebris.class, this,
				"OnCombineDebris");
		IOServer.getInstance().regMsgProcess(
				ProItemProto.Msg_C2G_ItemSplit.class, this, "OnItemSplit");
		IOServer.getInstance().regMsgProcess(
				ProItemProto.Msg_C2G_ItemStrengthen.class, this,
				"OnItemStrengthen");
	}

	private static int[] qulity_count_param = { 0, 2, 3, 4, 5 };

	public void OnItemSplit(Connection connection,
			ProItemProto.Msg_C2G_ItemSplit msg) {
		ConItemAttr items = connection.getItem();

		List<UtilItem> deleteitem = new ArrayList<UtilItem>();
		int item_count = 0;
		for (ProItemProto.item_pair it : msg.getItemListList()) {
			int item_id = it.getItemId();
			ItemInfo item = items.getItemById(item_id);
			if (item == null)
				continue;

			// 饰品是装备，但是特殊装备，不让分解
			if (item.isEquip()
					&& (item.getData().ItemPart() == Common.EQUIPPART.Accessories
							.Number()))
				continue;

			if (item.isEquip()) {
				MT_Data_ItemSplit spliteConfig = TableManager.GetInstance()
						.TableItemSplit().GetElement(item.getLvl());
				if (spliteConfig == null)
					continue;
				item_count += item.getData().ExternParam();
				if (!items.checkItemEnough(ITEM_ID.MONEY, item_count * Common.SPLIT_EQU_TIMES)) {
					connection.ShowPrompt(PromptType.MONEY_NOT_ENOUGH);
					return;
				}
				item_count += (item.getTotalExp()*qulity_count_param[item.getData().Quality()]/30);
				
				deleteitem.add(new UtilItem(item_id, 1));
			} else {
				item_count += item.getData().ExternParam() * it.getItemNum();
				if (!items.checkItemEnough(ITEM_ID.MONEY, Common.SPLIT_EQU_TIMES)) {
					connection.ShowPrompt(PromptType.MONEY_NOT_ENOUGH);
					return;
				}
				deleteitem.add(new UtilItem(item_id, it.getItemNum()));
			}
		}

		if (item_count > 0) {
			long costId = IPOManager.getInstance().getNextId();
			for (UtilItem it : deleteitem) {
				ItemInfo item = items.getItemById(it.GetItemId());
				item.setItemNumber(it.GetCount(), SETNUMBER_TYPE.MINUS,
						VmChannel.CompoundFromOtherVm,
						ProductChannel.CompoundFromOtherGoods, costId, "",
						true, Item_Channel.ITEM_SPLITE);
			}

			items.setItemNumber(ITEM_ID.MONEY, item_count * 500,
					SETNUMBER_TYPE.MINUS, VmChannel.CompoundFromOtherVm,
					ProductChannel.CompoundFromOtherGoods, costId, "",
					Item_Channel.ITEM_SPLITE); // 扣除金币

			items.setItemNumber(ITEM_ID.ITEM_STRENGTHEN, item_count,
					SETNUMBER_TYPE.ADDITION, VmChannel.PurchaseGoods,
					ProductChannel.PurchaseGoods, costId, "",
					Item_Channel.ITEM_SPLITE);
			connection.ShowPrompt(PromptType.ITEM_SPLITE_SUCCESS);
			LogService.logEvent(connection.getPlayerId(), costId, 29);
		}

		connection.getTasks().AddTaskNumber(TASK_TYPE.EQUIP_SPLITE, 0, 1, 0);
	}

	public void OnItemStrengthen(Connection connection,
			ProItemProto.Msg_C2G_ItemStrengthen msg) throws GameException {
		ConItemAttr items = connection.getItem();
		ItemInfo item = items.getItemById(msg.getItemId());
		if (item == null)
			return;

		if (!item.isEquip())
			return;

		if (item.getLvl() >= TableManager.GetInstance().getMaxItemStrengthen())
			return;

		// 蓝色以下太屌了
		if (item.getItemColor() < 1)
			return;

		MT_Data_ItemStrengthen is = TableManager.GetInstance()
				.TableItemStrengthen().GetElement(item.getLvl());


		int itemcost = is.getM_arrayitemCost().get(item.getItemColor() - 1).field2();
		int moneycost = is.getM_arraymoneyCost().get(item.getItemColor() - 1).field2();
		int maxexp = is.getM_arrayexpmax().get(item.getItemColor() - 1).field2();
		
		if (!items.checkItemEnough(ITEM_ID.MONEY, moneycost)) {
			connection.ShowPrompt(PromptType.MONEY_NOT_ENOUGH);
			return;
		}
			
		int goldcost = 0;
		int alreadyItemCount = items
				.getItemCountByTableId(ITEM_ID.ITEM_STRENGTHEN);
		if (alreadyItemCount < itemcost) {
			int lastCount = itemcost - alreadyItemCount;
			itemcost = alreadyItemCount;

			MT_Data_commodity conf = TableManager.GetInstance()
					.Tablecommodity().GetElement(310);
			goldcost = conf.Attr().field2() * lastCount;
			if (items.getItemCountByTableId(ITEM_ID.GOLD) < goldcost) {
				items.sendItemNotEnoughMsg(ITEM_ID.ITEM_STRENGTHEN);
				return;
			}
		}

		long costId = IPOManager.getInstance().getNextId();
		if (itemcost > 0)
			items.setItemNumber(ITEM_ID.ITEM_STRENGTHEN, itemcost,
					SETNUMBER_TYPE.MINUS, VmChannel.CompoundFromOtherVm,
					ProductChannel.CompoundFromOtherGoods, costId, "",
					Item_Channel.ITEM_STERNGTHEN);
		if (goldcost > 0)
			items.setItemNumber(ITEM_ID.GOLD, goldcost, SETNUMBER_TYPE.MINUS,
					VmChannel.CompoundFromOtherVm,
					ProductChannel.CompoundFromOtherGoods, costId, "",
					Item_Channel.ITEM_STERNGTHEN);
		
		if (moneycost > 0)
			items.setItemNumber(ITEM_ID.MONEY, moneycost,
					SETNUMBER_TYPE.MINUS, VmChannel.CompoundFromOtherVm,
					ProductChannel.CompoundFromOtherGoods, costId, "",
					Item_Channel.ITEM_STERNGTHEN); // 扣除金币

		Msg_G2C_FunctionComplete.Builder builder = Msg_G2C_FunctionComplete
				.newBuilder().setFunctionid(1);
		int oldlevel = item.getLvl();
		int addExp = items.randomStrengthenAddExp();
		item.ItemIncStrengthenExp(addExp);
		int goodLuckVal = addExp/5;
		if (goodLuckVal > 1)
			connection.ShowPrompt(PromptType.ITEM_STRENGTHEN_GOODLUCK,goodLuckVal);
		else
			connection.ShowPrompt(PromptType.ITEM_STRENGTHEN_SUCCESS);
		if (item.getExp() >= maxexp) {
			if (item.getOwner() != 0
					&& item.getData().ItemPart() == Common.EQUIPPART.Accessories
							.Number()) {
				ClientMessagePassiveBuff.getInstance().UpdateEquipPassiveBuff(
						connection, item.getItemId(), 0);
			}

			item.ItemIncStrengthen();
			connection.ShowPrompt(PromptType.ITEM_LVLUP_SUCCESS,item.getLvl());
			LogService.logEvent(connection.getPlayerId(), costId, 28, item.getItemId(),
					item.getLvl());
			connection.sendPushMessage(Msg_G2C_PlaySound.newBuilder()
					.setName("smallprize").build());
			connection.getHero().updateAllHeroFightVal();
			connection.setNeed_recalc_fight_val(true);

			// 饰品强化后加buff
			if (item.getOwner() != 0
					&& item.getData().ItemPart() == Common.EQUIPPART.Accessories
							.Number()) {
				ClientMessagePassiveBuff.getInstance().UpdateEquipPassiveBuff(
						connection, 0, item.getItemId());
			}
		}
		
		connection.sendPushMessage(builder.setMask(1).build());
		connection.getTasks().AddTaskNumber(TASK_TYPE.EQUIP_FORCE, 0, 1, 0);
	}

	public void OnCombineDebris(Connection connection,
			ProItemProto.Msg_C2G_CombineDebris message) {
		connection.getItem().CombineDebris(message.getItemId());
	}

	// 购买勋章
	public void OnBuyMedal(Connection connection,
			ProItemProto.Msg_C2G_BuyMedal message) throws GameException {
		ConHeroAttr heros = connection.getHero();
		ConItemAttr items = connection.getItem();

		HeroInfo heroInfo = heros.getHeroById(message.getHeroId());
		if (heroInfo == null)
			throw new GameException("heroInfo is null");
		if (heroInfo.getMedalByTableId(message.getTableId()) != null)
			throw new GameException("此英雄已经拥有这个勋章，id={}", message.getTableId());
		MT_Data_MedalAttribute medalData = TableManager.GetInstance()
				.TableMedalAttribute().GetElement(message.getTableId());
		if (medalData == null)
			throw new GameException("medalData 配置不对");
		if (TABLE.IsInvalid(medalData.OpenNeedMoney()))
			throw new GameException("medalData 配置不对，没有配置购买勋章需要的金钱");
		if (!items.checkItemEnoughByInt2(medalData.OpenNeedMoney()))
			return;

		long costId = IPOManagerDb.getInstance().getNextId();
		items.setItemNumberByInt2(medalData.OpenNeedMoney(),
				SETNUMBER_TYPE.MINUS, VmChannel.PurchaseGoods,
				ProductChannel.PurchaseGoods, costId, "",
				Item_Channel.BUY_MEDAL);
		heroInfo.addMedal(message.getTableId());
		IPOManagerDb.getInstance()
				.LogBuyService(connection, "medalbuy", costId);
		LogService.logEvent(connection.getPlayerId(), costId, 5,
				message.getTableId(), message.getHeroId());

		ProItemProto.Msg_G2C_BuyMedal.Builder msg = ProItemProto.Msg_G2C_BuyMedal
				.newBuilder();
		msg.setHeroId(message.getHeroId());
		msg.setTableId(message.getTableId());
		connection.sendReceiptMessage(msg.build());

		heroInfo.updateHeroFightVal();
		connection.setNeed_recalc_fight_val(true);
	}

	// 开启勋章
	public void OnOpenMedal(Connection connection,
			ProItemProto.Msg_C2G_Open_Medal message) {
		connection.getHero().onPlayerLevelUp(true);
	}

	// 出售物品
	public void OnSellItem(Connection connection,
			ProItemProto.Msg_C2G_SellItem message) throws GameException {
		ConItemAttr items = connection.getItem();
		ItemInfo item = items.getItemById(message.getItemId());
		if (item == null)
			throw new GameException("item is null");

		if (item.getOwner() != 0)
			return;

		if (message.getNum() < 0)
			return;

		if (item.getNumber() < message.getNum())
			return;

		long s_num = IPOManagerDb.getInstance().getNextId();
		items.setItemNumberByItemId(message.getItemId(), message.getNum(),
				SETNUMBER_TYPE.MINUS, VmChannel.CirculationBetweenSystem,
				ProductChannel.CirculationBetweenSystem, s_num, "",
				Item_Channel.SELL_ITEM);
		MT_Data_Item data = item.getData();
		int num = message.getNum() * data.SalesPrice().field2();
		items.setItemNumber(data.SalesPrice().field1(), num,
				SETNUMBER_TYPE.ADDITION, VmChannel.CirculationBetweenSystem,
				ProductChannel.CirculationBetweenSystem, s_num, "",
				Item_Channel.SELL_ITEM);
		LogService.logEvent(connection.getPlayerId(), s_num, 7);
	}

	// 购买而起使用物品
	public void OnUseOrBuyItem(Connection connection,
			ProItemProto.Msg_C2G_UseOrBuy message) throws Exception {
		ConPlayerAttr player = connection.getPlayer();
		ConItemAttr items = connection.getItem();
		ConCommonAttr common = connection.getCommon();

		if (message.getType().equals(Proto_UseOrBuyType.CP)) {

			if (player.getCount(0) <= 0)
				throw new GameException("体力补充超上限啦1");

			// 体力不足就购买
			int count = items.getItemCountByTableId(22000);
			if (count < 1)
				ClientMessageCommodity.getInstance().OnBuyOneCommodity(
						connection, 400, false);

			count = items.getItemCountByTableId(22000);
			if (count < 1)
				return;

			// 使用体力道具(活力饮料),加体力
			long s_num = IPOManagerDb.getInstance().getNextId();
			int oldCpNum = items.getItemCountByTableId(ITEM_ID.CP);
			items.setItemNumber(22000, 1, SETNUMBER_TYPE.MINUS,
					VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,
					s_num, "", Item_Channel.USE_ITEM);
			items.setItemNumber(ITEM_ID.CP, 10, SETNUMBER_TYPE.ADDITION,
					VmChannel.InGameDrop, ProductChannel.InGameDrop, s_num, "",
					Item_Channel.USE_ITEM);
			player.decCount(0);
			IPOManagerDb.getInstance()
					.LogBuyService(connection, "buycp", s_num);
			int newCpNum = items.getItemCountByTableId(ITEM_ID.CP);
			LogService.logEvent(connection.getPlayerId(), s_num, 22, oldCpNum,
					newCpNum);

			ClientMessageCommon.getInstance().UpdateCountInfo(connection,
					ProPvp.Proto_ActionType.CP,
					items.getItemCountByTableId(ITEM_ID.CP),
					common.GetValue(LIMIT_TYPE.CP));
			ClientMessageCommon.getInstance().UpdateCountInfo(connection,
					ProPvp.Proto_ActionType.USE_CP_ITEM, player.getCount(0),
					common.GetValue(LIMIT_TYPE.USE_CP_ITEM));
		} else if (message.getType().equals(Proto_UseOrBuyType.PVP)) {
			if (player.getCount(1) <= 0)
				throw new GameException("PVP补充超上限啦1");

			// pvp次数不足，就购买
			int count = items.getItemCountByTableId(5);
			if (count < 1)
				ClientMessageCommodity.getInstance().OnBuyOneCommodity(
						connection, 500, false);

			count = items.getItemCountByTableId(5);
			if (count < 1)
				return;

			// 使用pvp道具(令牌),加pvp次数
			long s_num = IPOManagerDb.getInstance().getNextId();
			items.setItemNumber(5, 1, SETNUMBER_TYPE.MINUS,
					VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,
					s_num, "", Item_Channel.USE_ITEM);

			int oldPvpNum = player.getPvpNumber();
			player.addPvpNumber(1);
			player.decCount(1);
			ClientMessageCommon.getInstance().UpdateCountInfo(connection,
					ProPvp.Proto_ActionType.PVP, player.getPvpNumber(),
					common.GetValue(LIMIT_TYPE.PVP_NUMBER));

			ClientMessageCommon.getInstance().UpdateCountInfo(connection,
					ProPvp.Proto_ActionType.USE_PVP_ITEM, player.getCount(1),
					common.GetValue(LIMIT_TYPE.USE_PVP_ITEM));

			IPOManagerDb.getInstance().LogBuyService(connection, "buypvp",
					s_num);
			LogService.logEvent(connection.getPlayerId(), s_num, 23, oldPvpNum,
					player.getPvpNumber());
		}
	}

	// 使用物品,这里使用的物品必须配置掉落组
	public void OnUseItem(Connection connection,
			ProItemProto.Msg_C2G_UseItem message) throws Exception {
		ConPlayerAttr player = connection.getPlayer();
		ConItemAttr items = connection.getItem();
		ConCommonAttr common = connection.getCommon();

		ItemInfo item = items.getItemById(message.getItemId());
		if (item == null)
			throw new GameException("item is null");

		if (message.getNum() < 0)
			throw new GameException("使用数量<0");

		if (item.getNumber() < message.getNum())
			throw new GameException("数量物品不足");

		MT_Data_Item data = item.getData();

		if (!item.getData().ID().equals(ITEM_ID.PVP_TOKEN)
				&& (item.getData().Type() != Common.ITEMTYPE.Vip_Card.Number())) {
			if (data.DropOut().isEmpty())
				throw new GameException("没有配置掉落组");

			for (Int2 dropconf : data.DropOut()) {
				if (!TableManager.GetInstance().TableDropOut().Contains(dropconf.field2())) {
					logger.error("OnUseItem, not exist dropid id={}", dropconf.field2());
					continue ;
				}
				
				MT_Data_DropOut dropOut = TableManager.GetInstance()
						.TableDropOut().GetElement(dropconf.field2());
				if (dropOut == null)
					throw new GameException("没有配置掉落组");
			}
		}

		// 检测是否满足开启需求
		if (TABLE.IsInvalid(data.OpenLevel()) == false) {
			if (!player.CheckPlayerLevel(data.OpenLevel(), true))
				return;
		}

		if (item.getData().ID() == ITEM_ID.CP_DRINK) {
			if (player.getCount(0) <= 0)
				throw new GameException("体力补充超上限啦2");
		} else if (item.getData().ID() == ITEM_ID.PVP_TOKEN) {
			if (player.getCount(1) <= 0)
				throw new GameException("PVP补充超上限啦2");
		}

		if (data.OpenNeed().size() > 0) {
			if (!items.checkItemArrayEnoughByInt2(data.OpenNeed()))
				throw new GameException("你的资源不足");
		}

		// 如果开的是礼包
		List<UtilItem> list = TableManager.GetInstance().getDropOut(player,
				data.DropOut());
		if (item.getData().Type().equals(4)) {
			int moneyCount = 0;
			int equipeCount = 0;
			for (UtilItem iteminfo : list) {
				if (iteminfo.GetItemId() == ITEM_ID.MONEY) {
					moneyCount += iteminfo.GetCount();
				} else if (Util.isEqiupFragByTableId(iteminfo.GetItemId())) {
					equipeCount += iteminfo.GetCount();
				}
			}

			if (connection.getItem().getItemCountByTableId(ITEM_ID.MONEY)
					+ moneyCount > connection.getBuild().GetMaxMoneyCount()) {
				connection.ShowPrompt(PromptType.MONEY_FULL);
				return;
			}
			if (!connection.getItem().checkEquipFragCount(equipeCount))
				return;
		}

		// 扣除物品
		long s_num = IPOManagerDb.getInstance().getNextId();
		items.setItemNumberByItemId(message.getItemId(), message.getNum(),
				SETNUMBER_TYPE.MINUS, VmChannel.PurchaseGoods,
				ProductChannel.PurchaseGoods, s_num, "", Item_Channel.USE_ITEM);
		for (Int2 pair : data.OpenNeed())
			items.setItemNumber(pair.field1(), pair.field2(),
					SETNUMBER_TYPE.MINUS, VmChannel.PurchaseGoods,
					ProductChannel.PurchaseGoods, s_num, "",
					Item_Channel.USE_ITEM);

		if (item.getData().ID() == ITEM_ID.PVP_TOKEN) {
			connection.getPlayer().addPvpNumber(1);
		} else {
			connection.getItem()
					.setItemNumberArrayByUtilItem(list,
							SETNUMBER_TYPE.ADDITION, VmChannel.InGameDrop,
							ProductChannel.InGameDrop, s_num, "",
							Item_Channel.USE_ITEM);
		}

		if (item.getData().ID().equals(ITEM_ID.CP_DRINK)) {
			player.decCount(0);
			ClientMessageCommon.getInstance().UpdateCountInfo(connection,
					ProPvp.Proto_ActionType.USE_CP_ITEM, player.getCount(0),
					common.GetValue(LIMIT_TYPE.USE_CP_ITEM));

			ClientMessageCommon.getInstance().UpdateCountInfo(connection,
					ProPvp.Proto_ActionType.CP,
					connection.getItem().getItemCountByTableId(ITEM_ID.CP),
					common.GetValue(LIMIT_TYPE.CP));

			IPOManagerDb.getInstance()
					.LogBuyService(connection, "buycp", s_num);
		} else if (item.getData().ID().equals(ITEM_ID.PVP_TOKEN)) {
			player.decCount(1);
			ClientMessageCommon.getInstance().UpdateCountInfo(connection,
					ProPvp.Proto_ActionType.USE_PVP_ITEM, player.getCount(1),
					common.GetValue(LIMIT_TYPE.USE_PVP_ITEM));

			ClientMessageCommon.getInstance().UpdateCountInfo(connection,
					ProPvp.Proto_ActionType.PVP,
					connection.getPlayer().getPvpNumber(),
					common.GetValue(LIMIT_TYPE.PVP_NUMBER));

			IPOManagerDb.getInstance().LogBuyService(connection, "buypvp",
					s_num);
		} else if (item.getData().Type() == Common.ITEMTYPE.Vip_Card.Number()) {
			int vip_time_by_hour = item.getData().ExternParam();
			player.OpenVip(vip_time_by_hour * 3600 * 1000);
		}

		LogService.logEvent(connection.getPlayerId(), s_num, 10);
		connection.getTasks().AddTaskNumber(TASK_TYPE.USE_ITEM,
				item.getTableId(), message.getNum(), 0);
	}

	// 穿装备
	public void OnDressEquip(Connection connection,
			ProItemProto.Msg_C2G_DressEquip message) throws GameException {
		ItemInfo item = connection.getItem().getItemById(message.getEquipId());
		if (item == null)
			return;
		if (item.getOwner() != 0)
			throw new GameException("改装备已经被穿上");
		MT_Data_Item itemData = item.getData();
		if (itemData == null)
			return;
		HeroInfo hero = connection.getHero().getHeroByTableId(
				message.getHeroId());
		if (hero == null)
			return;

		if (!TABLE.IsInvalid(itemData.HeroLimite())
				&& itemData.HeroLimite() > 0
				&& hero.getTableId() != itemData.HeroLimite())
			throw new GameException(
					"装备不是此英雄的无法装备   heroTableId:{} itemId:{} limiteId:{}",
					hero.getTableId(), itemData.ID(), itemData.HeroLimite());
		if (!TABLE.IsInvalid(itemData.OpenLevel())
				&& !connection.getPlayer().CheckPlayerLevel(
						itemData.OpenLevel(), true))
			return;

		int oldItemId = hero.equip(item);
		connection.getTasks()
				.AddTaskNumber(TASK_TYPE.HERO_DRESS_EQUIP, 0, 1, 0);
		ClientMessageHero.getInstance()
				.OnHeroData(connection, hero.getHeroId());

		hero.updateHeroFightVal();
		ClientMessagePassiveBuff.getInstance().UpdateEquipPassiveBuff(
				connection, oldItemId, item.getItemId());
		connection.setNeed_recalc_fight_val(true);
	}

	// 脱装备
	public void OnUndressEquip(Connection connection,
			ProItemProto.Msg_C2G_UndressEquip message) throws Exception {
		ItemInfo item = connection.getItem().getItemById(message.getEquipId());
		if (item == null || item.getOwner() == 0)
			throw new Exception("equipId is error Id=" + message.getEquipId());
		HeroInfo hero = connection.getHero().getHeroByTableId(
				message.getHeroId());
		if (hero == null)
			throw new Exception("hero is null   Id=" + message.getHeroId());
		if (hero.getByItemId(message.getEquipId()) == null)
			throw new Exception("装备没有装备,无法卸载");
		if (!connection.getItem().checkEquipFragCount(1))
			return;

		hero.unEquip(item);
		ClientMessageHero.getInstance()
				.OnHeroData(connection, hero.getHeroId());

		hero.updateHeroFightVal();
		ClientMessagePassiveBuff.getInstance().UpdateEquipPassiveBuff(
				connection, item.getItemId(), 0);
		connection.setNeed_recalc_fight_val(true);
	}

	// 勋章升级,id-数据库itemid
	public void OnUpMedal(Connection connection,
			ProItemProto.Msg_C2G_UpLev_Medal msg) throws GameException {
		ConItemAttr items = connection.getItem();
		HeroInfo hero = connection.getHero().getHeroById(msg.getHeroId());
		if (hero == null)
			throw new GameException("英雄不存在");
		// 根据tableId取出被升级的勋章
		MedalInfo medalInfo = hero.getMedalByTableId(msg.getUpLevMedalId());
		if (medalInfo == null)
			throw new GameException("该勋章还没有开启");

		// 检查要吞的物品数量是否满足
		Map<Integer, Integer> needItems = new TreeMap<Integer, Integer>();
		for (Integer id : msg.getBeEatenIdsList()) {
			ItemInfo it = items.getItemById(id);
			if (it == null)
				return;

			int count = 0;
			if (needItems.containsKey(it.getTableId()))
				count = needItems.get(it.getTableId());

			count++;
			needItems.put(it.getTableId(), count);
		}

		for (Entry<Integer, Integer> it : needItems.entrySet()) {
			if (!items.checkItemEnough(it.getKey(), it.getValue()))
				return;
		}

		// 升级
		medalInfo.upLev(connection.getItem().calcItemsExpCount(
				msg.getBeEatenIdsList()));
		hero.addCache(medalInfo.getMedal());

		// 删除材料
		long s_num = IPOManagerDb.getInstance().getNextId();
		for (Integer itemId : msg.getBeEatenIdsList()) {
			ItemInfo itemInfo = connection.getItem().getItemById(itemId);
			connection.getItem().setItemNumber(itemInfo.getTableId(), 1,
					SETNUMBER_TYPE.MINUS, VmChannel.PurchaseGoods,
					ProductChannel.PurchaseGoods, s_num, "",
					Item_Channel.UP_MEDAL);
		}
		IPOManagerDb.getInstance().LogBuyService(connection, "medalup", s_num);
		LogService.logEvent(connection.getPlayerId(), s_num, 8,
				msg.getHeroId(), msg.getUpLevMedalId());
		connection.getTasks().AddTaskNumber(TASK_TYPE.MEDAL_UPGRADE,
				medalInfo.getMedal().getMedal_table_id(), 1, 0);
		hero.updateHeroFightVal();

		ProItemProto.Msg_G2C_UpLev_Medal.Builder result = ProItemProto.Msg_G2C_UpLev_Medal
				.newBuilder();
		result.setHeroId(msg.getHeroId())
				.setItemId(medalInfo.getMedal().medal_table_id)
				.setCurLev(medalInfo.getLvl()).setExp(medalInfo.getExp());
		for (Integer id : msg.getBeEatenIdsList())
			result.addBeEatenIds(id);
		connection.sendReceiptMessage(result.build());
		connection.setNeed_recalc_fight_val(true);
	}

	// 勋章升星,id-数据库itemid
	public void OnUpMedalStar(Connection connection,
			ProItemProto.Msg_C2G_UpStar_Medal msg) throws GameException {
		HeroInfo hero = connection.getHero().getHeroById(msg.getHeroId());
		if (hero == null)
			throw new GameException("英雄不存在");
		// 被升星的勋章
		MedalInfo medalInfo = hero.getMedalByTableId(msg.getUpStarMedalId());
		if (medalInfo == null)
			throw new GameException("该勋章还没有开启");

		MT_Data_MedalStar starObj = TableManager.GetInstance().TableMedalStar()
				.GetElement(msg.getUpStarMedalId() + medalInfo.getStar());
		if (TABLE.IsInvalid(starObj.needNum()))
			throw new GameException("星级达到上限");

		// 判断材料是否足够
		if (!connection.getItem().checkItemEnoughByInt2(starObj.needNum()))
			return;

		medalInfo.upStar();
		hero.addCache(medalInfo.getMedal());
		// 减少材料物品
		long s_num = IPOManagerDb.getInstance().getNextId();
		connection.getItem().setItemNumber(starObj.needNum().field1(),
				starObj.needNum().field2(), SETNUMBER_TYPE.MINUS,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, s_num,
				"", Item_Channel.UP_MEDAL_STAR);
		IPOManagerDb.getInstance().LogBuyService(connection, "upmedalstar",
				s_num);
		LogService.logEvent(connection.getPlayerId(), s_num, 9,
				msg.getHeroId(), msg.getUpStarMedalId());

		connection.getTasks().AddTaskNumber(TASK_TYPE.MEDAL_UPSTAR,
				medalInfo.getMedal().getMedal_table_id(), 1, 0);
		hero.updateHeroFightVal();

		ProItemProto.Msg_G2C_UpStar_Medal.Builder result = ProItemProto.Msg_G2C_UpStar_Medal
				.newBuilder();
		result.setHeroId(msg.getHeroId())
				.setItemId(medalInfo.getMedal().medal_table_id)
				.setCurStar(medalInfo.getStar())
				.setBeEatenId(starObj.needNum().field1())
				.setBeEatenNum(starObj.needNum().field2());
		connection.sendReceiptMessage(result.build());
		connection.setNeed_recalc_fight_val(true);
	}

	// 勋章抽取(金砖抽奖)
	public void OnLuckDrawMedal(Connection connection,
			ProItemProto.Msg_C2G_Luck_Draw_Medal message) throws GameException {
		if (!connection.getPlayer().CheckPlayerLevel(
				Common.LUCKY_LOTTERY_LEVEL, true))
			return;
		long s_num = IPOManagerDb.getInstance().getNextId();
		if (Proto_LuckDrawType.LuckDraw_ONE.equals(message.getType())) {
			luckDrawByGoldOne(connection, s_num);
		} else if (Proto_LuckDrawType.LuckDraw_TEN.equals(message.getType())) {
			luckDrawByGoldTen(connection, s_num);
		} else if (Proto_LuckDrawType.LuckDraw_MONEY_ONE.equals(message
				.getType())) {
			luckDrawByMoneyOne(connection, s_num);
		} else if (Proto_LuckDrawType.LuckDraw_MONEY_TEN.equals(message
				.getType())) {
			luckDrawByMoneyTen(connection, s_num);
		}
		connection.getTasks().AddTaskNumber(TASK_TYPE.DRAW_MEDAL, 0, 1, 0);
		LogService.logEvent(connection.getPlayerId(), s_num, 6);
	}

	// 用金钱十连抽
	private void luckDrawByMoneyTen(Connection connection, long s_num)
			throws GameException {
		MT_Data_LuckDrawMedal mt_Data_LuckDrawMedal = TableManager
				.GetInstance().TableLuckDrawMedal().GetElement(20);
		if (!connection.getItem().checkItemEnoughByInt2(
				mt_Data_LuckDrawMedal.NeedMoney()))
			return;

		connection.getItem().setItemNumber(
				mt_Data_LuckDrawMedal.NeedMoney().field1(),
				mt_Data_LuckDrawMedal.NeedMoney().field2(),
				SETNUMBER_TYPE.MINUS, VmChannel.PurchaseGoods,
				ProductChannel.PurchaseGoods, s_num, "",
				Item_Channel.LUCK_DRAW_MEDAL_MONEY_10);

		ProItemProto.Msg_G2C_Luck_Draw_Medal.Builder rs = ProItemProto.Msg_G2C_Luck_Draw_Medal
				.newBuilder();
		rs.setType(Proto_LuckDrawType.LuckDraw_MONEY_TEN);
		List<Integer> selectedList = new LinkedList<>();
		for (int i = 0; i < 10; i++) {
			Integer selectedtableId = drawOnce(connection, s_num, 12, 10000,
					-1, Item_Channel.LUCK_DRAW_MEDAL_MONEY_10);
			selectedList.add(selectedtableId);
		}
		Collections.shuffle(selectedList);
		rs.addAllItemIds(selectedList);
		connection.sendReceiptMessage(rs.build());
	}

	// 用金钱抽一次
	private void luckDrawByMoneyOne(Connection connection, long s_num)
			throws GameException {
		// 不是免费
		if (!connection.getPlayer().isFree()
				|| !connection.getPlayer().freeTime()) {
			MT_Data_LuckDrawMedal mt_Data_LuckDrawMedal = TableManager
					.GetInstance().TableLuckDrawMedal().GetElement(2);
			if (!connection.getItem().checkItemEnoughByInt2(
					mt_Data_LuckDrawMedal.NeedMoney()))
				return;

			connection.getItem().setItemNumber(
					mt_Data_LuckDrawMedal.NeedMoney().field1(),
					mt_Data_LuckDrawMedal.NeedMoney().field2(),
					SETNUMBER_TYPE.MINUS, VmChannel.PurchaseGoods,
					ProductChannel.PurchaseGoods, s_num, "",
					Item_Channel.LUCK_DRAW_MEDAL_MONEY_1);
		} else {
			connection.getPlayer().setFreeNumAndTime();
		}

		Integer selectedtableId = drawOnce(connection, s_num, 12, 10000, -1,
				Item_Channel.LUCK_DRAW_MEDAL_MONEY_1);
		ProItemProto.Msg_G2C_Luck_Draw_Medal.Builder rs = ProItemProto.Msg_G2C_Luck_Draw_Medal
				.newBuilder();
		rs.setType(Proto_LuckDrawType.LuckDraw_MONEY_ONE);
		rs.addItemIds(selectedtableId);
		connection.sendReceiptMessage(rs.build());
		sendMoneyFreeLuckInfo(connection);
	}

	public void sendMoneyFreeLuckInfo(Connection connection) {
		// 免费抽奖次数相关消息
		ProItemProto.Msg_G2C_Luck_Draw_Medal_Free.Builder res = ProItemProto.Msg_G2C_Luck_Draw_Medal_Free
				.newBuilder();
		Proto_Luck_Draw_Medal_Free_Info.Builder free = Proto_Luck_Draw_Medal_Free_Info
				.newBuilder();
		free.setType(Proto_LuckDrawType.LuckDraw_MONEY_ONE);
		free.setNum(connection.getPlayer().getFreeMoneyNum());
		free.setTime(connection.getPlayer().getFreeMoneyLeftTimeSecond());
		res.addInfos(free);
		connection.sendReceiptMessage(res.build());
	}

	// 用金砖抽一次
	private void luckDrawByGoldOne(Connection connection, long s_num)
			throws GameException {
		// 不是免费抽取
		if (!connection.getPlayer().isFreeGold()) {
			MT_Data_LuckDrawMedal mt_Data_LuckDrawMedal = TableManager
					.GetInstance().TableLuckDrawMedal()
					.GetElement(Common.LUCKDRAWDONE);
			if (!connection.getItem().checkItemEnoughByInt2(
					mt_Data_LuckDrawMedal.NeedMoney()))
				return;
			connection.getItem().setItemNumber(
					mt_Data_LuckDrawMedal.NeedMoney().field1(),
					mt_Data_LuckDrawMedal.NeedMoney().field2(),
					SETNUMBER_TYPE.MINUS, VmChannel.PurchaseGoods,
					ProductChannel.PurchaseGoods, s_num, "",
					Item_Channel.LUCK_DRAW_MEDAL_GOLD_1);
		} else {
			// 设置金砖免费抽取次数和时间
			connection.getPlayer().setGoldTime();
		}

		Integer selectedtableId = drawOnce(connection, s_num, 8, 10000, -1,
				Item_Channel.LUCK_DRAW_MEDAL_GOLD_1);

		ProItemProto.Msg_G2C_Luck_Draw_Medal.Builder rs = ProItemProto.Msg_G2C_Luck_Draw_Medal
				.newBuilder();
		rs.setType(Proto_LuckDrawType.LuckDraw_ONE);
		rs.addItemIds(selectedtableId);
		connection.sendReceiptMessage(rs.build());
		sendGoldFreeLuckInfo(connection);
	}

	public void sendGoldFreeLuckInfo(Connection connection) {
		// 免费抽奖次数相关消息
		ProItemProto.Msg_G2C_Luck_Draw_Medal_Free.Builder res = ProItemProto.Msg_G2C_Luck_Draw_Medal_Free
				.newBuilder();
		Proto_Luck_Draw_Medal_Free_Info.Builder free = Proto_Luck_Draw_Medal_Free_Info
				.newBuilder();
		free.setType(Proto_LuckDrawType.LuckDraw_ONE);
		free.setNum(0);
		free.setTime(connection.getPlayer().getFreeGoldLeftTimeSecond());
		res.addInfos(free);
		connection.sendReceiptMessage(res.build());
	}

	// 用金砖十连抽
	private void luckDrawByGoldTen(Connection connection, long s_num)
			throws GameException {

		MT_Data_LuckDrawMedal mt_Data = TableManager.GetInstance()
				.TableLuckDrawMedal().GetElement(Common.LUCKDRAWDTEN);
		if (!connection.getItem().checkItemEnoughByInt2(mt_Data.NeedMoney()))
			return;

		connection.getItem().setItemNumber(mt_Data.NeedMoney().field1(),
				mt_Data.NeedMoney().field2(), SETNUMBER_TYPE.MINUS,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, s_num,
				"", Item_Channel.LUCK_DRAW_MEDAL_GOLD_10);

		ProItemProto.Msg_G2C_Luck_Draw_Medal.Builder rs = ProItemProto.Msg_G2C_Luck_Draw_Medal
				.newBuilder();
		int num = RandomUtil.RangeRandom(1, 5);// 随机数,紫色勋章的个数

		List<Integer> selectedList = new LinkedList<>();
		for (int j = 0; j < num; j++) {
			Integer selectedtableId = drawOnce(connection, s_num,
					Common.LUCKDRAWDROPOUTTEN, 10000, 1,
					Item_Channel.LUCK_DRAW_MEDAL_GOLD_10);
			selectedList.add(selectedtableId);
		}

		for (int j = 0; j < 10 - num; j++) {
			Integer selectedtableId = drawOnce(connection, s_num,
					Common.LUCKDRAWDROPOUTTEN, 10000, 2,
					Item_Channel.LUCK_DRAW_MEDAL_GOLD_10);
			selectedList.add(selectedtableId);
		}
		// System.out.println("num="+num+";id="+rs.getItemIdsList().toString());
		Collections.shuffle(selectedList);
		rs.addAllItemIds(selectedList);
		rs.setType(Proto_LuckDrawType.LuckDraw_TEN);
		connection.sendReceiptMessage(rs.build());
	}

	// 抽一次的逻辑
	private Integer drawOnce(Connection connection, long s_num,
			int dropOutTableId, int totalRandom, int type, Item_Channel itemch) {
		Integer selectedtableId = 0;
		
		if (!TableManager.GetInstance().TableDropOut().Contains(dropOutTableId)) {
			logger.error("drawOnce, not exist dropid id={}", dropOutTableId);
			return selectedtableId;
		}
		
		Integer begin = 0;
		Integer sum = 0;
		int random = RandomUtil.RangeRandom(1, 10000);// 随机数
		MT_Data_DropOut mt_Data_DropOut = TableManager.GetInstance()
				.TableDropOut().GetElement(dropOutTableId);
		ArrayList<Int6> allDrops = mt_Data_DropOut.Arrays();
		for (Int6 dropInfo : allDrops) {
			if (TABLE.IsInvalid(dropInfo))
				continue;
			if (type == -1 || type == dropInfo.field4()) {
				Integer r = dropInfo.field3();// 某一行的概率值
				sum = r + begin;
				if (random >= begin && random <= sum) {
					selectedtableId = dropInfo.field1();
					connection.getItem()
							.setItemNumber(selectedtableId, 1,
									SETNUMBER_TYPE.ADDITION,
									VmChannel.InGameDrop,
									ProductChannel.InGameDrop, s_num, "",
									false, itemch);
					break;
				}
				begin = sum;
			}
		}
		return selectedtableId;
	}
}
