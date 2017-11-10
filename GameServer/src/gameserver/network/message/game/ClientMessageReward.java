package gameserver.network.message.game;

import gameserver.config.GameConfig;
import gameserver.config.ServerConfig;
import gameserver.connection.attribute.ConPlayerAttr;
import gameserver.ipo.IPOManager;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.IOServer;
import gameserver.network.protos.game.ProReward;
import gameserver.network.protos.game.ProReward.Proto_RewardType;
import gameserver.network.server.connection.Connection;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;
import gameserver.utils.Util;
import gameserver.utils.UtilItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.MT_Data_Item;
import table.MT_Data_LevelReward;
import table.MT_Data_LoginReward;
import table.MT_Data_Luckdraw;
import table.MT_Data_TimeReward;
import table.MT_Data_TimeRewardConfig;
import table.MT_Data_commodity;
import table.base.TableManager;

import com.commons.util.RandomUtil;
import com.commons.util.TimeUtil;
import commonality.Common;
import commonality.Common.SETNUMBER_TYPE;
import commonality.Common.TASK_TYPE;
import commonality.ITEM_ID;
import commonality.ProductChannel;
import commonality.VmChannel;

public class ClientMessageReward {
	private final Logger logger = LoggerFactory.getLogger(ClientMessageReward.class);
	private final static ClientMessageReward instance = new ClientMessageReward();
	public static ClientMessageReward getInstance() {
		return instance;
	}
	        
	public void initialize() {
		IOServer.getInstance().regMsgProcess(ProReward.Msg_C2G_Reward.class,this, "OnRewardOrOnAskBegin");
		IOServer.getInstance().regMsgProcess(ProReward.Msg_C2G_End.class,this, "OnAskEnd");
		IOServer.getInstance().regMsgProcess(ProReward.Msg_C2G_OpenReward.class, this, "OnOpenReward");
	}
	
	public void OnRewardOrOnAskBegin(Connection connection, ProReward.Msg_C2G_Reward message) throws Exception{
		if (message.getType().equals(Proto_RewardType.TIMER_REWARD)) {
			 OnAskBegin(connection,message);
		}else if (message.getType().equals(Proto_RewardType.FUND_REWARD)){
			OnFundReward(connection,message);
		}else {
			OnReward(connection,message);
		}
	}
	
	public void OnFundReward(Connection connection, ProReward.Msg_C2G_Reward message) throws Exception{
		ConPlayerAttr player = connection.getPlayer();
		long now = TimeUtil.GetDateTime();
		if (ServerConfig.country.equals("US")) {
			if (TimeUtil.canFlush(player.getFundRecvDate(), now)) {
				getFund(connection, player, now);
			}
		} else {
			if (!TimeUtil.IsSameDay(player.getFundRecvDate(), now)) {
				getFund(connection, player, now);
			}
		}
	}

	private void getFund(Connection connection, ConPlayerAttr player, long now) {
		long s_num = IPOManager.getInstance().getNextId();
		connection.getItem().setItemNumber(ITEM_ID.GOLD, GameConfig.fund_day_back_gold,
				SETNUMBER_TYPE.ADDITION, VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,s_num,"",Item_Channel.FUND_GOLD);
		player.setFundRecvDate(now);
		player.syncFund();
		LogService.logEvent(player.getPlayerId(), s_num, 30, (int)(now/1000));
	}
	
