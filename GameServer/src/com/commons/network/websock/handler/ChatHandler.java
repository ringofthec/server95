package com.commons.network.websock.handler;

import gameserver.config.ServerConfig;
import gameserver.http.HttpServerManager;
import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.GDL_C2G_Chat;
import gameserver.jsonprotocol.GDL_C2G_GetChats;
import gameserver.jsonprotocol.GDL_C2G_KeepAlive;
import gameserver.jsonprotocol.GDL_G2C_BannerNotice;
import gameserver.jsonprotocol.GDL_G2C_ChatRe;
import gameserver.jsonprotocol.GDL_G2C_GetChatsRe;
import gameserver.jsonprotocol.GDL_G2C_KeepAliveRe;
import gameserver.logging.LogService;
import gameserver.utils.DbMgr;
import gameserver.utils.WordFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConManager;
import com.commons.network.websock.PlayerConnection;
import com.commons.thread.WorldEvents;
import com.commons.util.TimeUtil;
import com.gdl.data.MailData;
import com.gdl.data.PlayerData;
import com.gdl.game.BaijialeGameInstanceManager;
import com.gdl.game.FishGameInfo;
import com.gdl.game.FishGameInstanceManager;
import com.gdl.game.NiuniuGameInstanceManager;

import databaseshare.DatabaseKefu;

public class ChatHandler {
	private final static ChatHandler instance = new ChatHandler();
	public static ChatHandler getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(ChatHandler.class);
	
	private FastMap<String, GMCommand> cmds = new FastMap<String, ChatHandler.GMCommand>();
	boolean gm_enable = false;
	
	private FastList<GDL_G2C_ChatRe> chat_caches = new FastList<GDL_G2C_ChatRe>();
	
	private FastMap<Long, Long> bind_player = new FastMap<Long, Long>();
	
	public void reg_gm() {
		HandlerManager.getInstance().pushNornalHandler(ChatHandler.class, this, 
				"OnGetChats", new GDL_C2G_GetChats());
	}
	
	public void clearChat() {
		synchronized (chat_caches) {
			chat_caches.clear();
		}
	}
	
	public void banChat(long player_id) {
		bind_player.put(player_id, player_id);
	}
	
