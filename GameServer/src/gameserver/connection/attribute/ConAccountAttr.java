package gameserver.connection.attribute;

import gameserver.cache.AccountCache;
import gameserver.http.HttpProcessManager;
import gameserver.network.server.connection.Connection;
import gameserver.network.server.connection.ConnectionAttribute;
import gameserver.utils.DbMgr;
import commonality.Common.ACCOUNT_TYPE;
import databaseshare.DatabaseAccount;

public class ConAccountAttr extends ConnectionAttribute {
	private DatabaseAccount m_Account = null;
	//玩家登录会调用此函数
    public void Initialize(Connection connection,DatabaseAccount account)
    {
    	this.m_Con = connection;
    	this.m_Account = account;
    	this.m_Account.sync();
    	this.m_Account.serveruid = HttpProcessManager.getInstance().getUid();
    	DbMgr.getInstance().getShareDB().Update(account, "player_id = ?", this.m_Con.getPlayerId());
    	AccountCache.updateAccountServer(this.m_Account.session, this.m_Account.serveruid);
    }
	@Override
	protected void Initialize_impl() { }
	
	public DatabaseAccount getDBAccount() {
		return m_Account;
	}
    public String getUid()
    {
    	return m_Account.uid;
    }
    public String getTempUid() {
    	return m_Account.temp_uid;
    }
    public boolean getPasser() {
    	return m_Account.passer;
    }
    public String getSession()
    {
    	return m_Account.session;
    }
    public long getPlayerId()
    {
    	if (m_Account == null)
    		return 0;
    	return m_Account.player_id;
    }
    public String getClientVersion() {
    	return m_Account.last_version;
    }
    public ACCOUNT_TYPE getAccountType()
    {
    	return m_Account.type;
    }
    public String getLastDeviceUniqueIdentifier()
    {
    	return m_Account.last_device_unique_identifier;
    }
    public long getLastLoginTime() {
    	return m_Account.last_login_time;
    }
    public long getCreateTime() {
    	return m_Account.create_time;
    }
    public void updateAccount(String model, String lang, String version, String operatorsys, String operatorsys_lang) {
    	m_Account.last_model = model;
    	m_Account.last_lang = lang;
    	m_Account.last_version = version;
    	m_Account.last_opsys = operatorsys;
    	m_Account.last_opsys_lang = operatorsys_lang;
    	m_Account.save();
    	AccountCache.updateAccount(m_Account.getSession(), model, lang, version, operatorsys, operatorsys_lang);
    }
    public void gm_change_account() {
    	m_Account.temp_uid = "dummy_change_account_" + m_Account.player_id;
    	m_Account.save();
    }
}
