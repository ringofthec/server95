package gameserver.messageprocess.base;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.util.string;
import com.google.protobuf.GeneratedMessage;
public class IMessageProcessRegister {
	private final Logger logger = LoggerFactory.getLogger(IMessageProcessRegister.class);
	protected HashMap<String,ProcessInfo> msgProcessInfo = new HashMap<>();
	protected HashMap<Integer,ProcessInfo> msgIntProcessInfo = new HashMap<>();
	protected Class<?>[] getClasses(Class<? extends GeneratedMessage> messageType)
	{
		return new Class<?>[]{messageType};
	}
	public void regMsgProcess(MessageProcess<?> process,boolean checkSession)
	{
		regMsgProcess_impl(process.getMessageType(),null,null,process,checkSession, 0);
	}
    public void regMsgProcess(Class<? extends GeneratedMessage> messageType,Object processObject,String processMethod,boolean checkSession)
    {
    	regMsgProcess_impl(messageType,processObject,processMethod,null,checkSession, 0);
    }
    public void regMsgProcess(Class<? extends GeneratedMessage> messageType,Object processObject,String processMethod,boolean checkSession, int msgId)
    {
    	regMsgProcess_impl(messageType,processObject,processMethod,null,checkSession, msgId);
    }
    private void regMsgProcess_impl(Class<? extends GeneratedMessage> messageType,Object processObject,String processMethod,MessageProcess<?> process,boolean checkSession, int msgid)
    {
    	try {
    		if (messageType == null)
    			throw new Exception("messageType == null");
    		String msgId = messageType.getSimpleName();
        	if (msgProcessInfo.containsKey(msgId))
        		throw new Exception("msgId is exist msgid : " + msgId);
        	Method proMethod = null;
        	if (processObject != null && !string.IsNullOrEmpty(processMethod))
        	{
        		proMethod = processObject.getClass().getMethod(processMethod, getClasses(messageType));
    			if (proMethod == null)
    				throw new Exception("method is not found  object : " + processObject.getClass().getName() + "  method : " + processMethod);       		
        	}
        	Method parseMethod = messageType.getMethod("parseFrom", byte[].class);
			msgProcessInfo.put(msgId, new ProcessInfo(messageType, processObject, proMethod, parseMethod, process, checkSession));
			msgIntProcessInfo.put(msgid, new ProcessInfo(messageType, processObject, proMethod, parseMethod, process, checkSession));
		} catch (Exception e) {
			logger.error("regMsgProcess is error ",e);
		}
    }
	public void unregMsgProcess(Class<? extends GeneratedMessage> messageType)
	{
		if (messageType == null)
			return;
		unregMsgProcess(messageType.getSimpleName());
	}
	public void unregMsgProcess(String messageType)
	{
		if (string.IsNullOrEmpty(messageType))
			return;
		msgProcessInfo.remove(messageType);
	}
	public void unregAllMsgProcess()
	{
		msgProcessInfo.clear();
	}
	public ProcessInfo getMsgProcess(Class<? extends GeneratedMessage> messageType)
	{
		if (messageType == null)
			return null;
		return getMsgProcess(messageType.getSimpleName());
	}
	public ProcessInfo getMsgProcess(String messageType)
	{
		if (string.IsNullOrEmpty(messageType))
			return null;
		return msgProcessInfo.get(messageType);
	}
	public ProcessInfo getMsgProcess(int messageType)
	{
		return msgIntProcessInfo.get(messageType);
	}
}
