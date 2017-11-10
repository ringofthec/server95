package gameserver.network.message.game;

import gameserver.connection.attribute.ConItemAttr;
import gameserver.connection.attribute.info.HeroInfo;
import gameserver.ipo.IPOManager;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.IOServer;
import gameserver.network.protos.game.ProGameInfo.Msg_G2C_PlaySound;
import gameserver.network.protos.game.ProItemProto.Msg_G2C_FunctionComplete;
import gameserver.network.protos.game.ProHero;
import gameserver.network.server.connection.Connection;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;
import gameserver.utils.UtilItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.MT_Data_Hero;
import table.MT_Data_HeroCall;
import table.MT_Data_HeroInforce;
import table.MT_Data_commodity;
import table.base.TableManager;

import com.commons.util.RandomUtil;

import commonality.Common.SETNUMBER_TYPE;
import commonality.Common.TASK_TYPE;
import commonality.ITEM_ID;
import commonality.ProductChannel;
import commonality.PromptType;
import commonality.VmChannel;

public class ClientMessageHero {
	private final Logger logger = LoggerFactory.getLogger(ClientMessageHero.class);
	
	private final static ClientMessageHero instance = new ClientMessageHero();
	public static ClientMessageHero getInstance() { return instance; }
	public void initialize() {
		IOServer.getInstance().regMsgProcess(ProHero.Msg_C2G_SelHero.class,this, "OnSelHero");
		IOServer.getInstance().regMsgProcess(ProHero.Msg_C2G_HeroLvlUp.class, this, "OnHeroLvlUp");
		IOServer.getInstance().regMsgProcess(ProHero.Msg_C2G_HeroStarUp.class, this, "OnHeroStarUp");
	}
	
	
	public void OnHeroLvlUp(Connection connection, ProHero.Msg_C2G_HeroLvlUp msg) {
		int heroId = msg.getHeroId();
		HeroInfo hi = connection.getHero().getHeroById(heroId);
		if (hi == null)
			return ;
		
		int curHeroStar = hi.getStar();
		if (curHeroStar >= TableManager.GetInstance().getHeroMaxStar())
			return ;
		
		MT_Data_HeroInforce heroinfoceconfig = TableManager.GetInstance()
				.TableHeroInforce().GetElement(hi.getData().ID() * 10000 + curHeroStar);
		if (heroinfoceconfig == null)
			return ;
		
		int curLvl = hi.getLvl();
		int maxLvl = TableManager.GetInstance().getMaxHeroLvl(heroinfoceconfig);
		if (curLvl >= maxLvl)
			return ;
		
		ConItemAttr items = connection.getItem();
		if (!items.checkItemEnough(ITEM_ID.MONEY, heroinfoceconfig.lvlmoney()))
			return ;
		
		int itemcost = heroinfoceconfig.lvlitem();
		int goldcost = 0;
		int alreadyItemCount = items.getItemCountByTableId(ITEM_ID.HERO_YINGJI);
		if (alreadyItemCount < itemcost) {
			int lastCount = itemcost - alreadyItemCount;
			itemcost = alreadyItemCount;
			
			MT_Data_commodity conf = TableManager.GetInstance().Tablecommodity().GetElement(320);
			goldcost = conf.Attr().field2() * lastCount;
			if (items.getItemCountByTableId(ITEM_ID.GOLD) < goldcost) {
				items.sendItemNotEnoughMsg(ITEM_ID.HERO_YINGJI);
				return ;
			}
		}
		
		long costId = IPOManager.getInstance().getNextId();
		items.setItemNumber(ITEM_ID.MONEY, heroinfoceconfig.lvlmoney(), 
				SETNUMBER_TYPE.MINUS, VmChannel.CompoundFromOtherVm, 
				ProductChannel.CompoundFromOtherGoods, costId, "", true, Item_Channel.HERO_LVL_UP);
			
		if (itemcost > 0)
			items.setItemNumber(ITEM_ID.HERO_YINGJI, itemcost, 
					SETNUMBER_TYPE.MINUS, VmChannel.CompoundFromOtherVm, 
					ProductChannel.CompoundFromOtherGoods, costId, "", true, Item_Channel.HERO_LVL_UP);
			
		if (goldcost > 0)
			items.setItemNumber(ITEM_ID.GOLD, goldcost, 
					SETNUMBER_TYPE.MINUS, VmChannel.CompoundFromOtherVm, 
					ProductChannel.CompoundFromOtherGoods, costId, "", true, Item_Channel.HERO_LVL_UP);
		
		connection.getTasks().AddTaskNumber(TASK_TYPE.FORCE_HERO, 0, 1, 0);
		int sucessrate = RandomUtil.RangeRandom(1, 10000);
		logger.info("herolvlup,star={},lvl={},random={},config={}",hi.getStar(),hi.getLvl(),sucessrate,heroinfoceconfig.lvlrate());
		if (sucessrate > heroinfoceconfig.lvlrate()) {
			connection.ShowPrompt(PromptType.HERO_LVL_FAIL);
			LogService.logEvent(connection.getPlayerId(), costId, 37, curHeroStar, curLvl);
			connection.sendPushMessage(Msg_G2C_PlaySound.newBuilder().setName("smallprize").build());
			return ;
		}
		
		int maxRandom = 0;
		if (heroinfoceconfig.hplimit() > 0 && hi.getHpLvl() < heroinfoceconfig.hplimit())
			maxRandom += heroinfoceconfig.hplimit();
		if (heroinfoceconfig.attacklimit() > 0 && hi.getAttackLvl() < heroinfoceconfig.attacklimit())
			maxRandom += heroinfoceconfig.attacklimit();
		if (heroinfoceconfig.defenlimit() > 0 && hi.getDefenLvl() < heroinfoceconfig.defenlimit())
			maxRandom += heroinfoceconfig.defenlimit();
		
		int lvlup = RandomUtil.RangeRandom(1, maxRandom);
		int attr_type = 0;
		int des_lvl = 0;
		while (true) {
			if (lvlup <= heroinfoceconfig.hplimit() && 
					hi.getHpLvl() < heroinfoceconfig.hplimit())
			{
				attr_type = 1;
				des_lvl = hi.hpLvlUp();
				break;
			} else if (hi.getHpLvl() < heroinfoceconfig.hplimit()) {
				lvlup -= heroinfoceconfig.hplimit();
			}
			
			if (lvlup <= heroinfoceconfig.attacklimit() && 
					hi.getAttackLvl() < heroinfoceconfig.attacklimit()) 
			{
				attr_type = 2;
				des_lvl = hi.attackLvlUp();
				break;
			}
			
			attr_type = 3;
			des_lvl = hi.defenLvlUp();
			break;
		}
		
		ClientMessageHero.getInstance().OnHeroData(connection, hi);
		LogService.logEvent(connection.getPlayerId(), costId, 32, attr_type, des_lvl);
		connection.ShowPrompt(PromptType.HERO_LVL_SUCCESS);
		connection.sendPushMessage(Msg_G2C_PlaySound.newBuilder().setName("smallprize").build());
		hi.updateHeroFightVal();
		connection.setNeed_recalc_fight_val(true);
	}
	
