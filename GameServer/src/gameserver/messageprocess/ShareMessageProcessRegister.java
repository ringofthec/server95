package gameserver.messageprocess;
import gameserver.messageprocess.base.IMessageProcessRegister;
import gameserver.messageprocess.base.MessageProcess;
import gameserver.messageprocess.base.ProcessInfo;
import gameserver.network.server.connection.Connection;
import gameserver.utils.Util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.GeneratedMessage;
import commonality.SHARE_SERVER_TYPE;

public class ShareMessageProcessRegister extends IMessageProcessRegister {
	private final Logger logger = LoggerFactory.getLogger(ShareMessageProcessRegister.class);
	@Override
	protected Class<?>[] getClasses(Class<? extends GeneratedMessage> messageType) {
		return new Class<?>[] {Connection.class,SHARE_SERVER_TYPE.class,Integer.TYPE,messageType};
	}
	public List<GeneratedMessage> invokeMessage(Connection connection,SHARE_SERVER_TYPE type,String id,ByteBuffer buffer)  {
		buffer.getShort();					//MsgID
		Util.ReadString(buffer);			//Session
    	Short msgNum = buffer.getShort();	//Msg Number
    	List<GeneratedMessage> messages = new ArrayList<GeneratedMessage>();
    	for (Short i = 0;i < msgNum; ++i) {
    		String messageType = Util.ReadString(buffer);
    		Short messageLength = buffer.getShort();
    		byte[] bytes = new byte[messageLength];
    		buffer.get(bytes);
    		try {
        		ProcessInfo processInfo = getMsgProcess(messageType);
        		if (processInfo == null) {
        			logger.warn("ProcessInfo is null [{}]",messageType);
        			continue;
        		}
        		GeneratedMessage message = (GeneratedMessage) processInfo.parseMethod.invoke(null, bytes);
        		messages.add(message);
        		logger.info("process message:[{}]",messageType);
        		if (processInfo.processObject != null) {
        			if (processInfo.processMethod != null)
        				processInfo.processMethod.invoke(processInfo.processObject,connection,type,Integer.parseInt(id),message);
        		}
        		else if (processInfo.process != null) {
        			MessageProcess<?> process = processInfo.process;
        			process.onProcess(connection,message);
        		} else {
        			throw new Exception("processInfo process Method is null");
        		}
    		}
    		catch (Exception e) {
    			logger.error("process message [" + messageType + "] is error : ",e);
    		}
    	}
    	return messages;
	}
}
