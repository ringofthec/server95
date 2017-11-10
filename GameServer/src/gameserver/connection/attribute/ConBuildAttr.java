package gameserver.connection.attribute;

import gameserver.cache.BuildCache;
import gameserver.connection.attribute.info.BuildInfo;
import gameserver.connection.attribute.info.build.BuildInfoFactory;
import gameserver.connection.attribute.info.build.FactoryBuildInfo;
import gameserver.connection.attribute.info.build.InstituteBuildInfo;
import gameserver.connection.attribute.info.build.TechBuildInfo;
import gameserver.network.protos.game.CommonProto.Proto_BuildState;
import gameserver.network.protos.game.ProBuild;
import gameserver.network.protos.game.ProBuild.Proto_UpdateState;
import gameserver.network.server.connection.ConnectionAttribute;
import gameserver.utils.TransFormArgs;
import gameserver.utils.Util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.MT_DataBase;
import table.MT_Data_Building;
import table.MT_Data_Rampart;
import table.MT_TableEnum;
import table.MT_TableManager.Rampart;
import table.base.TableManager;

import com.commons.util.DatabaseInsertUpdateResult;
import com.commons.util.GridRect;
import com.commons.util.TimeUtil;

import commonality.BUILD_ID;
import commonality.Common;
import commonality.Common.BUILDTYPE;
import commonality.ITEM_ID;
import commonality.PromptType;
import database.CustomPossessions;
import database.DatabaseBuild;

public class ConBuildAttr extends ConnectionAttribute {
	private static final Logger logger = LoggerFactory
			.getLogger(ConBuildAttr.class);

	// <build_id, BuildInfo>
	private Map<Integer, BuildInfo> m_BuildArray = new HashMap<Integer, BuildInfo>();

	@Override
	protected void Initialize_impl() {
		boolean isNeedLoadDb = false;// 是否需要加载DB数据
		HashMap<Object, Object> allBuilds = BuildCache.getAllBuild(m_Con
				.getPlayerId());
		if (allBuilds == null) {
			isNeedLoadDb = true;
			allBuilds = new HashMap<Object, Object>();
		}
		m_BuildArray.clear();
		if (isNeedLoadDb) {
			List<DatabaseBuild> builds = getDb().Select(DatabaseBuild.class,
					"player_id = ?", m_Con.getPlayerId());
			for (DatabaseBuild build : builds) {
				try {
					m_BuildArray.put(build.build_id, BuildInfoFactory
							.createBuildInfo(m_Con, this, build));
					DatabaseBuild cacheBuild = new DatabaseBuild();
					cacheBuild.set(build);
					allBuilds.put(cacheBuild.build_id, cacheBuild);
				} catch (Exception e) {
					logger.error("Initialize is error : ", e);
				}
			}
		} else {
			Set<Object> keys = allBuilds.keySet();
			for (Object buildId : keys) {
				try {
					DatabaseBuild build = (DatabaseBuild) allBuilds
							.get(buildId);
					build.sync();
					build.setDatabaseSimple(getDb().getM_Simple());
					m_BuildArray.put(build.build_id, BuildInfoFactory
							.createBuildInfo(m_Con, this, build));
				} catch (Exception e) {
					logger.error("Initialize is error : ", e);
				}
			}
		}
		// 不管是否从缓存取出数据都再设置一次 因为 下线时设置了过期时间
		BuildCache.setAllBuilder(m_Con.getPlayerId(), allBuilds);
	}

	public static ProBuild.Proto_BuildingInfo GetProtoData(DatabaseBuild build)
			throws Exception {
		ProBuild.Proto_BuildingInfo.Builder builder = ProBuild.Proto_BuildingInfo
				.newBuilder();
		builder.setBuildid(build.getBuild_id());
		builder.setTableid(build.getTable_id());
		builder.setPosX(build.getPosx());
		builder.setPosY(build.getPosy());
		builder.setLevel(build.getLevel());
		builder.setState(build.getState());
		builder.setUpgradeTime(build.getUpgradetime());
		builder.setOperateTime(build.getLastoperatetime());
		builder.setGatherTime(build.getGathertime());
		builder.setPassTime(0);
		return builder.build();
	}

