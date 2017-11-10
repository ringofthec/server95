package gameserver.messageprocess.base;
import gameserver.network.IOServer;
public class ClientMessageBase extends MessageBase {
	@Override
	protected void regMsgProcess(MessageProcess<?> process) {
		IOServer.getInstance().regMsgProcess(process);
	}
}
