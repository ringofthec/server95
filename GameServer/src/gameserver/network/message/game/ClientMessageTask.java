package gameserver.network.message.game;
import commonality.Common.TASK_TYPE;

import gameserver.network.IOServer;
import gameserver.network.protos.game.ProTask;
import gameserver.network.server.connection.Connection;
public class ClientMessageTask {
	private final static ClientMessageTask instance = new ClientMessageTask();
	public static ClientMessageTask getInstance() { return instance; }
	public void initialize()
    {
    	IOServer.getInstance().regMsgProcess(ProTask.Msg_C2G_CompleteTask.class, this, "OnCompleteTask");
    	IOServer.getInstance().regMsgProcess(ProTask.Msg_C2G_FinishAppShareTask.class, this, "OnGameShare");
    }
	public void OnCompleteTask(Connection connection,ProTask.Msg_C2G_CompleteTask message)
	{
		connection.getTasks().CompleteTask(message.getTaskId());
	}
	
  
     public void OnGameShare(Connection connection,ProTask.Msg_C2G_FinishAppShareTask message)
   {
	        connection.getTasks().AddTaskNumber(TASK_TYPE.GAME_SHARE, message.getType().getNumber(), 1, 0);
    }
}
