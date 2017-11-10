package gameserver.connection.attribute;

import gameserver.cache.TaskCache;
import gameserver.connection.attribute.info.TaskInfo;
import gameserver.network.protos.game.ProBuild.Proto_UpdateState;
import gameserver.network.protos.game.ProTask.Msg_G2C_FinishedTask;
import gameserver.network.protos.game.ProTask;
import gameserver.network.server.connection.ConnectionAttribute;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_Task;
import table.MT_Data_TaskLimit;
import table.base.TABLE;
import table.base.TableManager;

import com.commons.util.DatabaseInsertUpdateResult;
import com.commons.util.TimeUtil;

import commonality.Common;
import commonality.PromptType;
import database.DatabaseTask;

public class ConTaskAttr extends ConnectionAttribute {
	private static final Logger logger = LoggerFactory
			.getLogger(ConTaskAttr.class);
	private Map<Long, TaskInfo> m_TaskArray = new HashMap<Long, TaskInfo>();

	@Override
	protected void Initialize_impl() {
		m_TaskArray.clear();
		long pid = m_Con.getPlayerId();
		boolean isLoadDb = false;
		HashMap<Object, Object> allTask = TaskCache.getAllTask(pid);
		if (allTask == null) {
			isLoadDb = true;
			allTask = new HashMap<Object, Object>();
		}

		if (isLoadDb) {
			List<DatabaseTask> tasks = getDb().Select(DatabaseTask.class,
					"player_id = ?", pid);
			for (DatabaseTask task : tasks) {
				TaskInfo info = new TaskInfo(m_Con, this, task);
				int linkID = info.GetTaskLinkID();
				if (linkID == -1) {
					logger.error("Initialize1, 任务ID:{} tableid:{} player_id:{} 找不到对应的任务链ID", task.task_id, task.table_id, m_Con.getPlayerId());
					continue;
				}
				DatabaseTask cacheTask = new DatabaseTask();
				cacheTask.set(task);
				allTask.put(task.task_id, cacheTask);
				m_TaskArray.put(task.task_id, info);
			}
		} else {
			Set<Object> keys = allTask.keySet();
			for (Object key : keys) {
				DatabaseTask task = (DatabaseTask) allTask.get(key);
				task.sync();
				task.setDatabaseSimple(getDb().getM_Simple());
				TaskInfo info = new TaskInfo(m_Con, this, task);
				int linkID = info.GetTaskLinkID();
				if (linkID == -1) {
					logger.error("Initialize2, 任务ID:{} tableid:{} player_id:{} 找不到对应的任务链ID", task.task_id, task.table_id, m_Con.getPlayerId());
					continue;
				}
				m_TaskArray.put(task.task_id, info);
			}
		}
		TaskCache.setAllTask(pid, allTask);
	}

	private ProTask.Proto_TaskInfo GetProtoData(TaskInfo info) {
		ProTask.Proto_TaskInfo.Builder builder = ProTask.Proto_TaskInfo
				.newBuilder();
		DatabaseTask data = info.GetTask();
		builder.setTaskId(data.task_id);
		builder.setTableId(data.table_id);
		builder.setNumber(data.number);
		builder.setComplete(data.complete);
		builder.setFinished(data.finished);
		return builder.build();
	}

	public void SendDataArray() {
		ProTask.Msg_G2C_TaskInfo.Builder message = ProTask.Msg_G2C_TaskInfo
				.newBuilder();
		for (Entry<?, TaskInfo> pair : m_TaskArray.entrySet()) {
			TaskInfo info = pair.getValue();
			if (info == null || info.IsFinshed())
				continue;
			message.addInfo(GetProtoData(info));
		}
		message.setType(Proto_UpdateState.LIST);
		m_Con.sendPushMessage(message.build());
	}

	@Override
	public void CheckData() {
		if (m_NeedUpdate.size() <= 0)
			return;
		try {
			ProTask.Msg_G2C_TaskInfo.Builder message = ProTask.Msg_G2C_TaskInfo
					.newBuilder();
			for (Long id : m_NeedUpdate) {
				TaskInfo info = GetTask(id);
				if (info == null)
					continue;
				message.addInfo(GetProtoData(info));
			}
			message.setType(Proto_UpdateState.UPDATE);
			m_Con.sendPushMessage(message.build());
		} catch (Exception e) {
			logger.error("CheckData is error : ", e);
		}
		m_NeedUpdate.clear();
	}

	public void onNewDay() {
		CheckComplete();
		CheckCanAcceptTask();
	}

	public void SendFinishedArray() {
		Msg_G2C_FinishedTask.Builder builder = Msg_G2C_FinishedTask
				.newBuilder();
		for (Map.Entry<Long, TaskInfo> task : m_TaskArray.entrySet()) {
			if (task.getValue().IsFinshed())
				builder.addFinishedTask(task.getValue().GetTaskLinkID());
		}
		m_Con.sendPushMessage(builder.build());
	}

