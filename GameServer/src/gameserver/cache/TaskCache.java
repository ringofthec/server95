package gameserver.cache;

import gameserver.connection.attribute.info.TaskInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.commons.cache.CacheManager;
import com.commons.util.CacheMapUtil;
import com.commons.util.CacheUtil;

import database.DatabaseTask;

public class TaskCache {

	public static final String TASK_CACHE = "_task_data_cache_";
	/** 每次启动游戏时调用 将key存入缓存 方便下线时做 过期处理 */
	public static void init() {
		CacheUtil.add(CacheManager.KEYS_NAME, TaskCache.class.getName()
				+ TASK_CACHE);
	}

	public static void addTask(long pid, DatabaseTask task1) {
		String key = TaskCache.class.getName() + TASK_CACHE + pid;
		HashMap<Object, Object> allTask = getAllTask(pid);
		if(allTask==null)
			allTask=new HashMap<Object, Object>();
		DatabaseTask task=new DatabaseTask();
		task.set(task1);
		allTask.put(task.task_id, task);
		CacheMapUtil.set(key, allTask);
	}

	/**集合中数据必须是处理过后的*/
	public static void setAllTask(long pid, HashMap<Object, Object> tasks) {
		String key = TaskCache.class.getName() + TASK_CACHE + pid;
		CacheMapUtil.set(key, tasks);
	}

	/**获取所有任务 集合*/
	public static HashMap<Object, Object> getAllTask(long pid) {
		String key = TaskCache.class.getName() + TASK_CACHE + pid;
		return CacheMapUtil.get(key);
	}

	/**获取指定sid的任务数据*/
	public static DatabaseTask getTask(long pid, long task_id) {
		HashMap<Object, Object> allTask = getAllTask(pid);
		if (allTask == null || allTask.size() == 0)
			return null;
		return (DatabaseTask) allTask.get(task_id);
	}

	/**更新指定的任务*/
	public static void updateTask(long pid, DatabaseTask task1) {
		String key = TaskCache.class.getName() + TASK_CACHE + pid;
		HashMap<Object, Object> allTask = getAllTask(pid);
		if(allTask==null)
			allTask=new HashMap<Object, Object>();
		DatabaseTask task=new DatabaseTask();
		task.set(task1);
		allTask.put(task.task_id, task);
		CacheMapUtil.set(key, allTask);
	}
	/** 是否需要把数据全部重新添加一次 */
	public static boolean isNeedAllIncr(long pid) {
		if (getAllTask(pid) == null)
			return true;
		return false;
	}

	public static void setAllTask(Long player_id,
			Map<Long, TaskInfo> m_TaskArray) {
		Set<Long>  keys=m_TaskArray.keySet();
		HashMap<Object, Object> tasks= new HashMap<Object, Object>();
		for(long key:keys)
		{
			TaskInfo info=m_TaskArray.get(key);
			DatabaseTask task=new DatabaseTask();
			task.set(info.GetTask());
			tasks.put(key, task);
		}
		setAllTask(player_id, tasks);
	}
}
