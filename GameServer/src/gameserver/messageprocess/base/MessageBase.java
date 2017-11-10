package gameserver.messageprocess.base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MessageBase {
	private final Logger logger = LoggerFactory.getLogger(MessageBase.class);
	public void initialize()
	{
		Class<?>[] messages = getClass().getDeclaredClasses();
		for (Class<?> clazz : messages)
		{
			if (!clazz.isInterface() && MessageProcess.class.isAssignableFrom(clazz))
			{
				try {
					regMsgProcess((MessageProcess<?>)clazz.newInstance());
				} catch (Exception e)  {
					logger.error("{} initialize is error : {}", getClass().getName(), e);
				}
			}
		}
		initialize_impl();
	}
	protected abstract void regMsgProcess(MessageProcess<?> process);
	protected void initialize_impl()
	{
		
	}
}
