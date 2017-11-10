package gameserver.taskmanager;

import gameserver.thread.ThreadPoolManager;

import com.aionemu.commons.utils.concurrent.ExecuteWrapper;

/**
 * 使用FastList作为放包的容器的先进先出的执行队列
 * 实例化了执行方法
 */
public abstract class FIFORunnableQueue<T extends Runnable> extends FIFOSimpleExecutableQueue<T> {

    @Override
    protected final void removeAndExecuteFirst()
    {
    	// 注意，这里使用的ExecuteWrapper的同步执行模式，也就是说，这个调用会等待removeFirst()中的任务调用完成后才
    	// 返回，这里整个是单线程，同步阻塞的，就是我们想要的
        final T runnable = removeFirst();
        ExecuteWrapper.execute(runnable, ThreadPoolManager.MAXIMUM_RUNTIME_IN_MILLISEC_WITHOUT_WARNING);
    }
}
