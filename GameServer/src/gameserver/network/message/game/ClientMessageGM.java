package gameserver.network.message.game;

import gameserver.connection.attribute.ConAccountAttr;
import gameserver.connection.attribute.info.HeroInfo;
import gameserver.http.HttpProcessManager;
import gameserver.ipo.IPOManagerDb;
import gameserver.network.IOServer;
import gameserver.network.protos.game.ProActive.Msg_S2G_Exit;
import gameserver.network.protos.game.ProChat.Msg_G2C_PublicBan;
import gameserver.network.protos.game.ProDebug;
import gameserver.network.protos.game.ProDebug.Msg_G2C_GetDebugFile;
import gameserver.network.protos.game.ProGameInfo.Msg_C2G_Recharge;
import gameserver.network.protos.game.ProLogin;
import gameserver.network.protos.game.ProLogin.Msg_C2G_GMCommand;
import gameserver.network.protos.game.ProReward;
import gameserver.network.protos.game.ProReward.Msg_C2G_Reward;
import gameserver.network.protos.game.ProReward.Proto_RewardType;
import gameserver.network.server.connection.Connection;
import gameserver.service.ServerManagerService;
import gameserver.utils.DbMgr;
import gameserver.utils.Item_Channel;
import gameserver.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_Recharge1;
import table.MT_TableManager;
import table.base.TableManager;

import com.commons.util.DatabaseInsertUpdateResult;
import com.commons.util.FileUtil;
import com.commons.util.TimeUtil;
import commonality.Common.SETNUMBER_TYPE;
import commonality.ProductChannel;
import commonality.VmChannel;

import database.CustomMailItemInfo;
import database.DatabaseMail;
import databaseshare.DatabaseAccount;


public class ClientMessageGM {
	private final static Logger logger = LoggerFactory.getLogger(ClientMessageGM.class);
	interface CommandProcess
	{
		void Handle(Connection connection, String[] args);
	}
	//添加物品
	public static class Mail implements CommandProcess{
		@Override
		public void Handle(Connection connection, String[] args) {
			DatabaseMail mail =  new DatabaseMail();
			mail.is_recv = 0;
			mail.is_read = 0;
			mail.is_delete = 0;
			mail.mail_sender = "ToyDog Team";
			mail.mail_comment = "This is mail form System";
			mail.mail_title = "GM mail";
			mail.expire_time = TimeUtil.GetDateTime() + 24*3600* 1000*15;
			mail.player_id = connection.getPlayerId();
			mail.create_time = TimeUtil.GetDateTime();
			List<CustomMailItemInfo> itemInfos =  new ArrayList<>();
			CustomMailItemInfo info =  new CustomMailItemInfo();
			info.item_id = Integer.parseInt(args[0]);
			info.count = Integer.parseInt(args[1]);
			itemInfos.add(info);
			CustomMailItemInfo info2 =  new CustomMailItemInfo();
			info2.item_id = Integer.parseInt(args[2]);
			info2.count = Integer.parseInt(args[3]);
			itemInfos.add(info2);
			mail.item_info = itemInfos;
			DatabaseInsertUpdateResult result =DbMgr.getInstance().getDbByPlayerId(connection.getPlayerId()).Insert(mail);
			mail.mail_id = result.identity;
			
			connection.getMails().addMail(mail,true);
		}
	}
	public static class CA implements CommandProcess
	{
		@Override
		public void Handle(Connection connection, String[] args) {
			ConAccountAttr ac = connection.getAccount();
			ac.gm_change_account();
		}
	}
	public static class GetDebug implements CommandProcess
	{
		@Override
		public void Handle(Connection connection, String[] args) {
			ProDebug.Msg_G2C_GetDebugFile.Builder ii = Msg_G2C_GetDebugFile.newBuilder();
			connection.sendReceiptMessage(ii.build());
		}
	}
	public static class sendban implements CommandProcess {
		@Override
		public void Handle(Connection connection, String[] args) {
			Msg_G2C_PublicBan msg = Msg_G2C_PublicBan.newBuilder().setText(args[0]).build();
			HttpProcessManager.getInstance().sendMessageToAllPlayerSync(msg);
		}
	}
	//添加金钱
	public static class AddMoney implements CommandProcess
	{
		@Override
		public void Handle(Connection connection, String[] args) {
			logger.info("添加金钱+"+args[0]);
		}
	}
	public static class Flush implements CommandProcess {
		@Override
		public void Handle(Connection connection, String[] args) {
			connection.getPlayer().onNewDay(TimeUtil.GetDateTime(), false);
		}
	}
	
