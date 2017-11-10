package gameserver.connection.attribute;

import gameserver.globmail.GlobMailService;
import gameserver.ipo.IPOManagerDb;
import gameserver.network.protos.game.ProMail;
import gameserver.network.protos.game.ProMail.Msg_G2C_MailUpdate;
import gameserver.network.protos.game.ProMail.Proto_MailInfo;
import gameserver.network.protos.game.ProMail.Proto_MailItem;
import gameserver.network.server.connection.Connection;
import gameserver.network.server.connection.ConnectionAttribute;
import gameserver.network.server.connection.ConnectionManager;
import gameserver.utils.DbMgr;
import gameserver.utils.Item_Channel;

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















import table.Int2;
import table.MT_Data_LoginMail;
import table.base.TableManager;

import com.commons.util.DatabaseInsertUpdateResult;
import com.commons.util.DatabaseUtil;
import com.commons.util.HttpUtil;
import com.commons.util.TimeUtil;
import com.commons.util.string;

import commonality.Common.GLOBMAILTYPE;
import commonality.Common.SETNUMBER_TYPE;
import commonality.Common;
import commonality.ITEM_ID;
import commonality.ProductChannel;
import commonality.VmChannel;
import database.CustomMailItemInfo;
import database.DatabaseMail;
import database.DatabasePlayer;
import databaseshare.CustomGlobMailItemInfo;
import databaseshare.DatabaseGlob_mail;

public class ConMailAttr extends ConnectionAttribute {
	private static final Logger logger = LoggerFactory.getLogger(ConMailAttr.class);
	// 物品数组    <mail_id, ItemInfo> 
	private Map<Long,DatabaseMail> mailMap = new HashMap<Long, DatabaseMail>();
	@Override
	protected void Initialize_impl() {
		List<DatabaseMail> mails = getDb().Select(DatabaseMail.class, 
				" player_id=? and is_delete=? and expire_time>?", 
				m_Con.getPlayerId(),0,TimeUtil.GetDateTime());
		mailMap.clear();
		for (DatabaseMail mail : mails)
			mailMap.put(mail.mail_id, mail);
	}
	
	public void getMailByDB() {
		List<DatabaseMail> mails = getDb().Select(DatabaseMail.class, 
				" player_id=? and is_delete=? and expire_time>?", 
				m_Con.getPlayerId(),0,TimeUtil.GetDateTime());
		for (DatabaseMail mail : mails) {
			if (mailMap.containsKey(mail.mail_id)) 
				continue;
			addMail(mail,true);
		}
	}
	
	//发送邮件里面的物品未领取列表
	public void SendMailListMsg() {
		SendMailListMsg(mailMap.values());
	}
	
	//领取邮件里面的物品
	public void recive(long mail_id) {
		if (!mailMap.containsKey(mail_id))
			return ;
		
		DatabaseMail mail = mailMap.get(mail_id);
		if (mail.is_recv == 1)
			return ;
		
		List<CustomMailItemInfo> items = mail.item_info;
		long s_num = IPOManagerDb.getInstance().getNextId();
		for (CustomMailItemInfo item : items)
			m_Con.getItem().setItemNumber(item.item_id, item.count, 
					SETNUMBER_TYPE.ADDITION,VmChannel.PurchaseGoods,ProductChannel.PurchaseGoods, s_num,"", Item_Channel.MAIL);
		
		//修改为已领取
		mail.is_recv = 1;
		mail.expire_time = TimeUtil.GetDateTime() + 24 * 3600 * 1000;
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
		InsertNeedUpdate(mail.mail_id);
		m_Con.pushSave(mail);
	}


	public void addMail(DatabaseMail databaseMail,boolean sendflag) {
		mailMap.put(databaseMail.mail_id, databaseMail);
		if (sendflag) {
			//发送新的消息
			List<DatabaseMail> mails = new ArrayList<>();
			mails.add(databaseMail);
			SendMailListMsg(mails);
		}
	}
	
	public void addGoldBackMail(int goldnum) {
		List<CustomMailItemInfo> customMailItemInfos = new ArrayList<>();
		customMailItemInfos.add(new CustomMailItemInfo(ITEM_ID.GOLD, goldnum));
		DatabaseMail databaseMail = new DatabaseMail();
		databaseMail.create_time = TimeUtil.GetDateTime();
		databaseMail.mail_sender = "Toydog Team";
		databaseMail.expire_time= TimeUtil.GetDateTime()+Common.DAY_MILLISECOND;
		databaseMail.is_delete = 0;
		databaseMail.is_recv = 0;
		databaseMail.is_read = 0;
		databaseMail.mail_title = "Compensation for VIP";
		databaseMail.create_time = TimeUtil.GetDateTime();
		databaseMail.mail_comment = "Thanks for your continued support to World War Toy! In this new version, everyone’s VIP points are cleared. Players whose VIP function is still activated will be sent bullion to compensate for the loss. (100 bullion for each remaining VIP day)Please tap “Receive” to collect bullion of your remaining VIP days.";
		databaseMail.item_info = customMailItemInfos;
		databaseMail.player_id = m_Con.getPlayerId();
		DatabaseInsertUpdateResult result = getDb().Insert(databaseMail);
		databaseMail.mail_id = result.identity;
		addMail(databaseMail,true);
	}
	
