package com.gdl.manager;

import gameserver.jsonprotocol.GDL_G2C_GetRankRe;
import gameserver.jsonprotocol.RankPlayerInfo;
import gameserver.utils.DbMgr;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.commons.util.TimeUtil;
import com.gdl.data.PlayerBriefInfo;

import javolution.util.FastMap;
import databaseshare.DatabaseLevel_rank;
import databaseshare.DatabaseLike_rank;
import databaseshare.DatabaseMoney_rank;
import databaseshare.DatabasePlayer_brief_info;

public class RankManager {
	private static RankManager m_int = new RankManager();
	private RankManager() {}
	public static RankManager getInstance() {return m_int;}
	
	FastMap<Long, RankPlayerInfo> lvl_orders = new FastMap<Long, RankPlayerInfo>();
	FastMap<Long, RankPlayerInfo> money_orders = new FastMap<Long, RankPlayerInfo>();
	FastMap<Long, RankPlayerInfo> like_orders = new FastMap<Long, RankPlayerInfo>();
	
	GDL_G2C_GetRankRe _level_rank;
	GDL_G2C_GetRankRe _money_rank;
	GDL_G2C_GetRankRe _like_rank;
	
	public void updateMyRankHead(long player_id, String headurl) {
		if (lvl_orders.containsKey(player_id))
			lvl_orders.get(player_id).setHeadUrl(headurl);
		if (money_orders.containsKey(player_id))
			money_orders.get(player_id).setHeadUrl(headurl);
		if (like_orders.containsKey(player_id))
			like_orders.get(player_id).setHeadUrl(headurl);
	}
	
	public void updateMyRankName(long player_id, String name) {
		if (lvl_orders.containsKey(player_id))
			lvl_orders.get(player_id).setPlayerName(name);
		if (money_orders.containsKey(player_id))
			money_orders.get(player_id).setPlayerName(name);
		if (like_orders.containsKey(player_id))
			like_orders.get(player_id).setPlayerName(name);
	}
	
	public int getMyRank(int type, long player) {
		if (type == 1) {
			if (lvl_orders.containsKey(player))
				return lvl_orders.get(player).getOrder();
		} else if (type ==0) {
			if (money_orders.containsKey(player))
				return money_orders.get(player).getOrder();
		} else if (type == 2) {
			if (like_orders.containsKey(player))
				return like_orders.get(player).getOrder();
		}
		
		return -1;
	}
	