	private ProBuild.Proto_BuildingInfo GetProtoData(BuildInfo info,
			ConPlayerAttr player) throws Exception {
		ProBuild.Proto_BuildingInfo.Builder builder = ProBuild.Proto_BuildingInfo
				.newBuilder();
		builder.setBuildid(info.getBuildId());
		builder.setTableid(info.getTableId());
		builder.setPosX(info.getPosX());
		builder.setPosY(info.getPosY());
		builder.setLevel(info.getLevel());
		builder.setState(info.getState());
		builder.setUpgradeTime(info.getupgradeTime());
		builder.setOperateTime(info.getLastOpTime());
		builder.setGatherTime(info.getGratherTime());

		if (info.getState() == Proto_BuildState.NONE_BUILD)
			builder.setPassTime(0);
		else if (info.getState() == Proto_BuildState.UPGRADE
				|| info.getState() == Proto_BuildState.OPERATE_UPGRADE) {
			builder.setPassTime(info.getUpgradeNeedTimeByMillisSecond() / 1000);
		} else if (info.getState() == Proto_BuildState.OPERATE) {
			if (info.isFactory()) {
				FactoryBuildInfo fact = (FactoryBuildInfo) info;
				builder.setPassTime(fact.getCorpNeedTime() / 1000);
			} else if (info.isInstitute()) {
				InstituteBuildInfo ins = (InstituteBuildInfo) info;
				builder.setPassTime(ins.getOperNeedTimeByMilliSecond() / 1000);
			} else if (info.isTech()) {
				TechBuildInfo tech = (TechBuildInfo) info;
				builder.setPassTime(tech.calcCorpUpgradeNeedTimeBySecond());
			}
		}

		List<CustomPossessions> queueArray = info.getCorpsArray();
		if (queueArray != null && queueArray.size() > 0) {
			for (CustomPossessions queue : queueArray)
				builder.addQueue(ProBuild.Proto_Queue.newBuilder()
						.setId(queue.id).setNumber(queue.number).build());
		}

		return builder.build();
	}

	public int getQueueNum() {
		int num = 0;
		for (BuildInfo build : m_BuildArray.values()) {
			if (build.isQueue())
				num++;
		}
		return num;
	}

	public void SendDataArray() throws Exception {
		ConPlayerAttr player = m_Con.getPlayer();
		ProBuild.Msg_G2C_AskBuildingInfo.Builder message = ProBuild.Msg_G2C_AskBuildingInfo
				.newBuilder();
		for (Entry<?, BuildInfo> pair : m_BuildArray.entrySet()) {
			BuildInfo info = pair.getValue();
			if (info == null)
				continue;
			info.checkBuild();
			message.addInfo(GetProtoData(info, player));
		}
		message.setType(Proto_UpdateState.LIST);
		m_Con.sendPushMessage(message.build());
	}

	@Override
	public void CheckData() {
		if (m_NeedUpdate.size() <= 0)
			return;
		try {
			ConPlayerAttr player = m_Con.getPlayer();
			ProBuild.Msg_G2C_AskBuildingInfo.Builder message = ProBuild.Msg_G2C_AskBuildingInfo
					.newBuilder();
			for (Long build_id : m_NeedUpdate) {
				BuildInfo info = getBuild(build_id.intValue());
				if (info == null)
					continue;
				message.addInfo(GetProtoData(info, player));
			}
			message.setType(Proto_UpdateState.UPDATE);
			m_Con.sendPushMessage(message.build());
		} catch (Exception e) {
			logger.error("CheckData is error : ", e);
		}
		m_NeedUpdate.clear();
	}

	/** 根据建筑ID获得一个建筑 */
	public BuildInfo getBuild(int build_id) {
		if (m_BuildArray.containsKey(build_id))
			return m_BuildArray.get(build_id);
		return null;
	}

	/** 获得主城信息 */
	public BuildInfo getMainCity() {
		Collection<BuildInfo> builds = m_BuildArray.values();
		for (BuildInfo build : builds) {
			if (build.getTableId() == BUILD_ID.MAIN_CITY)
				return build;
		}
		return null;
	}

	public boolean hasBuild(BUILDTYPE type) {
		return getBuild(type) != null;
	}

	public BuildInfo getBuild(BUILDTYPE type) {
		Collection<BuildInfo> builds = m_BuildArray.values();
		for (BuildInfo build : builds) {
			if (build.getType() == type.Number())
				return build;
		}
		return null;
	}

