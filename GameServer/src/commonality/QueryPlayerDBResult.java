package commonality;
/** Manager返回玩家数据所在的库和id */
public class QueryPlayerDBResult {
    /** 返回错误码 */
    public Integer code;
    /** 返回错误描述 */
    public String message;
    /** 玩家id */
    public Long id;
    /** 玩家所在的DB */
    public Integer db_id;
    /** 是否创建了新账号 */
    public Integer is_create;
    /** 上次登录时间 */
    public String last_login_time;
    /** 上次登录ip */
    public String last_login_ip;
    /** 上次登录操作系统 */
    public String last_login_platform;
    /** 设备唯一标识 如果是IOS获取的OpenUDID */
    public String last_device_unique_identifier;
    public QueryPlayerDBResult() {}
    public QueryPlayerDBResult( Integer code, String message, Long id, Integer db_id, Integer is_create, String last_login_time, String last_login_ip, String last_login_platform, String last_device_unique_identifier)
    {
        this.code = code;
        this.message = message;
        this.id = id;
        this.db_id = db_id;
        this.is_create = is_create;
        this.last_login_time = last_login_time;
        this.last_login_ip = last_login_ip;
        this.last_login_platform = last_login_platform;
        this.last_device_unique_identifier = last_device_unique_identifier;
    }
    public Integer getCode()
    {
        return this.code;
    }
    public String getMessage()
    {
        return this.message;
    }
    public Long getId()
    {
        return this.id;
    }
    public Integer getDb_id()
    {
        return this.db_id;
    }
    public Integer getIs_create()
    {
        return this.is_create;
    }
    public String getLast_login_time()
    {
        return this.last_login_time;
    }
    public String getLast_login_ip()
    {
        return this.last_login_ip;
    }
    public String getLast_login_platform()
    {
        return this.last_login_platform;
    }
    public String getLast_device_unique_identifier()
    {
        return this.last_device_unique_identifier;
    }
}