	public void addLoginMail() {
		String nowTimeStr = TimeUtil.GetDateString(TimeUtil.GetDateTime());
		List<DatabaseGlob_mail> mails = DbMgr.getInstance().getShareDB()
				.Select(DatabaseGlob_mail.class, "create_time>? and create_time<=? and expire_time>?",
						m_Con.getPlayer().getCreateTime(),
						nowTimeStr,
						nowTimeStr);
		if (mails == null)
			return;
		
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine scriptEngine = sem.getEngineByExtension("js");
		scriptEngine.put("player_id", m_Con.getPlayer().getPlayerId());
		scriptEngine.put("player_level", m_Con.getPlayer().getLevel());
		scriptEngine.put("legion_id", m_Con.getPlayer().getBelegionId());
		Boolean ret = false;
		
		for (DatabaseGlob_mail mail : mails) {
			int ttemp = m_Con.getReward().getActiveParam1(mail.mail_id);
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
				if (!GlobMailService.getInstance().isCanGetMail(mail.mail_id,m_Con.getPlayerId()))
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
			databaseMail.player_id = m_Con.getPlayerId();
			DatabaseInsertUpdateResult result = getDb().Insert(databaseMail);
			databaseMail.mail_id = result.identity;
			
			m_Con.getReward().incActiveParam1(mail.mail_id, 1);
			addMail(databaseMail,true);
		}
	}
	
	//发送邮件里面的物品未领取列表
	public void SendMailListMsg(Collection<DatabaseMail> malilList) {
		//邮件列表消息
		ProMail.Msg_G2C_MailInfo.Builder mailList = ProMail.Msg_G2C_MailInfo.newBuilder();
		for (DatabaseMail databaseMail : malilList) {
			Proto_MailInfo.Builder oneMail = Proto_MailInfo.newBuilder();
			oneMail.setMailId(databaseMail.mail_id);
			oneMail.setMailTitle(databaseMail.mail_title);
			oneMail.setMailComment(databaseMail.mail_comment);
			oneMail.setIsRecv(databaseMail.is_recv==1?true:false);
			oneMail.setIsRead(databaseMail.is_read==1?true:false);
			oneMail.setExpireTime(databaseMail.create_time);
			oneMail.setMailSender(databaseMail.mail_sender);
			
			//一个邮件里面的多个物品添加
			for (CustomMailItemInfo customMailItem : databaseMail.item_info) {
				Proto_MailItem.Builder mailItem= Proto_MailItem.newBuilder();
				mailItem.setItemTableId(customMailItem.item_id);
				mailItem.setNum(customMailItem.count);
				oneMail.addItems(mailItem);
			}
			mailList.addInfo(oneMail);
		}
		 m_Con.sendPushMessage(mailList.build());
	}


	@Override
	public void CheckData() {
		if (m_NeedUpdate.size() <= 0) 
			return;
		try {
			for (Long mailId : m_NeedUpdate) {
				if (!mailMap.containsKey(mailId))
					return ;
				
				DatabaseMail mail = mailMap.get(mailId);
				Msg_G2C_MailUpdate.Builder message = Msg_G2C_MailUpdate.newBuilder();
				message.setMailId(mailId);
				message.setIsRead(mail.is_read == 1);
				message.setIsRecv(mail.is_recv == 1);
				message.setIsDelete(mail.is_delete == 1);
				message.setExpireTime(mail.expire_time);
				m_Con.sendPushMessage(message.build());
			}
		} catch (Exception e) {
			logger.error("ConnectionMailAttribute.CheckData is error : " , e);
		}
		m_NeedUpdate.clear();
	}

	/**发送系统邮件 */
	public static void sendSystemMail(DatabaseMail mail)
	{
		DatabaseUtil db =DbMgr.getInstance().getDbByPlayerId(mail.player_id);
		DatabaseInsertUpdateResult result=db.Insert(mail);
		mail.mail_id = result.identity;
		Connection con=ConnectionManager.GetInstance().GetConnection(mail.player_id);
		if(con==null)
			return ;
		con.getMails().addMail(mail,true);
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
	
	public void addSevenDayMail(int dayNum)
	{
		int id = 700+dayNum;
		MT_Data_LoginMail mailData = TableManager.GetInstance().TableLoginMail().GetElement(id);
		if (mailData == null)
			return;
		int languageId = 1;
		languageId = m_Con.getPlayer().getLanguage();
		DatabaseMail databaseMail = new DatabaseMail();
		databaseMail.create_time = TimeUtil.GetDateTime();
		databaseMail.mail_sender = TableManager.GetInstance().ReadLString(
				languageId,"LoginMail","sender",id);
		databaseMail.expire_time= TimeUtil.GetDateTime()+Common.MONTH_MILLISECOND;
		databaseMail.is_delete = 0;
		databaseMail.is_recv = 0;
		databaseMail.is_read = 0;
		databaseMail.mail_title = TableManager.GetInstance().ReadLString(
				languageId,"LoginMail","title",id);
		databaseMail.create_time = TimeUtil.GetDateTime();
		databaseMail.mail_comment = TableManager.GetInstance().ReadLString(
				languageId,"LoginMail","content",id);
		databaseMail.item_info = new ArrayList<>();
		for(Int2 itemInfo:mailData.getM_arrayitems()) {
			databaseMail.item_info.add(new CustomMailItemInfo(itemInfo.field1(),itemInfo.field2()));
		}
		databaseMail.player_id = m_Con.getPlayer().getPlayerId();
		
		if("".equals(databaseMail.mail_sender))
			databaseMail.mail_sender="Toydog Team";
		if("".equals(databaseMail.mail_title))
			databaseMail.mail_sender="Toydog Team";
		if("".equals(databaseMail.mail_comment))
			databaseMail.mail_sender="Toydog Team";
		
		DatabaseInsertUpdateResult result = getDb().Insert(databaseMail);
		databaseMail.mail_id = result.identity;
		
		addMail(databaseMail,false);
	}
	
}
