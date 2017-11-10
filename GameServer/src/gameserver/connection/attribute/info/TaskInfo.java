package gameserver.connection.attribute.info;

import gameserver.connection.attribute.ConTaskAttr;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.server.connection.Connection;
import gameserver.stat.StatManger;
import gameserver.utils.Item_Channel;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int3;
import table.MT_Data_Task;
import table.base.TABLE;
import table.base.TableManager;

import com.commons.util.TimeUtil;

import commonality.Common.SETNUMBER_TYPE;
import commonality.Common.TASK_TYPE;
import commonality.ProductChannel;
import commonality.VmChannel;
import database.DatabaseTask;

public class TaskInfo {
	private static final Logger logger = LoggerFactory
			.getLogger(TaskInfo.class);
	private Connection m_Connection;
	private ConTaskAttr m_Attribute;
	private DatabaseTask m_Task;
	private MT_Data_Task m_LinkFirstTaskData; // 当前任务链的第一个任务的配置
	private MT_Data_Task m_TaskData; // 当前任务的配置
	private List<Integer> m_TaskLink; // 当前任务所在的任务链中全部任务id

	public TaskInfo(Connection connection, ConTaskAttr attribute,
			DatabaseTask task) {
		m_Connection = connection;
		m_Attribute = attribute;
		m_Task = task;
		m_Task.sync();
		m_TaskLink = TableManager.GetInstance().getTaskLink(m_Task.table_id);
		
		if (!TableManager.GetInstance().TableTask().Contains(m_Task.table_id)) {
			logger.error("TaskInfo1, task_table_id={},playerid={}", m_Task.table_id, m_Connection.getPlayerId());
		}
		
		if (!TableManager.GetInstance().TableTask().Contains(GetTaskLinkID())) {
			logger.error("TaskInfo2, task_table_id={},playerid={}", GetTaskLinkID(), m_Connection.getPlayerId());
		}
		
		m_TaskData = TableManager.GetInstance().TableTask()
				.GetElement(m_Task.table_id);
		m_LinkFirstTaskData = TableManager.GetInstance().TableTask()
				.GetElement(GetTaskLinkID());
		autoCheckTaskComplete();
	}

	public void Reset() {
		m_Task.table_id = GetTaskLinkID();
		m_Task.number = 0;
		m_Task.complete = false;
		m_Task.finished = false;
		m_Task.accept_time = TimeUtil.GetDateTime();
		Save();
	}

	// 获得任务链ID
	public int GetTaskLinkID() {
		if (m_TaskLink != null && m_TaskLink.size() > 0)
			return m_TaskLink.get(0);
		return -1;
	}

	public MT_Data_Task GetTaskLinkData() {
		return m_LinkFirstTaskData;
	}

	public final DatabaseTask GetTask() {
		return m_Task;
	}

	public MT_Data_Task GetData() {
		return m_TaskData;
	}

