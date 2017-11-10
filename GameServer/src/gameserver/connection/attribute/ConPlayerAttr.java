package gameserver.connection.attribute;

import gameserver.active.ActiveService;
import gameserver.cache.PlayerCache;
import gameserver.config.GameConfig;
import gameserver.config.ServerConfig;
import gameserver.connection.attribute.info.BuildInfo;
import gameserver.connection.attribute.info.HeroInfo;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.message.game.ClientMessageCommon;
import gameserver.network.message.game.ClientMessageHeroPath;
import gameserver.network.message.game.ClientMessageLegion;
import gameserver.network.message.game.ClientMessageRecharge;
import gameserver.network.message.game.ClientMessageWanted;
import gameserver.network.protos.common.ProLegion.Msg_C2G_Legion_List;
import gameserver.network.protos.game.ProActive.Msg_G2C_PublicInfo;
import gameserver.network.protos.game.ProActive.Proto_PublicInfo;
import gameserver.network.protos.game.ProChat.Proto_Enum_Refuse_ChatType;
import gameserver.network.protos.game.ProFight;
import gameserver.network.protos.game.ProGameInfo;
import gameserver.network.protos.game.ProGameInfo.Msg_G2C_AskPlayerInfo;
import gameserver.network.protos.game.ProGameInfo.Msg_G2C_FlushTotalFightVal;
import gameserver.network.protos.game.ProPve;
import gameserver.network.protos.game.ProPve.Msg_C2G_StartAttackPve;
import gameserver.network.protos.game.ProPve.Proto_Formation;
import gameserver.network.protos.game.ProPvp;
import gameserver.network.protos.game.ProPvp.Msg_G2C_PvpMatching;
import gameserver.network.protos.game.ProPvp.Msg_G2C_doSthNum;
import gameserver.network.protos.game.ProPvp.Msg_G2C_doSthNum.Proto_CountInfo;
import gameserver.network.protos.game.ProPvp.Proto_ActionType;
import gameserver.network.protos.game.ProReward;
import gameserver.network.protos.game.ProReward.Msg_G2C_CanFund;
import gameserver.network.protos.game.ProReward.Proto_RewardType;
import gameserver.network.protos.game.ProSee;
import gameserver.network.protos.game.ProSee.Msg_G2C_SeePlayerInfo;
import gameserver.network.protos.game.ProWanted.Msg_C2G_BeginWanted;
import gameserver.network.protos.game.ProWanted.Msg_G2C_WantedData;
import gameserver.network.server.connection.Connection;
import gameserver.network.server.connection.ConnectionAttribute;
import gameserver.network.server.connection.ConnectionManager;
import gameserver.stat.StatManger;
import gameserver.utils.DbMgr;
import gameserver.utils.Item_Channel;
import gameserver.utils.Util;
import gameserver.utils.UtilItem;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.MT_Data_Corps;
import table.MT_Data_Exp;
import table.MT_Data_HeroPath;
import table.MT_Data_HeroPathConfig;
import table.MT_Data_Instance;
import table.MT_Data_Item;
import table.MT_Data_LegionStore;
import table.MT_Data_LevelReward;
import table.MT_Data_LoginReward;
import table.MT_Data_Luckdraw;
import table.MT_Data_Rank;
import table.MT_Data_Recharge1;
import table.MT_Data_Vip;
import table.MT_Data_WantedInstance;
import table.MT_TableManager;
import table.base.TABLE;
import table.base.TableManager;

import com.commons.util.DatabaseInsertUpdateResult;
import com.commons.util.DatabaseUtil;
import com.commons.util.TimeUtil;

import commonality.Common;
import commonality.Common.ACTIVETYPE;
import commonality.Common.LIMIT_TYPE;
import commonality.Common.PLAYER_STATE;
import commonality.Common.SETNUMBER_TYPE;
import commonality.Common.TASK_TYPE;
import commonality.CommonPvp;
import commonality.ITEM_ID;
import commonality.ProductChannel;
import commonality.PromptType;
import commonality.VmChannel;

import database.CustomBuyCommodity;
import database.CustomChatReport;
import database.CustomCountInfo;
import database.CustomFormationCrops;
import database.CustomHeroPathAlreadyPoint;
import database.CustomHeroPathCrop;
import database.CustomHeroPathCurPoint;
import database.CustomInstanceStar;
import database.CustomLegionCommodity;
import database.CustomSuperior;
import database.CustomTaskCompleteCount;
import database.DatabaseLevReward;
import database.DatabaseMail;
import database.DatabasePlayer;
import databaseshare.CustomFormation;
import databaseshare.DatabaseChatReport;
import databaseshare.DatabaseLegion;
import databaseshare.DatabaseNotice;
import databaseshare.DatabasePvp_match;
import databaseshare.DatabaseRecharge_cache;

public class ConPlayerAttr extends ConnectionAttribute {
	protected static final Logger logger = LoggerFactory
			.getLogger(ConPlayerAttr.class);

	private DatabasePlayer m_Player = null;
	private DatabasePvp_match m_PvpInfo = null;
	private boolean isDirty = false;
	private long loginTime = 0;
	private long lastPauseTime = 0;
	private int totalPauseTime = 0;

	// 是否在PVE战斗中
	public boolean isPve = false;
	// Pve协议对象
	public Msg_C2G_StartAttackPve pveMessage = null;
	// Pvp匹配协议对象
	public Msg_G2C_PvpMatching pvpMessage = null;
	// PVP玩家阵型
	public List<Proto_Formation> pvpFormation = new ArrayList<ProPve.Proto_Formation>();
	// 转盘进行中标示
	private boolean isTimeRewarding = false;
	// 转盘奖励缓冲
	private UtilItem utilItem = null;
	// 玩家已经领取的等级奖励
	private List<DatabaseLevReward> levRewards = new ArrayList<DatabaseLevReward>();

	// 玩家所在军团信息
	private DatabaseLegion legion = null;

	// 当前悬赏关卡正在挑战的悬赏关卡
	public int curRewardInstanceId = 0;
	// 悬赏协议对象
	public Msg_C2G_BeginWanted rewardInstanceBeginMsg = null;

	@Override
	protected void Initialize_impl() {
		loginTime = TimeUtil.GetDateTime();
		long pid = this.m_Con.getPlayerId();
		// 取玩家数据时首先去cache取 如果没有才去数据库查询 并设置到缓存
		m_Player = PlayerCache.getPlayer(pid);
		if (m_Player == null)
			m_Player = getDb().SelectOne(DatabasePlayer.class, "player_id = ?",
					pid);
		else {
			m_Player.sync();
			m_Player.setDatabaseSimple(getDb().getM_Simple());
		}
		updateCustomSuperior();
		PlayerCache.setPlayer(pid, m_Player);
		m_PvpInfo = PlayerCache.getPvpMatch(pid);
		if (m_PvpInfo == null) {
			m_PvpInfo = DbMgr.getInstance().getShareDB()
					.SelectOne(DatabasePvp_match.class, "player_id = ?", pid);
		} else {
			m_PvpInfo.sync();
			m_PvpInfo.setDatabaseSimple(DbMgr.getInstance().getShareDB()
					.getM_Simple());
		}

		PlayerCache.setPvpMatch(pid, m_PvpInfo);
		PlayerCache.getLvAward(pid, levRewards, getDb().getM_Simple());
		if (levRewards == null || levRewards.size() == 0)
			levRewards = getDb().Select(DatabaseLevReward.class,
					"player_id = ?", pid);

		PlayerCache.setLvAward(pid, levRewards);

		if (m_Player.belong_legion != 0)
			legion = DbMgr
					.getInstance()
					.getShareDB()
					.SelectOne(DatabaseLegion.class, "legion_id = ? ",
							m_Player.belong_legion);
	}

	public Integer getLegionLv() {
		if (legion != null)
			return legion.level;
		return 0;
	}

	public long CheckState() {
		if (m_PvpInfo.state == PLAYER_STATE.NONE)
			return 0;
		long nowTime = TimeUtil.GetDateTime();
		if ((m_PvpInfo.state == PLAYER_STATE.MATCHED && nowTime
				- m_PvpInfo.matchtime > CommonPvp.MATCH_TIME_OUT)
				|| (m_PvpInfo.state == PLAYER_STATE.DEFENSE && nowTime
						- m_PvpInfo.fightingtime > CommonPvp.FIGHTING_TIME_OUT)) {
			m_PvpInfo.state = PLAYER_STATE.NONE;
			savePlayer(false);
			return 0;
		}
		if (m_PvpInfo.state == PLAYER_STATE.MATCHED)
			return (CommonPvp.MATCH_TIME_OUT - (nowTime - m_PvpInfo.matchtime) + CommonPvp.FIGHTING_TIME_OUT);
		return CommonPvp.FIGHTING_TIME_OUT - (nowTime - m_PvpInfo.fightingtime);
	}

	@Override
	public void CheckUpline() {
		long now = TimeUtil.GetDateTime();
		m_Player.online = true;
		m_PvpInfo.online = true;
		if (m_Player.belong_legion != m_PvpInfo.belong_legion.intValue())
			m_PvpInfo.belong_legion = m_Player.belong_legion;

		updateRegistrationId(m_Con.getRegistration());
		if (ServerConfig.country.equals("US")) {
			if (TimeUtil.canFlush(m_Player.last_flush_time, now)) {
				onNewDay(now, true);
			}
		} else {
			if (!TimeUtil.IsSameDay(m_Player.last_flush_time, now)) {
				onNewDay(now, true);
			}
		}
		m_PvpInfo.save();

		// 上线的时候检查玩家军团商店是否刷新
		int hour = (int) (now - m_Player.refresh_time)
				/ Common.ONE_HOUR_MILLISECOND;
		int count = hour / 2;
		if (count >= 1) {
			// 刷新
			Msg_C2G_Legion_List.Builder msg = Msg_C2G_Legion_List.newBuilder();
			msg.setIsHandRefresh(0);
			try {
				ClientMessageLegion.getInstance().OnLegionStoreList(m_Con,
						msg.build(), false);
			} catch (Exception e) {
				logger.error("上线刷新军团商店出错:" + e.getMessage());
			}
		}

		// 判断玩家的英雄之路是否初始化
		if (!m_Con.getPlayer().isHeroPathInit())
			initHeroPathData();
		
		m_Player.save();

	}

