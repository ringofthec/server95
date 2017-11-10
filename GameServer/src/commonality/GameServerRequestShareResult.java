package commonality;
/** 客户端请求服务器地址返回 */
public class GameServerRequestShareResult {
    /** 返回错误码 */
    public Integer code;
    /** 错误描述 */
    public String message;
    /** 分流服务器操作session如果session不符就不处理 标识改分流服务器已重启过 */
    public String session;
    /** 请求的类型 */
    public Integer type;
    /** 请求的ID */
    public Integer id;
    /** 返回分流服务器IP */
    public String ip;
    /** 返回分流服务器端口 */
    public Integer port;
    public GameServerRequestShareResult() {}
    public GameServerRequestShareResult( Integer code, String message, String session, Integer type, Integer id, String ip, Integer port)
    {
        this.code = code;
        this.message = message;
        this.session = session;
        this.type = type;
        this.id = id;
        this.ip = ip;
        this.port = port;
    }
    public Integer getCode()
    {
        return this.code;
    }
    public String getMessage()
    {
        return this.message;
    }
    public String getSession()
    {
        return this.session;
    }
    public Integer getType()
    {
        return this.type;
    }
    public Integer getId()
    {
        return this.id;
    }
    public String getIp()
    {
        return this.ip;
    }
    public Integer getPort()
    {
        return this.port;
    }
}