	/** 某个指定任务ID是否已完成 */
	private boolean TaskIsComplete(int task_table_id) {
		Collection<TaskInfo> tasks = m_TaskArray.values();
		for (TaskInfo info : tasks) {
			if (info.TaskIsComplete(task_table_id))
				return true;
		}
		return false;
	}

	/** 检测可接任务 */
	public void CheckCanAcceptTask() {
		Set<Integer> linkArray = TableManager.GetInstance().getTasks().keySet();
		for (Integer link : linkArray) {
			TaskInfo info = GetTaskByLinkID(link);
			if (info == null) {
				if (!TableManager.GetInstance().TableTask().Contains(link)) {
					logger.error("CheckCanAcceptTask, task_table_id={},playerid={}", link, m_Con.getPlayerId());
					continue;
				}
				
				MT_Data_Task taskData = TableManager.GetInstance().TableTask()
						.GetElement(link);
				if (taskData != null && CheckTaskLimit(taskData.Limit())) {
					    InsertTask(link);
				}
			} else if (info.GetTask().finished == true
					&& checkTaskComplete(info)
					&& CheckTaskLimit(info.GetTaskLinkData().Limit())) {
				info.Reset();
			}
		}
	}

	private boolean checkTaskComplete(TaskInfo info) {
		Integer max = info.GetTaskLinkData().loop();
		if (TABLE.IsInvalid(max))
			return false;

		if (max <= 0)
			return false;

		if (!m_Con.getPlayer().testTaskLimit(info.GetTaskLinkID(), max))
			return false;

		return true;
	}

	/** 检测任务条件是否达到 */
	private boolean CheckTaskLimit(int id) {
		if (TABLE.IsInvalid(id))
			return false;
		MT_Data_TaskLimit limit = TableManager.GetInstance().TableTaskLimit()
				.GetElement(id);
		if (limit == null)
			return false;
		if (!TABLE.IsInvalid(limit.PlayerLevel())
				&& !m_Con.getPlayer().CheckPlayerLevel(limit.PlayerLevel(),
						false))
			return false;
		for (Integer task : limit.PreTask()) {
			if (!TaskIsComplete(task))
				return false;
		}
		return true;
	}

	/** 添加任务数量 */
	public void AddTaskNumber(Common.TASK_TYPE type, int id, int number, int arg) {
		Collection<TaskInfo> tasks = m_TaskArray.values();
		for (TaskInfo info : tasks) {
			info.AddTaskNumber(type, id, number, arg);
		}
	}

	/** 完成某个任务 客户端主动操作 */
	public boolean CompleteTask(long task_id) {
		if (m_TaskArray.containsKey(task_id)) {
			if (m_TaskArray.get(task_id).CompleteTask()) {
				CheckCanAcceptTask();
				m_Con.ShowPrompt(PromptType.TASK_COMPLETE);
				return true;
			}
		} else {
			logger.error("没有找到对应的任务  taskid={},playerid={}", task_id,m_Con.getPlayerId());
		}
		return false;
	}

	private void InsertTask(int task_table_id) {
		DatabaseTask task = new DatabaseTask();
		task.table_id = task_table_id;
		task.number = 0;
		task.complete = false;
		task.finished = false;
		task.accept_time = TimeUtil.GetDateTime();
		task.player_id = m_Con.getPlayerId();
		DatabaseInsertUpdateResult result = getDb().Insert(task);
		task.task_id = result.identity;
		TaskInfo info = new TaskInfo(m_Con, this, task);
		int linkID = info.GetTaskLinkID();
		if (linkID == -1) {
			logger.error("InsertTask, 任务ID:{} tableid:{} player_id:{} 找不到对应的任务链ID", task.task_id, task.table_id, m_Con.getPlayerId());
			return;
		}
		InsertNeedUpdate(task.task_id.intValue());
		m_TaskArray.put(task.task_id, info);
		addCache(task);
	}

	public void addCache(DatabaseTask task) {
		if (TaskCache.isNeedAllIncr(task.player_id))
			TaskCache.setAllTask(task.player_id, m_TaskArray);
		else
			TaskCache.addTask(task.player_id, task);
	}

	public TaskInfo GetTask(long task_id) {
		if (m_TaskArray.containsKey(task_id))
			return m_TaskArray.get(task_id);
		return null;
	}

	/** 检测任务是否完成 */
	public void CheckComplete() {
		Collection<TaskInfo> tasks = m_TaskArray.values();
		for (TaskInfo info : tasks) {
			info.CheckComplete();
		}
	}

	/** 根据任务链ID获得任务 */
	public TaskInfo GetTaskByLinkID(int link_id) {
		Collection<TaskInfo> tasks = m_TaskArray.values();
		for (TaskInfo info : tasks) {
			if (info.GetTaskLinkID() == link_id)
				return info;
		}
		return null;
	}
}