	public static class feat implements CommandProcess {
		@Override
		public void Handle(Connection connection, String[] args) {
			int oldfeat = connection.getPlayer().getFeat();
			int addFeat = Integer.parseInt(args[0]);
			
			if (addFeat > 0) {
				connection.getPlayer().incFeat(addFeat);
			} else {
				if (Math.abs(addFeat) > oldfeat)
					connection.getPlayer().decFeat(oldfeat);
				else
					connection.getPlayer().decFeat(Math.abs(addFeat));
			}
		}
	}
	
	//添加物品
	public static class AddFushNum implements CommandProcess{
		@Override
		public void Handle(Connection connection, String[] args) {
			connection.getPlayer().updateFushNum(0);
		}
	}
	
	public static class starup implements CommandProcess{
		@Override
		public void Handle(Connection connection, String[] args) {
			int table_id = Integer.parseInt(args[0]);
			HeroInfo hi = connection.getHero().getHeroByTableId(table_id);
			if (hi != null) {
				hi.starUp();
			}
		}
	}
	
	//添加金钱
	public static class ip implements CommandProcess
	{
		@Override
		public void Handle(Connection connection, String[] args) {
			System.err.print(Util.ipToCountry(args[0]));
		}
	}
	//停服
	public static class Stop implements CommandProcess
	{
		@Override
		public void Handle(Connection connection, String[] args) {
			Msg_S2G_Exit.Builder msg = Msg_S2G_Exit.newBuilder();
			msg.setSecondDelay(180);
			try {
				ServerManagerService.getInstance().onGameExit(msg.build());
			} catch (Exception e) {
				logger.error("Stop GM:", e);
			}
		}
	}
	//停服
	public static class Exit implements CommandProcess
	{
		@Override
		public void Handle(Connection connection, String[] args) {
			System.exit(0);
		}
	}
	//添加物品
	public static class AddItem implements CommandProcess{
		@Override
		public void Handle(Connection connection, String[] args) {
			int item_table_id = Integer.parseInt(args[0]);
			int item_number = Integer.parseInt(args[1]);
			logger.info("添加物品 Id:"+item_table_id+" num:"+item_number);
			connection.getItem().setItemNumber(item_table_id, item_number, SETNUMBER_TYPE.ADDITION,
					VmChannel.ManmalGive, ProductChannel.ManmalGive, IPOManagerDb.getInstance().getNextId(), "",Item_Channel.GM);
			List<Integer> updateItems = new ArrayList<Integer>();
			updateItems.add(item_table_id);
		}
	}
	//删除物品
	public static class DelItem implements CommandProcess
	{
		@Override
		public void Handle(Connection connection, String[] args) {
			int item_table_id = Integer.parseInt(args[0]);
			int item_number = Integer.parseInt(args[1]);
			logger.info("删除物品 Id:"+item_table_id+" num:"+item_number);
			connection.getItem().setItemNumber(item_table_id, item_number, SETNUMBER_TYPE.MINUS,
					VmChannel.ManmalGive, ProductChannel.ManmalGive, IPOManagerDb.getInstance().getNextId(), "",Item_Channel.GM);
			List<Integer> updateItems = new ArrayList<Integer>();
			updateItems.add(item_table_id);
		}
	}
	public static class AddLand implements CommandProcess
	{
		@Override
		public void Handle(Connection connection, String[] args) {
			logger.info("开垦土地+"+args[0]);
			connection.getPlayer().extendLand(Integer.parseInt(args[0]));
		}
	}
	
	public static class reward implements CommandProcess {
		@Override
		public void Handle(Connection connection, String[] args) {
			ProReward.Msg_C2G_Reward msg = Msg_C2G_Reward.newBuilder().setType(Proto_RewardType.FUND_REWARD).setTableId(0).build();
			try {
				ClientMessageReward.getInstance().OnFundReward(connection, msg);
			} catch (Exception e) {
			}
		}
	}
	