	static Map<Integer, UtilItem> starup_rate = new FastMap<Integer, UtilItem>();
	static {
		starup_rate.put(0, new UtilItem(4, 7));
		starup_rate.put(1, new UtilItem(8, 14));
		starup_rate.put(2, new UtilItem(10, 18));
		starup_rate.put(3, new UtilItem(16, 28));
		starup_rate.put(4, new UtilItem(40, 70));
	}
	public void OnHeroStarUp(Connection connection, ProHero.Msg_C2G_HeroStarUp msg) {
		int heroId = msg.getHeroId();
		HeroInfo hi = connection.getHero().getHeroById(heroId);
		if (hi == null)
			return ;
		
		int curHeroStar = hi.getStar();
		if (curHeroStar >= TableManager.GetInstance().getHeroMaxStar())
			return ;
		
		MT_Data_HeroInforce heroinfoceconfig = TableManager.GetInstance()
				.TableHeroInforce().GetElement(hi.getData().ID() * 10000 + curHeroStar);
		if (heroinfoceconfig == null)
			return ;
		
		int curLvl = hi.getLvl();
		int maxLvl = TableManager.GetInstance().getMaxHeroLvl(heroinfoceconfig);
		if (curLvl != maxLvl)
			return ;
		
		ConItemAttr items = connection.getItem();
		
		int itemcost = heroinfoceconfig.staritem();
		int goldcost = 0;
		int alreadyItemCount = items.getItemCountByTableId(ITEM_ID.HERO_HUN);
		if (alreadyItemCount < itemcost) {
			int lastCount = itemcost - alreadyItemCount;
			itemcost = alreadyItemCount;
			
			MT_Data_commodity conf = TableManager.GetInstance().Tablecommodity().GetElement(321);
			goldcost = conf.Attr().field2() * lastCount;
			if (items.getItemCountByTableId(ITEM_ID.GOLD) < goldcost) {
				items.sendItemNotEnoughMsg(ITEM_ID.HERO_HUN);
				return ;
			}
		}
		
		long costId = IPOManager.getInstance().getNextId();
		if (itemcost > 0)
			items.setItemNumber(ITEM_ID.HERO_HUN, itemcost, 
					SETNUMBER_TYPE.MINUS, VmChannel.CompoundFromOtherVm, 
					ProductChannel.CompoundFromOtherGoods, costId, "", true, Item_Channel.HERO_STAR_UP);
		if (goldcost > 0)
			items.setItemNumber(ITEM_ID.GOLD, goldcost, 
					SETNUMBER_TYPE.MINUS, VmChannel.CompoundFromOtherVm, 
					ProductChannel.CompoundFromOtherGoods, costId, "", true, Item_Channel.HERO_STAR_UP);
		
		Msg_G2C_FunctionComplete.Builder builder = Msg_G2C_FunctionComplete.newBuilder().setFunctionid(2);
		UtilItem ratecountConf = starup_rate.get(curHeroStar);
		if (hi.getStarCount() + 1 <= ratecountConf.GetItemId()) {
			starupFail(connection, curHeroStar, curLvl, costId, builder, hi);
			return ;
		} else if (hi.getStarCount() + 1 >= ratecountConf.GetCount()) {
			starupsuccess(connection, hi, costId, builder);
		} else if (hi.getLuckVal() >= heroinfoceconfig.getM_nluckmax()) {
			starupsuccess(connection, hi, costId, builder);
		} else {
			int sucessrate = RandomUtil.RangeRandom(1, 10000);
			logger.info("herostarup,star={},lvl={},random={},config={}",hi.getStar(),hi.getLvl(),sucessrate,heroinfoceconfig.starrate());
			if (sucessrate > heroinfoceconfig.starrate()) {
				starupFail(connection, curHeroStar, curLvl, costId, builder, hi);
				return ;
			}
			
			starupsuccess(connection, hi, costId, builder);
		}
	}
	private void starupsuccess(Connection connection, HeroInfo hi, long costId,
			Msg_G2C_FunctionComplete.Builder builder) {
		int oldStar = hi.getStar();
		hi.starUp();
		ClientMessageHero.getInstance().OnHeroData(connection, hi);
		LogService.logEvent(connection.getPlayerId(), costId, 33, oldStar, hi.getStar());
		connection.ShowPrompt(PromptType.HERO_STAR_SUCCESS);
		connection.sendPushMessage(Msg_G2C_PlaySound.newBuilder().setName("smallprize").build());
		hi.updateHeroFightVal();
		connection.sendPushMessage(builder.setMask(1).build());
		connection.setNeed_recalc_fight_val(true);
	}
	private void starupFail(Connection connection, int curHeroStar, int curLvl,
			long costId, Msg_G2C_FunctionComplete.Builder builder, HeroInfo hero) {
		hero.addStarCount();
		int addVal = hero.addLuckVal();
		ClientMessageHero.getInstance().OnAskHeroLuckVal(connection, hero);
		connection.ShowPrompt(PromptType.HERO_STAR_FAIL,addVal);
		LogService.logEvent(connection.getPlayerId(), costId, 36, curHeroStar, curLvl);
		connection.sendPushMessage(Msg_G2C_PlaySound.newBuilder().setName("smallprize").build());
		connection.sendPushMessage(builder.setMask(0).build());
	}
	