	public void setDirty() {
		isDirty = true;
	}

	// 每次收到消息都会调用此函数
	@Override
	public void CheckData() {
		if (UpdateCp() == true)
			ClientMessageCommon.getInstance().UpdateCountInfo(m_Con,
					ProPvp.Proto_ActionType.CP,
					m_Con.getItem().getItemCountByTableId(ITEM_ID.CP),
					m_Con.getCommon().GetValue(LIMIT_TYPE.CP));

		if (isDirty) {
			isDirty = false;
			sendPlayerInfo();
		}
	}

	public void tryRecalcFightVal() {
		if (m_Con.isNeed_recalc_fight_val()) {
			int total_fight = calToTalFightVal();
			if (total_fight != m_PvpInfo.fightVal) {
				m_PvpInfo.fightVal = total_fight;
				sendTotalFightVal();
				savePlayer(false);
			}
			m_Con.setNeed_recalc_fight_val(false);
		}
	}

	public void sendTotalFightVal() {
		Msg_G2C_FlushTotalFightVal.Builder msg = Msg_G2C_FlushTotalFightVal
				.newBuilder().setFightVal(m_PvpInfo.fightVal);

		for (HeroInfo hi : m_Con.getHero().getHeros().values()) {
			msg.addHeroId(hi.getHeroId());
			msg.addHeroFight(hi.getFightVal());
		}

		m_Con.sendPushMessage(msg.build());
	}

	// 玩家下线了
	public void Offline() {
		if (m_Player != null) {
			m_PvpInfo.cur_population = totlePopulation();
			m_PvpInfo.totle_population = m_Con.getCommon().GetValue(
					LIMIT_TYPE.PEOPLE);
			MatchTarget(0);
			m_PvpInfo.online = false;
			m_Player.online = false;
			m_Player.last_offline_time = TimeUtil.GetDateTime();
			savePlayer(false);
		}
	}

	public int totlePopulation() {
		return m_Con.getCorps().getAllPopulation()
				+ m_Con.getBuild().GetMakePopulation()
				+ m_Con.getPlayer().getDefensePopulation();
	}

	public void updateRegistrationId(String res) {
		m_Player.registration_id = res;
		m_Con.pushSave(m_Player);
	}

	// 注意，只有在buildPlayerInfo中新加了需要同步给客户端的时候才能传true，其他情况一律传false
	private void savePlayer(boolean isSync) {
		if (m_Con.GetInitializeUpLine() && isSync)
			isDirty = true;
		m_Con.pushSave(m_Player);
		m_Con.pushSave(m_PvpInfo);
		PlayerCache.setPlayer(m_Con.getPlayerId(), m_Player);
		PlayerCache.setPvpMatch(m_Con.getPlayerId(), m_PvpInfo);
	}

	public int calToTalFightVal() {
		// 英雄战斗力
		int hero = m_Con.getHero().getFightVal();

		// 飞机战斗力
		int air = m_Con.getAir().getFightVal();

		// 科技战斗力
		int tech = m_Con.getTech().getFightVal();

		// 城防战斗力
		int wall = m_Con.getBuild().getFightWallVal();

		// 军衔战斗力
		int feat = getFightVal();

		// 城建战斗力
		int build = m_Con.getBuild().getFightBuildsVal();

		// 军团战斗力
		int legion = m_Con.getPlayer().getFightLegionVal();

		// 兵种战斗力
		int crop = m_Con.getCorps().getFightCropsVal();

		int total = hero + air + tech + wall + feat + build + legion + crop;
		if (m_Player.fight_value != total) {
			m_Player.fight_value = total;
			savePlayer(false);
			m_Con.setNeed_recalc_fight_val(true);
		}
		return total;
	}

	public int getTodayMoneyCount() {
		return m_Player.today_help_money_count;
	}

	public int getTodayCorpCount() {
		return m_Player.today_help_corp_count;
	}

	public void addTodayMoneyCount(int addv) {
		m_Player.today_help_money_count += addv;
		savePlayer(false);
	}

	public void addTodayCorpCount(int addv) {
		m_Player.today_help_corp_count += addv;
		savePlayer(false);
	}

	public int getFightLegionVal() {
		int legionId = m_Con.getPlayer().getBelegionId();
		if (legionId == 0)
			return 0;
		if (legion == null)
			return 0;
		return legion.getLevel() * 40;
	}

	private int getFightVal() {
		MT_Data_Rank rank = TableManager.GetInstance().TableRank()
				.GetElement(m_Con.getPlayer().getRank());
		return rank.Power();
	}

	// 用于初始化玩家每天重置数据,这个函数可能在两个地方调用
	// 1. 玩家在跨天的时刻(及晚上12:00)
	// 2. 玩家跨天时不在线，玩家登陆时检查上次下线时间和当前时候是否已跨天，如果发现跨天了，就要补调
	public void onNewDay(long now, boolean isLogin) {
		logger.debug("{}[{}] 于 {} 进行了每天一次的初始化操作", m_Player.getName(),
				m_Player.player_id, TimeUtil.GetDateString());
		m_Player.total_login_num++;
		if (Util.iscontinueLogin(now, m_Player.last_flush_time)) {
			m_Player.continue_login_num++;
			if (m_Player.continue_login_reward == 5) {
				m_Player.continue_login_reward = 0;
			}
			if (ActiveService.getInstance().isActiveRun(ACTIVETYPE.ACTIVE_SEVEN_DAY.Number())) {
				int dayNum = m_Con.getReward().getActiveRewardIdx(ACTIVETYPE.ACTIVE_SEVEN_DAY.Number());
				if (dayNum < 7) {
					m_Con.getMails().addSevenDayMail(dayNum+1);
					m_Con.getReward().incActiveRewardIdx(ACTIVETYPE.ACTIVE_SEVEN_DAY.Number(), 1);
				}
			}
			
		} else {
			m_Player.continue_login_num = 1;
			m_Player.continue_login_reward = 0;
			if (ActiveService.getInstance().isActiveRun(ACTIVETYPE.ACTIVE_SEVEN_DAY.Number())) {
				int dayNum = m_Con.getReward().getActiveRewardIdx(ACTIVETYPE.ACTIVE_SEVEN_DAY.Number());
				if (dayNum < 7) {
					m_Con.getMails().addSevenDayMail(1);
					m_Con.getReward().setActiveRewardIdx(ACTIVETYPE.ACTIVE_SEVEN_DAY.Number(), 1);
				}
			}
		}
		flushReward(now);
		resetRewardInfo(isLogin);
		resetTask();
		clearCommodity();
		ResetPlayerPvpCount();
		setContributeMoneyNum(0);
		clearPveNum();
		resetCount();
		clearFreeNum();
		fushHeroPathNum();
		reflushHelpCount();
		long old_last_flush_time = m_Player.last_flush_time;
		m_Player.last_flush_time = TimeUtil.GetDateTime() + 2000;
		savePlayer(true);

		if (!isLogin)
			syncFund();

		LogService.logEvent(m_Con.getPlayerId(), 0, 27,
				(int) (m_Player.last_flush_time / 1000), (int)(old_last_flush_time / 1000));
		LogService.logLogIn(m_Con.getPlayerId(), getNation(), getCreateTime(), getLevel());
		StatManger.getInstance().onNewDay(m_Con);
	}

	private void reflushHelpCount() {
		m_Player.today_help_corp_count = 0;
		m_Player.today_help_money_count = 0;
		savePlayer(false);
	}

	private void fushHeroPathNum() {
		m_Player.hero_path_num = 0;
		savePlayer(false);
	}

	private void clearFreeNum() {
		m_Player.free_num = 0;
		savePlayer(false);
	}

	public String getCreateTime() {
		Timestamp timestamp = TimeUtil.GetTimestamp(m_Player.create_time);
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateformat.format(timestamp);
	}

	public String getNation() {
		return m_Player.nation;
	}

	public Timestamp getCreateTimeTimeStamp() {
		return TimeUtil.GetTimestamp(m_Player.create_time);
	}

	public long getLastFlushTime() {
		return m_Player.last_flush_time;
	}

	private void resetCount() {
		m_Player.count_info.clear();

		m_Player.count_info.add(new CustomCountInfo(0, m_Con.getCommon()
				.GetValue(LIMIT_TYPE.USE_CP_ITEM)));
		m_Player.count_info.add(new CustomCountInfo(1, m_Con.getCommon()
				.GetValue(LIMIT_TYPE.USE_PVP_ITEM)));
		savePlayer(false);
	}

	public int getCount(int id) throws Exception {
		for (CustomCountInfo cci : m_Player.count_info) {
			if (cci.id == id)
				return cci.count;
		}

		throw new Exception("player getCount 出错了 ， id=" + id);
	}

	public void decCount(int id) throws Exception {
		for (CustomCountInfo cci : m_Player.count_info) {
			if (cci.id == id) {
				cci.count--;
				return;
			}
		}

		throw new Exception("player decCount 出错了 ， id=" + id);
	}

	private void clearPveNum() {
		for (CustomInstanceStar instanceStar : m_Player.instance_star_array)
			instanceStar.count = 0;
		savePlayer(false);
	}

	public void updateLanguage(int language) {
		m_Player.language = language;
		savePlayer(false);
	}

	public int getLanguage() {
		if (m_Player == null || m_Player.language == null)
			return 1;
		return m_Player.language;
	}

	// 商店购买限制相关
	public int getCommodityCount(int id) {
		for (CustomBuyCommodity cbc : m_Player.commodity_list) {
			if (cbc.commodity_id == id)
				return cbc.number;
		}
		return 0;
	}

	public void icrCommodityCount(int id) {
		if (getCommodityCount(id) == 0) {
			CustomBuyCommodity commodity = new CustomBuyCommodity();
			commodity.commodity_id = id;
			commodity.number = 1;
			commodity.buy_time = TimeUtil.GetDateTime();
			m_Player.commodity_list.add(commodity);
		} else {
			for (CustomBuyCommodity cbc : m_Player.commodity_list) {
				if (cbc.commodity_id == id) {
					cbc.number++;
				}
			}
		}
		savePlayer(false);
	}

	private void clearCommodity() {
		m_Player.commodity_list.clear();
		savePlayer(false);
	}

