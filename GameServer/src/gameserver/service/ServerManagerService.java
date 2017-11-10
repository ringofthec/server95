package gameserver.service;

import gameserver.http.HttpProcessManager;
import gameserver.network.protos.game.ProActive.Msg_S2G_Exit;
import gameserver.network.protos.game.ProHint.Msg_G2C_Prompt;
import gameserver.network.protos.game.ProHint.Msg_G2C_Prompt.PROMPT_SCENE;
import gameserver.network.server.connection.Connection;
import gameserver.network.server.connection.ConnectionManager;
import gameserver.thread.ThreadPoolManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import commonality.PromptType;

public class ServerManagerService {
	private final static Logger logger = LoggerFactory.getLogger(ServerManagerService.class);
	private static boolean inShutDown = false;
	
	private final static ServerManagerService instance = new ServerManagerService();
	public static ServerManagerService getInstance() {
		return instance;
	}
	
	public void init() {
		HttpProcessManager.getInstance().regMsgProcess(Msg_S2G_Exit.class,this, "onGameExit");
    }
	
	public void onGameExit(Msg_S2G_Exit message) throws Exception {
		final long shutdownTime = System.currentTimeMillis();
		final long delay = message.getSecondDelay() - shutdownTime;
		
		if (!inShutDown) {
			ThreadPoolManager.getInstance().schedule(new Runnable() {
				@Override
				public void run() {
					System.exit(0);
				}
			}, delay);

			ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
				int noticeCount = 0;
				@Override
				public void run() {
					long curDelay = (delay - (System.currentTimeMillis() - shutdownTime)) / 1000;
					noticeCount++;
					
					logger.info("server will shutdown after {} second..............", curDelay);
					if (curDelay <= 300) {
						if ((curDelay > 60 && noticeCount % 6 == 0) || curDelay < 60) {
							Msg_G2C_Prompt msg = Connection.buildPromptMsg(PromptType.SERVER_WILL_EXIT, 
									PROMPT_SCENE.NONE, null, curDelay / 60, curDelay % 60);
							ConnectionManager.GetInstance().broadcastMsg(msg);
						}
					}
				}
			}, 0, 10 * 1000);
		}
	}
}