	// 选择英雄
	public void OnSelHero(Connection connection, ProHero.Msg_C2G_SelHero msg) throws GameException {
		MT_Data_Hero data = TableManager.GetInstance().TableHero().GetElement(msg.getTableId());
		if (data == null)
			throw new GameException("找不到匹配的数据    tableId:{}",msg.getTableId());
		if (connection.getHero().isHaveHeroByTableId(data.ID()))
			throw new GameException("英雄以存在    tableId:{}",data.ID());
		
		int num = connection.getHero().getHeroCount() + 1;
		MT_Data_HeroCall hc = TableManager.GetInstance().TableHeroCall().GetElement(num);
		if (hc == null)
			return ;
		
		if (!connection.getPlayer().CheckPlayerLevel(hc.levelneed(), true))
			return ;
		
		if (!connection.getItem().checkItemEnough(ITEM_ID.GOLD, hc.goldneed()))
			return ;

		long s_num = IPOManagerDb.getInstance().getNextId();
		HeroInfo hero = connection.getHero().insertHero(data.ID());
		for (Int2 initEquip : hero.getData().FirstEquip()) {
			connection.getItem().setItemNumber(initEquip.field1(), initEquip.field2(),
					SETNUMBER_TYPE.ADDITION, VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, s_num, "", Item_Channel.SEL_HERO);
		}
		
		connection.getItem().setItemNumber(ITEM_ID.GOLD, hc.goldneed(),
				SETNUMBER_TYPE.MINUS, VmChannel.CompoundFromOtherVm, 
				ProductChannel.CompoundFromOtherGoods, s_num, "", true, Item_Channel.SEL_HERO);

		ProHero.Msg_G2C_AskHeroData.Builder message = ProHero.Msg_G2C_AskHeroData.newBuilder();
		message.setType(ProHero.HeroType.HERO_UPDATE);
		ProHero.ProtoHeroInfo.Builder info = connection.getHero().buildHeroMessage(hero);
		message.addInfo(info.build());
		connection.sendReceiptMessage(message.build());
		
		hero.updateHeroFightVal();
		connection.setNeed_recalc_fight_val(true);
		
		//记录选择英雄的日志
		LogService.logEvent(connection.getPlayerId(), s_num, 19, msg.getTableId());
	}
	