	//领取奖励
	public void OnReward(Connection connection, ProReward.Msg_C2G_Reward message) throws Exception{
		ProReward.Msg_G2C_AskRewardInfo.Builder result = ProReward.Msg_G2C_AskRewardInfo.newBuilder();
		ConPlayerAttr player = connection.getPlayer();
		Integer playerLv = player.getLevel();
		if (isLoginReward(message)) {
			//登陆奖励
			int reward_num = player.getContinueLoginReward();
			int login_day = Math.min(player.getContinueLoginDay(), 5);
			if (reward_num>=login_day)
				throw new GameException("奖励已经领取");
			
			MT_Data_LoginReward loginReward = TableManager.GetInstance().TableLoginReward().GetElement(reward_num + 1);
			if (loginReward == null)
				throw new GameException("该奖励不存在");
			
			player.reciveLoginReward(loginReward);
			
			result.setNum(player.getContinueLoginDay());
			result.setNumReward(player.getContinueLoginReward());
		}else if (isLvReward(message)) {
			//等级奖励
			List<Integer> ids = connection.getPlayer().getAllLevRewardTableIds();
			if (ids.contains(message.getTableId()))
				throw new GameException("奖励已经领取");
			
			MT_Data_LevelReward levReward = TableManager.GetInstance().TableLevelReward().GetElement(message.getTableId());
			if (levReward == null)
				throw new GameException("该奖励不存在");
			if (playerLv < levReward.lev()) 
				throw new GameException("玩家等级不足，无法领取此奖励");
			Map<Integer, Integer> rewardItems = connection.getPlayer().getLevelReward(levReward);
			int count = Util.getEqiepOrDesCount(rewardItems);
			if (!connection.getItem().checkEquipFragCount(count))
				return ;
			
			connection.getPlayer().reciveLevelReward(rewardItems, levReward.ID());//领取等级奖励
			result.addAllList(connection.getPlayer().getUnReciveLevRewardTableIds());
		}
		result.setType(message.getType());
		connection.sendReceiptMessage(result.build());
	}

	private boolean isLvReward(ProReward.Msg_C2G_Reward message) {
		return message.getType().equals(Proto_RewardType.LEVEL_REWARD);
	}

	private boolean isLoginReward(ProReward.Msg_C2G_Reward message) {
		return message.getType().equals(Proto_RewardType.LOGIN_REWARD);
	}
	
	//请求转盘抽奖
	public void OnAskBegin(Connection connection, ProReward.Msg_C2G_Reward message) throws Exception{
		ConPlayerAttr player = connection.getPlayer();
		if (!connection.getPlayer().CheckPlayerLevel(Common.LUCKY_LOTTERY_LEVEL, true))
			return ;
		
		if (player.isTimeRewarding())
			return ;
		
		if (player.isFirstLuckDraw()){
			//第一次转盘抽奖,必出神秘宝箱,宝箱的tabled=3，数量=1
			connection.getPlayer().addReward(3,1);
			sendMessage(connection,3,1);
			connection.getTasks().AddTaskNumber(TASK_TYPE.GET_ONLINE_REWARD, 0, 0, 0);
			player.setTimeRewarding(true);
			return;
		}
		
		//上次抽奖到现在时间间隔。秒
		long now = TimeUtil.GetDateTime();
		long date = (now - player.getprevious_time())/1000;
		MT_Data_Luckdraw mt_Data_Luckdraw = TableManager.GetInstance().TableLuckdraw().GetElement(player.getluck_draw_num()+1);
		if (mt_Data_Luckdraw==null)
			throw new GameException("本日抽奖次数已满");
		if (date < mt_Data_Luckdraw.time() * 60) 
			throw new GameException("时间间隔不足,抽奖时间没到");
		
		//随机物品
		Integer selected = 0;
		Integer selecteditemId = 0;
		Integer begin = 0;
		Integer sum = 0;
		HashMap<Integer,MT_Data_TimeReward> map = TableManager.GetInstance().TableTimeReward().Datas();
		int random = new Random().nextInt(10000);//随机数
		for (Map.Entry<Integer,MT_Data_TimeReward> entry : map.entrySet()) {
			Integer r = entry.getValue().Random();//某一行的概率值
			sum = r+begin;
			if (random>=begin&&random<=sum) {
				selected = entry.getValue().ID();
				selecteditemId = entry.getValue().TableId();
				break;
			}
			begin = sum;
		}
		
		//随机物品数量
		MT_Data_TimeReward mt = TableManager.GetInstance().TableTimeReward().GetElement(selected);
		ArrayList<Int2> lsArrayList = mt.LevelExtent();
		int num = 0;
		for (int i = 0; i < lsArrayList.size(); i++) {
			Int2 int2 = lsArrayList.get(i);
			if (player.getLevel()>=int2.field1()&&player.getLevel()<=int2.field2()) {
				StringBuffer stringBuffer = new StringBuffer("CountExtent").append(i);
				Int2 obj = (Int2) mt.GetDataByString(stringBuffer.toString());
				num = RandomUtil.RangeRandom(obj.field1(), obj.field2());
				break;
			}
		}
		
		//保存本次抽到的奖品
		connection.getPlayer().addReward(selecteditemId, num);
		sendMessage(connection, selected, num);
		connection.getTasks().AddTaskNumber(TASK_TYPE.GET_ONLINE_REWARD, 0, 0, 0);
		
		player.setTimeRewarding(true);
	}

