package gameserver.messageprocess.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import com.google.protobuf.GeneratedMessage;
import gameserver.network.server.connection.Connection;

@SuppressWarnings("unchecked")
public abstract class MessageProcess<T extends GeneratedMessage> {

	public Class<? extends GeneratedMessage> getMessageType()
	{
		Type genType = getClass().getGenericSuperclass();  
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
        return (Class<? extends GeneratedMessage>) params[0];
	}
	public void onProcess(Connection connection,GeneratedMessage message)
	{
		onProcess_impl(connection,(T)message);
	}
	protected abstract void onProcess_impl(Connection connection,T message);
}
