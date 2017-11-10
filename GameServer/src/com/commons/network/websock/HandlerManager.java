package com.commons.network.websock;

import gameserver.MethodStatitic;
import gameserver.jsonprotocol.GDL_G2C_ShowAlert;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.network.websock.PlayerConnection.PacketsWrapper;
import com.commons.util.TimeUtil;

public class HandlerManager {
	private final Logger logger = LoggerFactory.getLogger(HandlerManager.class);
			
	private final static HandlerManager instance = new HandlerManager();
	public static HandlerManager getInstance() { return instance; }
	
	public class ProcessInfo {
		public Method handler;
		public String processName;
		public int msgId;
		public Object handlerObj;
		public ProcessInfo(int mid, String pname, Object hl, Method me) {
			this.msgId = mid;
			this.processName = pname;
			this.handler = me;
			this.handlerObj = hl;
		}
	}
	
	public class PacketInfo {
		public Method parse;
		public String packetName;
		
		public PacketInfo(String pname, Method me) {
			this.packetName = pname;
			this.parse = me;
		}
	}
	
	Map<Integer, ProcessInfo> _processinfo = new TreeMap<Integer, ProcessInfo>();
	Map<Integer, PacketInfo> _parketinfo = new TreeMap<Integer, PacketInfo>();
	public void pushLoginHandler(Class<?> clazz, Object handler, String processName, JsonPacket msgObj) {
		pushHandler(clazz, handler, processName, msgObj, ChannelHandlerContext.class);
	}
	public void pushNornalHandler(Class<?> clazz, Object handler, String processName, JsonPacket msgObj) {
		pushHandler(clazz, handler, processName, msgObj, PlayerConnection.class);
	}
	public void pushHandler(Class<?> clazz, Object handler, String processName, JsonPacket msgObj, Class<?> conClazz) {
		@SuppressWarnings("rawtypes")
		Class[] args = new Class[2];
		args[0] = conClazz;
		args[1] = msgObj.getClass();
		int protoId = msgObj.getProtocolId();
		try {
			Method method = clazz.getMethod(processName, args);
			_processinfo.put(protoId, new ProcessInfo(protoId, processName, handler, method));
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
		pushParse(protoId, msgObj.getClass());
	}
	
	public void pushParse(int msg_id, Class<?> clazz) {
		@SuppressWarnings("rawtypes")
		Class[] args = new Class[1];
		args[0] = String.class;
		try {
			Method method = clazz.getMethod("parse", args);
			_parketinfo.put(msg_id, new PacketInfo(clazz.getSimpleName(), method));
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public ProcessInfo getMsgProcessor(int msg_id) {
		ProcessInfo pi = _processinfo.get(msg_id);
		if (pi == null)
			return null;
		
		return pi;
	}
	
	public PacketInfo getPacketProcessor(int msg_id) {
		PacketInfo pi = _parketinfo.get(msg_id);
		if (pi == null)
			return null;
		
		return pi;
	}
	
	public void cacheData(ChannelHandlerContext ctx, String data) {
		PlayerConnection pc = PlayerConManager.getInstance().getCon(ctx);
		if (pc != null) {
			pc.addCache(data);
		}
	}
	
	public String getCacheData(ChannelHandlerContext ctx) {
		PlayerConnection pc = PlayerConManager.getInstance().getCon(ctx);
		if (pc != null) {
			return pc.mergeCache();
		}
		
		return null;
	}
	
	public static boolean debug_recv = true;
	public static boolean debug_send = true;
	public void processData(ChannelHandlerContext ctx, String data) {
		if (debug_recv)
			logger.info("recv : " + data);
		
		try {
			// 1. parse data
			PlayerConnection pc = PlayerConManager.getInstance().getCon(ctx);
			
			// 2. handle
			if (pc != null) {
				pc.processMsg(data);
			} else {
				int idx = data.indexOf(':');
				String protocolIdStr = data.substring(0, idx);
				String protocolData = data.substring(idx + 1);
				int protocolId = Integer.parseInt(protocolIdStr);
				PacketInfo packetInfo = getPacketProcessor(protocolId);
				ProcessInfo p = getMsgProcessor(protocolId);
				
				long pre_time = TimeUtil.GetDateTime();
				Object msg = packetInfo.parse.invoke(null, protocolData);
				p.handler.invoke(p.handlerObj, ctx, msg);
				MethodStatitic.getInstance().record(packetInfo.packetName, TimeUtil.GetDateTime() - pre_time, 0);
			}
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMsg(ChannelHandlerContext ctx, JsonPacket msg) {
		if (msg.getProtocolId() == -1) {
			logger.error("error msg, name=" + msg.getClass().getName());
			return ;
		}
		
		ArrayList<JsonPacket> list = new ArrayList<>();
		list.add(msg);
		PacketsWrapper pw = new PacketsWrapper(list);
		TextWebSocketFrame tws = new TextWebSocketFrame(pw.json());
		ctx.writeAndFlush(tws);
	}
	
	public void showToast(ChannelHandlerContext ctx, String message) {
		GDL_G2C_ShowAlert msg = new GDL_G2C_ShowAlert();
		msg.setMsg(message);
		HandlerManager.getInstance().sendMsg(ctx, msg);
	}
	
	public void showToast(PlayerConnection ctx, String message) {
		showToast(ctx.getRawCon(), message);
	}
}
