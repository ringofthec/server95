package robot;

import gameserver.config.ServerConfig;
import gameserver.thread.ThreadPoolManager;

import java.io.IOException;
import java.util.ArrayList;

import com.commons.util.RandomUtil;

public class RobotManager {
	private static RobotManager instance = new RobotManager();
	public static RobotManager getInstace() {return instance; }
	
	public void beginRobeter(int thread, int num) throws IOException {
		
		int pre_thread_count =  num / thread;
		if (pre_thread_count == 0)
			pre_thread_count = 1;
		
		if (num == 1) {
			final RobotPlayer player = new RobotPlayer(1);
			ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					try{
						player.run();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}, 0, ServerConfig.ROBOT_INTERVAL);
		} else {
			int id = 1;
			for (int t = 0; t < thread; ++t) {
				final ArrayList<RobotPlayer> localplayers = new ArrayList<>();
				for (int i = 0; i < pre_thread_count; ++i)
					localplayers.add(new RobotPlayer(id++));

				ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
					@Override
					public void run() {
						try{
							RobotPlayer player = localplayers.get(RandomUtil.RangeRandom(0, localplayers.size() - 1));
							player.run();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}, 0, ServerConfig.ROBOT_INTERVAL);
			}
		}
	}
}