	public boolean AddTaskNumber(TASK_TYPE type, int id, int number, int arg) {
		if (IsFinshed())
			return false;
		if (m_Task.complete == true)
			return false;
		boolean change = false;
		TASK_TYPE taskType = TASK_TYPE.valueOf(m_TaskData.Type());
		if (taskType == type) {
			if (!IsRealNumber(type)) {
				if (type == TASK_TYPE.PVP) {
					// 无所谓胜负
					if (m_TaskData.TargetID() == 0) {
						change = true;
						++m_Task.number;

						// 必须胜利或失败
					} else if (m_TaskData.TargetID() == id) {
						change = true;
						++m_Task.number;
					}
				} else if (type == TASK_TYPE.OPEN_VIP) {
					change = true;
					++m_Task.number;
				} else if (type == TASK_TYPE.CLEAN_INSTANCE) {
					if ((TABLE.IsInvalid(m_TaskData.TargetID()) || id == m_TaskData
							.TargetID()) && arg >= m_TaskData.TargetArg1()) {
						change = true;
						++m_Task.number;
					}
				} else if (type == TASK_TYPE.AIRSUPPORT_OPERATE) {
					if ((id == m_TaskData.TargetID() || TABLE
							.IsInvalid(m_TaskData.TargetID()))
							&& arg == m_TaskData.TargetArg1()) {
						change = true;
						++m_Task.number;
					}
				} else if (type == TASK_TYPE.MEDAL_UPGRADE
						|| type == TASK_TYPE.MEDAL_UPSTAR) {
					if (TABLE.IsInvalid(m_TaskData.TargetID())
							|| id == m_TaskData.TargetID()) {
						change = true;
						++m_Task.number;
					}
				} else if (type == TASK_TYPE.SET_DEFENCE) {
					change = true;
					++m_Task.number;
				} else if (type == TASK_TYPE.GET_ONLINE_REWARD) {
					change = true;
					++m_Task.number;
				} else if (type == TASK_TYPE.SPEED_UP) {
					if (TABLE.IsInvalid(m_TaskData.TargetID())
							|| id == m_TaskData.TargetID()) {
						change = true;
						m_Task.number += number;
					}
				} else if (type == TASK_TYPE.FORCE_HERO) {
					change = true;
					++m_Task.number;
				} else if (type == TASK_TYPE.HERO_PATH) {
					change = true;
					++m_Task.number;
				} else if (type == TASK_TYPE.EQUIP_FORCE) {
					change = true;
					++m_Task.number;
				} else if (type == TASK_TYPE.EQUIP_SPLITE) {
					change = true;
					++m_Task.number;
				} else if (type == TASK_TYPE.DRAW_MEDAL) {
					change = true;
					++m_Task.number;
				} else if (type == TASK_TYPE.BUY_FROM_LEGION_STORE) {
					change = true;
					m_Task.number += number;
				} else if (type == TASK_TYPE.HELP_LEGION_MEMBER) {
					change = true;
					m_Task.number += number;
				} else if (type == TASK_TYPE.COMPLETE_TEACH) {
					if (m_TaskData.TargetID() == id) {
						change = true;
						m_Task.number += number;
					}
				}else if (type == TASK_TYPE.HERO_PATH_PASS) {
					if (id == m_TaskData.TargetID()) {
						change = true;
						++m_Task.number;
					}
				} else if (type == TASK_TYPE.WANTED_PASS) {
					if (id == 0) {
						change = true;
						++m_Task.number;
					} else if ((id-1) >= m_TaskData.TargetID()) {
						change = true;
						++m_Task.number;
					}
				}else if (id == m_TaskData.TargetID()) {
					change = true;
					m_Task.number += number;
				} 
			}

			if (IsComplete() == true) {
				change = true;
				m_Task.complete = true;
			}

			if (change)
				Save();
		}
		return change;
	}

	public boolean CheckComplete() {
		if (IsFinshed())
			return false;
		if (m_Task.complete == true)
			return false;
		if (!IsComplete() == true)
			return false;
		m_Task.complete = true;
		Save();
		return true;
	}

	/** 是否是时时取数据的任务类型 */
	public boolean IsRealNumber(TASK_TYPE type) {
		return (type == TASK_TYPE.BUILD || type == TASK_TYPE.TECH
				|| type == TASK_TYPE.INSTANCE || type == TASK_TYPE.RANK
				|| type == TASK_TYPE.GATHER_ITEM
				|| type == TASK_TYPE.GATHER_QUALITY_ITEM
				|| type == TASK_TYPE.MEDAL_TO_LEVEL
				|| type == TASK_TYPE.MEDAL_TO_STAR
				|| type == TASK_TYPE.GET_HERO
				|| type == TASK_TYPE.GET_HERO_NUMBER
				|| type == TASK_TYPE.JOIN_LEGION
				|| type == TASK_TYPE.PLAYER_LEVEL
				|| type == TASK_TYPE.BUILDING_NUMBER
				|| type == TASK_TYPE.SOLIDER_LEVEL
				|| type == TASK_TYPE.EXPAND_LAND || type == TASK_TYPE.VIP_LEVEL
				|| type == TASK_TYPE.GET_LOGIN_REWARD
				|| type == TASK_TYPE.GET_STAR_PVE_REWARD
				|| type == TASK_TYPE.GET_LEVEL_REWARD || type == TASK_TYPE.AIRSUPPORT_UP_LV);
	}