	private void sendMessage(Connection connection,int TableId,int num) {
		ProReward.Msg_G2C_Begin.Builder result = ProReward.Msg_G2C_Begin.newBuilder();
		//result.setTableId(TableId);
		//result.setNum(num);
		connection.sendReceiptMessage(result.build());
	}
	
	//转盘抽奖结束
	public void OnAskEnd(Connection connection, ProReward.Msg_C2G_End message) throws GameException{
		ConPlayerAttr player = connection.getPlayer();
		if (!connection.getPlayer().CheckPlayerLevel(Common.LUCKY_LOTTERY_LEVEL, true)) 
			return ;
		
		if (!player.isTimeRewarding())
			return ;
		UtilItem utilItem = connection.getPlayer().getReward();
		if (utilItem==null)
			return;
		MT_Data_Item itemData = TableManager.GetInstance().TableItem().GetElement(utilItem.GetItemId());
		if (itemData == null){
			logger.error("没有配置物品。id={}" , utilItem.GetItemId());
			return;
		}
		long s_sum = IPOManagerDb.getInstance().getNextId();
		if (!itemData.Type().equals(Common.ITEMTYPE.TimerRewardBox.Number())) {
			connection.getItem().setItemNumber(utilItem.GetItemId(), utilItem.GetCount(), SETNUMBER_TYPE.ADDITION,
					VmChannel.InGameDrop, ProductChannel.InGameDrop, s_sum,"", Item_Channel.ONLINE_REWARD);
			connection.getPlayer().clearReward();
		}
		player.luckDraw();
		LogService.logEvent(player.getPlayerId(), s_sum, 13);
		
		MT_Data_Luckdraw mt_Data = TableManager.GetInstance().TableLuckdraw().GetElement(player.getluck_draw_num()+1);
		sendMsg(connection, mt_Data);
		player.setTimeRewarding(false);
	}

	private void sendMsg(Connection connection, MT_Data_Luckdraw mt_Data) {
		ProReward.Msg_G2C_AskRewardInfo.Builder rs = ProReward.Msg_G2C_AskRewardInfo.newBuilder();
		rs.setType(Proto_RewardType.TIMER_REWARD);
		if (mt_Data==null) {
			rs.setTime(-1);
		} else {
			rs.setTime(mt_Data.time()*60);//距离下次抽奖的时间
		}
		connection.sendReceiptMessage(rs.build());
	}
	
