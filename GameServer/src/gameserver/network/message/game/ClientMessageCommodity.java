package gameserver.network.message.game;

import gameserver.connection.attribute.ConPlayerAttr;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.IOServer;
import gameserver.network.message.share.ShareMessageCommodity;
import gameserver.network.protos.game.ProBuyCommodity;
import gameserver.network.protos.game.ProPvp;
import gameserver.network.server.connection.Connection;
import gameserver.share.ShareServerManager;
import gameserver.thread.ThreadPoolManager;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;
import gameserver.utils.TransFormArgs;
import gameserver.utils.Util;

import java.util.HashMap;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_commodity;
import table.base.TABLE;
import table.base.TableManager;

import commonality.Common;
import commonality.Common.LIMIT_TYPE;
import commonality.Common.SETNUMBER_TYPE;
import commonality.ITEM_ID;
import commonality.ProductChannel;
import commonality.PromptType;
import commonality.VmChannel;

public class ClientMessageCommodity {
	private static final Logger logger = LoggerFactory.getLogger(ClientMessageCommodity.class);
	
	private final static ClientMessageCommodity instance = new ClientMessageCommodity();
	public static ClientMessageCommodity getInstance() { return instance; }
	
	public void initialize(){
		IOServer.getInstance().regMsgProcess(ProBuyCommodity.Msg_C2G_AskItemShop.class, this, "OnItemShop");
		IOServer.getInstance().regMsgProcess(ProBuyCommodity.Msg_C2G_Buy_Commodity.class, this, "OnBuyOneCommodity");
		IOServer.getInstance().regMsgProcess(ProBuyCommodity.Msg_C2G_SupplementMoney.class, this, "OnSupplementMoney");
		IOServer.getInstance().regMsgProcess(ProBuyCommodity.Msg_C2G_BuyQueue.class, this, "OnBuyQueue");
	}
	
	public void OnBuyQueue(Connection connect, ProBuyCommodity.Msg_C2G_BuyQueue msg) throws Exception{
		HashMap<Integer,MT_Data_commodity> map = TableManager.GetInstance().Tablecommodity().Datas();
		ConPlayerAttr player = connect.getPlayer();
		int queueMax = connect.getCommon().GetValue(LIMIT_TYPE.QUEUE);
		if (player.getQueuesize() >= queueMax)
			throw new GameException("购买超出最大数值:" + queueMax);
		MT_Data_commodity data = TableManager.GetInstance().Tablecommodity().GetElement(msg.getQueueId());
		if (connect.getItem().checkItemEnough(data.Attr().field1(), data.Attr().field2()) == false)
			throw new GameException("资源不足:  tableId:" + data.Attr().field1() + "  num:" + data.Attr().field2());
		player.addQueueSize(msg.getQueueSize());

		long s_num = IPOManagerDb.getInstance().getNextId();
		connect.getItem().setItemNumber(map.get(msg.getQueueId()).Attr().field1(), 
				msg.getQueueSize()*map.get(msg.getQueueId()).Attr().field2(), SETNUMBER_TYPE.MINUS,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, s_num, "", Item_Channel.BUY);
		IPOManagerDb.getInstance().LogBuyService(connect, "buyqueue", s_num);

		int curNum = Util.GetCurWorkNum(connect);
		ClientMessageCommon.getInstance().UpdateCountInfo(connect, ProPvp.Proto_ActionType.QUEUE, curNum, player.getQueuesize());
		ProBuyCommodity.Msg_G2C_BuyOver.Builder message = ProBuyCommodity.Msg_G2C_BuyOver.newBuilder();
		message.setTableId(msg.getQueueId());
		connect.sendReceiptMessage(message.build());
		
		connect.ShowPrompt(PromptType.BUY_BUILD_QUEUE);
	}
	
	// 补充金钱
	public void OnSupplementMoney(Connection connect, ProBuyCommodity.Msg_C2G_SupplementMoney msg) throws Exception{
		int money = connect.getItem().getItemCountByTableId(ITEM_ID.MONEY);
		if (money >= msg.getNum())
			return;
		
		int needMoney = msg.getNum() - money;
		int index = 0;
		if (msg.getComId() > 0)
			index = msg.getComId();
		else
		    index = getMoneyConfigIndex(connect, needMoney);
		
		MT_Data_commodity data = TableManager.GetInstance().Tablecommodity().GetElement(index);
		if (data == null)
			return ;
		
		if (TABLE.IsInvalid(data.ratio()))
			return;
		
		int needGold = (int) Math.ceil((float)needMoney / (float)data.ratio().field1() * data.ratio().field2());
		if (connect.getItem().checkItemEnough(ITEM_ID.GOLD, needGold) == false)
			return;
		
		// 检查个人每日限制
		if (!TABLE.IsInvalid(data.num())) {
			if (connect.getPlayer().getCommodityCount(index) >= data.num())
				return ;
		}
		OnSupplementMoney(connect, index, needMoney, msg.getComId() > 0);
	}
	