	// 任务完成次数限制相关
	public void incTaskCount(int taskId) {
		for (CustomTaskCompleteCount ts : m_Player.taskcompletecount) {
			if (ts.taskid == taskId) {
				ts.count++;
				return;
			}
		}

		m_Player.taskcompletecount.add(new CustomTaskCompleteCount(taskId, 1));
	}

	public boolean testTaskLimit(int taskId, int max) {
		for (CustomTaskCompleteCount ts : m_Player.taskcompletecount) {
			if (ts.taskid == taskId) {
				return ts.count < max;
			}
		}

		return true;
	}

	public void resetTask() {
		m_Player.taskcompletecount.clear();
		m_Con.getTasks().onNewDay();
		savePlayer(false);
	}

	// 军团商店相关
	public List<CustomLegionCommodity> getlegion_store_list() {
		return m_Player.legion_store_list;
	}

	public void setlegion_store_list(int id) {
		for (CustomLegionCommodity c : m_Player.legion_store_list) {
			if (c.table_id == id) {
				c.is_buy = 1;
				break;
			}
		}
		savePlayer(false);
	}

	public void reserveDefence() {
		for (CustomFormation corp : m_PvpInfo.defenseformation) {
			if (corp.type == 0)
				m_Con.getCorps().setCorpsNumber(corp.id, 1,
						SETNUMBER_TYPE.ADDITION);
		}
	}

	public String getName() {
		return m_Player.name;
	}

	public long getPlayerId() {
		return m_Player.player_id;
	}

	public int getluck_val() {
		return m_Player.luck_val;
	}

	public int getVipLevel() {
		return m_Player.vipLevel;
	}

	public int getLevel() {
		return m_Player.level;
	}

	public boolean isOnline() {
		return m_Player.online;
	}

	public Long getRefreshTime() {
		if (m_Player == null) {
			if (m_Con == null) {
				logger.error("getRefreshTime Connection is null");
			} else {
				ConAccountAttr acc = m_Con.getAccount();
				if (acc != null) {
					logger.error("getRefreshTime Connection account player_id={}", acc.getPlayerId());
				}
				logger.error("getRefreshTime Connection is not null and init={} destory={}", m_Con.isInit(), m_Con.isDestroy());
			}
			return null;
		}
		if (m_Player.refresh_time == null)
			return 0L;
		return m_Player.refresh_time;
	}

	public Long getLastOffline() {
		return m_Player.last_offline_time;
	}

	public int getHead() {
		return m_Player.head;
	}

	public long getCpTime() {
		return m_Player.cp_time;
	}

	public void icLuckVal() {
		m_Player.luck_val++;
	}

	public int getOnlineTime() {
		return (int) ((TimeUtil.GetDateTime() - loginTime) / 1000);
	}

	public int getActiveTime() {
		if (getOnlineTime() - totalPauseTime < 0)
			return 0;
		
		return getOnlineTime() - totalPauseTime;
	}

	public void setGamePause(boolean bPause) {
		if (bPause) {
			lastPauseTime = TimeUtil.GetDateTime();
		} else {
			if (lastPauseTime == 0)
				return ;
			
			totalPauseTime += (int) ((TimeUtil.GetDateTime() - lastPauseTime) / 1000);
			lastPauseTime = 0;
		}
	}

	// 阵型相关
	public List<database.CustomFormation> getformation1() {
		return m_Player.formation1;
	}

	public List<database.CustomFormation> getformation2() {
		return m_Player.formation2;
	}

	public int getDefensePopulation() {
		return getDefensePopulation(m_PvpInfo);
	}

	public static int getDefensePopulation(DatabasePvp_match pvpInfo) {
		int count = 0;
		for (CustomFormation crop : pvpInfo.defenseformation) {
			if (crop.type == ProPve.Proto_CorpsType.SOLIDER.getNumber()) {
				MT_Data_Corps corpInfo = TableManager.GetInstance()
						.TableCorps().GetElement(crop.id);
				if (corpInfo != null) {
					count += corpInfo.Population();
				}
			}
		}
		return count;
	}

	public List<databaseshare.CustomFormation> getdefenseformation() {
		return m_PvpInfo.defenseformation;
	}

	public void setFormation1(List<database.CustomFormation> formation) {
		m_Player.formation1 = formation;
		savePlayer(false);
	}

	public void setFormation2(List<database.CustomFormation> formation) {
		m_Player.formation2 = formation;
		savePlayer(false);
	}

	public void setDefenseFormation(
			List<databaseshare.CustomFormation> formation) {
		m_PvpInfo.defenseformation = formation;
		savePlayer(false);
	}

	public int gettotal_login_num() {
		return m_Player.total_login_num;
	}

	// 建造队列相关
	public void addQueueSize(int size) {
		m_Player.queue_size += size;
		savePlayer(false);
	}

	public int getQueuesize() {
		return m_Player.queue_size + m_Con.getBuild().getQueueNum();
	}

	// 军衔相关
	public int getRank() {
		return Util.getRank(m_PvpInfo.feat);
	}

	public int getFeat() {
		return m_PvpInfo.feat;
	}

	// 扩地相关
	public void extendLand(int number) {
		m_Player.extendNumber += number;
		savePlayer(true);
	}

	public int getExtend() {
		return m_Player.extendNumber;
	}

	// 军团相关
	public int getBelegionId() {
		return m_Player.belong_legion;
	}

	public boolean IsInLegion() {
		return m_Player.belong_legion > 0;
	}

	public int getContributionMoneyNum() {
		return m_Player.contribution_money_num;
	}

	public void updatePlayerLegion(int legionid) {
		m_Player.belong_legion = legionid;
		m_PvpInfo.belong_legion = legionid;
		m_PvpInfo.save();
		m_Player.save();
	}

	// 引导相关的
	// 发送所有已完成的引导
	public void SendGuideArray() {
		ProGameInfo.Msg_G2C_GuideInfo.Builder builder = ProGameInfo.Msg_G2C_GuideInfo
				.newBuilder();
		for (int id : m_Player.finishedGuide) {
			builder.addFinishedGuide(id);
		}
		m_Con.sendPushMessage(builder.build());
	}

	// 完成了一个引导
	public void FinishGuide(int guideID) {
		m_Player.finishedGuide.add(guideID);
		savePlayer(true);
	}

	// 奖励相关的
	public int getContinueLoginDay() {
		return m_Player.continue_login_num;
	}

	public int getContinueLoginReward() {
		return m_Player.continue_login_reward;
	}

	// 领取指定的登陆奖励
	public void reciveLoginReward(MT_Data_LoginReward loginReward)
			throws Exception {
		int money = 10000 + 1000 * getContinueLoginDay();
		money = Math.min(money, 100000);
		int vip_effect = 1;
		if (m_Con.getPlayer().getVipLevel() >= loginReward.viplevel()) {
			vip_effect = 2;
		}

		if (m_Con.getItem().getItemCountByTableId(ITEM_ID.MONEY) + money
				* vip_effect > m_Con.getBuild().GetMaxMoneyCount()) {
			m_Con.ShowPrompt(PromptType.MONEY_FULL);
			return;
		}

		MT_Data_Item item = TableManager.GetInstance().TableItem()
				.GetElement(loginReward.itemId());
		if (item == null)
			return;

		List<UtilItem> list = TableManager.GetInstance().getDropOut(
				m_Con.getPlayer(), item.DropOut());
		for (UtilItem ui : list) {
			ui.setM_Count(ui.GetCount() * vip_effect);
		}
		Map<Integer, Integer> itemmap = Util.getUtilItemListToMap(list);
		int count = Util.getEqiepOrDesCount(itemmap);
		if (!m_Con.getItem().checkEquipFragCount(count))
			return;

		long s_num = IPOManagerDb.getInstance().getNextId();
		m_Player.continue_login_reward++;
		savePlayer(false);

		// 加金砖
		m_Con.getItem().setItemNumber(ITEM_ID.GOLD,
				loginReward.gold() * vip_effect, SETNUMBER_TYPE.ADDITION,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, s_num,
				"", Item_Channel.LOGIN_REWARD);

		// 加金币
		m_Con.getItem().setItemNumber(ITEM_ID.MONEY, money * vip_effect,
				SETNUMBER_TYPE.ADDITION, VmChannel.PurchaseGoods,
				ProductChannel.PurchaseGoods, s_num, "",
				Item_Channel.LOGIN_REWARD);

		// 加物品
		m_Con.getItem().setItemNumberArrayByUtilItemList(list,
				SETNUMBER_TYPE.ADDITION, VmChannel.PurchaseGoods,
				ProductChannel.PurchaseGoods, s_num, "",
				Item_Channel.LOGIN_REWARD);
		LogService.logEvent(m_Con.getPlayerId(), s_num, 1, loginReward.ID());
	}

	// 获取所有已领取的等级奖励ids
	public List<Integer> getAllLevRewardTableIds() {
		List<Integer> ids = new ArrayList<>();
		for (DatabaseLevReward databaseLevelReward : levRewards) {
			ids.add(databaseLevelReward.table_id);
		}
		return ids;
	}

	public Map<Integer, Integer> getLevelReward(MT_Data_LevelReward levReward) {
		Map<Integer, Integer> items = new TreeMap<Integer, Integer>();
		for (int i = 1;; ++i) {
			String str = "Reward" + i;
			Int2 reward;
			try {
				reward = (Int2) levReward.GetDataByString(str);
			} catch (Exception e) {
				reward = null;
			}
			if (reward == null || TABLE.IsInvalid(reward)) {
				break;
			}

			Integer oldcount = items.get(reward.field1());
			if (oldcount == null)
				oldcount = 0;
			items.put(reward.field1(), oldcount + reward.field2());
		}

		return items;
	}

