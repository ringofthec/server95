package gameserver.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.utils.concurrent.AionRejectedExecutionHandler;
import com.aionemu.commons.utils.concurrent.ExecuteWrapper;
import com.aionemu.commons.utils.concurrent.ScheduledFutureWrapper;
import com.commons.network.DisconnectionTask;
import com.commons.network.DisconnectionThreadPool;

/**
 * @author -Nemesiss-, NB4L1, MrPoke, lord_rex
 */
public class ThreadPoolManager implements DisconnectionThreadPool {
    private static final Logger log = LoggerFactory.getLogger(ThreadPoolManager.class);
    
    private static ThreadPoolManager _instance = new ThreadPoolManager();
    public static ThreadPoolManager getInstance() { return _instance; }

    public static final long MAXIMUM_RUNTIME_IN_MILLISEC_WITHOUT_WARNING = 5000;

    private static final long MAX_DELAY = TimeUnit.NANOSECONDS.toMillis(Long.MAX_VALUE - System.nanoTime()) / 2;

    private final ScheduledThreadPoolExecutor scheduledPool;
    private final ThreadPoolExecutor instantPool;
    private final ThreadPoolExecutor longRunningPool;
    
    private boolean shutdownFlag = false;

    /**
     * Disconnection scheduled threads pool
     */
    private final ScheduledThreadPoolExecutor disconnectionScheduledThreadPool;

    /**
     * Constructor.
     */
    public ThreadPoolManager() {
        final int instantPoolSize = Runtime.getRuntime().availableProcessors() * 2 + 2;

        scheduledPool = new ScheduledThreadPoolExecutor(10);
        scheduledPool.setRejectedExecutionHandler(new AionRejectedExecutionHandler());
        scheduledPool.prestartAllCoreThreads();

        instantPool = new ThreadPoolExecutor(instantPoolSize, instantPoolSize, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(100000));
        instantPool.setRejectedExecutionHandler(new AionRejectedExecutionHandler());
        instantPool.prestartAllCoreThreads();

        longRunningPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        longRunningPool.setRejectedExecutionHandler(new AionRejectedExecutionHandler());
        longRunningPool.prestartAllCoreThreads();

        disconnectionScheduledThreadPool = new ScheduledThreadPoolExecutor(4);
        disconnectionScheduledThreadPool.setRejectedExecutionHandler(new AionRejectedExecutionHandler());
        disconnectionScheduledThreadPool.prestartAllCoreThreads();

//        scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                purge();
//            }
//        }, 60000, 60000);

        log.info("ThreadPoolManager: Initialized with " + scheduledPool.getPoolSize() + " scheduler, "
                + instantPool.getPoolSize() + " instant, " + longRunningPool.getPoolSize() + " long, "
                + disconnectionScheduledThreadPool.getPoolSize() + " disconnection running thread(s).");
    }

    private final long validate(long delay) {
        return Math.max(0, Math.min(MAX_DELAY, delay));
    }

    private static final class ThreadPoolExecuteWrapper extends ExecuteWrapper {
        private ThreadPoolExecuteWrapper(Runnable runnable) {
            super(runnable);
        }

        @Override
        protected long getMaximumRuntimeInMillisecWithoutWarning() {
            return 5000;
        }
    }

    // ===========================================================================================

    public ScheduledFuture<?> schedule(Runnable r, long delay) {
        r = new ThreadPoolExecuteWrapper(r);
        delay = validate(delay);

        return new ScheduledFutureWrapper(scheduledPool.schedule(r, delay, TimeUnit.MILLISECONDS));
    }