	// 开启特殊宝箱
	public void OnOpenReward(Connection connection, ProReward.Msg_C2G_OpenReward message) throws Exception {
		ConPlayerAttr playerAttribute = connection.getPlayer();
		UtilItem utilItem = connection.getPlayer().getReward();
		if (utilItem == null)
			throw new GameException("没有设置奖励");

		long s_sum = IPOManagerDb.getInstance().getNextId();
		MT_Data_TimeRewardConfig timeRewardConfig = TableManager.GetInstance().TableTimeRewardConfig().GetElement(1);
		boolean isAutoNol = false;
		if (isLuckOpen(message)){
			//判断幸运钥匙是否足够
			if (connection.getItem().checkItemEnough(ITEM_ID.LUCK_KRY, 1, false)){
				//钥匙足够
				connection.getItem().setItemNumber(ITEM_ID.LUCK_KRY, 1, SETNUMBER_TYPE.MINUS, 
						VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, s_sum,"",Item_Channel.OPEN_REWARD);
			}else{
				//钥匙不足，判断钱
				MT_Data_commodity mt_Data_commodity = TableManager.GetInstance().Tablecommodity().GetElement(303);
				//钱足够
				if(connection.getItem().checkItemEnough(mt_Data_commodity.Attr().field1(),mt_Data_commodity.Attr().field2()))
					connection.getItem().setItemNumber(mt_Data_commodity.Attr().field1(),mt_Data_commodity.Attr().field2(), SETNUMBER_TYPE.MINUS, 
							VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, s_sum,"",Item_Channel.OPEN_REWARD);
				else
					//钱不足,自动转为普通开启
					isAutoNol = true;
			}
		}
		
		if (playerAttribute.isFirstLuckDraw()){
			int count = 0;
			if (isLuckOpen(message) && !isAutoNol)
				count = timeRewardConfig.Reward().field2()*timeRewardConfig.times();
			else
				count = timeRewardConfig.Reward().field2();
			connection.getItem().setItemNumber(timeRewardConfig.Reward().field1(),count, SETNUMBER_TYPE.ADDITION,
					VmChannel.InGameDrop, ProductChannel.InGameDrop, s_sum,"",Item_Channel.OPEN_REWARD);
			playerAttribute.setFirstNum();
			connection.getPlayer().clearReward();
			LogService.logEvent(playerAttribute.getPlayerId(), s_sum, 14);
			
			List<UtilItem> list = new ArrayList<UtilItem>();
			list.add(new UtilItem(timeRewardConfig.Reward().field1(), count));
			OpenRewardItem(connection, list);
			return;
		}

		MT_Data_Item item = TableManager.GetInstance().TableItem().GetElement(utilItem.GetItemId());
		List<UtilItem> list = TableManager.GetInstance().getDropOut(connection.getPlayer(),item.DropOut());
		Map<Integer, Integer> itemmap = Util.getUtilItemListToMap(list);
		int count = Util.getEqiepOrDesCount(itemmap);
		if (!connection.getItem().checkEquipFragCount(count))
			return ;
		
		//如果是幸运开启而碁没有自动转为普通开启 
		if (isLuckOpen(message) && !isAutoNol){
			for (UtilItem utilItemTemp : list) 
				utilItemTemp.setM_Count(utilItemTemp.GetCount()*timeRewardConfig.times());
		}
		connection.getItem().setItemNumberArrayByUtilItem(list, 
				SETNUMBER_TYPE.ADDITION,VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, s_sum,"",Item_Channel.OPEN_REWARD);
		connection.getPlayer().clearReward();
		
		OpenRewardItem(connection, list);
		
		LogService.logEvent(playerAttribute.getPlayerId(), s_sum, 15, 0, 0);
	}

	private boolean isLuckOpen(ProReward.Msg_C2G_OpenReward message) {
		return message.getType() == ProReward.Proto_OpenType.LUCK;
	}
	
	private void OpenRewardItem(Connection connection, List<UtilItem> list)
	{
		ProReward.Msg_G2C_OpenRewardItem.Builder reads = ProReward.Msg_G2C_OpenRewardItem.newBuilder();
		for (UtilItem i : list) {
			ProReward.Msg_ItemPair.Builder ib = ProReward.Msg_ItemPair.newBuilder();
			ib.setItemid(i.GetItemId());
			ib.setCount(i.GetCount());
			reads.addItems(ib.build());
		}
		connection.sendReceiptMessage(reads.build());
	}
}