	// 领取指定的等级奖励
	public void reciveLevelReward(Map<Integer, Integer> items, int rewardid) {
		long s_num = IPOManagerDb.getInstance().getNextId();
		for (Entry<Integer, Integer> pairs : items.entrySet()) {
			m_Con.getItem().setItemNumber(pairs.getKey(), pairs.getValue(),
					SETNUMBER_TYPE.ADDITION, VmChannel.PurchaseGoods,
					ProductChannel.PurchaseGoods, s_num, "",
					Item_Channel.LVL_REWARD);
		}

		LogService.logEvent(m_Con.getPlayerId(), s_num, 2, rewardid);

		DatabaseLevReward databaseLevReward = new DatabaseLevReward();
		databaseLevReward.player_id = m_Con.getPlayerId();
		databaseLevReward.table_id = rewardid;
		DatabaseInsertUpdateResult result = getDb().Insert(databaseLevReward);
		databaseLevReward.levReward_id = result.identity.intValue();
		levRewards.add(databaseLevReward);
		if (PlayerCache.isNeedIncr(m_Con.getPlayerId()))
			PlayerCache.setLvAward(m_Con.getPlayerId(), levRewards);
		else
			PlayerCache.addLvAward(m_Con.getPlayerId(), databaseLevReward);
	}

	// 获取所有可以领但没有领的等级奖励
	public List<Integer> getUnReciveLevRewardTableIds() {
		List<Integer> recivedLevIds = getAllLevRewardTableIds();
		List<Integer> unRciveIds = new ArrayList<Integer>();

		HashMap<Integer, MT_Data_LevelReward> map = TableManager.GetInstance()
				.TableLevelReward().Datas();
		for (Map.Entry<Integer, MT_Data_LevelReward> entry : map.entrySet()) {
			if (entry.getValue().lev() > m_Player.level)
				continue;
			if (!recivedLevIds.contains(entry.getKey()))
				unRciveIds.add(entry.getKey());
		}
		return unRciveIds;
	}

	// 同步player数据
	public void sendPlayerInfo() {
		Msg_G2C_AskPlayerInfo msg = buildPlayerInfo(m_Player, m_PvpInfo);
		m_Con.sendPushMessage(msg);
	}

	public static Msg_G2C_AskPlayerInfo buildPlayerInfo(
			DatabasePlayer dbPlayer, DatabasePvp_match dbPvpInfo) {
		ProGameInfo.Msg_G2C_AskPlayerInfo.Builder builder = ProGameInfo.Msg_G2C_AskPlayerInfo
				.newBuilder();
		builder.setPlayerId(dbPlayer.player_id);
		builder.setLevel(dbPlayer.level);
		builder.setName(dbPlayer.name);
		builder.setVipLv(dbPlayer.vipLevel);
		builder.setFeat(dbPvpInfo.feat);
		builder.setHead(dbPlayer.head);
		builder.setVipOverdueTime(dbPlayer.vipOverdueTime);
		builder.setFightingCount(dbPlayer.fightingcount);
		builder.setVictoryCount(dbPlayer.victorycount);
		builder.setNumber(dbPlayer.extendNumber);
		builder.setSelHero(dbPlayer.selhero);
		builder.setDefencepopulation(getDefensePopulation(dbPvpInfo));
		if (dbPvpInfo.shield_end_time > TimeUtil.GetDateTime())
			builder.setShieldTime((int) (dbPvpInfo.shield_end_time - TimeUtil
					.GetDateTime()));
		return builder.build();
	}

	// 体力相关的
	boolean UpdateCp() {
		int maxCp = m_Con.getCommon().GetValue(LIMIT_TYPE.CP);
		int curCp = m_Con.getItem().getItemCountByTableId(ITEM_ID.CP);
		if (curCp >= maxCp)
			return false;
		long nowTime = TimeUtil.GetDateTime();
		long pass_time = (nowTime - m_Player.cp_time);
		if (pass_time < Common.CP_RESTORE_SPEED)
			return false;
		int add_cp = (int) (pass_time / Common.CP_RESTORE_SPEED);
		curCp += add_cp;
		if (curCp >= maxCp)
			curCp = maxCp;
		m_Con.getItem().setItemNumber(ITEM_ID.CP, curCp, SETNUMBER_TYPE.SET,
				VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, -1, "",
				Item_Channel.CP_INC);
		UpdatePlayerCpTime();
		return true;
	}

	public void UpdatePlayerCpTime() {
		// 发送距离下次的时间
		m_Player.cp_time = TimeUtil.GetDateTime();
		sendCpTime();
		savePlayer(false);
	}

	// 升级经验相关
	public boolean CheckPlayerLevel(int level, boolean needNotify) {
		if (m_Player.level >= level)
			return true;

		if (needNotify)
			m_Con.ShowPrompt(PromptType.NEED_PLAYER_LEVEL, level);
		return false;
	}

	public MT_Data_Exp GetPlayerExpData() {
		return TableManager.GetInstance().TableExp().GetElement(m_Player.level);
	}

	public boolean LevelUp() {
		if(m_Player.level.intValue() >= Common.PLAYER_MAX_LEVEL.intValue())
			return false;
		int oldLeve = m_Player.level;
		++m_Player.level;
		m_PvpInfo.level = m_Player.level;
		savePlayer(true);
		m_Con.getTasks().CheckCanAcceptTask();
		m_Con.getHero().onPlayerLevelUp(true);
		sendLvlRewardMsg();
		if (m_Player.level.intValue() == Common.LUCKY_LOTTERY_LEVEL.intValue())
			sendTimeRewardMsg();
		if (m_Player.level.intValue() == Common.LUCKY_LOTTERY_LEVEL.intValue())
			ClientMessageHeroPath.getInstance()
					.sendAskHeroPathMsgToClint(m_Con);

		{
			MT_Data_Exp dataExp = TableManager.GetInstance().TableExp()
					.GetElement(m_Player.level);
			if (dataExp != null) {
				int curCp = m_Con.getItem().getItemCountByTableId(ITEM_ID.CP)
						+ dataExp.cp_reward();
				m_Con.getItem().setItemNumber(ITEM_ID.CP, curCp,
						SETNUMBER_TYPE.SET, VmChannel.PurchaseGoods,
						ProductChannel.PurchaseGoods, -1, "",
						Item_Channel.LVL_UP_CP);
				ClientMessageCommon.getInstance().UpdateCountInfo(m_Con,
						ProPvp.Proto_ActionType.CP,
						m_Con.getItem().getItemCountByTableId(ITEM_ID.CP),
						m_Con.getCommon().GetValue(LIMIT_TYPE.CP));
			}
		}

		LogService
				.logEvent(m_Player.player_id, -1, 24, oldLeve, m_Player.level);
		LogService
				.logPlayerLevelUp(m_Player.player_id, oldLeve, m_Player.level);
		return true;
	}

	// 初始化玩家英雄之路的数据
	public void initHeroPathData() {
		// 本关卡信息
		m_Player.hero_path_curPoint.clear();
		CustomHeroPathCurPoint curPoint = new CustomHeroPathCurPoint();
		curPoint.curPointId = 1;
		curPoint.cropLv = 0;
		curPoint.requestHeroId = 0;// 清空友方英雄
		m_Player.hero_path_curPoint.add(curPoint);

		// 设置敌方士兵
		m_Player.hero_path_formation.clear();
		m_Player.hero_path_enemy_crops.clear();
		ClientMessageHeroPath.getInstance().randomEnemy(getLevel(),
				1,
				m_Player.hero_path_formation,
				m_Player.hero_path_enemy_crops);

		// 已经打过的关卡,本方士兵列表，已经开启过的宝箱
		m_Player.hero_path_already_points.clear();
		m_Player.hero_path_crops.clear();
		m_Player.hero_path_recive_box.clear();
		m_Player.hero_path_recived_crop_table_ids.clear();
		savePlayer(false);
	}

	private void sendLvlRewardMsg() {
		ProReward.Msg_G2C_AskRewardInfo.Builder rsLv = ProReward.Msg_G2C_AskRewardInfo
				.newBuilder();
		rsLv.setType(Proto_RewardType.LEVEL_REWARD);
		rsLv.addAllList(m_Con.getPlayer().getUnReciveLevRewardTableIds());
		m_Con.sendReceiptMessage(rsLv.build());
	}

	private void sendTimeRewardMsg() {
		long now = TimeUtil.GetDateTime();
		// 上次领奖时间
		long previousTime = m_Con.getPlayer().getprevious_time();
		// 经过的时间
		long passTime = (now - previousTime) / 1000;
		MT_Data_Luckdraw mt_Data = TableManager.GetInstance().TableLuckdraw()
				.GetElement(m_Con.getPlayer().getluck_draw_num() + 1);
		ProReward.Msg_G2C_AskRewardInfo.Builder rs = ProReward.Msg_G2C_AskRewardInfo
				.newBuilder();
		rs.setType(Proto_RewardType.TIMER_REWARD);
		if (mt_Data == null)
			rs.setTime(-1);// 本日抽奖次数已满
		else
			rs.setTime((int) ((mt_Data.time() * 60 - passTime) <= 0 ? 0
					: (mt_Data.time() * 60 - passTime)));

		m_Con.sendReceiptMessage(rs.build());
	}

	// vip等级经验相关的
	public void AddPlayerVipExp(int exp, long cost_id, Item_Channel itemch) {
		m_Con.getItem().setItemNumber(ITEM_ID.VIP_EXP, exp,
				SETNUMBER_TYPE.ADDITION, VmChannel.PurchaseGoods,
				ProductChannel.PurchaseGoods, cost_id, "", itemch);
	}

	public MT_Data_Vip GetPlayerVipData() {
		return TableManager.GetInstance().TableVip()
				.GetElement(m_Player.vipLevel);
	}

	public boolean VipLevelUp() {
		if(m_Player.vipLevel>=Common.VIP_MAX_LEVEL.intValue())
			return false;
		++m_Player.vipLevel;
		if (m_Player.vipLevel >= 6)
			clearRewardInstanceCD();
		// 每次升级免费赠送一天使用期
		savePlayer(true);
		return true;
	}

	// vip是否有效
	public boolean IsVipValid() {
		return true;
	}

	// vip续费
	public void OpenVip(long millisecond) {
		if (IsVipValid())
			m_Player.vipOverdueTime += millisecond;
		else
			m_Player.vipOverdueTime = TimeUtil.GetDateTime() + millisecond;
		savePlayer(true);
	}

	public void setVipOverdueTime(long vipOverdueTime) {
		m_Player.vipOverdueTime = vipOverdueTime;
		savePlayer(false);
	}

	public long getVipOverdueTime() {
		return m_Player.vipOverdueTime;
	}
	
	public void incFeat(int feat) {
		m_Player.feat += feat;
		m_PvpInfo.feat += feat;
		savePlayer(true);
	}
	
