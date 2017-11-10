package com.gdl.data;

import gameserver.globmail.GlobMailService;
import gameserver.jsonprotocol.Consts;
import gameserver.jsonprotocol.GDL_G2C_GetMaillListRe;
import gameserver.jsonprotocol.MailInfo;
import gameserver.jsonprotocol.MailItemInfo;
import gameserver.utils.DbMgr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.base.TableManager;

import com.commons.network.websock.PlayerConManager;
import com.commons.network.websock.PlayerConnection;
import com.commons.network.websock.handler.ItemHandler;
import com.commons.util.DatabaseInsertUpdateResult;
import com.commons.util.HttpUtil;
import com.commons.util.TimeUtil;

import commonality.Common;
import commonality.Common.GLOBMAILTYPE;
import database.CustomMailItemInfo;
import database.DatabaseMail;
import database.DatabasePlayer;
import databaseshare.CustomGlobMailItemInfo;
import databaseshare.DatabaseGlob_mail;

public class MailData {
	private static final Logger logger = LoggerFactory.getLogger(MailData.class);

	private Map<Long,DatabaseMail> mailMap = new HashMap<Long, DatabaseMail>();
	
	protected void init(PlayerData p) {
		List<DatabaseMail> mails = p.getDB().Select(DatabaseMail.class, 
				" player_id=? and is_delete=? and expire_time>? limit 10", 
				p.getPlayerId(), 0, TimeUtil.GetDateTime());
		for (DatabaseMail mail : mails)
			mailMap.put(mail.mail_id, mail);
	}
	
	public void getMailByDB(PlayerConnection con) {
		List<DatabaseMail> mails = con.getPlayer().getDB().Select(DatabaseMail.class, 
				" player_id=? and is_delete=? and expire_time>? limit 10", 
				con.getPlayerId(), 0, TimeUtil.GetDateTime());
		for (DatabaseMail mail : mails) {
			if (mailMap.containsKey(mail.mail_id)) 
				continue;
			addMail(con, mail, true);
		}
	}
	
	//发送邮件里面的物品未领取列表
	public void SendAllMailListMsg(PlayerConnection pc) {
		GDL_G2C_GetMaillListRe msg = SendMailListMsg(mailMap.values());
		pc.send(msg);
	}
	
	//发送邮件里面的物品未领取列表
	public GDL_G2C_GetMaillListRe SendMailListMsg(Collection<DatabaseMail> mailList) {
		GDL_G2C_GetMaillListRe re = new GDL_G2C_GetMaillListRe();
		re.setMode(0);
		
		for (DatabaseMail mail : mailList) {
			MailInfo mi = new MailInfo();
			mi.setMailId(mail.mail_id);
			mi.setSender(mail.mail_sender);
			mi.setTitle(mail.mail_title);
			mi.setComment(mail.mail_comment);
			mi.setExpireTime(TimeUtil.GetDateString(mail.expire_time));
			
			for (CustomMailItemInfo gi : mail.item_info) {
				MailItemInfo mii = new MailItemInfo();
				mii.setItemTempId(gi.item_id);
				mii.setItemNum((long) gi.count);
				mi.getItems().add(mii);
			}
			
			re.getMails().add(mi);
		}
		
		return re;
	}
	
	public void reciveAll(PlayerConnection pc) {
		for (DatabaseMail mail : mailMap.values()) {
			recive(pc, mail.mail_id);
		}
		
		// 清除全部邮件
		mailMap.clear();
	}
	
	//领取邮件里面的物品
	public void recive(PlayerConnection pc, long mail_id) {
		if (!mailMap.containsKey(mail_id))
			return ;
		
		DatabaseMail mail = mailMap.get(mail_id);
		if (mail.is_recv == 1)
			return ;
		
		List<CustomMailItemInfo> items = mail.item_info;
		for (CustomMailItemInfo item : items) {
			pc.getPlayer().getItemData().addItem(pc, item.item_id, item.count, Consts.getItemEventMail());
			ItemHandler.showAddItem(pc, item.item_id, item.count);
		}
		
		//修改为已领取
		mail.is_recv = 1;
		mail.is_delete = 1;
		mail.expire_time = TimeUtil.GetDateTime();
		save(mail);
	}
	
