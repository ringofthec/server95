package gameserver.time;


import gameserver.event.GameEventDispatcher;
import gameserver.thread.ThreadPoolManager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameTimeService {
	
	public static final Logger logger = LoggerFactory.getLogger(GameTimeService.class);
	
	private final static GameTimeService instance = new GameTimeService();
    public final static GameTimeService getInstance() { return instance; }

    private GameTime gameTime = GameTime.getInstance();
	private final static long TIME_UPDATE_TICK = 500;  //单位毫秒
    private final List<String> events = new ArrayList<String>();
    private ThreadPoolManager threadPoolManager;
    private GameEventDispatcher dispatcher;

    public void init() {
    	threadPoolManager = ThreadPoolManager.getInstance();
    	dispatcher = GameEventDispatcher.getInstance();
    	startClock();
    }

    private boolean clockStarted = false;
    private void startClock() {
        if (clockStarted) {
            throw new IllegalStateException("Clock is already started");
        }

		logger.info("GameTimeService started. Time update tick: " + TIME_UPDATE_TICK);

        // 每 1 分钟更新一次 GameTime
        threadPoolManager.scheduleAtFixedRate(new Runnable(){
            @Override
            public void run() {
                // 重复利用这个 events 集合对象
                events.clear();
                gameTime.update(events);

                for(String eventName: events) {
                    dispatcher.triggerEvent(eventName);
                }
            }
        }, 0, TIME_UPDATE_TICK);
        
        clockStarted = true;
    }
}