	public boolean isTechIsUp(int techId) {
		Collection<BuildInfo> builds = m_BuildArray.values();
		for (BuildInfo build : builds) {
			if (build.getType() == BUILDTYPE.Tech.Number()) {
				if (build.getState() == Proto_BuildState.OPERATE
						|| build.getState() == Proto_BuildState.OPERATE_UPGRADE) {
					int curWorkId = 0;
					try {
						curWorkId = build.getCurWorkTableId();
					} catch (Exception e) {
					}

					if (curWorkId == techId)
						return true;
				}
			}
		}

		return false;
	}

	public boolean isCorpIsUp(int corptId) {
		Collection<BuildInfo> builds = m_BuildArray.values();
		for (BuildInfo build : builds) {
			if (build.getType() == BUILDTYPE.Institute.Number()) {
				if (build.getState() == Proto_BuildState.OPERATE
						|| build.getState() == Proto_BuildState.OPERATE_UPGRADE) {
					int curWorkId = 0;
					try {
						curWorkId = build.getCurWorkTableId();
					} catch (Exception e) {
					}

					if (curWorkId == corptId)
						return true;
				}
			}
		}

		return false;
	}

	/** 插入一个建筑 */
	public BuildInfo insertBuild(int table_id, int x, int y) {
		return insertBuild(table_id, x, y, 0, false);
	}

	/** 插入一个建筑 */
	public BuildInfo insertBuild(int table_id, int x, int y, int level) {
		return insertBuild(table_id, x, y, level, true);
	}

	private BuildInfo insertBuild(int table_id, int x, int y, int level,
			boolean once) {
		try {
			DatabaseBuild build = new DatabaseBuild();
			build.player_id = m_Con.getPlayerId();
			build.table_id = table_id;
			build.posx = x;
			build.posy = y;
			if (once) {
				build.state = Proto_BuildState.NONE_BUILD;
				build.upgradetime = 0L;
				build.level = level;
			} else {
				build.state = Proto_BuildState.UPGRADE;
				build.level = 0;
				build.upgradetime = TimeUtil.GetDateTime();
			}
			build.lastoperatetime = 0L;
			build.gathertime = TimeUtil.GetDateTime();
			build.corpsArray = null;
			DatabaseInsertUpdateResult result = getDb().Insert(build);
			build.build_id = result.identity.intValue();
			BuildInfo info = BuildInfoFactory.createBuildInfo(m_Con, this,
					build);
			if (!once)
				InsertNeedUpdate(build.build_id);

			m_BuildArray.put(build.build_id, info);
			addCache(build);
			return info;
		} catch (Exception e) {
			logger.error("insertBuild is error : ", e);
		}
		return null;
	}

	
	public void addCache(DatabaseBuild build)
	{
		if ( BuildCache.isNeedAllIncr(build.player_id))
			BuildCache.setAllBuilder(build.player_id, m_BuildArray);
		else
			BuildCache.addBuild(build.player_id, build);
	}
	/**
	 * 获得建筑的数量
	 */
	public int getBuildNumber(int table_id) {
		return getBuildNumber(table_id, 0);
	}

	/** 获得某一类型建筑的数量 */
	public int getBuildNumber(int table_id, int level) {
		int count = 0;
		Collection<BuildInfo> builds = m_BuildArray.values();
		for (BuildInfo build : builds) {
			if (build.getTableId() != table_id)
				continue;
			if (build.getLevel() >= level
					|| (build.IsUpgrading() && build.getLevel() + 1 >= level))
				++count;
		}
		return count;
	}

	/** 检测某一类型建筑的最高等级 */
	public boolean checkBuildLevel(int table_id, int level, boolean needNotify) {
		if (level <= 0)
			return true;
		Collection<BuildInfo> builds = m_BuildArray.values();
		for (BuildInfo build : builds) {
			if (build.getTableId() == table_id && build.getLevel() >= level)
				return true;
		}

		MT_Data_Building build = TableManager.GetInstance().TableBuilding()
				.GetElement(table_id);

		if (needNotify)
			m_Con.ShowPrompt(PromptType.NEED_BUILD_LEVEL,
					TransFormArgs.CreateBuildArgs(build.SpriteName(), 1), level);
		return false;
	}