	public void read(long mail_id) {
		if (!mailMap.containsKey(mail_id))
			return ;
		
		DatabaseMail mail = mailMap.get(mail_id);
		if (mail.is_read == 1)
			return ;
		
		mail.is_read = 1;
		if (!mail.item_info.isEmpty())
			mail.expire_time = TimeUtil.GetDateTime() + 24 * 3600 * 1000;
		save(mail);
	}
	
	public void delete(long mail_id) {
		if (!mailMap.containsKey(mail_id))
			return ;
		
		DatabaseMail mail = mailMap.get(mail_id);
		mail.is_delete = 1;
		save(mail);
	}

	private void save(DatabaseMail mail) {
		mail.save();
	}


	private void addMail(PlayerConnection con, DatabaseMail databaseMail,boolean sendflag) {
		mailMap.put(databaseMail.mail_id, databaseMail);
		if (sendflag) {
			//发送新邮件的消息
			List<DatabaseMail> mails = new ArrayList<>();
			mails.add(databaseMail);
			GDL_G2C_GetMaillListRe re = SendMailListMsg(mails);
			re.setMode(1);
			con.send(re);
		}
	}
	
	public void addLoginMail(PlayerConnection con) {
		String nowTimeStr = TimeUtil.GetDateString(TimeUtil.GetDateTime());
		List<DatabaseGlob_mail> mails = DbMgr.getInstance().getShareDB()
				.Select(DatabaseGlob_mail.class, "create_time>? and create_time<=? and expire_time>?",
						con.getPlayer().getCreateTime(),
						nowTimeStr,
						nowTimeStr);
		if (mails == null)
			return;
		
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine scriptEngine = sem.getEngineByExtension("js");
		scriptEngine.put("pid", con.getPlayer().getPlayerId());
		scriptEngine.put("plvl", con.getPlayer().getLvl());
		scriptEngine.put("pviplvl", con.getPlayer().getLvl());
		Boolean ret = false;
		
		for (DatabaseGlob_mail mail : mails) {
			int ttemp = con.getPlayer().getActiveData().getActiveParam1(con, mail.mail_id);
			if (ttemp > 0)
				continue;
			
			if (mail.mail_type == GLOBMAILTYPE.MAIL_GM_FILTER.Number()) {
				if (mail.filter_value != null &&
						!mail.filter_value.equals("")) {
					try {
						ret = (Boolean)scriptEngine.eval(mail.filter_value);
					} catch (ScriptException e) {
					}
					if (!ret)
						continue;
				}
			}
			else if (mail.mail_type == GLOBMAILTYPE.MAIL_GM_SET.Number() ||
					mail.mail_type == GLOBMAILTYPE.MAIL_GM_PLAN.Number()) {
				if (mail.filter_value == null || 
						mail.filter_value.equals(""))
					continue;
				
				GlobMailService.getInstance().addGlobMailInfo(mail);
				if (!GlobMailService.getInstance().isCanGetMail(mail.mail_id, con.getPlayerId()))
					continue;
			}
			
			List<CustomMailItemInfo> customMailItemInfos = new ArrayList<>();
			for(CustomGlobMailItemInfo item : mail.item_info)
				customMailItemInfos.add(new CustomMailItemInfo(item.item_id, item.count));
			
			DatabaseMail databaseMail = new DatabaseMail();
			databaseMail.mail_sender = mail.mail_sender;
			databaseMail.mail_title =  HttpUtil.urldecode(mail.mail_title);
			databaseMail.mail_comment = HttpUtil.urldecode(mail.mail_comment);
			databaseMail.create_time = mail.create_time;
			databaseMail.expire_time= mail.expire_time;
			databaseMail.is_delete = 0;
			databaseMail.is_recv = 0;
			databaseMail.is_read = 0;
			databaseMail.item_info = customMailItemInfos;
			databaseMail.player_id = con.getPlayerId();
			DatabaseInsertUpdateResult result = con.getPlayer().getDB().Insert(databaseMail);
			databaseMail.mail_id = result.identity;
			
			con.getPlayer().getActiveData().incActiveParam1(con, mail.mail_id, 1);
			addMail(con, databaseMail,true);
		}
	}
	
