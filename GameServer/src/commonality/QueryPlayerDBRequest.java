package commonality;
/** 游戏服务器请求玩家数据所在的库 */
public class QueryPlayerDBRequest {
    /** 登录方式 */
    public Integer type;
    /** 玩家uid */
    public String uid;
    /** 玩家player_id */
    public Long player_id;
    /** 渠道 */
    public Integer channel;
    /** 上次登录时间 */
    public String last_login_time;
    /** 上次登录ip */
    public String last_login_ip;
    /** 上次登录操作系统 */
    public String last_login_platform;
    /** 设备唯一标识 如果是IOS获取的OpenUDID */
    public String last_device_unique_identifier;
    /** session */
    public String session;
    public QueryPlayerDBRequest() {}
    public QueryPlayerDBRequest( Integer type, String uid, Long player_id, Integer channel, String last_login_time, String last_login_ip, String last_login_platform, String last_device_unique_identifier, String session)
    {
        this.type = type;
        this.uid = uid;
        this.player_id = player_id;
        this.channel = channel;
        this.last_login_time = last_login_time;
        this.last_login_ip = last_login_ip;
        this.last_login_platform = last_login_platform;
        this.last_device_unique_identifier = last_device_unique_identifier;
        this.session = session;
    }
    public Integer getType()
    {
        return this.type;
    }
    public String getUid()
    {
        return this.uid;
    }
    public Long getPlayer_id()
    {
        return this.player_id;
    }
    public Integer getChannel()
    {
        return this.channel;
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
    public String getSession()
    {
        return this.session;
    }
}