	public void decFeat(int feat) {
		m_Player.feat -= feat;
		m_PvpInfo.feat -= feat;
		if (m_Player.feat < 0)
			m_Player.feat = 0;
		if (m_PvpInfo.feat < 0)
			m_PvpInfo.feat = 0;
		savePlayer(true);
	}

	// 返利基金相关
	public void setFundEndDay() {
		long now = TimeUtil.GetDateTime();
		if (now < m_Player.fund_end_day) {
			m_Player.fund_end_day = m_Player.fund_end_day + GameConfig.fund_day_count
					* Common.DAY_MILLISECOND;
		}
		else {
			m_Player.fund_end_day = now + GameConfig.fund_day_count
					* Common.DAY_MILLISECOND;
		}
		savePlayer(false);
	}

	public void setFundRecvDate(long recv_fund) {
		m_Player.last_recv_fund_day = recv_fund;
		savePlayer(false);
	}

	public long getFundRecvDate() {
		return m_Player.last_recv_fund_day;
	}

	public void syncFund() {
		int last_day = 0;
		int can_recv_gold = 0;
		boolean canFlush = false;
		long now = TimeUtil.GetDateTime();
		if (now < m_Player.fund_end_day) {
			last_day = TimeUtil.getDayDiff(now, m_Player.fund_end_day);
			can_recv_gold = GameConfig.fund_day_back_gold;

			if (ServerConfig.country.equals("US")) {
				if (TimeUtil.getDayDiff(now, m_Player.last_recv_fund_day) > 1)
					canFlush = true;
				else
					canFlush = TimeUtil.canFlush(m_Player.last_recv_fund_day,
							now);

				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(now);
				if (!canFlush && calendar.get(Calendar.HOUR_OF_DAY) >= 12)
					last_day--;
				if (last_day < 0)
					last_day = 0;
			} else {
				if (TimeUtil.IsSameDay(now, m_Player.last_recv_fund_day))
					last_day--;
				canFlush = TimeUtil
						.getDayDiff(now, m_Player.last_recv_fund_day) > 0;
			}
		}
		Msg_G2C_CanFund msg = Msg_G2C_CanFund.newBuilder()
				.setLastCount(last_day).setTodayCanRecv(canFlush)
				.setRecvGold(can_recv_gold).build();
		m_Con.sendPushMessage(msg);
	}

	// pvp相关
	public void MatchTarget(long target_id) {
		m_PvpInfo.match_player_id = target_id;
		savePlayer(false);
	}

	public DatabasePvp_match getPvpInfo() {
		return m_PvpInfo;
	}

	public boolean isPvpBeattacked() {
		if (m_PvpInfo.beattacked == 1) {
			m_PvpInfo.beattacked = 0;
			savePlayer(false);
			return true;
		}

		return false;
	}

	public static String getPlayerNameById(Long player_id) {
		Connection connection = ConnectionManager.GetInstance().GetConnection(
				player_id);
		if (connection == null)
			return (String) (DbMgr.getInstance().getDbByPlayerId(player_id)
					.selectOneField(DatabasePlayer.class, "player_id=?",
							"name", String.class, player_id));
		else
			return connection.getPlayer().getName();
	}

	public static Integer getPlayerLegionIdById(Long player_id) {
		Connection connection = ConnectionManager.GetInstance().GetConnection(
				player_id);
		if (connection == null)
			return (Integer) (DbMgr.getInstance().getDbByPlayerId(player_id)
					.selectOneField(DatabasePlayer.class, "player_id=?",
							"belong_legion", Integer.class, player_id));
		else
			return connection.getPlayer().getBelegionId();
	}

	private void ResetPlayerPvpCount() {
		m_Player.pvpNumber = m_Con.getCommon().GetValue(LIMIT_TYPE.PVP_NUMBER);
		savePlayer(false);
	}

	public int getPvpNumber() {
		return m_Player.pvpNumber;
	}

	public void addPvpNumber(int add) {
		m_Player.pvpNumber += add;
		savePlayer(false);
	}

	public int getPvpCount() {
		return m_Player.pvp_count;
	}

	public void incPvpCount() {
		m_Player.pvp_count++;
		savePlayer(false);
	}

	public void updatePvpNumber() {
		--m_Player.pvpNumber;
		m_Player.pvpTime = TimeUtil.GetDateTime();
		savePlayer(false);
	}

	public void addFightingCount(boolean win) {
		++m_Player.fightingcount;
		if (win == true)
			++m_Player.victorycount;
		savePlayer(true);
	}

	public void setPlayerFeat(int value, SETNUMBER_TYPE type) {
		if (type == SETNUMBER_TYPE.SET)
			m_PvpInfo.feat = value;
		else if (type == SETNUMBER_TYPE.ADDITION)
			m_PvpInfo.feat += value;
		else if (type == SETNUMBER_TYPE.MINUS)
			m_PvpInfo.feat -= value;
		if (m_PvpInfo.feat < 0)
			m_PvpInfo.feat = 0;
		int maxFeat=Util.getMaxFeat();
		//最大军衔处理
		if(m_PvpInfo.feat>maxFeat)
			m_PvpInfo.feat=maxFeat;
		m_Player.feat = m_PvpInfo.feat;
		savePlayer(true);
		return;
	}

	public boolean isFirstPvp() {
		if ((m_Player.gamestatusmask & 0x01) != 0)
			return false;
		return true;
	}

	public void maskFirstPvp() {
		m_Player.gamestatusmask = m_Player.gamestatusmask | 0x01;
		savePlayer(false);
	}

	// Pve相关的
	public int getinstance_id() {
		return m_Player.instance_id;
	}

	public List<CustomInstanceStar> getinstance_star_array() {
		return m_Player.instance_star_array;
	}

	public int GetInstanceCount(int instanceId) {
		for (CustomInstanceStar pair : m_Player.instance_star_array) {
			if (pair.id == instanceId)
				return pair.count;
		}
		return 0;
	}

	public CustomInstanceStar GetInstance(int instanceId) {
		for (CustomInstanceStar pair : m_Player.instance_star_array) {
			if (pair.id == instanceId)
				return pair;
		}
		return null;
	}

	// 是否打过指定关卡
	public boolean InstanceIdIsvalid(int instanceId) {
		for (CustomInstanceStar pair : m_Player.instance_star_array) {
			if (pair.id == instanceId)
				return true;
		}
		return false;
	}

	private int getSuperIndexByInstanceId(int instanceId) {
		return instanceId / 10000 - 1;
	}

	private boolean hasMaxCount(int id) {
		MT_Data_Instance data = TableManager.GetInstance().TableInstance()
				.GetElement(id);
		if (!TABLE.IsInvalid(data.MaxCount()))
			return true;

		return false;
	}

	public boolean IsMaxCount(int id) {
		if (!hasMaxCount(id))
			return false;

		MT_Data_Instance data = TableManager.GetInstance().TableInstance()
				.GetElement(id);
		CustomInstanceStar startinfo = GetInstance(id);
		if (startinfo.count >= data.MaxCount())
			return true;

		return false;
	}

	public void UpdatePlayerInstanceId(int id, int start) {
		CustomInstanceStar startinfo = GetInstance(id);
		if (startinfo == null)
			return;

		if (hasMaxCount(id))
			startinfo.count++;

		if (startinfo.starCount < start) {
			// 更新大关的星级
			UpdatePlayerAllStarCount(getSuperIndexByInstanceId(id), start
					- startinfo.starCount, SETNUMBER_TYPE.ADDITION);

			// 更新本关的星级
			startinfo.starCount = start;
		}

		int nextId = TableManager.GetInstance().getNextInstanceId(id);
		if (nextId > m_Player.instance_id) {
			m_Player.instance_id = nextId;
			CustomInstanceStar temp = GetInstance(nextId);
			if (temp == null)
				m_Player.instance_star_array.add(new CustomInstanceStar(nextId,
						0, 0, (byte) 0));
		}

		savePlayer(false);
	}

	public void repairPlayerInstanceId() {
		CustomInstanceStar temp = GetInstance(m_Player.instance_id);
		if (temp == null) {
			m_Player.instance_star_array.add(new CustomInstanceStar(
					m_Player.instance_id, 0, 0, (byte) 0));
			savePlayer(false);
		}
	}

	public List<CustomSuperior> getsuperiorInfo() {
		return m_Player.superiorInfo;
	}

	public void UpdatePlayerAllStarCount(int index, int count,
			SETNUMBER_TYPE type) {
		if (m_Player.superiorInfo.size() > index) {
			if (type == SETNUMBER_TYPE.ADDITION)
				m_Player.superiorInfo.get(index).starCount += count;
			else if (type == SETNUMBER_TYPE.MINUS)
				m_Player.superiorInfo.get(index).starCount -= count;
			else if (type == SETNUMBER_TYPE.SET)
				m_Player.superiorInfo.get(index).starCount = count;
		} else {
			m_Player.superiorInfo.add(new CustomSuperior(count, 0));
		}

		savePlayer(false);
	}

	public void UpdatePlayerAwardType(int id, int index, boolean bType) {
		int num = Util.setIntegerSomeBit(
				m_Player.superiorInfo.get(id).awardType, index, bType);
		m_Player.superiorInfo.get(id).awardType = num;
		savePlayer(false);
	}

	public boolean testPlayerAwardType(int id, int index) {
		if (id >= m_Player.superiorInfo.size())
			return false;
		return Util.getIntegerSomeBit(m_Player.superiorInfo.get(id).awardType,
				index) != 0;
	}

	// 获得副本的星数
	public int GetInstanceStar(int instanceId) {
		for (CustomInstanceStar pair : m_Player.instance_star_array) {
			if (pair.id == instanceId)
				return pair.starCount;
		}
		return 0;
	}

	// 返回某关卡的通关次数
	public int GetChallengeCount(int instanceId) {
		for (CustomInstanceStar pair : m_Player.instance_star_array) {
			if (pair.id == instanceId)
				return pair.count;
		}
		return 0;
	}

	// 护盾购买时间限制相关
	public void SetPlayerShieldEndTime(long time) {
		m_PvpInfo.shield_end_time = time;
		savePlayer(true);
	}

	public long getShieldEndTime() {
		return m_PvpInfo.shield_end_time;
	}