	public void reloadRank() {
		List<DatabaseLevel_rank> lvl_ranks = DbMgr.getInstance().getShareDB().Select(DatabaseLevel_rank.class, "1=1 order by level desc,exp desc limit 100");
		List<DatabaseMoney_rank> money_ranks = DbMgr.getInstance().getShareDB().Select(DatabaseMoney_rank.class, "1=1 order by coin desc,level desc,viplvl desc limit 100");
		List<DatabaseLike_rank> like_ranks = DbMgr.getInstance().getShareDB().Select(DatabaseLike_rank.class, "1=1 order by liked desc,level desc,viplvl desc limit 100");
		
		lvl_orders.clear();
		money_orders.clear();
		like_orders.clear();
		
		_level_rank = new GDL_G2C_GetRankRe();
		int count = Math.min(20, lvl_ranks.size());
		for (int i = 1; i <= count; ++i) {
			RankPlayerInfo me = new RankPlayerInfo();
			DatabaseLevel_rank info = lvl_ranks.get(i - 1);
			me.setPlayerId(info.player_id);
			me.setPlayerName(info.name);
			me.setLevel(info.level);
			me.setVipLevel(info.viplvl);
			me.setMoney(info.coin);
			me.setLiked(info.liked);
			me.setDiff(info.change);
			me.setHeadUrl(info.url);
			me.setSex(info.sex);
			me.setExp(info.exp);
			me.setOrder(i);
			_level_rank.getRank().add(me);
			lvl_orders.put(info.player_id, me);
		}
		
		_money_rank = new GDL_G2C_GetRankRe();
		count = Math.min(20, money_ranks.size());
		for (int i = 1; i <= count; ++i) {
			RankPlayerInfo me = new RankPlayerInfo();
			DatabaseMoney_rank info = money_ranks.get(i - 1);
			me.setPlayerId(info.player_id);
			me.setPlayerId(info.player_id);
			me.setPlayerName(info.name);
			me.setLevel(info.level);
			me.setVipLevel(info.viplvl);
			me.setMoney(info.coin);
			me.setLiked(info.liked);
			me.setDiff(info.change);
			me.setHeadUrl(info.url);
			me.setSex(info.sex);
			me.setExp(info.exp);
			me.setOrder(i);
			_money_rank.getRank().add(me);
			money_orders.put(info.player_id, me);
		}
		
		_like_rank = new GDL_G2C_GetRankRe();
		count = Math.min(20, like_ranks.size());
		for (int i = 1; i <= count; ++i) {
			RankPlayerInfo me = new RankPlayerInfo();
			DatabaseLike_rank info = like_ranks.get(i - 1);
			me.setPlayerId(info.player_id);
			me.setPlayerId(info.player_id);
			me.setPlayerName(info.name);
			me.setLevel(info.level);
			me.setVipLevel(info.viplvl);
			me.setMoney(info.coin);
			me.setLiked(info.liked);
			me.setDiff(info.change);
			me.setHeadUrl(info.url);
			me.setSex(info.sex);
			me.setExp(info.exp);
			me.setOrder(i);
			_like_rank.getRank().add(me);
			like_orders.put(info.player_id, me);
		}
	}
	
	
	public GDL_G2C_GetRankRe genRank(long player_id, int type, int mode) {
		synchronized (RankManager.class) {
			if (lvl_orders.isEmpty())
				reloadRank();
		}
		
		GDL_G2C_GetRankRe re = new GDL_G2C_GetRankRe();
		re.setMode(mode);
		re.setType(type);
		if (type == 1) {
			if (mode == 1) {
				if (_level_rank.getRank().size() > 0)
					re.setRank(_level_rank.getRank().subList(0, 1));
			}
			else
				re.setRank(_level_rank.getRank());
			if (lvl_orders.containsKey(player_id)) {
				RankPlayerInfo me = lvl_orders.get(player_id);
				re.setSelf(me);
			} else {
				DatabasePlayer_brief_info oerer = DbMgr.getInstance().getShareDB().SelectOne(DatabasePlayer_brief_info.class,
						"player_id=?", player_id);
				
				RankPlayerInfo me = new RankPlayerInfo();
				me.setPlayerId(oerer.player_id);
				me.setPlayerName(oerer.name);
				me.setLevel(oerer.level);
				me.setVipLevel(oerer.viplevel);
				me.setMoney(oerer.money);
				me.setLiked(oerer.liked);
				me.setDiff(0);
				me.setOrder(-1);
				me.setSex(oerer.sex);
				me.setExp(oerer.exp);
				re.setSelf(me);
			}
			
			return re;
		} else if (type == 0) {
			if (mode == 1) {
				if (_money_rank.getRank().size() > 0)
					re.setRank(_money_rank.getRank().subList(0, 1));
			}
			else
				re.setRank(_money_rank.getRank());
			if (money_orders.containsKey(player_id)) {
				RankPlayerInfo me = money_orders.get(player_id);
				re.setSelf(me);
			} else {
				DatabasePlayer_brief_info oerer = DbMgr.getInstance().getShareDB().SelectOne(DatabasePlayer_brief_info.class,
						"player_id=?", player_id);
				
				RankPlayerInfo me = new RankPlayerInfo();
				me.setPlayerId(oerer.player_id);
				me.setPlayerName(oerer.name);
				me.setLevel(oerer.level);
				me.setVipLevel(oerer.viplevel);
				me.setMoney(oerer.money);
				me.setLiked(oerer.liked);
				me.setDiff(0);
				me.setOrder(-1);
				me.setSex(oerer.sex);
				me.setExp(oerer.exp);
				re.setSelf(me);
			}
			
			return re;
		} else if (type == 2) {
			if (mode == 1) {
				if (_like_rank.getRank().size() > 0)
					re.setRank(_like_rank.getRank().subList(0, 1));
			}
			else
				re.setRank(_like_rank.getRank());
			if (like_orders.containsKey(player_id)) {
				RankPlayerInfo me = like_orders.get(player_id);
				re.setSelf(me);
			} else {
				DatabasePlayer_brief_info oerer = DbMgr.getInstance().getShareDB().SelectOne(DatabasePlayer_brief_info.class,
						"player_id=?", player_id);
				
				RankPlayerInfo me = new RankPlayerInfo();
				me.setPlayerId(oerer.player_id);
				me.setPlayerName(oerer.name);
				me.setLevel(oerer.level);
				me.setVipLevel(oerer.viplevel);
				me.setMoney(oerer.money);
				me.setLiked(oerer.liked);
				me.setDiff(0);
				me.setOrder(-1);
				me.setSex(oerer.sex);
				me.setExp(oerer.exp);
				re.setSelf(me);
			}
			
			return re;
		}else{
			return null;
		}
	}
	
