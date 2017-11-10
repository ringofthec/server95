package gameserver.messageprocess.base;
import gameserver.http.HttpProcessManager;
public class ManagementMessageBase extends MessageBase{
	@Override
	protected void regMsgProcess(MessageProcess<?> process) {
		HttpProcessManager.getInstance().regMsgProcess(process);
	}
}