	public static boolean sendSystemMail(long player_id, String title, String sender, String context, int lifeTimeByDay, Integer[] iteminfo) {
		DatabaseMail mil = createSysOfflineMail(player_id, title, sender, context, lifeTimeByDay, iteminfo);
		if (mil == null)
			return false;
		
		PlayerConnection pc = PlayerConManager.getInstance().getCon(player_id);
		if (pc != null) {
			pc.getPlayer().getMailData().addMail(pc, mil, true);
		}
		return true;
	}
	public static boolean sendSystemMail(long player_id, String title, String sender, String context, int lifeTimeByDay, List<Integer> itemids, List<Integer> nums) {
		Integer[] its = new Integer[itemids.size() * 2];
		for (int i = 0; i < itemids.size() * 2; ++i) {
			if (i % 2 == 0)
				its[i] = itemids.get(i / 2);
			else
				its[i] = nums.get(i / 2);
		}
		
		return sendSystemMail(player_id, title, sender, context, lifeTimeByDay, its);
	}
	public static boolean sendSystemMail(PlayerConnection pcon, String title, String sender, String context, int lifeTimeByDay, Integer[] iteminfo) {
		DatabaseMail mil = createSysOfflineMail(pcon.getPlayerId(), title, sender, context, lifeTimeByDay, iteminfo);
		if (mil == null)
			return false;
		
		pcon.getPlayer().getMailData().addMail(pcon, mil, true);
		return true;
	}
	
	public static DatabaseMail createSysOfflineMail(long player_id, String title, String sender, String context, int lifeTimeByDay, Integer[] iteminfo) {
		if (iteminfo.length % 2 != 0)
			return null;
		
		DatabaseMail databaseMail = new DatabaseMail();
		databaseMail.create_time = TimeUtil.GetDateTime();
		databaseMail.mail_sender = sender;
		databaseMail.expire_time = TimeUtil.GetDateTime() + Common.DAY_MILLISECOND * lifeTimeByDay;
		databaseMail.is_delete = 0;
		databaseMail.is_recv = 0;
		databaseMail.is_read = 0;
		databaseMail.mail_title = title;
		databaseMail.create_time = TimeUtil.GetDateTime();
		databaseMail.mail_comment = context;
		databaseMail.player_id = player_id;
		databaseMail.item_info =  new ArrayList<>();
		
		for (int i =0; i < iteminfo.length / 2; ++i) {
			CustomMailItemInfo temp = new CustomMailItemInfo(iteminfo[i * 2], iteminfo[i * 2 + 1]);
			databaseMail.item_info.add(temp);
		}
		
		DatabaseInsertUpdateResult res = DbMgr.getInstance().getDbByPlayerId(player_id).Insert(databaseMail);
		if (res == null || res.succeed == false)
			return null;
		
		databaseMail.mail_id = res.identity;
		return databaseMail;
	}
	
	/**创建禁言邮件*/
	public static DatabaseMail createChatWarningMail(DatabasePlayer player)
	{
		DatabaseMail databaseMail = new DatabaseMail();
		databaseMail.create_time = TimeUtil.GetDateTime();
		databaseMail.mail_sender = TableManager.GetInstance().ReadLString(
				player.getLanguage(),"LoginMail","sender",100);
		databaseMail.expire_time = TimeUtil.GetDateTime()+Common.DAY_MILLISECOND;
		databaseMail.is_delete = 0;
		databaseMail.is_recv = 0;
		databaseMail.is_read = 0;
		databaseMail.mail_title = TableManager.GetInstance().ReadLString(
				player.getLanguage(),"LoginMail","title",100);
		databaseMail.create_time = TimeUtil.GetDateTime();
		databaseMail.mail_comment = TableManager.GetInstance().ReadLString(
				player.getLanguage(),"LoginMail","content",100);
		databaseMail.item_info =  new ArrayList<>();
		databaseMail.player_id = player.getPlayer_id();
		
		if("".equals(databaseMail.mail_sender))
			databaseMail.mail_sender="Toydog Team";
		if("".equals(databaseMail.mail_title))
			databaseMail.mail_sender="Toydog Team";
		if("".equals(databaseMail.mail_comment))
			databaseMail.mail_sender="Toydog Team";
		return databaseMail;
	}
}
