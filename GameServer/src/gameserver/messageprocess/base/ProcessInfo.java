package gameserver.messageprocess.base;

import java.lang.reflect.Method;

import com.google.protobuf.GeneratedMessage;

public class ProcessInfo {
	public Class<? extends GeneratedMessage> messageType;		//消息类型
	public Object processObject;								//消息回调Obj
	public Method processMethod;								//消息回调函数
	public Method parseMethod;									//消息解析函数
	public MessageProcess<?> process;							//消息回调模版
	public boolean checkSession;								//是否检测session有效
	public ProcessInfo(final Class<? extends GeneratedMessage> messageType,
			final Object processObject,
			final Method processMethod,
			final Method parseMethod,
			MessageProcess<?> process,
			final boolean checkSession)
	{
		this.messageType = messageType;
		this.processObject = processObject;
		this.processMethod = processMethod;
		this.parseMethod = parseMethod;
		this.process = process;
		this.checkSession = checkSession;
	}
}