	// 更新某个英雄
	public void OnHeroData(Connection connection, int Id) {
		HeroInfo hero = connection.getHero().getHeroById(Id);
		if (hero == null)
			return ;
		
		List<HeroInfo> list = new ArrayList<HeroInfo>();
		list.add(hero);
		
		OnHeroDatas(connection, list, ProHero.HeroType.HERO_UPDATE);
	}
	
	public void OnHeroData(Connection connection, HeroInfo info) {
		List<HeroInfo> list = new ArrayList<HeroInfo>();
		list.add(info);
		
		OnHeroDatas(connection, list, ProHero.HeroType.HERO_UPDATE);
	}
	
	public void OnAskHeroLuckVal(Connection connection, HeroInfo info) {
		ProHero.Msg_G2C_AskHeroLuckVal.Builder message = ProHero.Msg_G2C_AskHeroLuckVal.newBuilder();
		message.setTableId(info.getTableId());
		message.setLuckVal(info.getLuckVal());
		connection.sendReceiptMessage(message.build());
	}
	
	//请求英雄数据
	public void OnHeroDatas(Connection connection, Collection<HeroInfo> heros, ProHero.HeroType type) {
		if (heros == null || heros.isEmpty())
			return ;
		
		ProHero.Msg_G2C_AskHeroData.Builder message = ProHero.Msg_G2C_AskHeroData.newBuilder();
		message.setType(type);
		for (HeroInfo pair : heros) {
			ProHero.ProtoHeroInfo.Builder info = connection.getHero().buildHeroMessage(pair);
			message.addInfo(info);
		}
		connection.sendReceiptMessage(message.build());
	}
}
