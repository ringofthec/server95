package gameserver.http.handler;

import gameserver.config.GameConfig;
import gameserver.event.listener.NewHourListener;
import gameserver.jsonprotocol.Consts;
import gameserver.utils.DbMgr;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javolution.util.FastMap;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_GItem;
import table.base.TableManager;

import com.commons.http.MyHttpHandler;
import com.commons.network.websock.HandlerManager;
import com.commons.network.websock.PlayerConManager;
import com.commons.network.websock.PlayerConnection;
import com.commons.network.websock.handler.ChatHandler;
import com.commons.util.CharsetUtil;
import com.commons.util.DatabaseUtil;
import com.commons.util.HttpUtil;
import com.commons.util.HttpUtil.HTTP_METHOD;
import com.commons.util.RandomUtil;
import com.commons.util.TimeUtil;
import com.gdl.game.FishGameConfig;
import com.gdl.game.FishGameInfo;
import com.gdl.manager.GamePoolManager;
import com.gdl.manager.RankManager;
import com.gdl.manager.ShopManager;
import com.sun.net.httpserver.HttpExchange;

import commonality.Common;
import database.DatabaseItem;
import database.gdl.gameserver.DatabasePlayer;
import databaseshare.DatabaseAccount;
import databaseshare.DatabaseKefu;
import databaseshare.DatabasePlayer_brief_info;

