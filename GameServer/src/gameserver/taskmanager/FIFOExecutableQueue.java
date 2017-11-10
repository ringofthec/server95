/**
 * This file is part of Aion Core <aioncore.com>
 *
 *  This is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This software is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser Public License
 *  along with this software.  If not, see <http://www.gnu.org/licenses/>.
 */
package gameserver.taskmanager;

import gameserver.thread.ThreadPoolManager;

import java.util.concurrent.locks.ReentrantLock;

/**
 *  FIFO 顾名思义，先进先出的执行队列
 *  通过这么一套状态切换判断的流程，保证了一个队列中的包，肯定是先到的包被先处理掉完成后才开始处理下一个包
 *  注意，本类只提供一种一次处理包的机制，但是本身包的容器和执行包的逻辑是暴露的
 */
public abstract class FIFOExecutableQueue implements Runnable {

    private static final byte NONE = 0;     // 闲置状态, 可以处理包
    private static final byte QUEUED = 1;   // 待处理状态, 正在线程池中等待被处理
    private static final byte RUNNING = 2;  // 处理包状态, 包正在被处理中

    // 状态切换，保证多线程下一致性的锁
    private final ReentrantLock lock = new ReentrantLock();

    // 注意 volatile 标识出这个变量是被多线程修改的，防止jvm的优化
    private volatile byte state = NONE;
    private ThreadPoolManager threadPoolManager;

    public final void lock() {
        lock.lock();
    }

    public final void unlock() {
        lock.unlock();
    }

    public void setThreadPoolManager(ThreadPoolManager threadPoolManager) {
        this.threadPoolManager = threadPoolManager;
    }

    protected final void execute()
    {
        // 1. 获得资格
        lock();
        try
        {
        	// 只有状态是 NONE的情况下，才能处理包
            if (state != NONE)
                return;

            // 切换到 待处理状态
            state = QUEUED;
        }
        finally {
            unlock();
        }

        // 2. 放入线程池待操作
        threadPoolManager.execute(this);
    }

    // 真正的执行流程
    public final void run()
    {
        try
        {
        	// 可能有很多包都在等待处理
            while (!isEmpty())
            {
            	// 切换到 运行状态
                setState(QUEUED, RUNNING);

                try
                {
                    while (!isEmpty())
                    {
                    	removeAndExecuteFirst();
                    }
                }
                finally
                {
                	// 一个包处理完后 切回待处理状态
                    setState(RUNNING, QUEUED);
                }
            }
        }
        finally
        {
        	// 包全部都处理完了，切回 NONE状态，这时候有可以开始处理新的包
            setState(QUEUED, NONE);
        }
    }

    private void setState(byte expected, byte value)
    {
        lock();
        try
        {
            if (state != expected)
            {
            	throw new IllegalStateException("state: " + state + ", expected: " + expected);
            }
        }
        finally
        {
            state = value;
            unlock();
        }
    }

    protected abstract boolean isEmpty();

    protected abstract void removeAndExecuteFirst();
}
