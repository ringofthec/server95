package commonality;
/** 帐号登录结构体 */
public class AccountLogin {
    /** 帐号 */
    public String user_name;
    /** 密码 */
    public String password;
    /** AppId */
    public Integer app_id;
    /** AppKey */
    public String app_key;
    public AccountLogin() {}
    public AccountLogin( String user_name, String password, Integer app_id, String app_key)
    {
        this.user_name = user_name;
        this.password = password;
        this.app_id = app_id;
        this.app_key = app_key;
    }
    public String getUser_name()
    {
        return this.user_name;
    }
    public String getPassword()
    {
        return this.password;
    }
    public Integer getApp_id()
    {
        return this.app_id;
    }
    public String getApp_key()
    {
        return this.app_key;
    }
}