    // ===========================================================================================

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable r, long delay, long period) {
        r = new ThreadPoolExecuteWrapper(r);
        delay = validate(delay);
        period = validate(period);

        return new ScheduledFutureWrapper(scheduledPool.scheduleAtFixedRate(r, delay, period, TimeUnit.MILLISECONDS));
    }

    // ===========================================================================================

    public void execute(Runnable r) {
        r = new ThreadPoolExecuteWrapper(r);

        instantPool.execute(r);
    }

    public void executeLongRunning(Runnable r) {
        r = new ExecuteWrapper(r);

        longRunningPool.execute(r);
    }

    // ===========================================================================================

    public Future<?> submit(Runnable r) {
        r = new ThreadPoolExecuteWrapper(r);

        return instantPool.submit(r);
    }

    public Future<?> submitLongRunning(Runnable r) {
        r = new ExecuteWrapper(r);

        return longRunningPool.submit(r);
    }

    // ===========================================================================================

    /**
     * Executes a loginServer packet task
     *
     * @param pkt runnable packet for Login Server
     */
    public void executeOtherServerPacket(Runnable pkt) {
        execute(pkt);
    }
    
    /*
      * (non-Javadoc)
      * @see com.aionemu.commons.network.DisconnectionThreadPool#waitForDisconnectionTasks()
      */

    @Override
	public void scheduleDisconnection(DisconnectionTask dt, long delay) {
    	schedule(dt, delay);
	}

	@Override
    public void waitForDisconnectionTasks() {
        try {
            disconnectionScheduledThreadPool.shutdown();
            disconnectionScheduledThreadPool.awaitTermination(6, TimeUnit.MINUTES);
        }
        catch (Exception e) {
        }
    }

    public void purge() {
        scheduledPool.purge();
        instantPool.purge();
        longRunningPool.purge();
        disconnectionScheduledThreadPool.purge();
    }

    /**
     * Shutdown all thread pools.
     */
    public void shutdown() {
    	shutdownFlag = true;
        final long begin = System.currentTimeMillis();

        log.info("ThreadPoolManager: Shutting down.");
        log.info("\t... executing " + getTaskCount(scheduledPool) + " scheduled tasks.");
        log.info("\t... executing " + getTaskCount(instantPool) + " instant tasks.");
        log.info("\t... executing " + getTaskCount(longRunningPool) + " long running tasks.");

        scheduledPool.shutdown();
        instantPool.shutdown();
        longRunningPool.shutdown();

        boolean success = false;
        try {
            success |= awaitTermination(5000);

            scheduledPool.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
            scheduledPool.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);

            success |= awaitTermination(10000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("\t... success: " + success + " in " + (System.currentTimeMillis() - begin) + " msec.");
        log.info("\t... " + getTaskCount(scheduledPool) + " scheduled tasks left.");
        log.info("\t... " + getTaskCount(instantPool) + " instant tasks left.");
        log.info("\t... " + getTaskCount(longRunningPool) + " long running tasks left.");
    }

    private int getTaskCount(ThreadPoolExecutor tp) {
        return tp.getQueue().size() + tp.getActiveCount();
    }

    public List<String> getStats() {
        List<String> list = new ArrayList<String>();

        list.add("");
        list.add("Scheduled pool:");
        list.add("=================================================");
        list.add("\tgetActiveCount: ...... " + scheduledPool.getActiveCount());
        list.add("\tgetCorePoolSize: ..... " + scheduledPool.getCorePoolSize());
        list.add("\tgetPoolSize: ......... " + scheduledPool.getPoolSize());
        list.add("\tgetLargestPoolSize: .. " + scheduledPool.getLargestPoolSize());
        list.add("\tgetMaximumPoolSize: .. " + scheduledPool.getMaximumPoolSize());
        list.add("\tgetCompletedTaskCount: " + scheduledPool.getCompletedTaskCount());
        list.add("\tgetQueuedTaskCount: .. " + scheduledPool.getQueue().size());
        list.add("\tgetTaskCount: ........ " + scheduledPool.getTaskCount());
        list.add("");
        list.add("Instant pool:");
        list.add("=================================================");
        list.add("\tgetActiveCount: ...... " + instantPool.getActiveCount());
        list.add("\tgetCorePoolSize: ..... " + instantPool.getCorePoolSize());
        list.add("\tgetPoolSize: ......... " + instantPool.getPoolSize());
        list.add("\tgetLargestPoolSize: .. " + instantPool.getLargestPoolSize());
        list.add("\tgetMaximumPoolSize: .. " + instantPool.getMaximumPoolSize());
        list.add("\tgetCompletedTaskCount: " + instantPool.getCompletedTaskCount());
        list.add("\tgetQueuedTaskCount: .. " + instantPool.getQueue().size());
        list.add("\tgetTaskCount: ........ " + instantPool.getTaskCount());
        list.add("");
        list.add("Long running pool:");
        list.add("=================================================");
        list.add("\tgetActiveCount: ...... " + longRunningPool.getActiveCount());
        list.add("\tgetCorePoolSize: ..... " + longRunningPool.getCorePoolSize());
        list.add("\tgetPoolSize: ......... " + longRunningPool.getPoolSize());
        list.add("\tgetLargestPoolSize: .. " + longRunningPool.getLargestPoolSize());
        list.add("\tgetMaximumPoolSize: .. " + longRunningPool.getMaximumPoolSize());
        list.add("\tgetCompletedTaskCount: " + longRunningPool.getCompletedTaskCount());
        list.add("\tgetQueuedTaskCount: .. " + longRunningPool.getQueue().size());
        list.add("\tgetTaskCount: ........ " + longRunningPool.getTaskCount());
        list.add("");
        list.add("");
        list.add("Disconnection running pool:");
        list.add("=================================================");
        list.add("\tgetActiveCount: ...... " + disconnectionScheduledThreadPool.getActiveCount());
        list.add("\tgetCorePoolSize: ..... " + disconnectionScheduledThreadPool.getCorePoolSize());
        list.add("\tgetPoolSize: ......... " + disconnectionScheduledThreadPool.getPoolSize());
        list.add("\tgetLargestPoolSize: .. " + disconnectionScheduledThreadPool.getLargestPoolSize());
        list.add("\tgetMaximumPoolSize: .. " + disconnectionScheduledThreadPool.getMaximumPoolSize());
        list.add("\tgetCompletedTaskCount: " + disconnectionScheduledThreadPool.getCompletedTaskCount());
        list.add("\tgetQueuedTaskCount: .. " + disconnectionScheduledThreadPool.getQueue().size());
        list.add("\tgetTaskCount: ........ " + disconnectionScheduledThreadPool.getTaskCount());
        list.add("");

        return list;
    }

    private boolean awaitTermination(long timeoutInMillisec) throws InterruptedException {
        final long begin = System.currentTimeMillis();

        while (System.currentTimeMillis() - begin < timeoutInMillisec) {
            if (!scheduledPool.awaitTermination(10, TimeUnit.MILLISECONDS) && scheduledPool.getActiveCount() > 0)
                continue;

            if (!instantPool.awaitTermination(10, TimeUnit.MILLISECONDS) && instantPool.getActiveCount() > 0)
                continue;

            if (!longRunningPool.awaitTermination(10, TimeUnit.MILLISECONDS) && longRunningPool.getActiveCount() > 0)
                continue;

            return true;
		}

		return false;
	}
    
    public boolean isShutdown() {
    	return shutdownFlag;
    }
}
