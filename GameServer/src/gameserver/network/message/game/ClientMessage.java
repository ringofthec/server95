package gameserver.network.message.game;

import gameserver.active.ActiveService;
import gameserver.ranking.ClientMessageRanking;
import gameserver.service.ServerManagerService;

public class ClientMessage {
	private final static ClientMessage instance = new ClientMessage();
	public static ClientMessage getInstance() { return instance; }
	public void initialize()
	{
		// 账号登陆相关的协议
		ClientMessageAuth.getInstance().initialize();
		
		// 建造
		ClientMessageBuild.getInstance().initialize();
		
		// 造兵
		ClientMessageCorp.getInstance().initialize();
		
		// 战斗
		ClientMessageFight.getInstance().initialize();
		
		// 空中支援
		ClientMessageAirSupport.getInstance().initialize();
		
		// pve
		ClientMessagePve.getInstance().initialize();
		
		// pvp
		ClientMessagePvp.getInstance().initialize();
		
		// 英雄
		ClientMessageHero.getInstance().initialize();
		
		// 物品
		ClientMessageItem.getInstance().initialize();
		
		// 奖励
		ClientMessageReward.getInstance().initialize();
		
		// gm指令
		ClientMessageGM.getInstance().initialize();
		
		// 军团
		ClientMessageLegion.getInstance().initialize();
		
		// 聊天
		ClientMessageChat.getInstance().initialize();
		
		// 商品
		ClientMessageCommodity.getInstance().initialize();
		
		// 任务
		ClientMessageTask.getInstance().initialize();
		
		// 排行榜
		ClientMessageRanking.getInstance().initialize();
		
		// 充值
		ClientMessageRecharge.getInstance().initialize();
		
		// vip
		ClientMessageVip.getInstance().initialize();
		
		// 英雄
		ClientMessageHeroPath.getInstance().initialize();
		
		// 其他
		ClientMessageCommon.getInstance().initialize();
		
		ClientMessageMail.getInstance().initialize();
		
		ClientMessageHelp.getInstance().initialize();
		
		ClientMessageChat.getInstance().init();
		
		ClientMessageSee.getInstance().initialize();
		
		ClientMessageActive.getInstance().initialize();
		
		//悬赏关卡
		ClientMessageWanted.getInstance().initialize();
		
		ActiveService.getInstance().init();
		
		ServerManagerService.getInstance().init();
	}
}
