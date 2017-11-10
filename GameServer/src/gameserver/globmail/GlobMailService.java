package gameserver.globmail;


import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import databaseshare.DatabaseGlob_mail;

public class GlobMailService{
	private final static Logger logger = LoggerFactory.getLogger(GlobMailService.class);
	private static FastMap<Integer, GlobMailInfo> m_infos = new FastMap<Integer, GlobMailInfo>();
	
	private final static GlobMailService instance = new GlobMailService();
	public static GlobMailService getInstance() {
		return instance;
	}
	
	public void addGlobMailInfo(DatabaseGlob_mail mail) {
		if (m_infos.containsKey(mail.getMail_id()))
			return;
		GlobMailInfo mailInfo = new GlobMailInfo();
		if (!mailInfo.init(mail))
			return;
		m_infos.put(mail.getMail_id(),mailInfo);
	}
	
	public boolean isHasMailInfo(int mailId) {
		if (!m_infos.containsKey(mailId))
			return false;
		return true;
	}
	
	public boolean isCanGetMail(int mailId,long playerId) {
		if (m_infos.containsKey(mailId)) {
			GlobMailInfo mail = m_infos.get(mailId);
			if (mail != null) {
				return mail.checkMailFilter(playerId);
			}
		}
		return false;
	}
}