	public boolean checkmainCityLevel(int level, boolean needNotify) {
		if (level <= 0)
			return true;
		Collection<BuildInfo> builds = m_BuildArray.values();
		for (BuildInfo build : builds) {
			if (build.isMainCity() && build.getLevel() >= level)
				return true;
		}

		if (needNotify)
			m_Con.ShowPrompt(PromptType.NEED_BUILD_LEVEL,
					TransFormArgs.CreateBuildArgs("main", "MainCity", 1), level);
		return false;
	}

	public boolean checkBuildQueue(boolean needNotify) throws Exception {
		int UpGreadingCount = Util.GetCurWorkNum(m_Con);
		if (UpGreadingCount < m_Con.getPlayer().getQueuesize())
			return true;

		if (needNotify)
			m_Con.ShowPrompt(PromptType.BUILD_QUEUE_NOT_ENOUGH);
		return false;
	}

	/** 返回建筑数组 */
	public Collection<BuildInfo> getBuildArray() {
		return m_BuildArray.values();
	}

	// 判断建筑是否可以在指定的坐标位置上摆放
	public boolean canPlace(int number, int build_id, int x, int y, int w, int h) {
		try {
			GridRect rect = new GridRect(x, y, w, h); // 建筑新位置范围
			int remainder = 0; // 余数(如果宽度够了就是高度的余数)
			int maxSize = Common.MAP_START_SIZE; // 最大地垫圈
			boolean wOffset = false; // 宽度是否够
			int finshedWidth = maxSize, finshedHeight = maxSize;

			// 下面这个算法是计算一下当前玩家的地垫扩展后的长宽
			if (number > 0) {
				while (true) {
					++maxSize;

					// 当前要扩展一圈需要的地垫数量
					int circleLength = maxSize * 2 - 1;

					if (number == circleLength) {
						// 刚好扩展了一圈
						finshedWidth = maxSize;
						finshedHeight = maxSize;
						remainder = 0;
						break;
					} else if (number < circleLength) {
						// 扩展了不到一圈
						finshedHeight = maxSize - 1;
						finshedWidth = maxSize - 1;

						if (number >= maxSize - 1) {
							// 竖向扩展已经满啦
							wOffset = true;

							// 计算一下横向扩展了几个
							remainder = number - (maxSize - 1);
							finshedWidth = maxSize;
						} else {
							// 扩展不到半圈，计算一下扩展了几个
							remainder = number;
						}
						break;
					}

					// 继续计算下一圈的扩展情况
					number -= circleLength;
				}
			}

			// 算法结束后
			// 可以知道现在扩展后的地垫的矩形部分的宽finshedWidth，高finshedHeight(以是否构成一个完整的矩形来辨识竖向或横向扩展的完成)
			// 以及是否以及完成竖向扩展开始横向扩展wOffset
			// 以及竖向或横向扩展到了第几个地垫remainder

			// 检查和现有的建筑是否有重叠
			Collection<BuildInfo> builds = m_BuildArray.values();
			for (BuildInfo build : builds) {
				if (build.getBuildId() != build_id) {
					if (build.getRect().Overlaps(rect))
						return false;
				}
			}

			// 矩形下判断是否能够摆放
			GridRect maxRect = new GridRect(0, 0, finshedWidth, finshedHeight);
			maxRect.Multiply(Common.LAND_WH);
			if (maxRect.Contains(rect))
				return true;

			// 这个情况说明了，当前地垫的矩形部分未能成功摆放建筑
			// 下面要检查异型部分的摆放情况
			if (remainder > 0) {
				GridRect remainderRect = null;
				if (wOffset) {
					// 已经开始横向扩展了，所以以横向扩展了几个作为基，构造一个矩形
					remainderRect = new GridRect(finshedWidth - remainder, 0,
							remainder, finshedHeight + 1);
				} else {
					// 还没有进行横向扩展，所以以竖向扩展了几个(remainder)作为基，构造一个矩形
					remainderRect = new GridRect(0, 0, finshedWidth + 1,
							remainder);
				}

				remainderRect.Multiply(Common.LAND_WH);
				if (remainderRect.Contains(rect))
					return true;
			}

			return false;
		} catch (Exception e) {
			logger.error("canPlace is error : ", e);
		}
		return false;
	}

