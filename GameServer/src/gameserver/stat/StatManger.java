package gameserver.stat;


import gameserver.network.server.connection.Connection;
import gameserver.utils.DbMgr;
import gameserver.utils.Item_Channel;
import javolution.util.FastMap;
import net.schst.EventDispatcher.Event;
import net.schst.EventDispatcher.EventListener;

import com.commons.util.TimeUtil;
import commonality.Common.SETNUMBER_TYPE;

import databaseshare.DatabaseGold_stat_dec_log;
import databaseshare.DatabaseGold_stat_inc_log;
import databaseshare.DatabaseLogs;
import databaseshare.DatabaseMomey_stat_dec_log;
import databaseshare.DatabaseMomey_stat_inc_log;
import databaseshare.DatabaseTask_log;


public class StatManger implements EventListener{
	private final static StatManger instance = new StatManger();
	public static StatManger getInstance() { return instance; }
	private DatabaseLogs m_logs = null;
	private FastMap<Integer, DatabaseTask_log> m_tasklogs = new FastMap<Integer, DatabaseTask_log>().shared();
	
	private FastMap<Integer, DatabaseMomey_stat_inc_log> m_moneyinclogs = new FastMap<Integer, DatabaseMomey_stat_inc_log>().shared();
	private FastMap<Integer, DatabaseGold_stat_inc_log> m_goldinclogs = new FastMap<Integer, DatabaseGold_stat_inc_log>().shared();
	private FastMap<Integer, DatabaseMomey_stat_dec_log> m_moneydeclogs = new FastMap<Integer, DatabaseMomey_stat_dec_log>().shared();
	private FastMap<Integer, DatabaseGold_stat_dec_log> m_golddeclogs = new FastMap<Integer, DatabaseGold_stat_dec_log>().shared();

	private StatManger() {
		Save();
	}
	
	@Override
	public void handleEvent(Event e) throws Exception {
		Save();
	}
	
	public void Save() {
		if (m_logs != null)
			DbMgr.getInstance().getShareDB().InsertOrUpdate(m_logs);
		m_logs = new DatabaseLogs();
		m_logs.log_day = TimeUtil.GetDateStringDay();
		
		for (DatabaseTask_log tlog : m_tasklogs.values()) {
			DbMgr.getInstance().getShareDB().InsertOrUpdate(tlog);
		}
		m_tasklogs.clear();
		
		for (DatabaseMomey_stat_dec_log tlog : m_moneydeclogs.values()) {
			DbMgr.getInstance().getShareDB().InsertOrUpdate(tlog);
		}
		m_moneydeclogs.clear();
		
		for (DatabaseGold_stat_dec_log tlog : m_golddeclogs.values()) {
			DbMgr.getInstance().getShareDB().InsertOrUpdate(tlog);
		}
		m_golddeclogs.clear();
		
		for (DatabaseMomey_stat_inc_log tlog : m_moneyinclogs.values()) {
			DbMgr.getInstance().getShareDB().InsertOrUpdate(tlog);
		}
		m_moneyinclogs.clear();
		
		for (DatabaseGold_stat_inc_log tlog : m_goldinclogs.values()) {
			DbMgr.getInstance().getShareDB().InsertOrUpdate(tlog);
		}
		m_goldinclogs.clear();
	}
	
	synchronized public void onTaskComplete(int taskid) {
		DatabaseTask_log tlog = m_tasklogs.get(taskid);
		if (!m_tasklogs.containsKey(taskid)) {
			tlog = new DatabaseTask_log();
			tlog.task_id = taskid;
			m_tasklogs.put(taskid, tlog);
		}
		
		tlog.count = inc(tlog.count, 1);
	}

	public void onNewPlayer() {
		m_logs.new_player = inc(m_logs.new_player);
	}
	
	public void onMoneyChange(SETNUMBER_TYPE type, int change, Item_Channel itemch) {
		if (type == SETNUMBER_TYPE.ADDITION) {
			m_logs.money_gen = inc(m_logs.money_gen, change);
			
			int channelId = itemch.getNum();
			DatabaseMomey_stat_inc_log tlog = m_moneyinclogs.get(channelId);
			if (!m_moneyinclogs.containsKey(channelId)) {
				tlog = new DatabaseMomey_stat_inc_log();
				tlog.channel_id = channelId;
				m_moneyinclogs.put(channelId, tlog);
			}
			tlog.count = inc(tlog.count, change);
		}
		else if (type == SETNUMBER_TYPE.MINUS) {
			m_logs.money_cost = inc(m_logs.money_cost, change);
			
			int channelId = itemch.getNum();
			DatabaseMomey_stat_dec_log tlog = m_moneydeclogs.get(channelId);
			if (!m_moneydeclogs.containsKey(channelId)) {
				tlog = new DatabaseMomey_stat_dec_log();
				tlog.channel_id = channelId;
				m_moneydeclogs.put(channelId, tlog);
			}
			tlog.count = inc(tlog.count, change);
		}
	}
	