	public boolean IsComplete() {
		if (IsFinshed())
			return false;

		if (m_Task.complete)
			return true;

		TASK_TYPE type = TASK_TYPE.valueOf(m_TaskData.Type());
		if (type == TASK_TYPE.GATHER_ITEM)
			return m_Connection.getItem().getItemCountByTableId(
					m_TaskData.TargetID()) >= m_TaskData.TargetNumber();

		if (type == TASK_TYPE.GATHER_QUALITY_ITEM)
			return m_Connection.getItem().getItemCount(m_TaskData.TargetID(),
					m_TaskData.TargetArg1(), m_TaskData.TargetArg2()) >= m_TaskData
					.TargetNumber();
		if (type == TASK_TYPE.WANTED_PASS) {
			int wantedId = m_Connection.getPlayer().getRewardCurId();
			if (m_Task.number >= m_TaskData.TargetNumber()||wantedId==0)
				return true;
			return (m_TaskData.TargetID() < wantedId);
		}

		if (!IsRealNumber(type))
			return m_Task.number >= m_TaskData.TargetNumber();

		switch (type) {
		case BUILD:
			return m_Connection.getBuild().getBuildMaxLevel(
					m_TaskData.TargetID()) >= m_TaskData.TargetNumber();
		case TECH: {
			int techLevel = m_Connection.getTech().getTechLevel(
					m_TaskData.TargetID());
			if (m_Connection.getBuild().isTechIsUp(m_TaskData.TargetID()))
				return techLevel + 1 >= m_TaskData.TargetNumber();
			else
				return techLevel >= m_TaskData.TargetNumber();
		}
		case INSTANCE:
			return m_Connection.getPlayer().GetInstanceStar(
					m_TaskData.TargetID()) >= m_TaskData.TargetArg1();
		case RANK:
			return m_Connection.getPlayer().getRank() >= m_TaskData
					.TargetNumber();
		case MEDAL_TO_LEVEL:
			return m_Connection.getHero().GetMedalMaxLevel(
					m_TaskData.TargetID()) >= m_TaskData.TargetNumber();
		case MEDAL_TO_STAR:
			return m_Connection.getHero()
					.GetMedalMaxStar(m_TaskData.TargetID()) >= m_TaskData
					.TargetNumber();
		case GET_HERO:
			return m_Connection.getHero().isHaveHeroByTableId(
					m_TaskData.TargetID());
		case GET_HERO_NUMBER:
			return m_Connection.getHero().getHeroCount() >= m_TaskData
					.TargetNumber();
		case JOIN_LEGION:
			return m_Connection.getPlayer().IsInLegion();
		case PLAYER_LEVEL:
			return m_Connection.getPlayer().CheckPlayerLevel(
					m_TaskData.TargetNumber(), false);
		case BUILDING_NUMBER:
			return m_Connection.getBuild().getBuildNumber(
					m_TaskData.TargetID(),
					TABLE.IsInvalid(m_TaskData.TargetArg1()) ? 0 : m_TaskData
							.TargetArg1()) >= m_TaskData.TargetNumber();
		case SOLIDER_LEVEL: {
			int corpLevel = m_Connection.getCorps().getCorpsLevel(
					m_TaskData.TargetID());
			if (m_Connection.getBuild().isCorpIsUp(m_TaskData.TargetID()))
				return corpLevel + 1 >= m_TaskData.TargetNumber();
			else
				return corpLevel >= m_TaskData.TargetNumber();
		}
		case EXPAND_LAND:
			return m_Connection.getPlayer().getExtend() >= m_TaskData
					.TargetNumber();
		case VIP_LEVEL:
			return m_Connection.getPlayer().getVipLevel() >= m_TaskData
					.TargetNumber();
		case GET_LOGIN_REWARD: {
			return m_Connection.getPlayer().getContinueLoginReward() > 0;
		}
		case GET_LEVEL_REWARD: {
			List<Integer> ids = m_Connection.getPlayer()
					.getAllLevRewardTableIds();
			return ids.contains(m_TaskData.TargetID());
		}
		case GET_STAR_PVE_REWARD: {
			if (m_Connection.getPlayer().testPlayerAwardType(
					m_TaskData.TargetID() - 1, m_TaskData.TargetArg1()))
				return true;
			return false;
		}
		case AIRSUPPORT_UP_LV: {
			// 参数二配置低是TABLEID
			int lv = 0;
			if (m_TaskData.TargetID() == null || m_TaskData.TargetID() == 0)
				lv = m_Connection.getAir().getMaxLv();
			else
				lv = m_Connection.getAir().getLv(m_TaskData.TargetID());
			return lv >= m_TaskData.TargetNumber();
		}
		default:
			return false;
		}
	}

