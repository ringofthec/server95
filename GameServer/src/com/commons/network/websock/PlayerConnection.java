package com.commons.network.websock;

import gameserver.MethodStatitic;
import gameserver.taskmanager.FIFORunnableQueue;
import gameserver.thread.ThreadPoolManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.network.websock.HandlerManager.PacketInfo;
import com.commons.network.websock.HandlerManager.ProcessInfo;
import com.commons.util.JsonUtil;
import com.commons.util.TimeUtil;
import com.gdl.data.PlayerData;

public class PlayerConnection {
	
	private final static Logger logger = LoggerFactory.getLogger(PlayerConnection.class);
			
	// 网络连接
	ChannelHandlerContext m_con;
	
	// 连接状态
	ConState m_con_state;
	
	// 发送队列
	ArrayList<JsonPacket> m_allsendpack = new ArrayList<JsonPacket>();
	
	boolean send_flsg = true;
	
	int channel;
	
	public int getChannel() {return channel;}
	public void setChannel(int c) {channel = c;}
	
	public void setSendFlsg(boolean send_flag) {
		this.send_flsg = send_flag;
	}
	
	// 玩家数据
	PlayerData m_data;
	
	ArrayList<String> m_packetCache = new ArrayList<String>();
	
	public void addCache(String pack) {
		m_packetCache.add(pack);
	}
	
	public String mergeCache() {
		String temp = StringUtils.join(m_packetCache, "");
		m_packetCache.clear();
		return temp;
	}
	
	public boolean hasCache() {
		return !m_packetCache.isEmpty();
	}
	
	public PlayerConnection(ChannelHandlerContext con, PlayerData data) {
		m_con = con;
		m_data = data;
	}
	
	public void close() {
		m_con.close();
	}
	
	public ChannelHandlerContext getRawCon() {
		return m_con;
	}
	
	public ConState getConState() {
		return this.m_con_state;
	}
	
	public void setConState(ConState cs) {
		this.m_con_state = cs;
	}
	
	public PlayerData getPlayer() {
		return m_data;
	}
	
	public long getPlayerId() {
		return m_data.getPlayerId();
	}
	
	public void send(JsonPacket msg) {
		if (!send_flsg)
			return ;
		
		m_allsendpack.add(msg);
	}
	
	public static class PacketWrapper {
		int protocolId;
		String msg;
		
		public PacketWrapper(JsonPacket msg) {
			this.msg = msg.json();
			this.protocolId = msg.getProtocolId();
		}
	}
	
	private FIFORunnableQueue<Runnable> _packetQueue;
	public FIFORunnableQueue<Runnable> getPacketQueue() {
		if (_packetQueue == null) {
			_packetQueue = new FIFORunnableQueue<Runnable>() {
			};
			_packetQueue.setThreadPoolManager(ThreadPoolManager.getInstance());
		}

		return _packetQueue;
	}
	
	public void processMsg(final String data) {
		final PlayerConnection pcon = this;
		if (false) {
			getPacketQueue().execute(new Runnable() {
					@Override
					public void run() {
						long pre_time = TimeUtil.GetDateTime();
						
						int idx = data.indexOf(':');
						String protocolIdStr = data.substring(0, idx);
						String protocolData = data.substring(idx + 1);
						int protocolId = Integer.parseInt(protocolIdStr);
						PacketInfo packetInfo = HandlerManager.getInstance().getPacketProcessor(protocolId);
						ProcessInfo p = HandlerManager.getInstance().getMsgProcessor(protocolId);
						
						try {
							Object msg = packetInfo.parse.invoke(null, protocolData);
							p.handler.invoke(p.handlerObj, pcon, msg);
							checkData();
							sendAllPacket();
						} catch (IllegalAccessException | IllegalArgumentException
								| InvocationTargetException e) {
							e.printStackTrace();
							close();
						} catch (Exception e1) {
							e1.printStackTrace();
							close();
						}
						
						MethodStatitic.getInstance().record(packetInfo.packetName, TimeUtil.GetDateTime() - pre_time, getPlayerId());
					}
				}
			);
		} else {
			long pre_time = TimeUtil.GetDateTime();
			
			int idx = data.indexOf(':');
			String protocolIdStr = data.substring(0, idx);
			String protocolData = data.substring(idx + 1);
			int protocolId = Integer.parseInt(protocolIdStr);
			PacketInfo packetInfo = HandlerManager.getInstance().getPacketProcessor(protocolId);
			ProcessInfo p = HandlerManager.getInstance().getMsgProcessor(protocolId);
			
			try {
				Object msg = packetInfo.parse.invoke(null, protocolData);
				p.handler.invoke(p.handlerObj, pcon, msg);
				checkData();
				sendAllPacket();
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
				close();
			} catch (Exception e1) {
				e1.printStackTrace();
				close();
			}
			
			MethodStatitic.getInstance().record(packetInfo.packetName, TimeUtil.GetDateTime() - pre_time, getPlayerId());
		}
	}
	
	public void checkData() {
		m_data.checkData(this);
	}
	
	public static class PacketsWrapper {
		ArrayList<PacketWrapper> msgs = new ArrayList<PlayerConnection.PacketWrapper>();
		
		public PacketsWrapper(ArrayList<JsonPacket> msgs) {
			for (JsonPacket m : msgs) {
				this.msgs.add(new PacketWrapper(m));
			}
		}
		
		public String json() {
			return JsonUtil.ObjectToJson(this);
		}
	}
	
	public void directSendPack(JsonPacket jp) {
		if (!send_flsg)
			return ;
		
		ArrayList<JsonPacket> msgs = new ArrayList<JsonPacket>();
		msgs.add(jp);
		PacketsWrapper pw = new PacketsWrapper(msgs);
		if (HandlerManager.debug_send)
			logger.info("send: " + pw.json());
		TextWebSocketFrame tws = new TextWebSocketFrame(pw.json());
		m_con.writeAndFlush(tws);
	}
	
	public void sendAllPacket() {
		if (m_allsendpack.isEmpty())
			return ;
		
		PacketsWrapper pw = new PacketsWrapper(m_allsendpack);
		TextWebSocketFrame tws = new TextWebSocketFrame(pw.json());
		m_con.writeAndFlush(tws);
		if (HandlerManager.debug_send)
			logger.info("send: " + pw.json());
		m_allsendpack.clear();
	}
}