	public void onGoldChange(SETNUMBER_TYPE type, int change, Item_Channel itemch) {
		if (type == SETNUMBER_TYPE.ADDITION) {
			m_logs.gold_gen = inc(m_logs.gold_gen, change);
			
			int channelId = itemch.getNum();
			DatabaseGold_stat_inc_log tlog = m_goldinclogs.get(channelId);
			if (!m_goldinclogs.containsKey(channelId)) {
				tlog = new DatabaseGold_stat_inc_log();
				tlog.channel_id = channelId;
				m_goldinclogs.put(channelId, tlog);
			}
			tlog.count = inc(tlog.count, change);
		}
		else if (type == SETNUMBER_TYPE.MINUS) {
			m_logs.gold_cost = inc(m_logs.gold_cost, change);
			
			int channelId = itemch.getNum();
			DatabaseGold_stat_dec_log tlog = m_golddeclogs.get(channelId);
			if (!m_golddeclogs.containsKey(channelId)) {
				tlog = new DatabaseGold_stat_dec_log();
				tlog.channel_id = channelId;
				m_golddeclogs.put(channelId, tlog);
			}
			tlog.count = inc(tlog.count, change);
		}
	}
	
	private int inc(Integer num) {
		if (num == null)
			return new Integer(1);
		else
			return num + 1;
	}
	
	private long inc(Long num, int inc) {
		if (num == null)
			return new Long(inc);
		else
			return num + inc;
	}
	
	private int inc(Integer num, int inc) {
		if (num == null)
			return new Integer(inc);
		else
			return num + inc;
	}
	
	public void onLogin() {
		m_logs.login_count = inc(m_logs.login_count);
	}
	
	public void onNewDay(Connection con) {
		onNewDay(con.getAccount().getCreateTime());
	}
	
	public void onNewDay(long create_time) {
		long creatureDate = create_time;
		long curTime = TimeUtil.GetDateTime();

		m_logs.login_player = inc(m_logs.login_player);
		int dayDiff = TimeUtil.getDayDiff(creatureDate, curTime);
		switch (dayDiff) {
		case 1:
			m_logs.d2daylogin = inc(m_logs.d2daylogin);
			break;

		case 2:
			m_logs.d3daylogin = inc(m_logs.d3daylogin);
			break;

		case 3:
			m_logs.d4daylogin = inc(m_logs.d4daylogin);
			break;

		case 4:
			m_logs.d5daylogin = inc(m_logs.d5daylogin);
			break;

		case 5:
			m_logs.d6daylogin = inc(m_logs.d6daylogin);
			break;

		case 6:
			m_logs.d7daylogin = inc(m_logs.d7daylogin);
			break;

		case 7:
			m_logs.d8daylogin = inc(m_logs.d8daylogin);
			break;

		case 8:
			m_logs.d9daylogin = inc(m_logs.d9daylogin);
			break;

		case 9:
			m_logs.d10daylogin = inc(m_logs.d10daylogin);
			break;

		case 14:
			m_logs.d15daylogin = inc(m_logs.d15daylogin);
			break;

		case 19:
			m_logs.d20daylogin = inc(m_logs.d20daylogin);
			break;

		case 24:
			m_logs.d25daylogin = inc(m_logs.d25daylogin);
			break;

		case 29:
			m_logs.d30daylogin = inc(m_logs.d30daylogin);
			break;

		case 39:
			m_logs.d40daylogin = inc(m_logs.d40daylogin);
			break;

		case 49:
			m_logs.d50daylogin = inc(m_logs.d50daylogin);
			break;

		case 59:
			m_logs.d60daylogin = inc(m_logs.d60daylogin);
			break;

		case 119:
			m_logs.d120daylogin = inc(m_logs.d120daylogin);
			break;
		}
	}
	
	public void onRecharge(int recharge) {
		m_logs.recharge_num = inc(m_logs.recharge_num);
		m_logs.recharge = inc(m_logs.recharge, recharge);
	}
}
