package commonality;
/** 帐号验证结构体 */
public class AccountVerify {
    /** 帐号 */
    public String uid;
    /** 密码 */
    public String session;
    /** AppId */
    public Integer app_id;
    public AccountVerify() {}
    public AccountVerify( String uid, String session, Integer app_id)
    {
        this.uid = uid;
        this.session = session;
        this.app_id = app_id;
    }
    public String getUid()
    {
        return this.uid;
    }
    public String getSession()
    {
        return this.session;
    }
    public Integer getApp_id()
    {
        return this.app_id;
    }
}