	// 转盘相关的
	public long getprevious_time() {
		return m_Player.previous_time;
	}

	public int getluck_draw_num() {
		return m_Player.luck_draw_num;
	}

	public int getluck_draw_free_num() {
		return m_Player.luck_draw_free_num;
	}

	public void luckDraw() {
		m_Player.previous_time = TimeUtil.GetDateTime();
		m_Player.luck_draw_num++;
		savePlayer(false);
	}

	public void setFirstNum() {
		m_Player.luck_draw_first_num = 1;
		savePlayer(false);
	}

	public int getLuckDrawFirstNum() {
		return m_Player.luck_draw_first_num;
	}

	public Boolean isFirstLuckDraw() {
		return m_Player.luck_draw_first_num == 0;
	}

	public boolean isTimeRewarding() {
		return isTimeRewarding;
	}

	public void setTimeRewarding(boolean isTimeRewarding) {
		this.isTimeRewarding = isTimeRewarding;
	}

	public void addReward(Integer tableId, Integer num) {
		utilItem = new UtilItem(tableId, num);
	}

	public UtilItem getReward() {
		return utilItem;
	}

	public void clearReward() {
		utilItem = null;
	}

	// 每天重置一次转盘相关的数据
	private void flushReward(long now) {
		m_Player.previous_time = now;
		m_Player.luck_draw_num = 0;
		savePlayer(false);
	}

	public int getFightingcount() {
		return m_Player.fightingcount;
	}

	public void setLuckVal(int luck) {
		m_Player.luck_val = luck;
		savePlayer(false);
	}

	public void setrefresh_time(Long sixteenTime) {
		m_Player.refresh_time = sixteenTime;
		savePlayer(false);
	}

	public void fushLegion_store_list(List<MT_Data_LegionStore> selecteds) {
		List<CustomLegionCommodity> commodities = getlegion_store_list();
		if (commodities != null)
			commodities.clear();
		for (MT_Data_LegionStore m : selecteds) {
			CustomLegionCommodity c = new CustomLegionCommodity();
			c.table_id = m.ID();
			c.is_buy = 0;
			commodities.add(c);
		}
		savePlayer(false);
	}

	public void setContributeMoneyNum(int num) {
		m_Player.contribution_money_num = num;
		savePlayer(false);
	}

	public boolean checkCount(Integer id, Integer maxCount) {
		List<CustomInstanceStar> instanceStars = m_Player
				.getInstance_star_array();
		CustomInstanceStar instanceStar = null;
		for (CustomInstanceStar customInstanceStar : instanceStars) {
			if (id == customInstanceStar.id)
				instanceStar = customInstanceStar;
		}
		if (instanceStar == null)
			return true;
		if (instanceStar.count < maxCount)
			return true;

		return false;
	}

	public void sendCpTime() {
		long time = TimeUtil.GetDateTime() - m_Player.cp_time;
		int leftTime = 0;
		int maxCp = m_Con.getCommon().GetValue(LIMIT_TYPE.CP);
		int curCp = m_Con.getItem().getItemCountByTableId(ITEM_ID.CP);
		if (curCp < maxCp)
			leftTime = Common.CP_RESTORE_SPEED / 1000 - (int) time / 1000;

		ProFight.Msg_G2C_Cp.Builder msg = ProFight.Msg_G2C_Cp.newBuilder();
		msg.setTime(leftTime);
		m_Con.sendReceiptMessage(msg.build());
	}

	public ProSee.Msg_G2C_SeePlayerInfo buildSeeData() throws Exception {
		ProSee.Msg_G2C_SeePlayerInfo.Builder seemsg = Msg_G2C_SeePlayerInfo
				.newBuilder();
		{
			DatabasePlayer dbPlayer = m_Player;
			DatabasePvp_match dbPvpInfo = m_PvpInfo;

			ProGameInfo.Msg_G2C_AskPlayerInfo msg = ConPlayerAttr
					.buildPlayerInfo(dbPlayer, dbPvpInfo);
			seemsg.setPlayerInfo(msg);

			Collection<BuildInfo> builds = m_Con.getBuild().getBuildArray();
			for (BuildInfo bui : builds)
				seemsg.addBuildInfo(ConBuildAttr.GetProtoData(bui.getM_Build()));

			seemsg.setExp(m_PvpInfo.exp);
			seemsg.setFightValue(dbPlayer.getFight_value());
		}

		return seemsg.build();
	}

	public void sendNoticeList() {
		List<DatabaseNotice> noticelist = DbMgr.getInstance().getShareDB()
				.Select(DatabaseNotice.class, "1=1 order by notice_id desc");
		if (noticelist.isEmpty())
			return;

		Msg_G2C_PublicInfo.Builder list = Msg_G2C_PublicInfo.newBuilder();
		for (DatabaseNotice notice : noticelist) {
			Proto_PublicInfo.Builder info = Proto_PublicInfo.newBuilder();
		//	info.setTitle(notice.title);
		//	info.setContext(notice.content);
			info.setId(notice.notice_id);
			info.setUpdateTime(notice.time.getTime());
			list.addInfo(info);
		}
		list.setIsLogin(1);
		m_Con.sendReceiptMessage(list.build());
	}

	public boolean isRefuseChat() {
		return isRefuseChat(m_Player);
	}
	public boolean isRefuseChat(DatabasePlayer m_Player) {
		return m_Player.refuse_chat == 1
				&& TimeUtil.GetDateTime() < m_Player.refuse_chat_time;
	}

	public long getReuseChatTime() {
		return m_Player.refuse_chat_time;
	}

	public void refuseChat(Proto_Enum_Refuse_ChatType type, Long endtime)
			throws ParseException {
		if (type == Proto_Enum_Refuse_ChatType.FREE) {
			// 解言
			m_Player.refuse_chat = 0;
			// m_Player.refuse_chat_time = getDefatTime("1980-11-11 00:00:00");
		}

		else if (type == Proto_Enum_Refuse_ChatType.REFUSE) {
			// 禁言
			m_Player.refuse_chat = 1;
			m_Player.refuse_chat_time = endtime;
		}
		DatabaseUtil databaseUtil = DbMgr.getInstance().getDbByPlayerId(
				m_Player.player_id);
		databaseUtil.Update(m_Player, "player_id = ? ", m_Player.player_id);
	}
	
	public void updateRefuseChatState()
	{
		updateRefuseChatState(m_Player);
	}
	/**更新禁言状态和举报数据*/
	public void updateRefuseChatState(DatabasePlayer m_Player)
	{
		if(TimeUtil.GetDateTime() > m_Player.refuse_chat_time)
		{
			m_Player.refuse_chat = 0;	
			if(m_Player.chatReport==null||m_Player.chatReport.size()==0)
				return;
			long time=TimeUtil.GetDateTime()-60*60*1000;
			if(time>m_Player.chatReport.get(m_Player.chatReport.size()-1).time)
			{
				m_Player.chatReport.removeAll(m_Player.chatReport);
				return ;
			}
			Iterator<CustomChatReport> chatReports=m_Player.chatReport.iterator();
			while(chatReports.hasNext())
			{
				CustomChatReport chatReport=chatReports.next();
				if(time<chatReport.time)
					break;
				
				chatReports.remove();
			}
		}
	}

	public void setPvpMatchExp(int exp) {
		m_PvpInfo.exp = exp;
		savePlayer(false);
	}

	public boolean isFirstPve(int instanceId) {
		for (CustomInstanceStar instance_star : m_Player.instance_star_array) {
			if (instance_star.id == instanceId && instance_star.starCount == 0)
				return true;
		}
		return false;
	}

	public boolean isLastIdInRoom(int instanceId) {
		if (instanceId % 10 == 9)
			return true;
		return false;
	}

	public boolean isFree() {
		return m_Player.free_num < Common.max_money_free_luck_draw;
	}

	public boolean freeTime() {
		return TimeUtil.GetDateTime() >= m_Player.free_time;
	}

	public void setFreeNumAndTime() {
		m_Player.free_num++;
		m_Player.free_time = TimeUtil.GetDateTime() + 15 * 60 * 1000;
		savePlayer(false);
	}

	public boolean isFreeGold() {
		return TimeUtil.GetDateTime() >= m_Player.free_gold_time;
	}

	public void setGoldTime() {
		m_Player.free_gold_time = TimeUtil.GetDateTime() + 3
				* Common.DAY_MILLISECOND;
		savePlayer(false);
	}

	public int getFreeMoneyNum() {
		return m_Player.free_num;
	}

	public long getFreeMoneyTime() {
		return m_Player.free_time;
	}

	public Long getFreeGoldTime() {
		return m_Player.free_gold_time;
	}

	public int getFreeGoldLeftTimeSecond() {
		long curTime = TimeUtil.GetDateTime();
		if (curTime < m_Player.free_gold_time)
			return (int) ((m_Player.free_gold_time - curTime) / 1000);
		else
			return 0;
	}

	public int getFreeMoneyLeftTimeSecond() {
		if (!isFree())
			return 0;

		long curTime = TimeUtil.GetDateTime();
		if (curTime < m_Player.free_time)
			return (int) ((m_Player.free_time - curTime) / 1000);
		else
			return 0;
	}

	public boolean isHeroPathInit() {
		return !m_Player.hero_path_curPoint.isEmpty();
	}

	public Integer getCurHeroPathPointId() {
		if (!isHeroPathInit())
			return 0;
		return m_Player.hero_path_curPoint.get(0).curPointId;
	}

	public void addAlreadyPoints(int star) {
		m_Player.hero_path_already_points.add(new CustomHeroPathAlreadyPoint(
				getCurHeroPathPointId(), star));
		savePlayer(false);
	}

	public void updateCurPoint(int nextid) {
		m_Player.hero_path_curPoint.get(0).curPointId = nextid;// 修改为下一个关卡
		m_Player.hero_path_cur_self_formation.clear();// 当前关卡阵型清空
		savePlayer(false);
	}

	public Integer getStarFromAlreadyPoints() {
		int totalStar = 0;
		for (CustomHeroPathAlreadyPoint alreadyPoint : m_Player.hero_path_already_points)
			totalStar += alreadyPoint.starCount;
		return totalStar;
	}

	public void heroPathCropLvUp(Integer totalStar) {
		int lv = getSelfCropLvByStar(totalStar);
		m_Player.hero_path_curPoint.get(0).cropLv = lv;
		savePlayer(false);
	}