	public <T> int findOrder(List<T> list, long player_id) {
		int count = 0;
		for (Object ele : list) {
			long src_id = 0;
			if (ele instanceof DatabaseLevel_rank) {
				DatabaseLevel_rank r = (DatabaseLevel_rank)ele;
				src_id = r.player_id;
			} else if (ele instanceof DatabaseMoney_rank) {
				DatabaseMoney_rank r = (DatabaseMoney_rank)ele;
				src_id = r.player_id;
			} else if (ele instanceof DatabaseLike_rank) {
				DatabaseLike_rank r = (DatabaseLike_rank)ele;
				src_id = r.player_id;
			}
			
			count = count + 1;
			if (src_id == player_id)
				return count;
		}
		return -1;
	}
	
	public <T> void buildOrderMap(List<T> list, Map<Long, Integer> map) {
		int order = 0;
		for (Object ele : list) {
			order += 1;
			
			long src_id = 0;
			if (ele instanceof DatabaseLevel_rank) {
				DatabaseLevel_rank r = (DatabaseLevel_rank)ele;
				src_id = r.player_id;
			} else if (ele instanceof DatabaseMoney_rank) {
				DatabaseMoney_rank r = (DatabaseMoney_rank)ele;
				src_id = r.player_id;
			} else if (ele instanceof DatabaseLike_rank) {
				DatabaseLike_rank r = (DatabaseLike_rank)ele;
				src_id = r.player_id;
			}
			
			map.put(src_id, order);
		}
	}
	