public class FuncHttpHandler extends MyHttpHandler {
	private final static Logger logger = LoggerFactory.getLogger(FuncHttpHandler.class);
	@Override
	public void handle(HttpExchange httpExchange) {
		try
		{
			String method = httpExchange.getRequestMethod();
			String uri = httpExchange.getRequestURI().toString();
			InetSocketAddress address = httpExchange.getRemoteAddress();
			logger.info("接收到http请求[{}]  uri : '{}'  method : '{}'",address,uri,method);
			if (method.toUpperCase().contains(HTTP_METHOD.POST.getName()))
			{
				InputStream stream = httpExchange.getRequestBody();
				String postData = IOUtils.toString(stream,CharsetUtil.defaultCharset);
				logger.info("postdata : '{}'",postData);
				String[] allargs = postData.split(":");
				if (allargs[0].equals("tablecheck")) {
					TableManager.GetInstance().checkTableTime();
					FishGameConfig.getInstance().checkTableTime();
					ChatHandler.flushRobotList();
					String response = "Success";
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				} else if (allargs[0].equals("flushshop")) {
					ShopManager.getInstance().refresh();
					String response = "Success flush";
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				} else if (allargs[0].equals("clearchat")) {
					ChatHandler.getInstance().clearChat();
					String response = "Success flush";
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				}  else if (allargs[0].equals("banchat")) {
					String[] itt = postData.split(":");
					Long player_id = Long.parseLong(itt[1]);
					ChatHandler.getInstance().banChat(player_id);
					String response = "Success flush";
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				}   
				else if (allargs[0].equals("logsr")) {
					HandlerManager.debug_recv = false;
					HandlerManager.debug_send = false;
					String response = "Success not send and recv log";
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				} 
				else if (allargs[0].equals("logsropen")) {
					HandlerManager.debug_recv = true;
					HandlerManager.debug_send = true;
					String response = "Success send and recv log";
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				} else if (allargs[0].equals("checkcmask")) {
					String[] itt = postData.split(":");
					Long player_id = Long.parseLong(itt[1]);
					Long cmask = Long.parseLong(itt[2]);
					
					int count = DbMgr.getInstance().getShareDB().Count(DatabaseAccount.class, "player_id=? and cmask=?",
							player_id, cmask);
					
					String response = "验证码正确，可以切换账号";
					if (count == 0) {
						response = "验证码不正确，请玩家重新提供";
					}
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				} else if (allargs[0].equals("achange")) {
					String[] itt = postData.split(":");
					Long player_id1 = Long.parseLong(itt[1]);
					Long player_id2 = Long.parseLong(itt[2]);
					
					DatabaseAccount a1 = DbMgr.getInstance().getShareDB().SelectOne(DatabaseAccount.class, "player_id=?",
							player_id1);
					if (a1 == null) {
						HttpUtil.sendResponse(httpExchange, "没有id为 "  + player_id1 + " 的玩家！");
						return;
					}
					
					DatabaseAccount a2 = DbMgr.getInstance().getShareDB().SelectOne(DatabaseAccount.class, "player_id=?",
							player_id2);
					if (a2 == null) {
						HttpUtil.sendResponse(httpExchange, "没有id为 "  + player_id2 + " 的玩家！");
						return;
					}
					
					DbMgr.getInstance().getShareDB().Execute("update account set temp_uid=? where player_id=?"
							, a1.temp_uid, player_id2);
					DbMgr.getInstance().getShareDB().Execute("update account set temp_uid=? where player_id=?"
							, a2.temp_uid, player_id1);
					HttpUtil.sendResponse(httpExchange, "账号切换成功，玩家重新登录即可");
					return ;
				} else if (allargs[0].equals("pool_cd")) {
					GamePoolManager.getInstance().initNoticeCd();
					String response = "Success ";
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				} else if (allargs[0].equals("34j3rsdf")) {
					NewHourListener.regi();
					String response = "Success ";
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				} else if (allargs[0].equals("pool_ban")) {
					GameConfig.fish_ban_pool_and_instance = true;
					String response = "Success " + GameConfig.fish_ban_pool_and_instance;
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				} else if (allargs[0].equals("unban")) {
					String[] itt = postData.split(":");
					Long player_id = Long.parseLong(itt[1]);
					
					DatabaseAccount bac = DbMgr.getInstance().getShareDB().SelectOne(DatabaseAccount.class, 
							"player_id=?", player_id);
					if (bac == null) {
						String response = "player_id为 " + player_id + " 的玩家并不存在";
						HttpUtil.sendResponse(httpExchange, response);
						return ;
					}
					
					bac.banned = 0;
					bac.banned_time = TimeUtil.GetDateTime() - 30 * 1000;
					bac.save();
					
					String response = "player_id为 " + player_id + " 的玩家可以登录啦";
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				} else if (postData.startsWith("specfisher")) {
					String[] itt = postData.split(":");
					Long player_id = Long.parseLong(itt[1]);
					FishGameInfo.register_spec_fisher(player_id);
					FishGameInfo.init_register();
					
					String response = "player_id为 " + player_id + " 的玩家列入了捕鱼特别对待中";
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				} else if (allargs[0].equals("updateheader")) {
					List<DatabasePlayer_brief_info> pbilist = DbMgr.getInstance().getShareDB()
							.Select(DatabasePlayer_brief_info.class, "head_url=?", "");
					for (DatabasePlayer_brief_info b : pbilist) {
						b.head_url = "TX" + RandomUtil.RangeRandom(1, 10) + ".png";
						b.save();
					}
					String response = "操作成功";
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				} else if (allargs[0].equals("banner")) {
					String[] itt = postData.split(":");
					ChatHandler.newbanner(2, itt[1]);
					String response = "发送成功";
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				} else if (allargs[0].equals("ban")) {
					String[] itt = postData.split(":");
					Long player_id = Long.parseLong(itt[1]);
					PlayerConnection con = PlayerConManager.getInstance().getCon(player_id);
					if (con != null) {
						con.getPlayer().ban();
						con.close();
					} else {
						DatabaseAccount bac = DbMgr.getInstance().getShareDB().SelectOne(DatabaseAccount.class, 
								"player_id=?", player_id);
						if (bac == null) {
							String response = "player_id为 " + player_id + " 的玩家并不存在";
							HttpUtil.sendResponse(httpExchange, response);
							return ;
						}
						
						bac.banned = 1;
						bac.banned_time = TimeUtil.GetDateTime() + Common.MONTH_MILLISECOND * 6;
						bac.save();
					}
					
					String response = "player_id为 " + player_id + " 的玩家被封禁登录啦";
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				}else if (allargs[0].equals("rank")) {
					RankManager.getInstance().reloadRank();
					String response = "Success";
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				} else if (allargs[0].equals("online")) {
					int num = PlayerConManager.getInstance().getPlayerNum();
					String response = "当前在线人数 " + num;
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				} else if (allargs[0].equals("kefu")) {
					String[] itt = postData.split(":");
					long kefu_id = Long.parseLong(itt[1]);
					long player_id = Long.parseLong(itt[2]);
					String kefumsg = itt[3];
					
					DatabaseKefu ku = DbMgr.getInstance().getShareDB().SelectOne(DatabaseKefu.class, 
							"kefu_id=? and player_id=?", kefu_id, player_id);
					if (ku == null) {
						String response = "回复失败";
						HttpUtil.sendResponse(httpExchange, response);
						return ;
					}
					
					ku.flag = 1;
					ku.retmsg = kefumsg;
					ku.save();
					
					String response = "回复成功";
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				}else if (allargs[0].equals("sall")) {
					PlayerConManager.getInstance().saveAll();
					String response = "保存成功";
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				} else if (allargs[0].equals("save")) {
					String[] itt = postData.split(":");
					long player_id = Long.parseLong(itt[1]);
					PlayerConnection pc = PlayerConManager.getInstance().getCon(player_id);
					if (pc != null) {
						pc.getPlayer().save();
					}
					String response = "保存成功";
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				} else if (allargs[0].equals("give")) {
					String[] itt = postData.split(":");
					if (itt.length % 2 != 0) {
						String response = "操作失败，请检查格式";
						HttpUtil.sendResponse(httpExchange, response);
						return ;
					}
					
					long player_id = Long.parseLong(itt[1]);
					int[] iteminfo = new int[itt.length - 2];
					
					for (int i = 2; i < itt.length; ++i) {
						iteminfo[i - 2] = Integer.parseInt(itt[i]);
					}
					
					boolean has_vip_exp = false;
					int item_num = iteminfo.length / 2;
					for (int i = 0; i < item_num; ++i) {
						MT_Data_GItem iconif = TableManager.GetInstance().TableGItem().GetElement(iteminfo[i*2]);
						if (iconif == null) {
							String response = "物品id " + iteminfo[i*2] + " 不存在";
							HttpUtil.sendResponse(httpExchange, response);
							return ;
						}
						
						if (iteminfo[i*2] == Consts.getVIP_EXP_ID()) {
							has_vip_exp = true;
							break;
						}
					}
					
					PlayerConnection p = PlayerConManager.getInstance().getCon(player_id);
					if (p != null) {
						for (int i = 0; i < item_num; ++i) {
							// 如果玩家现在的情况下，加vipexp和加vip等级不能同时进行，不然会导致加两次
							if (has_vip_exp && iteminfo[i*2] == Consts.getVIP_LVL_ID())
								continue;
							
							p.getPlayer().getItemData().addItem(p, iteminfo[i*2], iteminfo[i*2 + 1], 100);
							logger.error("{} {} {}", iteminfo[i*2], iteminfo[i*2 + 1], player_id);
						}
					} else {
						DatabaseAccount ac = DbMgr.getInstance().getShareDB().SelectOne(DatabaseAccount.class, "player_id=?", player_id);
						if (ac != null) {
							DatabaseUtil db = DbMgr.getInstance().getDb(ac.getDb_id());
							for (int i = 0; i < item_num; ++i) {
								db.Execute("update item set number=number+? where player_id=? and table_id=?",
										iteminfo[i*2 + 1], player_id, iteminfo[i*2]);
								logger.error("{} {} {}", iteminfo[i*2 + 1], player_id, iteminfo[i*2]);
							}
						}
						else {
							String response = "玩家不存在";
							HttpUtil.sendResponse(httpExchange, response);
							return ;
						}
					}
					
					String response = "操作成功";
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				} else if (allargs[0].equals("policy")) {
					String[] itt = postData.split(":");
					if (itt.length != 5) {
						String response = "操作失败，请检查格式";
						HttpUtil.sendResponse(httpExchange, response);
						return ;
					}
					
					long player_id = Long.parseLong(itt[1]);
					long eran = Long.parseLong(itt[2]);
					int policy_id = Integer.parseInt(itt[3]);
					int policy_step = Integer.parseInt(itt[4]);
					
					PlayerConnection p = PlayerConManager.getInstance().getCon(player_id);
					if (p != null) {
						if (eran == -1) {
							eran = p.getPlayer().getItemCountByTempId(Consts.getCOIN_ID());
						}
						
						p.getPlayer().getItemData().setItem(p, Consts.getGame_Cost_Count(), 0, -1);
						p.getPlayer().getItemData().setItem(p, Consts.getGame_Earn_Count(), eran, -1);
						p.getPlayer().getItemData().setItem(p, 112, policy_id, -1);
						p.getPlayer().getItemData().setItem(p, 113, policy_step, -1);
					} else {
						DatabaseAccount ac = DbMgr.getInstance().getShareDB().SelectOne(DatabaseAccount.class, "player_id=?", player_id);
						if (ac != null) {
							DatabaseUtil db = DbMgr.getInstance().getDb(ac.getDb_id());
							if (eran == -1) {
								DatabaseItem it = db.SelectOne(DatabaseItem.class, "player_id=? and table_id=1", player_id);
								eran = it.number;
							}
							
							db.Execute("update item set number=0 where player_id=? and table_id=101", player_id);
							db.Execute("update item set number=? where player_id=? and table_id=102", eran, player_id);
							db.Execute("update item set number=? where player_id=? and table_id=112", policy_id, player_id);
							db.Execute("update item set number=? where player_id=? and table_id=113", policy_step, player_id);
						}
						else {
							String response = "玩家不存在";
							HttpUtil.sendResponse(httpExchange, response);
							return ;
						}
					}
					
					String response = "玩家 [" + player_id + "]  净赢设置为: " + eran;
					HttpUtil.sendResponse(httpExchange, response);
					return ;
				}else if (allargs[0].equals("queryeran")) {
					String[] itt = postData.split(":");
					if (itt.length != 2) {
						String response = "操作失败，请检查格式";
						HttpUtil.sendResponse(httpExchange, response);
						return ;
					}
					
					long player_id = Long.parseLong(itt[1]);
					
					PlayerConnection p = PlayerConManager.getInstance().getCon(player_id);
					if (p != null) {
						long eran_c = p.getPlayer().getItemData().getEran();
						long coin = p.getPlayer().getCoin();
						long gold = p.getPlayer().getGold();
						long level = p.getPlayer().getLvl();
						long viplvl = p.getPlayer().getVipLvl();
						long vipexp = p.getPlayer().getVipExp();
						long jiang = p.getPlayer().getItemCountByTempId(10);
						long pigcoin = p.getPlayer().getItemCountByTempId(11);
						String cre_t = p.getPlayer().getCreateTime();
						String response = "<br>玩家 [ " + player_id + " ]  <br>净赢: " + eran_c + 
								"<br>昵称: " + p.getPlayer().getName() +
								"<br>金币数: " + coin +
								"<br>银行金币数: " + pigcoin +
								"<br>金砖数: " + gold +
								"<br>奖券数: " + jiang +
								"<br>段位: " + level +
								"<br>vip等级: " + viplvl +
								"<br>充值金额: " + vipexp +
								"<br>创建时间: " + cre_t +
								"<br>玩家正在游戏";
								;
						HttpUtil.sendResponse(httpExchange, response);
						return ;
					} else {
						DatabaseAccount ac = DbMgr.getInstance().getShareDB().SelectOne(DatabaseAccount.class, "player_id=?", player_id);
						if (ac != null) {
							DatabaseUtil db = DbMgr.getInstance().getDb(ac.getDb_id());
							List<DatabaseItem> its = db.Select(DatabaseItem.class, 
									"player_id=? and table_id in (101, 102, 1, 2, 3, 4, 6, 10, 11)", player_id);
							Map<Integer, DatabaseItem> t_item_map = new FastMap<Integer, DatabaseItem>();
							for (DatabaseItem tttt : its) {
								t_item_map.put(tttt.table_id, tttt);
							}
							
							long game_win = 0;
							if (t_item_map.containsKey(102))
								game_win = t_item_map.get(102).number;
							long game_cost = 0;
							if (t_item_map.containsKey(101))
								game_cost = t_item_map.get(101).number;
							long coin = 0;
							if (t_item_map.containsKey(1))
								coin = t_item_map.get(1).number;
							long gold = 0;
							if (t_item_map.containsKey(2))
								gold = t_item_map.get(2).number;
							long level = 0;
							if (t_item_map.containsKey(6))
								level = t_item_map.get(6).number;
							long viplvl = 0;
							if (t_item_map.containsKey(3))
								viplvl = t_item_map.get(3).number;
							long vipexp = 0;
							if (t_item_map.containsKey(4))
								vipexp = t_item_map.get(4).number;
							long jiang = 0;
							if (t_item_map.containsKey(10))
								jiang = t_item_map.get(10).number;
							long pigcoin = 0;
							if (t_item_map.containsKey(11))
								pigcoin = t_item_map.get(11).number;
							
							DatabasePlayer dbp = db.selectFieldFrist(DatabasePlayer.class, "player_id=?", 
									"create_time,last_flush_time,last_offline_time,name", player_id);
							
							DatabaseAccount dba = DbMgr.getInstance().getShareDB().selectFieldFrist(DatabaseAccount.class, "player_id=?", 
									"create_time", player_id);
							
							String response = "<br>玩家 [ " + player_id + " ]  <br>净赢: " + (game_win - game_cost) + 
									"<br>昵称: " + dbp.name +
									"<br>金币数: " + coin +
									"<br>银行金币数: " + pigcoin +
									"<br>金砖数: " + gold +
									"<br>奖券数: " + jiang +
									"<br>段位: " + level +
									"<br>vip等级: " + viplvl +
									"<br>充值金额: " + vipexp +
									"<br>创建时间: " + getCreateTime(dba.create_time) +
									"<br>上次下线时间: " + getCreateTime(dbp.last_offline_time);
									;
							HttpUtil.sendResponse(httpExchange, response);
							return ;
						}
						else {
							String response = "玩家不存在";
							HttpUtil.sendResponse(httpExchange, response);
							return ;
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			logger.error("FuncHttpHandler handle is error : ",e);
		}
		String response = "操作失败";
		HttpUtil.sendResponse(httpExchange, response);
	}
	
	public String getCreateTime(long t) {
		Timestamp timestamp = TimeUtil.GetTimestamp(t);
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateformat.format(timestamp);
	}
	
	@Override
	public String getPattern() {
		return "/func";
	}
}
