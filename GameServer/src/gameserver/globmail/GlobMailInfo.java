package gameserver.globmail;

import gameserver.utils.DbMgr;

import java.util.HashMap;

import commonality.Common.GLOBMAILTYPE;
import databaseshare.DatabaseGlob_mail;
import databaseshare.DatabasePlan_task;

public class GlobMailInfo {
	private Integer m_mailId;
	private HashMap<Long, Integer> m_filterMap = new HashMap<>();
	
	public boolean init(DatabaseGlob_mail mail) {
		setM_mailId(mail.getMail_id());
		//初始化m_filterMap
		if (mail.getMail_type().equals(GLOBMAILTYPE.MAIL_GM_PLAN.Number())) {
			DatabasePlan_task dbPlanTask = DbMgr.getInstance().getShareDB().
					SelectOne(DatabasePlan_task.class, "plan_key=?", mail.getFilter_value());
			if (dbPlanTask == null ||
				dbPlanTask.getFinish() == 0 ||
				dbPlanTask.getRes() == null ||
				dbPlanTask.getRes().equals(""))
				return false;
			String strArray[] = dbPlanTask.getRes().split(",");
			for(int i=0;i<strArray.length;i++) {
				addMailFilterValue(Long.valueOf(strArray[i]));
			}
		}
		else {
			String strArray[] = mail.getFilter_value().split(",");
			for(int i=0;i<strArray.length;i++) {
				addMailFilterValue(Long.valueOf(strArray[i]));
			}
		}
		return true;
	}
	
	public Integer getM_mailId() {
		return m_mailId;
	}
	
	public void setM_mailId(Integer m_mailId) {
		this.m_mailId = m_mailId;
	}
	
	public HashMap<Long, Integer> getM_filterMap() {
		return m_filterMap;
	}
	
	public void addMailFilterValue(long playerId) {
		if (playerId < 1)
			return;
		if (m_filterMap.containsKey(playerId))
			return;
		m_filterMap.put(playerId, 0);
	}
	
	public boolean checkMailFilter(long playerId) {
		if (!m_filterMap.containsKey(playerId))
			return false;
		return true;
	}
}