	// 判断某一个指定ID任务是否完成
	public boolean TaskIsComplete(int task_table_id) {
		if (!m_TaskLink.contains(task_table_id))
			return false;
		if (m_Task.finished)
			return true;
		return m_TaskLink.indexOf(m_Task.table_id) > m_TaskLink
				.indexOf(task_table_id);
	}

	private void addTaskReward(List<Int3> rewards, long costId) {
		for (Int3 rea : rewards) {
			if (rea.field1() == 1)
				m_Connection.getItem().setItemNumber(rea.field2(),
						rea.field3(), SETNUMBER_TYPE.ADDITION,
						VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods,
						costId, "", Item_Channel.TASK_REC);
			else if (rea.field1() == 2) {
				m_Connection.getCorps().setCorpsNumber(rea.field2(),
						rea.field3(), SETNUMBER_TYPE.ADDITION);
			}
		}
	}

	public boolean CompleteTask() {
		if (IsComplete() == false) {
			logger.error("任务条件未达到 不能完成任务  taskID {}  tableid {}",
					m_Task.task_id, m_Task.table_id);
			return false;
		}
		// ====================增加玩家任务奖励===========================
		long costId = IPOManagerDb.getInstance().getNextId();
		addTaskReward(m_TaskData.Award(), costId);
		LogService.logEvent(m_Connection.getPlayerId(), costId, 0,
				m_TaskData.ID());
		StatManger.getInstance().onTaskComplete(m_Task.table_id);
		// ===========================================================
		// 任务链最后一个任务
		if (TABLE.IsInvalid(m_TaskData.NextID())) {
			m_Task.finished = true;
			m_Task.finshed_time = TimeUtil.GetDateTime();
			m_Connection.getPlayer().incTaskCount(GetTaskLinkID());
		} else {
			m_Task.table_id = m_TaskData.NextID();
			m_Task.complete = false;
			m_Task.number = 0;
			m_Task.finished = false;
			
			if (!TableManager.GetInstance().TableTask().Contains(m_Task.table_id)) {
				logger.error("CompleteTask, task_table_id={},playerid={}", m_Task.table_id, m_Connection.getPlayerId());
			}
			
			m_TaskData = TableManager.GetInstance().TableTask()
					.GetElement(m_Task.table_id);
			autoCheckTaskComplete();
			if (IsComplete() == true)
				m_Task.complete = true;
		}

		Save();
		return true;
	}

	/**自动检查检测任务条件 在接任务滴时候 如果是及时抽取且需要保存滴条件加入*/
	private void autoCheckTaskComplete()
	{
		AddTaskNumber(TASK_TYPE.WANTED_PASS, m_Connection.getPlayer().getRewardCurId(), 1, 0);
	}
	public boolean IsFinshed() {
		return m_Task.finished;
	}

	private void Save() {
		m_Attribute.InsertNeedUpdate(m_Task.task_id);
		m_Connection.pushSave(m_Task);
		m_Connection.getTasks().addCache(m_Task);
	}
}