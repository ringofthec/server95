package gameserver.messageprocess;

import gameserver.messageprocess.base.IMessageProcessRegister;
import gameserver.messageprocess.base.MessageProcess;
import gameserver.messageprocess.base.ProcessInfo;
import gameserver.utils.Util;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.util.string;
import com.google.protobuf.GeneratedMessage;

public class HttpMessageProcessRegister extends IMessageProcessRegister {
	private final Logger logger = LoggerFactory.getLogger(HttpMessageProcessRegister.class);
	@Override
	protected Class<?>[] getClasses(Class<? extends GeneratedMessage> messageType) {
		return new Class<?>[]{messageType};
	}
	public String invokeMessage(ByteBuffer buffer)  {
		String messageType = Util.ReadString(buffer);
		Short messageLength = buffer.getShort();
		byte[] bytes = new byte[messageLength];
		buffer.get(bytes);
		try {
    		ProcessInfo processInfo = getMsgProcess(messageType);
    		if (processInfo == null) {
    			String errorMessage = "ProcessInfo is null";
    			logger.warn("{}  [{}]",errorMessage,messageType);
    			return string.Format("{}:{}  ", messageType,errorMessage);
    		}
    		GeneratedMessage message = (GeneratedMessage) processInfo.parseMethod.invoke(null, bytes);
    		Object retObject = null;
    		if (processInfo.processObject != null && processInfo.processMethod != null) {
    			retObject = processInfo.processMethod.invoke(processInfo.processObject,message);
    		}
    		else if (processInfo.process != null) {
    			MessageProcess<?> process = processInfo.process;
    			process.onProcess(null,message);
    		} else {
    			throw new Exception("processInfo process Method is null");
    		}
    		return string.Format("{}:{}  ",messageType,retObject);
		}
		catch (Exception e) {
			logger.error("process message [" + messageType + "] is error : ",e);
			return string.Format("{}:{}  ",messageType,e.getMessage());
		}
	}
}
