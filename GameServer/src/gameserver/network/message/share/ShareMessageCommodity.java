package gameserver.network.message.share;

import gameserver.connection.attribute.ConPlayerAttr;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.protos.game.ProBuyCommodity;
import gameserver.network.server.connection.Connection;
import gameserver.share.ShareServerManager;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_Item;
import table.MT_Data_commodity;
import table.base.TABLE;
import table.base.TableManager;

import com.commons.util.TimeUtil;
import commonality.Common;
import commonality.Common.SETNUMBER_TYPE;
import commonality.ProductChannel;
import commonality.PromptType;
import commonality.SHARE_SERVER_TYPE;
import commonality.VmChannel;

public class ShareMessageCommodity {
	private final static ShareMessageCommodity instance = new ShareMessageCommodity();
	public static ShareMessageCommodity getInstance() { return instance; }
	private final Logger logger = LoggerFactory.getLogger(ShareMessageCommodity.class);
	
	public void initialize(){
		ShareServerManager.getInstance().regMsgProcess(ProBuyCommodity.Msg_S2G_AskItemShop.class, this, "OnItemShop");
		ShareServerManager.getInstance().regMsgProcess(ProBuyCommodity.Msg_S2G_AskBuyCommodity.class, this, "OnBuyCommodity");
	}
	
	// 购买护盾
	public void OnBuyShield(Connection connect, int commodityId){
		ConPlayerAttr player = connect.getPlayer();
		if (player.getShieldEndTime() > TimeUtil.GetDateTime()) {
			connect.ShowPrompt(PromptType.ALREADY_IN_SHIELD);
			return;
		}
		
		MT_Data_commodity commodityData = TableManager.GetInstance().Tablecommodity().GetElement(commodityId);
		//设置护盾
		long shield_end_time = TimeUtil.GetDateTime() + commodityData.buyNum() * 24 * 60 * 60 * 1000L;
		connect.getPlayer().SetPlayerShieldEndTime(shield_end_time);
		//花费
		long s_num = IPOManagerDb.getInstance().getNextId();
		connect.getItem().setItemNumber(commodityData.Attr().field1(), commodityData.Attr().field2(), SETNUMBER_TYPE.MINUS,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"", Item_Channel.BUY);
		
		//记录普通商店购买物品的日志
		LogService.logStore(commodityId,s_num,connect.getPlayerId(),1,1,connect.getPlayer().getLevel());
	}
	
	// 购买商品
	public void OnBuyCommodity(Connection connect, SHARE_SERVER_TYPE type,int id, ProBuyCommodity.Msg_S2G_AskBuyCommodity msg) throws Exception {
		OnBuyCommodity(connect, msg.getTableId(), msg.getSurplusCount(),true);
	}
	
	public void OnBuyCommodity(Connection connect, int commodityId, int total_count,boolean isShowPoto) throws Exception{
		MT_Data_commodity commodityData = TableManager.GetInstance().Tablecommodity().GetElement(commodityId);
		if (commodityData == null)
			throw new GameException("MT_Data_commodity is null commodityId:{}", commodityId);
		if (TABLE.IsInvalid(commodityData.belong()) == false)
			commodityData = TableManager.GetInstance().Tablecommodity().GetElement(commodityId + connect.getPlayer().getCommodityCount(commodityId));

		// 如果购买的是护盾
		if (commodityData.Type().equals(Common.COMMODITY_TYPE.SHIELD.Number())) {
			OnBuyShield(connect, commodityId);
			return;
		}

		MT_Data_Item itemData = TableManager.GetInstance().TableItem().GetElement(commodityData.itemTableId());
		if (itemData == null)
			throw new GameException("MT_Data_Item is null itemId:{}", commodityData.itemTableId());

		int buyNum = commodityData.buyNum();
		if (commodityData.Type().equals(Common.COMMODITY_TYPE.MONEY.Number())) {
			buyNum = connect.getBuild().GetMaxMoneyCount() * commodityData.buyNum() / 100;
		}

		long s_num = IPOManagerDb.getInstance().getNextId();
		connect.getItem().setItemNumber(commodityData.itemTableId(), buyNum, SETNUMBER_TYPE.ADDITION,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"",isShowPoto, Item_Channel.BUY);
		connect.getItem().setItemNumber(commodityData.Attr().field1(), commodityData.Attr().field2(), SETNUMBER_TYPE.MINUS,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"", Item_Channel.BUY);
		//记录普通商店购买物品的日志
		LogService.logStore(commodityId,s_num,connect.getPlayerId(),1,1,connect.getPlayer().getLevel());

		// 记录购买次数
		if (!TABLE.IsInvalid(commodityData.num()))
			connect.getPlayer().icrCommodityCount(commodityId);

		ProBuyCommodity.Msg_G2C_AskItemShop.Builder message = ProBuyCommodity.Msg_G2C_AskItemShop.newBuilder();
		message.setType(ProBuyCommodity.Proto_CommodityType.UPDATE);
		ProBuyCommodity.Msg_G2C_AskItemShop.Proto_ShopItemInfo.Builder info = ProBuyCommodity.Msg_G2C_AskItemShop.Proto_ShopItemInfo.newBuilder();
		info.setItemId(commodityId);
		info.setTableId(commodityData.itemTableId());
		info.setCount(connect.getPlayer().getCommodityCount(commodityId));
		info.setSurplusCount(total_count);
		message.addInfo(info);
		connect.sendReceiptMessage(message.build());
	}
	
	// 请求商城物品
	public void OnItemShop(Connection connect, SHARE_SERVER_TYPE type,int id, ProBuyCommodity.Msg_S2G_AskItemShop msg){
		ProBuyCommodity.Msg_G2C_AskItemShop.Builder message = ProBuyCommodity.Msg_G2C_AskItemShop.newBuilder();
		
		for (ProBuyCommodity.Msg_S2G_AskItemShop.Proto_ShopItemInfo pair : msg.getInfoList()){
			ProBuyCommodity.Msg_G2C_AskItemShop.Proto_ShopItemInfo.Builder info = ProBuyCommodity.Msg_G2C_AskItemShop.Proto_ShopItemInfo.newBuilder(); 
			info.setItemId(pair.getItemId());
			info.setTableId(pair.getTableId());
			
			// 注意，个人限制放到gs这边了，所以这里要取一下
			info.setCount(connect.getPlayer().getCommodityCount(pair.getItemId()));
			info.setSurplusCount(pair.getSurplusCount());
			message.addInfo(info);
		}
		message.setType(ProBuyCommodity.Proto_CommodityType.LIST);
		connect.sendReceiptMessage(message.build());
	}
}