	private void OnSupplementMoney(Connection connect, int commodityId, int num, boolean sync) {
		MT_Data_commodity data = TableManager.GetInstance().Tablecommodity().GetElement(commodityId);
		if (data == null)
			return;
		
		long s_num = IPOManagerDb.getInstance().getNextId();
		int needGold = (int) Math.ceil((float)num / (float)data.ratio().field1() * data.ratio().field2());
		connect.getItem().setItemNumber(ITEM_ID.MONEY, num, SETNUMBER_TYPE.ADDITION,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"", Item_Channel.BUY);
		connect.getItem().setItemNumber(ITEM_ID.GOLD, needGold, SETNUMBER_TYPE.MINUS,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"", Item_Channel.BUY);
		
		if (!TABLE.IsInvalid(data.num()))
			connect.getPlayer().icrCommodityCount(commodityId);
		
		if (sync) {
			connect.getItem().CheckData();
			
			ProBuyCommodity.Msg_G2C_AskItemShop.Builder message = ProBuyCommodity.Msg_G2C_AskItemShop.newBuilder();
			message.setType(ProBuyCommodity.Proto_CommodityType.UPDATE);
			ProBuyCommodity.Msg_G2C_AskItemShop.Proto_ShopItemInfo.Builder info = ProBuyCommodity.Msg_G2C_AskItemShop.Proto_ShopItemInfo.newBuilder();
			info.setItemId(commodityId);
			info.setTableId(ITEM_ID.MONEY);
			info.setCount(connect.getPlayer().getCommodityCount(commodityId));
			info.setSurplusCount(0);
			message.addInfo(info);
			connect.sendReceiptMessage(message.build());
		}
		
		//记录普通商店购买物品的日志
		LogService.logStore(commodityId,s_num,connect.getPlayerId(),1,1, connect.getPlayer().getLevel());
	}

	// 找到最合适的档位
	private int getMoneyConfigIndex(Connection connect, int needMoney)
			throws Exception {
		int index = 0;
		int maxMoneyCount = connect.getBuild().GetMaxMoneyCount();
		for (Entry<Integer, MT_Data_commodity> pair : TableManager.GetInstance().Tablecommodity().Datas().entrySet()){
			if (pair.getValue().Type().equals(Common.COMMODITY_TYPE.MONEY.Number())){
				int num = maxMoneyCount * pair.getValue().buyNum() / 100;
				if (num <= needMoney)
					index = index == 0 ? pair.getKey() : index;
				else
					index = pair.getKey();
			}
		}
		return index;
	}
	
	// 请求商城物品数据
	public void OnItemShop(Connection connect, ProBuyCommodity.Msg_C2G_AskItemShop msg) throws Exception{
		ProBuyCommodity.Msg_G2S_AskItemShop.Builder message = ProBuyCommodity.Msg_G2S_AskItemShop.newBuilder();
		message.setPlayerId(connect.getPlayerId());
		final Connection cn = connect;
		final ProBuyCommodity.Msg_G2S_AskItemShop mg = message.build();
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				try {
					ShareServerManager.getInstance().sendMsgShop(cn, mg);
				} catch (GameException e) {
				}catch (Exception e) {
					logger.error("OnItemShop error ", e);
				}
			}
		}, 0);
	}
	
	public void OnBuyOneCommodity(Connection connect, ProBuyCommodity.Msg_C2G_Buy_Commodity msg) throws Exception {
		OnBuyOneCommodity(connect, msg.getCommodityId(), true);
	}
	
	// 购买物品
	public void OnBuyOneCommodity(Connection connect, int commodityId, boolean isShowProto) throws Exception {
		MT_Data_commodity data = TableManager.GetInstance().Tablecommodity().GetElement(commodityId);
		if (data == null)
			throw new GameException("MT_Data_commodity is null CommodityId:{}", commodityId);
		if (!connect.getPlayer().CheckPlayerLevel(data.needLev(), true))
			return ;
		if (!connect.getItem().checkItemEnough(data.Attr().field1(), data.Attr().field2()))
			throw new GameException("不满足购买条件  需要  tableId:{} num:{}", data.Attr().field1(), data.Attr().field2());

		// 检查个人每日限制
		if (!TABLE.IsInvalid(data.num())) {
			if (connect.getPlayer().getCommodityCount(commodityId) >= data.num())
				throw new GameException(PromptType.ITEM_BUY_LIMIT,"今天购买次数已满",TransFormArgs.CreateCommdityArgs(commodityId));
		}

		// 有全局限制，就去SG检查
		if (!TABLE.IsInvalid(data.maxNum()))
			sendBuyOneItem(connect, data);
		else
			ShareMessageCommodity.getInstance().OnBuyCommodity(connect, commodityId, 0, isShowProto);
	}
	
	void sendBuyOneItem(Connection connect, MT_Data_commodity data) throws Exception {
		ProBuyCommodity.Msg_G2S_AskBuyCommodity.Builder message = ProBuyCommodity.Msg_G2S_AskBuyCommodity.newBuilder();
		message.setPlayerId(connect.getPlayerId());
		message.setTableId(TABLE.IsInvalid(data.belong()) == true ? data.index() : data.belong());
		ShareServerManager.getInstance().sendMsgShop(connect, message.build());
	}
}