	public static class Recharge implements CommandProcess
	{
		@Override
		public void Handle(Connection connection, String[] args) {
			try {
			logger.info("充值+"+args[0]);
			Msg_C2G_Recharge.Builder build = Msg_C2G_Recharge.newBuilder();
			build.setItemid(100 + "_bullion");
			build.setClientinfo(3);
			build.setOrders("wsh");
			build.setOriginalJson("wsh");
			build.setSignature("wsh");
			
			MT_Data_Recharge1 data = TableManager.GetInstance().getSpawns(MT_TableManager.Recharge1.Recharge1_GooglePlay).GetElement(100 + "_bullion");
			if (data == null)
				return ;
			
			if (args[0].equals("0"))
				ClientMessageRecharge.getInstance().realPay(connection, "testorder", "fund", 1, data, false, false);
			else
				ClientMessageRecharge.getInstance().realPay(connection, "testorder", "100_bullion", 1, data, true, false);
			} catch (Exception e) {
			}
		}
	}
	
	public static class BuyQueue implements CommandProcess
	{
		@Override
		public void Handle(Connection connection, String[] args) {
			logger.info("次方法已经停用+"+args[0]);
			//connection.getPlayerAttribute().findMoreLand(Integer.parseInt(args[0]));
//			connection.getPlayerAttribute().buyQueue(Integer.parseInt(args[0]));
//			ClientMessageBuild.getInstance().UpdatePlayerInfo(connection);
		}
	}
	
	public static class AddFeat implements CommandProcess
	{

		@Override
		public void Handle(Connection connection, String[] args) {
			// TODO Auto-generated method stub
			connection.getPlayer().setPlayerFeat(Integer.parseInt(args[0]), SETNUMBER_TYPE.ADDITION);
		}
		
	}
//	public static class SendChat implements CommandProcess
//	{
//		@Override
//		public void Handle(Connection connection, String[] args) {
//			if (args.length < 2) return;
//			int type = Integer.parseInt(args[0]);
//			String text = args[1];
//			Msg_S_ChatMessage.Builder builder = Msg_S_ChatMessage.newBuilder();
//			builder.setType(Proto_Enum_ChatType.valueOf(type));
//			builder.setPlayerId(connection.getPlayerId());
//			builder.setPlayerName(connection.getPlayerName());
//			builder.setTime(TimeUtil.GetDateTime());
//			builder.setFeat(connection.getItemAttribute() connection.getPlayerAttribute().getPlayer().feat);
//			builder.setText(text);
//			HttpProcessManager.getInstance().sendMessageToAllPlayer(builder.build());
//		}
//	}
	static {
		if (FileUtil.FileExist("./gm_enable")) {
			Class<?>[] commonds = ClientMessageGM.class.getDeclaredClasses();
			for (Class<?> clazz : commonds)
			{
				if (!clazz.isInterface() && CommandProcess.class.isAssignableFrom(clazz))
				{
					try
					{
						AddCommand((CommandProcess)clazz.newInstance());
					}
					catch (Exception e) 
					{
						logger.error("ClientMessageGM static is error : ", e);
					}
				}
			}
		}
	}
	private final static ClientMessageGM instance = new ClientMessageGM();
	public static ClientMessageGM getInstance() { return instance; }
	
	private static Map<String, CommandProcess> commandProcesses;
	private static void AddCommand(CommandProcess process)
	{
		if (commandProcesses == null) commandProcesses = new HashMap<String, CommandProcess>();
		if (process == null) return;
		String name = process.getClass().getSimpleName();
		logger.info("增加GM命令 [{}]",name);
		commandProcesses.put(name.toLowerCase(),process);
	}
	public static void ExecuteCommand(Connection connection,String command,String[] args)
	{
		if (commandProcesses == null) return;
		if (commandProcesses.containsKey(command.toLowerCase()))
			commandProcesses.get(command.toLowerCase()).Handle(connection, args);
		else
			logger.error("无效的GM指令 " + command);
	}
	public void initialize()
    {
    	IOServer.getInstance().regMsgProcess(ProLogin.Msg_C2G_GMCommand.class, this, "OnGMCommand");
    }
	public void OnGMCommand(Connection connection,Msg_C2G_GMCommand message)
	{
//		if (connection.getAccountAttribute().getAccountType() != ACCOUNT_TYPE.GM)
//		{
//			logger.error("cannot us gm command  access ");
//			return;
//		}
		String command = message.getCommand();
		command = command.replace("/gm", "");
		command = command.trim();
		String[] strs = command.split(";");
		for (String str : strs)
		{
			int index = str.indexOf(" ");
			if (index > 0)
			{
				String c = str.substring(0,index);
				String a = str.substring(index).trim();
				ExecuteCommand(connection, c, a.split(" "));
			}
			else
			{
				ExecuteCommand(connection, str, new String[]{});
			}
		}
	}
}
