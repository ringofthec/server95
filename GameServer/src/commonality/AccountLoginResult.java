package commonality;
/** 帐号登录返回 */
public class AccountLoginResult {
    /** 返回编码 */
    public Integer code;
    /** 返回描述 */
    public String message;
    /** 处理以后的密码 */
    public String key;
    /** uid */
    public String uid;
    /** session */
    public String session;
    public AccountLoginResult() {}
    public AccountLoginResult( Integer code, String message, String key, String uid, String session)
    {
        this.code = code;
        this.message = message;
        this.key = key;
        this.uid = uid;
        this.session = session;
    }
    public Integer getCode()
    {
        return this.code;
    }
    public String getMessage()
    {
        return this.message;
    }
    public String getKey()
    {
        return this.key;
    }
    public String getUid()
    {
        return this.uid;
    }
    public String getSession()
    {
        return this.session;
    }
}