	private int getSelfCropLvByStar(Integer totalStar) {
		int cropLv = 1;
		while (true) {
			MT_Data_HeroPathConfig config = TableManager.GetInstance()
					.TableHeroPathConfig().GetElement(cropLv);
			if (config == null)
				break;
			if (TABLE.IsInvalid(config.starLv()))
				break;
			if (totalStar < config.starLv()) {
				cropLv = config.index();
				break;
			}
			++cropLv;
		}
		return cropLv;
	}

	public int getSelfCropLv() {
		if (!isHeroPathInit())
			return 0;
		return m_Player.hero_path_curPoint.get(0).cropLv;
	}

	public void addCrop(Integer cropTableId) {
		// 这里的等级没有用，本方士兵等级在当前关卡信息单独维护，所以兵种都是一个等级
		m_Player.hero_path_crops.add(new CustomHeroPathCrop(cropTableId, 0,
				getCropNumByTableId(cropTableId)));
		savePlayer(false);
	}

	public void setEnemyLv(List<CustomHeroPathCrop> customHeroPathCrops) {
		m_Player.hero_path_enemy_crops = customHeroPathCrops;
		savePlayer(false);
	}

	// 取得所以已经开启的宝箱数组
	public List<Integer> getReciveBoxes() {
		return m_Player.hero_path_recive_box;
	}

	// 获得本方士兵等级列表
	public List<CustomHeroPathCrop> getSelfHeroPathCrops() {
		return m_Player.hero_path_crops;
	}

	// 获得敌方士兵等级列表
	public List<CustomHeroPathCrop> getEnemyHeroPathCrops() {
		return m_Player.hero_path_enemy_crops;
	}

	// 获得当前关卡信息
	public CustomHeroPathCurPoint getCurHeroPathCurPointInfo() {
		return m_Player.hero_path_curPoint.get(0);
	}

	// 获得已经打关卡列表
	public List<CustomHeroPathAlreadyPoint> getAlreadyPoints() {
		return m_Player.hero_path_already_points;
	}

	// 获得敌方阵型
	public List<database.CustomFormation> getEnemyFom() {
		return m_Player.hero_path_formation;
	}

	public boolean haveThisCrop(Integer cropId) {
		for (CustomHeroPathCrop crop : m_Player.hero_path_crops) {
			if (crop.cropTableId == cropId)
				return true;
		}
		return false;
	}

	public void addCropNum(Integer cropTableId) {
		// 1:计算士兵的数量
		int cropNum = getCropNumByTableId(cropTableId);

		// 2:已经存在此类型的士兵，就把数量变大
		for (CustomHeroPathCrop crop : m_Player.hero_path_crops) {
			if (crop.cropTableId == cropTableId) {
				crop.num += cropNum;
				break;
			}
		}
		savePlayer(false);
	}

	private int getCropNumByTableId(Integer cropTableId) {
		Integer curId = m_Player.getHero_path_curPoint().get(0).curPointId;
		int cropNum = 0;
		MT_Data_HeroPath heroPath = TableManager.GetInstance().TableHeroPath()
				.GetElement(curId);
		ArrayList<Int2> selfList = heroPath.selfCrop();
		if (selfList == null || selfList.isEmpty())
			logger.error("heroPath 表本方士兵选择配置错误");

		for (Int2 int2 : selfList) {
			if (int2.field1().equals(cropTableId)) {
				cropNum = int2.field2();
				break;
			}
		}
		return cropNum;
	}

	// 更新敌方士兵相关信息
	public void updateHeroPathEnemy() {
		ClientMessageHeroPath.getInstance().randomEnemy(getLevel(),
				getCurHeroPathPointId(),
				m_Player.hero_path_formation,
				m_Player.hero_path_enemy_crops);
		savePlayer(false);
	}

	// 设置邀请英雄Id到本关卡信息中
	public void setRequestHeroId(Integer hero_id, long player_id) {
		CustomHeroPathCurPoint curPoint = m_Player.hero_path_curPoint.get(0);
		curPoint.requestHeroId = hero_id;
		curPoint.requestPlayerId = player_id;
		savePlayer(false);
	}

	// 返回邀请的英雄id
	public int getRequestHeroId() {
		if (!isHeroPathInit())
			return 0;

		return m_Player.hero_path_curPoint.get(0).requestHeroId;
	}

	public long getRequestPlayerId() {
		if (!isHeroPathInit())
			return 0;

		return m_Player.hero_path_curPoint.get(0).requestPlayerId;
	}

	public void updateCurPointForMation(
			List<database.CustomFormation> customFormations) {
		m_Player.hero_path_cur_self_formation = customFormations;
		savePlayer(false);
	}

	public void updateReciveBosex(Integer boxId) {
		m_Player.hero_path_recive_box.add(boxId);
		savePlayer(false);
	}

	public boolean isRecivedThisBox(Integer boxId) {
		return m_Player.hero_path_recive_box.contains(boxId);
	}

	public boolean isFirstPoint() {
		return getCurHeroPathPointId() == 1;
	}

	public boolean isLastPoint() {
		return getCurHeroPathPointId() == -1;
	}

	public int getSelfCropNumByTableId(Integer cropTableId) {
		for (CustomHeroPathCrop crop : m_Player.hero_path_crops) {
			if (crop.cropTableId == cropTableId)
				return crop.num;
		}

		return 0;
	}

	public boolean thisPointIsRecived() {
		if (m_Player.hero_path_recived_crop_table_ids.isEmpty())
			return false;

		return m_Player.hero_path_recived_crop_table_ids
				.contains(getCurHeroPathPointId());
	}

	public void addThisPointToRecived() {
		// 把当前关卡id加入已经领取士兵列表
		m_Player.hero_path_recived_crop_table_ids.add(getCurHeroPathPointId());
		savePlayer(false);
	}

	// 某一个关
	public CustomHeroPathAlreadyPoint getPreviousPointStar(Integer boxId) {
		int pointId = boxId - 1;// 宝箱Id-1就等于上一关的关卡ID
		for (CustomHeroPathAlreadyPoint pathAlreadyPoint : m_Player
				.getHero_path_already_points()) {
			if (pathAlreadyPoint.id == pointId)
				return pathAlreadyPoint;
		}
		return null;
	}

	public boolean isHaveNextPoint(Integer nextid) {
		if (!TABLE.IsInvalid(nextid))
			return true;
		return false;
	}

	public void sendHeroPathNum() {
		Msg_G2C_doSthNum.Builder msg = Msg_G2C_doSthNum.newBuilder();

		Proto_CountInfo.Builder builder = Proto_CountInfo.newBuilder();
		builder.setDoNum(m_Player.hero_path_num);
		builder.setMaxNum(1);// 目前最大值设置为1次,一天每个玩家都只能刷新一次
		builder.setType(Proto_ActionType.HEROPATH_COUNT);
		msg.addInfo(builder.build());

		m_Con.sendPushMessage(msg.build());
	}

	public void incrHeroPathNum() {
		++m_Player.hero_path_num;
		if (m_Player.hero_path_num > 1)
			m_Player.hero_path_num = 1;
	}

	public boolean canFush() {
		return m_Player.hero_path_num == 0;
	}

	public boolean isAladyFuck(Integer pointId) {
		for (CustomHeroPathAlreadyPoint pathAlreadyPoint : m_Player.hero_path_already_points) {
			if (pointId.equals(pathAlreadyPoint.id))
				return true;
		}
		return false;
	}

	public void setLegionInfo(int id, int level) {
		legion = new DatabaseLegion();
		legion.legion_id = id;
		legion.level = level;
	}

	public void updateLegionInfo(int newLv) {
		if (legion != null)
			legion.level = newLv;
	}

	public void updateFushNum(int fushNum) {
		m_Player.hero_path_num = fushNum;
	}

	public boolean isPvePass(Integer instanceId) {
		for (CustomInstanceStar customInstanceStar : m_Player.instance_star_array) {
			if (instanceId.equals(customInstanceStar.id)
					&& customInstanceStar.starCount != 0)
				return true;
		}
		return false;
	}

	public int getRewardCurId() {
		return m_Player.reward_cur_id;
	}

	public long getRewardLastPlayTime() {
		return m_Player.reward_last_play_time;
	}

	public int getRewardPlayCD() {
		long next = m_Player.reward_last_play_time + Common.REWARD_INSTANCE_CD * 1000;
		int cd = (int) ((next - TimeUtil.GetDateTime())/1000);
		if (cd < 0)
			cd = 0;
		return cd;
	}

	public List<database.CustomFormation> getRewardEnemyFormation() {
		return m_Player.reward_enemy_formation;
	}

	public List<CustomFormationCrops> getRewardEnemyCrops() {
		return m_Player.reward_enemy_crops;
	}

	public boolean isPlayRewardInstanceId(int id) {
		MT_Data_WantedInstance wantedInstance = TableManager.GetInstance()
				.TableWantedInstance().GetElement(id);
		if (wantedInstance == null)
			return false;
		if (id != m_Player.reward_cur_id)
			return false;
		if (!m_Con.getPlayer().isPvePass(wantedInstance.getM_nopenid()))
			return false;
		return true;
	}

	public void addRewardInstanceId() {
		int count = TableManager.GetInstance().TableWantedInstance().Count();
		m_Player.reward_cur_id++;
		if (m_Player.reward_cur_id > count)
		{
			m_Player.reward_cur_id = 0;//表示已打通
		}
		else
		{
			if (m_Player.vipLevel < 6)
				m_Player.reward_last_play_time = TimeUtil.GetDateTime();
			randomRewardEnemy();
		}
		m_Con.getTasks().AddTaskNumber(TASK_TYPE.WANTED_PASS, m_Player.reward_cur_id, 1, 0);
		savePlayer(false);
	}

	public void resetRewardInfo(boolean isLogin) {
		m_Player.reward_cur_id = 1;
		long oldTime = m_Player.reward_last_play_time;
		m_Player.reward_last_play_time = TimeUtil.GetDateTime() - Common.REWARD_INSTANCE_CD * 1000;
		randomRewardEnemy();
		int year = TimeUtil.GetCalendar(oldTime).get(Calendar.YEAR);
		if (year != 1980) {
			sendWantedData(isLogin);
		}
	}
	