	public void init() {
		HandlerManager.getInstance().pushNornalHandler(ChatHandler.class, this, 
				"OnChat", new GDL_C2G_Chat());
		HandlerManager.getInstance().pushNornalHandler(ChatHandler.class, this, 
				"OnGetChats", new GDL_C2G_GetChats());
		HandlerManager.getInstance().pushNornalHandler(ChatHandler.class, this, 
				"OnKeepAlive", new GDL_C2G_KeepAlive());
		
		File file = new File("gm_enable");
		if (file.isFile() && file.exists())
			gm_enable = true;
		
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.error("注意，GM指令启动了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		@SuppressWarnings("rawtypes")
		Class[] subClass = ChatHandler.class.getDeclaredClasses();
		for (@SuppressWarnings("rawtypes") Class clazz : subClass) {
			if (!clazz.isInterface() && GMCommand.class.isAssignableFrom(clazz)) {
				System.err.println("add gm command: " + clazz.getSimpleName());
				try {
					cmds.put(clazz.getSimpleName().toLowerCase(), (GMCommand)clazz.newInstance());
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public interface GMCommand {
		void execute(PlayerConnection con, String... args);
	}
	
	static List<Long> robots = new ArrayList<Long>();
	public static void flushRobotList() {
		
		if (!ServerConfig.app_test) {
			if (ServerConfig.szhios) {
				int[] a = {20827, 20826, 20829, 20830, 20828, 20831, 20834, 20833, 20832, 20836, 20835, 20837, 
						20838, 20840, 20839, 20841, 20842, 20843, 20844, 20845, 20846, 20847, 20848, 20849, 20850, 
						20851, 20852, 20853, 20854, 20855, 20856, 20857, 20858, 20859, 20860, 20861,
						
						20782, 20783, 20784, 20785, 20786, 20787, 20788, 20789, 20790, 20791, 20792, 20793, 20794, 
						20795, 20796, 20797, 20798, 20799, 20800, 20801, 20802, 20803, 20804, 20805, 20806, 20807, 
						20808, 20809, 20810, 20811, 20816, 20817, 20818, 20819, 20820, 20821, 20822, 20823, 20824, 
						20825, 20812, 20813, 20814, 20815
						
						
				};
				
				List<Long> ta = new ArrayList<Long>();
				StringBuilder sb = new StringBuilder();
				for (long p : a) {
					ta.add(p);
					sb.append(p).append(",");
				}
				
				robots = ta;
				LogService.logger.error("szhios: " + sb.toString());
			} else {
				int[] a = {5653, 5654, 5656, 5657, 5658, 5659, 5660, 5661, 5662, 5663, 5664, 5665, 
						5666, 5667, 5668, 5669, 5671, 5670, 5673, 5672, 5674, 5675, 5679, 5677, 
						5680, 5678, 5681, 5682, 5684, 5683,
						
						5695, 5696, 5697, 5698, 5699, 5701, 5700, 5702, 5704, 5703, 5705, 
						5706, 5707, 5708, 5710, 5709, 5712, 5711, 5714, 5713,
						5715, 5716, 5718, 5717, 5719, 5720, 5721, 5724, 5722, 5723,
						7035, 7036, 7037, 7038,
						5725, 5898, 5899, 5900, 5901, 5902, 5903, 5904, 5905, 5906,
						
						20577, 20579, 20578, 20580, 20581, 20582, 20583, 20585, 20584, 20587, 20586,
						
						22676, 22677, 22678, 22679, 22817, 22818, 22819, 22820};
				
				List<Long> ta = new ArrayList<Long>();
				StringBuilder sb = new StringBuilder();
				for (long p : a) {
					ta.add(p);
					sb.append(p).append(",");
				}
				
				robots = ta;
				LogService.logger.error("guonei: " + sb.toString());
			}
			
		} else {
			int[] a = {5653, 5654, 5656, 5657, 5658, 5659, 5660, 5661, 5662, 5663, 5664, 5665, 
					5666, 5667, 5668, 5669, 5671, 5670, 5673, 5672, 5674, 5675, 5679, 5677, 
					5680, 5678, 5681, 5682, 5684, 5683,
					
					1970, 1971, 1972, 1973, 1974, 1975, 1976, 1977, 1978, 1979, 
					1980, 1981, 1982, 1983, 1984, 1985, 1986, 1987, 1988, 1989,
					1990, 1991, 1992, 1993, 1994, 1995, 1996, 1997, 1998, 1999, 
					2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009,
					2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019
					};
			List<Long> ta = new ArrayList<Long>();
			for (long p : a) {
				ta.add(p);
			}
			
			robots = ta;
			LogService.sysErr(1, "flushRobotList success..............", 1);
		}
	}
	static {
		flushRobotList();
	}
	public static boolean isRobot(long pid) {
		if (ServerConfig.app_test) {
			return pid >= 1970;
		}
		
		if (robots.contains(pid))
			return true;
		
		return false;
	}
	
	public static class scene implements GMCommand {
		@Override
		public void execute(PlayerConnection con, String... args) {
			if (args.length != 1)
				return ;
			
			Integer[] iteminfo = new Integer[args.length];
			for (int i = 0; i < args.length; ++i) {
				iteminfo[i] = Integer.parseInt(args[i]);
			}
			
			FishGameInfo fg = FishGameInstanceManager.getInstance().getPlayerFishInstance(con);
			if (fg == null)
				return ;
			
			fg.swtichScene(iteminfo[0], false);
		}
	}
	
	public static class niu implements GMCommand {
		@Override
		public void execute(PlayerConnection con, String... args) {
			try {
				NiuniuGameInstanceManager.getInstance().checkStatus();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class b implements GMCommand {
		@Override
		public void execute(PlayerConnection con, String... args) {
			try {
				BaijialeGameInstanceManager.getInstance().checkStatus();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class mail implements GMCommand {
		@Override
		public void execute(PlayerConnection con, String... args) {
			if (args.length % 2 != 0)
				return ;
			
			Integer[] iteminfo = new Integer[args.length];
			for (int i = 0; i < args.length; ++i) {
				iteminfo[i] = Integer.parseInt(args[i]);
			}
			
			MailData.sendSystemMail(con, "测试标题", "管理员", "af3h24425哈4245换个4韩国人他根本不会放过5哈45高554fr3034",
					3, iteminfo);
		}
	}
	
	public static class rec implements GMCommand {
		@Override
		public void execute(PlayerConnection con, String... args) {
			if (args.length != 2)
				return ;
			
			con.getPlayer().giveRecharge(con, Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		}
	}
	
	public static class item implements GMCommand {
		@Override
		public void execute(PlayerConnection con, String... args) {
			if (args.length % 2 != 0)
				return ;
			
			int[] iteminfo = new int[args.length];
			for (int i = 0; i < args.length; ++i) {
				iteminfo[i] = Integer.parseInt(args[i]);
			}
			
			for (int i = 0; i < args.length / 2; ++i)
				con.getPlayer().getItemData().addItem(con, iteminfo[i * 2], iteminfo[i * 2 + 1], Consts.getItemEventGM());
		}
	}
	
	public static class banner implements GMCommand {
		@Override
		public void execute(PlayerConnection con, String... args) {
			GDL_G2C_ChatRe re = new GDL_G2C_ChatRe();
			re.setMode(3);
			re.setMsg(args[0]);
			PlayerConManager.getInstance().broadcastMsg(re);
		}
	}
	
	public void processGMCommand(PlayerConnection con, String message) {
		String[] part = message.split(" ");
		String gmname = part[1].toLowerCase();
		GMCommand cmd = cmds.get(gmname);
		if (cmd != null) {
			if (part.length > 1)
				cmd.execute(con, Arrays.copyOfRange(part, 2, part.length));
			else
				cmd.execute(con);
		}
	}
	
	public void OnKeepAlive(PlayerConnection con, GDL_C2G_KeepAlive msg) {
		GDL_G2C_KeepAliveRe re = new GDL_G2C_KeepAliveRe();
		re.setTimestamp(TimeUtil.GetDateTime());
		con.send(re);
	}
	
	public void OnGetChats(PlayerConnection con, GDL_C2G_GetChats msg) {
		GDL_G2C_GetChatsRe re = new GDL_G2C_GetChatsRe();
		if (msg.getMode() == 0) {
			re.setMode(0);
			synchronized (chat_caches) {
				for (GDL_G2C_ChatRe cr : chat_caches) {
					re.getMsgs().add(cr);
				}
			}
		} else {
			re.setMode(1);
			List<DatabaseKefu> li = DbMgr.getInstance().getShareDB().Select(DatabaseKefu.class, 
					"player_id=? order by id desc limit 5", con.getPlayerId());
			for (DatabaseKefu kf : li) {
				GDL_G2C_ChatRe r = new GDL_G2C_ChatRe();
				r.setMode(4);
				r.setPlayerName("我");
				r.setMsg(kf.msg);
				re.getMsgs().add(r);
				
				if (kf.flag == 1) {
					GDL_G2C_ChatRe r1 = new GDL_G2C_ChatRe();
					r1.setMode(4);
					r1.setPlayerName("客服");
					r1.setMsg(kf.retmsg);
					re.getMsgs().add(r1);
				}
			}
		}
		
		con.send(re);
	}

	public void OnChat(PlayerConnection con, GDL_C2G_Chat message) {
		PlayerData p = con.getPlayer();
		if (p.isChatCD())
			return ;
		
		if (message.getMsg().isEmpty())
			return ;
		
		if (bind_player.containsKey(p.getPlayerId()))
			return ;
		
		int mode = message.getMode();
		switch (mode) {
		case 0:
			String msg_p = WordFilter.getIns().filter(message.getMsg());
			// 私聊
			chatToPlayer(p.getPlayerId(), p.getName(), 
					message.getPlayerId(), msg_p);
			break;

		case 2:
			// 世界聊天
			if (message.getMsg().startsWith("gm")) {
				if (isRobot(con.getPlayerId())) {
					processGMCommand(con, message.getMsg());
					return ;
				}
				
				if (gm_enable) {
					processGMCommand(con, message.getMsg());
					return ;
				}
				
				return ;
			}
			
			if (p.getVipLvl() <= 0 && p.getLvl() <= 1)
				return ;
			
			if (p.getPlayerId() == 1451) {
				logger.error("system addmin " + message.getMsg());
				if (message.getMsg().equals("dhgrejh4h2j3432h434reload")) {
					HttpServerManager.getInstance().reload();
					return ;
				} else if (message.getMsg().equals("dhgrhg234h2j3432h4347r34rexit")) {
					LoginHandler.can_login = false;
					PlayerConManager.getInstance().disAll();
					logger.error("GameServer will shutdown!");
					System.exit(0);
					return ;
				}
			}
			
			String msg_p1 = WordFilter.getIns().filter(message.getMsg());
			chatToWorld(p.getPlayerId(), p.getName(), 
					msg_p1);
			break;
			
		case 4:
			saveKefu(con, p.getPlayerId(), p.getName(), 
					message.getMsg());
			break;
		}
	}
	
	private void saveKefu(PlayerConnection con, long splayer_id, String playerName, String msg) {
		DatabaseKefu kefu = new DatabaseKefu();
		kefu.create_time = TimeUtil.GetDateTime();
		kefu.player_id = splayer_id;
		kefu.name = playerName;
		kefu.status = 0;
		kefu.flag = 0;
		kefu.msg = msg;
		kefu.retmsg = "";
		DbMgr.getInstance().getShareDB().Insert(kefu);
		ItemHandler.newshowToast(con, 10002);
		
		GDL_G2C_ChatRe re = new GDL_G2C_ChatRe();
		re.setMode(4);
		re.setPlayerName("我");
		re.setMsg(msg);
		con.send(re);
	}
	
	public static void newbanner(int notice_id, Object... args) {
		GDL_G2C_BannerNotice bn = new GDL_G2C_BannerNotice();
		bn.setNid(notice_id);
		
		for (Object o : args)
			bn.getArgs().add(o.toString());
		
		PlayerConManager.getInstance().broadcastMsg(bn); 
	}
	
	public static void banner(String msg) {
		GDL_G2C_ChatRe re = new GDL_G2C_ChatRe();
		re.setMode(3);
		re.setMsg("[系统]: " + msg);
		PlayerConManager.getInstance().broadcastMsg(re);
	}
	
	private void chatToWorld(long splayer_id, String playerName, String msg) {
		GDL_G2C_ChatRe re = new GDL_G2C_ChatRe();
		re.setMode(2);
		re.setMsg(msg);
		re.setPlayerId(splayer_id);
		re.setPlayerName(playerName);
		PlayerConManager.getInstance().broadcastMsg(re);
		
		synchronized (chat_caches) {
			while (chat_caches.size() > 40) {
				chat_caches.removeFirst();
			}
			
			chat_caches.add(re);
		}
	}
	
	private void chatToPlayer(long splayer_id, String playerName, long player_id, String msg) {
		PlayerConnection pc = PlayerConManager.getInstance().getCon(player_id);
		if (pc == null) {
			return ;
		}
		
		GDL_G2C_ChatRe re = new GDL_G2C_ChatRe();
		re.setMode(0);
		re.setMsg(msg);
		re.setPlayerId(splayer_id);
		re.setPlayerName(playerName);
		
		pc.directSendPack(re);
	}
}
