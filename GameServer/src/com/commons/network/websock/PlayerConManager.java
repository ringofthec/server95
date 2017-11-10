package com.commons.network.websock;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;
import java.util.Set;

import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.util.CachePool;
import com.gdl.data.PlayerData;
import com.gdl.game.PlayerInstanceInfo;

public final class PlayerConManager {
	FastMap<Long, PlayerConnection> m_allcon1 = new FastMap<Long, PlayerConnection>().shared();
	FastMap<ChannelHandlerContext, Long> m_allcon2 = new FastMap<ChannelHandlerContext, Long>().shared();
	static PlayerConManager _inst = new PlayerConManager();
	public static PlayerConManager getInstance() {return _inst;}
	
	private final Logger logger = LoggerFactory.getLogger(PlayerConManager.class);
	
	// 缓存20分钟吧先
	CachePool<Long, PlayerData> m_cacheData = new CachePool<>(30);
	
	public int getPlayerNum() {
		return m_allcon1.size();
	}
	
	public void putCon(long player_id, PlayerConnection pc) {
		m_allcon1.put(player_id, pc);
		m_allcon2.put(pc.getRawCon(), player_id);
	}
	
	public PlayerConnection getCon(long plid) {
		return m_allcon1.get(plid);
	}
	
	public PlayerConnection getCon(ChannelHandlerContext ct) {
		Long player_id =  m_allcon2.get(ct);
		if (player_id == null)
			return null;
		
		return m_allcon1.get(player_id);
	}
	
	public void saveAll() {
		logger.error("begin save..................");
		for (PlayerConnection pc : m_allcon1.values()) {
			try {
				logger.error("save player.....{}", pc.getPlayerId());
				pc.getPlayer().save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.error("end save..................");
	}
	
	public void disAll() {
		logger.error("begin save..................");
		for (PlayerConnection pc : m_allcon1.values()) {
			try {
				logger.error("save player.....{}", pc.getPlayerId());
				pc.getPlayer().save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.error("end save..................");
	}
	
	public void delCon(ChannelHandlerContext chc) {
		Long playerid = m_allcon2.remove(chc);
		if (playerid != null) {
			PlayerConnection pp = m_allcon1.remove(playerid);
			if (pp != null) {
				PlayerData pd = pp.getPlayer();
				pd.onDisconnection(pp);
				pd.save();
				//m_cacheData.cache(pd.getPlayerId(), pd);
			}
		}
	}
	
	public void delCon(PlayerConnection pc) {
		PlayerConnection pp = m_allcon1.get(pc.getPlayerId());
		if (pp != null) {
			ChannelHandlerContext p = pp.getRawCon();
			delCon(p);
		}
	}
	
	public PlayerData getPlayerCache(long playerId) {
		return m_cacheData.getData(playerId);
	}
	
	public void broadcastMsg(JsonPacket msg) {
		try {
			for (PlayerConnection pc : m_allcon1.values()) {
				try {
					pc.directSendPack(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastMsgWithOut(JsonPacket msg, List<Long> playerIds) {
		try {
			for (PlayerConnection pc : m_allcon1.values()) {
				try {
					if (playerIds.contains(pc.getPlayerId()))
						continue;

					pc.directSendPack(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastMsgWithOut(JsonPacket msg, Long playerId) {
		try {
			for (PlayerConnection pc : m_allcon1.values()) {
				try {
					if (playerId == pc.getPlayerId())
						continue;

					pc.directSendPack(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastMsg(JsonPacket msg, List<PlayerInstanceInfo> pls) {
		try {
			for (PlayerInstanceInfo p : pls) {
				PlayerConnection pc = m_allcon1.get(p.player_id);
				try {
					if (pc != null)
						pc.directSendPack(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastMsg(JsonPacket msg, Set<Long> playerids) {
		try {
			for (long pid : playerids) {
				PlayerConnection pc = m_allcon1.get(pid);
				try {
					if (pc != null)
						pc.directSendPack(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastMsg1(JsonPacket msg, List<Long> playerids) {
		try {
			for (long pid : playerids) {
				PlayerConnection pc = m_allcon1.get(pid);
				try {
					if (pc != null)
						pc.directSendPack(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastMsg1(JsonPacket msg, List<Long> playerids, long expectid) {
		try {
			for (long pid : playerids) {
				if (pid == expectid)
					continue;
				
				PlayerConnection pc = m_allcon1.get(pid);
				try {
					if (pc != null)
						pc.directSendPack(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