	/**
	 * 移动建筑
	 * 
	 * @throws Exception
	 */
	public boolean buildMove(int build_id, int x, int y) throws Exception {
		if (m_BuildArray.containsKey(build_id)) {
			BuildInfo info = m_BuildArray.get(build_id);
			if (canPlace(m_Con.getPlayer().getExtend(), build_id, x, y,
					info.getSize().width, info.getSize().height)) {
				info.setPosition(x, y);
				return true;
			} else {
				logger.error("该位置不能摆放建筑");
			}
		}
		return false;
	}

	/** 获得建筑的table信息 */
	public MT_Data_Building getBuildData(int build_id) {
		BuildInfo build = getBuild(build_id);
		if (build != null)
			return build.getDataBuilding();
		return TableManager.GetInstance().TableBuilding().GetElement(build_id);
	}

	/** 获得建筑的table 详细信息 */
	public MT_DataBase getBuildDataBase(int build_id) {
		BuildInfo build = getBuild(build_id);
		if (build != null)
			return build.getDataBase();
		return Util.GetDataBaseByData(TableManager.GetInstance()
				.TableBuilding().GetElement(build_id), 0);
	}

	/**
	 * 获得某类型建筑的最高等级
	 * 
	 * @param buildType
	 *            建筑类型
	 * @return
	 */
	public int getBuildMaxLevel(int build_table_id) {
		int level = 0;
		for (BuildInfo build : m_BuildArray.values()) {
			if (build.getTableId() == build_table_id)
				if (build.getLevel() > level)
					level = build.getLevel();
		}
		return level;
	}

	public void checkAllBuild() throws Exception {
		for (BuildInfo build : m_BuildArray.values())
			build.checkBuild();
	}

	public int getBuildLevel(int build_table_id) {
		for (BuildInfo build : m_BuildArray.values()) {
			if (build.getTableId() == build_table_id)
				return build.getLevel();
		}
		return 0;
	}

	/**
	 * 获得金币的最大存储量
	 */
	public int GetMaxMoneyCount() throws Exception {
		int maxNumber = 0;
		Collection<BuildInfo> builds = m_Con.getBuild().getBuildArray();
		for (BuildInfo build : builds) {
			if (build.isDepot()) {
				Int2 count = (Int2) build.getDataBase().GetDataByString(
						MT_TableEnum.MaxStore);
				if (count.field1().equals(ITEM_ID.MONEY))
					maxNumber += count.field2();
			} else if (build.isMainCity()) {
				@SuppressWarnings("unchecked")
				List<Int2> count = (List<Int2>) build.getDataBase()
						.GetDataByString(MT_TableEnum.MaxStore);
				for (Int2 val : count) {
					if (val.field1().equals(ITEM_ID.MONEY))
						maxNumber += val.field2();
				}
			}
		}
		return maxNumber;
	}

	/**
	 * 正在生产的总人口
	 * 
	 * @return
	 */
	public int GetMakePopulation() {
		int population = 0;
		Collection<BuildInfo> builds = m_Con.getBuild().getBuildArray();
		for (BuildInfo build : builds) {
			population += build.getMakingPopulation();
		}
		return population;
	}

	public int GetWallHp() throws Exception {
		int hp = 0;
		Collection<BuildInfo> builds = m_Con.getBuild().getBuildArray();
		for (BuildInfo build : builds) {
			if (build.isWall()) {
				hp += (int) build.getDataBase().GetDataByString(
						MT_TableEnum.WallHP);
			} else if (build.isMainCity()) {
				hp += (int) build.getDataBase().GetDataByString(
						MT_TableEnum.WallHP);
			}
		}
		return hp;
	}

	public int GetWallLayout() throws Exception {
		Collection<BuildInfo> builds = m_Con.getBuild().getBuildArray();
		for (BuildInfo build : builds) {
			if (build.isWall())
				return (int) build.getDataBase().GetDataByString(
						MT_TableEnum.WallLayout);
		}
		return 1;
	}

	public int getFightWallVal() {
		int total = 0;
		for (BuildInfo build : m_BuildArray.values()) {
			if (build.isWall()) {
				MT_Data_Rampart wallData = TableManager.GetInstance()
						.getSpawns(Rampart.Rampart_wall)
						.GetElement(build.getLevel());
				total = wallData.Power();
				break;
			}
		}
		return total;
	}

	public int getFightBuildsVal() {
		int totalLv = 0;
		for (BuildInfo build : m_BuildArray.values())
			totalLv += build.getLevel();

		return totalLv * 10;
	}
}