	public void flushRank() {
		// 取出原来的排名
		List<DatabaseLevel_rank> last_lvl_ranks;
		List<DatabaseMoney_rank> last_money_ranks;
		List<DatabaseLike_rank> last_like_ranks;
		
		last_lvl_ranks = DbMgr.getInstance().getShareDB().Select(DatabaseLevel_rank.class, "1=1 order by level desc,exp desc limit 100");
		last_money_ranks = DbMgr.getInstance().getShareDB().Select(DatabaseMoney_rank.class, "1=1 order by coin desc,level desc,viplvl desc limit 100");
		last_like_ranks = DbMgr.getInstance().getShareDB().Select(DatabaseLike_rank.class, "1=1 order by liked desc,level desc,viplvl desc limit 100");
		
		Map<Long, Integer> lvl_o_map = new FastMap<Long, Integer>();
		Map<Long, Integer> money_o_map = new FastMap<Long, Integer>();
		Map<Long, Integer> like_o_map = new FastMap<Long, Integer>();
		buildOrderMap(last_lvl_ranks, lvl_o_map);
		buildOrderMap(last_money_ranks, money_o_map);
		buildOrderMap(last_like_ranks, like_o_map);
		
		Calendar calendar2 = TimeUtil.GetCalendar(TimeUtil.GetDateTime());
		int day = calendar2.get(Calendar.DAY_OF_YEAR);
		calendar2.set(Calendar.DAY_OF_YEAR, day - 3);
		String daysss = TimeUtil.GetDateString( calendar2.getTimeInMillis() );
		flushLevelRank(daysss);
		flushLikedRank(daysss);
		flushMoneyRank(daysss);
		
		List<DatabaseLevel_rank> now_lvl_ranks;
		List<DatabaseMoney_rank> now_money_ranks;
		List<DatabaseLike_rank> now_like_ranks;
		
		now_lvl_ranks = DbMgr.getInstance().getShareDB().Select(DatabaseLevel_rank.class, "1=1 order by level desc,exp desc limit 100");
		now_money_ranks = DbMgr.getInstance().getShareDB().Select(DatabaseMoney_rank.class, "1=1 order by coin desc,level desc,viplvl desc limit 100");
		now_like_ranks = DbMgr.getInstance().getShareDB().Select(DatabaseLike_rank.class, "1=1 order by liked desc,level desc,viplvl desc limit 100");
		
		int order = 0;
		for (DatabaseLevel_rank r : now_lvl_ranks) {
			order += 1;
			Integer old_order = lvl_o_map.get(r.player_id);
			// 之前没在榜上，这次上榜啦
			if (old_order == null)
				continue;
			
			if (old_order == order)
				continue;
			
			r.change = old_order - order;
			r.save();
		}
		
		 order = 0;
		for (DatabaseMoney_rank r : now_money_ranks) {
			order += 1;
			Integer old_order = money_o_map.get(r.player_id);
			// 之前没在榜上，这次上榜啦
			if (old_order == null)
				continue;
			
			if (old_order == order)
				continue;
			
			r.change = old_order - order;
			r.save();
		}
		
		 order = 0;
		for (DatabaseLike_rank r : now_like_ranks) {
			order += 1;
			Integer old_order = like_o_map.get(r.player_id);
			// 之前没在榜上，这次上榜啦
			if (old_order == null)
				continue;
			
			if (old_order == order)
				continue;
			
			r.change = old_order - order;
			r.save();
		}
	}
	
	private void flushLevelRank(String daysss) {
		DbMgr.getInstance().getShareDB().Execute("delete from level_rank;");
		DbMgr.getInstance().getShareDB().Execute("insert into level_rank(player_id, name,level,viplvl,coin,liked,exp,sex,url) "
				+ "select player_id,name,level,viplevel,money,liked,exp,sex,head_url "
				+ "from player_brief_info where last_active_time>=? order by exp desc limit 100;", 
				daysss);
	}
	
	private void flushMoneyRank(String daysss) {
		DbMgr.getInstance().getShareDB().Execute("delete from money_rank;");
		DbMgr.getInstance().getShareDB().Execute("insert into money_rank(player_id, name,level,viplvl,coin,liked,sex,url) "
				+ "select player_id,name,level,viplevel,money,liked,sex,head_url "
				+ "from player_brief_info where last_active_time>=? order by money desc,level desc,viplevel desc limit 100;", daysss);
	}
	
	private void flushLikedRank(String daysss) {
		DbMgr.getInstance().getShareDB().Execute("delete from like_rank;");
		DbMgr.getInstance().getShareDB().Execute("insert into like_rank(player_id, name,level,viplvl,coin,liked,sex,url) "
				+ "select player_id,name,level,viplevel,money,liked,sex,head_url "
				+ "from player_brief_info where last_active_time>=? order by liked desc,level desc,viplevel desc limit 100;", daysss);
	}
}
