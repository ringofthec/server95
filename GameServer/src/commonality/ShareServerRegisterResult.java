package commonality;
/** 分流服务器注册返回 */
public class ShareServerRegisterResult {
    /** 返回错误码 */
    public Integer code;
    /** 返回错误描述 */
    public String message;
    /** 返回服务器UID */
    public String uid;
    /** 返回服务器session */
    public String session;
    /** 返回处理服务器proto消息地址 */
    public String processAddress;
    /** 返回转发服务器消息地址 */
    public String conveyAddress;
    /** 返回的时间戳 */
    public Long time;
    /** 返回http服务端口 */
    public Integer httpPort;
    /** 返回数据库url */
    public String databaseUrl;
    /** 返回数据库帐号 */
    public String databaseUsername;
    /** 返回数据库密码 */
    public String databasePassword;
    public ShareServerRegisterResult() {}
    public ShareServerRegisterResult( Integer code, String message, String uid, String session, String processAddress, String conveyAddress, Long time, Integer httpPort, String databaseUrl, String databaseUsername, String databasePassword)
    {
        this.code = code;
        this.message = message;
        this.uid = uid;
        this.session = session;
        this.processAddress = processAddress;
        this.conveyAddress = conveyAddress;
        this.time = time;
        this.httpPort = httpPort;
        this.databaseUrl = databaseUrl;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
    }
    public Integer getCode()
    {
        return this.code;
    }
    public String getMessage()
    {
        return this.message;
    }
    public String getUid()
    {
        return this.uid;
    }
    public String getSession()
    {
        return this.session;
    }
    public String getProcessAddress()
    {
        return this.processAddress;
    }
    public String getConveyAddress()
    {
        return this.conveyAddress;
    }
    public Long getTime()
    {
        return this.time;
    }
    public Integer getHttpPort()
    {
        return this.httpPort;
    }
    public String getDatabaseUrl()
    {
        return this.databaseUrl;
    }
    public String getDatabaseUsername()
    {
        return this.databaseUsername;
    }
    public String getDatabasePassword()
    {
        return this.databasePassword;
    }
}