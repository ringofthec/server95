package gameserver;

import gameserver.jsonprotocol.Consts;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javolution.util.FastMap;
import net.schst.EventDispatcher.Event;
import net.schst.EventDispatcher.EventListener;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.network.websock.PlayerConnection;
import com.commons.util.TimeUtil;

public class MethodStatitic implements EventListener{
	private static final Logger logger = LoggerFactory.getLogger(MethodStatitic.class);
	
	private final static MethodStatitic instance = new MethodStatitic();
	public static MethodStatitic getInstance() { return instance; }
	
	private class State{
		public long maxTime;
		public long totleTime;
		public int callTimes;
		public long maxTimePlayerId;
	}
	
	public FastMap<String, State> maps = new FastMap<String, MethodStatitic.State>();
	
	public synchronized void record(String messageType, long runcostTime, long playerId) {
		State info = maps.get(messageType);
		if (info == null) {
			info = new State();
			maps.put(messageType, info);
		}
		
		info.callTimes++;
		info.totleTime += runcostTime;
		if (runcostTime > info.maxTime) {
			info.maxTimePlayerId = playerId;
			info.maxTime = runcostTime;
		}
	}
	
	@Override
	public void handleEvent(Event e) throws Exception {
		putToFile();
	}

	public void putToFile() {
		final List<String> lines = new ArrayList<String>();	
		
		for (Entry<String, State> pair : maps.entrySet()) {
			StringBuilder builder = new StringBuilder();
			State temp = pair.getValue();
			builder.append(pair.getKey())
				.append(",")
				.append(temp.callTimes)
				.append(",")
				.append(temp.maxTime)
				.append(",")
				.append(temp.totleTime)
				.append(",")
				.append(temp.totleTime/temp.callTimes)
				.append(",")
				.append(temp.maxTimePlayerId);
			lines.add(builder.toString());
		}
		
		PrintStream ps = null;
		try {
			File statsFile = new File("log/method-stats/" 
							+ TimeUtil.GetDateString().replace(":", "-").replace(" ", "-") + ".log");
			if(!statsFile.getParentFile().exists())
			{	
				boolean success = statsFile.getParentFile().mkdirs();
				if (!success) {
					logger.error("Unable to create MethodStats log dir");
					return ;
				}
			}
			
			ps = new PrintStream(statsFile.getAbsolutePath());
			for (String line : lines)
			{
				ps.println(line);
			}
		}
        catch (Exception e) {
        	logger.error("Failed to save MethodStats, error: " + e.getMessage());
        }
        finally {
            IOUtils.closeQuietly(ps);
        }
	}
	
	public class Polcudsfds {
		public long coin;
		public int policy_id;
		public int policy_step;
		public long get;
		public long dec;
	}
	
	List<Polcudsfds> _all_re = new ArrayList<Polcudsfds>();
	List<Integer> _all_pid = new ArrayList<Integer>();
	List<Integer> _all_pstep = new ArrayList<Integer>();
	List<Long> _all_money = new ArrayList<Long>();
	List<Integer> _all_fall = new ArrayList<Integer>();
	public void recd(PlayerConnection con) {
		Polcudsfds pp = new Polcudsfds();
		pp.coin = con.getPlayer().getItemCountByTempId(Consts.getCOIN_ID());
		pp.policy_id = (int)con.getPlayer().getItemCountByTempId(112);
		pp.policy_step = (int)con.getPlayer().getItemCountByTempId(113);
		
		pp.dec = con.getPlayer().getItemCountByTempId(Consts.getGame_Cost_Count());
		pp.get = con.getPlayer().getItemCountByTempId(Consts.getGame_Earn_Count());
		_all_re.add(pp);
	}
	
	int reward_count = 0;
	public void setreward_count() {
		reward_count++;
	}
	
	public void recd(int pid, int pst) {
		_all_pid.add(pid);
		_all_pstep.add(pst);
	}
	
	public void recdM(long mn) {
		_all_money.add(mn);
	}
	
	public void recdF(int mn) {
		_all_fall.add(mn);
	}
	
	public void clear() {
		_all_re.clear();
		_all_pid.clear();
		_all_pstep.clear();
		_all_money.clear();
		_all_fall.clear();
		reward_count = 0;
	}
	
	public void save(int levelid, int premoney, int count) {
		final List<String> lines = new ArrayList<String>();
		for (int i = 0; i < _all_re.size(); ++i) {
			Polcudsfds pp = _all_re.get(i);
			StringBuilder builder = new StringBuilder();
			builder.append(pp.coin);/*
				.append("\t")
				.append(pp.policy_id)
				.append("\t")
				.append(pp.policy_step)
				.append("\t")
				.append(pp.get)
				.append("\t")
				.append(pp.dec)
				.append("\t")
				.append(pp.get - pp.dec)
				.append("\t")
				.append(_all_pid.get(i))
				.append("\t")
				.append(_all_money.get(i))
				.append("\t")
				.append(_all_fall.get(i));*/
			lines.add(builder.toString());
		}
		
		PrintStream ps = null;
		try {
			File statsFile = new File("log/slots/" 
							+ TimeUtil.GetDateString().replace(":", "-").replace(" ", "-") 
							+ "_" + levelid + "_" + premoney + "_" + count + ".log");
			if(!statsFile.getParentFile().exists())
			{	
				boolean success = statsFile.getParentFile().mkdirs();
				if (!success) {
					logger.error("Unable to create MethodStats log dir");
					return ;
				}
			}
			
			ps = new PrintStream(statsFile.getAbsolutePath());
			for (String line : lines)
			{
				ps.println(line);
			}
		}
        catch (Exception e) {
        	logger.error("Failed to save MethodStats, error: " + e.getMessage());
        }
        finally {
            IOUtils.closeQuietly(ps);
        }
		
		System.err.println("reward: " + reward_count);
	}
	
	public static void main(String[] args) {
		final List<String> lines = new ArrayList<String>();	
		lines.add("fdgdfgdfgfdg");
		lines.add("fdgdfgdfgfdg");
		
		PrintStream ps = null;
		try {
			File statsFile = new File("log/method-stats/" + TimeUtil.GetDateString() + ".log");
			if(!statsFile.getParentFile().exists())
			{	
				boolean success = statsFile.getParentFile().mkdirs();
				if (!success) {
					logger.error("Unable to create MethodStats log dir");
					return ;
				}
			}
			
			ps = new PrintStream(statsFile.getAbsolutePath());
			for (String line : lines)
			{
				ps.println(line);
			}
		}
        catch (Exception e) {
        	logger.error("Failed to save MethodStats, error: " + e.getMessage());
        }
        finally {
            IOUtils.closeQuietly(ps);
        }
	}
}
