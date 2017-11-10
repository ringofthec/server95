package gameserver.pushevent;

import java.util.Collection;

import com.commons.util.TimeUtil;

import commonality.Common;
import commonality.ITEM_ID;
import commonality.PUSH_EVENT_TYPE;
import commonality.Common.LIMIT_TYPE;
import databaseshare.DatabasePush_event;

import gameserver.connection.attribute.info.BuildInfo;
import gameserver.network.protos.game.CommonProto.Proto_BuildState;
import gameserver.network.server.connection.Connection;
import gameserver.utils.DbMgr;

public class PushEventService {
	static PushEventService instance = new PushEventService();
	public static PushEventService getInstance() { return instance; }
	private PushEventService() {
	}
	
	public void pushAllEvent(Connection con) {
		try {
			con.getBuild().checkAllBuild();

			pushCpEvent(con);
			pushCorpEvent(con);
			pushUpgradeEvent(con);
			pushTechEvent(con);
			pushInstiteEvent(con);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clearAllPush(Connection con) {
		DbMgr.getInstance().getShareDB().Delete(DatabasePush_event.class, "player_id=?", con.getPlayerId());
	}
	
	public void pushPvpFuck(Connection con) {
		InsertNewEvent(con.getPlayerId(), PUSH_EVENT_TYPE.FUCK_WITH_PVP, 10 * 1000, con.getRegistration(), con.getPlayer().getLanguage());
	}
	
	private void InsertNewEvent(long playerid, int event, long timeDelay, String registration_id, int languageId) {
		DatabasePush_event eventdb = new DatabasePush_event();
		eventdb.player_id = playerid;
		eventdb.event_type = event;
		eventdb.notify_time = TimeUtil.GetDateTime() + timeDelay + 60 * 1000;
		eventdb.registration_id = registration_id;
		eventdb.language = languageId;
		DbMgr.getInstance().getShareDB().Insert(eventdb);
	}
	
	private void pushCpEvent(Connection con) {
		int maxCp = con.getCommon().GetValue(LIMIT_TYPE.CP);
		int curCp = con.getItem().getItemCountByTableId(ITEM_ID.CP);
		if (curCp >= maxCp)
			return ;

		int addCp = maxCp - curCp;
		long need_pass_time = addCp * Common.CP_RESTORE_SPEED - con.getPlayer().getCpTime();
		
		if (need_pass_time <= 0)
			return ;

		// 体力恢复满的推送
		InsertNewEvent(con.getPlayerId(), PUSH_EVENT_TYPE.CP_RECOVE_FULL, need_pass_time, con.getRegistration(), con.getPlayer().getLanguage());
	}
	
	public void pushCorpEvent(Connection con) throws Exception {
		long maxTime = 0;
		Collection<BuildInfo> builds = con.getBuild().getBuildArray(); 
		for (BuildInfo build : builds) {
			long time = build.getCorpFinishNeedTime();
			if (time == 0)
				continue;
			
			if (time > maxTime)
				maxTime = time;
		}
		
		if (maxTime <= 0)
			return ;
		
		// 兵种制造完成的推送
		InsertNewEvent(con.getPlayerId(), PUSH_EVENT_TYPE.ALL_CORP_READY, maxTime, con.getRegistration(), con.getPlayer().getLanguage());
	}
	
	// 建筑升级和建筑完成
	public void pushUpgradeEvent(Connection con) throws Exception {
		long nowTime = TimeUtil.GetDateTime();
		Collection<BuildInfo> builds = con.getBuild().getBuildArray();
		for (BuildInfo build : builds) {
			if (build.getState() == Proto_BuildState.NONE_BUILD)
				continue;
			
			long time = build.calcUpgradeLeftNeedTimeBySecond(nowTime) * 1000;
			if (time <= 0)
				continue;
			
			// 插入建筑升级完成，建造完成的通知事件
			if (build.getLevel() == 0)
				InsertNewEvent(con.getPlayerId(), PUSH_EVENT_TYPE.BUILD_IS_READY, time, con.getRegistration(), con.getPlayer().getLanguage());
			else
				InsertNewEvent(con.getPlayerId(), PUSH_EVENT_TYPE.BUILD_UPGRADE_IS_READY, time, con.getRegistration(), con.getPlayer().getLanguage());
		}
	}
	
	public void pushTechEvent(Connection con) throws Exception {
		Collection<BuildInfo> builds = con.getBuild().getBuildArray();
		for (BuildInfo build : builds) {
			long time = build.getTechLeftNeedTimeByMilliSecond();
			if (time <= 0)
				continue;
			
			// 插入科技升级完成通知事件
			InsertNewEvent(con.getPlayerId(), PUSH_EVENT_TYPE.TECH_UPGRADE_IS_READY, time, con.getRegistration(), con.getPlayer().getLanguage());
		}
	}
	
	public void pushInstiteEvent(Connection con) throws Exception {
		Collection<BuildInfo> builds = con.getBuild().getBuildArray();
		for (BuildInfo build : builds) {
			long time = build.getInstituteFinishLeftTimeByMilliSecond();
			if (time <= 0)
				continue;
			
			// 插入兵种升级完成通知事件
			InsertNewEvent(con.getPlayerId(), PUSH_EVENT_TYPE.CORP_UPGRADE_IS_READY, time, con.getRegistration(), con.getPlayer().getLanguage());
		}
	}
}