	public void clearRewardInstanceCD() {
		m_Player.reward_last_play_time = TimeUtil.GetDateTime() - Common.REWARD_INSTANCE_CD * 1000;
		sendWantedData(false);
	}

	public void randomRewardEnemy() {
		ClientMessageWanted.getInstance().randomEnemy(getLevel(),
				getRewardCurId(),
				m_Player.reward_enemy_formation,
				m_Player.reward_enemy_crops);
	}

	public List<UtilItem> randomRewardItem() throws Exception {
		MT_Data_WantedInstance wantedInstance = TableManager.GetInstance()
				.TableWantedInstance().GetElement(m_Player.reward_cur_id);
		HeroInfo hero = m_Con.getHero().getRandomHero();
		if (hero != null)
		{
			int lv = m_Con.getPlayer().getLevel();
			List<UtilItem> list = new ArrayList<>();
			for (Int2 dropinfo : wantedInstance.dropOut()) {
				if (dropinfo.field1() == 0
						|| hero.getTableId() == dropinfo.field1()) {
					list.addAll(TableManager.GetInstance().getDropOut(lv,
							dropinfo.field2()));
				}
			}
			return list;
		}
		return null;
	}

	public void sendWantedData(boolean isLogin) {
		int year = TimeUtil.GetCalendar(m_Player.reward_last_play_time).get(Calendar.YEAR);
		if (year == 1980) {
			resetRewardInfo(isLogin);
		}
		
		if (isLogin)
			return ;
			
		Msg_G2C_WantedData.Builder builder = Msg_G2C_WantedData.newBuilder();
		builder.setCurId(getRewardCurId());
		builder.setTime(getRewardPlayCD());
		MT_Data_WantedInstance wantedInstance = TableManager.GetInstance()
				.TableWantedInstance().GetElement(m_Player.reward_cur_id);
		if(wantedInstance==null)
			builder.setType(false);
		else
		     builder.setType(m_Con.getPlayer().isPvePass(
				wantedInstance.getM_nopenid()));
		builder.setFirst(false);
		m_Con.sendPushMessage(builder.build());
	}

	public void openRewardInstance(int instanceId) {
		MT_Data_WantedInstance wantedInstance = TableManager.GetInstance()
				.TableWantedInstance().GetElement(m_Player.reward_cur_id);
		if (wantedInstance == null)
			return;
		if (wantedInstance.getM_nopenid() == instanceId) {
			Msg_G2C_WantedData.Builder builder = Msg_G2C_WantedData
					.newBuilder();
			builder.setCurId(getRewardCurId());
			builder.setTime(getRewardPlayCD());
			builder.setType(m_Con.getPlayer().isPvePass(
					wantedInstance.getM_nopenid()));
			builder.setFirst(false);
			m_Con.sendPushMessage(builder.build());
		}
	}

	public void getRechargeCache() {
		List<DatabaseRecharge_cache> list = DbMgr
				.getInstance()
				.getShareDB()
				.Select(DatabaseRecharge_cache.class, "player_id=? and mask=0",
						getPlayerId());
		if (list.isEmpty())
			return;

		for (DatabaseRecharge_cache ca : list) {
			MT_Data_Recharge1 data = TableManager.GetInstance().getSpawns(MT_TableManager.Recharge1.Recharge1_360).GetElement(ca.item_id);
			if (data == null) {
				logger.error("充值失败，玩家[{}]企图充值一个不存在的recharge配置  {}",
						m_Con.getPlayerId(), ca.item_id);
				continue;
			}

			ClientMessageRecharge.getInstance().pay(m_Con, ca.pay_order,
					ca.item_id, 1, data, false);
			ca.mask = 1;
			ca.save();
		}
	}

	/** 修正关卡信息 */
	private void updateCustomSuperior() {
		if (m_Player == null)
			return;
		int instanceId = TableManager.GetInstance().TablePlayerConfig()
				.GetElement(0).InstanceId();// 初始化关卡ID--- 下面会用作当前已经处理到滴关卡ID
		int count=getCount(instanceId, m_Player.instance_id);
		int customId = m_Player.instance_id;// 最高关卡ID
		if(count==m_Player.instance_star_array.size())
			return ;
		int nowCustomId = 0;// 当前处理关卡滴下一关卡
		if (m_Player.instance_star_array == null)
			m_Player.instance_star_array = new ArrayList<CustomInstanceStar>();
		List<CustomInstanceStar> temps = m_Player.instance_star_array;// 当前存在滴数据
		List<CustomInstanceStar> newArray = new ArrayList<CustomInstanceStar>();
		CustomInstanceStar custom = null;
		// 处理 初始化关卡到 现在存在滴第一个关卡数据
		if (temps == null || temps.size() == 0) {
			addCustomData(newArray, instanceId, customId);
		} else {
			addCustomData(newArray, instanceId, temps.get(0).id);
			// 处理现在存滴all关卡滴数据
			int size = temps.size();
			for (int i = 0; i < size; i++) {
				custom = temps.get(i);
				nowCustomId = TableManager.GetInstance().getNextInstanceId(
						custom.id);
				if (nowCustomId >= customId) {
					newArray.add(custom);
					if((i + 1) < size)
						newArray.add(temps.get(i+1));
					break;
				}
				if ((i + 1) < size) {
					CustomInstanceStar custom1 = temps.get(i + 1);
					if (nowCustomId == custom1.id) {
						newArray.add(custom);
						continue;
					} else {
						newArray.add(custom);
						addCustomData(newArray, nowCustomId, custom1.id);
					}
				} else {
					newArray.add(custom);
					addCustomData(newArray, nowCustomId, customId);
				}
			}
		}
		m_Player.instance_star_array = newArray;
		updateStar();
	}

	/** 生成这两个ID之间滴关卡数据 然后 添加到集合中 */
	private void addCustomData(List<CustomInstanceStar> newArray, int nowId,
			int nextId) {

		if (nowId == nextId)
			return;

		for (; nowId < nextId;) {
			newArray.add(new CustomInstanceStar(nowId, 1, 0, (byte) 0));
			nowId = TableManager.GetInstance().getNextInstanceId(nowId);
		}

	}

	/** 更新Star */
	private void updateStar() {

		if (getinstance_star_array() != null) {

			HashMap<Integer, Integer> temp = new HashMap<>();
			// 计算一次 STAR
			int index;
			Integer val = null;
			for (CustomInstanceStar pair : getinstance_star_array()) {
				index = getSuperIndexByInstanceId(pair.id);
				val = temp.get(index);
				if (val == null)
					temp.put(index, pair.starCount);
				else
					temp.put(index, val + pair.starCount);
			}
			// 放入大关卡数据
			Set<Integer> keys = temp.keySet();
			for (Integer key : keys) {
				if (key < m_Player.superiorInfo.size()) {
					m_Player.superiorInfo.get(key).starCount = temp.get(key);
				} else {
					m_Player.superiorInfo.add(new CustomSuperior(temp.get(key),
							0));
				}
			}

		}
	}

	/** 获取已经通关滴关卡数量 */
	private int getCount(int initId, int maxId) {
		int num = 0;
		int nextId=0;
		for (; initId <= maxId;) {
			nextId=TableManager.GetInstance().getNextInstanceId(initId);
			if(initId==nextId)
			{
				m_Player.instance_id=nextId;
				return num;
			}
			initId=nextId;
			++num;
		}
		return num;
	}
	/**
	 * 玩家离线时被举报
	 * @param playerId 被举报玩家ID
	 * @param reportPid  举报玩家ID
	 */
	public int ChatReport(long playerId)
	{
		DatabasePlayer player=null;
		Connection con=ConnectionManager.GetInstance().GetConnection(playerId);
		DatabaseUtil db=DbMgr.getInstance().getDbByPlayerId(playerId);
		if(con!=null)
		     player=con.getPlayer().m_Player;
		if(player==null)
	    	player=db.SelectOne(DatabasePlayer.class,"player_id = ?",playerId);
		if(player==null)
			return 2;
		updateRefuseChatState(player);
		int result=addChatReoirt(player);
		if(result==0)
			db.Update(player, "player_id = ?",playerId);
		return result;
	}
	
	/**
	 *  添加聊天举报数据
	 * @param plyaer 被举报玩家
	 * @param reportPid 举报玩家ID
	 * @return  0成功 1 已经举报 2玩家不存在 3 禁言中
	 */
	public int addChatReoirt(DatabasePlayer player)
	{
		DatabasePlayer m_Player=player;
		//如果传入滴是NULL那么就是该玩家在线
		if(m_Player==null)
			m_Player=this.m_Player;
		long time=TimeUtil.GetDateTime();
		if(isRefuseChat(m_Player))
			return 3;
		long reportPid = getPlayerId();
		if(m_Player.chatReport==null)
			m_Player.chatReport=new ArrayList<CustomChatReport>();
	    else
	     {
	    	for(CustomChatReport chatReport:m_Player.chatReport)
	    	{
	    		if(chatReport.id == reportPid)
	    			return 1;
	    	}
	      }
		CustomChatReport chatReport=new CustomChatReport(reportPid,time);
		m_Player.chatReport.add(chatReport);
		
		DatabaseChatReport report=new DatabaseChatReport();
		report.player_id=getPlayerId();
		report.reported_player_id=m_Player.getPlayer_id();
		report.time=time;
		report.cur_reported_count = m_Player.chatReport.size();
		report.player_name = getName();
		report.reported_player_name = m_Player.name;
		
		DbMgr.getInstance().getShareDB().Insert(report);
		refuseChatTime(m_Player, time);
		return 0;
	}
	
	/**是否禁言计算*/
	private void refuseChatTime(DatabasePlayer m_Player,long time)
	{
		if(m_Player.chatReport.size() >= Common.CHAT_REPORT_PERSON_NUM)
		{
			m_Player.refuse_chat=1;
			m_Player.refuse_chat_time=	time+60*60*1000;
			DatabaseMail mail= m_Con.getMails().createChatWarningMail(m_Player);
			ConMailAttr.sendSystemMail(mail);
		}

	}
	
	public boolean isFirstPay() {
		return (m_Player.pay_count==0);
	}
	
	public void addPayCount() {
		m_Player.pay_count++;
		savePlayer(false);
	}
}
