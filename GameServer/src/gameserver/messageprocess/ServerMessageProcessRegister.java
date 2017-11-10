package gameserver.messageprocess;

import gameserver.MethodStatitic;
import gameserver.messageprocess.base.IMessageProcessRegister;
import gameserver.messageprocess.base.MessageProcess;
import gameserver.messageprocess.base.ProcessInfo;
import gameserver.network.server.connection.Connection;
import gameserver.utils.GameException;
import gameserver.utils.Util;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.util.TimeUtil;
import com.google.protobuf.GeneratedMessage;
import commonality.Common;
import commonality.PromptType;

// 客户端协议处理分发器
// 每一个游戏客户端发上来的协议，都会在这个函数中被分发到对应的处理函数中处理
public class ServerMessageProcessRegister extends IMessageProcessRegister {
	public static boolean isRobot = false;
	private final Logger logger = LoggerFactory.getLogger(ServerMessageProcessRegister.class);
	@Override
	protected Class<?>[] getClasses(Class<? extends GeneratedMessage> messageType) {
		return new Class<?>[]{Connection.class,messageType};
	}
	
	public void invokeMessage(Connection connection,short length, ByteBuffer buffer) throws Exception  {
		synchronized (connection) {
			// 1. 先解压缩数据
//			byte[] bytesCache = new byte[length];
//			bufferCache.get(bytesCache);
//			ByteBuffer buffer = ByteBuffer.wrap(GZipUtil.Decompress(bytesCache)).order(ByteOrder.LITTLE_ENDIAN);
			
			if (isRobot)
				buffer.get();
			
			/*
			Short msgId = buffer.getShort();
			if (msgId.equals(Common.BREAKING_MESSAGE_ID)) {
				if (connection.getMessageProcessing()) {
					connection.sendReceiptMessage();
					logger.error("上一消息还未处理完成");
					return;
				}
				connection.setMessageProcessing(true);
				connection.setReceiptPakage(false);
			}
			*/
			
			// 2. 这段数据中可能包含多个协议，依次处理
			//String session = Util.ReadString(buffer);
	    	Short msgNum = 1;
	    	String strMessages = "";
	    	
	    	for (Short i = 0;i < msgNum; ++i) {
	    		Short messageType = buffer.getShort();
	    		byte[] bytes = new byte[buffer.capacity() - buffer.position()];
	    		buffer.get(bytes);
	    		strMessages += (messageType + " ");
	    		try {
	        		ProcessInfo processInfo = getMsgProcess(messageType);
	        		if (processInfo == null) {
	        			logger.info("ProcessInfo is null [{}]",messageType);
	        			continue;
	        		}
	        		
	        		/*
	        		if (processInfo.checkSession) {
	        			if (!connection.CheckSession(session,true)) {
	        				logger.error("session 验证失败 session : {}  form : {}",session,connection.getIP());
	        				throw new GameException(PromptType.SEESION_ERROR, "session验证失败 请重新登录");
	        			} else {
	        				long time = connection.CheckState(session);
	        				if (time > 0) {
	        					logger.error("玩家当前状态不能登录 预计等待时间 {} ms  session : {} from : {}",time,session,connection.getIP());
	        					throw new GameException(PromptType.LOGIN_STATE_ERROR, "玩家当前状态不能登录 预计等待时间 {}秒", time/1000);
	        				}
	        			}
	        		}
	        		*/
	        		
	        		GeneratedMessage message = (GeneratedMessage) processInfo.parseMethod.invoke(null, bytes);
	        		connection.setLastkeeplive(TimeUtil.GetDateTime());
	        		logger.info("process message:[{}]",messageType);
	        		long runTime = System.currentTimeMillis();
	        		long playerId = connection.getPlayerId();
	        		if (processInfo.processObject != null && processInfo.processMethod != null) {
	        			processInfo.processMethod.invoke(processInfo.processObject, connection,message);
	        		}
	        		else if (processInfo.process != null) {
	        			MessageProcess<?> process = processInfo.process;
	        			process.onProcess(connection,message);
	        		} else {
	        			throw new Exception("processInfo process Method is null");
	        		}
	        		runTime = System.currentTimeMillis() - runTime;
	        		//MethodStatitic.getInstance().record(messageType, runTime, playerId);
	    		}
	    		catch (InvocationTargetException ie) {
	    			if (ie.getTargetException() instanceof GameException)
	    			{
	    				GameException e = (GameException)ie.getTargetException();
		    			connection.ShowPrompt(e.getId(), e.getArgs());
	    			}
	    			else {
	    				Exception e = (Exception)ie.getTargetException();
	    				logger.error("InvocationTargetException :", e);
	    			}
	    		}catch (Exception e) {
	    			logger.error("process message [" + messageType + "] is error : ",e);
	    		}
	    	}
	    	//检测
	    	connection.CheckData();
	    	//发送一个回执空包 避免客户端收不到回执锁定发包限制
	    	/*
	    	if (msgId.equals(Common.BREAKING_MESSAGE_ID)) {
	    		connection.setMessageProcessing(false);
	    		
	    		if (!connection.isRecipePakageSend())
	    			connection.sendReceiptMessage();
	    	
	    	}
	    	
	    	if (!strMessages.equals(""))
	    		logger.info("recv message [0x{}] length [{}] form [{}] messages [{}]",Integer.toHexString(msgId.intValue()),buffer.limit(),connection.getIP(),strMessages);
	    	*/			
		}
	}
}
